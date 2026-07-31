/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.decoration;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public abstract class HangingEntity
extends Entity {
    private static final Logger f_238173_ = LogUtils.getLogger();
    protected static final Predicate<Entity> f_31697_ = p_31734_ -> p_31734_ instanceof HangingEntity;
    private int f_31700_;
    protected BlockPos f_31698_;
    protected Direction f_31699_ = Direction.SOUTH;

    protected HangingEntity(EntityType<? extends HangingEntity> p_31703_, Level p_31704_) {
        super(p_31703_, p_31704_);
    }

    protected HangingEntity(EntityType<? extends HangingEntity> p_31706_, Level p_31707_, BlockPos p_31708_) {
        this(p_31706_, p_31707_);
        this.f_31698_ = p_31708_;
    }

    @Override
    protected void m_8097_() {
    }

    protected void m_6022_(Direction p_31728_) {
        Validate.notNull((Object)p_31728_);
        Validate.isTrue((boolean)p_31728_.m_122434_().m_122479_());
        this.f_31699_ = p_31728_;
        this.m_146922_(this.f_31699_.m_122416_() * 90);
        this.f_19859_ = this.m_146908_();
        this.m_7087_();
    }

    protected void m_7087_() {
        if (this.f_31699_ == null) {
            return;
        }
        double $$0 = (double)this.f_31698_.m_123341_() + 0.5;
        double $$1 = (double)this.f_31698_.m_123342_() + 0.5;
        double $$2 = (double)this.f_31698_.m_123343_() + 0.5;
        double $$3 = 0.46875;
        double $$4 = this.m_31709_(this.m_7076_());
        double $$5 = this.m_31709_(this.m_7068_());
        $$0 -= (double)this.f_31699_.m_122429_() * 0.46875;
        $$2 -= (double)this.f_31699_.m_122431_() * 0.46875;
        Direction $$6 = this.f_31699_.m_122428_();
        this.m_20343_($$0 += $$4 * (double)$$6.m_122429_(), $$1 += $$5, $$2 += $$4 * (double)$$6.m_122431_());
        double $$7 = this.m_7076_();
        double $$8 = this.m_7068_();
        double $$9 = this.m_7076_();
        if (this.f_31699_.m_122434_() == Direction.Axis.Z) {
            $$9 = 1.0;
        } else {
            $$7 = 1.0;
        }
        this.m_20011_(new AABB($$0 - ($$7 /= 32.0), $$1 - ($$8 /= 32.0), $$2 - ($$9 /= 32.0), $$0 + $$7, $$1 + $$8, $$2 + $$9));
    }

    private double m_31709_(int p_31710_) {
        return p_31710_ % 32 == 0 ? 0.5 : 0.0;
    }

    @Override
    public void m_8119_() {
        if (!this.f_19853_.f_46443_) {
            this.m_146871_();
            if (this.f_31700_++ == 100) {
                this.f_31700_ = 0;
                if (!this.m_213877_() && !this.m_7088_()) {
                    this.m_146870_();
                    this.m_5553_(null);
                }
            }
        }
    }

    public boolean m_7088_() {
        if (!this.f_19853_.m_45786_(this)) {
            return false;
        }
        int $$0 = Math.max(1, this.m_7076_() / 16);
        int $$1 = Math.max(1, this.m_7068_() / 16);
        BlockPos $$2 = this.f_31698_.m_121945_(this.f_31699_.m_122424_());
        Direction $$3 = this.f_31699_.m_122428_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (int $$5 = 0; $$5 < $$0; ++$$5) {
            for (int $$6 = 0; $$6 < $$1; ++$$6) {
                int $$7 = ($$0 - 1) / -2;
                int $$8 = ($$1 - 1) / -2;
                $$4.m_122190_($$2).m_122175_($$3, $$5 + $$7).m_122175_(Direction.UP, $$6 + $$8);
                BlockState $$9 = this.f_19853_.m_8055_($$4);
                if ($$9.m_60767_().m_76333_() || DiodeBlock.m_52586_($$9)) continue;
                return false;
            }
        }
        return this.f_19853_.m_6249_(this, this.m_20191_(), f_31697_).isEmpty();
    }

    @Override
    public boolean m_6087_() {
        return true;
    }

    @Override
    public boolean m_7313_(Entity p_31750_) {
        if (p_31750_ instanceof Player) {
            Player $$1 = (Player)p_31750_;
            if (!this.f_19853_.m_7966_($$1, this.f_31698_)) {
                return true;
            }
            return this.m_6469_(DamageSource.m_19344_($$1), 0.0f);
        }
        return false;
    }

    @Override
    public Direction m_6350_() {
        return this.f_31699_;
    }

    @Override
    public boolean m_6469_(DamageSource p_31715_, float p_31716_) {
        if (this.m_6673_(p_31715_)) {
            return false;
        }
        if (!this.m_213877_() && !this.f_19853_.f_46443_) {
            this.m_6074_();
            this.m_5834_();
            this.m_5553_(p_31715_.m_7639_());
        }
        return true;
    }

    @Override
    public void m_6478_(MoverType p_31719_, Vec3 p_31720_) {
        if (!this.f_19853_.f_46443_ && !this.m_213877_() && p_31720_.m_82556_() > 0.0) {
            this.m_6074_();
            this.m_5553_(null);
        }
    }

    @Override
    public void m_5997_(double p_31744_, double p_31745_, double p_31746_) {
        if (!this.f_19853_.f_46443_ && !this.m_213877_() && p_31744_ * p_31744_ + p_31745_ * p_31745_ + p_31746_ * p_31746_ > 0.0) {
            this.m_6074_();
            this.m_5553_(null);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_31736_) {
        BlockPos $$1 = this.m_31748_();
        p_31736_.m_128405_("TileX", $$1.m_123341_());
        p_31736_.m_128405_("TileY", $$1.m_123342_());
        p_31736_.m_128405_("TileZ", $$1.m_123343_());
    }

    @Override
    public void m_7378_(CompoundTag p_31730_) {
        BlockPos $$1 = new BlockPos(p_31730_.m_128451_("TileX"), p_31730_.m_128451_("TileY"), p_31730_.m_128451_("TileZ"));
        if (!$$1.m_123314_(this.m_20183_(), 16.0)) {
            f_238173_.error("Hanging entity at invalid position: {}", (Object)$$1);
            return;
        }
        this.f_31698_ = $$1;
    }

    public abstract int m_7076_();

    public abstract int m_7068_();

    public abstract void m_5553_(@Nullable Entity var1);

    public abstract void m_7084_();

    @Override
    public ItemEntity m_5552_(ItemStack p_31722_, float p_31723_) {
        ItemEntity $$2 = new ItemEntity(this.f_19853_, this.m_20185_() + (double)((float)this.f_31699_.m_122429_() * 0.15f), this.m_20186_() + (double)p_31723_, this.m_20189_() + (double)((float)this.f_31699_.m_122431_() * 0.15f), p_31722_);
        $$2.m_32060_();
        this.f_19853_.m_7967_($$2);
        return $$2;
    }

    @Override
    protected boolean m_6093_() {
        return false;
    }

    @Override
    public void m_6034_(double p_31739_, double p_31740_, double p_31741_) {
        this.f_31698_ = new BlockPos(p_31739_, p_31740_, p_31741_);
        this.m_7087_();
        this.f_19812_ = true;
    }

    public BlockPos m_31748_() {
        return this.f_31698_;
    }

    @Override
    public float m_7890_(Rotation p_31727_) {
        if (this.f_31699_.m_122434_() != Direction.Axis.Y) {
            switch (p_31727_) {
                case CLOCKWISE_180: {
                    this.f_31699_ = this.f_31699_.m_122424_();
                    break;
                }
                case COUNTERCLOCKWISE_90: {
                    this.f_31699_ = this.f_31699_.m_122428_();
                    break;
                }
                case CLOCKWISE_90: {
                    this.f_31699_ = this.f_31699_.m_122427_();
                    break;
                }
            }
        }
        float $$1 = Mth.m_14177_(this.m_146908_());
        switch (p_31727_) {
            case CLOCKWISE_180: {
                return $$1 + 180.0f;
            }
            case COUNTERCLOCKWISE_90: {
                return $$1 + 90.0f;
            }
            case CLOCKWISE_90: {
                return $$1 + 270.0f;
            }
        }
        return $$1;
    }

    @Override
    public float m_6961_(Mirror p_31725_) {
        return this.m_7890_(p_31725_.m_54846_(this.f_31699_));
    }

    @Override
    public void m_8038_(ServerLevel p_31712_, LightningBolt p_31713_) {
    }

    @Override
    public void m_6210_() {
    }
}

