/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
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
import ic2.core.block.machine.gui.GuiFluidRegulator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.init.Localization;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.util.LiquidUtil;
import java.util.Collections;
import java.util.EnumSet;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public TileEntityFluidRegulator() {
        super(10000, 4);
        this.fluidTank = this.fluids.addTank("fluidTank", 10000, InvSlot.Access.NONE);
        this.wasserinputSlot = new InvSlotConsumableLiquidByTank(this, "wasserinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
        this.wasseroutputSlot = new InvSlotOutput(this, "wasseroutputSlot", 1);
        this.updateTicker = IC2.random.nextInt(this.getTickRate());
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.outputmb = nbt.func_74762_e("outputmb");
        this.mode = nbt.func_74762_e("mode");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("outputmb", this.outputmb);
        nbt.func_74768_a("mode", this.mode);
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    public void setFacing(EnumFacing side) {
        super.setFacing(side);
        this.updateConnectivity();
    }

    private void updateConnectivity() {
        this.fluids.changeConnectivity(this.fluidTank, EnumSet.complementOf(EnumSet.of(this.getFacing())), Collections.emptySet());
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.wasserinputSlot.processIntoTank((IFluidTank)this.fluidTank, this.wasseroutputSlot);
        if (this.updateTicker++ % this.getTickRate() != 0 && this.mode == 0) {
            return;
        }
        this.newActive = this.work();
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    private boolean work() {
        int amount;
        EnumFacing side;
        if (this.outputmb == 0) {
            return false;
        }
        if (this.energy.getEnergy() < 10.0) {
            return false;
        }
        if (this.fluidTank.getFluidAmount() <= 0) {
            return false;
        }
        EnumFacing dir = this.getFacing();
        TileEntity te = this.func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
        if (LiquidUtil.isFluidTile(te, side = dir.func_176734_d()) && (amount = LiquidUtil.fillTile(te, side, this.fluidTank.drainInternal(this.outputmb, false), false)) > 0) {
            this.fluidTank.drainInternal(this.outputmb, true);
            this.energy.useEnergy(10.0);
            return true;
        }
        return false;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event == 1001 || event == 1002) {
            if (event == 1001 && this.mode == 0) {
                this.mode = 1;
            }
            if (event == 1002 && this.mode == 1) {
                this.mode = 0;
            }
            return;
        }
        this.outputmb += event;
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

    public ContainerBase<TileEntityFluidRegulator> getGuiContainer(EntityPlayer player) {
        return new ContainerFluidRegulator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiFluidRegulator(new ContainerFluidRegulator(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public int gaugeLiquidScaled(int i, int tank) {
        switch (tank) {
            case 0: {
                if (this.fluidTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.fluidTank.getFluidAmount() * i / this.fluidTank.getCapacity();
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

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }
}

