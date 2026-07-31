/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public interface StructureSets {
    public static final Holder<StructureSet> f_211109_ = StructureSets.m_211128_(BuiltinStructureSets.f_209820_, new StructureSet(List.of(StructureSet.m_210015_(Structures.f_236524_), StructureSet.m_210015_(Structures.f_236525_), StructureSet.m_210015_(Structures.f_236526_), StructureSet.m_210015_(Structures.f_236527_), StructureSet.m_210015_(Structures.f_236528_)), (StructurePlacement)new RandomSpreadStructurePlacement(34, 8, RandomSpreadType.LINEAR, 10387312)));
    public static final Holder<StructureSet> f_211110_ = StructureSets.m_211131_(BuiltinStructureSets.f_209821_, Structures.f_236510_, new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357617));
    public static final Holder<StructureSet> f_211111_ = StructureSets.m_211131_(BuiltinStructureSets.f_209822_, Structures.f_236511_, new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357618));
    public static final Holder<StructureSet> f_211112_ = StructureSets.m_211131_(BuiltinStructureSets.f_209823_, Structures.f_236509_, new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357619));
    public static final Holder<StructureSet> f_211113_ = StructureSets.m_211131_(BuiltinStructureSets.f_209824_, Structures.f_236514_, new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357620));
    public static final Holder<StructureSet> f_211114_ = StructureSets.m_211131_(BuiltinStructureSets.f_209825_, Structures.f_236505_, new RandomSpreadStructurePlacement(Vec3i.f_123288_, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1, 0.2f, 165745296, Optional.of(new StructurePlacement.ExclusionZone(f_211109_, 10)), 32, 8, RandomSpreadType.LINEAR));
    public static final Holder<StructureSet> f_236496_ = StructureSets.m_211131_(BuiltinStructureSets.f_226491_, Structures.f_236504_, new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.LINEAR, 20083232));
    public static final Holder<StructureSet> f_211115_ = StructureSets.m_211131_(BuiltinStructureSets.f_209826_, Structures.f_236516_, new RandomSpreadStructurePlacement(32, 5, RandomSpreadType.TRIANGULAR, 10387313));
    public static final Holder<StructureSet> f_211116_ = StructureSets.m_211131_(BuiltinStructureSets.f_209827_, Structures.f_236508_, new RandomSpreadStructurePlacement(80, 20, RandomSpreadType.TRIANGULAR, 10387319));
    public static final Holder<StructureSet> f_211117_ = StructureSets.m_211131_(BuiltinStructureSets.f_209828_, Structures.f_236522_, new RandomSpreadStructurePlacement(new Vec3i(9, 0, 9), StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_2, 0.01f, 0, Optional.empty(), 1, 0, RandomSpreadType.LINEAR));
    public static final Holder<StructureSet> f_211118_ = StructureSets.m_211128_(BuiltinStructureSets.f_209829_, new StructureSet(List.of(StructureSet.m_210015_(Structures.f_236506_), StructureSet.m_210015_(Structures.f_236507_)), (StructurePlacement)new RandomSpreadStructurePlacement(Vec3i.f_123288_, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_3, 0.004f, 0, Optional.empty(), 1, 0, RandomSpreadType.LINEAR)));
    public static final Holder<StructureSet> f_211119_ = StructureSets.m_211128_(BuiltinStructureSets.f_209830_, new StructureSet(List.of(StructureSet.m_210015_(Structures.f_236529_), StructureSet.m_210015_(Structures.f_236530_), StructureSet.m_210015_(Structures.f_236499_), StructureSet.m_210015_(Structures.f_236500_), StructureSet.m_210015_(Structures.f_236501_), StructureSet.m_210015_(Structures.f_236502_), StructureSet.m_210015_(Structures.f_236503_)), (StructurePlacement)new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 34222645)));
    public static final Holder<StructureSet> f_211120_ = StructureSets.m_211128_(BuiltinStructureSets.f_209831_, new StructureSet(List.of(StructureSet.m_210015_(Structures.f_236512_), StructureSet.m_210015_(Structures.f_236513_)), (StructurePlacement)new RandomSpreadStructurePlacement(24, 4, RandomSpreadType.LINEAR, 165745295)));
    public static final Holder<StructureSet> f_211121_ = StructureSets.m_211128_(BuiltinStructureSets.f_209832_, new StructureSet(List.of(StructureSet.m_210015_(Structures.f_236517_), StructureSet.m_210015_(Structures.f_236518_)), (StructurePlacement)new RandomSpreadStructurePlacement(20, 8, RandomSpreadType.LINEAR, 14357621)));
    public static final Holder<StructureSet> f_211122_ = StructureSets.m_211128_(BuiltinStructureSets.f_209833_, new StructureSet(List.of(StructureSet.m_210017_(Structures.f_236519_, 2), StructureSet.m_210017_(Structures.f_236523_, 3)), (StructurePlacement)new RandomSpreadStructurePlacement(27, 4, RandomSpreadType.LINEAR, 30084232)));
    public static final Holder<StructureSet> f_211123_ = StructureSets.m_211131_(BuiltinStructureSets.f_209834_, Structures.f_236520_, new RandomSpreadStructurePlacement(2, 1, RandomSpreadType.LINEAR, 14357921));
    public static final Holder<StructureSet> f_211124_ = StructureSets.m_211131_(BuiltinStructureSets.f_209835_, Structures.f_236521_, new RandomSpreadStructurePlacement(20, 11, RandomSpreadType.TRIANGULAR, 10387313));
    public static final Holder<StructureSet> f_211125_ = StructureSets.m_211131_(BuiltinStructureSets.f_209836_, Structures.f_236515_, new ConcentricRingsStructurePlacement(32, 3, 128, BuiltinRegistries.f_123865_.m_203561_(BiomeTags.f_215819_)));

    public static Holder<StructureSet> m_236497_(Registry<StructureSet> p_236498_) {
        return (Holder)p_236498_.m_203611_().iterator().next();
    }

    public static Holder<StructureSet> m_211128_(ResourceKey<StructureSet> p_211129_, StructureSet p_211130_) {
        return BuiltinRegistries.m_206384_(BuiltinRegistries.f_211084_, p_211129_, p_211130_);
    }

    public static Holder<StructureSet> m_211131_(ResourceKey<StructureSet> p_211132_, Holder<Structure> p_211133_, StructurePlacement p_211134_) {
        return StructureSets.m_211128_(p_211132_, new StructureSet(p_211133_, p_211134_));
    }
}

