/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.server.packs.metadata.pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.GsonHelper;

public class PackMetadataSectionSerializer
implements MetadataSectionSerializer<PackMetadataSection> {
    @Override
    public PackMetadataSection m_6322_(JsonObject p_10380_) {
        MutableComponent $$1 = Component.Serializer.m_130691_(p_10380_.get("description"));
        if ($$1 == null) {
            throw new JsonParseException("Invalid/missing description!");
        }
        int $$2 = GsonHelper.m_13927_(p_10380_, "pack_format");
        return new PackMetadataSection($$1, $$2);
    }

    @Override
    public String m_7991_() {
        return "pack";
    }

    @Override
    public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
        return this.m_6322_(jsonObject);
    }
}

