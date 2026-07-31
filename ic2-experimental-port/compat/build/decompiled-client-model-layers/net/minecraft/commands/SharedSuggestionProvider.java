/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;

public interface SharedSuggestionProvider {
    public Collection<String> m_5982_();

    default public Collection<String> m_240700_() {
        return this.m_5982_();
    }

    default public Collection<String> m_6264_() {
        return Collections.emptyList();
    }

    public Collection<String> m_5983_();

    public Collection<ResourceLocation> m_5984_();

    public Stream<ResourceLocation> m_6860_();

    public CompletableFuture<Suggestions> m_212155_(CommandContext<?> var1);

    default public Collection<TextCoordinates> m_6265_() {
        return Collections.singleton(TextCoordinates.f_82988_);
    }

    default public Collection<TextCoordinates> m_6284_() {
        return Collections.singleton(TextCoordinates.f_82988_);
    }

    public Set<ResourceKey<Level>> m_6553_();

    public RegistryAccess m_5894_();

    default public void m_212335_(Registry<?> p_212336_, ElementSuggestionType p_212337_, SuggestionsBuilder p_212338_) {
        if (p_212337_.m_212351_()) {
            SharedSuggestionProvider.m_205106_(p_212336_.m_203613_().map(TagKey::f_203868_), p_212338_, "#");
        }
        if (p_212337_.m_212352_()) {
            SharedSuggestionProvider.m_82926_(p_212336_.m_6566_(), p_212338_);
        }
    }

    public CompletableFuture<Suggestions> m_212095_(ResourceKey<? extends Registry<?>> var1, ElementSuggestionType var2, SuggestionsBuilder var3, CommandContext<?> var4);

    public boolean m_6761_(int var1);

    public static <T> void m_82944_(Iterable<T> p_82945_, String p_82946_, Function<T, ResourceLocation> p_82947_, Consumer<T> p_82948_) {
        boolean $$4 = p_82946_.indexOf(58) > -1;
        for (T $$5 : p_82945_) {
            ResourceLocation $$6 = p_82947_.apply($$5);
            if ($$4) {
                String $$7 = $$6.toString();
                if (!SharedSuggestionProvider.m_82949_(p_82946_, $$7)) continue;
                p_82948_.accept($$5);
                continue;
            }
            if (!SharedSuggestionProvider.m_82949_(p_82946_, $$6.m_135827_()) && (!$$6.m_135827_().equals("minecraft") || !SharedSuggestionProvider.m_82949_(p_82946_, $$6.m_135815_()))) continue;
            p_82948_.accept($$5);
        }
    }

    public static <T> void m_82938_(Iterable<T> p_82939_, String p_82940_, String p_82941_, Function<T, ResourceLocation> p_82942_, Consumer<T> p_82943_) {
        if (p_82940_.isEmpty()) {
            p_82939_.forEach(p_82943_);
        } else {
            String $$5 = Strings.commonPrefix((CharSequence)p_82940_, (CharSequence)p_82941_);
            if (!$$5.isEmpty()) {
                String $$6 = p_82940_.substring($$5.length());
                SharedSuggestionProvider.m_82944_(p_82939_, $$6, p_82942_, p_82943_);
            }
        }
    }

    public static CompletableFuture<Suggestions> m_82929_(Iterable<ResourceLocation> p_82930_, SuggestionsBuilder p_82931_, String p_82932_) {
        String $$3 = p_82931_.getRemaining().toLowerCase(Locale.ROOT);
        SharedSuggestionProvider.m_82938_(p_82930_, $$3, p_82932_, p_82985_ -> p_82985_, p_82917_ -> p_82931_.suggest(p_82932_ + p_82917_));
        return p_82931_.buildFuture();
    }

    public static CompletableFuture<Suggestions> m_205106_(Stream<ResourceLocation> p_205107_, SuggestionsBuilder p_205108_, String p_205109_) {
        return SharedSuggestionProvider.m_82929_(p_205107_::iterator, p_205108_, p_205109_);
    }

