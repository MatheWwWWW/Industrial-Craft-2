/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package ic2.core.network;

import ic2.core.block.tileentity.Ic2TileEntityBlock;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;

class TeUpdateDataClient {
    private final List<TeData> updates = new ArrayList<TeData>();

    TeUpdateDataClient() {
    }

    public TeData addTe(BlockPos blockPos, int n) {
        TeData teData = new TeData(blockPos, n);
        this.updates.add(teData);
        return teData;
    }

    public Collection<TeData> getTes() {
        return this.updates;
    }

    static class TeData {
        final BlockPos pos;
        private final List<FieldData> fields;
        Ic2TileEntityBlock teType;

        private TeData(BlockPos blockPos, int n) {
            this.pos = blockPos;
            this.fields = new ArrayList<FieldData>(n);
        }

        public void addField(String string, Object object) {
            this.fields.add(new FieldData(string, object));
        }

        public Collection<FieldData> getFields() {
            return this.fields;
        }

        public boolean hasClass() {
            return this.teType != null;
        }
    }

    static class FieldData {
        final String name;
        final Object value;
        Field field;

        private FieldData(String string, Object object) {
            this.name = string;
            this.value = object;
        }
    }
}

