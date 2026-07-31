/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeConfig
 *  mcjty.theoneprobe.api.IProbeHitData
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.ProbeMode
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.probeplugin.base;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IProbeModifier {
    default public void modifyData(ProbeMode probe, IProbeInfo info, Player player, Level Level2, BlockState state, IProbeHitData hitData) {
    }

    default public boolean hasOverrideStandardInfo() {
        return false;
    }

    default public void overrideStandardInfo(IProbeConfig config, ProbeMode mode, IProbeInfo probeInfo, BlockState blockState, Block block, Level Level2, BlockPos pos, Player player, IProbeHitData data) {
    }
}

