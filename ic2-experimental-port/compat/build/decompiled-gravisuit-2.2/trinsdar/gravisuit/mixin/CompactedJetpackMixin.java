/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.base.PropertiesBuilder
 *  ic2.core.item.wearable.base.IC2ElectricJetpackBase
 *  ic2.core.item.wearable.jetpacks.CompactElectricJetpack
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 */
package trinsdar.gravisuit.mixin;

import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.item.wearable.jetpacks.CompactElectricJetpack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.util.GravisuitConfig;

@Mixin(value={CompactElectricJetpack.class})
public abstract class CompactedJetpackMixin
extends IC2ElectricJetpackBase {
    public CompactedJetpackMixin(String itemName, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(itemName, slot, props);
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.COMPACTED_JETPACK_PROVIDE_ENERGY;
    }
}

