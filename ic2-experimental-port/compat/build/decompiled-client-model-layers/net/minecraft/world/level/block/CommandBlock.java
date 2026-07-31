/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.slf4j.Logger;

public class CommandBlock
extends BaseEntityBlock
implements GameMasterBlock {
    private static final Logger f_51795_ = LogUtils.getLogger();
    public static final DirectionProperty f_51793_ = DirectionalBlock.f_52588_;
    public static final BooleanProperty f_51794_ = BlockStateProperties.f_61428_;
    private final boolean f_153078_;

    public CommandBlock(BlockBehaviour.Properties p_153080_, boolean p_153081_) {
        super(p_153080_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51793_, Direction.NORTH)).m_61124_(f_51794_, false));
        this.f_153078_ = p_153081_;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153083_, BlockState p_153084_) {
        CommandBlockEntity $$2 = new CommandBlockEntity(p_153083_, p_153084_);
        $$2.m_59137_(this.f_153078_);
        return $$2;
    }

    @Override
    public void m_6861_(BlockState p_51838_, Level p_51839_, BlockPos p_51840_, Block p_51841_, BlockPos p_51842_, boolean p_51843_) {
        if (p_51839_.f_46443_) {
            return;
        }
        BlockEntity $$6 = p_51839_.m_7702_(p_51840_);
        if (!($$6 instanceof CommandBlockEntity)) {
            return;
        }
        CommandBlockEntity $$7 = (CommandBlockEntity)$$6;
        boolean $$8 = p_51839_.m_46753_(p_51840_);
        boolean $$9 = $$7.m_59142_();
        $$7.m_59135_($$8);
        if ($$9 || $$7.m_59143_() || $$7.m_59148_() == CommandBlockEntity.Mode.SEQUENCE) {
            return;
        }
        if ($$8) {
            $$7.m_59146_();
            p_51839_.m_186460_(p_51840_, this, 1);
        }
    }

    @Override
    public void m_213897_(BlockState p_221005_, ServerLevel p_221006_, BlockPos p_221007_, RandomSource p_221008_) {
        BlockEntity $$4 = p_221006_.m_7702_(p_221007_);
        if ($$4 instanceof CommandBlockEntity) {
            CommandBlockEntity $$5 = (CommandBlockEntity)$$4;
            BaseCommandBlock $$6 = $$5.m_59141_();
            boolean $$7 = !StringUtil.m_14408_($$6.m_45438_());
            CommandBlockEntity.Mode $$8 = $$5.m_59148_();
            boolean $$9 = $$5.m_59145_();
            if ($$8 == CommandBlockEntity.Mode.AUTO) {
                $$5.m_59146_();
                if ($$9) {
                    this.m_51831_(p_221005_, p_221006_, p_221007_, $$6, $$7);
                } else if ($$5.m_59151_()) {
                    $$6.m_45410_(0);
                }
                if ($$5.m_59142_() || $$5.m_59143_()) {
                    p_221006_.m_186460_(p_221007_, this, 1);
                }
            } else if ($$8 == CommandBlockEntity.Mode.REDSTONE) {
                if ($$9) {
                    this.m_51831_(p_221005_, p_221006_, p_221007_, $$6, $$7);
                } else if ($$5.m_59151_()) {
                    $$6.m_45410_(0);
                }
            }
            p_221006_.m_46717_(p_221007_, this);
        }
    }

    private void m_51831_(BlockState p_51832_, Level p_51833_, BlockPos p_51834_, BaseCommandBlock p_51835_, boolean p_51836_) {
        if (p_51836_) {
            p_51835_.m_45414_(p_51833_);
        } else {
            p_51835_.m_45410_(0);
        }
        CommandBlock.m_51809_(p_51833_, p_51834_, p_51832_.m_61143_(f_51793_));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51825_, Level p_51826_, BlockPos p_51827_, Player p_51828_, InteractionHand p_51829_, BlockHitResult p_51830_) {
        BlockEntity $$6 = p_51826_.m_7702_(p_51827_);
        if ($$6 instanceof CommandBlockEntity && p_51828_.m_36337_()) {
            p_51828_.m_7698_((CommandBlockEntity)$$6);
            return InteractionResult.m_19078_(p_51826_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean m_7278_(BlockState p_51814_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_51821_, Level p_51822_, BlockPos p_51823_) {
        BlockEntity $$3 = p_51822_.m_7702_(p_51823_);
        if ($$3 instanceof CommandBlockEntity) {
            return ((CommandBlockEntity)$$3).m_59141_().m_45436_();
        }
        return 0;
    }

    @Override
    public void m_6402_(Level p_51804_, BlockPos p_51805_, BlockState p_51806_, LivingEntity p_51807_, ItemStack p_51808_) {
        BlockEntity $$5 = p_51804_.m_7702_(p_51805_);
        if (!($$5 instanceof CommandBlockEntity)) {
            return;
        }
        CommandBlockEntity $$6 = (CommandBlockEntity)$$5;
        BaseCommandBlock $$7 = $$6.m_59141_();
        if (p_51808_.m_41788_()) {
            $$7.m_45423_(p_51808_.m_41786_());
        }
        if (!p_51804_.f_46443_) {
            if (BlockItem.m_186336_(p_51808_) == null) {
                $$7.m_45428_(p_51804_.m_46469_().m_46207_(GameRules.f_46144_));
                $$6.m_59137_(this.f_153078_);
            }
            if ($$6.m_59148_() == CommandBlockEntity.Mode.SEQUENCE) {
                boolean $$8 = p_51804_.m_46753_(p_51805_);
                $$6.m_59135_($$8);
            }
        }
    }

    @Override
    public RenderShape m_7514_(BlockState p_51853_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState m_6843_(BlockState p_51848_, Rotation p_51849_) {
        return (BlockState)p_51848_.m_61124_(f_51793_, p_51849_.m_55954_(p_51848_.m_61143_(f_51793_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_51845_, Mirror p_51846_) {
        return p_51845_.m_60717_(p_51846_.m_54846_(p_51845_.m_61143_(f_51793_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51851_) {
        p_51851_.m_61104_(f_51793_, f_51794_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_51800_) {
        return (BlockState)this.m_49966_().m_61124_(f_51793_, p_51800_.m_7820_().m_122424_());
    }

    private static void m_51809_(Level p_51810_, BlockPos p_51811_, Direction p_51812_) {
        BlockPos.MutableBlockPos $$3 = p_51811_.m_122032_();
        GameRules $$4 = p_51810_.m_46469_();
        int $$5 = $$4.m_46215_(GameRules.f_46152_);
        while ($$5-- > 0) {
            CommandBlockEntity $$9;
            BlockEntity $$8;
            $$3.m_122173_(p_51812_);
            BlockState $$6 = p_51810_.m_8055_($$3);
            Block $$7 = $$6.m_60734_();
            if (!$$6.m_60713_(Blocks.f_50448_) || !(($$8 = p_51810_.m_7702_($$3)) instanceof CommandBlockEntity) || ($$9 = (CommandBlockEntity)$$8).m_59148_() != CommandBlockEntity.Mode.SEQUENCE) break;
            if ($$9.m_59142_() || $$9.m_59143_()) {
                BaseCommandBlock $$10 = $$9.m_59141_();
                if ($$9.m_59146_()) {
                    if (!$$10.m_45414_(p_51810_)) break;
                    p_51810_.m_46717_($$3, $$7);
                } else if ($$9.m_59151_()) {
                    $$10.m_45410_(0);
                }
            }
            p_51812_ = $$6.m_61143_(f_51793_);
        }
        if ($$5 <= 0) {
            int $$11 = Math.max($$4.m_46215_(GameRules.f_46152_), 0);
            f_51795_.warn("Command Block chain tried to execute more than {} steps!", (Object)$$11);
        }
    }
}

