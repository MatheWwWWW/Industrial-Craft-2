/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IValueNode;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface IConfigNode {
    public List<IConfigNode> getChildren();

    public IValueNode asValue();

    public IArrayNode asArray();

    public ICompoundNode asCompound();

    public List<DataType> getDataType();

    public List<ConfigEntry.Suggestion> getValidValues();

    public boolean isArray();

    public boolean isLeaf();

    public boolean isRoot();

    public boolean isChanged();

    public void setPrevious();

    public void setDefault();

    public void save();

    public boolean requiresRestart();

    public boolean requiresReload();

    public Component getName();

    public Component getTooltip();

    public static MutableComponent createLabel(String name) {
        MutableComponent comp = Component.m_237119_();
        for (String s : name.split("(?=\\p{Lu})|\\_|\\-")) {
            String first = Character.toString(s.charAt(0));
            comp.m_130946_(s.replaceFirst(first, first.toUpperCase())).m_130946_(" ");
        }
        return comp;
    }
}

