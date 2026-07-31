/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShulkerBoxBlock
extends BaseEntityBlock {
    public static final EnumProperty<Direction> f_56183_ = DirectionalBlock.f_52588_;
    public static final ResourceLocation f_56184_ = new ResourceLocation("contents");
    @Nullable
    private final DyeColor f_56185_;

    public ShulkerBoxBlock(@Nullable DyeColor p_56188_, BlockBehaviour.Properties p_56189_) {
        super(p_56189_);
        this.f_56185_ = p_56188_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_56183_, Direction.UP));
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_154552_, BlockState p_154553_) {
        return new ShulkerBoxBlockEntity(this.f_56185_, p_154552_, p_154553_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154543_, BlockState p_154544_, BlockEntityType<T> p_154545_) {
        return ShulkerBoxBlock.m_152132_(p_154545_, BlockEntityType.f_58939_, ShulkerBoxBlockEntity::m_155672_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_56255_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_56227_, Level p_56228_, BlockPos p_56229_, Player p_56230_, InteractionHand p_56231_, BlockHitResult p_56232_) {
        if (p_56228_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        if (p_56230_.m_5833_()) {
            return InteractionResult.CONSUME;
        }
        BlockEntity $$6 = p_56228_.m_7702_(p_56229_);
        if ($$6 instanceof ShulkerBoxBlockEntity) {
            ShulkerBoxBlockEntity $$7 = (ShulkerBoxBlockEntity)$$6;
            if (ShulkerBoxBlock.m_154546_(p_56227_, p_56228_, p_56229_, $$7)) {
                p_56230_.m_5893_($$7);
                p_56230_.m_36220_(Stats.f_12970_);
                PiglinAi.m_34873_(p_56230_, true);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private static boolean m_154546_(BlockState p_154547_, Level p_154548_, BlockPos p_154549_, ShulkerBoxBlockEntity p_154550_) {
        if (p_154550_.m_59700_() != ShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
            return true;
        }
        AABB $$4 = Shulker.m_149793_(p_154547_.m_61143_(f_56183_), 0.0f, 0.5f).m_82338_(p_154549_).m_82406_(1.0E-6);
        return p_154548_.m_45772_($$4);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_56198_) {
        return (BlockState)this.m_49966_().m_61124_(f_56183_, p_56198_.m_43719_());
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56249_) {
        p_56249_.m_61104_(f_56183_);
    }

    @Override
    public void m_5707_(Level p_56212_, BlockPos p_56213_, BlockState p_56214_, Player p_56215_) {
        BlockEntity $$4 = p_56212_.m_7702_(p_56213_);
        if ($$4 instanceof ShulkerBoxBlockEntity) {
            ShulkerBoxBlockEntity $$5 = (ShulkerBoxBlockEntity)$$4;
            if (!p_56212_.f_46443_ && p_56215_.m_7500_() && !$$5.m_7983_()) {
                ItemStack $$6 = ShulkerBoxBlock.m_56250_(this.m_56261_());
                $$4.m_187476_($$6);
                if ($$5.m_8077_()) {
                    $$6.m_41714_($$5.m_7770_());
                }
                ItemEntity $$7 = new ItemEntity(p_56212_, (double)p_56213_.m_123341_() + 0.5, (double)p_56213_.m_123342_() + 0.5, (double)p_56213_.m_123343_() + 0.5, $$6);
                $$7.m_32060_();
                p_56212_.m_7967_($$7);
            } else {
                $$5.m_59640_(p_56215_);
            }
        }
        super.m_5707_(p_56212_, p_56213_, p_56214_, p_56215_);
    }

    @Override
    public List<ItemStack> m_7381_(BlockState p_56246_, LootContext.Builder p_56247_) {
        BlockEntity $$2 = p_56247_.m_78982_(LootContextParams.f_81462_);
        if ($$2 instanceof ShulkerBoxBlockEntity) {
            ShulkerBoxBlockEntity $$3 = (ShulkerBoxBlockEntity)$$2;
            p_56247_ = p_56247_.m_78979_(f_56184_, (p_56218_, p_56219_) -> {
                for (int $$3 = 0; $$3 < $$3.m_6643_(); ++$$3) {
                    p_56219_.accept($$3.m_8020_($$3));
                }
            });
        }
        return super.m_7381_(p_56246_, p_56247_);
    }

    @Override
    public void m_6402_(Level p_56206_, BlockPos p_56207_, BlockState p_56208_, LivingEntity p_56209_, ItemStack p_56210_) {
        BlockEntity $$5;
        if (p_56210_.m_41788_() && ($$5 = p_56206_.m_7702_(p_56207_)) instanceof ShulkerBoxBlockEntity) {
            ((ShulkerBoxBlockEntity)$$5).m_58638_(p_56210_.m_41786_());
        }
    }

    @Override
    public void m_6810_(BlockState p_56234_, Level p_56235_, BlockPos p_56236_, BlockState p_56237_, boolean p_56238_) {
        if (p_56234_.m_60713_(p_56237_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_56235_.m_7702_(p_56236_);
        if ($$5 instanceof ShulkerBoxBlockEntity) {
            p_56235_.m_46717_(p_56236_, p_56234_.m_60734_());
        }
        super.m_6810_(p_56234_, p_56235_, p_56236_, p_56237_, p_56238_);
    }

    @Override
    public void m_5871_(ItemStack p_56193_, @Nullable BlockGetter p_56194_, List<Component> p_56195_, TooltipFlag p_56196_) {
        super.m_5871_(p_56193_, p_56194_, p_56195_, p_56196_);
        CompoundTag $$4 = BlockItem.m_186336_(p_56193_);
        if ($$4 != null) {
            if ($$4.m_128425_("LootTable", 8)) {
                p_56195_.add(Component.m_237113_("???????"));
            }
            if ($$4.m_128425_("Items", 9)) {
                NonNullList<ItemStack> $$5 = NonNullList.m_122780_(27, ItemStack.f_41583_);
                ContainerHelper.m_18980_($$4, $$5);
                int $$6 = 0;
                int $$7 = 0;
                for (ItemStack $$8 : $$5) {
                    if ($$8.m_41619_()) continue;
                    ++$$7;
                    if ($$6 > 4) continue;
                    ++$$6;
                    MutableComponent $$9 = $$8.m_41786_().m_6881_();
                    $$9.m_130946_(" x").m_130946_(String.valueOf($$8.m_41613_()));
                    p_56195_.add($$9);
                }
                if ($$7 - $$6 > 0) {
                    p_56195_.add(Component.m_237110_("container.shulkerBox.more", $$7 - $$6).m_130940_(ChatFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public PushReaction m_5537_(BlockState p_56265_) {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_56257_, BlockGetter p_56258_, BlockPos p_56259_, CollisionContext p_56260_) {
        BlockEntity $$4 = p_56258_.m_7702_(p_56259_);
        if ($$4 instanceof ShulkerBoxBlockEntity) {
            return Shapes.m_83064_(((ShulkerBoxBlockEntity)$$4).m_59666_(p_56257_));
        }
        return Shapes.m_83144_();
    }

    @Override
    public boolean m_7278_(BlockState p_56221_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_56223_, Level p_56224_, BlockPos p_56225_) {
        return AbstractContainerMenu.m_38938_((Container)((Object)p_56224_.m_7702_(p_56225_)));
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_56202_, BlockPos p_56203_, BlockState p_56204_) {
        ItemStack $$3 = super.m_7397_(p_56202_, p_56203_, p_56204_);
        p_56202_.m_141902_(p_56203_, BlockEntityType.f_58939_).ifPresent(p_187446_ -> p_187446_.m_187476_($$3));
        return $$3;
    }

    @Nullable
    public static DyeColor m_56252_(Item p_56253_) {
        return ShulkerBoxBlock.m_56262_(Block.m_49814_(p_56253_));
    }

    @Nullable
    public static DyeColor m_56262_(Block p_56263_) {
        if (p_56263_ instanceof ShulkerBoxBlock) {
            return ((ShulkerBoxBlock)p_56263_).m_56261_();
        }
        return null;
    }

    public static Block m_56190_(@Nullable DyeColor p_56191_) {
        if (p_56191_ == null) {
            return Blocks.f_50456_;
        }
        switch (p_56191_) {
            case WHITE: {
                return Blocks.f_50457_;
            }
            case ORANGE: {
                return Blocks.f_50458_;
            }
            case MAGENTA: {
                return Blocks.f_50459_;
            }
            case LIGHT_BLUE: {
                return Blocks.f_50460_;
            }
            case YELLOW: {
                return Blocks.f_50461_;
            }
            case LIME: {
                return Blocks.f_50462_;
            }
            case PINK: {
                return Blocks.f_50463_;
            }
            case GRAY: {
                return Blocks.f_50464_;
            }
            case LIGHT_GRAY: {
                return Blocks.f_50465_;
            }
            case CYAN: {
                return Blocks.f_50466_;
            }
            default: {
                return Blocks.f_50520_;
            }
            case BLUE: {
                return Blocks.f_50521_;
            }
            case BROWN: {
                return Blocks.f_50522_;
            }
            case GREEN: {
                return Blocks.f_50523_;
            }
            case RED: {
                return Blocks.f_50524_;
            }
            case BLACK: 
        }
        return Blocks.f_50525_;
    }

    @Nullable
    public DyeColor m_56261_() {
        return this.f_56185_;
    }

    public static ItemStack m_56250_(@Nullable DyeColor p_56251_) {
        return new ItemStack(ShulkerBoxBlock.m_56190_(p_56251_));
    }

    @Override
    public BlockState m_6843_(BlockState p_56243_, Rotation p_56244_) {
        return (BlockState)p_56243_.m_61124_(f_56183_, p_56244_.m_55954_(p_56243_.m_61143_(f_56183_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_56240_, Mirror p_56241_) {
        return p_56240_.m_60717_(p_56241_.m_54846_(p_56240_.m_61143_(f_56183_)));
    }
}

