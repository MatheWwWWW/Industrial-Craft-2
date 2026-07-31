/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.material;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class Fluid {
    public static final IdMapper<FluidState> f_76104_ = new IdMapper();
    protected final StateDefinition<Fluid, FluidState> f_76105_;
    private FluidState f_76103_;
    private final Holder.Reference<Fluid> f_205066_ = Registry.f_122822_.m_203693_(this);

    protected Fluid() {
        StateDefinition.Builder<Fluid, FluidState> $$0 = new StateDefinition.Builder<Fluid, FluidState>(this);
        this.m_7180_($$0);
        this.f_76105_ = $$0.m_61101_(Fluid::m_76145_, FluidState::new);
        this.m_76142_(this.f_76105_.m_61090_());
    }

    protected void m_7180_(StateDefinition.Builder<Fluid, FluidState> p_76121_) {
    }

    public StateDefinition<Fluid, FluidState> m_76144_() {
        return this.f_76105_;
    }

    protected final void m_76142_(FluidState p_76143_) {
        this.f_76103_ = p_76143_;
    }

    public final FluidState m_76145_() {
        return this.f_76103_;
    }

    public abstract Item m_6859_();

    protected void m_213811_(Level p_230550_, BlockPos p_230551_, FluidState p_230552_, RandomSource p_230553_) {
    }

    protected void m_6292_(Level p_76113_, BlockPos p_76114_, FluidState p_76115_) {
    }

    protected void m_213812_(Level p_230554_, BlockPos p_230555_, FluidState p_230556_, RandomSource p_230557_) {
    }

    @Nullable
    protected ParticleOptions m_7792_() {
        return null;
    }

    protected abstract boolean m_5486_(FluidState var1, BlockGetter var2, BlockPos var3, Fluid var4, Direction var5);

    protected abstract Vec3 m_7000_(BlockGetter var1, BlockPos var2, FluidState var3);

    public abstract int m_6718_(LevelReader var1);

    protected boolean m_6685_() {
        return false;
    }

    protected boolean m_6759_() {
        return false;
    }

    protected abstract float m_6752_();

    public abstract float m_6098_(FluidState var1, BlockGetter var2, BlockPos var3);

    public abstract float m_7427_(FluidState var1);

    protected abstract BlockState m_5804_(FluidState var1);

    public abstract boolean m_7444_(FluidState var1);

    public abstract int m_7430_(FluidState var1);

    public boolean m_6212_(Fluid p_76122_) {
        return p_76122_ == this;
    }

    @Deprecated
    public boolean m_205067_(TagKey<Fluid> p_205068_) {
        return this.f_205066_.m_203656_(p_205068_);
    }

    public abstract VoxelShape m_7999_(FluidState var1, BlockGetter var2, BlockPos var3);

    public Optional<SoundEvent> m_142520_() {
        return Optional.empty();
    }

    @Deprecated
    public Holder.Reference<Fluid> m_205069_() {
        return this.f_205066_;
    }
}

