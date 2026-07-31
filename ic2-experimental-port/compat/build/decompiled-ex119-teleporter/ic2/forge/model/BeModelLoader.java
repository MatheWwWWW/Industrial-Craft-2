/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.client.model.geometry.IGeometryLoader
 */
package ic2.forge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import ic2.forge.model.DynamicBeModelForge;
import ic2.forge.model.Ic2Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public final class BeModelLoader
implements IGeometryLoader<Ic2Model> {
    public Ic2Model read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        ResourceLocation resourceLocation = new ResourceLocation(jsonObject.get("id").getAsString());
        return new DynamicBeModelForge(resourceLocation);
    }
}

