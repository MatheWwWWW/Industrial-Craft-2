/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChorusFlowerBlock
extends Block {
    public static final int f_153067_ = 5;
    public static final IntegerProperty f_51647_ = BlockStateProperties.f_61408_;
    private final ChorusPlantBlock f_51648_;

    protected ChorusFlowerBlock(ChorusPlantBlock p_51651_, BlockBehaviour.Properties p_51652_) {
        super(p_51652_);
        this.f_51648_ = p_51651_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51647_, 0));
    }

    @Override
    public void m_213897_(BlockState p_220975_, ServerLevel p_220976_, BlockPos p_220977_, RandomSource p_220978_) {
        if (!p_220975_.m_60710_(p_220976_, p_220977_)) {
            p_220976_.m_46961_(p_220977_, true);
        }
    }

    @Override
    public boolean m_6724_(BlockState p_51696_) {
        return p_51696_.m_61143_(f_51647_) < 5;
    }

    @Override
    public void m_213898_(BlockState p_220980_, ServerLevel p_220981_, BlockPos p_220982_, RandomSource p_220983_) {
        BlockPos $$4 = p_220982_.m_7494_();
        if (!p_220981_.m_46859_($$4) || $$4.m_123342_() >= p_220981_.m_151558_()) {
            return;
        }
        int $$5 = p_220980_.m_61143_(f_51647_);
        if ($$5 >= 5) {
            return;
        }
        boolean $$6 = false;
        boolean $$7 = false;
        BlockState $$8 = p_220981_.m_8055_(p_220982_.m_7495_());
        if ($$8.m_60713_(Blocks.f_50259_)) {
            $$6 = true;
        } else if ($$8.m_60713_(this.f_51648_)) {
            int $$9 = 1;
            for (int $$10 = 0; $$10 < 4; ++$$10) {
                BlockState $$11 = p_220981_.m_8055_(p_220982_.m_6625_($$9 + 1));
                if ($$11.m_60713_(this.f_51648_)) {
                    ++$$9;
                    continue;
                }
                if (!$$11.m_60713_(Blocks.f_50259_)) break;
                $$7 = true;
                break;
            }
            if ($$9 < 2 || $$9 <= p_220983_.m_188503_($$7 ? 5 : 4)) {
                $$6 = true;
            }
        } else if ($$8.m_60795_()) {
            $$6 = true;
        }
        if ($$6 && ChorusFlowerBlock.m_51697_(p_220981_, $$4, null) && p_220981_.m_46859_(p_220982_.m_6630_(2))) {
            p_220981_.m_7731_(p_220982_, this.f_51648_.m_51710_(p_220981_, p_220982_), 2);
            this.m_51661_(p_220981_, $$4, $$5);
        } else if ($$5 < 4) {
            int $$12 = p_220983_.m_188503_(4);
            if ($$7) {
                ++$$12;
            }
            boolean $$13 = false;
            for (int $$14 = 0; $$14 < $$12; ++$$14) {
                Direction $$15 = Direction.Plane.HORIZONTAL.m_235690_(p_220983_);
                BlockPos $$16 = p_220982_.m_121945_($$15);
                if (!p_220981_.m_46859_($$16) || !p_220981_.m_46859_($$16.m_7495_()) || !ChorusFlowerBlock.m_51697_(p_220981_, $$16, $$15.m_122424_())) continue;
                this.m_51661_(p_220981_, $$16, $$5 + 1);
                $$13 = true;
            }
            if ($$13) {
                p_220981_.m_7731_(p_220982_, this.f_51648_.m_51710_(p_220981_, p_220982_), 2);
            } else {
                this.m_51658_(p_220981_, p_220982_);
            }
        } else {
            this.m_51658_(p_220981_, p_220982_);
        }
    }

    private void m_51661_(Level p_51662_, BlockPos p_51663_, int p_51664_) {
        p_51662_.m_7731_(p_51663_, (BlockState)this.m_49966_().m_61124_(f_51647_, p_51664_), 2);
        p_51662_.m_46796_(1033, p_51663_, 0);
    }

    private void m_51658_(Level p_51659_, BlockPos p_51660_) {
        p_51659_.m_7731_(p_51660_, (BlockState)this.m_49966_().m_61124_(f_51647_, 5), 2);
        p_51659_.m_46796_(1034, p_51660_, 0);
    }

    private static boolean m_51697_(LevelReader p_51698_, BlockPos p_51699_, @Nullable Direction p_51700_) {
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            if ($$3 == p_51700_ || p_51698_.m_46859_(p_51699_.m_121945_($$3))) continue;
            return false;
        }
        return true;
    }

    @Override
    public BlockState m_7417_(BlockState p_51687_, Direction p_51688_, BlockState p_51689_, LevelAccessor p_51690_, BlockPos p_51691_, BlockPos p_51692_) {
        if (p_51688_ != Direction.UP && !p_51687_.m_60710_(p_51690_, p_51691_)) {
            p_51690_.m_186460_(p_51691_, this, 1);
        }
        return super.m_7417_(p_51687_, p_51688_, p_51689_, p_51690_, p_51691_, p_51692_);
    }

    @Override
    public boolean m_7898_(BlockState p_51683_, LevelReader p_51684_, BlockPos p_51685_) {
        BlockState $$3 = p_51684_.m_8055_(p_51685_.m_7495_());
        if ($$3.m_60713_(this.f_51648_) || $$3.m_60713_(Blocks.f_50259_)) {
            return true;
        }
        if (!$$3.m_60795_()) {
            return false;
        }
        boolean $$4 = false;
        for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            BlockState $$6 = p_51684_.m_8055_(p_51685_.m_121945_($$5));
            if ($$6.m_60713_(this.f_51648_)) {
                if ($$4) {
                    return false;
                }
                $$4 = true;
                continue;
            }
            if ($$6.m_60795_()) continue;
            return false;
        }
        return $$4;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51694_) {
        p_51694_.m_61104_(f_51647_);
    }

    public static void m_220962_(LevelAccessor p_220963_, BlockPos p_220964_, RandomSource p_220965_, int p_220966_) {
        p_220963_.m_7731_(p_220964_, ((ChorusPlantBlock)Blocks.f_50490_).m_51710_(p_220963_, p_220964_), 2);
        ChorusFlowerBlock.m_220967_(p_220963_, p_220964_, p_220965_, p_220964_, p_220966_, 0);
    }

    private static void m_220967_(LevelAccessor p_220968_, BlockPos p_220969_, RandomSource p_220970_, BlockPos p_220971_, int p_220972_, int p_220973_) {
        ChorusPlantBlock $$6 = (ChorusPlantBlock)Blocks.f_50490_;
        int $$7 = p_220970_.m_188503_(4) + 1;
        if (p_220973_ == 0) {
            ++$$7;
        }
        for (int $$8 = 0; $$8 < $$7; ++$$8) {
            BlockPos $$9 = p_220969_.m_6630_($$8 + 1);
            if (!ChorusFlowerBlock.m_51697_(p_220968_, $$9, null)) {
                return;
            }
            p_220968_.m_7731_($$9, $$6.m_51710_(p_220968_, $$9), 2);
            p_220968_.m_7731_($$9.m_7495_(), $$6.m_51710_(p_220968_, $$9.m_7495_()), 2);
        }
        boolean $$10 = false;
        if (p_220973_ < 4) {
            int $$11 = p_220970_.m_188503_(4);
            if (p_220973_ == 0) {
                ++$$11;
            }
            for (int $$12 = 0; $$12 < $$11; ++$$12) {
                Direction $$13 = Direction.Plane.HORIZONTAL.m_235690_(p_220970_);
                BlockPos $$14 = p_220969_.m_6630_($$7).m_121945_($$13);
                if (Math.abs($$14.m_123341_() - p_220971_.m_123341_()) >= p_220972_ || Math.abs($$14.m_123343_() - p_220971_.m_123343_()) >= p_220972_ || !p_220968_.m_46859_($$14) || !p_220968_.m_46859_($$14.m_7495_()) || !ChorusFlowerBlock.m_51697_(p_220968_, $$14, $$13.m_122424_())) continue;
                $$10 = true;
                p_220968_.m_7731_($$14, $$6.m_51710_(p_220968_, $$14), 2);
                p_220968_.m_7731_($$14.m_121945_($$13.m_122424_()), $$6.m_51710_(p_220968_, $$14.m_121945_($$13.m_122424_())), 2);
                ChorusFlowerBlock.m_220967_(p_220968_, $$14, p_220970_, p_220971_, p_220972_, p_220973_ + 1);
            }
        }
        if (!$$10) {
            p_220968_.m_7731_(p_220969_.m_6630_($$7), (BlockState)Blocks.f_50491_.m_49966_().m_61124_(f_51647_, 5), 2);
        }
    }

    @Override
    public void m_5581_(Level p_51654_, BlockState p_51655_, BlockHitResult p_51656_, Projectile p_51657_) {
        BlockPos $$4 = p_51656_.m_82425_();
        if (!p_51654_.f_46443_ && p_51657_.m_142265_(p_51654_, $$4) && p_51657_.m_6095_().m_204039_(EntityTypeTags.f_13124_)) {
            p_51654_.m_46953_($$4, true, p_51657_);
        }
    }
}

