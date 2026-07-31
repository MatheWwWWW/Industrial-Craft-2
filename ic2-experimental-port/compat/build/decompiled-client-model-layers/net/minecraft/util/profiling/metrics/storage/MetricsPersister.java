/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling.metrics.storage;

import com.mojang.logging.LogUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.storage.RecordedDeviation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class MetricsPersister {
    public static final Path f_146209_ = Paths.get("debug/profiling", new String[0]);
    public static final String f_146210_ = "metrics";
    public static final String f_146211_ = "deviations";
    public static final String f_146212_ = "profiling.txt";
    private static final Logger f_146213_ = LogUtils.getLogger();
    private final String f_146214_;

    public MetricsPersister(String p_146217_) {
        this.f_146214_ = p_146217_;
    }

    public Path m_146250_(Set<MetricSampler> p_146251_, Map<MetricSampler, List<RecordedDeviation>> p_146252_, ProfileResults p_146253_) {
        try {
            Files.createDirectories(f_146209_, new FileAttribute[0]);
        }
        catch (IOException $$3) {
            throw new UncheckedIOException($$3);
        }
        try {
            Path $$4 = Files.createTempDirectory("minecraft-profiling", new FileAttribute[0]);
            $$4.toFile().deleteOnExit();
            Files.createDirectories(f_146209_, new FileAttribute[0]);
            Path $$5 = $$4.resolve(this.f_146214_);
            Path $$6 = $$5.resolve(f_146210_);
            this.m_146247_(p_146251_, $$6);
            if (!p_146252_.isEmpty()) {
                this.m_146244_(p_146252_, $$5.resolve(f_146211_));
            }
            this.m_146223_(p_146253_, $$5);
            return $$4;
        }
        catch (IOException $$7) {
            throw new UncheckedIOException($$7);
        }
    }

    private void m_146247_(Set<MetricSampler> p_146248_, Path p_146249_) {
        if (p_146248_.isEmpty()) {
            throw new IllegalArgumentException("Expected at least one sampler to persist");
        }
        Map<MetricCategory, List<MetricSampler>> $$2 = p_146248_.stream().collect(Collectors.groupingBy(MetricSampler::m_146021_));
        $$2.forEach((p_146232_, p_146233_) -> this.m_146226_((MetricCategory)((Object)p_146232_), (List<MetricSampler>)p_146233_, p_146249_));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void m_146226_(MetricCategory p_146227_, List<MetricSampler> p_146228_, Path p_146229_) {
        Path $$3 = p_146229_.resolve(Util.m_137483_(p_146227_.m_145981_(), ResourceLocation::m_135828_) + ".csv");
        BufferedWriter $$4 = null;
        try {
            Files.createDirectories($$3.getParent(), new FileAttribute[0]);
            $$4 = Files.newBufferedWriter($$3, StandardCharsets.UTF_8, new OpenOption[0]);
            CsvOutput.Builder $$5 = CsvOutput.m_13619_();
            $$5.m_13630_("@tick");
            for (MetricSampler $$6 : p_146228_) {
                $$5.m_13630_($$6.m_146020_());
            }
            CsvOutput $$7 = $$5.m_13628_($$4);
            List $$8 = p_146228_.stream().map(MetricSampler::m_146024_).collect(Collectors.toList());
            int $$9 = $$8.stream().mapToInt(MetricSampler.SamplerResult::m_146056_).summaryStatistics().getMin();
            int $$10 = $$8.stream().mapToInt(MetricSampler.SamplerResult::m_146059_).summaryStatistics().getMax();
            for (int $$11 = $$9; $$11 <= $$10; ++$$11) {
                int $$12 = $$11;
                Stream<String> $$13 = $$8.stream().map(p_146222_ -> String.valueOf(p_146222_.m_146057_($$12)));
                Object[] $$14 = Stream.concat(Stream.of(String.valueOf($$11)), $$13).toArray(String[]::new);
                $$7.m_13624_($$14);
            }
            f_146213_.info("Flushed metrics to {}", (Object)$$3);
            IOUtils.closeQuietly((Writer)$$4);
        }
        catch (Exception $$15) {
            f_146213_.error("Could not save profiler results to {}", (Object)$$3, (Object)$$15);
        }
        finally {
            IOUtils.closeQuietly($$4);
        }
    }

    private void m_146244_(Map<MetricSampler, List<RecordedDeviation>> p_146245_, Path p_146246_) {
        DateTimeFormatter $$2 = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK).withZone(ZoneId.systemDefault());
        p_146245_.forEach((p_146242_, p_146243_) -> p_146243_.forEach(p_146238_ -> {
            String $$4 = $$2.format(p_146238_.f_146254_);
            Path $$5 = p_146246_.resolve(Util.m_137483_(p_146242_.m_146020_(), ResourceLocation::m_135828_)).resolve(String.format(Locale.ROOT, "%d@%s.txt", p_146238_.f_146255_, $$4));
            p_146238_.f_146256_.m_142444_($$5);
        }));
    }

    private void m_146223_(ProfileResults p_146224_, Path p_146225_) {
        p_146224_.m_142444_(p_146225_.resolve(f_146212_));
    }
}

