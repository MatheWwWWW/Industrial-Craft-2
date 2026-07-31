/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import org.slf4j.Logger;

public final class StructureStart {
    public static final String f_163590_ = "INVALID";
    public static final StructureStart f_73561_ = new StructureStart(null, new ChunkPos(0, 0), 0, new PiecesContainer(List.of()));
    private static final Logger f_226843_ = LogUtils.getLogger();
    private final Structure f_226844_;
    private final PiecesContainer f_192654_;
    private final ChunkPos f_163592_;
    private int f_73568_;
    @Nullable
    private volatile BoundingBox f_163593_;

    public StructureStart(Structure p_226846_, ChunkPos p_226847_, int p_226848_, PiecesContainer p_226849_) {
        this.f_226844_ = p_226846_;
        this.f_163592_ = p_226847_;
        this.f_73568_ = p_226848_;
        this.f_192654_ = p_226849_;
    }

    @Nullable
    public static StructureStart m_226857_(StructurePieceSerializationContext p_226858_, CompoundTag p_226859_, long p_226860_) {
        String $$3 = p_226859_.m_128461_("id");
        if (f_163590_.equals($$3)) {
            return f_73561_;
        }
        Registry<Structure> $$4 = p_226858_.f_192763_().m_175515_(Registry.f_235725_);
        Structure $$5 = $$4.m_7745_(new ResourceLocation($$3));
        if ($$5 == null) {
            f_226843_.error("Unknown stucture id: {}", (Object)$$3);
            return null;
        }
        ChunkPos $$6 = new ChunkPos(p_226859_.m_128451_("ChunkX"), p_226859_.m_128451_("ChunkZ"));
        int $$7 = p_226859_.m_128451_("references");
        ListTag $$8 = p_226859_.m_128437_("Children", 10);
        try {
            PiecesContainer $$9 = PiecesContainer.m_192753_($$8, p_226858_);
            if ($$5 instanceof OceanMonumentStructure) {
                $$9 = OceanMonumentStructure.m_228956_($$6, p_226860_, $$9);
            }
            return new StructureStart($$5, $$6, $$7, $$9);
        }
        catch (Exception $$10) {
            f_226843_.error("Failed Start with id {}", (Object)$$3, (Object)$$10);
            return null;
        }
    }

    public BoundingBox m_73601_() {
        BoundingBox $$0 = this.f_163593_;
        if ($$0 == null) {
            this.f_163593_ = $$0 = this.f_226844_.m_226569_(this.f_192654_.m_192756_());
        }
        return $$0;
    }

    public void m_226850_(WorldGenLevel p_226851_, StructureManager p_226852_, ChunkGenerator p_226853_, RandomSource p_226854_, BoundingBox p_226855_, ChunkPos p_226856_) {
        List<StructurePiece> $$6 = this.f_192654_.f_192741_();
        if ($$6.isEmpty()) {
            return;
        }
        BoundingBox $$7 = $$6.get((int)0).f_73383_;
        BlockPos $$8 = $$7.m_162394_();
        BlockPos $$9 = new BlockPos($$8.m_123341_(), $$7.m_162396_(), $$8.m_123343_());
        for (StructurePiece $$10 : $$6) {
            if (!$$10.m_73547_().m_71049_(p_226855_)) continue;
            $$10.m_213694_(p_226851_, p_226852_, p_226853_, p_226854_, p_226855_, p_226856_, $$9);
        }
        this.f_226844_.m_214110_(p_226851_, p_226852_, p_226853_, p_226854_, p_226855_, p_226856_, this.f_192654_);
    }

    public CompoundTag m_192660_(StructurePieceSerializationContext p_192661_, ChunkPos p_192662_) {
        CompoundTag $$2 = new CompoundTag();
        if (!this.m_73603_()) {
            $$2.m_128359_("id", f_163590_);
            return $$2;
        }
        $$2.m_128359_("id", p_192661_.f_192763_().m_175515_(Registry.f_235725_).m_7981_(this.f_226844_).toString());
        $$2.m_128405_("ChunkX", p_192662_.f_45578_);
        $$2.m_128405_("ChunkZ", p_192662_.f_45579_);
        $$2.m_128405_("references", this.f_73568_);
        $$2.m_128365_("Children", this.f_192654_.m_192749_(p_192661_));
        return $$2;
    }

    public boolean m_73603_() {
        return !this.f_192654_.m_192748_();
    }

    public ChunkPos m_163625_() {
        return this.f_163592_;
    }

    public boolean m_73606_() {
        return this.f_73568_ < this.m_73609_();
    }

    public void m_73607_() {
        ++this.f_73568_;
    }

    public int m_73608_() {
        return this.f_73568_;
    }

    protected int m_73609_() {
        return 1;
    }

    public Structure m_226861_() {
        return this.f_226844_;
    }

    public List<StructurePiece> m_73602_() {
        return this.f_192654_.f_192741_();
    }
}

