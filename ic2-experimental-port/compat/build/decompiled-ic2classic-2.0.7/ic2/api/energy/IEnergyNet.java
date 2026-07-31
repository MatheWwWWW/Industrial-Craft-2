/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 */
package ic2.api.energy;

import ic2.api.energy.PacketStats;
import ic2.api.energy.TransferStats;
import ic2.api.energy.tile.IEnergyTile;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IEnergyNet {
    public IEnergyTile getTile(Level var1, BlockPos var2);

    public IEnergyTile getSubTile(Level var1, BlockPos var2);

    public GridTile getTiles(Level var1, BlockPos var2);

    public void addTile(IEnergyTile var1);

    public void removeTile(IEnergyTile var1);

    public void updateTile(IEnergyTile var1);

    public int getPowerFromTier(int var1);

    public int getTierFromPower(int var1);

    public String getDisplayTier(int var1);

    public TransferStats getStats(IEnergyTile var1);

    public List<PacketStats> getPacketStats(IEnergyTile var1);

    public static class GridTile {
        IEnergyTile mainTile;
        IEnergyTile subTile;

        public GridTile(IEnergyTile mainTile, IEnergyTile subTile) {
            this.mainTile = mainTile;
            this.subTile = subTile;
        }

        public IEnergyTile getMainTile() {
            return this.mainTile;
        }

        public IEnergyTile getSubTile() {
            return this.subTile;
        }

        public BlockPos getPos() {
            return this.subTile.getPosition();
        }

        public Level getWorld() {
            return this.subTile.getWorldObj();
        }
    }
}

