/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.item;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class FallingBlockEntity
extends Entity {
    private static final Logger f_201970_ = LogUtils.getLogger();
    private BlockState f_31946_ = Blocks.f_49992_.m_49966_();
    public int f_31942_;
    public boolean f_31943_ = true;
    private boolean f_31947_;
    private boolean f_31939_;
    private int f_31940_ = 40;
    private float f_149641_;
    @Nullable
    public CompoundTag f_31944_;
    protected static final EntityDataAccessor<BlockPos> f_31945_ = SynchedEntityData.m_135353_(FallingBlockEntity.class, EntityDataSerializers.f_135038_);

    public FallingBlockEntity(EntityType<? extends FallingBlockEntity> p_31950_, Level p_31951_) {
        super(p_31950_, p_31951_);
    }

    private FallingBlockEntity(Level p_31953_, double p_31954_, double p_31955_, double p_31956_, BlockState p_31957_) {
        this((EntityType<? extends FallingBlockEntity>)EntityType.f_20450_, p_31953_);
        this.f_31946_ = p_31957_;
        this.f_19850_ = true;
        this.m_6034_(p_31954_, p_31955_, p_31956_);
        this.m_20256_(Vec3.f_82478_);
        this.f_19854_ = p_31954_;
        this.f_19855_ = p_31955_;
        this.f_19856_ = p_31956_;
        this.m_31959_(this.m_20183_());
    }

    public static FallingBlockEntity m_201971_(Level p_201972_, BlockPos p_201973_, BlockState p_201974_) {
        FallingBlockEntity $$3 = new FallingBlockEntity(p_201972_, (double)p_201973_.m_123341_() + 0.5, p_201973_.m_123342_(), (double)p_201973_.m_123343_() + 0.5, p_201974_.m_61138_(BlockStateProperties.f_61362_) ? (BlockState)p_201974_.m_61124_(BlockStateProperties.f_61362_, false) : p_201974_);
        p_201972_.m_7731_(p_201973_, p_201974_.m_60819_().m_76188_(), 3);
        p_201972_.m_7967_($$3);
        return $$3;
    }

    @Override
    public boolean m_6097_() {
        return false;
    }

    public void m_31959_(BlockPos p_31960_) {
        this.f_19804_.m_135381_(f_31945_, p_31960_);
    }

    public BlockPos m_31978_() {
        return this.f_19804_.m_135370_(f_31945_);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_31945_, BlockPos.f_121853_);
    }

    @Override
    public boolean m_6087_() {
        return !this.m_213877_();
    }

    @Override
    public void m_8119_() {
        if (this.f_31946_.m_60795_()) {
            this.m_146870_();
            return;
        }
        Block $$0 = this.f_31946_.m_60734_();
        ++this.f_31942_;
        if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
        }
        this.m_6478_(MoverType.SELF, this.m_20184_());
        if (!this.f_19853_.f_46443_) {
            BlockHitResult $$5;
            BlockPos $$1 = this.m_20183_();
            boolean $$2 = this.f_31946_.m_60734_() instanceof ConcretePowderBlock;
            boolean $$3 = $$2 && this.f_19853_.m_6425_($$1).m_205070_(FluidTags.f_13131_);
            double $$4 = this.m_20184_().m_82556_();
            if ($$2 && $$4 > 1.0 && ($$5 = this.f_19853_.m_45547_(new ClipContext(new Vec3(this.f_19854_, this.f_19855_, this.f_19856_), this.m_20182_(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this))).m_6662_() != HitResult.Type.MISS && this.f_19853_.m_6425_($$5.m_82425_()).m_205070_(FluidTags.f_13131_)) {
                $$1 = $$5.m_82425_();
                $$3 = true;
            }
            if (this.f_19861_ || $$3) {
                BlockState $$6 = this.f_19853_.m_8055_($$1);
                this.m_20256_(this.m_20184_().m_82542_(0.7, -0.5, 0.7));
                if (!$$6.m_60713_(Blocks.f_50110_)) {
                    if (!this.f_31947_) {
                        boolean $$9;
                        boolean $$7 = $$6.m_60629_(new DirectionalPlaceContext(this.f_19853_, $$1, Direction.DOWN, ItemStack.f_41583_, Direction.UP));
                        boolean $$8 = FallingBlock.m_53241_(this.f_19853_.m_8055_($$1.m_7495_())) && (!$$2 || !$$3);
                        boolean bl = $$9 = this.f_31946_.m_60710_(this.f_19853_, $$1) && !$$8;
                        if ($$7 && $$9) {
                            if (this.f_31946_.m_61138_(BlockStateProperties.f_61362_) && this.f_19853_.m_6425_($$1).m_76152_() == Fluids.f_76193_) {
                                this.f_31946_ = (BlockState)this.f_31946_.m_61124_(BlockStateProperties.f_61362_, true);
                            }
                            if (this.f_19853_.m_7731_($$1, this.f_31946_, 3)) {
                                BlockEntity $$10;
                                ((ServerLevel)this.f_19853_).m_7726_().f_8325_.m_140201_(this, new ClientboundBlockUpdatePacket($$1, this.f_19853_.m_8055_($$1)));
                                this.m_146870_();
                                if ($$0 instanceof Fallable) {
                                    ((Fallable)((Object)$$0)).m_48792_(this.f_19853_, $$1, this.f_31946_, $$6, this);
                                }
                                if (this.f_31944_ != null && this.f_31946_.m_155947_() && ($$10 = this.f_19853_.m_7702_($$1)) != null) {
                                    CompoundTag $$11 = $$10.m_187482_();
                                    for (String $$12 : this.f_31944_.m_128431_()) {
                                        $$11.m_128365_($$12, this.f_31944_.m_128423_($$12).m_6426_());
                                    }
                                    try {
                                        $$10.m_142466_($$11);
                                    }
                                    catch (Exception $$13) {
                                        f_201970_.error("Failed to load block entity from falling block", (Throwable)$$13);
                                    }
                                    $$10.m_6596_();
                                }
                            } else if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                                this.m_146870_();
                                this.m_149650_($$0, $$1);
                                this.m_19998_($$0);
                            }
                        } else {
                            this.m_146870_();
                            if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                                this.m_149650_($$0, $$1);
                                this.m_19998_($$0);
                            }
                        }
                    } else {
                        this.m_146870_();
                        this.m_149650_($$0, $$1);
                    }
                }
            } else if (!(this.f_19853_.f_46443_ || (this.f_31942_ <= 100 || $$1.m_123342_() > this.f_19853_.m_141937_() && $$1.m_123342_() <= this.f_19853_.m_151558_()) && this.f_31942_ <= 600)) {
                if (this.f_31943_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                    this.m_19998_($$0);
                }
                this.m_146870_();
            }
        }
        this.m_20256_(this.m_20184_().m_82490_(0.98));
    }

    public void m_149650_(Block p_149651_, BlockPos p_149652_) {
        if (p_149651_ instanceof Fallable) {
            ((Fallable)((Object)p_149651_)).m_142525_(this.f_19853_, p_149652_, this);
        }
    }

    @Override
    public boolean m_142535_(float p_149643_, float p_149644_, DamageSource p_149645_) {
        DamageSource $$8;
        Predicate<Entity> $$7;
        if (!this.f_31939_) {
            return false;
        }
        int $$3 = Mth.m_14167_(p_149643_ - 1.0f);
        if ($$3 < 0) {
            return false;
        }
        if (this.f_31946_.m_60734_() instanceof Fallable) {
            Fallable $$4 = (Fallable)((Object)this.f_31946_.m_60734_());
            Predicate<Entity> $$5 = $$4.m_142398_();
            DamageSource $$6 = $$4.m_142088_();
        } else {
            $$7 = EntitySelector.f_20408_;
            $$8 = DamageSource.f_19322_;
        }
        float $$9 = Math.min(Mth.m_14143_((float)$$3 * this.f_149641_), this.f_31940_);
        this.f_19853_.m_6249_(this, this.m_20191_(), $$7).forEach(p_149649_ -> p_149649_.m_6469_($$8, $$9));
        boolean $$10 = this.f_31946_.m_204336_(BlockTags.f_13033_);
        if ($$10 && $$9 > 0.0f && this.f_19796_.m_188501_() < 0.05f + (float)$$3 * 0.05f) {
            BlockState $$11 = AnvilBlock.m_48824_(this.f_31946_);
            if ($$11 == null) {
                this.f_31947_ = true;
            } else {
                this.f_31946_ = $$11;
            }
        }
        return false;
    }

    @Override
    protected void m_7380_(CompoundTag p_31973_) {
        p_31973_.m_128365_("BlockState", NbtUtils.m_129202_(this.f_31946_));
        p_31973_.m_128405_("Time", this.f_31942_);
        p_31973_.m_128379_("DropItem", this.f_31943_);
        p_31973_.m_128379_("HurtEntities", this.f_31939_);
        p_31973_.m_128350_("FallHurtAmount", this.f_149641_);
        p_31973_.m_128405_("FallHurtMax", this.f_31940_);
        if (this.f_31944_ != null) {
            p_31973_.m_128365_("TileEntityData", this.f_31944_);
        }
    }

    @Override
    protected void m_7378_(CompoundTag p_31964_) {
        this.f_31946_ = NbtUtils.m_129241_(p_31964_.m_128469_("BlockState"));
        this.f_31942_ = p_31964_.m_128451_("Time");
        if (p_31964_.m_128425_("HurtEntities", 99)) {
            this.f_31939_ = p_31964_.m_128471_("HurtEntities");
            this.f_149641_ = p_31964_.m_128457_("FallHurtAmount");
            this.f_31940_ = p_31964_.m_128451_("FallHurtMax");
        } else if (this.f_31946_.m_204336_(BlockTags.f_13033_)) {
            this.f_31939_ = true;
        }
        if (p_31964_.m_128425_("DropItem", 99)) {
            this.f_31943_ = p_31964_.m_128471_("DropItem");
        }
        if (p_31964_.m_128425_("TileEntityData", 10)) {
            this.f_31944_ = p_31964_.m_128469_("TileEntityData");
        }
        if (this.f_31946_.m_60795_()) {
            this.f_31946_ = Blocks.f_49992_.m_49966_();
        }
    }

    public void m_149656_(float p_149657_, int p_149658_) {
        this.f_31939_ = true;
        this.f_149641_ = p_149657_;
        this.f_31940_ = p_149658_;
    }

    @Override
    public boolean m_6051_() {
        return false;
    }

    @Override
    public void m_7976_(CrashReportCategory p_31962_) {
        super.m_7976_(p_31962_);
        p_31962_.m_128159_("Immitating BlockState", this.f_31946_.toString());
    }

    public BlockState m_31980_() {
        return this.f_31946_;
    }

    @Override
    public boolean m_6127_() {
        return true;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this, Block.m_49956_(this.m_31980_()));
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_149654_) {
        super.m_141965_(p_149654_);
        this.f_31946_ = Block.m_49803_(p_149654_.m_131509_());
        this.f_19850_ = true;
        double $$1 = p_149654_.m_131500_();
        double $$2 = p_149654_.m_131501_();
        double $$3 = p_149654_.m_131502_();
        this.m_6034_($$1, $$2, $$3);
        this.m_31959_(this.m_20183_());
    }
}

