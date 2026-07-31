/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.crops;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropSeed;
import net.minecraft.world.item.ItemStack;

public class BaseSeed {
    public final ICrop crop;
    public int stage;
    public int growth;
    public int gain;
    public int resistance;
    public int stack_size;

    public BaseSeed(ICrop crop, int stage, int growth, int gain, int resistance, int stack_size) {
        this.crop = crop;
        this.stage = stage;
        this.growth = growth;
        this.gain = gain;
        this.resistance = resistance;
        this.stack_size = stack_size;
    }

    public BaseSeed(ItemStack stack) {
        ICropSeed seed = (ICropSeed)stack.m_41720_();
        this.crop = seed.getCrop(stack);
        this.stage = 1;
        this.growth = seed.getGrowth(stack);
        this.gain = seed.getGain(stack);
        this.resistance = seed.getResistance(stack);
        this.stack_size = 1;
    }
}

