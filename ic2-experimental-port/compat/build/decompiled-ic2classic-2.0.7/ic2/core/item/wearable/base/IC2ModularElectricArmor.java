/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.INBTSerializable
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.item.wearable.base;

import ic2.api.core.IC2Classic;
import ic2.api.items.armor.IArmorHud;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.armor.IEnergyShieldArmor;
import ic2.api.items.armor.IMetalArmor;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.readers.ICropReader;
import ic2.api.items.readers.IEUReader;
import ic2.api.items.readers.IThermometer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.item.INetworkItemBufferEvent;
import ic2.api.recipes.misc.RecipeMods;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.base.IC2ElectricArmorBase;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IHasArmorInventory;
import ic2.core.item.wearable.modules.IModuleUseAction;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public abstract class IC2ModularElectricArmor
extends IC2ElectricArmorBase
implements IArmorModule.IArmorModuleHolder,
IHasHeldSlotInventory,
IEUReader,
IThermometer,
ICropReader,
IC2JetpackBase.IIndirectJetpack,
IEnergyShieldArmor,
IArmorHud,
INetworkItemBufferEvent<INetworkDataBuffer>,
IMetalArmor {
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
    public static final String UPGRADE_LIST = "armor_upgrades";
    public static final String UPGRADE_TYPE = "upgrade_type";
    protected Object2IntMap<IArmorModule.ModuleType> types = new Object2IntOpenHashMap();

    public IC2ModularElectricArmor(String itemName, EquipmentSlot slot, PropertiesBuilder props) {
        super(itemName, slot, props);
        if (slot == EquipmentSlot.HEAD) {
            this.types.put((Object)IArmorModule.ModuleType.HUD, 9);
        }
    }

    public abstract ItemStack createDefaultArmor();

    protected void addSlotType(IArmorModule.ModuleType type, int slots) {
        this.types.put((Object)type, slots);
    }

    @Override
    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> subItems) {
        if (!this.m_220152_(group)) {
            return;
        }
        subItems.add((Object)new ItemStack((ItemLike)this));
        IC2ElectricItem.addEmptyAndFullToGroup(this.createDefaultArmor(), subItems);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        for (ItemStack entry : this.getInstalledModules(stack)) {
            if (!(entry.m_41720_() instanceof IArmorToolTip)) continue;
            ((IArmorToolTip)entry.m_41720_()).addToolTip(stack, entry, player, type, helper);
        }
    }

    @Override
    public Object2IntMap<IArmorModule.ModuleType> getModuleLimits(ItemStack stack) {
        return Object2IntMaps.unmodifiable(this.types);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        for (ItemStack module : this.getInstalledModules(stack)) {
            if (!(module.m_41720_() instanceof IArmorModule)) continue;
            ((IArmorModule)module.m_41720_()).onTick(module, stack, world, player);
        }
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_STORAGE, this.capacity);
    }

    @Override
    public int getTier(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_TIER, this.tier);
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_TRANSFER, this.transferLimit);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_PROVIDER, 0) > 0;
    }

    @Override
    public boolean isCropReader(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.CROP_SCANNER, 0) > 0;
    }

    @Override
    public boolean isThermometer(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.THERMOMETER, 0) > 0;
    }

    @Override
    public boolean isEUReader(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.EU_READER, 0) > 0;
    }

    @Override
    public boolean isHudEnabled(ItemStack stack) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.HUD_ENERGY, 0) > 0;
    }

    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.GOLDEN, 0) > 0;
    }

    public boolean isFullyAbsorbingFallDamage(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBlockingEverything(ItemStack stack, DamageSource source, LivingEntity living) {
        if ("sonic_boom".equals(source.m_19385_())) {
            for (EquipmentSlot slot : ARMOR_SLOTS) {
                if (living.m_6844_(slot).m_41720_() instanceof IC2ModularElectricArmor) continue;
                return false;
            }
            return true;
        }
        return !source.m_19387_() && !source.m_19379_() && !source.m_19378_();
    }

    @Override
    public boolean isMetalArmor(ItemStack stack, Player player, EquipmentSlot targetSlot) {
        return targetSlot == this.f_40377_;
    }

    @Override
    public boolean addsShieldEffect(EquipmentSlot type, LivingEntity entity, ItemStack stack) {
        return type == this.f_40377_ && IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_SHIELD, 0) + IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_SHIELD_ALWAYS, 0) > 0;
    }

    @Override
    public boolean isEffectAlwaysOn(EquipmentSlot type, LivingEntity entity, ItemStack stack) {
        return type == this.f_40377_ && IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.ENERGY_SHIELD_ALWAYS, 0) > 0;
    }

    @Override
    public void onDataBufferReceived(ItemStack stack, Player player, String id, INetworkDataBuffer buffer, Dist targetSide) {
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(UPGRADE_LIST, 10), CompoundTag.class)) {
            ItemStack item = ItemStack.m_41712_((CompoundTag)nbt);
            if (!(item.m_41720_() instanceof IArmorModule) || !((IArmorModule)item.m_41720_()).handlePacket(player, item, stack, id, buffer, targetSide)) continue;
            return;
        }
    }

    @Override
    public IC2JetpackBase getJetpack(ItemStack stack) {
        if (this.f_40377_ != EquipmentSlot.CHEST) {
            return null;
        }
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(UPGRADE_LIST, 10), CompoundTag.class)) {
            ItemStack jetpack = ItemStack.m_41712_((CompoundTag)nbt);
            if (!(jetpack.m_41720_() instanceof IC2JetpackBase)) continue;
            return (IC2JetpackBase)jetpack.m_41720_();
        }
        return null;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn) && this.hasInventory(stack)) {
            IC2.PLATFORM.launchGui(playerIn, handIn, Direction.NORTH, this.getInventory(playerIn, handIn, stack));
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        IC2ModularElectricArmor armorItem = (IC2ModularElectricArmor)stack.m_41720_();
        for (ItemStack module : this.getInstalledModules(stack)) {
            if (!(module.m_41720_() instanceof IModuleUseAction) || !((IModuleUseAction)module.m_41720_()).use(worldIn, playerIn, module, stack, armorItem)) continue;
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        ItemStack result = this.findInventory(stack);
        return result.m_41619_() ? null : ((IHasArmorInventory)result.m_41720_()).getInventory(player, hand, stack);
    }

    @Override
    public boolean hasInventory(ItemStack stack) {
        ItemStack result = this.findInventory(stack);
        return !result.m_41619_();
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        ItemStack result = this.findInventory(stack);
        return result.m_41619_() ? null : ((IHasArmorInventory)result.m_41720_()).getInventory(player, stack, slot);
    }

    protected ItemStack findInventory(ItemStack stack) {
        for (ItemStack entry : this.getInstalledModules(stack)) {
            if (!(entry.m_41720_() instanceof IHasArmorInventory) || !((IHasArmorInventory)entry.m_41720_()).hasArmorInventory(stack, entry)) continue;
            return entry;
        }
        return ItemStack.f_41583_;
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new ArmorCapability(this, stack);
    }

    @Override
    public List<ItemStack> getInstalledModules(ItemStack stack) {
        ObjectList items = CollectionUtils.createList();
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(UPGRADE_LIST, 10), CompoundTag.class)) {
            ItemStack item = ItemStack.m_41712_((CompoundTag)nbt);
            if (item.m_41619_()) continue;
            items.add((ItemStack)item);
        }
        return items;
    }

    @Override
    public void onEquipmentStateChanged(ItemStack stack, boolean equip, Player player) {
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(UPGRADE_LIST, 10), CompoundTag.class)) {
            ItemStack item = ItemStack.m_41712_((CompoundTag)nbt);
            if (!(item.m_41720_() instanceof IArmorModule)) continue;
            if (equip) {
                ((IArmorModule)item.m_41720_()).onEquipped(item, stack, player);
                continue;
            }
            ((IArmorModule)item.m_41720_()).onUnequipped(item, stack, player);
        }
    }

    public static Object2ObjectMap<IArmorModule.ModuleType, List<ItemStack>> getModuleTypes(ItemStack stack) {
        Object2ObjectSortedMap maps = CollectionUtils.createLinkedMap();
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(UPGRADE_LIST, 10), CompoundTag.class)) {
            IArmorModule.ModuleType type;
            ItemStack item = ItemStack.m_41712_((CompoundTag)nbt);
            if (item.m_41619_() || (type = IArmorModule.ModuleType.getType(nbt.m_128461_(UPGRADE_TYPE))) == null) continue;
            ObjectList list = (ObjectList)maps.get((Object)type);
            if (list == null) {
                list = CollectionUtils.createList();
                maps.put((Object)type, list);
            }
            list.add((ItemStack)item);
        }
        return maps;
    }

    public static void setAndInstallTypes(ItemStack stack, Map<IArmorModule.ModuleType, List<ItemStack>> types) {
        IArmorModule.IArmorModuleHolder holder = (IArmorModule.IArmorModuleHolder)stack.m_41720_();
        ListTag list = new ListTag();
        for (Map.Entry<IArmorModule.ModuleType, List<ItemStack>> entry : types.entrySet()) {
            String s = entry.getKey().getId();
            List<ItemStack> itemList = entry.getValue();
            int m = itemList.size();
            for (int i = 0; i < m; ++i) {
                ItemStack module = itemList.get(i);
                if (module.m_41619_()) continue;
                if (module.m_41720_() instanceof IArmorModule) {
                    ((IArmorModule)module.m_41720_()).onInstall(module, stack, holder);
                }
                CompoundTag nbt = module.m_41739_(new CompoundTag());
                nbt.m_128359_(UPGRADE_TYPE, s);
                list.add((Object)nbt);
            }
        }
        stack.m_41784_().m_128365_(UPGRADE_LIST, (Tag)list);
        ((IHasHeldGui)stack.m_41720_()).setGuiID(stack, -1);
        int stored = ElectricItem.MANAGER.discharge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, false);
        if (stored > 0) {
            ElectricItem.MANAGER.charge(stack, stored, Integer.MAX_VALUE, true, false);
        }
    }

    public static void setModuleTypes(ItemStack stack, Map<IArmorModule.ModuleType, List<ItemStack>> types) {
        ListTag list = new ListTag();
        for (Map.Entry<IArmorModule.ModuleType, List<ItemStack>> entry : types.entrySet()) {
            String s = entry.getKey().getId();
            List<ItemStack> itemList = entry.getValue();
            int m = itemList.size();
            for (int i = 0; i < m; ++i) {
                ItemStack module = itemList.get(i);
                if (module.m_41619_()) continue;
                CompoundTag nbt = module.m_41739_(new CompoundTag());
                nbt.m_128359_(UPGRADE_TYPE, s);
                list.add((Object)nbt);
            }
        }
        ((IHasHeldGui)stack.m_41720_()).setGuiID(stack, -1);
        stack.m_41784_().m_128365_(UPGRADE_LIST, (Tag)list);
        int stored = ElectricItem.MANAGER.discharge(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, false);
        if (stored > 0) {
            ElectricItem.MANAGER.charge(stack, stored, Integer.MAX_VALUE, true, false);
        }
    }

    @Override
    public void addAddModifier(ItemStack stack, IArmorModule.ArmorMod type, int amount) {
        if (type.getAdd() == null) {
            return;
        }
        CompoundTag nbt = stack.m_41698_("mods");
        nbt.m_128405_(type.getAdd(), nbt.m_128451_(type.getAdd()) + amount);
    }

    @Override
    public void addMulModifier(ItemStack stack, IArmorModule.ArmorMod type, float amount) {
        if (type.getMul() == null) {
            return;
        }
        CompoundTag nbt = stack.m_41698_("mods");
        nbt.m_128350_(type.getMul(), nbt.m_128457_(type.getMul()) + amount);
    }

    @Override
    public void removeAddModifier(ItemStack stack, IArmorModule.ArmorMod type, int amount) {
        if (type.getAdd() == null) {
            return;
        }
        CompoundTag nbt = stack.m_41737_("mods");
        if (nbt == null) {
            return;
        }
        int add = nbt.m_128451_(type.getAdd()) - amount;
        if (add == 0) {
            nbt.m_128473_(type.getAdd());
            if (nbt.m_128456_()) {
                stack.m_41749_("mods");
            }
            return;
        }
        nbt.m_128405_(type.getAdd(), add);
    }

    @Override
    public void removeMulModifier(ItemStack stack, IArmorModule.ArmorMod type, float amount) {
        if (type.getMul() == null) {
            return;
        }
        CompoundTag nbt = stack.m_41737_("mods");
        if (nbt == null) {
            return;
        }
        float mul = nbt.m_128457_(type.getMul()) - amount;
        if (mul == 0.0f) {
            nbt.m_128473_(type.getMul());
            if (nbt.m_128456_()) {
                stack.m_41749_("mods");
            }
            return;
        }
        nbt.m_128350_(type.getMul(), mul);
    }

    public static int getValue(ItemStack stack, IArmorModule.ArmorMod type, int base) {
        CompoundTag nbt = stack.m_41737_("mods");
        if (nbt == null) {
            return base;
        }
        return RecipeMods.apply(0, base, type.getAdd() == null ? 0 : nbt.m_128451_(type.getAdd()), type.getMul() == null ? 1.0 : (double)(1.0f + nbt.m_128457_(type.getMul())));
    }

    public static class ArmorCapability
    implements IArmorModule.IArmorCapability,
    ICapabilityProvider,
    INBTSerializable<CompoundTag> {
        LazyOptional<IArmorModule.IArmorCapability> own = LazyOptional.of(() -> this);
        IC2ModularElectricArmor module;
        ItemStack stack;
        Object2ObjectMap<Capability<?>, ICapabilityProvider> caps = CollectionUtils.createLinkedMap();

        public ArmorCapability(IC2ModularElectricArmor module, ItemStack stack) {
            this.module = module;
            this.stack = stack;
        }

        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            for (Map.Entry entry : Object2ObjectMaps.fastIterable(this.caps)) {
                if (!(entry.getValue() instanceof INBTSerializable)) continue;
                nbt.m_128365_(((Capability)entry.getKey()).getName(), ((INBTSerializable)entry.getValue()).serializeNBT());
            }
            return nbt;
        }

        public void deserializeNBT(CompoundTag nbt) {
            for (ItemStack stack : this.module.getInstalledModules(this.stack)) {
                if (!(stack.m_41720_() instanceof IArmorModule)) continue;
                ((IArmorModule)stack.m_41720_()).provideCapabilities(stack, this.stack);
            }
            for (Map.Entry entry : Object2ObjectMaps.fastIterable(this.caps)) {
                if (!(entry.getValue() instanceof INBTSerializable)) continue;
                ((INBTSerializable)entry.getValue()).deserializeNBT(nbt.m_128423_(((Capability)entry.getKey()).getName()));
            }
        }

        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            if (cap == IC2Classic.ARMOR_CAPABILITY) {
                return this.own.cast();
            }
            ICapabilityProvider lazy = (ICapabilityProvider)this.caps.get(cap);
            return lazy == null ? LazyOptional.empty() : lazy.getCapability(cap, side);
        }

        @Override
        public <T> void registerArmorCapability(Capability<T> cap, ICapabilityProvider value) {
            this.caps.put(cap, (Object)value);
        }

        @Override
        public <T> ICapabilityProvider removeArmorCapability(Capability<T> cap) {
            return (ICapabilityProvider)this.caps.remove(cap);
        }

        @Override
        public <T> LazyOptional<T> getArmorCapability(Capability<T> cap) {
            ICapabilityProvider lazy = (ICapabilityProvider)this.caps.get(cap);
            return lazy == null ? LazyOptional.empty() : lazy.getCapability(cap);
        }
    }
}

