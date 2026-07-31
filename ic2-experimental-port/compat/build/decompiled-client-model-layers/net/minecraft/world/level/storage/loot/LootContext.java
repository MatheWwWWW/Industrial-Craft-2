/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.common.collect.Sets$SetView
 *  com.google.gson.TypeAdapter
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonWriter
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootContext {
    private final RandomSource f_78907_;
    private final float f_78908_;
    private final ServerLevel f_78909_;
    private final Function<ResourceLocation, LootTable> f_78910_;
    private final Set<LootTable> f_78911_ = Sets.newLinkedHashSet();
    private final Function<ResourceLocation, LootItemCondition> f_78912_;
    private final Set<LootItemCondition> f_78913_ = Sets.newLinkedHashSet();
    private final Map<LootContextParam<?>, Object> f_78914_;
    private final Map<ResourceLocation, DynamicDrop> f_78915_;

    LootContext(RandomSource p_230900_, float p_230901_, ServerLevel p_230902_, Function<ResourceLocation, LootTable> p_230903_, Function<ResourceLocation, LootItemCondition> p_230904_, Map<LootContextParam<?>, Object> p_230905_, Map<ResourceLocation, DynamicDrop> p_230906_) {
        this.f_78907_ = p_230900_;
        this.f_78908_ = p_230901_;
        this.f_78909_ = p_230902_;
        this.f_78910_ = p_230903_;
        this.f_78912_ = p_230904_;
        this.f_78914_ = ImmutableMap.copyOf(p_230905_);
        this.f_78915_ = ImmutableMap.copyOf(p_230906_);
    }

    public boolean m_78936_(LootContextParam<?> p_78937_) {
        return this.f_78914_.containsKey(p_78937_);
    }

    public <T> T m_165124_(LootContextParam<T> p_165125_) {
        Object $$1 = this.f_78914_.get(p_165125_);
        if ($$1 == null) {
            throw new NoSuchElementException(p_165125_.m_81284_().toString());
        }
        return (T)$$1;
    }

    public void m_78942_(ResourceLocation p_78943_, Consumer<ItemStack> p_78944_) {
        DynamicDrop $$2 = this.f_78915_.get(p_78943_);
        if ($$2 != null) {
            $$2.m_78987_(this, p_78944_);
        }
    }

    @Nullable
    public <T> T m_78953_(LootContextParam<T> p_78954_) {
        return (T)this.f_78914_.get(p_78954_);
    }

    public boolean m_78934_(LootTable p_78935_) {
        return this.f_78911_.add(p_78935_);
    }

    public void m_78946_(LootTable p_78947_) {
        this.f_78911_.remove(p_78947_);
    }

    public boolean m_78938_(LootItemCondition p_78939_) {
        return this.f_78913_.add(p_78939_);
    }

    public void m_78948_(LootItemCondition p_78949_) {
        this.f_78913_.remove(p_78949_);
    }

    public LootTable m_78940_(ResourceLocation p_78941_) {
        return this.f_78910_.apply(p_78941_);
    }

    @Nullable
    public LootItemCondition m_78950_(ResourceLocation p_78951_) {
        return this.f_78912_.apply(p_78951_);
    }

    public RandomSource m_230907_() {
        return this.f_78907_;
    }

    public float m_78945_() {
        return this.f_78908_;
    }

    public ServerLevel m_78952_() {
        return this.f_78909_;
    }

    @FunctionalInterface
    public static interface DynamicDrop {
        public void m_78987_(LootContext var1, Consumer<ItemStack> var2);
    }

    public static final class EntityTarget
    extends Enum<EntityTarget> {
        public static final /* enum */ EntityTarget THIS = new EntityTarget("this", LootContextParams.f_81455_);
        public static final /* enum */ EntityTarget KILLER = new EntityTarget("killer", LootContextParams.f_81458_);
        public static final /* enum */ EntityTarget DIRECT_KILLER = new EntityTarget("direct_killer", LootContextParams.f_81459_);
        public static final /* enum */ EntityTarget KILLER_PLAYER = new EntityTarget("killer_player", LootContextParams.f_81456_);
        final String f_78994_;
        private final LootContextParam<? extends Entity> f_78995_;
        private static final /* synthetic */ EntityTarget[] $VALUES;

        public static EntityTarget[] values() {
            return (EntityTarget[])$VALUES.clone();
        }

        public static EntityTarget valueOf(String p_79009_) {
            return Enum.valueOf(EntityTarget.class, p_79009_);
        }

        private EntityTarget(String p_79001_, LootContextParam<? extends Entity> p_79002_) {
            this.f_78994_ = p_79001_;
            this.f_78995_ = p_79002_;
        }

        public LootContextParam<? extends Entity> m_79003_() {
            return this.f_78995_;
        }

        public static EntityTarget m_79006_(String p_79007_) {
            for (EntityTarget $$1 : EntityTarget.values()) {
                if (!$$1.f_78994_.equals(p_79007_)) continue;
                return $$1;
            }
            throw new IllegalArgumentException("Invalid entity target " + p_79007_);
        }

        private static /* synthetic */ EntityTarget[] m_165126_() {
            return new EntityTarget[]{THIS, KILLER, DIRECT_KILLER, KILLER_PLAYER};
        }

        static {
            $VALUES = EntityTarget.m_165126_();
        }

        public static class Serializer
        extends TypeAdapter<EntityTarget> {
            public void write(JsonWriter p_79015_, EntityTarget p_79016_) throws IOException {
                p_79015_.value(p_79016_.f_78994_);
            }

            public EntityTarget read(JsonReader p_79013_) throws IOException {
                return EntityTarget.m_79006_(p_79013_.nextString());
            }

            public /* synthetic */ Object read(JsonReader jsonReader) throws IOException {
                return this.read(jsonReader);
            }

            public /* synthetic */ void write(JsonWriter jsonWriter, Object object) throws IOException {
                this.write(jsonWriter, (EntityTarget)((Object)object));
            }
        }
    }

    public static class Builder {
        private final ServerLevel f_78955_;
        private final Map<LootContextParam<?>, Object> f_78956_ = Maps.newIdentityHashMap();
        private final Map<ResourceLocation, DynamicDrop> f_78957_ = Maps.newHashMap();
        private RandomSource f_78958_;
        private float f_78959_;

        public Builder(ServerLevel p_78961_) {
            this.f_78955_ = p_78961_;
        }

        public Builder m_230911_(RandomSource p_230912_) {
            this.f_78958_ = p_230912_;
            return this;
        }

        public Builder m_78965_(long p_78966_) {
            if (p_78966_ != 0L) {
                this.f_78958_ = RandomSource.m_216335_(p_78966_);
            }
            return this;
        }

        public Builder m_230908_(long p_230909_, RandomSource p_230910_) {
            this.f_78958_ = p_230909_ == 0L ? p_230910_ : RandomSource.m_216335_(p_230909_);
            return this;
        }

        public Builder m_78963_(float p_78964_) {
            this.f_78959_ = p_78964_;
            return this;
        }

        public <T> Builder m_78972_(LootContextParam<T> p_78973_, T p_78974_) {
            this.f_78956_.put(p_78973_, p_78974_);
            return this;
        }

        public <T> Builder m_78984_(LootContextParam<T> p_78985_, @Nullable T p_78986_) {
            if (p_78986_ == null) {
                this.f_78956_.remove(p_78985_);
            } else {
                this.f_78956_.put(p_78985_, p_78986_);
            }
            return this;
        }

        public Builder m_78979_(ResourceLocation p_78980_, DynamicDrop p_78981_) {
            DynamicDrop $$2 = this.f_78957_.put(p_78980_, p_78981_);
            if ($$2 != null) {
                throw new IllegalStateException("Duplicated dynamic drop '" + this.f_78957_ + "'");
            }
            return this;
        }

        public ServerLevel m_78962_() {
            return this.f_78955_;
        }

        public <T> T m_78970_(LootContextParam<T> p_78971_) {
            Object $$1 = this.f_78956_.get(p_78971_);
            if ($$1 == null) {
                throw new IllegalArgumentException("No parameter " + p_78971_);
            }
            return (T)$$1;
        }

        @Nullable
        public <T> T m_78982_(LootContextParam<T> p_78983_) {
            return (T)this.f_78956_.get(p_78983_);
        }

        public LootContext m_78975_(LootContextParamSet p_78976_) {
            Sets.SetView $$1 = Sets.difference(this.f_78956_.keySet(), p_78976_.m_81398_());
            if (!$$1.isEmpty()) {
                throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + (Set)$$1);
            }
            Sets.SetView $$2 = Sets.difference(p_78976_.m_81394_(), this.f_78956_.keySet());
            if (!$$2.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters: " + (Set)$$2);
            }
            RandomSource $$3 = this.f_78958_;
            if ($$3 == null) {
                $$3 = RandomSource.m_216327_();
            }
            MinecraftServer $$4 = this.f_78955_.m_7654_();
            return new LootContext($$3, this.f_78959_, this.f_78955_, $$4.m_129898_()::m_79217_, $$4.m_129899_()::m_79252_, this.f_78956_, this.f_78957_);
        }
    }
}

