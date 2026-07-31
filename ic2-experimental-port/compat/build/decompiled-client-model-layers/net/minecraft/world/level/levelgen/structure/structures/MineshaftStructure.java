/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;

public class MineshaftStructure
extends Structure {
    public static final Codec<MineshaftStructure> f_227957_ = RecordCodecBuilder.create(p_227971_ -> p_227971_.group(MineshaftStructure.m_226567_(p_227971_), (App)Type.f_227975_.fieldOf("mineshaft_type").forGetter(p_227969_ -> p_227969_.f_227958_)).apply((Applicative)p_227971_, MineshaftStructure::new));
    private final Type f_227958_;

    public MineshaftStructure(Structure.StructureSettings p_227961_, Type p_227962_) {
        super(p_227961_);
        this.f_227958_ = p_227962_;
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_227964_) {
        p_227964_.f_226626_().m_188500_();
        ChunkPos $$1 = p_227964_.f_226628_();
        BlockPos $$2 = new BlockPos($$1.m_151390_(), 50, $$1.m_45605_());
        StructurePiecesBuilder $$3 = new StructurePiecesBuilder();
        int $$4 = this.m_227965_($$3, p_227964_);
        return Optional.of(new Structure.GenerationStub($$2.m_7918_(0, $$4, 0), (Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder>)Either.right((Object)$$3)));
    }

    private int m_227965_(StructurePiecesBuilder p_227966_, Structure.GenerationContext p_227967_) {
        ChunkPos $$2 = p_227967_.f_226628_();
        WorldgenRandom $$3 = p_227967_.f_226626_();
        ChunkGenerator $$4 = p_227967_.f_226622_();
        MineshaftPieces.MineShaftRoom $$5 = new MineshaftPieces.MineShaftRoom(0, $$3, $$2.m_151382_(2), $$2.m_151391_(2), this.f_227958_);
        p_227966_.m_142679_($$5);
        $$5.m_214092_($$5, p_227966_, $$3);
        int $$6 = $$4.m_6337_();
        if (this.f_227958_ == Type.MESA) {
            BlockPos $$7 = p_227966_.m_192798_().m_162394_();
            int $$8 = $$4.m_214096_($$7.m_123341_(), $$7.m_123343_(), Heightmap.Types.WORLD_SURFACE_WG, p_227967_.f_226629_(), p_227967_.f_226624_());
            int $$9 = $$8 <= $$6 ? $$6 : Mth.m_216287_($$3, $$6, $$8);
            int $$10 = $$9 - $$7.m_123342_();
            p_227966_.m_192781_($$10);
            return $$10;
        }
        return p_227966_.m_226965_($$6, $$4.m_142062_(), $$3, 10);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226869_;
    }

    public static final class Type
    extends Enum<Type>
    implements StringRepresentable {
        public static final /* enum */ Type NORMAL = new Type("normal", Blocks.f_49999_, Blocks.f_50705_, Blocks.f_50132_);
        public static final /* enum */ Type MESA = new Type("mesa", Blocks.f_50004_, Blocks.f_50745_, Blocks.f_50483_);
        public static final Codec<Type> f_227975_;
        private final String f_227976_;
        private final BlockState f_227977_;
        private final BlockState f_227978_;
        private final BlockState f_227979_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_227998_) {
            return Enum.valueOf(Type.class, p_227998_);
        }

        private Type(String p_227985_, Block p_227986_, Block p_227987_, Block p_227988_) {
            this.f_227976_ = p_227985_;
            this.f_227977_ = p_227986_.m_49966_();
            this.f_227978_ = p_227987_.m_49966_();
            this.f_227979_ = p_227988_.m_49966_();
        }

        public String m_227989_() {
            return this.f_227976_;
        }

        public static Type m_227990_(int p_227991_) {
            if (p_227991_ < 0 || p_227991_ >= Type.values().length) {
                return NORMAL;
            }
            return Type.values()[p_227991_];
        }

        public BlockState m_227992_() {
            return this.f_227977_;
        }

        public BlockState m_227994_() {
            return this.f_227978_;
        }

        public BlockState m_227995_() {
            return this.f_227979_;
        }

        @Override
        public String m_7912_() {
            return this.f_227976_;
        }

        private static /* synthetic */ Type[] m_227996_() {
            return new Type[]{NORMAL, MESA};
        }

        static {
            $VALUES = Type.m_227996_();
            f_227975_ = StringRepresentable.m_216439_(Type::values);
        }
    }
}

