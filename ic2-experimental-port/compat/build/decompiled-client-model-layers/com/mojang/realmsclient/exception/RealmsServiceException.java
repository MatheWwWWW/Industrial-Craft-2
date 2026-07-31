/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.resources.language.I18n;

public class RealmsServiceException
extends Exception {
    public final int f_87773_;
    public final String f_200940_;
    @Nullable
    public final RealmsError f_200941_;

    public RealmsServiceException(int p_87783_, String p_87784_, RealmsError p_87785_) {
        super(p_87784_);
        this.f_87773_ = p_87783_;
        this.f_200940_ = p_87784_;
        this.f_200941_ = p_87785_;
    }

    public RealmsServiceException(int p_200943_, String p_200944_) {
        super(p_200944_);
        this.f_87773_ = p_200943_;
        this.f_200940_ = p_200944_;
        this.f_200941_ = null;
    }

    @Override
    public String toString() {
        if (this.f_200941_ != null) {
            String $$0 = "mco.errorMessage." + this.f_200941_.m_87305_();
            String $$1 = I18n.m_118936_($$0) ? I18n.m_118938_($$0, new Object[0]) : this.f_200941_.m_87302_();
            return String.format(Locale.ROOT, "Realms service error (%d/%d) %s", this.f_87773_, this.f_200941_.m_87305_(), $$1);
        }
        return String.format(Locale.ROOT, "Realms service error (%d) %s", this.f_87773_, this.f_200940_);
    }

    public int m_200945_(int p_200946_) {
        return this.f_200941_ != null ? this.f_200941_.m_87305_() : p_200946_;
    }
}

