/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.RegionFile;

public final class RegionFileStorage
implements AutoCloseable {
    public static final String f_156615_ = ".mca";
    private static final int f_156616_ = 256;
    private final Long2ObjectLinkedOpenHashMap<RegionFile> f_63699_ = new Long2ObjectLinkedOpenHashMap();
    private final Path f_63700_;
    private final boolean f_63701_;

    RegionFileStorage(Path p_196954_, boolean p_196955_) {
        this.f_63700_ = p_196954_;
        this.f_63701_ = p_196955_;
    }

    private RegionFile m_63711_(ChunkPos p_63712_) throws IOException {
        long $$1 = ChunkPos.m_45589_(p_63712_.m_45610_(), p_63712_.m_45612_());
        RegionFile $$2 = (RegionFile)this.f_63699_.getAndMoveToFirst($$1);
        if ($$2 != null) {
            return $$2;
        }
        if (this.f_63699_.size() >= 256) {
            ((RegionFile)this.f_63699_.removeLast()).close();
        }
        Files.createDirectories(this.f_63700_, new FileAttribute[0]);
        Path $$3 = this.f_63700_.resolve("r." + p_63712_.m_45610_() + "." + p_63712_.m_45612_() + f_156615_);
        RegionFile $$4 = new RegionFile($$3, this.f_63700_, this.f_63701_);
        this.f_63699_.putAndMoveToFirst($$1, (Object)$$4);
        return $$4;
    }

    @Nullable
    public CompoundTag m_63706_(ChunkPos p_63707_) throws IOException {
        RegionFile $$1 = this.m_63711_(p_63707_);
        try (DataInputStream $$2 = $$1.m_63645_(p_63707_);){
            if ($$2 == null) {
                CompoundTag compoundTag = null;
                return compoundTag;
            }
            CompoundTag compoundTag = NbtIo.m_128928_($$2);
            return compoundTag;
        }
    }

    public void m_196956_(ChunkPos p_196957_, StreamTagVisitor p_196958_) throws IOException {
        RegionFile $$2 = this.m_63711_(p_196957_);
        try (DataInputStream $$3 = $$2.m_63645_(p_196957_);){
            if ($$3 != null) {
                NbtIo.m_197509_($$3, p_196958_);
            }
        }
    }

    protected void m_63708_(ChunkPos p_63709_, @Nullable CompoundTag p_63710_) throws IOException {
        RegionFile $$2 = this.m_63711_(p_63709_);
        if (p_63710_ == null) {
            $$2.m_156613_(p_63709_);
        } else {
            try (DataOutputStream $$3 = $$2.m_63678_(p_63709_);){
                NbtIo.m_128941_(p_63710_, $$3);
            }
        }
    }

    @Override
    public void close() throws IOException {
        ExceptionCollector<IOException> $$0 = new ExceptionCollector<IOException>();
        for (RegionFile $$1 : this.f_63699_.values()) {
            try {
                $$1.close();
            }
            catch (IOException $$2) {
                $$0.m_13653_($$2);
            }
        }
        $$0.m_13652_();
    }

    public void m_63705_() throws IOException {
        for (RegionFile $$0 : this.f_63699_.values()) {
            $$0.m_63637_();
        }
    }
}

