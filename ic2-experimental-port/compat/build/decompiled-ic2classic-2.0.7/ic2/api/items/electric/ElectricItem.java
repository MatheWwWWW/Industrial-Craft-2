/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.api.items.electric;

import ic2.api.items.electric.IElectricItemManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.items.IItemHandler;

public final class ElectricItem {
    static final Map<Item, IElectricItemManager> BACKUPS = new Object2ObjectOpenHashMap();
    static Function<Player, IItemHandler> CURIO_ACCESSOR;
    public static IElectricItemManager MANAGER;
    public static IElectricItemManager DIRECT_MANAGER;

    public static void registerBackupManager(IElectricItemManager manager, ItemLike ... items) {
        for (ItemLike prov : items) {
            BACKUPS.put(prov.m_5456_(), manager);
        }
    }

    public static void setCurioSupport(Function<Player, IItemHandler> curio) {
        CURIO_ACCESSOR = curio;
    }

    public static IElectricItemManager getBackupManager(Item item) {
        return BACKUPS.get(item);
    }

    public static int chargeArmor(Player player, int provided) {
        return ElectricItem.chargeArmor(player, provided, true, EquipmentSlot.values());
    }

    public static int chargeArmor(Player player, int provided, boolean whitelist, EquipmentSlot ... slots) {
        return ElectricItem.chargeArmor(player, provided, whitelist, true, slots);
    }

    public static int chargeArmor(Player player, int provided, boolean whitelist, boolean allowCurio, EquipmentSlot ... slots) {
        if (whitelist) {
            for (EquipmentSlot slot : slots) {
                if (provided <= 0) break;
                if (slot.m_20743_() == EquipmentSlot.Type.HAND) continue;
                provided -= MANAGER.charge(player.m_6844_(slot), provided, Integer.MAX_VALUE, false, false);
            }
            if (provided > 0 && allowCurio && CURIO_ACCESSOR != null) {
                provided -= ElectricItem.chargeCurio(CURIO_ACCESSOR.apply(player), provided);
            }
            return provided;
        }
        EnumSet notAllowed = EnumSet.copyOf(ObjectArrayList.wrap((Object[])slots));
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (provided <= 0) break;
            if (slot.m_20743_() == EquipmentSlot.Type.HAND || notAllowed.contains(slot)) continue;
            provided -= MANAGER.charge(player.m_6844_(slot), provided, Integer.MAX_VALUE, false, false);
        }
        if (provided > 0 && allowCurio && CURIO_ACCESSOR != null) {
            provided -= ElectricItem.chargeCurio(CURIO_ACCESSOR.apply(player), provided);
        }
        return provided;
    }

    public static int chargeCurio(IItemHandler handler, int energyProvided) {
        if (handler == null) {
            return 0;
        }
        int used = 0;
        int m = handler.getSlots();
        for (int i = 0; i < m && used < energyProvided; used += MANAGER.charge(handler.getStackInSlot(i), energyProvided - used, Integer.MAX_VALUE, false, false), ++i) {
        }
        return used;
    }

    public static int applyEnchantmentEffect(ItemStack item, int amount) {
        amount = (int)((double)amount * (1.0 + 0.15 * (double)EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44984_, (ItemStack)item)));
        amount = (int)((double)amount * (1.0 - 0.1 * (double)EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44986_, (ItemStack)item)));
        return amount;
    }
}

