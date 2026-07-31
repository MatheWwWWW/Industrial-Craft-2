/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.math.DoubleMath
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.EnchantedBookItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.EnchantmentInstance
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.utils.helpers;

import com.google.common.math.DoubleMath;
import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.core.IC2;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantUtil {
    public static int getEnchantmentCount(ItemStack stack) {
        return (stack.m_41720_() == Items.f_42690_ ? EnchantedBookItem.m_41163_((ItemStack)stack) : stack.m_41785_()).size() + stack.m_41610_();
    }

    public static List<EnchantmentInstance> createElectricEnchantments(int level, ItemStack stack, boolean treasure, RandomSource rand) {
        int i;
        if (stack.m_41619_() || level < 0) {
            return ObjectLists.emptyList();
        }
        int n = i = stack.m_41720_() == Items.f_42690_ ? 1 : stack.getEnchantmentValue();
        if (i <= 0) {
            return ObjectLists.emptyList();
        }
        level = level + 1 + rand.m_188503_(i / 4 + 1) + rand.m_188503_(i / 4 + 1);
        float f = (rand.m_188501_() + rand.m_188501_() - 1.0f) * 0.15f;
        level = Mth.m_14045_((int)Math.round((float)level + (float)level * f), (int)1, (int)Integer.MAX_VALUE);
        return EnchantUtil.getElectricEnchantmentData(level, stack, treasure);
    }

    public static List<EnchantmentInstance> getElectricEnchantmentData(int level, ItemStack stack, boolean allowTreasure) {
        ObjectList result = CollectionUtils.createList();
        boolean book = stack.m_41720_() == Items.f_42517_ || stack.m_41720_() == Items.f_42690_;
        EnchantmentCategory type = stack.m_41720_() instanceof IElectricEnchantable ? ((IElectricEnchantable)stack.m_41720_()).getEnchantmentType(stack) : null;
        block0: for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
            if (enchantment.m_6591_() && !allowTreasure || !enchantment.m_6592_() || enchantment.m_6589_() || !EnchantUtil.isValidElectricEnchantment(stack, enchantment, type) && (!book || !enchantment.isAllowedOnBooks())) continue;
            for (int i = enchantment.m_6586_(); i > enchantment.m_44702_() - 1; --i) {
                if (level < enchantment.m_6183_(i) || level > enchantment.m_6175_(i)) continue;
                result.add((EnchantmentInstance)new EnchantmentInstance(enchantment, i));
                continue block0;
            }
        }
        return result;
    }

    public static boolean isValidElectricEnchantment(ItemStack stack, Enchantment enchantment) {
        return EnchantUtil.isValidElectricEnchantment(stack, enchantment, stack.m_41720_() instanceof IElectricEnchantable ? ((IElectricEnchantable)stack.m_41720_()).getEnchantmentType(stack) : null);
    }

    public static boolean isValidElectricEnchantment(ItemStack stack, Enchantment enchantment, EnchantmentCategory type) {
        if (type == null || !(stack.m_41720_() instanceof IElectricEnchantable)) {
            return enchantment.canApplyAtEnchantingTable(stack);
        }
        IElectricEnchantable tool = (IElectricEnchantable)stack.m_41720_();
        switch (tool.getEnchantmentCompatibility(stack, enchantment)) {
            case FAIL: {
                return false;
            }
            case SUCCESS: {
                return true;
            }
            case PASS: {
                return type == EnchantmentCategory.BREAKABLE && (stack.m_41763_() || stack.m_41720_() instanceof IDamagelessElectricItem) || (EnchantUtil.ignoreCanApplyAtEnchantingTable(enchantment) || enchantment.canApplyAtEnchantingTable(stack)) && (enchantment.f_44672_ == type || (type == EnchantmentCategory.ARMOR_FEET || type == EnchantmentCategory.ARMOR_CHEST || type == EnchantmentCategory.ARMOR_HEAD || type == EnchantmentCategory.ARMOR_LEGS) && enchantment.f_44672_ == EnchantmentCategory.ARMOR);
            }
        }
        return false;
    }

    private static boolean ignoreCanApplyAtEnchantingTable(Enchantment enchant) {
        ResourceLocation location = ForgeRegistries.ENCHANTMENTS.getKey((Object)enchant);
        return location.m_135827_().equals("minecraft") || IC2.CONFIG.ignoreEnchantabilityCheck.contains(location.m_135827_()) || IC2.CONFIG.ignoreEnchantabilityCheck.contains(location.toString());
    }

    public static void mergeEnchantments(Object2IntMap<Enchantment> enchantments, List<EnchantmentInstance> data, ItemStack stack) {
        EnchantmentCategory type = stack.m_41720_() instanceof IElectricEnchantable ? ((IElectricEnchantable)stack.m_41720_()).getEnchantmentType(stack) : null;
        boolean book = stack.m_41720_() == Items.f_42517_ || stack.m_41720_() == Items.f_42690_;
        int m = data.size();
        for (int i = 0; i < m; ++i) {
            EnchantmentInstance entry = data.get(i);
            if (!EnchantUtil.isValidElectricEnchantment(stack, entry.f_44947_, type) && (!book || !entry.f_44947_.isAllowedOnBooks())) continue;
            int oldLvl = enchantments.getInt((Object)entry.f_44947_);
            if (oldLvl == 0) {
                if (!book && !EnchantUtil.canInsertEnchantment(entry.f_44947_, (Set<Enchantment>)enchantments.keySet())) continue;
                enchantments.put((Object)entry.f_44947_, entry.f_44948_);
                continue;
            }
            if (oldLvl < entry.f_44948_) {
                enchantments.put((Object)entry.f_44947_, entry.f_44948_);
                continue;
            }
            if (oldLvl != entry.f_44948_ || entry.f_44947_.m_6586_() <= oldLvl) continue;
            enchantments.put((Object)entry.f_44947_, entry.f_44948_ + 1);
        }
    }

    public static boolean canInsertEnchantment(EnchantmentInstance ench, List<EnchantmentInstance> list) {
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            Enchantment entry = list.get((int)i).f_44947_;
            if (ench.f_44947_ == entry || ench.f_44947_.m_44695_(entry)) continue;
            return false;
        }
        return true;
    }

    public static boolean canInsertEnchantment(Enchantment enchantment, Set<Enchantment> set) {
        for (Enchantment entry : set) {
            if (entry == enchantment || entry.m_44695_(enchantment)) continue;
            return false;
        }
        return true;
    }

    public static int drainExperience(Player player, int points) {
        if (player.m_7500_()) {
            player.m_7408_(ItemStack.f_41583_, 0);
            return points;
        }
        int totalXP = EnchantUtil.getXP(player);
        if (totalXP != player.f_36079_) {
            player.f_36079_ = totalXP;
        }
        int change = Math.min(EnchantUtil.getXP(player), points);
        player.f_36079_ -= change;
        player.f_36078_ = EnchantUtil.getLvlForXP(player.f_36079_);
        player.f_36080_ = (float)(player.f_36079_ - EnchantUtil.getXPForLvl(player.f_36078_)) / (float)player.m_36323_();
        player.m_7408_(ItemStack.f_41583_, 0);
        return change;
    }

    public static int getXP(Player player) {
        return EnchantUtil.getXPForLvl(player.f_36078_) + DoubleMath.roundToInt((double)(player.f_36080_ * (float)player.m_36323_()), (RoundingMode)RoundingMode.HALF_UP);
    }

    public static int getXPForLvl(int level) {
        if (level < 0) {
            return Integer.MAX_VALUE;
        }
        if (level <= 15) {
            return level * level + 6 * level;
        }
        if (level <= 30) {
            return (int)((double)(level * level) * 2.5 - 40.5 * (double)level + 360.0);
        }
        return (int)((double)(level * level) * 4.5 - 162.5 * (double)level + 2220.0);
    }

    public static int getLvlForXP(int totalXP) {
        int result = 0;
        while (EnchantUtil.getXPForLvl(result) <= totalXP) {
            ++result;
        }
        return --result;
    }
}

