package ru.mot.ic2exfidelity.gravisuit;

import java.util.ArrayList;
import java.util.List;
import ic2.core.IC2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

/** Gravisuit 2.2 screwdriver rotation including neighbour validation and chest guard. */
public final class LegacyGravisuitRotationHelper {
    private static final List<RotationBlacklistEntry> BLACKLIST = new ArrayList<>();

    static {
        BLACKLIST.add((level, position) -> {
            BlockState state = level.m_8055_(position);
            return state.m_60734_() != Blocks.f_50087_
                    || state.m_61143_(ChestBlock.f_51479_) == ChestType.SINGLE;
        });
    }

    private LegacyGravisuitRotationHelper() {
    }

    public static boolean rotateBlock(Level level, BlockPos position, boolean inverse) {
        return rotateBlock(
                level,
                position,
                inverse ? Rotation.COUNTERCLOCKWISE_90 : Rotation.CLOCKWISE_90);
    }

    public static boolean rotateBlock(Level level, BlockPos position, Rotation rotation) {
        for (RotationBlacklistEntry entry : BLACKLIST) {
            if (!entry.blockRotation(level, position)) {
                return false;
            }
        }

        BlockState original = level.m_8055_(position);
        BlockState rotated = IC2.envProxy.rotate(original, level, position, rotation);
        if (rotated == original) {
            return false;
        }
        level.m_46597_(position, rotated);

        for (Direction direction : Direction.values()) {
            BlockPos neighbourPosition = position.m_121945_(direction);
            BlockState neighbour = level.m_8055_(neighbourPosition);
            BlockState updated = rotated.m_60728_(
                    direction, neighbour, level, position, neighbourPosition);
            if (updated == rotated) {
                continue;
            }
            if (updated.m_60795_()) {
                level.m_46597_(position, original);
                return false;
            }
            level.m_46597_(position, updated);
            rotated = updated;
        }

        for (Direction direction : Direction.values()) {
            BlockPos neighbourPosition = position.m_121945_(direction);
            BlockState neighbour = level.m_8055_(neighbourPosition);
            BlockState updated = neighbour.m_60728_(
                    direction.m_122424_(), rotated, level, neighbourPosition, position);
            if (updated != neighbour) {
                level.m_46597_(neighbourPosition, updated);
            }
        }
        return true;
    }

    @FunctionalInterface
    private interface RotationBlacklistEntry {
        boolean blockRotation(Level level, BlockPos position);
    }
}
