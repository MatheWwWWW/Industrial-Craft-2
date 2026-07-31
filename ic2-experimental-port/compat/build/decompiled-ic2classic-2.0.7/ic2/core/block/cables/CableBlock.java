/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.BooleanOp
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.cables;

import ic2.api.blocks.PainterHelper;
import ic2.api.items.ICutterItem;
import ic2.api.util.DirectionList;
import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.misc.IDualLogged;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.cables.CableTileEntity;
import ic2.core.block.misc.CFoamBlock;
import ic2.core.block.rendering.block.CamouflageModel;
import ic2.core.block.rendering.block.tubes.CableModel;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.item.tool.SprayerTool;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.NonTranslucentBlockModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Tool;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public abstract class CableBlock
extends IC2ContainerBlock
implements ICustomBlockModel,
IStateController<CableTileEntity>,
IDualLogged,
ICamouflageBlock,
ITubeBlock,
PainterHelper.IPaintable {
    public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;
    public static final BooleanProperty WATER = BlockStateProperties.f_61362_;
    public static final BooleanProperty LAVA = IC2Properties.LAVA_LOGGED;
    public static final IntegerProperty COLOR = IntegerProperty.m_61631_((String)"color", (int)0, (int)16);
    public static final IntegerProperty FOAMED = IntegerProperty.m_61631_((String)"foamed", (int)0, (int)2);
    public static final IntegerProperty[] INSULATION = new IntegerProperty[]{IntegerProperty.m_61631_((String)"insulation", (int)0, (int)1), IntegerProperty.m_61631_((String)"insulation", (int)0, (int)2), IntegerProperty.m_61631_((String)"insulation", (int)0, (int)3), IntegerProperty.m_61631_((String)"insulation", (int)0, (int)4), IntegerProperty.m_61631_((String)"insulation", (int)0, (int)5)};
    public static final VoxelShape FULL_CUBE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)16.0, (double)16.0);
    final VoxelShape[][] shapes;
    CableInstance instance;
    BlockEntityType<? extends CableTileEntity> cableTile;

    protected CableBlock(String blockName, BlockEntityType<? extends CableTileEntity> cableTile) {
        super(blockName, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60955_().m_60924_((T, V, X) -> false).m_60918_(SoundType.f_56745_).m_60913_(0.2f, 1.0f));
        this.setHarvestTool(Tool.AXE);
        this.shapes = this.getCable().createShapes();
        this.cableTile = cableTile;
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false)));
    }

    public boolean m_6864_(BlockState state, BlockPlaceContext useContext) {
        return useContext.m_43722_().m_41720_() instanceof SprayerTool && (Integer)state.m_61143_((Property)FOAMED) <= 0;
    }

    public static CableBlock createBlock(String name, final CableInstance cable, BlockEntityType<? extends CableTileEntity> creator) {
        return new CableBlock(name, creator){

            @Override
            public CableInstance createInstance() {
                return cable;
            }
        };
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    @Override
    public int getHighlightColor(BlockState state) {
        return ColorUtils.rgb(175, 175, 0);
    }

    public BlockState withInsulation(int level) {
        return this.hasInsulation() ? (BlockState)this.m_49966_().m_61124_((Property)this.getInsulation(), (Comparable)Integer.valueOf(level)) : this.m_49966_();
    }

    public BlockState withActivity(boolean active) {
        return this.instance.hasActivity ? (BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(active)) : this.m_49966_();
    }

    protected CableInstance getCable() {
        return this.instance;
    }

    public abstract CableInstance createInstance();

    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem((Block)this).disableCreative();
    }

    @Override
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return this.cableTile.m_155264_(pos, state);
    }

    @Override
    public boolean recolor(BlockState state, Level world, BlockPos pos, Vec3 exactClick, Direction dir, DyeColor color) {
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof CableTileEntity) {
            return ((CableTileEntity)tile).setColor(dir, color);
        }
        return false;
    }

    @Override
    public DyeColor getColor(BlockState state) {
        return this.getBlockColor(state);
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        FluidState state = context.m_43725_().m_6425_(context.m_8083_());
        return (BlockState)((BlockState)super.m_5573_(context).m_61124_((Property)WATER, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76193_))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(state.m_76152_() == Fluids.f_76195_));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
        int insulation = this.getInsulation(state);
        CableInstance instance = this.getCable();
        if (insulation == 0 && !instance.usesUninsulatedColor) {
            return IC2Textures.getMappedEntriesBlockIC2(instance.folder).get(instance.textures.get(instance.hasActivity && (Boolean)state.m_61143_((Property)ACTIVE) != false ? 1 : 0));
        }
        return IC2Textures.getMappedEntriesBlockIC2(instance.getTexture(insulation)).get(this.getCable().usesColor ? this.getTextureColor(state) : "blank");
    }

    @Override
    public List<BlockState> getCustomStates() {
        return this.m_49965_().m_61056_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        switch ((Integer)state.m_61143_((Property)FOAMED)) {
            case 1: {
                return new NonTranslucentBlockModel((BlockState)IC2Blocks.CFOAM_WET.m_49966_().m_61124_(CFoamBlock.FOAM_TYPE, (Comparable)((Object)CFoamBlock.FoamType.CABLE)), (IBlockModel)IC2Blocks.CFOAM_WET);
            }
            case 2: {
                return CamouflageModel.getInstance();
            }
        }
        return new CableModel(this, state);
    }

    public String getTextureColor(BlockState state) {
        int index = (Integer)state.m_61143_((Property)COLOR);
        return index == 0 ? "blank" : DyeColor.m_41053_((int)(index - 1)).m_41065_();
    }

    @Override
    public float getRadius(BlockState state) {
        return this.getCable().widths[this.getInsulation(state)];
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if ((Integer)state.m_61143_((Property)FOAMED) > 0) {
            return Shapes.m_83144_();
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof CableTileEntity) {
            CableTileEntity cable = (CableTileEntity)tile;
            return this.shapes[this.getInsulation(state)][cable.getConnectivity()];
        }
        return this.shapes[0][0];
    }

    @Override
    public VoxelShape[] getRealShapes(BlockState state, BlockGetter reader, BlockPos pos) {
        BlockEntity tile = reader.m_7702_(pos);
        if (tile instanceof CableTileEntity) {
            CableTileEntity cable = (CableTileEntity)tile;
            VoxelShape[] array = this.shapes[this.getInsulation(state)];
            return new VoxelShape[]{array[cable.getConnections()], Shapes.m_83113_((VoxelShape)array[0], (VoxelShape)array[cable.getAnchors()], (BooleanOp)BooleanOp.f_82683_)};
        }
        return new VoxelShape[]{this.shapes[0][0], Shapes.m_83040_()};
    }

    @Override
    public DyeColor getBlockColor(BlockState state) {
        if (!state.m_61138_((Property)COLOR)) {
            return null;
        }
        int index = (Integer)state.m_61143_((Property)COLOR);
        return index == 0 ? null : DyeColor.m_41053_((int)(index - 1));
    }

    public final boolean hasInsulation() {
        return this.getCable().insulation > 0;
    }

    public final IntegerProperty getInsulation() {
        return INSULATION[this.getCable().insulation - 1];
    }

    public int getInsulation(BlockState state) {
        return this.hasInsulation() ? (Integer)state.m_61143_((Property)this.getInsulation()) : 0;
    }

    public FluidState m_5888_(BlockState state) {
        return IDualLogged.getFluidState(state);
    }

    @Override
    public boolean m_6044_(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IDualLogged.super.m_6044_(worldIn, pos, state, fluidIn) && (Integer)state.m_61143_((Property)FOAMED) <= 0;
    }

    @Override
    public boolean m_7361_(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (IDualLogged.super.m_7361_(world, pos, state, fluid)) {
            BlockEntity tile;
            if (fluid.m_76152_() == Fluids.f_76195_ && (tile = world.m_7702_(pos)) instanceof CableTileEntity && ((CableTileEntity)tile).insulation > 0) {
                ((CableTileEntity)tile).removeInsulation();
                world.m_5594_(null, pos, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5f, 2.6f + (world.m_213780_().m_188501_() - world.m_213780_().m_188501_()) * 0.8f);
            }
            return true;
        }
        return false;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        this.instance = this.createInstance();
        if (this.getCable().hasActivity) {
            builder.m_61104_(new Property[]{ACTIVE});
        }
        if (this.getCable().usesColor) {
            builder.m_61104_(new Property[]{COLOR});
        }
        if (this.hasInsulation()) {
            builder.m_61104_(new Property[]{this.getInsulation()});
        }
        builder.m_61104_(new Property[]{WATER}).m_61104_(new Property[]{LAVA}).m_61104_(new Property[]{FOAMED});
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, CableTileEntity tile) {
        if (this.getCable().hasActivity) {
            state = (BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive()));
        }
        if (this.getCable().usesColor) {
            state = (BlockState)state.m_61124_((Property)COLOR, (Comparable)Integer.valueOf(tile.cableColor));
        }
        if (this.hasInsulation()) {
            state = (BlockState)state.m_61124_((Property)this.getInsulation(), (Comparable)Integer.valueOf(tile.insulation));
        }
        if (tile.foamed > 0) {
            state = (BlockState)((BlockState)state.m_61124_((Property)WATER, (Comparable)Boolean.valueOf(false))).m_61124_((Property)LAVA, (Comparable)Boolean.valueOf(false));
        }
        tile.setState((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed)));
    }

    @Override
    public ItemStack createDrop(BlockState state, ItemStack stack, RandomSource rand, BlockEntity tile, boolean wrench) {
        return this.instance.drops.apply(this.getInsulation(state));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockState rayState;
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof CableTileEntity && ((CableTileEntity)tile).foamed == 2 && target instanceof BlockHitResult && (rayState = ((CableTileEntity)tile).getStorage().getCache(((BlockHitResult)target).m_82434_()).getDisplayBlock()) != null) {
            try {
                return rayState.getCloneItemStack(target, world, pos, player);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return this.instance.drops.apply(this.getInsulation(state));
    }

    @Override
    public void m_6256_(BlockState state, Level worldIn, BlockPos pos, Player player) {
        ItemStack stack = player.m_21205_();
        if (stack.m_41720_() instanceof ICutterItem) {
            ((ICutterItem)stack.m_41720_()).cutInsulation(player, stack, worldIn, pos);
            return;
        }
        super.m_6256_(state, worldIn, pos, player);
    }

    public static class CableInstance {
        boolean usesInsulation;
        boolean usesColor;
        boolean usesUninsulatedColor = false;
        boolean needsTranslucency = false;
        boolean hasActivity;
        int insulation;
        float[] widths;
        String folder;
        List<String> textures = CollectionUtils.createList();
        IntFunction<ItemStack> drops;

        public CableInstance(boolean usesInsulation, boolean usesColor, boolean hasActivity, int insulation, float[] widths, String folder, IntFunction<ItemStack> drops) {
            this.usesInsulation = usesInsulation;
            this.usesColor = usesColor;
            this.hasActivity = hasActivity;
            this.insulation = insulation;
            this.widths = widths;
            this.folder = folder;
            this.drops = drops;
        }

        public CableInstance setNeedsTranslucency() {
            this.needsTranslucency = true;
            return this;
        }

        public CableInstance setHasUninsulatedColor() {
            this.usesUninsulatedColor = true;
            return this;
        }

        public CableInstance addTextures(String ... textures) {
            this.textures.addAll((Collection<String>)ObjectArrayList.wrap((Object[])textures));
            return this;
        }

        public String getTexture(int insulation) {
            return this.folder + "/" + this.textures.get(insulation);
        }

        VoxelShape[][] createShapes() {
            if (this.widths == null || this.widths.length <= 0) {
                throw new RuntimeException("Need at least 1 width");
            }
            VoxelShape[][] result = new VoxelShape[this.widths.length][64];
            for (int i = 0; i < this.widths.length; ++i) {
                VoxelShape[] shapes = this.createShapes(i);
                for (int j = 0; j < 64; ++j) {
                    DirectionList list = DirectionList.ofNumber(j);
                    VoxelShape[] entry = new VoxelShape[list.size()];
                    int index = 0;
                    for (Direction dir : list) {
                        entry[index++] = shapes[dir.m_122411_()];
                    }
                    result[i][j] = Shapes.m_83124_((VoxelShape)shapes[6], (VoxelShape[])entry);
                }
            }
            return result;
        }

        VoxelShape[] createShapes(int index) {
            VoxelShape[] result = new VoxelShape[7];
            float minValue = 8.0f - this.widths[index];
            float maxValue = 8.0f + this.widths[index];
            result[0] = Block.m_49796_((double)minValue, (double)0.0, (double)minValue, (double)maxValue, (double)minValue, (double)maxValue);
            result[1] = Block.m_49796_((double)minValue, (double)maxValue, (double)minValue, (double)maxValue, (double)16.0, (double)maxValue);
            result[2] = Block.m_49796_((double)minValue, (double)minValue, (double)0.0, (double)maxValue, (double)maxValue, (double)minValue);
            result[3] = Block.m_49796_((double)minValue, (double)minValue, (double)maxValue, (double)maxValue, (double)maxValue, (double)16.0);
            result[4] = Block.m_49796_((double)0.0, (double)minValue, (double)minValue, (double)minValue, (double)maxValue, (double)maxValue);
            result[5] = Block.m_49796_((double)maxValue, (double)minValue, (double)minValue, (double)16.0, (double)maxValue, (double)maxValue);
            result[6] = Block.m_49796_((double)minValue, (double)minValue, (double)minValue, (double)maxValue, (double)maxValue, (double)maxValue);
            return result;
        }
    }
}

