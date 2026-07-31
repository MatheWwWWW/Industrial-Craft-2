/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.world.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

public class PlayerHeadItem
extends StandingAndWallBlockItem {
    public static final String f_151174_ = "SkullOwner";

    public PlayerHeadItem(Block p_42971_, Block p_42972_, Item.Properties p_42973_) {
        super(p_42971_, p_42972_, p_42973_);
    }

    @Override
    public Component m_7626_(ItemStack p_42977_) {
        if (p_42977_.m_150930_(Items.f_42680_) && p_42977_.m_41782_()) {
            CompoundTag $$3;
            String $$1 = null;
            CompoundTag $$2 = p_42977_.m_41783_();
            if ($$2.m_128425_(f_151174_, 8)) {
                $$1 = $$2.m_128461_(f_151174_);
            } else if ($$2.m_128425_(f_151174_, 10) && ($$3 = $$2.m_128469_(f_151174_)).m_128425_("Name", 8)) {
                $$1 = $$3.m_128461_("Name");
            }
            if ($$1 != null) {
                return Component.m_237110_(this.m_5524_() + ".named", $$1);
            }
        }
        return super.m_7626_(p_42977_);
    }

    @Override
    public void m_142312_(CompoundTag p_151179_) {
        super.m_142312_(p_151179_);
        if (p_151179_.m_128425_(f_151174_, 8) && !StringUtils.isBlank((CharSequence)p_151179_.m_128461_(f_151174_))) {
            GameProfile $$1 = new GameProfile(null, p_151179_.m_128461_(f_151174_));
            SkullBlockEntity.m_155738_($$1, p_151177_ -> p_151179_.m_128365_(f_151174_, NbtUtils.m_129230_(new CompoundTag(), p_151177_)));
        }
    }
}

