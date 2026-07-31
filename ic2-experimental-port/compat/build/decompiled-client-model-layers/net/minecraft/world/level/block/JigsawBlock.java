/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.BlockHitResult;

public class JigsawBlock
extends Block
implements EntityBlock,
GameMasterBlock {
    public static final EnumProperty<FrontAndTop> f_54222_ = BlockStateProperties.f_61375_;

    protected JigsawBlock(BlockBehaviour.Properties p_54225_) {
        super(p_54225_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54222_, FrontAndTop.NORTH_UP));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54244_) {
        p_54244_.m_61104_(f_54222_);
    }

    @Override
    public BlockState m_6843_(BlockState p_54241_, Rotation p_54242_) {
        return (BlockState)p_54241_.m_61124_(f_54222_, p_54242_.m_55948_().m_56530_(p_54241_.m_61143_(f_54222_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_54238_, Mirror p_54239_) {
        return (BlockState)p_54238_.m_61124_(f_54222_, p_54239_.m_54842_().m_56530_(p_54238_.m_61143_(f_54222_)));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54227_) {
        Direction $$3;
        Direction $$1 = p_54227_.m_43719_();
        if ($$1.m_122434_() == Direction.Axis.Y) {
            Direction $$2 = p_54227_.m_8125_().m_122424_();
        } else {
            $$3 = Direction.UP;
        }
        return (BlockState)this.m_49966_().m_61124_(f_54222_, FrontAndTop.m_122622_($$1, $$3));
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153448_, BlockState p_153449_) {
        return new JigsawBlockEntity(p_153448_, p_153449_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_54231_, Level p_54232_, BlockPos p_54233_, Player p_54234_, InteractionHand p_54235_, BlockHitResult p_54236_) {
        BlockEntity $$6 = p_54232_.m_7702_(p_54233_);
        if ($$6 instanceof JigsawBlockEntity && p_54234_.m_36337_()) {
            p_54234_.m_7569_((JigsawBlockEntity)$$6);
            return InteractionResult.m_19078_(p_54232_.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static boolean m_54245_(StructureTemplate.StructureBlockInfo p_54246_, StructureTemplate.StructureBlockInfo p_54247_) {
        Direction $$2 = JigsawBlock.m_54250_(p_54246_.f_74676_);
        Direction $$3 = JigsawBlock.m_54250_(p_54247_.f_74676_);
        Direction $$4 = JigsawBlock.m_54252_(p_54246_.f_74676_);
        Direction $$5 = JigsawBlock.m_54252_(p_54247_.f_74676_);
        JigsawBlockEntity.JointType $$6 = JigsawBlockEntity.JointType.m_59457_(p_54246_.f_74677_.m_128461_("joint")).orElseGet(() -> $$2.m_122434_().m_122479_() ? JigsawBlockEntity.JointType.ALIGNED : JigsawBlockEntity.JointType.ROLLABLE);
        boolean $$7 = $$6 == JigsawBlockEntity.JointType.ROLLABLE;
        return $$2 == $$3.m_122424_() && ($$7 || $$4 == $$5) && p_54246_.f_74677_.m_128461_("target").equals(p_54247_.f_74677_.m_128461_("name"));
    }

    public static Direction m_54250_(BlockState p_54251_) {
        return p_54251_.m_61143_(f_54222_).m_122625_();
    }

    public static Direction m_54252_(BlockState p_54253_) {
        return p_54253_.m_61143_(f_54222_).m_122629_();
    }
}

