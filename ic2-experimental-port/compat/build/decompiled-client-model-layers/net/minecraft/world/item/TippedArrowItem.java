/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class TippedArrowItem
extends ArrowItem {
    public TippedArrowItem(Item.Properties p_43354_) {
        super(p_43354_);
    }

    @Override
    public ItemStack m_7968_() {
        return PotionUtils.m_43549_(super.m_7968_(), Potions.f_43584_);
    }

    @Override
    public void m_6787_(CreativeModeTab p_43356_, NonNullList<ItemStack> p_43357_) {
        if (this.m_220152_(p_43356_)) {
            for (Potion $$2 : Registry.f_122828_) {
                if ($$2.m_43488_().isEmpty()) continue;
                p_43357_.add(PotionUtils.m_43549_(new ItemStack(this), $$2));
            }
        }
    }

    @Override
    public void m_7373_(ItemStack p_43359_, @Nullable Level p_43360_, List<Component> p_43361_, TooltipFlag p_43362_) {
        PotionUtils.m_43555_(p_43359_, p_43361_, 0.125f);
    }

    @Override
    public String m_5671_(ItemStack p_43364_) {
        return PotionUtils.m_43579_(p_43364_).m_43492_(this.m_5524_() + ".effect.");
    }
}

