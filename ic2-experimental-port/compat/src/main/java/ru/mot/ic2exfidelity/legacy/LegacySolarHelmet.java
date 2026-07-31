package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Utility helmet that transfers the available skylight as EU to chest armor. */
public final class LegacySolarHelmet extends ItemArmorUtility {
    public LegacySolarHelmet(Item.Properties properties) {
        super(Ic2ArmorMaterials.CF_PACK, properties, EquipmentSlot.HEAD);
    }

    // Forge adds this hook to Item while transforming the game classes.
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        ItemStack chest = player.m_150109_().f_35975_.get(2);
        if (chest.m_41619_()) {
            return;
        }
        double skyLight = TileEntitySolarGenerator.getSkyLight(level, player.m_20183_());
        if (skyLight > 0.0) {
            ElectricItem.manager.charge(chest, skyLight, Integer.MAX_VALUE, true, false);
        }
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "ic2:textures/armor/solar_1.png";
    }
}
