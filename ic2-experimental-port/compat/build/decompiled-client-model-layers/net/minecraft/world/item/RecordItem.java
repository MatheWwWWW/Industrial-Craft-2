/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RecordItem
extends Item {
    private static final Map<SoundEvent, RecordItem> f_43032_ = Maps.newHashMap();
    private final int f_43033_;
    private final SoundEvent f_43034_;
    private final int f_238749_;

    protected RecordItem(int p_239614_, SoundEvent p_239615_, Item.Properties p_239616_, int p_239617_) {
        super(p_239616_);
        this.f_43033_ = p_239614_;
        this.f_43034_ = p_239615_;
        this.f_238749_ = p_239617_ * 20;
        f_43032_.put(this.f_43034_, this);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_43048_) {
        BlockPos $$2;
        Level $$1 = p_43048_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_43048_.m_8083_());
        if (!$$3.m_60713_(Blocks.f_50131_) || $$3.m_61143_(JukeboxBlock.f_54254_).booleanValue()) {
            return InteractionResult.PASS;
        }
        ItemStack $$4 = p_43048_.m_43722_();
        if (!$$1.f_46443_) {
            ((JukeboxBlock)Blocks.f_50131_).m_238345_(p_43048_.m_43723_(), $$1, $$2, $$3, $$4);
            $$1.m_5898_(null, 1010, $$2, Item.m_41393_(this));
            $$4.m_41774_(1);
            Player $$5 = p_43048_.m_43723_();
            if ($$5 != null) {
                $$5.m_36220_(Stats.f_12965_);
            }
        }
        return InteractionResult.m_19078_($$1.f_46443_);
    }

    public int m_43049_() {
        return this.f_43033_;
    }

    @Override
    public void m_7373_(ItemStack p_43043_, @Nullable Level p_43044_, List<Component> p_43045_, TooltipFlag p_43046_) {
        p_43045_.add(this.m_43050_().m_130940_(ChatFormatting.GRAY));
    }

    public MutableComponent m_43050_() {
        return Component.m_237115_(this.m_5524_() + ".desc");
    }

    @Nullable
    public static RecordItem m_43040_(SoundEvent p_43041_) {
        return f_43032_.get(p_43041_);
    }

    public SoundEvent m_43051_() {
        return this.f_43034_;
    }

    public int m_43036_() {
        return this.f_238749_;
    }
}

