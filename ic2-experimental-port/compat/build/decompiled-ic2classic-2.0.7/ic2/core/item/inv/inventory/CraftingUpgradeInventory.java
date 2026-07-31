/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ContainerData
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraftforge.common.ForgeHooks
 */
package ic2.core.item.inv.inventory;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.IC2CraftingInventory;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.CraftingUpgradeContainer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

public class CraftingUpgradeInventory
extends PortableInventory
implements ContainerData {
    static final CraftingContainer CRAFTING = new IC2CraftingInventory(3, 3);
    Object2ObjectMap<ItemStack, IntList> mappedItems;
    public int flags = 4;
    public boolean valid = false;
    public int direction;
    int typeCount = 0;
    Component output;
    String recipeId;

    public CraftingUpgradeInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CraftingUpgradeContainer(this, player, this.getID(), windowID);
    }

    public int m_6413_(int index) {
        return index == 0 ? this.flags : this.direction;
    }

    public void m_8050_(int index, int value) {
        if (index == 0) {
            this.flags = value;
            return;
        }
        this.direction = value;
    }

    public int m_6499_() {
        return 2;
    }

    @Override
    public int getSlotCount() {
        return 10;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.mappedItems = CollectionUtils.createMap();
        this.valid = nbt.m_128471_("valid");
        this.flags = nbt.m_128451_("flags");
        this.direction = nbt.m_128451_("facing");
        if (!this.valid) {
            return;
        }
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("mappedItems", 10), CompoundTag.class)) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            this.mappedItems.put((Object)stack, (Object)IntArrayList.wrap((int[])data.m_128465_("slots")));
        }
        this.recipeId = nbt.m_128461_("recipeId");
        this.output = Component.Serializer.m_130701_((String)nbt.m_128461_("output"));
        this.typeCount = nbt.m_128451_("typeCount");
    }

    @Override
    public void save(CompoundTag nbt) {
        super.save(nbt);
        nbt.m_128379_("valid", this.valid);
        nbt.m_128405_("flags", this.flags);
        nbt.m_128405_("facing", this.direction);
        if (!this.valid) {
            nbt.m_128473_("mappedItems");
            nbt.m_128473_("recipeId");
            nbt.m_128473_("output");
            nbt.m_128473_("typeCount");
            return;
        }
        ListTag list = new ListTag();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.mappedItems)) {
            CompoundTag data = ((ItemStack)entry.getKey()).m_41739_(new CompoundTag());
            data.m_128385_("slots", ((IntList)entry.getValue()).toIntArray());
            list.add((Object)data);
        }
        nbt.m_128365_("mappedItems", (Tag)list);
        nbt.m_128359_("recipeId", this.recipeId);
        nbt.m_128359_("output", Component.Serializer.m_130703_((Component)this.output));
        nbt.m_128344_("typeCount", (byte)this.typeCount);
    }

    public void injectRecipe(ListTag list, Player player) {
        this.valid = false;
        this.inventory.clear();
        this.recipeId = null;
        for (CompoundTag data : NBTListWrapper.wrap(list, CompoundTag.class)) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            int[] slots = data.m_128465_("slots");
            for (int x = 0; x < slots.length; ++x) {
                this.inventory.set(slots[x], (Object)stack.m_41777_());
            }
        }
        this.validateRecipe(player);
    }

    public void validateRecipe(Player player) {
        this.valid = false;
        this.typeCount = 0;
        this.mappedItems.clear();
        CRAFTING.m_6211_();
        for (int i = 0; i < 9; ++i) {
            CRAFTING.m_6836_(i, ((ItemStack)this.inventory.get(i)).m_41777_());
        }
        ForgeHooks.setCraftingPlayer((Player)player);
        CraftingRecipe recipe = IC2.PLATFORM.getRecipes().m_44015_(RecipeType.f_44107_, (Container)CRAFTING, player.f_19853_).orElse(null);
        this.inventory.set(9, (Object)(recipe == null ? ItemStack.f_41583_ : recipe.m_5874_((Container)CRAFTING).m_41777_()));
        ForgeHooks.setCraftingPlayer(null);
        if (((ItemStack)this.inventory.get(9)).m_41619_()) {
            this.output = null;
            this.recipeId = null;
            return;
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(i);
        }
        this.calculateData();
        this.output = ((ItemStack)this.inventory.get(9)).m_41786_();
        this.recipeId = recipe.m_6423_().toString();
        this.valid = true;
        this.markDirty();
    }

    public void setFacing(int index) {
        if (this.direction != index) {
            this.direction = index;
            this.markDirty();
        }
    }

    public void setFlag(int index) {
        this.flags ^= index;
        this.markDirty();
    }

    public void clearResult() {
        this.valid = false;
        this.inventory.set(9, (Object)ItemStack.f_41583_);
    }

    protected void addSlot(int index) {
        ItemStack stack = (ItemStack)this.inventory.get(index);
        if (stack.m_41619_()) {
            return;
        }
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.mappedItems)) {
            if (!StackUtil.isStackEqual((ItemStack)entry.getKey(), stack) || ((ItemStack)entry.getKey()).m_41613_() != stack.m_41613_()) continue;
            ((IntList)entry.getValue()).add(index);
            return;
        }
        this.mappedItems.put((Object)stack, (Object)IntArrayList.wrap((int[])new int[]{index}));
    }

    protected void calculateData() {
        Object2IntLinkedOpenHashMap result = new Object2IntLinkedOpenHashMap();
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = (ItemStack)this.inventory.get(i);
            if (stack.m_41619_()) continue;
            boolean found = false;
            for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)result)) {
                if (!StackUtil.isStackEqual((ItemStack)entry.getKey(), stack)) continue;
                found = true;
                entry.setValue(entry.getIntValue() + stack.m_41613_());
                break;
            }
            if (found) continue;
            result.put((Object)stack, stack.m_41613_());
        }
        this.typeCount = result.size();
    }
}

