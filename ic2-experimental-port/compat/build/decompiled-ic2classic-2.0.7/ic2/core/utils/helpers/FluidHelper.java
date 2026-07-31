/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidActionResult
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.utils.helpers;

import ic2.core.fluid.WrapperFluidHandler;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.misc.FluidDisplay;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHelper {
    public static FluidStack transform(FluidStack stack, TagKey<Fluid> tag, Fluid targetFluid) {
        if (stack.getFluid() == targetFluid) {
            return stack;
        }
        if (stack.getFluid().m_205067_(tag)) {
            return new FluidStack(targetFluid, stack.getAmount());
        }
        return FluidStack.EMPTY;
    }

    public static int getFluidColor(FluidStack stack) {
        return IClientFluidTypeExtensions.of((Fluid)stack.getFluid()).getTintColor(stack);
    }

    @OnlyIn(value=Dist.CLIENT)
    public static TextureAtlasSprite getTexture(FluidStack stack, boolean flowing) {
        return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(FluidHelper.getTextureLocation(stack, flowing));
    }

    public static ResourceLocation getTextureLocation(FluidStack stack, boolean flowing) {
        IClientFluidTypeExtensions attribute = IClientFluidTypeExtensions.of((Fluid)stack.getFluid());
        return flowing ? attribute.getFlowingTexture(stack) : attribute.getStillTexture(stack);
    }

    public static Fluid getDisplayFluid(ItemStack stack) {
        if (stack.m_41720_() instanceof FluidDisplay) {
            return FluidDisplay.getFluid(stack);
        }
        return FluidUtil.getFluidContained((ItemStack)stack).orElse(FluidStack.EMPTY).getFluid();
    }

    public static boolean drainContainers(ItemStack source, Player player, IFluidHandler handler) {
        if (source.m_41619_()) {
            return false;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(player.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).orElse(null));
        if (transporter == null) {
            return false;
        }
        FluidActionResult emptyingResult = FluidUtil.tryEmptyContainer((ItemStack)source, (IFluidHandler)new WrapperFluidHandler.FillHandlerWrapper(handler), (int)handler.getTankCapacity(0), null, (boolean)true);
        if (!emptyingResult.isSuccess()) {
            return false;
        }
        if (transporter.addItem(emptyingResult.getResult(), Direction.DOWN, true) <= 0) {
            return false;
        }
        emptyingResult = FluidUtil.tryEmptyContainer((ItemStack)source, (IFluidHandler)handler, (int)handler.getTankCapacity(0), null, (boolean)true);
        source.m_41774_(1);
        transporter.addItem(emptyingResult.getResult(), Direction.DOWN, false);
        return true;
    }

    public static boolean drainContainers(IHasInventory inv, int inputSlot, int outputSlot, IFluidHandler handler) {
        ItemStack input = inv.getStackInSlot(inputSlot);
        if (input.m_41619_() || StackUtil.getStackSizeLeft(inv.getStackInSlot(outputSlot)) <= 0) {
            return false;
        }
        FluidActionResult emptyingResult = FluidUtil.tryEmptyContainer((ItemStack)input, (IFluidHandler)new WrapperFluidHandler.FillHandlerWrapper(handler), (int)handler.getTankCapacity(0), null, (boolean)true);
        if (!emptyingResult.isSuccess()) {
            return false;
        }
        ItemStack result = emptyingResult.getResult();
        if (!FluidHelper.fillIntoInventory(inv, outputSlot, result, false)) {
            return false;
        }
        emptyingResult = FluidUtil.tryEmptyContainer((ItemStack)input, (IFluidHandler)handler, (int)handler.getTankCapacity(0), null, (boolean)true);
        input.m_41774_(1);
        FluidHelper.fillIntoInventory(inv, outputSlot, emptyingResult.getResult(), true);
        return true;
    }

    public static boolean fillContainers(IHasInventory inv, int inputSlot, int outputSlot, IFluidHandler handler) {
        ItemStack input = inv.getStackInSlot(inputSlot);
        if (input.m_41619_() || StackUtil.getStackSizeLeft(inv.getStackInSlot(outputSlot)) <= 0) {
            return false;
        }
        FluidActionResult fillingResult = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)new WrapperFluidHandler(handler), (int)handler.getTankCapacity(0), null, (boolean)true);
        if (!fillingResult.isSuccess()) {
            return false;
        }
        ItemStack result = fillingResult.getResult();
        if (!FluidHelper.fillIntoInventory(inv, outputSlot, result, false)) {
            return false;
        }
        fillingResult = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)handler, (int)handler.getTankCapacity(0), null, (boolean)true);
        input.m_41774_(1);
        FluidHelper.fillIntoInventory(inv, outputSlot, fillingResult.getResult(), true);
        return true;
    }

    public static boolean fillContainer(IHasInventory inputInv, int inputSlot, IHasInventory outputInv, IFluidHandler handler, boolean simulate) {
        ItemStack input = inputInv.getStackInSlot(inputSlot);
        if (input.m_41619_()) {
            return false;
        }
        FluidActionResult fillingResult = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)new WrapperFluidHandler(handler), (int)handler.getTankCapacity(0), null, (boolean)true);
        if (!fillingResult.isSuccess()) {
            return false;
        }
        ItemStack result = fillingResult.getResult();
        if (!FluidHelper.fillIntoInventory(outputInv, result, false)) {
            return false;
        }
        if (simulate) {
            return true;
        }
        fillingResult = FluidUtil.tryFillContainer((ItemStack)input, (IFluidHandler)handler, (int)handler.getTankCapacity(0), null, (boolean)true);
        input.m_41774_(1);
        FluidHelper.fillIntoInventory(outputInv, fillingResult.getResult(), true);
        return true;
    }

    public static boolean fillContainers(ItemStack source, Player player, IFluidHandler handler) {
        if (source.m_41619_()) {
            return false;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(player.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).orElse(null));
        if (transporter == null) {
            return false;
        }
        FluidActionResult fillingResult = FluidUtil.tryFillContainer((ItemStack)source, (IFluidHandler)new WrapperFluidHandler(handler), (int)handler.getTankCapacity(0), null, (boolean)true);
        if (!fillingResult.isSuccess()) {
            return false;
        }
        if (transporter.addItem(fillingResult.getResult(), Direction.DOWN, true) <= 0) {
            return false;
        }
        fillingResult = FluidUtil.tryFillContainer((ItemStack)source, (IFluidHandler)handler, (int)handler.getTankCapacity(0), null, (boolean)true);
        source.m_41774_(1);
        transporter.addItem(fillingResult.getResult(), Direction.DOWN, false);
        return true;
    }

    static boolean fillIntoInventory(IHasInventory inv, ItemStack stack, boolean doInsert) {
        if (stack.m_41619_()) {
            return false;
        }
        return TransporterManager.getTransporter(inv).addItem(stack, null, !doInsert) >= stack.m_41613_();
    }

    static boolean fillIntoInventory(IHasInventory inv, int slot, ItemStack stack, boolean doInsert) {
        if (stack.m_41619_()) {
            return false;
        }
        ItemStack invStack = inv.getStackInSlot(slot);
        if (invStack.m_41619_()) {
            if (doInsert) {
                inv.setStackInSlot(slot, stack);
            }
            return true;
        }
        if (StackUtil.isStackEqual(invStack, stack) && invStack.m_41613_() + stack.m_41613_() <= Math.min(inv.getMaxStackSize(slot), stack.m_41741_())) {
            if (doInsert) {
                invStack.m_41769_(stack.m_41613_());
                stack.m_41764_(0);
                inv.setStackInSlot(slot, invStack);
            }
            return true;
        }
        return false;
    }
}

