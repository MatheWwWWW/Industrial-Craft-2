/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.realms;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsLabel;

public abstract class RealmsScreen
extends Screen {
    protected static final int f_175058_ = 17;
    protected static final int f_175059_ = 20;
    protected static final int f_175060_ = 7;
    protected static final long f_175061_ = 0x140000000L;
    public static final int f_175062_ = 0xFFFFFF;
    public static final int f_175063_ = 0xA0A0A0;
    protected static final int f_175064_ = 0x4C4C4C;
    protected static final int f_175065_ = 0x6C6C6C;
    protected static final int f_175066_ = 0x7FFF7F;
    protected static final int f_175067_ = 6077788;
    protected static final int f_175068_ = 0xFF0000;
    protected static final int f_175069_ = 15553363;
    protected static final int f_175070_ = -1073741824;
    protected static final int f_175040_ = 0xCCAC5C;
    protected static final int f_175041_ = -256;
    protected static final int f_175042_ = 0x3366BB;
    protected static final int f_175043_ = 7107012;
    protected static final int f_175044_ = 8226750;
    protected static final int f_175045_ = 0xFFFFA0;
    protected static final String f_175046_ = "https://www.minecraft.net/realms/adventure-maps-in-1-9";
    protected static final int f_238765_ = 8;
    private final List<RealmsLabel> f_175057_ = Lists.newArrayList();

    public RealmsScreen(Component p_175072_) {
        super(p_175072_);
    }

    protected static int m_120774_(int p_120775_) {
        return 40 + p_120775_ * 13;
    }

    protected RealmsLabel m_175073_(RealmsLabel p_175074_) {
        this.f_175057_.add(p_175074_);
        return this.m_169394_(p_175074_);
    }

    public Component m_175075_() {
        return CommonComponents.m_178391_(this.f_175057_.stream().map(RealmsLabel::m_175034_).collect(Collectors.toList()));
    }
}

