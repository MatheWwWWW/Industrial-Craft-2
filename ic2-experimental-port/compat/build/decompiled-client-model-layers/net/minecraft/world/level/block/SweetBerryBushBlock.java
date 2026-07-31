/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SweetBerryBushBlock
extends BushBlock
implements BonemealableBlock {
    private static final float f_154738_ = 0.003f;
    public static final int f_154737_ = 3;
    public static final IntegerProperty f_57244_ = BlockStateProperties.f_61407_;
    private static final VoxelShape f_57245_ = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private static final VoxelShape f_57246_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public SweetBerryBushBlock(BlockBehaviour.Properties p_57249_) {
        super(p_57249_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57244_, 0));
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_57256_, BlockPos p_57257_, BlockState p_57258_) {
        return new ItemStack(Items.f_42780_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57291_, BlockGetter p_57292_, BlockPos p_57293_, CollisionContext p_57294_) {
        if (p_57291_.m_61143_(f_57244_) == 0) {
            return f_57245_;
        }
        if (p_57291_.m_61143_(f_57244_) < 3) {
            return f_57246_;
        }
        return super.m_5940_(p_57291_, p_57292_, p_57293_, p_57294_);
    }

    @Override
    public boolean m_6724_(BlockState p_57284_) {
        return p_57284_.m_61143_(f_57244_) < 3;
    }

    @Override
    public void m_213898_(BlockState p_222563_, ServerLevel p_222564_, BlockPos p_222565_, RandomSource p_222566_) {
        int $$4 = p_222563_.m_61143_(f_57244_);
        if ($$4 < 3 && p_222566_.m_188503_(5) == 0 && p_222564_.m_45524_(p_222565_.m_7494_(), 0) >= 9) {
            BlockState $$5 = (BlockState)p_222563_.m_61124_(f_57244_, $$4 + 1);
            p_222564_.m_7731_(p_222565_, $$5, 2);
            p_222564_.m_220407_(GameEvent.f_157792_, p_222565_, GameEvent.Context.m_223722_($$5));
        }
    }

    @Override
    public void m_7892_(BlockState p_57270_, Level p_57271_, BlockPos p_57272_, Entity p_57273_) {
        if (!(p_57273_ instanceof LivingEntity) || p_57273_.m_6095_() == EntityType.f_20452_ || p_57273_.m_6095_() == EntityType.f_20550_) {
            return;
        }
        p_57273_.m_7601_(p_57270_, new Vec3(0.8f, 0.75, 0.8f));
        if (!(p_57271_.f_46443_ || p_57270_.m_61143_(f_57244_) <= 0 || p_57273_.f_19790_ == p_57273_.m_20185_() && p_57273_.f_19792_ == p_57273_.m_20189_())) {
            double $$4 = Math.abs(p_57273_.m_20185_() - p_57273_.f_19790_);
            double $$5 = Math.abs(p_57273_.m_20189_() - p_57273_.f_19792_);
            if ($$4 >= (double)0.003f || $$5 >= (double)0.003f) {
                p_57273_.m_6469_(DamageSource.f_19325_, 1.0f);
            }
        }
    }

    @Override
    public InteractionResult m_6227_(BlockState p_57275_, Level p_57276_, BlockPos p_57277_, Player p_57278_, InteractionHand p_57279_, BlockHitResult p_57280_) {
        boolean $$7;
        int $$6 = p_57275_.m_61143_(f_57244_);
        boolean bl = $$7 = $$6 == 3;
        if (!$$7 && p_57278_.m_21120_(p_57279_).m_150930_(Items.f_42499_)) {
            return InteractionResult.PASS;
        }
        if ($$6 > 1) {
            int $$8 = 1 + p_57276_.f_46441_.m_188503_(2);
            SweetBerryBushBlock.m_49840_(p_57276_, p_57277_, new ItemStack(Items.f_42780_, $$8 + ($$7 ? 1 : 0)));
            p_57276_.m_5594_(null, p_57277_, SoundEvents.f_12457_, SoundSource.BLOCKS, 1.0f, 0.8f + p_57276_.f_46441_.m_188501_() * 0.4f);
            BlockState $$9 = (BlockState)p_57275_.m_61124_(f_57244_, 1);
            p_57276_.m_7731_(p_57277_, $$9, 2);
            p_57276_.m_220407_(GameEvent.f_157792_, p_57277_, GameEvent.Context.m_223719_(p_57278_, $$9));
            return InteractionResult.m_19078_(p_57276_.f_46443_);
        }
        return super.m_6227_(p_57275_, p_57276_, p_57277_, p_57278_, p_57279_, p_57280_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57282_) {
        p_57282_.m_61104_(f_57244_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_57260_, BlockPos p_57261_, BlockState p_57262_, boolean p_57263_) {
        return p_57262_.m_61143_(f_57244_) < 3;
    }

    @Override
    public boolean m_214167_(Level p_222558_, RandomSource p_222559_, BlockPos p_222560_, BlockState p_222561_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222553_, RandomSource p_222554_, BlockPos p_222555_, BlockState p_222556_) {
        int $$4 = Math.min(3, p_222556_.m_61143_(f_57244_) + 1);
        p_222553_.m_7731_(p_222555_, (BlockState)p_222556_.m_61124_(f_57244_, $$4), 2);
    }
}

