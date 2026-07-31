/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.FluidName;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntitySteamRepressurizer
extends TileEntityInventory
implements IHasGui {
    protected int currentHeat;
    @GuiSynced
    protected final FluidTank output;
    @GuiSynced
    protected final FluidTank input;
    protected static final int CONSUMPTION = 10;
    public static final Fluid STEAM = FluidRegistry.getFluid((String)"steam");
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntitySteamRepressurizer() {
        this.input = this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate(FluidName.steam.getInstance(), FluidName.superheated_steam.getInstance()));
        this.output = this.fluids.addTankExtract("output", 10000);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.currentHeat = nbt.func_74762_e("heat");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("heat", this.currentHeat);
        return nbt;
    }

    public static boolean hasSteam() {
        return STEAM != null;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (!TileEntitySteamRepressurizer.hasSteam()) {
            return;
        }
        if (this.input.getFluidAmount() >= 10) {
            if (this.currentHeat < this.input.getFluidAmount() / 10) {
                this.getHeat();
            }
            int amount = this.getOutput();
            while (this.currentHeat > 0 && this.input.getFluidAmount() >= 10 && this.canOutput(amount)) {
                --this.currentHeat;
                this.input.drainInternal(10, true);
                this.output.fillInternal(new FluidStack(STEAM, amount), true);
            }
        }
    }

    protected void getHeat() {
        int aim = this.input.getFluidAmount() / 10;
        if (aim > 0) {
            IHeatSource hs;
            int request;
            EnumFacing dir;
            TileEntity target;
            World world = this.func_145831_w();
            int targetHeat = aim;
            EnumFacing[] enumFacingArray = EnumFacing.field_82609_l;
            int n = enumFacingArray.length;
            for (int i = 0; !(i >= n || (target = world.func_175625_s(this.field_174879_c.func_177972_a(dir = enumFacingArray[i]))) instanceof IHeatSource && (request = (hs = (IHeatSource)target).drawHeat(dir.func_176734_d(), targetHeat, true)) > 0 && (targetHeat -= hs.drawHeat(dir.func_176734_d(), request, false)) <= 0); ++i) {
            }
            this.currentHeat += aim - targetHeat;
        }
    }

    protected int getOutput() {
        assert (this.input.getFluid() != null);
        Fluid fluid = this.input.getFluid().getFluid();
        if (fluid == FluidName.steam.getInstance()) {
            return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSteam");
        }
        if (fluid == FluidName.superheated_steam.getInstance()) {
            return ConfigUtil.getInt(MainConfig.get(), "balance/steamRepressurizer/steamPerSuperSteam");
        }
        throw new IllegalStateException("Unknown tank contents: " + fluid);
    }

    protected boolean canOutput(int amount) {
        return this.output.fillInternal(new FluidStack(STEAM, amount), false) == amount;
    }

    public ContainerBase<TileEntitySteamRepressurizer> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public boolean getGuiState(String name) {
        if ("valid".equals(name)) {
            return TileEntitySteamRepressurizer.hasSteam();
        }
        return super.getGuiState(name);
    }
}

