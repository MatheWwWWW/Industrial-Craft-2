/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.phys;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class Vec3
implements Position {
    public static final Codec<Vec3> f_231074_ = Codec.DOUBLE.listOf().comapFlatMap(p_231079_ -> Util.m_143795_(p_231079_, 3).map(p_231081_ -> new Vec3((Double)p_231081_.get(0), (Double)p_231081_.get(1), (Double)p_231081_.get(2))), p_231083_ -> List.of(Double.valueOf(p_231083_.m_7096_()), Double.valueOf(p_231083_.m_7098_()), Double.valueOf(p_231083_.m_7094_())));
    public static final Vec3 f_82478_ = new Vec3(0.0, 0.0, 0.0);
    public final double f_82479_;
    public final double f_82480_;
    public final double f_82481_;

    public static Vec3 m_82501_(int p_82502_) {
        double $$1 = (double)(p_82502_ >> 16 & 0xFF) / 255.0;
        double $$2 = (double)(p_82502_ >> 8 & 0xFF) / 255.0;
        double $$3 = (double)(p_82502_ & 0xFF) / 255.0;
        return new Vec3($$1, $$2, $$3);
    }

    public static Vec3 m_82512_(Vec3i p_82513_) {
        return new Vec3((double)p_82513_.m_123341_() + 0.5, (double)p_82513_.m_123342_() + 0.5, (double)p_82513_.m_123343_() + 0.5);
    }

    public static Vec3 m_82528_(Vec3i p_82529_) {
        return new Vec3(p_82529_.m_123341_(), p_82529_.m_123342_(), p_82529_.m_123343_());
    }

    public static Vec3 m_82539_(Vec3i p_82540_) {
        return new Vec3((double)p_82540_.m_123341_() + 0.5, p_82540_.m_123342_(), (double)p_82540_.m_123343_() + 0.5);
    }

    public static Vec3 m_82514_(Vec3i p_82515_, double p_82516_) {
        return new Vec3((double)p_82515_.m_123341_() + 0.5, (double)p_82515_.m_123342_() + p_82516_, (double)p_82515_.m_123343_() + 0.5);
    }

    public Vec3(double p_82484_, double p_82485_, double p_82486_) {
        this.f_82479_ = p_82484_;
        this.f_82480_ = p_82485_;
        this.f_82481_ = p_82486_;
    }

    public Vec3(Vector3f p_82488_) {
        this(p_82488_.m_122239_(), p_82488_.m_122260_(), p_82488_.m_122269_());
    }

    public Vec3 m_82505_(Vec3 p_82506_) {
        return new Vec3(p_82506_.f_82479_ - this.f_82479_, p_82506_.f_82480_ - this.f_82480_, p_82506_.f_82481_ - this.f_82481_);
    }

    public Vec3 m_82541_() {
        double $$0 = Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_);
        if ($$0 < 1.0E-4) {
            return f_82478_;
        }
        return new Vec3(this.f_82479_ / $$0, this.f_82480_ / $$0, this.f_82481_ / $$0);
    }

    public double m_82526_(Vec3 p_82527_) {
        return this.f_82479_ * p_82527_.f_82479_ + this.f_82480_ * p_82527_.f_82480_ + this.f_82481_ * p_82527_.f_82481_;
    }

    public Vec3 m_82537_(Vec3 p_82538_) {
        return new Vec3(this.f_82480_ * p_82538_.f_82481_ - this.f_82481_ * p_82538_.f_82480_, this.f_82481_ * p_82538_.f_82479_ - this.f_82479_ * p_82538_.f_82481_, this.f_82479_ * p_82538_.f_82480_ - this.f_82480_ * p_82538_.f_82479_);
    }

    public Vec3 m_82546_(Vec3 p_82547_) {
        return this.m_82492_(p_82547_.f_82479_, p_82547_.f_82480_, p_82547_.f_82481_);
    }

    public Vec3 m_82492_(double p_82493_, double p_82494_, double p_82495_) {
        return this.m_82520_(-p_82493_, -p_82494_, -p_82495_);
    }

    public Vec3 m_82549_(Vec3 p_82550_) {
        return this.m_82520_(p_82550_.f_82479_, p_82550_.f_82480_, p_82550_.f_82481_);
    }

    public Vec3 m_82520_(double p_82521_, double p_82522_, double p_82523_) {
        return new Vec3(this.f_82479_ + p_82521_, this.f_82480_ + p_82522_, this.f_82481_ + p_82523_);
    }

    public boolean m_82509_(Position p_82510_, double p_82511_) {
        return this.m_82531_(p_82510_.m_7096_(), p_82510_.m_7098_(), p_82510_.m_7094_()) < p_82511_ * p_82511_;
    }

    public double m_82554_(Vec3 p_82555_) {
        double $$1 = p_82555_.f_82479_ - this.f_82479_;
        double $$2 = p_82555_.f_82480_ - this.f_82480_;
        double $$3 = p_82555_.f_82481_ - this.f_82481_;
        return Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
    }

    public double m_82557_(Vec3 p_82558_) {
        double $$1 = p_82558_.f_82479_ - this.f_82479_;
        double $$2 = p_82558_.f_82480_ - this.f_82480_;
        double $$3 = p_82558_.f_82481_ - this.f_82481_;
        return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
    }

    public double m_82531_(double p_82532_, double p_82533_, double p_82534_) {
        double $$3 = p_82532_ - this.f_82479_;
        double $$4 = p_82533_ - this.f_82480_;
        double $$5 = p_82534_ - this.f_82481_;
        return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
    }

    public Vec3 m_82490_(double p_82491_) {
        return this.m_82542_(p_82491_, p_82491_, p_82491_);
    }

    public Vec3 m_82548_() {
        return this.m_82490_(-1.0);
    }

    public Vec3 m_82559_(Vec3 p_82560_) {
        return this.m_82542_(p_82560_.f_82479_, p_82560_.f_82480_, p_82560_.f_82481_);
    }

    public Vec3 m_82542_(double p_82543_, double p_82544_, double p_82545_) {
        return new Vec3(this.f_82479_ * p_82543_, this.f_82480_ * p_82544_, this.f_82481_ * p_82545_);
    }

    public double m_82553_() {
        return Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_);
    }

    public double m_82556_() {
        return this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_;
    }

    public double m_165924_() {
        return Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82481_ * this.f_82481_);
    }

    public double m_165925_() {
        return this.f_82479_ * this.f_82479_ + this.f_82481_ * this.f_82481_;
    }

    public boolean equals(Object p_82552_) {
        if (this == p_82552_) {
            return true;
        }
        if (!(p_82552_ instanceof Vec3)) {
            return false;
        }
        Vec3 $$1 = (Vec3)p_82552_;
        if (Double.compare($$1.f_82479_, this.f_82479_) != 0) {
            return false;
        }
        if (Double.compare($$1.f_82480_, this.f_82480_) != 0) {
            return false;
        }
        return Double.compare($$1.f_82481_, this.f_82481_) == 0;
    }

    public int hashCode() {
        long $$0 = Double.doubleToLongBits(this.f_82479_);
        int $$1 = (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82480_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        $$0 = Double.doubleToLongBits(this.f_82481_);
        $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32);
        return $$1;
    }

    public String toString() {
        return "(" + this.f_82479_ + ", " + this.f_82480_ + ", " + this.f_82481_ + ")";
    }

    public Vec3 m_165921_(Vec3 p_165922_, double p_165923_) {
        return new Vec3(Mth.m_14139_(p_165923_, this.f_82479_, p_165922_.f_82479_), Mth.m_14139_(p_165923_, this.f_82480_, p_165922_.f_82480_), Mth.m_14139_(p_165923_, this.f_82481_, p_165922_.f_82481_));
    }

    public Vec3 m_82496_(float p_82497_) {
        float $$1 = Mth.m_14089_(p_82497_);
        float $$2 = Mth.m_14031_(p_82497_);
        double $$3 = this.f_82479_;
        double $$4 = this.f_82480_ * (double)$$1 + this.f_82481_ * (double)$$2;
        double $$5 = this.f_82481_ * (double)$$1 - this.f_82480_ * (double)$$2;
        return new Vec3($$3, $$4, $$5);
    }

    public Vec3 m_82524_(float p_82525_) {
        float $$1 = Mth.m_14089_(p_82525_);
        float $$2 = Mth.m_14031_(p_82525_);
        double $$3 = this.f_82479_ * (double)$$1 + this.f_82481_ * (double)$$2;
        double $$4 = this.f_82480_;
        double $$5 = this.f_82481_ * (double)$$1 - this.f_82479_ * (double)$$2;
        return new Vec3($$3, $$4, $$5);
    }

    public Vec3 m_82535_(float p_82536_) {
        float $$1 = Mth.m_14089_(p_82536_);
        float $$2 = Mth.m_14031_(p_82536_);
        double $$3 = this.f_82479_ * (double)$$1 + this.f_82480_ * (double)$$2;
        double $$4 = this.f_82480_ * (double)$$1 - this.f_82479_ * (double)$$2;
        double $$5 = this.f_82481_;
        return new Vec3($$3, $$4, $$5);
    }

    public static Vec3 m_82503_(Vec2 p_82504_) {
        return Vec3.m_82498_(p_82504_.f_82470_, p_82504_.f_82471_);
    }

    public static Vec3 m_82498_(float p_82499_, float p_82500_) {
        float $$2 = Mth.m_14089_(-p_82500_ * ((float)Math.PI / 180) - (float)Math.PI);
        float $$3 = Mth.m_14031_(-p_82500_ * ((float)Math.PI / 180) - (float)Math.PI);
        float $$4 = -Mth.m_14089_(-p_82499_ * ((float)Math.PI / 180));
        float $$5 = Mth.m_14031_(-p_82499_ * ((float)Math.PI / 180));
        return new Vec3($$3 * $$4, $$5, $$2 * $$4);
    }

    public Vec3 m_82517_(EnumSet<Direction.Axis> p_82518_) {
        double $$1 = p_82518_.contains(Direction.Axis.X) ? (double)Mth.m_14107_(this.f_82479_) : this.f_82479_;
        double $$2 = p_82518_.contains(Direction.Axis.Y) ? (double)Mth.m_14107_(this.f_82480_) : this.f_82480_;
        double $$3 = p_82518_.contains(Direction.Axis.Z) ? (double)Mth.m_14107_(this.f_82481_) : this.f_82481_;
        return new Vec3($$1, $$2, $$3);
    }

    public double m_82507_(Direction.Axis p_82508_) {
        return p_82508_.m_6150_(this.f_82479_, this.f_82480_, this.f_82481_);
    }

    public Vec3 m_193103_(Direction.Axis p_193104_, double p_193105_) {
        double $$2 = p_193104_ == Direction.Axis.X ? p_193105_ : this.f_82479_;
        double $$3 = p_193104_ == Direction.Axis.Y ? p_193105_ : this.f_82480_;
        double $$4 = p_193104_ == Direction.Axis.Z ? p_193105_ : this.f_82481_;
        return new Vec3($$2, $$3, $$4);
    }

    public Vec3 m_231075_(Direction p_231076_, double p_231077_) {
        Vec3i $$2 = p_231076_.m_122436_();
        return new Vec3(this.f_82479_ + p_231077_ * (double)$$2.m_123341_(), this.f_82480_ + p_231077_ * (double)$$2.m_123342_(), this.f_82481_ + p_231077_ * (double)$$2.m_123343_());
    }

    @Override
    public final double m_7096_() {
        return this.f_82479_;
    }

    @Override
    public final double m_7098_() {
        return this.f_82480_;
    }

    @Override
    public final double m_7094_() {
        return this.f_82481_;
    }
}

