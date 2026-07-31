/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LocalCoordinates
implements Coordinates {
    public static final char f_174681_ = '^';
    private final double f_119898_;
    private final double f_119899_;
    private final double f_119900_;

    public LocalCoordinates(double p_119902_, double p_119903_, double p_119904_) {
        this.f_119898_ = p_119902_;
        this.f_119899_ = p_119903_;
        this.f_119900_ = p_119904_;
    }

    @Override
    public Vec3 m_6955_(CommandSourceStack p_119912_) {
        Vec2 $$1 = p_119912_.m_81376_();
        Vec3 $$2 = p_119912_.m_81378_().m_90379_(p_119912_);
        float $$3 = Mth.m_14089_(($$1.f_82471_ + 90.0f) * ((float)Math.PI / 180));
        float $$4 = Mth.m_14031_(($$1.f_82471_ + 90.0f) * ((float)Math.PI / 180));
        float $$5 = Mth.m_14089_(-$$1.f_82470_ * ((float)Math.PI / 180));
        float $$6 = Mth.m_14031_(-$$1.f_82470_ * ((float)Math.PI / 180));
        float $$7 = Mth.m_14089_((-$$1.f_82470_ + 90.0f) * ((float)Math.PI / 180));
        float $$8 = Mth.m_14031_((-$$1.f_82470_ + 90.0f) * ((float)Math.PI / 180));
        Vec3 $$9 = new Vec3($$3 * $$5, $$6, $$4 * $$5);
        Vec3 $$10 = new Vec3($$3 * $$7, $$8, $$4 * $$7);
        Vec3 $$11 = $$9.m_82537_($$10).m_82490_(-1.0);
        double $$12 = $$9.f_82479_ * this.f_119900_ + $$10.f_82479_ * this.f_119899_ + $$11.f_82479_ * this.f_119898_;
        double $$13 = $$9.f_82480_ * this.f_119900_ + $$10.f_82480_ * this.f_119899_ + $$11.f_82480_ * this.f_119898_;
        double $$14 = $$9.f_82481_ * this.f_119900_ + $$10.f_82481_ * this.f_119899_ + $$11.f_82481_ * this.f_119898_;
        return new Vec3($$2.f_82479_ + $$12, $$2.f_82480_ + $$13, $$2.f_82481_ + $$14);
    }

    @Override
    public Vec2 m_6970_(CommandSourceStack p_119915_) {
        return Vec2.f_82462_;
    }

    @Override
    public boolean m_6888_() {
        return true;
    }

    @Override
    public boolean m_6892_() {
        return true;
    }

    @Override
    public boolean m_6900_() {
        return true;
    }

    public static LocalCoordinates m_119906_(StringReader p_119907_) throws CommandSyntaxException {
        int $$1 = p_119907_.getCursor();
        double $$2 = LocalCoordinates.m_119908_(p_119907_, $$1);
        if (!p_119907_.canRead() || p_119907_.peek() != ' ') {
            p_119907_.setCursor($$1);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_119907_);
        }
        p_119907_.skip();
        double $$3 = LocalCoordinates.m_119908_(p_119907_, $$1);
        if (!p_119907_.canRead() || p_119907_.peek() != ' ') {
            p_119907_.setCursor($$1);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_119907_);
        }
        p_119907_.skip();
        double $$4 = LocalCoordinates.m_119908_(p_119907_, $$1);
        return new LocalCoordinates($$2, $$3, $$4);
    }

    private static double m_119908_(StringReader p_119909_, int p_119910_) throws CommandSyntaxException {
        if (!p_119909_.canRead()) {
            throw WorldCoordinate.f_120858_.createWithContext((ImmutableStringReader)p_119909_);
        }
        if (p_119909_.peek() != '^') {
            p_119909_.setCursor(p_119910_);
            throw Vec3Argument.f_120835_.createWithContext((ImmutableStringReader)p_119909_);
        }
        p_119909_.skip();
        return p_119909_.canRead() && p_119909_.peek() != ' ' ? p_119909_.readDouble() : 0.0;
    }

    public boolean equals(Object p_119918_) {
        if (this == p_119918_) {
            return true;
        }
        if (!(p_119918_ instanceof LocalCoordinates)) {
            return false;
        }
        LocalCoordinates $$1 = (LocalCoordinates)p_119918_;
        return this.f_119898_ == $$1.f_119898_ && this.f_119899_ == $$1.f_119899_ && this.f_119900_ == $$1.f_119900_;
    }

    public int hashCode() {
        return Objects.hash(this.f_119898_, this.f_119899_, this.f_119900_);
    }
}

