/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces;

public class OceanRuinStructure
extends Structure {
    public static final Codec<OceanRuinStructure> f_229054_ = RecordCodecBuilder.create(p_229075_ -> p_229075_.group(OceanRuinStructure.m_226567_(p_229075_), (App)Type.f_229083_.fieldOf("biome_temp").forGetter(p_229079_ -> p_229079_.f_229055_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("large_probability").forGetter(p_229077_ -> Float.valueOf(p_229077_.f_229056_)), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("cluster_probability").forGetter(p_229073_ -> Float.valueOf(p_229073_.f_229057_))).apply((Applicative)p_229075_, OceanRuinStructure::new));
    public final Type f_229055_;
    public final float f_229056_;
    public final float f_229057_;

    public OceanRuinStructure(Structure.StructureSettings p_229060_, Type p_229061_, float p_229062_, float p_229063_) {
        super(p_229060_);
        this.f_229055_ = p_229061_;
        this.f_229056_ = p_229062_;
        this.f_229057_ = p_229063_;
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_229065_) {
        return OceanRuinStructure.m_226585_(p_229065_, Heightmap.Types.OCEAN_FLOOR_WG, p_229068_ -> this.m_229069_((StructurePiecesBuilder)p_229068_, p_229065_));
    }

    private void m_229069_(StructurePiecesBuilder p_229070_, Structure.GenerationContext p_229071_) {
        BlockPos $$2 = new BlockPos(p_229071_.f_226628_().m_45604_(), 90, p_229071_.f_226628_().m_45605_());
        Rotation $$3 = Rotation.m_221990_(p_229071_.f_226626_());
        OceanRuinPieces.m_228994_(p_229071_.f_226625_(), $$2, $$3, p_229070_, p_229071_.f_226626_(), this);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226872_;
    }

    public static final class Type
    extends Enum<Type>
    implements StringRepresentable {
        public static final /* enum */ Type WARM = new Type("warm");
        public static final /* enum */ Type COLD = new Type("cold");
        public static final Codec<Type> f_229083_;
        private final String f_229084_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_229095_) {
            return Enum.valueOf(Type.class, p_229095_);
        }

        private Type(String p_229090_) {
            this.f_229084_ = p_229090_;
        }

        public String m_229091_() {
            return this.f_229084_;
        }

        @Override
        public String m_7912_() {
            return this.f_229084_;
        }

        private static /* synthetic */ Type[] m_229092_() {
            return new Type[]{WARM, COLD};
        }

        static {
            $VALUES = Type.m_229092_();
            f_229083_ = StringRepresentable.m_216439_(Type::values);
        }
    }
}

