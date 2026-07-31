/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ChainedJsonException
extends IOException {
    private final List<Entry> f_135899_ = Lists.newArrayList();
    private final String f_135900_;

    public ChainedJsonException(String p_135902_) {
        this.f_135899_.add(new Entry());
        this.f_135900_ = p_135902_;
    }

    public ChainedJsonException(String p_135904_, Throwable p_135905_) {
        super(p_135905_);
        this.f_135899_.add(new Entry());
        this.f_135900_ = p_135904_;
    }

    public void m_135908_(String p_135909_) {
        this.f_135899_.get(0).m_135918_(p_135909_);
    }

    public void m_135910_(String p_135911_) {
        this.f_135899_.get((int)0).f_135913_ = p_135911_;
        this.f_135899_.add(0, new Entry());
    }

    @Override
    public String getMessage() {
        return "Invalid " + this.f_135899_.get(this.f_135899_.size() - 1) + ": " + this.f_135900_;
    }

    public static ChainedJsonException m_135906_(Exception p_135907_) {
        if (p_135907_ instanceof ChainedJsonException) {
            return (ChainedJsonException)p_135907_;
        }
        String $$1 = p_135907_.getMessage();
        if (p_135907_ instanceof FileNotFoundException) {
            $$1 = "File not found";
        }
        return new ChainedJsonException($$1, p_135907_);
    }

    public static class Entry {
        @Nullable
        String f_135913_;
        private final List<String> f_135914_ = Lists.newArrayList();

        Entry() {
        }

        void m_135918_(String p_135919_) {
            this.f_135914_.add(0, p_135919_);
        }

        @Nullable
        public String m_179919_() {
            return this.f_135913_;
        }

        public String m_135923_() {
            return StringUtils.join(this.f_135914_, (String)"->");
        }

        public String toString() {
            if (this.f_135913_ != null) {
                if (this.f_135914_.isEmpty()) {
                    return this.f_135913_;
                }
                return this.f_135913_ + " " + this.m_135923_();
            }
            if (this.f_135914_.isEmpty()) {
                return "(Unknown file)";
            }
            return "(Unknown file) " + this.m_135923_();
        }
    }
}

