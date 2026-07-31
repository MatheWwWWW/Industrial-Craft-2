/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.wearable.armor.electric;

import ic2.api.items.electric.IDamagelessElectricItem;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ElectricPackArmor
extends IC2AdvancedArmorBase
implements IDamagelessElectricItem {
    String textureFolder;
    String textureName;
    String modelTexture;
    int capacity;
    int tier;
    int transferLimit;

    public ElectricPackArmor(String itemName, EquipmentSlot slot, String textureFolder, String textureName, String modelTexture, @Nullable Rarity rarity, int capacity, int tier, int transferLimit) {
        super(itemName, slot, new PropertiesBuilder().maxDamage(0).rarity(rarity == null ? Rarity.COMMON : rarity));
        this.textureFolder = textureFolder;
        this.textureName = textureName;
        this.modelTexture = modelTexture;
        this.capacity = capacity;
        this.tier = tier;
        this.transferLimit = transferLimit;
    }

    public ElectricPackArmor(String itemName, String textureFolder, String textureName, String modelTexture, Rarity rarity, int capacity, int tier, int transferLimit) {
        this(itemName, EquipmentSlot.CHEST, textureFolder, textureName, modelTexture, rarity, capacity, tier, transferLimit);
    }

    public ElectricPackArmor(String itemName, String textureFolder, String textureName, String modelTexture, int capacity, int tier, int transferLimit) {
        this(itemName, EquipmentSlot.CHEST, textureFolder, textureName, modelTexture, null, capacity, tier, transferLimit);
    }

    public ElectricPackArmor(String itemName, EquipmentSlot slot, String textureFolder, String textureName, String modelTexture, int capacity, int tier, int transferLimit) {
        this(itemName, slot, textureFolder, textureName, modelTexture, null, capacity, tier, transferLimit);
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }

    @Override
    public String getTextureFolder() {
        return this.textureFolder;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

    @Override
    public String getArmorTexture() {
        return this.modelTexture;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return this.capacity;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!this.m_220152_(group)) {
            return;
        }
        IC2ElectricItem.addEmptyAndFullToGroup((ItemLike)this, items);
    }

    public int m_142158_(ItemStack stack) {
        return IC2ElectricItem.getElectricWidth(stack);
    }

    public int m_142159_(ItemStack stack) {
        return IC2ElectricItem.getRGBDurability(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return 0;
    }
}

