/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.misc;

import ic2.core.block.base.misc.color.IItemColorListener;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.renders.models.FluidModel;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidDisplay
extends IC2Item
implements ICustomItemModel,
IItemColorListener {
    public FluidDisplay() {
        super("fluid_display", new PropertiesBuilder().setNoRepair().maxStackSize(1));
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public int getItemColor(ItemStack stack, int tintIndex) {
        Fluid fluid = FluidDisplay.getFluid(stack);
        return fluid == Fluids.f_76191_ ? -1 : IClientFluidTypeExtensions.of((Fluid)fluid).getTintColor(new FluidStack(fluid, 1000));
    }

    @Override
    public List<ItemStack> getCustomTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getModel(ItemStack stack) {
        return new FluidModel();
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.m_220152_(group)) {
            for (Fluid fluid : ForgeRegistries.FLUIDS) {
                if (fluid == Fluids.f_76191_ || !fluid.m_76145_().m_76170_()) continue;
                items.add((Object)FluidDisplay.createDisplay(fluid));
            }
        }
    }

    public Component m_7626_(ItemStack stack) {
        Fluid fluid = FluidDisplay.getFluid(stack);
        return fluid == Fluids.f_76191_ ? super.m_7626_(stack) : fluid.getFluidType().getDescription(new FluidStack(fluid, 1000));
    }

    public static ItemStack createDisplay(Fluid fluid) {
        ItemStack stack = new ItemStack((ItemLike)IC2Items.FLUID_DISPLAY);
        FluidDisplay.setFluid(stack, fluid);
        return stack;
    }

    public static Fluid getFluid(ItemStack stack) {
        return (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(StackUtil.getNbtData(stack).m_128461_("fluid")));
    }

    public static void setFluid(ItemStack stack, Fluid fluid) {
        if (fluid == Fluids.f_76191_ || !fluid.m_76145_().m_76170_()) {
            return;
        }
        stack.m_41784_().m_128359_("fluid", ForgeRegistries.FLUIDS.getKey((Object)fluid).toString());
    }
}

