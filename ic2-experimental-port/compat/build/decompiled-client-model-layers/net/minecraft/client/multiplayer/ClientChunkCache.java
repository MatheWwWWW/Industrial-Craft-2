/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.mojang.logging.LogUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.slf4j.Logger;

public class ClientChunkCache
extends ChunkSource {
    static final Logger f_104407_ = LogUtils.getLogger();
    private final LevelChunk f_104408_;
    private final LevelLightEngine f_104409_;
    volatile Storage f_104410_;
    final ClientLevel f_104411_;

    public ClientChunkCache(ClientLevel p_104414_, int p_104415_) {
        this.f_104411_ = p_104414_;
        this.f_104408_ = new EmptyLevelChunk(p_104414_, new ChunkPos(0, 0), p_104414_.m_5962_().m_175515_(Registry.f_122885_).m_206081_(Biomes.f_48202_));
        this.f_104409_ = new LevelLightEngine(this, true, p_104414_.m_6042_().f_223549_());
        this.f_104410_ = new Storage(ClientChunkCache.m_104448_(p_104415_));
    }

    @Override
    public LevelLightEngine m_7827_() {
        return this.f_104409_;
    }

    private static boolean m_104438_(@Nullable LevelChunk p_104439_, int p_104440_, int p_104441_) {
        if (p_104439_ == null) {
            return false;
        }
        ChunkPos $$3 = p_104439_.m_7697_();
        return $$3.f_45578_ == p_104440_ && $$3.f_45579_ == p_104441_;
    }

    public void m_104455_(int p_104456_, int p_104457_) {
        if (!this.f_104410_.m_104500_(p_104456_, p_104457_)) {
            return;
        }
        int $$2 = this.f_104410_.m_104481_(p_104456_, p_104457_);
        LevelChunk $$3 = this.f_104410_.m_104479_($$2);
        if (ClientChunkCache.m_104438_($$3, p_104456_, p_104457_)) {
            this.f_104410_.m_104487_($$2, $$3, null);
        }
    }

    @Override
    @Nullable
    public LevelChunk m_7587_(int p_104451_, int p_104452_, ChunkStatus p_104453_, boolean p_104454_) {
        LevelChunk $$4;
        if (this.f_104410_.m_104500_(p_104451_, p_104452_) && ClientChunkCache.m_104438_($$4 = this.f_104410_.m_104479_(this.f_104410_.m_104481_(p_104451_, p_104452_)), p_104451_, p_104452_)) {
            return $$4;
        }
        if (p_104454_) {
            return this.f_104408_;
        }
        return null;
    }

    @Override
    public BlockGetter m_7653_() {
        return this.f_104411_;
    }

    @Nullable
    public LevelChunk m_194116_(int p_194117_, int p_194118_, FriendlyByteBuf p_194119_, CompoundTag p_194120_, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> p_194121_) {
        if (!this.f_104410_.m_104500_(p_194117_, p_194118_)) {
            f_104407_.warn("Ignoring chunk since it's not in the view range: {}, {}", (Object)p_194117_, (Object)p_194118_);
            return null;
        }
        int $$5 = this.f_104410_.m_104481_(p_194117_, p_194118_);
        LevelChunk $$6 = this.f_104410_.f_104466_.get($$5);
        ChunkPos $$7 = new ChunkPos(p_194117_, p_194118_);
        if (!ClientChunkCache.m_104438_($$6, p_194117_, p_194118_)) {
            $$6 = new LevelChunk(this.f_104411_, $$7);
            $$6.m_187971_(p_194119_, p_194120_, p_194121_);
            this.f_104410_.m_104484_($$5, $$6);
        } else {
            $$6.m_187971_(p_194119_, p_194120_, p_194121_);
        }
        this.f_104411_.m_171649_($$7);
        return $$6;
    }

    @Override
    public void m_201698_(BooleanSupplier p_202421_, boolean p_202422_) {
    }

    public void m_104459_(int p_104460_, int p_104461_) {
        this.f_104410_.f_104469_ = p_104460_;
        this.f_104410_.f_104470_ = p_104461_;
    }

    public void m_104416_(int p_104417_) {
        int $$1 = this.f_104410_.f_104467_;
        int $$2 = ClientChunkCache.m_104448_(p_104417_);
        if ($$1 != $$2) {
            Storage $$3 = new Storage($$2);
            $$3.f_104469_ = this.f_104410_.f_104469_;
            $$3.f_104470_ = this.f_104410_.f_104470_;
            for (int $$4 = 0; $$4 < this.f_104410_.f_104466_.length(); ++$$4) {
                LevelChunk $$5 = this.f_104410_.f_104466_.get($$4);
                if ($$5 == null) continue;
                ChunkPos $$6 = $$5.m_7697_();
                if (!$$3.m_104500_($$6.f_45578_, $$6.f_45579_)) continue;
                $$3.m_104484_($$3.m_104481_($$6.f_45578_, $$6.f_45579_), $$5);
            }
            this.f_104410_ = $$3;
        }
    }

    private static int m_104448_(int p_104449_) {
        return Math.max(2, p_104449_) + 3;
    }

    @Override
    public String m_6754_() {
        return this.f_104410_.f_104466_.length() + ", " + this.m_8482_();
    }

    @Override
    public int m_8482_() {
        return this.f_104410_.f_104471_;
    }

    @Override
    public void m_6506_(LightLayer p_104436_, SectionPos p_104437_) {
        Minecraft.m_91087_().f_91060_.m_109770_(p_104437_.m_123170_(), p_104437_.m_123206_(), p_104437_.m_123222_());
    }

    @Override
    @Nullable
    public /* synthetic */ ChunkAccess m_7587_(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        return this.m_7587_(n, n2, chunkStatus, bl);
    }

    final class Storage {
        final AtomicReferenceArray<LevelChunk> f_104466_;
        final int f_104467_;
        private final int f_104468_;
        volatile int f_104469_;
        volatile int f_104470_;
        int f_104471_;

        Storage(int p_104474_) {
            this.f_104467_ = p_104474_;
            this.f_104468_ = p_104474_ * 2 + 1;
            this.f_104466_ = new AtomicReferenceArray(this.f_104468_ * this.f_104468_);
        }

        int m_104481_(int p_104482_, int p_104483_) {
            return Math.floorMod(p_104483_, this.f_104468_) * this.f_104468_ + Math.floorMod(p_104482_, this.f_104468_);
        }

        protected void m_104484_(int p_104485_, @Nullable LevelChunk p_104486_) {
            LevelChunk $$2 = this.f_104466_.getAndSet(p_104485_, p_104486_);
            if ($$2 != null) {
                --this.f_104471_;
                ClientChunkCache.this.f_104411_.m_104665_($$2);
            }
            if (p_104486_ != null) {
                ++this.f_104471_;
            }
        }

        protected LevelChunk m_104487_(int p_104488_, LevelChunk p_104489_, @Nullable LevelChunk p_104490_) {
            if (this.f_104466_.compareAndSet(p_104488_, p_104489_, p_104490_) && p_104490_ == null) {
                --this.f_104471_;
            }
            ClientChunkCache.this.f_104411_.m_104665_(p_104489_);
            return p_104489_;
        }

        boolean m_104500_(int p_104501_, int p_104502_) {
            return Math.abs(p_104501_ - this.f_104469_) <= this.f_104467_ && Math.abs(p_104502_ - this.f_104470_) <= this.f_104467_;
        }

        @Nullable
        protected LevelChunk m_104479_(int p_104480_) {
            return this.f_104466_.get(p_104480_);
        }

        private void m_171622_(String p_171623_) {
            try (FileOutputStream $$1 = new FileOutputStream(p_171623_);){
                int $$2 = ClientChunkCache.this.f_104410_.f_104467_;
                for (int $$3 = this.f_104470_ - $$2; $$3 <= this.f_104470_ + $$2; ++$$3) {
                    for (int $$4 = this.f_104469_ - $$2; $$4 <= this.f_104469_ + $$2; ++$$4) {
                        LevelChunk $$5 = ClientChunkCache.this.f_104410_.f_104466_.get(ClientChunkCache.this.f_104410_.m_104481_($$4, $$3));
                        if ($$5 == null) continue;
                        ChunkPos $$6 = $$5.m_7697_();
                        $$1.write(($$6.f_45578_ + "\t" + $$6.f_45579_ + "\t" + $$5.m_6430_() + "\n").getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
            catch (IOException $$7) {
                f_104407_.error("Failed to dump chunks to file {}", (Object)p_171623_, (Object)$$7);
            }
        }
    }
}

