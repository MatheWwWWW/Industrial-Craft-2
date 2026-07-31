/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.events;

import ic2.api.blocks.PainterHelper;
import ic2.api.events.FoamEvent;
import ic2.api.events.RetextureEvent;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.cables.CableBlock;
import ic2.core.block.cables.CableTileEntity;
import ic2.core.block.cables.luminator.LuminatorTileEntity;
import ic2.core.block.misc.CFoamBlock;
import ic2.core.block.misc.PlayerDetectorBlock;
import ic2.core.block.misc.base.DyeableBlock;
import ic2.core.block.misc.base.IC2SlabBlock;
import ic2.core.block.misc.base.IC2StairsBlock;
import ic2.core.block.misc.base.IC2WallBlock;
import ic2.core.block.misc.base.ScaffoldBlock;
import ic2.core.block.misc.tiles.PlayerDetectorTileEntity;
import ic2.core.block.misc.tiles.TexturedBlockTileEntity;
import ic2.core.block.transport.fluid.PipeBlock;
import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.block.transport.item.TubeBlock;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.ColorMaps;
import ic2.core.platform.registries.IC2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InternalEventHandler {
    public static final InternalEventHandler INSTANCE = new InternalEventHandler();

    public static BlockState applyCamouflage(BlockState ownerState, BlockGetter world, BlockPos position, Direction side, BlockPos offsetPos, BlockState neighborState) {
        try {
            BlockState glassState;
            BlockEntity tile = world.m_7702_(offsetPos);
            if (tile instanceof ICamouflagable) {
                BlockState state;
                ICamouflagable able = (ICamouflagable)tile;
                if (able.isSideEnabled(side.m_122424_()) && (state = able.getStorage().getCache(side.m_122424_()).getDisplayBlock()) != null) {
                    return state;
                }
            } else if (tile instanceof LuminatorTileEntity && (glassState = ((LuminatorTileEntity)tile).getGlassState()) != null) {
                return glassState;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return neighborState;
    }

    @SubscribeEvent
    public void onFoamCheck(FoamEvent.Check event) {
        BlockEntity tile;
        if (event.isCustomPlacement()) {
            return;
        }
        Block block = event.getState().m_60734_();
        if (block instanceof ScaffoldBlock) {
            event.setCustomTarget(FoamEvent.TargetType.SCAFFOLD);
        } else if (block instanceof CableBlock) {
            BlockEntity tile2 = event.getBlockEntity();
            if (tile2 instanceof CableTileEntity) {
                CableTileEntity cable = (CableTileEntity)tile2;
                if (cable.foamed == 0) {
                    event.setCustomTarget(FoamEvent.TargetType.CABLE);
                }
            }
        } else if (block instanceof TubeBlock) {
            BlockEntity tile3 = event.getBlockEntity();
            if (tile3 instanceof TubeTileEntity) {
                TubeTileEntity cable = (TubeTileEntity)tile3;
                if (cable.foamed == 0) {
                    event.setCustomTarget(FoamEvent.TargetType.TUBE);
                }
            }
        } else if (block instanceof PipeBlock) {
            BlockEntity tile4 = event.getBlockEntity();
            if (tile4 instanceof PipeTileEntity) {
                PipeTileEntity cable = (PipeTileEntity)tile4;
                if (cable.foamed == 0) {
                    event.setCustomTarget(FoamEvent.TargetType.PIPE);
                }
            }
        } else if (block instanceof PlayerDetectorBlock && (tile = event.getBlockEntity()) instanceof PlayerDetectorTileEntity) {
            PlayerDetectorTileEntity cable = (PlayerDetectorTileEntity)tile;
            if (cable.foamed == 0) {
                event.setCustomTarget(FoamEvent.TargetType.CUSTOM);
            }
        }
    }

    @SubscribeEvent
    public void onFoamPlaced(FoamEvent.Place event) {
        PlayerDetectorTileEntity cable;
        BlockEntity tile;
        BlockState state = event.getState();
        Block block = state.m_60734_();
        if (block == IC2Blocks.SCAFFOLD_IRON) {
            event.getLevel().m_7731_(event.getPos(), (BlockState)IC2Blocks.CFOAM_WET.m_49966_().m_61124_(CFoamBlock.FOAM_TYPE, (Comparable)((Object)CFoamBlock.FoamType.REINFORCED)), 3);
            event.setCanceled(true);
        } else if (block == IC2Blocks.SCAFFOLD_WOOD) {
            Block.m_49950_((BlockState)state, (Level)((ServerLevel)event.getLevel()), (BlockPos)event.getPos());
            event.requestFoamPlacement();
            event.setCanceled(true);
        } else if (block instanceof CableBlock) {
            CableTileEntity cable2;
            BlockEntity tile2 = event.getBlockEntity();
            if (tile2 instanceof CableTileEntity && (cable2 = (CableTileEntity)tile2).changeFoam((byte)1, false)) {
                event.setCanceled(true);
            }
        } else if (block instanceof TubeBlock) {
            TubeTileEntity cable3;
            BlockEntity tile3 = event.getBlockEntity();
            if (tile3 instanceof TubeTileEntity && (cable3 = (TubeTileEntity)tile3).changeFoam((byte)1, false)) {
                event.setCanceled(true);
            }
        } else if (block instanceof PipeBlock) {
            PipeTileEntity cable4;
            BlockEntity tile4 = event.getBlockEntity();
            if (tile4 instanceof PipeTileEntity && (cable4 = (PipeTileEntity)tile4).changeFoam((byte)1, false)) {
                event.setCanceled(true);
            }
        } else if (block instanceof PlayerDetectorBlock && (tile = event.getBlockEntity()) instanceof PlayerDetectorTileEntity && (cable = (PlayerDetectorTileEntity)tile).changeFoam((byte)1, false)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onTextured(RetextureEvent event) {
        if (event.isApplied() || event.isCanceled()) {
            return;
        }
        BlockState state = event.getBlockState();
        Block block = state.m_60734_();
        if (block instanceof DyeableBlock && ((DyeableBlock)block).getColorMap() == ColorMaps.CFOAM_BLOCKS) {
            event.getLevel().m_7731_(event.getPos(), IC2Blocks.TEXTURED_BLOCK.m_49966_(), 3);
            BlockEntity tile = event.getBlockEntity();
            if (tile instanceof TexturedBlockTileEntity) {
                TexturedBlockTileEntity tex = (TexturedBlockTileEntity)tile;
                tex.setColor(null, ((DyeableBlock)block).getColor(), true);
                if (tex.applyTexture(event.getSide(), event.getContainer())) {
                    event.setApplied(true);
                }
            }
            return;
        }
        if (block instanceof IC2SlabBlock && ((IC2SlabBlock)block).getColorMap() == ColorMaps.CFOAM_SLABS) {
            event.getLevel().m_7731_(event.getPos(), PainterHelper.copyProperties(state, IC2Blocks.TEXTURED_SLAB.m_49966_()), 3);
            BlockEntity tile = event.getBlockEntity();
            if (tile instanceof TexturedBlockTileEntity) {
                TexturedBlockTileEntity tex = (TexturedBlockTileEntity)tile;
                tex.setColor(null, ((IC2SlabBlock)block).getColor(), true);
                if (tex.applyTexture(event.getSide(), event.getContainer())) {
                    event.setApplied(true);
                }
            }
            return;
        }
        if (block instanceof IC2StairsBlock && ((IC2StairsBlock)block).getColorMap() == ColorMaps.CFOAM_STAIRS) {
            event.getLevel().m_7731_(event.getPos(), PainterHelper.copyProperties(state, IC2Blocks.TEXTURED_STAIRS.m_49966_()), 3);
            BlockEntity tile = event.getBlockEntity();
            if (tile instanceof TexturedBlockTileEntity) {
                TexturedBlockTileEntity tex = (TexturedBlockTileEntity)tile;
                tex.setColor(null, ((IC2StairsBlock)block).getColor(), true);
                if (tex.applyTexture(event.getSide(), event.getContainer())) {
                    event.setApplied(true);
                }
            }
            return;
        }
        if (block instanceof IC2WallBlock && ((IC2WallBlock)block).getColorMap() == ColorMaps.CFOAM_WALLS) {
            event.getLevel().m_7731_(event.getPos(), PainterHelper.copyProperties(state, IC2Blocks.TEXTURED_WALL.m_49966_()), 3);
            BlockEntity tile = event.getBlockEntity();
            if (tile instanceof TexturedBlockTileEntity) {
                TexturedBlockTileEntity tex = (TexturedBlockTileEntity)tile;
                tex.setColor(null, ((IC2WallBlock)block).getColor(), true);
                if (tex.applyTexture(event.getSide(), event.getContainer())) {
                    event.setApplied(true);
                }
            }
            return;
        }
        BlockEntity tile = event.getBlockEntity();
        if (tile instanceof ICamouflagable && ((ICamouflagable)tile).applyTexture(event.getSide(), event.getContainer())) {
            event.setApplied(true);
        }
    }
}

