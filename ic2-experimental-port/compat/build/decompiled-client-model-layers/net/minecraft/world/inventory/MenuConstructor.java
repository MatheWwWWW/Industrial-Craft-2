/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface MenuConstructor {
    @Nullable
    public AbstractContainerMenu m_7208_(int var1, Inventory var2, Player var3);
}

