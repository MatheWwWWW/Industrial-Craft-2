/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.WorldVersion;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

public class DataGenerator {
    private static final Logger f_123905_ = LogUtils.getLogger();
    private final Collection<Path> f_123906_;
    private final Path f_123907_;
    private final List<DataProvider> f_236025_ = Lists.newArrayList();
    private final List<DataProvider> f_236026_ = Lists.newArrayList();
    private final WorldVersion f_236027_;
    private final boolean f_236028_;

    public DataGenerator(Path p_236030_, Collection<Path> p_236031_, WorldVersion p_236032_, boolean p_236033_) {
        this.f_123907_ = p_236030_;
        this.f_123906_ = p_236031_;
        this.f_236027_ = p_236032_;
        this.f_236028_ = p_236033_;
    }

    public Collection<Path> m_123913_() {
        return this.f_123906_;
    }

    public Path m_123916_() {
        return this.f_123907_;
    }

    public Path m_236034_(Target p_236035_) {
        return this.m_123916_().resolve(p_236035_.f_236056_);
    }

    public void m_123917_() throws IOException {
        HashCache $$0 = new HashCache(this.f_123907_, this.f_236025_, this.f_236027_);
        Stopwatch $$1 = Stopwatch.createStarted();
        Stopwatch $$2 = Stopwatch.createUnstarted();
        for (DataProvider $$3 : this.f_236026_) {
            if (!this.f_236028_ && !$$0.m_236090_($$3)) {
                f_123905_.debug("Generator {} already run for version {}", (Object)$$3.m_6055_(), (Object)this.f_236027_.getName());
                continue;
            }
            f_123905_.info("Starting provider: {}", (Object)$$3.m_6055_());
            $$2.start();
            $$3.m_213708_($$0.m_236107_($$3));
            $$2.stop();
            f_123905_.info("{} finished after {} ms", (Object)$$3.m_6055_(), (Object)$$2.elapsed(TimeUnit.MILLISECONDS));
            $$2.reset();
        }
        f_123905_.info("All providers took: {} ms", (Object)$$1.elapsed(TimeUnit.MILLISECONDS));
        $$0.m_123937_();
    }

    public void m_236039_(boolean p_236040_, DataProvider p_236041_) {
        if (p_236040_) {
            this.f_236026_.add(p_236041_);
        }
        this.f_236025_.add(p_236041_);
    }

    public PathProvider m_236036_(Target p_236037_, String p_236038_) {
        return new PathProvider(this, p_236037_, p_236038_);
    }

    static {
        Bootstrap.m_135870_();
    }

    public static final class Target
    extends Enum<Target> {
        public static final /* enum */ Target DATA_PACK = new Target("data");
        public static final /* enum */ Target RESOURCE_PACK = new Target("assets");
        public static final /* enum */ Target REPORTS = new Target("reports");
        final String f_236056_;
        private static final /* synthetic */ Target[] $VALUES;

        public static Target[] values() {
            return (Target[])$VALUES.clone();
        }

        public static Target valueOf(String p_236065_) {
            return Enum.valueOf(Target.class, p_236065_);
        }

        private Target(String p_236062_) {
            this.f_236056_ = p_236062_;
        }

        private static /* synthetic */ Target[] m_236063_() {
            return new Target[]{DATA_PACK, RESOURCE_PACK, REPORTS};
        }

        static {
            $VALUES = Target.m_236063_();
        }
    }

    public static class PathProvider {
        private final Path f_236042_;
        private final String f_236043_;

        PathProvider(DataGenerator p_236045_, Target p_236046_, String p_236047_) {
            this.f_236042_ = p_236045_.m_236034_(p_236046_);
            this.f_236043_ = p_236047_;
        }

        public Path m_236050_(ResourceLocation p_236051_, String p_236052_) {
            return this.f_236042_.resolve(p_236051_.m_135827_()).resolve(this.f_236043_).resolve(p_236051_.m_135815_() + "." + p_236052_);
        }

        public Path m_236048_(ResourceLocation p_236049_) {
            return this.f_236042_.resolve(p_236049_.m_135827_()).resolve(this.f_236043_).resolve(p_236049_.m_135815_() + ".json");
        }
    }
}

