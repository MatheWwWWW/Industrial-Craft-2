/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package net.minecraft.data.models.blockstates;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public interface Condition
extends Supplier<JsonElement> {
    public void m_7619_(StateDefinition<?, ?> var1);

    public static TerminalCondition m_125135_() {
        return new TerminalCondition();
    }

    public static Condition m_176293_(Condition ... p_176294_) {
        return new CompositeCondition(Operation.AND, Arrays.asList(p_176294_));
    }

    public static Condition m_125137_(Condition ... p_125138_) {
        return new CompositeCondition(Operation.OR, Arrays.asList(p_125138_));
    }

    public static class TerminalCondition
    implements Condition {
        private final Map<Property<?>, String> f_125169_ = Maps.newHashMap();

        private static <T extends Comparable<T>> String m_125186_(Property<T> p_125187_, Stream<T> p_125188_) {
            return p_125188_.map(p_125187_::m_6940_).collect(Collectors.joining("|"));
        }

        private static <T extends Comparable<T>> String m_125194_(Property<T> p_125195_, T p_125196_, T[] p_125197_) {
            return TerminalCondition.m_125186_(p_125195_, Stream.concat(Stream.of(p_125196_), Stream.of(p_125197_)));
        }

        private <T extends Comparable<T>> void m_125183_(Property<T> p_125184_, String p_125185_) {
            String $$2 = this.f_125169_.put(p_125184_, p_125185_);
            if ($$2 != null) {
                throw new IllegalStateException("Tried to replace " + p_125184_ + " value from " + $$2 + " to " + p_125185_);
            }
        }

        public final <T extends Comparable<T>> TerminalCondition m_125176_(Property<T> p_125177_, T p_125178_) {
            this.m_125183_(p_125177_, p_125177_.m_6940_(p_125178_));
            return this;
        }

        @SafeVarargs
        public final <T extends Comparable<T>> TerminalCondition m_125179_(Property<T> p_125180_, T p_125181_, T ... p_125182_) {
            this.m_125183_(p_125180_, TerminalCondition.m_125194_(p_125180_, p_125181_, p_125182_));
            return this;
        }

        public final <T extends Comparable<T>> TerminalCondition m_176296_(Property<T> p_176297_, T p_176298_) {
            this.m_125183_(p_176297_, "!" + p_176297_.m_6940_(p_176298_));
            return this;
        }

        @SafeVarargs
        public final <T extends Comparable<T>> TerminalCondition m_176299_(Property<T> p_176300_, T p_176301_, T ... p_176302_) {
            this.m_125183_(p_176300_, "!" + TerminalCondition.m_125194_(p_176300_, p_176301_, p_176302_));
            return this;
        }

        @Override
        public JsonElement get() {
            JsonObject $$0 = new JsonObject();
            this.f_125169_.forEach((p_125191_, p_125192_) -> $$0.addProperty(p_125191_.m_61708_(), p_125192_));
            return $$0;
        }

        @Override
        public void m_7619_(StateDefinition<?, ?> p_125172_) {
            List $$1 = this.f_125169_.keySet().stream().filter(p_125175_ -> p_125172_.m_61081_(p_125175_.m_61708_()) != p_125175_).collect(Collectors.toList());
            if (!$$1.isEmpty()) {
                throw new IllegalStateException("Properties " + $$1 + " are missing from " + p_125172_);
            }
        }

        @Override
        public /* synthetic */ Object get() {
            return this.get();
        }
    }

    public static class CompositeCondition
    implements Condition {
        private final Operation f_125139_;
        private final List<Condition> f_125140_;

        CompositeCondition(Operation p_125142_, List<Condition> p_125143_) {
            this.f_125139_ = p_125142_;
            this.f_125140_ = p_125143_;
        }

        @Override
        public void m_7619_(StateDefinition<?, ?> p_125149_) {
            this.f_125140_.forEach(p_125152_ -> p_125152_.m_7619_(p_125149_));
        }

        @Override
        public JsonElement get() {
            JsonArray $$0 = new JsonArray();
            this.f_125140_.stream().map(Supplier::get).forEach(arg_0 -> ((JsonArray)$$0).add(arg_0));
            JsonObject $$1 = new JsonObject();
            $$1.add(this.f_125139_.f_125157_, (JsonElement)$$0);
            return $$1;
        }

        @Override
        public /* synthetic */ Object get() {
            return this.get();
        }
    }

    public static final class Operation
    extends Enum<Operation> {
        public static final /* enum */ Operation AND = new Operation("AND");
        public static final /* enum */ Operation OR = new Operation("OR");
        final String f_125157_;
        private static final /* synthetic */ Operation[] $VALUES;

        public static Operation[] values() {
            return (Operation[])$VALUES.clone();
        }

        public static Operation valueOf(String p_125167_) {
            return Enum.valueOf(Operation.class, p_125167_);
        }

        private Operation(String p_125163_) {
            this.f_125157_ = p_125163_;
        }

        private static /* synthetic */ Operation[] m_176295_() {
            return new Operation[]{AND, OR};
        }

        static {
            $VALUES = Operation.m_176295_();
        }
    }
}

