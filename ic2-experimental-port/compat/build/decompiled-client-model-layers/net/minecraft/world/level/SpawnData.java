/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;

public record SpawnData(CompoundTag f_186561_, Optional<CustomSpawnRules> f_186562_) {
    public static final Codec<SpawnData> f_186559_ = RecordCodecBuilder.create(p_186571_ -> p_186571_.group((App)CompoundTag.f_128325_.fieldOf("entity").forGetter(p_186576_ -> p_186576_.f_186561_), (App)CustomSpawnRules.f_186583_.optionalFieldOf("custom_spawn_rules").forGetter(p_186569_ -> p_186569_.f_186562_)).apply((Applicative)p_186571_, SpawnData::new));
    public static final Codec<SimpleWeightedRandomList<SpawnData>> f_186560_ = SimpleWeightedRandomList.m_185860_(f_186559_);
    public static final String f_151630_ = "minecraft:pig";

    public SpawnData() {
        this(Util.m_137469_(new CompoundTag(), p_186573_ -> p_186573_.m_128359_("id", f_151630_)), Optional.empty());
    }

    public SpawnData {
        ResourceLocation $$2 = ResourceLocation.m_135820_(f_186561_.m_128461_("id"));
        f_186561_.m_128359_("id", $$2 != null ? $$2.toString() : f_151630_);
    }

    public CompoundTag m_186567_() {
        return this.f_186561_;
    }

    public Optional<CustomSpawnRules> m_186574_() {
        return this.f_186562_;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SpawnData.class, "entityToSpawn;customSpawnRules", "f_186561_", "f_186562_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SpawnData.class, "entityToSpawn;customSpawnRules", "f_186561_", "f_186562_"}, this);
    }

    @Override
    public final boolean equals(Object p_186580_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SpawnData.class, "entityToSpawn;customSpawnRules", "f_186561_", "f_186562_"}, this, p_186580_);
    }

    public record CustomSpawnRules(InclusiveRange<Integer> f_186584_, InclusiveRange<Integer> f_186585_) {
        private static final InclusiveRange<Integer> f_186586_ = new InclusiveRange<Integer>(0, 15);
        public static final Codec<CustomSpawnRules> f_186583_ = RecordCodecBuilder.create(p_186597_ -> p_186597_.group((App)InclusiveRange.f_184562_.optionalFieldOf("block_light_limit", f_186586_).flatXmap(CustomSpawnRules::m_186592_, CustomSpawnRules::m_186592_).forGetter(p_186600_ -> p_186600_.f_186584_), (App)InclusiveRange.f_184562_.optionalFieldOf("sky_light_limit", f_186586_).flatXmap(CustomSpawnRules::m_186592_, CustomSpawnRules::m_186592_).forGetter(p_186595_ -> p_186595_.f_186585_)).apply((Applicative)p_186597_, CustomSpawnRules::new));

        private static DataResult<InclusiveRange<Integer>> m_186592_(InclusiveRange<Integer> p_186593_) {
            if (!f_186586_.m_184570_(p_186593_)) {
                return DataResult.error((String)("Light values must be withing range " + f_186586_));
            }
            return DataResult.success(p_186593_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{CustomSpawnRules.class, "blockLightLimit;skyLightLimit", "f_186584_", "f_186585_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{CustomSpawnRules.class, "blockLightLimit;skyLightLimit", "f_186584_", "f_186585_"}, this);
        }

        @Override
        public final boolean equals(Object p_186602_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{CustomSpawnRules.class, "blockLightLimit;skyLightLimit", "f_186584_", "f_186585_"}, this, p_186602_);
        }
    }
}

