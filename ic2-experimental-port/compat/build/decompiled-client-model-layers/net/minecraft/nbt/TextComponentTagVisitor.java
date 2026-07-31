/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.bytes.ByteCollection
 *  it.unimi.dsi.fastutil.bytes.ByteOpenHashSet
 *  org.slf4j.Logger
 */
package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
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
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;

public class TextComponentTagVisitor
implements TagVisitor {
    private static final Logger f_178229_ = LogUtils.getLogger();
    private static final int f_178230_ = 8;
    private static final ByteCollection f_178231_ = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
    private static final ChatFormatting f_178232_ = ChatFormatting.AQUA;
    private static final ChatFormatting f_178233_ = ChatFormatting.GREEN;
    private static final ChatFormatting f_178234_ = ChatFormatting.GOLD;
    private static final ChatFormatting f_178235_ = ChatFormatting.RED;
    private static final Pattern f_178236_ = Pattern.compile("[A-Za-z0-9._+-]+");
    private static final String f_178237_ = String.valueOf(':');
    private static final String f_178238_ = String.valueOf(',');
    private static final String f_178239_ = "[";
    private static final String f_178240_ = "]";
    private static final String f_178241_ = ";";
    private static final String f_178242_ = " ";
    private static final String f_178243_ = "{";
    private static final String f_178244_ = "}";
    private static final String f_178245_ = "\n";
    private final String f_178246_;
    private final int f_178247_;
    private Component f_178248_ = CommonComponents.f_237098_;

    public TextComponentTagVisitor(String p_178251_, int p_178252_) {
        this.f_178246_ = p_178251_;
        this.f_178247_ = p_178252_;
    }

    public Component m_178281_(Tag p_178282_) {
        p_178282_.m_142327_(this);
        return this.f_178248_;
    }

    @Override
    public void m_142614_(StringTag p_178280_) {
        String $$1 = StringTag.m_129303_(p_178280_.m_7916_());
        String $$2 = $$1.substring(0, 1);
        MutableComponent $$3 = Component.m_237113_($$1.substring(1, $$1.length() - 1)).m_130940_(f_178233_);
        this.f_178248_ = Component.m_237113_($$2).m_7220_($$3).m_130946_($$2);
    }

    @Override
    public void m_141946_(ByteTag p_178258_) {
        MutableComponent $$1 = Component.m_237113_("b").m_130940_(f_178235_);
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178258_.m_8103_())).m_7220_($$1).m_130940_(f_178234_);
    }

    @Override
    public void m_142183_(ShortTag p_178278_) {
        MutableComponent $$1 = Component.m_237113_("s").m_130940_(f_178235_);
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178278_.m_8103_())).m_7220_($$1).m_130940_(f_178234_);
    }

    @Override
    public void m_142045_(IntTag p_178270_) {
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178270_.m_8103_())).m_130940_(f_178234_);
    }

    @Override
    public void m_142046_(LongTag p_178276_) {
        MutableComponent $$1 = Component.m_237113_("L").m_130940_(f_178235_);
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178276_.m_8103_())).m_7220_($$1).m_130940_(f_178234_);
    }

    @Override
    public void m_142181_(FloatTag p_178266_) {
        MutableComponent $$1 = Component.m_237113_("f").m_130940_(f_178235_);
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178266_.m_7057_())).m_7220_($$1).m_130940_(f_178234_);
    }

    @Override
    public void m_142121_(DoubleTag p_178262_) {
        MutableComponent $$1 = Component.m_237113_("d").m_130940_(f_178235_);
        this.f_178248_ = Component.m_237113_(String.valueOf(p_178262_.m_7061_())).m_7220_($$1).m_130940_(f_178234_);
    }

    @Override
    public void m_142154_(ByteArrayTag p_178256_) {
        MutableComponent $$1 = Component.m_237113_("B").m_130940_(f_178235_);
        MutableComponent $$2 = Component.m_237113_(f_178239_).m_7220_($$1).m_130946_(f_178241_);
        byte[] $$3 = p_178256_.m_128227_();
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            MutableComponent $$5 = Component.m_237113_(String.valueOf($$3[$$4])).m_130940_(f_178234_);
            $$2.m_130946_(f_178242_).m_7220_($$5).m_7220_($$1);
            if ($$4 == $$3.length - 1) continue;
            $$2.m_130946_(f_178238_);
        }
        $$2.m_130946_(f_178240_);
        this.f_178248_ = $$2;
    }

    @Override
    public void m_142251_(IntArrayTag p_178268_) {
        MutableComponent $$1 = Component.m_237113_("I").m_130940_(f_178235_);
        MutableComponent $$2 = Component.m_237113_(f_178239_).m_7220_($$1).m_130946_(f_178241_);
        int[] $$3 = p_178268_.m_128648_();
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            $$2.m_130946_(f_178242_).m_7220_(Component.m_237113_(String.valueOf($$3[$$4])).m_130940_(f_178234_));
            if ($$4 == $$3.length - 1) continue;
            $$2.m_130946_(f_178238_);
        }
        $$2.m_130946_(f_178240_);
        this.f_178248_ = $$2;
    }

    @Override
    public void m_142309_(LongArrayTag p_178274_) {
        MutableComponent $$1 = Component.m_237113_("L").m_130940_(f_178235_);
        MutableComponent $$2 = Component.m_237113_(f_178239_).m_7220_($$1).m_130946_(f_178241_);
        long[] $$3 = p_178274_.m_128851_();
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            MutableComponent $$5 = Component.m_237113_(String.valueOf($$3[$$4])).m_130940_(f_178234_);
            $$2.m_130946_(f_178242_).m_7220_($$5).m_7220_($$1);
            if ($$4 == $$3.length - 1) continue;
            $$2.m_130946_(f_178238_);
        }
        $$2.m_130946_(f_178240_);
        this.f_178248_ = $$2;
    }

    @Override
    public void m_142447_(ListTag p_178272_) {
        if (p_178272_.isEmpty()) {
            this.f_178248_ = Component.m_237113_("[]");
            return;
        }
        if (f_178231_.contains(p_178272_.m_7264_()) && p_178272_.size() <= 8) {
            String $$1 = f_178238_ + f_178242_;
            MutableComponent $$2 = Component.m_237113_(f_178239_);
            for (int $$3 = 0; $$3 < p_178272_.size(); ++$$3) {
                if ($$3 != 0) {
                    $$2.m_130946_($$1);
                }
                $$2.m_7220_(new TextComponentTagVisitor(this.f_178246_, this.f_178247_).m_178281_(p_178272_.get($$3)));
            }
            $$2.m_130946_(f_178240_);
            this.f_178248_ = $$2;
            return;
        }
        MutableComponent $$4 = Component.m_237113_(f_178239_);
        if (!this.f_178246_.isEmpty()) {
            $$4.m_130946_(f_178245_);
        }
        for (int $$5 = 0; $$5 < p_178272_.size(); ++$$5) {
            MutableComponent $$6 = Component.m_237113_(Strings.repeat((String)this.f_178246_, (int)(this.f_178247_ + 1)));
            $$6.m_7220_(new TextComponentTagVisitor(this.f_178246_, this.f_178247_ + 1).m_178281_(p_178272_.get($$5)));
            if ($$5 != p_178272_.size() - 1) {
                $$6.m_130946_(f_178238_).m_130946_(this.f_178246_.isEmpty() ? f_178242_ : f_178245_);
            }
            $$4.m_7220_($$6);
        }
        if (!this.f_178246_.isEmpty()) {
            $$4.m_130946_(f_178245_).m_130946_(Strings.repeat((String)this.f_178246_, (int)this.f_178247_));
        }
        $$4.m_130946_(f_178240_);
        this.f_178248_ = $$4;
    }

    @Override
    public void m_142303_(CompoundTag p_178260_) {
        if (p_178260_.m_128456_()) {
            this.f_178248_ = Component.m_237113_("{}");
            return;
        }
        MutableComponent $$1 = Component.m_237113_(f_178243_);
        Collection<String> $$2 = p_178260_.m_128431_();
        if (f_178229_.isDebugEnabled()) {
            ArrayList $$3 = Lists.newArrayList(p_178260_.m_128431_());
            Collections.sort($$3);
            $$2 = $$3;
        }
        if (!this.f_178246_.isEmpty()) {
            $$1.m_130946_(f_178245_);
        }
        Iterator $$4 = $$2.iterator();
        while ($$4.hasNext()) {
            String $$5 = (String)$$4.next();
            MutableComponent $$6 = Component.m_237113_(Strings.repeat((String)this.f_178246_, (int)(this.f_178247_ + 1))).m_7220_(TextComponentTagVisitor.m_178253_($$5)).m_130946_(f_178237_).m_130946_(f_178242_).m_7220_(new TextComponentTagVisitor(this.f_178246_, this.f_178247_ + 1).m_178281_(p_178260_.m_128423_($$5)));
            if ($$4.hasNext()) {
                $$6.m_130946_(f_178238_).m_130946_(this.f_178246_.isEmpty() ? f_178242_ : f_178245_);
            }
            $$1.m_7220_($$6);
        }
        if (!this.f_178246_.isEmpty()) {
            $$1.m_130946_(f_178245_).m_130946_(Strings.repeat((String)this.f_178246_, (int)this.f_178247_));
        }
        $$1.m_130946_(f_178244_);
        this.f_178248_ = $$1;
    }

    protected static Component m_178253_(String p_178254_) {
        if (f_178236_.matcher(p_178254_).matches()) {
            return Component.m_237113_(p_178254_).m_130940_(f_178232_);
        }
        String $$1 = StringTag.m_129303_(p_178254_);
        String $$2 = $$1.substring(0, 1);
        MutableComponent $$3 = Component.m_237113_($$1.substring(1, $$1.length() - 1)).m_130940_(f_178232_);
        return Component.m_237113_($$2).m_7220_($$3).m_130946_($$2);
    }

    @Override
    public void m_142384_(EndTag p_178264_) {
        this.f_178248_ = CommonComponents.f_237098_;
    }
}

