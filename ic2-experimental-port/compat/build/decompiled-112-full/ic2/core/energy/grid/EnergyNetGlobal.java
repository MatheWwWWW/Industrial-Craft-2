/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.energy.grid;

import ic2.api.energy.IEnergyNet;
import ic2.api.energy.IEnergyNetEventReceiver;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import ic2.core.IC2;
import ic2.core.WorldData;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.EventHandler;
import ic2.core.energy.grid.IEnergyCalculator;
import ic2.core.energy.leg.EnergyCalculatorLeg;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EnergyNetGlobal
implements IEnergyNet {
    private static final List<IEnergyNetEventReceiver> eventReceivers = new CopyOnWriteArrayList<IEnergyNetEventReceiver>();
    private static IEnergyCalculator calculator;

    public static EnergyNetGlobal create() {
        if (System.getProperty("IC2ExpEnet") != null) {
            // empty if block
        }
        calculator = new EnergyCalculatorLeg();
        EventHandler.init();
        return new EnergyNetGlobal();
    }

    private EnergyNetGlobal() {
    }

    @Override
    public IEnergyTile getTile(World world, BlockPos pos) {
        if (world == null) {
            throw new NullPointerException("null world");
        }
        if (pos == null) {
            throw new NullPointerException("null pos");
        }
        return EnergyNetGlobal.getLocal(world).getIoTile(pos);
    }

    @Override
    public IEnergyTile getSubTile(World world, BlockPos pos) {
        if (world == null) {
            throw new NullPointerException("null world");
        }
        if (pos == null) {
            throw new NullPointerException("null pos");
        }
        return EnergyNetGlobal.getLocal(world).getSubTile(pos);
    }

    @Override
    public <T extends TileEntity> void addTile(T tile) {
        if (tile == null) {
            throw new NullPointerException("null tile");
        }
        EnergyNetGlobal.addTile((IEnergyTile)tile, tile.func_145831_w(), tile.func_174877_v());
    }

    @Override
    public <T extends ILocatable & IEnergyTile> void addTile(T tile) {
        if (tile == null) {
            throw new NullPointerException("null tile");
        }
        EnergyNetGlobal.addTile(tile, tile.getWorldObj(), tile.getPosition());
    }

    private static void addTile(IEnergyTile tile, World world, BlockPos pos) {
        if (EnergyNetSettings.logEnetApiAccessTraces) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API addTile %s.", Util.toString(tile, (IBlockAccess)world, pos));
        } else if (EnergyNetSettings.logEnetApiAccesses) {
            IC2.log.debug(LogCategory.EnergyNet, "API addTile %s.", Util.toString(tile, (IBlockAccess)world, pos));
        }
        EnergyNetGlobal.getLocal(world).addTile(tile, pos);
    }

    @Override
    public void removeTile(IEnergyTile tile) {
        if (tile == null) {
            throw new NullPointerException("null tile");
        }
        World world = this.getWorld(tile);
        BlockPos pos = this.getPos(tile);
        if (EnergyNetSettings.logEnetApiAccessTraces) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API removeTile %s.", Util.toString(tile, (IBlockAccess)world, pos));
        } else if (EnergyNetSettings.logEnetApiAccesses) {
            IC2.log.debug(LogCategory.EnergyNet, "API removeTile %s.", Util.toString(tile, (IBlockAccess)world, pos));
        }
        EnergyNetGlobal.getLocal(world).removeTile(tile, pos);
    }

    @Override
    public World getWorld(IEnergyTile tile) {
        if (tile == null) {
            throw new NullPointerException("null tile");
        }
        if (tile instanceof ILocatable) {
            return ((ILocatable)((Object)tile)).getWorldObj();
        }
        if (tile instanceof TileEntity) {
            return ((TileEntity)tile).func_145831_w();
        }
        throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
    }

    @Override
    public BlockPos getPos(IEnergyTile tile) {
        if (tile == null) {
            throw new NullPointerException("null tile");
        }
        if (tile instanceof ILocatable) {
            return ((ILocatable)((Object)tile)).getPosition();
        }
        if (tile instanceof TileEntity) {
            return ((TileEntity)tile).func_174877_v();
        }
        throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
    }

    @Override
    public NodeStats getNodeStats(IEnergyTile tile) {
        return EnergyNetGlobal.getLocal(this.getWorld(tile)).getNodeStats(tile);
    }

    @Override
    public boolean dumpDebugInfo(World world, BlockPos pos, PrintStream console, PrintStream chat) {
        return EnergyNetGlobal.getLocal(world).dumpDebugInfo(pos, console, chat);
    }

    @Override
    public double getPowerFromTier(int tier) {
        if (tier < 14) {
            return 8 << tier * 2;
        }
        if (tier < 30) {
            return 8.0 * Math.pow(4.0, tier);
        }
        return 9.223372036854776E18;
    }

    @Override
    public int getTierFromPower(double power) {
        if (power <= 0.0) {
            return 0;
        }
        return (int)Math.ceil(Math.log(power / 8.0) / Math.log(4.0));
    }

    @Override
    public synchronized void registerEventReceiver(IEnergyNetEventReceiver receiver) {
        if (eventReceivers.contains(receiver)) {
            return;
        }
        eventReceivers.add(receiver);
    }

    @Override
    public synchronized void unregisterEventReceiver(IEnergyNetEventReceiver receiver) {
        eventReceivers.remove(receiver);
    }

    static Iterable<IEnergyNetEventReceiver> getEventReceivers() {
        return eventReceivers;
    }

    static IEnergyCalculator getCalculator() {
        return calculator;
    }

    public static EnergyNetLocal getLocal(World world) {
        if (world.field_72995_K) {
            throw new IllegalStateException("not applicable clientside");
        }
        assert (world.func_73046_m().func_152345_ab());
        return WorldData.get((World)world).energyNet;
    }
}

