/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NbtPathArgument
implements ArgumentType<NbtPath> {
    private static final Collection<String> f_99484_ = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
    public static final SimpleCommandExceptionType f_99482_ = new SimpleCommandExceptionType((Message)Component.m_237115_("arguments.nbtpath.node.invalid"));
    public static final DynamicCommandExceptionType f_99483_ = new DynamicCommandExceptionType(p_99502_ -> Component.m_237110_("arguments.nbtpath.nothing_found", p_99502_));
    private static final char f_169530_ = '[';
    private static final char f_169531_ = ']';
    private static final char f_169532_ = '{';
    private static final char f_169533_ = '}';
    private static final char f_169534_ = '\"';

    public static NbtPathArgument m_99487_() {
        return new NbtPathArgument();
    }

    public static NbtPath m_99498_(CommandContext<CommandSourceStack> p_99499_, String p_99500_) {
        return (NbtPath)p_99499_.getArgument(p_99500_, NbtPath.class);
    }

    public NbtPath parse(StringReader p_99491_) throws CommandSyntaxException {
        ArrayList $$1 = Lists.newArrayList();
        int $$2 = p_99491_.getCursor();
        Object2IntOpenHashMap $$3 = new Object2IntOpenHashMap();
        boolean $$4 = true;
        while (p_99491_.canRead() && p_99491_.peek() != ' ') {
            char $$6;
            Node $$5 = NbtPathArgument.m_99495_(p_99491_, $$4);
            $$1.add($$5);
            $$3.put((Object)$$5, p_99491_.getCursor() - $$2);
            $$4 = false;
            if (!p_99491_.canRead() || ($$6 = p_99491_.peek()) == ' ' || $$6 == '[' || $$6 == '{') continue;
            p_99491_.expect('.');
        }
        return new NbtPath(p_99491_.getString().substring($$2, p_99491_.getCursor()), $$1.toArray(new Node[0]), (Object2IntMap<Node>)$$3);
    }

    private static Node m_99495_(StringReader p_99496_, boolean p_99497_) throws CommandSyntaxException {
        switch (p_99496_.peek()) {
            case '{': {
                if (!p_99497_) {
                    throw f_99482_.createWithContext((ImmutableStringReader)p_99496_);
                }
                CompoundTag $$2 = new TagParser(p_99496_).m_129373_();
                return new MatchRootObjectNode($$2);
            }
            case '[': {
                p_99496_.skip();
                char $$3 = p_99496_.peek();
                if ($$3 == '{') {
                    CompoundTag $$4 = new TagParser(p_99496_).m_129373_();
                    p_99496_.expect(']');
                    return new MatchElementNode($$4);
                }
                if ($$3 == ']') {
                    p_99496_.skip();
                    return AllElementsNode.f_99515_;
                }
                int $$5 = p_99496_.readInt();
                p_99496_.expect(']');
                return new IndexedElementNode($$5);
            }
            case '\"': {
                String $$6 = p_99496_.readString();
                return NbtPathArgument.m_99492_(p_99496_, $$6);
            }
        }
        String $$7 = NbtPathArgument.m_99508_(p_99496_);
        return NbtPathArgument.m_99492_(p_99496_, $$7);
    }

    private static Node m_99492_(StringReader p_99493_, String p_99494_) throws CommandSyntaxException {
        if (p_99493_.canRead() && p_99493_.peek() == '{') {
            CompoundTag $$2 = new TagParser(p_99493_).m_129373_();
            return new MatchObjectNode(p_99494_, $$2);
        }
        return new CompoundChildNode(p_99494_);
    }

    private static String m_99508_(StringReader p_99509_) throws CommandSyntaxException {
        int $$1 = p_99509_.getCursor();
        while (p_99509_.canRead() && NbtPathArgument.m_99488_(p_99509_.peek())) {
            p_99509_.skip();
        }
        if (p_99509_.getCursor() == $$1) {
            throw f_99482_.createWithContext((ImmutableStringReader)p_99509_);
        }
        return p_99509_.getString().substring($$1, p_99509_.getCursor());
    }

    public Collection<String> getExamples() {
        return f_99484_;
    }

    private static boolean m_99488_(char p_99489_) {
        return p_99489_ != ' ' && p_99489_ != '\"' && p_99489_ != '[' && p_99489_ != ']' && p_99489_ != '.' && p_99489_ != '{' && p_99489_ != '}';
    }

    static Predicate<Tag> m_99510_(CompoundTag p_99511_) {
        return p_99507_ -> NbtUtils.m_129235_(p_99511_, p_99507_, true);
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class NbtPath {
        private final String f_99619_;
        private final Object2IntMap<Node> f_99620_;
        private final Node[] f_99621_;

        public NbtPath(String p_99623_, Node[] p_99624_, Object2IntMap<Node> p_99625_) {
            this.f_99619_ = p_99623_;
            this.f_99621_ = p_99624_;
            this.f_99620_ = p_99625_;
        }

        public List<Tag> m_99638_(Tag p_99639_) throws CommandSyntaxException {
            List<Tag> $$1 = Collections.singletonList(p_99639_);
            for (Node $$2 : this.f_99621_) {
                if (!($$1 = $$2.m_99653_($$1)).isEmpty()) continue;
                throw this.m_99626_($$2);
            }
            return $$1;
        }

        public int m_99643_(Tag p_99644_) {
            List<Tag> $$1 = Collections.singletonList(p_99644_);
            for (Node $$2 : this.f_99621_) {
                if (!($$1 = $$2.m_99653_($$1)).isEmpty()) continue;
                return 0;
            }
            return $$1.size();
        }

        private List<Tag> m_99650_(Tag p_99651_) throws CommandSyntaxException {
            List<Tag> $$1 = Collections.singletonList(p_99651_);
            for (int $$2 = 0; $$2 < this.f_99621_.length - 1; ++$$2) {
                Node $$3 = this.f_99621_[$$2];
                int $$4 = $$2 + 1;
                if (!($$1 = $$3.m_99658_($$1, this.f_99621_[$$4]::m_7510_)).isEmpty()) continue;
                throw this.m_99626_($$3);
            }
            return $$1;
        }

        public List<Tag> m_99640_(Tag p_99641_, Supplier<Tag> p_99642_) throws CommandSyntaxException {
            List<Tag> $$2 = this.m_99650_(p_99641_);
            Node $$3 = this.f_99621_[this.f_99621_.length - 1];
            return $$3.m_99658_($$2, p_99642_);
        }

        private static int m_99635_(List<Tag> p_99636_, Function<Tag, Integer> p_99637_) {
            return p_99636_.stream().map(p_99637_).reduce(0, (p_99633_, p_99634_) -> p_99633_ + p_99634_);
        }

        public int m_169535_(Tag p_169536_, Tag p_169537_) throws CommandSyntaxException {
            return this.m_99645_(p_169536_, p_169537_::m_6426_);
        }

        public int m_99645_(Tag p_99646_, Supplier<Tag> p_99647_) throws CommandSyntaxException {
            List<Tag> $$2 = this.m_99650_(p_99646_);
            Node $$3 = this.f_99621_[this.f_99621_.length - 1];
            return NbtPath.m_99635_($$2, p_99631_ -> $$3.m_5571_((Tag)p_99631_, p_99647_));
        }

        public int m_99648_(Tag p_99649_) {
            List<Tag> $$1 = Collections.singletonList(p_99649_);
            for (int $$2 = 0; $$2 < this.f_99621_.length - 1; ++$$2) {
                $$1 = this.f_99621_[$$2].m_99653_($$1);
            }
            Node $$3 = this.f_99621_[this.f_99621_.length - 1];
            return NbtPath.m_99635_($$1, $$3::m_6015_);
        }

        private CommandSyntaxException m_99626_(Node p_99627_) {
            int $$1 = this.f_99620_.getInt((Object)p_99627_);
            return f_99483_.create((Object)this.f_99619_.substring(0, $$1));
        }

        public String toString() {
            return this.f_99619_;
        }
    }

    static interface Node {
        public void m_7273_(Tag var1, List<Tag> var2);

        public void m_7876_(Tag var1, Supplier<Tag> var2, List<Tag> var3);

        public Tag m_7510_();

        public int m_5571_(Tag var1, Supplier<Tag> var2);

        public int m_6015_(Tag var1);

        default public List<Tag> m_99653_(List<Tag> p_99654_) {
            return this.m_99655_(p_99654_, this::m_7273_);
        }

        default public List<Tag> m_99658_(List<Tag> p_99659_, Supplier<Tag> p_99660_) {
            return this.m_99655_(p_99659_, (p_99663_, p_99664_) -> this.m_7876_((Tag)p_99663_, p_99660_, (List<Tag>)p_99664_));
        }

        default public List<Tag> m_99655_(List<Tag> p_99656_, BiConsumer<Tag, List<Tag>> p_99657_) {
            ArrayList $$2 = Lists.newArrayList();
            for (Tag $$3 : p_99656_) {
                p_99657_.accept($$3, $$2);
            }
            return $$2;
        }
    }

    static class MatchRootObjectNode
    implements Node {
        private final Predicate<Tag> f_99603_;

        public MatchRootObjectNode(CompoundTag p_99605_) {
            this.f_99603_ = NbtPathArgument.m_99510_(p_99605_);
        }

        @Override
        public void m_7273_(Tag p_99610_, List<Tag> p_99611_) {
            if (p_99610_ instanceof CompoundTag && this.f_99603_.test(p_99610_)) {
                p_99611_.add(p_99610_);
            }
        }

        @Override
        public void m_7876_(Tag p_99616_, Supplier<Tag> p_99617_, List<Tag> p_99618_) {
            this.m_7273_(p_99616_, p_99618_);
        }

        @Override
        public Tag m_7510_() {
            return new CompoundTag();
        }

        @Override
        public int m_5571_(Tag p_99613_, Supplier<Tag> p_99614_) {
            return 0;
        }

        @Override
        public int m_6015_(Tag p_99608_) {
            return 0;
        }
    }

    static class MatchElementNode
    implements Node {
        private final CompoundTag f_99563_;
        private final Predicate<Tag> f_99564_;

        public MatchElementNode(CompoundTag p_99566_) {
            this.f_99563_ = p_99566_;
            this.f_99564_ = NbtPathArgument.m_99510_(p_99566_);
        }

        @Override
        public void m_7273_(Tag p_99575_, List<Tag> p_99576_) {
            if (p_99575_ instanceof ListTag) {
                ListTag $$2 = (ListTag)p_99575_;
                $$2.stream().filter(this.f_99564_).forEach(p_99576_::add);
            }
        }

        @Override
        public void m_7876_(Tag p_99581_, Supplier<Tag> p_99582_, List<Tag> p_99583_) {
            MutableBoolean $$3 = new MutableBoolean();
            if (p_99581_ instanceof ListTag) {
                ListTag $$4 = (ListTag)p_99581_;
                $$4.stream().filter(this.f_99564_).forEach(p_99571_ -> {
                    p_99583_.add((Tag)p_99571_);
                    $$3.setTrue();
                });
                if ($$3.isFalse()) {
                    CompoundTag $$5 = this.f_99563_.m_6426_();
                    $$4.add($$5);
                    p_99583_.add($$5);
                }
            }
        }

        @Override
        public Tag m_7510_() {
            return new ListTag();
        }

        @Override
        public int m_5571_(Tag p_99578_, Supplier<Tag> p_99579_) {
            int $$2 = 0;
            if (p_99578_ instanceof ListTag) {
                ListTag $$3 = (ListTag)p_99578_;
                int $$4 = $$3.size();
                if ($$4 == 0) {
                    $$3.add(p_99579_.get());
                    ++$$2;
                } else {
                    for (int $$5 = 0; $$5 < $$4; ++$$5) {
                        Tag $$7;
                        Tag $$6 = $$3.get($$5);
                        if (!this.f_99564_.test($$6) || ($$7 = p_99579_.get()).equals($$6) || !$$3.m_7615_($$5, $$7)) continue;
                        ++$$2;
                    }
                }
            }
            return $$2;
        }

        @Override
        public int m_6015_(Tag p_99573_) {
            int $$1 = 0;
            if (p_99573_ instanceof ListTag) {
                ListTag $$2 = (ListTag)p_99573_;
                for (int $$3 = $$2.size() - 1; $$3 >= 0; --$$3) {
                    if (!this.f_99564_.test($$2.get($$3))) continue;
                    $$2.remove($$3);
                    ++$$1;
                }
            }
            return $$1;
        }
    }

    static class AllElementsNode
    implements Node {
        public static final AllElementsNode f_99515_ = new AllElementsNode();

        private AllElementsNode() {
        }

        @Override
        public void m_7273_(Tag p_99522_, List<Tag> p_99523_) {
            if (p_99522_ instanceof CollectionTag) {
                p_99523_.addAll((CollectionTag)p_99522_);
            }
        }

        @Override
        public void m_7876_(Tag p_99528_, Supplier<Tag> p_99529_, List<Tag> p_99530_) {
            if (p_99528_ instanceof CollectionTag) {
                CollectionTag $$3 = (CollectionTag)p_99528_;
                if ($$3.isEmpty()) {
                    Tag $$4 = p_99529_.get();
                    if ($$3.m_7614_(0, $$4)) {
                        p_99530_.add($$4);
                    }
                } else {
                    p_99530_.addAll($$3);
                }
            }
        }

        @Override
        public Tag m_7510_() {
            return new ListTag();
        }

        @Override
        public int m_5571_(Tag p_99525_, Supplier<Tag> p_99526_) {
            if (p_99525_ instanceof CollectionTag) {
                CollectionTag $$2 = (CollectionTag)p_99525_;
                int $$3 = $$2.size();
                if ($$3 == 0) {
                    $$2.m_7614_(0, p_99526_.get());
                    return 1;
                }
                Tag $$4 = p_99526_.get();
                int $$5 = $$3 - (int)$$2.stream().filter($$4::equals).count();
                if ($$5 == 0) {
                    return 0;
                }
                $$2.clear();
                if (!$$2.m_7614_(0, $$4)) {
                    return 0;
                }
                for (int $$6 = 1; $$6 < $$3; ++$$6) {
                    $$2.m_7614_($$6, p_99526_.get());
                }
                return $$5;
            }
            return 0;
        }

        @Override
        public int m_6015_(Tag p_99520_) {
            CollectionTag $$1;
            int $$2;
            if (p_99520_ instanceof CollectionTag && ($$2 = ($$1 = (CollectionTag)p_99520_).size()) > 0) {
                $$1.clear();
                return $$2;
            }
            return 0;
        }
    }

    static class IndexedElementNode
    implements Node {
        private final int f_99547_;

        public IndexedElementNode(int p_99549_) {
            this.f_99547_ = p_99549_;
        }

        @Override
        public void m_7273_(Tag p_99554_, List<Tag> p_99555_) {
            if (p_99554_ instanceof CollectionTag) {
                int $$4;
                CollectionTag $$2 = (CollectionTag)p_99554_;
                int $$3 = $$2.size();
                int n = $$4 = this.f_99547_ < 0 ? $$3 + this.f_99547_ : this.f_99547_;
                if (0 <= $$4 && $$4 < $$3) {
                    p_99555_.add((Tag)$$2.get($$4));
                }
            }
        }

        @Override
        public void m_7876_(Tag p_99560_, Supplier<Tag> p_99561_, List<Tag> p_99562_) {
            this.m_7273_(p_99560_, p_99562_);
        }

        @Override
        public Tag m_7510_() {
            return new ListTag();
        }

        @Override
        public int m_5571_(Tag p_99557_, Supplier<Tag> p_99558_) {
            if (p_99557_ instanceof CollectionTag) {
                int $$4;
                CollectionTag $$2 = (CollectionTag)p_99557_;
                int $$3 = $$2.size();
                int n = $$4 = this.f_99547_ < 0 ? $$3 + this.f_99547_ : this.f_99547_;
                if (0 <= $$4 && $$4 < $$3) {
                    Tag $$5 = (Tag)$$2.get($$4);
                    Tag $$6 = p_99558_.get();
                    if (!$$6.equals($$5) && $$2.m_7615_($$4, $$6)) {
                        return 1;
                    }
                }
            }
            return 0;
        }

        @Override
        public int m_6015_(Tag p_99552_) {
            if (p_99552_ instanceof CollectionTag) {
                int $$3;
                CollectionTag $$1 = (CollectionTag)p_99552_;
                int $$2 = $$1.size();
                int n = $$3 = this.f_99547_ < 0 ? $$2 + this.f_99547_ : this.f_99547_;
                if (0 <= $$3 && $$3 < $$2) {
                    $$1.remove($$3);
                    return 1;
                }
            }
            return 0;
        }
    }

    static class MatchObjectNode
    implements Node {
        private final String f_99584_;
        private final CompoundTag f_99585_;
        private final Predicate<Tag> f_99586_;

        public MatchObjectNode(String p_99588_, CompoundTag p_99589_) {
            this.f_99584_ = p_99588_;
            this.f_99585_ = p_99589_;
            this.f_99586_ = NbtPathArgument.m_99510_(p_99589_);
        }

        @Override
        public void m_7273_(Tag p_99594_, List<Tag> p_99595_) {
            Tag $$2;
            if (p_99594_ instanceof CompoundTag && this.f_99586_.test($$2 = ((CompoundTag)p_99594_).m_128423_(this.f_99584_))) {
                p_99595_.add($$2);
            }
        }

        @Override
        public void m_7876_(Tag p_99600_, Supplier<Tag> p_99601_, List<Tag> p_99602_) {
            if (p_99600_ instanceof CompoundTag) {
                CompoundTag $$3 = (CompoundTag)p_99600_;
                Tag $$4 = $$3.m_128423_(this.f_99584_);
                if ($$4 == null) {
                    $$4 = this.f_99585_.m_6426_();
                    $$3.m_128365_(this.f_99584_, $$4);
                    p_99602_.add($$4);
                } else if (this.f_99586_.test($$4)) {
                    p_99602_.add($$4);
                }
            }
        }

        @Override
        public Tag m_7510_() {
            return new CompoundTag();
        }

        @Override
        public int m_5571_(Tag p_99597_, Supplier<Tag> p_99598_) {
            Tag $$4;
            CompoundTag $$2;
            Tag $$3;
            if (p_99597_ instanceof CompoundTag && this.f_99586_.test($$3 = ($$2 = (CompoundTag)p_99597_).m_128423_(this.f_99584_)) && !($$4 = p_99598_.get()).equals($$3)) {
                $$2.m_128365_(this.f_99584_, $$4);
                return 1;
            }
            return 0;
        }

        @Override
        public int m_6015_(Tag p_99592_) {
            CompoundTag $$1;
            Tag $$2;
            if (p_99592_ instanceof CompoundTag && this.f_99586_.test($$2 = ($$1 = (CompoundTag)p_99592_).m_128423_(this.f_99584_))) {
                $$1.m_128473_(this.f_99584_);
                return 1;
            }
            return 0;
        }
    }

    static class CompoundChildNode
    implements Node {
        private final String f_99531_;

        public CompoundChildNode(String p_99533_) {
            this.f_99531_ = p_99533_;
        }

        @Override
        public void m_7273_(Tag p_99538_, List<Tag> p_99539_) {
            Tag $$2;
            if (p_99538_ instanceof CompoundTag && ($$2 = ((CompoundTag)p_99538_).m_128423_(this.f_99531_)) != null) {
                p_99539_.add($$2);
            }
        }

        @Override
        public void m_7876_(Tag p_99544_, Supplier<Tag> p_99545_, List<Tag> p_99546_) {
            if (p_99544_ instanceof CompoundTag) {
                Tag $$5;
                CompoundTag $$3 = (CompoundTag)p_99544_;
                if ($$3.m_128441_(this.f_99531_)) {
                    Tag $$4 = $$3.m_128423_(this.f_99531_);
                } else {
                    $$5 = p_99545_.get();
                    $$3.m_128365_(this.f_99531_, $$5);
                }
                p_99546_.add($$5);
            }
        }

        @Override
        public Tag m_7510_() {
            return new CompoundTag();
        }

        @Override
        public int m_5571_(Tag p_99541_, Supplier<Tag> p_99542_) {
            if (p_99541_ instanceof CompoundTag) {
                Tag $$4;
                CompoundTag $$2 = (CompoundTag)p_99541_;
                Tag $$3 = p_99542_.get();
                if (!$$3.equals($$4 = $$2.m_128365_(this.f_99531_, $$3))) {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public int m_6015_(Tag p_99536_) {
            CompoundTag $$1;
            if (p_99536_ instanceof CompoundTag && ($$1 = (CompoundTag)p_99536_).m_128441_(this.f_99531_)) {
                $$1.m_128473_(this.f_99531_);
                return 1;
            }
            return 0;
        }
    }
}

