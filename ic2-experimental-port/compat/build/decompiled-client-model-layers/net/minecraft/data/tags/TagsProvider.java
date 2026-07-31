/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  org.slf4j.Logger
 */
package net.minecraft.data.tags;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import org.slf4j.Logger;

public abstract class TagsProvider<T>
implements DataProvider {
    private static final Logger f_126541_ = LogUtils.getLogger();
    protected final DataGenerator.PathProvider f_236439_;
    protected final Registry<T> f_126540_;
    private final Map<ResourceLocation, TagBuilder> f_126543_ = Maps.newLinkedHashMap();

    protected TagsProvider(DataGenerator p_126546_, Registry<T> p_126547_) {
        this.f_236439_ = p_126546_.m_236036_(DataGenerator.Target.DATA_PACK, TagManager.m_203918_(p_126547_.m_123023_()));
        this.f_126540_ = p_126547_;
    }

    @Override
    public final String m_6055_() {
        return "Tags for " + this.f_126540_.m_123023_().m_135782_();
    }

    protected abstract void m_6577_();

    @Override
    public void m_213708_(CachedOutput p_236446_) {
        this.f_126543_.clear();
        this.m_6577_();
        this.f_126543_.forEach((p_236449_, p_236450_) -> {
            List<TagEntry> $$3 = p_236450_.m_215904_();
            List<TagEntry> $$4 = $$3.stream().filter(p_236444_ -> !p_236444_.m_215940_(this.f_126540_::m_7804_, this.f_126543_::containsKey)).toList();
            if (!$$4.isEmpty()) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Couldn't define tag %s as it is missing following references: %s", p_236449_, $$4.stream().map(Objects::toString).collect(Collectors.joining(","))));
            }
            JsonElement $$5 = (JsonElement)TagFile.f_215958_.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)new TagFile($$3, false)).getOrThrow(false, arg_0 -> ((Logger)f_126541_).error(arg_0));
            Path $$6 = this.f_236439_.m_236048_((ResourceLocation)p_236449_);
            try {
                DataProvider.m_236072_(p_236446_, $$5, $$6);
            }
            catch (IOException $$7) {
                f_126541_.error("Couldn't save tags to {}", (Object)$$6, (Object)$$7);
            }
        });
    }

    protected TagAppender<T> m_206424_(TagKey<T> p_206425_) {
        TagBuilder $$1 = this.m_236451_(p_206425_);
        return new TagAppender<T>($$1, this.f_126540_);
    }

    protected TagBuilder m_236451_(TagKey<T> p_236452_) {
        return this.f_126543_.computeIfAbsent(p_236452_.f_203868_(), p_236442_ -> TagBuilder.m_215899_());
    }

    protected static class TagAppender<T> {
        private final TagBuilder f_126568_;
        private final Registry<T> f_126569_;

        TagAppender(TagBuilder p_236454_, Registry<T> p_236455_) {
            this.f_126568_ = p_236454_;
            this.f_126569_ = p_236455_;
        }

        public TagAppender<T> m_126582_(T p_126583_) {
            this.f_126568_.m_215900_(this.f_126569_.m_7981_(p_126583_));
            return this;
        }

        @SafeVarargs
        public final TagAppender<T> m_211101_(ResourceKey<T> ... p_211102_) {
            for (ResourceKey<T> $$1 : p_211102_) {
                this.f_126568_.m_215900_($$1.m_135782_());
            }
            return this;
        }

        public TagAppender<T> m_176839_(ResourceLocation p_176840_) {
            this.f_126568_.m_215905_(p_176840_);
            return this;
        }

        public TagAppender<T> m_206428_(TagKey<T> p_206429_) {
            this.f_126568_.m_215907_(p_206429_.f_203868_());
            return this;
        }

        public TagAppender<T> m_176841_(ResourceLocation p_176842_) {
            this.f_126568_.m_215909_(p_176842_);
            return this;
        }

        @SafeVarargs
        public final TagAppender<T> m_126584_(T ... p_126585_) {
            Stream.of(p_126585_).map(this.f_126569_::m_7981_).forEach(p_126587_ -> this.f_126568_.m_215900_((ResourceLocation)p_126587_));
            return this;
        }
    }
}

