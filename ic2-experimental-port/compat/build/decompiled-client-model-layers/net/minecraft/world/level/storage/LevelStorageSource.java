/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.nbt.visitors.SkipFields;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.DirectoryLock;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.LevelVersion;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;

public class LevelStorageSource {
    static final Logger f_78191_ = LogUtils.getLogger();
    static final DateTimeFormatter f_78192_ = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();
    private static final ImmutableList<String> f_78193_ = ImmutableList.of((Object)"RandomSeed", (Object)"generatorName", (Object)"generatorOptions", (Object)"generatorVersion", (Object)"legacy_custom_options", (Object)"MapFeatures", (Object)"BonusChest");
    private static final String f_202311_ = "Data";
    final Path f_78194_;
    private final Path f_78195_;
    final DataFixer f_78196_;

    public LevelStorageSource(Path p_78199_, Path p_78200_, DataFixer p_78201_) {
        this.f_78196_ = p_78201_;
        try {
            Files.createDirectories(Files.exists(p_78199_, new LinkOption[0]) ? p_78199_.toRealPath(new LinkOption[0]) : p_78199_, new FileAttribute[0]);
        }
        catch (IOException $$3) {
            throw new RuntimeException($$3);
        }
        this.f_78194_ = p_78199_;
        this.f_78195_ = p_78200_;
    }

    public static LevelStorageSource m_78242_(Path p_78243_) {
        return new LevelStorageSource(p_78243_, p_78243_.resolve("../backups"), DataFixers.m_14512_());
    }

    private static <T> Pair<WorldGenSettings, Lifecycle> m_78204_(Dynamic<T> p_78205_, DataFixer p_78206_, int p_78207_) {
        Dynamic $$3 = p_78205_.get("WorldGenSettings").orElseEmptyMap();
        for (String $$4 : f_78193_) {
            Optional $$5 = p_78205_.get($$4).result();
            if (!$$5.isPresent()) continue;
            $$3 = $$3.set($$4, (Dynamic)$$5.get());
        }
        Dynamic $$6 = p_78206_.update(References.f_16795_, $$3, p_78207_, SharedConstants.m_183709_().getWorldVersion());
        DataResult $$7 = WorldGenSettings.f_64600_.parse($$6);
        return Pair.of((Object)$$7.resultOrPartial(Util.m_137489_("WorldGenSettings: ", arg_0 -> ((Logger)f_78191_).error(arg_0))).orElseGet(() -> {
            RegistryAccess $$1 = RegistryAccess.m_206154_($$6);
            return WorldPresets.m_226449_($$1);
        }), (Object)$$7.lifecycle());
    }

    private static DataPackConfig m_78202_(Dynamic<?> p_78203_) {
        return DataPackConfig.f_45843_.parse(p_78203_).resultOrPartial(arg_0 -> ((Logger)f_78191_).error(arg_0)).orElse(DataPackConfig.f_45842_);
    }

    public String m_164909_() {
        return "Anvil";
    }

    public LevelCandidates m_230833_() throws LevelStorageException {
        if (!Files.isDirectory(this.f_78194_, new LinkOption[0])) {
            throw new LevelStorageException(Component.m_237115_("selectWorld.load_folder_access"));
        }
        try {
            List<LevelDirectory> $$0 = Files.list(this.f_78194_).filter(p_230839_ -> Files.isDirectory(p_230839_, new LinkOption[0])).map(LevelDirectory::new).filter(p_230835_ -> Files.isRegularFile(p_230835_.m_230858_(), new LinkOption[0]) || Files.isRegularFile(p_230835_.m_230859_(), new LinkOption[0])).toList();
            return new LevelCandidates($$0);
        }
        catch (IOException $$1) {
            throw new LevelStorageException(Component.m_237115_("selectWorld.load_folder_access"));
        }
    }

