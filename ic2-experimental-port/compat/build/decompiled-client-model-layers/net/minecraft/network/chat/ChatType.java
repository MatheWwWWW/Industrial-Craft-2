/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record ChatType(ChatTypeDecoration f_237011_, ChatTypeDecoration f_237013_) {
    public static final Codec<ChatType> f_237005_ = RecordCodecBuilder.create(p_240514_ -> p_240514_.group((App)ChatTypeDecoration.f_238580_.fieldOf("chat").forGetter(ChatType::f_237011_), (App)ChatTypeDecoration.f_238580_.fieldOf("narration").forGetter(ChatType::f_237013_)).apply((Applicative)p_240514_, ChatType::new));
    public static final ChatTypeDecoration f_238668_ = ChatTypeDecoration.m_239222_("chat.type.text");
    public static final ResourceKey<ChatType> f_130598_ = ChatType.m_237023_("chat");
    public static final ResourceKey<ChatType> f_237006_ = ChatType.m_237023_("say_command");
    public static final ResourceKey<ChatType> f_240674_ = ChatType.m_237023_("msg_command_incoming");
    public static final ResourceKey<ChatType> f_240668_ = ChatType.m_237023_("msg_command_outgoing");
    public static final ResourceKey<ChatType> f_241694_ = ChatType.m_237023_("team_msg_command_incoming");
    public static final ResourceKey<ChatType> f_241626_ = ChatType.m_237023_("team_msg_command_outgoing");
    public static final ResourceKey<ChatType> f_237009_ = ChatType.m_237023_("emote_command");

    private static ResourceKey<ChatType> m_237023_(String p_237024_) {
        return ResourceKey.m_135785_(Registry.f_235730_, new ResourceLocation(p_237024_));
    }

    public static Holder<ChatType> m_237021_(Registry<ChatType> p_237022_) {
        BuiltinRegistries.m_206384_(p_237022_, f_130598_, new ChatType(f_238668_, ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        BuiltinRegistries.m_206384_(p_237022_, f_237006_, new ChatType(ChatTypeDecoration.m_239222_("chat.type.announcement"), ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        BuiltinRegistries.m_206384_(p_237022_, f_240674_, new ChatType(ChatTypeDecoration.m_239424_("commands.message.display.incoming"), ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        BuiltinRegistries.m_206384_(p_237022_, f_240668_, new ChatType(ChatTypeDecoration.m_240709_("commands.message.display.outgoing"), ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        BuiltinRegistries.m_206384_(p_237022_, f_241694_, new ChatType(ChatTypeDecoration.m_239094_("chat.type.team.text"), ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        BuiltinRegistries.m_206384_(p_237022_, f_241626_, new ChatType(ChatTypeDecoration.m_239094_("chat.type.team.sent"), ChatTypeDecoration.m_239222_("chat.type.text.narrate")));
        return BuiltinRegistries.m_206384_(p_237022_, f_237009_, new ChatType(ChatTypeDecoration.m_239222_("chat.type.emote"), ChatTypeDecoration.m_239222_("chat.type.emote")));
    }

    public static Bound m_240980_(ResourceKey<ChatType> p_241279_, Entity p_241483_) {
        return ChatType.m_240968_(p_241279_, p_241483_.f_19853_.m_5962_(), p_241483_.m_5446_());
    }

    public static Bound m_241073_(ResourceKey<ChatType> p_241345_, CommandSourceStack p_241466_) {
        return ChatType.m_240968_(p_241345_, p_241466_.m_5894_(), p_241466_.m_81357_());
    }

    public static Bound m_240968_(ResourceKey<ChatType> p_241284_, RegistryAccess p_241373_, Component p_241455_) {
        Registry<ChatType> $$3 = p_241373_.m_175515_(Registry.f_235730_);
        return $$3.m_123013_(p_241284_).m_240982_(p_241455_);
    }

    public Bound m_240982_(Component p_241506_) {
        return new Bound(this, p_241506_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChatType.class, "chat;narration", "f_237011_", "f_237013_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChatType.class, "chat;narration", "f_237011_", "f_237013_"}, this);
    }

    @Override
    public final boolean equals(Object p_237028_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChatType.class, "chat;narration", "f_237011_", "f_237013_"}, this, p_237028_);
    }

    public record Bound(ChatType f_240859_, Component f_240886_, @Nullable Component f_240896_) {
        Bound(ChatType p_241377_, Component p_241447_) {
            this(p_241377_, p_241447_, null);
        }

        public Component m_240977_(Component p_241411_) {
            return this.f_240859_.f_237011_().m_240955_(p_241411_, this);
        }

        public Component m_240941_(Component p_241354_) {
            return this.f_240859_.f_237013_().m_240955_(p_241354_, this);
        }

        public Bound m_241018_(Component p_241530_) {
            return new Bound(this.f_240859_, this.f_240886_, p_241530_);
        }

        public BoundNetwork m_240987_(RegistryAccess p_241362_) {
            Registry<ChatType> $$1 = p_241362_.m_175515_(Registry.f_235730_);
            return new BoundNetwork($$1.m_7447_(this.f_240859_), this.f_240886_, this.f_240896_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Bound.class, "chatType;name;targetName", "f_240859_", "f_240886_", "f_240896_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Bound.class, "chatType;name;targetName", "f_240859_", "f_240886_", "f_240896_"}, this);
        }

        @Override
        public final boolean equals(Object p_241456_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Bound.class, "chatType;name;targetName", "f_240859_", "f_240886_", "f_240896_"}, this, p_241456_);
        }
    }

    public record BoundNetwork(int f_240870_, Component f_240862_, @Nullable Component f_240865_) {
        public BoundNetwork(FriendlyByteBuf p_241341_) {
            this(p_241341_.m_130242_(), p_241341_.m_130238_(), (Component)p_241341_.m_236868_(FriendlyByteBuf::m_130238_));
        }

        public void m_240969_(FriendlyByteBuf p_241522_) {
            p_241522_.m_130130_(this.f_240870_);
            p_241522_.m_130083_(this.f_240862_);
            p_241522_.m_236821_(this.f_240865_, FriendlyByteBuf::m_130083_);
        }

        public Optional<Bound> m_242652_(RegistryAccess p_242936_) {
            Registry<ChatType> $$1 = p_242936_.m_175515_(Registry.f_235730_);
            ChatType $$2 = (ChatType)$$1.m_7942_(this.f_240870_);
            return Optional.ofNullable($$2).map(p_242929_ -> new Bound((ChatType)p_242929_, this.f_240862_, this.f_240865_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{BoundNetwork.class, "chatType;name;targetName", "f_240870_", "f_240862_", "f_240865_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BoundNetwork.class, "chatType;name;targetName", "f_240870_", "f_240862_", "f_240865_"}, this);
        }

        @Override
        public final boolean equals(Object p_241423_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BoundNetwork.class, "chatType;name;targetName", "f_240870_", "f_240862_", "f_240865_"}, this, p_241423_);
        }
    }
}

