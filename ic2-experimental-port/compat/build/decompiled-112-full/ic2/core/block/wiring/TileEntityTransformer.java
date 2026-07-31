/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.wiring;

import ic2.api.energy.EnergyNet;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Energy;
import ic2.core.block.wiring.ContainerTransformer;
import ic2.core.block.wiring.GuiTransformer;
import ic2.core.init.Localization;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TileEntityTransformer
extends TileEntityInventory
implements IHasGui,
INetworkClientTileEntityEventListener {
    private static final Mode defaultMode = Mode.redstone;
    private double inputFlow = 0.0;
    private double outputFlow = 0.0;
    private final int defaultTier;
    protected final Energy energy;
    private Mode configuredMode = defaultMode;
    private Mode transformMode = null;

    public TileEntityTransformer(int tier) {
        this.defaultTier = tier;
        this.energy = this.addComponent(new Energy(this, EnergyNet.instance.getPowerFromTier(tier) * 8.0, Collections.emptySet(), Collections.emptySet(), tier, tier, true).setMultiSource(true));
    }

    public String getType() {
        switch (this.energy.getSourceTier()) {
            case 1: {
                return "LV";
            }
            case 2: {
                return "MV";
            }
            case 3: {
                return "HV";
            }
            case 4: {
                return "EV";
            }
        }
        return "";
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        int mode = nbt.func_74762_e("mode");
        this.configuredMode = mode >= 0 && mode < Mode.VALUES.length ? Mode.VALUES[mode] : defaultMode;
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("mode", this.configuredMode.ordinal());
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            this.updateRedstone(true);
        }
    }

    public Mode getMode() {
        return this.configuredMode;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event >= 0 && event < Mode.VALUES.length) {
            this.configuredMode = Mode.VALUES[event];
            this.updateRedstone(false);
        } else if (event == 3) {
            // empty if block
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.updateRedstone(false);
    }

    private void updateRedstone(boolean force) {
        Mode newMode;
        assert (!this.func_145831_w().field_72995_K);
        switch (this.configuredMode) {
            case redstone: {
                newMode = this.func_145831_w().func_175640_z(this.field_174879_c) ? Mode.stepup : Mode.stepdown;
                break;
            }
            case stepdown: 
            case stepup: {
                newMode = this.configuredMode;
                break;
            }
            default: {
                throw new RuntimeException("invalid mode: " + (Object)((Object)this.configuredMode));
            }
        }
        if (newMode != Mode.stepup && newMode != Mode.stepdown) {
            throw new RuntimeException("invalid mode: " + (Object)((Object)newMode));
        }
        this.energy.setEnabled(true);
        if (force || this.transformMode != newMode) {
            this.transformMode = newMode;
            this.setActive(this.isStepUp());
            if (this.isStepUp()) {
                this.energy.setSourceTier(this.defaultTier + 1);
                this.energy.setSinkTier(this.defaultTier);
                this.energy.setPacketOutput(1);
                this.energy.setDirections(EnumSet.complementOf(EnumSet.of(this.getFacing())), EnumSet.of(this.getFacing()));
            } else {
                this.energy.setSourceTier(this.defaultTier);
                this.energy.setSinkTier(this.defaultTier + 1);
                this.energy.setPacketOutput(4);
                this.energy.setDirections(EnumSet.of(this.getFacing()), EnumSet.complementOf(EnumSet.of(this.getFacing())));
            }
            this.outputFlow = EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier());
            this.inputFlow = EnergyNet.instance.getPowerFromTier(this.energy.getSinkTier());
        }
    }

    @Override
    public void setFacing(EnumFacing facing) {
        super.setFacing(facing);
        if (!this.func_145831_w().field_72995_K) {
            this.updateRedstone(true);
        }
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, tooltip, advanced);
        tooltip.add(String.format("%s %.0f %s %s %.0f %s", Localization.translate("ic2.item.tooltip.Low"), EnergyNet.instance.getPowerFromTier(this.energy.getSinkTier()), Localization.translate("ic2.generic.text.EUt"), Localization.translate("ic2.item.tooltip.High"), EnergyNet.instance.getPowerFromTier(this.energy.getSourceTier() + 1), Localization.translate("ic2.generic.text.EUt")));
    }

    public ContainerBase<TileEntityTransformer> getGuiContainer(EntityPlayer player) {
        return new ContainerTransformer(player, this, 219);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiTransformer(new ContainerTransformer(player, this, 219));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public double getinputflow() {
        if (!this.isStepUp()) {
            return this.inputFlow;
        }
        return this.outputFlow;
    }

    public double getoutputflow() {
        if (this.isStepUp()) {
            return this.inputFlow;
        }
        return this.outputFlow;
    }

    private boolean isStepUp() {
        return this.transformMode == Mode.stepup;
    }

    public static enum Mode {
        redstone,
        stepdown,
        stepup;

        static final Mode[] VALUES;

        static {
            VALUES = Mode.values();
        }
    }
}

