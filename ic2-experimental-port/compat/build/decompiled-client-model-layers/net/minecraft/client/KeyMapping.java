/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class KeyMapping
implements Comparable<KeyMapping> {
    private static final Map<String, KeyMapping> f_90809_ = Maps.newHashMap();
    private static final Map<InputConstants.Key, KeyMapping> f_90810_ = Maps.newHashMap();
    private static final Set<String> f_90811_ = Sets.newHashSet();
    public static final String f_167805_ = "key.categories.movement";
    public static final String f_167806_ = "key.categories.misc";
    public static final String f_167807_ = "key.categories.multiplayer";
    public static final String f_167808_ = "key.categories.gameplay";
    public static final String f_167809_ = "key.categories.inventory";
    public static final String f_167810_ = "key.categories.ui";
    public static final String f_167811_ = "key.categories.creative";
    private static final Map<String, Integer> f_90812_ = Util.m_137469_(Maps.newHashMap(), p_90845_ -> {
        p_90845_.put(f_167805_, 1);
        p_90845_.put(f_167808_, 2);
        p_90845_.put(f_167809_, 3);
        p_90845_.put(f_167811_, 4);
        p_90845_.put(f_167807_, 5);
        p_90845_.put(f_167810_, 6);
        p_90845_.put(f_167806_, 7);
    });
    private final String f_90813_;
    private final InputConstants.Key f_90814_;
    private final String f_90815_;
    private InputConstants.Key f_90816_;
    private boolean f_90817_;
    private int f_90818_;

    public static void m_90835_(InputConstants.Key p_90836_) {
        KeyMapping $$1 = f_90810_.get(p_90836_);
        if ($$1 != null) {
            ++$$1.f_90818_;
        }
    }

    public static void m_90837_(InputConstants.Key p_90838_, boolean p_90839_) {
        KeyMapping $$2 = f_90810_.get(p_90838_);
        if ($$2 != null) {
            $$2.m_7249_(p_90839_);
        }
    }

    public static void m_90829_() {
        for (KeyMapping $$0 : f_90809_.values()) {
            if ($$0.f_90816_.m_84868_() != InputConstants.Type.KEYSYM || $$0.f_90816_.m_84873_() == InputConstants.f_84822_.m_84873_()) continue;
            $$0.m_7249_(InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), $$0.f_90816_.m_84873_()));
        }
    }

    public static void m_90847_() {
        for (KeyMapping $$0 : f_90809_.values()) {
            $$0.m_90866_();
        }
    }

    public static void m_90854_() {
        f_90810_.clear();
        for (KeyMapping $$0 : f_90809_.values()) {
            f_90810_.put($$0.f_90816_, $$0);
        }
    }

    public KeyMapping(String p_90821_, int p_90822_, String p_90823_) {
        this(p_90821_, InputConstants.Type.KEYSYM, p_90822_, p_90823_);
    }

    public KeyMapping(String p_90825_, InputConstants.Type p_90826_, int p_90827_, String p_90828_) {
        this.f_90813_ = p_90825_;
        this.f_90814_ = this.f_90816_ = p_90826_.m_84895_(p_90827_);
        this.f_90815_ = p_90828_;
        f_90809_.put(p_90825_, this);
        f_90810_.put(this.f_90816_, this);
        f_90811_.add(p_90828_);
    }

    public boolean m_90857_() {
        return this.f_90817_;
    }

    public String m_90858_() {
        return this.f_90815_;
    }

    public boolean m_90859_() {
        if (this.f_90818_ == 0) {
            return false;
        }
        --this.f_90818_;
        return true;
    }

    private void m_90866_() {
        this.f_90818_ = 0;
        this.m_7249_(false);
    }

    public String m_90860_() {
        return this.f_90813_;
    }

    public InputConstants.Key m_90861_() {
        return this.f_90814_;
    }

    public void m_90848_(InputConstants.Key p_90849_) {
        this.f_90816_ = p_90849_;
    }

    @Override
    public int compareTo(KeyMapping p_90841_) {
        if (this.f_90815_.equals(p_90841_.f_90815_)) {
            return I18n.m_118938_(this.f_90813_, new Object[0]).compareTo(I18n.m_118938_(p_90841_.f_90813_, new Object[0]));
        }
        return f_90812_.get(this.f_90815_).compareTo(f_90812_.get(p_90841_.f_90815_));
    }

    public static Supplier<Component> m_90842_(String p_90843_) {
        KeyMapping $$1 = f_90809_.get(p_90843_);
        if ($$1 == null) {
            return () -> Component.m_237115_(p_90843_);
        }
        return $$1::m_90863_;
    }

    public boolean m_90850_(KeyMapping p_90851_) {
        return this.f_90816_.equals(p_90851_.f_90816_);
    }

    public boolean m_90862_() {
        return this.f_90816_.equals(InputConstants.f_84822_);
    }

    public boolean m_90832_(int p_90833_, int p_90834_) {
        if (p_90833_ == InputConstants.f_84822_.m_84873_()) {
            return this.f_90816_.m_84868_() == InputConstants.Type.SCANCODE && this.f_90816_.m_84873_() == p_90834_;
        }
        return this.f_90816_.m_84868_() == InputConstants.Type.KEYSYM && this.f_90816_.m_84873_() == p_90833_;
    }

    public boolean m_90830_(int p_90831_) {
        return this.f_90816_.m_84868_() == InputConstants.Type.MOUSE && this.f_90816_.m_84873_() == p_90831_;
    }

    public Component m_90863_() {
        return this.f_90816_.m_84875_();
    }

    public boolean m_90864_() {
        return this.f_90816_.equals(this.f_90814_);
    }

    public String m_90865_() {
        return this.f_90816_.m_84874_();
    }

    public void m_7249_(boolean p_90846_) {
        this.f_90817_ = p_90846_;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((KeyMapping)object);
    }
}

