/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockFarmland
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerCropmatron;
import ic2.core.block.machine.gui.GuiCropmatron;
import ic2.core.block.machine.tileentity.TileEntityClassicCropmatron;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.crop.TileEntityCrop;
import ic2.core.item.type.CropResItemType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TeBlock.Delegated(current=TileEntityCropmatron.class, old=TileEntityClassicCropmatron.class)
public class TileEntityCropmatron
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    public final InvSlotUpgrade upgradeSlot;
    public int scanX = -4;
    public int scanY = -1;
    public int scanZ = -4;
    public final InvSlotConsumable fertilizerSlot;
    public final InvSlotOutput wasseroutputSlot;
    public final InvSlotOutput exOutputSlot;
    public final InvSlotConsumableLiquidByTank wasserinputSlot;
    public final InvSlotConsumableLiquidByTank exInputSlot;
    protected final FluidTank waterTank;
    protected final FluidTank exTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public static Class<? extends TileEntityElectricMachine> delegate() {
        return IC2.version.isClassic() ? TileEntityClassicCropmatron.class : TileEntityCropmatron.class;
    }

    public TileEntityCropmatron() {
        super(10000, 1);
        this.waterTank = this.fluids.addTankInsert("waterTank", 2000, Fluids.fluidPredicate(FluidRegistry.WATER));
        this.exTank = this.fluids.addTankInsert("exTank", 2000, Fluids.fluidPredicate(FluidName.weed_ex.getInstance()));
        this.fertilizerSlot = new InvSlotConsumableItemStack(this, "fertilizer", 7, ItemName.crop_res.getItemStack(CropResItemType.fertilizer));
        this.wasserinputSlot = new InvSlotConsumableLiquidByTank(this, "wasserinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.waterTank);
        this.exInputSlot = new InvSlotConsumableLiquidByTank(this, "exInputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.exTank);
        this.wasseroutputSlot = new InvSlotOutput(this, "wasseroutputSlot", 1);
        this.exOutputSlot = new InvSlotOutput(this, "exOutputSlot", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.upgradeSlot.tick();
        this.wasserinputSlot.processIntoTank((IFluidTank)this.waterTank, this.wasseroutputSlot);
        this.exInputSlot.processIntoTank((IFluidTank)this.exTank, this.exOutputSlot);
        this.fertilizerSlot.organize();
        if (this.field_145850_b.func_82737_E() % 10L == 0L && this.energy.getEnergy() >= 31.0) {
            this.scan();
        }
    }

    public void scan() {
        ++this.scanX;
        if (this.scanX > 4) {
            this.scanX = -4;
            ++this.scanZ;
            if (this.scanZ > 4) {
                this.scanZ = -4;
                ++this.scanY;
                if (this.scanY > 1) {
                    this.scanY = -1;
                }
            }
        }
        this.energy.useEnergy(1.0);
        BlockPos scan = this.field_174879_c.func_177982_a(this.scanX, this.scanY, this.scanZ);
        TileEntity te = this.func_145831_w().func_175625_s(scan);
        if (te instanceof TileEntityCrop) {
            TileEntityCrop crop = (TileEntityCrop)te;
            if (!this.fertilizerSlot.isEmpty() && this.fertilizerSlot.consume(1, true, false) != null && crop.applyFertilizer(false)) {
                this.energy.useEnergy(10.0);
                this.fertilizerSlot.consume(1);
            }
            if (this.waterTank.getFluidAmount() > 0 && crop.applyHydration((IFluidHandler)this.getWaterTank())) {
                this.energy.useEnergy(10.0);
            }
            if (this.exTank.getFluidAmount() > 0 && crop.applyWeedEx((IFluidHandler)this.getExTank(), false)) {
                this.energy.useEnergy(10.0);
            }
        } else if (this.waterTank.getFluidAmount() > 0 && this.tryHydrateFarmland(scan)) {
            this.energy.useEnergy(10.0);
        }
    }

    private boolean tryHydrateFarmland(BlockPos pos) {
        int hydration;
        World world = this.func_145831_w();
        IBlockState state = world.func_180495_p(pos);
        if (state.func_177230_c() == Blocks.field_150458_ak && (hydration = ((Integer)state.func_177229_b((IProperty)BlockFarmland.field_176531_a)).intValue()) < 7) {
            int drainAmount = Math.min(this.waterTank.getFluidAmount(), 7 - hydration);
            assert (drainAmount > 0);
            assert (drainAmount <= 7);
            this.waterTank.drainInternal(drainAmount, true);
            world.func_180501_a(pos, state.func_177226_a((IProperty)BlockFarmland.field_176531_a, (Comparable)Integer.valueOf(hydration + drainAmount)), 2);
            return true;
        }
        return false;
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.FluidConsuming);
    }

    public ContainerBase<TileEntityCropmatron> getGuiContainer(EntityPlayer player) {
        return new ContainerCropmatron(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiCropmatron(new ContainerCropmatron(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public FluidTank getWaterTank() {
        return this.waterTank;
    }

    public FluidTank getExTank() {
        return this.exTank;
    }
}

