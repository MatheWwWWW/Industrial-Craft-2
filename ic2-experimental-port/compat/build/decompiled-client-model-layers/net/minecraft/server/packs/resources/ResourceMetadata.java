/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.server.packs.resources;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public interface ResourceMetadata {
    public static final ResourceMetadata f_215577_ = new ResourceMetadata(){

        @Override
        public <T> Optional<T> m_214059_(MetadataSectionSerializer<T> p_215584_) {
            return Optional.empty();
        }
    };

    public static ResourceMetadata m_215580_(InputStream p_215581_) throws IOException {
        try (BufferedReader $$1 = new BufferedReader(new InputStreamReader(p_215581_, StandardCharsets.UTF_8));){
            final JsonObject $$2 = GsonHelper.m_13859_($$1);
            ResourceMetadata resourceMetadata = new ResourceMetadata(){

                @Override
                public <T> Optional<T> m_214059_(MetadataSectionSerializer<T> p_215589_) {
                    String $$1 = p_215589_.m_7991_();
                    return $$2.has($$1) ? Optional.of(p_215589_.m_6322_(GsonHelper.m_13930_($$2, $$1))) : Optional.empty();
                }
            };
            return resourceMetadata;
        }
    }

    public <T> Optional<T> m_214059_(MetadataSectionSerializer<T> var1);
}

