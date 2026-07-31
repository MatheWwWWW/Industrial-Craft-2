/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopyNameFunction
extends LootItemConditionalFunction {
    final NameSource f_80175_;

    CopyNameFunction(LootItemCondition[] p_80177_, NameSource p_80178_) {
        super(p_80177_);
        this.f_80175_ = p_80178_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80747_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(this.f_80175_.f_80200_);
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80185_, LootContext p_80186_) {
        Nameable $$3;
        Object $$2 = p_80186_.m_78953_(this.f_80175_.f_80200_);
        if ($$2 instanceof Nameable && ($$3 = (Nameable)$$2).m_8077_()) {
            p_80185_.m_41714_($$3.m_5446_());
        }
        return p_80185_;
    }

    public static LootItemConditionalFunction.Builder<?> m_80187_(NameSource p_80188_) {
        return CopyNameFunction.m_80683_(p_80191_ -> new CopyNameFunction((LootItemCondition[])p_80191_, p_80188_));
    }

    public static final class NameSource
    extends Enum<NameSource> {
        public static final /* enum */ NameSource THIS = new NameSource("this", LootContextParams.f_81455_);
        public static final /* enum */ NameSource KILLER = new NameSource("killer", LootContextParams.f_81458_);
        public static final /* enum */ NameSource KILLER_PLAYER = new NameSource("killer_player", LootContextParams.f_81456_);
        public static final /* enum */ NameSource BLOCK_ENTITY = new NameSource("block_entity", LootContextParams.f_81462_);
        public final String f_80199_;
        public final LootContextParam<?> f_80200_;
        private static final /* synthetic */ NameSource[] $VALUES;

        public static NameSource[] values() {
            return (NameSource[])$VALUES.clone();
        }

        public static NameSource valueOf(String p_80211_) {
            return Enum.valueOf(NameSource.class, p_80211_);
        }

        private NameSource(String p_80206_, LootContextParam<?> p_80207_) {
            this.f_80199_ = p_80206_;
            this.f_80200_ = p_80207_;
        }

        public static NameSource m_80208_(String p_80209_) {
            for (NameSource $$1 : NameSource.values()) {
                if (!$$1.f_80199_.equals(p_80209_)) continue;
                return $$1;
            }
            throw new IllegalArgumentException("Invalid name source " + p_80209_);
        }

        private static /* synthetic */ NameSource[] m_165173_() {
            return new NameSource[]{THIS, KILLER, KILLER_PLAYER, BLOCK_ENTITY};
        }

        static {
            $VALUES = NameSource.m_165173_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<CopyNameFunction> {
        @Override
        public void m_6170_(JsonObject p_80219_, CopyNameFunction p_80220_, JsonSerializationContext p_80221_) {
            super.m_6170_(p_80219_, p_80220_, p_80221_);
            p_80219_.addProperty("source", p_80220_.f_80175_.f_80199_);
        }

        @Override
        public CopyNameFunction m_6821_(JsonObject p_80215_, JsonDeserializationContext p_80216_, LootItemCondition[] p_80217_) {
            NameSource $$3 = NameSource.m_80208_(GsonHelper.m_13906_(p_80215_, "source"));
            return new CopyNameFunction(p_80217_, $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

