/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 */
package ic2.core.ref;

import ic2.core.block.state.IIdProvider;
import ic2.core.ref.IMultiItem;
import net.minecraft.block.state.IBlockState;

public interface IMultiBlock<T extends IIdProvider>
extends IMultiItem<T> {
    public IBlockState getState(T var1);

    public IBlockState getState(String var1);
}

