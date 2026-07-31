/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.block.Block;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public abstract class EnchantmentCategory
extends Enum<EnchantmentCategory> {
    public static final /* enum */ EnchantmentCategory ARMOR = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44751_) {
            return p_44751_ instanceof ArmorItem;
        }
    };
    public static final /* enum */ EnchantmentCategory ARMOR_FEET = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44806_) {
            return p_44806_ instanceof ArmorItem && ((ArmorItem)p_44806_).m_40402_() == EquipmentSlot.FEET;
        }
    };
    public static final /* enum */ EnchantmentCategory ARMOR_LEGS = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44811_) {
            return p_44811_ instanceof ArmorItem && ((ArmorItem)p_44811_).m_40402_() == EquipmentSlot.LEGS;
        }
    };
    public static final /* enum */ EnchantmentCategory ARMOR_CHEST = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44816_) {
            return p_44816_ instanceof ArmorItem && ((ArmorItem)p_44816_).m_40402_() == EquipmentSlot.CHEST;
        }
    };
    public static final /* enum */ EnchantmentCategory ARMOR_HEAD = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44756_) {
            return p_44756_ instanceof ArmorItem && ((ArmorItem)p_44756_).m_40402_() == EquipmentSlot.HEAD;
        }
    };
    public static final /* enum */ EnchantmentCategory WEAPON = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44761_) {
            return p_44761_ instanceof SwordItem;
        }
    };
    public static final /* enum */ EnchantmentCategory DIGGER = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44766_) {
            return p_44766_ instanceof DiggerItem;
        }
    };
    public static final /* enum */ EnchantmentCategory FISHING_ROD = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44771_) {
            return p_44771_ instanceof FishingRodItem;
        }
    };
    public static final /* enum */ EnchantmentCategory TRIDENT = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44776_) {
            return p_44776_ instanceof TridentItem;
        }
    };
    public static final /* enum */ EnchantmentCategory BREAKABLE = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44781_) {
            return p_44781_.m_41465_();
        }
    };
    public static final /* enum */ EnchantmentCategory BOW = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44786_) {
            return p_44786_ instanceof BowItem;
        }
    };
    public static final /* enum */ EnchantmentCategory WEARABLE = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44791_) {
            return p_44791_ instanceof Wearable || Block.m_49814_(p_44791_) instanceof Wearable;
        }
    };
    public static final /* enum */ EnchantmentCategory CROSSBOW = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44796_) {
            return p_44796_ instanceof CrossbowItem;
        }
    };
    public static final /* enum */ EnchantmentCategory VANISHABLE = new EnchantmentCategory(){

        @Override
        public boolean m_7454_(Item p_44801_) {
            return p_44801_ instanceof Vanishable || Block.m_49814_(p_44801_) instanceof Vanishable || BREAKABLE.m_7454_(p_44801_);
        }
    };
    private static final /* synthetic */ EnchantmentCategory[] $VALUES;

    public static EnchantmentCategory[] values() {
        return (EnchantmentCategory[])$VALUES.clone();
    }

    public static EnchantmentCategory valueOf(String p_44745_) {
        return Enum.valueOf(EnchantmentCategory.class, p_44745_);
    }

    public abstract boolean m_7454_(Item var1);

    private static /* synthetic */ EnchantmentCategory[] m_151293_() {
        return new EnchantmentCategory[]{ARMOR, ARMOR_FEET, ARMOR_LEGS, ARMOR_CHEST, ARMOR_HEAD, WEAPON, DIGGER, FISHING_ROD, TRIDENT, BREAKABLE, BOW, WEARABLE, CROSSBOW, VANISHABLE};
    }

    static {
        $VALUES = EnchantmentCategory.m_151293_();
    }
}

