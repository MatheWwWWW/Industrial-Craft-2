/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.heatgenerator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityHeatSourceInventory;
import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
import ic2.core.block.heatgenerator.gui.GuiRTHeatGenerator;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.init.MainConfig;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityRTHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui {
    private boolean newActive;
    public final InvSlotConsumable fuelSlot = new InvSlotConsumableItemStack(this, "fuelSlot", 6, ItemName.nuclear.getItemStack(NuclearResourceType.rtg_pellet));
    public static final float outputMultiplier = 2.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/radioisotope");

    public TileEntityRTHeatGenerator() {
        this.fuelSlot.setStackSizeLimit(1);
        this.newActive = false;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.newActive = this.HeatBuffer > 0;
        if (this.getActive() != this.newActive) {
            this.setActive(this.newActive);
        }
    }

    @Override
    protected int fillHeatBuffer(int maxAmount) {
        if (maxAmount >= this.getMaxHeatEmittedPerTick()) {
            return this.getMaxHeatEmittedPerTick();
        }
        return maxAmount;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        int counter = 0;
        for (int i = 0; i < this.fuelSlot.size(); ++i) {
            if (this.fuelSlot.isEmpty(i)) continue;
            ++counter;
        }
        if (counter == 0) {
            return 0;
        }
        return (int)(Math.pow(2.0, counter - 1) * (double)outputMultiplier);
    }

    public ContainerBase<TileEntityRTHeatGenerator> getGuiContainer(EntityPlayer player) {
        return new ContainerRTHeatGenerator(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiRTHeatGenerator(new ContainerRTHeatGenerator(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }
}

