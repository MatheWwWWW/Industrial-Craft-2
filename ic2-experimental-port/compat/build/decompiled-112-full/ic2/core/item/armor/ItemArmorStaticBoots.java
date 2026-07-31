/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.world.World
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemArmorStaticBoots
extends ItemArmorUtility {
    public ItemArmorStaticBoots() {
        super(ItemName.static_boots, "rubber", EntityEquipmentSlot.FEET);
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        double distance;
        boolean isNotWalking;
        if (StackUtil.isEmpty((ItemStack)player.field_71071_by.field_70460_b.get(2))) {
            return;
        }
        boolean ret = false;
        NBTTagCompound compound = StackUtil.getOrCreateNbtData(stack);
        boolean bl = isNotWalking = player.func_184187_bx() != null || player.func_70090_H();
        if (!compound.func_74764_b("x") || isNotWalking) {
            compound.func_74768_a("x", (int)player.field_70165_t);
        }
        if (!compound.func_74764_b("z") || isNotWalking) {
            compound.func_74768_a("z", (int)player.field_70161_v);
        }
        if ((distance = Math.sqrt((compound.func_74762_e("x") - (int)player.field_70165_t) * (compound.func_74762_e("x") - (int)player.field_70165_t) + (compound.func_74762_e("z") - (int)player.field_70161_v) * (compound.func_74762_e("z") - (int)player.field_70161_v))) >= 5.0) {
            compound.func_74768_a("x", (int)player.field_70165_t);
            compound.func_74768_a("z", (int)player.field_70161_v);
            boolean bl2 = ret = ElectricItem.manager.charge((ItemStack)player.field_71071_by.field_70460_b.get(2), Math.min(3.0, distance / 5.0), Integer.MAX_VALUE, true, false) > 0.0;
        }
        if (ret) {
            player.field_71069_bz.func_75142_b();
        }
    }
}

