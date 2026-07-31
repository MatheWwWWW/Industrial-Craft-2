/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class PistonStructureResolver {
    public static final int f_155936_ = 12;
    private final Level f_60409_;
    private final BlockPos f_60410_;
    private final boolean f_60411_;
    private final BlockPos f_60412_;
    private final Direction f_60413_;
    private final List<BlockPos> f_60414_ = Lists.newArrayList();
    private final List<BlockPos> f_60415_ = Lists.newArrayList();
    private final Direction f_60416_;

    public PistonStructureResolver(Level p_60418_, BlockPos p_60419_, Direction p_60420_, boolean p_60421_) {
        this.f_60409_ = p_60418_;
        this.f_60410_ = p_60419_;
        this.f_60416_ = p_60420_;
        this.f_60411_ = p_60421_;
        if (p_60421_) {
            this.f_60413_ = p_60420_;
            this.f_60412_ = p_60419_.m_121945_(p_60420_);
        } else {
            this.f_60413_ = p_60420_.m_122424_();
            this.f_60412_ = p_60419_.m_5484_(p_60420_, 2);
        }
    }

    public boolean m_60422_() {
        this.f_60414_.clear();
        this.f_60415_.clear();
        BlockState $$0 = this.f_60409_.m_8055_(this.f_60412_);
        if (!PistonBaseBlock.m_60204_($$0, this.f_60409_, this.f_60412_, this.f_60413_, false, this.f_60416_)) {
            if (this.f_60411_ && $$0.m_60811_() == PushReaction.DESTROY) {
                this.f_60415_.add(this.f_60412_);
                return true;
            }
            return false;
        }
        if (!this.m_60433_(this.f_60412_, this.f_60413_)) {
            return false;
        }
        for (int $$1 = 0; $$1 < this.f_60414_.size(); ++$$1) {
            BlockPos $$2 = this.f_60414_.get($$1);
            if (!PistonStructureResolver.m_155937_(this.f_60409_.m_8055_($$2)) || this.m_60431_($$2)) continue;
            return false;
        }
        return true;
    }

    private static boolean m_155937_(BlockState p_155938_) {
        return p_155938_.m_60713_(Blocks.f_50374_) || p_155938_.m_60713_(Blocks.f_50719_);
    }

    private static boolean m_155939_(BlockState p_155940_, BlockState p_155941_) {
        if (p_155940_.m_60713_(Blocks.f_50719_) && p_155941_.m_60713_(Blocks.f_50374_)) {
            return false;
        }
        if (p_155940_.m_60713_(Blocks.f_50374_) && p_155941_.m_60713_(Blocks.f_50719_)) {
            return false;
        }
        return PistonStructureResolver.m_155937_(p_155940_) || PistonStructureResolver.m_155937_(p_155941_);
    }

    private boolean m_60433_(BlockPos p_60434_, Direction p_60435_) {
        BlockState $$2 = this.f_60409_.m_8055_(p_60434_);
        if ($$2.m_60795_()) {
            return true;
        }
        if (!PistonBaseBlock.m_60204_($$2, this.f_60409_, p_60434_, this.f_60413_, false, p_60435_)) {
            return true;
        }
        if (p_60434_.equals(this.f_60410_)) {
            return true;
        }
        if (this.f_60414_.contains(p_60434_)) {
            return true;
        }
        int $$3 = 1;
        if ($$3 + this.f_60414_.size() > 12) {
            return false;
        }
        while (PistonStructureResolver.m_155937_($$2)) {
            BlockPos $$4 = p_60434_.m_5484_(this.f_60413_.m_122424_(), $$3);
            BlockState $$5 = $$2;
            $$2 = this.f_60409_.m_8055_($$4);
            if ($$2.m_60795_() || !PistonStructureResolver.m_155939_($$5, $$2) || !PistonBaseBlock.m_60204_($$2, this.f_60409_, $$4, this.f_60413_, false, this.f_60413_.m_122424_()) || $$4.equals(this.f_60410_)) break;
            if (++$$3 + this.f_60414_.size() <= 12) continue;
            return false;
        }
        int $$6 = 0;
        for (int $$7 = $$3 - 1; $$7 >= 0; --$$7) {
            this.f_60414_.add(p_60434_.m_5484_(this.f_60413_.m_122424_(), $$7));
            ++$$6;
        }
        int $$8 = 1;
        while (true) {
            BlockPos $$9;
            int $$10;
            if (($$10 = this.f_60414_.indexOf($$9 = p_60434_.m_5484_(this.f_60413_, $$8))) > -1) {
                this.m_60423_($$6, $$10);
                for (int $$11 = 0; $$11 <= $$10 + $$6; ++$$11) {
                    BlockPos $$12 = this.f_60414_.get($$11);
                    if (!PistonStructureResolver.m_155937_(this.f_60409_.m_8055_($$12)) || this.m_60431_($$12)) continue;
                    return false;
                }
                return true;
            }
            $$2 = this.f_60409_.m_8055_($$9);
            if ($$2.m_60795_()) {
                return true;
            }
            if (!PistonBaseBlock.m_60204_($$2, this.f_60409_, $$9, this.f_60413_, true, this.f_60413_) || $$9.equals(this.f_60410_)) {
                return false;
            }
            if ($$2.m_60811_() == PushReaction.DESTROY) {
                this.f_60415_.add($$9);
                return true;
            }
            if (this.f_60414_.size() >= 12) {
                return false;
            }
            this.f_60414_.add($$9);
            ++$$6;
            ++$$8;
        }
    }

    private void m_60423_(int p_60424_, int p_60425_) {
        ArrayList $$2 = Lists.newArrayList();
        ArrayList $$3 = Lists.newArrayList();
        ArrayList $$4 = Lists.newArrayList();
        $$2.addAll(this.f_60414_.subList(0, p_60425_));
        $$3.addAll(this.f_60414_.subList(this.f_60414_.size() - p_60424_, this.f_60414_.size()));
        $$4.addAll(this.f_60414_.subList(p_60425_, this.f_60414_.size() - p_60424_));
        this.f_60414_.clear();
        this.f_60414_.addAll($$2);
        this.f_60414_.addAll($$3);
        this.f_60414_.addAll($$4);
    }

    private boolean m_60431_(BlockPos p_60432_) {
        BlockState $$1 = this.f_60409_.m_8055_(p_60432_);
        for (Direction $$2 : Direction.values()) {
            BlockPos $$3;
            BlockState $$4;
            if ($$2.m_122434_() == this.f_60413_.m_122434_() || !PistonStructureResolver.m_155939_($$4 = this.f_60409_.m_8055_($$3 = p_60432_.m_121945_($$2)), $$1) || this.m_60433_($$3, $$2)) continue;
            return false;
        }
        return true;
    }

    public Direction m_155942_() {
        return this.f_60413_;
    }

    public List<BlockPos> m_60436_() {
        return this.f_60414_;
    }

    public List<BlockPos> m_60437_() {
        return this.f_60415_;
    }
}

