/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.ev;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.ev.CrafterContainer;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.IMemorySlotProvider;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.IC2CraftingInventory;
import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.inventory.inv.InventoryWrapper;
import ic2.core.inventory.inv.ListenerInventory;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.misc.MemoryStickItem;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrafterTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
INotifyInventory,
IMemorySlotProvider {
    public static final int INVENTORY_CHANGED = 349525;
    static final CraftingContainer CRAFTING = new IC2CraftingInventory(3, 3);
    private boolean isCrafting = false;
    @NetworkInfo
    public int enabledSlots = 0;
    int flags = 0;
    LinkedList<ItemStack> clockUps = new LinkedList();
    public CraftingList recipeList = new CraftingList(9);
    public SimpleInventory upgrades = new ListenerInventory(3, this);

    public CrafterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 18, 2048, 25000);
        this.addGuiFields("enabledSlots");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] slots = MathUtils.fromTo(0, 18);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotsForSide(DirectionList.ALL, slots);
        handler.registerSlotAccess(AccessRule.BOTH, slots);
        handler.registerInputFilter(this::isSlotValid, slots);
        handler.registerNamedSlot(SlotType.STORAGE, slots);
    }

    private boolean isSlotValid(int slot, ItemStack stack) {
        if (this.isCrafting) {
            return true;
        }
        return slot < 9 || this.recipeList.getRecipe(slot - 9) == null;
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        this.upgrades.addToDrops(drops);
        super.addDrops(drops);
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CRAFTER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CrafterContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("flags", this.flags);
        compound.m_128365_("upgrades", (Tag)this.upgrades.save(new CompoundTag()));
        ListTag clock = new ListTag();
        for (ItemStack stack : this.clockUps) {
            clock.add((Object)stack.m_41739_(new CompoundTag()));
        }
        NBTUtils.put(compound, "clock", clock);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.flags = compound.m_128451_("flags");
        this.upgrades.load(compound.m_128469_("upgrades"));
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("clock", 10), CompoundTag.class)) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            if (stack.m_41619_()) continue;
            this.clockUps.add(stack);
        }
    }

    @Override
    public int getEnabledSlots() {
        return this.enabledSlots;
    }

    @Override
    public boolean isServerSided() {
        return this.isSimulating();
    }

    @Override
    public CraftingList getRecipes() {
        return this.recipeList;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.isCrafting = true;
            this.onNotify(this.upgrades, 2);
            this.isCrafting = false;
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && !this.isCrafting) {
            this.flags = 0;
        }
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (slot == 2 && inventory == this.upgrades) {
            ItemStack stack;
            if (!this.isCrafting) {
                this.flags = 0;
            }
            if ((stack = inventory.getStackInSlot(slot)).m_41619_()) {
                for (int i = 0; i < 9; ++i) {
                    this.enabledSlots &= ~(1 << i);
                    this.recipeList.removeRecipe(i);
                }
            } else if (stack.m_41720_() instanceof MemoryStickItem) {
                for (int i = 0; i < 9; ++i) {
                    this.enabledSlots |= 1 << i;
                    CraftRecipe recipe = MemoryStickItem.loadRecipe(stack, i);
                    if (recipe != null && recipe.validate(this.m_58904_())) {
                        this.recipeList.saveRecipe(i, recipe);
                        continue;
                    }
                    this.recipeList.removeRecipe(i);
                }
            }
            this.updateGuiField("enabledSlots");
        }
    }

    public int getSpeed() {
        return Math.max(1, 20 - this.upgrades.getStackInSlot(1).m_41613_());
    }

    public int getCrafts() {
        return this.upgrades.getStackInSlot(0).m_41613_();
    }

    @Override
    public void onTick() {
        int crafts = this.getCrafts();
        if (this.invClock(this.getSpeed()) || !this.hasEnergy(crafts * 25) || this.recipeList.isEmpty() || crafts <= 0) {
            return;
        }
        if (!this.clockUps.isEmpty()) {
            IItemTransporter transporter = TransporterManager.getTransporter(new RangedInventory(this, 0, 1, 2, 3, 4, 5, 6, 7, 8));
            Iterator iter = this.clockUps.iterator();
            while (iter.hasNext()) {
                ItemStack stack = (ItemStack)iter.next();
                stack.m_41774_(transporter.addItem(stack, null, false));
                if (stack.m_41613_() > 0) continue;
                iter.remove();
            }
            return;
        }
        int thisCraft = crafts;
        this.isCrafting = true;
        CraftTransporter transporter = new CraftTransporter(new InventoryWrapper(this));
        while (thisCraft > 0) {
            boolean ranOut = true;
            for (int i = 0; i < 9 && thisCraft > 0; ++i) {
                CraftRecipe recipe = this.recipeList.getRecipe(i);
                if (recipe == null || (this.flags & 3 << i * 2) != 0 || !this.tryCraft(i, recipe, transporter)) continue;
                --thisCraft;
                ranOut = false;
            }
            if (!ranOut) continue;
            break;
        }
        this.isCrafting = false;
        if (thisCraft != crafts) {
            this.useEnergy((crafts - thisCraft) * 25);
            this.flags = 0;
            this.notifyListeners();
        }
        this.handleComparators();
    }

    public boolean tryCraft(int slot, CraftRecipe recipe, IItemTransporter transporter) {
        ItemStack present = (ItemStack)this.inventory.get(slot + 9);
        if (!present.m_41619_() && StackUtil.getStackSizeLeft(present) < recipe.getDisplayItem().m_41613_()) {
            this.flags |= 1 << slot * 2 + 1;
            return false;
        }
        ObjectList toInsertBack = CollectionUtils.createList();
        for (Object2ObjectMap.Entry items : Object2ObjectMaps.fastIterable(recipe.getFilters())) {
            int[] stackInfo = (int[])items.getValue();
            ItemStack provided = transporter.removeItem((IFilter)items.getKey(), null, stackInfo[1], false);
            if (provided.m_41619_()) {
                this.reinsert((List<ItemStack>)toInsertBack, transporter);
                this.flags |= 1 << slot * 2;
                return false;
            }
            toInsertBack.add((ItemStack)provided);
            if (provided.m_41613_() < stackInfo[1]) {
                this.reinsert((List<ItemStack>)toInsertBack, transporter);
                this.flags |= 1 << slot * 2;
                return false;
            }
            CRAFTING.m_6836_(stackInfo[0], provided.m_41777_());
        }
        ItemStack output = recipe.getOutput(CRAFTING, this.f_58857_);
        if (output.m_41619_() || !this.canPlace(slot + 9, output)) {
            this.reinsert((List<ItemStack>)toInsertBack, transporter);
            this.flags |= 1 << slot * 2 + 1;
            return false;
        }
        this.setOrGrow(slot + 9, output, false);
        NonNullList<ItemStack> remaining = recipe.getRemainingItems(CRAFTING, this.f_58857_);
        int m = remaining.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = (ItemStack)remaining.get(i);
            if (entry.m_41619_()) continue;
            entry.m_41774_(transporter.addItem(entry, null, false));
            if (entry.m_41613_() <= 0) continue;
            this.clockUps.add(entry);
        }
        CRAFTING.m_6211_();
        return true;
    }

    private void reinsert(List<ItemStack> list, IItemTransporter transporter) {
        int i = 0;
        int m = list.size();
        while (i < m) {
            transporter.addItem(list.get(i++), null, false);
        }
        CRAFTING.m_6211_();
    }

    private class CraftTransporter
    implements IItemTransporter {
        IHasInventory inventory;
        IItemTransporter simple;
        NonNullList<ItemStack> memoryInv;

        public CraftTransporter(IHasInventory inventory) {
            this.inventory = inventory;
            this.simple = TransporterManager.getTransporter(inventory);
            this.memoryInv = NonNullList.m_122780_((int)inventory.getSlotCount(), (Object)ItemStack.f_41583_);
            int m = inventory.getSlotCount();
            for (int i = 0; i < m; ++i) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack.m_41619_()) continue;
                this.memoryInv.set(i, (Object)StackUtil.copyWithSize(stack, 1));
            }
        }

        @Override
        public int addItem(ItemStack stack, Direction dir, boolean simulate) {
            int i;
            int invSize = this.getInventorySize(dir);
            IntArrayList emptySlots = new IntArrayList(invSize);
            int stackSize = stack.m_41613_();
            int added = 0;
            for (i = 0; i < invSize; ++i) {
                if (!this.inventory.canInsert(i, stack)) continue;
                ItemStack inv = this.inventory.getStackInSlot(i);
                boolean wasEmpty = false;
                if (inv.m_41619_()) {
                    ItemStack memory = (ItemStack)this.memoryInv.get(i);
                    if (memory.m_41619_()) {
                        emptySlots.add(i);
                        continue;
                    }
                    inv = memory;
                    wasEmpty = true;
                }
                if (StackUtil.isStackEqual(inv, stack)) {
                    if (wasEmpty) {
                        int toAdd = Math.min(this.inventory.getMaxStackSize(i), stackSize - added);
                        if (!simulate) {
                            this.inventory.setStackInSlot(i, StackUtil.copyWithSize(stack, toAdd));
                        }
                        if ((added += toAdd) >= stackSize) {
                            return added;
                        }
                    } else {
                        int room = Math.min(inv.m_41741_() - inv.m_41613_(), this.inventory.getMaxStackSize(i));
                        if (room <= 0) continue;
                        int toAdd = Math.min(room, stackSize - added);
                        if (!simulate) {
                            inv.m_41769_(toAdd);
                            this.inventory.setStackInSlot(i, inv);
                        }
                        added += toAdd;
                    }
                }
                if (added < stackSize) continue;
                return added;
            }
            int m = emptySlots.size();
            for (i = 0; i < m; ++i) {
                int toAdd = Math.min(this.inventory.getMaxStackSize(i), stackSize - added);
                if (!simulate) {
                    this.inventory.setStackInSlot(emptySlots.getInt(i), StackUtil.copyWithSize(stack, toAdd));
                }
                if ((added += toAdd) < stackSize) continue;
                return added;
            }
            return added;
        }

        @Override
        public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
            return this.simple.removeItem(filter, dir, amount, simulate);
        }

        @Override
        public int getInventorySize(Direction dir) {
            return this.inventory.getSlotCount();
        }

        @Override
        public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
            return this.simple.getAllItems(dir, compareNBT);
        }

        @Override
        public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
            return this.simple.getInventory(dir, compareNBT);
        }
    }
}

