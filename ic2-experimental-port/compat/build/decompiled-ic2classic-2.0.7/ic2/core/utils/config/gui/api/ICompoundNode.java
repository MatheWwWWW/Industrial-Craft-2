/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.api;

import ic2.core.utils.config.gui.api.INode;
import ic2.core.utils.config.gui.api.IValueNode;
import ic2.core.utils.config.utils.ParseResult;
import java.util.List;
import net.minecraft.network.chat.Component;

public interface ICompoundNode
extends INode {
    public List<IValueNode> getValues();

    public Component getName(int var1);

    public boolean isValid();

    public String get();

    public ParseResult<Boolean> isValid(String var1);

    public void set(String var1);
}

