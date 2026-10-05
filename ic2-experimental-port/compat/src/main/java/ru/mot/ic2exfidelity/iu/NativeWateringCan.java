package ru.mot.ic2exfidelity.iu;

import ic2.core.crop.TileEntityCrop;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.util.LiquidUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

/** Water-only cans, 100 mB per growing crop and 20% of its current growth stage. */
public final class NativeWateringCan extends Item implements StandardFluidItem {
    private final int radius;
    private final int capacity;
    public NativeWateringCan(Properties properties, int radius, int capacity) { super(properties); this.radius = radius; this.capacity = capacity; }
    @Override public int getCapacityMb(ItemStack stack) { return capacity; }
    @Override public boolean canFill(ItemStack stack, Ic2FluidStack fluid) { return fluid != null && fluid.getFluid() == Fluids.f_76193_; }
    @Override public net.minecraft.world.InteractionResultHolder<ItemStack> m_7203_(Level world, net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand) {
        var hit = m_41435_(world, player, net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
        boolean water = hit.m_6662_() == net.minecraft.world.phys.HitResult.Type.BLOCK && world.m_6425_(hit.m_82425_()).m_76152_() == Fluids.f_76193_;
        boolean filled = water && (world.f_46443_ || LiquidUtil.drainWorldFluidBlockToContainer(world, hit.m_82425_(), player, hand));
        return new net.minecraft.world.InteractionResultHolder<>(filled ? InteractionResult.SUCCESS : InteractionResult.PASS, player.m_21120_(hand));
    }
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        return context.m_43723_() != null && context.m_43723_().m_6144_() ? m_6225_(context) : InteractionResult.PASS;
    }
    @Override public InteractionResult m_6225_(UseOnContext context) {
        if (context.m_43723_() == null) return InteractionResult.PASS;
        Level world = context.m_43725_();
        if (world.f_46443_) return InteractionResult.SUCCESS;
        ItemStack stack = context.m_43722_();
        if (!context.m_43723_().m_6144_()) {
            return LiquidUtil.drainWorldFluidBlockToContainer(world, context.m_8083_(), context.m_43723_(), context.m_43724_()) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        Ic2FluidStack fluid = Ic2FluidStack.get(stack);
        if (fluid == null || fluid.getFluid() != Fluids.f_76193_) return InteractionResult.PASS;
        int remaining = fluid.getAmountMb();
        int watered = 0;
        BlockPos center = context.m_8083_();
        for (int x = -radius + 1; x <= radius && remaining >= 100; x++)
            for (int z = -radius + 1; z <= radius && remaining >= 100; z++)
                for (int y = -radius + 1; y <= radius && remaining >= 100; y++) {
                    BlockPos pos = center.m_7918_(x, y, z);
                    if (!world.m_46805_(pos)) continue;
                    if (!(world.m_7702_(pos) instanceof TileEntityCrop crop) || crop.getCrop() == null || !crop.getCrop().canGrow(crop) || crop.getCrop().isWeed(crop)) continue;
                    int duration = crop.getCrop().getGrowthDuration(crop);
                    if (duration <= 0 || crop.getGrowthPoints() >= duration) continue;
                    int growth = crop.getGrowthPoints() + Math.max(1, duration / 5);
                    if (growth >= duration) { crop.setGrowthPoints(0); crop.setCurrentAge(crop.getCurrentAge() + 1); }
                    else crop.setGrowthPoints(growth);
                    crop.m_6596_();
                    remaining -= 100;
                    watered++;
                }
        if (watered > 0) StandardFluidItem.setFs(stack, remaining == 0 ? null : Ic2FluidStack.create(Fluids.f_76193_, remaining));
        return watered > 0 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
    @Override public void m_7373_(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
        Ic2FluidStack fluid = Ic2FluidStack.get(stack);
        tooltip.add(Component.m_237110_("iu_native.watering.info", fluid == null ? 0 : fluid.getAmountMb(), capacity, radius));
    }
}
