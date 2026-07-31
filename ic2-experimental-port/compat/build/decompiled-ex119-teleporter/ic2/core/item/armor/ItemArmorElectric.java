/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.ElectricItemTooltipHandler;
import ic2.core.item.armor.ItemArmorIC2;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class ItemArmorElectric
extends ItemArmorIC2
implements IElectricItem {
    public static final UUID[] MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    protected final double maxCharge;
    protected final double transferLimit;
    protected final int tier;

    public ItemArmorElectric(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Item.Properties properties, double d, double d2, int n) {
        super(armorMaterial, equipmentSlot, properties);
        this.maxCharge = d;
        this.transferLimit = d2;
        this.tier = n;
    }

    public abstract int getEnergyPerDamage();

    public static void damageArmor(Player player, DamageSource damageSource, float f) {
        if (f <= 0.0f || damageSource.m_19379_()) {
            return;
        }
        float f2 = f / 4.0f;
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            ItemStack itemStack = player.m_6844_(equipmentSlot);
            Item item = itemStack.m_41720_();
            if (!(item instanceof ItemArmorElectric)) continue;
            ItemArmorElectric itemArmorElectric = (ItemArmorElectric)item;
            itemArmorElectric.damageArmor((LivingEntity)player, itemStack, damageSource, f2, equipmentSlot);
        }
    }

    public boolean m_8120_(ItemStack itemStack) {
        return false;
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        ElectricItemManager.addChargeVariants((Item)this, nonNullList);
    }

    public void m_7373_(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        ElectricItemTooltipHandler.addTooltip(itemStack, list);
    }

    public void damageArmor(LivingEntity livingEntity, ItemStack itemStack, DamageSource damageSource, double d, EquipmentSlot equipmentSlot) {
        ElectricItem.manager.discharge(itemStack, d * (double)this.getEnergyPerDamage(), Integer.MAX_VALUE, true, false, false);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    public boolean m_142522_(ItemStack itemStack) {
        return ElectricItem.manager.getChargeLevel(itemStack) < 1.0;
    }

    public int m_142158_(ItemStack itemStack) {
        return (int)Math.round(ElectricItem.manager.getChargeLevel(itemStack) * 13.0);
    }

    public int m_142159_(ItemStack itemStack) {
        return Mth.m_14169_((float)((float)(ElectricItem.manager.getChargeLevel(itemStack) / 3.0)), (float)1.0f, (float)1.0f);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack itemStack, EquipmentSlot equipmentSlot) {
        int n;
        boolean bl;
        if (equipmentSlot != this.f_40377_) {
            return this.m_7167_(equipmentSlot);
        }
        boolean bl2 = bl = ElectricItem.manager.getCharge(itemStack) >= (double)((ItemArmorElectric)itemStack.m_41720_()).getEnergyPerDamage();
        if (!bl) {
            return this.m_7167_(equipmentSlot);
        }
        Item item = itemStack.m_41720_();
        if (item instanceof ItemArmorNanoSuit) {
            n = ItemArmorNanoSuit.CHARGED_PROTECTION[equipmentSlot.m_20749_()];
        } else if (item instanceof ItemArmorQuantumSuit) {
            n = ItemArmorQuantumSuit.CHARGED_PROTECTION[equipmentSlot.m_20749_()];
        } else {
            return this.m_7167_(equipmentSlot);
        }
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        Attribute attribute = Attributes.f_22284_;
        UUID uUID = MODIFIERS[equipmentSlot.m_20749_()];
        Collection collection = this.m_7167_(equipmentSlot).get((Object)attribute);
        if (collection != null) {
            for (AttributeModifier attributeModifier : collection) {
                if (attributeModifier.m_22209_().equals(uUID)) continue;
                builder.put((Object)attribute, (Object)attributeModifier);
            }
        }
        builder.put((Object)attribute, (Object)new AttributeModifier(uUID, "Armor modifier", (double)n, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        return this.getAttributeModifiers(itemStack, equipmentSlot);
    }
}

