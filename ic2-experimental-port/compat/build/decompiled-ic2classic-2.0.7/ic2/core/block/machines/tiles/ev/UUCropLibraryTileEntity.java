/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.ev;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropSeed;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.containers.ev.UUCropLibraryContainer;
import ic2.core.block.machines.logic.crop.CropSorter;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import ic2.core.block.machines.logic.crop.SeedEntry;
import ic2.core.block.machines.logic.crop.SeedStorage;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class UUCropLibraryTileEntity
extends BaseCropLibraryTileEntity
implements IEnergySink,
IEUStorage,
ITileActivityProvider {
    @NetworkInfo
    public int energy = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int uu_matter;
    boolean addedToEnergyNet = false;

    public UUCropLibraryTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 2, new SeedStorage(-1, -1, 1));
        this.addGuiFields("energy", "uu_matter");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotsForSide(DirectionList.ALL, 0, 1);
        handler.registerInputFilter(SpecialFilters.CROP_FILTER_SCANNED, 0);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.UUMATTER), 1);
        handler.registerNamedSlot(SlotType.INPUTS, 0);
        handler.registerNamedSlot(SlotType.EXTRA_INPUT, 1);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.uu_matter = compound.m_128451_("uu_matter");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.putShort(compound, "uu_matter", this.uu_matter, 0);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new UUCropLibraryContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.UU_CROP_LIBRARY;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.7;
    }

    @Override
    public void onTick() {
        if (this.energy > 0) {
            --this.energy;
            this.updateGuiField("energy");
            if (this.storage.setActive(true)) {
                this.storage.updateDisplay(this.crop);
            }
            if (this.storage.processSorting(this.crop, this.inverting ? ((CropSorter)SORTERS.get(this.sorter)).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
                this.storage.updateDisplay(this.crop);
            }
            this.setActive(true);
        } else {
            this.storage.setActive(false);
            this.setActive(false);
        }
        if (this.clock(5) && this.energy >= 512) {
            this.addCrop();
        }
        if (this.clock(10) && this.uu_matter < 512 && ((ItemStack)this.inventory.get(1)).m_41720_() == IC2Items.UUMATTER) {
            int added = Math.min(512 - this.uu_matter, ((ItemStack)this.inventory.get(1)).m_41613_());
            this.uu_matter += added;
            this.updateGuiField("uu_matter");
            ((ItemStack)this.inventory.get(1)).m_41774_(added);
        }
        this.handleComparators();
    }

    @Override
    protected boolean addCrop() {
        ItemStack stack;
        ICropSeed seed;
        if (!((ItemStack)this.inventory.get(0)).m_41619_() && (seed = this.getCrop(stack = (ItemStack)this.inventory.get(0))) != null) {
            ICrop crop = seed.getCrop(stack);
            if (crop == null || seed.getScanLevel(stack) < 4) {
                return false;
            }
            if (this.storage.addCrop(crop, new SeedEntry(seed.getGrowth(stack), seed.getGain(stack), seed.getResistance(stack), crop.getProperties().getTier(), stack.m_41613_()))) {
                this.energy -= 512;
                this.updateGuiField("energy");
                this.storage.updateDisplay(this.crop);
                this.updateGuiField("syncer");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 6) {
            if (this.uu_matter <= 0 || this.energy <= 0) {
                return;
            }
            int slot = MathUtils.getShortKey(value);
            ItemStack stack = this.storage.remove(this.crop, slot, MathUtils.getShortValue(value), false);
            if (!stack.m_41619_()) {
                ICropSeed seed = (ICropSeed)stack.m_41720_();
                int neededEnergy = (seed.getGain(stack) + seed.getGrowth(stack) + seed.getResistance(stack)) * 10000;
                int amount = Math.min(Math.min(MathUtils.getShortValue(value), this.uu_matter), this.energy / neededEnergy);
                if (amount <= 0) {
                    return;
                }
                stack.m_41764_(amount);
                int added = TransporterManager.getTransporter(entity).addItem(stack, Direction.DOWN, false);
                if (added > 0) {
                    this.energy -= neededEnergy * added;
                    this.uu_matter -= added;
                    this.updateGuiFields("energy", "uu_matter");
                }
            }
            return;
        }
        super.onClientDataReceived(entity, key, value);
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
        return 30000000;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return true;
    }

    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public int getRequestedEnergy() {
        return 30000000 - this.energy;
    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > 30000000 || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, 30000000 - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return amount - added;
    }
}

