/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.report.AbuseReport
 *  com.mojang.authlib.minecraft.report.AbuseReportLimits
 *  com.mojang.authlib.minecraft.report.ReportChatMessage
 *  com.mojang.authlib.minecraft.report.ReportChatMessageBody
 *  com.mojang.authlib.minecraft.report.ReportChatMessageBody$LastSeenSignature
 *  com.mojang.authlib.minecraft.report.ReportChatMessageContent
 *  com.mojang.authlib.minecraft.report.ReportChatMessageHeader
 *  com.mojang.authlib.minecraft.report.ReportEvidence
 *  com.mojang.authlib.minecraft.report.ReportedEntity
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntArrayPriorityQueue
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntComparators
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.minecraft.report.AbuseReport;
import com.mojang.authlib.minecraft.report.AbuseReportLimits;
import com.mojang.authlib.minecraft.report.ReportChatMessage;
import com.mojang.authlib.minecraft.report.ReportChatMessageBody;
import com.mojang.authlib.minecraft.report.ReportChatMessageContent;
import com.mojang.authlib.minecraft.report.ReportChatMessageHeader;
import com.mojang.authlib.minecraft.report.ReportEvidence;
import com.mojang.authlib.minecraft.report.ReportedEntity;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayPriorityQueue;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.client.multiplayer.chat.LoggedChatMessageLink;
import net.minecraft.client.multiplayer.chat.report.ReportReason;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;

public class ChatReportBuilder {
    private final UUID f_241684_;
    private final Instant f_238740_;
    private final UUID f_238574_;
    private final AbuseReportLimits f_238736_;
    private final IntSet f_238766_ = new IntOpenHashSet();
    private String f_238576_ = "";
    @Nullable
    private ReportReason f_238594_;

    private ChatReportBuilder(UUID p_239204_, Instant p_239205_, UUID p_239206_, AbuseReportLimits p_239207_) {
        this.f_241684_ = p_239204_;
        this.f_238740_ = p_239205_;
        this.f_238574_ = p_239206_;
        this.f_238736_ = p_239207_;
    }

    public ChatReportBuilder(UUID p_239528_, AbuseReportLimits p_239529_) {
        this(UUID.randomUUID(), Instant.now(), p_239528_, p_239529_);
    }

    public void m_239079_(String p_239080_) {
        this.f_238576_ = p_239080_;
    }

    public void m_239097_(ReportReason p_239098_) {
        this.f_238594_ = p_239098_;
    }

    public void m_239051_(int p_239052_) {
        if (this.f_238766_.contains(p_239052_)) {
            this.f_238766_.remove(p_239052_);
        } else if (this.f_238766_.size() < this.f_238736_.maxReportedMessageCount()) {
            this.f_238766_.add(p_239052_);
        }
    }

    public UUID m_239436_() {
        return this.f_238574_;
    }

    public IntSet m_239716_() {
        return this.f_238766_;
    }

    public String m_238976_() {
        return this.f_238576_;
    }

    @Nullable
    public ReportReason m_239339_() {
        return this.f_238594_;
    }

    public boolean m_240221_(int p_243333_) {
        return this.f_238766_.contains(p_243333_);
    }

    @Nullable
    public CannotBuildReason m_239332_() {
        if (this.f_238766_.isEmpty()) {
            return CannotBuildReason.f_238619_;
        }
        if (this.f_238766_.size() > this.f_238736_.maxReportedMessageCount()) {
            return CannotBuildReason.f_238799_;
        }
        if (this.f_238594_ == null) {
            return CannotBuildReason.f_238819_;
        }
        if (this.f_238576_.length() > this.f_238736_.maxOpinionCommentsLength()) {
            return CannotBuildReason.f_238583_;
        }
        return null;
    }

