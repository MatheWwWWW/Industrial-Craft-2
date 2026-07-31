/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BeehiveBlock
extends BaseEntityBlock {
    public static final DirectionProperty f_49563_ = HorizontalDirectionalBlock.f_54117_;
    public static final IntegerProperty f_49564_ = BlockStateProperties.f_61421_;
    public static final int f_152177_ = 5;
    private static final int f_152178_ = 3;

    public BeehiveBlock(BlockBehaviour.Properties p_49568_) {
        super(p_49568_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_49564_, 0)).m_61124_(f_49563_, Direction.NORTH));
    }

    @Override
    public boolean m_7278_(BlockState p_49618_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_49620_, Level p_49621_, BlockPos p_49622_) {
        return p_49620_.m_61143_(f_49564_);
    }

    @Override
    public void m_6240_(Level p_49584_, Player p_49585_, BlockPos p_49586_, BlockState p_49587_, @Nullable BlockEntity p_49588_, ItemStack p_49589_) {
        super.m_6240_(p_49584_, p_49585_, p_49586_, p_49587_, p_49588_, p_49589_);
        if (!p_49584_.f_46443_ && p_49588_ instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity $$6 = (BeehiveBlockEntity)p_49588_;
            if (EnchantmentHelper.m_44843_(Enchantments.f_44985_, p_49589_) == 0) {
                $$6.m_58748_(p_49585_, p_49587_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
                p_49584_.m_46717_(p_49586_, this);
                this.m_49649_(p_49584_, p_49586_);
            }
            CriteriaTriggers.f_10560_.m_146651_((ServerPlayer)p_49585_, p_49587_, p_49589_, $$6.m_58776_());
        }
    }

    private void m_49649_(Level p_49650_, BlockPos p_49651_) {
        List<Bee> $$2 = p_49650_.m_45976_(Bee.class, new AABB(p_49651_).m_82377_(8.0, 6.0, 8.0));
        if (!$$2.isEmpty()) {
            List<Player> $$3 = p_49650_.m_45976_(Player.class, new AABB(p_49651_).m_82377_(8.0, 6.0, 8.0));
            int $$4 = $$3.size();
            for (Bee $$5 : $$2) {
                if ($$5.m_5448_() != null) continue;
                $$5.m_6710_($$3.get(p_49650_.f_46441_.m_188503_($$4)));
            }
        }
    }

    public static void m_49600_(Level p_49601_, BlockPos p_49602_) {
        BeehiveBlock.m_49840_(p_49601_, p_49602_, new ItemStack(Items.f_42784_, 3));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_49624_, Level p_49625_, BlockPos p_49626_, Player p_49627_, InteractionHand p_49628_, BlockHitResult p_49629_) {
        ItemStack $$6 = p_49627_.m_21120_(p_49628_);
        int $$7 = p_49624_.m_61143_(f_49564_);
        boolean $$8 = false;
        if ($$7 >= 5) {
            Item $$9 = $$6.m_41720_();
            if ($$6.m_150930_(Items.f_42574_)) {
                p_49625_.m_6263_(p_49627_, p_49627_.m_20185_(), p_49627_.m_20186_(), p_49627_.m_20189_(), SoundEvents.f_11697_, SoundSource.NEUTRAL, 1.0f, 1.0f);
                BeehiveBlock.m_49600_(p_49625_, p_49626_);
                $$6.m_41622_(1, p_49627_, p_49571_ -> p_49571_.m_21190_(p_49628_));
                $$8 = true;
                p_49625_.m_142346_(p_49627_, GameEvent.f_157781_, p_49626_);
            } else if ($$6.m_150930_(Items.f_42590_)) {
                $$6.m_41774_(1);
                p_49625_.m_6263_(p_49627_, p_49627_.m_20185_(), p_49627_.m_20186_(), p_49627_.m_20189_(), SoundEvents.f_11770_, SoundSource.NEUTRAL, 1.0f, 1.0f);
                if ($$6.m_41619_()) {
                    p_49627_.m_21008_(p_49628_, new ItemStack(Items.f_42787_));
                } else if (!p_49627_.m_150109_().m_36054_(new ItemStack(Items.f_42787_))) {
                    p_49627_.m_36176_(new ItemStack(Items.f_42787_), false);
                }
                $$8 = true;
                p_49625_.m_142346_(p_49627_, GameEvent.f_157816_, p_49626_);
            }
            if (!p_49625_.m_5776_() && $$8) {
                p_49627_.m_36246_(Stats.f_12982_.m_12902_($$9));
            }
        }
        if ($$8) {
            if (!CampfireBlock.m_51248_(p_49625_, p_49626_)) {
                if (this.m_49654_(p_49625_, p_49626_)) {
                    this.m_49649_(p_49625_, p_49626_);
                }
                this.m_49594_(p_49625_, p_49624_, p_49626_, p_49627_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            } else {
                this.m_49590_(p_49625_, p_49624_, p_49626_);
            }
            return InteractionResult.m_19078_(p_49625_.f_46443_);
        }
        return super.m_6227_(p_49624_, p_49625_, p_49626_, p_49627_, p_49628_, p_49629_);
    }

    private boolean m_49654_(Level p_49655_, BlockPos p_49656_) {
        BlockEntity $$2 = p_49655_.m_7702_(p_49656_);
        if ($$2 instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity $$3 = (BeehiveBlockEntity)$$2;
            return !$$3.m_58774_();
        }
        return false;
    }

    public void m_49594_(Level p_49595_, BlockState p_49596_, BlockPos p_49597_, @Nullable Player p_49598_, BeehiveBlockEntity.BeeReleaseStatus p_49599_) {
        this.m_49590_(p_49595_, p_49596_, p_49597_);
        BlockEntity $$5 = p_49595_.m_7702_(p_49597_);
        if ($$5 instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity $$6 = (BeehiveBlockEntity)$$5;
            $$6.m_58748_(p_49598_, p_49596_, p_49599_);
        }
    }

    public void m_49590_(Level p_49591_, BlockState p_49592_, BlockPos p_49593_) {
        p_49591_.m_7731_(p_49593_, (BlockState)p_49592_.m_61124_(f_49564_, 0), 3);
    }

    @Override
    public void m_214162_(BlockState p_220773_, Level p_220774_, BlockPos p_220775_, RandomSource p_220776_) {
        if (p_220773_.m_61143_(f_49564_) >= 5) {
            for (int $$4 = 0; $$4 < p_220776_.m_188503_(1) + 1; ++$$4) {
                this.m_49603_(p_220774_, p_220775_, p_220773_);
            }
        }
    }

    private void m_49603_(Level p_49604_, BlockPos p_49605_, BlockState p_49606_) {
        if (!p_49606_.m_60819_().m_76178_() || p_49604_.f_46441_.m_188501_() < 0.3f) {
            return;
        }
        VoxelShape $$3 = p_49606_.m_60812_(p_49604_, p_49605_);
        double $$4 = $$3.m_83297_(Direction.Axis.Y);
        if ($$4 >= 1.0 && !p_49606_.m_204336_(BlockTags.f_13049_)) {
            double $$5 = $$3.m_83288_(Direction.Axis.Y);
            if ($$5 > 0.0) {
                this.m_49612_(p_49604_, p_49605_, $$3, (double)p_49605_.m_123342_() + $$5 - 0.05);
            } else {
                BlockPos $$6 = p_49605_.m_7495_();
                BlockState $$7 = p_49604_.m_8055_($$6);
                VoxelShape $$8 = $$7.m_60812_(p_49604_, $$6);
                double $$9 = $$8.m_83297_(Direction.Axis.Y);
                if (($$9 < 1.0 || !$$7.m_60838_(p_49604_, $$6)) && $$7.m_60819_().m_76178_()) {
                    this.m_49612_(p_49604_, p_49605_, $$3, (double)p_49605_.m_123342_() - 0.05);
                }
            }
        }
    }

    private void m_49612_(Level p_49613_, BlockPos p_49614_, VoxelShape p_49615_, double p_49616_) {
        this.m_49576_(p_49613_, (double)p_49614_.m_123341_() + p_49615_.m_83288_(Direction.Axis.X), (double)p_49614_.m_123341_() + p_49615_.m_83297_(Direction.Axis.X), (double)p_49614_.m_123343_() + p_49615_.m_83288_(Direction.Axis.Z), (double)p_49614_.m_123343_() + p_49615_.m_83297_(Direction.Axis.Z), p_49616_);
    }

    private void m_49576_(Level p_49577_, double p_49578_, double p_49579_, double p_49580_, double p_49581_, double p_49582_) {
        p_49577_.m_7106_(ParticleTypes.f_123779_, Mth.m_14139_(p_49577_.f_46441_.m_188500_(), p_49578_, p_49579_), p_49582_, Mth.m_14139_(p_49577_.f_46441_.m_188500_(), p_49580_, p_49581_), 0.0, 0.0, 0.0);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_49573_) {
        return (BlockState)this.m_49966_().m_61124_(f_49563_, p_49573_.m_8125_().m_122424_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49646_) {
        p_49646_.m_61104_(f_49564_, f_49563_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_49653_) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity m_142194_(BlockPos p_152184_, BlockState p_152185_) {
        return new BeehiveBlockEntity(p_152184_, p_152185_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
        return p_152180_.f_46443_ ? null : BeehiveBlock.m_152132_(p_152182_, BlockEntityType.f_58912_, BeehiveBlockEntity::m_155144_);
    }

    @Override
    public void m_5707_(Level p_49608_, BlockPos p_49609_, BlockState p_49610_, Player p_49611_) {
        BlockEntity $$4;
        if (!p_49608_.f_46443_ && p_49611_.m_7500_() && p_49608_.m_46469_().m_46207_(GameRules.f_46136_) && ($$4 = p_49608_.m_7702_(p_49609_)) instanceof BeehiveBlockEntity) {
            boolean $$8;
            BeehiveBlockEntity $$5 = (BeehiveBlockEntity)$$4;
            ItemStack $$6 = new ItemStack(this);
            int $$7 = p_49610_.m_61143_(f_49564_);
            boolean bl = $$8 = !$$5.m_58774_();
            if ($$8 || $$7 > 0) {
                if ($$8) {
                    CompoundTag $$9 = new CompoundTag();
                    $$9.m_128365_("Bees", $$5.m_58779_());
                    BlockItem.m_186338_($$6, BlockEntityType.f_58912_, $$9);
                }
                CompoundTag $$10 = new CompoundTag();
                $$10.m_128405_("honey_level", $$7);
                $$6.m_41700_("BlockStateTag", $$10);
                ItemEntity $$11 = new ItemEntity(p_49608_, p_49609_.m_123341_(), p_49609_.m_123342_(), p_49609_.m_123343_(), $$6);
                $$11.m_32060_();
                p_49608_.m_7967_($$11);
            }
        }
        super.m_5707_(p_49608_, p_49609_, p_49610_, p_49611_);
    }

    @Override
    public List<ItemStack> m_7381_(BlockState p_49636_, LootContext.Builder p_49637_) {
        BlockEntity $$3;
        Entity $$2 = p_49637_.m_78982_(LootContextParams.f_81455_);
        if (($$2 instanceof PrimedTnt || $$2 instanceof Creeper || $$2 instanceof WitherSkull || $$2 instanceof WitherBoss || $$2 instanceof MinecartTNT) && ($$3 = p_49637_.m_78982_(LootContextParams.f_81462_)) instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity $$4 = (BeehiveBlockEntity)$$3;
            $$4.m_58748_(null, p_49636_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
        }
        return super.m_7381_(p_49636_, p_49637_);
    }

    @Override
    public BlockState m_7417_(BlockState p_49639_, Direction p_49640_, BlockState p_49641_, LevelAccessor p_49642_, BlockPos p_49643_, BlockPos p_49644_) {
        BlockEntity $$6;
        if (p_49642_.m_8055_(p_49644_).m_60734_() instanceof FireBlock && ($$6 = p_49642_.m_7702_(p_49643_)) instanceof BeehiveBlockEntity) {
            BeehiveBlockEntity $$7 = (BeehiveBlockEntity)$$6;
            $$7.m_58748_(null, p_49639_, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
        }
        return super.m_7417_(p_49639_, p_49640_, p_49641_, p_49642_, p_49643_, p_49644_);
    }
}

