/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IArrayNode;
import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.IConfigNode;
import ic2.core.utils.config.gui.api.IValueNode;
import java.util.List;
import net.minecraft.network.chat.Component;

public interface IConfigFolderNode
extends IConfigNode {
    @Override
    default public List<ConfigEntry.Suggestion> getValidValues() {
        return null;
    }

    @Override
    default public IValueNode asValue() {
        return null;
    }

    @Override
    default public IArrayNode asArray() {
        return null;
    }

    @Override
    default public ICompoundNode asCompound() {
        return null;
    }

    @Override
    default public boolean isArray() {
        return false;
    }

    @Override
    default public List<DataType> getDataType() {
        return null;
    }

    @Override
    default public boolean isLeaf() {
        return false;
    }

    @Override
    default public boolean isRoot() {
        return false;
    }

    @Override
    default public boolean isChanged() {
        return false;
    }

    @Override
    default public void save() {
    }

    @Override
    default public void setPrevious() {
    }

    @Override
    default public void setDefault() {
    }

    @Override
    default public boolean requiresRestart() {
        return false;
    }

    @Override
    default public boolean requiresReload() {
        return false;
    }

    @Override
    default public Component getTooltip() {
        return Component.m_237119_();
    }
}

