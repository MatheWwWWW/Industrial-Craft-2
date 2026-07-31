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
import net.minecraft.world.level.GrassColor;

public class GrassColorReloadListener
extends SimplePreparableReloadListener<int[]> {
    private static final ResourceLocation f_118673_ = new ResourceLocation("textures/colormap/grass.png");

    @Override
    protected int[] m_5944_(ResourceManager p_118677_, ProfilerFiller p_118678_) {
        try {
            return LegacyStuffWrapper.m_118726_(p_118677_, f_118673_);
        }
        catch (IOException $$2) {
            throw new IllegalStateException("Failed to load grass color texture", $$2);
        }
    }

    @Override
    protected void m_5787_(int[] p_118684_, ResourceManager p_118685_, ProfilerFiller p_118686_) {
        GrassColor.m_46418_(p_118684_);
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }
}

