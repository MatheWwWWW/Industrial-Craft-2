/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.misc;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkFieldNotifier;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.core.IC2;
import ic2.core.networking.FieldEncoder;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.buffers.FieldBuffer;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NetworkUtils {
    public static List<NetworkManager.FieldData> createFieldData(INetworkFieldProvider prov) {
        if (!(prov instanceof BlockEntity)) {
            return Collections.emptyList();
        }
        BlockEntity tile = (BlockEntity)prov;
        FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
        ArrayList<NetworkManager.FieldData> fields = new ArrayList<NetworkManager.FieldData>();
        for (String s : prov.getNetworkFields()) {
            FieldBuffer.FieldInfo info = buffer.getInfo(s);
            if (info == null || info.isDefault(prov)) continue;
            fields.add(new NetworkManager.FieldData(tile, info));
        }
        return fields;
    }

    public static void writeData(OutputBuffer buffer, Map<BlockPos, List<NetworkManager.FieldData>> data) {
        buffer.writeMedium(data.size());
        for (Map.Entry<BlockPos, List<NetworkManager.FieldData>> entry : data.entrySet()) {
            buffer.writeLong(entry.getKey().m_121878_());
            NetworkUtils.writeEntries(buffer, entry.getValue());
        }
    }

    public static void writeEntries(OutputBuffer buffer, List<NetworkManager.FieldData> data) {
        buffer.writeByte((byte)data.size());
        for (NetworkManager.FieldData subData : data) {
            if (subData.isNew) {
                buffer.writeByte((byte)(1 + subData.bitLevel.getIndex()));
                buffer.writeByte((byte)subData.index);
                FieldEncoder.encode(buffer, subData.data, subData.bitLevel);
                continue;
            }
            buffer.writeByte((byte)0);
            buffer.writeString(subData.fieldName);
            FieldEncoder.encode(buffer, subData.data);
        }
    }

    public static void applyFields(BlockEntity tile, List<NetworkManager.FieldData> data, boolean gui, Player player) {
        INetworkFieldNotifier notify = tile instanceof INetworkFieldNotifier ? (INetworkFieldNotifier)tile : null;
        FieldBuffer buffer = NetworkManager.getFieldBuffer(tile);
        data.sort(Comparator.comparingInt(K -> K.index));
        ObjectSet fieldsChanged = CollectionUtils.createSet();
        for (NetworkManager.FieldData fields : data) {
            FieldBuffer.FieldInfo info;
            FieldBuffer.FieldInfo fieldInfo = info = fields.isNew ? buffer.getInfo(fields.index) : buffer.getInfo(fields.fieldName);
            if (info == null) {
                IC2.LOGGER.info("Field: [New=" + fields.isNew + ", Index=" + fields.index + ", Name=" + fields.fieldName + "], Not Existent In the clientSide");
                continue;
            }
            try {
                info.setData(tile, fields.data);
            }
            catch (Exception e) {
                IC2.LOGGER.catching((Throwable)e);
                continue;
            }
            fieldsChanged.add((String)info.getFieldID());
        }
        if (notify != null) {
            if (gui) {
                notify.onGuiFieldChanged((Set<String>)fieldsChanged, player);
            } else {
                notify.onNetworkFieldChanged((Set<String>)fieldsChanged, player);
            }
        }
    }

    public static Map<BlockPos, List<NetworkManager.FieldData>> readData(InputBuffer buffer) {
        Object2ObjectSortedMap<BlockPos, List<NetworkManager.FieldData>> result = CollectionUtils.createLinkedMap();
        int length = buffer.readMedium();
        for (int i = 0; i < length; ++i) {
            BlockPos pos = BlockPos.m_122022_((long)buffer.readLong());
            result.put(pos, NetworkUtils.readEntries(buffer));
        }
        return result;
    }

    public static List<NetworkManager.FieldData> readEntries(InputBuffer buffer) {
        int expected = buffer.readByte();
        ArrayList<NetworkManager.FieldData> list = new ArrayList<NetworkManager.FieldData>(expected);
        for (int x = 0; x < expected; ++x) {
            byte data = buffer.readByte();
            if (data > 0) {
                byte index = buffer.readByte();
                NetworkInfo.BitLevel level = NetworkInfo.BitLevel.getLevel(data - 1);
                list.add(new NetworkManager.FieldData(index, level, FieldEncoder.decode(buffer, level)));
                continue;
            }
            String id = buffer.readString();
            list.add(new NetworkManager.FieldData(id, FieldEncoder.decode(buffer)));
        }
        return list;
    }
}

