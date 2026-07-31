/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.recipes.villager;

import ic2.core.platform.recipes.villager.ITradeComp;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemTradeComp
implements ITradeComp {
    ItemStack stack;
    int min;
    int max;
    ITradeComp.Target target;

    public ItemTradeComp(ItemLike item, int count, ITradeComp.Target target) {
        this(item, count, count, target);
    }

    public ItemTradeComp(ItemLike item, int min, int max, ITradeComp.Target target) {
        this(new ItemStack(item), min, max, target);
    }

    public ItemTradeComp(ItemStack stack, int count, ITradeComp.Target target) {
        this(stack, count, count, target);
    }

    public ItemTradeComp(ItemStack stack, int min, int max, ITradeComp.Target target) {
        this.stack = stack;
        this.min = min;
        this.max = max;
        this.target = target;
    }

    @Override
    public ItemStack getItem(RandomSource rand) {
        return StackUtil.copyWithSize(this.stack, this.min == this.max ? this.min : this.min + rand.m_188503_(this.max - this.min + 1));
    }

    @Override
    public ITradeComp.Target getTarget() {
        return this.target;
    }
}

