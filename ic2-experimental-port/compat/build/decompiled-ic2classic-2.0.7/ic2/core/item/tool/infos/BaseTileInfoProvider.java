/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool.infos;

import ic2.api.items.IDisplayProvider;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.impl.ProgressDisplayInfo;
import ic2.api.tiles.display.impl.StringDisplayInfo;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2SimpleItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.inv.container.CardContainer;
import ic2.core.item.inv.inventory.CardInventory;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseTileInfoProvider
extends IC2SimpleItem
implements IDisplayProvider,
IItemModel,
INetworkItemEvent,
INetworkItemBufferEvent<StringDataBuffer>,
IHasHeldSlotInventory {
    public static final int FLAG_SHOW_NAME = 1;
    String inactiveTexture;

    public BaseTileInfoProvider(String itemName, String textureFolder, String textureName) {
        this(itemName, new PropertiesBuilder().maxStackSize(1), textureFolder, textureName);
    }

    public BaseTileInfoProvider(String itemName, PropertiesBuilder properties, String textureFolder, String textureName) {
        super(itemName, properties, textureFolder, "active_" + textureName);
        this.inactiveTexture = "inactive_" + textureName;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BLOCK_CLICK, KeyHelper.SNEAK_KEY, "tooltip.item.ic2.card.init", new Object[0]));
    }

    protected abstract boolean isValidTile(BlockEntity var1);

    protected abstract int createDefaultFlags();

    protected int[] createDefaultColors() {
        return new int[0];
    }

    protected List<SettingEntry> createSettings() {
        return CollectionUtils.createList();
    }

    private boolean isInvalid(BlockEntity tile) {
        return !this.isValidTile(tile);
    }

    protected IDisplayInfo createTileError(ItemStack stack, BlockEntity tile) {
        if (tile == null) {
            if (this.isValidCardData(stack)) {
                return new StringDisplayInfo(() -> this.translate("tooltip.item.ic2.base_card.tile_not_found"), new ErrorTester(stack, Objects::isNull));
            }
            return new StringDisplayInfo(() -> this.translate("tooltip.item.ic2.base_card.invalid_data"), () -> true);
        }
        return new StringDisplayInfo(() -> this.translate("tooltip.item.ic2.base_card.invalid_tile"), new ErrorTester(stack, this::isInvalid));
    }

    protected BooleanSupplier createAliveTester(BlockEntity tile) {
        return new AliveTester(tile);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        if (this instanceof IHasHeldGui && IC2.KEYBOARD.isSideInventoryKeyDown(player)) {
            ItemStack stack = player.m_21120_(hand);
            IC2.PLATFORM.launchGui(player, hand, Direction.NORTH, this.getInventory(player, hand, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(world, player, hand);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        BlockEntity tile;
        if (context.m_7078_() && (tile = context.m_43725_().m_7702_(context.m_8083_())) != null && this.isValidTile(tile)) {
            this.storeTileEntity(context.m_43722_(), tile);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new CardInventory(player, this, stack, null, this.createDefaultColors().length, this.createSettings()).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new CardInventory(player, this, stack, slot, this.createDefaultColors().length, this.createSettings()).load(stack);
    }

    @Override
    public int getModelIndexForStack(ItemStack stack, LivingEntity entity) {
        return StackUtil.getNbtData(stack).m_128454_("pos") != 0L ? 1 : 0;
    }

    @Override
    public List<ItemStack> getModelTypes() {
        return ObjectLists.singleton((Object)new ItemStack((ItemLike)this));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSprite(ItemStack input) {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), this.textureFolder).get(this.inactiveTexture);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer()) {
            CardContainer container = (CardContainer)this.getContainer(player, CardContainer.class);
            if (container == null) {
                return;
            }
            ((CardInventory)container.getHolder()).onDataReceived(key, value);
        }
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, StringDataBuffer buffer, Dist targetSide) {
        if (!id.equalsIgnoreCase("customName") || targetSide.isClient()) {
            return;
        }
        CardContainer container = (CardContainer)this.getContainer(player, CardContainer.class);
        if (container == null) {
            return;
        }
        ((CardInventory)container.getHolder()).updateName(buffer.getValue());
    }

    public void storeFlags(ItemStack stack, int flags) {
        stack.m_41784_().m_128405_("flags", flags);
    }

    public void storeTileEntity(ItemStack stack, BlockEntity tile) {
        Nameable name;
        CompoundTag data = stack.m_41784_();
        data.m_128359_("world", tile.m_58904_().m_46472_().m_135782_().toString());
        data.m_128356_("pos", tile.m_58899_().m_121878_());
        if (!data.m_128441_("flags")) {
            data.m_128405_("flags", this.createDefaultFlags());
        }
        if (!data.m_128441_("colors")) {
            data.m_128385_("colors", this.createDefaultColors());
        }
        if (tile instanceof Nameable && (name = (Nameable)tile).m_8077_()) {
            data.m_128359_("base_name", Component.Serializer.m_130703_((Component)name.m_7770_()));
            return;
        }
        data.m_128359_("base_name", Component.Serializer.m_130703_((Component)Component.m_237115_((String)tile.m_58900_().m_60734_().m_7705_())));
    }

    public static int getColor(ItemStack stack, int colorIndex, int defaultValue) {
        int[] colors = StackUtil.getNbtData(stack).m_128465_("colors");
        return colors == null || colors.length <= colorIndex ? defaultValue : DyeColor.m_41053_((int)colors[colorIndex]).m_41071_() | 0xFF000000;
    }

    public Component withColor(ItemStack stack, int index, MutableComponent comp) {
        return comp.m_130948_(ColorUtils.toColor(BaseTileInfoProvider.getColor(stack, index, -1)));
    }

    public int getFlags(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("flags");
    }

    public Component getTileName(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return this.withColor(stack, 0, data.m_128441_("name") ? Component.Serializer.m_130701_((String)data.m_128461_("name")) : (data.m_128441_("base_name") ? Component.Serializer.m_130701_((String)data.m_128461_("base_name")) : this.string("Error")));
    }

    public BlockEntity getTileEntity(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return data.m_128441_("world") && data.m_128441_("pos") ? IC2.PLATFORM.getTileEntity((ResourceKey<Level>)ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)ResourceLocation.m_135820_((String)data.m_128461_("world"))), BlockPos.m_122022_((long)data.m_128454_("pos")), true) : null;
    }

    public <T> T getTileEntity(ItemStack stack, Class<T> clz) {
        BlockEntity tile = this.getTileEntity(stack);
        return clz.isInstance(tile) ? (T)clz.cast(tile) : null;
    }

    public ProgressDisplayInfo.Progress createProgress(IEUStorage machine, int color) {
        return this.createProgress(machine, color, ColorUtils.darker(color));
    }

    public ProgressDisplayInfo.Progress createProgress(IEUStorage machine, int mainColor, int subColor) {
        return new ProgressDisplayInfo.Progress((double)machine.getStoredEU() / (double)machine.getMaxEU(), mainColor, subColor);
    }

    public ProgressDisplayInfo.Progress createProgress(IFuelStorage machine, int color) {
        return this.createProgress(machine, color, ColorUtils.darker(color));
    }

    public ProgressDisplayInfo.Progress createProgress(IFuelStorage storage, int mainColor, int subColor) {
        return new ProgressDisplayInfo.Progress((double)storage.getFuel() / (double)storage.getMaxFuel(), mainColor, subColor);
    }

    public ProgressDisplayInfo.Progress createProgress(IProgressMachine machine, int color) {
        return this.createProgress(machine, color, ColorUtils.darker(color));
    }

    public ProgressDisplayInfo.Progress createProgress(IProgressMachine machine, int mainColor, int subColor) {
        return new ProgressDisplayInfo.Progress((double)machine.getProgress() / (double)machine.getMaxProgress(), mainColor, subColor);
    }

    private boolean isValidCardData(ItemStack stack) {
        CompoundTag data = StackUtil.getNbtData(stack);
        return data.m_128441_("world") && data.m_128441_("pos");
    }

    private static class ErrorTester
    implements BooleanSupplier {
        BlockPos pos;
        ResourceKey<Level> world;
        Predicate<BlockEntity> tester;

        public ErrorTester(ItemStack stack, Predicate<BlockEntity> tester) {
            CompoundTag data = StackUtil.getNbtData(stack);
            this.pos = BlockPos.m_122022_((long)data.m_128454_("pos"));
            this.world = ResourceKey.m_135785_((ResourceKey)Registry.f_122819_, (ResourceLocation)ResourceLocation.m_135820_((String)data.m_128461_("world")));
            this.tester = tester;
        }

        @Override
        public boolean getAsBoolean() {
            return this.tester.test(IC2.PLATFORM.getTileEntity(this.world, this.pos, true));
        }
    }

    private static class AliveTester
    implements BooleanSupplier {
        BlockEntity tile;
        boolean cache = false;
        long lastTime = -1L;

        public AliveTester(BlockEntity tile) {
            this.tile = tile;
        }

        @Override
        public boolean getAsBoolean() {
            long time = this.tile.m_58904_().m_46467_();
            if (this.lastTime != time) {
                this.lastTime = time;
                this.cache = !this.tile.m_58901_() && this.tile.m_58904_().m_7232_(this.tile.m_58899_().m_123341_() >> 4, this.tile.m_58899_().m_123343_() >> 4);
            }
            return this.cache;
        }
    }

    public static class NameProvider
    implements Supplier<Component> {
        Component comp;

        public NameProvider(Component comp) {
            this.comp = comp;
        }

        @Override
        public Component get() {
            return this.comp;
        }
    }

    public static enum SettingType {
        SIMPLE,
        COLOR,
        TEXT;

    }

    public static class SettingEntry {
        public final Object display;
        public final Component tooltip;
        public final int index;
        public final SettingType type;
        public final int x;
        public final int y;

        public SettingEntry(Object display, String tooltip, int index, SettingType type, int x, int y) {
            this.display = display;
            this.tooltip = Component.m_237115_((String)tooltip);
            this.index = index;
            this.type = type;
            this.x = x;
            this.y = y;
        }
    }
}

