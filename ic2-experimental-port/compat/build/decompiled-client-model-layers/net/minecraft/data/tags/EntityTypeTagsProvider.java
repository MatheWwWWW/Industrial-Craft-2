/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class EntityTypeTagsProvider
extends TagsProvider<EntityType<?>> {
    public EntityTypeTagsProvider(DataGenerator p_126517_) {
        super(p_126517_, Registry.f_122826_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(EntityTypeTags.f_13120_).m_126584_(EntityType.f_20524_, EntityType.f_20481_, EntityType.f_20497_);
        this.m_206424_(EntityTypeTags.f_13121_).m_126584_(EntityType.f_20568_, EntityType.f_20513_, EntityType.f_20518_, EntityType.f_20493_, EntityType.f_20459_, EntityType.f_20495_);
        this.m_206424_(EntityTypeTags.f_13122_).m_126582_(EntityType.f_20550_);
        this.m_206424_(EntityTypeTags.f_13123_).m_126584_(EntityType.f_20548_, EntityType.f_20478_);
        this.m_206424_(EntityTypeTags.f_13124_).m_206428_(EntityTypeTags.f_13123_).m_126584_(EntityType.f_20477_, EntityType.f_20463_, EntityType.f_20527_, EntityType.f_20483_, EntityType.f_20487_, EntityType.f_20561_, EntityType.f_20498_);
        this.m_206424_(EntityTypeTags.f_144291_).m_126584_(EntityType.f_20517_, EntityType.f_20567_, EntityType.f_20523_, EntityType.f_20452_);
        this.m_206424_(EntityTypeTags.f_144293_).m_126584_(EntityType.f_20489_, EntityType.f_20516_, EntityType.f_20519_, EntityType.f_20556_, EntityType.f_20480_, EntityType.f_147034_, EntityType.f_217013_);
        this.m_206424_(EntityTypeTags.f_144292_).m_126584_(EntityType.f_20562_, EntityType.f_20455_, EntityType.f_20563_);
        this.m_206424_(EntityTypeTags.f_144294_).m_126584_(EntityType.f_20481_, EntityType.f_20514_, EntityType.f_20528_, EntityType.f_20496_);
        this.m_206424_(EntityTypeTags.f_144295_).m_126584_(EntityType.f_20482_, EntityType.f_20551_, EntityType.f_20468_);
        this.m_206424_(EntityTypeTags.f_215847_).m_126584_(EntityType.f_20526_, EntityType.f_20468_);
    }
}

