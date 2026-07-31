/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.client.renderer.blockentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BrightnessCombiner<S extends BlockEntity>
implements DoubleBlockCombiner.Combiner<S, Int2IntFunction> {
    @Override
    public Int2IntFunction m_6959_(S p_112320_, S p_112321_) {
        return p_112325_ -> {
            int $$3 = LevelRenderer.m_109541_(p_112320_.m_58904_(), p_112320_.m_58899_());
            int $$4 = LevelRenderer.m_109541_(p_112321_.m_58904_(), p_112321_.m_58899_());
            int $$5 = LightTexture.m_109883_($$3);
            int $$6 = LightTexture.m_109883_($$4);
            int $$7 = LightTexture.m_109894_($$3);
            int $$8 = LightTexture.m_109894_($$4);
            return LightTexture.m_109885_(Math.max($$5, $$6), Math.max($$7, $$8));
        };
    }

    @Override
    public Int2IntFunction m_7693_(S p_112318_) {
        return p_112333_ -> p_112333_;
    }

    @Override
    public Int2IntFunction m_6502_() {
        return p_112316_ -> p_112316_;
    }

    @Override
    public /* synthetic */ Object m_6502_() {
        return this.m_6502_();
    }
}

