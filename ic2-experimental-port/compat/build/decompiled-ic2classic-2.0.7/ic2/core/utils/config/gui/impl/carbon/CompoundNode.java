/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.impl.carbon.ICompoundProvider;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.ParseResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;

public class CompoundNode
implements ICompoundNode,
ICompoundProvider {
    ConfigEntry<?> entry;
    List<DataType> types;
    String[] names;
    List<IValueNode> values = new ObjectArrayList();
    ObjectArrayList<String[]> previous = new ObjectArrayList();
    String[] current;
    String[] defaultValues;

    public CompoundNode(ConfigEntry<?> entry, List<DataType> types, String[] names) {
        this.entry = entry;
        this.types = types;
        this.names = names;
        String[] value = Helpers.splitArray(entry.serialize(), ";");
        this.current = value;
        this.previous.push((Object)Arrays.copyOf(value, value.length));
        this.defaultValues = Helpers.splitArray(entry.serializeDefault(), ";");
        this.reload();
    }

    public void save() {
        this.entry.deserializeValue(Helpers.mergeCompound(this.current));
    }

    private void reload() {
        this.values.clear();
        int m = this.types.size();
        for (int i = 0; i < m; ++i) {
            this.values.add(new CompoundValue(this.types.get(i), i, this, this.current, i >= this.defaultValues.length ? "" : this.defaultValues[i]));
        }
    }

    @Override
    public Component getName(int index) {
        return Component.m_237113_((String)this.names[index]);
    }

    @Override
    public void set(String value) {
        this.current = Helpers.splitArray(value, ";");
    }

    @Override
    public ParseResult<Boolean> isValid(String value) {
        return this.entry.canSetValue(value);
    }

    @Override
    public String get() {
        return Helpers.mergeCompound(this.current);
    }

    private String[] getPrev() {
        return (String[])this.previous.top();
    }

    @Override
    public boolean isDefault() {
        return Arrays.equals(this.defaultValues, this.current);
    }

    @Override
    public boolean isChanged() {
        return !Arrays.equals(this.getPrev(), this.current);
    }

    @Override
    public void setDefault() {
        this.current = Arrays.copyOf(this.defaultValues, this.defaultValues.length);
    }

    @Override
    public void setPrevious() {
        String[] prev = this.getPrev();
        this.current = Arrays.copyOf(prev, prev.length);
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
    }

    @Override
    public boolean isValid() {
        return this.entry.canSetValue(Helpers.mergeCompound(this.current)).getValue();
    }

    @Override
    public ParseResult<Boolean> isValid(String value, int index) {
        return this.entry.canSetValue(this.buildValue(value, index));
    }

    private String buildValue(String value, int index) {
        String[] copy = Arrays.copyOf(this.current, this.current.length);
        copy[index] = value;
        return Helpers.mergeCompound(copy);
    }

    @Override
    public void createTemp() {
        this.previous.push((Object)Arrays.copyOf(this.current, this.current.length));
        this.reload();
    }

    @Override
    public void apply() {
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
    }

    @Override
    public List<IValueNode> getValues() {
        return this.values;
    }

    public static class CompoundValue
    implements IValueNode {
        DataType type;
        int index;
        ICompoundProvider compound;
        String[] values;
        String defaultValue;
        ObjectArrayList<String> startingValue = new ObjectArrayList();

        public CompoundValue(DataType type, int index, ICompoundProvider compound, String[] values, String defaultValue) {
            this.type = type;
            this.index = index;
            this.compound = compound;
            this.values = values;
            this.startingValue.push((Object)values[index]);
            this.defaultValue = defaultValue;
        }

        @Override
        public boolean isDefault() {
            return this.values[this.index].equals(this.defaultValue);
        }

        @Override
        public boolean isChanged() {
            return !this.values[this.index].equals(this.startingValue.top());
        }

        @Override
        public boolean isCompoundNode() {
            return true;
        }

        @Override
        public void setDefault() {
            this.values[this.index] = this.defaultValue;
        }

        @Override
        public void setPrevious() {
            this.values[this.index] = (String)this.startingValue.top();
            if (this.startingValue.size() > 1) {
                this.startingValue.pop();
            }
        }

        @Override
        public void createTemp() {
            this.startingValue.push((Object)this.values[this.index]);
        }

        @Override
        public void apply() {
            if (this.startingValue.size() > 1) {
                this.startingValue.pop();
            }
        }

        @Override
        public String get() {
            return this.values[this.index];
        }

        @Override
        public void set(String value) {
            this.values[this.index] = value;
        }

        @Override
        public ParseResult<Boolean> isValid(String value) {
            return this.compound.isValid(value, this.index);
        }
    }
}

