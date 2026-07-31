/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.block.cables.luminator;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.impl.LinkedSink;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergyTile;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ConstructionLightTileEntity
extends BaseTileEntity
implements ITickListener,
IEnergySink,
IMultiEnergyTile,
IWrenchableTile,
IEUStorage,
ITileActivityProvider {
    int energy = 0;
    boolean addedToEnet;

    public ConstructionLightTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 50);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = NBTUtils.getInt(compound, "energy", 50);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            this.f_58857_.m_7726_().m_7827_().m_7174_(this.m_58899_());
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onTick() {
        if (this.clock(10)) {
            if (this.energy > 0) {
                if (!this.isActive()) {
                    --this.energy;
                }
                this.setActive(true);
                --this.energy;
            } else {
                this.setActive(false);
            }
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CONSTRUCTION_LIGHT;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return false;
    }

    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public int getRequestedEnergy() {
        return 50 - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, 50 - this.energy);
        if (added > 0) {
            this.energy += added;
        }
        return amount - added;
    }

    @Override
    public List<IEnergyTile> getTiles() {
        ObjectList tiles = CollectionUtils.createList();
        tiles.add((ConstructionLightTileEntity)this);
        tiles.add((LinkedSink)new LinkedSink(this.m_58904_(), this.m_58899_().m_7495_(), DirectionList.DOWN.getCode()));
        return tiles;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.m_58900_().m_61138_((Property)BlockStateProperties.f_61362_) && ((Boolean)this.m_58900_().m_61143_((Property)BlockStateProperties.f_61362_)).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        } else if (this.m_58900_().m_61138_((Property)IC2Properties.LAVA_LOGGED) && ((Boolean)this.m_58900_().m_61143_((Property)IC2Properties.LAVA_LOGGED)).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)this.f_58857_));
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (!this.addedToEnet && IC2.PLATFORM.isSimulating()) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnet && IC2.PLATFORM.isSimulating()) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return 50;
    }

    @Override
    public int getTier() {
        return this.getSinkTier();
    }
}

