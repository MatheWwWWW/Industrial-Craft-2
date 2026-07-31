/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.lwjgl.openal.AL
 *  org.lwjgl.openal.AL10
 *  org.lwjgl.openal.ALC
 *  org.lwjgl.openal.ALC10
 *  org.lwjgl.openal.ALC11
 *  org.lwjgl.openal.ALCCapabilities
 *  org.lwjgl.openal.ALCapabilities
 *  org.lwjgl.openal.ALUtil
 *  org.lwjgl.openal.SOFTHRTF
 *  org.lwjgl.system.MemoryStack
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.audio;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Listener;
import com.mojang.blaze3d.audio.OpenAlUtil;
import com.mojang.logging.LogUtils;
import java.nio.IntBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.util.Mth;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.openal.ALUtil;
import org.lwjgl.openal.SOFTHRTF;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;

public class Library {
    static final Logger f_83685_ = LogUtils.getLogger();
    private static final int f_193464_ = 0;
    private static final int f_166128_ = 30;
    private long f_193465_;
    private long f_83687_;
    private boolean f_193466_;
    @Nullable
    private String f_193467_;
    private static final ChannelPool f_83688_ = new ChannelPool(){

        @Override
        @Nullable
        public Channel m_5574_() {
            return null;
        }

        @Override
        public boolean m_5658_(Channel p_83708_) {
            return false;
        }

        @Override
        public void m_6471_() {
        }

        @Override
        public int m_6492_() {
            return 0;
        }

        @Override
        public int m_6500_() {
            return 0;
        }
    };
    private ChannelPool f_83689_ = f_83688_;
    private ChannelPool f_83690_ = f_83688_;
    private final Listener f_83691_ = new Listener();

    public Library() {
        this.f_193467_ = Library.m_193468_();
    }

    public void m_231084_(@Nullable String p_231085_, boolean p_231086_) {
        this.f_193465_ = Library.m_193472_(p_231085_);
        this.f_193466_ = ALC10.alcIsExtensionPresent((long)this.f_193465_, (CharSequence)"ALC_EXT_disconnect");
        ALCCapabilities $$2 = ALC.createCapabilities((long)this.f_193465_);
        if (OpenAlUtil.m_83784_(this.f_193465_, "Get capabilities")) {
            throw new IllegalStateException("Failed to get OpenAL capabilities");
        }
        if (!$$2.OpenALC11) {
            throw new IllegalStateException("OpenAL 1.1 not supported");
        }
        this.m_241879_($$2.ALC_SOFT_HRTF && p_231086_);
        this.f_83687_ = ALC10.alcCreateContext((long)this.f_193465_, (IntBuffer)null);
        ALC10.alcMakeContextCurrent((long)this.f_83687_);
        int $$3 = this.m_83703_();
        int $$4 = Mth.m_14045_((int)Mth.m_14116_($$3), 2, 8);
        int $$5 = Mth.m_14045_($$3 - $$4, 8, 255);
        this.f_83689_ = new CountingChannelPool($$5);
        this.f_83690_ = new CountingChannelPool($$4);
        ALCapabilities $$6 = AL.createCapabilities((ALCCapabilities)$$2);
        OpenAlUtil.m_83787_("Initialization");
        if (!$$6.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
        }
        AL10.alEnable((int)512);
        if (!$$6.AL_EXT_LINEAR_DISTANCE) {
            throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
        }
        OpenAlUtil.m_83787_("Enable per-source distance models");
        f_83685_.info("OpenAL initialized on device {}", (Object)this.m_193471_());
    }

    private void m_241879_(boolean p_242278_) {
        int $$1 = ALC10.alcGetInteger((long)this.f_193465_, (int)6548);
        if ($$1 > 0) {
            try (MemoryStack $$2 = MemoryStack.stackPush();){
                IntBuffer $$3 = $$2.callocInt(10).put(6546).put(p_242278_ ? 1 : 0).put(6550).put(0).put(0).flip();
                if (!SOFTHRTF.alcResetDeviceSOFT((long)this.f_193465_, (IntBuffer)$$3)) {
                    f_83685_.warn("Failed to reset device: {}", (Object)ALC10.alcGetString((long)this.f_193465_, (int)ALC10.alcGetError((long)this.f_193465_)));
                }
            }
        }
    }

