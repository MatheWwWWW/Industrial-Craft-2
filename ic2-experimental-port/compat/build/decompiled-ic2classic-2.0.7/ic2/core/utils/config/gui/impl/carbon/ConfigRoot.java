/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.Config;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.gui.impl.carbon.ConfigNode;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;

public class ConfigRoot
implements IConfigNode {
    Config config;
    List<IConfigNode> children;

    public ConfigRoot(Config config) {
        this.config = config;
    }

    @Override
    public List<IConfigNode> getChildren() {
        if (this.children == null) {
            this.children = new ObjectArrayList();
            for (ConfigSection section : this.config.getChildren()) {
                this.children.add(new ConfigNode(section));
            }
        }
        return this.children;
    }

    @Override
    public IValueNode asValue() {
        return null;
    }

    @Override
    public IArrayNode asArray() {
        return null;
    }

    @Override
    public ICompoundNode asCompound() {
        return null;
    }

    @Override
    public List<ConfigEntry.Suggestion> getValidValues() {
        return null;
    }

    @Override
    public List<DataType> getDataType() {
        return null;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return false;
    }

    @Override
    public void save() {
    }

    @Override
    public void setPrevious() {
    }

    @Override
    public void setDefault() {
    }

    @Override
    public boolean requiresRestart() {
        return false;
    }

    @Override
    public boolean requiresReload() {
        return false;
    }

    @Override
    public Component getName() {
        return IConfigNode.createLabel(this.config.getName());
    }

    @Override
    public Component getTooltip() {
        return null;
    }
}

