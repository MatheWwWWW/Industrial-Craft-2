/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.utils.ParseResult;

public interface IValueNode
extends INode {
    public String get();

    public void set(String var1);

    public ParseResult<Boolean> isValid(String var1);

    public boolean isCompoundNode();
}

