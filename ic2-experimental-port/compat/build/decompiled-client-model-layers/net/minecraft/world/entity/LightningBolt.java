/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningBolt
extends Entity {
    private static final int f_147136_ = 2;
    private static final double f_147137_ = 3.0;
    private static final double f_147138_ = 15.0;
    private int f_20860_;
    public long f_20859_;
    private int f_20861_;
    private boolean f_20862_;
    @Nullable
    private ServerPlayer f_20863_;
    private final Set<Entity> f_147134_ = Sets.newHashSet();
    private int f_147135_;

    public LightningBolt(EntityType<? extends LightningBolt> p_20865_, Level p_20866_) {
        super(p_20865_, p_20866_);
        this.f_19811_ = true;
        this.f_20860_ = 2;
        this.f_20859_ = this.f_19796_.m_188505_();
        this.f_20861_ = this.f_19796_.m_188503_(3) + 1;
    }

    public void m_20874_(boolean p_20875_) {
        this.f_20862_ = p_20875_;
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.WEATHER;
    }

    @Nullable
    public ServerPlayer m_147158_() {
        return this.f_20863_;
    }

    public void m_20879_(@Nullable ServerPlayer p_20880_) {
        this.f_20863_ = p_20880_;
    }

    private void m_147161_() {
        BlockPos $$0 = this.m_147162_();
        BlockState $$1 = this.f_19853_.m_8055_($$0);
        if ($$1.m_60713_(Blocks.f_152587_)) {
            ((LightningRodBlock)$$1.m_60734_()).m_153760_($$1, this.f_19853_, $$0);
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_20860_ == 2) {
            if (this.f_19853_.m_5776_()) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12090_, SoundSource.WEATHER, 10000.0f, 0.8f + this.f_19796_.m_188501_() * 0.2f, false);
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12089_, SoundSource.WEATHER, 2.0f, 0.5f + this.f_19796_.m_188501_() * 0.2f, false);
            } else {
                Difficulty $$0 = this.f_19853_.m_46791_();
                if ($$0 == Difficulty.NORMAL || $$0 == Difficulty.HARD) {
                    this.m_20870_(4);
                }
                this.m_147161_();
                LightningBolt.m_147150_(this.f_19853_, this.m_147162_());
                this.m_146850_(GameEvent.f_157772_);
            }
        }
        --this.f_20860_;
        if (this.f_20860_ < 0) {
            if (this.f_20861_ == 0) {
                if (this.f_19853_ instanceof ServerLevel) {
                    List<Entity> $$1 = this.f_19853_.m_6249_(this, new AABB(this.m_20185_() - 15.0, this.m_20186_() - 15.0, this.m_20189_() - 15.0, this.m_20185_() + 15.0, this.m_20186_() + 6.0 + 15.0, this.m_20189_() + 15.0), p_147140_ -> p_147140_.m_6084_() && !this.f_147134_.contains(p_147140_));
                    for (ServerPlayer $$2 : ((ServerLevel)this.f_19853_).m_8795_(p_147157_ -> p_147157_.m_20270_(this) < 256.0f)) {
                        CriteriaTriggers.f_145089_.m_153391_($$2, this, $$1);
                    }
                }
                this.m_146870_();
            } else if (this.f_20860_ < -this.f_19796_.m_188503_(10)) {
                --this.f_20861_;
                this.f_20860_ = 1;
                this.f_20859_ = this.f_19796_.m_188505_();
                this.m_20870_(0);
            }
        }
        if (this.f_20860_ >= 0) {
            if (!(this.f_19853_ instanceof ServerLevel)) {
                this.f_19853_.m_6580_(2);
            } else if (!this.f_20862_) {
                List<Entity> $$3 = this.f_19853_.m_6249_(this, new AABB(this.m_20185_() - 3.0, this.m_20186_() - 3.0, this.m_20189_() - 3.0, this.m_20185_() + 3.0, this.m_20186_() + 6.0 + 3.0, this.m_20189_() + 3.0), Entity::m_6084_);
                for (Entity $$4 : $$3) {
                    $$4.m_8038_((ServerLevel)this.f_19853_, this);
                }
                this.f_147134_.addAll($$3);
                if (this.f_20863_ != null) {
                    CriteriaTriggers.f_10554_.m_21721_(this.f_20863_, $$3);
                }
            }
        }
    }

    private BlockPos m_147162_() {
        Vec3 $$0 = this.m_20182_();
        return new BlockPos($$0.f_82479_, $$0.f_82480_ - 1.0E-6, $$0.f_82481_);
    }

    private void m_20870_(int p_20871_) {
        if (this.f_20862_ || this.f_19853_.f_46443_ || !this.f_19853_.m_46469_().m_46207_(GameRules.f_46131_)) {
            return;
        }
        BlockPos $$1 = this.m_20183_();
        BlockState $$2 = BaseFireBlock.m_49245_(this.f_19853_, $$1);
        if (this.f_19853_.m_8055_($$1).m_60795_() && $$2.m_60710_(this.f_19853_, $$1)) {
            this.f_19853_.m_46597_($$1, $$2);
            ++this.f_147135_;
        }
        for (int $$3 = 0; $$3 < p_20871_; ++$$3) {
            BlockPos $$4 = $$1.m_7918_(this.f_19796_.m_188503_(3) - 1, this.f_19796_.m_188503_(3) - 1, this.f_19796_.m_188503_(3) - 1);
            $$2 = BaseFireBlock.m_49245_(this.f_19853_, $$4);
            if (!this.f_19853_.m_8055_($$4).m_60795_() || !$$2.m_60710_(this.f_19853_, $$4)) continue;
            this.f_19853_.m_46597_($$4, $$2);
            ++this.f_147135_;
        }
    }

    private static void m_147150_(Level p_147151_, BlockPos p_147152_) {
        BlockState $$6;
        BlockPos $$5;
        BlockState $$2 = p_147151_.m_8055_(p_147152_);
        if ($$2.m_60713_(Blocks.f_152587_)) {
            BlockPos $$3 = p_147152_.m_121945_($$2.m_61143_(LightningRodBlock.f_52588_).m_122424_());
            BlockState $$4 = p_147151_.m_8055_($$3);
        } else {
            $$5 = p_147152_;
            $$6 = $$2;
        }
        if (!($$6.m_60734_() instanceof WeatheringCopper)) {
            return;
        }
        p_147151_.m_46597_($$5, WeatheringCopper.m_154906_(p_147151_.m_8055_($$5)));
        BlockPos.MutableBlockPos $$7 = p_147152_.m_122032_();
        int $$8 = p_147151_.f_46441_.m_188503_(3) + 3;
        for (int $$9 = 0; $$9 < $$8; ++$$9) {
            int $$10 = p_147151_.f_46441_.m_188503_(8) + 1;
            LightningBolt.m_147145_(p_147151_, $$5, $$7, $$10);
        }
    }

    private static void m_147145_(Level p_147146_, BlockPos p_147147_, BlockPos.MutableBlockPos p_147148_, int p_147149_) {
        Optional<BlockPos> $$5;
        p_147148_.m_122190_(p_147147_);
        for (int $$4 = 0; $$4 < p_147149_ && ($$5 = LightningBolt.m_147153_(p_147146_, p_147148_)).isPresent(); ++$$4) {
            p_147148_.m_122190_($$5.get());
        }
    }

    private static Optional<BlockPos> m_147153_(Level p_147154_, BlockPos p_147155_) {
        for (BlockPos $$2 : BlockPos.m_235650_(p_147154_.f_46441_, 10, p_147155_, 1)) {
            BlockState $$3 = p_147154_.m_8055_($$2);
            if (!($$3.m_60734_() instanceof WeatheringCopper)) continue;
            WeatheringCopper.m_154899_($$3).ifPresent(p_147144_ -> p_147154_.m_46597_($$2, (BlockState)p_147144_));
            p_147154_.m_46796_(3002, $$2, -1);
            return Optional.of($$2);
        }
        return Optional.empty();
    }

    @Override
    public boolean m_6783_(double p_20869_) {
        double $$1 = 64.0 * LightningBolt.m_20150_();
        return p_20869_ < $$1 * $$1;
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    protected void m_7378_(CompoundTag p_20873_) {
    }

    @Override
    protected void m_7380_(CompoundTag p_20877_) {
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    public int m_147159_() {
        return this.f_147135_;
    }

    public Stream<Entity> m_147160_() {
        return this.f_147134_.stream().filter(Entity::m_6084_);
    }
}

