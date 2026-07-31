/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Ticker
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.util.concurrent.ListeningExecutorService
 *  com.google.common.util.concurrent.MoreExecutors
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft;

import com.google.common.base.Ticker;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CharPredicate;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeSource;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.block.state.properties.Property;
import org.slf4j.Logger;

public class Util {
    static final Logger f_137446_ = LogUtils.getLogger();
    private static final int f_183935_ = 255;
    private static final String f_183936_ = "max.bg.threads";
    private static final AtomicInteger f_137442_ = new AtomicInteger(1);
    private static final ExecutorService f_137443_ = Util.m_137477_("Bootstrap");
    private static final ExecutorService f_137444_ = Util.m_137477_("Main");
    private static final ExecutorService f_137445_ = Util.m_137586_();
    private static final DateTimeFormatter f_241646_ = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
    public static TimeSource.NanoTimeSource f_137440_ = System::nanoTime;
    public static final Ticker f_211544_ = new Ticker(){

        public long read() {
            return f_137440_.getAsLong();
        }
    };
    public static final UUID f_137441_ = new UUID(0L, 0L);
    public static final FileSystemProvider f_143778_ = FileSystemProvider.installedProviders().stream().filter(p_201865_ -> p_201865_.getScheme().equalsIgnoreCase("jar")).findFirst().orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
    private static Consumer<String> f_183937_ = p_201905_ -> {};

    public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> m_137448_() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T extends Comparable<T>> String m_137453_(Property<T> p_137454_, Object p_137455_) {
        return p_137454_.m_6940_((Comparable)p_137455_);
    }

    public static String m_137492_(String p_137493_, @Nullable ResourceLocation p_137494_) {
        if (p_137494_ == null) {
            return p_137493_ + ".unregistered_sadface";
        }
        return p_137493_ + "." + p_137494_.m_135827_() + "." + p_137494_.m_135815_().replace('/', '.');
    }

    public static long m_137550_() {
        return Util.m_137569_() / 1000000L;
    }

    public static long m_137569_() {
        return f_137440_.getAsLong();
    }

    public static long m_137574_() {
        return Instant.now().toEpochMilli();
    }

    public static String m_241986_() {
        return f_241646_.format(ZonedDateTime.now());
    }

    private static ExecutorService m_137477_(String p_137478_) {
        ForkJoinPool $$3;
        int $$1 = Mth.m_14045_(Runtime.getRuntime().availableProcessors() - 1, 1, Util.m_183993_());
        if ($$1 <= 0) {
            ListeningExecutorService $$2 = MoreExecutors.newDirectExecutorService();
        } else {
            $$3 = new ForkJoinPool($$1, p_201863_ -> {
                ForkJoinWorkerThread $$2 = new ForkJoinWorkerThread(p_201863_){

                    @Override
                    protected void onTermination(Throwable p_211561_) {
                        if (p_211561_ != null) {
                            f_137446_.warn("{} died", (Object)this.getName(), (Object)p_211561_);
                        } else {
                            f_137446_.debug("{} shutdown", (Object)this.getName());
                        }
                        super.onTermination(p_211561_);
                    }
                };
                $$2.setName("Worker-" + p_137478_ + "-" + f_137442_.getAndIncrement());
                return $$2;
            }, Util::m_137495_, true);
        }
        return $$3;
    }

