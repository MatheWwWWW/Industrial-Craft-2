package ru.mot.ic2exfidelity.gravisuit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Invisible, non-colliding 25-second GraviSuite plasma portal. */
public final class LegacyPlasmaPortalBlock extends Block implements EntityBlock {
    public LegacyPlasmaPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape m_5940_(
            BlockState state, BlockGetter level, BlockPos position,
            CollisionContext context) {
        return Shapes.m_83040_();
    }

    @Override
    public BlockEntity m_142194_(BlockPos position, BlockState state) {
        return new LegacyPlasmaPortalBlockEntity(position, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(
            Level level, BlockState state, BlockEntityType<T> type) {
        return type == LegacyGravisuitContent.PLASMA_PORTAL_BLOCK_ENTITY.get()
                ? (world, position, portalState, blockEntity) ->
                        LegacyPlasmaPortalBlockEntity.tick(
                                world, position, portalState,
                                (LegacyPlasmaPortalBlockEntity) blockEntity)
                : null;
    }

    @Override
    public void m_7892_(
            BlockState state, Level level, BlockPos position, Entity entity) {
        if (entity instanceof LivingEntity living
                && level.m_7702_(position)
                        instanceof LegacyPlasmaPortalBlockEntity portal
                && portal.hasTarget()) {
            portal.addEntityToTeleport(living);
        }
    }
}
