/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.models.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;

public class DelegatedModel
implements Supplier<JsonElement> {
    private final ResourceLocation f_125566_;

    public DelegatedModel(ResourceLocation p_125568_) {
        this.f_125566_ = p_125568_;
    }

    @Override
    public JsonElement get() {
        JsonObject $$0 = new JsonObject();
        $$0.addProperty("parent", this.f_125566_.toString());
        return $$0;
    }

    @Override
    public /* synthetic */ Object get() {
        return this.get();
    }
}

