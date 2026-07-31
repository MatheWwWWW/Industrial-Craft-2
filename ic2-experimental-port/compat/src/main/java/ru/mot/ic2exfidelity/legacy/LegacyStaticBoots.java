package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Distance-powered boots with the same five-block accumulator used in 2.8. */
public final class LegacyStaticBoots extends ItemArmorUtility {
    private static final String X_TAG = "x";
    private static final String Z_TAG = "z";

    public LegacyStaticBoots(Item.Properties properties) {
        super(Ic2ArmorMaterials.JET_PACK, properties, EquipmentSlot.FEET);
    }

    // Forge adds this hook to Item while transforming the game classes.
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        ItemStack chest = player.m_150109_().f_35975_.get(2);
        if (chest.m_41619_()) {
            return;
        }

        CompoundTag tag = stack.m_41784_();
        boolean resetPosition = player.m_20202_() != null || player.m_20069_();
        int x = (int) player.m_20185_();
        int z = (int) player.m_20189_();
        if (!tag.m_128441_(X_TAG) || resetPosition) {
            tag.m_128405_(X_TAG, x);
        }
        if (!tag.m_128441_(Z_TAG) || resetPosition) {
            tag.m_128405_(Z_TAG, z);
        }

        int dx = tag.m_128451_(X_TAG) - x;
        int dz = tag.m_128451_(Z_TAG) - z;
        double distance = Math.sqrt((double) dx * dx + (double) dz * dz);
        if (distance < 5.0) {
            return;
        }

        tag.m_128405_(X_TAG, x);
        tag.m_128405_(Z_TAG, z);
        ElectricItem.manager.charge(
                chest, Math.min(3.0, distance / 5.0), Integer.MAX_VALUE, true, false);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "ic2:textures/armor/rubber_1.png";
    }
}
