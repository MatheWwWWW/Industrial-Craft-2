/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.renderer.SpriteCoordinateExpander;
import net.minecraft.client.renderer.texture.MipmapGenerator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class TextureAtlasSprite
implements AutoCloseable {
    private static final Logger f_174721_ = LogUtils.getLogger();
    private final TextureAtlas f_118343_;
    private final ResourceLocation f_174722_;
    final int f_174723_;
    final int f_174724_;
    protected final NativeImage[] f_118342_;
    @Nullable
    private final AnimatedTexture f_174725_;
    private final int f_118349_;
    private final int f_118350_;
    private final float f_118351_;
    private final float f_118352_;
    private final float f_118353_;
    private final float f_118354_;

    protected TextureAtlasSprite(TextureAtlas p_118358_, Info p_118359_, int p_118360_, int p_118361_, int p_118362_, int p_118363_, int p_118364_, NativeImage p_118365_) {
        this.f_118343_ = p_118358_;
        this.f_174723_ = p_118359_.f_118423_;
        this.f_174724_ = p_118359_.f_118424_;
        this.f_174722_ = p_118359_.f_118422_;
        this.f_118349_ = p_118363_;
        this.f_118350_ = p_118364_;
        this.f_118351_ = (float)p_118363_ / (float)p_118361_;
        this.f_118352_ = (float)(p_118363_ + this.f_174723_) / (float)p_118361_;
        this.f_118353_ = (float)p_118364_ / (float)p_118362_;
        this.f_118354_ = (float)(p_118364_ + this.f_174724_) / (float)p_118362_;
        this.f_174725_ = this.m_174729_(p_118359_, p_118365_.m_84982_(), p_118365_.m_85084_(), p_118360_);
        try {
            try {
                this.f_118342_ = MipmapGenerator.m_118054_(p_118365_, p_118360_);
            }
            catch (Throwable $$8) {
                CrashReport $$9 = CrashReport.m_127521_($$8, "Generating mipmaps for frame");
                CrashReportCategory $$10 = $$9.m_127514_("Frame being iterated");
                $$10.m_128165_("First frame", () -> {
                    StringBuilder $$1 = new StringBuilder();
                    if ($$1.length() > 0) {
                        $$1.append(", ");
                    }
                    $$1.append(p_118365_.m_84982_()).append("x").append(p_118365_.m_85084_());
                    return $$1.toString();
                });
                throw new ReportedException($$9);
            }
        }
        catch (Throwable $$11) {
            CrashReport $$12 = CrashReport.m_127521_($$11, "Applying mipmap");
            CrashReportCategory $$13 = $$12.m_127514_("Sprite being mipmapped");
            $$13.m_128165_("Sprite name", this.f_174722_::toString);
            $$13.m_128165_("Sprite size", () -> this.f_174723_ + " x " + this.f_174724_);
            $$13.m_128165_("Sprite frames", () -> this.m_118415_() + " frames");
            $$13.m_128159_("Mipmap levels", p_118360_);
            throw new ReportedException($$12);
        }
    }

    private int m_118415_() {
        return this.f_174725_ != null ? this.f_174725_.f_174750_.size() : 1;
    }

    @Nullable
    private AnimatedTexture m_174729_(Info p_174730_, int p_174731_, int p_174732_, int p_174733_) {
        AnimationMetadataSection $$4 = p_174730_.f_118425_;
        int $$5 = p_174731_ / $$4.m_119031_(p_174730_.f_118423_);
        int $$6 = p_174732_ / $$4.m_119026_(p_174730_.f_118424_);
        int $$7 = $$5 * $$6;
        ArrayList $$8 = Lists.newArrayList();
        $$4.m_174861_((p_174739_, p_174740_) -> $$8.add(new FrameInfo(p_174739_, p_174740_)));
        if ($$8.isEmpty()) {
            for (int $$9 = 0; $$9 < $$7; ++$$9) {
                $$8.add(new FrameInfo($$9, $$4.m_119030_()));
            }
        } else {
            int $$10 = 0;
            IntOpenHashSet $$11 = new IntOpenHashSet();
            Iterator $$12 = $$8.iterator();
            while ($$12.hasNext()) {
                FrameInfo $$13 = (FrameInfo)$$12.next();
                boolean $$14 = true;
                if ($$13.f_174772_ <= 0) {
                    f_174721_.warn("Invalid frame duration on sprite {} frame {}: {}", new Object[]{this.f_174722_, $$10, $$13.f_174772_});
                    $$14 = false;
                }
                if ($$13.f_174771_ < 0 || $$13.f_174771_ >= $$7) {
                    f_174721_.warn("Invalid frame index on sprite {} frame {}: {}", new Object[]{this.f_174722_, $$10, $$13.f_174771_});
                    $$14 = false;
                }
                if ($$14) {
                    $$11.add($$13.f_174771_);
                } else {
                    $$12.remove();
                }
                ++$$10;
            }
            int[] $$15 = IntStream.range(0, $$7).filter(arg_0 -> TextureAtlasSprite.m_174734_((IntSet)$$11, arg_0)).toArray();
            if ($$15.length > 0) {
                f_174721_.warn("Unused frames in sprite {}: {}", (Object)this.f_174722_, (Object)Arrays.toString($$15));
            }
        }
        if ($$8.size() <= 1) {
            return null;
        }
        InterpolationData $$16 = $$4.m_119036_() ? new InterpolationData(p_174730_, p_174733_) : null;
        return new AnimatedTexture((List<FrameInfo>)ImmutableList.copyOf((Collection)$$8), $$5, $$16);
    }

    void m_118375_(int p_118376_, int p_118377_, NativeImage[] p_118378_) {
        for (int $$3 = 0; $$3 < this.f_118342_.length; ++$$3) {
            p_118378_[$$3].m_85003_($$3, this.f_118349_ >> $$3, this.f_118350_ >> $$3, p_118376_ >> $$3, p_118377_ >> $$3, this.f_174723_ >> $$3, this.f_174724_ >> $$3, this.f_118342_.length > 1, false);
        }
    }

    public int m_174743_() {
        return this.f_118349_;
    }

    public int m_174744_() {
        return this.f_118350_;
    }

    public int m_118405_() {
        return this.f_174723_;
    }

    public int m_118408_() {
        return this.f_174724_;
    }

    public float m_118409_() {
        return this.f_118351_;
    }

    public float m_118410_() {
        return this.f_118352_;
    }

    public float m_118367_(double p_118368_) {
        float $$1 = this.f_118352_ - this.f_118351_;
        return this.f_118351_ + $$1 * (float)p_118368_ / 16.0f;
    }

    public float m_174727_(float p_174728_) {
        float $$1 = this.f_118352_ - this.f_118351_;
        return (p_174728_ - this.f_118351_) / $$1 * 16.0f;
    }

    public float m_118411_() {
        return this.f_118353_;
    }

    public float m_118412_() {
        return this.f_118354_;
    }

    public float m_118393_(double p_118394_) {
        float $$1 = this.f_118354_ - this.f_118353_;
        return this.f_118353_ + $$1 * (float)p_118394_ / 16.0f;
    }

    public float m_174741_(float p_174742_) {
        float $$1 = this.f_118354_ - this.f_118353_;
        return (p_174742_ - this.f_118353_) / $$1 * 16.0f;
    }

    public ResourceLocation m_118413_() {
        return this.f_174722_;
    }

    public TextureAtlas m_118414_() {
        return this.f_118343_;
    }

    public IntStream m_174745_() {
        return this.f_174725_ != null ? this.f_174725_.m_174763_() : IntStream.of(1);
    }

    @Override
    public void close() {
        for (NativeImage $$0 : this.f_118342_) {
            if ($$0 == null) continue;
            $$0.close();
        }
        if (this.f_174725_ != null) {
            this.f_174725_.close();
        }
    }

    public String toString() {
        return "TextureAtlasSprite{name='" + this.f_174722_ + "', frameCount=" + this.m_118415_() + ", x=" + this.f_118349_ + ", y=" + this.f_118350_ + ", height=" + this.f_174724_ + ", width=" + this.f_174723_ + ", u0=" + this.f_118351_ + ", u1=" + this.f_118352_ + ", v0=" + this.f_118353_ + ", v1=" + this.f_118354_ + "}";
    }

    public boolean m_118371_(int p_118372_, int p_118373_, int p_118374_) {
        int $$3 = p_118373_;
        int $$4 = p_118374_;
        if (this.f_174725_ != null) {
            $$3 += this.f_174725_.m_174759_(p_118372_) * this.f_174723_;
            $$4 += this.f_174725_.m_174764_(p_118372_) * this.f_174724_;
        }
        return (this.f_118342_[0].m_84985_($$3, $$4) >> 24 & 0xFF) == 0;
    }

    public void m_118416_() {
        if (this.f_174725_ != null) {
            this.f_174725_.m_174758_();
        } else {
            this.m_118375_(0, 0, this.f_118342_);
        }
    }

    private float m_118366_() {
        float $$0 = (float)this.f_174723_ / (this.f_118352_ - this.f_118351_);
        float $$1 = (float)this.f_174724_ / (this.f_118354_ - this.f_118353_);
        return Math.max($$1, $$0);
    }

    public float m_118417_() {
        return 4.0f / this.m_118366_();
    }

    @Nullable
    public Tickable m_174746_() {
        return this.f_174725_;
    }

    public VertexConsumer m_118381_(VertexConsumer p_118382_) {
        return new SpriteCoordinateExpander(p_118382_, this);
    }

    private static /* synthetic */ boolean m_174734_(IntSet p_174735_, int p_174736_) {
        return !p_174735_.contains(p_174736_);
    }

    public static final class Info {
        final ResourceLocation f_118422_;
        final int f_118423_;
        final int f_118424_;
        final AnimationMetadataSection f_118425_;

        public Info(ResourceLocation p_118427_, int p_118428_, int p_118429_, AnimationMetadataSection p_118430_) {
            this.f_118422_ = p_118427_;
            this.f_118423_ = p_118428_;
            this.f_118424_ = p_118429_;
            this.f_118425_ = p_118430_;
        }

        public ResourceLocation m_118431_() {
            return this.f_118422_;
        }

        public int m_118434_() {
            return this.f_118423_;
        }

        public int m_118437_() {
            return this.f_118424_;
        }
    }

    class AnimatedTexture
    implements Tickable,
    AutoCloseable {
        int f_174748_;
        int f_174749_;
        final List<FrameInfo> f_174750_;
        private final int f_174751_;
        @Nullable
        private final InterpolationData f_174752_;

        AnimatedTexture(@Nullable List<FrameInfo> p_174755_, int p_174756_, InterpolationData p_174757_) {
            this.f_174750_ = p_174755_;
            this.f_174751_ = p_174756_;
            this.f_174752_ = p_174757_;
        }

        int m_174759_(int p_174760_) {
            return p_174760_ % this.f_174751_;
        }

        int m_174764_(int p_174765_) {
            return p_174765_ / this.f_174751_;
        }

        private void m_174767_(int p_174768_) {
            int $$1 = this.m_174759_(p_174768_) * TextureAtlasSprite.this.f_174723_;
            int $$2 = this.m_174764_(p_174768_) * TextureAtlasSprite.this.f_174724_;
            TextureAtlasSprite.this.m_118375_($$1, $$2, TextureAtlasSprite.this.f_118342_);
        }

        @Override
        public void close() {
            if (this.f_174752_ != null) {
                this.f_174752_.close();
            }
        }

        @Override
        public void m_7673_() {
            ++this.f_174749_;
            FrameInfo $$0 = this.f_174750_.get(this.f_174748_);
            if (this.f_174749_ >= $$0.f_174772_) {
                int $$1 = $$0.f_174771_;
                this.f_174748_ = (this.f_174748_ + 1) % this.f_174750_.size();
                this.f_174749_ = 0;
                int $$2 = this.f_174750_.get((int)this.f_174748_).f_174771_;
                if ($$1 != $$2) {
                    this.m_174767_($$2);
                }
            } else if (this.f_174752_ != null) {
                if (!RenderSystem.m_69586_()) {
                    RenderSystem.m_69879_(() -> this.f_174752_.m_174776_(this));
                } else {
                    this.f_174752_.m_174776_(this);
                }
            }
        }

        public void m_174758_() {
            this.m_174767_(this.f_174750_.get((int)0).f_174771_);
        }

        public IntStream m_174763_() {
            return this.f_174750_.stream().mapToInt(p_174762_ -> p_174762_.f_174771_).distinct();
        }
    }

    static class FrameInfo {
        final int f_174771_;
        final int f_174772_;

        FrameInfo(int p_174774_, int p_174775_) {
            this.f_174771_ = p_174774_;
            this.f_174772_ = p_174775_;
        }
    }

    final class InterpolationData
    implements AutoCloseable {
        private final NativeImage[] f_118443_;

        InterpolationData(Info p_118446_, int p_118447_) {
            this.f_118443_ = new NativeImage[p_118447_ + 1];
            for (int $$2 = 0; $$2 < this.f_118443_.length; ++$$2) {
                int $$3 = p_118446_.f_118423_ >> $$2;
                int $$4 = p_118446_.f_118424_ >> $$2;
                if (this.f_118443_[$$2] != null) continue;
                this.f_118443_[$$2] = new NativeImage($$3, $$4, false);
            }
        }

        void m_174776_(AnimatedTexture p_174777_) {
            FrameInfo $$1 = p_174777_.f_174750_.get(p_174777_.f_174748_);
            double $$2 = 1.0 - (double)p_174777_.f_174749_ / (double)$$1.f_174772_;
            int $$3 = $$1.f_174771_;
            int $$4 = p_174777_.f_174750_.get((int)((p_174777_.f_174748_ + 1) % p_174777_.f_174750_.size())).f_174771_;
            if ($$3 != $$4) {
                for (int $$5 = 0; $$5 < this.f_118443_.length; ++$$5) {
                    int $$6 = TextureAtlasSprite.this.f_174723_ >> $$5;
                    int $$7 = TextureAtlasSprite.this.f_174724_ >> $$5;
                    for (int $$8 = 0; $$8 < $$7; ++$$8) {
                        for (int $$9 = 0; $$9 < $$6; ++$$9) {
                            int $$10 = this.m_174778_(p_174777_, $$3, $$5, $$9, $$8);
                            int $$11 = this.m_174778_(p_174777_, $$4, $$5, $$9, $$8);
                            int $$12 = this.m_118454_($$2, $$10 >> 16 & 0xFF, $$11 >> 16 & 0xFF);
                            int $$13 = this.m_118454_($$2, $$10 >> 8 & 0xFF, $$11 >> 8 & 0xFF);
                            int $$14 = this.m_118454_($$2, $$10 & 0xFF, $$11 & 0xFF);
                            this.f_118443_[$$5].m_84988_($$9, $$8, $$10 & 0xFF000000 | $$12 << 16 | $$13 << 8 | $$14);
                        }
                    }
                }
                TextureAtlasSprite.this.m_118375_(0, 0, this.f_118443_);
            }
        }

        private int m_174778_(AnimatedTexture p_174779_, int p_174780_, int p_174781_, int p_174782_, int p_174783_) {
            return TextureAtlasSprite.this.f_118342_[p_174781_].m_84985_(p_174782_ + (p_174779_.m_174759_(p_174780_) * TextureAtlasSprite.this.f_174723_ >> p_174781_), p_174783_ + (p_174779_.m_174764_(p_174780_) * TextureAtlasSprite.this.f_174724_ >> p_174781_));
        }

        private int m_118454_(double p_118455_, int p_118456_, int p_118457_) {
            return (int)(p_118455_ * (double)p_118456_ + (1.0 - p_118455_) * (double)p_118457_);
        }

        @Override
        public void close() {
            for (NativeImage $$0 : this.f_118443_) {
                if ($$0 == null) continue;
                $$0.close();
            }
        }
    }
}

