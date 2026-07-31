/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ExplorationMapFunction
extends LootItemConditionalFunction {
    static final Logger f_80523_ = LogUtils.getLogger();
    public static final TagKey<Structure> f_230983_ = StructureTags.f_215886_;
    public static final String f_165201_ = "mansion";
    public static final MapDecoration.Type f_80522_ = MapDecoration.Type.MANSION;
    public static final byte f_165202_ = 2;
    public static final int f_165203_ = 50;
    public static final boolean f_165204_ = true;
    final TagKey<Structure> f_80524_;
    final MapDecoration.Type f_80525_;
    final byte f_80526_;
    final int f_80527_;
    final boolean f_80528_;

    ExplorationMapFunction(LootItemCondition[] p_210652_, TagKey<Structure> p_210653_, MapDecoration.Type p_210654_, byte p_210655_, int p_210656_, boolean p_210657_) {
        super(p_210652_);
        this.f_80524_ = p_210653_;
        this.f_80525_ = p_210654_;
        this.f_80526_ = p_210655_;
        this.f_80527_ = p_210656_;
        this.f_80528_ = p_210657_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80745_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81460_);
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80547_, LootContext p_80548_) {
        ServerLevel $$3;
        BlockPos $$4;
        if (!p_80547_.m_150930_(Items.f_42676_)) {
            return p_80547_;
        }
        Vec3 $$2 = p_80548_.m_78953_(LootContextParams.f_81460_);
        if ($$2 != null && ($$4 = ($$3 = p_80548_.m_78952_()).m_215011_(this.f_80524_, new BlockPos($$2), this.f_80527_, this.f_80528_)) != null) {
            ItemStack $$5 = MapItem.m_42886_($$3, $$4.m_123341_(), $$4.m_123343_(), this.f_80526_, true, true);
            MapItem.m_42850_($$3, $$5);
            MapItemSavedData.m_77925_($$5, $$4, "+", this.f_80525_);
            return $$5;
        }
        return p_80547_;
    }

    public static Builder m_80554_() {
        return new Builder();
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private TagKey<Structure> f_80562_ = f_230983_;
        private MapDecoration.Type f_80563_ = f_80522_;
        private byte f_80564_ = (byte)2;
        private int f_80565_ = 50;
        private boolean f_80566_ = true;

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_210658_(TagKey<Structure> p_210659_) {
            this.f_80562_ = p_210659_;
            return this;
        }

        public Builder m_80573_(MapDecoration.Type p_80574_) {
            this.f_80563_ = p_80574_;
            return this;
        }

        public Builder m_80569_(byte p_80570_) {
            this.f_80564_ = p_80570_;
            return this;
        }

        public Builder m_165205_(int p_165206_) {
            this.f_80565_ = p_165206_;
            return this;
        }

        public Builder m_80575_(boolean p_80576_) {
            this.f_80566_ = p_80576_;
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new ExplorationMapFunction(this.m_80699_(), this.f_80562_, this.f_80563_, this.f_80564_, this.f_80565_, this.f_80566_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<ExplorationMapFunction> {
        @Override
        public void m_6170_(JsonObject p_80587_, ExplorationMapFunction p_80588_, JsonSerializationContext p_80589_) {
            super.m_6170_(p_80587_, p_80588_, p_80589_);
            if (!p_80588_.f_80524_.equals(f_230983_)) {
                p_80587_.addProperty("destination", p_80588_.f_80524_.f_203868_().toString());
            }
            if (p_80588_.f_80525_ != f_80522_) {
                p_80587_.add("decoration", p_80589_.serialize((Object)p_80588_.f_80525_.toString().toLowerCase(Locale.ROOT)));
            }
            if (p_80588_.f_80526_ != 2) {
                p_80587_.addProperty("zoom", (Number)p_80588_.f_80526_);
            }
            if (p_80588_.f_80527_ != 50) {
                p_80587_.addProperty("search_radius", (Number)p_80588_.f_80527_);
            }
            if (!p_80588_.f_80528_) {
                p_80587_.addProperty("skip_existing_chunks", Boolean.valueOf(p_80588_.f_80528_));
            }
        }

        @Override
        public ExplorationMapFunction m_6821_(JsonObject p_80583_, JsonDeserializationContext p_80584_, LootItemCondition[] p_80585_) {
            TagKey<Structure> $$3 = Serializer.m_210660_(p_80583_);
            String $$4 = p_80583_.has("decoration") ? GsonHelper.m_13906_(p_80583_, "decoration") : ExplorationMapFunction.f_165201_;
            MapDecoration.Type $$5 = f_80522_;
            try {
                $$5 = MapDecoration.Type.valueOf($$4.toUpperCase(Locale.ROOT));
            }
            catch (IllegalArgumentException $$6) {
                f_80523_.error("Error while parsing loot table decoration entry. Found {}. Defaulting to {}", (Object)$$4, (Object)f_80522_);
            }
            byte $$7 = GsonHelper.m_13816_(p_80583_, "zoom", (byte)2);
            int $$8 = GsonHelper.m_13824_(p_80583_, "search_radius", 50);
            boolean $$9 = GsonHelper.m_13855_(p_80583_, "skip_existing_chunks", true);
            return new ExplorationMapFunction(p_80585_, $$3, $$5, $$7, $$8, $$9);
        }

        private static TagKey<Structure> m_210660_(JsonObject p_210661_) {
            if (p_210661_.has("destination")) {
                String $$1 = GsonHelper.m_13906_(p_210661_, "destination");
                return TagKey.m_203882_(Registry.f_235725_, new ResourceLocation($$1));
            }
            return f_230983_;
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

