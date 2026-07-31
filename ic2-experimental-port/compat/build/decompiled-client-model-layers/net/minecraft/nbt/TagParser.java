/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.network.chat.Component;

public class TagParser {
    public static final SimpleCommandExceptionType f_129334_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.nbt.trailing"));
    public static final SimpleCommandExceptionType f_129335_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.nbt.expected.key"));
    public static final SimpleCommandExceptionType f_129336_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.nbt.expected.value"));
    public static final Dynamic2CommandExceptionType f_129337_ = new Dynamic2CommandExceptionType((p_129366_, p_129367_) -> Component.m_237110_("argument.nbt.list.mixed", p_129366_, p_129367_));
    public static final Dynamic2CommandExceptionType f_129338_ = new Dynamic2CommandExceptionType((p_129357_, p_129358_) -> Component.m_237110_("argument.nbt.array.mixed", p_129357_, p_129358_));
    public static final DynamicCommandExceptionType f_129339_ = new DynamicCommandExceptionType(p_129355_ -> Component.m_237110_("argument.nbt.array.invalid", p_129355_));
    public static final char f_178209_ = ',';
    public static final char f_178210_ = ':';
    private static final char f_178211_ = '[';
    private static final char f_178212_ = ']';
    private static final char f_178213_ = '}';
    private static final char f_178214_ = '{';
    private static final Pattern f_129340_ = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
    private static final Pattern f_129341_ = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern f_129342_ = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern f_129343_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
    private static final Pattern f_129344_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
    private static final Pattern f_129345_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
    private static final Pattern f_129346_ = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private final StringReader f_129347_;

    public static CompoundTag m_129359_(String p_129360_) throws CommandSyntaxException {
        return new TagParser(new StringReader(p_129360_)).m_129351_();
    }