    public static CompletableFuture<Suggestions> m_82926_(Iterable<ResourceLocation> p_82927_, SuggestionsBuilder p_82928_) {
        String $$2 = p_82928_.getRemaining().toLowerCase(Locale.ROOT);
        SharedSuggestionProvider.m_82944_(p_82927_, $$2, p_82966_ -> p_82966_, p_82925_ -> p_82928_.suggest(p_82925_.toString()));
        return p_82928_.buildFuture();
    }

    public static <T> CompletableFuture<Suggestions> m_82933_(Iterable<T> p_82934_, SuggestionsBuilder p_82935_, Function<T, ResourceLocation> p_82936_, Function<T, Message> p_82937_) {
        String $$4 = p_82935_.getRemaining().toLowerCase(Locale.ROOT);
        SharedSuggestionProvider.m_82944_(p_82934_, $$4, p_82936_, p_82922_ -> p_82935_.suggest(((ResourceLocation)p_82936_.apply(p_82922_)).toString(), (Message)p_82937_.apply(p_82922_)));
        return p_82935_.buildFuture();
    }

    public static CompletableFuture<Suggestions> m_82957_(Stream<ResourceLocation> p_82958_, SuggestionsBuilder p_82959_) {
        return SharedSuggestionProvider.m_82926_(p_82958_::iterator, p_82959_);
    }

    public static <T> CompletableFuture<Suggestions> m_82960_(Stream<T> p_82961_, SuggestionsBuilder p_82962_, Function<T, ResourceLocation> p_82963_, Function<T, Message> p_82964_) {
        return SharedSuggestionProvider.m_82933_(p_82961_::iterator, p_82962_, p_82963_, p_82964_);
    }

    public static CompletableFuture<Suggestions> m_82952_(String p_82953_, Collection<TextCoordinates> p_82954_, SuggestionsBuilder p_82955_, Predicate<String> p_82956_) {
        ArrayList $$4;
        block4: {
            String[] $$7;
            block5: {
                block3: {
                    $$4 = Lists.newArrayList();
                    if (!Strings.isNullOrEmpty((String)p_82953_)) break block3;
                    for (TextCoordinates $$5 : p_82954_) {
                        String $$6 = $$5.f_82989_ + " " + $$5.f_82990_ + " " + $$5.f_82991_;
                        if (!p_82956_.test($$6)) continue;
                        $$4.add($$5.f_82989_);
                        $$4.add($$5.f_82989_ + " " + $$5.f_82990_);
                        $$4.add($$6);
                    }
                    break block4;
                }
                $$7 = p_82953_.split(" ");
                if ($$7.length != 1) break block5;
                for (TextCoordinates $$8 : p_82954_) {
                    String $$9 = $$7[0] + " " + $$8.f_82990_ + " " + $$8.f_82991_;
                    if (!p_82956_.test($$9)) continue;
                    $$4.add($$7[0] + " " + $$8.f_82990_);
                    $$4.add($$9);
                }
                break block4;
            }
            if ($$7.length != 2) break block4;
            for (TextCoordinates $$10 : p_82954_) {
                String $$11 = $$7[0] + " " + $$7[1] + " " + $$10.f_82991_;
                if (!p_82956_.test($$11)) continue;
                $$4.add($$11);
            }
        }
        return SharedSuggestionProvider.m_82970_($$4, p_82955_);
    }

    public static CompletableFuture<Suggestions> m_82976_(String p_82977_, Collection<TextCoordinates> p_82978_, SuggestionsBuilder p_82979_, Predicate<String> p_82980_) {
        ArrayList $$4;
        block3: {
            block2: {
                $$4 = Lists.newArrayList();
                if (!Strings.isNullOrEmpty((String)p_82977_)) break block2;
                for (TextCoordinates $$5 : p_82978_) {
                    String $$6 = $$5.f_82989_ + " " + $$5.f_82991_;
                    if (!p_82980_.test($$6)) continue;
                    $$4.add($$5.f_82989_);
                    $$4.add($$6);
                }
                break block3;
            }
            String[] $$7 = p_82977_.split(" ");
            if ($$7.length != 1) break block3;
            for (TextCoordinates $$8 : p_82978_) {
                String $$9 = $$7[0] + " " + $$8.f_82991_;
                if (!p_82980_.test($$9)) continue;
                $$4.add($$9);
            }
        }
        return SharedSuggestionProvider.m_82970_($$4, p_82979_);
    }

