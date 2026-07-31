/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.container.ContainerMetalFormer;
import ic2.core.block.machine.gui.GuiMetalFormer;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.init.MainConfig;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.BasicMachineRecipeManager;
import ic2.core.util.ConfigUtil;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityMetalFormer
extends TileEntityStandardMachine<IRecipeInput, Collection<ItemStack>, ItemStack>
implements INetworkClientTileEntityEventListener {
    private int mode;
    public static final int EventSwitch = 0;

    public TileEntityMetalFormer() {
        super(10, 200, 1);
        this.inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, Recipes.metalformerExtruding);
    }

    public static void init() {
        Recipes.metalformerExtruding = new BasicMachineRecipeManager();
        Recipes.metalformerCutting = new BasicMachineRecipeManager();
        Recipes.metalformerRolling = new BasicMachineRecipeManager();
        if (ConfigUtil.getBool(MainConfig.get(), "recipes/allowCoinCrafting")) {
            // empty if block
        }
    }

    public static void addRecipeCutting(ItemStack input, int amount, ItemStack output) {
        TileEntityMetalFormer.addRecipeCutting(Recipes.inputFactory.forStack(input, amount), output);
    }

    public static void addRecipeCutting(IRecipeInput input, ItemStack output) {
        Recipes.metalformerCutting.addRecipe(input, null, false, output);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.setMode(nbt.func_74762_e("mode"));
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("mode", this.mode);
        return nbt;
    }

    @Override
    public ContainerBase<TileEntityMetalFormer> getGuiContainer(EntityPlayer player) {
        return new ContainerMetalFormer(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiMetalFormer(new ContainerMetalFormer(player, this));
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        switch (event) {
            case 0: {
                this.cycleMode();
            }
        }
    }

    @Override
    public void onNetworkUpdate(String field) {
        super.onNetworkUpdate(field);
        if (field.equals("mode")) {
            this.setMode(this.mode);
        }
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode1) {
        InvSlotProcessableGeneric slot = (InvSlotProcessableGeneric)this.inputSlot;
        switch (mode1) {
            case 0: {
                slot.setRecipeManager(Recipes.metalformerExtruding);
                break;
            }
            case 1: {
                slot.setRecipeManager(Recipes.metalformerRolling);
                break;
            }
            case 2: {
                slot.setRecipeManager(Recipes.metalformerCutting);
                break;
            }
            default: {
                throw new RuntimeException("invalid mode: " + mode1);
            }
        }
        this.mode = mode1;
    }

    private void cycleMode() {
        this.setMode((this.getMode() + 1) % 3);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}

