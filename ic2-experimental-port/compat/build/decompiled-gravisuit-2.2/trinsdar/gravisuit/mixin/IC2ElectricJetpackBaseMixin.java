/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.base.IC2ElectricJetpackBase
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import trinsdar.gravisuit.items.armor.IHasOverlay;

@Mixin(value={IC2ElectricJetpackBase.class})
public abstract class IC2ElectricJetpackBaseMixin
implements IHasOverlay {
    @Shadow
    public abstract CompoundTag getNBTData(ItemStack var1, boolean var2);

    @Override
    public CompoundTag getArmorNBT(ItemStack stack, boolean create) {
        return this.getNBTData(stack, create);
    }
}

