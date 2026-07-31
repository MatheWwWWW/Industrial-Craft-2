/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  javax.annotation.Nullable
 *  net.minecraft.client.util.ITooltipFlag
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item;

import com.google.common.base.Function;
import ic2.api.item.IItemHudInfo;
import ic2.core.init.Localization;
import ic2.core.item.ItemIC2;
import ic2.core.item.capability.CapabilityFluidHandlerItem;
import ic2.core.ref.FluidName;
import ic2.core.ref.IMultiItem;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemIC2FluidContainer
extends ItemIC2
implements IMultiItem<FluidName>,
IItemHudInfo {
    protected final int capacity;

    public ItemIC2FluidContainer(ItemName name, int capacity) {
        super(name);
        this.capacity = capacity;
        this.func_77627_a(true);
        this.addCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, new Function<ItemStack, IFluidHandlerItem>(){

            public IFluidHandlerItem apply(@Nullable ItemStack stack) {
                return new CapabilityFluidHandlerItem(stack, ItemIC2FluidContainer.this.capacity){

                    public boolean canFillFluidType(FluidStack fluid) {
                        return fluid != null && ItemIC2FluidContainer.this.canfill(fluid.getFluid());
                    }

                    public boolean canDrainFluidType(FluidStack fluid) {
                        return fluid != null && ItemIC2FluidContainer.this.canfill(fluid.getFluid());
                    }
                };
            }
        });
    }

    @Override
    public ItemStack getItemStack(FluidName type) {
        return this.getItemStack(type.getInstance());
    }

    @Override
    public ItemStack getItemStack(Fluid fluid) {
        ItemStack ret = new ItemStack((Item)this);
        if (fluid == null) {
            return ret;
        }
        IFluidHandlerItem handler = FluidUtil.getFluidHandler((ItemStack)ret);
        if (handler == null) {
            return null;
        }
        if (handler.fill(new FluidStack(fluid, Integer.MAX_VALUE), true) > 0) {
            return handler.getContainer();
        }
        return null;
    }

    @Override
    public ItemStack getItemStack(String variant) {
        if (variant == null || variant.isEmpty()) {
            return new ItemStack((Item)this);
        }
        Fluid fluid = FluidRegistry.getFluid((String)variant);
        if (fluid == null) {
            return null;
        }
        return this.getItemStack(fluid);
    }

    @Override
    public String getVariant(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null stack");
        }
        if (stack.func_77973_b() != this) {
            throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this);
        }
        FluidStack fs = FluidUtil.getFluidContained((ItemStack)stack);
        if (fs == null || fs.getFluid() == null) {
            return null;
        }
        return fs.getFluid().getName();
    }

    @Override
    public Set<FluidName> getAllTypes() {
        return EnumSet.allOf(FluidName.class);
    }

    @Override
    public Set<ItemStack> getAllStacks() {
        HashSet<ItemStack> ret = new HashSet<ItemStack>();
        ret.add(new ItemStack((Item)this));
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            ItemStack add = this.getItemStack(fluid);
            if (add == null) continue;
            ret.add(add);
        }
        return ret;
    }

    public boolean hasContainerItem(ItemStack stack) {
        return FluidUtil.getFluidContained((ItemStack)stack) != null;
    }

    public ItemStack getContainerItem(ItemStack stack) {
        if (!this.hasContainerItem(stack)) {
            return super.getContainerItem(stack);
        }
        ItemStack ret = StackUtil.copyWithSize(stack, 1);
        IFluidHandlerItem handler = FluidUtil.getFluidHandler((ItemStack)ret);
        handler.drain(Integer.MAX_VALUE, true);
        return handler.getContainer();
    }

    @SideOnly(value=Side.CLIENT)
    public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        super.func_77624_a(stack, world, tooltip, advanced);
        FluidStack fs = FluidUtil.getFluidContained((ItemStack)stack);
        if (fs != null) {
            tooltip.add("< " + fs.getLocalizedName() + ", " + fs.amount + " mB >");
        } else {
            tooltip.add(Localization.translate("ic2.item.FluidContainer.Empty"));
        }
    }

    @Override
    public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        LinkedList<String> info = new LinkedList<String>();
        FluidStack fs = FluidUtil.getFluidContained((ItemStack)stack);
        if (fs != null) {
            info.add("< " + FluidRegistry.getFluidName((FluidStack)fs) + ", " + fs.amount + " mB >");
        } else {
            info.add(Localization.translate("ic2.item.FluidContainer.Empty"));
        }
        return info;
    }

    public abstract boolean canfill(Fluid var1);
}

