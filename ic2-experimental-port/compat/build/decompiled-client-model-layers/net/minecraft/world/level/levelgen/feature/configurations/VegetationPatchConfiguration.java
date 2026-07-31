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
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class VegetationPatchConfiguration
implements FeatureConfiguration {
    public static final Codec<VegetationPatchConfiguration> f_161280_ = RecordCodecBuilder.create(p_161304_ -> p_161304_.group((App)TagKey.m_203886_(Registry.f_122901_).fieldOf("replaceable").forGetter(p_204869_ -> p_204869_.f_161281_), (App)BlockStateProvider.f_68747_.fieldOf("ground_state").forGetter(p_161322_ -> p_161322_.f_161282_), (App)PlacedFeature.f_191773_.fieldOf("vegetation_feature").forGetter(p_204867_ -> p_204867_.f_161283_), (App)CaveSurface.f_162094_.fieldOf("surface").forGetter(p_161318_ -> p_161318_.f_161284_), (App)IntProvider.m_146545_(1, 128).fieldOf("depth").forGetter(p_161316_ -> p_161316_.f_161285_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("extra_bottom_block_chance").forGetter(p_161314_ -> Float.valueOf(p_161314_.f_161286_)), (App)Codec.intRange((int)1, (int)256).fieldOf("vertical_range").forGetter(p_161312_ -> p_161312_.f_161287_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("vegetation_chance").forGetter(p_161310_ -> Float.valueOf(p_161310_.f_161288_)), (App)IntProvider.f_146531_.fieldOf("xz_radius").forGetter(p_161308_ -> p_161308_.f_161289_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("extra_edge_column_chance").forGetter(p_161306_ -> Float.valueOf(p_161306_.f_161290_))).apply((Applicative)p_161304_, VegetationPatchConfiguration::new));
    public final TagKey<Block> f_161281_;
    public final BlockStateProvider f_161282_;
    public final Holder<PlacedFeature> f_161283_;
    public final CaveSurface f_161284_;
    public final IntProvider f_161285_;
    public final float f_161286_;
    public final int f_161287_;
    public final float f_161288_;
    public final IntProvider f_161289_;
    public final float f_161290_;

    public VegetationPatchConfiguration(TagKey<Block> p_204856_, BlockStateProvider p_204857_, Holder<PlacedFeature> p_204858_, CaveSurface p_204859_, IntProvider p_204860_, float p_204861_, int p_204862_, float p_204863_, IntProvider p_204864_, float p_204865_) {
        this.f_161281_ = p_204856_;
        this.f_161282_ = p_204857_;
        this.f_161283_ = p_204858_;
        this.f_161284_ = p_204859_;
        this.f_161285_ = p_204860_;
        this.f_161286_ = p_204861_;
        this.f_161287_ = p_204862_;
        this.f_161288_ = p_204863_;
        this.f_161289_ = p_204864_;
        this.f_161290_ = p_204865_;
    }
}

