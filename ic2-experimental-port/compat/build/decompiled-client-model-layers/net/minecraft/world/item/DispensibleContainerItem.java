/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public interface DispensibleContainerItem {
    default public void m_142131_(@Nullable Player p_150817_, Level p_150818_, ItemStack p_150819_, BlockPos p_150820_) {
    }

    public boolean m_142073_(@Nullable Player var1, Level var2, BlockPos var3, @Nullable BlockHitResult var4);
}

