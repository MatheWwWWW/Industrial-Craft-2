/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk.storage;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.chunk.storage.IOWorker;
import net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ChunkStorage
implements AutoCloseable {
    public static final int f_196910_ = 1493;
    private final IOWorker f_63495_;
    protected final DataFixer f_63496_;
    @Nullable
    private volatile LegacyStructureDataHandler f_63497_;

    public ChunkStorage(Path p_196912_, DataFixer p_196913_, boolean p_196914_) {
        this.f_63496_ = p_196913_;
        this.f_63495_ = new IOWorker(p_196912_, p_196914_, "chunk");
    }

    public boolean m_223451_(ChunkPos p_223452_, int p_223453_) {
        return this.f_63495_.m_223471_(p_223452_, p_223453_);
    }

    public CompoundTag m_188288_(ResourceKey<Level> p_188289_, Supplier<DimensionDataStorage> p_188290_, CompoundTag p_188291_, Optional<ResourceKey<Codec<? extends ChunkGenerator>>> p_188292_) {
        int $$4 = ChunkStorage.m_63505_(p_188291_);
        if ($$4 < 1493 && (p_188291_ = NbtUtils.m_129218_(this.f_63496_, DataFixTypes.CHUNK, p_188291_, $$4, 1493)).m_128469_("Level").m_128471_("hasLegacyStructureData")) {
            LegacyStructureDataHandler $$5 = this.m_223448_(p_188289_, p_188290_);
            p_188291_ = $$5.m_71326_(p_188291_);
        }
        ChunkStorage.m_196918_(p_188291_, p_188289_, p_188292_);
        p_188291_ = NbtUtils.m_129213_(this.f_63496_, DataFixTypes.CHUNK, p_188291_, Math.max(1493, $$4));
        if ($$4 < SharedConstants.m_183709_().getWorldVersion()) {
            p_188291_.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        }
        p_188291_.m_128473_("__context");
        return p_188291_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private LegacyStructureDataHandler m_223448_(ResourceKey<Level> p_223449_, Supplier<DimensionDataStorage> p_223450_) {
        LegacyStructureDataHandler $$2 = this.f_63497_;
        if ($$2 == null) {
            ChunkStorage chunkStorage = this;
            synchronized (chunkStorage) {
                $$2 = this.f_63497_;
                if ($$2 == null) {
                    this.f_63497_ = $$2 = LegacyStructureDataHandler.m_71331_(p_223449_, p_223450_.get());
                }
            }
        }
        return $$2;
    }

    public static void m_196918_(CompoundTag p_196919_, ResourceKey<Level> p_196920_, Optional<ResourceKey<Codec<? extends ChunkGenerator>>> p_196921_) {
        CompoundTag $$3 = new CompoundTag();
        $$3.m_128359_("dimension", p_196920_.m_135782_().toString());
        p_196921_.ifPresent(p_196917_ -> $$3.m_128359_("generator", p_196917_.m_135782_().toString()));
        p_196919_.m_128365_("__context", $$3);
    }

    public static int m_63505_(CompoundTag p_63506_) {
        return p_63506_.m_128425_("DataVersion", 99) ? p_63506_.m_128451_("DataVersion") : -1;
    }

    public CompletableFuture<Optional<CompoundTag>> m_223454_(ChunkPos p_223455_) {
        return this.f_63495_.m_156587_(p_223455_);
    }

    public void m_63502_(ChunkPos p_63503_, CompoundTag p_63504_) {
        this.f_63495_.m_63538_(p_63503_, p_63504_);
        if (this.f_63497_ != null) {
            this.f_63497_.m_71318_(p_63503_.m_45588_());
        }
    }

    public void m_63514_() {
        this.f_63495_.m_182498_(true).join();
    }

    @Override
    public void close() throws IOException {
        this.f_63495_.close();
    }

    public ChunkScanAccess m_196922_() {
        return this.f_63495_;
    }
}

