/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SupportType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc.explosive;

import ic2.api.util.DirectionList;
import ic2.core.block.misc.base.IC2ExplosiveBlock;
import ic2.core.block.rendering.block.DynamiteModel;
import ic2.core.entity.explosion.DynamiteEntity;
import ic2.core.item.block.DynamiteItem;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DynamiteBlock
extends IC2ExplosiveBlock
implements ICustomBlockModel,
IRenderType {
    public static final VoxelShape[] SHAPES = new VoxelShape[]{Block.m_49796_((double)6.0, (double)0.0, (double)6.0, (double)10.0, (double)10.0, (double)10.0), null, Block.m_49796_((double)5.5, (double)3.0, (double)11.0, (double)10.5, (double)13.0, (double)16.0), Block.m_49796_((double)5.5, (double)3.0, (double)0.0, (double)10.5, (double)13.0, (double)5.0), Block.m_49796_((double)11.0, (double)3.0, (double)5.5, (double)16.0, (double)13.0, (double)10.5), Block.m_49796_((double)0.0, (double)3.0, (double)5.5, (double)5.0, (double)13.0, (double)10.5)};
    public static final AABB[] MODELS = new AABB[]{new AABB(6.0, 0.0, 6.0, 10.0, 10.0, 10.0), null, new AABB(5.5, 3.0, 11.0, 10.5, 13.0, 16.0), new AABB(5.5, 3.0, 0.0, 10.5, 13.0, 5.0), new AABB(11.0, 3.0, 5.5, 16.0, 13.0, 10.5), new AABB(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)};
    public static final BooleanProperty LINKED = BooleanProperty.m_61465_((String)"linked");
    public static final DirectionProperty DIRECTION = DirectionProperty.m_61543_((String)"side", DirectionList.UP.invert().toFacings());

    public DynamiteBlock() {
        super("dynamite", null, null, true);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)LINKED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)DIRECTION, (Comparable)Direction.DOWN));
    }

    @Override
    public BlockItem createItem() {
        return new DynamiteItem(this);
    }

    @Override
    public Entity getExplosiveEntity(Level world, double x, double y, double z, BlockState state, boolean explosion) {
        DynamiteEntity dynamite = new DynamiteEntity(IC2Entities.DYNAMITE, world, x, y, z, state.m_61143_((Property)DIRECTION) != Direction.DOWN ? new BlockPos(x, y, z) : null);
        dynamite.setItem(new ItemStack((ItemLike)this));
        dynamite.setFuse(explosion ? 40 : 5);
        return dynamite;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[((Direction)state.m_61143_((Property)DIRECTION)).m_122411_()];
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        BlockState state = super.m_5573_(context);
        Direction facing = context.m_43719_();
        if (facing != Direction.DOWN && this.m_7898_((BlockState)state.m_61124_((Property)DIRECTION, (Comparable)(facing == Direction.UP ? facing.m_122424_() : facing)), (LevelReader)context.m_43725_(), context.m_8083_())) {
            state = (BlockState)state.m_61124_((Property)DIRECTION, (Comparable)(facing == Direction.UP ? facing.m_122424_() : facing));
        }
        return state;
    }

    public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !this.m_7898_(stateIn, (LevelReader)worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction dir = (Direction)state.m_61143_((Property)DIRECTION);
        if (dir.m_122434_().m_122478_()) {
            dir = Direction.UP;
        }
        BlockPos newPos = pos.m_121945_(dir.m_122424_());
        return worldIn.m_8055_(newPos).m_60659_((BlockGetter)worldIn, newPos, dir, SupportType.RIGID);
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return MODELS[((Direction)state.m_61143_((Property)DIRECTION)).m_122411_()];
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2("misc/explosive/dynamite").get((Boolean)state.m_61143_((Property)LINKED) != false ? "linked_dynamite" : "unlinked_dynamite");
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{LINKED}).m_61104_(new Property[]{DIRECTION});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new DynamiteModel(this.getSpriteForState(state, Direction.NORTH), (Direction)state.m_61143_((Property)DIRECTION));
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.emptyList();
    }

    public static void setLinked(BlockState state, Level world, BlockPos pos, boolean linked) {
        world.m_46597_(pos, (BlockState)state.m_61124_((Property)LINKED, (Comparable)Boolean.valueOf(linked)));
    }

    public static boolean isLinked(BlockState state) {
        return (Boolean)state.m_61143_((Property)LINKED);
    }

    public static void ignite(BlockState state, Level world, BlockPos pos) {
        world.m_7967_(((DynamiteBlock)state.m_60734_()).getExplosiveEntity(world, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, state, true));
        world.m_7471_(pos, false);
    }
}

