/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.slf4j.Logger;

public class VanillaPackResources
implements PackResources {
    @Nullable
    public static Path f_10312_;
    private static final Logger f_10315_;
    public static Class<?> f_10313_;
    private static final Map<PackType, Path> f_182296_;
    public final PackMetadataSection f_143759_;
    public final Set<String> f_10314_;

    private static Path m_182297_(URI p_182298_) throws IOException {
        try {
            return Paths.get(p_182298_);
        }
        catch (FileSystemNotFoundException fileSystemNotFoundException) {
        }
        catch (Throwable $$1) {
            f_10315_.warn("Unable to get path for: {}", (Object)p_182298_, (Object)$$1);
        }
        try {
            FileSystems.newFileSystem(p_182298_, Collections.emptyMap());
        }
        catch (FileSystemAlreadyExistsException fileSystemAlreadyExistsException) {
            // empty catch block
        }
        return Paths.get(p_182298_);
    }

    public VanillaPackResources(PackMetadataSection p_143761_, String ... p_143762_) {
        this.f_143759_ = p_143761_;
        this.f_10314_ = ImmutableSet.copyOf((Object[])p_143762_);
    }

    @Override
    public InputStream m_5542_(String p_10358_) throws IOException {
        Path $$1;
        if (p_10358_.contains("/") || p_10358_.contains("\\")) {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
        if (f_10312_ != null && Files.exists($$1 = f_10312_.resolve(p_10358_), new LinkOption[0])) {
            return Files.newInputStream($$1, new OpenOption[0]);
        }
        return this.m_5539_(p_10358_);
    }

    @Override
    public InputStream m_8031_(PackType p_10330_, ResourceLocation p_10331_) throws IOException {
        InputStream $$2 = this.m_8033_(p_10330_, p_10331_);
        if ($$2 != null) {
            return $$2;
        }
        throw new FileNotFoundException(p_10331_.m_135815_());
    }

    @Override
    public Collection<ResourceLocation> m_214146_(PackType p_215346_, String p_215347_, String p_215348_, Predicate<ResourceLocation> p_215349_) {
        HashSet $$4 = Sets.newHashSet();
        if (f_10312_ != null) {
            try {
                VanillaPackResources.m_215357_($$4, p_215347_, f_10312_.resolve(p_215346_.m_10305_()), p_215348_, p_215349_);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (p_215346_ == PackType.CLIENT_RESOURCES) {
                Enumeration<URL> $$5 = null;
                try {
                    $$5 = f_10313_.getClassLoader().getResources(p_215346_.m_10305_() + "/");
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                while ($$5 != null && $$5.hasMoreElements()) {
                    try {
                        URI $$6 = $$5.nextElement().toURI();
                        if (!"file".equals($$6.getScheme())) continue;
                        VanillaPackResources.m_215357_($$4, p_215347_, Paths.get($$6), p_215348_, p_215349_);
                    }
                    catch (IOException | URISyntaxException exception) {}
                }
            }
        }
        try {
            Path $$7 = f_182296_.get((Object)p_215346_);
            if ($$7 != null) {
                VanillaPackResources.m_215357_($$4, p_215347_, $$7, p_215348_, p_215349_);
            } else {
                f_10315_.error("Can't access assets root for type: {}", (Object)p_215346_);
            }
        }
        catch (FileNotFoundException | NoSuchFileException $$7) {
        }
        catch (IOException $$8) {
            f_10315_.error("Couldn't get a list of all vanilla resources", (Throwable)$$8);
        }
        return $$4;
    }

    private static void m_215357_(Collection<ResourceLocation> p_215358_, String p_215359_, Path p_215360_, String p_215361_, Predicate<ResourceLocation> p_215362_) throws IOException {
        Path $$5 = p_215360_.resolve(p_215359_);
        try (Stream<Path> $$6 = Files.walk($$5.resolve(p_215361_), new FileVisitOption[0]);){
            $$6.filter(p_215351_ -> !p_215351_.endsWith(".mcmeta") && Files.isRegularFile(p_215351_, new LinkOption[0])).mapMulti((p_215355_, p_215356_) -> {
                String $$4 = $$5.relativize((Path)p_215355_).toString().replaceAll("\\\\", "/");
                ResourceLocation $$5 = ResourceLocation.m_214293_(p_215359_, $$4);
                if ($$5 == null) {
                    Util.m_143785_(String.format(Locale.ROOT, "Invalid path in datapack: %s:%s, ignoring", p_215359_, $$4));
                } else {
                    p_215356_.accept($$5);
                }
            }).filter(p_215362_).forEach(p_215358_::add);
        }
    }

    @Nullable
    protected InputStream m_8033_(PackType p_10359_, ResourceLocation p_10360_) {
        Path $$3;
        String $$2 = VanillaPackResources.m_10362_(p_10359_, p_10360_);
        if (f_10312_ != null && Files.exists($$3 = f_10312_.resolve(p_10359_.m_10305_() + "/" + p_10360_.m_135827_() + "/" + p_10360_.m_135815_()), new LinkOption[0])) {
            try {
                return Files.newInputStream($$3, new OpenOption[0]);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        try {
            URL $$4 = VanillaPackResources.class.getResource($$2);
            if (VanillaPackResources.m_10335_($$2, $$4)) {
                return $$4.openStream();
            }
        }
        catch (IOException $$5) {
            return VanillaPackResources.class.getResourceAsStream($$2);
        }
        return null;
    }

    private static String m_10362_(PackType p_10363_, ResourceLocation p_10364_) {
        return "/" + p_10363_.m_10305_() + "/" + p_10364_.m_135827_() + "/" + p_10364_.m_135815_();
    }

    private static boolean m_10335_(String p_10336_, @Nullable URL p_10337_) throws IOException {
        return p_10337_ != null && (p_10337_.getProtocol().equals("jar") || FolderPackResources.m_10273_(new File(p_10337_.getFile()), p_10336_));
    }

    @Nullable
    protected InputStream m_5539_(String p_10334_) {
        return VanillaPackResources.class.getResourceAsStream("/" + p_10334_);
    }

    @Override
    public boolean m_7211_(PackType p_10355_, ResourceLocation p_10356_) {
        Path $$3;
        String $$2 = VanillaPackResources.m_10362_(p_10355_, p_10356_);
        if (f_10312_ != null && Files.exists($$3 = f_10312_.resolve(p_10355_.m_10305_() + "/" + p_10356_.m_135827_() + "/" + p_10356_.m_135815_()), new LinkOption[0])) {
            return true;
        }
        try {
            URL $$4 = VanillaPackResources.class.getResource($$2);
            return VanillaPackResources.m_10335_($$2, $$4);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public Set<String> m_5698_(PackType p_10322_) {
        return this.f_10314_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @Nullable
    public <T> T m_5550_(MetadataSectionSerializer<T> p_10333_) throws IOException {
        try (InputStream $$1 = this.m_5542_("pack.mcmeta");){
            T $$2;
            if ($$1 != null && ($$2 = AbstractPackResources.m_10214_(p_10333_, $$1)) != null) {
                T t = $$2;
                return t;
            }
        }
        catch (FileNotFoundException | RuntimeException exception) {
            // empty catch block
        }
        if (p_10333_ != PackMetadataSection.f_10366_) return null;
        return (T)this.f_143759_;
    }

    @Override
    public String m_8017_() {
        return "Default";
    }

    @Override
    public void close() {
    }

    public ResourceProvider m_215363_() {
        return p_215344_ -> Optional.of(new Resource(this.m_8017_(), () -> this.m_8031_(PackType.CLIENT_RESOURCES, p_215344_)));
    }

    static {
        f_10315_ = LogUtils.getLogger();
        f_182296_ = (Map)Util.m_137537_(() -> {
            Class<VanillaPackResources> clazz = VanillaPackResources.class;
            synchronized (VanillaPackResources.class) {
                ImmutableMap.Builder $$0 = ImmutableMap.builder();
                for (PackType $$1 : PackType.values()) {
                    String $$2 = "/" + $$1.m_10305_() + "/.mcassetsroot";
                    URL $$3 = VanillaPackResources.class.getResource($$2);
                    if ($$3 == null) {
                        f_10315_.error("File {} does not exist in classpath", (Object)$$2);
                        continue;
                    }
                    try {
                        URI $$4 = $$3.toURI();
                        String $$5 = $$4.getScheme();
                        if (!"jar".equals($$5) && !"file".equals($$5)) {
                            f_10315_.warn("Assets URL '{}' uses unexpected schema", (Object)$$4);
                        }
                        Path $$6 = VanillaPackResources.m_182297_($$4);
                        $$0.put((Object)$$1, (Object)$$6.getParent());
                    }
                    catch (Exception $$7) {
                        f_10315_.error("Couldn't resolve path to vanilla assets", (Throwable)$$7);
                    }
                }
                // ** MonitorExit[var0] (shouldn't be in output)
                return $$0.build();
            }
        });
    }
}

