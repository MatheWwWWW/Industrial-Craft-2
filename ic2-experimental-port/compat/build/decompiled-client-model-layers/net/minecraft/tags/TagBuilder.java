/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tags;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;

public class TagBuilder {
    private final List<TagEntry> f_215897_ = new ArrayList<TagEntry>();

    public static TagBuilder m_215899_() {
        return new TagBuilder();
    }

    public List<TagEntry> m_215904_() {
        return List.copyOf(this.f_215897_);
    }

    public TagBuilder m_215902_(TagEntry p_215903_) {
        this.f_215897_.add(p_215903_);
        return this;
    }

    public TagBuilder m_215900_(ResourceLocation p_215901_) {
        return this.m_215902_(TagEntry.m_215925_(p_215901_));
    }

    public TagBuilder m_215905_(ResourceLocation p_215906_) {
        return this.m_215902_(TagEntry.m_215943_(p_215906_));
    }

    public TagBuilder m_215907_(ResourceLocation p_215908_) {
        return this.m_215902_(TagEntry.m_215949_(p_215908_));
    }

    public TagBuilder m_215909_(ResourceLocation p_215910_) {
        return this.m_215902_(TagEntry.m_215953_(p_215910_));
    }
}

