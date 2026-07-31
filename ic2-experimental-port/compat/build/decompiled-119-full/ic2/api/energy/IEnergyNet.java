/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.energy;

import ic2.api.energy.IEnergyNetEventReceiver;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IEnergyNet {
    public IEnergyTile getTile(Level var1, BlockPos var2);

    public IEnergyTile getSubTile(Level var1, BlockPos var2);

    public <T extends BlockEntity> void addBlockEntityTile(T var1);

    public <T extends ILocatable & IEnergyTile> void addLocatableTile(T var1);

    default public void addTileUnchecked(IEnergyTile iEnergyTile) {
        if (iEnergyTile instanceof BlockEntity) {
            this.addBlockEntityTile((BlockEntity)iEnergyTile);
        } else if (iEnergyTile instanceof ILocatable) {
            this.addLocatableTile((ILocatable)((Object)iEnergyTile));
        } else {
            throw new IllegalArgumentException("invalid tile type: " + iEnergyTile.getClass().getName());
        }
    }

    public void removeTile(IEnergyTile var1);

    public Level getWorld(IEnergyTile var1);

    public BlockPos getPos(IEnergyTile var1);

    public NodeStats getNodeStats(IEnergyTile var1);

    public int getAdjacentConnections(IEnergyTile var1);

    public double getPowerFromTier(int var1);

    public int getTierFromPower(double var1);

    public void registerEventReceiver(IEnergyNetEventReceiver var1);

    public void unregisterEventReceiver(IEnergyNetEventReceiver var1);
}