    public Either<Result, CannotBuildReason> m_240128_(ReportingContext p_240129_) {
        CannotBuildReason $$1 = this.m_239332_();
        if ($$1 != null) {
            return Either.right((Object)$$1);
        }
        String $$2 = Objects.requireNonNull(this.f_238594_).m_239892_();
        ReportEvidence $$3 = this.m_239182_(p_240129_.f_238743_());
        ReportedEntity $$4 = new ReportedEntity(this.f_238574_);
        AbuseReport $$5 = new AbuseReport(this.f_238576_, $$2, $$3, $$4, this.f_238740_);
        return Either.left((Object)new Result(this.f_241684_, $$5));
    }

    private ReportEvidence m_239182_(ChatLog p_239183_) {
        Int2ObjectRBTreeMap $$1 = new Int2ObjectRBTreeMap();
        this.f_238766_.forEach(arg_0 -> this.m_241726_(p_239183_, (Int2ObjectSortedMap)$$1, arg_0));
        return new ReportEvidence(new ArrayList($$1.values()));
    }

    private Stream<ChatLog.Entry<LoggedChatMessageLink>> m_241825_(ChatLog p_242368_, Int2ObjectMap<LoggedChatMessage.Player> p_242153_, UUID p_242301_) {
        int $$3 = Integer.MAX_VALUE;
        int $$4 = Integer.MIN_VALUE;
        for (Int2ObjectMap.Entry $$5 : Int2ObjectMaps.fastIterable(p_242153_)) {
            LoggedChatMessage.Player $$6 = (LoggedChatMessage.Player)$$5.getValue();
            if (!$$6.m_241803_().equals(p_242301_)) continue;
            int $$7 = $$5.getIntKey();
            $$3 = Math.min($$3, $$7);
            $$4 = Math.max($$4, $$7);
        }
        return p_242368_.m_239412_($$3, $$4).m_240148_().map(p_242069_ -> p_242069_.m_241867_(LoggedChatMessageLink.class)).filter(Objects::nonNull).filter(p_242055_ -> ((LoggedChatMessageLink)p_242055_.f_241599_()).m_241887_().f_240866_().equals(p_242301_));
    }

    private static Int2ObjectMap<LoggedChatMessage.Player> m_241787_(ChatLog p_242227_, int p_242178_, AbuseReportLimits p_242421_) {
        int $$3 = p_242421_.leadingContextMessageCount() + 1;
        Int2ObjectOpenHashMap $$4 = new Int2ObjectOpenHashMap();
        ChatReportBuilder.m_241774_(p_242227_, p_242178_, (arg_0, arg_1) -> ChatReportBuilder.m_242509_((Int2ObjectMap)$$4, $$3, arg_0, arg_1));
        ChatReportBuilder.m_241801_(p_242227_, p_242178_, p_242421_.trailingContextMessageCount()).forEach(arg_0 -> ChatReportBuilder.m_241725_((Int2ObjectMap)$$4, arg_0));
        return $$4;
    }

    private static Stream<ChatLog.Entry<LoggedChatMessage.Player>> m_241801_(ChatLog p_242447_, int p_242340_, int p_242471_) {
        return p_242447_.m_239514_(p_242447_.m_239583_(p_242340_)).m_240148_().map(p_242065_ -> p_242065_.m_241867_(LoggedChatMessage.Player.class)).filter(Objects::nonNull).limit(p_242471_);
    }