    @VisibleForTesting
    CompoundTag m_129351_() throws CommandSyntaxException {
        CompoundTag $$0 = this.m_129373_();
        this.f_129347_.skipWhitespace();
        if (this.f_129347_.canRead()) {
            throw f_129334_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        return $$0;
    }

    public TagParser(StringReader p_129350_) {
        this.f_129347_ = p_129350_;
    }

    protected String m_129364_() throws CommandSyntaxException {
        this.f_129347_.skipWhitespace();
        if (!this.f_129347_.canRead()) {
            throw f_129335_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        return this.f_129347_.readString();
    }

    protected Tag m_129370_() throws CommandSyntaxException {
        this.f_129347_.skipWhitespace();
        int $$0 = this.f_129347_.getCursor();
        if (StringReader.isQuotedStringStart((char)this.f_129347_.peek())) {
            return StringTag.m_129297_(this.f_129347_.readQuotedString());
        }
        String $$1 = this.f_129347_.readUnquotedString();
        if ($$1.isEmpty()) {
            this.f_129347_.setCursor($$0);
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        return this.m_129368_($$1);
    }

    private Tag m_129368_(String p_129369_) {
        try {
            if (f_129342_.matcher(p_129369_).matches()) {
                return FloatTag.m_128566_(Float.parseFloat(p_129369_.substring(0, p_129369_.length() - 1)));
            }
            if (f_129343_.matcher(p_129369_).matches()) {
                return ByteTag.m_128266_(Byte.parseByte(p_129369_.substring(0, p_129369_.length() - 1)));
            }
            if (f_129344_.matcher(p_129369_).matches()) {
                return LongTag.m_128882_(Long.parseLong(p_129369_.substring(0, p_129369_.length() - 1)));
            }
            if (f_129345_.matcher(p_129369_).matches()) {
                return ShortTag.m_129258_(Short.parseShort(p_129369_.substring(0, p_129369_.length() - 1)));
            }
            if (f_129346_.matcher(p_129369_).matches()) {
                return IntTag.m_128679_(Integer.parseInt(p_129369_));
            }
            if (f_129341_.matcher(p_129369_).matches()) {
                return DoubleTag.m_128500_(Double.parseDouble(p_129369_.substring(0, p_129369_.length() - 1)));
            }
            if (f_129340_.matcher(p_129369_).matches()) {
                return DoubleTag.m_128500_(Double.parseDouble(p_129369_));
            }
            if ("true".equalsIgnoreCase(p_129369_)) {
                return ByteTag.f_128257_;
            }
            if ("false".equalsIgnoreCase(p_129369_)) {
                return ByteTag.f_128256_;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return StringTag.m_129297_(p_129369_);
    }

    public Tag m_129371_() throws CommandSyntaxException {
        this.f_129347_.skipWhitespace();
        if (!this.f_129347_.canRead()) {
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        char $$0 = this.f_129347_.peek();
        if ($$0 == '{') {
            return this.m_129373_();
        }
        if ($$0 == '[') {
            return this.m_129372_();
        }
        return this.m_129370_();
    }

    protected Tag m_129372_() throws CommandSyntaxException {
        if (this.f_129347_.canRead(3) && !StringReader.isQuotedStringStart((char)this.f_129347_.peek(1)) && this.f_129347_.peek(2) == ';') {
            return this.m_129375_();
        }
        return this.m_129374_();
    }

    public CompoundTag m_129373_() throws CommandSyntaxException {
        this.m_129352_('{');
        CompoundTag $$0 = new CompoundTag();
        this.f_129347_.skipWhitespace();
        while (this.f_129347_.canRead() && this.f_129347_.peek() != '}') {
            int $$1 = this.f_129347_.getCursor();
            String $$2 = this.m_129364_();
            if ($$2.isEmpty()) {
                this.f_129347_.setCursor($$1);
                throw f_129335_.createWithContext((ImmutableStringReader)this.f_129347_);
            }
            this.m_129352_(':');
            $$0.m_128365_($$2, this.m_129371_());
            if (!this.m_129376_()) break;
            if (this.f_129347_.canRead()) continue;
            throw f_129335_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        this.m_129352_('}');
        return $$0;
    }

    private Tag m_129374_() throws CommandSyntaxException {
        this.m_129352_('[');
        this.f_129347_.skipWhitespace();
        if (!this.f_129347_.canRead()) {
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        ListTag $$0 = new ListTag();
        TagType<?> $$1 = null;
        while (this.f_129347_.peek() != ']') {
            int $$2 = this.f_129347_.getCursor();
            Tag $$3 = this.m_129371_();
            TagType<?> $$4 = $$3.m_6458_();
            if ($$1 == null) {
                $$1 = $$4;
            } else if ($$4 != $$1) {
                this.f_129347_.setCursor($$2);
                throw f_129337_.createWithContext((ImmutableStringReader)this.f_129347_, (Object)$$4.m_5986_(), (Object)$$1.m_5986_());
            }
            $$0.add($$3);
            if (!this.m_129376_()) break;
            if (this.f_129347_.canRead()) continue;
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        this.m_129352_(']');
        return $$0;
    }

    private Tag m_129375_() throws CommandSyntaxException {
        this.m_129352_('[');
        int $$0 = this.f_129347_.getCursor();
        char $$1 = this.f_129347_.read();
        this.f_129347_.read();
        this.f_129347_.skipWhitespace();
        if (!this.f_129347_.canRead()) {
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        if ($$1 == 'B') {
            return new ByteArrayTag(this.m_129361_(ByteArrayTag.f_128185_, ByteTag.f_128255_));
        }
        if ($$1 == 'L') {
            return new LongArrayTag(this.m_129361_(LongArrayTag.f_128800_, LongTag.f_128873_));
        }
        if ($$1 == 'I') {
            return new IntArrayTag(this.m_129361_(IntArrayTag.f_128599_, IntTag.f_128670_));
        }
        this.f_129347_.setCursor($$0);
        throw f_129339_.createWithContext((ImmutableStringReader)this.f_129347_, (Object)String.valueOf($$1));
    }

    private <T extends Number> List<T> m_129361_(TagType<?> p_129362_, TagType<?> p_129363_) throws CommandSyntaxException {
        ArrayList $$2 = Lists.newArrayList();
        while (this.f_129347_.peek() != ']') {
            int $$3 = this.f_129347_.getCursor();
            Tag $$4 = this.m_129371_();
            TagType<?> $$5 = $$4.m_6458_();
            if ($$5 != p_129363_) {
                this.f_129347_.setCursor($$3);
                throw f_129338_.createWithContext((ImmutableStringReader)this.f_129347_, (Object)$$5.m_5986_(), (Object)p_129362_.m_5986_());
            }
            if (p_129363_ == ByteTag.f_128255_) {
                $$2.add(((NumericTag)$$4).m_7063_());
            } else if (p_129363_ == LongTag.f_128873_) {
                $$2.add(((NumericTag)$$4).m_7046_());
            } else {
                $$2.add(((NumericTag)$$4).m_7047_());
            }
            if (!this.m_129376_()) break;
            if (this.f_129347_.canRead()) continue;
            throw f_129336_.createWithContext((ImmutableStringReader)this.f_129347_);
        }
        this.m_129352_(']');
        return $$2;
    }

    private boolean m_129376_() {
        this.f_129347_.skipWhitespace();
        if (this.f_129347_.canRead() && this.f_129347_.peek() == ',') {
            this.f_129347_.skip();
            this.f_129347_.skipWhitespace();
            return true;
        }
        return false;
    }

    private void m_129352_(char p_129353_) throws CommandSyntaxException {
        this.f_129347_.skipWhitespace();
        this.f_129347_.expect(p_129353_);
    }
}

