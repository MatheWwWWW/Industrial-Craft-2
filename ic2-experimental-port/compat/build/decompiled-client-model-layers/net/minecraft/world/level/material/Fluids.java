/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.material;

import net.minecraft.core.Registry;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;

public class Fluids {
    public static final Fluid f_76191_ = Fluids.m_76197_("empty", new EmptyFluid());
    public static final FlowingFluid f_76192_ = Fluids.m_76197_("flowing_water", new WaterFluid.Flowing());
    public static final FlowingFluid f_76193_ = Fluids.m_76197_("water", new WaterFluid.Source());
    public static final FlowingFluid f_76194_ = Fluids.m_76197_("flowing_lava", new LavaFluid.Flowing());
    public static final FlowingFluid f_76195_ = Fluids.m_76197_("lava", new LavaFluid.Source());

    private static <T extends Fluid> T m_76197_(String p_76198_, T p_76199_) {
        return (T)Registry.m_122961_(Registry.f_122822_, p_76198_, p_76199_);
    }

    static {
        for (Fluid $$0 : Registry.f_122822_) {
            for (FluidState $$1 : $$0.m_76144_().m_61056_()) {
                Fluid.f_76104_.m_122667_($$1);
            }
        }
    }
}

