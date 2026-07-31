/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 */
package ic2.core.utils.config.gui.impl.carbon;

import com.google.common.base.Objects;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.utils.ParseResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;

public class ArrayNode
implements IArrayNode {
    List<WrappedEntry> values = new ObjectArrayList();
    ConfigEntry<?> entry;
    ConfigEntry.IArrayConfig config;
    DataType type;
    ObjectArrayList<List<String>> previous = new ObjectArrayList();
    List<String> currentValues;
    List<String> defaults;

    public ArrayNode(ConfigEntry<?> entry, ConfigEntry.IArrayConfig config, DataType type) {
        this.entry = entry;
        this.config = config;
        this.type = type;
        this.previous.push(config.getEntries());
        this.currentValues = config.getEntries();
        this.defaults = config.getDefaults();
        this.reload();
    }

    public void save() {
        this.config.setArray(this.currentValues);
    }

    protected void reload() {
        this.values.clear();
        for (int i = 0; i < this.currentValues.size(); ++i) {
            this.values.add(new WrappedEntry(this, this.config, this.currentValues, i, i >= this.defaults.size() ? null : this.defaults.get(i)));
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
    public IValueNode get(int index) {
        return this.values.get(index);
    }

    @Override
    public int indexOf(INode value) {
        return this.values.indexOf(value);
    }

    @Override
    public void createNode() {
        this.currentValues.add(this.type.getDefaultValue());
        this.values.add(new WrappedEntry(this, this.config, this.currentValues, this.values.size(), null));
    }

    @Override
    public void removeNode(int index) {
        this.values.remove(index);
        this.currentValues.remove(index);
    }

    public static class WrappedEntry
    implements IValueNode {
        IArrayNode owner;
        ConfigEntry.IArrayConfig array;
        List<String> values;
        String defaultValue;
        ObjectArrayList<String> previous = new ObjectArrayList();

        public WrappedEntry(IArrayNode owner, ConfigEntry.IArrayConfig array, List<String> values, int index, String defaultValue) {
            this.owner = owner;
            this.array = array;
            this.values = values;
            this.defaultValue = defaultValue;
            this.previous.add((Object)values.get(index));
        }

        private int getIndex() {
            return this.owner.indexOf(this);
        }

        @Override
        public String get() {
            return this.values.get(this.getIndex());
        }

        @Override
        public void set(String value) {
            this.values.set(this.getIndex(), value);
        }

        @Override
        public ParseResult<Boolean> isValid(String value) {
            return this.array.canSetArray((List<String>)ObjectLists.singleton((Object)value));
        }

        @Override
        public boolean isCompoundNode() {
            return false;
        }

        @Override
        public boolean isDefault() {
            return this.defaultValue == null || Objects.equal((Object)this.defaultValue, (Object)this.values.get(this.getIndex()));
        }

        @Override
        public boolean isChanged() {
            return !Objects.equal((Object)this.previous.top(), (Object)this.values.get(this.getIndex()));
        }

        @Override
        public void setDefault() {
            this.values.set(this.getIndex(), this.defaultValue == null ? "" : this.defaultValue);
        }

        @Override
        public void setPrevious() {
            this.values.set(this.getIndex(), (String)this.previous.top());
            if (this.previous.size() > 1) {
                this.previous.pop();
            }
        }

        @Override
        public void createTemp() {
            this.previous.push((Object)this.values.get(this.getIndex()));
        }

        @Override
        public void apply() {
            if (this.previous.size() > 1) {
                this.previous.pop();
            }
        }
    }
}

