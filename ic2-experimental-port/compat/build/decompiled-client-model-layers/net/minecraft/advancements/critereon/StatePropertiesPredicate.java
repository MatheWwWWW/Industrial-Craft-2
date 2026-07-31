/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;

public class StatePropertiesPredicate {
    public static final StatePropertiesPredicate f_67658_ = new StatePropertiesPredicate((List<PropertyMatcher>)ImmutableList.of());
    private final List<PropertyMatcher> f_67659_;

    private static PropertyMatcher m_67686_(String p_67687_, JsonElement p_67688_) {
        if (p_67688_.isJsonPrimitive()) {
            String $$2 = p_67688_.getAsString();
            return new ExactPropertyMatcher(p_67687_, $$2);
        }
        JsonObject $$3 = GsonHelper.m_13918_(p_67688_, "value");
        String $$4 = $$3.has("min") ? StatePropertiesPredicate.m_67689_($$3.get("min")) : null;
        String $$5 = $$3.has("max") ? StatePropertiesPredicate.m_67689_($$3.get("max")) : null;
        return $$4 != null && $$4.equals($$5) ? new ExactPropertyMatcher(p_67687_, $$4) : new RangedPropertyMatcher(p_67687_, $$4, $$5);
    }

    @Nullable
    private static String m_67689_(JsonElement p_67690_) {
        if (p_67690_.isJsonNull()) {
            return null;
        }
        return p_67690_.getAsString();
    }

    StatePropertiesPredicate(List<PropertyMatcher> p_67662_) {
        this.f_67659_ = ImmutableList.copyOf(p_67662_);
    }

    public <S extends StateHolder<?, S>> boolean m_67669_(StateDefinition<?, S> p_67670_, S p_67671_) {
        for (PropertyMatcher $$2 : this.f_67659_) {
            if ($$2.m_67718_(p_67670_, p_67671_)) continue;
            return false;
        }
        return true;
    }

    public boolean m_67667_(BlockState p_67668_) {
        return this.m_67669_(p_67668_.m_60734_().m_49965_(), p_67668_);
    }

    public boolean m_67684_(FluidState p_67685_) {
        return this.m_67669_(p_67685_.m_76152_().m_76144_(), p_67685_);
    }

    public void m_67672_(StateDefinition<?, ?> p_67673_, Consumer<String> p_67674_) {
        this.f_67659_.forEach(p_67678_ -> p_67678_.m_67721_(p_67673_, p_67674_));
    }

    public static StatePropertiesPredicate m_67679_(@Nullable JsonElement p_67680_) {
        if (p_67680_ == null || p_67680_.isJsonNull()) {
            return f_67658_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_67680_, "properties");
        ArrayList $$2 = Lists.newArrayList();
        for (Map.Entry $$3 : $$1.entrySet()) {
            $$2.add(StatePropertiesPredicate.m_67686_((String)$$3.getKey(), (JsonElement)$$3.getValue()));
        }
        return new StatePropertiesPredicate($$2);
    }

    public JsonElement m_67666_() {
        if (this == f_67658_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (!this.f_67659_.isEmpty()) {
            this.f_67659_.forEach(p_67683_ -> $$0.add(p_67683_.m_67726_(), p_67683_.m_7682_()));
        }
        return $$0;
    }

    static class ExactPropertyMatcher
    extends PropertyMatcher {
        private final String f_67707_;

        public ExactPropertyMatcher(String p_67709_, String p_67710_) {
            super(p_67709_);
            this.f_67707_ = p_67710_;
        }

        @Override
        protected <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> p_67713_, Property<T> p_67714_) {
            Comparable $$2 = p_67713_.m_61143_(p_67714_);
            Optional<T> $$3 = p_67714_.m_6215_(this.f_67707_);
            return $$3.isPresent() && $$2.compareTo((Comparable)((Comparable)$$3.get())) == 0;
        }

        @Override
        public JsonElement m_7682_() {
            return new JsonPrimitive(this.f_67707_);
        }
    }

    static class RangedPropertyMatcher
    extends PropertyMatcher {
        @Nullable
        private final String f_67727_;
        @Nullable
        private final String f_67728_;

        public RangedPropertyMatcher(String p_67730_, @Nullable String p_67731_, @Nullable String p_67732_) {
            super(p_67730_);
            this.f_67727_ = p_67731_;
            this.f_67728_ = p_67732_;
        }

        @Override
        protected <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> p_67735_, Property<T> p_67736_) {
            Optional<T> $$4;
            Optional<T> $$3;
            Comparable $$2 = p_67735_.m_61143_(p_67736_);
            if (!(this.f_67727_ == null || ($$3 = p_67736_.m_6215_(this.f_67727_)).isPresent() && $$2.compareTo((Comparable)((Comparable)$$3.get())) >= 0)) {
                return false;
            }
            return this.f_67728_ == null || ($$4 = p_67736_.m_6215_(this.f_67728_)).isPresent() && $$2.compareTo((Comparable)((Comparable)$$4.get())) <= 0;
        }

        @Override
        public JsonElement m_7682_() {
            JsonObject $$0 = new JsonObject();
            if (this.f_67727_ != null) {
                $$0.addProperty("min", this.f_67727_);
            }
            if (this.f_67728_ != null) {
                $$0.addProperty("max", this.f_67728_);
            }
            return $$0;
        }
    }

    static abstract class PropertyMatcher {
        private final String f_67715_;

        public PropertyMatcher(String p_67717_) {
            this.f_67715_ = p_67717_;
        }

        public <S extends StateHolder<?, S>> boolean m_67718_(StateDefinition<?, S> p_67719_, S p_67720_) {
            Property<?> $$2 = p_67719_.m_61081_(this.f_67715_);
            if ($$2 == null) {
                return false;
            }
            return this.m_7517_(p_67720_, $$2);
        }

        protected abstract <T extends Comparable<T>> boolean m_7517_(StateHolder<?, ?> var1, Property<T> var2);

        public abstract JsonElement m_7682_();

        public String m_67726_() {
            return this.f_67715_;
        }

        public void m_67721_(StateDefinition<?, ?> p_67722_, Consumer<String> p_67723_) {
            Property<?> $$2 = p_67722_.m_61081_(this.f_67715_);
            if ($$2 == null) {
                p_67723_.accept(this.f_67715_);
            }
        }
    }

    public static class Builder {
        private final List<PropertyMatcher> f_67691_ = Lists.newArrayList();

        private Builder() {
        }

        public static Builder m_67693_() {
            return new Builder();
        }

        public Builder m_67700_(Property<?> p_67701_, String p_67702_) {
            this.f_67691_.add(new ExactPropertyMatcher(p_67701_.m_61708_(), p_67702_));
            return this;
        }

        public Builder m_67694_(Property<Integer> p_67695_, int p_67696_) {
            return this.m_67700_(p_67695_, Integer.toString(p_67696_));
        }

        public Builder m_67703_(Property<Boolean> p_67704_, boolean p_67705_) {
            return this.m_67700_(p_67704_, Boolean.toString(p_67705_));
        }

        public <T extends Comparable<T> & StringRepresentable> Builder m_67697_(Property<T> p_67698_, T p_67699_) {
            return this.m_67700_(p_67698_, ((StringRepresentable)p_67699_).m_7912_());
        }

        public StatePropertiesPredicate m_67706_() {
            return new StatePropertiesPredicate(this.f_67691_);
        }
    }
}