    public static CompletableFuture<Suggestions> m_82970_(Iterable<String> p_82971_, SuggestionsBuilder p_82972_) {
        String $$2 = p_82972_.getRemaining().toLowerCase(Locale.ROOT);
        for (String $$3 : p_82971_) {
            if (!SharedSuggestionProvider.m_82949_($$2, $$3.toLowerCase(Locale.ROOT))) continue;
            p_82972_.suggest($$3);
        }
        return p_82972_.buildFuture();
    }

    public static CompletableFuture<Suggestions> m_82981_(Stream<String> p_82982_, SuggestionsBuilder p_82983_) {
        String $$2 = p_82983_.getRemaining().toLowerCase(Locale.ROOT);
        p_82982_.filter(p_82975_ -> SharedSuggestionProvider.m_82949_($$2, p_82975_.toLowerCase(Locale.ROOT))).forEach(arg_0 -> ((SuggestionsBuilder)p_82983_).suggest(arg_0));
        return p_82983_.buildFuture();
    }

    public static CompletableFuture<Suggestions> m_82967_(String[] p_82968_, SuggestionsBuilder p_82969_) {
        String $$2 = p_82969_.getRemaining().toLowerCase(Locale.ROOT);
        for (String $$3 : p_82968_) {
            if (!SharedSuggestionProvider.m_82949_($$2, $$3.toLowerCase(Locale.ROOT))) continue;
            p_82969_.suggest($$3);
        }
        return p_82969_.buildFuture();
    }

    public static <T> CompletableFuture<Suggestions> m_165916_(Iterable<T> p_165917_, SuggestionsBuilder p_165918_, Function<T, String> p_165919_, Function<T, Message> p_165920_) {
        String $$4 = p_165918_.getRemaining().toLowerCase(Locale.ROOT);
        for (T $$5 : p_165917_) {
            String $$6 = p_165919_.apply($$5);
            if (!SharedSuggestionProvider.m_82949_($$4, $$6.toLowerCase(Locale.ROOT))) continue;
            p_165918_.suggest($$6, p_165920_.apply($$5));
        }
        return p_165918_.buildFuture();
    }

    public static boolean m_82949_(String p_82950_, String p_82951_) {
        int $$2 = 0;
        while (!p_82951_.startsWith(p_82950_, $$2)) {
            if (($$2 = p_82951_.indexOf(95, $$2)) < 0) {
                return false;
            }
            ++$$2;
        }
        return true;
    }

    public static class TextCoordinates {
        public static final TextCoordinates f_82987_ = new TextCoordinates("^", "^", "^");
        public static final TextCoordinates f_82988_ = new TextCoordinates("~", "~", "~");
        public final String f_82989_;
        public final String f_82990_;
        public final String f_82991_;

        public TextCoordinates(String p_82994_, String p_82995_, String p_82996_) {
            this.f_82989_ = p_82994_;
            this.f_82990_ = p_82995_;
            this.f_82991_ = p_82996_;
        }
    }

    public static final class ElementSuggestionType
    extends Enum<ElementSuggestionType> {
        public static final /* enum */ ElementSuggestionType TAGS = new ElementSuggestionType();
        public static final /* enum */ ElementSuggestionType ELEMENTS = new ElementSuggestionType();
        public static final /* enum */ ElementSuggestionType ALL = new ElementSuggestionType();
        private static final /* synthetic */ ElementSuggestionType[] $VALUES;

        public static ElementSuggestionType[] values() {
            return (ElementSuggestionType[])$VALUES.clone();
        }

        public static ElementSuggestionType valueOf(String p_212355_) {
            return Enum.valueOf(ElementSuggestionType.class, p_212355_);
        }

        public boolean m_212351_() {
            return this == TAGS || this == ALL;
        }

        public boolean m_212352_() {
            return this == ELEMENTS || this == ALL;
        }

        private static /* synthetic */ ElementSuggestionType[] m_212353_() {
            return new ElementSuggestionType[]{TAGS, ELEMENTS, ALL};
        }

        static {
            $VALUES = ElementSuggestionType.m_212353_();
        }
    }
}

