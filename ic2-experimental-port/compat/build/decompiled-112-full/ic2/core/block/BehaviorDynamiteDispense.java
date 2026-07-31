/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package ic2.core.block;

import ic2.core.block.EntityDynamite;
import ic2.core.block.EntityStickyDynamite;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BehaviorDynamiteDispense
extends BehaviorProjectileDispense {
    private final boolean sticky;

    public BehaviorDynamiteDispense(boolean sticky) {
        this.sticky = sticky;
    }

    protected IProjectile func_82499_a(World world, IPosition pos, ItemStack stack) {
        if (this.sticky) {
            return new EntityStickyDynamite(world, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
        }
        return new EntityDynamite(world, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
    }
}

