/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.armor.electric.ElectricPackArmor
 *  ic2.core.item.wearable.base.IC2JetpackBase
 *  ic2.core.item.wearable.base.IC2ModularElectricArmor
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Debug
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package trinsdar.gravisuit.mixin;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import trinsdar.gravisuit.items.armor.IHasOverlay;

@Debug(export=true)
@Mixin(value={IC2ModularElectricArmor.class})
public abstract class IC2ModularElectricArmorMixin
implements IHasOverlay {
    @Shadow
    public abstract IC2JetpackBase getJetpack(ItemStack var1);

    @Override
    public boolean isEnabled(ItemStack stack) {
        ListTag listTag = StackUtil.getNbtData((ItemStack)stack).m_128437_("armor_upgrades", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag nbt = listTag.m_128728_(i);
            Item item = ItemStack.m_41712_((CompoundTag)nbt).m_41720_();
            if (!(item instanceof ElectricPackArmor)) continue;
            return true;
        }
        return this.getJetpack(stack) != null;
    }

    @Override
    public CompoundTag getArmorNBT(ItemStack stack, boolean create) {
        CompoundTag data = create ? stack.m_41784_() : StackUtil.getNbtData((ItemStack)stack);
        CompoundTag subData = data.m_128469_("jetpack_data");
        if (create) {
            data.m_128365_("jetpack_data", (Tag)subData);
        }
        return subData;
    }
}

