/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.world.entity.animal.CatVariant;

public class CatVariantTagsProvider
extends TagsProvider<CatVariant> {
    public CatVariantTagsProvider(DataGenerator p_236420_) {
        super(p_236420_, Registry.f_235732_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(CatVariantTags.f_215841_).m_126584_((CatVariant[])new CatVariant[]{CatVariant.f_218140_, CatVariant.f_218141_, CatVariant.f_218142_, CatVariant.f_218143_, CatVariant.f_218144_, CatVariant.f_218145_, CatVariant.f_218146_, CatVariant.f_218147_, CatVariant.f_218148_, CatVariant.f_218149_});
        this.m_206424_(CatVariantTags.f_215842_).m_206428_(CatVariantTags.f_215841_).m_126582_(CatVariant.f_218150_);
    }
}

