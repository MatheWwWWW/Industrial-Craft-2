/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.VillagerTrades$ItemListing
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 */
package ic2.core.platform.recipes.villager;

import ic2.core.platform.recipes.villager.ITradeComp;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class SimpleTrade
implements VillagerTrades.ItemListing {
    ITradeComp[] comps = new ITradeComp[3];
    int maxUses;
    int villagerXP;
    float zombieCheese;

    public SimpleTrade(ITradeComp ... comps) {
        this(16, 5, 0.05f, comps);
    }

    public SimpleTrade(int maxUses, int villagerXP, float zombieCheese, ITradeComp ... comps) {
        this.maxUses = maxUses;
        this.villagerXP = villagerXP;
        this.zombieCheese = zombieCheese;
        ITradeComp[] iTradeCompArray = comps;
        int n = iTradeCompArray.length;
        for (int i = 0; i < n; ++i) {
            ITradeComp comp;
            this.comps[comp.getTarget().ordinal()] = comp = iTradeCompArray[i];
        }
    }

    public ItemStack build(ITradeComp.Target target, RandomSource rand) {
        return this.comps[target.ordinal()] == null ? ItemStack.f_41583_ : this.comps[target.ordinal()].getItem(rand);
    }

    public MerchantOffer m_213663_(Entity entity, RandomSource random) {
        return new MerchantOffer(this.build(ITradeComp.Target.MAIN, random), this.build(ITradeComp.Target.SUB, random), this.build(ITradeComp.Target.OUT, random), this.maxUses, this.villagerXP, this.zombieCheese);
    }
}

