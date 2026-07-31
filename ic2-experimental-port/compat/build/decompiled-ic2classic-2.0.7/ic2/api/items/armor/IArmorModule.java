/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.api.items.armor;

import ic2.api.network.buffer.INetworkDataBuffer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public interface IArmorModule {
    public ModuleType getType(ItemStack var1);

    public boolean canInstallInArmor(ItemStack var1, ItemStack var2, EquipmentSlot var3);

    public void onInstall(ItemStack var1, ItemStack var2, IArmorModuleHolder var3);

    public void onUninstall(ItemStack var1, ItemStack var2, IArmorModuleHolder var3);

    public void transferToArmor(ItemStack var1, ItemStack var2, ItemStack var3);

    public void onTick(ItemStack var1, ItemStack var2, Level var3, Player var4);

    public void onEquipped(ItemStack var1, ItemStack var2, Player var3);

    public void onUnequipped(ItemStack var1, ItemStack var2, Player var3);

    public void provideCapabilities(ItemStack var1, ItemStack var2);

    public boolean handlePacket(Player var1, ItemStack var2, ItemStack var3, String var4, INetworkDataBuffer var5, Dist var6);

    default public void handleToolTip(ItemStack stack, Consumer<MutableComponent> results) {
        results.accept(Component.m_237110_((String)"misc.ic2.module.slot_type", (Object[])new Object[]{this.getType(stack).getName()}));
        MutableComponent adding = Component.m_237113_((String)"[");
        MutableComponent next = null;
        boolean first = true;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !this.canInstallInArmor(stack, stack, slot)) continue;
            if (next != null) {
                if (!first) {
                    adding.m_130946_(", ");
                }
                adding.m_7220_(next);
                first = false;
            }
            next = Component.m_237115_((String)("misc.ic2.module.valid_armor." + slot.m_20751_()));
        }
        if (next != null) {
            if (!first) {
                adding.m_130946_(", ");
            }
            results.accept(Component.m_237110_((String)"misc.ic2.module.valid_armor", (Object[])new Object[]{adding.m_7220_(next).m_130946_("]")}));
        }
    }

    public static final class ModuleType
    implements Comparable<ModuleType> {
        private static final Map<String, ModuleType> TYPES = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());
        private static final AtomicInteger IDS = new AtomicInteger(1);
        public static final ModuleType BATTERY = ModuleType.getOrCreateType("battery", "misc.ic2.module.battery");
        public static final ModuleType HUD = ModuleType.getOrCreateType("hud", "misc.ic2.module.hud");
        public static final ModuleType ANY = ModuleType.getOrCreateType("any", "misc.ic2.module.any");
        public static final ModuleType GENERIC = ModuleType.getOrCreateType("generic", "misc.ic2.module.generic");
        public static final ModuleType CHARGER = ModuleType.getOrCreateType("charge", "misc.ic2.module.charger");
        public static final ModuleType STORAGE = ModuleType.getOrCreateType("storage", "misc.ic2.module.storage");
        public static final ModuleType BACK_SLOT = ModuleType.getOrCreateType("back_slot", "misc.ic2.module.back_slot");
        public static final ModuleType MOVEMENT = ModuleType.getOrCreateType("movement", "misc.ic2.module.movement");
        final int index = IDS.getAndIncrement();
        final String id;
        final String name;

        ModuleType(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Component getName() {
            return Component.m_237115_((String)this.name);
        }

        public String getId() {
            return this.id;
        }

        public static ModuleType getType(String id) {
            return TYPES.get(id);
        }

        public static synchronized ModuleType getOrCreateType(String id, String name) {
            ModuleType type = TYPES.get(id);
            if (type == null) {
                type = new ModuleType(id, name);
                TYPES.put(id, type);
            }
            return type;
        }

        public static List<ModuleType> getAllTypes() {
            return new ObjectArrayList(TYPES.values());
        }

        @Override
        public int compareTo(ModuleType o) {
            return Integer.compareUnsigned(this.index, o.index);
        }
    }

    public static interface IArmorCapability {
        public <T> void registerArmorCapability(Capability<T> var1, ICapabilityProvider var2);

        public <T> ICapabilityProvider removeArmorCapability(Capability<T> var1);

        public <T> LazyOptional<T> getArmorCapability(Capability<T> var1);
    }

    public static class ArmorMod {
        public static final ArmorMod ENERGY_STORAGE = new ArmorMod("storage_add", "storage_mul");
        public static final ArmorMod ENERGY_TIER = new ArmorMod("tier");
        public static final ArmorMod ENERGY_TRANSFER = new ArmorMod("transfer_add", "transfer_mul");
        public static final ArmorMod ENERGY_PROVIDER = new ArmorMod("provider");
        public static final ArmorMod NO_LAVA_DAMAGE = new ArmorMod("lava");
        public static final ArmorMod ENERGY_SHIELD = new ArmorMod("shield");
        public static final ArmorMod ENERGY_SHIELD_ALWAYS = new ArmorMod("shield_always");
        public static final ArmorMod EU_READER = new ArmorMod("eu_reader");
        public static final ArmorMod THERMOMETER = new ArmorMod("thermo");
        public static final ArmorMod HAZMAT_PROTECTION = new ArmorMod("hazmat");
        public static final ArmorMod CROP_SCANNER = new ArmorMod("crop");
        public static final ArmorMod HUD_ENERGY = new ArmorMod("armor_hud");
        public static final ArmorMod GOLDEN = new ArmorMod("golden");
        String add_mod;
        String mul_mod;

        public ArmorMod(String add_mod) {
            this(add_mod, null);
        }

        public ArmorMod(String add_mod, String mul_mod) {
            this.add_mod = add_mod;
            this.mul_mod = mul_mod;
        }

        public String getAdd() {
            return this.add_mod;
        }

        public String getMul() {
            return this.mul_mod;
        }
    }

    public static interface IArmorModuleHolder {
        public static final String DISABLE_MODULES = "no_modules";

        public List<ItemStack> getInstalledModules(ItemStack var1);

        public Object2IntMap<ModuleType> getModuleLimits(ItemStack var1);

        public void addAddModifier(ItemStack var1, ArmorMod var2, int var3);

        public void addMulModifier(ItemStack var1, ArmorMod var2, float var3);

        public void removeAddModifier(ItemStack var1, ArmorMod var2, int var3);

        public void removeMulModifier(ItemStack var1, ArmorMod var2, float var3);

        public void onEquipmentStateChanged(ItemStack var1, boolean var2, Player var3);

        public static boolean isValidModularArmor(ItemStack stack) {
            if (!(stack.m_41720_() instanceof IArmorModuleHolder)) {
                return false;
            }
            CompoundTag tag = stack.m_41783_();
            return tag == null || !tag.m_128471_(DISABLE_MODULES);
        }
    }
}

