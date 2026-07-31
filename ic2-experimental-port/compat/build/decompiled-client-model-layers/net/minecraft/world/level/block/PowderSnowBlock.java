/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PowderSnowBlock
extends Block
implements BucketPickup {
    private static final float f_154246_ = 0.083333336f;
    private static final float f_154247_ = 0.9f;
    private static final float f_154248_ = 1.5f;
    private static final float f_154249_ = 2.5f;
    private static final VoxelShape f_154250_ = Shapes.m_83048_(0.0, 0.0, 0.0, 1.0, 0.9f, 1.0);
    private static final double f_196692_ = 4.0;
    private static final double f_196693_ = 7.0;

    public PowderSnowBlock(BlockBehaviour.Properties p_154253_) {
        super(p_154253_);
    }

    @Override
    public boolean m_6104_(BlockState p_154268_, BlockState p_154269_, Direction p_154270_) {
        if (p_154269_.m_60713_(this)) {
            return true;
        }
        return super.m_6104_(p_154268_, p_154269_, p_154270_);
    }

    @Override
    public VoxelShape m_7952_(BlockState p_154272_, BlockGetter p_154273_, BlockPos p_154274_) {
        return Shapes.m_83040_();
    }

    @Override
    public void m_7892_(BlockState p_154263_, Level p_154264_, BlockPos p_154265_, Entity p_154266_) {
        if (!(p_154266_ instanceof LivingEntity) || p_154266_.m_146900_().m_60713_(this)) {
            p_154266_.m_7601_(p_154263_, new Vec3(0.9f, 1.5, 0.9f));
            if (p_154264_.f_46443_) {
                boolean $$5;
                RandomSource $$4 = p_154264_.m_213780_();
                boolean bl = $$5 = p_154266_.f_19790_ != p_154266_.m_20185_() || p_154266_.f_19792_ != p_154266_.m_20189_();
                if ($$5 && $$4.m_188499_()) {
                    p_154264_.m_7106_(ParticleTypes.f_175821_, p_154266_.m_20185_(), p_154265_.m_123342_() + 1, p_154266_.m_20189_(), Mth.m_216283_($$4, -1.0f, 1.0f) * 0.083333336f, 0.05f, Mth.m_216283_($$4, -1.0f, 1.0f) * 0.083333336f);
                }
            }
        }
        p_154266_.m_146924_(true);
        if (!p_154264_.f_46443_) {
            if (p_154266_.m_6060_() && (p_154264_.m_46469_().m_46207_(GameRules.f_46132_) || p_154266_ instanceof Player) && p_154266_.m_142265_(p_154264_, p_154265_)) {
                p_154264_.m_46961_(p_154265_, false);
            }
            p_154266_.m_146868_(false);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void m_142072_(Level p_196695_, BlockState p_196696_, BlockPos p_196697_, Entity p_196698_, float p_196699_) {
        void $$6;
        if ((double)p_196699_ < 4.0 || !(p_196698_ instanceof LivingEntity)) {
            return;
        }
        LivingEntity $$5 = (LivingEntity)p_196698_;
        LivingEntity.Fallsounds $$7 = $$6.m_196493_();
        SoundEvent $$8 = (double)p_196699_ < 7.0 ? $$7.f_196626_() : $$7.f_196627_();
        p_196698_.m_5496_($$8, 1.0f, 1.0f);
    }

    @Override
    public VoxelShape m_5939_(BlockState p_154285_, BlockGetter p_154286_, BlockPos p_154287_, CollisionContext p_154288_) {
        EntityCollisionContext $$4;
        Entity $$5;
        if (p_154288_ instanceof EntityCollisionContext && ($$5 = ($$4 = (EntityCollisionContext)p_154288_).m_193113_()) != null) {
            if ($$5.f_19789_ > 2.5f) {
                return f_154250_;
            }
            boolean $$6 = $$5 instanceof FallingBlockEntity;
            if ($$6 || PowderSnowBlock.m_154255_($$5) && p_154288_.m_6513_(Shapes.m_83144_(), p_154287_, false) && !p_154288_.m_6226_()) {
                return super.m_5939_(p_154285_, p_154286_, p_154287_, p_154288_);
            }
        }
        return Shapes.m_83040_();
    }

    @Override
    public VoxelShape m_5909_(BlockState p_154276_, BlockGetter p_154277_, BlockPos p_154278_, CollisionContext p_154279_) {
        return Shapes.m_83040_();
    }

    public static boolean m_154255_(Entity p_154256_) {
        if (p_154256_.m_6095_().m_204039_(EntityTypeTags.f_144291_)) {
            return true;
        }
        if (p_154256_ instanceof LivingEntity) {
            return ((LivingEntity)p_154256_).m_6844_(EquipmentSlot.FEET).m_150930_(Items.f_42463_);
        }
        return false;
    }

    @Override
    public ItemStack m_142598_(LevelAccessor p_154281_, BlockPos p_154282_, BlockState p_154283_) {
        p_154281_.m_7731_(p_154282_, Blocks.f_50016_.m_49966_(), 11);
        if (!p_154281_.m_5776_()) {
            p_154281_.m_46796_(2001, p_154282_, Block.m_49956_(p_154283_));
        }
        return new ItemStack(Items.f_151055_);
    }

    @Override
    public Optional<SoundEvent> m_142298_() {
        return Optional.of(SoundEvents.f_144089_);
    }

    @Override
    public boolean m_7357_(BlockState p_154258_, BlockGetter p_154259_, BlockPos p_154260_, PathComputationType p_154261_) {
        return true;
    }
}

