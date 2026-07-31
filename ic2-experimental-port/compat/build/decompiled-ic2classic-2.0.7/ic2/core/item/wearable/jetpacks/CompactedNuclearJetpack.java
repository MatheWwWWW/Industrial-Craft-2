/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.jetpacks;

import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.world.item.ItemStack;

public class CompactedNuclearJetpack
extends NuclearJetpack {
    public CompactedNuclearJetpack() {
        super("compacted_nuclear_jetpack");
    }

    @Override
    public int getCapacity(ItemStack itemStack) {
        return 360000;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 1.8f;
    }

    @Override
    public float getThruster(ItemStack stack, IC2JetpackBase.HoverMode hoverMode) {
        switch (hoverMode) {
            case ADV: {
                return 3.0f;
            }
            case BASIC: {
                return 2.0f;
            }
            case NONE: {
                return 1.0f;
            }
        }
        return 2.0f;
    }

    @Override
    public int getFuelCost(ItemStack stack, IC2JetpackBase.HoverMode hoverMode) {
        return hoverMode == IC2JetpackBase.HoverMode.BASIC ? 35 : 40;
    }

    @Override
    public int getMaxRocketCharge(ItemStack stack) {
        return 30000;
    }

    @Override
    public String getTextureName() {
        return "compacted_nuclear";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/jetpack_compacted_nuclear";
    }
}

