/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.ItemName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorSolarHelmet
extends ItemArmorUtility {
    public ItemArmorSolarHelmet() {
        super(ItemName.solar_helmet, "solar", EntityEquipmentSlot.HEAD);
        this.func_77656_e(0);
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        double chargeAmount;
        boolean ret = false;
        if (player.field_71071_by.field_70460_b.get(2) != null && (chargeAmount = (double)TileEntitySolarGenerator.getSkyLight(player.func_130014_f_(), player.func_180425_c())) > 0.0) {
            boolean bl = ret = ElectricItem.manager.charge((ItemStack)player.field_71071_by.field_70460_b.get(2), chargeAmount, Integer.MAX_VALUE, true, false) > 0.0;
        }
        if (ret) {
            player.field_71069_bz.func_75142_b();
        }
    }

    @Override
    public int func_77619_b() {
        return 0;
    }
}

