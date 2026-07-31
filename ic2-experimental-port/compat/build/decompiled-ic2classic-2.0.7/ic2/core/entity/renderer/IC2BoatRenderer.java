/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.model.BoatModel
 *  net.minecraft.client.renderer.entity.BoatRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.vehicle.Boat
 */
package ic2.core.entity.renderer;

import com.mojang.datafixers.util.Pair;
import ic2.core.entity.misc.IC2Boat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class IC2BoatRenderer
extends BoatRenderer {
    public IC2BoatRenderer(EntityRendererProvider.Context context, boolean chest) {
        super(context, chest);
    }

    @Deprecated
    public ResourceLocation m_5478_(Boat entity) {
        return entity instanceof IC2Boat ? ((IC2Boat)entity).getTexture() : super.m_5478_(entity);
    }

    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
        Pair pair;
        if (boat instanceof IC2Boat) {
            IC2Boat ic2Boat = (IC2Boat)boat;
            pair = new Pair((Object)ic2Boat.getTexture(), (Object)((BoatModel)super.getModelWithLocation(boat).getSecond()));
        } else {
            pair = super.getModelWithLocation(boat);
        }
        return pair;
    }
}

