/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestListener;

public class MultipleTestTracker {
    private static final char f_177677_ = ' ';
    private static final char f_177678_ = '_';
    private static final char f_177679_ = '+';
    private static final char f_177680_ = 'x';
    private static final char f_177681_ = 'X';
    private final Collection<GameTestInfo> f_127798_ = Lists.newArrayList();
    @Nullable
    private final Collection<GameTestListener> f_127799_ = Lists.newArrayList();

    public MultipleTestTracker() {
    }

    public MultipleTestTracker(Collection<GameTestInfo> p_127802_) {
        this.f_127798_.addAll(p_127802_);
    }

    public void m_127809_(GameTestInfo p_127810_) {
        this.f_127798_.add(p_127810_);
        this.f_127799_.forEach(p_127810_::m_127624_);
    }

    public void m_127811_(GameTestListener p_127812_) {
        this.f_127799_.add(p_127812_);
        this.f_127798_.forEach(p_127815_ -> p_127815_.m_127624_(p_127812_));
    }

    public void m_127807_(final Consumer<GameTestInfo> p_127808_) {
        this.m_127811_(new GameTestListener(){

            @Override
            public void m_8073_(GameTestInfo p_127830_) {
            }

            @Override
            public void m_142378_(GameTestInfo p_177685_) {
            }

            @Override
            public void m_8066_(GameTestInfo p_127832_) {
                p_127808_.accept(p_127832_);
            }
        });
    }

    public int m_127803_() {
        return (int)this.f_127798_.stream().filter(GameTestInfo::m_127639_).filter(GameTestInfo::m_127643_).count();
    }

    public int m_127816_() {
        return (int)this.f_127798_.stream().filter(GameTestInfo::m_127639_).filter(GameTestInfo::m_127644_).count();
    }

    public int m_127817_() {
        return (int)this.f_127798_.stream().filter(GameTestInfo::m_127641_).count();
    }

    public boolean m_127818_() {
        return this.m_127803_() > 0;
    }

    public boolean m_127819_() {
        return this.m_127816_() > 0;
    }

    public Collection<GameTestInfo> m_177682_() {
        return this.f_127798_.stream().filter(GameTestInfo::m_127639_).filter(GameTestInfo::m_127643_).collect(Collectors.toList());
    }

    public Collection<GameTestInfo> m_177683_() {
        return this.f_127798_.stream().filter(GameTestInfo::m_127639_).filter(GameTestInfo::m_127644_).collect(Collectors.toList());
    }

    public int m_127820_() {
        return this.f_127798_.size();
    }

    public boolean m_127821_() {
        return this.m_127817_() == this.m_127820_();
    }

    public String m_127822_() {
        StringBuffer $$0 = new StringBuffer();
        $$0.append('[');
        this.f_127798_.forEach(p_127806_ -> {
            if (!p_127806_.m_127640_()) {
                $$0.append(' ');
            } else if (p_127806_.m_127638_()) {
                $$0.append('+');
            } else if (p_127806_.m_127639_()) {
                $$0.append(p_127806_.m_127643_() ? (char)'X' : (char)'x');
            } else {
                $$0.append('_');
            }
        });
        $$0.append(']');
        return $$0.toString();
    }

    public String toString() {
        return this.m_127822_();
    }
}

