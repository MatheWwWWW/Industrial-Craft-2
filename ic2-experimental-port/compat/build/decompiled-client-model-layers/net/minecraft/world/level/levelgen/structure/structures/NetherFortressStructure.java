/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;

public class NetherFortressStructure
extends Structure {
    public static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_228517_ = WeightedRandomList.m_146330_((WeightedEntry[])new MobSpawnSettings.SpawnerData[]{new MobSpawnSettings.SpawnerData(EntityType.f_20551_, 10, 2, 3), new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 5, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.f_20497_, 8, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.f_20524_, 2, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 3, 4, 4)});
    public static final Codec<NetherFortressStructure> f_228518_ = NetherFortressStructure.m_226607_(NetherFortressStructure::new);

    public NetherFortressStructure(Structure.StructureSettings p_228521_) {
        super(p_228521_);
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_228523_) {
        ChunkPos $$1 = p_228523_.f_226628_();
        BlockPos $$2 = new BlockPos($$1.m_45604_(), 64, $$1.m_45605_());
        return Optional.of(new Structure.GenerationStub($$2, p_228526_ -> NetherFortressStructure.m_228527_(p_228526_, p_228523_)));
    }

    private static void m_228527_(StructurePiecesBuilder p_228528_, Structure.GenerationContext p_228529_) {
        NetherFortressPieces.StartPiece $$2 = new NetherFortressPieces.StartPiece(p_228529_.f_226626_(), p_228529_.f_226628_().m_151382_(2), p_228529_.f_226628_().m_151391_(2));
        p_228528_.m_142679_($$2);
        $$2.m_214092_($$2, p_228528_, p_228529_.f_226626_());
        List<StructurePiece> $$3 = $$2.f_228510_;
        while (!$$3.isEmpty()) {
            int $$4 = p_228529_.f_226626_().m_188503_($$3.size());
            StructurePiece $$5 = $$3.remove($$4);
            $$5.m_214092_($$2, p_228528_, p_228529_.f_226626_());
        }
        p_228528_.m_226970_(p_228529_.f_226626_(), 48, 70);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226865_;
    }
}

