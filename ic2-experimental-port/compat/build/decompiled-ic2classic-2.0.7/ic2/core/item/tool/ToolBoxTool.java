/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.SlotAccess
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.ClickAction
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.inventory.tooltip.TooltipComponent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.items.IAutoEatable;
import ic2.api.items.readers.ICropReader;
import ic2.api.items.readers.IEUReader;
import ic2.api.items.readers.IThermometer;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.base.features.IScrollableInventory;
import ic2.core.item.inv.inventory.ToolBoxInventory;
import ic2.core.item.renders.tooltip.InventoryTooltip;
import ic2.core.item.wearable.modules.AutoFeedModuleItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Vec2i;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ToolBoxTool
extends IC2Item
implements IHasHeldSlotInventory,
IItemModel,
IScrollableInventory,
IEUReader,
IThermometer,
ICropReader {
    public static final String FLAGS = "flags";
    public static final ToolBoxData TOOL_BOX = new ToolBoxData("base", 8, 4, false, new Vec2i(0, -17), 53, new ResourceLocation("ic2", "textures/gui_sprites/items/guitoolbox.png"), SpecialFilters.TOOL_BOX);
    public static final ToolBoxData CARBON_TOOL_BOX = new ToolBoxData("nano", 15, 5, true, new Vec2i(0, 1), 44, new ResourceLocation("ic2", "textures/gui_sprites/items/guicarbonbox.png"), SpecialFilters.TOOL_BOX);
    public static final ToolBoxData QUANTUM_TOOL_BOX = new ToolBoxData("quantum", 45, 9, true, new Vec2i(0, 37), 8, new ResourceLocation("ic2", "textures/gui_sprites/items/guiiridiumbox.png"), SpecialFilters.TOOL_BOX);
    ToolBoxData data;

    public ToolBoxTool(String itemName, ToolBoxData data) {
        super(itemName, new PropertiesBuilder().maxStackSize(1).group(IC2.IC2_MAIN_GROUP));
        this.data = data;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, "tooltip.ic2.open_item_inventory", new Object[0]));
    }

    public Optional<TooltipComponent> m_142422_(ItemStack stack) {
        return Optional.of(InventoryTooltip.create(stack));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isSimulating()) {
            if (playerIn.m_6144_() && this.hasFoodCan(stack)) {
                playerIn.m_6672_(handIn);
                return InteractionResultHolder.m_19090_((Object)stack);
            }
            IC2.PLATFORM.launchGui(playerIn, handIn, Direction.NORTH, this.getInventory(playerIn, handIn, stack));
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    public ItemStack m_5922_(ItemStack stack, Level world, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            IItemTransporter transporter = TransporterManager.getTransporter(new ToolInventory(this.data, stack));
            ItemStack food = transporter.removeItem(SpecialFilters.AUTO_EATABLE, null, 1, false);
            if (!food.m_41619_() && !(food = ((IAutoEatable)food.m_41720_()).onEaten(food, world, player)).m_41619_()) {
                food.m_41774_(transporter.addItem(food, null, false));
                StackUtil.addOrDrop(player, food);
            }
        }
        return stack;
    }

    public boolean m_142305_(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction action, Player player, SlotAccess carry) {
        if (action == ClickAction.SECONDARY && slot.m_150651_(player) && !otherStack.m_41619_() && stack.m_41613_() <= 1 && this.data.getFilter().matches(otherStack)) {
            otherStack.m_41774_(TransporterManager.getTransporter(new ToolInventory(this.data, stack)).addItem(otherStack, null, false));
            return true;
        }
        return false;
    }

    public boolean m_142207_(ItemStack p_150888_, Slot p_150889_, ClickAction p_150890_, Player p_150891_) {
        return super.m_142207_(p_150888_, p_150889_, p_150890_, p_150891_);
    }

    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.EAT;
    }

    public int m_8105_(ItemStack stack) {
        return 20;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return ToolBoxInventory.create(player, this, stack, null, this.data).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return ToolBoxInventory.create(player, this, stack, slot, this.data).load(stack);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, @Nullable LivingEntity entity) {
        return this.isOpen(stack) ? 1 : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        ObjectList types = CollectionUtils.createList();
        types.add((ItemStack)new ItemStack((ItemLike)this));
        ItemStack stack = new ItemStack((ItemLike)this);
        stack.m_41700_("open", (Tag)ByteTag.m_128266_((byte)1));
        types.add((ItemStack)stack);
        return types;
    }

    @Override
    public boolean isCropReader(ItemStack stack) {
        return (StackUtil.getNbtData(stack).m_128451_(FLAGS) & 4) != 0;
    }

    @Override
    public boolean isThermometer(ItemStack stack) {
        return (StackUtil.getNbtData(stack).m_128451_(FLAGS) & 2) != 0;
    }

    @Override
    public boolean isEUReader(ItemStack stack) {
        return (StackUtil.getNbtData(stack).m_128451_(FLAGS) & 1) != 0;
    }

    public boolean hasFoodCan(ItemStack stack) {
        return (StackUtil.getNbtData(stack).m_128451_(FLAGS) & 8) != 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "tools/toolbox").get((this.isOpen(input) ? "filled_" : "empty_") + this.data.getItemTextureSuffix());
    }

    @Override
    public boolean canScrollInventory(ItemStack stack) {
        return this.data.isScroll();
    }

    @Override
    public int getScrollIndex(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("selected");
    }

    @Override
    public void setScrollIndex(ItemStack stack, int index) {
        stack.m_41784_().m_128405_("selected", index);
    }

    @Override
    public int getSlotCount(ItemStack stack) {
        return this.data.getSlotCount();
    }

    @Override
    public boolean isValidItem(ItemStack stack, ItemStack toInsert) {
        return this.data.getFilter().matches(toInsert);
    }

    @Override
    public IScrollableInventory.IndexedStack[] getItemsToSwapWith(ItemStack stack, boolean scan) {
        int slotCount = this.getSlotCount(stack);
        NonNullList items = NonNullList.m_122780_((int)slotCount, (Object)ItemStack.f_41583_);
        NBTUtils.loadItems(StackUtil.getNbtData(stack), (NonNullList<ItemStack>)items);
        int current = this.getScrollIndex(stack);
        int first = current == 0 ? slotCount - 1 : current;
        int second = (current + 1) % slotCount;
        if (scan && StackUtil.getNbtData(stack).m_128437_("Items", 10).size() > 0) {
            while (((ItemStack)items.get(first)).m_41619_()) {
                if (--first >= 0) continue;
                first = slotCount - 1;
            }
            while (((ItemStack)items.get(second)).m_41619_()) {
                second = (second + 1) % slotCount;
            }
        }
        return new IScrollableInventory.IndexedStack[]{new IScrollableInventory.IndexedStack(first, (ItemStack)items.get(first)), new IScrollableInventory.IndexedStack(second, (ItemStack)items.get(second))};
    }

    @Override
    public boolean swapItems(ItemStack stack, int clearSlot, int fillSlot, ItemStack toInsert) {
        int slotCount = this.getSlotCount(stack);
        if (fillSlot < 0 || fillSlot >= slotCount || clearSlot < 0 || clearSlot >= slotCount) {
            return false;
        }
        NonNullList items = NonNullList.m_122780_((int)slotCount, (Object)ItemStack.f_41583_);
        CompoundTag nbt = StackUtil.getNbtData(stack);
        NBTUtils.loadItems(StackUtil.getNbtData(stack), (NonNullList<ItemStack>)items);
        items.set(clearSlot, (Object)ItemStack.f_41583_);
        if (((ItemStack)items.get(fillSlot)).m_41619_()) {
            items.set(fillSlot, (Object)toInsert);
        } else {
            items.set(clearSlot, (Object)toInsert);
        }
        NBTUtils.saveItems(nbt, (NonNullList<ItemStack>)items);
        stack.m_41751_(nbt.m_128456_() ? null : nbt);
        return true;
    }

    public boolean isOpen(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128471_("open");
    }

    private static IItemTransporter getTransporter(Player player, ItemStack armor) {
        Inventory inventory = player.m_150109_();
        int m = Inventory.m_36059_();
        for (int i = 0; i < m; ++i) {
            ToolBoxTool tool;
            ItemStack stack = (ItemStack)inventory.f_35974_.get(i);
            if (!(stack.m_41720_() instanceof ToolBoxTool) || (tool = (ToolBoxTool)stack.m_41720_()).isOpen(stack) || !tool.hasFoodCan(stack)) continue;
            return TransporterManager.getTransporter(new ToolInventory(tool.data, stack));
        }
        return null;
    }

    static {
        AutoFeedModuleItem.addFoodSource(ToolBoxTool::getTransporter);
    }

    public static class ToolBoxData {
        String itemTextureSuffix;
        int slotCount;
        int columns;
        boolean scroll;
        Vec2i inventoryOffset;
        int slotXStart;
        ResourceLocation guiTexture;
        IFilter filter;

        public ToolBoxData(String itemTextureSuffix, int slotCount, int itemsPerRow, boolean scroll, Vec2i inventoryOffset, int slotXStart, ResourceLocation guiTexture, IFilter filter) {
            this.itemTextureSuffix = itemTextureSuffix;
            this.slotCount = slotCount;
            this.columns = itemsPerRow;
            this.scroll = scroll;
            this.inventoryOffset = inventoryOffset;
            this.slotXStart = slotXStart;
            this.guiTexture = guiTexture;
            this.filter = filter;
        }

        public String getItemTextureSuffix() {
            return this.itemTextureSuffix;
        }

        public int getSlotCount() {
            return this.slotCount;
        }

        public int getColumns() {
            return this.columns;
        }

        public boolean isScroll() {
            return this.scroll;
        }

        public Vec2i getInventoryOffset() {
            return this.inventoryOffset;
        }

        public int getSlotXStart() {
            return this.slotXStart;
        }

        public ResourceLocation getGuiTexture() {
            return this.guiTexture;
        }

        public IFilter getFilter() {
            return this.filter;
        }
    }

    private static class ToolInventory
    implements IHasInventory {
        ItemStack stack;
        NonNullList<ItemStack> inventory;

        public ToolInventory(ToolBoxData data, ItemStack stack) {
            this.stack = stack;
            this.inventory = NonNullList.m_122780_((int)data.getSlotCount(), (Object)ItemStack.f_41583_);
            NBTUtils.loadItems(StackUtil.getNbtData(stack), this.inventory);
        }

        public void onChanged() {
            if (IC2.PLATFORM.isRendering()) {
                return;
            }
            NBTUtils.saveItems(this.stack.m_41784_(), this.inventory);
            NBTUtils.putInt(this.stack.m_41784_(), ToolBoxTool.FLAGS, StackUtil.hasItems(this.inventory, SpecialFilters.EU_READER, SpecialFilters.THERMOMETER, SpecialFilters.CROP_SCANNER, SpecialFilters.AUTO_EATABLE), 0);
        }

        @Override
        public int getSlotCount() {
            return this.inventory.size();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return (ItemStack)this.inventory.get(slot);
        }

        @Override
        public void setStackInSlot(int slot, ItemStack stack) {
            this.inventory.set(slot, (Object)stack);
            this.onChanged();
        }

        @Override
        public int getMaxStackSize(int slot) {
            return 64;
        }

        @Override
        public boolean canInsert(int slot, ItemStack stack) {
            return true;
        }

        @Override
        public boolean canExtract(int slot, ItemStack stack) {
            return true;
        }
    }
}

