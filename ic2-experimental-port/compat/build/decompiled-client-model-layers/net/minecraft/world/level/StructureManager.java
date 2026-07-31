/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureManager {
    private final LevelAccessor f_220460_;
    private final WorldGenSettings f_220461_;
    private final StructureCheck f_220462_;

    public StructureManager(LevelAccessor p_220464_, WorldGenSettings p_220465_, StructureCheck p_220466_) {
        this.f_220460_ = p_220464_;
        this.f_220461_ = p_220465_;
        this.f_220462_ = p_220466_;
    }

    public StructureManager m_220468_(WorldGenRegion p_220469_) {
        if (p_220469_.m_6018_() != this.f_220460_) {
            throw new IllegalStateException("Using invalid structure manager (source level: " + p_220469_.m_6018_() + ", region: " + p_220469_);
        }
        return new StructureManager(p_220469_, this.f_220461_, this.f_220462_);
    }

    public List<StructureStart> m_220477_(ChunkPos p_220478_, Predicate<Structure> p_220479_) {
        Map<Structure, LongSet> $$2 = this.f_220460_.m_46819_(p_220478_.f_45578_, p_220478_.f_45579_, ChunkStatus.f_62316_).m_62769_();
        ImmutableList.Builder $$3 = ImmutableList.builder();
        for (Map.Entry<Structure, LongSet> $$4 : $$2.entrySet()) {
            Structure $$5 = $$4.getKey();
            if (!p_220479_.test($$5)) continue;
            this.m_220480_($$5, $$4.getValue(), arg_0 -> ((ImmutableList.Builder)$$3).add(arg_0));
        }
        return $$3.build();
    }

    public List<StructureStart> m_220504_(SectionPos p_220505_, Structure p_220506_) {
        LongSet $$2 = this.f_220460_.m_46819_(p_220505_.m_123170_(), p_220505_.m_123222_(), ChunkStatus.f_62316_).m_213649_(p_220506_);
        ImmutableList.Builder $$3 = ImmutableList.builder();
        this.m_220480_(p_220506_, $$2, arg_0 -> ((ImmutableList.Builder)$$3).add(arg_0));
        return $$3.build();
    }

    public void m_220480_(Structure p_220481_, LongSet p_220482_, Consumer<StructureStart> p_220483_) {
        LongIterator longIterator = p_220482_.iterator();
        while (longIterator.hasNext()) {
            long $$3 = (Long)longIterator.next();
            SectionPos $$4 = SectionPos.m_123196_(new ChunkPos($$3), this.f_220460_.m_151560_());
            StructureStart $$5 = this.m_220512_($$4, p_220481_, this.f_220460_.m_46819_($$4.m_123170_(), $$4.m_123222_(), ChunkStatus.f_62315_));
            if ($$5 == null || !$$5.m_73603_()) continue;
            p_220483_.accept($$5);
        }
    }

    @Nullable
    public StructureStart m_220512_(SectionPos p_220513_, Structure p_220514_, StructureAccess p_220515_) {
        return p_220515_.m_213652_(p_220514_);
    }

    public void m_220516_(SectionPos p_220517_, Structure p_220518_, StructureStart p_220519_, StructureAccess p_220520_) {
        p_220520_.m_213792_(p_220518_, p_220519_);
    }

    public void m_220507_(SectionPos p_220508_, Structure p_220509_, long p_220510_, StructureAccess p_220511_) {
        p_220511_.m_213843_(p_220509_, p_220510_);
    }

    public boolean m_220467_() {
        return this.f_220461_.m_224677_();
    }

    public StructureStart m_220494_(BlockPos p_220495_, Structure p_220496_) {
        for (StructureStart $$2 : this.m_220504_(SectionPos.m_123199_(p_220495_), p_220496_)) {
            if (!$$2.m_73601_().m_71051_(p_220495_)) continue;
            return $$2;
        }
        return StructureStart.f_73561_;
    }

    public StructureStart m_220488_(BlockPos p_220489_, ResourceKey<Structure> p_220490_) {
        Structure $$2 = this.m_220521_().m_175515_(Registry.f_235725_).m_6246_(p_220490_);
        if ($$2 == null) {
            return StructureStart.f_73561_;
        }
        return this.m_220524_(p_220489_, $$2);
    }

    public StructureStart m_220491_(BlockPos p_220492_, TagKey<Structure> p_220493_) {
        Registry<Structure> $$2 = this.m_220521_().m_175515_(Registry.f_235725_);
        for (StructureStart $$3 : this.m_220477_(new ChunkPos(p_220492_), p_220503_ -> $$2.m_203300_($$2.m_7447_((Structure)p_220503_)).map(p_220472_ -> p_220472_.m_203656_(p_220493_)).orElse(false))) {
            if (!this.m_220497_(p_220492_, $$3)) continue;
            return $$3;
        }
        return StructureStart.f_73561_;
    }

    public StructureStart m_220524_(BlockPos p_220525_, Structure p_220526_) {
        for (StructureStart $$2 : this.m_220504_(SectionPos.m_123199_(p_220525_), p_220526_)) {
            if (!this.m_220497_(p_220525_, $$2)) continue;
            return $$2;
        }
        return StructureStart.f_73561_;
    }

    public boolean m_220497_(BlockPos p_220498_, StructureStart p_220499_) {
        for (StructurePiece $$2 : p_220499_.m_73602_()) {
            if (!$$2.m_73547_().m_71051_(p_220498_)) continue;
            return true;
        }
        return false;
    }

    public boolean m_220486_(BlockPos p_220487_) {
        SectionPos $$1 = SectionPos.m_123199_(p_220487_);
        return this.f_220460_.m_46819_($$1.m_123170_(), $$1.m_123222_(), ChunkStatus.f_62316_).m_187678_();
    }

    public Map<Structure, LongSet> m_220522_(BlockPos p_220523_) {
        SectionPos $$1 = SectionPos.m_123199_(p_220523_);
        return this.f_220460_.m_46819_($$1.m_123170_(), $$1.m_123222_(), ChunkStatus.f_62316_).m_62769_();
    }

    public StructureCheckResult m_220473_(ChunkPos p_220474_, Structure p_220475_, boolean p_220476_) {
        return this.f_220462_.m_226729_(p_220474_, p_220475_, p_220476_);
    }

    public void m_220484_(StructureStart p_220485_) {
        p_220485_.m_73607_();
        this.f_220462_.m_226722_(p_220485_.m_163625_(), p_220485_.m_226861_());
    }

    public RegistryAccess m_220521_() {
        return this.f_220460_.m_5962_();
    }
}

