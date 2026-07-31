/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.recipes.villager;

import ic2.core.platform.recipes.villager.ITradeComp;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ElemeraldTradeComp
implements ITradeComp {
    int min;
    int max;
    ITradeComp.Target target;

    public ElemeraldTradeComp(int count, ITradeComp.Target target) {
        this(count, count, target);
    }

    public ElemeraldTradeComp(int min, int max, ITradeComp.Target target) {
        this.min = min;
        this.max = max;
        this.target = target;
    }

    @Override
    public ItemStack getItem(RandomSource rand) {
        return new ItemStack((ItemLike)Items.f_42616_, this.min == this.max ? this.min : this.min + rand.m_188503_(this.max - this.min + 1));
    }

    @Override
    public ITradeComp.Target getTarget() {
        return this.target;
    }
}

