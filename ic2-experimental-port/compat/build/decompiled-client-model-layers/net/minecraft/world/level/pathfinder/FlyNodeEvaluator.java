/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FlyNodeEvaluator
extends WalkNodeEvaluator {
    private final Long2ObjectMap<BlockPathTypes> f_164687_ = new Long2ObjectOpenHashMap();

    @Override
    public void m_6028_(PathNavigationRegion p_77261_, Mob p_77262_) {
        super.m_6028_(p_77261_, p_77262_);
        this.f_164687_.clear();
        this.f_77544_ = p_77262_.m_21439_(BlockPathTypes.WATER);
    }

    @Override
    public void m_6802_() {
        this.f_77313_.m_21441_(BlockPathTypes.WATER, this.f_77544_);
        this.f_164687_.clear();
        super.m_6802_();
    }

    @Override
    @Nullable
    public Node m_7171_() {
        BlockPos $$4;
        BlockPathTypes $$5;
        int $$3;
        if (this.m_77361_() && this.f_77313_.m_20069_()) {
            int $$0 = this.f_77313_.m_146904_();
            BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos(this.f_77313_.m_20185_(), (double)$$0, this.f_77313_.m_20189_());
            BlockState $$2 = this.f_77312_.m_8055_($$1);
            while ($$2.m_60713_(Blocks.f_49990_)) {
                $$1.m_122169_(this.f_77313_.m_20185_(), ++$$0, this.f_77313_.m_20189_());
                $$2 = this.f_77312_.m_8055_($$1);
            }
        } else {
            $$3 = Mth.m_14107_(this.f_77313_.m_20186_() + 0.5);
        }
        if (this.f_77313_.m_21439_($$5 = this.m_164693_(($$4 = this.f_77313_.m_20183_()).m_123341_(), $$3, $$4.m_123343_())) < 0.0f) {
            for (BlockPos $$6 : this.f_77313_.m_238383_()) {
                BlockPathTypes $$7 = this.m_164693_($$6.m_123341_(), $$6.m_123342_(), $$6.m_123343_());
                if (!(this.f_77313_.m_21439_($$7) >= 0.0f)) continue;
                return super.m_230631_($$6);
            }
        }
        return super.m_230631_(new BlockPos($$4.m_123341_(), $$3, $$4.m_123343_()));
    }

    @Override
    public Target m_7568_(double p_77229_, double p_77230_, double p_77231_) {
        return this.m_230615_(super.m_5676_(Mth.m_14107_(p_77229_), Mth.m_14107_(p_77230_), Mth.m_14107_(p_77231_)));
    }

    @Override
    public int m_6065_(Node[] p_77266_, Node p_77267_) {
        Node $$28;
        Node $$27;
        Node $$26;
        Node $$25;
        Node $$24;
        Node $$23;
        Node $$22;
        Node $$21;
        Node $$20;
        Node $$19;
        Node $$18;
        Node $$17;
        Node $$16;
        Node $$15;
        Node $$14;
        Node $$13;
        Node $$12;
        Node $$11;
        Node $$10;
        Node $$9;
        Node $$8;
        Node $$7;
        Node $$6;
        Node $$5;
        Node $$4;
        int $$2 = 0;
        Node $$3 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_, p_77267_.f_77273_ + 1);
        if (this.m_77269_($$3)) {
            p_77266_[$$2++] = $$3;
        }
        if (this.m_77269_($$4 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_))) {
            p_77266_[$$2++] = $$4;
        }
        if (this.m_77269_($$5 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_))) {
            p_77266_[$$2++] = $$5;
        }
        if (this.m_77269_($$6 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_, p_77267_.f_77273_ - 1))) {
            p_77266_[$$2++] = $$6;
        }
        if (this.m_77269_($$7 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_))) {
            p_77266_[$$2++] = $$7;
        }
        if (this.m_77269_($$8 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_))) {
            p_77266_[$$2++] = $$8;
        }
        if (this.m_77269_($$9 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$3) && this.m_77263_($$7)) {
            p_77266_[$$2++] = $$9;
        }
        if (this.m_77269_($$10 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_)) && this.m_77263_($$4) && this.m_77263_($$7)) {
            p_77266_[$$2++] = $$10;
        }
        if (this.m_77269_($$11 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_)) && this.m_77263_($$5) && this.m_77263_($$7)) {
            p_77266_[$$2++] = $$11;
        }
        if (this.m_77269_($$12 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$6) && this.m_77263_($$7)) {
            p_77266_[$$2++] = $$12;
        }
        if (this.m_77269_($$13 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$3) && this.m_77263_($$8)) {
            p_77266_[$$2++] = $$13;
        }
        if (this.m_77269_($$14 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_)) && this.m_77263_($$4) && this.m_77263_($$8)) {
            p_77266_[$$2++] = $$14;
        }
        if (this.m_77269_($$15 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_)) && this.m_77263_($$5) && this.m_77263_($$8)) {
            p_77266_[$$2++] = $$15;
        }
        if (this.m_77269_($$16 = this.m_5676_(p_77267_.f_77271_, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$6) && this.m_77263_($$8)) {
            p_77266_[$$2++] = $$16;
        }
        if (this.m_77269_($$17 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_ - 1)) && this.m_77263_($$6) && this.m_77263_($$5)) {
            p_77266_[$$2++] = $$17;
        }
        if (this.m_77269_($$18 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_, p_77267_.f_77273_ + 1)) && this.m_77263_($$3) && this.m_77263_($$5)) {
            p_77266_[$$2++] = $$18;
        }
        if (this.m_77269_($$19 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_ - 1)) && this.m_77263_($$6) && this.m_77263_($$4)) {
            p_77266_[$$2++] = $$19;
        }
        if (this.m_77269_($$20 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_, p_77267_.f_77273_ + 1)) && this.m_77263_($$3) && this.m_77263_($$4)) {
            p_77266_[$$2++] = $$20;
        }
        if (this.m_77269_($$21 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$17) && this.m_77263_($$6) && this.m_77263_($$5) && this.m_77263_($$7) && this.m_77263_($$12) && this.m_77263_($$11)) {
            p_77266_[$$2++] = $$21;
        }
        if (this.m_77269_($$22 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$18) && this.m_77263_($$3) && this.m_77263_($$5) && this.m_77263_($$7) && this.m_77263_($$9) && this.m_77263_($$11)) {
            p_77266_[$$2++] = $$22;
        }
        if (this.m_77269_($$23 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$19) && this.m_77263_($$6) && this.m_77263_($$4) && this.m_77263_($$7) && this.m_77263_($$12) && this.m_77263_($$10)) {
            p_77266_[$$2++] = $$23;
        }
        if (this.m_77269_($$24 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ + 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$20) && this.m_77263_($$3) && this.m_77263_($$4) && this.m_77263_($$7) && this.m_77263_($$9) && this.m_77263_($$10)) {
            p_77266_[$$2++] = $$24;
        }
        if (this.m_77269_($$25 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$17) && this.m_77263_($$6) && this.m_77263_($$5) && this.m_77263_($$8) && this.m_77263_($$16) && this.m_77263_($$15)) {
            p_77266_[$$2++] = $$25;
        }
        if (this.m_77269_($$26 = this.m_5676_(p_77267_.f_77271_ + 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$18) && this.m_77263_($$3) && this.m_77263_($$5) && this.m_77263_($$8) && this.m_77263_($$13) && this.m_77263_($$15)) {
            p_77266_[$$2++] = $$26;
        }
        if (this.m_77269_($$27 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ - 1)) && this.m_77263_($$19) && this.m_77263_($$6) && this.m_77263_($$4) && this.m_77263_($$8) && this.m_77263_($$16) && this.m_77263_($$14)) {
            p_77266_[$$2++] = $$27;
        }
        if (this.m_77269_($$28 = this.m_5676_(p_77267_.f_77271_ - 1, p_77267_.f_77272_ - 1, p_77267_.f_77273_ + 1)) && this.m_77263_($$20) && this.m_77263_($$3) && this.m_77263_($$4) && this.m_77263_($$8) && this.m_77263_($$13) && this.m_77263_($$14)) {
            p_77266_[$$2++] = $$28;
        }
        return $$2;
    }

    private boolean m_77263_(@Nullable Node p_77264_) {
        return p_77264_ != null && p_77264_.f_77281_ >= 0.0f;
    }

    private boolean m_77269_(@Nullable Node p_77270_) {
        return p_77270_ != null && !p_77270_.f_77279_;
    }

    @Override
    @Nullable
    protected Node m_5676_(int p_77233_, int p_77234_, int p_77235_) {
        Node $$3 = null;
        BlockPathTypes $$4 = this.m_164693_(p_77233_, p_77234_, p_77235_);
        float $$5 = this.f_77313_.m_21439_($$4);
        if ($$5 >= 0.0f && ($$3 = super.m_5676_(p_77233_, p_77234_, p_77235_)) != null) {
            $$3.f_77282_ = $$4;
            $$3.f_77281_ = Math.max($$3.f_77281_, $$5);
            if ($$4 == BlockPathTypes.WALKABLE) {
                $$3.f_77281_ += 1.0f;
            }
        }
        return $$3;
    }

    private BlockPathTypes m_164693_(int p_164694_, int p_164695_, int p_164696_) {
        return (BlockPathTypes)((Object)this.f_164687_.computeIfAbsent(BlockPos.m_121882_(p_164694_, p_164695_, p_164696_), p_164692_ -> this.m_7209_(this.f_77312_, p_164694_, p_164695_, p_164696_, this.f_77313_, this.f_77315_, this.f_77316_, this.f_77317_, this.m_77360_(), this.m_77357_())));
    }

    @Override
    public BlockPathTypes m_7209_(BlockGetter p_77250_, int p_77251_, int p_77252_, int p_77253_, Mob p_77254_, int p_77255_, int p_77256_, int p_77257_, boolean p_77258_, boolean p_77259_) {
        EnumSet<BlockPathTypes> $$10 = EnumSet.noneOf(BlockPathTypes.class);
        BlockPathTypes $$11 = BlockPathTypes.BLOCKED;
        BlockPos $$12 = p_77254_.m_20183_();
        $$11 = super.m_77580_(p_77250_, p_77251_, p_77252_, p_77253_, p_77255_, p_77256_, p_77257_, p_77258_, p_77259_, $$10, $$11, $$12);
        if ($$10.contains((Object)BlockPathTypes.FENCE)) {
            return BlockPathTypes.FENCE;
        }
        BlockPathTypes $$13 = BlockPathTypes.BLOCKED;
        for (BlockPathTypes $$14 : $$10) {
            if (p_77254_.m_21439_($$14) < 0.0f) {
                return $$14;
            }
            if (!(p_77254_.m_21439_($$14) >= p_77254_.m_21439_($$13))) continue;
            $$13 = $$14;
        }
        if ($$11 == BlockPathTypes.OPEN && p_77254_.m_21439_($$13) == 0.0f) {
            return BlockPathTypes.OPEN;
        }
        return $$13;
    }

    @Override
    public BlockPathTypes m_8086_(BlockGetter p_77245_, int p_77246_, int p_77247_, int p_77248_) {
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        BlockPathTypes $$5 = FlyNodeEvaluator.m_77643_(p_77245_, $$4.m_122178_(p_77246_, p_77247_, p_77248_));
        if ($$5 == BlockPathTypes.OPEN && p_77247_ >= p_77245_.m_141937_() + 1) {
            BlockPathTypes $$6 = FlyNodeEvaluator.m_77643_(p_77245_, $$4.m_122178_(p_77246_, p_77247_ - 1, p_77248_));
            if ($$6 == BlockPathTypes.DAMAGE_FIRE || $$6 == BlockPathTypes.LAVA) {
                $$5 = BlockPathTypes.DAMAGE_FIRE;
            } else if ($$6 == BlockPathTypes.DAMAGE_CACTUS) {
                $$5 = BlockPathTypes.DAMAGE_CACTUS;
            } else if ($$6 == BlockPathTypes.DAMAGE_OTHER) {
                $$5 = BlockPathTypes.DAMAGE_OTHER;
            } else if ($$6 == BlockPathTypes.COCOA) {
                $$5 = BlockPathTypes.COCOA;
            } else if ($$6 == BlockPathTypes.FENCE) {
                if (!$$4.equals(this.f_77313_.m_20183_())) {
                    $$5 = BlockPathTypes.FENCE;
                }
            } else {
                BlockPathTypes blockPathTypes = $$5 = $$6 == BlockPathTypes.WALKABLE || $$6 == BlockPathTypes.OPEN || $$6 == BlockPathTypes.WATER ? BlockPathTypes.OPEN : BlockPathTypes.WALKABLE;
            }
        }
        if ($$5 == BlockPathTypes.WALKABLE || $$5 == BlockPathTypes.OPEN) {
            $$5 = FlyNodeEvaluator.m_77607_(p_77245_, $$4.m_122178_(p_77246_, p_77247_, p_77248_), $$5);
        }
        return $$5;
    }
}

