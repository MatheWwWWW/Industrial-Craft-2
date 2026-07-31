/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Camera {
    private boolean f_90549_;
    private BlockGetter f_90550_;
    private Entity f_90551_;
    private Vec3 f_90552_ = Vec3.f_82478_;
    private final BlockPos.MutableBlockPos f_90553_ = new BlockPos.MutableBlockPos();
    private final Vector3f f_90554_ = new Vector3f(0.0f, 0.0f, 1.0f);
    private final Vector3f f_90555_ = new Vector3f(0.0f, 1.0f, 0.0f);
    private final Vector3f f_90556_ = new Vector3f(1.0f, 0.0f, 0.0f);
    private float f_90557_;
    private float f_90558_;
    private final Quaternion f_90559_ = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private boolean f_90560_;
    private float f_90562_;
    private float f_90563_;
    public static final float f_167683_ = 0.083333336f;

    public void m_90575_(BlockGetter p_90576_, Entity p_90577_, boolean p_90578_, boolean p_90579_, float p_90580_) {
        this.f_90549_ = true;
        this.f_90550_ = p_90576_;
        this.f_90551_ = p_90577_;
        this.f_90560_ = p_90578_;
        this.m_90572_(p_90577_.m_5675_(p_90580_), p_90577_.m_5686_(p_90580_));
        this.m_90584_(Mth.m_14139_(p_90580_, p_90577_.f_19854_, p_90577_.m_20185_()), Mth.m_14139_(p_90580_, p_90577_.f_19855_, p_90577_.m_20186_()) + (double)Mth.m_14179_(p_90580_, this.f_90563_, this.f_90562_), Mth.m_14139_(p_90580_, p_90577_.f_19856_, p_90577_.m_20189_()));
        if (p_90578_) {
            if (p_90579_) {
                this.m_90572_(this.f_90558_ + 180.0f, -this.f_90557_);
            }
            this.m_90568_(-this.m_90566_(4.0), 0.0, 0.0);
        } else if (p_90577_ instanceof LivingEntity && ((LivingEntity)p_90577_).m_5803_()) {
            Direction $$5 = ((LivingEntity)p_90577_).m_21259_();
            this.m_90572_($$5 != null ? $$5.m_122435_() - 180.0f : 0.0f, 0.0f);
            this.m_90568_(0.0, 0.3, 0.0);
        }
    }

    public void m_90565_() {
        if (this.f_90551_ != null) {
            this.f_90563_ = this.f_90562_;
            this.f_90562_ += (this.f_90551_.m_20192_() - this.f_90562_) * 0.5f;
        }
    }

    private double m_90566_(double p_90567_) {
        for (int $$1 = 0; $$1 < 8; ++$$1) {
            double $$8;
            Vec3 $$6;
            BlockHitResult $$7;
            float $$2 = ($$1 & 1) * 2 - 1;
            float $$3 = ($$1 >> 1 & 1) * 2 - 1;
            float $$4 = ($$1 >> 2 & 1) * 2 - 1;
            Vec3 $$5 = this.f_90552_.m_82520_($$2 *= 0.1f, $$3 *= 0.1f, $$4 *= 0.1f);
            if (((HitResult)($$7 = this.f_90550_.m_45547_(new ClipContext($$5, $$6 = new Vec3(this.f_90552_.f_82479_ - (double)this.f_90554_.m_122239_() * p_90567_ + (double)$$2 + (double)$$4, this.f_90552_.f_82480_ - (double)this.f_90554_.m_122260_() * p_90567_ + (double)$$3, this.f_90552_.f_82481_ - (double)this.f_90554_.m_122269_() * p_90567_ + (double)$$4), ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, this.f_90551_)))).m_6662_() == HitResult.Type.MISS || !(($$8 = $$7.m_82450_().m_82554_(this.f_90552_)) < p_90567_)) continue;
            p_90567_ = $$8;
        }
        return p_90567_;
    }

    protected void m_90568_(double p_90569_, double p_90570_, double p_90571_) {
        double $$3 = (double)this.f_90554_.m_122239_() * p_90569_ + (double)this.f_90555_.m_122239_() * p_90570_ + (double)this.f_90556_.m_122239_() * p_90571_;
        double $$4 = (double)this.f_90554_.m_122260_() * p_90569_ + (double)this.f_90555_.m_122260_() * p_90570_ + (double)this.f_90556_.m_122260_() * p_90571_;
        double $$5 = (double)this.f_90554_.m_122269_() * p_90569_ + (double)this.f_90555_.m_122269_() * p_90570_ + (double)this.f_90556_.m_122269_() * p_90571_;
        this.m_90581_(new Vec3(this.f_90552_.f_82479_ + $$3, this.f_90552_.f_82480_ + $$4, this.f_90552_.f_82481_ + $$5));
    }

    protected void m_90572_(float p_90573_, float p_90574_) {
        this.f_90557_ = p_90574_;
        this.f_90558_ = p_90573_;
        this.f_90559_.m_80143_(0.0f, 0.0f, 0.0f, 1.0f);
        this.f_90559_.m_80148_(Vector3f.f_122225_.m_122240_(-p_90573_));
        this.f_90559_.m_80148_(Vector3f.f_122223_.m_122240_(p_90574_));
        this.f_90554_.m_122245_(0.0f, 0.0f, 1.0f);
        this.f_90554_.m_122251_(this.f_90559_);
        this.f_90555_.m_122245_(0.0f, 1.0f, 0.0f);
        this.f_90555_.m_122251_(this.f_90559_);
        this.f_90556_.m_122245_(1.0f, 0.0f, 0.0f);
        this.f_90556_.m_122251_(this.f_90559_);
    }

    protected void m_90584_(double p_90585_, double p_90586_, double p_90587_) {
        this.m_90581_(new Vec3(p_90585_, p_90586_, p_90587_));
    }

    protected void m_90581_(Vec3 p_90582_) {
        this.f_90552_ = p_90582_;
        this.f_90553_.m_122169_(p_90582_.f_82479_, p_90582_.f_82480_, p_90582_.f_82481_);
    }

    public Vec3 m_90583_() {
        return this.f_90552_;
    }

    public BlockPos m_90588_() {
        return this.f_90553_;
    }

    public float m_90589_() {
        return this.f_90557_;
    }

    public float m_90590_() {
        return this.f_90558_;
    }

    public Quaternion m_90591_() {
        return this.f_90559_;
    }

    public Entity m_90592_() {
        return this.f_90551_;
    }

    public boolean m_90593_() {
        return this.f_90549_;
    }

    public boolean m_90594_() {
        return this.f_90560_;
    }

    public NearPlane m_167684_() {
        Minecraft $$0 = Minecraft.m_91087_();
        double $$1 = (double)$$0.m_91268_().m_85441_() / (double)$$0.m_91268_().m_85442_();
        double $$2 = Math.tan((double)((float)$$0.f_91066_.m_231837_().m_231551_().intValue() * ((float)Math.PI / 180)) / 2.0) * (double)0.05f;
        double $$3 = $$2 * $$1;
        Vec3 $$4 = new Vec3(this.f_90554_).m_82490_(0.05f);
        Vec3 $$5 = new Vec3(this.f_90556_).m_82490_($$3);
        Vec3 $$6 = new Vec3(this.f_90555_).m_82490_($$2);
        return new NearPlane($$4, $$5, $$6);
    }

    public FogType m_167685_() {
        if (!this.f_90549_) {
            return FogType.NONE;
        }
        FluidState $$0 = this.f_90550_.m_6425_(this.f_90553_);
        if ($$0.m_205070_(FluidTags.f_13131_) && this.f_90552_.f_82480_ < (double)((float)this.f_90553_.m_123342_() + $$0.m_76155_(this.f_90550_, this.f_90553_))) {
            return FogType.WATER;
        }
        NearPlane $$1 = this.m_167684_();
        List<Vec3> $$2 = Arrays.asList($$1.f_167687_, $$1.m_167694_(), $$1.m_167698_(), $$1.m_167699_(), $$1.m_167700_());
        for (Vec3 $$3 : $$2) {
            Vec3 $$4 = this.f_90552_.m_82549_($$3);
            BlockPos $$5 = new BlockPos($$4);
            FluidState $$6 = this.f_90550_.m_6425_($$5);
            if ($$6.m_205070_(FluidTags.f_13132_)) {
                if (!($$4.f_82480_ <= (double)($$6.m_76155_(this.f_90550_, $$5) + (float)$$5.m_123342_()))) continue;
                return FogType.LAVA;
            }
            BlockState $$7 = this.f_90550_.m_8055_($$5);
            if (!$$7.m_60713_(Blocks.f_152499_)) continue;
            return FogType.POWDER_SNOW;
        }
        return FogType.NONE;
    }

    public final Vector3f m_90596_() {
        return this.f_90554_;
    }

    public final Vector3f m_90597_() {
        return this.f_90555_;
    }

    public final Vector3f m_167686_() {
        return this.f_90556_;
    }

    public void m_90598_() {
        this.f_90550_ = null;
        this.f_90551_ = null;
        this.f_90549_ = false;
    }

    public static class NearPlane {
        final Vec3 f_167687_;
        private final Vec3 f_167688_;
        private final Vec3 f_167689_;

        NearPlane(Vec3 p_167691_, Vec3 p_167692_, Vec3 p_167693_) {
            this.f_167687_ = p_167691_;
            this.f_167688_ = p_167692_;
            this.f_167689_ = p_167693_;
        }

        public Vec3 m_167694_() {
            return this.f_167687_.m_82549_(this.f_167689_).m_82549_(this.f_167688_);
        }

        public Vec3 m_167698_() {
            return this.f_167687_.m_82549_(this.f_167689_).m_82546_(this.f_167688_);
        }

        public Vec3 m_167699_() {
            return this.f_167687_.m_82546_(this.f_167689_).m_82549_(this.f_167688_);
        }

        public Vec3 m_167700_() {
            return this.f_167687_.m_82546_(this.f_167689_).m_82546_(this.f_167688_);
        }

        public Vec3 m_167695_(float p_167696_, float p_167697_) {
            return this.f_167687_.m_82549_(this.f_167689_.m_82490_(p_167697_)).m_82546_(this.f_167688_.m_82490_(p_167696_));
        }
    }
}

