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
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class RandomBlockMatchTest
extends RuleTest {
    public static final Codec<RandomBlockMatchTest> f_74258_ = RecordCodecBuilder.create(p_74270_ -> p_74270_.group((App)Registry.f_122824_.m_194605_().fieldOf("block").forGetter(p_163766_ -> p_163766_.f_74259_), (App)Codec.FLOAT.fieldOf("probability").forGetter(p_163764_ -> Float.valueOf(p_163764_.f_74260_))).apply((Applicative)p_74270_, RandomBlockMatchTest::new));
    private final Block f_74259_;
    private final float f_74260_;

    public RandomBlockMatchTest(Block p_74263_, float p_74264_) {
        this.f_74259_ = p_74263_;
        this.f_74260_ = p_74264_;
    }

    @Override
    public boolean m_213865_(BlockState p_230317_, RandomSource p_230318_) {
        return p_230317_.m_60713_(this.f_74259_) && p_230318_.m_188501_() < this.f_74260_;
    }

    @Override
    protected RuleTestType<?> m_7319_() {
        return RuleTestType.f_74316_;
    }
}