    public CompletableFuture<List<LevelSummary>> m_230813_(LevelCandidates p_230814_) {
        ArrayList<CompletableFuture<LevelSummary>> $$1 = new ArrayList<CompletableFuture<LevelSummary>>(p_230814_.f_230840_.size());
        for (LevelDirectory $$2 : p_230814_.f_230840_) {
            $$1.add(CompletableFuture.supplyAsync(() -> {
                try {
                    boolean $$1 = DirectoryLock.m_13642_($$2.f_230850_());
                }
                catch (Exception $$2) {
                    f_78191_.warn("Failed to read {} lock", (Object)$$2.f_230850_(), (Object)$$2);
                    return null;
                }
                try {
                    void $$3;
                    LevelSummary $$4 = this.m_230817_($$2, this.m_230820_($$2, (boolean)$$3));
                    if ($$4 != null) {
                        return $$4;
                    }
                }
                catch (OutOfMemoryError $$5) {
                    MemoryReserve.m_182328_();
                    System.gc();
                    f_78191_.error(LogUtils.FATAL_MARKER, "Ran out of memory trying to read summary of {}", (Object)$$2.m_230853_());
                    throw $$5;
                }
                catch (StackOverflowError $$6) {
                    f_78191_.error(LogUtils.FATAL_MARKER, "Ran out of stack trying to read summary of {}. Assuming corruption; attempting to restore from from level.dat_old.", (Object)$$2.m_230853_());
                    Util.m_212229_($$2.m_230858_(), $$2.m_230859_(), $$2.m_230856_(LocalDateTime.now()), true);
                    throw $$6;
                }
                return null;
            }, Util.m_183991_()));
        }
        return Util.m_214684_($$1).thenApply(p_230832_ -> p_230832_.stream().filter(Objects::nonNull).sorted().toList());
    }

    private int m_78265_() {
        return 19133;
    }

    @Nullable
    <T> T m_230817_(LevelDirectory p_230818_, BiFunction<Path, DataFixer, T> p_230819_) {
        T $$3;
        if (!Files.exists(p_230818_.f_230850_(), new LinkOption[0])) {
            return null;
        }
        Path $$2 = p_230818_.m_230858_();
        if (Files.exists($$2, new LinkOption[0]) && ($$3 = p_230819_.apply($$2, this.f_78196_)) != null) {
            return $$3;
        }
        $$2 = p_230818_.m_230859_();
        if (Files.exists($$2, new LinkOption[0])) {
            return p_230819_.apply($$2, this.f_78196_);
        }
        return null;
    }

