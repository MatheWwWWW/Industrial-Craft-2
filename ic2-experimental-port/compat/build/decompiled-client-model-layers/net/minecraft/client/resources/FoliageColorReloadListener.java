/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.FoliageColor;

public class FoliageColorReloadListener
extends SimplePreparableReloadListener<int[]> {
    private static final ResourceLocation f_118656_ = new ResourceLocation("textures/colormap/foliage.png");

    @Override
    protected int[] m_5944_(ResourceManager p_118660_, ProfilerFiller p_118661_) {
        try {
            return LegacyStuffWrapper.m_118726_(p_118660_, f_118656_);
        }
        catch (IOException $$2) {
            throw new IllegalStateException("Failed to load foliage color texture", $$2);
        }
    }

    @Override
    protected void m_5787_(int[] p_118667_, ResourceManager p_118668_, ProfilerFiller p_118669_) {
        FoliageColor.m_46110_(p_118667_);
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }
}