    private static int m_183993_() {
        String $$0 = System.getProperty(f_183936_);
        if ($$0 != null) {
            try {
                int $$1 = Integer.parseInt($$0);
                if ($$1 >= 1 && $$1 <= 255) {
                    return $$1;
                }
                f_137446_.error("Wrong {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{f_183936_, $$0, 255});
            }
            catch (NumberFormatException $$2) {
                f_137446_.error("Could not parse {} property value '{}'. Should be an integer value between 1 and {}.", new Object[]{f_183936_, $$0, 255});
            }
        }
        return 255;
    }

    public static ExecutorService m_183988_() {
        return f_137443_;
    }

    public static ExecutorService m_183991_() {
        return f_137444_;
    }

    public static ExecutorService m_183992_() {
        return f_137445_;
    }

    public static void m_137580_() {
        Util.m_137531_(f_137444_);
        Util.m_137531_(f_137445_);
    }

    private static void m_137531_(ExecutorService p_137532_) {
        boolean $$3;
        p_137532_.shutdown();
        try {
            boolean $$1 = p_137532_.awaitTermination(3L, TimeUnit.SECONDS);
        }
        catch (InterruptedException $$2) {
            $$3 = false;
        }
        if (!$$3) {
            p_137532_.shutdownNow();
        }
    }

    private static ExecutorService m_137586_() {
        return Executors.newCachedThreadPool(p_201860_ -> {
            Thread $$1 = new Thread(p_201860_);
            $$1.setName("IO-Worker-" + f_137442_.getAndIncrement());
            $$1.setUncaughtExceptionHandler(Util::m_137495_);
            return $$1;
        });
    }

    public static <T> CompletableFuture<T> m_137498_(Throwable p_137499_) {
        CompletableFuture $$1 = new CompletableFuture();
        $$1.completeExceptionally(p_137499_);
        return $$1;
    }

    public static void m_137559_(Throwable p_137560_) {
        throw p_137560_ instanceof RuntimeException ? (RuntimeException)p_137560_ : new RuntimeException(p_137560_);
    }

    private static void m_137495_(Thread p_137496_, Throwable p_137497_) {
        Util.m_137570_(p_137497_);
        if (p_137497_ instanceof CompletionException) {
            p_137497_ = p_137497_.getCause();
        }
        if (p_137497_ instanceof ReportedException) {
            Bootstrap.m_135875_(((ReportedException)p_137497_).m_134761_().m_127526_());
            System.exit(-1);
        }
        f_137446_.error(String.format(Locale.ROOT, "Caught exception in thread %s", p_137496_), p_137497_);
    }

    @Nullable
    public static Type<?> m_137456_(DSL.TypeReference p_137457_, String p_137458_) {
        if (!SharedConstants.f_136182_) {
            return null;
        }
        return Util.m_137551_(p_137457_, p_137458_);
    }

    @Nullable
    private static Type<?> m_137551_(DSL.TypeReference p_137552_, String p_137553_) {
        Type $$2;
        block2: {
            $$2 = null;
            try {
                $$2 = DataFixers.m_14512_().getSchema(DataFixUtils.makeKey((int)SharedConstants.m_183709_().getWorldVersion())).getChoiceType(p_137552_, p_137553_);
            }
            catch (IllegalArgumentException $$3) {
                f_137446_.error("No data fixer registered for {}", (Object)p_137553_);
                if (!SharedConstants.f_136183_) break block2;
                throw $$3;
            }
        }
        return $$2;
    }

    public static Runnable m_143787_(String p_143788_, Runnable p_143789_) {
        if (SharedConstants.f_136183_) {
            return () -> {
                Thread $$2 = Thread.currentThread();
                String $$3 = $$2.getName();
                $$2.setName(p_143788_);
                try {
                    p_143789_.run();
                }
                finally {
                    $$2.setName($$3);
                }
            };
        }
        return p_143789_;
    }

    public static <V> Supplier<V> m_183946_(String p_183947_, Supplier<V> p_183948_) {
        if (SharedConstants.f_136183_) {
            return () -> {
                Thread $$2 = Thread.currentThread();
                String $$3 = $$2.getName();
                $$2.setName(p_183947_);
                try {
                    Object t = p_183948_.get();
                    return t;
                }
                finally {
                    $$2.setName($$3);
                }
            };
        }
        return p_183948_;
    }

    public static OS m_137581_() {
        String $$0 = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if ($$0.contains("win")) {
            return OS.WINDOWS;
        }
        if ($$0.contains("mac")) {
            return OS.OSX;
        }
        if ($$0.contains("solaris")) {
            return OS.SOLARIS;
        }
        if ($$0.contains("sunos")) {
            return OS.SOLARIS;
        }
        if ($$0.contains("linux")) {
            return OS.LINUX;
        }
        if ($$0.contains("unix")) {
            return OS.LINUX;
        }
        return OS.UNKNOWN;
    }

    public static Stream<String> m_137582_() {
        RuntimeMXBean $$0 = ManagementFactory.getRuntimeMXBean();
        return $$0.getInputArguments().stream().filter(p_201903_ -> p_201903_.startsWith("-X"));
    }

    public static <T> T m_137509_(List<T> p_137510_) {
        return p_137510_.get(p_137510_.size() - 1);
    }

    public static <T> T m_137466_(Iterable<T> p_137467_, @Nullable T p_137468_) {
        Iterator<T> $$2 = p_137467_.iterator();
        T $$3 = $$2.next();
        if (p_137468_ != null) {
            T $$4 = $$3;
            while (true) {
                if ($$4 == p_137468_) {
                    if (!$$2.hasNext()) break;
                    return $$2.next();
                }
                if (!$$2.hasNext()) continue;
                $$4 = $$2.next();
            }
        }
        return $$3;
    }

    public static <T> T m_137554_(Iterable<T> p_137555_, @Nullable T p_137556_) {
        Iterator<T> $$2 = p_137555_.iterator();
        T $$3 = null;
        while ($$2.hasNext()) {
            T $$4 = $$2.next();
            if ($$4 == p_137556_) {
                if ($$3 != null) break;
                $$3 = (T)($$2.hasNext() ? Iterators.getLast($$2) : p_137556_);
                break;
            }
            $$3 = $$4;
        }
        return $$3;
    }

    public static <T> T m_137537_(Supplier<T> p_137538_) {
        return p_137538_.get();
    }

    public static <T> T m_137469_(T p_137470_, Consumer<T> p_137471_) {
        p_137471_.accept(p_137470_);
        return p_137470_;
    }

    @Nullable
    public static <T, R> R m_214614_(@Nullable T p_214615_, Function<T, R> p_214616_) {
        if (p_214615_ == null) {
            return null;
        }
        return p_214616_.apply(p_214615_);
    }

    public static <T, R> R m_214617_(@Nullable T p_214618_, Function<T, R> p_214619_, R p_214620_) {
        if (p_214618_ == null) {
            return p_214620_;
        }
        return p_214619_.apply(p_214618_);
    }

    public static <K> Hash.Strategy<K> m_137583_() {
        return IdentityStrategy.INSTANCE;
    }

    public static <V> CompletableFuture<List<V>> m_137567_(List<? extends CompletableFuture<V>> p_137568_) {
        if (p_137568_.isEmpty()) {
            return CompletableFuture.completedFuture(List.of());
        }
        if (p_137568_.size() == 1) {
            return p_137568_.get(0).thenApply(List::of);
        }
        CompletableFuture<Void> $$1 = CompletableFuture.allOf(p_137568_.toArray(new CompletableFuture[0]));
        return $$1.thenApply(p_203746_ -> p_137568_.stream().map(CompletableFuture::join).toList());
    }

    public static <V> CompletableFuture<List<V>> m_143840_(List<? extends CompletableFuture<? extends V>> p_143841_) {
        CompletableFuture $$1 = new CompletableFuture();
        return Util.m_214631_(p_143841_, $$1::completeExceptionally).applyToEither((CompletionStage)$$1, Function.identity());
    }

    public static <V> CompletableFuture<List<V>> m_214684_(List<? extends CompletableFuture<? extends V>> p_214685_) {
        CompletableFuture $$1 = new CompletableFuture();
        return Util.m_214631_(p_214685_, p_214630_ -> {
            for (CompletableFuture $$3 : p_214685_) {
                $$3.cancel(true);
            }
            $$1.completeExceptionally((Throwable)p_214630_);
        }).applyToEither((CompletionStage)$$1, Function.identity());
    }

    private static <V> CompletableFuture<List<V>> m_214631_(List<? extends CompletableFuture<? extends V>> p_214632_, Consumer<Throwable> p_214633_) {
        ArrayList $$2 = Lists.newArrayListWithCapacity((int)p_214632_.size());
        CompletableFuture[] $$3 = new CompletableFuture[p_214632_.size()];
        p_214632_.forEach(p_214641_ -> {
            int $$4 = $$2.size();
            $$2.add(null);
            p_214639_[$$4] = p_214641_.whenComplete((p_214650_, p_214651_) -> {
                if (p_214651_ != null) {
                    p_214633_.accept((Throwable)p_214651_);
                } else {
                    $$2.set($$4, p_214650_);
                }
            });
        });
        return CompletableFuture.allOf($$3).thenApply(p_214626_ -> $$2);
    }

    public static <T> Optional<T> m_137521_(Optional<T> p_137522_, Consumer<T> p_137523_, Runnable p_137524_) {
        if (p_137522_.isPresent()) {
            p_137523_.accept(p_137522_.get());
        } else {
            p_137524_.run();
        }
        return p_137522_;
    }

    public static <T> Supplier<T> m_214655_(Supplier<T> p_214656_, Supplier<String> p_214657_) {
        return p_214656_;
    }

    public static Runnable m_137474_(Runnable p_137475_, Supplier<String> p_137476_) {
        return p_137475_;
    }

    public static void m_143785_(String p_143786_) {
        f_137446_.error(p_143786_);
        if (SharedConstants.f_136183_) {
            Util.m_183984_(p_143786_);
        }
    }

    public static void m_200890_(String p_200891_, Throwable p_200892_) {
        f_137446_.error(p_200891_, p_200892_);
        if (SharedConstants.f_136183_) {
            Util.m_183984_(p_200891_);
        }
    }

    public static <T extends Throwable> T m_137570_(T p_137571_) {
        if (SharedConstants.f_136183_) {
            f_137446_.error("Trying to throw a fatal exception, pausing in IDE", p_137571_);
            Util.m_183984_(p_137571_.getMessage());
        }
        return p_137571_;
    }

    public static void m_183969_(Consumer<String> p_183970_) {
        f_183937_ = p_183970_;
    }

    private static void m_183984_(String p_183985_) {
        boolean $$2;
        Instant $$1 = Instant.now();
        f_137446_.warn("Did you remember to set a breakpoint here?");
        boolean bl = $$2 = Duration.between($$1, Instant.now()).toMillis() > 500L;
        if (!$$2) {
            f_183937_.accept(p_183985_);
        }
    }

    public static String m_137575_(Throwable p_137576_) {
        if (p_137576_.getCause() != null) {
            return Util.m_137575_(p_137576_.getCause());
        }
        if (p_137576_.getMessage() != null) {
            return p_137576_.getMessage();
        }
        return p_137576_.toString();
    }

    public static <T> T m_214670_(T[] p_214671_, RandomSource p_214672_) {
        return p_214671_[p_214672_.m_188503_(p_214671_.length)];
    }

    public static int m_214667_(int[] p_214668_, RandomSource p_214669_) {
        return p_214668_[p_214669_.m_188503_(p_214668_.length)];
    }

    public static <T> T m_214621_(List<T> p_214622_, RandomSource p_214623_) {
        return p_214622_.get(p_214623_.m_188503_(p_214622_.size()));
    }

    public static <T> Optional<T> m_214676_(List<T> p_214677_, RandomSource p_214678_) {
        if (p_214677_.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Util.m_214621_(p_214677_, p_214678_));
    }

    private static BooleanSupplier m_137502_(final Path p_137503_, final Path p_137504_) {
        return new BooleanSupplier(){

            @Override
            public boolean getAsBoolean() {
                try {
                    Files.move(p_137503_, p_137504_, new CopyOption[0]);
                    return true;
                }
                catch (IOException $$0) {
                    f_137446_.error("Failed to rename", (Throwable)$$0);
                    return false;
                }
            }

            public String toString() {
                return "rename " + p_137503_ + " to " + p_137504_;
            }
        };
    }

    private static BooleanSupplier m_137500_(final Path p_137501_) {
        return new BooleanSupplier(){

            @Override
            public boolean getAsBoolean() {
                try {
                    Files.deleteIfExists(p_137501_);
                    return true;
                }
                catch (IOException $$0) {
                    f_137446_.warn("Failed to delete", (Throwable)$$0);
                    return false;
                }
            }

            public String toString() {
                return "delete old " + p_137501_;
            }
        };
    }

    private static BooleanSupplier m_137561_(final Path p_137562_) {
        return new BooleanSupplier(){

            @Override
            public boolean getAsBoolean() {
                return !Files.exists(p_137562_, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + p_137562_ + " is deleted";
            }
        };
    }

    private static BooleanSupplier m_137572_(final Path p_137573_) {
        return new BooleanSupplier(){

            @Override
            public boolean getAsBoolean() {
                return Files.isRegularFile(p_137573_, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + p_137573_ + " is present";
            }
        };
    }

    private static boolean m_137548_(BooleanSupplier ... p_137549_) {
        for (BooleanSupplier $$1 : p_137549_) {
            if ($$1.getAsBoolean()) continue;
            f_137446_.warn("Failed to execute {}", (Object)$$1);
            return false;
        }
        return true;
    }

    private static boolean m_137449_(int p_137450_, String p_137451_, BooleanSupplier ... p_137452_) {
        for (int $$3 = 0; $$3 < p_137450_; ++$$3) {
            if (Util.m_137548_(p_137452_)) {
                return true;
            }
            f_137446_.error("Failed to {}, retrying {}/{}", new Object[]{p_137451_, $$3, p_137450_});
        }
        f_137446_.error("Failed to {}, aborting, progress might be lost", (Object)p_137451_);
        return false;
    }

    public static void m_137462_(File p_137463_, File p_137464_, File p_137465_) {
        Util.m_137505_(p_137463_.toPath(), p_137464_.toPath(), p_137465_.toPath());
    }

    public static void m_137505_(Path p_137506_, Path p_137507_, Path p_137508_) {
        Util.m_212229_(p_137506_, p_137507_, p_137508_, false);
    }

    public static void m_212224_(File p_212225_, File p_212226_, File p_212227_, boolean p_212228_) {
        Util.m_212229_(p_212225_.toPath(), p_212226_.toPath(), p_212227_.toPath(), p_212228_);
    }

    public static void m_212229_(Path p_212230_, Path p_212231_, Path p_212232_, boolean p_212233_) {
        int $$4 = 10;
        if (Files.exists(p_212230_, new LinkOption[0]) && !Util.m_137449_(10, "create backup " + p_212232_, Util.m_137500_(p_212232_), Util.m_137502_(p_212230_, p_212232_), Util.m_137572_(p_212232_))) {
            return;
        }
        if (!Util.m_137449_(10, "remove old " + p_212230_, Util.m_137500_(p_212230_), Util.m_137561_(p_212230_))) {
            return;
        }
        if (!Util.m_137449_(10, "replace " + p_212230_ + " with " + p_212231_, Util.m_137502_(p_212231_, p_212230_), Util.m_137572_(p_212230_)) && !p_212233_) {
            Util.m_137449_(10, "restore " + p_212230_ + " from " + p_212232_, Util.m_137502_(p_212232_, p_212230_), Util.m_137572_(p_212230_));
        }
    }

    public static int m_137479_(String p_137480_, int p_137481_, int p_137482_) {
        int $$3 = p_137480_.length();
        if (p_137482_ >= 0) {
            for (int $$4 = 0; p_137481_ < $$3 && $$4 < p_137482_; ++$$4) {
                if (!Character.isHighSurrogate(p_137480_.charAt(p_137481_++)) || p_137481_ >= $$3 || !Character.isLowSurrogate(p_137480_.charAt(p_137481_))) continue;
                ++p_137481_;
            }
        } else {
            for (int $$5 = p_137482_; p_137481_ > 0 && $$5 < 0; ++$$5) {
                if (!Character.isLowSurrogate(p_137480_.charAt(--p_137481_)) || p_137481_ <= 0 || !Character.isHighSurrogate(p_137480_.charAt(p_137481_ - 1))) continue;
                --p_137481_;
            }
        }
        return p_137481_;
    }

    public static Consumer<String> m_137489_(String p_137490_, Consumer<String> p_137491_) {
        return p_214645_ -> p_137491_.accept(p_137490_ + p_214645_);
    }

    public static DataResult<int[]> m_137539_(IntStream p_137540_, int p_137541_) {
        int[] $$2 = p_137540_.limit(p_137541_ + 1).toArray();
        if ($$2.length != p_137541_) {
            String $$3 = "Input is not a list of " + p_137541_ + " ints";
            if ($$2.length >= p_137541_) {
                return DataResult.error((String)$$3, (Object)Arrays.copyOf($$2, p_137541_));
            }
            return DataResult.error((String)$$3);
        }
        return DataResult.success((Object)$$2);
    }

    public static <T> DataResult<List<T>> m_143795_(List<T> p_143796_, int p_143797_) {
        if (p_143796_.size() != p_143797_) {
            String $$2 = "Input is not a list of " + p_143797_ + " elements";
            if (p_143796_.size() >= p_143797_) {
                return DataResult.error((String)$$2, p_143796_.subList(0, p_143797_));
            }
            return DataResult.error((String)$$2);
        }
        return DataResult.success(p_143796_);
    }

    public static void m_137584_() {
        Thread $$0 = new Thread("Timer hack thread"){

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(Integer.MAX_VALUE);
                    }
                }
                catch (InterruptedException $$0) {
                    f_137446_.warn("Timer hack thread interrupted, that really should not happen");
                    return;
                }
            }
        };
        $$0.setDaemon(true);
        $$0.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_137446_));
        $$0.start();
    }

    public static void m_137563_(Path p_137564_, Path p_137565_, Path p_137566_) throws IOException {
        Path $$3 = p_137564_.relativize(p_137566_);
        Path $$4 = p_137565_.resolve($$3);
        Files.copy(p_137566_, $$4, new CopyOption[0]);
    }

    public static String m_137483_(String p_137484_, CharPredicate p_137485_) {
        return p_137484_.toLowerCase(Locale.ROOT).chars().mapToObj(p_214666_ -> p_137485_.m_125854_((char)p_214666_) ? Character.toString((char)p_214666_) : "_").collect(Collectors.joining());
    }

    public static <T, R> Function<T, R> m_143827_(final Function<T, R> p_143828_) {
        return new Function<T, R>(){
            private final Map<T, R> f_211548_ = Maps.newHashMap();

            @Override
            public R apply(T p_214691_) {
                return this.f_211548_.computeIfAbsent(p_214691_, p_143828_);
            }

            public String toString() {
                return "memoize/1[function=" + p_143828_ + ", size=" + this.f_211548_.size() + "]";
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> m_143821_(final BiFunction<T, U, R> p_143822_) {
        return new BiFunction<T, U, R>(){
            private final Map<Pair<T, U>, R> f_214693_ = Maps.newHashMap();

            @Override
            public R apply(T p_214700_, U p_214701_) {
                return this.f_214693_.computeIfAbsent(Pair.of(p_214700_, p_214701_), p_214698_ -> p_143822_.apply(p_214698_.getFirst(), p_214698_.getSecond()));
            }

            public String toString() {
                return "memoize/2[function=" + p_143822_ + ", size=" + this.f_214693_.size() + "]";
            }
        };
    }

    public static <T> List<T> m_214661_(Stream<T> p_214662_, RandomSource p_214663_) {
        ObjectArrayList $$2 = (ObjectArrayList)p_214662_.collect(ObjectArrayList.toList());
        Util.m_214673_($$2, p_214663_);
        return $$2;
    }

    public static IntArrayList m_214658_(IntStream p_214659_, RandomSource p_214660_) {
        int $$3;
        IntArrayList $$2 = IntArrayList.wrap((int[])p_214659_.toArray());
        for (int $$4 = $$3 = $$2.size(); $$4 > 1; --$$4) {
            int $$5 = p_214660_.m_188503_($$4);
            $$2.set($$4 - 1, $$2.set($$5, $$2.getInt($$4 - 1)));
        }
        return $$2;
    }

    public static <T> List<T> m_214681_(T[] p_214682_, RandomSource p_214683_) {
        ObjectArrayList $$2 = new ObjectArrayList((Object[])p_214682_);
        Util.m_214673_($$2, p_214683_);
        return $$2;
    }

    public static <T> List<T> m_214611_(ObjectArrayList<T> p_214612_, RandomSource p_214613_) {
        ObjectArrayList $$2 = new ObjectArrayList(p_214612_);
        Util.m_214673_($$2, p_214613_);
        return $$2;
    }

    public static <T> void m_214673_(ObjectArrayList<T> p_214674_, RandomSource p_214675_) {
        int $$2;
        for (int $$3 = $$2 = p_214674_.size(); $$3 > 1; --$$3) {
            int $$4 = p_214675_.m_188503_($$3);
            p_214674_.set($$3 - 1, p_214674_.set($$4, p_214674_.get($$3 - 1)));
        }
    }

    public static <T> CompletableFuture<T> m_214679_(Function<Executor, CompletableFuture<T>> p_214680_) {
        return Util.m_214652_(p_214680_, CompletableFuture::isDone);
    }

    public static <T> T m_214652_(Function<Executor, T> p_214653_, Predicate<T> p_214654_) {
        int $$6;
        LinkedBlockingQueue $$2 = new LinkedBlockingQueue();
        T $$3 = p_214653_.apply($$2::add);
        while (!p_214654_.test($$3)) {
            try {
                Runnable $$4 = (Runnable)$$2.poll(100L, TimeUnit.MILLISECONDS);
                if ($$4 == null) continue;
                $$4.run();
            }
            catch (InterruptedException $$5) {
                f_137446_.warn("Interrupted wait");
                break;
            }
        }
        if (($$6 = $$2.size()) > 0) {
            f_137446_.warn("Tasks left in queue: {}", (Object)$$6);
        }
        return $$3;
    }

    public static <T> ToIntFunction<T> m_214686_(List<T> p_214687_) {
        return Util.m_214634_(p_214687_, Object2IntOpenHashMap::new);
    }

    public static <T> ToIntFunction<T> m_214634_(List<T> p_214635_, IntFunction<Object2IntMap<T>> p_214636_) {
        Object2IntMap<T> $$2 = p_214636_.apply(p_214635_.size());
        for (int $$3 = 0; $$3 < p_214635_.size(); ++$$3) {
            $$2.put(p_214635_.get($$3), $$3);
        }
        return $$2;
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static class OS
    extends Enum<OS> {
        public static final /* enum */ OS LINUX = new OS("linux");
        public static final /* enum */ OS SOLARIS = new OS("solaris");
        public static final /* enum */ OS WINDOWS = new OS("windows"){

            @Override
            protected String[] m_6868_(URL p_137662_) {
                return new String[]{"rundll32", "url.dll,FileProtocolHandler", p_137662_.toString()};
            }
        };
        public static final /* enum */ OS OSX = new OS("mac"){

            @Override
            protected String[] m_6868_(URL p_137667_) {
                return new String[]{"open", p_137667_.toString()};
            }
        };
        public static final /* enum */ OS UNKNOWN = new OS("unknown");
        private final String f_183994_;
        private static final /* synthetic */ OS[] $VALUES;

        public static OS[] values() {
            return (OS[])$VALUES.clone();
        }

        public static OS valueOf(String p_137656_) {
            return Enum.valueOf(OS.class, p_137656_);
        }

        OS(String p_183998_) {
            this.f_183994_ = p_183998_;
        }

        public void m_137650_(URL p_137651_) {
            try {
                Process $$1 = AccessController.doPrivileged(() -> Runtime.getRuntime().exec(this.m_6868_(p_137651_)));
                $$1.getInputStream().close();
                $$1.getErrorStream().close();
                $$1.getOutputStream().close();
            }
            catch (IOException | PrivilegedActionException $$2) {
                f_137446_.error("Couldn't open url '{}'", (Object)p_137651_, (Object)$$2);
            }
        }

        public void m_137648_(URI p_137649_) {
            try {
                this.m_137650_(p_137649_.toURL());
            }
            catch (MalformedURLException $$1) {
                f_137446_.error("Couldn't open uri '{}'", (Object)p_137649_, (Object)$$1);
            }
        }

        public void m_137644_(File p_137645_) {
            try {
                this.m_137650_(p_137645_.toURI().toURL());
            }
            catch (MalformedURLException $$1) {
                f_137446_.error("Couldn't open file '{}'", (Object)p_137645_, (Object)$$1);
            }
        }

        protected String[] m_6868_(URL p_137652_) {
            String $$1 = p_137652_.toString();
            if ("file".equals(p_137652_.getProtocol())) {
                $$1 = $$1.replace("file:", "file://");
            }
            return new String[]{"xdg-open", $$1};
        }

        public void m_137646_(String p_137647_) {
            try {
                this.m_137650_(new URI(p_137647_).toURL());
            }
            catch (IllegalArgumentException | MalformedURLException | URISyntaxException $$1) {
                f_137446_.error("Couldn't open uri '{}'", (Object)p_137647_, (Object)$$1);
            }
        }

        public String m_183999_() {
            return this.f_183994_;
        }

        private static /* synthetic */ OS[] m_143863_() {
            return new OS[]{LINUX, SOLARIS, WINDOWS, OSX, UNKNOWN};
        }

        static {
            $VALUES = OS.m_143863_();
        }
    }

    static final class IdentityStrategy
    extends Enum<IdentityStrategy>
    implements Hash.Strategy<Object> {
        public static final /* enum */ IdentityStrategy INSTANCE = new IdentityStrategy();
        private static final /* synthetic */ IdentityStrategy[] $VALUES;

        public static IdentityStrategy[] values() {
            return (IdentityStrategy[])$VALUES.clone();
        }

        public static IdentityStrategy valueOf(String p_137628_) {
            return Enum.valueOf(IdentityStrategy.class, p_137628_);
        }

        public int hashCode(Object p_137626_) {
            return System.identityHashCode(p_137626_);
        }

        public boolean equals(Object p_137623_, Object p_137624_) {
            return p_137623_ == p_137624_;
        }

        private static /* synthetic */ IdentityStrategy[] m_143862_() {
            return new IdentityStrategy[]{INSTANCE};
        }

        static {
            $VALUES = IdentityStrategy.m_143862_();
        }
    }
}

