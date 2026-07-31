/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class TankStorageModule
extends BaseModuleItem
implements IArmorToolTip {
    public TankStorageModule(String textureFolder, String textureName) {
        super("tank_storage_module", null, textureFolder, textureName, IArmorModule.ModuleType.STORAGE);
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.CHEST;
    }

    @Override
    public void provideCapabilities(ItemStack stack, ItemStack armor) {
        this.registerCapability(armor, ForgeCapabilities.FLUID_HANDLER_ITEM, new FluidHandlerItemStack(armor, 64000));
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        this.registerCapability(armor, ForgeCapabilities.FLUID_HANDLER_ITEM, new FluidHandlerItemStack(armor, 64000));
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        this.removeCapability(armor, ForgeCapabilities.FLUID_HANDLER_ITEM);
        armor.m_41749_("Fluid");
    }

    @Override
    public void transferToArmor(ItemStack stack, ItemStack oldArmor, ItemStack newArmor) {
        newArmor.m_41700_("Fluid", StackUtil.getNbtData(oldArmor).m_128423_("Fluid").m_6426_());
    }

    @Override
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        IFluidHandlerItem handler = (IFluidHandlerItem)armor.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (handler != null) {
            FluidStack fluid = handler.getFluidInTank(0);
            helper.addSimpleToolTip("tooltip.item.ic2.tank_storage_module." + (fluid.getAmount() <= 0 ? "empty" : "filled"), Formatters.EU_FORMAT.format(fluid.getAmount()), Formatters.EU_FORMAT.format(handler.getTankCapacity(0)), fluid.getDisplayName());
        }
    }
}

