/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops;

import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.CropTileEntity;
import ic2.core.block.rendering.block.CropModel;
import ic2.core.item.block.CropItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CropBlock
extends IC2ContainerBlock
implements ICustomBlockModel,
IStateController<CropTileEntity>,
SimpleWaterloggedBlock,
IRenderType {
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final IntegerProperty LIGHT = IC2Properties.LIGHT_0_15;
    public static final VoxelShape SHAPE = Block.m_49796_((double)3.2f, (double)0.0, (double)3.2f, (double)12.8f, (double)11.2, (double)12.8f);

    public CropBlock() {
        super("crop_sticks", BlockBehaviour.Properties.m_60939_((Material)Material.f_76300_).m_60953_(T -> (Integer)T.m_61143_((Property)LIGHT)).m_60910_().m_60918_(SoundType.f_56740_).m_60913_(0.8f, 0.2f));
        this.setHarvestTool(Tool.AXE);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LIGHT, (Comparable)Integer.valueOf(0)));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return RenderType.m_110463_();
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, CropTileEntity tile) {
        tile.setState((BlockState)state.m_61124_((Property)LIGHT, (Comparable)Integer.valueOf(tile.getCropLight())));
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        return CropRegistry.INSTANCE.getFarmland(worldIn.m_8055_(pos.m_7495_()).m_60734_()) != null;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_));
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATER) != false ? Fluids.f_76193_.m_76145_() : Fluids.f_76191_.m_76145_();
    }

    @Override
    public BlockItem createItem() {
        return new CropItem((Block)this);
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.CROP_STICK.m_155264_(pos, state);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LIGHT});
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return CropModel.INSTANCE;
    }
}

