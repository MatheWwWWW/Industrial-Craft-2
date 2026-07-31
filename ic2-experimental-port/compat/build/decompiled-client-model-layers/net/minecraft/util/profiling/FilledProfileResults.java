/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.BufferedWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerPathEntry;
import net.minecraft.util.profiling.ResultField;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class FilledProfileResults
implements ProfileResults {
    private static final Logger f_18452_ = LogUtils.getLogger();
    private static final ProfilerPathEntry f_18453_ = new ProfilerPathEntry(){

        @Override
        public long m_7235_() {
            return 0L;
        }

        @Override
        public long m_142752_() {
            return 0L;
        }

        @Override
        public long m_7234_() {
            return 0L;
        }

        @Override
        public Object2LongMap<String> m_7446_() {
            return Object2LongMaps.emptyMap();
        }
    };
    private static final Splitter f_18454_ = Splitter.on((char)'\u001e');
    private static final Comparator<Map.Entry<String, CounterCollector>> f_18455_ = Map.Entry.comparingByValue(Comparator.comparingLong(p_18489_ -> p_18489_.f_18538_)).reversed();
    private final Map<String, ? extends ProfilerPathEntry> f_18456_;
    private final long f_18457_;
    private final int f_18458_;
    private final long f_18459_;
    private final int f_18460_;
    private final int f_18461_;

    public FilledProfileResults(Map<String, ? extends ProfilerPathEntry> p_18464_, long p_18465_, int p_18466_, long p_18467_, int p_18468_) {
        this.f_18456_ = p_18464_;
        this.f_18457_ = p_18465_;
        this.f_18458_ = p_18466_;
        this.f_18459_ = p_18467_;
        this.f_18460_ = p_18468_;
        this.f_18461_ = p_18468_ - p_18466_;
    }

    private ProfilerPathEntry m_18525_(String p_18526_) {
        ProfilerPathEntry $$1 = this.f_18456_.get(p_18526_);
        return $$1 != null ? $$1 : f_18453_;
    }

    @Override
    public List<ResultField> m_6412_(String p_18493_) {
        String $$1 = p_18493_;
        ProfilerPathEntry $$2 = this.m_18525_("root");
        long $$3 = $$2.m_7235_();
        ProfilerPathEntry $$4 = this.m_18525_((String)p_18493_);
        long $$5 = $$4.m_7235_();
        long $$6 = $$4.m_7234_();
        ArrayList $$7 = Lists.newArrayList();
        if (!((String)p_18493_).isEmpty()) {
            p_18493_ = (String)p_18493_ + "\u001e";
        }
        long $$8 = 0L;
        for (String $$9 : this.f_18456_.keySet()) {
            if (!FilledProfileResults.m_18494_((String)p_18493_, $$9)) continue;
            $$8 += this.m_18525_($$9).m_7235_();
        }
        float $$10 = $$8;
        if ($$8 < $$5) {
            $$8 = $$5;
        }
        if ($$3 < $$8) {
            $$3 = $$8;
        }
        for (String $$11 : this.f_18456_.keySet()) {
            if (!FilledProfileResults.m_18494_((String)p_18493_, $$11)) continue;
            ProfilerPathEntry $$12 = this.m_18525_($$11);
            long $$13 = $$12.m_7235_();
            double $$14 = (double)$$13 * 100.0 / (double)$$8;
            double $$15 = (double)$$13 * 100.0 / (double)$$3;
            String $$16 = $$11.substring(((String)p_18493_).length());
            $$7.add(new ResultField($$16, $$14, $$15, $$12.m_7234_()));
        }
        if ((float)$$8 > $$10) {
            $$7.add(new ResultField("unspecified", (double)((float)$$8 - $$10) * 100.0 / (double)$$8, (double)((float)$$8 - $$10) * 100.0 / (double)$$3, $$6));
        }
        Collections.sort($$7);
        $$7.add(0, new ResultField($$1, 100.0, (double)$$8 * 100.0 / (double)$$3, $$6));
        return $$7;
    }

    private static boolean m_18494_(String p_18495_, String p_18496_) {
        return p_18496_.length() > p_18495_.length() && p_18496_.startsWith(p_18495_) && p_18496_.indexOf(30, p_18495_.length() + 1) < 0;
    }

    private Map<String, CounterCollector> m_18531_() {
        TreeMap $$0 = Maps.newTreeMap();
        this.f_18456_.forEach((p_18512_, p_18513_) -> {
            Object2LongMap<String> $$3 = p_18513_.m_7446_();
            if (!$$3.isEmpty()) {
                List $$4 = f_18454_.splitToList((CharSequence)p_18512_);
                $$3.forEach((p_145944_, p_145945_) -> $$0.computeIfAbsent(p_145944_, p_145947_ -> new CounterCollector()).m_18547_($$4.iterator(), (long)p_145945_));
            }
        });
        return $$0;
    }

    @Override
    public long m_7229_() {
        return this.f_18457_;
    }

    @Override
    public int m_7230_() {
        return this.f_18458_;
    }

    @Override
    public long m_7236_() {
        return this.f_18459_;
    }

    @Override
    public int m_7317_() {
        return this.f_18460_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean m_142444_(Path p_145940_) {
        boolean bl;
        BufferedWriter $$1 = null;
        try {
            Files.createDirectories(p_145940_.getParent(), new FileAttribute[0]);
            $$1 = Files.newBufferedWriter(p_145940_, StandardCharsets.UTF_8, new OpenOption[0]);
            $$1.write(this.m_18485_(this.m_18577_(), this.m_7315_()));
            bl = true;
        }
        catch (Throwable $$2) {
            boolean bl2;
            try {
                f_18452_.error("Could not save profiler results to {}", (Object)p_145940_, (Object)$$2);
                bl2 = false;
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly($$1);
                throw throwable;
            }
            IOUtils.closeQuietly((Writer)$$1);
            return bl2;
        }
        IOUtils.closeQuietly((Writer)$$1);
        return bl;
    }

    protected String m_18485_(long p_18486_, int p_18487_) {
        StringBuilder $$2 = new StringBuilder();
        $$2.append("---- Minecraft Profiler Results ----\n");
        $$2.append("// ");
        $$2.append(FilledProfileResults.m_18532_());
        $$2.append("\n\n");
        $$2.append("Version: ").append(SharedConstants.m_183709_().getId()).append('\n');
        $$2.append("Time span: ").append(p_18486_ / 1000000L).append(" ms\n");
        $$2.append("Tick span: ").append(p_18487_).append(" ticks\n");
        $$2.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", Float.valueOf((float)p_18487_ / ((float)p_18486_ / 1.0E9f)))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        $$2.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.m_18481_(0, "root", $$2);
        $$2.append("--- END PROFILE DUMP ---\n\n");
        Map<String, CounterCollector> $$3 = this.m_18531_();
        if (!$$3.isEmpty()) {
            $$2.append("--- BEGIN COUNTER DUMP ---\n\n");
            this.m_18514_($$3, $$2, p_18487_);
            $$2.append("--- END COUNTER DUMP ---\n\n");
        }
        return $$2.toString();
    }

    @Override
    public String m_142368_() {
        StringBuilder $$0 = new StringBuilder();
        this.m_18481_(0, "root", $$0);
        return $$0.toString();
    }

    private static StringBuilder m_18497_(StringBuilder p_18498_, int p_18499_) {
        p_18498_.append(String.format(Locale.ROOT, "[%02d] ", p_18499_));
        for (int $$2 = 0; $$2 < p_18499_; ++$$2) {
            p_18498_.append("|   ");
        }
        return p_18498_;
    }

    private void m_18481_(int p_18482_, String p_18483_, StringBuilder p_18484_) {
        List<ResultField> $$3 = this.m_6412_(p_18483_);
        Object2LongMap<String> $$4 = ((ProfilerPathEntry)ObjectUtils.firstNonNull((Object[])new ProfilerPathEntry[]{this.f_18456_.get(p_18483_), f_18453_})).m_7446_();
        $$4.forEach((p_18508_, p_18509_) -> FilledProfileResults.m_18497_(p_18484_, p_18482_).append('#').append((String)p_18508_).append(' ').append(p_18509_).append('/').append(p_18509_ / (long)this.f_18461_).append('\n'));
        if ($$3.size() < 3) {
            return;
        }
        for (int $$5 = 1; $$5 < $$3.size(); ++$$5) {
            ResultField $$6 = $$3.get($$5);
            FilledProfileResults.m_18497_(p_18484_, p_18482_).append($$6.f_18610_).append('(').append($$6.f_18609_).append('/').append(String.format(Locale.ROOT, "%.0f", Float.valueOf((float)$$6.f_18609_ / (float)this.f_18461_))).append(')').append(" - ").append(String.format(Locale.ROOT, "%.2f", $$6.f_18607_)).append("%/").append(String.format(Locale.ROOT, "%.2f", $$6.f_18608_)).append("%\n");
            if ("unspecified".equals($$6.f_18610_)) continue;
            try {
                this.m_18481_(p_18482_ + 1, p_18483_ + "\u001e" + $$6.f_18610_, p_18484_);
                continue;
            }
            catch (Exception $$7) {
                p_18484_.append("[[ EXCEPTION ").append($$7).append(" ]]");
            }
        }
    }

    private void m_18475_(int p_18476_, String p_18477_, CounterCollector p_18478_, int p_18479_, StringBuilder p_18480_) {
        FilledProfileResults.m_18497_(p_18480_, p_18476_).append(p_18477_).append(" total:").append(p_18478_.f_18537_).append('/').append(p_18478_.f_18538_).append(" average: ").append(p_18478_.f_18537_ / (long)p_18479_).append('/').append(p_18478_.f_18538_ / (long)p_18479_).append('\n');
        p_18478_.f_18539_.entrySet().stream().sorted(f_18455_).forEach(p_18474_ -> this.m_18475_(p_18476_ + 1, (String)p_18474_.getKey(), (CounterCollector)p_18474_.getValue(), p_18479_, p_18480_));
    }

    private void m_18514_(Map<String, CounterCollector> p_18515_, StringBuilder p_18516_, int p_18517_) {
        p_18515_.forEach((p_18503_, p_18504_) -> {
            p_18516_.append("-- Counter: ").append((String)p_18503_).append(" --\n");
            this.m_18475_(0, "root", p_18504_.f_18539_.get("root"), p_18517_, p_18516_);
            p_18516_.append("\n\n");
        });
    }

    private static String m_18532_() {
        String[] $$0 = new String[]{"I'd Rather Be Surfing", "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};
        try {
            return $$0[(int)(Util.m_137569_() % (long)$$0.length)];
        }
        catch (Throwable $$1) {
            return "Witty comment unavailable :(";
        }
    }

    @Override
    public int m_7315_() {
        return this.f_18461_;
    }

    static class CounterCollector {
        long f_18537_;
        long f_18538_;
        final Map<String, CounterCollector> f_18539_ = Maps.newHashMap();

        CounterCollector() {
        }

        public void m_18547_(Iterator<String> p_18548_, long p_18549_) {
            this.f_18538_ += p_18549_;
            if (!p_18548_.hasNext()) {
                this.f_18537_ += p_18549_;
            } else {
                this.f_18539_.computeIfAbsent(p_18548_.next(), p_18546_ -> new CounterCollector()).m_18547_(p_18548_, p_18549_);
            }
        }
    }
}

