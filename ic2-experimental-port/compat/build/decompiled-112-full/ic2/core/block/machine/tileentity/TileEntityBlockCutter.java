/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.item.IBlockCuttingBlade;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.BasicMachineRecipeManager;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityBlockCutter
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack> {
    @GuiSynced
    private boolean bladeTooWeak = false;
    public final InvSlotConsumableClass cutterSlot;

    public TileEntityBlockCutter() {
        super(4, 450, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.blockcutter);
        this.cutterSlot = new InvSlotConsumableClass(this, "cutterInputSlot", 1, IBlockCuttingBlade.class);
    }

    public static void init() {
        Recipes.blockcutter = new BasicMachineRecipeManager();
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> ret;
        if (this.cutterSlot.isEmpty()) {
            if (!this.bladeTooWeak) {
                this.bladeTooWeak = true;
            }
            return null;
        }
        if (this.bladeTooWeak) {
            this.bladeTooWeak = false;
        }
        if ((ret = super.getOutput()) == null || ret.getRecipe().getMetaData() == null) {
            return null;
        }
        ItemStack bladeStack = this.cutterSlot.get();
        IBlockCuttingBlade blade = (IBlockCuttingBlade)bladeStack.func_77973_b();
        if (ret.getRecipe().getMetaData().func_74762_e("hardness") > blade.getHardness(bladeStack)) {
            if (!this.bladeTooWeak) {
                this.bladeTooWeak = true;
            }
            return null;
        }
        if (this.bladeTooWeak) {
            this.bladeTooWeak = false;
        }
        return ret;
    }

    @Override
    public ContainerBase<TileEntityBlockCutter> getGuiContainer(EntityPlayer player) {
        return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return DynamicGui.create(this, player, GuiParser.parse(this.teBlock));
    }

    @Override
    public boolean getGuiState(String name) {
        if ("isBladeTooWeak".equals(name)) {
            return this.bladeTooWeak;
        }
        return super.getGuiState(name);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

