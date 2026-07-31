/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.ExtraCodecs;
import org.slf4j.Logger;

public class ResourceFilterSection {
    static final Logger f_215513_ = LogUtils.getLogger();
    static final Codec<ResourceFilterSection> f_215514_ = RecordCodecBuilder.create(p_215522_ -> p_215522_.group((App)Codec.list(ResourceLocationPattern.f_215539_).fieldOf("block").forGetter(p_215520_ -> p_215520_.f_215515_)).apply((Applicative)p_215522_, ResourceFilterSection::new));
    public static final MetadataSectionSerializer<ResourceFilterSection> f_215512_ = new MetadataSectionSerializer<ResourceFilterSection>(){

        @Override
        public String m_7991_() {
            return "filter";
        }

        @Override
        public ResourceFilterSection m_6322_(JsonObject p_215538_) {
            return (ResourceFilterSection)f_215514_.parse((DynamicOps)JsonOps.INSTANCE, (Object)p_215538_).getOrThrow(false, arg_0 -> ((Logger)f_215513_).error(arg_0));
        }

        @Override
        public /* synthetic */ Object m_6322_(JsonObject jsonObject) {
            return this.m_6322_(jsonObject);
        }
    };
    private final List<ResourceLocationPattern> f_215515_;

    public ResourceFilterSection(List<ResourceLocationPattern> p_215518_) {
        this.f_215515_ = List.copyOf(p_215518_);
    }

    public boolean m_215523_(String p_215524_) {
        return this.f_215515_.stream().anyMatch(p_215532_ -> p_215532_.f_215541_.test(p_215524_));
    }

    public boolean m_215528_(String p_215529_) {
        return this.f_215515_.stream().anyMatch(p_215527_ -> p_215527_.f_215543_.test(p_215529_));
    }

    static class ResourceLocationPattern
    implements Predicate<ResourceLocation> {
        static final Codec<ResourceLocationPattern> f_215539_ = RecordCodecBuilder.create(p_215553_ -> p_215553_.group((App)ExtraCodecs.f_216158_.optionalFieldOf("namespace").forGetter(p_215557_ -> p_215557_.f_215540_), (App)ExtraCodecs.f_216158_.optionalFieldOf("path").forGetter(p_215551_ -> p_215551_.f_215542_)).apply((Applicative)p_215553_, ResourceLocationPattern::new));
        private final Optional<Pattern> f_215540_;
        final Predicate<String> f_215541_;
        private final Optional<Pattern> f_215542_;
        final Predicate<String> f_215543_;

        private ResourceLocationPattern(Optional<Pattern> p_215546_, Optional<Pattern> p_215547_) {
            this.f_215540_ = p_215546_;
            this.f_215541_ = p_215546_.map(Pattern::asPredicate).orElse(p_215559_ -> true);
            this.f_215542_ = p_215547_;
            this.f_215543_ = p_215547_.map(Pattern::asPredicate).orElse(p_215555_ -> true);
        }

        @Override
        public boolean test(ResourceLocation p_215549_) {
            return this.f_215541_.test(p_215549_.m_135827_()) && this.f_215543_.test(p_215549_.m_135815_());
        }

        @Override
        public /* synthetic */ boolean test(Object object) {
            return this.test((ResourceLocation)object);
        }
    }
}

