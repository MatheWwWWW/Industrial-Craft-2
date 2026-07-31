/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package ic2.core.item;

import ic2.core.item.EntityIC2Boat;
import ic2.core.item.ItemIC2Boat;
import ic2.core.ref.ItemName;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityBoatRubber
extends EntityIC2Boat {
    public EntityBoatRubber(World world) {
        super(world);
    }

    @Override
    protected ItemStack getItem() {
        return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.rubber);
    }

    @Override
    protected ItemStack getBrokenItem() {
        this.func_184185_a(SoundEvents.field_187638_cR, 16.0f, 8.0f);
        return ItemName.boat.getItemStack(ItemIC2Boat.BoatType.broken_rubber);
    }

    @Override
    public String getTexture() {
        return "textures/models/boat_rubber.png";
    }
}

