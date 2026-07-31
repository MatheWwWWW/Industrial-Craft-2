/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;

public class LerpingBossEvent
extends BossEvent {
    private static final long f_169019_ = 100L;
    protected float f_94286_;
    protected long f_94287_;

    public LerpingBossEvent(UUID p_169021_, Component p_169022_, float p_169023_, BossEvent.BossBarColor p_169024_, BossEvent.BossBarOverlay p_169025_, boolean p_169026_, boolean p_169027_, boolean p_169028_) {
        super(p_169021_, p_169022_, p_169024_, p_169025_);
        this.f_94286_ = p_169023_;
        this.f_146638_ = p_169023_;
        this.f_94287_ = Util.m_137550_();
        this.m_7003_(p_169026_);
        this.m_7005_(p_169027_);
        this.m_7006_(p_169028_);
    }

    @Override
    public void m_142711_(float p_169030_) {
        this.f_146638_ = this.m_142717_();
        this.f_94286_ = p_169030_;
        this.f_94287_ = Util.m_137550_();
    }

    @Override
    public float m_142717_() {
        long $$0 = Util.m_137550_() - this.f_94287_;
        float $$1 = Mth.m_14036_((float)$$0 / 100.0f, 0.0f, 1.0f);
        return Mth.m_14179_($$1, this.f_146638_, this.f_94286_);
    }
}

