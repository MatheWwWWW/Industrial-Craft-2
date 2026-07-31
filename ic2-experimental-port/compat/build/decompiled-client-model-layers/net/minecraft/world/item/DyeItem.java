/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DyeItem
extends Item {
    private static final Map<DyeColor, DyeItem> f_41076_ = Maps.newEnumMap(DyeColor.class);
    private final DyeColor f_41077_;

    public DyeItem(DyeColor p_41080_, Item.Properties p_41081_) {
        super(p_41081_);
        this.f_41077_ = p_41080_;
        f_41076_.put(p_41080_, this);
    }

    @Override
    public InteractionResult m_6880_(ItemStack p_41085_, Player p_41086_, LivingEntity p_41087_, InteractionHand p_41088_) {
        Sheep $$4;
        if (p_41087_ instanceof Sheep && ($$4 = (Sheep)p_41087_).m_6084_() && !$$4.m_29875_() && $$4.m_29874_() != this.f_41077_) {
            $$4.f_19853_.m_6269_(p_41086_, $$4, SoundEvents.f_144133_, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!p_41086_.f_19853_.f_46443_) {
                $$4.m_29855_(this.f_41077_);
                p_41085_.m_41774_(1);
            }
            return InteractionResult.m_19078_(p_41086_.f_19853_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public DyeColor m_41089_() {
        return this.f_41077_;
    }

    public static DyeItem m_41082_(DyeColor p_41083_) {
        return f_41076_.get(p_41083_);
    }
}

