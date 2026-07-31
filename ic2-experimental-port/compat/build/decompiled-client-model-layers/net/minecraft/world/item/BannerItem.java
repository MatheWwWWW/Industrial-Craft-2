/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.apache.commons.lang3.Validate;

public class BannerItem
extends StandingAndWallBlockItem {
    private static final String f_150695_ = "block.minecraft.banner.";

    public BannerItem(Block p_40534_, Block p_40535_, Item.Properties p_40536_) {
        super(p_40534_, p_40535_, p_40536_);
        Validate.isInstanceOf(AbstractBannerBlock.class, (Object)p_40534_);
        Validate.isInstanceOf(AbstractBannerBlock.class, (Object)p_40535_);
    }

    public static void m_40542_(ItemStack p_40543_, List<Component> p_40544_) {
        CompoundTag $$2 = BlockItem.m_186336_(p_40543_);
        if ($$2 == null || !$$2.m_128441_("Patterns")) {
            return;
        }
        ListTag $$3 = $$2.m_128437_("Patterns", 10);
        for (int $$4 = 0; $$4 < $$3.size() && $$4 < 6; ++$$4) {
            CompoundTag $$5 = $$3.m_128728_($$4);
            DyeColor $$6 = DyeColor.m_41053_($$5.m_128451_("Color"));
            Holder<BannerPattern> $$7 = BannerPattern.m_222700_($$5.m_128461_("Pattern"));
            if ($$7 == null) continue;
            $$7.m_203543_().map(p_220002_ -> p_220002_.m_135782_().m_214299_()).ifPresent(p_220006_ -> p_40544_.add(Component.m_237115_(f_150695_ + p_220006_ + "." + $$6.m_41065_()).m_130940_(ChatFormatting.GRAY)));
        }
    }

    public DyeColor m_40545_() {
        return ((AbstractBannerBlock)this.m_40614_()).m_48674_();
    }

    @Override
    public void m_7373_(ItemStack p_40538_, @Nullable Level p_40539_, List<Component> p_40540_, TooltipFlag p_40541_) {
        BannerItem.m_40542_(p_40538_, p_40540_);
    }
}

