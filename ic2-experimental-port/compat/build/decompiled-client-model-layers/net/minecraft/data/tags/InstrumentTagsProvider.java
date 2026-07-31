/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;

public class InstrumentTagsProvider
extends TagsProvider<Instrument> {
    public InstrumentTagsProvider(DataGenerator p_236428_) {
        super(p_236428_, Registry.f_235738_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(InstrumentTags.f_215856_).m_211101_(Instruments.f_220139_).m_211101_(Instruments.f_220140_).m_211101_(Instruments.f_220141_).m_211101_(Instruments.f_220142_);
        this.m_206424_(InstrumentTags.f_215857_).m_211101_(Instruments.f_220143_).m_211101_(Instruments.f_220144_).m_211101_(Instruments.f_220145_).m_211101_(Instruments.f_220146_);
        this.m_206424_(InstrumentTags.f_215858_).m_206428_(InstrumentTags.f_215856_).m_206428_(InstrumentTags.f_215857_);
    }
}

