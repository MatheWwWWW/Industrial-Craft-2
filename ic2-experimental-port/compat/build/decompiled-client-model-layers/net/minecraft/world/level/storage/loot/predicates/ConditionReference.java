/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.slf4j.Logger;

public class ConditionReference
implements LootItemCondition {
    private static final Logger f_81549_ = LogUtils.getLogger();
    final ResourceLocation f_81550_;

    ConditionReference(ResourceLocation p_81553_) {
        this.f_81550_ = p_81553_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81825_;
    }

    @Override
    public void m_6169_(ValidationContext p_81560_) {
        if (p_81560_.m_79370_(this.f_81550_)) {
            p_81560_.m_79357_("Condition " + this.f_81550_ + " is recursively called");
            return;
        }
        LootItemCondition.super.m_6169_(p_81560_);
        LootItemCondition $$1 = p_81560_.m_79379_(this.f_81550_);
        if ($$1 == null) {
            p_81560_.m_79357_("Unknown condition table called " + this.f_81550_);
        } else {
            $$1.m_6169_(p_81560_.m_79359_(".{" + this.f_81550_ + "}", this.f_81550_));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean test(LootContext p_81558_) {
        LootItemCondition $$1 = p_81558_.m_78950_(this.f_81550_);
        if ($$1 == null) {
            f_81549_.warn("Tried using unknown condition table called {}", (Object)this.f_81550_);
            return false;
        }
        if (p_81558_.m_78938_($$1)) {
            try {
                boolean bl = $$1.test(p_81558_);
                return bl;
            }
            finally {
                p_81558_.m_78948_($$1);
            }
        }
        f_81549_.warn("Detected infinite loop in loot tables");
        return false;
    }

    public static LootItemCondition.Builder m_165480_(ResourceLocation p_165481_) {
        return () -> new ConditionReference(p_165481_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ConditionReference> {
        @Override
        public void m_6170_(JsonObject p_81571_, ConditionReference p_81572_, JsonSerializationContext p_81573_) {
            p_81571_.addProperty("name", p_81572_.f_81550_.toString());
        }

        @Override
        public ConditionReference m_7561_(JsonObject p_81579_, JsonDeserializationContext p_81580_) {
            ResourceLocation $$2 = new ResourceLocation(GsonHelper.m_13906_(p_81579_, "name"));
            return new ConditionReference($$2);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}

