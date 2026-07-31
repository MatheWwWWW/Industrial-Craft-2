/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.info;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

public class RegistryDumpReport
implements DataProvider {
    private final DataGenerator f_124050_;

    public RegistryDumpReport(DataGenerator p_124053_) {
        this.f_124050_ = p_124053_;
    }

    @Override
    public void m_213708_(CachedOutput p_236201_) throws IOException {
        JsonObject $$1 = new JsonObject();
        Registry.f_122897_.m_203611_().forEach(p_211088_ -> $$1.add(p_211088_.m_205785_().m_135782_().toString(), RegistryDumpReport.m_124058_((Registry)p_211088_.m_203334_())));
        Path $$2 = this.f_124050_.m_236034_(DataGenerator.Target.REPORTS).resolve("registries.json");
        DataProvider.m_236072_(p_236201_, (JsonElement)$$1, $$2);
    }

    private static <T> JsonElement m_124058_(Registry<T> p_124059_) {
        JsonObject $$1 = new JsonObject();
        if (p_124059_ instanceof DefaultedRegistry) {
            ResourceLocation $$2 = ((DefaultedRegistry)p_124059_).m_122315_();
            $$1.addProperty("default", $$2.toString());
        }
        int $$3 = Registry.f_122897_.m_7447_(p_124059_);
        $$1.addProperty("protocol_id", (Number)$$3);
        JsonObject $$4 = new JsonObject();
        p_124059_.m_203611_().forEach(p_211092_ -> {
            Object $$3 = p_211092_.m_203334_();
            int $$4 = p_124059_.m_7447_($$3);
            JsonObject $$5 = new JsonObject();
            $$5.addProperty("protocol_id", (Number)$$4);
            $$4.add(p_211092_.m_205785_().m_135782_().toString(), (JsonElement)$$5);
        });
        $$1.add("entries", (JsonElement)$$4);
        return $$1;
    }

    @Override
    public String m_6055_() {
        return "Registry Dump";
    }
}

