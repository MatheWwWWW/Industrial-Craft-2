/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.containers.hv.CropLibraryContainer;
import ic2.core.block.machines.logic.crop.CropSorter;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import ic2.core.block.machines.logic.crop.SeedStorage;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CropLibraryTileEntity
extends BaseCropLibraryTileEntity
implements IEnergySink,
IEUStorage,
ITileActivityProvider {
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int energy = 0;
    boolean addedToEnergyNet = false;

    public CropLibraryTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, new SeedStorage(-1, -1, -1));
        this.addGuiFields("energy");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128376_("energy", (short)this.energy);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CropLibraryContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CROP_LIBRARY;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.7;
    }

    @Override
    public void onLoaded() {
        if (!this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        super.onLoaded();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = false;
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
        return 10000;
    }

    @Override
    public int getTier() {
        return this.getSinkTier();
    }

    @Override
    public void onTick() {
        if (this.energy > 0) {
            --this.energy;
            this.updateGuiField("energy");
            if (this.storage.setActive(true)) {
                this.storage.updateDisplay(this.crop);
                this.updateGuiField("syncer");
            }
            if (this.storage.processSorting(this.crop, this.inverting ? ((CropSorter)SORTERS.get(this.sorter)).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
                this.storage.updateDisplay(this.crop);
            }
            this.setActive(true);
        } else {
            if (this.storage.setActive(false)) {
                this.updateGuiField("syncer");
            }
            this.setActive(false);
        }
        if (this.clock(5) && this.energy >= 5) {
            this.transferStack(3, 2);
            this.transferStack(2, 1);
            this.transferStack(1, 0);
            this.addCrop();
        }
        this.handleComparators();
    }

    @Override
    protected boolean addCrop() {
        if (super.addCrop()) {
            this.energy -= 5;
            this.updateGuiField("energy");
            return true;
        }
        return false;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return true;
    }

    @Override
    public int getSinkTier() {
        return 3;
    }

    @Override
    public int getRequestedEnergy() {
        return 10000 - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > 10000 || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, 10000 - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return amount - added;
    }
}

