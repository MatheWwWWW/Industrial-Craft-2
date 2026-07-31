/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.armor.IArmorModule$ArmorMod
 *  ic2.api.items.armor.IArmorModule$IArmorModuleHolder
 *  ic2.core.item.wearable.base.IC2JetpackBase$HoverMode
 *  ic2.core.item.wearable.jetpacks.NuclearJetpack
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.armor;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import trinsdar.gravisuit.util.GravisuitConfig;
import trinsdar.gravisuit.util.Registry;

public class ItemAdvancedNuclearJetpack
extends NuclearJetpack {
    public ItemAdvancedNuclearJetpack() {
        super("advanced_electric_jetpack");
        Registry.REGISTRY.put(new ResourceLocation("gravisuit", "advanced_nuclear_jetpack"), (Item)this);
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.ADVANCED_N_JETPACK_PROVIDE_ENERGY;
    }

    public int getCapacity(ItemStack itemStack) {
        return GravisuitConfig.POWER_VALUES.ADVANCED_NUCLEAR_JETPACK_STORAGE;
    }

    public int getTier(ItemStack itemStack) {
        return 2;
    }

    public int getTransferLimit(ItemStack itemStack) {
        return 0;
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

    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onInstall(stack, armor, holder);
        if (this.canProvideEnergy(stack)) {
            holder.addAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        super.onUninstall(stack, armor, holder);
        if (this.canProvideEnergy(stack)) {
            holder.removeAddModifier(armor, IArmorModule.ArmorMod.ENERGY_PROVIDER, 1001);
        }
    }

    public String getTextureFolder() {
        return "jetpack";
    }

    public String getTextureName() {
        return "advanced_nuclear_jetpack";
    }

    public String getArmorTexture() {
        return "gravisuit:textures/models/advanced_nuclear_jetpack";
    }
}

