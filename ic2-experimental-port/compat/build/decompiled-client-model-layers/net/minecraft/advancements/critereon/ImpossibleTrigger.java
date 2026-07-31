/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public class ImpossibleTrigger
implements CriterionTrigger<TriggerInstance> {
    static final ResourceLocation f_41555_ = new ResourceLocation("impossible");

    @Override
    public ResourceLocation m_7295_() {
        return f_41555_;
    }

    @Override
    public void m_6467_(PlayerAdvancements p_41565_, CriterionTrigger.Listener<TriggerInstance> p_41566_) {
    }

    @Override
    public void m_6468_(PlayerAdvancements p_41572_, CriterionTrigger.Listener<TriggerInstance> p_41573_) {
    }

    @Override
    public void m_5656_(PlayerAdvancements p_41563_) {
    }

    @Override
    public TriggerInstance m_5868_(JsonObject p_41569_, DeserializationContext p_41570_) {
        return new TriggerInstance();
    }

    @Override
    public /* synthetic */ CriterionTriggerInstance m_5868_(JsonObject jsonObject, DeserializationContext deserializationContext) {
        return this.m_5868_(jsonObject, deserializationContext);
    }

    public static class TriggerInstance
    implements CriterionTriggerInstance {
        @Override
        public ResourceLocation m_7294_() {
            return f_41555_;
        }

        @Override
        public JsonObject m_7683_(SerializationContext p_41577_) {
            return new JsonObject();
        }
    }
}

