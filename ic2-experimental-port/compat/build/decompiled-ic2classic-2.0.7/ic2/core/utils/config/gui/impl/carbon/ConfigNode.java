/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.impl.carbon;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigSection;
import ic2.core.utils.config.gui.api.IConfigFolderNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.impl.carbon.ConfigCompoundLeaf;
import ic2.core.utils.config.gui.impl.carbon.ConfigLeaf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;

public class ConfigNode
implements IConfigFolderNode {
    ConfigSection section;
    List<IConfigNode> children;

    public ConfigNode(ConfigSection section) {
        this.section = section;
    }

    @Override
    public List<IConfigNode> getChildren() {
        if (this.children == null) {
            this.children = new ObjectArrayList();
            for (ConfigSection configSection : this.section.getChildren()) {
                this.children.add(new ConfigNode(configSection));
            }
            for (ConfigEntry configEntry : this.section.getEntries()) {
                if (configEntry.getDataType().isCompound()) {
                    this.children.add(new ConfigCompoundLeaf(configEntry));
                    continue;
                }
                this.children.add(new ConfigLeaf(configEntry));
            }
        }
        return this.children;
    }

    @Override
    public Component getName() {
        return IConfigNode.createLabel(this.section.getName());
    }
}

