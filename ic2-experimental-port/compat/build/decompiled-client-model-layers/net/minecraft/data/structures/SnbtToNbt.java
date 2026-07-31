/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.hash.HashCode
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data.structures;

import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class SnbtToNbt
implements DataProvider {
    @Nullable
    private static final Path f_176815_ = null;
    private static final Logger f_126443_ = LogUtils.getLogger();
    private final DataGenerator f_126444_;
    private final List<Filter> f_126445_ = Lists.newArrayList();

    public SnbtToNbt(DataGenerator p_126448_) {
        this.f_126444_ = p_126448_;
    }

    public SnbtToNbt m_126475_(Filter p_126476_) {
        this.f_126445_.add(p_126476_);
        return this;
    }

    private CompoundTag m_126460_(String p_126461_, CompoundTag p_126462_) {
        CompoundTag $$2 = p_126462_;
        for (Filter $$3 : this.f_126445_) {
            $$2 = $$3.m_6392_(p_126461_, $$2);
        }
        return $$2;
    }

    @Override
    public void m_213708_(CachedOutput p_236392_) throws IOException {
        Path $$1 = this.f_126444_.m_123916_();
        ArrayList $$2 = Lists.newArrayList();
        for (Path $$3 : this.f_126444_.m_123913_()) {
            Files.walk($$3, new FileVisitOption[0]).filter(p_126464_ -> p_126464_.toString().endsWith(".snbt")).forEach(p_126474_ -> $$2.add(CompletableFuture.supplyAsync(() -> this.m_126465_((Path)p_126474_, this.m_126468_($$3, (Path)p_126474_)), Util.m_183991_())));
        }
        boolean $$4 = false;
        for (CompletableFuture $$5 : $$2) {
            try {
                this.m_236393_(p_236392_, (TaskResult)$$5.get(), $$1);
            }
            catch (Exception $$6) {
                f_126443_.error("Failed to process structure", (Throwable)$$6);
                $$4 = true;
            }
        }
        if ($$4) {
            throw new IllegalStateException("Failed to convert all structures, aborting");
        }
    }

    @Override
    public String m_6055_() {
        return "SNBT -> NBT";
    }

    private String m_126468_(Path p_126469_, Path p_126470_) {
        String $$2 = p_126469_.relativize(p_126470_).toString().replaceAll("\\\\", "/");
        return $$2.substring(0, $$2.length() - ".snbt".length());
    }

    private TaskResult m_126465_(Path p_126466_, String p_126467_) {
        TaskResult taskResult;
        block10: {
            BufferedReader $$2 = Files.newBufferedReader(p_126466_);
            try {
                String $$10;
                String $$3 = IOUtils.toString((Reader)$$2);
                CompoundTag $$4 = this.m_126460_(p_126467_, NbtUtils.m_178024_($$3));
                ByteArrayOutputStream $$5 = new ByteArrayOutputStream();
                HashingOutputStream $$6 = new HashingOutputStream(Hashing.sha1(), (OutputStream)$$5);
                NbtIo.m_128947_($$4, (OutputStream)$$6);
                byte[] $$7 = $$5.toByteArray();
                HashCode $$8 = $$6.hash();
                if (f_176815_ != null) {
                    String $$9 = NbtUtils.m_178063_($$4);
                } else {
                    $$10 = null;
                }
                taskResult = new TaskResult(p_126467_, $$7, $$10, $$8);
                if ($$2 == null) break block10;
            }
            catch (Throwable throwable) {
                try {
                    if ($$2 != null) {
                        try {
                            $$2.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Throwable $$11) {
                    throw new StructureConversionException(p_126466_, $$11);
                }
            }
            $$2.close();
        }
        return taskResult;
    }

    private void m_236393_(CachedOutput p_236394_, TaskResult p_236395_, Path p_236396_) {
        if (p_236395_.f_126484_ != null) {
            Path $$3 = f_176815_.resolve(p_236395_.f_126482_ + ".snbt");
            try {
                NbtToSnbt.m_236377_(CachedOutput.f_236016_, $$3, p_236395_.f_126484_);
            }
            catch (IOException $$4) {
                f_126443_.error("Couldn't write structure SNBT {} at {}", new Object[]{p_236395_.f_126482_, $$3, $$4});
            }
        }
        Path $$5 = p_236396_.resolve(p_236395_.f_126482_ + ".nbt");
        try {
            p_236394_.m_213871_($$5, p_236395_.f_126483_, p_236395_.f_126485_);
        }
        catch (IOException $$6) {
            f_126443_.error("Couldn't write structure {} at {}", new Object[]{p_236395_.f_126482_, $$5, $$6});
        }
    }

    @FunctionalInterface
    public static interface Filter {
        public CompoundTag m_6392_(String var1, CompoundTag var2);
    }

    record TaskResult(String f_126482_, byte[] f_126483_, @Nullable String f_126484_, HashCode f_126485_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TaskResult.class, "name;payload;snbtPayload;hash", "f_126482_", "f_126483_", "f_126484_", "f_126485_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TaskResult.class, "name;payload;snbtPayload;hash", "f_126482_", "f_126483_", "f_126484_", "f_126485_"}, this);
        }

        @Override
        public final boolean equals(Object p_236407_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TaskResult.class, "name;payload;snbtPayload;hash", "f_126482_", "f_126483_", "f_126484_", "f_126485_"}, this, p_236407_);
        }
    }

    static class StructureConversionException
    extends RuntimeException {
        public StructureConversionException(Path p_176820_, Throwable p_176821_) {
            super(p_176820_.toAbsolutePath().toString(), p_176821_);
        }
    }
}

