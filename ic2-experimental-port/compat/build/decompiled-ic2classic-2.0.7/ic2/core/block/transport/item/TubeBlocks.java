/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 */
package ic2.core.block.transport.item;

import ic2.api.core.IC2Classic;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.misc.CFoamBlock;
import ic2.core.block.rendering.block.tubes.MultiTubeModel;
import ic2.core.block.rendering.block.tubes.PipeModel;
import ic2.core.block.transport.item.TubeBlock;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.NonTranslucentBlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class TubeBlocks {

    public static class PersonalTubeBlock
    extends TubeBlock {
        public PersonalTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String texture) {
            super(name, type, texture);
        }

        public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
            if (IC2.PLATFORM.isRendering() || !(placer instanceof Player)) {
                return;
            }
            BlockEntity tile = worldIn.m_7702_(pos);
            if (!(tile instanceof IPersonalTile)) {
                return;
            }
            IPersonalTile base = (IPersonalTile)tile;
            base.setOwner(placer.m_20148_());
        }
    }

    public static class PriorityMultiDirTubeBlock
    extends MultiDirectionalTubeBlock {
        public static final IntegerProperty PRIORITY = IntegerProperty.m_61631_((String)"priority", (int)0, (int)6);

        public PriorityMultiDirTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, int searchState, String ... texture) {
            super(name, type, searchState, texture);
        }

        @Override
        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{PRIORITY});
        }

        @Override
        public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
            tile.setState((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)PRIORITY, (Comparable)Integer.valueOf(tile.getPrioritySide())));
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            int priority = (Integer)state.m_61143_((Property)PRIORITY);
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[dir == state.m_61143_((Property)FACING) ? 1 : (priority > 0 && Direction.m_122376_((int)(priority - 1)) == dir ? 2 : 0)]);
        }
    }

    public static class MultiDirectionalTubeBlock
    extends DirectionalTubeBlock {
        public MultiDirectionalTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, int searchState, String ... texture) {
            super(name, type, searchState, texture);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public BaseModel getForCustomState(BlockState state) {
            return (Integer)state.m_61143_((Property)FOAMED) == 1 ? super.getForCustomState(state) : new MultiTubeModel(this, state);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[dir == state.m_61143_((Property)FACING) ? 1 : 0]);
        }

        @Override
        public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
            tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)FACING, (Comparable)tile.getFacing()));
        }
    }

    public static class ClosedDirectionalTube
    extends DirectionalTubeBlock {
        public ClosedDirectionalTube(String name, BlockEntityType<? extends BlockEntity> type, int searchState, String ... texture) {
            super(name, type, searchState, texture);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[side == state.m_61143_((Property)FACING) ? 1 : 0]);
        }
    }

    public static class DirectionalTubeBlock
    extends TubeBlock {
        public static final int AUTO_CONNECT_TUBE = 1;
        public static final int AUTO_CONNECT_INVENTORY = 2;
        public static final int AUTO_CONNECT_INVENTORY_TUBE = 3;
        public static final int AUTO_CONNECT_NOTHING = 4;
        public static final DirectionProperty FACING = IC2Properties.ALL_FACINGS;
        String[] textures;
        int searchState;

        public DirectionalTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, int searchState, String ... texture) {
            super(name, type, "");
            this.searchState = searchState;
            this.textures = texture;
        }

        @Override
        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{FACING});
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[dir == state.m_61143_((Property)FACING) ? 1 : 0]);
        }

        @Override
        public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
            tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)FACING, (Comparable)tile.getFacing()));
        }

        @Override
        public BlockState m_5573_(BlockPlaceContext context) {
            return (BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)this.createList(context.m_43725_(), context.m_8083_()).getRandomFacing());
        }

        protected DirectionList createList(Level world, BlockPos pos) {
            DirectionList list = DirectionList.EMPTY;
            for (Direction dir : DirectionList.ALL) {
                BlockEntity tile = DirectionList.getNeighborTile(world, pos, dir);
                if (tile != null && ((this.searchState & 1) != 0 && tile.getCapability(IC2Classic.TUBE_CAPABILITY, dir.m_122424_()).isPresent() || (this.searchState & 2) != 0 && tile.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.m_122424_()).isPresent())) {
                    list = list.add(dir);
                    continue;
                }
                if ((this.searchState & 4) == 0) continue;
                list = list.add(dir);
            }
            return list.isEmpty() ? DirectionList.NORTH : list;
        }

        public void m_6402_(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
            if (IC2.PLATFORM.isRendering()) {
                return;
            }
            BlockEntity tile = worldIn.m_7702_(pos);
            if (!(tile instanceof BaseTileEntity)) {
                return;
            }
            BaseTileEntity base = (BaseTileEntity)tile;
            base.lock();
            base.setFacing((Direction)state.m_61143_((Property)FACING));
            if (stack.m_41788_()) {
                base.setCustomName(stack.m_41786_());
            }
            base.unlock();
        }
    }

    public static class PipeTubeBlock
    extends TubeBlock {
        public PipeTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String texture) {
            super(name, type, texture);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public BaseModel getForCustomState(BlockState state) {
            return (Integer)state.m_61143_((Property)FOAMED) == 1 ? new NonTranslucentBlockModel((BlockState)IC2Blocks.CFOAM_WET.m_49966_().m_61124_(CFoamBlock.FOAM_TYPE, (Comparable)((Object)CFoamBlock.FoamType.CABLE)), (IBlockModel)IC2Blocks.CFOAM_WET) : new PipeModel(this, state);
        }
    }

    public static class StickyTubeBlock
    extends PriorityTubeBlock {
        public StickyTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String ... texture) {
            super(name, type, 0, texture);
        }

        @Override
        protected DirectionList createList(Level world, BlockPos pos) {
            return DirectionList.UP;
        }
    }

    public static class PriorityTubeBlock
    extends DirectionalTubeBlock {
        public static final IntegerProperty PRIORITY = IntegerProperty.m_61631_((String)"priority", (int)0, (int)6);

        public PriorityTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, int searchState, String ... texture) {
            super(name, type, searchState, texture);
        }

        @Override
        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{PRIORITY});
        }

        @Override
        public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
            tile.setState((BlockState)((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)FACING, (Comparable)tile.getFacing())).m_61124_((Property)PRIORITY, (Comparable)Integer.valueOf(tile.getPrioritySide())));
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            int priority = (Integer)state.m_61143_((Property)PRIORITY);
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[dir == state.m_61143_((Property)FACING) ? 1 : (priority > 0 && Direction.m_122376_((int)(priority - 1)) == dir ? 2 : 0)]);
        }
    }

    public static class ActivatableTubeBlock
    extends TubeBlock {
        String[] textures;
        public static final BooleanProperty ACTIVE = IC2Properties.ACTIVE;

        public ActivatableTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String ... textures) {
            super(name, type, "");
            this.textures = textures;
            this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false)));
        }

        @Override
        protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
            super.m_7926_(builder);
            builder.m_61104_(new Property[]{ACTIVE});
        }

        @Override
        public void onStateUpdate(Level world, BlockPos pos, BlockState state, TubeTileEntity tile) {
            tile.setState((BlockState)((BlockState)((BlockState)state.m_61124_((Property)FOAMED, (Comparable)Integer.valueOf(tile.foamed))).m_61124_((Property)WATER, (Comparable)Boolean.valueOf((Boolean)state.m_61143_((Property)WATER) != false && tile.foamed == 0))).m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive())));
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[(Boolean)state.m_61143_((Property)ACTIVE) != false ? 1 : 0]);
        }
    }

    public static class SixSidedTubeBlock
    extends TubeBlock {
        String[] textures;

        public SixSidedTubeBlock(String name, BlockEntityType<? extends BlockEntity> type, String ... textures) {
            super(name, type, "");
            if (textures.length != 7) {
                throw new IllegalStateException("7 Textures are needed");
            }
            this.textures = textures;
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public TextureAtlasSprite getTexture(BlockState state, boolean center, Direction dir, Direction side) {
            return IC2Textures.getMappedEntriesBlockIC2("transport/tubes").get(this.textures[dir == null ? 6 : dir.m_122411_()]);
        }
    }
}

