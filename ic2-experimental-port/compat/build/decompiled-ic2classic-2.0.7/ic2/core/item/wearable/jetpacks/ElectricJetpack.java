/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.jetpacks;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.base.IC2JetpackBase;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ElectricJetpack
extends IC2ElectricJetpackBase {
    public ElectricJetpack() {
        super("electric_jetpack", EquipmentSlot.CHEST, null);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 30000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 60;
    }

    @Override
    public boolean canDoRocketMode(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isElectricJetpack(ItemStack stack) {
        return true;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 0.7f;
    }

    @Override
    public float getThruster(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 0.65f : 0.3f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    @Override
    public int getMaxHeight(ItemStack stack, int worldHeight) {
        return (int)((float)worldHeight / 1.28f);
    }

    @Override
    public int getMaxRocketCharge(ItemStack stack) {
        return 0;
    }

    @Override
    public int getFuelCost(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 4 : 7;
    }

    @Override
    public String getTextureFolder() {
        return "armor/jetpack";
    }

    @Override
    public String getTextureName() {
        return "electric";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/jetpack_electric";
    }
}

