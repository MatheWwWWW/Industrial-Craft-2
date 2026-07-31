/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.apache.logging.log4j.util.Strings
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.impl.carbon.ArrayNode;
import ic2.core.utils.config.gui.impl.carbon.ValueNode;
import ic2.core.utils.config.impl.ReloadMode;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.logging.log4j.util.Strings;

public class ConfigLeaf
implements IConfigNode {
    ConfigEntry<?> entry;
    ValueNode value;
    ArrayNode arrayValue;

    public ConfigLeaf(ConfigEntry<?> entry) {
        this.entry = entry;
    }

    @Override
    public List<IConfigNode> getChildren() {
        return null;
    }

    @Override
    public IValueNode asValue() {
        if (this.isArray()) {
            return null;
        }
        if (this.value == null) {
            this.value = new ValueNode(this.entry);
        }
        return this.value;
    }

    @Override
    public IArrayNode asArray() {
        if (!this.isArray()) {
            return null;
        }
        if (this.arrayValue == null) {
            this.arrayValue = new ArrayNode(this.entry, (ConfigEntry.IArrayConfig)((Object)this.entry), DataType.bySimple(this.entry.getDataType().asDataType()));
        }
        return this.arrayValue;
    }

    @Override
    public ICompoundNode asCompound() {
        return null;
    }

    @Override
    public List<DataType> getDataType() {
        return ObjectLists.singleton((Object)DataType.bySimple(this.entry.getDataType().asDataType()));
    }

    @Override
    public List<ConfigEntry.Suggestion> getValidValues() {
        return this.entry.getSuggestions();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public boolean isArray() {
        return this.entry instanceof ConfigEntry.IArrayConfig;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean isChanged() {
        return this.value != null && this.value.isChanged() || this.arrayValue != null && this.arrayValue.isChanged();
    }

    @Override
    public void save() {
        if (this.value != null) {
            this.value.save();
        }
        if (this.arrayValue != null) {
            this.arrayValue.save();
        }
    }

    @Override
    public void setPrevious() {
        if (this.value != null) {
            this.value.setPrevious();
        }
        if (this.arrayValue != null) {
            this.arrayValue.setPrevious();
        }
    }

    @Override
    public void setDefault() {
        if (this.isArray()) {
            if (this.arrayValue == null) {
                this.asArray();
            }
            this.arrayValue.setDefault();
        } else {
            if (this.value == null) {
                this.asValue();
            }
            this.value.setDefault();
        }
    }

    @Override
    public boolean requiresRestart() {
        return this.entry.getReloadState() == ReloadMode.GAME;
    }

    @Override
    public boolean requiresReload() {
        return this.entry.getReloadState() == ReloadMode.WORLD;
    }

    @Override
    public Component getName() {
        return IConfigNode.createLabel(this.entry.getKey());
    }

    @Override
    public Component getTooltip() {
        String limit;
        MutableComponent comp = Component.m_237119_();
        comp.m_7220_((Component)Component.m_237113_((String)this.entry.getKey()).m_130940_(ChatFormatting.YELLOW));
        String[] array = this.entry.getComment();
        if (array != null && array.length > 0) {
            int i = 0;
            while (i < array.length) {
                comp.m_130946_("\n").m_130946_(array[i++]).m_130940_(ChatFormatting.GRAY);
            }
        }
        if (!Strings.isBlank((String)(limit = this.entry.getLimitations()))) {
            comp.m_130946_("\n").m_7220_((Component)Component.m_237113_((String)limit).m_130940_(ChatFormatting.BLUE));
        }
        return comp;
    }
}

