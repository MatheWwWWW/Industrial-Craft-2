/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class SignBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_56268_ = BlockStateProperties.f_61362_;
    protected static final float f_154554_ = 4.0f;
    protected static final VoxelShape f_56269_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private final WoodType f_56270_;

    protected SignBlock(BlockBehaviour.Properties p_56273_, WoodType p_56274_) {
        super(p_56273_);
        this.f_56270_ = p_56274_;
    }

    @Override
    public BlockState m_7417_(BlockState p_56285_, Direction p_56286_, BlockState p_56287_, LevelAccessor p_56288_, BlockPos p_56289_, BlockPos p_56290_) {
        if (p_56285_.m_61143_(f_56268_).booleanValue()) {
            p_56288_.m_186469_(p_56289_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56288_));
        }
        return super.m_7417_(p_56285_, p_56286_, p_56287_, p_56288_, p_56289_, p_56290_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56293_, BlockGetter p_56294_, BlockPos p_56295_, CollisionContext p_56296_) {
        return f_56269_;
    }

    @Override
    public boolean m_5568_() {
        return true;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_154556_, BlockState p_154557_) {
        return new SignBlockEntity(p_154556_, p_154557_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_56278_, Level p_56279_, BlockPos p_56280_, Player p_56281_, InteractionHand p_56282_, BlockHitResult p_56283_) {
        boolean $$11;
        ItemStack $$6 = p_56281_.m_21120_(p_56282_);
        Item $$7 = $$6.m_41720_();
        boolean $$8 = $$7 instanceof DyeItem;
        boolean $$9 = $$6.m_150930_(Items.f_151056_);
        boolean $$10 = $$6.m_150930_(Items.f_42532_);
        boolean bl = $$11 = ($$9 || $$8 || $$10) && p_56281_.m_150110_().f_35938_;
        if (p_56279_.f_46443_) {
            return $$11 ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
        BlockEntity $$12 = p_56279_.m_7702_(p_56280_);
        if ($$12 instanceof SignBlockEntity) {
            SignBlockEntity $$13 = (SignBlockEntity)$$12;
            boolean $$14 = $$13.m_155727_();
            if ($$9 && $$14 || $$10 && !$$14) {
                return InteractionResult.PASS;
            }
            if ($$11) {
                boolean $$17;
                if ($$9) {
                    p_56279_.m_5594_(null, p_56280_, SoundEvents.f_144153_, SoundSource.BLOCKS, 1.0f, 1.0f);
                    boolean $$15 = $$13.m_155722_(true);
                    if (p_56281_ instanceof ServerPlayer) {
                        CriteriaTriggers.f_10562_.m_220040_((ServerPlayer)p_56281_, p_56280_, $$6);
                    }
                } else if ($$10) {
                    p_56279_.m_5594_(null, p_56280_, SoundEvents.f_144181_, SoundSource.BLOCKS, 1.0f, 1.0f);
                    boolean $$16 = $$13.m_155722_(false);
                } else {
                    p_56279_.m_5594_(null, p_56280_, SoundEvents.f_144133_, SoundSource.BLOCKS, 1.0f, 1.0f);
                    $$17 = $$13.m_59739_(((DyeItem)$$7).m_41089_());
                }
                if ($$17) {
                    if (!p_56281_.m_7500_()) {
                        $$6.m_41774_(1);
                    }
                    p_56281_.m_36246_(Stats.f_12982_.m_12902_($$7));
                }
            }
            return $$13.m_155709_((ServerPlayer)p_56281_) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public FluidState m_5888_(BlockState p_56299_) {
        if (p_56299_.m_61143_(f_56268_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_56299_);
    }

    public WoodType m_56297_() {
        return this.f_56270_;
    }
}

