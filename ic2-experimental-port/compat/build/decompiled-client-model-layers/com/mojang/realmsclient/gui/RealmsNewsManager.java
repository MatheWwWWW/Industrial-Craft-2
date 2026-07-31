/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.util.RealmsPersistence;

public class RealmsNewsManager {
    private final RealmsPersistence f_238804_;
    private boolean f_238831_;
    private String f_238573_;

    public RealmsNewsManager(RealmsPersistence p_239304_) {
        this.f_238804_ = p_239304_;
        RealmsPersistence.RealmsPersistenceData $$1 = p_239304_.m_167615_();
        this.f_238831_ = $$1.f_90176_;
        this.f_238573_ = $$1.f_90175_;
    }

    public boolean m_239499_() {
        return this.f_238831_;
    }

    public String m_240058_() {
        return this.f_238573_;
    }

    public void m_239190_(RealmsNews p_239191_) {
        RealmsPersistence.RealmsPersistenceData $$1 = this.m_240152_(p_239191_);
        this.f_238831_ = $$1.f_90176_;
        this.f_238573_ = $$1.f_90175_;
    }

    private RealmsPersistence.RealmsPersistenceData m_240152_(RealmsNews p_240153_) {
        boolean $$3;
        RealmsPersistence.RealmsPersistenceData $$1 = new RealmsPersistence.RealmsPersistenceData();
        $$1.f_90175_ = p_240153_.f_87467_;
        RealmsPersistence.RealmsPersistenceData $$2 = this.f_238804_.m_167615_();
        boolean bl = $$3 = $$1.f_90175_ == null || $$1.f_90175_.equals($$2.f_90175_);
        if ($$3) {
            return $$2;
        }
        $$1.f_90176_ = true;
        this.f_238804_.m_167616_($$1);
        return $$1;
    }
}

