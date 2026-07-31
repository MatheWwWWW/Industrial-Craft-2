package ru.mot.ic2exfidelity.integration;

import ic2.api.transport.IPipe;
import ic2.core.util.RotationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/** Shared hit-side selection and mirrored connection change for both IC2 wrenches. */
public final class PipeWrenchCompat {
    private PipeWrenchCompat() {
    }

    public static boolean isPipe(UseOnContext context) {
        return context.m_43725_().m_7702_(context.m_8083_()) instanceof IPipe;
    }

    public static void flip(UseOnContext context) {
        Level level = context.m_43725_();
        BlockPos pos = context.m_8083_();
        BlockEntity tile = level.m_7702_(pos);
        if (!(tile instanceof IPipe pipe)) {
            return;
        }
        Vec3 hit = context.m_43720_();
        Direction side = RotationUtil.rotateByHit(
                context.m_43719_(),
                (float) (hit.m_7096_() - pos.m_123341_()),
                (float) (hit.m_7098_() - pos.m_123342_()),
                (float) (hit.m_7094_() - pos.m_123343_()));
        pipe.flipConnection(side);
        BlockEntity neighbor = level.m_7702_(pos.m_121945_(side));
        if (neighbor instanceof IPipe adjacent
                && adjacent.isConnected(side.m_122424_()) != pipe.isConnected(side)) {
            adjacent.flipConnection(side.m_122424_());
        }
    }
}
