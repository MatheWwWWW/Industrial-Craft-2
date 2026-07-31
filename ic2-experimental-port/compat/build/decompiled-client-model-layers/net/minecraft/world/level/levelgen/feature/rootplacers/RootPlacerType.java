/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;

public class RootPlacerType<P extends RootPlacer> {
    public static final RootPlacerType<MangroveRootPlacer> f_225898_ = RootPlacerType.m_225904_("mangrove_root_placer", MangroveRootPlacer.f_225813_);
    private final Codec<P> f_225899_;

    private static <P extends RootPlacer> RootPlacerType<P> m_225904_(String p_225905_, Codec<P> p_225906_) {
        return Registry.m_122961_(Registry.f_235742_, p_225905_, new RootPlacerType<P>(p_225906_));
    }

    private RootPlacerType(Codec<P> p_225902_) {
        this.f_225899_ = p_225902_;
    }

    public Codec<P> m_225903_() {
        return this.f_225899_;
    }
}