    private static void m_241774_(ChatLog p_242430_, int p_242234_, ReferencedMessageVisitor p_242920_) {
        IntArrayPriorityQueue $$3 = new IntArrayPriorityQueue(IntComparators.OPPOSITE_COMPARATOR);
        $$3.enqueue(p_242234_);
        IntOpenHashSet $$4 = new IntOpenHashSet();
        $$4.add(p_242234_);
        while (!$$3.isEmpty()) {
            int $$5 = $$3.dequeueInt();
            LoggedChatEvent loggedChatEvent = p_242430_.m_239049_($$5);
            if (!(loggedChatEvent instanceof LoggedChatMessage.Player)) continue;
            LoggedChatMessage.Player $$6 = (LoggedChatMessage.Player)loggedChatEvent;
            if (!p_242920_.m_242664_($$5, $$6)) break;
            loggedChatEvent = ChatReportBuilder.m_242584_(p_242430_, $$5, $$6.f_241690_()).iterator();
            while (loggedChatEvent.hasNext()) {
                int $$7 = (Integer)loggedChatEvent.next();
                if (!$$4.add($$7)) continue;
                $$3.enqueue($$7);
            }
        }
    }

    private static IntCollection m_242584_(ChatLog p_242933_, int p_242860_, PlayerChatMessage p_242922_) {
        Set $$3 = (Set)p_242922_.f_240885_().f_240868_().f_241630_().stream().map(LastSeenMessages.Entry::f_241674_).collect(Collectors.toCollection(ObjectOpenHashSet::new));
        MessageSignature $$4 = p_242922_.f_240875_().f_240892_();
        if ($$4 != null) {
            $$3.add($$4);
        }
        IntArrayList $$5 = new IntArrayList();
        Iterator $$6 = p_242933_.m_238953_(p_242860_).m_240148_().iterator();
        while ($$6.hasNext() && !$$3.isEmpty()) {
            LoggedChatMessage.Player $$8;
            ChatLog.Entry $$7 = (ChatLog.Entry)$$6.next();
            Object t = $$7.f_241599_();
            if (!(t instanceof LoggedChatMessage.Player) || !$$3.remove(($$8 = (LoggedChatMessage.Player)t).m_241834_())) continue;
            $$5.add($$7.f_241698_());
        }
        return $$5;
    }

    private ReportChatMessage m_242005_(int p_242213_, LoggedChatMessage.Player p_242239_) {
        PlayerChatMessage $$2 = p_242239_.f_241690_();
        SignedMessageBody $$3 = $$2.f_240885_();
        Instant $$4 = $$2.m_241109_();
        long $$5 = $$2.m_241064_();
        ByteBuffer $$6 = $$2.f_240893_().m_241929_();
        ByteBuffer $$7 = Util.m_214614_($$2.f_240875_().f_240892_(), MessageSignature::m_241929_);
        ByteBuffer $$8 = ByteBuffer.wrap($$3.m_241131_().asBytes());
        ReportChatMessageContent $$9 = new ReportChatMessageContent($$2.m_241775_().f_241656_(), $$2.m_241775_().m_241978_() ? ChatReportBuilder.m_239802_($$2.m_241775_().f_241671_()) : null);
        String $$10 = $$2.f_237215_().map(ChatReportBuilder::m_239802_).orElse(null);
        List<ReportChatMessageBody.LastSeenSignature> $$11 = $$3.f_240868_().f_241630_().stream().map(p_242068_ -> new ReportChatMessageBody.LastSeenSignature(p_242068_.f_241648_(), p_242068_.f_241674_().m_241929_())).toList();
        return new ReportChatMessage(new ReportChatMessageHeader($$7, p_242239_.m_241803_(), $$8, $$6), new ReportChatMessageBody($$4, $$5, $$11, $$9), $$10, this.m_240221_(p_242213_));
    }

    private ReportChatMessage m_241990_(LoggedChatMessageLink p_242212_) {
        ByteBuffer $$1 = p_242212_.m_241834_().m_241929_();
        ByteBuffer $$2 = Util.m_214614_(p_242212_.m_241887_().f_240892_(), MessageSignature::m_241929_);
        return new ReportChatMessage(new ReportChatMessageHeader($$2, p_242212_.m_241887_().f_240866_(), ByteBuffer.wrap(p_242212_.m_241770_()), $$1), null, null, false);
    }

    private static String m_239802_(Component p_239803_) {
        return Component.Serializer.m_237122_(p_239803_);
    }

