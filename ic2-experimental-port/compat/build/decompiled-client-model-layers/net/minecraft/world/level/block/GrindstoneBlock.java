/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GrindstoneBlock
extends FaceAttachedHorizontalDirectionalBlock {
    public static final VoxelShape f_53767_ = Block.m_49796_(2.0, 0.0, 6.0, 4.0, 7.0, 10.0);
    public static final VoxelShape f_53785_ = Block.m_49796_(12.0, 0.0, 6.0, 14.0, 7.0, 10.0);
    public static final VoxelShape f_53786_ = Block.m_49796_(2.0, 7.0, 5.0, 4.0, 13.0, 11.0);
    public static final VoxelShape f_53787_ = Block.m_49796_(12.0, 7.0, 5.0, 14.0, 13.0, 11.0);
    public static final VoxelShape f_53788_ = Shapes.m_83110_(f_53767_, f_53786_);
    public static final VoxelShape f_53789_ = Shapes.m_83110_(f_53785_, f_53787_);
    public static final VoxelShape f_53790_ = Shapes.m_83110_(f_53788_, f_53789_);
    public static final VoxelShape f_53791_ = Shapes.m_83110_(f_53790_, Block.m_49796_(4.0, 4.0, 2.0, 12.0, 16.0, 14.0));
    public static final VoxelShape f_53792_ = Block.m_49796_(6.0, 0.0, 2.0, 10.0, 7.0, 4.0);
    public static final VoxelShape f_53793_ = Block.m_49796_(6.0, 0.0, 12.0, 10.0, 7.0, 14.0);
    public static final VoxelShape f_53794_ = Block.m_49796_(5.0, 7.0, 2.0, 11.0, 13.0, 4.0);
    public static final VoxelShape f_53795_ = Block.m_49796_(5.0, 7.0, 12.0, 11.0, 13.0, 14.0);
    public static final VoxelShape f_53796_ = Shapes.m_83110_(f_53792_, f_53794_);
    public static final VoxelShape f_53797_ = Shapes.m_83110_(f_53793_, f_53795_);
    public static final VoxelShape f_53798_ = Shapes.m_83110_(f_53796_, f_53797_);
    public static final VoxelShape f_53799_ = Shapes.m_83110_(f_53798_, Block.m_49796_(2.0, 4.0, 4.0, 14.0, 16.0, 12.0));
    public static final VoxelShape f_53800_ = Block.m_49796_(2.0, 6.0, 0.0, 4.0, 10.0, 7.0);
    public static final VoxelShape f_53801_ = Block.m_49796_(12.0, 6.0, 0.0, 14.0, 10.0, 7.0);
    public static final VoxelShape f_53802_ = Block.m_49796_(2.0, 5.0, 7.0, 4.0, 11.0, 13.0);
    public static final VoxelShape f_53803_ = Block.m_49796_(12.0, 5.0, 7.0, 14.0, 11.0, 13.0);
    public static final VoxelShape f_53804_ = Shapes.m_83110_(f_53800_, f_53802_);
    public static final VoxelShape f_53805_ = Shapes.m_83110_(f_53801_, f_53803_);
    public static final VoxelShape f_53741_ = Shapes.m_83110_(f_53804_, f_53805_);
    public static final VoxelShape f_53742_ = Shapes.m_83110_(f_53741_, Block.m_49796_(4.0, 2.0, 4.0, 12.0, 14.0, 16.0));
    public static final VoxelShape f_53743_ = Block.m_49796_(2.0, 6.0, 7.0, 4.0, 10.0, 16.0);
    public static final VoxelShape f_53744_ = Block.m_49796_(12.0, 6.0, 7.0, 14.0, 10.0, 16.0);
    public static final VoxelShape f_53745_ = Block.m_49796_(2.0, 5.0, 3.0, 4.0, 11.0, 9.0);
    public static final VoxelShape f_53746_ = Block.m_49796_(12.0, 5.0, 3.0, 14.0, 11.0, 9.0);
    public static final VoxelShape f_53747_ = Shapes.m_83110_(f_53743_, f_53745_);
    public static final VoxelShape f_53748_ = Shapes.m_83110_(f_53744_, f_53746_);
    public static final VoxelShape f_53749_ = Shapes.m_83110_(f_53747_, f_53748_);
    public static final VoxelShape f_53750_ = Shapes.m_83110_(f_53749_, Block.m_49796_(4.0, 2.0, 0.0, 12.0, 14.0, 12.0));
    public static final VoxelShape f_53751_ = Block.m_49796_(7.0, 6.0, 2.0, 16.0, 10.0, 4.0);
    public static final VoxelShape f_53752_ = Block.m_49796_(7.0, 6.0, 12.0, 16.0, 10.0, 14.0);
    public static final VoxelShape f_53753_ = Block.m_49796_(3.0, 5.0, 2.0, 9.0, 11.0, 4.0);
    public static final VoxelShape f_53754_ = Block.m_49796_(3.0, 5.0, 12.0, 9.0, 11.0, 14.0);
    public static final VoxelShape f_53755_ = Shapes.m_83110_(f_53751_, f_53753_);
    public static final VoxelShape f_53756_ = Shapes.m_83110_(f_53752_, f_53754_);
    public static final VoxelShape f_53757_ = Shapes.m_83110_(f_53755_, f_53756_);
    public static final VoxelShape f_53758_ = Shapes.m_83110_(f_53757_, Block.m_49796_(0.0, 2.0, 4.0, 12.0, 14.0, 12.0));
    public static final VoxelShape f_53759_ = Block.m_49796_(0.0, 6.0, 2.0, 9.0, 10.0, 4.0);
    public static final VoxelShape f_53760_ = Block.m_49796_(0.0, 6.0, 12.0, 9.0, 10.0, 14.0);
    public static final VoxelShape f_53761_ = Block.m_49796_(7.0, 5.0, 2.0, 13.0, 11.0, 4.0);
    public static final VoxelShape f_53762_ = Block.m_49796_(7.0, 5.0, 12.0, 13.0, 11.0, 14.0);
    public static final VoxelShape f_53763_ = Shapes.m_83110_(f_53759_, f_53761_);
    public static final VoxelShape f_53764_ = Shapes.m_83110_(f_53760_, f_53762_);
    public static final VoxelShape f_53765_ = Shapes.m_83110_(f_53763_, f_53764_);
    public static final VoxelShape f_53766_ = Shapes.m_83110_(f_53765_, Block.m_49796_(4.0, 2.0, 4.0, 16.0, 14.0, 12.0));
    public static final VoxelShape f_53769_ = Block.m_49796_(2.0, 9.0, 6.0, 4.0, 16.0, 10.0);
    public static final VoxelShape f_53770_ = Block.m_49796_(12.0, 9.0, 6.0, 14.0, 16.0, 10.0);
    public static final VoxelShape f_53771_ = Block.m_49796_(2.0, 3.0, 5.0, 4.0, 9.0, 11.0);
    public static final VoxelShape f_53772_ = Block.m_49796_(12.0, 3.0, 5.0, 14.0, 9.0, 11.0);
    public static final VoxelShape f_53773_ = Shapes.m_83110_(f_53769_, f_53771_);
    public static final VoxelShape f_53774_ = Shapes.m_83110_(f_53770_, f_53772_);
    public static final VoxelShape f_53775_ = Shapes.m_83110_(f_53773_, f_53774_);
    public static final VoxelShape f_53776_ = Shapes.m_83110_(f_53775_, Block.m_49796_(4.0, 0.0, 2.0, 12.0, 12.0, 14.0));
    public static final VoxelShape f_53777_ = Block.m_49796_(6.0, 9.0, 2.0, 10.0, 16.0, 4.0);
    public static final VoxelShape f_53778_ = Block.m_49796_(6.0, 9.0, 12.0, 10.0, 16.0, 14.0);
    public static final VoxelShape f_53779_ = Block.m_49796_(5.0, 3.0, 2.0, 11.0, 9.0, 4.0);
    public static final VoxelShape f_53780_ = Block.m_49796_(5.0, 3.0, 12.0, 11.0, 9.0, 14.0);
    public static final VoxelShape f_53781_ = Shapes.m_83110_(f_53777_, f_53779_);
    public static final VoxelShape f_53782_ = Shapes.m_83110_(f_53778_, f_53780_);
    public static final VoxelShape f_53783_ = Shapes.m_83110_(f_53781_, f_53782_);
    public static final VoxelShape f_53784_ = Shapes.m_83110_(f_53783_, Block.m_49796_(2.0, 0.0, 4.0, 14.0, 12.0, 12.0));
    private static final Component f_53768_ = Component.m_237115_("container.grindstone_title");

    protected GrindstoneBlock(BlockBehaviour.Properties p_53808_) {
        super(p_53808_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_53179_, AttachFace.WALL));
    }

    @Override
    public RenderShape m_7514_(BlockState p_53840_) {
        return RenderShape.MODEL;
    }

    private VoxelShape m_53855_(BlockState p_53856_) {
        Direction $$1 = p_53856_.m_61143_(f_54117_);
        switch ((AttachFace)p_53856_.m_61143_(f_53179_)) {
            case FLOOR: {
                if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
                    return f_53791_;
                }
                return f_53799_;
            }
            case WALL: {
                if ($$1 == Direction.NORTH) {
                    return f_53750_;
                }
                if ($$1 == Direction.SOUTH) {
                    return f_53742_;
                }
                if ($$1 == Direction.EAST) {
                    return f_53766_;
                }
                return f_53758_;
            }
            case CEILING: {
                if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
                    return f_53776_;
                }
                return f_53784_;
            }
        }
        return f_53799_;
    }

    @Override
    public VoxelShape m_5939_(BlockState p_53851_, BlockGetter p_53852_, BlockPos p_53853_, CollisionContext p_53854_) {
        return this.m_53855_(p_53851_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53842_, BlockGetter p_53843_, BlockPos p_53844_, CollisionContext p_53845_) {
        return this.m_53855_(p_53842_);
    }

    @Override
    public boolean m_7898_(BlockState p_53828_, LevelReader p_53829_, BlockPos p_53830_) {
        return true;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_53821_, Level p_53822_, BlockPos p_53823_, Player p_53824_, InteractionHand p_53825_, BlockHitResult p_53826_) {
        if (p_53822_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_53824_.m_5893_(p_53821_.m_60750_(p_53822_, p_53823_));
        p_53824_.m_36220_(Stats.f_12952_);
        return InteractionResult.CONSUME;
    }

    @Override
    public MenuProvider m_7246_(BlockState p_53847_, Level p_53848_, BlockPos p_53849_) {
        return new SimpleMenuProvider((p_53812_, p_53813_, p_53814_) -> new GrindstoneMenu(p_53812_, p_53813_, ContainerLevelAccess.m_39289_(p_53848_, p_53849_)), f_53768_);
    }

    @Override
    public BlockState m_6843_(BlockState p_53835_, Rotation p_53836_) {
        return (BlockState)p_53835_.m_61124_(f_54117_, p_53836_.m_55954_(p_53835_.m_61143_(f_54117_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_53832_, Mirror p_53833_) {
        return p_53832_.m_60717_(p_53833_.m_54846_(p_53832_.m_61143_(f_54117_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53838_) {
        p_53838_.m_61104_(f_54117_, f_53179_);
    }

    @Override
    public boolean m_7357_(BlockState p_53816_, BlockGetter p_53817_, BlockPos p_53818_, PathComputationType p_53819_) {
        return false;
    }
}

