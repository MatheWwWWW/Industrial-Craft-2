/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.items.armor.IArmorModule$ArmorMod
 *  ic2.api.items.armor.IArmorModule$IArmorModuleHolder
 *  ic2.core.item.wearable.jetpacks.CompactedNuclearJetpack
 *  ic2.core.item.wearable.jetpacks.NuclearJetpack
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 */
package trinsdar.gravisuit.mixin;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.jetpacks.CompactedNuclearJetpack;
import ic2.core.item.wearable.jetpacks.NuclearJetpack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.util.GravisuitConfig;

@Mixin(value={CompactedNuclearJetpack.class})
public abstract class CompactedNuclearJetpackMixin
extends NuclearJetpack {
    public boolean canProvideEnergy(ItemStack itemStack) {
        return GravisuitConfig.MISC.COMPACTED_N_JETPACK_PROVIDE_ENERGY;
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
}

