/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.generator.tileentity;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GuiSynced;
import ic2.core.util.ConfigUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityGenerator
extends TileEntityBaseGenerator
implements IGuiValueProvider {
    public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, false);
    @GuiSynced
    public int totalFuel = 0;

    public TileEntityGenerator() {
        super(Math.round(10.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator")), 1, 4000);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            TileEntityIronFurnace.showFlames(this.func_145831_w(), this.field_174879_c, this.getFacing());
        }
    }

    public double getFuelRatio() {
        if (this.fuel <= 0) {
            return 0.0;
        }
        return (double)this.fuel / (double)this.totalFuel;
    }

    @Override
    public boolean gainFuel() {
        int fuelValue = this.fuelSlot.consumeFuel() / 4;
        if (fuelValue == 0) {
            return false;
        }
        this.fuel += fuelValue;
        this.totalFuel = fuelValue;
        return true;
    }

    @Override
    public boolean isConverting() {
        return this.fuel > 0;
    }

    @Override
    public String getOperationSoundFile() {
        return "Generators/GeneratorLoop.ogg";
    }

    @Override
    public double getGuiValue(String name) {
        if ("fuel".equals(name)) {
            return this.getFuelRatio();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.totalFuel = nbt.func_74762_e("totalFuel");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("totalFuel", this.totalFuel);
        return nbt;
    }
}

