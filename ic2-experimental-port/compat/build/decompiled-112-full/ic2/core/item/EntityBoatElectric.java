/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.EntityIC2Boat;
import ic2.core.item.ItemIC2Boat;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBoatElectric
extends EntityIC2Boat {
    private static final double euConsume = 4.0;
    private boolean accelerated = false;

    public EntityBoatElectric(World world) {
        super(world);
        this.field_70178_ae = true;
    }

    @Override
    protected ItemStack getItem() {
        return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.electric);
    }

    @Override
    protected double getAccelerationFactor() {
        return this.accelerated ? 1.5 : 0.25;
    }

    @Override
    protected double getTopSpeed() {
        return 0.7;
    }

    @Override
    protected boolean isWater(IBlockState block) {
        return block.func_185904_a() == Material.field_151586_h || block.func_185904_a() == Material.field_151587_i;
    }

    @Override
    public String getTexture() {
        return "textures/models/boat_electric.png";
    }

    @Override
    public void func_70071_h_() {
        this.func_70066_B();
        for (Entity e : this.func_184182_bu()) {
            e.func_70066_B();
        }
        this.accelerated = false;
        Entity driver = this.func_184179_bs();
        if (driver instanceof EntityPlayer && IC2.keyboard.isForwardKeyDown((EntityPlayer)driver)) {
            for (ItemStack stack : ((EntityPlayer)driver).field_71071_by.field_70460_b) {
                if (StackUtil.isEmpty(stack) || ElectricItem.manager.discharge(stack, 4.0, Integer.MAX_VALUE, true, true, true) != 4.0) continue;
                ElectricItem.manager.discharge(stack, 4.0, Integer.MAX_VALUE, true, true, false);
                this.accelerated = true;
                break;
            }
        }
        super.func_70071_h_();
    }
}

