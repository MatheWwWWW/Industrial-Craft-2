/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package ic2.core.network;

import ic2.core.block.TileEntityBlock;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.util.math.BlockPos;

class TeUpdateDataClient {
    private final List<TeData> updates = new ArrayList<TeData>();

    TeUpdateDataClient() {
    }

    public TeData addTe(BlockPos pos, int fieldCount) {
        TeData ret = new TeData(pos, fieldCount);
        this.updates.add(ret);
        return ret;
    }

    public Collection<TeData> getTes() {
        return this.updates;
    }

    static class FieldData {
        final String name;
        final Object value;
        Field field;

        private FieldData(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    static class TeData {
        final BlockPos pos;
        private final List<FieldData> fields;
        Class<? extends TileEntityBlock> teClass;

        private TeData(BlockPos pos, int fieldCount) {
            this.pos = pos;
            this.fields = new ArrayList<FieldData>(fieldCount);
        }

        public void addField(String name, Object value) {
            this.fields.add(new FieldData(name, value));
        }

        public Collection<FieldData> getFields() {
            return this.fields;
        }
    }
}

