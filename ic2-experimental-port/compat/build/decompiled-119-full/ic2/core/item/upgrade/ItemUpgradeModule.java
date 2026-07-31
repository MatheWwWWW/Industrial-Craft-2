/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item.upgrade;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.api.upgrade.IFullUpgrade;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.gui.dynamic.DynamicHandHeldContainer;
import ic2.core.init.Localization;
import ic2.core.item.EnvItemHandler;
import ic2.core.item.IHandHeldSubInventory;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.item.upgrade.ComparisonType;
import ic2.core.item.upgrade.HandHeldAdvancedUpgrade;
import ic2.core.item.upgrade.NbtSettings;
import ic2.core.item.upgrade.UpgradeSettings;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class ItemUpgradeModule
extends Item
implements IFullUpgrade,
IHandHeldSubInventory,
IItemHudInfo {
    private static final DecimalFormat decimalformat = new DecimalFormat("0.##");
    private static final List<EnvItemHandler.AdjacentInventory> emptyInvList = Collections.emptyList();
    private static final List<LiquidUtil.AdjacentFluidHandler> emptyFhList = Collections.emptyList();
    public final UpgradeType type;

    public ItemUpgradeModule(Item.Properties properties, UpgradeType upgradeType) {
        super(properties);
        this.type = upgradeType;
        IC2.envProxy.runAfterRegistryInit(() -> UpgradeRegistry.register(new ItemStack((ItemLike)this, 1)));
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("Machine Upgrade");
        return linkedList;
    }

    @Environment(value=EnvType.CLIENT)
    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.m_7373_(itemStack, level, list, tooltipFlag);
        switch (this.type) {
            case overclocker: {
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.overclocker.time", (Object[])new Object[]{decimalformat.format(100.0 * Math.pow(this.getProcessTimeMultiplier(itemStack, null), StackUtil.getSize(itemStack)))}).m_130940_(ChatFormatting.GRAY));
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.overclocker.power", (Object[])new Object[]{decimalformat.format(100.0 * Math.pow(this.getEnergyDemandMultiplier(itemStack, null), StackUtil.getSize(itemStack)))}).m_130940_(ChatFormatting.GRAY));
                break;
            }
            case transformer: {
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.transformer", (Object[])new Object[]{this.getExtraTier(itemStack, null) * StackUtil.getSize(itemStack)}).m_130940_(ChatFormatting.GRAY));
                break;
            }
            case energy_storage: {
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.storage", (Object[])new Object[]{this.getExtraEnergyStorage(itemStack, null) * StackUtil.getSize(itemStack)}).m_130940_(ChatFormatting.GRAY));
                break;
            }
            case ejector: 
            case advanced_ejector: 
            case fluid_ejector: {
                String string = ItemUpgradeModule.getSideName(itemStack);
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.ejector", (Object[])new Object[]{Localization.translate(string)}).m_130940_(ChatFormatting.GRAY));
                break;
            }
            case pulling: 
            case advanced_pulling: 
            case fluid_pulling: {
                String string = ItemUpgradeModule.getSideName(itemStack);
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.pulling", (Object[])new Object[]{Localization.translate(string)}).m_130940_(ChatFormatting.GRAY));
                break;
            }
            case redstone_inverter: {
                list.add((Component)Component.m_237115_((String)"ic2.tooltip.upgrade.redstone").m_130940_(ChatFormatting.GRAY));
                break;
            }
            case remote_interface: {
                list.add((Component)Component.m_237110_((String)"ic2.tooltip.upgrade.remote_interface", (Object[])new Object[]{StackUtil.getSize(itemStack)}).m_130940_(ChatFormatting.GRAY));
            }
        }
    }

    private static String getSideName(ItemStack itemStack) {
        Direction direction = ItemUpgradeModule.getDirection(itemStack);
        if (direction == null) {
            return "ic2.tooltip.upgrade.ejector.anyside";
        }
        switch (direction) {
            case WEST: {
                return "ic2.dir.west";
            }
            case EAST: {
                return "ic2.dir.east";
            }
            case DOWN: {
                return "ic2.dir.bottom";
            }
            case UP: {
                return "ic2.dir.top";
            }
            case NORTH: {
                return "ic2.dir.north";
            }
            case SOUTH: {
                return "ic2.dir.south";
            }
        }
        throw new RuntimeException("invalid dir: " + direction);
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        ItemStack itemStack = useOnContext.m_43722_();
        if (this.type.directional) {
            int n = 1 + useOnContext.m_43719_().ordinal();
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            if (compoundTag.m_128445_("dir") == n) {
                compoundTag.m_128344_("dir", (byte)0);
            } else {
                compoundTag.m_128344_("dir", (byte)n);
            }
            if (useOnContext.m_43725_().m_5776_()) {
                switch (this.type) {
                    case ejector: 
                    case advanced_ejector: {
                        IC2.sideProxy.messagePlayer(useOnContext.m_43723_(), Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(ItemUpgradeModule.getSideName(itemStack))), new Object[0]);
                        break;
                    }
                    case pulling: 
                    case advanced_pulling: {
                        IC2.sideProxy.messagePlayer(useOnContext.m_43723_(), Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(ItemUpgradeModule.getSideName(itemStack))), new Object[0]);
                        break;
                    }
                    case fluid_ejector: {
                        IC2.sideProxy.messagePlayer(useOnContext.m_43723_(), Localization.translate("ic2.tooltip.upgrade.ejector", Localization.translate(ItemUpgradeModule.getSideName(itemStack))), new Object[0]);
                        break;
                    }
                    case fluid_pulling: {
                        IC2.sideProxy.messagePlayer(useOnContext.m_43723_(), Localization.translate("ic2.tooltip.upgrade.pulling", Localization.translate(ItemUpgradeModule.getSideName(itemStack))), new Object[0]);
                        break;
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.m_21120_(interactionHand);
        switch (this.type) {
            case advanced_ejector: 
            case advanced_pulling: {
                if (!level.f_46443_) {
                    this.getInventory(player, interactionHand, itemStack).openManagedItem(player, interactionHand, null);
                }
                return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
            }
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
    }

    public boolean onDroppedByPlayer(ItemStack itemStack, Player player) {
        switch (this.type) {
            case advanced_ejector: 
            case advanced_pulling: {
                HandHeldInventory handHeldInventory;
                if (player.m_20193_().f_46443_ || StackUtil.isEmpty(itemStack) || !(player.f_36096_ instanceof DynamicHandHeldContainer) || !((handHeldInventory = (HandHeldInventory)((DynamicHandHeldContainer)player.f_36096_).base) instanceof HandHeldAdvancedUpgrade) || !handHeldInventory.isThisContainer(itemStack)) break;
                handHeldInventory.saveAsThrown(itemStack);
                ((ServerPlayer)player).m_6915_();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(ItemStack itemStack, Set<UpgradableProperty> set) {
        switch (this.type) {
            case ejector: 
            case advanced_ejector: {
                return set.contains((Object)UpgradableProperty.ItemProducing);
            }
            case pulling: 
            case advanced_pulling: {
                return set.contains((Object)UpgradableProperty.ItemConsuming);
            }
            case fluid_ejector: {
                return set.contains((Object)UpgradableProperty.FluidProducing);
            }
            case fluid_pulling: {
                return set.contains((Object)UpgradableProperty.FluidConsuming);
            }
            case energy_storage: {
                return set.contains((Object)UpgradableProperty.EnergyStorage);
            }
            case overclocker: {
                return set.contains((Object)UpgradableProperty.Processing) || set.contains((Object)UpgradableProperty.Augmentable);
            }
            case redstone_inverter: {
                return set.contains((Object)UpgradableProperty.RedstoneSensitive);
            }
            case transformer: {
                return set.contains((Object)UpgradableProperty.Transformer);
            }
            case remote_interface: {
                return set.contains((Object)UpgradableProperty.RemotelyAccessible);
            }
        }
        return false;
    }

    @Override
    public int getAugmentation(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case overclocker: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int getExtraProcessTime(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        return 0;
    }

    @Override
    public double getProcessTimeMultiplier(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case overclocker: {
                return 0.7;
            }
        }
        return 1.0;
    }

    @Override
    public int getExtraEnergyDemand(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        return 0;
    }

    @Override
    public double getEnergyDemandMultiplier(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case overclocker: {
                return 1.6;
            }
        }
        return 1.0;
    }

    @Override
    public int getExtraEnergyStorage(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case energy_storage: {
                return 10000;
            }
        }
        return 0;
    }

    @Override
    public double getEnergyStorageMultiplier(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        return 1.0;
    }

    @Override
    public int getExtraTier(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case transformer: {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean modifiesRedstoneInput(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        switch (this.type) {
            case redstone_inverter: {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRedstoneInput(ItemStack itemStack, IUpgradableBlock iUpgradableBlock, int n) {
        switch (this.type) {
            case redstone_inverter: {
                return 15 - n;
            }
        }
        return n;
    }

    @Override
    public int getRangeAmplification(ItemStack itemStack, IUpgradableBlock iUpgradableBlock, int n) {
        switch (this.type) {
            case remote_interface: {
                return n << 1;
            }
        }
        return n;
    }

    @Override
    public boolean onTick(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        int n = StackUtil.getSize(itemStack);
        BlockEntity blockEntity = (BlockEntity)iUpgradableBlock;
        boolean bl = false;
        switch (this.type) {
            case ejector: {
                int n2 = (int)Math.pow(4.0, Math.min(4, n - 1));
                for (EnvItemHandler.AdjacentInventory adjacentInventory : ItemUpgradeModule.getTargetInventories(itemStack, blockEntity)) {
                    StackUtil.ENV.transfer(StackUtil.ENV.wrapInventory(blockEntity, adjacentInventory.getSide()), adjacentInventory, n2);
                }
                break;
            }
            case advanced_ejector: {
                int n3 = (int)Math.pow(4.0, Math.min(4, n - 1));
                for (EnvItemHandler.AdjacentInventory adjacentInventory : ItemUpgradeModule.getTargetInventories(itemStack, blockEntity)) {
                    StackUtil.ENV.transfer(StackUtil.ENV.wrapInventory(blockEntity, adjacentInventory.getSide()), adjacentInventory, n3, (Predicate<ItemStack>)ItemUpgradeModule.stackChecker(itemStack));
                }
                break;
            }
            case pulling: {
                int n4 = (int)Math.pow(4.0, Math.min(4, n - 1));
                for (EnvItemHandler.AdjacentInventory adjacentInventory : ItemUpgradeModule.getTargetInventories(itemStack, blockEntity)) {
                    StackUtil.ENV.transfer(adjacentInventory, StackUtil.ENV.wrapInventory(blockEntity, adjacentInventory.getSide()), n4);
                }
                break;
            }
            case advanced_pulling: {
                int n5 = (int)Math.pow(4.0, Math.min(4, n - 1));
                for (EnvItemHandler.AdjacentInventory adjacentInventory : ItemUpgradeModule.getTargetInventories(itemStack, blockEntity)) {
                    StackUtil.ENV.transfer(adjacentInventory, StackUtil.ENV.wrapInventory(blockEntity, adjacentInventory.getSide()), n5, (Predicate<ItemStack>)ItemUpgradeModule.stackChecker(itemStack));
                }
                break;
            }
            case fluid_ejector: {
                if (!LiquidUtil.isFluidTile(blockEntity, null)) {
                    return false;
                }
                int n6 = (int)(50.0 * Math.pow(4.0, Math.min(4, n - 1)));
                for (LiquidUtil.AdjacentFluidHandler adjacentFluidHandler : ItemUpgradeModule.getTargetFluidHandlers(itemStack, blockEntity)) {
                    LiquidUtil.transfer(blockEntity, adjacentFluidHandler.dir, adjacentFluidHandler.handler, n6);
                }
                break;
            }
            case fluid_pulling: {
                if (!LiquidUtil.isFluidTile(blockEntity, null)) {
                    return false;
                }
                int n7 = (int)(50.0 * Math.pow(4.0, Math.min(4, n - 1)));
                for (LiquidUtil.AdjacentFluidHandler adjacentFluidHandler : ItemUpgradeModule.getTargetFluidHandlers(itemStack, blockEntity)) {
                    LiquidUtil.transfer(adjacentFluidHandler.handler, adjacentFluidHandler.dir.m_122424_(), blockEntity, n7);
                }
                break;
            }
            default: {
                return false;
            }
        }
        return bl;
    }

    private static com.google.common.base.Predicate<ItemStack> stackChecker(final ItemStack itemStack) {
        return new com.google.common.base.Predicate<ItemStack>(){
            private boolean hasInitialised = false;
            private Set<ItemStack> filters;
            private UpgradeSettings damage;
            private UpgradeSettings energy;
            private NbtSettings nbt;

            private void initalise() {
                assert (!this.hasInitialised);
                CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
                this.filters = this.getFilterStacks(compoundTag);
                this.damage = null;
                this.nbt = NbtSettings.getFromNBT(HandHeldAdvancedUpgrade.getTag(compoundTag, "nbt").m_128445_("type"));
                this.energy = new UpgradeSettings(HandHeldAdvancedUpgrade.getTag(compoundTag, "energy"));
                this.hasInitialised = true;
            }

            private Set<ItemStack> getFilterStacks(CompoundTag compoundTag) {
                HashSet<ItemStack> hashSet = new HashSet<ItemStack>();
                ListTag listTag = compoundTag.m_128437_("Items", 10);
                for (int i = 0; i < listTag.size(); ++i) {
                    ItemStack itemStack2;
                    CompoundTag compoundTag2 = listTag.m_128728_(i);
                    byte by = compoundTag2.m_128445_("Slot");
                    if (by < 0 || by >= 9 || StackUtil.isEmpty(itemStack2 = ItemStack.m_41712_((CompoundTag)compoundTag2))) continue;
                    hashSet.add(itemStack2);
                }
                return hashSet;
            }

            private boolean checkDamage(ItemStack itemStack3, ItemStack itemStack2) {
                assert (this.damage.active);
                assert (this.damage.comparison == ComparisonType.DIRECT);
                return itemStack2.m_41773_() == itemStack3.m_41773_();
            }

            private boolean checkNBT(ItemStack itemStack3, ItemStack itemStack2) {
                switch (this.nbt) {
                    case IGNORED: {
                        return true;
                    }
                    case FUZZY: {
                        return StackUtil.checkNbtEquality(itemStack3.m_41783_(), itemStack2.m_41783_());
                    }
                    case EXACT: {
                        return StackUtil.checkNbtEqualityStrict(itemStack3, itemStack2);
                    }
                }
                throw new IllegalStateException("Unexpected NBT state: " + this.nbt);
            }

            private boolean checkEnergy(ItemStack itemStack3, ItemStack itemStack2) {
                assert (this.energy.active);
                assert (this.energy.comparison == ComparisonType.DIRECT);
                return itemStack2.m_41720_() instanceof IElectricItem && Util.isSimilar(ElectricItem.manager.getCharge(itemStack3), ElectricItem.manager.getCharge(itemStack2));
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean apply(ItemStack itemStack3) {
                boolean bl;
                if (!this.hasInitialised) {
                    this.initalise();
                }
                boolean bl2 = false;
                if (!this.energy.comparison.ignoreFilters()) {
                    if (!(itemStack3.m_41720_() instanceof IElectricItem)) return false;
                    if (!this.energy.doComparison((int)ElectricItem.manager.getCharge(itemStack3))) return false;
                    bl = false;
                } else {
                    bl = this.energy.active;
                    if (bl && !(itemStack3.m_41720_() instanceof IElectricItem)) {
                        return false;
                    }
                }
                for (ItemStack itemStack2 : this.filters) {
                    if (itemStack2.m_41720_() != itemStack3.m_41720_() || bl2 && !this.checkDamage(itemStack3, itemStack2) || !this.checkNBT(itemStack3, itemStack2)) continue;
                    if (!bl) return true;
                    if (!this.checkEnergy(itemStack3, itemStack2)) continue;
                    return true;
                }
                if (!this.filters.isEmpty()) return false;
                if (!this.energy.active) return false;
                if (bl) return false;
                return true;
            }
        };
    }

    private static List<? extends EnvItemHandler.AdjacentInventory> getTargetInventories(ItemStack itemStack, BlockEntity blockEntity) {
        Direction direction = ItemUpgradeModule.getDirection(itemStack);
        if (direction == null) {
            return StackUtil.ENV.getAdjacentInventories(blockEntity);
        }
        EnvItemHandler.AdjacentInventory adjacentInventory = StackUtil.ENV.getAdjacentInventory(blockEntity, direction);
        if (adjacentInventory == null) {
            return emptyInvList;
        }
        return Collections.singletonList(adjacentInventory);
    }

    private static List<LiquidUtil.AdjacentFluidHandler> getTargetFluidHandlers(ItemStack itemStack, BlockEntity blockEntity) {
        Direction direction = ItemUpgradeModule.getDirection(itemStack);
        if (direction == null) {
            return LiquidUtil.getAdjacentHandlers(blockEntity);
        }
        LiquidUtil.AdjacentFluidHandler adjacentFluidHandler = LiquidUtil.getAdjacentHandler(blockEntity, direction);
        if (adjacentFluidHandler == null) {
            return emptyFhList;
        }
        return Collections.singletonList(adjacentFluidHandler);
    }

    @Override
    public Collection<ItemStack> onProcessEnd(ItemStack itemStack, IUpgradableBlock iUpgradableBlock, Collection<ItemStack> collection) {
        return collection;
    }

    @Override
    public IHasGui getInventory(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        switch (this.type) {
            case advanced_ejector: 
            case advanced_pulling: {
                return new HandHeldAdvancedUpgrade(player, interactionHand, itemStack);
            }
        }
        return null;
    }

    @Override
    public IHasGui getSubInventory(Player player, InteractionHand interactionHand, ItemStack itemStack, int n) {
        switch (this.type) {
            case advanced_ejector: 
            case advanced_pulling: {
                return HandHeldAdvancedUpgrade.delegate(player, interactionHand, itemStack, n);
            }
        }
        return null;
    }

    @Nullable
    public static Direction getDirection(ItemStack itemStack) {
        byte by = StackUtil.getOrCreateNbtData(itemStack).m_128445_("dir");
        if (by < 1 || by > 6) {
            return null;
        }
        return Util.ALL_DIRS[by - 1];
    }

    public static enum UpgradeType {
        overclocker(false),
        transformer(false),
        energy_storage(false),
        redstone_inverter(false),
        ejector(true),
        advanced_ejector(true),
        pulling(true),
        advanced_pulling(true),
        fluid_ejector(true),
        fluid_pulling(true),
        remote_interface(false);

        public final boolean directional;

        private UpgradeType(boolean bl) {
            this.directional = bl;
        }
    }
}

