/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.damagesource;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CombatTracker {
    public static final int f_146694_ = 100;
    public static final int f_146695_ = 300;
    private final List<CombatEntry> f_19276_ = Lists.newArrayList();
    private final LivingEntity f_19277_;
    private int f_19278_;
    private int f_19279_;
    private int f_19280_;
    private boolean f_19281_;
    private boolean f_19282_;
    @Nullable
    private String f_19283_;

    public CombatTracker(LivingEntity p_19285_) {
        this.f_19277_ = p_19285_;
    }

    public void m_19286_() {
        this.m_19299_();
        Optional<BlockPos> $$0 = this.f_19277_.m_21227_();
        if ($$0.isPresent()) {
            BlockState $$1 = this.f_19277_.f_19853_.m_8055_($$0.get());
            this.f_19283_ = $$1.m_60713_(Blocks.f_50155_) || $$1.m_204336_(BlockTags.f_13036_) ? "ladder" : ($$1.m_60713_(Blocks.f_50191_) ? "vines" : ($$1.m_60713_(Blocks.f_50702_) || $$1.m_60713_(Blocks.f_50703_) ? "weeping_vines" : ($$1.m_60713_(Blocks.f_50704_) || $$1.m_60713_(Blocks.f_50653_) ? "twisting_vines" : ($$1.m_60713_(Blocks.f_50616_) ? "scaffolding" : "other_climbable"))));
        } else if (this.f_19277_.m_20069_()) {
            this.f_19283_ = "water";
        }
    }

    public void m_19289_(DamageSource p_19290_, float p_19291_, float p_19292_) {
        this.m_19296_();
        this.m_19286_();
        CombatEntry $$3 = new CombatEntry(p_19290_, this.f_19277_.f_19797_, p_19291_, p_19292_, this.f_19283_, this.f_19277_.f_19789_);
        this.f_19276_.add($$3);
        this.f_19278_ = this.f_19277_.f_19797_;
        this.f_19282_ = true;
        if ($$3.m_19265_() && !this.f_19281_ && this.f_19277_.m_6084_()) {
            this.f_19281_ = true;
            this.f_19280_ = this.f_19279_ = this.f_19277_.f_19797_;
            this.f_19277_.m_8108_();
        }
    }

    public Component m_19293_() {
        Component $$14;
        if (this.f_19276_.isEmpty()) {
            return Component.m_237110_("death.attack.generic", this.f_19277_.m_5446_());
        }
        CombatEntry $$0 = this.m_19298_();
        CombatEntry $$1 = this.f_19276_.get(this.f_19276_.size() - 1);
        Component $$2 = $$1.m_19267_();
        Entity $$3 = $$1.m_19263_().m_7639_();
        if ($$0 != null && $$1.m_19263_() == DamageSource.f_19315_) {
            Component $$4 = $$0.m_19267_();
            if ($$0.m_19263_() == DamageSource.f_19315_ || $$0.m_19263_() == DamageSource.f_19317_) {
                MutableComponent $$5 = Component.m_237110_("death.fell.accident." + this.m_19287_($$0), this.f_19277_.m_5446_());
            } else if ($$4 != null && !$$4.equals($$2)) {
                ItemStack $$7;
                Entity $$6 = $$0.m_19263_().m_7639_();
                ItemStack itemStack = $$7 = $$6 instanceof LivingEntity ? ((LivingEntity)$$6).m_21205_() : ItemStack.f_41583_;
                if (!$$7.m_41619_() && $$7.m_41788_()) {
                    MutableComponent $$8 = Component.m_237110_("death.fell.assist.item", this.f_19277_.m_5446_(), $$4, $$7.m_41611_());
                } else {
                    MutableComponent $$9 = Component.m_237110_("death.fell.assist", this.f_19277_.m_5446_(), $$4);
                }
            } else if ($$2 != null) {
                ItemStack $$10;
                ItemStack itemStack = $$10 = $$3 instanceof LivingEntity ? ((LivingEntity)$$3).m_21205_() : ItemStack.f_41583_;
                if (!$$10.m_41619_() && $$10.m_41788_()) {
                    MutableComponent $$11 = Component.m_237110_("death.fell.finish.item", this.f_19277_.m_5446_(), $$2, $$10.m_41611_());
                } else {
                    MutableComponent $$12 = Component.m_237110_("death.fell.finish", this.f_19277_.m_5446_(), $$2);
                }
            } else {
                MutableComponent $$13 = Component.m_237110_("death.fell.killer", this.f_19277_.m_5446_());
            }
        } else {
            $$14 = $$1.m_19263_().m_6157_(this.f_19277_);
        }
        return $$14;
    }

    @Nullable
    public LivingEntity m_19294_() {
        LivingEntity $$0 = null;
        Player $$1 = null;
        float $$2 = 0.0f;
        float $$3 = 0.0f;
        for (CombatEntry $$4 : this.f_19276_) {
            if ($$4.m_19263_().m_7639_() instanceof Player && ($$1 == null || $$4.m_19264_() > $$3)) {
                $$3 = $$4.m_19264_();
                $$1 = (Player)$$4.m_19263_().m_7639_();
            }
            if (!($$4.m_19263_().m_7639_() instanceof LivingEntity) || $$0 != null && !($$4.m_19264_() > $$2)) continue;
            $$2 = $$4.m_19264_();
            $$0 = (LivingEntity)$$4.m_19263_().m_7639_();
        }
        if ($$1 != null && $$3 >= $$2 / 3.0f) {
            return $$1;
        }
        return $$0;
    }

    @Nullable
    private CombatEntry m_19298_() {
        CombatEntry $$0 = null;
        CombatEntry $$1 = null;
        float $$2 = 0.0f;
        float $$3 = 0.0f;
        for (int $$4 = 0; $$4 < this.f_19276_.size(); ++$$4) {
            CombatEntry $$6;
            CombatEntry $$5 = this.f_19276_.get($$4);
            CombatEntry combatEntry = $$6 = $$4 > 0 ? this.f_19276_.get($$4 - 1) : null;
            if (($$5.m_19263_() == DamageSource.f_19315_ || $$5.m_19263_() == DamageSource.f_19317_) && $$5.m_19268_() > 0.0f && ($$0 == null || $$5.m_19268_() > $$3)) {
                $$0 = $$4 > 0 ? $$6 : $$5;
                $$3 = $$5.m_19268_();
            }
            if ($$5.m_19266_() == null || $$1 != null && !($$5.m_19264_() > $$2)) continue;
            $$1 = $$5;
            $$2 = $$5.m_19264_();
        }
        if ($$3 > 5.0f && $$0 != null) {
            return $$0;
        }
        if ($$2 > 5.0f && $$1 != null) {
            return $$1;
        }
        return null;
    }

    private String m_19287_(CombatEntry p_19288_) {
        return p_19288_.m_19266_() == null ? "generic" : p_19288_.m_19266_();
    }

    public boolean m_146696_() {
        this.m_19296_();
        return this.f_19282_;
    }

    public boolean m_146697_() {
        this.m_19296_();
        return this.f_19281_;
    }

    public int m_19295_() {
        if (this.f_19281_) {
            return this.f_19277_.f_19797_ - this.f_19279_;
        }
        return this.f_19280_ - this.f_19279_;
    }

    private void m_19299_() {
        this.f_19283_ = null;
    }

    public void m_19296_() {
        int $$0;
        int n = $$0 = this.f_19281_ ? 300 : 100;
        if (this.f_19282_ && (!this.f_19277_.m_6084_() || this.f_19277_.f_19797_ - this.f_19278_ > $$0)) {
            boolean $$1 = this.f_19281_;
            this.f_19282_ = false;
            this.f_19281_ = false;
            this.f_19280_ = this.f_19277_.f_19797_;
            if ($$1) {
                this.f_19277_.m_8098_();
            }
            this.f_19276_.clear();
        }
    }

    public LivingEntity m_19297_() {
        return this.f_19277_;
    }

    @Nullable
    public CombatEntry m_146698_() {
        if (this.f_19276_.isEmpty()) {
            return null;
        }
        return this.f_19276_.get(this.f_19276_.size() - 1);
    }

    public int m_146699_() {
        LivingEntity $$0 = this.m_19294_();
        return $$0 == null ? -1 : $$0.m_19879_();
    }
}

