/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.energy.grid;

import ic2.api.energy.IEnergyNet;
import ic2.api.energy.IEnergyNetEventReceiver;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import ic2.core.IC2;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.IEnergyCalculator;
import ic2.core.energy.leg.EnergyCalculatorLeg;
import ic2.core.event.WorldData;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnergyNetGlobal
implements IEnergyNet {
    private static final List<IEnergyNetEventReceiver> eventReceivers = new CopyOnWriteArrayList<IEnergyNetEventReceiver>();
    private static IEnergyCalculator calculator;

    public static EnergyNetGlobal create() {
        if (System.getProperty("IC2ExpEnet") != null) {
            // empty if block
        }
        calculator = new EnergyCalculatorLeg();
        return new EnergyNetGlobal();
    }

    private EnergyNetGlobal() {
    }

    @Override
    public IEnergyTile getTile(Level level, BlockPos blockPos) {
        if (level == null) {
            throw new NullPointerException("null world");
        }
        if (blockPos == null) {
            throw new NullPointerException("null pos");
        }
        return EnergyNetGlobal.getLocal(level).getIoTile(blockPos);
    }

    @Override
    public IEnergyTile getSubTile(Level level, BlockPos blockPos) {
        if (level == null) {
            throw new NullPointerException("null world");
        }
        if (blockPos == null) {
            throw new NullPointerException("null pos");
        }
        return EnergyNetGlobal.getLocal(level).getSubTile(blockPos);
    }

    @Override
    public <T extends BlockEntity> void addBlockEntityTile(T t) {
        if (t == null) {
            throw new NullPointerException("null tile");
        }
        EnergyNetGlobal.addTile((IEnergyTile)t, t.m_58904_(), t.m_58899_());
    }

    @Override
    public <T extends ILocatable & IEnergyTile> void addLocatableTile(T t) {
        if (t == null) {
            throw new NullPointerException("null tile");
        }
        EnergyNetGlobal.addTile(t, t.getWorldObj(), t.getPosition());
    }

    private static void addTile(IEnergyTile iEnergyTile, Level level, BlockPos blockPos) {
        if (EnergyNetSettings.logEnetApiAccessTraces) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API addTile %s.", Util.toString(iEnergyTile, (BlockGetter)level, blockPos));
        } else if (EnergyNetSettings.logEnetApiAccesses) {
            IC2.log.debug(LogCategory.EnergyNet, "API addTile %s.", Util.toString(iEnergyTile, (BlockGetter)level, blockPos));
        }
        EnergyNetGlobal.getLocal(level).addTile(iEnergyTile, blockPos);
    }

    @Override
    public void removeTile(IEnergyTile iEnergyTile) {
        if (iEnergyTile == null) {
            throw new NullPointerException("null tile");
        }
        Level level = this.getWorld(iEnergyTile);
        BlockPos blockPos = this.getPos(iEnergyTile);
        if (EnergyNetSettings.logEnetApiAccessTraces) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API removeTile %s.", Util.toString(iEnergyTile, (BlockGetter)level, blockPos));
        } else if (EnergyNetSettings.logEnetApiAccesses) {
            IC2.log.debug(LogCategory.EnergyNet, "API removeTile %s.", Util.toString(iEnergyTile, (BlockGetter)level, blockPos));
        }
        EnergyNetGlobal.getLocal(level).removeTile(iEnergyTile, blockPos);
    }

    @Override
    public Level getWorld(IEnergyTile iEnergyTile) {
        if (iEnergyTile == null) {
            throw new NullPointerException("null tile");
        }
        if (iEnergyTile instanceof ILocatable) {
            return ((ILocatable)((Object)iEnergyTile)).getWorldObj();
        }
        if (iEnergyTile instanceof BlockEntity) {
            return ((BlockEntity)iEnergyTile).m_58904_();
        }
        throw new UnsupportedOperationException("unlocatable tile type: " + iEnergyTile.getClass().getName());
    }

    @Override
    public BlockPos getPos(IEnergyTile iEnergyTile) {
        if (iEnergyTile == null) {
            throw new NullPointerException("null tile");
        }
        if (iEnergyTile instanceof ILocatable) {
            return ((ILocatable)((Object)iEnergyTile)).getPosition();
        }
        if (iEnergyTile instanceof BlockEntity) {
            return ((BlockEntity)iEnergyTile).m_58899_();
        }
        throw new UnsupportedOperationException("unlocatable tile type: " + iEnergyTile.getClass().getName());
    }

    @Override
    public NodeStats getNodeStats(IEnergyTile iEnergyTile) {
        return EnergyNetGlobal.getLocal(this.getWorld(iEnergyTile)).getNodeStats(iEnergyTile);
    }

    @Override
    public int getAdjacentConnections(IEnergyTile iEnergyTile) {
        return EnergyNetGlobal.getLocal(this.getWorld(iEnergyTile)).getAdjacentConnections(iEnergyTile);
    }

    public boolean dumpDebugInfo(Level level, BlockPos blockPos, PrintStream printStream, PrintStream printStream2) {
        return EnergyNetGlobal.getLocal(level).dumpDebugInfo(blockPos, printStream, printStream2);
    }

    @Override
    public double getPowerFromTier(int n) {
        if (n < 14) {
            return 8 << n * 2;
        }
        if (n < 30) {
            return 8.0 * Math.pow(4.0, n);
        }
        return 9.223372036854776E18;
    }

    @Override
    public int getTierFromPower(double d) {
        if (d <= 0.0) {
            return 0;
        }
        return (int)Math.ceil(Math.log(d / 8.0) / Math.log(4.0));
    }

    @Override
    public synchronized void registerEventReceiver(IEnergyNetEventReceiver iEnergyNetEventReceiver) {
        if (eventReceivers.contains(iEnergyNetEventReceiver)) {
            return;
        }
        eventReceivers.add(iEnergyNetEventReceiver);
    }

    @Override
    public synchronized void unregisterEventReceiver(IEnergyNetEventReceiver iEnergyNetEventReceiver) {
        eventReceivers.remove(iEnergyNetEventReceiver);
    }

    static Iterable<IEnergyNetEventReceiver> getEventReceivers() {
        return eventReceivers;
    }

    static IEnergyCalculator getCalculator() {
        return calculator;
    }

    public static EnergyNetLocal getLocal(Level level) {
        if (level.f_46443_) {
            throw new IllegalStateException("not applicable clientside");
        }
        assert (level.m_7654_().m_18695_());
        return WorldData.get((Level)level).energyNet;
    }
}

