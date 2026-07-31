/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class Carvers {
    public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> f_126848_ = Carvers.m_206430_("cave", WorldCarver.f_64974_.m_65063_(new CaveCarverConfiguration(0.15f, UniformHeight.m_162034_(VerticalAnchor.m_158930_(8), VerticalAnchor.m_158922_(180)), UniformFloat.m_146605_(0.1f, 0.9f), VerticalAnchor.m_158930_(8), CarverDebugSettings.m_159136_(false, Blocks.f_50669_.m_49966_()), Registry.f_122824_.m_203561_(BlockTags.f_215820_), UniformFloat.m_146605_(0.7f, 1.4f), UniformFloat.m_146605_(0.8f, 1.3f), UniformFloat.m_146605_(-1.0f, -0.4f))));
    public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> f_194741_ = Carvers.m_206430_("cave_extra_underground", WorldCarver.f_64974_.m_65063_(new CaveCarverConfiguration(0.07f, UniformHeight.m_162034_(VerticalAnchor.m_158930_(8), VerticalAnchor.m_158922_(47)), UniformFloat.m_146605_(0.1f, 0.9f), VerticalAnchor.m_158930_(8), CarverDebugSettings.m_159136_(false, Blocks.f_50251_.m_49966_()), Registry.f_122824_.m_203561_(BlockTags.f_215820_), UniformFloat.m_146605_(0.7f, 1.4f), UniformFloat.m_146605_(0.8f, 1.3f), UniformFloat.m_146605_(-1.0f, -0.4f))));
    public static final Holder<ConfiguredWorldCarver<CanyonCarverConfiguration>> f_126849_ = Carvers.m_206430_("canyon", WorldCarver.f_64976_.m_65063_(new CanyonCarverConfiguration(0.01f, UniformHeight.m_162034_(VerticalAnchor.m_158922_(10), VerticalAnchor.m_158922_(67)), ConstantFloat.m_146458_(3.0f), VerticalAnchor.m_158930_(8), CarverDebugSettings.m_159136_(false, Blocks.f_50670_.m_49966_()), Registry.f_122824_.m_203561_(BlockTags.f_215820_), UniformFloat.m_146605_(-0.125f, 0.125f), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.m_146605_(0.75f, 1.0f), TrapezoidFloat.m_146571_(0.0f, 6.0f, 2.0f), 3, UniformFloat.m_146605_(0.75f, 1.0f), 1.0f, 0.0f))));
    public static final Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> f_126853_ = Carvers.m_206430_("nether_cave", WorldCarver.f_64975_.m_65063_(new CaveCarverConfiguration(0.2f, UniformHeight.m_162034_(VerticalAnchor.m_158922_(0), VerticalAnchor.m_158935_(1)), ConstantFloat.m_146458_(0.5f), VerticalAnchor.m_158930_(10), Registry.f_122824_.m_203561_(BlockTags.f_215835_), ConstantFloat.m_146458_(1.0f), ConstantFloat.m_146458_(1.0f), ConstantFloat.m_146458_(-0.7f))));

    private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> m_206430_(String p_206431_, ConfiguredWorldCarver<WC> p_206432_) {
        return BuiltinRegistries.m_206380_(BuiltinRegistries.f_123860_, p_206431_, p_206432_);
    }
}

