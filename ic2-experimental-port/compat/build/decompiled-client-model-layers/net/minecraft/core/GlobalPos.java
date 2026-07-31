/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.core;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class GlobalPos {
    public static final Codec<GlobalPos> f_122633_ = RecordCodecBuilder.create(p_122642_ -> p_122642_.group((App)Level.f_46427_.fieldOf("dimension").forGetter(GlobalPos::m_122640_), (App)BlockPos.f_121852_.fieldOf("pos").forGetter(GlobalPos::m_122646_)).apply((Applicative)p_122642_, GlobalPos::m_122643_));
    private final ResourceKey<Level> f_122634_;
    private final BlockPos f_122635_;

    private GlobalPos(ResourceKey<Level> p_122638_, BlockPos p_122639_) {
        this.f_122634_ = p_122638_;
        this.f_122635_ = p_122639_;
    }

    public static GlobalPos m_122643_(ResourceKey<Level> p_122644_, BlockPos p_122645_) {
        return new GlobalPos(p_122644_, p_122645_);
    }

    public ResourceKey<Level> m_122640_() {
        return this.f_122634_;
    }

    public BlockPos m_122646_() {
        return this.f_122635_;
    }

    public boolean equals(Object p_122648_) {
        if (this == p_122648_) {
            return true;
        }
        if (p_122648_ == null || this.getClass() != p_122648_.getClass()) {
            return false;
        }
        GlobalPos $$1 = (GlobalPos)p_122648_;
        return Objects.equals(this.f_122634_, $$1.f_122634_) && Objects.equals(this.f_122635_, $$1.f_122635_);
    }

    public int hashCode() {
        return Objects.hash(this.f_122634_, this.f_122635_);
    }

    public String toString() {
        return this.f_122634_ + " " + this.f_122635_;
    }
}

