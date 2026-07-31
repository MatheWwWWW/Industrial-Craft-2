/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.RegionBitmap;
import net.minecraft.world.level.chunk.storage.RegionFileVersion;
import org.slf4j.Logger;

public class RegionFile
implements AutoCloseable {
    private static final Logger f_63619_ = LogUtils.getLogger();
    private static final int f_156605_ = 4096;
    @VisibleForTesting
    protected static final int f_156604_ = 1024;
    private static final int f_156606_ = 5;
    private static final int f_156607_ = 0;
    private static final ByteBuffer f_63620_ = ByteBuffer.allocateDirect(1);
    private static final String f_156608_ = ".mcc";
    private static final int f_156609_ = 128;
    private static final int f_156610_ = 256;
    private static final int f_156611_ = 0;
    private final FileChannel f_63621_;
    private final Path f_63622_;
    final RegionFileVersion f_63623_;
    private final ByteBuffer f_63624_ = ByteBuffer.allocateDirect(8192);
    private final IntBuffer f_63625_;
    private final IntBuffer f_63626_;
    @VisibleForTesting
    protected final RegionBitmap f_63618_ = new RegionBitmap();

    public RegionFile(Path p_196950_, Path p_196951_, boolean p_196952_) throws IOException {
        this(p_196950_, p_196951_, RegionFileVersion.f_63744_, p_196952_);
    }

    public RegionFile(Path p_63633_, Path p_63634_, RegionFileVersion p_63635_, boolean p_63636_) throws IOException {
        this.f_63623_ = p_63635_;
        if (!Files.isDirectory(p_63634_, new LinkOption[0])) {
            throw new IllegalArgumentException("Expected directory, got " + p_63634_.toAbsolutePath());
        }
        this.f_63622_ = p_63634_;
        this.f_63625_ = this.f_63624_.asIntBuffer();
        this.f_63625_.limit(1024);
        this.f_63624_.position(4096);
        this.f_63626_ = this.f_63624_.asIntBuffer();
        this.f_63621_ = p_63636_ ? FileChannel.open(p_63633_, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC) : FileChannel.open(p_63633_, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        this.f_63618_.m_63612_(0, 2);
        this.f_63624_.position(0);
        int $$4 = this.f_63621_.read(this.f_63624_, 0L);
        if ($$4 != -1) {
            if ($$4 != 8192) {
                f_63619_.warn("Region file {} has truncated header: {}", (Object)p_63633_, (Object)$$4);
            }
            long $$5 = Files.size(p_63633_);
            for (int $$6 = 0; $$6 < 1024; ++$$6) {
                int $$7 = this.f_63625_.get($$6);
                if ($$7 == 0) continue;
                int $$8 = RegionFile.m_63671_($$7);
                int $$9 = RegionFile.m_63640_($$7);
                if ($$8 < 2) {
                    f_63619_.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", new Object[]{p_63633_, $$6, $$8});
                    this.f_63625_.put($$6, 0);
                    continue;
                }
                if ($$9 == 0) {
                    f_63619_.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", (Object)p_63633_, (Object)$$6);
                    this.f_63625_.put($$6, 0);
                    continue;
                }
                if ((long)$$8 * 4096L > $$5) {
                    f_63619_.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", new Object[]{p_63633_, $$6, $$8});
                    this.f_63625_.put($$6, 0);
                    continue;
                }
                this.f_63618_.m_63612_($$8, $$9);
            }
        }
    }

    private Path m_63684_(ChunkPos p_63685_) {
        String $$1 = "c." + p_63685_.f_45578_ + "." + p_63685_.f_45579_ + f_156608_;
        return this.f_63622_.resolve($$1);
    }

    @Nullable
    public synchronized DataInputStream m_63645_(ChunkPos p_63646_) throws IOException {
        int $$1 = this.m_63686_(p_63646_);
        if ($$1 == 0) {
            return null;
        }
        int $$2 = RegionFile.m_63671_($$1);
        int $$3 = RegionFile.m_63640_($$1);
        int $$4 = $$3 * 4096;
        ByteBuffer $$5 = ByteBuffer.allocate($$4);
        this.f_63621_.read($$5, $$2 * 4096);
        $$5.flip();
        if ($$5.remaining() < 5) {
            f_63619_.error("Chunk {} header is truncated: expected {} but read {}", new Object[]{p_63646_, $$4, $$5.remaining()});
            return null;
        }
        int $$6 = $$5.getInt();
        byte $$7 = $$5.get();
        if ($$6 == 0) {
            f_63619_.warn("Chunk {} is allocated, but stream is missing", (Object)p_63646_);
            return null;
        }
        int $$8 = $$6 - 1;
        if (RegionFile.m_63638_($$7)) {
            if ($$8 != 0) {
                f_63619_.warn("Chunk has both internal and external streams");
            }
            return this.m_63647_(p_63646_, RegionFile.m_63669_($$7));
        }
        if ($$8 > $$5.remaining()) {
            f_63619_.error("Chunk {} stream is truncated: expected {} but read {}", new Object[]{p_63646_, $$8, $$5.remaining()});
            return null;
        }
        if ($$8 < 0) {
            f_63619_.error("Declared size {} of chunk {} is negative", (Object)$$6, (Object)p_63646_);
            return null;
        }
        return this.m_63650_(p_63646_, $$7, RegionFile.m_63659_($$5, $$8));
    }

    private static int m_156612_() {
        return (int)(Util.m_137574_() / 1000L);
    }

    private static boolean m_63638_(byte p_63639_) {
        return (p_63639_ & 0x80) != 0;
    }

    private static byte m_63669_(byte p_63670_) {
        return (byte)(p_63670_ & 0xFFFFFF7F);
    }

    @Nullable
    private DataInputStream m_63650_(ChunkPos p_63651_, byte p_63652_, InputStream p_63653_) throws IOException {
        RegionFileVersion $$3 = RegionFileVersion.m_63756_(p_63652_);
        if ($$3 == null) {
            f_63619_.error("Chunk {} has invalid chunk stream version {}", (Object)p_63651_, (Object)p_63652_);
            return null;
        }
        return new DataInputStream($$3.m_63760_(p_63653_));
    }

    @Nullable
    private DataInputStream m_63647_(ChunkPos p_63648_, byte p_63649_) throws IOException {
        Path $$2 = this.m_63684_(p_63648_);
        if (!Files.isRegularFile($$2, new LinkOption[0])) {
            f_63619_.error("External chunk path {} is not file", (Object)$$2);
            return null;
        }
        return this.m_63650_(p_63648_, p_63649_, Files.newInputStream($$2, new OpenOption[0]));
    }

    private static ByteArrayInputStream m_63659_(ByteBuffer p_63660_, int p_63661_) {
        return new ByteArrayInputStream(p_63660_.array(), p_63660_.position(), p_63661_);
    }

    private int m_63642_(int p_63643_, int p_63644_) {
        return p_63643_ << 8 | p_63644_;
    }

    private static int m_63640_(int p_63641_) {
        return p_63641_ & 0xFF;
    }

    private static int m_63671_(int p_63672_) {
        return p_63672_ >> 8 & 0xFFFFFF;
    }

    private static int m_63676_(int p_63677_) {
        return (p_63677_ + 4096 - 1) / 4096;
    }

    public boolean m_63673_(ChunkPos p_63674_) {
        int $$1 = this.m_63686_(p_63674_);
        if ($$1 == 0) {
            return false;
        }
        int $$2 = RegionFile.m_63671_($$1);
        int $$3 = RegionFile.m_63640_($$1);
        ByteBuffer $$4 = ByteBuffer.allocate(5);
        try {
            this.f_63621_.read($$4, $$2 * 4096);
            $$4.flip();
            if ($$4.remaining() != 5) {
                return false;
            }
            int $$5 = $$4.getInt();
            byte $$6 = $$4.get();
            if (RegionFile.m_63638_($$6)) {
                if (!RegionFileVersion.m_63764_(RegionFile.m_63669_($$6))) {
                    return false;
                }
                if (!Files.isRegularFile(this.m_63684_(p_63674_), new LinkOption[0])) {
                    return false;
                }
            } else {
                if (!RegionFileVersion.m_63764_($$6)) {
                    return false;
                }
                if ($$5 == 0) {
                    return false;
                }
                int $$7 = $$5 - 1;
                if ($$7 < 0 || $$7 > 4096 * $$3) {
                    return false;
                }
            }
        }
        catch (IOException $$8) {
            return false;
        }
        return true;
    }

    public DataOutputStream m_63678_(ChunkPos p_63679_) throws IOException {
        return new DataOutputStream(this.f_63623_.m_63762_(new ChunkBuffer(p_63679_)));
    }

    public void m_63637_() throws IOException {
        this.f_63621_.force(true);
    }

    public void m_156613_(ChunkPos p_156614_) throws IOException {
        int $$1 = RegionFile.m_63688_(p_156614_);
        int $$2 = this.f_63625_.get($$1);
        if ($$2 == 0) {
            return;
        }
        this.f_63625_.put($$1, 0);
        this.f_63626_.put($$1, RegionFile.m_156612_());
        this.m_63675_();
        Files.deleteIfExists(this.m_63684_(p_156614_));
        this.f_63618_.m_63615_(RegionFile.m_63671_($$2), RegionFile.m_63640_($$2));
    }

    protected synchronized void m_63654_(ChunkPos p_63655_, ByteBuffer p_63656_) throws IOException {
        CommitOp $$13;
        int $$12;
        int $$2 = RegionFile.m_63688_(p_63655_);
        int $$3 = this.f_63625_.get($$2);
        int $$4 = RegionFile.m_63671_($$3);
        int $$5 = RegionFile.m_63640_($$3);
        int $$6 = p_63656_.remaining();
        int $$7 = RegionFile.m_63676_($$6);
        if ($$7 >= 256) {
            Path $$8 = this.m_63684_(p_63655_);
            f_63619_.warn("Saving oversized chunk {} ({} bytes} to external file {}", new Object[]{p_63655_, $$6, $$8});
            $$7 = 1;
            int $$9 = this.f_63618_.m_63610_($$7);
            CommitOp $$10 = this.m_63662_($$8, p_63656_);
            ByteBuffer $$11 = this.m_63668_();
            this.f_63621_.write($$11, $$9 * 4096);
        } else {
            $$12 = this.f_63618_.m_63610_($$7);
            $$13 = () -> Files.deleteIfExists(this.m_63684_(p_63655_));
            this.f_63621_.write(p_63656_, $$12 * 4096);
        }
        this.f_63625_.put($$2, this.m_63642_($$12, $$7));
        this.f_63626_.put($$2, RegionFile.m_156612_());
        this.m_63675_();
        $$13.m_63698_();
        if ($$4 != 0) {
            this.f_63618_.m_63615_($$4, $$5);
        }
    }

    private ByteBuffer m_63668_() {
        ByteBuffer $$0 = ByteBuffer.allocate(5);
        $$0.putInt(1);
        $$0.put((byte)(this.f_63623_.m_63755_() | 0x80));
        $$0.flip();
        return $$0;
    }

    private CommitOp m_63662_(Path p_63663_, ByteBuffer p_63664_) throws IOException {
        Path $$2 = Files.createTempFile(this.f_63622_, "tmp", null, new FileAttribute[0]);
        try (FileChannel $$3 = FileChannel.open($$2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);){
            p_63664_.position(5);
            $$3.write(p_63664_);
        }
        return () -> Files.move($$2, p_63663_, StandardCopyOption.REPLACE_EXISTING);
    }

    private void m_63675_() throws IOException {
        this.f_63624_.position(0);
        this.f_63621_.write(this.f_63624_, 0L);
    }

    private int m_63686_(ChunkPos p_63687_) {
        return this.f_63625_.get(RegionFile.m_63688_(p_63687_));
    }

    public boolean m_63682_(ChunkPos p_63683_) {
        return this.m_63686_(p_63683_) != 0;
    }

    private static int m_63688_(ChunkPos p_63689_) {
        return p_63689_.m_45613_() + p_63689_.m_45614_() * 32;
    }

    @Override
    public void close() throws IOException {
        try {
            this.m_63681_();
        }
        finally {
            try {
                this.f_63621_.force(true);
            }
            finally {
                this.f_63621_.close();
            }
        }
    }

    private void m_63681_() throws IOException {
        int $$1;
        int $$0 = (int)this.f_63621_.size();
        if ($$0 != ($$1 = RegionFile.m_63676_($$0) * 4096)) {
            ByteBuffer $$2 = f_63620_.duplicate();
            $$2.position(0);
            this.f_63621_.write($$2, $$1 - 1);
        }
    }

    class ChunkBuffer
    extends ByteArrayOutputStream {
        private final ChunkPos f_63693_;

        public ChunkBuffer(ChunkPos p_63696_) {
            super(8096);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(0);
            super.write(RegionFile.this.f_63623_.m_63755_());
            this.f_63693_ = p_63696_;
        }

        @Override
        public void close() throws IOException {
            ByteBuffer $$0 = ByteBuffer.wrap(this.buf, 0, this.count);
            $$0.putInt(0, this.count - 5 + 1);
            RegionFile.this.m_63654_(this.f_63693_, $$0);
        }
    }

    static interface CommitOp {
        public void m_63698_() throws IOException;
    }
}

