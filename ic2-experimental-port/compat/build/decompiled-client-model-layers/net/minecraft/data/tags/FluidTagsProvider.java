/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidTagsProvider
extends TagsProvider<Fluid> {
    public FluidTagsProvider(DataGenerator p_126523_) {
        super(p_126523_, Registry.f_122822_);
    }

    @Override
    protected void m_6577_() {
        this.m_206424_(FluidTags.f_13131_).m_126584_((Fluid[])new Fluid[]{Fluids.f_76193_, Fluids.f_76192_});
        this.m_206424_(FluidTags.f_13132_).m_126584_((Fluid[])new Fluid[]{Fluids.f_76195_, Fluids.f_76194_});
    }
}

