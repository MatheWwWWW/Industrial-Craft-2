/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.tuple.Pair
 */
package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class MultiPartBakedModel
implements BakedModel {
    private final List<Pair<Predicate<BlockState>, BakedModel>> f_119459_;
    protected final boolean f_119453_;
    protected final boolean f_119454_;
    protected final boolean f_119455_;
    protected final TextureAtlasSprite f_119456_;
    protected final ItemTransforms f_119457_;
    protected final ItemOverrides f_119458_;
    private final Map<BlockState, BitSet> f_119460_ = new Object2ObjectOpenCustomHashMap(Util.m_137583_());

    public MultiPartBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> p_119462_) {
        this.f_119459_ = p_119462_;
        BakedModel $$1 = (BakedModel)p_119462_.iterator().next().getRight();
        this.f_119453_ = $$1.m_7541_();
        this.f_119454_ = $$1.m_7539_();
        this.f_119455_ = $$1.m_7547_();
        this.f_119456_ = $$1.m_6160_();
        this.f_119457_ = $$1.m_7442_();
        this.f_119458_ = $$1.m_7343_();
    }

    @Override
    public List<BakedQuad> m_213637_(@Nullable BlockState p_235050_, @Nullable Direction p_235051_, RandomSource p_235052_) {
        if (p_235050_ == null) {
            return Collections.emptyList();
        }
        BitSet $$3 = this.f_119460_.get(p_235050_);
        if ($$3 == null) {
            $$3 = new BitSet();
            for (int $$4 = 0; $$4 < this.f_119459_.size(); ++$$4) {
                Pair<Predicate<BlockState>, BakedModel> $$5 = this.f_119459_.get($$4);
                if (!((Predicate)$$5.getLeft()).test(p_235050_)) continue;
                $$3.set($$4);
            }
            this.f_119460_.put(p_235050_, $$3);
        }
        ArrayList $$6 = Lists.newArrayList();
        long $$7 = p_235052_.m_188505_();
        for (int $$8 = 0; $$8 < $$3.length(); ++$$8) {
            if (!$$3.get($$8)) continue;
            $$6.addAll(((BakedModel)this.f_119459_.get($$8).getRight()).m_213637_(p_235050_, p_235051_, RandomSource.m_216335_($$7)));
        }
        return $$6;
    }

    @Override
    public boolean m_7541_() {
        return this.f_119453_;
    }

    @Override
    public boolean m_7539_() {
        return this.f_119454_;
    }

    @Override
    public boolean m_7547_() {
        return this.f_119455_;
    }

    @Override
    public boolean m_7521_() {
        return false;
    }

    @Override
    public TextureAtlasSprite m_6160_() {
        return this.f_119456_;
    }

    @Override
    public ItemTransforms m_7442_() {
        return this.f_119457_;
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.f_119458_;
    }

    public static class Builder {
        private final List<Pair<Predicate<BlockState>, BakedModel>> f_119474_ = Lists.newArrayList();

        public void m_119477_(Predicate<BlockState> p_119478_, BakedModel p_119479_) {
            this.f_119474_.add((Pair<Predicate<BlockState>, BakedModel>)Pair.of(p_119478_, (Object)p_119479_));
        }

        public BakedModel m_119476_() {
            return new MultiPartBakedModel(this.f_119474_);
        }
    }
}

