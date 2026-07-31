/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropSeed;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.machines.logic.crop.CropSorter;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import ic2.core.block.machines.logic.crop.SeedEntry;
import ic2.core.block.machines.logic.crop.SeedStorage;
import ic2.core.block.machines.logic.crop.SeedSyncer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseCropLibraryTileEntity
extends BaseInventoryTileEntity
implements ITickListener,
ITileGui,
IWrenchableTile {
    public static final List<CropSorter> SORTERS = ObjectArrayList.wrap((Object[])new CropSorter[]{new CropSorter(0), new CropSorter(1), new CropSorter(2), new CropSorter(3), new CropSorter(4), new CropSorter(5)});
    public SeedStorage storage;
    @NetworkInfo
    public SeedSyncer syncer;
    @NetworkInfo
    public ICrop crop = null;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int cropOffset = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int sorter = 0;
    @NetworkInfo
    public boolean sorting = false;
    @NetworkInfo
    public boolean inverting = false;

    public BaseCropLibraryTileEntity(BlockPos pos, BlockState state, SeedStorage storage) {
        this(pos, state, 4, storage);
    }

    public BaseCropLibraryTileEntity(BlockPos pos, BlockState state, int slots, SeedStorage storage) {
        super(pos, state, slots);
        this.storage = storage;
        this.syncer = storage.getSyncer();
        this.addGuiFields("syncer", "crop", "selected", "sorter", "sorting");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.IMPORT);
        handler.registerBlockSides(DirectionList.ALL);
        int[] slots = MathUtils.fromTo(0, this.getSlotCount());
        handler.registerSlotAccess(AccessRule.IMPORT, slots);
        handler.registerSlotsForSide(DirectionList.ALL, slots);
        handler.registerInputFilter(SpecialFilters.CROP_FILTER_SCANNED, slots);
        handler.registerNamedSlot(SlotType.INPUTS, slots);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.storage.read(compound.m_128469_("storage"));
        String id = NBTUtils.getString(compound, "crop", "empty");
        this.crop = id.equalsIgnoreCase("empty") ? null : ICropRegistry.INSTANCE.getCrop(new ResourceLocation(id));
        this.cropOffset = compound.m_128451_("selectionIndex");
        this.sorter = compound.m_128451_("sorter");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "storage", this.storage.write(new CompoundTag()));
        NBTUtils.putString(compound, "crop", this.crop == null ? "empty" : this.crop.id().toString(), "empty");
        NBTUtils.putShort(compound, "selectionIndex", (int)((short)this.cropOffset), 0);
        NBTUtils.putByte(compound, "sorter", (int)((byte)this.sorter), 0);
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return this.getFacing() != dir && this.getFacing().m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.storage.updateDisplay(this.crop);
        }
    }

    @Override
    public void onTick() {
        if (this.storage.processSorting(this.crop, this.inverting ? SORTERS.get(this.sorter).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
            this.storage.updateDisplay(this.crop);
        }
        if (this.clock(10)) {
            this.addCrop();
            this.transferStack(1, 0);
            this.transferStack(2, 1);
            this.transferStack(3, 2);
        }
        this.handleComparators();
    }

    protected boolean addCrop() {
        ItemStack stack;
        ICropSeed seed;
        if (!((ItemStack)this.inventory.get(0)).m_41619_() && (seed = this.getCrop(stack = (ItemStack)this.inventory.get(0))) != null) {
            ICrop crop = seed.getCrop(stack);
            if (crop == null || seed.getScanLevel(stack) < 4) {
                return false;
            }
            SeedEntry entry = new SeedEntry(seed.getGrowth(stack), seed.getGain(stack), seed.getResistance(stack), crop.getProperties().getTier(), stack.m_41613_());
            if (this.storage.addCrop(crop, entry)) {
                stack.m_41764_(entry.getCount());
                this.storage.updateDisplay(this.crop);
                this.updateGuiField("syncer");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        int added;
        int slot;
        ItemStack stack;
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.cropOffset = Mth.m_14045_((int)(this.cropOffset + value), (int)0, (int)((this.syncer.size() + 1) / 5));
            this.updateGuiField("cropOffset");
        } else if (key == 1) {
            this.crop = (value = Mth.m_14045_((int)value, (int)0, (int)(this.syncer.size() + 1))) == 0 ? null : ICropRegistry.INSTANCE.getCrop(this.syncer.get(value - 1));
            this.storage.offset(this.crop, Integer.MIN_VALUE);
            this.storage.updateDisplay(this.crop);
            this.updateGuiFields("crop");
        } else if (key == 2) {
            this.storage.offset(this.crop, value);
            this.storage.updateDisplay(this.crop);
        } else if (key == 3) {
            this.sorting = value > 0;
            this.updateGuiField("sorting");
            if (this.sorting) {
                this.storage.markAllDirty(this.crop);
                if (this.storage.processSorting(this.crop, this.inverting ? SORTERS.get(this.sorter).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
                    this.storage.updateDisplay(this.crop);
                }
            }
        } else if (key == 4) {
            this.sorter = (this.sorter + 1) % SORTERS.size();
            this.updateGuiField("sorter");
            this.storage.markAllDirty(this.crop);
            if (this.storage.processSorting(this.crop, this.inverting ? SORTERS.get(this.sorter).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
                this.storage.updateDisplay(this.crop);
            }
        } else if (key == 5) {
            this.inverting = !this.inverting;
            this.updateGuiField("inverting");
            this.storage.markAllDirty(this.crop);
            if (this.storage.processSorting(this.crop, this.inverting ? SORTERS.get(this.sorter).reversed() : (Comparator<ISeedEntry>)SORTERS.get(this.sorter))) {
                this.storage.updateDisplay(this.crop);
            }
        } else if (key == 6 && !(stack = this.storage.remove(this.crop, slot = MathUtils.getShortKey(value), MathUtils.getShortValue(value), false)).m_41619_() && (added = TransporterManager.getTransporter(entity).addItem(stack, Direction.DOWN, false)) > 0) {
            this.storage.remove(this.crop, slot, added, true);
            this.updateGuiField("syncer");
            if (!this.storage.containsCrop(this.crop)) {
                this.crop = null;
                this.cropOffset = 0;
                this.storage.offset(null, Integer.MIN_VALUE);
                this.updateGuiFields("crop", "cropOffset");
            }
            this.storage.markCurrentDirty(this.crop);
        }
    }

    protected ICropSeed getCrop(ItemStack stack) {
        if (!(stack.m_41720_() instanceof ICropSeed)) {
            return null;
        }
        return (ICropSeed)stack.m_41720_();
    }
}

