/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.armor;

import ic2.api.items.armor.IMetalArmor;
import ic2.core.IC2;
import ic2.core.item.wearable.base.IC2ArmorBase;
import ic2.core.platform.registries.IC2Materials;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BronzeArmor
extends IC2ArmorBase
implements IMetalArmor {
    boolean enable;

    public BronzeArmor(String itemName, EquipmentSlot slot) {
        super(itemName, IC2Materials.BRONZE_ARMOR, slot);
        this.enable = !IC2.CONFIG.disableBronzeTools.get();
    }

    public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> subItems) {
        if (!this.m_220152_(IC2.IC2_MAIN_GROUP)) {
            return;
        }
        if (this.enable) {
            super.m_6787_(group, subItems);
        }
    }

    @Override
    public String getTextureFolder() {
        return "armor/bronze";
    }

    @Override
    public String getTextureName() {
        switch (this.f_40377_) {
            case HEAD: {
                return "helmet";
            }
            case CHEST: {
                return "chestplate";
            }
            case LEGS: {
                return "leggings";
            }
            case FEET: {
                return "boots";
            }
        }
        return "";
    }

    @Override
    public String getArmorTexture() {
        return "ic2:textures/models/armor/bronze";
    }

    @Override
    public boolean isMetalArmor(ItemStack stack, Player player, EquipmentSlot targetSlot) {
        return true;
    }
}

