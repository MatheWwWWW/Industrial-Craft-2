/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.container.ContainerFluidRegulator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.Localization;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.LiquidUtil;
import java.util.Collections;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityFluidRegulator
extends TileEntityElectricMachine
implements IHasGui,
INetworkClientTileEntityEventListener {
    private int mode = 0;
    private int updateTicker;
    private int outputmb = 0;
    private boolean newActive = false;
    public final InvSlotOutput wasseroutputSlot;
    public final InvSlotConsumableLiquidByTank wasserinputSlot;
    @GuiSynced
    protected final Fluids.InternalFluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidRegulator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.FLUID_REGULATOR, blockPos, blockState, 10000, 4);
        this.fluidTank = this.fluids.addTank("fluidTank", 10000, InvSlot.Access.NONE);
        this.wasserinputSlot = new InvSlotConsumableLiquidByTank(this, "wasserinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, this.fluidTank);
        this.wasseroutputSlot = new InvSlotOutput(this, "wasseroutputSlot", 1);
        this.updateTicker = IC2.random.m_188503_(this.getTickRate());
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.outputmb = compoundTag.m_128451_("outputmb");
        this.mode = compoundTag.m_128451_("mode");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("outputmb", this.outputmb);
        compoundTag.m_128405_("mode", this.mode);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    protected void setFacing(Level level, Direction direction) {
        super.setFacing(level, direction);
        this.updateConnectivity();
    }

    private void updateConnectivity() {
        this.fluids.changeConnectivity(this.fluidTank, EnumSet.complementOf(EnumSet.of(this.getFacing())), Collections.emptySet());
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.wasserinputSlot.processIntoTank(this.fluidTank, this.wasseroutputSlot);
        if (this.updateTicker++ % this.getTickRate() != 0 && this.mode == 0) {
            return;
        }
        this.newActive = this.work();
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    private boolean work() {
        int n;
        Direction direction;
        if (this.outputmb == 0) {
            return false;
        }
        if (this.energy.getEnergy() < 10.0) {
            return false;
        }
        if (this.fluidTank.getFluidAmount() <= 0) {
            return false;
        }
        Direction direction2 = this.getFacing();
        BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(direction2));
        if (LiquidUtil.isFluidTile(blockEntity, direction = direction2.m_122424_()) && (n = LiquidUtil.fillTile(blockEntity, direction, this.fluidTank.drainMbUnchecked(this.outputmb, true), false)) > 0) {
            this.fluidTank.drainMbUnchecked(this.outputmb, false);
            this.energy.useEnergy(10.0);
            return true;
        }
        return false;
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n == 1001 || n == 1002) {
            if (n == 1001 && this.mode == 0) {
                this.mode = 1;
            }
            if (n == 1002 && this.mode == 1) {
                this.mode = 0;
            }
            return;
        }
        this.outputmb += n;
        if (this.outputmb > 1000) {
            this.outputmb = 1000;
        }
        if (this.outputmb < 0) {
            this.outputmb = 0;
        }
    }

    public int getTickRate() {
        return 20;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerFluidRegulator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerFluidRegulator(n, inventory, this);
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.fluidTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.fluidTank.getFluidAmount() * n / this.fluidTank.getCapacity();
            }
        }
        return 0;
    }

    public int getoutputmb() {
        return this.outputmb;
    }

    public String getmodegui() {
        switch (this.mode) {
            case 0: {
                return Localization.translate("ic2.generic.text.sec");
            }
            case 1: {
                return Localization.translate("ic2.generic.text.tick");
            }
        }
        return "";
    }

    public Ic2FluidTank getFluidTank() {
        return this.fluidTank;
    }
}

