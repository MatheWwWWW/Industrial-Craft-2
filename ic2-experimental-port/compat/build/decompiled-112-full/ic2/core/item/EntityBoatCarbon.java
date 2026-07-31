/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.item.EntityIC2Boat;
import ic2.core.item.ItemIC2Boat;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBoatCarbon
extends EntityIC2Boat {
    public EntityBoatCarbon(World world) {
        super(world);
    }

    @Override
    protected ItemStack getItem() {
        return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.carbon);
    }

    @Override
    public String getTexture() {
        return "textures/models/boat_carbon.png";
    }
}

