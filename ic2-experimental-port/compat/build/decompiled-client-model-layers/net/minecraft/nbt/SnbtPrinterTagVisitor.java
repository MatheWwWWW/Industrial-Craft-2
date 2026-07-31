/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.Util;
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

public class SnbtPrinterTagVisitor
implements TagVisitor {
    private static final Map<String, List<String>> f_178088_ = Util.m_137469_(Maps.newHashMap(), p_178114_ -> {
        p_178114_.put("{}", Lists.newArrayList((Object[])new String[]{"DataVersion", "author", "size", "data", "entities", "palette", "palettes"}));
        p_178114_.put("{}.data.[].{}", Lists.newArrayList((Object[])new String[]{"pos", "state", "nbt"}));
        p_178114_.put("{}.entities.[].{}", Lists.newArrayList((Object[])new String[]{"blockPos", "pos"}));
    });
    private static final Set<String> f_178089_ = Sets.newHashSet((Object[])new String[]{"{}.size.[]", "{}.data.[].{}", "{}.palette.[].{}", "{}.entities.[].{}"});
    private static final Pattern f_178090_ = Pattern.compile("[A-Za-z0-9._+-]+");
    private static final String f_178091_ = String.valueOf(':');
    private static final String f_178092_ = String.valueOf(',');
    private static final String f_178093_ = "[";
    private static final String f_178094_ = "]";
    private static final String f_178095_ = ";";
    private static final String f_178096_ = " ";
    private static final String f_178097_ = "{";
    private static final String f_178098_ = "}";
    private static final String f_178099_ = "\n";
    private final String f_178100_;
    private final int f_178101_;
    private final List<String> f_178102_;
    private String f_178103_ = "";

    public SnbtPrinterTagVisitor() {
        this("    ", 0, Lists.newArrayList());
    }

    public SnbtPrinterTagVisitor(String p_178107_, int p_178108_, List<String> p_178109_) {
        this.f_178100_ = p_178107_;
        this.f_178101_ = p_178108_;
        this.f_178102_ = p_178109_;
    }

    public String m_178141_(Tag p_178142_) {
        p_178142_.m_142327_(this);
        return this.f_178103_;
    }

    @Override
    public void m_142614_(StringTag p_178140_) {
        this.f_178103_ = StringTag.m_129303_(p_178140_.m_7916_());
    }

    @Override
    public void m_141946_(ByteTag p_178118_) {
        this.f_178103_ = p_178118_.m_8103_() + "b";
    }

    @Override
    public void m_142183_(ShortTag p_178138_) {
        this.f_178103_ = p_178138_.m_8103_() + "s";
    }

    @Override
    public void m_142045_(IntTag p_178130_) {
        this.f_178103_ = String.valueOf(p_178130_.m_8103_());
    }

    @Override
    public void m_142046_(LongTag p_178136_) {
        this.f_178103_ = p_178136_.m_8103_() + "L";
    }

    @Override
    public void m_142181_(FloatTag p_178126_) {
        this.f_178103_ = p_178126_.m_7057_() + "f";
    }

    @Override
    public void m_142121_(DoubleTag p_178122_) {
        this.f_178103_ = p_178122_.m_7061_() + "d";
    }

    @Override
    public void m_142154_(ByteArrayTag p_178116_) {
        StringBuilder $$1 = new StringBuilder(f_178093_).append("B").append(f_178095_);
        byte[] $$2 = p_178116_.m_128227_();
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$1.append(f_178096_).append($$2[$$3]).append("B");
            if ($$3 == $$2.length - 1) continue;
            $$1.append(f_178092_);
        }
        $$1.append(f_178094_);
        this.f_178103_ = $$1.toString();
    }

    @Override
    public void m_142251_(IntArrayTag p_178128_) {
        StringBuilder $$1 = new StringBuilder(f_178093_).append("I").append(f_178095_);
        int[] $$2 = p_178128_.m_128648_();
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$1.append(f_178096_).append($$2[$$3]);
            if ($$3 == $$2.length - 1) continue;
            $$1.append(f_178092_);
        }
        $$1.append(f_178094_);
        this.f_178103_ = $$1.toString();
    }

    @Override
    public void m_142309_(LongArrayTag p_178134_) {
        String $$1 = "L";
        StringBuilder $$2 = new StringBuilder(f_178093_).append("L").append(f_178095_);
        long[] $$3 = p_178134_.m_128851_();
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            $$2.append(f_178096_).append($$3[$$4]).append("L");
            if ($$4 == $$3.length - 1) continue;
            $$2.append(f_178092_);
        }
        $$2.append(f_178094_);
        this.f_178103_ = $$2.toString();
    }

    @Override
    public void m_142447_(ListTag p_178132_) {
        String $$2;
        if (p_178132_.isEmpty()) {
            this.f_178103_ = "[]";
            return;
        }
        StringBuilder $$1 = new StringBuilder(f_178093_);
        this.m_178144_("[]");
        String string = $$2 = f_178089_.contains(this.m_178110_()) ? "" : this.f_178100_;
        if (!$$2.isEmpty()) {
            $$1.append(f_178099_);
        }
        for (int $$3 = 0; $$3 < p_178132_.size(); ++$$3) {
            $$1.append(Strings.repeat((String)$$2, (int)(this.f_178101_ + 1)));
            $$1.append(new SnbtPrinterTagVisitor($$2, this.f_178101_ + 1, this.f_178102_).m_178141_(p_178132_.get($$3)));
            if ($$3 == p_178132_.size() - 1) continue;
            $$1.append(f_178092_).append($$2.isEmpty() ? f_178096_ : f_178099_);
        }
        if (!$$2.isEmpty()) {
            $$1.append(f_178099_).append(Strings.repeat((String)$$2, (int)this.f_178101_));
        }
        $$1.append(f_178094_);
        this.f_178103_ = $$1.toString();
        this.m_178143_();
    }

    @Override
    public void m_142303_(CompoundTag p_178120_) {
        String $$2;
        if (p_178120_.m_128456_()) {
            this.f_178103_ = "{}";
            return;
        }
        StringBuilder $$1 = new StringBuilder(f_178097_);
        this.m_178144_("{}");
        String string = $$2 = f_178089_.contains(this.m_178110_()) ? "" : this.f_178100_;
        if (!$$2.isEmpty()) {
            $$1.append(f_178099_);
        }
        List<String> $$3 = this.m_178146_(p_178120_);
        Iterator $$4 = $$3.iterator();
        while ($$4.hasNext()) {
            String $$5 = (String)$$4.next();
            Tag $$6 = p_178120_.m_128423_($$5);
            this.m_178144_($$5);
            $$1.append(Strings.repeat((String)$$2, (int)(this.f_178101_ + 1))).append(SnbtPrinterTagVisitor.m_178111_($$5)).append(f_178091_).append(f_178096_).append(new SnbtPrinterTagVisitor($$2, this.f_178101_ + 1, this.f_178102_).m_178141_($$6));
            this.m_178143_();
            if (!$$4.hasNext()) continue;
            $$1.append(f_178092_).append($$2.isEmpty() ? f_178096_ : f_178099_);
        }
        if (!$$2.isEmpty()) {
            $$1.append(f_178099_).append(Strings.repeat((String)$$2, (int)this.f_178101_));
        }
        $$1.append(f_178098_);
        this.f_178103_ = $$1.toString();
        this.m_178143_();
    }

    private void m_178143_() {
        this.f_178102_.remove(this.f_178102_.size() - 1);
    }

    private void m_178144_(String p_178145_) {
        this.f_178102_.add(p_178145_);
    }

    protected List<String> m_178146_(CompoundTag p_178147_) {
        HashSet $$1 = Sets.newHashSet(p_178147_.m_128431_());
        ArrayList $$2 = Lists.newArrayList();
        List<String> $$3 = f_178088_.get(this.m_178110_());
        if ($$3 != null) {
            for (String $$4 : $$3) {
                if (!$$1.remove($$4)) continue;
                $$2.add($$4);
            }
            if (!$$1.isEmpty()) {
                $$1.stream().sorted().forEach($$2::add);
            }
        } else {
            $$2.addAll($$1);
            Collections.sort($$2);
        }
        return $$2;
    }

    public String m_178110_() {
        return String.join((CharSequence)".", this.f_178102_);
    }

    protected static String m_178111_(String p_178112_) {
        if (f_178090_.matcher(p_178112_).matches()) {
            return p_178112_;
        }
        return StringTag.m_129303_(p_178112_);
    }

    @Override
    public void m_142384_(EndTag p_178124_) {
    }
}

