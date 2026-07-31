/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;

public class BannerPatternItem
extends Item {
    private final TagKey<BannerPattern> f_40546_;

    public BannerPatternItem(TagKey<BannerPattern> p_220008_, Item.Properties p_220009_) {
        super(p_220009_);
        this.f_40546_ = p_220008_;
    }

    public TagKey<BannerPattern> m_220010_() {
        return this.f_40546_;
    }

    @Override
    public void m_7373_(ItemStack p_40551_, @Nullable Level p_40552_, List<Component> p_40553_, TooltipFlag p_40554_) {
        p_40553_.add(this.m_40556_().m_130940_(ChatFormatting.GRAY));
    }

    public MutableComponent m_40556_() {
        return Component.m_237115_(this.m_5524_() + ".desc");
    }
}

