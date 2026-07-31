/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.task;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public interface RepeatedDelayStrategy {
    public static final RepeatedDelayStrategy f_238691_ = new RepeatedDelayStrategy(){

        @Override
        public long m_239029_() {
            return 1L;
        }

        @Override
        public long m_239153_() {
            return 1L;
        }
    };

    public long m_239029_();

    public long m_239153_();

    public static RepeatedDelayStrategy m_239255_(final int p_239256_) {
        return new RepeatedDelayStrategy(){
            private static final Logger f_238635_ = LogUtils.getLogger();
            private int f_238730_;

            @Override
            public long m_239029_() {
                this.f_238730_ = 0;
                return 1L;
            }

            @Override
            public long m_239153_() {
                ++this.f_238730_;
                long $$0 = Math.min(1L << this.f_238730_, (long)p_239256_);
                f_238635_.debug("Skipping for {} extra cycles", (Object)$$0);
                return $$0;
            }
        };
    }
}

