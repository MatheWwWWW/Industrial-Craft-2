/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.recipes.villager;

import ic2.api.crops.ICrop;
import ic2.core.item.misc.CropSeedItem;
import ic2.core.platform.recipes.villager.ITradeComp;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class CropTradeComp
implements ITradeComp {
    ICrop crop;
    int minStats;
    int maxStats;
    boolean scanned;
    ITradeComp.Target target;

    public CropTradeComp(ICrop crop, int stats, boolean scanned, ITradeComp.Target target) {
        this(crop, stats, stats, scanned, target);
    }

    public CropTradeComp(ICrop crop, int minStats, int maxStats, boolean scanned, ITradeComp.Target target) {
        this.crop = crop;
        this.minStats = minStats;
        this.maxStats = maxStats;
        this.scanned = scanned;
        this.target = target;
    }

    private int getStat(RandomSource rand) {
        if (this.minStats == this.maxStats) {
            return this.minStats;
        }
        return this.minStats + rand.m_188503_(this.maxStats - this.minStats + 1);
    }

    @Override
    public ItemStack getItem(RandomSource rand) {
        return CropSeedItem.createStack(this.crop, this.getStat(rand), this.getStat(rand), this.getStat(rand), this.scanned ? 4 : 0);
    }

    @Override
    public ITradeComp.Target getTarget() {
        return this.target;
    }
}

