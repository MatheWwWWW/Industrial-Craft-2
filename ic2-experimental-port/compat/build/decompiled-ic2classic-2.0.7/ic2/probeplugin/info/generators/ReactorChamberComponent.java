/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 */
package ic2.probeplugin.info.generators;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.info.generators.NuclearReactorComponent;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class ReactorChamberComponent
implements ITileInfoComponent<IReactorChamber> {
    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, IReactorChamber tile) {
        IReactor reactor = tile.getReactor();
        if (reactor != null) {
            NuclearReactorComponent.INSTANCE.addInfo(info, player, dir, reactor);
        }
    }
}

