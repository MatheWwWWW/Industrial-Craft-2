/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core.block.generator.tileentity;

import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityBaseRotorGenerator;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.util.ConfigUtil;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class TileEntityWaterGenerator
extends TileEntityBaseRotorGenerator
implements IGuiValueProvider {
    private static final double energyMultiplier = ConfigUtil.getDouble(MainConfig.get(), "balance/energy/generator/water");
    private static final boolean allowAutomation = ConfigUtil.getBool(MainConfig.get(), "balance/watermillAutomation");
    public final InvSlotConsumableLiquid fuelSlot;
    private static final int tickRate = 128;
    private int ticker = IC2.random.nextInt(128);
    @GuiSynced
    public int water = 0;
    public int microStorage = 0;
    public int maxWater = 2000;

    public TileEntityWaterGenerator() {
        super(2.0, 1, 4, 2);
        this.production = 2.0;
        this.fuelSlot = new InvSlotConsumableLiquidByList(this, "fuel", allowAutomation ? InvSlot.Access.IO : InvSlot.Access.NONE, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, FluidRegistry.WATER);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateWaterCount();
    }

    @Override
    public boolean gainFuel() {
        if (this.fuel + 500 > this.maxWater) {
            return false;
        }
        if (!this.fuelSlot.isEmpty()) {
            ItemStack liquid = this.fuelSlot.consume(1);
            if (liquid == null) {
                return false;
            }
            this.fuel += 500;
            this.production = liquid.func_77973_b().hasContainerItem(liquid) ? 1.0 : 2.0;
            return true;
        }
        if (this.fuel <= 0) {
            this.flowPower();
            this.production = this.microStorage / 100;
            this.microStorage = (int)((double)this.microStorage - this.production * 100.0);
            if (this.production > 0.0) {
                ++this.fuel;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isConverting() {
        return this.fuel > 0;
    }

    @Override
    public boolean needsFuel() {
        return this.fuel <= this.maxWater;
    }

    public void flowPower() {
        if (++this.ticker % 128 == 0) {
            this.updateWaterCount();
        }
        this.water = (int)Math.round((double)this.water * energyMultiplier);
        if (this.water > 0) {
            this.microStorage += this.water;
        }
    }

    public void updateWaterCount() {
        World world = this.func_145831_w();
        int count = 0;
        for (int x = -1; x < 2; ++x) {
            for (int y = -1; y < 2; ++y) {
                for (int z = -1; z < 2; ++z) {
                    if (world.func_180495_p(this.field_174879_c.func_177982_a(x, y, z)).func_185904_a() != Material.field_151586_h) continue;
                    ++count;
                }
            }
        }
        this.water = count;
    }

    @Override
    public String getOperationSoundFile() {
        return "Generators/WatermillLoop.ogg";
    }

    @Override
    protected boolean delayActiveUpdate() {
        return true;
    }

    @Override
    protected boolean shouldRotorRotate() {
        return this.water > 0 || this.fuel > 0;
    }

    @Override
    protected float rotorSpeedFactor() {
        return this.fuel > 0 ? 1.0f : (float)this.water / 25.0f;
    }

    @Override
    public double getGuiValue(String name) {
        if ("water".equals(name)) {
            assert (this.maxWater > 0);
            return (double)this.fuel / (double)this.maxWater;
        }
        throw new IllegalArgumentException("Unexpected value requested: " + name);
    }
}

