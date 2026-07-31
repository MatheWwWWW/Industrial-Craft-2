/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LongJumpToPreferredBlock<E extends Mob>
extends LongJumpToRandomPos<E> {
    private final TagKey<Block> f_217259_;
    private final float f_217260_;
    private final List<LongJumpToRandomPos.PossibleJump> f_217261_ = new ArrayList<LongJumpToRandomPos.PossibleJump>();
    private boolean f_217262_;

    public LongJumpToPreferredBlock(UniformInt p_217264_, int p_217265_, int p_217266_, float p_217267_, Function<E, SoundEvent> p_217268_, TagKey<Block> p_217269_, float p_217270_, Predicate<BlockState> p_217271_) {
        super(p_217264_, p_217265_, p_217266_, p_217267_, p_217268_, p_217271_);
        this.f_217259_ = p_217269_;
        this.f_217260_ = p_217270_;
    }

    @Override
    protected void m_6735_(ServerLevel p_217279_, E p_217280_, long p_217281_) {
        super.m_6735_(p_217279_, p_217280_, p_217281_);
        this.f_217261_.clear();
        this.f_217262_ = ((LivingEntity)p_217280_).m_217043_().m_188501_() < this.f_217260_;
    }

    @Override
    protected Optional<LongJumpToRandomPos.PossibleJump> m_213675_(ServerLevel p_217273_) {
        if (!this.f_217262_) {
            return super.m_213675_(p_217273_);
        }
        BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
        while (!this.f_147630_.isEmpty()) {
            Optional<LongJumpToRandomPos.PossibleJump> $$2 = super.m_213675_(p_217273_);
            if (!$$2.isPresent()) continue;
            LongJumpToRandomPos.PossibleJump $$3 = $$2.get();
            if (p_217273_.m_8055_($$1.m_122159_($$3.m_147693_(), Direction.DOWN)).m_204336_(this.f_217259_)) {
                return $$2;
            }
            this.f_217261_.add($$3);
        }
        if (!this.f_217261_.isEmpty()) {
            return Optional.of(this.f_217261_.remove(0));
        }
        return Optional.empty();
    }

    @Override
    protected boolean m_213828_(ServerLevel p_217283_, E p_217284_, BlockPos p_217285_) {
        return super.m_213828_(p_217283_, p_217284_, p_217285_) && this.m_217286_(p_217283_, p_217285_);
    }

    private boolean m_217286_(ServerLevel p_217287_, BlockPos p_217288_) {
        return p_217287_.m_6425_(p_217288_).m_76178_() && p_217287_.m_6425_(p_217288_.m_7495_()).m_76178_();
    }
}

