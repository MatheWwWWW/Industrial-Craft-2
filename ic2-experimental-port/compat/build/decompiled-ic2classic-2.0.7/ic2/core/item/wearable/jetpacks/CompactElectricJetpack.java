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

public class CompactElectricJetpack
extends IC2ElectricJetpackBase {
    public CompactElectricJetpack() {
        super("compacted_electric_jetpack", EquipmentSlot.CHEST, null);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return 360000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 2;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 500;
    }

    @Override
    public boolean canDoRocketMode(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canDoAdvHoverMode(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isElectricJetpack(ItemStack stack) {
        return true;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 1.4f;
    }

    @Override
    public float getThruster(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        switch (mode) {
            case ADV: {
                return 1.8f;
            }
            case BASIC: {
                return 1.2f;
            }
            case NONE: {
                return 0.6f;
            }
        }
        return 0.6f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    @Override
    public int getMaxHeight(ItemStack stack, int worldHeight) {
        return (int)((float)worldHeight / 1.1f);
    }

    @Override
    public int getMaxRocketCharge(ItemStack stack) {
        return 25000;
    }

    @Override
    public int getFuelCost(ItemStack stack, IC2JetpackBase.HoverMode mode) {
        return mode == IC2JetpackBase.HoverMode.BASIC ? 25 : 30;
    }

    @Override
    public String getTextureFolder() {
        return "armor/jetpack";
    }

    @Override
    public String getTextureName() {
        return "compacted_electric";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/jetpack_compacted_electric";
    }
}