    public ChatReportBuilder m_239582_() {
        ChatReportBuilder $$0 = new ChatReportBuilder(this.f_241684_, this.f_238740_, this.f_238574_, this.f_238736_);
        $$0.f_238766_.addAll((IntCollection)this.f_238766_);
        $$0.f_238576_ = this.f_238576_;
        $$0.f_238594_ = this.f_238594_;
        return $$0;
    }

    private static /* synthetic */ void m_241725_(Int2ObjectMap p_242056_, ChatLog.Entry p_242057_) {
        p_242056_.put(p_242057_.f_241698_(), (Object)((LoggedChatMessage.Player)p_242057_.f_241599_()));
    }

    private static /* synthetic */ boolean m_242509_(Int2ObjectMap p_242691_, int p_242692_, int p_242693_, LoggedChatMessage.Player p_242694_) {
        p_242691_.put(p_242693_, (Object)p_242694_);
        return p_242691_.size() < p_242692_;
    }

    private /* synthetic */ void m_241726_(ChatLog p_242058_, Int2ObjectSortedMap p_242059_, int p_242060_) {
        Int2ObjectMap<LoggedChatMessage.Player> $$3 = ChatReportBuilder.m_241787_(p_242058_, p_242060_, this.f_238736_);
        ObjectOpenHashSet $$4 = new ObjectOpenHashSet();
        for (Int2ObjectMap.Entry $$5 : Int2ObjectMaps.fastIterable($$3)) {
            int $$6 = $$5.getIntKey();
            LoggedChatMessage.Player $$7 = (LoggedChatMessage.Player)$$5.getValue();
            p_242059_.put($$6, (Object)this.m_242005_($$6, $$7));
            $$4.add($$7.m_241803_());
        }
        for (UUID $$8 : $$4) {
            this.m_241825_(p_242058_, $$3, $$8).forEach(p_242067_ -> {
                LoggedChatMessageLink $$2 = (LoggedChatMessageLink)p_242067_.f_241599_();
                if ($$2 instanceof LoggedChatMessage.Player) {
                    LoggedChatMessage.Player $$3 = (LoggedChatMessage.Player)$$2;
                    p_242059_.putIfAbsent(p_242067_.f_241698_(), (Object)this.m_242005_(p_242067_.f_241698_(), $$3));
                } else {
                    p_242059_.putIfAbsent(p_242067_.f_241698_(), (Object)this.m_241990_($$2));
                }
            });
        }
    }

    public record CannotBuildReason(Component f_238631_) {
        public static final CannotBuildReason f_238819_ = new CannotBuildReason(Component.m_237115_("gui.chatReport.send.no_reason"));
        public static final CannotBuildReason f_238619_ = new CannotBuildReason(Component.m_237115_("gui.chatReport.send.no_reported_messages"));
        public static final CannotBuildReason f_238799_ = new CannotBuildReason(Component.m_237115_("gui.chatReport.send.too_many_messages"));
        public static final CannotBuildReason f_238583_ = new CannotBuildReason(Component.m_237115_("gui.chatReport.send.comments_too_long"));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{CannotBuildReason.class, "message", "f_238631_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{CannotBuildReason.class, "message", "f_238631_"}, this);
        }

        @Override
        public final boolean equals(Object p_240127_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{CannotBuildReason.class, "message", "f_238631_"}, this, p_240127_);
        }
    }

    public record Result(UUID f_238815_, AbuseReport f_238727_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Result.class, "id;report", "f_238815_", "f_238727_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Result.class, "id;report", "f_238815_", "f_238727_"}, this);
        }

        @Override
        public final boolean equals(Object p_238996_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Result.class, "id;report", "f_238815_", "f_238727_"}, this, p_238996_);
        }
    }

    static interface ReferencedMessageVisitor {
        public boolean m_242664_(int var1, LoggedChatMessage.Player var2);
    }
}

