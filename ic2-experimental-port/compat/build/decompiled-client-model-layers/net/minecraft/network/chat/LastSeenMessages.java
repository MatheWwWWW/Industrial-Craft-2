/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;

public record LastSeenMessages(List<Entry> f_241630_) {
    public static LastSeenMessages f_241634_ = new LastSeenMessages(List.of());
    public static final int f_241617_ = 5;

    public LastSeenMessages(FriendlyByteBuf p_242268_) {
        this(p_242268_.m_236838_(FriendlyByteBuf.m_182695_(ArrayList::new, 5), Entry::new));
    }

    public void m_241868_(FriendlyByteBuf p_242309_) {
        p_242309_.m_236828_(this.f_241630_, (p_242176_, p_242457_) -> p_242457_.m_241844_((FriendlyByteBuf)((Object)p_242176_)));
    }

    public void m_241953_(DataOutput p_242294_) throws IOException {
        for (Entry $$1 : this.f_241630_) {
            UUID $$2 = $$1.f_241648_();
            MessageSignature $$3 = $$1.f_241674_();
            p_242294_.writeByte(70);
            p_242294_.writeLong($$2.getMostSignificantBits());
            p_242294_.writeLong($$2.getLeastSignificantBits());
            p_242294_.write($$3.f_240884_());
        }
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{LastSeenMessages.class, "entries", "f_241630_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LastSeenMessages.class, "entries", "f_241630_"}, this);
    }

    @Override
    public final boolean equals(Object p_242428_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LastSeenMessages.class, "entries", "f_241630_"}, this, p_242428_);
    }

    public record Entry(UUID f_241648_, MessageSignature f_241674_) {
        public Entry(FriendlyByteBuf p_242242_) {
            this(p_242242_.m_130259_(), new MessageSignature(p_242242_));
        }

        public void m_241844_(FriendlyByteBuf p_242253_) {
            p_242253_.m_130077_(this.f_241648_);
            this.f_241674_.m_241011_(p_242253_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Entry.class, "profileId;lastSignature", "f_241648_", "f_241674_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Entry.class, "profileId;lastSignature", "f_241648_", "f_241674_"}, this);
        }

        @Override
        public final boolean equals(Object p_242160_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Entry.class, "profileId;lastSignature", "f_241648_", "f_241674_"}, this, p_242160_);
        }
    }

    public record Update(LastSeenMessages f_241678_, Optional<Entry> f_241661_) {
        public Update(FriendlyByteBuf p_242184_) {
            this(new LastSeenMessages(p_242184_), p_242184_.m_236860_(Entry::new));
        }

        public void m_242008_(FriendlyByteBuf p_242221_) {
            this.f_241678_.m_241868_(p_242221_);
            p_242221_.m_236835_(this.f_241661_, (p_242427_, p_242226_) -> p_242226_.m_241844_((FriendlyByteBuf)((Object)p_242427_)));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Update.class, "lastSeen;lastReceived", "f_241678_", "f_241661_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Update.class, "lastSeen;lastReceived", "f_241678_", "f_241661_"}, this);
        }

        @Override
        public final boolean equals(Object p_242333_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Update.class, "lastSeen;lastReceived", "f_241678_", "f_241661_"}, this, p_242333_);
        }
    }
}

