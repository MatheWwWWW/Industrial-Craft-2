/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  net.minecraftforge.client.model.geometry.IGeometryLoader
 */
package ic2.forge.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import ic2.forge.model.DynamicCableModelForge;
import ic2.forge.model.Ic2Model;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public final class CableModelLoader
implements IGeometryLoader<Ic2Model> {
    public Ic2Model read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        CableType cableType = CableType.valueOf(jsonObject.get("type").getAsString());
        int n = jsonObject.get("insulation").getAsInt();
        CableFoam cableFoam = CableFoam.get(jsonObject.get("foam").getAsString());
        boolean bl = jsonObject.get("active").getAsBoolean();
        return new DynamicCableModelForge(cableType, n, cableFoam, bl);
    }
}

