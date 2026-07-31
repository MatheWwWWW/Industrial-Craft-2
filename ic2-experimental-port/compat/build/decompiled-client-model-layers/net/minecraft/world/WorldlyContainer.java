/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface WorldlyContainer
extends Container {
    public int[] m_7071_(Direction var1);

    public boolean m_7155_(int var1, ItemStack var2, @Nullable Direction var3);

    public boolean m_7157_(int var1, ItemStack var2, Direction var3);
}

