/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.buffers;

import ic2.api.network.buffer.IBitLevelOverride;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.core.IC2;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FieldBuffer {
    final Class<? extends BlockEntity> tile;
    Map<String, FieldInfo> fieldNameToIndex = CollectionUtils.createMap();
    Int2ObjectMap<FieldInfo> indexToField = new Int2ObjectOpenHashMap();

    public FieldBuffer(Class<? extends BlockEntity> clz) {
        this.tile = clz;
    }

    public void initClass(BlockEntity tileData) {
        try {
            Field field;
            IBitLevelOverride override = tileData instanceof IBitLevelOverride ? (IBitLevelOverride)tileData : null;
            ObjectList toProcess = CollectionUtils.createList();
            for (Class<? extends BlockEntity> currentClass = this.tile; currentClass != null && currentClass != BlockEntity.class; currentClass = currentClass.getSuperclass()) {
                Field[] fields = currentClass.getDeclaredFields();
                for (int i = fields.length - 1; i >= 0; --i) {
                    field = fields[i];
                    field.setAccessible(true);
                    if (field.getDeclaredAnnotation(NetworkInfo.class) != null) {
                        toProcess.add((Field)field);
                        continue;
                    }
                    String name = field.getName();
                    this.fieldNameToIndex.put(name, new FieldInfo(field, name));
                }
            }
            int index = 0;
            for (int i = toProcess.size() - 1; i >= 0; --i) {
                field = (Field)toProcess.get(i);
                NetworkInfo data = field.getDeclaredAnnotation(NetworkInfo.class);
                String name = field.getName();
                FieldInfo info = new FieldInfo(field, name, index++, data, override);
                this.fieldNameToIndex.put(name, info);
                this.indexToField.put(info.getIndex(), (Object)info);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FieldInfo getInfo(String id) {
        return this.fieldNameToIndex.get(id);
    }

    public FieldInfo getInfo(int index) {
        return (FieldInfo)this.indexToField.get(index);
    }

    public List<FieldInfo> getAllInfos() {
        return new ObjectArrayList(this.indexToField.values());
    }

    public static class FieldInfo {
        Field field;
        boolean isNew;
        int index;
        String fieldName;
        NetworkInfo.BitLevel compression;

        public FieldInfo(Field theField, String fieldID) {
            this.field = theField;
            this.fieldName = fieldID;
            this.index = -1;
            this.isNew = false;
            this.compression = NetworkInfo.BitLevel.BIT_0;
        }

        public FieldInfo(Field theField, String fieldID, int indexValue, NetworkInfo fieldData, IBitLevelOverride override) {
            this.field = theField;
            this.fieldName = fieldID;
            this.index = indexValue;
            this.isNew = this.index != -1;
            this.compression = fieldData.value();
            if (override != null && override.hasOverride(this.index, this.fieldName)) {
                this.compression = override.getOverride(this.index, this.fieldName);
            }
        }

        public int getIndex() {
            return this.index;
        }

        public boolean isNew() {
            return this.isNew;
        }

        public NetworkInfo.BitLevel getBits() {
            return this.compression;
        }

        public String getFieldID() {
            return this.fieldName;
        }

        public boolean isDefault(INetworkFieldProvider tile) {
            return tile.isDefaultData(this.fieldName);
        }

        public Object getData(BlockEntity tile) {
            try {
                return this.field.get(tile);
            }
            catch (Exception e) {
                return null;
            }
        }

        public void setData(BlockEntity tile, Object obj) throws Exception {
            if (!this.isNew) {
                IC2.LOGGER.info("TileEntity: (" + tile.getClass().getSimpleName() + ".java:0) uses a deprecated IC2C Networking. Please Implement @NetworkInfo at field: " + this.fieldName);
            }
            if (this.field.getType().isEnum() && obj != null) {
                obj = Enum.valueOf(this.field.getType(), (String)obj);
            }
            if (INetworkDataBuffer.class.isAssignableFrom(this.field.getType()) && obj instanceof InputBuffer) {
                ((INetworkDataBuffer)this.field.get(tile)).read((IInputBuffer)obj);
            } else {
                this.field.set(tile, obj);
            }
        }
    }
}

