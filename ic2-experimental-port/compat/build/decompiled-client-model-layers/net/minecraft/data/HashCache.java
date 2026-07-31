/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.HashCode
 *  com.google.common.hash.Hashing
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.slf4j.Logger
 */
package net.minecraft.data;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.WorldVersion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;

public class HashCache {
    static final Logger f_123926_ = LogUtils.getLogger();
    private static final String f_236078_ = "// ";
    private final Path f_236079_;
    private final Path f_236080_;
    private final String f_236081_;
    private final Map<DataProvider, ProviderCache> f_236082_;
    private final Map<DataProvider, CacheUpdater> f_236083_ = new HashMap<DataProvider, CacheUpdater>();
    private final Set<Path> f_236084_ = new HashSet<Path>();
    private final int f_236085_;

    private Path m_236109_(DataProvider p_236110_) {
        return this.f_236080_.resolve(Hashing.sha1().hashString((CharSequence)p_236110_.m_6055_(), StandardCharsets.UTF_8).toString());
    }

    public HashCache(Path p_236087_, List<DataProvider> p_236088_, WorldVersion p_236089_) throws IOException {
        this.f_236081_ = p_236089_.getName();
        this.f_236079_ = p_236087_;
        this.f_236080_ = p_236087_.resolve(".cache");
        Files.createDirectories(this.f_236080_, new FileAttribute[0]);
        HashMap<DataProvider, ProviderCache> $$3 = new HashMap<DataProvider, ProviderCache>();
        int $$4 = 0;
        for (DataProvider $$5 : p_236088_) {
            Path $$6 = this.m_236109_($$5);
            this.f_236084_.add($$6);
            ProviderCache $$7 = HashCache.m_236092_(p_236087_, $$6);
            $$3.put($$5, $$7);
            $$4 += $$7.m_236133_();
        }
        this.f_236082_ = $$3;
        this.f_236085_ = $$4;
    }

    private static ProviderCache m_236092_(Path p_236093_, Path p_236094_) {
        if (Files.isReadable(p_236094_)) {
            try {
                return ProviderCache.m_236139_(p_236093_, p_236094_);
            }
            catch (Exception $$2) {
                f_123926_.warn("Failed to parse cache {}, discarding", (Object)p_236094_, (Object)$$2);
            }
        }
        return new ProviderCache("unknown");
    }

    public boolean m_236090_(DataProvider p_236091_) {
        ProviderCache $$1 = this.f_236082_.get(p_236091_);
        return $$1 == null || !$$1.f_236126_.equals(this.f_236081_);
    }

    public CachedOutput m_236107_(DataProvider p_236108_) {
        return this.f_236083_.computeIfAbsent(p_236108_, p_236112_ -> {
            ProviderCache $$1 = this.f_236082_.get(p_236112_);
            if ($$1 == null) {
                throw new IllegalStateException("Provider not registered: " + p_236112_.m_6055_());
            }
            CacheUpdater $$2 = new CacheUpdater(this.f_236081_, $$1);
            this.f_236082_.put((DataProvider)p_236112_, $$2.f_236114_);
            return $$2;
        });
    }

