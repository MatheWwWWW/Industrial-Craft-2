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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WorldCoordinates
implements Coordinates {
    private final WorldCoordinate f_120879_;
    private final WorldCoordinate f_120880_;
    private final WorldCoordinate f_120881_;

    public WorldCoordinates(WorldCoordinate p_120883_, WorldCoordinate p_120884_, WorldCoordinate p_120885_) {
        this.f_120879_ = p_120883_;
        this.f_120880_ = p_120884_;
        this.f_120881_ = p_120885_;
    }

    @Override
    public Vec3 m_6955_(CommandSourceStack p_120893_) {
        Vec3 $$1 = p_120893_.m_81371_();
        return new Vec3(this.f_120879_.m_120867_($$1.f_82479_), this.f_120880_.m_120867_($$1.f_82480_), this.f_120881_.m_120867_($$1.f_82481_));
    }

    @Override
    public Vec2 m_6970_(CommandSourceStack p_120896_) {
        Vec2 $$1 = p_120896_.m_81376_();
        return new Vec2((float)this.f_120879_.m_120867_($$1.f_82470_), (float)this.f_120880_.m_120867_($$1.f_82471_));
    }

    @Override
    public boolean m_6888_() {
        return this.f_120879_.m_120866_();
    }

    @Override
    public boolean m_6892_() {
        return this.f_120880_.m_120866_();
    }

    @Override
    public boolean m_6900_() {
        return this.f_120881_.m_120866_();
    }

    public boolean equals(Object p_120900_) {
        if (this == p_120900_) {
            return true;
        }
        if (!(p_120900_ instanceof WorldCoordinates)) {
            return false;
        }
        WorldCoordinates $$1 = (WorldCoordinates)p_120900_;
        if (!this.f_120879_.equals($$1.f_120879_)) {
            return false;
        }
        if (!this.f_120880_.equals($$1.f_120880_)) {
            return false;
        }
        return this.f_120881_.equals($$1.f_120881_);
    }

    public static WorldCoordinates m_120887_(StringReader p_120888_) throws CommandSyntaxException {
        int $$1 = p_120888_.getCursor();
        WorldCoordinate $$2 = WorldCoordinate.m_120869_(p_120888_);
        if (!p_120888_.canRead() || p_120888_.peek() != ' ') {
            p_120888_.setCursor($$1);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_120888_);
        }
        p_120888_.skip();
        WorldCoordinate $$3 = WorldCoordinate.m_120869_(p_120888_);
        if (!p_120888_.canRead() || p_120888_.peek() != ' ') {
            p_120888_.setCursor($$1);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_120888_);
        }
        p_120888_.skip();
        WorldCoordinate $$4 = WorldCoordinate.m_120869_(p_120888_);
        return new WorldCoordinates($$2, $$3, $$4);
    }

    public static WorldCoordinates m_120889_(StringReader p_120890_, boolean p_120891_) throws CommandSyntaxException {
        int $$2 = p_120890_.getCursor();
        WorldCoordinate $$3 = WorldCoordinate.m_120871_(p_120890_, p_120891_);
        if (!p_120890_.canRead() || p_120890_.peek() != ' ') {
            p_120890_.setCursor($$2);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_120890_);
        }
        p_120890_.skip();
        WorldCoordinate $$4 = WorldCoordinate.m_120871_(p_120890_, false);
        if (!p_120890_.canRead() || p_120890_.peek() != ' ') {
            p_120890_.setCursor($$2);
            throw Vec3Argument.f_120834_.createWithContext((ImmutableStringReader)p_120890_);
        }
        p_120890_.skip();
        WorldCoordinate $$5 = WorldCoordinate.m_120871_(p_120890_, p_120891_);
        return new WorldCoordinates($$3, $$4, $$5);
    }

    public static WorldCoordinates m_175085_(double p_175086_, double p_175087_, double p_175088_) {
        return new WorldCoordinates(new WorldCoordinate(false, p_175086_), new WorldCoordinate(false, p_175087_), new WorldCoordinate(false, p_175088_));
    }

    public static WorldCoordinates m_175089_(Vec2 p_175090_) {
        return new WorldCoordinates(new WorldCoordinate(false, p_175090_.f_82470_), new WorldCoordinate(false, p_175090_.f_82471_), new WorldCoordinate(true, 0.0));
    }

    public static WorldCoordinates m_120898_() {
        return new WorldCoordinates(new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0), new WorldCoordinate(true, 0.0));
    }

    public int hashCode() {
        int $$0 = this.f_120879_.hashCode();
        $$0 = 31 * $$0 + this.f_120880_.hashCode();
        $$0 = 31 * $$0 + this.f_120881_.hashCode();
        return $$0;
    }
}

