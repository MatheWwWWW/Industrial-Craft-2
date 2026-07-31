/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.level.block.state.BlockState;

public class WeightedBakedModel
implements BakedModel {
    private final int f_119540_;
    private final List<WeightedEntry.Wrapper<BakedModel>> f_119541_;
    private final BakedModel f_119542_;

    public WeightedBakedModel(List<WeightedEntry.Wrapper<BakedModel>> p_119544_) {
        this.f_119541_ = p_119544_;
        this.f_119540_ = WeightedRandom.m_146312_(p_119544_);
        this.f_119542_ = p_119544_.get(0).m_146310_();
    }

    @Override
    public List<BakedQuad> m_213637_(@Nullable BlockState p_235058_, @Nullable Direction p_235059_, RandomSource p_235060_) {
        return WeightedRandom.m_146314_(this.f_119541_, Math.abs((int)p_235060_.m_188505_()) % this.f_119540_).map(p_235065_ -> ((BakedModel)p_235065_.m_146310_()).m_213637_(p_235058_, p_235059_, p_235060_)).orElse(Collections.emptyList());
    }

    @Override
    public boolean m_7541_() {
        return this.f_119542_.m_7541_();
    }

    @Override
    public boolean m_7539_() {
        return this.f_119542_.m_7539_();
    }

    @Override
    public boolean m_7547_() {
        return this.f_119542_.m_7547_();
    }

    @Override
    public boolean m_7521_() {
        return this.f_119542_.m_7521_();
    }

    @Override
    public TextureAtlasSprite m_6160_() {
        return this.f_119542_.m_6160_();
    }

    @Override
    public ItemTransforms m_7442_() {
        return this.f_119542_.m_7442_();
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.f_119542_.m_7343_();
    }

    public static class Builder {
        private final List<WeightedEntry.Wrapper<BakedModel>> f_119556_ = Lists.newArrayList();

        public Builder m_119559_(@Nullable BakedModel p_119560_, int p_119561_) {
            if (p_119560_ != null) {
                this.f_119556_.add(WeightedEntry.m_146290_(p_119560_, p_119561_));
            }
            return this;
        }

        @Nullable
        public BakedModel m_119558_() {
            if (this.f_119556_.isEmpty()) {
                return null;
            }
            if (this.f_119556_.size() == 1) {
                return this.f_119556_.get(0).m_146310_();
            }
            return new WeightedBakedModel(this.f_119556_);
        }
    }
}

