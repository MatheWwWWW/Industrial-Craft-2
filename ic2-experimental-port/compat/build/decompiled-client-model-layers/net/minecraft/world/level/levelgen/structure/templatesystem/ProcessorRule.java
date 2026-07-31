/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class ProcessorRule {
    public static final Codec<ProcessorRule> f_74215_ = RecordCodecBuilder.create(p_74246_ -> p_74246_.group((App)RuleTest.f_74307_.fieldOf("input_predicate").forGetter(p_163747_ -> p_163747_.f_74216_), (App)RuleTest.f_74307_.fieldOf("location_predicate").forGetter(p_163745_ -> p_163745_.f_74217_), (App)PosRuleTest.f_74198_.optionalFieldOf("position_predicate", (Object)PosAlwaysTrueTest.f_74188_).forGetter(p_163743_ -> p_163743_.f_74218_), (App)BlockState.f_61039_.fieldOf("output_state").forGetter(p_163741_ -> p_163741_.f_74219_), (App)CompoundTag.f_128325_.optionalFieldOf("output_nbt").forGetter(p_163739_ -> Optional.ofNullable(p_163739_.f_74220_))).apply((Applicative)p_74246_, ProcessorRule::new));
    private final RuleTest f_74216_;
    private final RuleTest f_74217_;
    private final PosRuleTest f_74218_;
    private final BlockState f_74219_;
    @Nullable
    private final CompoundTag f_74220_;

    public ProcessorRule(RuleTest p_74223_, RuleTest p_74224_, BlockState p_74225_) {
        this(p_74223_, p_74224_, PosAlwaysTrueTest.f_74188_, p_74225_, Optional.empty());
    }

    public ProcessorRule(RuleTest p_74227_, RuleTest p_74228_, PosRuleTest p_74229_, BlockState p_74230_) {
        this(p_74227_, p_74228_, p_74229_, p_74230_, Optional.empty());
    }

    public ProcessorRule(RuleTest p_74232_, RuleTest p_74233_, PosRuleTest p_74234_, BlockState p_74235_, Optional<CompoundTag> p_74236_) {
        this.f_74216_ = p_74232_;
        this.f_74217_ = p_74233_;
        this.f_74218_ = p_74234_;
        this.f_74219_ = p_74235_;
        this.f_74220_ = p_74236_.orElse(null);
    }

    public boolean m_230309_(BlockState p_230310_, BlockState p_230311_, BlockPos p_230312_, BlockPos p_230313_, BlockPos p_230314_, RandomSource p_230315_) {
        return this.f_74216_.m_213865_(p_230310_, p_230315_) && this.f_74217_.m_213865_(p_230311_, p_230315_) && this.f_74218_.m_213782_(p_230312_, p_230313_, p_230314_, p_230315_);
    }

    public BlockState m_74237_() {
        return this.f_74219_;
    }

    @Nullable
    public CompoundTag m_74249_() {
        return this.f_74220_;
    }
}

