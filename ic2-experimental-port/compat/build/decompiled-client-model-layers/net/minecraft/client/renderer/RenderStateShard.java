/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  org.apache.commons.lang3.tuple.Triple
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

public abstract class RenderStateShard {
    private static final float f_173089_ = 0.99975586f;
    protected final String f_110133_;
    private final Runnable f_110131_;
    private final Runnable f_110132_;
    protected static final TransparencyStateShard f_110134_ = new TransparencyStateShard("no_transparency", () -> RenderSystem.m_69461_(), () -> {});
    protected static final TransparencyStateShard f_110135_ = new TransparencyStateShard("additive_transparency", () -> {
        RenderSystem.m_69478_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.m_69461_();
        RenderSystem.m_69453_();
    });
    protected static final TransparencyStateShard f_110136_ = new TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.m_69478_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.m_69461_();
        RenderSystem.m_69453_();
    });
    protected static final TransparencyStateShard f_110137_ = new TransparencyStateShard("glint_transparency", () -> {
        RenderSystem.m_69478_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.m_69461_();
        RenderSystem.m_69453_();
    });
    protected static final TransparencyStateShard f_110138_ = new TransparencyStateShard("crumbling_transparency", () -> {
        RenderSystem.m_69478_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }, () -> {
        RenderSystem.m_69461_();
        RenderSystem.m_69453_();
    });
    protected static final TransparencyStateShard f_110139_ = new TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.m_69478_();
        RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.m_69461_();
        RenderSystem.m_69453_();
    });
    protected static final ShaderStateShard f_173096_ = new ShaderStateShard();
    protected static final ShaderStateShard f_173097_ = new ShaderStateShard(GameRenderer::m_172823_);
    protected static final ShaderStateShard f_173098_ = new ShaderStateShard(GameRenderer::m_172826_);
    protected static final ShaderStateShard f_173099_ = new ShaderStateShard(GameRenderer::m_172832_);
    protected static final ShaderStateShard f_173100_ = new ShaderStateShard(GameRenderer::m_172808_);
    protected static final ShaderStateShard f_173101_ = new ShaderStateShard(GameRenderer::m_172814_);
    protected static final ShaderStateShard f_173102_ = new ShaderStateShard(GameRenderer::m_172817_);
    protected static final ShaderStateShard f_173103_ = new ShaderStateShard(GameRenderer::m_172835_);
    protected static final ShaderStateShard f_173104_ = new ShaderStateShard(GameRenderer::m_172811_);
    protected static final ShaderStateShard f_173105_ = new ShaderStateShard(GameRenderer::m_172640_);
    protected static final ShaderStateShard f_173106_ = new ShaderStateShard(GameRenderer::m_172643_);
    protected static final ShaderStateShard f_173107_ = new ShaderStateShard(GameRenderer::m_172646_);
    protected static final ShaderStateShard f_173108_ = new ShaderStateShard(GameRenderer::m_172649_);
    protected static final ShaderStateShard f_173109_ = new ShaderStateShard(GameRenderer::m_172652_);
    protected static final ShaderStateShard f_173110_ = new ShaderStateShard(GameRenderer::m_172655_);
    protected static final ShaderStateShard f_173111_ = new ShaderStateShard(GameRenderer::m_172658_);
    protected static final ShaderStateShard f_173112_ = new ShaderStateShard(GameRenderer::m_172661_);
    protected static final ShaderStateShard f_173113_ = new ShaderStateShard(GameRenderer::m_172664_);
    protected static final ShaderStateShard f_173114_ = new ShaderStateShard(GameRenderer::m_172667_);
    protected static final ShaderStateShard f_173063_ = new ShaderStateShard(GameRenderer::m_172670_);
    protected static final ShaderStateShard f_173064_ = new ShaderStateShard(GameRenderer::m_172673_);
    protected static final ShaderStateShard f_173065_ = new ShaderStateShard(GameRenderer::m_172676_);
    protected static final ShaderStateShard f_173066_ = new ShaderStateShard(GameRenderer::m_172679_);
    protected static final ShaderStateShard f_234323_ = new ShaderStateShard(GameRenderer::m_234223_);
    protected static final ShaderStateShard f_173067_ = new ShaderStateShard(GameRenderer::m_172682_);
    protected static final ShaderStateShard f_173068_ = new ShaderStateShard(GameRenderer::m_172685_);
    protected static final ShaderStateShard f_173069_ = new ShaderStateShard(GameRenderer::m_172688_);
    protected static final ShaderStateShard f_173070_ = new ShaderStateShard(GameRenderer::m_172691_);
    protected static final ShaderStateShard f_173071_ = new ShaderStateShard(GameRenderer::m_172694_);
    protected static final ShaderStateShard f_173072_ = new ShaderStateShard(GameRenderer::m_172697_);
    protected static final ShaderStateShard f_173073_ = new ShaderStateShard(GameRenderer::m_172700_);
    protected static final ShaderStateShard f_173074_ = new ShaderStateShard(GameRenderer::m_172703_);
    protected static final ShaderStateShard f_173075_ = new ShaderStateShard(GameRenderer::m_172706_);
    protected static final ShaderStateShard f_173076_ = new ShaderStateShard(GameRenderer::m_172709_);
    protected static final ShaderStateShard f_173077_ = new ShaderStateShard(GameRenderer::m_172712_);
    protected static final ShaderStateShard f_173078_ = new ShaderStateShard(GameRenderer::m_172738_);
    protected static final ShaderStateShard f_173079_ = new ShaderStateShard(GameRenderer::m_172741_);
    protected static final ShaderStateShard f_173080_ = new ShaderStateShard(GameRenderer::m_172744_);
    protected static final ShaderStateShard f_173081_ = new ShaderStateShard(GameRenderer::m_172745_);
    protected static final ShaderStateShard f_173082_ = new ShaderStateShard(GameRenderer::m_172746_);
    protected static final ShaderStateShard f_173083_ = new ShaderStateShard(GameRenderer::m_172747_);
    protected static final ShaderStateShard f_173084_ = new ShaderStateShard(GameRenderer::m_172748_);
    protected static final ShaderStateShard f_173085_ = new ShaderStateShard(GameRenderer::m_172758_);
    protected static final ShaderStateShard f_173086_ = new ShaderStateShard(GameRenderer::m_172749_);
    protected static final ShaderStateShard f_173087_ = new ShaderStateShard(GameRenderer::m_172750_);
    protected static final ShaderStateShard f_173088_ = new ShaderStateShard(GameRenderer::m_172751_);
    protected static final ShaderStateShard f_173090_ = new ShaderStateShard(GameRenderer::m_172752_);
    protected static final ShaderStateShard f_173091_ = new ShaderStateShard(GameRenderer::m_172753_);
    protected static final ShaderStateShard f_173092_ = new ShaderStateShard(GameRenderer::m_172754_);
    protected static final ShaderStateShard f_173093_ = new ShaderStateShard(GameRenderer::m_172755_);
    protected static final ShaderStateShard f_173094_ = new ShaderStateShard(GameRenderer::m_172756_);
    protected static final ShaderStateShard f_173095_ = new ShaderStateShard(GameRenderer::m_172757_);
    protected static final TextureStateShard f_110145_ = new TextureStateShard(TextureAtlas.f_118259_, false, true);
    protected static final TextureStateShard f_110146_ = new TextureStateShard(TextureAtlas.f_118259_, false, false);
    protected static final EmptyTextureStateShard f_110147_ = new EmptyTextureStateShard();
    protected static final TexturingStateShard f_110148_ = new TexturingStateShard("default_texturing", () -> {}, () -> {});
    protected static final TexturingStateShard f_110150_ = new TexturingStateShard("glint_texturing", () -> RenderStateShard.m_110186_(8.0f), () -> RenderSystem.m_157423_());
    protected static final TexturingStateShard f_110151_ = new TexturingStateShard("entity_glint_texturing", () -> RenderStateShard.m_110186_(0.16f), () -> RenderSystem.m_157423_());
    protected static final LightmapStateShard f_110152_ = new LightmapStateShard(true);
    protected static final LightmapStateShard f_110153_ = new LightmapStateShard(false);
    protected static final OverlayStateShard f_110154_ = new OverlayStateShard(true);
    protected static final OverlayStateShard f_110155_ = new OverlayStateShard(false);
    protected static final CullStateShard f_110158_ = new CullStateShard(true);
    protected static final CullStateShard f_110110_ = new CullStateShard(false);
    protected static final DepthTestStateShard f_110111_ = new DepthTestStateShard("always", 519);
    protected static final DepthTestStateShard f_110112_ = new DepthTestStateShard("==", 514);
    protected static final DepthTestStateShard f_110113_ = new DepthTestStateShard("<=", 515);
    protected static final WriteMaskStateShard f_110114_ = new WriteMaskStateShard(true, true);
    protected static final WriteMaskStateShard f_110115_ = new WriteMaskStateShard(true, false);
    protected static final WriteMaskStateShard f_110116_ = new WriteMaskStateShard(false, true);
    protected static final LayeringStateShard f_110117_ = new LayeringStateShard("no_layering", () -> {}, () -> {});
    protected static final LayeringStateShard f_110118_ = new LayeringStateShard("polygon_offset_layering", () -> {
        RenderSystem.m_69863_(-1.0f, -10.0f);
        RenderSystem.m_69486_();
    }, () -> {
        RenderSystem.m_69863_(0.0f, 0.0f);
        RenderSystem.m_69469_();
    });
    protected static final LayeringStateShard f_110119_ = new LayeringStateShard("view_offset_z_layering", () -> {
        PoseStack $$0 = RenderSystem.m_157191_();
        $$0.m_85836_();
        $$0.m_85841_(0.99975586f, 0.99975586f, 0.99975586f);
        RenderSystem.m_157182_();
    }, () -> {
        PoseStack $$0 = RenderSystem.m_157191_();
        $$0.m_85849_();
        RenderSystem.m_157182_();
    });
    protected static final OutputStateShard f_110123_ = new OutputStateShard("main_target", () -> {}, () -> {});
    protected static final OutputStateShard f_110124_ = new OutputStateShard("outline_target", () -> Minecraft.m_91087_().f_91060_.m_109827_().m_83947_(false), () -> Minecraft.m_91087_().m_91385_().m_83947_(false));
    protected static final OutputStateShard f_110125_ = new OutputStateShard("translucent_target", () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109828_().m_83947_(false);
        }
    }, () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
        }
    });
    protected static final OutputStateShard f_110126_ = new OutputStateShard("particles_target", () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109830_().m_83947_(false);
        }
    }, () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
        }
    });
    protected static final OutputStateShard f_110127_ = new OutputStateShard("weather_target", () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109831_().m_83947_(false);
        }
    }, () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
        }
    });
    protected static final OutputStateShard f_110128_ = new OutputStateShard("clouds_target", () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109832_().m_83947_(false);
        }
    }, () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
        }
    });
    protected static final OutputStateShard f_110129_ = new OutputStateShard("item_entity_target", () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().f_91060_.m_109829_().m_83947_(false);
        }
    }, () -> {
        if (Minecraft.m_91085_()) {
            Minecraft.m_91087_().m_91385_().m_83947_(false);
        }
    });
    protected static final LineStateShard f_110130_ = new LineStateShard(OptionalDouble.of(1.0));

    public RenderStateShard(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        this.f_110133_ = p_110161_;
        this.f_110131_ = p_110162_;
        this.f_110132_ = p_110163_;
    }

    public void m_110185_() {
        this.f_110131_.run();
    }

    public void m_110188_() {
        this.f_110132_.run();
    }

    public String toString() {
        return this.f_110133_;
    }

    private static void m_110186_(float p_110187_) {
        long $$1 = Util.m_137550_() * 8L;
        float $$2 = (float)($$1 % 110000L) / 110000.0f;
        float $$3 = (float)($$1 % 30000L) / 30000.0f;
        Matrix4f $$4 = Matrix4f.m_27653_(-$$2, $$3, 0.0f);
        $$4.m_27646_(Vector3f.f_122227_.m_122240_(10.0f));
        $$4.m_27644_(Matrix4f.m_27632_(p_110187_, p_110187_, p_110187_));
        RenderSystem.m_157459_($$4);
    }

    protected static class TransparencyStateShard
    extends RenderStateShard {
        public TransparencyStateShard(String p_110353_, Runnable p_110354_, Runnable p_110355_) {
            super(p_110353_, p_110354_, p_110355_);
        }
    }

    protected static class ShaderStateShard
    extends RenderStateShard {
        private final Optional<Supplier<ShaderInstance>> f_173136_;

        public ShaderStateShard(Supplier<ShaderInstance> p_173139_) {
            super("shader", () -> RenderSystem.m_157427_(p_173139_), () -> {});
            this.f_173136_ = Optional.of(p_173139_);
        }

        public ShaderStateShard() {
            super("shader", () -> RenderSystem.m_157427_(() -> null), () -> {});
            this.f_173136_ = Optional.empty();
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[" + this.f_173136_ + "]";
        }
    }

    protected static class TextureStateShard
    extends EmptyTextureStateShard {
        private final Optional<ResourceLocation> f_110328_;
        private final boolean f_110329_;
        private final boolean f_110330_;

        public TextureStateShard(ResourceLocation p_110333_, boolean p_110334_, boolean p_110335_) {
            super(() -> {
                RenderSystem.m_69493_();
                TextureManager $$3 = Minecraft.m_91087_().m_91097_();
                $$3.m_118506_(p_110333_).m_117960_(p_110334_, p_110335_);
                RenderSystem.m_157456_(0, p_110333_);
            }, () -> {});
            this.f_110328_ = Optional.of(p_110333_);
            this.f_110329_ = p_110334_;
            this.f_110330_ = p_110335_;
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[" + this.f_110328_ + "(blur=" + this.f_110329_ + ", mipmap=" + this.f_110330_ + ")]";
        }

        @Override
        protected Optional<ResourceLocation> m_142706_() {
            return this.f_110328_;
        }
    }

    protected static class EmptyTextureStateShard
    extends RenderStateShard {
        public EmptyTextureStateShard(Runnable p_173117_, Runnable p_173118_) {
            super("texture", p_173117_, p_173118_);
        }

        EmptyTextureStateShard() {
            super("texture", () -> {}, () -> {});
        }

        protected Optional<ResourceLocation> m_142706_() {
            return Optional.empty();
        }
    }

    protected static class TexturingStateShard
    extends RenderStateShard {
        public TexturingStateShard(String p_110349_, Runnable p_110350_, Runnable p_110351_) {
            super(p_110349_, p_110350_, p_110351_);
        }
    }

    protected static class LightmapStateShard
    extends BooleanStateShard {
        public LightmapStateShard(boolean p_110271_) {
            super("lightmap", () -> {
                if (p_110271_) {
                    Minecraft.m_91087_().f_91063_.m_109154_().m_109896_();
                }
            }, () -> {
                if (p_110271_) {
                    Minecraft.m_91087_().f_91063_.m_109154_().m_109891_();
                }
            }, p_110271_);
        }
    }

    protected static class OverlayStateShard
    extends BooleanStateShard {
        public OverlayStateShard(boolean p_110304_) {
            super("overlay", () -> {
                if (p_110304_) {
                    Minecraft.m_91087_().f_91063_.m_109155_().m_118087_();
                }
            }, () -> {
                if (p_110304_) {
                    Minecraft.m_91087_().f_91063_.m_109155_().m_118098_();
                }
            }, p_110304_);
        }
    }

    protected static class CullStateShard
    extends BooleanStateShard {
        public CullStateShard(boolean p_110238_) {
            super("cull", () -> {
                if (!p_110238_) {
                    RenderSystem.m_69464_();
                }
            }, () -> {
                if (!p_110238_) {
                    RenderSystem.m_69481_();
                }
            }, p_110238_);
        }
    }

    protected static class DepthTestStateShard
    extends RenderStateShard {
        private final String f_110243_;

        public DepthTestStateShard(String p_110246_, int p_110247_) {
            super("depth_test", () -> {
                if (p_110247_ != 519) {
                    RenderSystem.m_69482_();
                    RenderSystem.m_69456_(p_110247_);
                }
            }, () -> {
                if (p_110247_ != 519) {
                    RenderSystem.m_69465_();
                    RenderSystem.m_69456_(515);
                }
            });
            this.f_110243_ = p_110246_;
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[" + this.f_110243_ + "]";
        }
    }

    protected static class WriteMaskStateShard
    extends RenderStateShard {
        private final boolean f_110356_;
        private final boolean f_110357_;

        public WriteMaskStateShard(boolean p_110359_, boolean p_110360_) {
            super("write_mask_state", () -> {
                if (!p_110360_) {
                    RenderSystem.m_69458_(p_110360_);
                }
                if (!p_110359_) {
                    RenderSystem.m_69444_(p_110359_, p_110359_, p_110359_, p_110359_);
                }
            }, () -> {
                if (!p_110360_) {
                    RenderSystem.m_69458_(true);
                }
                if (!p_110359_) {
                    RenderSystem.m_69444_(true, true, true, true);
                }
            });
            this.f_110356_ = p_110359_;
            this.f_110357_ = p_110360_;
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[writeColor=" + this.f_110356_ + ", writeDepth=" + this.f_110357_ + "]";
        }
    }

    protected static class LayeringStateShard
    extends RenderStateShard {
        public LayeringStateShard(String p_110267_, Runnable p_110268_, Runnable p_110269_) {
            super(p_110267_, p_110268_, p_110269_);
        }
    }

    protected static class OutputStateShard
    extends RenderStateShard {
        public OutputStateShard(String p_110300_, Runnable p_110301_, Runnable p_110302_) {
            super(p_110300_, p_110301_, p_110302_);
        }
    }

    protected static class LineStateShard
    extends RenderStateShard {
        private final OptionalDouble f_110276_;

        public LineStateShard(OptionalDouble p_110278_) {
            super("line_width", () -> {
                if (!Objects.equals(p_110278_, OptionalDouble.of(1.0))) {
                    if (p_110278_.isPresent()) {
                        RenderSystem.m_69832_((float)p_110278_.getAsDouble());
                    } else {
                        RenderSystem.m_69832_(Math.max(2.5f, (float)Minecraft.m_91087_().m_91268_().m_85441_() / 1920.0f * 2.5f));
                    }
                }
            }, () -> {
                if (!Objects.equals(p_110278_, OptionalDouble.of(1.0))) {
                    RenderSystem.m_69832_(1.0f);
                }
            });
            this.f_110276_ = p_110278_;
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[" + (Serializable)(this.f_110276_.isPresent() ? Double.valueOf(this.f_110276_.getAsDouble()) : "window_scale") + "]";
        }
    }

    static class BooleanStateShard
    extends RenderStateShard {
        private final boolean f_110227_;

        public BooleanStateShard(String p_110229_, Runnable p_110230_, Runnable p_110231_, boolean p_110232_) {
            super(p_110229_, p_110230_, p_110231_);
            this.f_110227_ = p_110232_;
        }

        @Override
        public String toString() {
            return this.f_110133_ + "[" + this.f_110227_ + "]";
        }
    }

    protected static final class OffsetTexturingStateShard
    extends TexturingStateShard {
        public OffsetTexturingStateShard(float p_110290_, float p_110291_) {
            super("offset_texturing", () -> RenderSystem.m_157459_(Matrix4f.m_27653_(p_110290_, p_110291_, 0.0f)), () -> RenderSystem.m_157423_());
        }
    }

    protected static class MultiTextureStateShard
    extends EmptyTextureStateShard {
        private final Optional<ResourceLocation> f_173121_;

        MultiTextureStateShard(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> p_173123_) {
            super(() -> {
                int $$1 = 0;
                for (Triple $$2 : p_173123_) {
                    TextureManager $$3 = Minecraft.m_91087_().m_91097_();
                    $$3.m_118506_((ResourceLocation)$$2.getLeft()).m_117960_((Boolean)$$2.getMiddle(), (Boolean)$$2.getRight());
                    RenderSystem.m_157456_($$1++, (ResourceLocation)$$2.getLeft());
                }
            }, () -> {});
            this.f_173121_ = p_173123_.stream().findFirst().map(Triple::getLeft);
        }

        @Override
        protected Optional<ResourceLocation> m_142706_() {
            return this.f_173121_;
        }

        public static Builder m_173127_() {
            return new Builder();
        }

        public static final class Builder {
            private final ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> f_173129_ = new ImmutableList.Builder();

            public Builder m_173132_(ResourceLocation p_173133_, boolean p_173134_, boolean p_173135_) {
                this.f_173129_.add((Object)Triple.of((Object)p_173133_, (Object)p_173134_, (Object)p_173135_));
                return this;
            }

            public MultiTextureStateShard m_173131_() {
                return new MultiTextureStateShard((ImmutableList<Triple<ResourceLocation, Boolean, Boolean>>)this.f_173129_.build());
            }
        }
    }
}

