/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.gui.api.ICompoundNode;
import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.gui.api.IValueNode;

public interface IArrayNode
extends INode {
    public int size();

    public INode get(int var1);

    default public IValueNode asValue(int index) {
        INode node = this.get(index);
        return node instanceof IValueNode ? (IValueNode)node : null;
    }

    default public ICompoundNode asCompound(int index) {
        INode node = this.get(index);
        return node instanceof ICompoundNode ? (ICompoundNode)node : null;
    }

    public void createNode();

    public void removeNode(int var1);

    public int indexOf(INode var1);
}

