/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 */
package net.minecraft.util;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;

public class DirectoryLock
implements AutoCloseable {
    public static final String f_144627_ = "session.lock";
    private final FileChannel f_13632_;
    private final FileLock f_13633_;
    private static final ByteBuffer f_13634_;

    public static DirectoryLock m_13640_(Path p_13641_) throws IOException {
        Path $$1 = p_13641_.resolve(f_144627_);
        if (!Files.isDirectory(p_13641_, new LinkOption[0])) {
            Files.createDirectories(p_13641_, new FileAttribute[0]);
        }
        FileChannel $$2 = FileChannel.open($$1, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        try {
            $$2.write(f_13634_.duplicate());
            $$2.force(true);
            FileLock $$3 = $$2.tryLock();
            if ($$3 == null) {
                throw LockException.m_13648_($$1);
            }
            return new DirectoryLock($$2, $$3);
        }
        catch (IOException $$4) {
            try {
                $$2.close();
            }
            catch (IOException $$5) {
                $$4.addSuppressed($$5);
            }
            throw $$4;
        }
    }

    private DirectoryLock(FileChannel p_13637_, FileLock p_13638_) {
        this.f_13632_ = p_13637_;
        this.f_13633_ = p_13638_;
    }

    @Override
    public void close() throws IOException {
        try {
            if (this.f_13633_.isValid()) {
                this.f_13633_.release();
            }
        }
        finally {
            if (this.f_13632_.isOpen()) {
                this.f_13632_.close();
            }
        }
    }

    public boolean m_13639_() {
        return this.f_13633_.isValid();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static boolean m_13642_(Path p_13643_) throws IOException {
        Path $$1 = p_13643_.resolve(f_144627_);
        try (FileChannel $$2 = FileChannel.open($$1, StandardOpenOption.WRITE);){
            boolean bl;
            block15: {
                FileLock $$3 = $$2.tryLock();
                try {
                    boolean bl2 = bl = $$3 == null;
                    if ($$3 == null) break block15;
                }
                catch (Throwable throwable) {
                    if ($$3 != null) {
                        try {
                            $$3.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                $$3.close();
            }
            return bl;
        }
        catch (AccessDeniedException $$4) {
            return true;
        }
        catch (NoSuchFileException $$5) {
            return false;
        }
    }

    static {
        byte[] $$0 = "\u2603".getBytes(Charsets.UTF_8);
        f_13634_ = ByteBuffer.allocateDirect($$0.length);
        f_13634_.put($$0);
        f_13634_.flip();
    }

    public static class LockException
    extends IOException {
        private LockException(Path p_13646_, String p_13647_) {
            super(p_13646_.toAbsolutePath() + ": " + p_13647_);
        }

        public static LockException m_13648_(Path p_13649_) {
            return new LockException(p_13649_, "already locked (possibly by other Minecraft instance?)");
        }
    }
}

