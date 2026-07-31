/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.util.FakePlayerFactory
 */
package ic2.core.item.upgrades.io.item;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.IMachine;
import ic2.core.IC2;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.inv.IC2CraftingInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.inv.container.CraftingUpgradeContainer;
import ic2.core.item.inv.inventory.CraftingUpgradeInventory;
import ic2.core.item.upgrades.base.BaseDirectionalUpgrade;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayerFactory;

public class CraftingUpgradeItem
extends BaseDirectionalUpgrade
implements IHasHeldSlotInventory,
INetworkItemEvent,
INetworkItemBufferEvent<NBTBuffer> {
    static final CraftingContainer CRAFTING = new IC2CraftingInventory(3, 3);
    public static final String VALID = "valid";
    public static final String LAST_CRAFT = "last_craft";
    public static final String DELAY = "delay";
    public static final String QUEUE = "queue";
    public static final String ERROR = "error";
    public static final String TYPE_COUNT = "typeCount";
    public static final String FLAG = "flags";
    public static final String RECIPE_ID = "recipeId";

    public CraftingUpgradeItem() {
        super("crafting");
        this.functions.remove((Object)IUpgradeItem.Functions.RECIPE);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.TRANSPORT_MOD;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        CompoundTag nbt = StackUtil.getNbtData(stack);
        int data = nbt.m_128451_(ERROR);
        if (data > 0) {
            helper.addSimpleToolTip("gui.ic2.crafting_upgrade.error." + data, new Object[0]);
        }
        helper.addSimpleToolTip("gui.ic2.crafting_upgrade.delay", nbt.m_128451_(DELAY));
        ListTag list = nbt.m_128437_(QUEUE, 10);
        if (list.size() > 0) {
            helper.addSimpleToolTip("gui.ic2.crafting_upgrade.items_stuck", new Object[0]);
            int m = list.size();
            for (int i = 0; i < m; ++i) {
                ItemStack cached = ItemStack.m_41712_((CompoundTag)list.m_128728_(i));
                if (cached.m_41619_()) continue;
                helper.addSimpleToolTip(this.string(cached.m_41613_() + "x ").m_7220_(cached.m_41611_()));
            }
        }
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new CraftingUpgradeInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new CraftingUpgradeInventory(player, this, stack, slot).load(stack);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        CraftingUpgradeContainer container;
        if (target.isDedicatedServer() && (container = (CraftingUpgradeContainer)this.getContainer(player, CraftingUpgradeContainer.class)) != null) {
            CraftingUpgradeInventory inv = (CraftingUpgradeInventory)container.getHolder();
            switch (key) {
                case 0: {
                    inv.validateRecipe(player);
                    break;
                }
                case 1: {
                    inv.setFlag(value);
                    break;
                }
                case 2: {
                    inv.setFacing(value);
                }
            }
        }
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, NBTBuffer buffer, Dist target) {
        CraftingUpgradeContainer container;
        if (target.isDedicatedServer() && id.equalsIgnoreCase("jei") && (container = (CraftingUpgradeContainer)this.getContainer(player, CraftingUpgradeContainer.class)) != null) {
            ((CraftingUpgradeInventory)container.getHolder()).injectRecipe(buffer.getNBT().m_128437_("RecipeItems", 10), player);
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.PLATFORM.isSimulating()) {
                IC2.PLATFORM.launchGui(playerIn, handIn, null, this.getInventory(playerIn, handIn, stack));
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    @Override
    public void onInstall(ItemStack stack, IMachine machine) {
        CompoundTag nbt = stack.m_41784_();
        if (!nbt.m_128471_(VALID)) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128405_(ERROR, 1);
            return;
        }
        if (this.getFacing(stack) == null) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128405_(ERROR, 2);
            return;
        }
        if (!(machine instanceof IInventoryMachine)) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128405_(ERROR, 3);
            return;
        }
        IHasInventory inventory = ((IInventoryMachine)machine).getOutputInventory();
        if (inventory == null) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128405_(ERROR, 3);
            return;
        }
        if (inventory.getSlotCount() < nbt.m_128451_(TYPE_COUNT)) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128405_(ERROR, 4);
            return;
        }
        nbt.m_128405_(DELAY, 20);
        nbt.m_128405_(ERROR, -1);
    }

    @Override
    public void onTick(ItemStack stack, IMachine machine) {
        long world;
        CompoundTag nbt = stack.m_41784_();
        if (!nbt.m_128471_(VALID)) {
            return;
        }
        int delay = nbt.m_128451_(DELAY);
        if (delay <= 0) {
            return;
        }
        long last = nbt.m_128454_(LAST_CRAFT);
        if (last + (long)delay > (world = machine.getWorldObj().m_46467_())) {
            return;
        }
        nbt.m_128356_(LAST_CRAFT, world);
        CraftingRecipe recipe = IC2.PLATFORM.getRecipes().m_44043_(new ResourceLocation(nbt.m_128461_(RECIPE_ID))).orElse(null);
        if (recipe == null) {
            nbt.m_128379_(VALID, false);
            return;
        }
        ListTag list = nbt.m_128437_(QUEUE, 10);
        if (!list.isEmpty()) {
            Direction dir = this.getFacing(stack);
            if (dir == null) {
                nbt.m_128405_(DELAY, -1);
                nbt.m_128344_(ERROR, (byte)2);
                return;
            }
            IItemTransporter transporter = TransporterManager.getTransporter(machine.getConnectedInventory(dir));
            if (transporter == null && (transporter = TransporterManager.getTransporter(machine.getConnectedTube(dir))) == null) {
                CraftingUpgradeItem.changeDelay(nbt, 5);
                return;
            }
            dir = dir.m_122424_();
            ListTag newList = new ListTag();
            int m = list.size();
            for (int i = 0; i < m; ++i) {
                ItemStack cached = ItemStack.m_41712_((CompoundTag)list.m_128728_(i));
                if (cached.m_41619_()) continue;
                cached.m_41774_(transporter.addItem(cached, dir, false));
                if (cached.m_41613_() <= 0) continue;
                newList.add((Object)cached.m_41739_(new CompoundTag()));
            }
            if (newList.isEmpty()) {
                nbt.m_128473_(QUEUE);
                CraftingUpgradeItem.changeDelay(nbt, -5);
            } else {
                nbt.m_128365_(QUEUE, (Tag)newList);
                CraftingUpgradeItem.changeDelay(nbt, 5);
            }
            return;
        }
        if (!(machine instanceof IInventoryMachine)) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128344_(ERROR, (byte)3);
            return;
        }
        IHasInventory inv = ((IInventoryMachine)machine).getOutputInventory();
        if (inv == null || inv.getSlotCount() < nbt.m_128451_(TYPE_COUNT)) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128344_(ERROR, (byte)4);
            return;
        }
        Direction dir = this.getFacing(stack);
        if (dir == null) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128344_(ERROR, (byte)2);
            return;
        }
        IItemTransporter destination = TransporterManager.getTransporter(machine.getConnectedInventory(dir));
        if (destination == null && (destination = TransporterManager.getTransporter(machine.getConnectedTube(dir))) == null) {
            CraftingUpgradeItem.changeDelay(nbt, 5);
            return;
        }
        dir = dir.m_122424_();
        Object2ObjectMap<ItemStack, IntList> recipeItems = CraftingUpgradeItem.createMap(nbt);
        if (recipeItems.isEmpty()) {
            nbt.m_128405_(DELAY, -1);
            nbt.m_128344_(ERROR, (byte)4);
            return;
        }
        int flags = nbt.m_128451_(FLAG);
        SimpleInventory wrapper = SimpleInventory.createCopy(inv);
        IItemTransporter source = TransporterManager.getTransporter(wrapper);
        CRAFTING.m_6211_();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(recipeItems)) {
            int itemsNeeded = ((ItemStack)entry.getKey()).m_41613_();
            int needed = itemsNeeded * ((IntList)entry.getValue()).size();
            ItemStack fetched = source.removeItem(new StackFilter((ItemStack)entry.getKey(), flags | 4), dir, needed, false);
            if (fetched.m_41613_() < needed) {
                CraftingUpgradeItem.changeDelay(nbt, 5);
                return;
            }
            IntList slots = (IntList)entry.getValue();
            int m = slots.size();
            for (int i = 0; i < m; ++i) {
                int slot = slots.getInt(i);
                CRAFTING.m_6836_(slot, fetched.m_41620_(itemsNeeded));
            }
        }
        ItemStack output = recipe.m_5874_((Container)CRAFTING);
        if (output.m_41619_()) {
            CraftingUpgradeItem.changeDelay(nbt, 5);
            return;
        }
        ForgeHooks.setCraftingPlayer((Player)FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)machine.getWorldObj())));
        NonNullList remaining = recipe.m_7457_((Container)CRAFTING);
        ForgeHooks.setCraftingPlayer(null);
        output.m_41774_(destination.addItem(output, dir, false));
        ListTag queue = nbt.m_128437_(QUEUE, 10);
        if (output.m_41613_() > 0) {
            queue.add((Object)output.m_41739_(new CompoundTag()));
        }
        int m = remaining.size();
        for (int i = 0; i < m; ++i) {
            ItemStack entry = (ItemStack)remaining.get(i);
            if (entry.m_41619_()) continue;
            entry.m_41774_(destination.addItem(entry, dir, false));
            if (entry.m_41613_() <= 0) continue;
            queue.add((Object)entry.m_41739_(new CompoundTag()));
        }
        nbt.m_128365_(QUEUE, (Tag)queue);
        wrapper.pasteTo(inv);
        CraftingUpgradeItem.changeDelay(nbt, -5);
    }

    public static void changeDelay(CompoundTag nbt, int change) {
        nbt.m_128405_(DELAY, Mth.m_14045_((int)(nbt.m_128451_(DELAY) + change), (int)5, (int)200));
    }

    public static Object2ObjectMap<ItemStack, IntList> createMap(CompoundTag nbt) {
        Object2ObjectSortedMap recipes = CollectionUtils.createLinkedMap();
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("mappedItems", 10), CompoundTag.class)) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)data);
            if (stack.m_41619_()) continue;
            recipes.put((Object)stack, (Object)IntArrayList.wrap((int[])data.m_128465_("slots")));
        }
        return recipes;
    }
}

