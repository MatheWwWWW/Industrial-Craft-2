/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.IntList;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;

public interface ChatLog {
    public static final int f_238746_ = -1;

    public void m_239651_(LoggedChatEvent var1);

    @Nullable
    public LoggedChatEvent m_239049_(int var1);

    @Nullable
    default public Entry<LoggedChatEvent> m_241994_(int p_242449_) {
        LoggedChatEvent $$1 = this.m_239049_(p_242449_);
        return $$1 != null ? new Entry<LoggedChatEvent>(p_242449_, $$1) : null;
    }

    default public boolean m_238950_(int p_238951_) {
        return this.m_239049_(p_238951_) != null;
    }

    public int m_239141_(int var1, int var2);

    default public int m_239679_(int p_239680_) {
        return this.m_239141_(p_239680_, -1);
    }

    default public int m_239583_(int p_239584_) {
        return this.m_239141_(p_239584_, 1);
    }

    public int m_239178_();

    public int m_239389_();

    default public Selection m_240063_() {
        return this.m_239514_(this.m_239389_());
    }

    default public Selection m_240298_() {
        return this.m_238953_(this.m_239178_());
    }

    default public Selection m_239514_(int p_239515_) {
        return this.m_239756_(p_239515_, this::m_239583_);
    }

    default public Selection m_238953_(int p_238954_) {
        return this.m_239756_(p_238954_, this::m_239679_);
    }

    default public Selection m_239412_(int p_239413_, int p_239414_) {
        if (!this.m_238950_(p_239413_) || !this.m_238950_(p_239414_)) {
            return this.m_239724_();
        }
        return this.m_239756_(p_239413_, p_239928_ -> {
            if (p_239928_ == p_239414_) {
                return -1;
            }
            return this.m_239583_(p_239928_);
        });
    }

    default public Selection m_239756_(final int p_239757_, final IntUnaryOperator p_239758_) {
        if (!this.m_238950_(p_239757_)) {
            return this.m_239724_();
        }
        return new Selection(this, new PrimitiveIterator.OfInt(){
            private int f_238661_;
            {
                this.f_238661_ = p_239757_;
            }

            @Override
            public int nextInt() {
                int $$0 = this.f_238661_;
                this.f_238661_ = p_239758_.applyAsInt($$0);
                return $$0;
            }

            @Override
            public boolean hasNext() {
                return this.f_238661_ != -1;
            }
        });
    }

    private Selection m_239724_() {
        return new Selection(this, (PrimitiveIterator.OfInt)IntList.of().iterator());
    }

    public record Entry<T extends LoggedChatEvent>(int f_241698_, T f_241599_) {
        @Nullable
        public <U extends LoggedChatEvent> Entry<U> m_241867_(Class<U> p_242327_) {
            if (p_242327_.isInstance(this.f_241599_)) {
                return new Entry<LoggedChatEvent>(this.f_241698_, (LoggedChatEvent)p_242327_.cast(this.f_241599_));
            }
            return null;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Entry.class, "id;event", "f_241698_", "f_241599_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Entry.class, "id;event", "f_241698_", "f_241599_"}, this);
        }

        @Override
        public final boolean equals(Object p_242237_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Entry.class, "id;event", "f_241698_", "f_241599_"}, this, p_242237_);
        }
    }

    public static class Selection {
        private static final int f_238792_ = 1041;
        private final ChatLog f_238754_;
        private final PrimitiveIterator.OfInt f_238629_;

        Selection(ChatLog p_239661_, PrimitiveIterator.OfInt p_239662_) {
            this.f_238754_ = p_239661_;
            this.f_238629_ = p_239662_;
        }

        public IntStream m_239032_() {
            return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(this.f_238629_, 1041), false);
        }

        public Stream<LoggedChatEvent> m_239119_() {
            return this.m_239032_().mapToObj(this.f_238754_::m_239049_).filter(Objects::nonNull);
        }

        public Collection<GameProfile> m_240328_() {
            return this.m_239119_().map(p_243150_ -> {
                LoggedChatMessage.Player $$1;
                if (p_243150_ instanceof LoggedChatMessage.Player && ($$1 = (LoggedChatMessage.Player)p_243150_).m_241866_($$1.f_241668_().getId())) {
                    return $$1.f_241668_();
                }
                return null;
            }).filter(Objects::nonNull).distinct().toList();
        }

        public Stream<Entry<LoggedChatEvent>> m_240148_() {
            return this.m_239032_().mapToObj(this.f_238754_::m_241994_).filter(Objects::nonNull);
        }
    }
}

