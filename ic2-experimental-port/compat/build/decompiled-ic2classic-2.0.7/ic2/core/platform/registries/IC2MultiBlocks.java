/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FarmBlock
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.platform.registries;

import ic2.core.IC2;
import ic2.core.block.base.blocks.BaseFacingBlock;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.generators.FuelBoilerBlock;
import ic2.core.block.machines.ColossalMachineBlock;
import ic2.core.block.machines.customBlocks.BasePressureAlloyFurnaceBlock;
import ic2.core.block.machines.tiles.ev.ColossalCompressor;
import ic2.core.block.machines.tiles.ev.ColossalExtractor;
import ic2.core.block.machines.tiles.ev.ColossalFurnace;
import ic2.core.block.machines.tiles.ev.ColossalMacerator;
import ic2.core.block.machines.tiles.ev.ColossalRecycler;
import ic2.core.block.multi.ColossalMultiBlock;
import ic2.core.block.multi.SimpleMultiBlock;
import ic2.core.block.multi.TurbineMultiBlock;
import ic2.core.block.rendering.block.CropEntry;
import ic2.core.block.rendering.props.CropProperty;
import ic2.core.block.rendering.props.TextureProperty;
import ic2.core.block.rendering.world.impl.MultiBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class IC2MultiBlocks {
    public static final Supplier<MultiBlock> TANK = () -> {
        MultiBlock block = new MultiBlock();
        Box bounding = Box.fromPos(new BlockPos(-2, 0, -2), new BlockPos(2, 4, 2));
        for (BlockPos pos : bounding.getHollowIterator()) {
            if (bounding.isEdge(pos) || bounding.getBorderSide(pos) == Direction.DOWN) {
                block.addBlock(Blocks.f_50075_, pos);
                continue;
            }
            block.addBlock(Blocks.f_50058_, pos);
        }
        return block.addBlock(IC2Blocks.TANK, 2, 2, 0).build();
    };
    public static final Supplier<MultiBlock> FUEL_BOILER = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(SimpleMultiBlock.buildState(IC2Blocks.FUEL_BOILER_MULTIBLOCK, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.FUEL_BOILER.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)FuelBoilerBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.FUEL_BOILER.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)FuelBoilerBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> PRESSURE_ALLOY_FURNACE = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(SimpleMultiBlock.buildState(IC2Blocks.PRESSURE_ALLOY_FURNACE_MULTIBLOCK, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.PRESSURE_ALLOY_FURNACE.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)BasePressureAlloyFurnaceBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.PRESSURE_ALLOY_FURNACE.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)BasePressureAlloyFurnaceBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> REACTOR = () -> new MultiBlock().addBlock(IC2Blocks.NUCLEAR_REACTOR, 0, 1, 0).addBlock(IC2Blocks.REACTOR_CHAMBER, 0, 1, -1).addBlock(IC2Blocks.REACTOR_CHAMBER, 0, 1, 1).addBlock(IC2Blocks.REACTOR_CHAMBER, 0, 0, 0).addBlock(IC2Blocks.REACTOR_CHAMBER, 0, 2, 0).addBlock(IC2Blocks.REACTOR_CHAMBER, -1, 1, 0).addBlock(IC2Blocks.REACTOR_CHAMBER, 1, 1, 0).build();
    public static final Supplier<MultiBlock> STEAM_REACTOR = () -> new MultiBlock().addBlock(IC2Blocks.STEAM_REACTOR, 0, 1, 0).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, 0, 1, -1).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, 0, 1, 1).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, 0, 0, 0).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, 0, 2, 0).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, -1, 1, 0).addBlock(IC2Blocks.STEAM_REACTOR_CHAMBER, 1, 1, 0).build();
    public static final Supplier<MultiBlock> FUSION_REACTOR = () -> {
        MultiBlock block = new MultiBlock();
        Box bounding = Box.fromPos(new BlockPos(-2, 0, -2), new BlockPos(2, 4, 2));
        for (BlockPos pos : bounding.getHollowIterator()) {
            if (bounding.isEdge(pos) || bounding.getBorderSide(pos) == Direction.DOWN) {
                block.addBlock(IC2Blocks.REINFORCED_STONE, pos);
                continue;
            }
            block.addBlock(IC2Blocks.REINFORCED_CLEAR_GLASS, pos);
        }
        return block.addBlock(IC2Blocks.FUSION_REACTOR, 2, 4, -2).addBlock(IC2Blocks.VALVE, 2, 4, 0).addBlock(IC2Blocks.VALVE, -2, 4, 0).addBlock(IC2Blocks.VALVE, 0, 4, 2).addBlock(IC2Blocks.VALVE, 0, 4, -2).withModelData(IC2.PLATFORM.createValveData(IC2Blocks.REINFORCED_STONE)).build();
    };
    public static final Supplier<MultiBlock> FISHER = () -> {
        MultiBlock block = new MultiBlock();
        for (BlockPos pos : Box.fromPos(new BlockPos(-2, 6, -2), new BlockPos(2, 6, 2))) {
            int max = Math.max(Math.abs(pos.m_123341_()), Math.abs(pos.m_123343_()));
            block.addBlock(max == 1 ? Blocks.f_50705_ : Blocks.f_49999_, pos);
        }
        block.addBlock(IC2Blocks.FISHER, 0, 6, 0).addBlock(Blocks.f_50087_, 0, 7, 0);
        for (int i = 0; i < 6; ++i) {
            block.addBlock((BlockState)Blocks.f_50184_.m_49966_().m_61124_((Property)RotatedPillarBlock.f_55923_, (Comparable)Direction.Axis.Y), 0, i, 0);
        }
        return block.setOffset(0.0, 0.0, 0.0).build();
    };
    public static final Supplier<MultiBlock> STEAM_TUNNEL = () -> {
        MultiBlock block = new MultiBlock();
        Box bounding = Box.fromPos(new BlockPos(-2, 0, -1), new BlockPos(1, 2, 1));
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(TurbineMultiBlock.createMultiBlockFrame(bounding))) {
            block.addBlock((BlockState)entry.getValue(), BlockPos.m_122022_((long)entry.getLongKey())).addUnformedBlock(((BlockState)entry.getValue()).m_60734_().m_49966_(), BlockPos.m_122022_((long)entry.getLongKey()));
        }
        return block.setOffset(-7.5, 0.0, 0.0).build();
    };
    public static final Supplier<MultiBlock> COLOSSAL_FURNACE = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0).withModelData(TextureProperty.INSTANCE, ColossalFurnace.PROVIDER);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(ColossalMultiBlock.buildState(IC2Blocks.COLOSSAL_BASE, 0, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_FURNACE.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_FURNACE.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> COLOSSAL_MACERATOR = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0).withModelData(TextureProperty.INSTANCE, ColossalMacerator.PROVIDER);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(ColossalMultiBlock.buildState(IC2Blocks.COLOSSAL_BASE, 0, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_MACERATOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_MACERATOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> COLOSSAL_EXTRACTOR = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0).withModelData(TextureProperty.INSTANCE, ColossalExtractor.PROVIDER);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(ColossalMultiBlock.buildState(IC2Blocks.COLOSSAL_BASE, 0, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_EXTRACTOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_EXTRACTOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> COLOSSAL_COMPRESSOR = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0).withModelData(TextureProperty.INSTANCE, ColossalCompressor.PROVIDER);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(ColossalMultiBlock.buildState(IC2Blocks.COLOSSAL_BASE, 0, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_COMPRESSOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_COMPRESSOR.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final Supplier<MultiBlock> COLOSSAL_RECYCLER = () -> {
        MultiBlock block = new MultiBlock().setOffset(-15.0, 0.0, 0.0).withModelData(TextureProperty.INSTANCE, ColossalRecycler.PROVIDER);
        for (int i = 1; i < 8; ++i) {
            block.addBlock(ColossalMultiBlock.buildState(IC2Blocks.COLOSSAL_BASE, 0, Direction.NORTH, i), SimpleMultiBlock.buildPos(i, Direction.NORTH).m_7918_(-1, 1, 1));
        }
        block.addUnformedBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_RECYCLER.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(false)), 0, 1, 0);
        return block.addBlock((BlockState)((BlockState)IC2Blocks.COLOSSAL_RECYCLER.m_49966_().m_61124_((Property)BaseFacingBlock.FACING, (Comparable)Direction.NORTH)).m_61124_((Property)ColossalMachineBlock.FORMED, (Comparable)Boolean.valueOf(true)), 0, 1, 0).build();
    };
    public static final IntFunction<MultiBlock> CROP_BLOCK = T -> {
        MultiBlock block = new MultiBlock();
        if (T == 0) {
            block.addBlock((BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)), 0, 0, 0);
            block.addBlock(IC2Blocks.CROP_STICK, 0, 1, 0);
            block.withModelData(CropProperty.CROP, new CropEntry(CropRegistry.AURELIA, 3));
        } else if (T == 1) {
            block.addBlock((BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)), 0, 0, 0);
            block.addBlock((BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)), 1, 0, 0);
            block.addBlock((BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)), -1, 0, 0);
            block.addBlock(IC2Blocks.CROP_STICK, 0, 1, 0).withPositionedModelData(CropProperty.CROP, new CropEntry(null, 1), 0, 1, 0);
            block.addBlock(IC2Blocks.CROP_STICK, -1, 1, 0).withPositionedModelData(CropProperty.CROP, new CropEntry(CropRegistry.AURELIA, 3), -1, 1, 0);
            block.addBlock(IC2Blocks.CROP_STICK, 1, 1, 0).withPositionedModelData(CropProperty.CROP, new CropEntry(CropRegistry.FERRU, 3), 1, 1, 0);
        } else if (T == 2) {
            block.setOffset(-10.0, -50.0, 0.0);
            block.addBlock((BlockState)Blocks.f_50093_.m_49966_().m_61124_((Property)FarmBlock.f_53243_, (Comparable)Integer.valueOf(7)), 0, 0, 0);
            block.addBlock(Blocks.f_50136_, 0, -1, 0).addBlock(Blocks.f_50136_, 0, -2, 0).addBlock(Blocks.f_50136_, 0, -3, 0);
            block.addBlock(Blocks.f_49995_, 0, -4, 0);
            block.addBlock(IC2Blocks.CROP_STICK, 0, 1, 0);
            block.withModelData(CropProperty.CROP, new CropEntry(CropRegistry.AURELIA, 3));
        }
        return block.build();
    };
    public static final Function<Block, MultiBlock> ELECTROLYZER = T -> {
        MultiBlock block = new MultiBlock();
        block.addBlock((Block)T, 0, 0, 0).addBlock(IC2Blocks.MFE, -1, 0, 0).addBlock(Blocks.f_50016_, 1, 0, 0);
        return block.build();
    };
    public static final Supplier<MultiBlock> ELECTRIC_ENCHANTER = () -> {
        MultiBlock block = new MultiBlock();
        for (int x = -2; x < 3; ++x) {
            for (int y = 0; y < 2; ++y) {
                block.addBlock(Blocks.f_50078_, x, y, -2);
                block.addBlock(Blocks.f_50078_, x, y, 2);
                block.addBlock(Blocks.f_50078_, -2, y, x);
            }
            for (int z = -2; z < 3; ++z) {
                block.addBlock(Blocks.f_50440_, x, -1, z);
            }
        }
        block.addBlock(IC2Blocks.ELECTRIC_ENCHANTER, 0, 0, 0);
        return block.build();
    };
    public static Function<Block, MultiBlock> PUMP = B -> {
        MultiBlock block = new MultiBlock();
        block.addBlock((Block)B, 0, 0, 0).addBlock(IC2Blocks.COMPRESSOR, -1, 0, 0).addBlock(Blocks.f_50016_, 1, 0, 0);
        return block.build();
    };
    public static Supplier<MultiBlock> MINER = () -> {
        MultiBlock block = new MultiBlock().setOffset(0.0, -40.0, 0.0);
        block.addBlock(IC2Blocks.MINER, 0, 0, 0).addBlock(IC2Blocks.MINING_PIPE_SHAFT, 0, -1, 0).addBlock(IC2Blocks.MINING_PIPE_SHAFT, 0, -2, 0).addBlock(IC2Blocks.MINING_PIPE_BOTTOM, 0, -3, 0);
        block.addBlock(IC2Blocks.PUMP, -1, 0, 0).addBlock(Blocks.f_50016_, 1, 0, 0);
        block.addBlock(Blocks.f_50087_, 0, 0, 1).addBlock(Blocks.f_50016_, 0, 0, -1);
        block.addBlock(Blocks.f_50332_, 0, 1, 0);
        return block.build();
    };
}

