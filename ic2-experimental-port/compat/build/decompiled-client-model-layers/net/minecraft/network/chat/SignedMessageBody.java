/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.HashCode
 *  com.google.common.hash.Hashing
 *  com.google.common.hash.HashingOutputStream
 */
package net.minecraft.network.chat;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.LastSeenMessages;

public record SignedMessageBody(ChatMessageContent f_240856_, Instant f_240863_, long f_240873_, LastSeenMessages f_240868_) {
    public static final byte f_240901_ = 70;

    public SignedMessageBody(FriendlyByteBuf p_241548_) {
        this(ChatMessageContent.m_241957_(p_241548_), p_241548_.m_236873_(), p_241548_.readLong(), new LastSeenMessages(p_241548_));
    }

    public void m_241161_(FriendlyByteBuf p_241357_) {
        ChatMessageContent.m_242020_(p_241357_, this.f_240856_);
        p_241357_.m_236826_(this.f_240863_);
        p_241357_.writeLong(this.f_240873_);
        this.f_240868_.m_241868_(p_241357_);
    }

    public HashCode m_241131_() {
        HashingOutputStream $$0 = new HashingOutputStream(Hashing.sha256(), OutputStream.nullOutputStream());
        try {
            DataOutputStream $$1 = new DataOutputStream((OutputStream)$$0);
            $$1.writeLong(this.f_240873_);
            $$1.writeLong(this.f_240863_.getEpochSecond());
            OutputStreamWriter $$2 = new OutputStreamWriter((OutputStream)$$1, StandardCharsets.UTF_8);
            $$2.write(this.f_240856_.f_241656_());
            $$2.flush();
            $$1.write(70);
            if (this.f_240856_.m_241978_()) {
                $$2.write(Component.Serializer.m_237122_(this.f_240856_.f_241671_()));
                $$2.flush();
            }
            this.f_240868_.m_241953_($$1);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return $$0.hash();
    }

    public SignedMessageBody m_242670_(ChatMessageContent p_242907_) {
        return new SignedMessageBody(p_242907_, this.f_240863_, this.f_240873_, this.f_240868_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SignedMessageBody.class, "content;timeStamp;salt;lastSeen", "f_240856_", "f_240863_", "f_240873_", "f_240868_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SignedMessageBody.class, "content;timeStamp;salt;lastSeen", "f_240856_", "f_240863_", "f_240873_", "f_240868_"}, this);
    }

    @Override
    public final boolean equals(Object p_241378_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SignedMessageBody.class, "content;timeStamp;salt;lastSeen", "f_240856_", "f_240863_", "f_240873_", "f_240868_"}, this, p_241378_);
    }
}