    public void m_123937_() throws IOException {
        MutableInt $$0 = new MutableInt();
        this.f_236083_.forEach((p_236100_, p_236101_) -> {
            Path $$3 = this.m_236109_((DataProvider)p_236100_);
            p_236101_.f_236114_.m_236142_(this.f_236079_, $$3, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()) + "\t" + p_236100_.m_6055_());
            $$0.add(p_236101_.f_236115_);
        });
        HashSet<Path> $$1 = new HashSet<Path>();
        this.f_236082_.values().forEach(p_236097_ -> $$1.addAll(p_236097_.f_236127_().keySet()));
        $$1.add(this.f_236079_.resolve("version.json"));
        MutableInt $$2 = new MutableInt();
        MutableInt $$3 = new MutableInt();
        try (Stream<Path> $$4 = Files.walk(this.f_236079_, new FileVisitOption[0]);){
            $$4.forEach(p_236106_ -> {
                if (Files.isDirectory(p_236106_, new LinkOption[0])) {
                    return;
                }
                if (this.f_236084_.contains(p_236106_)) {
                    return;
                }
                $$2.increment();
                if ($$1.contains(p_236106_)) {
                    return;
                }
                try {
                    Files.delete(p_236106_);
                }
                catch (IOException $$4) {
                    f_123926_.warn("Failed to delete file {}", p_236106_, (Object)$$4);
                }
                $$3.increment();
            });
        }
        f_123926_.info("Caching: total files: {}, old count: {}, new count: {}, removed stale: {}, written: {}", new Object[]{$$2, this.f_236085_, $$1.size(), $$3, $$0});
    }

    record ProviderCache(String f_236126_, Map<Path, HashCode> f_236127_) {
        ProviderCache(String p_236129_) {
            this(p_236129_, new HashMap<Path, HashCode>());
        }

        @Nullable
        public HashCode m_236134_(Path p_236135_) {
            return this.f_236127_.get(p_236135_);
        }

        public void m_236136_(Path p_236137_, HashCode p_236138_) {
            this.f_236127_.put(p_236137_, p_236138_);
        }

        public int m_236133_() {
            return this.f_236127_.size();
        }

        public static ProviderCache m_236139_(Path p_236140_, Path p_236141_) throws IOException {
            try (BufferedReader $$2 = Files.newBufferedReader(p_236141_, StandardCharsets.UTF_8);){
                String $$3 = $$2.readLine();
                if (!$$3.startsWith(HashCache.f_236078_)) {
                    throw new IllegalStateException("Missing cache file header");
                }
                String[] $$4 = $$3.substring(HashCache.f_236078_.length()).split("\t", 2);
                String $$5 = $$4[0];
                HashMap $$6 = new HashMap();
                $$2.lines().forEach(p_236149_ -> {
                    int $$3 = p_236149_.indexOf(32);
                    $$6.put(p_236140_.resolve(p_236149_.substring($$3 + 1)), HashCode.fromString((String)p_236149_.substring(0, $$3)));
                });
                ProviderCache providerCache = new ProviderCache($$5, Map.copyOf($$6));
                return providerCache;
            }
        }

        public void m_236142_(Path p_236143_, Path p_236144_, String p_236145_) {
            try (BufferedWriter $$3 = Files.newBufferedWriter(p_236144_, StandardCharsets.UTF_8, new OpenOption[0]);){
                $$3.write(HashCache.f_236078_);
                $$3.write(this.f_236126_);
                $$3.write(9);
                $$3.write(p_236145_);
                $$3.newLine();
                for (Map.Entry<Path, HashCode> $$4 : this.f_236127_.entrySet()) {
                    $$3.write($$4.getValue().toString());
                    $$3.write(32);
                    $$3.write(p_236143_.relativize($$4.getKey()).toString());
                    $$3.newLine();
                }
            }
            catch (IOException $$5) {
                f_123926_.warn("Unable write cachefile {}: {}", (Object)p_236144_, (Object)$$5);
            }
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ProviderCache.class, "version;data", "f_236126_", "f_236127_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ProviderCache.class, "version;data", "f_236126_", "f_236127_"}, this);
        }

        @Override
        public final boolean equals(Object p_236153_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ProviderCache.class, "version;data", "f_236126_", "f_236127_"}, this, p_236153_);
        }
    }

    static class CacheUpdater
    implements CachedOutput {
        private final ProviderCache f_236113_;
        final ProviderCache f_236114_;
        int f_236115_;

        CacheUpdater(String p_236117_, ProviderCache p_236118_) {
            this.f_236113_ = p_236118_;
            this.f_236114_ = new ProviderCache(p_236117_);
        }

        private boolean m_236119_(Path p_236120_, HashCode p_236121_) {
            return !Objects.equals(this.f_236113_.m_236134_(p_236120_), p_236121_) || !Files.exists(p_236120_, new LinkOption[0]);
        }

        @Override
        public void m_213871_(Path p_236123_, byte[] p_236124_, HashCode p_236125_) throws IOException {
            if (this.m_236119_(p_236123_, p_236125_)) {
                ++this.f_236115_;
                Files.createDirectories(p_236123_.getParent(), new FileAttribute[0]);
                Files.write(p_236123_, p_236124_, new OpenOption[0]);
            }
            this.f_236114_.m_236136_(p_236123_, p_236125_);
        }
    }
}

