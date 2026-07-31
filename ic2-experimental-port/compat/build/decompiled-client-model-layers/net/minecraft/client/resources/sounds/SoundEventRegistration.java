/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.sounds;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.sounds.Sound;

public class SoundEventRegistration {
    private final List<Sound> f_119815_;
    private final boolean f_119816_;
    @Nullable
    private final String f_119817_;

    public SoundEventRegistration(List<Sound> p_119819_, boolean p_119820_, @Nullable String p_119821_) {
        this.f_119815_ = p_119819_;
        this.f_119816_ = p_119820_;
        this.f_119817_ = p_119821_;
    }

    public List<Sound> m_119822_() {
        return this.f_119815_;
    }

    public boolean m_119823_() {
        return this.f_119816_;
    }

    @Nullable
    public String m_119824_() {
        return this.f_119817_;
    }
}

