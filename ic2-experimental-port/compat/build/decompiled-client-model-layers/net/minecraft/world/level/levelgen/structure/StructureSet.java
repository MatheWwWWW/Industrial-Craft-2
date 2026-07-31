/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public record StructureSet(List<StructureSelectionEntry> f_210003_, StructurePlacement f_210004_) {
    public static final Codec<StructureSet> f_210001_ = RecordCodecBuilder.create(p_210014_ -> p_210014_.group((App)StructureSelectionEntry.f_210025_.listOf().fieldOf("structures").forGetter(StructureSet::f_210003_), (App)StructurePlacement.f_205036_.fieldOf("placement").forGetter(StructureSet::f_210004_)).apply((Applicative)p_210014_, StructureSet::new));
    public static final Codec<Holder<StructureSet>> f_210002_ = RegistryFileCodec.m_135589_(Registry.f_211073_, f_210001_);

    public StructureSet(Holder<Structure> p_210007_, StructurePlacement p_210008_) {
        this(List.of(new StructureSelectionEntry(p_210007_, 1)), p_210008_);
    }

    public static StructureSelectionEntry m_210017_(Holder<Structure> p_210018_, int p_210019_) {
        return new StructureSelectionEntry(p_210018_, p_210019_);
    }

    public static StructureSelectionEntry m_210015_(Holder<Structure> p_210016_) {
        return new StructureSelectionEntry(p_210016_, 1);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{StructureSet.class, "structures;placement", "f_210003_", "f_210004_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StructureSet.class, "structures;placement", "f_210003_", "f_210004_"}, this);
    }

    @Override
    public final boolean equals(Object p_210022_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StructureSet.class, "structures;placement", "f_210003_", "f_210004_"}, this, p_210022_);
    }

    public record StructureSelectionEntry(Holder<Structure> f_210026_, int f_210027_) {
        public static final Codec<StructureSelectionEntry> f_210025_ = RecordCodecBuilder.create(p_210034_ -> p_210034_.group((App)Structure.f_226554_.fieldOf("structure").forGetter(StructureSelectionEntry::f_210026_), (App)ExtraCodecs.f_144629_.fieldOf("weight").forGetter(StructureSelectionEntry::f_210027_)).apply((Applicative)p_210034_, StructureSelectionEntry::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StructureSelectionEntry.class, "structure;weight", "f_210026_", "f_210027_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StructureSelectionEntry.class, "structure;weight", "f_210026_", "f_210027_"}, this);
        }

        @Override
        public final boolean equals(Object p_210039_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StructureSelectionEntry.class, "structure;weight", "f_210026_", "f_210027_"}, this, p_210039_);
        }
    }
}

