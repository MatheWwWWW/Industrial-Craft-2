package ru.mot.ic2exfidelity.legacy;

import ic2.api.tile.IWrenchable;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.item.tool.ItemToolWrench;
import ic2.core.util.RotationUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import ru.mot.ic2exfidelity.integration.PipeWrenchCompat;

/**
 * The second 2.8.222 wrench: hit-region facing selection and direct pipe links.
 * It intentionally does not remove machines; the older wrench keeps that role.
 */
public final class LegacyNewWrench extends ItemToolWrench {
    public LegacyNewWrench(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.m_43723_();
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (PipeWrenchCompat.isPipe(context)) {
            if (context.m_43725_().f_46443_) {
                player.m_5496_(Ic2SoundEvents.ITEM_WRENCH_USE, 1.0F, 1.0F);
            } else {
                PipeWrenchCompat.flip(context);
            }
            return InteractionResult.SUCCESS;
        }

        BlockPos position = context.m_8083_();
        BlockState state = context.m_43725_().m_8055_(position);
        if (!(state.m_60734_() instanceof IWrenchable wrenchable)) {
            return InteractionResult.FAIL;
        }
        Vec3 hit = context.m_43720_();
        Direction facing = RotationUtil.rotateByHit(
                context.m_43719_(),
                (float) (hit.m_7096_() - position.m_123341_()),
                (float) (hit.m_7098_() - position.m_123342_()),
                (float) (hit.m_7094_() - position.m_123343_()));
        if (wrenchable.canSetFacing(context.m_43725_(), position, facing, player)
                && wrenchable.setFacing(context.m_43725_(), position, facing, player)) {
            if (context.m_43725_().f_46443_) {
                player.m_5496_(Ic2SoundEvents.ITEM_WRENCH_USE, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean m_6832_(ItemStack stack, ItemStack repair) {
        return repair != null && repair.m_204117_(Ic2ItemTags.BRONZE_INGOTS);
    }
}
