/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class StructureTemplateManager {
    private static final Logger f_230340_ = LogUtils.getLogger();
    private static final String f_230341_ = "structures";
    private static final String f_230342_ = "gameteststructures";
    private static final String f_230343_ = ".nbt";
    private static final String f_230344_ = ".snbt";
    private final Map<ResourceLocation, Optional<StructureTemplate>> f_230345_ = Maps.newConcurrentMap();
    private final DataFixer f_230346_;
    private ResourceManager f_230347_;
    private final Path f_230348_;
    private final List<Source> f_230349_;

    public StructureTemplateManager(ResourceManager p_230352_, LevelStorageSource.LevelStorageAccess p_230353_, DataFixer p_230354_) {
        this.f_230347_ = p_230352_;
        this.f_230346_ = p_230354_;
        this.f_230348_ = p_230353_.m_78283_(LevelResource.f_78179_).normalize();
        ImmutableList.Builder $$3 = ImmutableList.builder();
        $$3.add((Object)new Source(this::m_230431_, this::m_230420_));
        if (SharedConstants.f_136183_) {
            $$3.add((Object)new Source(this::m_230429_, this::m_230415_));
        }
        $$3.add((Object)new Source(this::m_230427_, this::m_230406_));
        this.f_230349_ = $$3.build();
    }

    public StructureTemplate m_230359_(ResourceLocation p_230360_) {
        Optional<StructureTemplate> $$1 = this.m_230407_(p_230360_);
        if ($$1.isPresent()) {
            return $$1.get();
        }
        StructureTemplate $$2 = new StructureTemplate();
        this.f_230345_.put(p_230360_, Optional.of($$2));
        return $$2;
    }

    public Optional<StructureTemplate> m_230407_(ResourceLocation p_230408_) {
        return this.f_230345_.computeIfAbsent(p_230408_, this::m_230425_);
    }

    public Stream<ResourceLocation> m_230355_() {
        return this.f_230349_.stream().flatMap(p_230376_ -> p_230376_.f_230441_().get()).distinct();
    }

    private Optional<StructureTemplate> m_230425_(ResourceLocation p_230426_) {
        for (Source $$1 : this.f_230349_) {
            try {
                Optional<StructureTemplate> $$2 = $$1.f_230440_().apply(p_230426_);
                if (!$$2.isPresent()) continue;
                return $$2;
            }
            catch (Exception exception) {
            }
        }
        return Optional.empty();
    }

    public void m_230370_(ResourceManager p_230371_) {
        this.f_230347_ = p_230371_;
        this.f_230345_.clear();
    }

    private Optional<StructureTemplate> m_230427_(ResourceLocation p_230428_) {
        ResourceLocation $$1 = new ResourceLocation(p_230428_.m_135827_(), "structures/" + p_230428_.m_135815_() + f_230343_);
        return this.m_230372_(() -> this.f_230347_.m_215595_($$1), p_230366_ -> f_230340_.error("Couldn't load structure {}", (Object)p_230428_, p_230366_));
    }

    private Stream<ResourceLocation> m_230406_() {
        return this.f_230347_.m_214159_(f_230341_, p_230436_ -> true).keySet().stream().map(p_230434_ -> new ResourceLocation(p_230434_.m_135827_(), p_230434_.m_135815_().substring(f_230341_.length() + 1, p_230434_.m_135815_().length() - f_230343_.length())));
    }

    private Optional<StructureTemplate> m_230429_(ResourceLocation p_230430_) {
        return this.m_230367_(p_230430_, Paths.get(f_230342_, new String[0]));
    }

    private Stream<ResourceLocation> m_230415_() {
        return this.m_230394_(Paths.get(f_230342_, new String[0]), "minecraft", f_230344_);
    }

    private Optional<StructureTemplate> m_230431_(ResourceLocation p_230432_) {
        if (!Files.isDirectory(this.f_230348_, new LinkOption[0])) {
            return Optional.empty();
        }
        Path $$1 = StructureTemplateManager.m_230411_(this.f_230348_, p_230432_, f_230343_);
        return this.m_230372_(() -> new FileInputStream($$1.toFile()), p_230400_ -> f_230340_.error("Couldn't load structure from {}", (Object)$$1, p_230400_));
    }

    private Stream<ResourceLocation> m_230420_() {
        if (!Files.isDirectory(this.f_230348_, new LinkOption[0])) {
            return Stream.empty();
        }
        try {
            return Files.list(this.f_230348_).filter(p_230419_ -> Files.isDirectory(p_230419_, new LinkOption[0])).flatMap(p_230410_ -> this.m_230388_((Path)p_230410_));
        }
        catch (IOException $$0) {
            return Stream.empty();
        }
    }

    private Stream<ResourceLocation> m_230388_(Path p_230389_) {
        Path $$1 = p_230389_.resolve(f_230341_);
        return this.m_230394_($$1, p_230389_.getFileName().toString(), f_230343_);
    }

    private Stream<ResourceLocation> m_230394_(Path p_230395_, String p_230396_, String p_230397_) {
        if (!Files.isDirectory(p_230395_, new LinkOption[0])) {
            return Stream.empty();
        }
        int $$3 = p_230397_.length();
        Function<String, String> $$4 = p_230358_ -> p_230358_.substring(0, p_230358_.length() - $$3);
        try {
            return Files.walk(p_230395_, new FileVisitOption[0]).filter(p_230381_ -> p_230381_.toString().endsWith(p_230397_)).mapMulti((p_230386_, p_230387_) -> {
                try {
                    p_230387_.accept(new ResourceLocation(p_230396_, (String)$$4.apply(this.m_230401_(p_230395_, (Path)p_230386_))));
                }
                catch (ResourceLocationException $$5) {
                    f_230340_.error("Invalid location while listing pack contents", (Throwable)$$5);
                }
            });
        }
        catch (IOException $$5) {
            f_230340_.error("Failed to list folder contents", (Throwable)$$5);
            return Stream.empty();
        }
    }

    private String m_230401_(Path p_230402_, Path p_230403_) {
        return p_230402_.relativize(p_230403_).toString().replace(File.separator, "/");
    }

    private Optional<StructureTemplate> m_230367_(ResourceLocation p_230368_, Path p_230369_) {
        Optional<StructureTemplate> optional;
        block10: {
            if (!Files.isDirectory(p_230369_, new LinkOption[0])) {
                return Optional.empty();
            }
            Path $$2 = FileUtil.m_133736_(p_230369_, p_230368_.m_135815_(), f_230344_);
            BufferedReader $$3 = Files.newBufferedReader($$2);
            try {
                String $$4 = IOUtils.toString((Reader)$$3);
                optional = Optional.of(this.m_230404_(NbtUtils.m_178024_($$4)));
                if ($$3 == null) break block10;
            }
            catch (Throwable throwable) {
                try {
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
                catch (NoSuchFileException $$5) {
                    return Optional.empty();
                }
                catch (CommandSyntaxException | IOException $$6) {
                    f_230340_.error("Couldn't load structure from {}", (Object)$$2, (Object)$$6);
                    return Optional.empty();
                }
            }
            $$3.close();
        }
        return optional;
    }

    private Optional<StructureTemplate> m_230372_(InputStreamOpener p_230373_, Consumer<Throwable> p_230374_) {
        Optional<StructureTemplate> optional;
        block9: {
            InputStream $$2 = p_230373_.m_230439_();
            try {
                optional = Optional.of(this.m_230377_($$2));
                if ($$2 == null) break block9;
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
                catch (FileNotFoundException $$3) {
                    return Optional.empty();
                }
                catch (Throwable $$4) {
                    p_230374_.accept($$4);
                    return Optional.empty();
                }
            }
            $$2.close();
        }
        return optional;
    }

    private StructureTemplate m_230377_(InputStream p_230378_) throws IOException {
        CompoundTag $$1 = NbtIo.m_128939_(p_230378_);
        return this.m_230404_($$1);
    }

    public StructureTemplate m_230404_(CompoundTag p_230405_) {
        if (!p_230405_.m_128425_("DataVersion", 99)) {
            p_230405_.m_128405_("DataVersion", 500);
        }
        StructureTemplate $$1 = new StructureTemplate();
        $$1.m_74638_(NbtUtils.m_129213_(this.f_230346_, DataFixTypes.STRUCTURE, p_230405_, p_230405_.m_128451_("DataVersion")));
        return $$1;
    }

    public boolean m_230416_(ResourceLocation p_230417_) {
        Optional<StructureTemplate> $$1 = this.f_230345_.get(p_230417_);
        if (!$$1.isPresent()) {
            return false;
        }
        StructureTemplate $$2 = $$1.get();
        Path $$3 = StructureTemplateManager.m_230411_(this.f_230348_, p_230417_, f_230343_);
        Path $$4 = $$3.getParent();
        if ($$4 == null) {
            return false;
        }
        try {
            Files.createDirectories(Files.exists($$4, new LinkOption[0]) ? $$4.toRealPath(new LinkOption[0]) : $$4, new FileAttribute[0]);
        }
        catch (IOException $$5) {
            f_230340_.error("Failed to create parent directory: {}", (Object)$$4);
            return false;
        }
        CompoundTag $$6 = $$2.m_74618_(new CompoundTag());
        try (FileOutputStream $$7 = new FileOutputStream($$3.toFile());){
            NbtIo.m_128947_($$6, $$7);
        }
        catch (Throwable $$8) {
            return false;
        }
        return true;
    }

    public Path m_230361_(ResourceLocation p_230362_, String p_230363_) {
        return StructureTemplateManager.m_230390_(this.f_230348_, p_230362_, p_230363_);
    }

    public static Path m_230390_(Path p_230391_, ResourceLocation p_230392_, String p_230393_) {
        try {
            Path $$3 = p_230391_.resolve(p_230392_.m_135827_());
            Path $$4 = $$3.resolve(f_230341_);
            return FileUtil.m_133736_($$4, p_230392_.m_135815_(), p_230393_);
        }
        catch (InvalidPathException $$5) {
            throw new ResourceLocationException("Invalid resource path: " + p_230392_, $$5);
        }
    }

    private static Path m_230411_(Path p_230412_, ResourceLocation p_230413_, String p_230414_) {
        if (p_230413_.m_135815_().contains("//")) {
            throw new ResourceLocationException("Invalid resource path: " + p_230413_);
        }
        Path $$3 = StructureTemplateManager.m_230390_(p_230412_, p_230413_, p_230414_);
        if (!($$3.startsWith(p_230412_) && FileUtil.m_133728_($$3) && FileUtil.m_133734_($$3))) {
            throw new ResourceLocationException("Invalid resource path: " + $$3);
        }
        return $$3;
    }

    public void m_230421_(ResourceLocation p_230422_) {
        this.f_230345_.remove(p_230422_);
    }

    record Source(Function<ResourceLocation, Optional<StructureTemplate>> f_230440_, Supplier<Stream<ResourceLocation>> f_230441_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Source.class, "loader;lister", "f_230440_", "f_230441_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Source.class, "loader;lister", "f_230440_", "f_230441_"}, this);
        }

        @Override
        public final boolean equals(Object p_230448_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Source.class, "loader;lister", "f_230440_", "f_230441_"}, this, p_230448_);
        }
    }

    @FunctionalInterface
    static interface InputStreamOpener {
        public InputStream m_230439_() throws IOException;
    }
}

