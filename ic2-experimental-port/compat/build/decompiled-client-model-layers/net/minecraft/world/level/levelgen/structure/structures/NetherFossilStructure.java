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
import net.minecraft.core.Direction;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;

public class NetherFossilStructure
extends Structure {
    public static final Codec<NetherFossilStructure> f_228569_ = RecordCodecBuilder.create(p_228585_ -> p_228585_.group(NetherFossilStructure.m_226567_(p_228585_), (App)HeightProvider.f_161970_.fieldOf("height").forGetter(p_228583_ -> p_228583_.f_228570_)).apply((Applicative)p_228585_, NetherFossilStructure::new));
    public final HeightProvider f_228570_;

    public NetherFossilStructure(Structure.StructureSettings p_228573_, HeightProvider p_228574_) {
        super(p_228573_);
        this.f_228570_ = p_228574_;
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_228576_) {
        WorldgenRandom $$1 = p_228576_.f_226626_();
        int $$2 = p_228576_.f_226628_().m_45604_() + $$1.m_188503_(16);
        int $$3 = p_228576_.f_226628_().m_45605_() + $$1.m_188503_(16);
        int $$4 = p_228576_.f_226622_().m_6337_();
        WorldGenerationContext $$5 = new WorldGenerationContext(p_228576_.f_226622_(), p_228576_.f_226629_());
        int $$6 = this.f_228570_.m_213859_($$1, $$5);
        NoiseColumn $$7 = p_228576_.f_226622_().m_214184_($$2, $$3, p_228576_.f_226629_(), p_228576_.f_226624_());
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos($$2, $$6, $$3);
        while ($$6 > $$4) {
            BlockState $$9 = $$7.m_183556_($$6);
            BlockState $$10 = $$7.m_183556_(--$$6);
            if (!$$9.m_60795_() || !$$10.m_60713_(Blocks.f_50135_) && !$$10.m_60783_(EmptyBlockGetter.INSTANCE, $$8.m_142448_($$6), Direction.UP)) continue;
            break;
        }
        if ($$6 <= $$4) {
            return Optional.empty();
        }
        BlockPos $$11 = new BlockPos($$2, $$6, $$3);
        return Optional.of(new Structure.GenerationStub($$11, p_228581_ -> NetherFossilPieces.m_228534_(p_228576_.f_226625_(), p_228581_, $$1, $$11)));
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226870_;
    }
}

