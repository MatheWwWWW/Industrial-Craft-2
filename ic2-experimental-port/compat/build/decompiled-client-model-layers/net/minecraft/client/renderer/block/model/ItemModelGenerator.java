/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;

public class ItemModelGenerator {
    public static final List<String> f_111635_ = Lists.newArrayList((Object[])new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});
    private static final float f_173437_ = 7.5f;
    private static final float f_173438_ = 8.5f;

    public BlockModel m_111670_(Function<Material, TextureAtlasSprite> p_111671_, BlockModel p_111672_) {
        String $$5;
        HashMap $$2 = Maps.newHashMap();
        ArrayList $$3 = Lists.newArrayList();
        for (int $$4 = 0; $$4 < f_111635_.size() && p_111672_.m_111477_($$5 = f_111635_.get($$4)); ++$$4) {
            Material $$6 = p_111672_.m_111480_($$5);
            $$2.put($$5, Either.left((Object)$$6));
            TextureAtlasSprite $$7 = p_111671_.apply($$6);
            $$3.addAll(this.m_111638_($$4, $$5, $$7));
        }
        $$2.put("particle", p_111672_.m_111477_("particle") ? Either.left((Object)p_111672_.m_111480_("particle")) : (Either)$$2.get("layer0"));
        BlockModel $$8 = new BlockModel(null, $$3, $$2, false, p_111672_.m_111479_(), p_111672_.m_111491_(), p_111672_.m_111484_());
        $$8.f_111416_ = p_111672_.f_111416_;
        return $$8;
    }

    private List<BlockElement> m_111638_(int p_111639_, String p_111640_, TextureAtlasSprite p_111641_) {
        HashMap $$3 = Maps.newHashMap();
        $$3.put(Direction.SOUTH, new BlockElementFace(null, p_111639_, p_111640_, new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)));
        $$3.put(Direction.NORTH, new BlockElementFace(null, p_111639_, p_111640_, new BlockFaceUV(new float[]{16.0f, 0.0f, 0.0f, 16.0f}, 0)));
        ArrayList $$4 = Lists.newArrayList();
        $$4.add(new BlockElement(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), $$3, null, true));
        $$4.addAll(this.m_111661_(p_111641_, p_111640_, p_111639_));
        return $$4;
    }

    private List<BlockElement> m_111661_(TextureAtlasSprite p_111662_, String p_111663_, int p_111664_) {
        float $$3 = p_111662_.m_118405_();
        float $$4 = p_111662_.m_118408_();
        ArrayList $$5 = Lists.newArrayList();
        for (Span $$6 : this.m_111652_(p_111662_)) {
            float $$7 = 0.0f;
            float $$8 = 0.0f;
            float $$9 = 0.0f;
            float $$10 = 0.0f;
            float $$11 = 0.0f;
            float $$12 = 0.0f;
            float $$13 = 0.0f;
            float $$14 = 0.0f;
            float $$15 = 16.0f / $$3;
            float $$16 = 16.0f / $$4;
            float $$17 = $$6.m_111686_();
            float $$18 = $$6.m_111687_();
            float $$19 = $$6.m_111688_();
            SpanFacing $$20 = $$6.m_111683_();
            switch ($$20) {
                case UP: {
                    $$7 = $$11 = $$17;
                    $$9 = $$12 = $$18 + 1.0f;
                    $$8 = $$13 = $$19;
                    $$10 = $$19;
                    $$14 = $$19 + 1.0f;
                    break;
                }
                case DOWN: {
                    $$13 = $$19;
                    $$14 = $$19 + 1.0f;
                    $$7 = $$11 = $$17;
                    $$9 = $$12 = $$18 + 1.0f;
                    $$8 = $$19 + 1.0f;
                    $$10 = $$19 + 1.0f;
                    break;
                }
                case LEFT: {
                    $$7 = $$11 = $$19;
                    $$9 = $$19;
                    $$12 = $$19 + 1.0f;
                    $$8 = $$14 = $$17;
                    $$10 = $$13 = $$18 + 1.0f;
                    break;
                }
                case RIGHT: {
                    $$11 = $$19;
                    $$12 = $$19 + 1.0f;
                    $$7 = $$19 + 1.0f;
                    $$9 = $$19 + 1.0f;
                    $$8 = $$14 = $$17;
                    $$10 = $$13 = $$18 + 1.0f;
                }
            }
            $$7 *= $$15;
            $$9 *= $$15;
            $$8 *= $$16;
            $$10 *= $$16;
            $$8 = 16.0f - $$8;
            $$10 = 16.0f - $$10;
            HashMap $$21 = Maps.newHashMap();
            $$21.put($$20.m_111704_(), new BlockElementFace(null, p_111664_, p_111663_, new BlockFaceUV(new float[]{$$11 *= $$15, $$13 *= $$16, $$12 *= $$15, $$14 *= $$16}, 0)));
            switch ($$20) {
                case UP: {
                    $$5.add(new BlockElement(new Vector3f($$7, $$8, 7.5f), new Vector3f($$9, $$8, 8.5f), $$21, null, true));
                    break;
                }
                case DOWN: {
                    $$5.add(new BlockElement(new Vector3f($$7, $$10, 7.5f), new Vector3f($$9, $$10, 8.5f), $$21, null, true));
                    break;
                }
                case LEFT: {
                    $$5.add(new BlockElement(new Vector3f($$7, $$8, 7.5f), new Vector3f($$7, $$10, 8.5f), $$21, null, true));
                    break;
                }
                case RIGHT: {
                    $$5.add(new BlockElement(new Vector3f($$9, $$8, 7.5f), new Vector3f($$9, $$10, 8.5f), $$21, null, true));
                }
            }
        }
        return $$5;
    }

    private List<Span> m_111652_(TextureAtlasSprite p_111653_) {
        int $$1 = p_111653_.m_118405_();
        int $$2 = p_111653_.m_118408_();
        ArrayList $$3 = Lists.newArrayList();
        p_111653_.m_174745_().forEach(p_173444_ -> {
            for (int $$5 = 0; $$5 < $$2; ++$$5) {
                for (int $$6 = 0; $$6 < $$1; ++$$6) {
                    boolean $$7 = !this.m_111654_(p_111653_, p_173444_, $$6, $$5, $$1, $$2);
                    this.m_111642_(SpanFacing.UP, $$3, p_111653_, p_173444_, $$6, $$5, $$1, $$2, $$7);
                    this.m_111642_(SpanFacing.DOWN, $$3, p_111653_, p_173444_, $$6, $$5, $$1, $$2, $$7);
                    this.m_111642_(SpanFacing.LEFT, $$3, p_111653_, p_173444_, $$6, $$5, $$1, $$2, $$7);
                    this.m_111642_(SpanFacing.RIGHT, $$3, p_111653_, p_173444_, $$6, $$5, $$1, $$2, $$7);
                }
            }
        });
        return $$3;
    }

    private void m_111642_(SpanFacing p_111643_, List<Span> p_111644_, TextureAtlasSprite p_111645_, int p_111646_, int p_111647_, int p_111648_, int p_111649_, int p_111650_, boolean p_111651_) {
        boolean $$9;
        boolean bl = $$9 = this.m_111654_(p_111645_, p_111646_, p_111647_ + p_111643_.m_111707_(), p_111648_ + p_111643_.m_111708_(), p_111649_, p_111650_) && p_111651_;
        if ($$9) {
            this.m_111665_(p_111644_, p_111643_, p_111647_, p_111648_);
        }
    }

    private void m_111665_(List<Span> p_111666_, SpanFacing p_111667_, int p_111668_, int p_111669_) {
        int $$8;
        Span $$4 = null;
        for (Span $$5 : p_111666_) {
            int $$6;
            if ($$5.m_111683_() != p_111667_) continue;
            int n = $$6 = p_111667_.m_111709_() ? p_111669_ : p_111668_;
            if ($$5.m_111688_() != $$6) continue;
            $$4 = $$5;
            break;
        }
        int $$7 = p_111667_.m_111709_() ? p_111669_ : p_111668_;
        int n = $$8 = p_111667_.m_111709_() ? p_111668_ : p_111669_;
        if ($$4 == null) {
            p_111666_.add(new Span(p_111667_, $$8, $$7));
        } else {
            $$4.m_111684_($$8);
        }
    }

    private boolean m_111654_(TextureAtlasSprite p_111655_, int p_111656_, int p_111657_, int p_111658_, int p_111659_, int p_111660_) {
        if (p_111657_ < 0 || p_111658_ < 0 || p_111657_ >= p_111659_ || p_111658_ >= p_111660_) {
            return true;
        }
        return p_111655_.m_118371_(p_111656_, p_111657_, p_111658_);
    }

    static class Span {
        private final SpanFacing f_111675_;
        private int f_111676_;
        private int f_111677_;
        private final int f_111678_;

        public Span(SpanFacing p_111680_, int p_111681_, int p_111682_) {
            this.f_111675_ = p_111680_;
            this.f_111676_ = p_111681_;
            this.f_111677_ = p_111681_;
            this.f_111678_ = p_111682_;
        }

        public void m_111684_(int p_111685_) {
            if (p_111685_ < this.f_111676_) {
                this.f_111676_ = p_111685_;
            } else if (p_111685_ > this.f_111677_) {
                this.f_111677_ = p_111685_;
            }
        }

        public SpanFacing m_111683_() {
            return this.f_111675_;
        }

        public int m_111686_() {
            return this.f_111676_;
        }

        public int m_111687_() {
            return this.f_111677_;
        }

        public int m_111688_() {
            return this.f_111678_;
        }
    }

    static final class SpanFacing
    extends Enum<SpanFacing> {
        public static final /* enum */ SpanFacing UP = new SpanFacing(Direction.UP, 0, -1);
        public static final /* enum */ SpanFacing DOWN = new SpanFacing(Direction.DOWN, 0, 1);
        public static final /* enum */ SpanFacing LEFT = new SpanFacing(Direction.EAST, -1, 0);
        public static final /* enum */ SpanFacing RIGHT = new SpanFacing(Direction.WEST, 1, 0);
        private final Direction f_111693_;
        private final int f_111694_;
        private final int f_111695_;
        private static final /* synthetic */ SpanFacing[] $VALUES;

        public static SpanFacing[] values() {
            return (SpanFacing[])$VALUES.clone();
        }

        public static SpanFacing valueOf(String p_111711_) {
            return Enum.valueOf(SpanFacing.class, p_111711_);
        }

        private SpanFacing(Direction p_111701_, int p_111702_, int p_111703_) {
            this.f_111693_ = p_111701_;
            this.f_111694_ = p_111702_;
            this.f_111695_ = p_111703_;
        }

        public Direction m_111704_() {
            return this.f_111693_;
        }

        public int m_111707_() {
            return this.f_111694_;
        }

        public int m_111708_() {
            return this.f_111695_;
        }

        boolean m_111709_() {
            return this == DOWN || this == UP;
        }

        private static /* synthetic */ SpanFacing[] m_173445_() {
            return new SpanFacing[]{UP, DOWN, LEFT, RIGHT};
        }

        static {
            $VALUES = SpanFacing.m_173445_();
        }
    }
}

