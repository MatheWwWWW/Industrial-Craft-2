/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagVisitor;

public class StringTagVisitor
implements TagVisitor {
    private static final Pattern f_178155_ = Pattern.compile("[A-Za-z0-9._+-]+");
    private final StringBuilder f_178156_ = new StringBuilder();

    public String m_178187_(Tag p_178188_) {
        p_178188_.m_142327_(this);
        return this.f_178156_.toString();
    }

    @Override
    public void m_142614_(StringTag p_178186_) {
        this.f_178156_.append(StringTag.m_129303_(p_178186_.m_7916_()));
    }

    @Override
    public void m_141946_(ByteTag p_178164_) {
        this.f_178156_.append(p_178164_.m_8103_()).append('b');
    }

    @Override
    public void m_142183_(ShortTag p_178184_) {
        this.f_178156_.append(p_178184_.m_8103_()).append('s');
    }

    @Override
    public void m_142045_(IntTag p_178176_) {
        this.f_178156_.append(p_178176_.m_8103_());
    }

    @Override
    public void m_142046_(LongTag p_178182_) {
        this.f_178156_.append(p_178182_.m_8103_()).append('L');
    }

    @Override
    public void m_142181_(FloatTag p_178172_) {
        this.f_178156_.append(p_178172_.m_7057_()).append('f');
    }

    @Override
    public void m_142121_(DoubleTag p_178168_) {
        this.f_178156_.append(p_178168_.m_7061_()).append('d');
    }

    @Override
    public void m_142154_(ByteArrayTag p_178162_) {
        this.f_178156_.append("[B;");
        byte[] $$1 = p_178162_.m_128227_();
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            if ($$2 != 0) {
                this.f_178156_.append(',');
            }
            this.f_178156_.append($$1[$$2]).append('B');
        }
        this.f_178156_.append(']');
    }

    @Override
    public void m_142251_(IntArrayTag p_178174_) {
        this.f_178156_.append("[I;");
        int[] $$1 = p_178174_.m_128648_();
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            if ($$2 != 0) {
                this.f_178156_.append(',');
            }
            this.f_178156_.append($$1[$$2]);
        }
        this.f_178156_.append(']');
    }

    @Override
    public void m_142309_(LongArrayTag p_178180_) {
        this.f_178156_.append("[L;");
        long[] $$1 = p_178180_.m_128851_();
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            if ($$2 != 0) {
                this.f_178156_.append(',');
            }
            this.f_178156_.append($$1[$$2]).append('L');
        }
        this.f_178156_.append(']');
    }

    @Override
    public void m_142447_(ListTag p_178178_) {
        this.f_178156_.append('[');
        for (int $$1 = 0; $$1 < p_178178_.size(); ++$$1) {
            if ($$1 != 0) {
                this.f_178156_.append(',');
            }
            this.f_178156_.append(new StringTagVisitor().m_178187_(p_178178_.get($$1)));
        }
        this.f_178156_.append(']');
    }

    @Override
    public void m_142303_(CompoundTag p_178166_) {
        this.f_178156_.append('{');
        ArrayList $$1 = Lists.newArrayList(p_178166_.m_128431_());
        Collections.sort($$1);
        for (String $$2 : $$1) {
            if (this.f_178156_.length() != 1) {
                this.f_178156_.append(',');
            }
            this.f_178156_.append(StringTagVisitor.m_178159_($$2)).append(':').append(new StringTagVisitor().m_178187_(p_178166_.m_128423_($$2)));
        }
        this.f_178156_.append('}');
    }

    protected static String m_178159_(String p_178160_) {
        if (f_178155_.matcher(p_178160_).matches()) {
            return p_178160_;
        }
        return StringTag.m_129303_(p_178160_);
    }

    @Override
    public void m_142384_(EndTag p_178170_) {
        this.f_178156_.append("END");
    }
}

