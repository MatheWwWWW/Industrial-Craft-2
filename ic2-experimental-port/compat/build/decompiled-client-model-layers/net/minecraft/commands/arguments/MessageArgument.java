/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.SignedArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.players.PlayerList;
import org.slf4j.Logger;

public class MessageArgument
implements SignedArgument<Message> {
    private static final Collection<String> f_96829_ = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");
    private static final Logger f_232145_ = LogUtils.getLogger();

    public static MessageArgument m_96832_() {
        return new MessageArgument();
    }

    public static Component m_96835_(CommandContext<CommandSourceStack> p_96836_, String p_96837_) throws CommandSyntaxException {
        Message $$2 = (Message)p_96836_.getArgument(p_96837_, Message.class);
        return $$2.m_232196_((CommandSourceStack)p_96836_.getSource());
    }

    public static ChatMessage m_232163_(CommandContext<CommandSourceStack> p_232164_, String p_232165_) throws CommandSyntaxException {
        Message $$2 = (Message)p_232164_.getArgument(p_232165_, Message.class);
        Component $$3 = $$2.m_232196_((CommandSourceStack)p_232164_.getSource());
        CommandSigningContext $$4 = ((CommandSourceStack)p_232164_.getSource()).m_230898_();
        PlayerChatMessage $$5 = $$4.m_213987_(p_232165_);
        if ($$5 == null) {
            ChatMessageContent $$6 = new ChatMessageContent($$2.f_96841_, $$3);
            return new ChatMessage(PlayerChatMessage.m_242673_($$6));
        }
        return new ChatMessage(ChatDecorator.m_243125_($$5, $$3));
    }

    public Message parse(StringReader p_96834_) throws CommandSyntaxException {
        return Message.m_96846_(p_96834_, true);
    }

    public Collection<String> getExamples() {
        return f_96829_;
    }

    @Override
    public String m_213813_(Message p_242423_) {
        return p_242423_.m_169112_();
    }

    @Override
    public CompletableFuture<Component> m_213969_(CommandSourceStack p_232147_, Message p_232148_) throws CommandSyntaxException {
        return p_232148_.m_232194_(p_232147_);
    }

    @Override
    public Class<Message> m_213797_() {
        return Message.class;
    }

    static void m_232155_(CommandSourceStack p_232156_, CompletableFuture<?> p_232157_) {
        p_232157_.exceptionally(p_232154_ -> {
            f_232145_.error("Encountered unexpected exception while resolving chat message argument from '{}'", (Object)p_232156_.m_81357_().getString(), p_232154_);
            return null;
        });
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class Message {
        final String f_96841_;
        private final Part[] f_96842_;

        public Message(String p_96844_, Part[] p_96845_) {
            this.f_96841_ = p_96844_;
            this.f_96842_ = p_96845_;
        }

        public String m_169112_() {
            return this.f_96841_;
        }

        public Part[] m_169113_() {
            return this.f_96842_;
        }

        CompletableFuture<Component> m_232194_(CommandSourceStack p_232195_) throws CommandSyntaxException {
            Component $$1 = this.m_232196_(p_232195_);
            CompletableFuture<Component> $$2 = p_232195_.m_81377_().m_236742_().m_236961_(p_232195_.m_230896_(), $$1);
            MessageArgument.m_232155_(p_232195_, $$2);
            return $$2;
        }

        Component m_232196_(CommandSourceStack p_232197_) throws CommandSyntaxException {
            return this.m_96849_(p_232197_, p_232197_.m_6761_(2));
        }

        public Component m_96849_(CommandSourceStack p_96850_, boolean p_96851_) throws CommandSyntaxException {
            if (this.f_96842_.length == 0 || !p_96851_) {
                return Component.m_237113_(this.f_96841_);
            }
            MutableComponent $$2 = Component.m_237113_(this.f_96841_.substring(0, this.f_96842_[0].m_96859_()));
            int $$3 = this.f_96842_[0].m_96859_();
            for (Part $$4 : this.f_96842_) {
                Component $$5 = $$4.m_96860_(p_96850_);
                if ($$3 < $$4.m_96859_()) {
                    $$2.m_130946_(this.f_96841_.substring($$3, $$4.m_96859_()));
                }
                if ($$5 != null) {
                    $$2.m_7220_($$5);
                }
                $$3 = $$4.m_96862_();
            }
            if ($$3 < this.f_96841_.length()) {
                $$2.m_130946_(this.f_96841_.substring($$3));
            }
            return $$2;
        }

        /*
         * WARNING - void declaration
         */
        public static Message m_96846_(StringReader p_96847_, boolean p_96848_) throws CommandSyntaxException {
            String $$2 = p_96847_.getString().substring(p_96847_.getCursor(), p_96847_.getTotalLength());
            if (!p_96848_) {
                p_96847_.setCursor(p_96847_.getTotalLength());
                return new Message($$2, new Part[0]);
            }
            ArrayList $$3 = Lists.newArrayList();
            int $$4 = p_96847_.getCursor();
            while (p_96847_.canRead()) {
                if (p_96847_.peek() == '@') {
                    void $$9;
                    int $$5 = p_96847_.getCursor();
                    try {
                        EntitySelectorParser $$6 = new EntitySelectorParser(p_96847_);
                        EntitySelector $$7 = $$6.m_121377_();
                    }
                    catch (CommandSyntaxException $$8) {
                        if ($$8.getType() == EntitySelectorParser.f_121193_ || $$8.getType() == EntitySelectorParser.f_121191_) {
                            p_96847_.setCursor($$5 + 1);
                            continue;
                        }
                        throw $$8;
                    }
                    $$3.add(new Part($$5 - $$4, p_96847_.getCursor() - $$4, (EntitySelector)$$9));
                    continue;
                }
                p_96847_.skip();
            }
            return new Message($$2, $$3.toArray(new Part[0]));
        }
    }

    public record ChatMessage(PlayerChatMessage f_241639_) {
        public void m_241987_(CommandSourceStack p_242313_, Consumer<PlayerChatMessage> p_242409_) {
            MinecraftServer $$2 = p_242313_.m_81377_();
            p_242313_.m_241923_().m_241849_(() -> {
                CompletableFuture<FilteredText> $$3 = this.m_241079_(p_242313_, this.f_241639_.m_241775_().f_241656_());
                CompletableFuture<PlayerChatMessage> $$4 = $$2.m_236742_().m_243107_(p_242313_.m_230896_(), this.f_241639_);
                return CompletableFuture.allOf($$3, $$4).thenAcceptAsync(p_243162_ -> {
                    PlayerChatMessage $$4 = ((PlayerChatMessage)$$4.join()).m_243072_(((FilteredText)$$3.join()).f_243010_());
                    p_242409_.accept($$4);
                }, (Executor)$$2);
            });
        }

        private CompletableFuture<FilteredText> m_241079_(CommandSourceStack p_241399_, String p_241465_) {
            ServerPlayer $$2 = p_241399_.m_230896_();
            if ($$2 != null && this.f_241639_.m_243088_($$2.m_20148_())) {
                return $$2.m_8967_().m_6770_(p_241465_);
            }
            return CompletableFuture.completedFuture(FilteredText.m_243054_(p_241465_));
        }

        public void m_241074_(CommandSourceStack p_241491_) {
            if (!this.f_241639_.m_241067_().m_241005_()) {
                this.m_241987_(p_241491_, p_243158_ -> {
                    PlayerList $$2 = p_241491_.m_81377_().m_6846_();
                    $$2.m_241163_((PlayerChatMessage)p_243158_, Set.of());
                });
            }
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChatMessage.class, "signedArgument", "f_241639_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChatMessage.class, "signedArgument", "f_241639_"}, this);
        }

        @Override
        public final boolean equals(Object p_232191_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChatMessage.class, "signedArgument", "f_241639_"}, this, p_232191_);
        }
    }

    public static class Part {
        private final int f_96852_;
        private final int f_96853_;
        private final EntitySelector f_96854_;

        public Part(int p_96856_, int p_96857_, EntitySelector p_96858_) {
            this.f_96852_ = p_96856_;
            this.f_96853_ = p_96857_;
            this.f_96854_ = p_96858_;
        }

        public int m_96859_() {
            return this.f_96852_;
        }

        public int m_96862_() {
            return this.f_96853_;
        }

        public EntitySelector m_169114_() {
            return this.f_96854_;
        }

        @Nullable
        public Component m_96860_(CommandSourceStack p_96861_) throws CommandSyntaxException {
            return EntitySelector.m_175103_(this.f_96854_.m_121160_(p_96861_));
        }
    }
}

