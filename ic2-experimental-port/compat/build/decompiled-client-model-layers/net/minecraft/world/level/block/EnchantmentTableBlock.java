/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantmentTableBlock
extends BaseEntityBlock {
    protected static final VoxelShape f_52950_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final List<BlockPos> f_207902_ = BlockPos.m_121886_(-2, 0, -2, 2, 1, 2).filter(p_207914_ -> Math.abs(p_207914_.m_123341_()) == 2 || Math.abs(p_207914_.m_123343_()) == 2).map(BlockPos::m_7949_).toList();

    protected EnchantmentTableBlock(BlockBehaviour.Properties p_52953_) {
        super(p_52953_);
    }

    public static boolean m_207909_(Level p_207910_, BlockPos p_207911_, BlockPos p_207912_) {
        return p_207910_.m_8055_(p_207911_.m_121955_(p_207912_)).m_60713_(Blocks.f_50078_) && p_207910_.m_46859_(p_207911_.m_7918_(p_207912_.m_123341_() / 2, p_207912_.m_123342_(), p_207912_.m_123343_() / 2));
    }

    @Override
    public boolean m_7923_(BlockState p_52997_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52988_, BlockGetter p_52989_, BlockPos p_52990_, CollisionContext p_52991_) {
        return f_52950_;
    }

    @Override
    public void m_214162_(BlockState p_221092_, Level p_221093_, BlockPos p_221094_, RandomSource p_221095_) {
        super.m_214162_(p_221092_, p_221093_, p_221094_, p_221095_);
        for (BlockPos $$4 : f_207902_) {
            if (p_221095_.m_188503_(16) != 0 || !EnchantmentTableBlock.m_207909_(p_221093_, p_221094_, $$4)) continue;
            p_221093_.m_7106_(ParticleTypes.f_123809_, (double)p_221094_.m_123341_() + 0.5, (double)p_221094_.m_123342_() + 2.0, (double)p_221094_.m_123343_() + 0.5, (double)((float)$$4.m_123341_() + p_221095_.m_188501_()) - 0.5, (float)$$4.m_123342_() - p_221095_.m_188501_() - 1.0f, (double)((float)$$4.m_123343_() + p_221095_.m_188501_()) - 0.5);
        }
    }

    @Override
    public RenderShape m_7514_(BlockState p_52986_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153186_, BlockState p_153187_) {
        return new EnchantmentTableBlockEntity(p_153186_, p_153187_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153182_, BlockState p_153183_, BlockEntityType<T> p_153184_) {
        return p_153182_.f_46443_ ? EnchantmentTableBlock.m_152132_(p_153184_, BlockEntityType.f_58928_, EnchantmentTableBlockEntity::m_155503_) : null;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52974_, Level p_52975_, BlockPos p_52976_, Player p_52977_, InteractionHand p_52978_, BlockHitResult p_52979_) {
        if (p_52975_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        p_52977_.m_5893_(p_52974_.m_60750_(p_52975_, p_52976_));
        return InteractionResult.CONSUME;
    }

    @Override
    @Nullable
    public MenuProvider m_7246_(BlockState p_52993_, Level p_52994_, BlockPos p_52995_) {
        BlockEntity $$3 = p_52994_.m_7702_(p_52995_);
        if ($$3 instanceof EnchantmentTableBlockEntity) {
            Component $$4 = ((Nameable)((Object)$$3)).m_5446_();
            return new SimpleMenuProvider((p_207906_, p_207907_, p_207908_) -> new EnchantmentMenu(p_207906_, p_207907_, ContainerLevelAccess.m_39289_(p_52994_, p_52995_)), $$4);
        }
        return null;
    }

    @Override
    public void m_6402_(Level p_52963_, BlockPos p_52964_, BlockState p_52965_, LivingEntity p_52966_, ItemStack p_52967_) {
        BlockEntity $$5;
        if (p_52967_.m_41788_() && ($$5 = p_52963_.m_7702_(p_52964_)) instanceof EnchantmentTableBlockEntity) {
            ((EnchantmentTableBlockEntity)$$5).m_59272_(p_52967_.m_41786_());
        }
    }

    @Override
    public boolean m_7357_(BlockState p_52969_, BlockGetter p_52970_, BlockPos p_52971_, PathComputationType p_52972_) {
        return false;
    }
}

