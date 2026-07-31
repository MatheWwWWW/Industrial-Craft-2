/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class ChatPreviewCache {
    @Nullable
    private Result f_242483_;

    public void m_242647_(String p_242847_, Component p_242916_) {
        this.f_242483_ = new Result(p_242847_, p_242916_);
    }

    @Nullable
    public Component m_242657_(String p_242864_) {
        Result $$1 = this.f_242483_;
        if ($$1 != null && $$1.m_242606_(p_242864_)) {
            this.f_242483_ = null;
            return $$1.f_242490_();
        }
        return null;
    }

    record Result(String f_242485_, Component f_242490_) {
        public boolean m_242606_(String p_242915_) {
            return this.f_242485_.equals(p_242915_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Result.class, "query;preview", "f_242485_", "f_242490_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Result.class, "query;preview", "f_242485_", "f_242490_"}, this);
        }

        @Override
        public final boolean equals(Object p_242858_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Result.class, "query;preview", "f_242485_", "f_242490_"}, this, p_242858_);
        }
    }
}

