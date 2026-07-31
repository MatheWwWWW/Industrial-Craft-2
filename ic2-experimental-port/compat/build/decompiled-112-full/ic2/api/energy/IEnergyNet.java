/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.energy;

import ic2.api.energy.IEnergyNetEventReceiver;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import java.io.PrintStream;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnergyNet {
    public IEnergyTile getTile(World var1, BlockPos var2);

    public IEnergyTile getSubTile(World var1, BlockPos var2);

    public <T extends TileEntity> void addTile(T var1);

    public <T extends ILocatable & IEnergyTile> void addTile(T var1);

    public void removeTile(IEnergyTile var1);

    public World getWorld(IEnergyTile var1);

    public BlockPos getPos(IEnergyTile var1);

    public NodeStats getNodeStats(IEnergyTile var1);

    public boolean dumpDebugInfo(World var1, BlockPos var2, PrintStream var3, PrintStream var4);

    public double getPowerFromTier(int var1);

    public int getTierFromPower(double var1);

    public void registerEventReceiver(IEnergyNetEventReceiver var1);

    public void unregisterEventReceiver(IEnergyNetEventReceiver var1);
}

