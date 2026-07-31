/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.client.resources.metadata.texture;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class TextureMetadataSectionSerializer
implements MetadataSectionSerializer<TextureMetadataSection> {
    @Override
    public TextureMetadataSection m_6322_(JsonObject p_119122_) {
        boolean $$1 = GsonHelper.m_13855_(p_119122_, "blur", false);
        boolean $$2 = GsonHelper.m_13855_(p_119122_, "clamp", false);
        return new TextureMetadataSection($$1, $$2);
    }

    @Override
    public String m_7991_() {
        return "texture";
    }

    @Override
    public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
        return this.m_6322_(jsonObject);
    }
}

