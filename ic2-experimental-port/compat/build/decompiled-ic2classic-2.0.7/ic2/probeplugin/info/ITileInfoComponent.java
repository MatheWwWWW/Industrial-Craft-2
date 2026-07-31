/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.ProbeMode
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.probeplugin.info;

import ic2.core.platform.player.PlayerHandler;
import ic2.core.utils.tooltips.ILangHelper;
import java.util.Set;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ITileInfoComponent<T>
extends ILangHelper {
    default public boolean isValid(ProbeMode mode, PlayerHandler handler) {
        return this.hasValidReader(handler);
    }

    default public boolean hasValidReader(PlayerHandler handler) {
        return handler.hasEUReader();
    }

    default public void disableClasses(Set<Class<?>> toCheck) {
    }

    default public void addTileInfo(IProbeInfo info, Player player, Direction dir, BlockEntity tile) {
        this.addInfo(info, player, dir, tile);
    }

    public void addInfo(IProbeInfo var1, Player var2, Direction var3, T var4);
}

