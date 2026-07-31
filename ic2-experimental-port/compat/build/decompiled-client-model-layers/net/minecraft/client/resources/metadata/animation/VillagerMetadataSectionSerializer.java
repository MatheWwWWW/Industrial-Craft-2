/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.client.resources.metadata.animation;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class VillagerMetadataSectionSerializer
implements MetadataSectionSerializer<VillagerMetaDataSection> {
    @Override
    public VillagerMetaDataSection m_6322_(JsonObject p_119095_) {
        return new VillagerMetaDataSection(VillagerMetaDataSection.Hat.m_119085_(GsonHelper.m_13851_(p_119095_, "hat", "none")));
    }

    @Override
    public String m_7991_() {
        return "villager";
    }

    @Override
    public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
        return this.m_6322_(jsonObject);
    }
}

