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
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.biome.MobSpawnSettings;

public record StructureSpawnOverride(BoundingBoxType f_210043_, WeightedRandomList<MobSpawnSettings.SpawnerData> f_210044_) {
    public static final Codec<StructureSpawnOverride> f_210042_ = RecordCodecBuilder.create(p_210051_ -> p_210051_.group((App)BoundingBoxType.f_210060_.fieldOf("bounding_box").forGetter(StructureSpawnOverride::f_210043_), (App)WeightedRandomList.m_146333_(MobSpawnSettings.SpawnerData.f_48403_).fieldOf("spawns").forGetter(StructureSpawnOverride::f_210044_)).apply((Applicative)p_210051_, StructureSpawnOverride::new));

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{StructureSpawnOverride.class, "boundingBox;spawns", "f_210043_", "f_210044_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StructureSpawnOverride.class, "boundingBox;spawns", "f_210043_", "f_210044_"}, this);
    }

    @Override
    public final boolean equals(Object p_210054_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StructureSpawnOverride.class, "boundingBox;spawns", "f_210043_", "f_210044_"}, this, p_210054_);
    }

    public static final class BoundingBoxType
    extends Enum<BoundingBoxType>
    implements StringRepresentable {
        public static final /* enum */ BoundingBoxType PIECE = new BoundingBoxType("piece");
        public static final /* enum */ BoundingBoxType STRUCTURE = new BoundingBoxType("full");
        public static final Codec<BoundingBoxType> f_210060_;
        private final String f_210061_;
        private static final /* synthetic */ BoundingBoxType[] $VALUES;

        public static BoundingBoxType[] values() {
            return (BoundingBoxType[])$VALUES.clone();
        }

        public static BoundingBoxType valueOf(String p_210074_) {
            return Enum.valueOf(BoundingBoxType.class, p_210074_);
        }

        private BoundingBoxType(String p_210067_) {
            this.f_210061_ = p_210067_;
        }

        @Override
        public String m_7912_() {
            return this.f_210061_;
        }

        private static /* synthetic */ BoundingBoxType[] m_210071_() {
            return new BoundingBoxType[]{PIECE, STRUCTURE};
        }

        static {
            $VALUES = BoundingBoxType.m_210071_();
            f_210060_ = StringRepresentable.m_216439_(BoundingBoxType::values);
        }
    }
}

