/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonIOException
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  org.slf4j.Logger
 */
package net.minecraft.resources;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public interface RegistryResourceAccess {
    public <E> Map<ResourceKey<E>, EntryThunk<E>> m_214030_(ResourceKey<? extends Registry<E>> var1);

    public <E> Optional<EntryThunk<E>> m_213852_(ResourceKey<E> var1);

    public static RegistryResourceAccess m_195881_(final ResourceManager p_195882_) {
        return new RegistryResourceAccess(){
            private static final String f_195889_ = ".json";

            @Override
            public <E> Map<ResourceKey<E>, EntryThunk<E>> m_214030_(ResourceKey<? extends Registry<E>> p_214238_) {
                String $$1 = 1.m_214239_(p_214238_.m_135782_());
                HashMap $$2 = Maps.newHashMap();
                p_195882_.m_214159_($$1, p_214262_ -> p_214262_.m_135815_().endsWith(f_195889_)).forEach((p_214257_, p_214258_) -> {
                    String $$5 = p_214257_.m_135815_();
                    String $$6 = $$5.substring($$1.length() + 1, $$5.length() - f_195889_.length());
                    ResourceKey $$7 = ResourceKey.m_135785_(p_214238_, new ResourceLocation(p_214257_.m_135827_(), $$6));
                    $$2.put($$7, (p_214266_, p_214267_) -> {
                        DataResult dataResult;
                        block8: {
                            BufferedReader $$4 = p_214258_.m_215508_();
                            try {
                                dataResult = this.m_214249_((DynamicOps<JsonElement>)p_214266_, p_214267_, $$4);
                                if ($$4 == null) break block8;
                            }
                            catch (Throwable throwable) {
                                try {
                                    if ($$4 != null) {
                                        try {
                                            ((Reader)$$4).close();
                                        }
                                        catch (Throwable throwable2) {
                                            throwable.addSuppressed(throwable2);
                                        }
                                    }
                                    throw throwable;
                                }
                                catch (JsonIOException | JsonSyntaxException | IOException $$5) {
                                    return DataResult.error((String)("Failed to parse " + p_214257_ + " file: " + $$5.getMessage()));
                                }
                            }
                            ((Reader)$$4).close();
                        }
                        return dataResult;
                    });
                });
                return $$2;
            }

            @Override
            public <E> Optional<EntryThunk<E>> m_213852_(ResourceKey<E> p_214260_) {
                ResourceLocation $$1 = 1.m_214268_(p_214260_);
                return p_195882_.m_213713_($$1).map(p_214243_ -> (p_214247_, p_214248_) -> {
                    DataResult dataResult;
                    block8: {
                        BufferedReader $$4 = p_214243_.m_215508_();
                        try {
                            dataResult = this.m_214249_((DynamicOps<JsonElement>)p_214247_, p_214248_, $$4);
                            if ($$4 == null) break block8;
                        }
                        catch (Throwable throwable) {
                            try {
                                if ($$4 != null) {
                                    try {
                                        ((Reader)$$4).close();
                                    }
                                    catch (Throwable throwable2) {
                                        throwable.addSuppressed(throwable2);
                                    }
                                }
                                throw throwable;
                            }
                            catch (JsonIOException | JsonSyntaxException | IOException $$5) {
                                return DataResult.error((String)("Failed to parse " + $$1 + " file: " + $$5.getMessage()));
                            }
                        }
                        ((Reader)$$4).close();
                    }
                    return dataResult;
                });
            }

            private <E> DataResult<ParsedEntry<E>> m_214249_(DynamicOps<JsonElement> p_214250_, Decoder<E> p_214251_, Reader p_214252_) throws IOException {
                JsonElement $$3 = JsonParser.parseReader((Reader)p_214252_);
                return p_214251_.parse(p_214250_, (Object)$$3).map(ParsedEntry::m_195956_);
            }

            private static String m_214239_(ResourceLocation p_214240_) {
                return p_214240_.m_135815_();
            }

            private static <E> ResourceLocation m_214268_(ResourceKey<E> p_214269_) {
                return new ResourceLocation(p_214269_.m_135782_().m_135827_(), 1.m_214239_(p_214269_.m_211136_()) + "/" + p_214269_.m_135782_().m_135815_() + f_195889_);
            }

            public String toString() {
                return "ResourceAccess[" + p_195882_ + "]";
            }
        };
    }

    public static final class InMemoryStorage
    implements RegistryResourceAccess {
        private static final Logger f_195912_ = LogUtils.getLogger();
        private final Map<ResourceKey<?>, Entry> f_195913_ = Maps.newIdentityHashMap();

        public <E> void m_206836_(RegistryAccess p_206837_, ResourceKey<E> p_206838_, Encoder<E> p_206839_, int p_206840_, E p_206841_, Lifecycle p_206842_) {
            DataResult $$6 = p_206839_.encodeStart(RegistryOps.m_206821_(JsonOps.INSTANCE, p_206837_), p_206841_);
            Optional $$7 = $$6.error();
            if ($$7.isPresent()) {
                f_195912_.error("Error adding element: {}", (Object)((DataResult.PartialResult)$$7.get()).message());
            } else {
                this.f_195913_.put(p_206838_, new Entry((JsonElement)$$6.result().get(), p_206840_, p_206842_));
            }
        }

        @Override
        public <E> Map<ResourceKey<E>, EntryThunk<E>> m_214030_(ResourceKey<? extends Registry<E>> p_214274_) {
            return this.f_195913_.entrySet().stream().filter(p_214277_ -> ((ResourceKey)p_214277_.getKey()).m_135783_(p_214274_)).collect(Collectors.toMap(p_214287_ -> (ResourceKey)p_214287_.getKey(), p_214283_ -> ((Entry)p_214283_.getValue())::m_214288_));
        }

        @Override
        public <E> Optional<EntryThunk<E>> m_213852_(ResourceKey<E> p_214285_) {
            Entry $$1 = this.f_195913_.get(p_214285_);
            if ($$1 == null) {
                DataResult $$2 = DataResult.error((String)("Unknown element: " + p_214285_));
                return Optional.of((p_214280_, p_214281_) -> $$2);
            }
            return Optional.of($$1::m_214288_);
        }

        record Entry(JsonElement f_195936_, int f_195937_, Lifecycle f_195938_) {
            public <E> DataResult<ParsedEntry<E>> m_214288_(DynamicOps<JsonElement> p_214289_, Decoder<E> p_214290_) {
                return p_214290_.parse(p_214289_, (Object)this.f_195936_).setLifecycle(this.f_195938_).map(p_214292_ -> ParsedEntry.m_195958_(p_214292_, this.f_195937_));
            }

            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Entry.class, "data;id;lifecycle", "f_195936_", "f_195937_", "f_195938_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Entry.class, "data;id;lifecycle", "f_195936_", "f_195937_", "f_195938_"}, this);
            }

            @Override
            public final boolean equals(Object p_195947_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Entry.class, "data;id;lifecycle", "f_195936_", "f_195937_", "f_195938_"}, this, p_195947_);
            }
        }
    }

    @FunctionalInterface
    public static interface EntryThunk<E> {
        public DataResult<ParsedEntry<E>> m_214270_(DynamicOps<JsonElement> var1, Decoder<E> var2);
    }

    public record ParsedEntry<E>(E f_195950_, OptionalInt f_195951_) {
        public static <E> ParsedEntry<E> m_195956_(E p_195957_) {
            return new ParsedEntry<E>(p_195957_, OptionalInt.empty());
        }

        public static <E> ParsedEntry<E> m_195958_(E p_195959_, int p_195960_) {
            return new ParsedEntry<E>(p_195959_, OptionalInt.of(p_195960_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ParsedEntry.class, "value;fixedId", "f_195950_", "f_195951_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ParsedEntry.class, "value;fixedId", "f_195950_", "f_195951_"}, this);
        }

        @Override
        public final boolean equals(Object p_195963_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ParsedEntry.class, "value;fixedId", "f_195950_", "f_195951_"}, this, p_195963_);
        }
    }
}

