/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockModelShaper {
    private final Map<BlockState, BakedModel> f_110877_ = Maps.newIdentityHashMap();
    private final ModelManager f_110878_;

    public BlockModelShaper(ModelManager p_110880_) {
        this.f_110878_ = p_110880_;
    }

    public TextureAtlasSprite m_110882_(BlockState p_110883_) {
        return this.m_110893_(p_110883_).m_6160_();
    }

    public BakedModel m_110893_(BlockState p_110894_) {
        BakedModel $$1 = this.f_110877_.get(p_110894_);
        if ($$1 == null) {
            $$1 = this.f_110878_.m_119409_();
        }
        return $$1;
    }

    public ModelManager m_110881_() {
        return this.f_110878_;
    }

    public void m_110892_() {
        this.f_110877_.clear();
        for (Block $$0 : Registry.f_122824_) {
            $$0.m_49965_().m_61056_().forEach(p_110898_ -> this.f_110877_.put((BlockState)p_110898_, this.f_110878_.m_119422_(BlockModelShaper.m_110895_(p_110898_))));
        }
    }

    public static ModelResourceLocation m_110895_(BlockState p_110896_) {
        return BlockModelShaper.m_110889_(Registry.f_122824_.m_7981_(p_110896_.m_60734_()), p_110896_);
    }

    public static ModelResourceLocation m_110889_(ResourceLocation p_110890_, BlockState p_110891_) {
        return new ModelResourceLocation(p_110890_, BlockModelShaper.m_110887_(p_110891_.m_61148_()));
    }

    public static String m_110887_(Map<Property<?>, Comparable<?>> p_110888_) {
        StringBuilder $$1 = new StringBuilder();
        for (Map.Entry<Property<?>, Comparable<?>> $$2 : p_110888_.entrySet()) {
            if ($$1.length() != 0) {
                $$1.append(',');
            }
            Property<?> $$3 = $$2.getKey();
            $$1.append($$3.m_61708_());
            $$1.append('=');
            $$1.append(BlockModelShaper.m_110884_($$3, $$2.getValue()));
        }
        return $$1.toString();
    }

    private static <T extends Comparable<T>> String m_110884_(Property<T> p_110885_, Comparable<?> p_110886_) {
        return p_110885_.m_6940_(p_110886_);
    }
}

