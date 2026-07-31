/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.wearable.modules;

import ic2.api.items.armor.IArmorModule;
import ic2.core.item.wearable.modules.BaseModuleItem;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LavaProtectModuleItem
extends BaseModuleItem {
    public static final String LAVA = "lava_enabled";

    public LavaProtectModuleItem(String textureFolder, String textureName) {
        super("lava_module", null, textureFolder, textureName, IArmorModule.ModuleType.ANY);
    }

    @Override
    public void onInstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        armor.m_41784_().m_128379_(LAVA, true);
        holder.addAddModifier(armor, IArmorModule.ArmorMod.NO_LAVA_DAMAGE, 1);
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        if (world.m_46467_() % 20L == 0L) {
            CompoundTag data;
            boolean result = this.canUseEnergy(stack, 1000);
            if (player.m_20077_()) {
                this.useEnergy(armor, 1000, (LivingEntity)player);
            }
            if ((data = stack.m_41784_()).m_128471_(LAVA) != result) {
                data.m_128379_(LAVA, result);
                if (result) {
                    ((IArmorModule.IArmorModuleHolder)armor.m_41720_()).addAddModifier(armor, IArmorModule.ArmorMod.NO_LAVA_DAMAGE, 1);
                } else {
                    ((IArmorModule.IArmorModuleHolder)armor.m_41720_()).removeAddModifier(armor, IArmorModule.ArmorMod.NO_LAVA_DAMAGE, 1);
                }
            }
        }
    }

    @Override
    public void onUninstall(ItemStack stack, ItemStack armor, IArmorModule.IArmorModuleHolder holder) {
        CompoundTag data = StackUtil.getNbtData(armor);
        if (data.m_128471_(LAVA)) {
            holder.removeAddModifier(armor, IArmorModule.ArmorMod.NO_LAVA_DAMAGE, 1);
        }
        data.m_128473_(LAVA);
    }
}

