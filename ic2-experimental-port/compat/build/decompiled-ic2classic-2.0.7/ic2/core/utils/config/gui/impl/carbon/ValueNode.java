/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.utils.ParseResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Objects;

public class ValueNode
implements IValueNode {
    ObjectArrayList<String> previous = new ObjectArrayList();
    String current;
    String defaultValue;
    ConfigEntry<?> entry;

    public ValueNode(ConfigEntry<?> entry) {
        this.entry = entry;
        String start = entry.serialize();
        this.previous.push((Object)start);
        this.current = start;
        this.defaultValue = entry.serializeDefault();
    }

    public void save() {
        this.entry.deserializeValue(this.current);
    }

    @Override
    public String get() {
        return this.current;
    }

    @Override
    public void set(String value) {
        this.current = value;
    }

    @Override
    public ParseResult<Boolean> isValid(String value) {
        return this.entry.canSetValue(value);
    }

    @Override
    public boolean isDefault() {
        return Objects.equals(this.defaultValue, this.current);
    }

    @Override
    public boolean isChanged() {
        return !Objects.equals(this.previous.top(), this.current);
    }

    @Override
    public boolean isCompoundNode() {
        return false;
    }

    @Override
    public void setDefault() {
        this.current = this.defaultValue;
    }

    @Override
    public void setPrevious() {
        this.current = (String)this.previous.top();
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
    }

    @Override
    public void createTemp() {
        this.previous.push((Object)this.current);
    }

    @Override
    public void apply() {
        if (this.previous.size() > 1) {
            this.previous.pop();
        }
    }
}

