/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringEscapeUtils
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class CsvOutput {
    private static final String f_144618_ = "\r\n";
    private static final String f_144619_ = ",";
    private final Writer f_13610_;
    private final int f_13611_;

    CsvOutput(Writer p_13613_, List<String> p_13614_) throws IOException {
        this.f_13610_ = p_13613_;
        this.f_13611_ = p_13614_.size();
        this.m_13622_(p_13614_.stream());
    }

    public static Builder m_13619_() {
        return new Builder();
    }

    public void m_13624_(Object ... p_13625_) throws IOException {
        if (p_13625_.length != this.f_13611_) {
            throw new IllegalArgumentException("Invalid number of columns, expected " + this.f_13611_ + ", but got " + p_13625_.length);
        }
        this.m_13622_(Stream.of(p_13625_));
    }

    private void m_13622_(Stream<?> p_13623_) throws IOException {
        this.f_13610_.write(p_13623_.map(CsvOutput::m_13620_).collect(Collectors.joining(f_144619_)) + f_144618_);
    }

    private static String m_13620_(@Nullable Object p_13621_) {
        return StringEscapeUtils.escapeCsv((String)(p_13621_ != null ? p_13621_.toString() : "[null]"));
    }

    public static class Builder {
        private final List<String> f_13626_ = Lists.newArrayList();

        public Builder m_13630_(String p_13631_) {
            this.f_13626_.add(p_13631_);
            return this;
        }

        public CsvOutput m_13628_(Writer p_13629_) throws IOException {
            return new CsvOutput(p_13629_, this.f_13626_);
        }
    }
}

