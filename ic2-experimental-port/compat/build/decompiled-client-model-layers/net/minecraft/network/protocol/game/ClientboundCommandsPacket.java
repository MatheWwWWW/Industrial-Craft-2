/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.ArgumentCommandNode
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  com.mojang.brigadier.tree.RootCommandNode
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.function.BiPredicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ClientboundCommandsPacket
implements Packet<ClientGamePacketListener> {
    private static final byte f_178797_ = 3;
    private static final byte f_178798_ = 4;
    private static final byte f_178799_ = 8;
    private static final byte f_178800_ = 16;
    private static final byte f_178801_ = 0;
    private static final byte f_178802_ = 1;
    private static final byte f_178803_ = 2;
    private final int f_237619_;
    private final List<Entry> f_237620_;

    public ClientboundCommandsPacket(RootCommandNode<SharedSuggestionProvider> p_131861_) {
        Object2IntMap<CommandNode<SharedSuggestionProvider>> $$1 = ClientboundCommandsPacket.m_131862_(p_131861_);
        this.f_237620_ = ClientboundCommandsPacket.m_237626_($$1);
        this.f_237619_ = $$1.getInt(p_131861_);
    }

    public ClientboundCommandsPacket(FriendlyByteBuf p_178805_) {
        this.f_237620_ = p_178805_.m_236845_(ClientboundCommandsPacket::m_131887_);
        this.f_237619_ = p_178805_.m_130242_();
        ClientboundCommandsPacket.m_237628_(this.f_237620_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131886_) {
        p_131886_.m_236828_(this.f_237620_, (p_237642_, p_237643_) -> p_237643_.m_237674_((FriendlyByteBuf)((Object)p_237642_)));
        p_131886_.m_130130_(this.f_237619_);
    }

    private static void m_237630_(List<Entry> p_237631_, BiPredicate<Entry, IntSet> p_237632_) {
        IntOpenHashSet $$2 = new IntOpenHashSet((IntCollection)IntSets.fromTo((int)0, (int)p_237631_.size()));
        while (!$$2.isEmpty()) {
            boolean $$3 = $$2.removeIf(arg_0 -> ClientboundCommandsPacket.m_237633_(p_237632_, p_237631_, (IntSet)$$2, arg_0));
            if ($$3) continue;
            throw new IllegalStateException("Server sent an impossible command tree");
        }
    }

    private static void m_237628_(List<Entry> p_237629_) {
        ClientboundCommandsPacket.m_237630_(p_237629_, Entry::m_237672_);
        ClientboundCommandsPacket.m_237630_(p_237629_, Entry::m_237676_);
    }

    private static Object2IntMap<CommandNode<SharedSuggestionProvider>> m_131862_(RootCommandNode<SharedSuggestionProvider> p_131863_) {
        CommandNode $$3;
        Object2IntOpenHashMap $$1 = new Object2IntOpenHashMap();
        ArrayDeque $$2 = Queues.newArrayDeque();
        $$2.add(p_131863_);
        while (($$3 = (CommandNode)$$2.poll()) != null) {
            if ($$1.containsKey((Object)$$3)) continue;
            int $$4 = $$1.size();
            $$1.put((Object)$$3, $$4);
            $$2.addAll($$3.getChildren());
            if ($$3.getRedirect() == null) continue;
            $$2.add($$3.getRedirect());
        }
        return $$1;
    }

    private static List<Entry> m_237626_(Object2IntMap<CommandNode<SharedSuggestionProvider>> p_237627_) {
        ObjectArrayList $$1 = new ObjectArrayList(p_237627_.size());
        $$1.size(p_237627_.size());
        for (Object2IntMap.Entry $$2 : Object2IntMaps.fastIterable(p_237627_)) {
            $$1.set($$2.getIntValue(), (Object)ClientboundCommandsPacket.m_237621_((CommandNode<SharedSuggestionProvider>)((CommandNode)$$2.getKey()), p_237627_));
        }
        return $$1;
    }

    private static Entry m_131887_(FriendlyByteBuf p_131888_) {
        byte $$1 = p_131888_.readByte();
        int[] $$2 = p_131888_.m_130100_();
        int $$3 = ($$1 & 8) != 0 ? p_131888_.m_130242_() : 0;
        NodeStub $$4 = ClientboundCommandsPacket.m_237638_(p_131888_, $$1);
        return new Entry($$4, $$1, $$3, $$2);
    }

    @Nullable
    private static NodeStub m_237638_(FriendlyByteBuf p_237639_, byte p_237640_) {
        int $$2 = p_237640_ & 3;
        if ($$2 == 2) {
            String $$3 = p_237639_.m_130277_();
            int $$4 = p_237639_.m_130242_();
            ArgumentTypeInfo $$5 = (ArgumentTypeInfo)Registry.f_235729_.m_7942_($$4);
            if ($$5 == null) {
                return null;
            }
            Object $$6 = $$5.m_213618_(p_237639_);
            ResourceLocation $$7 = (p_237640_ & 0x10) != 0 ? p_237639_.m_130281_() : null;
            return new ArgumentNodeStub($$3, (ArgumentTypeInfo.Template<?>)$$6, $$7);
        }
        if ($$2 == 1) {
            String $$8 = p_237639_.m_130277_();
            return new LiteralNodeStub($$8);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private static Entry m_237621_(CommandNode<SharedSuggestionProvider> p_237622_, Object2IntMap<CommandNode<SharedSuggestionProvider>> p_237623_) {
        void $$10;
        int $$4;
        int $$2 = 0;
        if (p_237622_.getRedirect() != null) {
            $$2 |= 8;
            int $$3 = p_237623_.getInt((Object)p_237622_.getRedirect());
        } else {
            $$4 = 0;
        }
        if (p_237622_.getCommand() != null) {
            $$2 |= 4;
        }
        if (p_237622_ instanceof RootCommandNode) {
            $$2 |= 0;
            Object $$5 = null;
        } else if (p_237622_ instanceof ArgumentCommandNode) {
            ArgumentCommandNode $$6 = (ArgumentCommandNode)p_237622_;
            ArgumentNodeStub $$7 = new ArgumentNodeStub($$6);
            $$2 |= 2;
            if ($$6.getCustomSuggestions() != null) {
                $$2 |= 0x10;
            }
        } else if (p_237622_ instanceof LiteralCommandNode) {
            LiteralCommandNode $$8 = (LiteralCommandNode)p_237622_;
            LiteralNodeStub $$9 = new LiteralNodeStub($$8.getLiteral());
            $$2 |= 1;
        } else {
            throw new UnsupportedOperationException("Unknown node type " + p_237622_);
        }
        int[] $$11 = p_237622_.getChildren().stream().mapToInt(arg_0 -> p_237623_.getInt(arg_0)).toArray();
        return new Entry((NodeStub)$$10, $$2, $$4, $$11);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131878_) {
        p_131878_.m_7443_(this);
    }

    public RootCommandNode<SharedSuggestionProvider> m_237624_(CommandBuildContext p_237625_) {
        return (RootCommandNode)new NodeResolver(p_237625_, this.f_237620_).m_237691_(this.f_237619_);
    }

    private static /* synthetic */ boolean m_237633_(BiPredicate p_237634_, List p_237635_, IntSet p_237636_, int p_237637_) {
        return p_237634_.test((Entry)p_237635_.get(p_237637_), p_237636_);
    }

    static class Entry {
        @Nullable
        final NodeStub f_237666_;
        final int f_131890_;
        final int f_131891_;
        final int[] f_131892_;

        Entry(@Nullable NodeStub p_237668_, int p_237669_, int p_237670_, int[] p_237671_) {
            this.f_237666_ = p_237668_;
            this.f_131890_ = p_237669_;
            this.f_131891_ = p_237670_;
            this.f_131892_ = p_237671_;
        }

        public void m_237674_(FriendlyByteBuf p_237675_) {
            p_237675_.writeByte(this.f_131890_);
            p_237675_.m_130089_(this.f_131892_);
            if ((this.f_131890_ & 8) != 0) {
                p_237675_.m_130130_(this.f_131891_);
            }
            if (this.f_237666_ != null) {
                this.f_237666_.m_214206_(p_237675_);
            }
        }

        public boolean m_237672_(IntSet p_237673_) {
            if ((this.f_131890_ & 8) != 0) {
                return !p_237673_.contains(this.f_131891_);
            }
            return true;
        }

        public boolean m_237676_(IntSet p_237677_) {
            for (int $$1 : this.f_131892_) {
                if (!p_237677_.contains($$1)) continue;
                return false;
            }
            return true;
        }
    }

    static interface NodeStub {
        public ArgumentBuilder<SharedSuggestionProvider, ?> m_213891_(CommandBuildContext var1);

        public void m_214206_(FriendlyByteBuf var1);
    }

    static class ArgumentNodeStub
    implements NodeStub {
        private final String f_237644_;
        private final ArgumentTypeInfo.Template<?> f_237645_;
        @Nullable
        private final ResourceLocation f_237646_;

        @Nullable
        private static ResourceLocation m_237653_(@Nullable SuggestionProvider<SharedSuggestionProvider> p_237654_) {
            return p_237654_ != null ? SuggestionProviders.m_121654_(p_237654_) : null;
        }

        ArgumentNodeStub(String p_237650_, ArgumentTypeInfo.Template<?> p_237651_, @Nullable ResourceLocation p_237652_) {
            this.f_237644_ = p_237650_;
            this.f_237645_ = p_237651_;
            this.f_237646_ = p_237652_;
        }

        public ArgumentNodeStub(ArgumentCommandNode<SharedSuggestionProvider, ?> p_237648_) {
            this(p_237648_.getName(), ArgumentTypeInfos.m_235393_(p_237648_.getType()), ArgumentNodeStub.m_237653_((SuggestionProvider<SharedSuggestionProvider>)p_237648_.getCustomSuggestions()));
        }

        @Override
        public ArgumentBuilder<SharedSuggestionProvider, ?> m_213891_(CommandBuildContext p_237656_) {
            Object $$1 = this.f_237645_.m_213879_(p_237656_);
            RequiredArgumentBuilder $$2 = RequiredArgumentBuilder.argument((String)this.f_237644_, $$1);
            if (this.f_237646_ != null) {
                $$2.suggests(SuggestionProviders.m_121656_(this.f_237646_));
            }
            return $$2;
        }

        @Override
        public void m_214206_(FriendlyByteBuf p_237658_) {
            p_237658_.m_130070_(this.f_237644_);
            ArgumentNodeStub.m_237659_(p_237658_, this.f_237645_);
            if (this.f_237646_ != null) {
                p_237658_.m_130085_(this.f_237646_);
            }
        }

        private static <A extends ArgumentType<?>> void m_237659_(FriendlyByteBuf p_237660_, ArgumentTypeInfo.Template<A> p_237661_) {
            ArgumentNodeStub.m_237662_(p_237660_, p_237661_.m_213709_(), p_237661_);
        }

        private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void m_237662_(FriendlyByteBuf p_237663_, ArgumentTypeInfo<A, T> p_237664_, ArgumentTypeInfo.Template<A> p_237665_) {
            p_237663_.m_130130_(Registry.f_235729_.m_7447_(p_237664_));
            p_237664_.m_214155_(p_237665_, p_237663_);
        }
    }

    static class LiteralNodeStub
    implements NodeStub {
        private final String f_237678_;

        LiteralNodeStub(String p_237680_) {
            this.f_237678_ = p_237680_;
        }

        @Override
        public ArgumentBuilder<SharedSuggestionProvider, ?> m_213891_(CommandBuildContext p_237682_) {
            return LiteralArgumentBuilder.literal((String)this.f_237678_);
        }

        @Override
        public void m_214206_(FriendlyByteBuf p_237684_) {
            p_237684_.m_130070_(this.f_237678_);
        }
    }

    static class NodeResolver {
        private final CommandBuildContext f_237685_;
        private final List<Entry> f_237686_;
        private final List<CommandNode<SharedSuggestionProvider>> f_237687_;

        NodeResolver(CommandBuildContext p_237689_, List<Entry> p_237690_) {
            this.f_237685_ = p_237689_;
            this.f_237686_ = p_237690_;
            ObjectArrayList $$2 = new ObjectArrayList();
            $$2.size(p_237690_.size());
            this.f_237687_ = $$2;
        }

        public CommandNode<SharedSuggestionProvider> m_237691_(int p_237692_) {
            CommandNode $$5;
            CommandNode<SharedSuggestionProvider> $$1 = this.f_237687_.get(p_237692_);
            if ($$1 != null) {
                return $$1;
            }
            Entry $$2 = this.f_237686_.get(p_237692_);
            if ($$2.f_237666_ == null) {
                RootCommandNode $$3 = new RootCommandNode();
            } else {
                ArgumentBuilder<SharedSuggestionProvider, ?> $$4 = $$2.f_237666_.m_213891_(this.f_237685_);
                if (($$2.f_131890_ & 8) != 0) {
                    $$4.redirect(this.m_237691_($$2.f_131891_));
                }
                if (($$2.f_131890_ & 4) != 0) {
                    $$4.executes(p_237694_ -> 0);
                }
                $$5 = $$4.build();
            }
            this.f_237687_.set(p_237692_, (CommandNode<SharedSuggestionProvider>)$$5);
            for (int $$6 : $$2.f_131892_) {
                CommandNode<SharedSuggestionProvider> $$7 = this.m_237691_($$6);
                if ($$7 instanceof RootCommandNode) continue;
                $$5.addChild($$7);
            }
            return $$5;
        }
    }
}

