/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 */
package net.minecraft.tags;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagNetworkSerialization {
    public static Map<ResourceKey<? extends Registry<?>>, NetworkPayload> m_203950_(RegistryAccess p_203951_) {
        return p_203951_.m_206196_().map(p_203949_ -> Pair.of(p_203949_.f_206233_(), (Object)TagNetworkSerialization.m_203942_(p_203949_.f_206234_()))).filter(p_203941_ -> !((NetworkPayload)p_203941_.getSecond()).m_203966_()).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    private static <T> NetworkPayload m_203942_(Registry<T> p_203943_) {
        HashMap<ResourceLocation, IntList> $$1 = new HashMap<ResourceLocation, IntList>();
        p_203943_.m_203612_().forEach(p_203947_ -> {
            HolderSet $$3 = (HolderSet)p_203947_.getSecond();
            IntArrayList $$4 = new IntArrayList($$3.m_203632_());
            for (Holder $$5 : $$3) {
                if ($$5.m_203376_() != Holder.Kind.REFERENCE) {
                    throw new IllegalStateException("Can't serialize unregistered value " + $$5);
                }
                $$4.add(p_203943_.m_7447_($$5.m_203334_()));
            }
            $$1.put(((TagKey)p_203947_.getFirst()).f_203868_(), (IntList)$$4);
        });
        return new NetworkPayload($$1);
    }

    public static <T> void m_203952_(ResourceKey<? extends Registry<T>> p_203953_, Registry<T> p_203954_, NetworkPayload p_203955_, TagOutput<T> p_203956_) {
        p_203955_.f_203963_.forEach((p_203961_, p_203962_) -> {
            TagKey $$5 = TagKey.m_203882_(p_203953_, p_203961_);
            List $$6 = p_203962_.intStream().mapToObj(p_203954_::m_203300_).flatMap(Optional::stream).toList();
            p_203956_.m_203971_($$5, $$6);
        });
    }

    public static final class NetworkPayload {
        final Map<ResourceLocation, IntList> f_203963_;

        NetworkPayload(Map<ResourceLocation, IntList> p_203965_) {
            this.f_203963_ = p_203965_;
        }

        public void m_203967_(FriendlyByteBuf p_203968_) {
            p_203968_.m_236831_(this.f_203963_, FriendlyByteBuf::m_130085_, FriendlyByteBuf::m_178345_);
        }

        public static NetworkPayload m_203969_(FriendlyByteBuf p_203970_) {
            return new NetworkPayload(p_203970_.m_236847_(FriendlyByteBuf::m_130281_, FriendlyByteBuf::m_178338_));
        }

        public boolean m_203966_() {
            return this.f_203963_.isEmpty();
        }
    }

    @FunctionalInterface
    public static interface TagOutput<T> {
        public void m_203971_(TagKey<T> var1, List<Holder<T>> var2);
    }
}

