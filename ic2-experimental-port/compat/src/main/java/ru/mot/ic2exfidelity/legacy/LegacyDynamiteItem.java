package ru.mot.ic2exfidelity.legacy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Both throwable dynamite variants; only the ordinary one may be placed. */
public final class LegacyDynamiteItem extends Item {
    private final boolean sticky;

    public LegacyDynamiteItem(Properties properties, boolean sticky) {
        super(properties.m_41487_(16));
        this.sticky = sticky;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext context) {
        if (sticky) {
            return InteractionResult.PASS;
        }
        Direction facing = context.m_43719_();
        if (facing == Direction.DOWN) {
            return InteractionResult.FAIL;
        }
        Level level = context.m_43725_();
        BlockPos pos = context.m_8083_().m_121945_(facing);
        if (!level.m_8055_(pos).m_60795_()) {
            return InteractionResult.FAIL;
        }
        LegacyDynamiteBlock block = RestoredLegacyContent.DYNAMITE_BLOCK.get();
        BlockState state = block.m_49966_()
                .m_61124_(LegacyDynamiteBlock.FACING, facing)
                .m_61124_(LegacyDynamiteBlock.LINKED, Boolean.FALSE);
        if (!block.m_7898_(state, level, pos) || !level.m_7731_(pos, state, 3)) {
            return InteractionResult.FAIL;
        }

        Player player = context.m_43723_();
        if (player == null || !player.m_150110_().f_35937_) {
            context.m_43722_().m_41774_(1);
        }
        return InteractionResult.m_19078_(level.f_46443_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        level.m_5594_(
                null,
                player.m_20183_(),
                SoundEvents.f_12473_,
                SoundSource.PLAYERS,
                0.5F,
                0.4F / (level.f_46441_.m_188501_() * 0.4F + 0.8F));

        if (!level.f_46443_) {
            LegacyDynamiteEntity entity = new LegacyDynamiteEntity(level, player, sticky);
            entity.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, 1.5F, 1.0F);
            level.m_7967_(entity);
            if (!player.m_150110_().f_35937_) {
                stack.m_41774_(1);
            }
        }
        return InteractionResultHolder.m_19092_(stack, level.f_46443_);
    }
}
