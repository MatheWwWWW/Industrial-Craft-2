/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.base.IC2ElectricJetpackBase
 *  ic2.core.item.wearable.base.IC2JetpackBase$HoverMode
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.armor;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.base.IC2JetpackBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedElectricJetpack
extends IC2ElectricJetpackBase {
    public ItemAdvancedElectricJetpack() {
        super("advanced_electric_jetpack", EquipmentSlot.CHEST, null);
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", "advanced_electric_jetpack"), (Item)this);
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.ADVANCED_JETPACK_PROVIDE_ENERGY;
    }

    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_STORAGE;
    }

    public int getTier(ItemStack itemStack) {
        return 2;
    }

    public int getTransferLimit(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.ADVANCED_ELECTRIC_JETPACK_TRANSFER;
    }

    public boolean canDoRocketMode(ItemStack itemStack) {
        return false;
    }

    public boolean canDoAdvHoverMode(ItemStack itemStack) {
        return true;
    }

    public boolean isElectricJetpack(ItemStack itemStack) {
        return true;
    }

    public float getPower(ItemStack itemStack) {
        return 1.0f;
    }

    public float getThruster(ItemStack itemStack, IC2JetpackBase.HoverMode hoverMode) {
        return switch (hoverMode) {
            default -> throw new IncompatibleClassChangeError();
            case IC2JetpackBase.HoverMode.ADV -> 1.35f;
            case IC2JetpackBase.HoverMode.BASIC -> 0.9f;
            case IC2JetpackBase.HoverMode.NONE -> 0.45f;
        };
    }

    public float getDropPercentage(ItemStack itemStack) {
        return 0.05f;
    }

    public int getMaxHeight(ItemStack itemStack, int worldHeight) {
        return (int)((float)worldHeight / 1.15f);
    }

    public int getMaxRocketCharge(ItemStack itemStack) {
        return 0;
    }

    public int getFuelCost(ItemStack itemStack, IC2JetpackBase.HoverMode hoverMode) {
        return hoverMode == IC2JetpackBase.HoverMode.BASIC ? 8 : 14;
    }

    public String getTextureFolder() {
        return "jetpack";
    }

    public String getTextureName() {
        return "advanced_electric_jetpack";
    }

    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_electric_jetpack";
    }
}

