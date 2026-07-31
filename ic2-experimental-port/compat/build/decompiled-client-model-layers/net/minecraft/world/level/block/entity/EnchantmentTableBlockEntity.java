/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantmentTableBlockEntity
extends BlockEntity
implements Nameable {
    public int f_59251_;
    public float f_59252_;
    public float f_59253_;
    public float f_59254_;
    public float f_59255_;
    public float f_59256_;
    public float f_59257_;
    public float f_59258_;
    public float f_59259_;
    public float f_59260_;
    private static final RandomSource f_59261_ = RandomSource.m_216327_();
    private Component f_59262_;

    public EnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_) {
        super(BlockEntityType.f_58928_, p_155501_, p_155502_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187500_) {
        super.m_183515_(p_187500_);
        if (this.m_8077_()) {
            p_187500_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_59262_));
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155509_) {
        super.m_142466_(p_155509_);
        if (p_155509_.m_128425_("CustomName", 8)) {
            this.f_59262_ = Component.Serializer.m_130701_(p_155509_.m_128461_("CustomName"));
        }
    }

    public static void m_155503_(Level p_155504_, BlockPos p_155505_, BlockState p_155506_, EnchantmentTableBlockEntity p_155507_) {
        float $$8;
        p_155507_.f_59257_ = p_155507_.f_59256_;
        p_155507_.f_59259_ = p_155507_.f_59258_;
        Player $$4 = p_155504_.m_45924_((double)p_155505_.m_123341_() + 0.5, (double)p_155505_.m_123342_() + 0.5, (double)p_155505_.m_123343_() + 0.5, 3.0, false);
        if ($$4 != null) {
            double $$5 = $$4.m_20185_() - ((double)p_155505_.m_123341_() + 0.5);
            double $$6 = $$4.m_20189_() - ((double)p_155505_.m_123343_() + 0.5);
            p_155507_.f_59260_ = (float)Mth.m_14136_($$6, $$5);
            p_155507_.f_59256_ += 0.1f;
            if (p_155507_.f_59256_ < 0.5f || f_59261_.m_188503_(40) == 0) {
                float $$7 = p_155507_.f_59254_;
                do {
                    p_155507_.f_59254_ += (float)(f_59261_.m_188503_(4) - f_59261_.m_188503_(4));
                } while ($$7 == p_155507_.f_59254_);
            }
        } else {
            p_155507_.f_59260_ += 0.02f;
            p_155507_.f_59256_ -= 0.1f;
        }
        while (p_155507_.f_59258_ >= (float)Math.PI) {
            p_155507_.f_59258_ -= (float)Math.PI * 2;
        }
        while (p_155507_.f_59258_ < (float)(-Math.PI)) {
            p_155507_.f_59258_ += (float)Math.PI * 2;
        }
        while (p_155507_.f_59260_ >= (float)Math.PI) {
            p_155507_.f_59260_ -= (float)Math.PI * 2;
        }
        while (p_155507_.f_59260_ < (float)(-Math.PI)) {
            p_155507_.f_59260_ += (float)Math.PI * 2;
        }
        for ($$8 = p_155507_.f_59260_ - p_155507_.f_59258_; $$8 >= (float)Math.PI; $$8 -= (float)Math.PI * 2) {
        }
        while ($$8 < (float)(-Math.PI)) {
            $$8 += (float)Math.PI * 2;
        }
        p_155507_.f_59258_ += $$8 * 0.4f;
        p_155507_.f_59256_ = Mth.m_14036_(p_155507_.f_59256_, 0.0f, 1.0f);
        ++p_155507_.f_59251_;
        p_155507_.f_59253_ = p_155507_.f_59252_;
        float $$9 = (p_155507_.f_59254_ - p_155507_.f_59252_) * 0.4f;
        float $$10 = 0.2f;
        $$9 = Mth.m_14036_($$9, -0.2f, 0.2f);
        p_155507_.f_59255_ += ($$9 - p_155507_.f_59255_) * 0.9f;
        p_155507_.f_59252_ += p_155507_.f_59255_;
    }

    @Override
    public Component m_7755_() {
        if (this.f_59262_ != null) {
            return this.f_59262_;
        }
        return Component.m_237115_("container.enchant");
    }

    public void m_59272_(@Nullable Component p_59273_) {
        this.f_59262_ = p_59273_;
    }

    @Override
    @Nullable
    public Component m_7770_() {
        return this.f_59262_;
    }
}