    private int m_83703_() {
        try (MemoryStack $$0 = MemoryStack.stackPush();){
            int $$1 = ALC10.alcGetInteger((long)this.f_193465_, (int)4098);
            if (OpenAlUtil.m_83784_(this.f_193465_, "Get attributes size")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            IntBuffer $$2 = $$0.mallocInt($$1);
            ALC10.alcGetIntegerv((long)this.f_193465_, (int)4099, (IntBuffer)$$2);
            if (OpenAlUtil.m_83784_(this.f_193465_, "Get attributes")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            int $$3 = 0;
            while ($$3 < $$1) {
                int $$4;
                if (($$4 = $$2.get($$3++)) == 0) {
                    break;
                }
                int $$5 = $$2.get($$3++);
                if ($$4 != 4112) continue;
                int n = $$5;
                return n;
            }
        }
        return 30;
    }

    @Nullable
    public static String m_193468_() {
        if (!ALC10.alcIsExtensionPresent((long)0L, (CharSequence)"ALC_ENUMERATE_ALL_EXT")) {
            return null;
        }
        ALUtil.getStringList((long)0L, (int)4115);
        return ALC10.alcGetString((long)0L, (int)4114);
    }

    public String m_193471_() {
        String $$0 = ALC10.alcGetString((long)this.f_193465_, (int)4115);
        if ($$0 == null) {
            $$0 = ALC10.alcGetString((long)this.f_193465_, (int)4101);
        }
        if ($$0 == null) {
            $$0 = "Unknown";
        }
        return $$0;
    }

    public synchronized boolean m_193474_() {
        String $$0 = Library.m_193468_();
        if (Objects.equals(this.f_193467_, $$0)) {
            return false;
        }
        this.f_193467_ = $$0;
        return true;
    }

    private static long m_193472_(@Nullable String p_193473_) {
        OptionalLong $$1 = OptionalLong.empty();
        if (p_193473_ != null) {
            $$1 = Library.m_193475_(p_193473_);
        }
        if ($$1.isEmpty()) {
            $$1 = Library.m_193475_(Library.m_193468_());
        }
        if ($$1.isEmpty()) {
            $$1 = Library.m_193475_(null);
        }
        if ($$1.isEmpty()) {
            throw new IllegalStateException("Failed to open OpenAL device");
        }
        return $$1.getAsLong();
    }

    private static OptionalLong m_193475_(@Nullable String p_193476_) {
        long $$1 = ALC10.alcOpenDevice((CharSequence)p_193476_);
        if ($$1 != 0L && !OpenAlUtil.m_83784_($$1, "Open device")) {
            return OptionalLong.of($$1);
        }
        return OptionalLong.empty();
    }

    public void m_83699_() {
        this.f_83689_.m_6471_();
        this.f_83690_.m_6471_();
        ALC10.alcDestroyContext((long)this.f_83687_);
        if (this.f_193465_ != 0L) {
            ALC10.alcCloseDevice((long)this.f_193465_);
        }
    }

    public Listener m_83700_() {
        return this.f_83691_;
    }

    @Nullable
    public Channel m_83697_(Pool p_83698_) {
        return (p_83698_ == Pool.STREAMING ? this.f_83690_ : this.f_83689_).m_5574_();
    }

    public void m_83695_(Channel p_83696_) {
        if (!this.f_83689_.m_5658_(p_83696_) && !this.f_83690_.m_5658_(p_83696_)) {
            throw new IllegalStateException("Tried to release unknown channel");
        }
    }

    public String m_83701_() {
        return String.format(Locale.ROOT, "Sounds: %d/%d + %d/%d", this.f_83689_.m_6500_(), this.f_83689_.m_6492_(), this.f_83690_.m_6500_(), this.f_83690_.m_6492_());
    }

    public List<String> m_193477_() {
        List $$0 = ALUtil.getStringList((long)0L, (int)4115);
        if ($$0 == null) {
            return Collections.emptyList();
        }
        return $$0;
    }

    public boolean m_193478_() {
        return this.f_193466_ && ALC11.alcGetInteger((long)this.f_193465_, (int)787) == 0;
    }

    static interface ChannelPool {
        @Nullable
        public Channel m_5574_();

        public boolean m_5658_(Channel var1);

        public void m_6471_();

        public int m_6492_();

        public int m_6500_();
    }

    static class CountingChannelPool
    implements ChannelPool {
        private final int f_83713_;
        private final Set<Channel> f_83714_ = Sets.newIdentityHashSet();

        public CountingChannelPool(int p_83716_) {
            this.f_83713_ = p_83716_;
        }

        @Override
        @Nullable
        public Channel m_5574_() {
            if (this.f_83714_.size() >= this.f_83713_) {
                if (SharedConstants.f_136183_) {
                    f_83685_.warn("Maximum sound pool size {} reached", (Object)this.f_83713_);
                }
                return null;
            }
            Channel $$0 = Channel.m_83649_();
            if ($$0 != null) {
                this.f_83714_.add($$0);
            }
            return $$0;
        }

        @Override
        public boolean m_5658_(Channel p_83719_) {
            if (!this.f_83714_.remove(p_83719_)) {
                return false;
            }
            p_83719_.m_83665_();
            return true;
        }

        @Override
        public void m_6471_() {
            this.f_83714_.forEach(Channel::m_83665_);
            this.f_83714_.clear();
        }

        @Override
        public int m_6492_() {
            return this.f_83713_;
        }

        @Override
        public int m_6500_() {
            return this.f_83714_.size();
        }
    }

    public static final class Pool
    extends Enum<Pool> {
        public static final /* enum */ Pool STATIC = new Pool();
        public static final /* enum */ Pool STREAMING = new Pool();
        private static final /* synthetic */ Pool[] $VALUES;

        public static Pool[] values() {
            return (Pool[])$VALUES.clone();
        }

        public static Pool valueOf(String p_83731_) {
            return Enum.valueOf(Pool.class, p_83731_);
        }

        private static /* synthetic */ Pool[] m_166129_() {
            return new Pool[]{STATIC, STREAMING};
        }

        static {
            $VALUES = Pool.m_166129_();
        }
    }
}

