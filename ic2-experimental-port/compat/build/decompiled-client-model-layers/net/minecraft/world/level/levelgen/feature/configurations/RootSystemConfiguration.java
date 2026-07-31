/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class RootSystemConfiguration
implements FeatureConfiguration {
    public static final Codec<RootSystemConfiguration> f_161101_ = RecordCodecBuilder.create(p_198371_ -> p_198371_.group((App)PlacedFeature.f_191773_.fieldOf("feature").forGetter(p_204840_ -> p_204840_.f_161102_), (App)Codec.intRange((int)1, (int)64).fieldOf("required_vertical_space_for_tree").forGetter(p_161151_ -> p_161151_.f_161103_), (App)Codec.intRange((int)1, (int)64).fieldOf("root_radius").forGetter(p_161149_ -> p_161149_.f_161104_), (App)TagKey.m_203886_(Registry.f_122901_).fieldOf("root_replaceable").forGetter(p_204838_ -> p_204838_.f_161105_), (App)BlockStateProvider.f_68747_.fieldOf("root_state_provider").forGetter(p_161145_ -> p_161145_.f_161106_), (App)Codec.intRange((int)1, (int)256).fieldOf("root_placement_attempts").forGetter(p_161143_ -> p_161143_.f_161107_), (App)Codec.intRange((int)1, (int)4096).fieldOf("root_column_max_height").forGetter(p_161141_ -> p_161141_.f_161108_), (App)Codec.intRange((int)1, (int)64).fieldOf("hanging_root_radius").forGetter(p_161139_ -> p_161139_.f_161109_), (App)Codec.intRange((int)0, (int)16).fieldOf("hanging_roots_vertical_span").forGetter(p_161137_ -> p_161137_.f_161110_), (App)BlockStateProvider.f_68747_.fieldOf("hanging_root_state_provider").forGetter(p_161135_ -> p_161135_.f_161111_), (App)Codec.intRange((int)1, (int)256).fieldOf("hanging_root_placement_attempts").forGetter(p_161133_ -> p_161133_.f_161112_), (App)Codec.intRange((int)1, (int)64).fieldOf("allowed_vertical_water_for_tree").forGetter(p_161131_ -> p_161131_.f_161113_), (App)BlockPredicate.f_190392_.fieldOf("allowed_tree_position").forGetter(p_198373_ -> p_198373_.f_198355_)).apply((Applicative)p_198371_, RootSystemConfiguration::new));
    public final Holder<PlacedFeature> f_161102_;
    public final int f_161103_;
    public final int f_161104_;
    public final TagKey<Block> f_161105_;
    public final BlockStateProvider f_161106_;
    public final int f_161107_;
    public final int f_161108_;
    public final int f_161109_;
    public final int f_161110_;
    public final BlockStateProvider f_161111_;
    public final int f_161112_;
    public final int f_161113_;
    public final BlockPredicate f_198355_;

    public RootSystemConfiguration(Holder<PlacedFeature> p_204824_, int p_204825_, int p_204826_, TagKey<Block> p_204827_, BlockStateProvider p_204828_, int p_204829_, int p_204830_, int p_204831_, int p_204832_, BlockStateProvider p_204833_, int p_204834_, int p_204835_, BlockPredicate p_204836_) {
        this.f_161102_ = p_204824_;
        this.f_161103_ = p_204825_;
        this.f_161104_ = p_204826_;
        this.f_161105_ = p_204827_;
        this.f_161106_ = p_204828_;
        this.f_161107_ = p_204829_;
        this.f_161108_ = p_204830_;
        this.f_161109_ = p_204831_;
        this.f_161110_ = p_204832_;
        this.f_161111_ = p_204833_;
        this.f_161112_ = p_204834_;
        this.f_161113_ = p_204835_;
        this.f_198355_ = p_204836_;
    }
}

