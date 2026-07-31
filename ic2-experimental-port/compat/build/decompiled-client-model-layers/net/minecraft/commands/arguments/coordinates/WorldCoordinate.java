/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.network.chat.Component;

public class WorldCoordinate {
    private static final char f_175084_ = '~';
    public static final SimpleCommandExceptionType f_120858_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.missing.double"));
    public static final SimpleCommandExceptionType f_120859_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.pos.missing.int"));
    private final boolean f_120860_;
    private final double f_120861_;

    public WorldCoordinate(boolean p_120864_, double p_120865_) {
        this.f_120860_ = p_120864_;
        this.f_120861_ = p_120865_;
    }

    public double m_120867_(double p_120868_) {
        if (this.f_120860_) {
            return this.f_120861_ + p_120868_;
        }
        return this.f_120861_;
    }

    public static WorldCoordinate m_120871_(StringReader p_120872_, boolean p_120873_) throws CommandSyntaxException {
        if (p_120872_.canRead() && p_120872_.peek() == '^') {
            throw Vec3Argument.f_120835_.createWithContext((ImmutableStringReader)p_120872_);
        }
        if (!p_120872_.canRead()) {
            throw f_120858_.createWithContext((ImmutableStringReader)p_120872_);
        }
        boolean $$2 = WorldCoordinate.m_120874_(p_120872_);
        int $$3 = p_120872_.getCursor();
        double $$4 = p_120872_.canRead() && p_120872_.peek() != ' ' ? p_120872_.readDouble() : 0.0;
        String $$5 = p_120872_.getString().substring($$3, p_120872_.getCursor());
        if ($$2 && $$5.isEmpty()) {
            return new WorldCoordinate(true, 0.0);
        }
        if (!$$5.contains(".") && !$$2 && p_120873_) {
            $$4 += 0.5;
        }
        return new WorldCoordinate($$2, $$4);
    }

    public static WorldCoordinate m_120869_(StringReader p_120870_) throws CommandSyntaxException {
        double $$3;
        if (p_120870_.canRead() && p_120870_.peek() == '^') {
            throw Vec3Argument.f_120835_.createWithContext((ImmutableStringReader)p_120870_);
        }
        if (!p_120870_.canRead()) {
            throw f_120859_.createWithContext((ImmutableStringReader)p_120870_);
        }
        boolean $$1 = WorldCoordinate.m_120874_(p_120870_);
        if (p_120870_.canRead() && p_120870_.peek() != ' ') {
            double $$2 = $$1 ? p_120870_.readDouble() : (double)p_120870_.readInt();
        } else {
            $$3 = 0.0;
        }
        return new WorldCoordinate($$1, $$3);
    }

    public static boolean m_120874_(StringReader p_120875_) {
        boolean $$2;
        if (p_120875_.peek() == '~') {
            boolean $$1 = true;
            p_120875_.skip();
        } else {
            $$2 = false;
        }
        return $$2;
    }

    public boolean equals(Object p_120877_) {
        if (this == p_120877_) {
            return true;
        }
        if (!(p_120877_ instanceof WorldCoordinate)) {
            return false;
        }
        WorldCoordinate $$1 = (WorldCoordinate)p_120877_;
        if (this.f_120860_ != $$1.f_120860_) {
            return false;
        }
        return Double.compare($$1.f_120861_, this.f_120861_) == 0;
    }

    public int hashCode() {
        int $$0 = this.f_120860_ ? 1 : 0;
        long $$1 = Double.doubleToLongBits(this.f_120861_);
        $$0 = 31 * $$0 + (int)($$1 ^ $$1 >>> 32);
        return $$0;
    }

    public boolean m_120866_() {
        return this.f_120860_;
    }
}

