/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBakedModel
implements BakedModel {
    protected final List<BakedQuad> f_119480_;
    protected final Map<Direction, List<BakedQuad>> f_119481_;
    protected final boolean f_119482_;
    protected final boolean f_119483_;
    protected final boolean f_119484_;
    protected final TextureAtlasSprite f_119485_;
    protected final ItemTransforms f_119486_;
    protected final ItemOverrides f_119487_;

    public SimpleBakedModel(List<BakedQuad> p_119489_, Map<Direction, List<BakedQuad>> p_119490_, boolean p_119491_, boolean p_119492_, boolean p_119493_, TextureAtlasSprite p_119494_, ItemTransforms p_119495_, ItemOverrides p_119496_) {
        this.f_119480_ = p_119489_;
        this.f_119481_ = p_119490_;
        this.f_119482_ = p_119491_;
        this.f_119483_ = p_119493_;
        this.f_119484_ = p_119492_;
        this.f_119485_ = p_119494_;
        this.f_119486_ = p_119495_;
        this.f_119487_ = p_119496_;
    }

    @Override
    public List<BakedQuad> m_213637_(@Nullable BlockState p_235054_, @Nullable Direction p_235055_, RandomSource p_235056_) {
        return p_235055_ == null ? this.f_119480_ : this.f_119481_.get(p_235055_);
    }

    @Override
    public boolean m_7541_() {
        return this.f_119482_;
    }

    @Override
    public boolean m_7539_() {
        return this.f_119483_;
    }

    @Override
    public boolean m_7547_() {
        return this.f_119484_;
    }

    @Override
    public boolean m_7521_() {
        return false;
    }

    @Override
    public TextureAtlasSprite m_6160_() {
        return this.f_119485_;
    }

    @Override
    public ItemTransforms m_7442_() {
        return this.f_119486_;
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.f_119487_;
    }

    public static class Builder {
        private final List<BakedQuad> f_119508_ = Lists.newArrayList();
        private final Map<Direction, List<BakedQuad>> f_119509_ = Maps.newEnumMap(Direction.class);
        private final ItemOverrides f_119510_;
        private final boolean f_119511_;
        private TextureAtlasSprite f_119512_;
        private final boolean f_119513_;
        private final boolean f_119514_;
        private final ItemTransforms f_119515_;

        public Builder(BlockModel p_119517_, ItemOverrides p_119518_, boolean p_119519_) {
            this(p_119517_.m_111476_(), p_119517_.m_111479_().m_111526_(), p_119519_, p_119517_.m_111491_(), p_119518_);
        }

        private Builder(boolean p_119521_, boolean p_119522_, boolean p_119523_, ItemTransforms p_119524_, ItemOverrides p_119525_) {
            for (Direction $$5 : Direction.values()) {
                this.f_119509_.put($$5, Lists.newArrayList());
            }
            this.f_119510_ = p_119525_;
            this.f_119511_ = p_119521_;
            this.f_119513_ = p_119522_;
            this.f_119514_ = p_119523_;
            this.f_119515_ = p_119524_;
        }

        public Builder m_119530_(Direction p_119531_, BakedQuad p_119532_) {
            this.f_119509_.get(p_119531_).add(p_119532_);
            return this;
        }

        public Builder m_119526_(BakedQuad p_119527_) {
            this.f_119508_.add(p_119527_);
            return this;
        }

        public Builder m_119528_(TextureAtlasSprite p_119529_) {
            this.f_119512_ = p_119529_;
            return this;
        }

        public Builder m_174911_() {
            return this;
        }

        public BakedModel m_119533_() {
            if (this.f_119512_ == null) {
                throw new RuntimeException("Missing particle!");
            }
            return new SimpleBakedModel(this.f_119508_, this.f_119509_, this.f_119511_, this.f_119513_, this.f_119514_, this.f_119512_, this.f_119515_, this.f_119510_);
        }
    }
}

