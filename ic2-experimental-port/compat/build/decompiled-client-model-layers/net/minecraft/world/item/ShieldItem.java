/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ShieldItem
extends Item {
    public static final int f_151182_ = 5;
    public static final float f_151183_ = 3.0f;
    public static final String f_151184_ = "Base";

    public ShieldItem(Item.Properties p_43089_) {
        super(p_43089_);
        DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
    }

    @Override
    public String m_5671_(ItemStack p_43109_) {
        if (BlockItem.m_186336_(p_43109_) != null) {
            return this.m_5524_() + "." + ShieldItem.m_43102_(p_43109_).m_41065_();
        }
        return super.m_5671_(p_43109_);
    }

    @Override
    public void m_7373_(ItemStack p_43094_, @Nullable Level p_43095_, List<Component> p_43096_, TooltipFlag p_43097_) {
        BannerItem.m_40542_(p_43094_, p_43096_);
    }

    @Override
    public UseAnim m_6164_(ItemStack p_43105_) {
        return UseAnim.BLOCK;
    }

    @Override
    public int m_8105_(ItemStack p_43107_) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43099_, Player p_43100_, InteractionHand p_43101_) {
        ItemStack $$3 = p_43100_.m_21120_(p_43101_);
        p_43100_.m_6672_(p_43101_);
        return InteractionResultHolder.m_19096_($$3);
    }

    @Override
    public boolean m_6832_(ItemStack p_43091_, ItemStack p_43092_) {
        return p_43092_.m_204117_(ItemTags.f_13168_) || super.m_6832_(p_43091_, p_43092_);
    }

    public static DyeColor m_43102_(ItemStack p_43103_) {
        CompoundTag $$1 = BlockItem.m_186336_(p_43103_);
        return $$1 != null ? DyeColor.m_41053_($$1.m_128451_(f_151184_)) : DyeColor.WHITE;
    }
}

