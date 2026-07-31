/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetAttributesFunction
extends LootItemConditionalFunction {
    final List<Modifier> f_80831_;

    SetAttributesFunction(LootItemCondition[] p_80833_, List<Modifier> p_80834_) {
        super(p_80833_);
        this.f_80831_ = ImmutableList.copyOf(p_80834_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80743_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return (Set)this.f_80831_.stream().flatMap(p_165234_ -> p_165234_.f_80850_.m_6231_().stream()).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80840_, LootContext p_80841_) {
        RandomSource $$2 = p_80841_.m_230907_();
        for (Modifier $$3 : this.f_80831_) {
            UUID $$4 = $$3.f_80851_;
            if ($$4 == null) {
                $$4 = UUID.randomUUID();
            }
            EquipmentSlot $$5 = Util.m_214670_($$3.f_80852_, $$2);
            p_80840_.m_41643_($$3.f_80848_, new AttributeModifier($$4, $$3.f_80847_, (double)$$3.f_80850_.m_142688_(p_80841_), $$3.f_80849_), $$5);
        }
        return p_80840_;
    }

    public static ModifierBuilder m_165235_(String p_165236_, Attribute p_165237_, AttributeModifier.Operation p_165238_, NumberProvider p_165239_) {
        return new ModifierBuilder(p_165236_, p_165237_, p_165238_, p_165239_);
    }

    public static Builder m_165241_() {
        return new Builder();
    }

    static class Modifier {
        final String f_80847_;
        final Attribute f_80848_;
        final AttributeModifier.Operation f_80849_;
        final NumberProvider f_80850_;
        @Nullable
        final UUID f_80851_;
        final EquipmentSlot[] f_80852_;

        Modifier(String p_165250_, Attribute p_165251_, AttributeModifier.Operation p_165252_, NumberProvider p_165253_, EquipmentSlot[] p_165254_, @Nullable UUID p_165255_) {
            this.f_80847_ = p_165250_;
            this.f_80848_ = p_165251_;
            this.f_80849_ = p_165252_;
            this.f_80850_ = p_165253_;
            this.f_80851_ = p_165255_;
            this.f_80852_ = p_165254_;
        }

        public JsonObject m_80865_(JsonSerializationContext p_80866_) {
            JsonObject $$1 = new JsonObject();
            $$1.addProperty("name", this.f_80847_);
            $$1.addProperty("attribute", Registry.f_122866_.m_7981_(this.f_80848_).toString());
            $$1.addProperty("operation", Modifier.m_80860_(this.f_80849_));
            $$1.add("amount", p_80866_.serialize((Object)this.f_80850_));
            if (this.f_80851_ != null) {
                $$1.addProperty("id", this.f_80851_.toString());
            }
            if (this.f_80852_.length == 1) {
                $$1.addProperty("slot", this.f_80852_[0].m_20751_());
            } else {
                JsonArray $$2 = new JsonArray();
                for (EquipmentSlot $$3 : this.f_80852_) {
                    $$2.add((JsonElement)new JsonPrimitive($$3.m_20751_()));
                }
                $$1.add("slot", (JsonElement)$$2);
            }
            return $$1;
        }

        /*
         * WARNING - void declaration
         */
        public static Modifier m_80862_(JsonObject p_80863_, JsonDeserializationContext p_80864_) {
            void $$13;
            String $$2 = GsonHelper.m_13906_(p_80863_, "name");
            ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_(p_80863_, "attribute"));
            Attribute $$4 = Registry.f_122866_.m_7745_($$3);
            if ($$4 == null) {
                throw new JsonSyntaxException("Unknown attribute: " + $$3);
            }
            AttributeModifier.Operation $$5 = Modifier.m_80869_(GsonHelper.m_13906_(p_80863_, "operation"));
            NumberProvider $$6 = GsonHelper.m_13836_(p_80863_, "amount", p_80864_, NumberProvider.class);
            UUID $$7 = null;
            if (GsonHelper.m_13813_(p_80863_, "slot")) {
                EquipmentSlot[] $$8 = new EquipmentSlot[]{EquipmentSlot.m_20747_(GsonHelper.m_13906_(p_80863_, "slot"))};
            } else if (GsonHelper.m_13885_(p_80863_, "slot")) {
                JsonArray $$9 = GsonHelper.m_13933_(p_80863_, "slot");
                EquipmentSlot[] $$10 = new EquipmentSlot[$$9.size()];
                int $$11 = 0;
                for (JsonElement $$12 : $$9) {
                    $$10[$$11++] = EquipmentSlot.m_20747_(GsonHelper.m_13805_($$12, "slot"));
                }
                if ($$10.length == 0) {
                    throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
                }
            } else {
                throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
            }
            if (p_80863_.has("id")) {
                String $$14 = GsonHelper.m_13906_(p_80863_, "id");
                try {
                    $$7 = UUID.fromString($$14);
                }
                catch (IllegalArgumentException $$15) {
                    throw new JsonSyntaxException("Invalid attribute modifier id '" + $$14 + "' (must be UUID format, with dashes)");
                }
            }
            return new Modifier($$2, $$4, $$5, $$6, (EquipmentSlot[])$$13, $$7);
        }

        private static String m_80860_(AttributeModifier.Operation p_80861_) {
            switch (p_80861_) {
                case ADDITION: {
                    return "addition";
                }
                case MULTIPLY_BASE: {
                    return "multiply_base";
                }
                case MULTIPLY_TOTAL: {
                    return "multiply_total";
                }
            }
            throw new IllegalArgumentException("Unknown operation " + p_80861_);
        }

        private static AttributeModifier.Operation m_80869_(String p_80870_) {
            switch (p_80870_) {
                case "addition": {
                    return AttributeModifier.Operation.ADDITION;
                }
                case "multiply_base": {
                    return AttributeModifier.Operation.MULTIPLY_BASE;
                }
                case "multiply_total": {
                    return AttributeModifier.Operation.MULTIPLY_TOTAL;
                }
            }
            throw new JsonSyntaxException("Unknown attribute modifier operation " + p_80870_);
        }
    }

    public static class ModifierBuilder {
        private final String f_165256_;
        private final Attribute f_165257_;
        private final AttributeModifier.Operation f_165258_;
        private final NumberProvider f_165259_;
        @Nullable
        private UUID f_165260_;
        private final Set<EquipmentSlot> f_165261_ = EnumSet.noneOf(EquipmentSlot.class);

        public ModifierBuilder(String p_165263_, Attribute p_165264_, AttributeModifier.Operation p_165265_, NumberProvider p_165266_) {
            this.f_165256_ = p_165263_;
            this.f_165257_ = p_165264_;
            this.f_165258_ = p_165265_;
            this.f_165259_ = p_165266_;
        }

        public ModifierBuilder m_165268_(EquipmentSlot p_165269_) {
            this.f_165261_.add(p_165269_);
            return this;
        }

        public ModifierBuilder m_165270_(UUID p_165271_) {
            this.f_165260_ = p_165271_;
            return this;
        }

        public Modifier m_165267_() {
            return new Modifier(this.f_165256_, this.f_165257_, this.f_165258_, this.f_165259_, this.f_165261_.toArray(new EquipmentSlot[0]), this.f_165260_);
        }
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final List<Modifier> f_165242_ = Lists.newArrayList();

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_165245_(ModifierBuilder p_165246_) {
            this.f_165242_.add(p_165246_.m_165267_());
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetAttributesFunction(this.m_80699_(), this.f_165242_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetAttributesFunction> {
        @Override
        public void m_6170_(JsonObject p_80891_, SetAttributesFunction p_80892_, JsonSerializationContext p_80893_) {
            super.m_6170_(p_80891_, p_80892_, p_80893_);
            JsonArray $$3 = new JsonArray();
            for (Modifier $$4 : p_80892_.f_80831_) {
                $$3.add((JsonElement)$$4.m_80865_(p_80893_));
            }
            p_80891_.add("modifiers", (JsonElement)$$3);
        }

        @Override
        public SetAttributesFunction m_6821_(JsonObject p_80883_, JsonDeserializationContext p_80884_, LootItemCondition[] p_80885_) {
            JsonArray $$3 = GsonHelper.m_13933_(p_80883_, "modifiers");
            ArrayList $$4 = Lists.newArrayListWithExpectedSize((int)$$3.size());
            for (JsonElement $$5 : $$3) {
                $$4.add(Modifier.m_80862_(GsonHelper.m_13918_($$5, "modifier"), p_80884_));
            }
            if ($$4.isEmpty()) {
                throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
            }
            return new SetAttributesFunction(p_80885_, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

