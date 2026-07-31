/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ReferenceMap
 *  it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.PipeBlock
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.wiring;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IColoredEnergyTile;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.info.ILocatable;
import ic2.core.block.ChunkLoadAwareBlock;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.FoamCableBlock;
import ic2.core.item.tool.ItemToolCutter;
import ic2.core.ref.Ic2BlockTags;
import ic2.core.ref.Ic2Fluids;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractCableBlock
extends PipeBlock
implements ChunkLoadAwareBlock,
SimpleWaterloggedBlock {
    public static final DyeColor DEFAULT_COLOR = DyeColor.BLACK;
    public static final EnumProperty<DyeColor> colorProperty = EnumProperty.m_61587_((String)"color", DyeColor.class);
    public static final EnumProperty<CableFoam> foamProperty = EnumProperty.m_61587_((String)"foam", CableFoam.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;
    public static final BooleanProperty UP = BlockStateProperties.f_61366_;
    public static final BooleanProperty DOWN = BlockStateProperties.f_61367_;
    public static final BooleanProperty NORTH = BlockStateProperties.f_61368_;
    public static final BooleanProperty EAST = BlockStateProperties.f_61369_;
    public static final BooleanProperty SOUTH = BlockStateProperties.f_61370_;
    public static final BooleanProperty WEST = BlockStateProperties.f_61371_;
    private static final Map<CableType, Int2ReferenceMap<AbstractCableBlock>> types = new EnumMap<CableType, Int2ReferenceMap<AbstractCableBlock>>(CableType.class);
    private static boolean pendingHasColor;
    private boolean hasColor;
    final CableType type;
    final int insulation;

    public abstract boolean isFoam();

    public abstract boolean isHardFoam(BlockState var1);

    public void initializeState(BlockState blockState) {
        if (this.isFoam()) {
            blockState = (BlockState)blockState.m_61124_(foamProperty, (Comparable)((Object)FoamCableBlock.DEFAULT_FOAM));
        } else {
            blockState = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.m_61124_((Property)UP, (Comparable)Boolean.valueOf(false))).m_61124_((Property)DOWN, (Comparable)Boolean.valueOf(false))).m_61124_((Property)NORTH, (Comparable)Boolean.valueOf(false))).m_61124_((Property)EAST, (Comparable)Boolean.valueOf(false))).m_61124_((Property)SOUTH, (Comparable)Boolean.valueOf(false))).m_61124_((Property)WEST, (Comparable)Boolean.valueOf(false));
            if (this.hasColor()) {
                blockState = (BlockState)blockState.m_61124_(colorProperty, (Comparable)DEFAULT_COLOR);
            }
        }
        this.m_49959_(blockState);
    }

    protected BlockState copyState(BlockState blockState, AbstractCableBlock abstractCableBlock) {
        BlockState blockState2 = abstractCableBlock.m_49966_();
        if (abstractCableBlock.insulation >= abstractCableBlock.type.minColoredInsulation) {
            blockState2 = (BlockState)blockState2.m_61124_(colorProperty, (Comparable)((AbstractCableBlock)blockState.m_60734_()).getColor(blockState));
        }
        blockState2 = this.isFoam() ? (BlockState)blockState2.m_61124_(foamProperty, (Comparable)((Object)((CableFoam)((Object)blockState.m_61143_(foamProperty))))) : (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState2.m_61124_((Property)UP, (Comparable)((Boolean)blockState.m_61143_((Property)UP)))).m_61124_((Property)DOWN, (Comparable)((Boolean)blockState.m_61143_((Property)DOWN)))).m_61124_((Property)NORTH, (Comparable)((Boolean)blockState.m_61143_((Property)NORTH)))).m_61124_((Property)EAST, (Comparable)((Boolean)blockState.m_61143_((Property)EAST)))).m_61124_((Property)SOUTH, (Comparable)((Boolean)blockState.m_61143_((Property)SOUTH)))).m_61124_((Property)WEST, (Comparable)((Boolean)blockState.m_61143_((Property)WEST)));
        return blockState2;
    }

    protected static void prepareCreate(CableType cableType, int n) {
        pendingHasColor = n >= cableType.minColoredInsulation;
    }

    protected AbstractCableBlock(BlockBehaviour.Properties properties, CableType cableType2, int n) {
        super(cableType2.getThickness(n) / 2.0f, properties);
        if (n > cableType2.maxInsulation) {
            throw new IllegalArgumentException("invalid insulation " + n + " for type " + cableType2);
        }
        this.type = cableType2;
        this.insulation = n;
        this.hasColor = n >= cableType2.minColoredInsulation;
        BlockState blockState = (BlockState)this.f_49792_.m_61090_();
        if (!this.isFoam()) {
            blockState = (BlockState)blockState.m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false));
        }
        this.initializeState(blockState);
        if (cableType2.maxInsulation > 0) {
            types.computeIfAbsent(cableType2, cableType -> new Int2ReferenceOpenHashMap(cableType.maxInsulation + 1)).put(n, (Object)this);
        }
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        if (this.isFoam()) {
            builder.m_61104_(new Property[]{foamProperty});
        } else {
            builder.m_61104_(new Property[]{WATERLOGGED, UP, DOWN, NORTH, EAST, SOUTH, WEST});
        }
        if (pendingHasColor) {
            builder.m_61104_(new Property[]{colorProperty});
        }
    }

    public FluidState m_5888_(BlockState blockState) {
        if (!this.isFoam() && ((Boolean)blockState.m_61143_((Property)WATERLOGGED)).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return Fluids.f_76191_.m_76145_();
    }

    public BlockState withConnectionStates(BlockState blockState, Level level, BlockPos blockPos) {
        boolean bl = this.isConnectedWith(blockState, null, level, blockPos.m_7495_());
        boolean bl2 = this.isConnectedWith(blockState, null, level, blockPos.m_7494_());
        boolean bl3 = this.isConnectedWith(blockState, null, level, blockPos.m_122012_());
        boolean bl4 = this.isConnectedWith(blockState, null, level, blockPos.m_122029_());
        boolean bl5 = this.isConnectedWith(blockState, null, level, blockPos.m_122019_());
        boolean bl6 = this.isConnectedWith(blockState, null, level, blockPos.m_122024_());
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.m_61124_((Property)DOWN, (Comparable)Boolean.valueOf(bl))).m_61124_((Property)UP, (Comparable)Boolean.valueOf(bl2))).m_61124_((Property)NORTH, (Comparable)Boolean.valueOf(bl3))).m_61124_((Property)EAST, (Comparable)Boolean.valueOf(bl4))).m_61124_((Property)SOUTH, (Comparable)Boolean.valueOf(bl5))).m_61124_((Property)WEST, (Comparable)Boolean.valueOf(bl6));
    }

    public boolean isConnectedWith(BlockState blockState, BlockState blockState2, Level level, BlockPos blockPos) {
        Block block;
        if (blockState2 == null) {
            blockState2 = level.m_8055_(blockPos);
        }
        if ((block = blockState2.m_60734_()) instanceof AbstractCableBlock) {
            AbstractCableBlock abstractCableBlock = (AbstractCableBlock)block;
            if (!abstractCableBlock.hasColor() || !this.hasColor()) {
                return true;
            }
            return AbstractCableBlock.getColor(blockState2, abstractCableBlock.type, abstractCableBlock.insulation) == this.getColor(blockState);
        }
        return blockState2.m_204336_(Ic2BlockTags.CABLE_CONNECTABLE);
    }

    public BlockState m_5573_(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = this.m_49966_();
        Fluid fluid = blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_()).m_76152_();
        if (fluid == Fluids.f_76193_) {
            blockState = (BlockState)blockState.m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(true));
        }
        if (!this.isFoam()) {
            blockState = this.withConnectionStates(blockState, blockPlaceContext.m_43725_(), blockPlaceContext.m_8083_());
        } else if (fluid == Ic2Fluids.CONSTRUCTION_FOAM.still) {
            blockState = (BlockState)blockState.m_61124_(foamProperty, (Comparable)((Object)CableFoam.SOFT));
        }
        return blockState;
    }

    public VoxelShape m_7952_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        if (!this.isFoam()) {
            return super.m_7952_(blockState, blockGetter, blockPos);
        }
        if (((CableFoam)((Object)blockState.m_61143_(foamProperty))).isSoft()) {
            return Shapes.m_83040_();
        }
        return super.m_7952_(blockState, blockGetter, blockPos);
    }

    public VoxelShape m_5940_(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (!this.isFoam()) {
            return super.m_5940_(blockState, blockGetter, blockPos, collisionContext);
        }
        if (((CableFoam)((Object)blockState.m_61143_(foamProperty))).isPresent()) {
            return Shapes.m_83144_();
        }
        return Shapes.m_83040_();
    }

    public BlockState m_7417_(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (!this.isFoam() && ((Boolean)blockState.m_61143_((Property)WATERLOGGED)).booleanValue()) {
            levelAccessor.m_186469_(blockPos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)levelAccessor));
        }
        if (!this.isFoam()) {
            boolean bl = this.isConnectedWith(blockState, blockState2, (Level)levelAccessor, blockPos2);
            return (BlockState)blockState.m_61124_((Property)PipeBlock.f_55154_.get(direction), (Comparable)Boolean.valueOf(bl));
        }
        return super.m_7417_(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    public boolean m_6044_(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return !this.isFoam() && (Boolean)blockState.m_61143_((Property)WATERLOGGED) == false && fluid == Fluids.f_76193_;
    }

    public boolean m_7361_(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!this.m_6044_((BlockGetter)levelAccessor, blockPos, blockState, fluidState.m_76152_())) {
            return false;
        }
        if (!levelAccessor.m_5776_()) {
            levelAccessor.m_7731_(blockPos, (BlockState)blockState.m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(true)), 3);
            levelAccessor.m_186469_(blockPos, fluidState.m_76152_(), fluidState.m_76152_().m_6718_((LevelReader)levelAccessor));
        }
        return true;
    }

    public ItemStack m_142598_(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        if (this.isFoam() || !((Boolean)blockState.m_61143_((Property)WATERLOGGED)).booleanValue()) {
            return ItemStack.f_41583_;
        }
        levelAccessor.m_7731_(blockPos, (BlockState)blockState.m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false)), 3);
        return new ItemStack((ItemLike)Items.f_42447_);
    }

    public void m_6256_(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        if (this.isHardFoam(blockState)) {
            return;
        }
        ItemStack itemStack = player.m_21205_();
        Item item = itemStack.m_41720_();
        if (item instanceof ItemToolCutter) {
            ((ItemToolCutter)item).removeInsulation(player, player.m_7655_(), blockState, level, blockPos);
        }
    }

    public boolean hasColor() {
        return this.hasColor;
    }

    DyeColor getColor(BlockState blockState) {
        return AbstractCableBlock.getColor(blockState, this.type, this.insulation);
    }

    public static DyeColor getColor(BlockState blockState, CableType cableType, int n) {
        if (n >= cableType.minColoredInsulation) {
            return (DyeColor)blockState.m_61143_(colorProperty);
        }
        return DEFAULT_COLOR;
    }

    public boolean tryAddInsulation(BlockState blockState, Level level, BlockPos blockPos) {
        if (this.insulation >= this.type.maxInsulation) {
            return false;
        }
        if (this.isHardFoam(blockState)) {
            return false;
        }
        AbstractCableBlock abstractCableBlock = (AbstractCableBlock)types.get((Object)this.type).get(this.insulation + 1);
        if (abstractCableBlock == null) {
            return false;
        }
        level.m_46597_(blockPos, this.copyState(blockState, abstractCableBlock));
        return true;
    }

    public boolean tryRemoveInsulation(BlockState blockState, Level level, BlockPos blockPos, boolean bl) {
        if (this.insulation <= 0) {
            return false;
        }
        if (this.isHardFoam(blockState)) {
            return false;
        }
        AbstractCableBlock abstractCableBlock = (AbstractCableBlock)types.get((Object)this.type).get(this.insulation - 1);
        if (abstractCableBlock == null) {
            return false;
        }
        if (bl) {
            return true;
        }
        BlockState blockState2 = this.copyState(blockState, abstractCableBlock);
        IEnergyTile iEnergyTile = null;
        if (this.insulation == this.type.minColoredInsulation && this.getColor(blockState) != DEFAULT_COLOR) {
            assert (abstractCableBlock.getColor(blockState2) == DEFAULT_COLOR);
            if (!level.f_46443_ && (iEnergyTile = EnergyNet.instance.getTile(level, blockPos)) != null) {
                EnergyNet.instance.removeTile(iEnergyTile);
            }
        }
        level.m_46597_(blockPos, blockState2);
        if (iEnergyTile != null) {
            EnergyNet.instance.addTileUnchecked(iEnergyTile);
        }
        return true;
    }

    public void m_6810_(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.m_60713_((Block)this) || this.getColor(blockState2) != this.getColor(blockState)) {
            this.removeFromEnet(blockState, level, blockPos);
        }
    }

    public void m_6807_(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        this.addToEnet(blockState, level, blockPos, true);
    }

    @Override
    public void onLoad(BlockState blockState, Level level, BlockPos blockPos) {
        this.addToEnet(blockState, level, blockPos, false);
    }

    @Override
    public void onUnload(BlockState blockState, Level level, BlockPos blockPos) {
        this.removeFromEnet(blockState, level, blockPos);
    }

    protected void addToEnet(BlockState blockState, Level level, BlockPos blockPos, boolean bl) {
        if (bl && EnergyNet.instance.getTile(level, blockPos) != null) {
            return;
        }
        EnergyNet.instance.addLocatableTile(new Conductor(blockState, level, blockPos));
    }

    protected void removeFromEnet(BlockState blockState, Level level, BlockPos blockPos) {
        IEnergyTile iEnergyTile = EnergyNet.instance.getTile(level, blockPos);
        if (iEnergyTile != null) {
            EnergyNet.instance.removeTile(iEnergyTile);
        }
    }

    private final class Conductor
    implements ILocatable,
    IColoredEnergyTile,
    IEnergyConductor {
        private BlockState state;
        private final Level world;
        private final BlockPos pos;

        Conductor(BlockState blockState, Level level, BlockPos blockPos) {
            this.state = blockState;
            this.world = level;
            this.pos = blockPos.m_7949_();
        }

        @Override
        public Level getWorldObj() {
            return this.world;
        }

        @Override
        public BlockPos getPosition() {
            return this.pos;
        }

        @Override
        public DyeColor getColor(Direction direction) {
            return AbstractCableBlock.getColor(this.state, AbstractCableBlock.this.type, AbstractCableBlock.this.insulation);
        }

        @Override
        public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, Direction direction) {
            return this.canInteractWith(iEnergyEmitter, direction);
        }

        @Override
        public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
            return this.canInteractWith(iEnergyAcceptor, direction);
        }

        @Override
        public double getConductionLoss() {
            return AbstractCableBlock.this.type.loss;
        }

        @Override
        public double getInsulationEnergyAbsorption() {
            if (AbstractCableBlock.this.type.maxInsulation == 0) {
                return 2.147483647E9;
            }
            if (AbstractCableBlock.this.type.capacity < 128) {
                return EnergyNet.instance.getPowerFromTier(AbstractCableBlock.this.insulation);
            }
            return EnergyNet.instance.getPowerFromTier(AbstractCableBlock.this.insulation + 1);
        }

        @Override
        public double getInsulationBreakdownEnergy() {
            return 9001.0;
        }

        @Override
        public double getConductorBreakdownEnergy() {
            return AbstractCableBlock.this.type.capacity + 1;
        }

        @Override
        public void removeInsulation() {
            AbstractCableBlock.this.tryRemoveInsulation(this.state, this.world, this.pos, false);
        }

        @Override
        public void removeConductor() {
            this.world.m_7471_(this.pos, false);
        }

        @Override
        public void onConnectionChange() {
        }

        private void setState(BlockState blockState) {
            assert (blockState != this.state);
            this.state = blockState;
            this.world.m_46597_(this.pos, blockState);
        }
    }
}

