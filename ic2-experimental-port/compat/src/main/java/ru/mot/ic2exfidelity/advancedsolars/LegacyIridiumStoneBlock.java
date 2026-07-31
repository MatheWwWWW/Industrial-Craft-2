package ru.mot.ic2exfidelity.advancedsolars;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Owner-protected, explosion-proof IC2 Classic iridium stone. */
public final class LegacyIridiumStoneBlock extends Block implements EntityBlock {
    public LegacyIridiumStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity m_142194_(BlockPos position, BlockState state) {
        return new LegacyIridiumStoneBlockEntity(position, state);
    }

    @Override
    public void m_6402_(Level level, BlockPos position, BlockState state,
            LivingEntity placer, ItemStack stack) {
        super.m_6402_(level, position, state, placer, stack);
        if (placer instanceof Player player
                && level.m_7702_(position)
                        instanceof LegacyIridiumStoneBlockEntity stone) {
            stone.setOwner(player.m_20148_());
        }
    }

    public boolean canEntityDestroy(
            BlockState state, BlockGetter level, BlockPos position, Entity entity) {
        return false;
    }

    @Override
    public float m_5880_(
            BlockState state, Player player, BlockGetter level, BlockPos position) {
        if (level.m_7702_(position)
                instanceof LegacyIridiumStoneBlockEntity stone) {
            return stone.canBreak(player.m_20148_(), player.m_20310_(2))
                    ? player.m_36281_(state) / 240.0F
                    : 0.0F;
        }
        return 0.0F;
    }
}
