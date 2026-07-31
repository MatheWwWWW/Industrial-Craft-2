/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class RandomBlockStateMatchTest
extends RuleTest {
    public static final Codec<RandomBlockStateMatchTest> f_74275_ = RecordCodecBuilder.create(p_74287_ -> p_74287_.group((App)BlockState.f_61039_.fieldOf("block_state").forGetter(p_163770_ -> p_163770_.f_74276_), (App)Codec.FLOAT.fieldOf("probability").forGetter(p_163768_ -> Float.valueOf(p_163768_.f_74277_))).apply((Applicative)p_74287_, RandomBlockStateMatchTest::new));
    private final BlockState f_74276_;
    private final float f_74277_;

    public RandomBlockStateMatchTest(BlockState p_74280_, float p_74281_) {
        this.f_74276_ = p_74280_;
        this.f_74277_ = p_74281_;
    }

    @Override
    public boolean m_213865_(BlockState p_230320_, RandomSource p_230321_) {
        return p_230320_ == this.f_74276_ && p_230321_.m_188501_() < this.f_74277_;
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74317_;
    }
}

