/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public interface CriterionTrigger<T extends CriterionTriggerInstance> {
    public ResourceLocation m_7295_();

    public void m_6467_(PlayerAdvancements var1, Listener<T> var2);

    public void m_6468_(PlayerAdvancements var1, Listener<T> var2);

    public void m_5656_(PlayerAdvancements var1);

    public T m_5868_(JsonObject var1, DeserializationContext var2);

    public static class Listener<T extends CriterionTriggerInstance> {
        private final T f_13678_;
        private final Advancement f_13679_;
        private final String f_13680_;

        public Listener(T p_13682_, Advancement p_13683_, String p_13684_) {
            this.f_13678_ = p_13682_;
            this.f_13679_ = p_13683_;
            this.f_13680_ = p_13684_;
        }

        public T m_13685_() {
            return this.f_13678_;
        }

        public void m_13686_(PlayerAdvancements p_13687_) {
            p_13687_.m_135988_(this.f_13679_, this.f_13680_);
        }

        public boolean equals(Object p_13689_) {
            if (this == p_13689_) {
                return true;
            }
            if (p_13689_ == null || this.getClass() != p_13689_.getClass()) {
                return false;
            }
            Listener $$1 = (Listener)p_13689_;
            if (!this.f_13678_.equals($$1.f_13678_)) {
                return false;
            }
            if (!this.f_13679_.equals($$1.f_13679_)) {
                return false;
            }
            return this.f_13680_.equals($$1.f_13680_);
        }

        public int hashCode() {
            int $$0 = this.f_13678_.hashCode();
            $$0 = 31 * $$0 + this.f_13679_.hashCode();
            $$0 = 31 * $$0 + this.f_13680_.hashCode();
            return $$0;
        }
    }
}

