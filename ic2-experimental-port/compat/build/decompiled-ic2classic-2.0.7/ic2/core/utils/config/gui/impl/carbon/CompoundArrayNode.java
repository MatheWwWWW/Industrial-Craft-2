/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.impl.carbon.CompoundNode;
import ic2.core.utils.config.gui.impl.carbon.ICompoundProvider;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.ParseResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;

public class CompoundArrayNode
implements IArrayNode {
    ConfigEntry<?> entry;
    ConfigEntry.IArrayConfig config;
    String[] names;
    List<DataType> dataTypes;
    List<ICompoundNode> values = new ObjectArrayList();
    ObjectArrayList<List<String>> previous = new ObjectArrayList();
    List<String> currentValues;
    List<String> defaults;
    String defaultEmptyValue;

    public CompoundArrayNode(ConfigEntry<?> entry, ConfigEntry.IArrayConfig config, List<DataType> dataTypes, String[] names) {
        this.entry = entry;
        this.config = config;
        this.dataTypes = dataTypes;
        this.names = names;
        this.previous.push(config.getEntries());
        this.currentValues = config.getEntries();
        this.defaults = config.getDefaults();
        String[] data = new String[dataTypes.size()];
        int m = dataTypes.size();
        for (int i = 0; i < m; ++i) {
            data[i] = dataTypes.get(i).getDefaultValue();
        }
        this.defaultEmptyValue = Helpers.mergeCompound(data);
    }

    public void save() {
        this.config.setArray(this.currentValues);
    }

    protected void reload() {
        this.values.clear();
        for (int i = 0; i < this.currentValues.size(); ++i) {
            this.values.add(new CompoundEntry(this, this.config, this.currentValues, i >= this.defaults.size() ? this.defaultEmptyValue : this.defaults.get(i), i, this.dataTypes, this.names));
        }
    }

    protected List<String> getPrev() {
        return (List)this.previous.top();
    }

    @Override
    public boolean isChanged() {
        return !this.getPrev().equals(this.currentValues);
    }

    @Override
    public boolean isDefault() {
        return this.currentValues.equals(this.defaults);
    }

    @Override
    public void setPrevious() {
        this.currentValues.clear();
        this.currentValues.addAll(this.getPrev());
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
        this.reload();
    }

    @Override
    public void setDefault() {
        this.currentValues.clear();
        this.currentValues.addAll(this.defaults);
        this.reload();
    }

    @Override
    public void createTemp() {
        this.previous.push((Object)new ObjectArrayList(this.currentValues));
        this.reload();
    }

    @Override
    public void apply() {
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public ICompoundNode get(int index) {
        return this.values.get(index);
    }

    @Override
    public int indexOf(INode value) {
        return this.values.indexOf(value);
    }

    @Override
    public void createNode() {
        this.currentValues.add(this.defaultEmptyValue);
        this.values.add(new CompoundEntry(this, this.config, this.currentValues, this.defaultEmptyValue, this.values.size(), this.dataTypes, this.names));
    }

    @Override
    public void removeNode(int index) {
        this.values.remove(index);
        this.currentValues.remove(index);
    }

    public static class CompoundEntry
    implements ICompoundNode,
    ICompoundProvider {
        ConfigEntry.IArrayConfig config;
        IArrayNode node;
        List<DataType> types;
        String[] names;
        List<IValueNode> values = new ObjectArrayList();
        List<String> results;
        ObjectArrayList<String[]> previous = new ObjectArrayList();
        String[] current;
        String[] defaultValues;

        public CompoundEntry(IArrayNode node, ConfigEntry.IArrayConfig config, List<String> values, String defaultValue, int index, List<DataType> types, String[] names) {
            this.node = node;
            this.config = config;
            this.types = types;
            this.results = values;
            this.names = names;
            String[] value = Helpers.splitArray(values.get(index), ";");
            this.current = value;
            this.previous.push((Object)Arrays.copyOf(value, value.length));
            this.defaultValues = Helpers.splitArray(defaultValue, ";");
            this.reload();
        }

        public void reload() {
            this.values.clear();
            int m = this.types.size();
            for (int i = 0; i < m; ++i) {
                this.values.add(new CompoundNode.CompoundValue(this.types.get(i), i, this, this.current, i >= this.defaultValues.length ? "" : this.defaultValues[i]));
            }
        }

        @Override
        public void set(String value) {
            this.current = Helpers.splitArray(value, ";");
        }

        @Override
        public ParseResult<Boolean> isValid(String value) {
            return this.config.canSetArray((List<String>)ObjectLists.singleton((Object)value));
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
            return this.config.canSetArray((List<String>)ObjectLists.singleton((Object)Helpers.mergeCompound(this.current))).getValue();
        }

        @Override
        public ParseResult<Boolean> isValid(String value, int index) {
            return this.canSkip(value, index) ? ParseResult.success(true) : this.config.canSetArray((List<String>)ObjectLists.singleton((Object)this.buildValue(value, index)));
        }

        private boolean canSkip(String newValue, int index) {
            if (!this.types.get(index).isAllowEmptyValue() && newValue.trim().isEmpty()) {
                return true;
            }
            int m = this.current.length;
            for (int i = 0; i < m; ++i) {
                if (i == index || this.types.get(index).isAllowEmptyValue() || !this.current[i].trim().isEmpty()) continue;
                return true;
            }
            return false;
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
            this.results.set(this.node.indexOf(this), Helpers.mergeCompound(this.current));
        }

        @Override
        public List<IValueNode> getValues() {
            return this.values;
        }

        @Override
        public Component getName(int index) {
            return Component.m_237113_((String)this.names[index]);
        }
    }
}

