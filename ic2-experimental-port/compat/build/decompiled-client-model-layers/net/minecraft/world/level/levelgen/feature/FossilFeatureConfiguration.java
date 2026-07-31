/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class FossilFeatureConfiguration
implements FeatureConfiguration {
    public static final Codec<FossilFeatureConfiguration> f_159796_ = RecordCodecBuilder.create(p_159816_ -> p_159816_.group((App)ResourceLocation.f_135803_.listOf().fieldOf("fossil_structures").forGetter(p_159830_ -> p_159830_.f_159797_), (App)ResourceLocation.f_135803_.listOf().fieldOf("overlay_structures").forGetter(p_159828_ -> p_159828_.f_159798_), (App)StructureProcessorType.f_74468_.fieldOf("fossil_processors").forGetter(p_204759_ -> p_204759_.f_159799_), (App)StructureProcessorType.f_74468_.fieldOf("overlay_processors").forGetter(p_204757_ -> p_204757_.f_159800_), (App)Codec.intRange((int)0, (int)7).fieldOf("max_empty_corners_allowed").forGetter(p_159818_ -> p_159818_.f_159801_)).apply((Applicative)p_159816_, FossilFeatureConfiguration::new));
    public final List<ResourceLocation> f_159797_;
    public final List<ResourceLocation> f_159798_;
    public final Holder<StructureProcessorList> f_159799_;
    public final Holder<StructureProcessorList> f_159800_;
    public final int f_159801_;

    public FossilFeatureConfiguration(List<ResourceLocation> p_204751_, List<ResourceLocation> p_204752_, Holder<StructureProcessorList> p_204753_, Holder<StructureProcessorList> p_204754_, int p_204755_) {
        if (p_204751_.isEmpty()) {
            throw new IllegalArgumentException("Fossil structure lists need at least one entry");
        }
        if (p_204751_.size() != p_204752_.size()) {
            throw new IllegalArgumentException("Fossil structure lists must be equal lengths");
        }
        this.f_159797_ = p_204751_;
        this.f_159798_ = p_204752_;
        this.f_159799_ = p_204753_;
        this.f_159800_ = p_204754_;
        this.f_159801_ = p_204755_;
    }
}

