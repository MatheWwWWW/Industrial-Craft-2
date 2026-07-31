/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
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
import ic2.core.utils.config.gui.impl.carbon.CompoundArrayNode;
import ic2.core.utils.config.gui.impl.carbon.CompoundNode;
import ic2.core.utils.config.impl.ReloadMode;
import ic2.core.utils.config.utils.Helpers;
import ic2.core.utils.config.utils.IEntryDataType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.logging.log4j.util.Strings;

public class ConfigCompoundLeaf
implements IConfigNode {
    ConfigEntry<?> entry;
    CompoundNode value;
    CompoundArrayNode array;

    public ConfigCompoundLeaf(ConfigEntry<?> entry) {
        this.entry = entry;
    }

    @Override
    public List<IConfigNode> getChildren() {
        return null;
    }

    @Override
    public IValueNode asValue() {
        return null;
    }

    @Override
    public IArrayNode asArray() {
        if (!this.isArray()) {
            return null;
        }
        if (this.array == null) {
            this.array = new CompoundArrayNode(this.entry, (ConfigEntry.IArrayConfig)((Object)this.entry), this.getDataType(), this.generateNames());
        }
        return this.array;
    }

    @Override
    public ICompoundNode asCompound() {
        if (this.isArray()) {
            return null;
        }
        if (this.value == null) {
            this.value = new CompoundNode(this.entry, this.getDataType(), this.generateNames());
        }
        return this.value;
    }

    private String[] generateNames() {
        List<Map.Entry<String, IEntryDataType.EntryDataType>> types = this.entry.getDataType().asCompound().getCompound();
        String[] names = new String[types.size()];
        int m = types.size();
        for (int i = 0; i < m; ++i) {
            names[i] = types.get(i).getKey();
        }
        return names;
    }

    @Override
    public List<DataType> getDataType() {
        IEntryDataType.CompoundDataType compound = this.entry.getDataType().asCompound();
        ObjectArrayList result = new ObjectArrayList();
        for (Map.Entry<String, IEntryDataType.EntryDataType> entry : compound.getCompound()) {
            result.add(DataType.byConfig(entry.getValue(), compound.getVariant(entry.getKey())));
        }
        return result;
    }

    @Override
    public List<ConfigEntry.Suggestion> getValidValues() {
        return this.entry.getSuggestions();
    }

    @Override
    public boolean isArray() {
        return this.entry instanceof ConfigEntry.IArrayConfig;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public boolean isChanged() {
        return this.value != null && this.value.isChanged() || this.array != null && this.array.isChanged();
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
    public void save() {
        if (this.value != null) {
            this.value.save();
        }
        if (this.array != null) {
            this.array.save();
        }
    }

    @Override
    public void setPrevious() {
        if (this.value != null) {
            this.value.setPrevious();
        }
        if (this.array != null) {
            this.array.setPrevious();
        }
    }

    @Override
    public void setDefault() {
        if (this.isArray()) {
            if (this.array == null) {
                this.asArray();
            }
            this.array.setDefault();
        } else {
            if (this.value == null) {
                this.asCompound();
            }
            this.value.setDefault();
        }
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
            comp.m_130946_("\n");
            int i = 0;
            while (i < array.length) {
                comp.m_130946_(array[i++]).m_130946_("\n");
            }
        }
        if (!Strings.isBlank((String)(limit = this.entry.getLimitations()))) {
            String[] split = Helpers.splitArray(limit, ",");
            int i = 0;
            int m = split.length;
            while (i < m) {
                comp.m_130946_("\n").m_7220_((Component)Component.m_237113_((String)split[i++]).m_130940_(ChatFormatting.GRAY));
            }
        }
        return comp;
    }
}

