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
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.ExplosionIC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.machine.container.ContainerSteamGenerator;
import ic2.core.block.machine.gui.GuiSteamGenerator;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.profile.NotClassic;
import ic2.core.ref.FluidName;
import ic2.core.util.BiomeUtil;
import ic2.core.util.LiquidUtil;
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
public class TileEntitySteamGenerator
extends TileEntityInventory
implements IHasGui,
IGuiValueProvider,
INetworkClientTileEntityEventListener {
    private static final float maxHeat = 500.0f;
    private static final float heatPerHu = 5.0E-4f;
    private static final float coolingPerMb = 0.1f;
    private static final float maxCooling = 2.0f;
    private static final int maxHuInput = 1200;
    private static final int maxCalcification = 100000;
    private static final int steamExpansion = 100;
    private static final float epsilon = 1.0E-4f;
    private int heatInput = 0;
    private int inputMB = 0;
    public final FluidTank waterTank;
    private int calcification = 0;
    private int outputMB = 0;
    private outputType outputFluid = outputType.NONE;
    private float systemHeat;
    private int pressure = 0;
    private boolean newActive = false;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntitySteamGenerator() {
        this.waterTank = this.fluids.addTankInsert("waterTank", 10000, Fluids.fluidPredicate(FluidRegistry.WATER, FluidName.distilled_water.getInstance()));
    }

    @Override
    public void func_145839_a(NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.inputMB = nbttagcompound.func_74762_e("inputmb");
        this.pressure = nbttagcompound.func_74762_e("pressurevalve");
        this.systemHeat = nbttagcompound.func_74760_g("systemheat");
        this.calcification = nbttagcompound.func_74762_e("calcification");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("inputmb", this.inputMB);
        nbt.func_74768_a("pressurevalve", this.pressure);
        nbt.func_74776_a("systemheat", this.systemHeat);
        nbt.func_74768_a("calcification", this.calcification);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.systemHeat = Math.max(this.systemHeat, (float)BiomeUtil.getBiomeTemperature(this.func_145831_w(), this.field_174879_c));
        if (this.isCalcified()) {
            if (this.getActive()) {
                this.setActive(false);
            }
        } else {
            this.newActive = this.work();
            if (this.getActive() != this.newActive) {
                this.setActive(this.newActive);
            }
        }
        if (!this.getActive()) {
            this.cooldown(0.01f);
        }
    }

    private boolean work() {
        Fluid output;
        int activeAmount;
        this.heatInput = this.requestHeat(1200);
        if (this.heatInput <= 0) {
            return false;
        }
        assert (this.heatInput <= 1200);
        this.outputMB = 0;
        this.outputFluid = outputType.NONE;
        if (this.waterTank.getFluid() == null || this.waterTank.getFluidAmount() <= 0 || this.inputMB <= 0) {
            this.heatup(this.heatInput);
            return true;
        }
        Fluid inputFluid = this.waterTank.getFluid().getFluid();
        boolean hasDistilledWater = inputFluid == FluidName.distilled_water.getInstance();
        int maxAmount = Math.min(this.inputMB, this.waterTank.getFluidAmount());
        float hUneeded = 100.0f + (float)this.pressure / 220.0f * 100.0f;
        float targetTemp = 100.0f + (float)this.pressure / 220.0f * 100.0f * 2.74f;
        float reqHeat = targetTemp - this.systemHeat;
        float remainingHuInput = this.heatInput;
        if (reqHeat > 1.0E-4f) {
            int heatReq = (int)Math.ceil(reqHeat / 5.0E-4f);
            if (this.heatInput <= heatReq) {
                this.heatup(this.heatInput);
                if (this.pressure == 0 && this.systemHeat < 99.9999f) {
                    this.outputMB = maxAmount;
                    this.outputFluid = hasDistilledWater ? outputType.DISTILLEDWATER : outputType.WATER;
                    int transferred = LiquidUtil.distribute(this, new FluidStack(inputFluid, maxAmount), false);
                    if (transferred > 0) {
                        this.waterTank.drainInternal(transferred, true);
                    }
                }
                return true;
            }
            this.heatup(heatReq);
            remainingHuInput -= (float)heatReq;
            reqHeat = targetTemp - this.systemHeat;
        }
        assert (this.systemHeat >= targetTemp - 1.0E-4f);
        assert (this.systemHeat >= 99.9999f);
        float availableSystemHu = Math.min(-reqHeat / 5.0E-4f, (float)(1200 - this.heatInput));
        int totalAmount = activeAmount = Math.min(maxAmount, (int)((remainingHuInput + availableSystemHu) / hUneeded));
        if ((remainingHuInput -= (float)activeAmount * hUneeded) < 0.0f) {
            this.cooldown(-remainingHuInput * 5.0E-4f);
            reqHeat = targetTemp - this.systemHeat;
        }
        if (reqHeat <= -0.1001f) {
            int coolingAmount = Math.min(maxAmount, (int)(-reqHeat / 0.1f));
            coolingAmount = Math.min(coolingAmount, (int)Math.ceil(20.0));
            assert (coolingAmount >= 0);
            this.cooldown((float)coolingAmount * 0.1f);
            totalAmount = Math.max(activeAmount, coolingAmount);
        }
        if (remainingHuInput > 0.0f) {
            this.heatup(remainingHuInput);
        }
        if (totalAmount <= 0) {
            return true;
        }
        if (!hasDistilledWater) {
            this.calcification += totalAmount;
        }
        this.waterTank.drainInternal(totalAmount, true);
        if (activeAmount <= 0) {
            return true;
        }
        this.outputMB = activeAmount * 100;
        if (this.systemHeat >= 373.9999f) {
            output = FluidName.superheated_steam.getInstance();
            this.outputFluid = outputType.SUPERHEATEDSTEAM;
        } else {
            output = FluidName.steam.getInstance();
            this.outputFluid = outputType.STEAM;
        }
        int transferred = LiquidUtil.distribute(this, new FluidStack(output, this.outputMB), false);
        int remaining = this.outputMB - transferred;
        if (remaining > 0) {
            World world = this.func_145831_w();
            if (world.field_73012_v.nextInt(10) == 0) {
                new ExplosionIC2(world, null, this.field_174879_c, 1, 1.0f, ExplosionIC2.Type.Heat).doExplosion();
            } else if (remaining >= 100) {
                this.waterTank.fillInternal(new FluidStack(inputFluid, remaining / 100), true);
            }
        }
        return true;
    }

    private void heatup(float heatinput) {
        assert (heatinput >= -1.0E-4f);
        this.systemHeat += heatinput * 5.0E-4f;
        if (this.systemHeat > 500.0f) {
            World world = this.func_145831_w();
            world.func_175698_g(this.field_174879_c);
            new ExplosionIC2(world, null, this.field_174879_c, 10, 0.01f, ExplosionIC2.Type.Heat).doExplosion();
        }
    }

    private void cooldown(float cool) {
        assert (cool >= -1.0E-4f);
        this.systemHeat = Math.max(this.systemHeat - cool, (float)BiomeUtil.getBiomeTemperature(this.func_145831_w(), this.field_174879_c));
    }

    private int requestHeat(int requestHeat) {
        World world = this.func_145831_w();
        int targetHeat = requestHeat;
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            IHeatSource hs;
            int request;
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof IHeatSource) || (request = (hs = (IHeatSource)target).drawHeat(dir.func_176734_d(), targetHeat, true)) <= 0 || (targetHeat -= hs.drawHeat(dir.func_176734_d(), request, false)) != 0) continue;
            return requestHeat;
        }
        return requestHeat - targetHeat;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event > 2000 || event < -2000) {
            if (event > 2000) {
                this.pressure = Math.min(this.pressure + (event - 2000), 300);
            }
            if (event < -2000) {
                this.pressure = Math.max(this.pressure + (event + 2000), 0);
            }
        } else {
            this.inputMB = Math.max(Math.min(this.inputMB + event, 1000), 0);
        }
    }

    public int gaugeLiquidScaled(int i, int tank) {
        if (tank == 0) {
            if (this.waterTank.getFluidAmount() <= 0) {
                return 0;
            }
            return this.waterTank.getFluidAmount() * i / this.waterTank.getCapacity();
        }
        return 0;
    }

    public ContainerBase<TileEntitySteamGenerator> getGuiContainer(EntityPlayer player) {
        return new ContainerSteamGenerator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiSteamGenerator(new ContainerSteamGenerator(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getGuiValue(String name) {
        if ("heat".equals(name)) {
            return this.systemHeat == 0.0f ? 0.0 : (double)this.systemHeat / 500.0;
        }
        if ("calcification".equals(name)) {
            return this.calcification == 0 ? 0.0 : (double)this.calcification / 100000.0;
        }
        throw new IllegalArgumentException();
    }

    public int getOutputMB() {
        return this.outputMB;
    }

    public int getInputMB() {
        return this.inputMB;
    }

    public int getHeatInput() {
        return this.heatInput;
    }

    public int getPressure() {
        return this.pressure;
    }

    public float getSystemHeat() {
        return (float)Math.round(this.systemHeat * 10.0f) / 10.0f;
    }

    public float getCalcification() {
        return (float)Math.round((float)this.calcification / 100000.0f * 100.0f * 100.0f) / 100.0f;
    }

    public boolean isCalcified() {
        return this.calcification >= 100000;
    }

    public String getOutputFluidName() {
        return this.outputFluid.getName();
    }

    private static enum outputType {
        NONE(""),
        WATER("ic2.SteamGenerator.output.water"),
        DISTILLEDWATER("ic2.SteamGenerator.output.destiwater"),
        STEAM("ic2.SteamGenerator.output.steam"),
        SUPERHEATEDSTEAM("ic2.SteamGenerator.output.hotsteam");

        private final String name;

        private outputType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

