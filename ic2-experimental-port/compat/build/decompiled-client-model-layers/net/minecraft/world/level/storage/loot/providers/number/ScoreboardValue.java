/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.score.ContextScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import net.minecraft.world.scores.Objective;

public class ScoreboardValue
implements NumberProvider {
    final ScoreboardNameProvider f_165741_;
    final String f_165742_;
    final float f_165743_;

    ScoreboardValue(ScoreboardNameProvider p_165745_, String p_165746_, float p_165747_) {
        this.f_165741_ = p_165745_;
        this.f_165742_ = p_165746_;
        this.f_165743_ = p_165747_;
    }

    @Override
    public LootNumberProviderType m_142587_() {
        return NumberProviders.f_165734_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_165741_.m_142636_();
    }

    public static ScoreboardValue m_165749_(LootContext.EntityTarget p_165750_, String p_165751_) {
        return ScoreboardValue.m_165752_(p_165750_, p_165751_, 1.0f);
    }

    public static ScoreboardValue m_165752_(LootContext.EntityTarget p_165753_, String p_165754_, float p_165755_) {
        return new ScoreboardValue(ContextScoreboardNameProvider.m_165807_(p_165753_), p_165754_, p_165755_);
    }

    @Override
    public float m_142688_(LootContext p_165758_) {
        String $$1 = this.f_165741_.m_142600_(p_165758_);
        if ($$1 == null) {
            return 0.0f;
        }
        ServerScoreboard $$2 = p_165758_.m_78952_().m_6188_();
        Objective $$3 = $$2.m_83477_(this.f_165742_);
        if ($$3 == null) {
            return 0.0f;
        }
        if (!$$2.m_83461_($$1, $$3)) {
            return 0.0f;
        }
        return (float)$$2.m_83471_($$1, $$3).m_83400_() * this.f_165743_;
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<ScoreboardValue> {
        @Override
        public ScoreboardValue m_7561_(JsonObject p_165772_, JsonDeserializationContext p_165773_) {
            String $$2 = GsonHelper.m_13906_(p_165772_, "score");
            float $$3 = GsonHelper.m_13820_(p_165772_, "scale", 1.0f);
            ScoreboardNameProvider $$4 = GsonHelper.m_13836_(p_165772_, "target", p_165773_, ScoreboardNameProvider.class);
            return new ScoreboardValue($$4, $$2, $$3);
        }

        @Override
        public void m_6170_(JsonObject p_165764_, ScoreboardValue p_165765_, JsonSerializationContext p_165766_) {
            p_165764_.addProperty("score", p_165765_.f_165742_);
            p_165764_.add("target", p_165766_.serialize((Object)p_165765_.f_165741_));
            p_165764_.addProperty("scale", (Number)Float.valueOf(p_165765_.f_165743_));
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}

