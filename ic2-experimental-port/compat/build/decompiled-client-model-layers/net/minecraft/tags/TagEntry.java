/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.tags;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

public class TagEntry {
    private static final Codec<TagEntry> f_215912_ = RecordCodecBuilder.create(p_215937_ -> p_215937_.group((App)ExtraCodecs.f_216161_.fieldOf("id").forGetter(TagEntry::m_215924_), (App)Codec.BOOL.optionalFieldOf("required", (Object)true).forGetter(p_215952_ -> p_215952_.f_215915_)).apply((Applicative)p_215937_, TagEntry::new));
    public static final Codec<TagEntry> f_215911_ = Codec.either(ExtraCodecs.f_216161_, f_215912_).xmap(p_215935_ -> (TagEntry)p_215935_.map(p_215933_ -> new TagEntry((ExtraCodecs.TagOrElementLocation)p_215933_, true), p_215946_ -> p_215946_), p_215931_ -> p_215931_.f_215915_ ? Either.left((Object)p_215931_.m_215924_()) : Either.right((Object)p_215931_));
    private final ResourceLocation f_215913_;
    private final boolean f_215914_;
    private final boolean f_215915_;

    private TagEntry(ResourceLocation p_215918_, boolean p_215919_, boolean p_215920_) {
        this.f_215913_ = p_215918_;
        this.f_215914_ = p_215919_;
        this.f_215915_ = p_215920_;
    }

    private TagEntry(ExtraCodecs.TagOrElementLocation p_215922_, boolean p_215923_) {
        this.f_215913_ = p_215922_.f_216195_();
        this.f_215914_ = p_215922_.f_216196_();
        this.f_215915_ = p_215923_;
    }

    private ExtraCodecs.TagOrElementLocation m_215924_() {
        return new ExtraCodecs.TagOrElementLocation(this.f_215913_, this.f_215914_);
    }

    public static TagEntry m_215925_(ResourceLocation p_215926_) {
        return new TagEntry(p_215926_, false, true);
    }

    public static TagEntry m_215943_(ResourceLocation p_215944_) {
        return new TagEntry(p_215944_, false, false);
    }

    public static TagEntry m_215949_(ResourceLocation p_215950_) {
        return new TagEntry(p_215950_, true, true);
    }

    public static TagEntry m_215953_(ResourceLocation p_215954_) {
        return new TagEntry(p_215954_, true, false);
    }

    public <T> boolean m_215927_(Lookup<T> p_215928_, Consumer<T> p_215929_) {
        if (this.f_215914_) {
            Collection<T> $$2 = p_215928_.m_214048_(this.f_215913_);
            if ($$2 == null) {
                return !this.f_215915_;
            }
            $$2.forEach(p_215929_);
        } else {
            T $$3 = p_215928_.m_213619_(this.f_215913_);
            if ($$3 == null) {
                return !this.f_215915_;
            }
            p_215929_.accept($$3);
        }
        return true;
    }

    public void m_215938_(Consumer<ResourceLocation> p_215939_) {
        if (this.f_215914_ && this.f_215915_) {
            p_215939_.accept(this.f_215913_);
        }
    }

    public void m_215947_(Consumer<ResourceLocation> p_215948_) {
        if (this.f_215914_ && !this.f_215915_) {
            p_215948_.accept(this.f_215913_);
        }
    }

    public boolean m_215940_(Predicate<ResourceLocation> p_215941_, Predicate<ResourceLocation> p_215942_) {
        return !this.f_215915_ || (this.f_215914_ ? p_215942_ : p_215941_).test(this.f_215913_);
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        if (this.f_215914_) {
            $$0.append('#');
        }
        $$0.append(this.f_215913_);
        if (!this.f_215915_) {
            $$0.append('?');
        }
        return $$0.toString();
    }

    public static interface Lookup<T> {
        @Nullable
        public T m_213619_(ResourceLocation var1);

        @Nullable
        public Collection<T> m_214048_(ResourceLocation var1);
    }
}

