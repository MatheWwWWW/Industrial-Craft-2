/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.armor.electric.ElectricPackArmor
 *  org.spongepowered.asm.mixin.Mixin
 */
package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import org.spongepowered.asm.mixin.Mixin;
import trinsdar.gravisuit.items.armor.IHasOverlay;

@Mixin(value={ElectricPackArmor.class})
public class ElectricPackArmorMixin
implements IHasOverlay {
}

