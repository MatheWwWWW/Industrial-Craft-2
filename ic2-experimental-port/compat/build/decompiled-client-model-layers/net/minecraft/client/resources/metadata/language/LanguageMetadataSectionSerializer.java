/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.resources.metadata.language;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.metadata.language.LanguageMetadataSection;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class LanguageMetadataSectionSerializer
implements MetadataSectionSerializer<LanguageMetadataSection> {
    private static final int f_174869_ = 16;

    @Override
    public LanguageMetadataSection m_6322_(JsonObject p_119107_) {
        HashSet $$1 = Sets.newHashSet();
        for (Map.Entry $$2 : p_119107_.entrySet()) {
            String $$3 = (String)$$2.getKey();
            if ($$3.length() > 16) {
                throw new JsonParseException("Invalid language->'" + $$3 + "': language code must not be more than 16 characters long");
            }
            JsonObject $$4 = GsonHelper.m_13918_((JsonElement)$$2.getValue(), "language");
            String $$5 = GsonHelper.m_13906_($$4, "region");
            String $$6 = GsonHelper.m_13906_($$4, "name");
            boolean $$7 = GsonHelper.m_13855_($$4, "bidirectional", false);
            if ($$5.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + $$3 + "'->region: empty value");
            }
            if ($$6.isEmpty()) {
                throw new JsonParseException("Invalid language->'" + $$3 + "'->name: empty value");
            }
            if ($$1.add(new LanguageInfo($$3, $$5, $$6, $$7))) continue;
            throw new JsonParseException("Duplicate language->'" + $$3 + "' defined");
        }
        return new LanguageMetadataSection($$1);
    }

    @Override
    public String m_7991_() {
        return "language";
    }

    @Override
    public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
        return this.m_6322_(jsonObject);
    }
}

