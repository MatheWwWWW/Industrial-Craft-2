/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.machine.container.ContainerFluidDistributor;
import ic2.core.block.machine.gui.GuiFluidDistributor;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.util.LiquidUtil;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityFluidDistributor
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener {
    public final InvSlotConsumableLiquidByTank inputSlot;
    public final InvSlotOutput OutputSlot;
    @GuiSynced
    public final Fluids.InternalFluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityFluidDistributor() {
        this.fluidTank = this.fluids.addTank("fluidTank", 1000);
        this.inputSlot = new InvSlotConsumableLiquidByTank(this, "inputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.fluidTank);
        this.OutputSlot = new InvSlotOutput(this, "OutputSlot", 1);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    public void setActive(boolean val) {
        super.setActive(val);
        this.updateConnectivity();
    }

    @Override
    public void setFacing(EnumFacing facing) {
        super.setFacing(facing);
        this.updateConnectivity();
    }

    protected void updateConnectivity() {
        EnumSet<EnumFacing> acceptingSides = EnumSet.of(this.getFacing());
        if (this.getActive()) {
            acceptingSides = EnumSet.complementOf(acceptingSides);
        }
        this.fluids.changeConnectivity(this.fluidTank, acceptingSides, Collections.emptySet());
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.inputSlot.processFromTank((IFluidTank)this.fluidTank, this.OutputSlot);
        if (this.fluidTank.getFluidAmount() > 0) {
            this.moveFluid();
        }
    }

    protected void moveFluid() {
        block6: {
            int amount;
            World world;
            block5: {
                int amount2;
                EnumFacing side;
                world = this.func_145831_w();
                if (!this.getActive()) break block5;
                TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(this.getFacing()));
                if (!LiquidUtil.isFluidTile(target, side = this.getFacing().func_176734_d()) || (amount2 = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), false)) <= 0) break block6;
                this.fluidTank.drainInternal(amount2, true);
                break block6;
            }
            EnumMap<EnumFacing, TileEntity> acceptingNeighbors = new EnumMap<EnumFacing, TileEntity>(EnumFacing.class);
            int acceptedVolume = 0;
            for (EnumFacing dir : EnumFacing.field_82609_l) {
                int amount3;
                EnumFacing side;
                TileEntity target;
                if (dir == this.getFacing() || !LiquidUtil.isFluidTile(target = world.func_175625_s(this.field_174879_c.func_177972_a(dir)), side = dir.func_176734_d()) || (amount3 = LiquidUtil.fillTile(target, side, this.fluidTank.getFluid(), true)) <= 0) continue;
                acceptingNeighbors.put(dir, target);
                acceptedVolume += amount3;
            }
            block1: while (!acceptingNeighbors.isEmpty() && (amount = Math.min(acceptedVolume, this.fluidTank.getFluidAmount())) > 0) {
                int cAmount;
                FluidStack fs;
                EnumFacing side;
                TileEntity target;
                if ((amount /= acceptingNeighbors.size()) > 0) {
                    Iterator it = acceptingNeighbors.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        target = (TileEntity)entry.getValue();
                        side = ((EnumFacing)entry.getKey()).func_176734_d();
                        fs = this.fluidTank.getFluid();
                        if (fs == null) continue block1;
                        fs = fs.copy();
                        if (fs.amount <= 0) continue block1;
                        fs.amount = Math.min(amount, fs.amount);
                        cAmount = LiquidUtil.fillTile(target, side, fs, false);
                        this.fluidTank.drainInternal(cAmount, true);
                        acceptedVolume -= cAmount;
                        if (cAmount >= fs.amount) continue;
                        it.remove();
                    }
                    continue;
                }
                for (Map.Entry entry : acceptingNeighbors.entrySet()) {
                    target = (TileEntity)entry.getValue();
                    side = ((EnumFacing)entry.getKey()).func_176734_d();
                    fs = this.fluidTank.getFluid();
                    if (fs == null) break block1;
                    fs = fs.copy();
                    fs.amount = Math.min(acceptedVolume, fs.amount);
                    if (fs.amount <= 0) break block1;
                    cAmount = LiquidUtil.fillTile(target, side, fs, false);
                    this.fluidTank.drainInternal(cAmount, true);
                    acceptedVolume -= cAmount;
                }
                break;
            }
        }
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        this.setActive(!this.getActive());
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerFluidDistributor(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiFluidDistributor(new ContainerFluidDistributor(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }
}

