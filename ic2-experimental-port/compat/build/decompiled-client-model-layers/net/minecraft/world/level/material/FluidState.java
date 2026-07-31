/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.material;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class FluidState
extends StateHolder<Fluid, FluidState> {
    public static final Codec<FluidState> f_76146_ = FluidState.m_61127_(Registry.f_122822_.m_194605_(), Fluid::m_76145_).stable();
    public static final int f_164510_ = 9;
    public static final int f_164511_ = 8;

    public FluidState(Fluid p_76149_, ImmutableMap<Property<?>, Comparable<?>> p_76150_, MapCodec<FluidState> p_76151_) {
        super(p_76149_, p_76150_, p_76151_);
    }

    public Fluid m_76152_() {
        return (Fluid)this.f_61112_;
    }

    public boolean m_76170_() {
        return this.m_76152_().m_7444_(this);
    }

    public boolean m_164512_(Fluid p_164513_) {
        return this.f_61112_ == p_164513_ && ((Fluid)this.f_61112_).m_7444_(this);
    }

    public boolean m_76178_() {
        return this.m_76152_().m_6759_();
    }

    public float m_76155_(BlockGetter p_76156_, BlockPos p_76157_) {
        return this.m_76152_().m_6098_(this, p_76156_, p_76157_);
    }

    public float m_76182_() {
        return this.m_76152_().m_7427_(this);
    }

    public int m_76186_() {
        return this.m_76152_().m_7430_(this);
    }

    public boolean m_76171_(BlockGetter p_76172_, BlockPos p_76173_) {
        for (int $$2 = -1; $$2 <= 1; ++$$2) {
            for (int $$3 = -1; $$3 <= 1; ++$$3) {
                BlockPos $$4 = p_76173_.m_7918_($$2, 0, $$3);
                FluidState $$5 = p_76172_.m_6425_($$4);
                if ($$5.m_76152_().m_6212_(this.m_76152_()) || p_76172_.m_8055_($$4).m_60804_(p_76172_, $$4)) continue;
                return true;
            }
        }
        return false;
    }

    public void m_76163_(Level p_76164_, BlockPos p_76165_) {
        this.m_76152_().m_6292_(p_76164_, p_76165_, this);
    }

    public void m_230558_(Level p_230559_, BlockPos p_230560_, RandomSource p_230561_) {
        this.m_76152_().m_213811_(p_230559_, p_230560_, this, p_230561_);
    }

    public boolean m_76187_() {
        return this.m_76152_().m_6685_();
    }

    public void m_230562_(Level p_230563_, BlockPos p_230564_, RandomSource p_230565_) {
        this.m_76152_().m_213812_(p_230563_, p_230564_, this, p_230565_);
    }

    public Vec3 m_76179_(BlockGetter p_76180_, BlockPos p_76181_) {
        return this.m_76152_().m_7000_(p_76180_, p_76181_, this);
    }

    public BlockState m_76188_() {
        return this.m_76152_().m_5804_(this);
    }

    @Nullable
    public ParticleOptions m_76189_() {
        return this.m_76152_().m_7792_();
    }

    public boolean m_205070_(TagKey<Fluid> p_205071_) {
        return this.m_76152_().m_205069_().m_203656_(p_205071_);
    }

    public boolean m_205072_(HolderSet<Fluid> p_205073_) {
        return p_205073_.m_203333_(this.m_76152_().m_205069_());
    }

    public boolean m_192917_(Fluid p_192918_) {
        return this.m_76152_() == p_192918_;
    }

    public float m_76190_() {
        return this.m_76152_().m_6752_();
    }

    public boolean m_76158_(BlockGetter p_76159_, BlockPos p_76160_, Fluid p_76161_, Direction p_76162_) {
        return this.m_76152_().m_5486_(this, p_76159_, p_76160_, p_76161_, p_76162_);
    }

    public VoxelShape m_76183_(BlockGetter p_76184_, BlockPos p_76185_) {
        return this.m_76152_().m_7999_(this, p_76184_, p_76185_);
    }

    public Holder<Fluid> m_205074_() {
        return ((Fluid)this.f_61112_).m_205069_();
    }

    public Stream<TagKey<Fluid>> m_205075_() {
        return ((Fluid)this.f_61112_).m_205069_().m_203616_();
    }
}

