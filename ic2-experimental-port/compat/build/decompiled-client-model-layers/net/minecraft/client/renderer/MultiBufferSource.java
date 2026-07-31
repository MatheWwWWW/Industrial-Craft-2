/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;

public interface MultiBufferSource {
    public static BufferSource m_109898_(BufferBuilder p_109899_) {
        return MultiBufferSource.m_109900_((Map<RenderType, BufferBuilder>)ImmutableMap.of(), p_109899_);
    }

    public static BufferSource m_109900_(Map<RenderType, BufferBuilder> p_109901_, BufferBuilder p_109902_) {
        return new BufferSource(p_109902_, p_109901_);
    }

    public VertexConsumer m_6299_(RenderType var1);

    public static class BufferSource
    implements MultiBufferSource {
        protected final BufferBuilder f_109904_;
        protected final Map<RenderType, BufferBuilder> f_109905_;
        protected Optional<RenderType> f_109906_ = Optional.empty();
        protected final Set<BufferBuilder> f_109907_ = Sets.newHashSet();

        protected BufferSource(BufferBuilder p_109909_, Map<RenderType, BufferBuilder> p_109910_) {
            this.f_109904_ = p_109909_;
            this.f_109905_ = p_109910_;
        }

        @Override
        public VertexConsumer m_6299_(RenderType p_109919_) {
            Optional<RenderType> $$1 = p_109919_.m_110406_();
            BufferBuilder $$2 = this.m_109914_(p_109919_);
            if (!Objects.equals(this.f_109906_, $$1) || !p_109919_.m_234326_()) {
                RenderType $$3;
                if (this.f_109906_.isPresent() && !this.f_109905_.containsKey($$3 = this.f_109906_.get())) {
                    this.m_109912_($$3);
                }
                if (this.f_109907_.add($$2)) {
                    $$2.m_166779_(p_109919_.m_173186_(), p_109919_.m_110508_());
                }
                this.f_109906_ = $$1;
            }
            return $$2;
        }

        private BufferBuilder m_109914_(RenderType p_109915_) {
            return this.f_109905_.getOrDefault(p_109915_, this.f_109904_);
        }

        public void m_173043_() {
            if (this.f_109906_.isPresent()) {
                RenderType $$0 = this.f_109906_.get();
                if (!this.f_109905_.containsKey($$0)) {
                    this.m_109912_($$0);
                }
                this.f_109906_ = Optional.empty();
            }
        }

        public void m_109911_() {
            this.f_109906_.ifPresent(p_109917_ -> {
                VertexConsumer $$1 = this.m_6299_((RenderType)p_109917_);
                if ($$1 == this.f_109904_) {
                    this.m_109912_((RenderType)p_109917_);
                }
            });
            for (RenderType $$0 : this.f_109905_.keySet()) {
                this.m_109912_($$0);
            }
        }

        public void m_109912_(RenderType p_109913_) {
            BufferBuilder $$1 = this.m_109914_(p_109913_);
            boolean $$2 = Objects.equals(this.f_109906_, p_109913_.m_110406_());
            if (!$$2 && $$1 == this.f_109904_) {
                return;
            }
            if (!this.f_109907_.remove($$1)) {
                return;
            }
            p_109913_.m_110412_($$1, 0, 0, 0);
            if ($$2) {
                this.f_109906_ = Optional.empty();
            }
        }
    }
}

