/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class FlatLevelGeneratorPresets {
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226263_ = FlatLevelGeneratorPresets.m_226276_("classic_flat");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226264_ = FlatLevelGeneratorPresets.m_226276_("tunnelers_dream");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226265_ = FlatLevelGeneratorPresets.m_226276_("water_world");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226266_ = FlatLevelGeneratorPresets.m_226276_("overworld");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226267_ = FlatLevelGeneratorPresets.m_226276_("snowy_kingdom");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226268_ = FlatLevelGeneratorPresets.m_226276_("bottomless_pit");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226269_ = FlatLevelGeneratorPresets.m_226276_("desert");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226270_ = FlatLevelGeneratorPresets.m_226276_("redstone_ready");
    public static final ResourceKey<FlatLevelGeneratorPreset> f_226271_ = FlatLevelGeneratorPresets.m_226276_("the_void");

    public static Holder<FlatLevelGeneratorPreset> m_226274_(Registry<FlatLevelGeneratorPreset> p_226275_) {
        return new Bootstrap(p_226275_).m_226283_();
    }

    private static ResourceKey<FlatLevelGeneratorPreset> m_226276_(String p_226277_) {
        return ResourceKey.m_135785_(Registry.f_235727_, new ResourceLocation(p_226277_));
    }

    static class Bootstrap {
        private final Registry<FlatLevelGeneratorPreset> f_226278_;
        private final Registry<Biome> f_226279_ = BuiltinRegistries.f_123865_;
        private final Registry<StructureSet> f_226280_ = BuiltinRegistries.f_211084_;

        Bootstrap(Registry<FlatLevelGeneratorPreset> p_226282_) {
            this.f_226278_ = p_226282_;
        }

        private Holder<FlatLevelGeneratorPreset> m_226286_(ResourceKey<FlatLevelGeneratorPreset> p_226287_, ItemLike p_226288_, ResourceKey<Biome> p_226289_, Set<ResourceKey<StructureSet>> p_226290_, boolean p_226291_, boolean p_226292_, FlatLayerInfo ... p_226293_) {
            HolderSet.Direct $$7 = HolderSet.m_205800_(p_226290_.stream().flatMap(p_226285_ -> this.f_226280_.m_203636_((ResourceKey<StructureSet>)p_226285_).stream()).collect(Collectors.toList()));
            FlatLevelGeneratorSettings $$8 = new FlatLevelGeneratorSettings(Optional.of($$7), this.f_226279_);
            if (p_226291_) {
                $$8.m_70369_();
            }
            if (p_226292_) {
                $$8.m_70385_();
            }
            for (int $$9 = p_226293_.length - 1; $$9 >= 0; --$$9) {
                $$8.m_70401_().add(p_226293_[$$9]);
            }
            $$8.m_204918_(this.f_226279_.m_214121_(p_226289_));
            return BuiltinRegistries.m_206384_(this.f_226278_, p_226287_, new FlatLevelGeneratorPreset(p_226288_.m_5456_().m_204114_(), $$8));
        }

        public Holder<FlatLevelGeneratorPreset> m_226283_() {
            this.m_226286_(f_226263_, Blocks.f_50440_, Biomes.f_48202_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209820_), false, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(2, Blocks.f_50493_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226264_, Blocks.f_50069_, Biomes.f_186765_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209829_, BuiltinStructureSets.f_209836_), true, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(5, Blocks.f_50493_), new FlatLayerInfo(230, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226265_, Items.f_42447_, Biomes.f_48225_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209832_, BuiltinStructureSets.f_209831_, BuiltinStructureSets.f_209826_), false, false, new FlatLayerInfo(90, Blocks.f_49990_), new FlatLayerInfo(5, Blocks.f_49994_), new FlatLayerInfo(5, Blocks.f_50493_), new FlatLayerInfo(5, Blocks.f_50069_), new FlatLayerInfo(64, Blocks.f_152550_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226266_, Blocks.f_50034_, Biomes.f_48202_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209820_, BuiltinStructureSets.f_209829_, BuiltinStructureSets.f_209825_, BuiltinStructureSets.f_209830_, BuiltinStructureSets.f_209836_), true, true, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(59, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226267_, Blocks.f_50125_, Biomes.f_186761_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209820_, BuiltinStructureSets.f_209822_), false, false, new FlatLayerInfo(1, Blocks.f_50125_), new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(59, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226268_, Items.f_42402_, Biomes.f_48202_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209820_), false, false, new FlatLayerInfo(1, Blocks.f_50440_), new FlatLayerInfo(3, Blocks.f_50493_), new FlatLayerInfo(2, Blocks.f_50652_));
            this.m_226286_(f_226269_, Blocks.f_49992_, Biomes.f_48203_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(BuiltinStructureSets.f_209820_, BuiltinStructureSets.f_209821_, BuiltinStructureSets.f_209829_, BuiltinStructureSets.f_209836_), true, false, new FlatLayerInfo(8, Blocks.f_49992_), new FlatLayerInfo(52, Blocks.f_50062_), new FlatLayerInfo(3, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
            this.m_226286_(f_226270_, Items.f_42451_, Biomes.f_48203_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(), false, false, new FlatLayerInfo(116, Blocks.f_50062_), new FlatLayerInfo(3, Blocks.f_50069_), new FlatLayerInfo(1, Blocks.f_50752_));
            return this.m_226286_(f_226271_, Blocks.f_50375_, Biomes.f_48173_, (Set<ResourceKey<StructureSet>>)ImmutableSet.of(), true, false, new FlatLayerInfo(1, Blocks.f_50016_));
        }
    }
}

