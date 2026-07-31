/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class OceanGeneratorTileEntity
extends BaseTileEntity
implements IEnergySource,
IEUStorage,
ITickListener,
AABBUtil.IBlockMapper,
Consumer<AABBUtil.SearchResult>,
IWrenchableTile,
IEUProducer,
ITileActivityProvider {
    static final Predicate<BlockState> CORAL = T -> T.m_204336_(BlockTags.f_13064_) || T.m_204336_(BlockTags.f_13051_) || T.m_204336_(BlockTags.f_13052_);
    public int storage = 0;
    boolean checking = false;
    public int waterFound = 0;
    public int coralsFound = 0;
    public int production = 0;
    boolean addedToEnet = false;

    public OceanGeneratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "energy", this.storage, 0);
        NBTUtils.putShort(compound, "production", this.production, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.storage = compound.m_128451_("energy");
        this.production = compound.m_128451_("production");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_OCEAN;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return this.getFacing() != dir && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.75;
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            EnergyNet.INSTANCE.addTile(this);
            this.addedToEnet = true;
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            EnergyNet.INSTANCE.removeTile(this);
            this.addedToEnet = false;
        }
        super.onUnloaded(chunk);
    }

    @Override
    public float getEUProduction() {
        return this.production;
    }

    @Override
    public void onTick() {
        if (!this.checking && this.clock(512)) {
            this.checking = true;
            AABBUtil.createOffthreadTask(this.m_58904_(), this.m_58899_(), 5, this, 33, DirectionList.ALL, this);
        }
        if (this.production > 0 && this.storage < this.getMaxEU()) {
            this.storage = Math.min(this.storage + this.production, this.getMaxEU());
        }
        this.setActive(this.production > 0);
    }

    @Override
    public void accept(AABBUtil.SearchResult t) {
        this.checking = false;
        this.coralsFound = 0;
        this.waterFound = 0;
        int productionPenalty = 1;
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(t.getMappedPositions())) {
            if (entry.getKey() == Blocks.f_49990_) {
                this.waterFound += ((List)entry.getValue()).size();
            } else if (CORAL.test(((Block)entry.getKey()).m_49966_())) {
                this.coralsFound += ((List)entry.getValue()).size();
            }
            if (entry.getKey() != IC2Blocks.OCEAN_GENERATOR) continue;
            productionPenalty = Math.max(((List)entry.getValue()).size(), 1);
        }
        this.production = (int)(Math.max(0.0f, Math.min(1.0f, (float)this.waterFound * 0.001f) * Math.min((float)(this.coralsFound - 25) * 0.04f, 1.0f) * (float)IC2.CONFIG.oceanGenOutput.get()) / (float)productionPenalty);
    }

    @Override
    public int getStoredEU() {
        return this.storage;
    }

    @Override
    public int getMaxEU() {
        return 8000;
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public int getSourceTier() {
        return 2;
    }

    @Override
    public int getMaxEnergyOutput() {
        return 100;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(100, this.storage);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.storage -= consumed;
    }

    @Override
    public boolean isValid(LevelReader world, BlockPos pos) {
        return this.isValid(world.m_8055_(pos));
    }

    @Override
    public boolean isValid(BlockState state) {
        return CORAL.test(state) || state.m_60819_().m_76152_() == Fluids.f_76193_ || state.m_60734_() == IC2Blocks.OCEAN_GENERATOR;
    }

    @Override
    public void mapBlocks(LevelReader reader, BlockPos pos, Map<Block, List<BlockPos>> mapper) {
        BlockState state = reader.m_8055_(pos);
        if (state.m_60819_().m_76152_() == Fluids.f_76193_) {
            AABBUtil.mapResult(Blocks.f_49990_, pos, mapper);
        }
        if (CORAL.test(state) || state.m_60734_() == IC2Blocks.OCEAN_GENERATOR) {
            AABBUtil.mapResult(state.m_60734_(), pos, mapper);
        }
    }
}

