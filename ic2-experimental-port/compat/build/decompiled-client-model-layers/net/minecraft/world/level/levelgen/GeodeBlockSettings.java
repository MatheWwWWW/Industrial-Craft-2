/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GeodeBlockSettings {
    public final BlockStateProvider f_158287_;
    public final BlockStateProvider f_158288_;
    public final BlockStateProvider f_158289_;
    public final BlockStateProvider f_158290_;
    public final BlockStateProvider f_158291_;
    public final List<BlockState> f_158292_;
    public final TagKey<Block> f_158293_;
    public final TagKey<Block> f_158294_;
    public static final Codec<GeodeBlockSettings> f_158295_ = RecordCodecBuilder.create(p_158307_ -> p_158307_.group((App)BlockStateProvider.f_68747_.fieldOf("filling_provider").forGetter(p_158323_ -> p_158323_.f_158287_), (App)BlockStateProvider.f_68747_.fieldOf("inner_layer_provider").forGetter(p_158321_ -> p_158321_.f_158288_), (App)BlockStateProvider.f_68747_.fieldOf("alternate_inner_layer_provider").forGetter(p_158319_ -> p_158319_.f_158289_), (App)BlockStateProvider.f_68747_.fieldOf("middle_layer_provider").forGetter(p_158317_ -> p_158317_.f_158290_), (App)BlockStateProvider.f_68747_.fieldOf("outer_layer_provider").forGetter(p_158315_ -> p_158315_.f_158291_), (App)ExtraCodecs.m_144637_(BlockState.f_61039_.listOf()).fieldOf("inner_placements").forGetter(p_158313_ -> p_158313_.f_158292_), (App)TagKey.m_203886_(Registry.f_122901_).fieldOf("cannot_replace").forGetter(p_204566_ -> p_204566_.f_158293_), (App)TagKey.m_203886_(Registry.f_122901_).fieldOf("invalid_blocks").forGetter(p_204564_ -> p_204564_.f_158294_)).apply((Applicative)p_158307_, GeodeBlockSettings::new));

    public GeodeBlockSettings(BlockStateProvider p_204555_, BlockStateProvider p_204556_, BlockStateProvider p_204557_, BlockStateProvider p_204558_, BlockStateProvider p_204559_, List<BlockState> p_204560_, TagKey<Block> p_204561_, TagKey<Block> p_204562_) {
        this.f_158287_ = p_204555_;
        this.f_158288_ = p_204556_;
        this.f_158289_ = p_204557_;
        this.f_158290_ = p_204558_;
        this.f_158291_ = p_204559_;
        this.f_158292_ = p_204560_;
        this.f_158293_ = p_204561_;
        this.f_158294_ = p_204562_;
    }
}

