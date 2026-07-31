/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DiodeBlock
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.RedStoneWireBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SupportType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.ComparatorMode
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.level.storage.loot.LootContext$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraft.world.ticks.TickPriority
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 *  net.minecraftforge.event.ForgeEventFactory
 */
package ic2.core.block.cables;

import ic2.api.blocks.IAdvancedComparable;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.IAutoCreator;
import ic2.core.block.cables.AdvancedComparatorTileEntity;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.block.ITileModel;
import ic2.core.platform.rendering.features.block.ITileParticleTexture;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.plugins.IRegistryProvider;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;

public class AdvancedComparatorBlock
extends DiodeBlock
implements ITileModel,
IAutoCreator,
ITileParticleTexture,
IRegistryProvider,
EntityBlock {
    public static final DirectionProperty FACING = IC2Properties.ALL_FACINGS;
    public static final DirectionProperty ROTATION = DirectionProperty.m_61546_((String)"rotation", (Predicate)DirectionList.ALL);
    public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.f_61393_;
    public static final VoxelShape[] SHAPES = new VoxelShape[]{Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)2.0, (double)16.0), Block.m_49796_((double)0.0, (double)14.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)2.0), Block.m_49796_((double)0.0, (double)0.0, (double)14.0, (double)16.0, (double)16.0, (double)16.0), Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)2.0, (double)16.0, (double)16.0), Block.m_49796_((double)14.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0)};

    public AdvancedComparatorBlock() {
        super(BlockBehaviour.Properties.m_60939_((Material)Material.f_76310_).m_60955_());
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)Direction.DOWN)).m_61124_((Property)ROTATION, (Comparable)Direction.SOUTH)).m_61124_((Property)f_52496_, (Comparable)Boolean.valueOf(false))).m_61124_(MODE, (Comparable)ComparatorMode.COMPARE));
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation("ic2", "advanced_comparator");
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this, new Item.Properties().m_41491_(IC2.IC2_MAIN_GROUP)){

            public void initializeClient(Consumer<IClientItemExtensions> consumer) {
                consumer.accept(IC2.PLATFORM.getTileRenderer());
            }
        };
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getParticleTexture(BlockState state) {
        return (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(new ResourceLocation("block/comparator"));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING, ROTATION, MODE, f_52496_});
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return IC2Tiles.ADVANCED_COMPARATOR.m_155264_(pos, state);
    }

    public List<ItemStack> m_7381_(BlockState state, LootContext.Builder builder) {
        LootTable loottable = builder.m_78962_().m_7654_().m_129898_().m_79217_(this.m_60589_());
        if (loottable != LootTable.f_79105_) {
            return loottable.m_230922_(builder.m_78972_(LootContextParams.f_81461_, (Object)state).m_78975_(LootContextParamSets.f_81421_));
        }
        ObjectList drops = CollectionUtils.createList();
        drops.add((ItemStack)new ItemStack((ItemLike)this));
        return drops;
    }

    protected int m_6112_(BlockState state) {
        return 2;
    }

    public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction dir = (Direction)state.m_61143_((Property)FACING);
        pos = pos.m_121945_(dir);
        return worldIn.m_8055_(pos).m_60659_((BlockGetter)worldIn, pos, dir.m_122424_(), SupportType.RIGID);
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[((Direction)state.m_61143_((Property)FACING)).m_122411_()];
    }

    protected int m_5968_(BlockGetter worldIn, BlockPos pos, BlockState state) {
        BlockEntity tileentity = worldIn.m_7702_(pos);
        return tileentity instanceof AdvancedComparatorTileEntity ? ((AdvancedComparatorTileEntity)tileentity).getOutputSignal() : 0;
    }

    private int calculateOutput(Level worldIn, BlockPos pos, BlockState state) {
        return state.m_61143_(MODE) == ComparatorMode.SUBTRACT ? Math.max(this.m_7312_(worldIn, pos, state) - this.m_52547_((LevelReader)worldIn, pos, state), 0) : this.m_7312_(worldIn, pos, state);
    }

    protected boolean m_7320_(Level worldIn, BlockPos pos, BlockState state) {
        int i = this.m_7312_(worldIn, pos, state);
        if (i >= 15) {
            return true;
        }
        if (i == 0) {
            return false;
        }
        return i >= this.m_52547_((LevelReader)worldIn, pos, state);
    }

    protected int getBaseStrength(Level worldIn, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.m_61143_((Property)ROTATION);
        BlockPos offset = pos.m_121945_(direction);
        int i = worldIn.m_46681_(offset, direction);
        if (i >= 15) {
            return i;
        }
        BlockState offsetState = worldIn.m_8055_(offset);
        return Math.max(i, offsetState.m_60734_() == Blocks.f_50088_ ? (Integer)offsetState.m_61143_((Property)RedStoneWireBlock.f_55500_) : 0);
    }

    protected int m_7312_(Level worldIn, BlockPos pos, BlockState state) {
        return AdvancedComparatorBlock.getComparatorOutput(worldIn, pos, (Direction)state.m_61143_((Property)ROTATION), true, this.getBaseStrength(worldIn, pos, state));
    }

    public static int getComparatorOutput(Level level, BlockPos pos, Direction dir, boolean throughBlocks, int defaultValue) {
        BlockPos offset = pos.m_121945_(dir);
        BlockState state = level.m_8055_(offset);
        if (state.m_60807_()) {
            return AdvancedComparatorBlock.getComparatorOutput(state, level, offset, dir.m_122424_());
        }
        if (defaultValue < 15 && throughBlocks && state.m_60796_((BlockGetter)level, offset)) {
            return AdvancedComparatorBlock.getComparatorOutput(level, offset, dir, false, defaultValue);
        }
        return defaultValue;
    }

    public static int getComparatorOutput(BlockState state, Level world, BlockPos pos, Direction side) {
        int n;
        Block block = state.m_60734_();
        if (block instanceof IAdvancedComparable) {
            IAdvancedComparable comp = (IAdvancedComparable)block;
            n = comp.getComparatorInputOverride(state, world, pos, side);
        } else {
            n = state.m_60674_(world, pos);
        }
        return n;
    }

    public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!player.m_150110_().f_35938_) {
            return InteractionResult.PASS;
        }
        state = player.m_6144_() ? (BlockState)state.m_61124_((Property)ROTATION, (Comparable)DirectionList.rotateAround((Direction)state.m_61143_((Property)ROTATION), ((Direction)state.m_61143_((Property)FACING)).m_122434_())) : (BlockState)state.m_61122_(MODE);
        worldIn.m_5594_(player, pos, SoundEvents.f_11762_, SoundSource.BLOCKS, 0.3f, state.m_61143_(MODE) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f);
        worldIn.m_7731_(pos, state, 2);
        this.onStateChange(worldIn, pos, state);
        return InteractionResult.SUCCESS;
    }

    protected void m_7321_(Level worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.m_183326_().m_183588_(pos, (Object)this)) {
            int j;
            int i = this.calculateOutput(worldIn, pos, state);
            BlockEntity tileentity = worldIn.m_7702_(pos);
            int n = j = tileentity instanceof AdvancedComparatorTileEntity ? ((AdvancedComparatorTileEntity)tileentity).getOutputSignal() : 0;
            if (i != j || ((Boolean)state.m_61143_((Property)f_52496_)).booleanValue() != this.m_7320_(worldIn, pos, state)) {
                worldIn.m_186464_(pos, (Block)this, 2, this.m_52573_((BlockGetter)worldIn, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL);
            }
        }
    }

    private void onStateChange(Level worldIn, BlockPos pos, BlockState state) {
        int i = this.calculateOutput(worldIn, pos, state);
        BlockEntity tile = worldIn.m_7702_(pos);
        int j = 0;
        if (tile instanceof AdvancedComparatorTileEntity) {
            AdvancedComparatorTileEntity comp = (AdvancedComparatorTileEntity)tile;
            j = comp.getOutputSignal();
            comp.setOutputSignal(i);
        }
        if (j != i || state.m_61143_(MODE) == ComparatorMode.COMPARE) {
            boolean flag1 = this.m_7320_(worldIn, pos, state);
            boolean flag = (Boolean)state.m_61143_((Property)f_52496_);
            if (flag && !flag1) {
                worldIn.m_7731_(pos, (BlockState)state.m_61124_((Property)f_52496_, (Comparable)Boolean.valueOf(false)), 2);
            } else if (!flag && flag1) {
                worldIn.m_7731_(pos, (BlockState)state.m_61124_((Property)f_52496_, (Comparable)Boolean.valueOf(true)), 2);
            }
            this.m_52580_(worldIn, pos, state);
        }
    }

    public int m_6376_(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return this.getCommonPower(state, world, pos, side);
    }

    public int m_6378_(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return this.getCommonPower(state, world, pos, side);
    }

    public int getCommonPower(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        if (side != state.m_61143_((Property)ROTATION)) {
            return 0;
        }
        BlockEntity tile = world.m_7702_(pos);
        return tile instanceof AdvancedComparatorTileEntity ? ((AdvancedComparatorTileEntity)tile).getOutputSignal() : 0;
    }

    protected int m_52547_(LevelReader worldIn, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.m_61143_((Property)FACING);
        Direction rotation = (Direction)state.m_61143_((Property)ROTATION);
        Direction rotation1 = DirectionList.rotateAround(rotation, direction.m_122434_());
        Direction rotation2 = DirectionList.rotateAround(rotation.m_122424_(), direction.m_122434_());
        return Math.max(this.m_52551_(worldIn, pos.m_121945_(rotation1), rotation1), this.m_52551_(worldIn, pos.m_121945_(rotation2), rotation2));
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        Vec3 hit = context.m_43720_().m_82492_((double)context.m_8083_().m_123341_(), (double)context.m_8083_().m_123342_(), (double)context.m_8083_().m_123343_());
        return (BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)context.m_43719_().m_122424_())).m_61124_((Property)ROTATION, (Comparable)AdvancedComparatorBlock.getDefaultForAxis(context.m_43719_().m_122424_().m_122434_(), (float)hit.m_7096_(), (float)hit.m_7098_(), (float)hit.m_7094_()));
    }

    protected void m_52580_(Level worldIn, BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.m_61143_((Property)ROTATION);
        BlockPos blockpos = pos.m_121945_(direction.m_122424_());
        if (ForgeEventFactory.onNeighborNotify((Level)worldIn, (BlockPos)pos, (BlockState)worldIn.m_8055_(pos), EnumSet.of(direction.m_122424_()), (boolean)false).isCanceled()) {
            return;
        }
        worldIn.m_46586_(blockpos, (Block)this, pos);
        worldIn.m_46590_(blockpos, (Block)this, direction);
    }

    public boolean m_52573_(BlockGetter worldIn, BlockPos pos, BlockState state) {
        Direction direction = ((Direction)state.m_61143_((Property)ROTATION)).m_122424_();
        BlockState blockstate = worldIn.m_8055_(pos.m_121945_(direction));
        if (!AdvancedComparatorBlock.m_52586_((BlockState)blockstate)) {
            return false;
        }
        if (blockstate.m_61138_((Property)ROTATION)) {
            return blockstate.m_61143_((Property)ROTATION) != direction;
        }
        if (blockstate.m_61138_((Property)DiodeBlock.f_54117_)) {
            return blockstate.m_61143_((Property)DiodeBlock.f_54117_) != direction;
        }
        return false;
    }

    public BlockState m_6843_(BlockState state, Rotation rot) {
        Direction dir = (Direction)state.m_61143_((Property)FACING);
        Direction rotatedDir = rot.m_55954_((Direction)state.m_61143_((Property)FACING));
        Direction rotation = (Direction)state.m_61143_((Property)ROTATION);
        return (BlockState)((BlockState)state.m_61124_((Property)FACING, (Comparable)rotatedDir)).m_61124_((Property)ROTATION, (Comparable)AdvancedComparatorBlock.rotate(AdvancedComparatorBlock.getDefaultForAxis(rotatedDir.m_122434_()), AdvancedComparatorBlock.getRotation(rotation, dir.m_122434_()), rotatedDir.m_122434_()));
    }

    public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
        return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_((Property)FACING)));
    }

    public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        this.onStateChange((Level)worldIn, pos, state);
    }

    public boolean getWeakChanges(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
        if (pos.m_123342_() == neighbor.m_123342_() && world instanceof Level && !world.m_5776_()) {
            state.m_60690_((Level)world, pos, world.m_8055_(neighbor).m_60734_(), neighbor, false);
        }
    }

    public static Direction getDefaultForAxis(Direction.Axis side, float x, float y, float z) {
        switch (side) {
            case X: {
                return !AdvancedComparatorBlock.getDifference(y, z) ? (y > 0.5f ? Direction.DOWN : Direction.UP) : (z > 0.5f ? Direction.NORTH : Direction.SOUTH);
            }
            case Y: {
                return !AdvancedComparatorBlock.getDifference(x, z) ? (x > 0.5f ? Direction.WEST : Direction.EAST) : (z > 0.5f ? Direction.NORTH : Direction.SOUTH);
            }
            case Z: {
                return !AdvancedComparatorBlock.getDifference(y, x) ? (y > 0.5f ? Direction.DOWN : Direction.UP) : (x > 0.5f ? Direction.WEST : Direction.EAST);
            }
        }
        return Direction.NORTH;
    }

    public static boolean getDifference(float x, float y) {
        return x < 0.5f ? (y >= 0.5f ? 1.0f - y < x : y < x) : (y < 0.5f ? 1.0f - y > x : y > x);
    }

    public static Direction getDefaultForAxis(Direction.Axis side) {
        switch (side) {
            case X: {
                return Direction.UP;
            }
            case Y: {
                return Direction.NORTH;
            }
            case Z: {
                return Direction.UP;
            }
        }
        return Direction.NORTH;
    }

    public static Direction rotate(Direction base, int count, Direction.Axis axis) {
        for (int i = 0; i < count; ++i) {
            base = DirectionList.rotateAround(base, axis);
        }
        return base;
    }

    public static Direction getFacingForRotation(Direction.Axis axis, int rotation) {
        switch (axis) {
            case X: {
                switch (rotation) {
                    case 0: {
                        return Direction.UP;
                    }
                    case 1: {
                        return Direction.NORTH;
                    }
                    case 2: {
                        return Direction.DOWN;
                    }
                    case 3: {
                        return Direction.SOUTH;
                    }
                }
                return null;
            }
            case Y: {
                return rotation == -1 ? null : Direction.m_122407_((int)rotation);
            }
            case Z: {
                switch (rotation) {
                    case 0: {
                        return Direction.UP;
                    }
                    case 1: {
                        return Direction.EAST;
                    }
                    case 2: {
                        return Direction.DOWN;
                    }
                    case 3: {
                        return Direction.WEST;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static int getRotation(Direction dir, Direction.Axis axis) {
        switch (axis) {
            case X: {
                switch (dir) {
                    case UP: {
                        return 0;
                    }
                    case NORTH: {
                        return 1;
                    }
                    case DOWN: {
                        return 2;
                    }
                    case SOUTH: {
                        return 3;
                    }
                }
                return -1;
            }
            case Y: {
                return dir.m_122416_();
            }
            case Z: {
                switch (dir) {
                    case UP: {
                        return 0;
                    }
                    case EAST: {
                        return 1;
                    }
                    case DOWN: {
                        return 2;
                    }
                    case WEST: {
                        return 3;
                    }
                }
                return -1;
            }
        }
        return -1;
    }
}

