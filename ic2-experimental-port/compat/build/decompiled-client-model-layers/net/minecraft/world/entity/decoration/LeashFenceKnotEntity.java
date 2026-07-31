/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LeashFenceKnotEntity
extends HangingEntity {
    public static final double f_149638_ = 0.375;

    public LeashFenceKnotEntity(EntityType<? extends LeashFenceKnotEntity> p_31828_, Level p_31829_) {
        super((EntityType<? extends HangingEntity>)p_31828_, p_31829_);
    }

    public LeashFenceKnotEntity(Level p_31831_, BlockPos p_31832_) {
        super(EntityType.f_20464_, p_31831_, p_31832_);
        this.m_6034_(p_31832_.m_123341_(), p_31832_.m_123342_(), p_31832_.m_123343_());
    }

    @Override
    protected void m_7087_() {
        this.m_20343_((double)this.f_31698_.m_123341_() + 0.5, (double)this.f_31698_.m_123342_() + 0.375, (double)this.f_31698_.m_123343_() + 0.5);
        double $$0 = (double)this.m_6095_().m_20678_() / 2.0;
        double $$1 = this.m_6095_().m_20679_();
        this.m_20011_(new AABB(this.m_20185_() - $$0, this.m_20186_(), this.m_20189_() - $$0, this.m_20185_() + $$0, this.m_20186_() + $$1, this.m_20189_() + $$0));
    }

    @Override
    public void m_6022_(Direction p_31848_) {
    }

    @Override
    public int m_7076_() {
        return 9;
    }

    @Override
    public int m_7068_() {
        return 9;
    }

    @Override
    protected float m_6380_(Pose p_31839_, EntityDimensions p_31840_) {
        return 0.0625f;
    }

    @Override
    public boolean m_6783_(double p_31835_) {
        return p_31835_ < 1024.0;
    }

    @Override
    public void m_5553_(@Nullable Entity p_31837_) {
        this.m_5496_(SoundEvents.f_12033_, 1.0f, 1.0f);
    }

    @Override
    public void m_7380_(CompoundTag p_31852_) {
    }

    @Override
    public void m_7378_(CompoundTag p_31850_) {
    }

    @Override
    public InteractionResult m_6096_(Player p_31842_, InteractionHand p_31843_) {
        if (this.f_19853_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        boolean $$2 = false;
        double $$3 = 7.0;
        List<Mob> $$4 = this.f_19853_.m_45976_(Mob.class, new AABB(this.m_20185_() - 7.0, this.m_20186_() - 7.0, this.m_20189_() - 7.0, this.m_20185_() + 7.0, this.m_20186_() + 7.0, this.m_20189_() + 7.0));
        for (Mob $$5 : $$4) {
            if ($$5.m_21524_() != p_31842_) continue;
            $$5.m_21463_(this, true);
            $$2 = true;
        }
        if (!$$2) {
            this.m_146870_();
            if (p_31842_.m_150110_().f_35937_) {
                for (Mob $$6 : $$4) {
                    if (!$$6.m_21523_() || $$6.m_21524_() != this) continue;
                    $$6.m_21455_(true, false);
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean m_7088_() {
        return this.f_19853_.m_8055_(this.f_31698_).m_204336_(BlockTags.f_13039_);
    }

    public static LeashFenceKnotEntity m_31844_(Level p_31845_, BlockPos p_31846_) {
        int $$2 = p_31846_.m_123341_();
        int $$3 = p_31846_.m_123342_();
        int $$4 = p_31846_.m_123343_();
        List<LeashFenceKnotEntity> $$5 = p_31845_.m_45976_(LeashFenceKnotEntity.class, new AABB((double)$$2 - 1.0, (double)$$3 - 1.0, (double)$$4 - 1.0, (double)$$2 + 1.0, (double)$$3 + 1.0, (double)$$4 + 1.0));
        for (LeashFenceKnotEntity $$6 : $$5) {
            if (!$$6.m_31748_().equals(p_31846_)) continue;
            return $$6;
        }
        LeashFenceKnotEntity $$7 = new LeashFenceKnotEntity(p_31845_, p_31846_);
        p_31845_.m_7967_($$7);
        return $$7;
    }

    @Override
    public void m_7084_() {
        this.m_5496_(SoundEvents.f_12087_, 1.0f, 1.0f);
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this, 0, this.m_31748_());
    }

    @Override
    public Vec3 m_7398_(float p_31863_) {
        return this.m_20318_(p_31863_).m_82520_(0.0, 0.2, 0.0);
    }

    @Override
    public ItemStack m_142340_() {
        return new ItemStack(Items.f_42655_);
    }
}

