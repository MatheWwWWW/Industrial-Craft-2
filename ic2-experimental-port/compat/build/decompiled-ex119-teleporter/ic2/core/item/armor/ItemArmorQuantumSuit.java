/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.DyeableLeatherItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IHazmatLike;
import ic2.api.item.IItemHudProvider;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.util.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class ItemArmorQuantumSuit
extends ItemArmorElectric
implements IJetpack,
IHazmatLike,
IItemHudProvider,
DyeableLeatherItem {
    public static final int[] CHARGED_PROTECTION = new int[]{3, 6, 8, 3};
    private static final int defaultColor = -1;

    public ItemArmorQuantumSuit(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Item.Properties properties) {
        super(armorMaterial, equipmentSlot, properties, 1.0E7, 12000.0, 4);
    }

    @Override
    public int getEnergyPerDamage() {
        return 20000;
    }

    public boolean m_41113_(ItemStack itemStack) {
        return this.m_41121_(itemStack) != -1;
    }

    public void m_41123_(ItemStack itemStack) {
        CompoundTag compoundTag = this.getDisplayNbt(itemStack, false);
        if (compoundTag == null || !compoundTag.m_128425_("color", 3)) {
            return;
        }
        compoundTag.m_128473_("color");
        if (compoundTag.m_128456_()) {
            assert (itemStack.m_41783_() != null);
            itemStack.m_41783_().m_128473_("display");
        }
    }

    public int m_41121_(ItemStack itemStack) {
        CompoundTag compoundTag = this.getDisplayNbt(itemStack, false);
        if (compoundTag == null || !compoundTag.m_128425_("color", 3)) {
            return -1;
        }
        return compoundTag.m_128451_("color");
    }

    public void m_41115_(ItemStack itemStack, int n) {
        CompoundTag compoundTag = this.getDisplayNbt(itemStack, true);
        assert (compoundTag != null);
        compoundTag.m_128405_("color", n);
    }

    private CompoundTag getDisplayNbt(ItemStack itemStack, boolean bl) {
        CompoundTag compoundTag;
        CompoundTag compoundTag2 = itemStack.m_41783_();
        if (compoundTag2 == null) {
            if (!bl) {
                return null;
            }
            compoundTag2 = new CompoundTag();
            itemStack.m_41751_(compoundTag2);
        }
        if (!compoundTag2.m_128425_("display", 10)) {
            if (!bl) {
                return null;
            }
            compoundTag = new CompoundTag();
            compoundTag2.m_128365_("display", (Tag)compoundTag);
        } else {
            compoundTag = compoundTag2.m_128469_("display");
        }
        return compoundTag;
    }

    @Override
    public boolean addsProtection(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack itemStack) {
        return ElectricItem.manager.getCharge(itemStack) > 0.0;
    }

    public boolean absorbFall(ItemStack itemStack, LivingEntity livingEntity, float f) {
        int n = Math.max((int)f - 10, 0);
        double d = this.getEnergyPerDamage() * n;
        if (d > ElectricItem.manager.getCharge(itemStack)) {
            return false;
        }
        ElectricItem.manager.discharge(itemStack, d, Integer.MAX_VALUE, true, false, false);
        return true;
    }

    public Rarity m_41460_(ItemStack itemStack) {
        return Rarity.RARE;
    }

    public int m_6473_() {
        return 0;
    }

    @Override
    public boolean drainEnergy(ItemStack itemStack, int n) {
        return ElectricItem.manager.discharge(itemStack, n + 6, Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    @Override
    public float getPower(ItemStack itemStack) {
        return 1.0f;
    }

    @Override
    public float getDropPercentage(ItemStack itemStack) {
        return 0.05f;
    }

    @Override
    public double getChargeLevel(ItemStack itemStack) {
        return ElectricItem.manager.getCharge(itemStack) / this.getMaxCharge(itemStack);
    }

    @Override
    public boolean isJetpackActive(ItemStack itemStack) {
        return true;
    }

    @Override
    public float getHoverMultiplier(ItemStack itemStack, boolean bl) {
        return 0.1f;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack itemStack) {
        return 0.9f;
    }

    @Override
    public boolean doesProvideHUD(ItemStack itemStack) {
        return this.f_40377_ == EquipmentSlot.HEAD && ElectricItem.manager.getCharge(itemStack) > 0.0;
    }

    @Override
    public HudMode getHudMode(ItemStack itemStack) {
        return HudMode.getFromID(StackUtil.getOrCreateNbtData(itemStack).m_128448_("HudMode"));
    }
}

