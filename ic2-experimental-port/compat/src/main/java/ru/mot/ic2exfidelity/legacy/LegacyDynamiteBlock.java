package ru.mot.ic2exfidelity.legacy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Placeable dynamite with the original wall/floor support and ignition rules. */
public final class LegacyDynamiteBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.m_61546_(
            "facing", direction -> direction != Direction.DOWN);
    public static final BooleanProperty LINKED = BooleanProperty.m_61465_("linked");

    private static final VoxelShape FLOOR = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
    private static final VoxelShape NORTH = Block.m_49796_(6.0D, 3.0D, 11.0D, 10.0D, 13.0D, 16.0D);
    private static final VoxelShape SOUTH = Block.m_49796_(6.0D, 3.0D, 0.0D, 10.0D, 13.0D, 5.0D);
    private static final VoxelShape WEST = Block.m_49796_(11.0D, 3.0D, 6.0D, 16.0D, 13.0D, 10.0D);
    private static final VoxelShape EAST = Block.m_49796_(0.0D, 3.0D, 6.0D, 5.0D, 13.0D, 10.0D);

    public LegacyDynamiteBlock() {
        super(BlockBehaviour.Properties.m_60926_(Blocks.f_50081_));
        m_49959_(m_49966_()
                .m_61124_(FACING, Direction.UP)
                .m_61124_(LINKED, Boolean.FALSE));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(FACING, LINKED);
    }

    @Override
    public VoxelShape m_5940_(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.m_61143_(FACING)) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
            default -> FLOOR;
        };
    }

    @Override
    public boolean m_7898_(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.m_61143_(FACING);
        BlockPos support = pos.m_121945_(facing.m_122424_());
        return level.m_8055_(support).m_60783_(level, support, facing);
    }

    @Override
    public void m_6807_(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        super.m_6807_(state, level, pos, oldState, moving);
        checkPlacement(level, pos, state);
    }

    @Override
    public void m_6861_(
            BlockState state, Level level, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean moving) {
        checkPlacement(level, pos, state);
    }

    @Override
    public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.f_46443_ && level.m_8055_(pos).m_60713_(this)) {
            ignite(level, pos, player, 40);
        }
    }

    @Override
    public void m_7592_(Level level, BlockPos pos, Explosion explosion) {
        if (!level.f_46443_) {
            ignite(level, pos, explosion == null ? null : explosion.m_46079_(), 5);
        }
    }

    private void checkPlacement(Level level, BlockPos pos, BlockState state) {
        if (level.f_46443_) {
            return;
        }
        if (level.m_46753_(pos)) {
            ignite(level, pos, null, 40);
        } else if (!m_7898_(state, level, pos)) {
            level.m_7471_(pos, false);
            Block.m_49840_(level, pos, new ItemStack(RestoredLegacyContent.DYNAMITE.get()));
        }
    }

    public void ignite(Level level, BlockPos pos, LivingEntity owner, int fuse) {
        if (level.f_46443_) {
            return;
        }
        level.m_7471_(pos, false);
        LegacyDynamiteEntity entity = new LegacyDynamiteEntity(
                level,
                pos.m_123341_() + 0.5D,
                pos.m_123342_() + 0.5D,
                pos.m_123343_() + 0.5D,
                true);
        if (owner != null) {
            entity.m_5602_(owner);
        }
        entity.setFuse(fuse);
        level.m_7967_(entity);
        level.m_5594_(null, pos, SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