    @Nullable
    private static DataPackConfig m_230828_(Path p_230829_, DataFixer p_230830_) {
        try {
            Tag $$2 = LevelStorageSource.m_230836_(p_230829_);
            if ($$2 instanceof CompoundTag) {
                CompoundTag $$3 = (CompoundTag)$$2;
                CompoundTag $$4 = $$3.m_128469_(f_202311_);
                int $$5 = $$4.m_128425_("DataVersion", 99) ? $$4.m_128451_("DataVersion") : -1;
                Dynamic $$6 = p_230830_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)$$4), $$5, SharedConstants.m_183709_().getWorldVersion());
                return $$6.get("DataPacks").result().map(LevelStorageSource::m_78202_).orElse(DataPackConfig.f_45842_);
            }
        }
        catch (Exception $$7) {
            f_78191_.error("Exception reading {}", (Object)p_230829_, (Object)$$7);
        }
        return null;
    }

    static BiFunction<Path, DataFixer, PrimaryLevelData> m_211737_(DynamicOps<Tag> p_211738_, DataPackConfig p_211739_, Lifecycle p_211740_) {
        return (p_230811_, p_230812_) -> {
            try {
                CompoundTag $$5 = NbtIo.m_128937_(p_230811_.toFile());
                CompoundTag $$6 = $$5.m_128469_(f_202311_);
                CompoundTag $$7 = $$6.m_128425_("Player", 10) ? $$6.m_128469_("Player") : null;
                $$6.m_128473_("Player");
                int $$8 = $$6.m_128425_("DataVersion", 99) ? $$6.m_128451_("DataVersion") : -1;
                Dynamic $$9 = p_230812_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic(p_211738_, (Object)$$6), $$8, SharedConstants.m_183709_().getWorldVersion());
                Pair<WorldGenSettings, Lifecycle> $$10 = LevelStorageSource.m_78204_($$9, p_230812_, $$8);
                LevelVersion $$11 = LevelVersion.m_78390_($$9);
                LevelSettings $$12 = LevelSettings.m_46924_($$9, p_211739_);
                Lifecycle $$13 = ((Lifecycle)$$10.getSecond()).add(p_211740_);
                return PrimaryLevelData.m_78530_((Dynamic<Tag>)$$9, p_230812_, $$8, $$7, $$12, $$11, (WorldGenSettings)$$10.getFirst(), $$13);
            }
            catch (Exception $$14) {
                f_78191_.error("Exception reading {}", p_230811_, (Object)$$14);
                return null;
            }
        };
    }

    BiFunction<Path, DataFixer, LevelSummary> m_230820_(LevelDirectory p_230821_, boolean p_230822_) {
        return (p_230826_, p_230827_) -> {
            try {
                Tag $$4 = LevelStorageSource.m_230836_(p_230826_);
                if ($$4 instanceof CompoundTag) {
                    CompoundTag $$5 = (CompoundTag)$$4;
                    CompoundTag $$6 = $$5.m_128469_(f_202311_);
                    int $$7 = $$6.m_128425_("DataVersion", 99) ? $$6.m_128451_("DataVersion") : -1;
                    Dynamic $$8 = p_230827_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)$$6), $$7, SharedConstants.m_183709_().getWorldVersion());
                    LevelVersion $$9 = LevelVersion.m_78390_($$8);
                    int $$10 = $$9.m_78389_();
                    if ($$10 == 19132 || $$10 == 19133) {
                        boolean $$11 = $$10 != this.m_78265_();
                        Path $$12 = p_230821_.m_230860_();
                        DataPackConfig $$13 = $$8.get("DataPacks").result().map(LevelStorageSource::m_78202_).orElse(DataPackConfig.f_45842_);
                        LevelSettings $$14 = LevelSettings.m_46924_($$8, $$13);
                        return new LevelSummary($$14, $$9, p_230821_.m_230853_(), $$11, p_230822_, $$12);
                    }
                } else {
                    f_78191_.warn("Invalid root tag in {}", p_230826_);
                }
                return null;
            }
            catch (Exception $$15) {
                f_78191_.error("Exception reading {}", p_230826_, (Object)$$15);
                return null;
            }
        };
    }

    @Nullable
    private static Tag m_230836_(Path p_230837_) throws IOException {
        SkipFields $$1 = new SkipFields(new FieldSelector(f_202311_, CompoundTag.f_128326_, "Player"), new FieldSelector(f_202311_, CompoundTag.f_128326_, "WorldGenSettings"));
        NbtIo.m_202487_(p_230837_.toFile(), $$1);
        return $$1.m_197713_();
    }

    public boolean m_78240_(String p_78241_) {
        try {
            Path $$1 = this.f_78194_.resolve(p_78241_);
            Files.createDirectory($$1, new FileAttribute[0]);
            Files.deleteIfExists($$1);
            return true;
        }
        catch (IOException $$2) {
            return false;
        }
    }

    public boolean m_78255_(String p_78256_) {
        return Files.isDirectory(this.f_78194_.resolve(p_78256_), new LinkOption[0]);
    }

    public Path m_78257_() {
        return this.f_78194_;
    }

    public Path m_78262_() {
        return this.f_78195_;
    }

    public LevelStorageAccess m_78260_(String p_78261_) throws IOException {
        return new LevelStorageAccess(p_78261_);
    }

    public record LevelCandidates(List<LevelDirectory> f_230840_) implements Iterable<LevelDirectory>
    {
        public boolean m_230843_() {
            return this.f_230840_.isEmpty();
        }

        @Override
        public Iterator<LevelDirectory> iterator() {
            return this.f_230840_.iterator();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LevelCandidates.class, "levels", "f_230840_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LevelCandidates.class, "levels", "f_230840_"}, this);
        }

        @Override
        public final boolean equals(Object p_230846_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LevelCandidates.class, "levels", "f_230840_"}, this, p_230846_);
        }
    }

    public record LevelDirectory(Path f_230850_) {
        public String m_230853_() {
            return this.f_230850_.getFileName().toString();
        }

        public Path m_230858_() {
            return this.m_230854_(LevelResource.f_78178_);
        }

        public Path m_230859_() {
            return this.m_230854_(LevelResource.f_230800_);
        }

        public Path m_230856_(LocalDateTime p_230857_) {
            return this.f_230850_.resolve(LevelResource.f_78178_.m_78187_() + "_corrupted_" + p_230857_.format(f_78192_));
        }

        public Path m_230860_() {
            return this.m_230854_(LevelResource.f_230801_);
        }

        public Path m_230861_() {
            return this.m_230854_(LevelResource.f_230802_);
        }

        public Path m_230854_(LevelResource p_230855_) {
            return this.f_230850_.resolve(p_230855_.m_78187_());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LevelDirectory.class, "path", "f_230850_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LevelDirectory.class, "path", "f_230850_"}, this);
        }

        @Override
        public final boolean equals(Object p_230863_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LevelDirectory.class, "path", "f_230850_"}, this, p_230863_);
        }
    }

    public class LevelStorageAccess
    implements AutoCloseable {
        final DirectoryLock f_78270_;
        final LevelDirectory f_230867_;
        private final String f_78272_;
        private final Map<LevelResource, Path> f_78273_ = Maps.newHashMap();

        public LevelStorageAccess(String p_78276_) throws IOException {
            this.f_78272_ = p_78276_;
            this.f_230867_ = new LevelDirectory(LevelStorageSource.this.f_78194_.resolve(p_78276_));
            this.f_78270_ = DirectoryLock.m_13640_(this.f_230867_.f_230850_());
        }

        public String m_78277_() {
            return this.f_78272_;
        }

        public Path m_78283_(LevelResource p_78284_) {
            return this.f_78273_.computeIfAbsent(p_78284_, this.f_230867_::m_230854_);
        }

        public Path m_197394_(ResourceKey<Level> p_197395_) {
            return DimensionType.m_196975_(p_197395_, this.f_230867_.f_230850_());
        }

        private void m_78313_() {
            if (!this.f_78270_.m_13639_()) {
                throw new IllegalStateException("Lock is no longer valid");
            }
        }

        public PlayerDataStorage m_78301_() {
            this.m_78313_();
            return new PlayerDataStorage(this, LevelStorageSource.this.f_78196_);
        }

        @Nullable
        public LevelSummary m_78308_() {
            this.m_78313_();
            return LevelStorageSource.this.m_230817_(this.f_230867_, LevelStorageSource.this.m_230820_(this.f_230867_, false));
        }

        @Nullable
        public WorldData m_211747_(DynamicOps<Tag> p_211748_, DataPackConfig p_211749_, Lifecycle p_211750_) {
            this.m_78313_();
            return LevelStorageSource.this.m_230817_(this.f_230867_, LevelStorageSource.m_211737_(p_211748_, p_211749_, p_211750_));
        }

        @Nullable
        public DataPackConfig m_78309_() {
            this.m_78313_();
            return LevelStorageSource.this.m_230817_(this.f_230867_, LevelStorageSource::m_230828_);
        }

        public void m_78287_(RegistryAccess p_78288_, WorldData p_78289_) {
            this.m_78290_(p_78288_, p_78289_, null);
        }

        public void m_78290_(RegistryAccess p_78291_, WorldData p_78292_, @Nullable CompoundTag p_78293_) {
            File $$3 = this.f_230867_.f_230850_().toFile();
            CompoundTag $$4 = p_78292_.m_6626_(p_78291_, p_78293_);
            CompoundTag $$5 = new CompoundTag();
            $$5.m_128365_(LevelStorageSource.f_202311_, $$4);
            try {
                File $$6 = File.createTempFile("level", ".dat", $$3);
                NbtIo.m_128944_($$5, $$6);
                File $$7 = this.f_230867_.m_230859_().toFile();
                File $$8 = this.f_230867_.m_230858_().toFile();
                Util.m_137462_($$8, $$6, $$7);
            }
            catch (Exception $$9) {
                f_78191_.error("Failed to save level {}", (Object)$$3, (Object)$$9);
            }
        }

        public Optional<Path> m_182514_() {
            if (!this.f_78270_.m_13639_()) {
                return Optional.empty();
            }
            return Optional.of(this.f_230867_.m_230860_());
        }

        public void m_78311_() throws IOException {
            this.m_78313_();
            final Path $$0 = this.f_230867_.m_230861_();
            f_78191_.info("Deleting level {}", (Object)this.f_78272_);
            for (int $$1 = 1; $$1 <= 5; ++$$1) {
                f_78191_.info("Attempt {}...", (Object)$$1);
                try {
                    Files.walkFileTree(this.f_230867_.f_230850_(), (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                        @Override
                        public FileVisitResult visitFile(Path p_78323_, BasicFileAttributes p_78324_) throws IOException {
                            if (!p_78323_.equals($$0)) {
                                f_78191_.debug("Deleting {}", (Object)p_78323_);
                                Files.delete(p_78323_);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path p_78320_, IOException p_78321_) throws IOException {
                            if (p_78321_ != null) {
                                throw p_78321_;
                            }
                            if (p_78320_.equals(LevelStorageAccess.this.f_230867_.f_230850_())) {
                                LevelStorageAccess.this.f_78270_.close();
                                Files.deleteIfExists($$0);
                            }
                            Files.delete(p_78320_);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public /* synthetic */ FileVisitResult postVisitDirectory(Object object, IOException iOException) throws IOException {
                            return this.postVisitDirectory((Path)object, iOException);
                        }

                        @Override
                        public /* synthetic */ FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                            return this.visitFile((Path)object, basicFileAttributes);
                        }
                    });
                    break;
                }
                catch (IOException $$2) {
                    if ($$1 < 5) {
                        f_78191_.warn("Failed to delete {}", (Object)this.f_230867_.f_230850_(), (Object)$$2);
                        try {
                            Thread.sleep(500L);
                        }
                        catch (InterruptedException interruptedException) {}
                        continue;
                    }
                    throw $$2;
                }
            }
        }

        public void m_78297_(String p_78298_) throws IOException {
            this.m_78313_();
            Path $$1 = this.f_230867_.m_230858_();
            if (Files.exists($$1, new LinkOption[0])) {
                CompoundTag $$2 = NbtIo.m_128937_($$1.toFile());
                CompoundTag $$3 = $$2.m_128469_(LevelStorageSource.f_202311_);
                $$3.m_128359_("LevelName", p_78298_);
                NbtIo.m_128944_($$2, $$1.toFile());
            }
        }

        public long m_78312_() throws IOException {
            this.m_78313_();
            String $$0 = LocalDateTime.now().format(f_78192_) + "_" + this.f_78272_;
            Path $$1 = LevelStorageSource.this.m_78262_();
            try {
                Files.createDirectories(Files.exists($$1, new LinkOption[0]) ? $$1.toRealPath(new LinkOption[0]) : $$1, new FileAttribute[0]);
            }
            catch (IOException $$2) {
                throw new RuntimeException($$2);
            }
            Path $$3 = $$1.resolve(FileUtil.m_133730_($$1, $$0, ".zip"));
            try (final ZipOutputStream $$4 = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream($$3, new OpenOption[0])));){
                final Path $$5 = Paths.get(this.f_78272_, new String[0]);
                Files.walkFileTree(this.f_230867_.f_230850_(), (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                    @Override
                    public FileVisitResult visitFile(Path p_78339_, BasicFileAttributes p_78340_) throws IOException {
                        if (p_78339_.endsWith("session.lock")) {
                            return FileVisitResult.CONTINUE;
                        }
                        String $$2 = $$5.resolve(LevelStorageAccess.this.f_230867_.f_230850_().relativize(p_78339_)).toString().replace('\\', '/');
                        ZipEntry $$3 = new ZipEntry($$2);
                        $$4.putNextEntry($$3);
                        com.google.common.io.Files.asByteSource((File)p_78339_.toFile()).copyTo((OutputStream)$$4);
                        $$4.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public /* synthetic */ FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                        return this.visitFile((Path)object, basicFileAttributes);
                    }
                });
            }
            return Files.size($$3);
        }

        @Override
        public void close() throws IOException {
            this.f_78270_.close();
        }
    }
}

