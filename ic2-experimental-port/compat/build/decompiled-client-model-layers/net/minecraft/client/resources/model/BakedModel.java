/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resources.model;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public interface BakedModel {
    public List<BakedQuad> m_213637_(@Nullable BlockState var1, @Nullable Direction var2, RandomSource var3);

    public boolean m_7541_();

    public boolean m_7539_();

    public boolean m_7547_();

    public boolean m_7521_();

    public TextureAtlasSprite m_6160_();

    public ItemTransforms m_7442_();

    public ItemOverrides m_7343_();
}

