package ru.mot.ic2exfidelity.legacy;

import ic2.api.util.FluidContainerOutputMode;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.StandardFluidItem;
import ic2.core.util.LiquidUtil;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/** Universal one-bucket cell used by IC2 2.8 machine and fluid recipes. */
public final class LegacyFluidCell extends Item implements StandardFluidItem {
    private static final int CAPACITY_MB = 1_000;

    public LegacyFluidCell(Properties properties) {
        super(properties);
    }

    @Override
    public int getCapacityMb(ItemStack stack) {
        return CAPACITY_MB;
    }

    @Override
    public boolean canFill(ItemStack stack, Ic2FluidStack fluid) {
        return fluid != null && !fluid.isEmpty();
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        Player player = context.m_43723_();
        if (player == null) {
            return InteractionResult.PASS;
        }
        Level level = context.m_43725_();
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }

        BlockState state = level.m_8055_(context.m_8083_());
        BlockEntity blockEntity = level.m_7702_(context.m_8083_());
        if (blockEntity != null
                && LiquidUtil.isFluidTile(state, blockEntity, context.m_43719_())
                && emptyIntoTank(player, context, blockEntity)) {
            return InteractionResult.SUCCESS;
        }

        if (LiquidUtil.drainWorldFluidBlockToContainer(
                level, context.m_8083_(), player, context.m_43724_())) {
            return InteractionResult.SUCCESS;
        }
        if (LiquidUtil.fillWorldFluidBlockFromContainer(
                level, context.m_8083_(), player, context.m_43724_())) {
            return InteractionResult.SUCCESS;
        }
        if (LiquidUtil.fillWorldFluidBlockFromContainer(
                level, context.m_8083_().m_121945_(context.m_43719_()),
                player, context.m_43724_())) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private static boolean emptyIntoTank(
            Player player, UseOnContext context, BlockEntity blockEntity) {
        Ic2FluidStack contained = Ic2FluidStack.get(context.m_43722_());
        if (contained == null || contained.isEmpty()) {
            return false;
        }

        int accepted = LiquidUtil.fillTile(
                blockEntity, context.m_43719_(), contained, true);
        if (accepted <= 0) {
            return false;
        }
        Ic2FluidStack simulated = LiquidUtil.drainContainer(
                player, context.m_43724_(), contained.getFluid(), accepted,
                FluidContainerOutputMode.InPlacePreferred, true);
        if (simulated == null || simulated.getAmountMb() != accepted) {
            return false;
        }

        Ic2FluidStack drained = LiquidUtil.drainContainer(
                player, context.m_43724_(), contained.getFluid(), accepted,
                FluidContainerOutputMode.InPlacePreferred, false);
        return drained != null
                && LiquidUtil.fillTile(blockEntity, context.m_43719_(), drained, false)
                        == drained.getAmountMb();
    }

    @Override
    public void m_7373_(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        Ic2FluidStack fluid = Ic2FluidStack.get(stack);
        if (fluid == null || fluid.isEmpty()) {
            tooltip.add(Component.m_237115_("item.ic2.fluid_cell.empty"));
            return;
        }
        ResourceLocation id = ForgeRegistries.FLUIDS.getKey(fluid.getFluid());
        tooltip.add(Component.m_237113_(
                (id == null ? "unknown" : id.toString())
                        + ": " + fluid.getAmountMb() + " / " + CAPACITY_MB + " mB"));
    }
}
