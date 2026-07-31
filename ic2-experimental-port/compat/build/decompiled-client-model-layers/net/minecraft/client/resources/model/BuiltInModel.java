/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.model;

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
import net.minecraft.world.level.block.state.BlockState;

public class BuiltInModel
implements BakedModel {
    private final ItemTransforms f_119167_;
    private final ItemOverrides f_119168_;
    private final TextureAtlasSprite f_119169_;
    private final boolean f_119170_;

    public BuiltInModel(ItemTransforms p_119172_, ItemOverrides p_119173_, TextureAtlasSprite p_119174_, boolean p_119175_) {
        this.f_119167_ = p_119172_;
        this.f_119168_ = p_119173_;
        this.f_119169_ = p_119174_;
        this.f_119170_ = p_119175_;
    }

    @Override
    public List<BakedQuad> m_213637_(@Nullable BlockState p_235043_, @Nullable Direction p_235044_, RandomSource p_235045_) {
        return Collections.emptyList();
    }

    @Override
    public boolean m_7541_() {
        return false;
    }

    @Override
    public boolean m_7539_() {
        return true;
    }

    @Override
    public boolean m_7547_() {
        return this.f_119170_;
    }

    @Override
    public boolean m_7521_() {
        return true;
    }

    @Override
    public TextureAtlasSprite m_6160_() {
        return this.f_119169_;
    }

    @Override
    public ItemTransforms m_7442_() {
        return this.f_119167_;
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.f_119168_;
    }
}

