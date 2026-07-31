/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.IPlantable
 *  net.minecraftforge.common.PlantType
 */
package ic2.core.block.resource;

import ic2.core.block.base.IC2Block;
import ic2.core.block.rendering.block.SaplingModel;
import ic2.core.item.block.RubberwoodSaplingItem;
import ic2.core.platform.events.WorldGenerator;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RubberwoodSaplingBlock
extends IC2Block
implements IPlantable,
BonemealableBlock,
ICustomBlockModel,
IRenderType {
    public static final VoxelShape SHAPE = Block.m_49796_((double)2.0, (double)0.0, (double)2.0, (double)14.0, (double)12.0, (double)14.0);

    public RubberwoodSaplingBlock() {
        super("rubber_sapling", BlockBehaviour.Properties.m_60939_((Material)Material.f_76300_).m_60918_(SoundType.f_56740_).m_60977_());
    }

    @Override
    public BlockItem createItem() {
        return new RubberwoodSaplingItem(this);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!this.isValidGround(worldIn.m_8055_(pos.m_7495_()), (BlockGetter)worldIn, pos.m_7495_())) {
            Block.m_49950_((BlockState)state, (Level)worldIn, (BlockPos)pos);
            worldIn.m_46597_(pos, Blocks.f_50016_.m_49966_());
            return;
        }
        if (worldIn.m_46803_(pos.m_7494_()) >= 9 && random.m_188503_(7) == 0) {
            this.m_214148_(worldIn, random, pos, state);
        }
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public VoxelShape m_5939_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.m_83040_();
    }

    public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean m_214167_(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void m_214148_(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        worldIn.m_7731_(pos, Blocks.f_50016_.m_49966_(), 4);
        if (!WorldGenerator.RUBBER_TREE_SAPLING.m_224953_((WorldGenLevel)worldIn, worldIn.m_7726_().m_8481_(), rand, pos)) {
            worldIn.m_7731_(pos, state, 4);
        }
    }

    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return this.m_49966_();
    }

    protected boolean isValidGround(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.m_60734_();
        return block == Blocks.f_50440_ || block == Blocks.f_50493_ || block == Blocks.f_50546_ || block == Blocks.f_50599_ || block == Blocks.f_50093_;
    }

    public BlockState m_7417_(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.m_60710_((LevelReader)worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : stateIn;
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.m_7495_();
        return state.m_60734_() == this ? worldIn.m_8055_(blockpos).canSustainPlant((BlockGetter)worldIn, blockpos, Direction.UP, (IPlantable)this) : this.isValidGround(worldIn.m_8055_(blockpos), (BlockGetter)worldIn, blockpos);
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public List<BlockState> getCustomStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new SaplingModel(IC2Textures.getMappedEntriesBlockIC2("resources/rubberwood").get("sapling"));
    }
}

