/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;

public class MinecartItem
extends Item {
    private static final DispenseItemBehavior f_42934_ = new DefaultDispenseItemBehavior(){
        private final DefaultDispenseItemBehavior f_42944_ = new DefaultDispenseItemBehavior();

        /*
         * WARNING - void declaration
         */
        @Override
        public ItemStack m_7498_(BlockSource p_42949_, ItemStack p_42950_) {
            void $$16;
            RailShape $$9;
            Direction $$2 = p_42949_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            ServerLevel $$3 = p_42949_.m_7727_();
            double $$4 = p_42949_.m_7096_() + (double)$$2.m_122429_() * 1.125;
            double $$5 = Math.floor(p_42949_.m_7098_()) + (double)$$2.m_122430_();
            double $$6 = p_42949_.m_7094_() + (double)$$2.m_122431_() * 1.125;
            BlockPos $$7 = p_42949_.m_7961_().m_121945_($$2);
            BlockState $$8 = $$3.m_8055_($$7);
            RailShape railShape = $$9 = $$8.m_60734_() instanceof BaseRailBlock ? $$8.m_61143_(((BaseRailBlock)$$8.m_60734_()).m_7978_()) : RailShape.NORTH_SOUTH;
            if ($$8.m_204336_(BlockTags.f_13034_)) {
                if ($$9.m_61745_()) {
                    double $$10 = 0.6;
                } else {
                    double $$11 = 0.1;
                }
            } else if ($$8.m_60795_() && $$3.m_8055_($$7.m_7495_()).m_204336_(BlockTags.f_13034_)) {
                RailShape $$13;
                BlockState $$12 = $$3.m_8055_($$7.m_7495_());
                RailShape railShape2 = $$13 = $$12.m_60734_() instanceof BaseRailBlock ? $$12.m_61143_(((BaseRailBlock)$$12.m_60734_()).m_7978_()) : RailShape.NORTH_SOUTH;
                if ($$2 == Direction.DOWN || !$$13.m_61745_()) {
                    double $$14 = -0.9;
                } else {
                    double $$15 = -0.4;
                }
            } else {
                return this.f_42944_.m_6115_(p_42949_, p_42950_);
            }
            AbstractMinecart $$17 = AbstractMinecart.m_38119_($$3, $$4, $$5 + $$16, $$6, ((MinecartItem)p_42950_.m_41720_()).f_42935_);
            if (p_42950_.m_41788_()) {
                $$17.m_6593_(p_42950_.m_41786_());
            }
            $$3.m_7967_($$17);
            p_42950_.m_41774_(1);
            return p_42950_;
        }

        @Override
        protected void m_6823_(BlockSource p_42947_) {
            p_42947_.m_7727_().m_46796_(1000, p_42947_.m_7961_(), 0);
        }
    };
    final AbstractMinecart.Type f_42935_;

    public MinecartItem(AbstractMinecart.Type p_42938_, Item.Properties p_42939_) {
        super(p_42939_);
        this.f_42935_ = p_42938_;
        DispenserBlock.m_52672_(this, f_42934_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_42943_) {
        BlockPos $$2;
        Level $$1 = p_42943_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_42943_.m_8083_());
        if (!$$3.m_204336_(BlockTags.f_13034_)) {
            return InteractionResult.FAIL;
        }
        ItemStack $$4 = p_42943_.m_43722_();
        if (!$$1.f_46443_) {
            RailShape $$5 = $$3.m_60734_() instanceof BaseRailBlock ? $$3.m_61143_(((BaseRailBlock)$$3.m_60734_()).m_7978_()) : RailShape.NORTH_SOUTH;
            double $$6 = 0.0;
            if ($$5.m_61745_()) {
                $$6 = 0.5;
            }
            AbstractMinecart $$7 = AbstractMinecart.m_38119_($$1, (double)$$2.m_123341_() + 0.5, (double)$$2.m_123342_() + 0.0625 + $$6, (double)$$2.m_123343_() + 0.5, this.f_42935_);
            if ($$4.m_41788_()) {
                $$7.m_6593_($$4.m_41786_());
            }
            $$1.m_7967_($$7);
            $$1.m_220407_(GameEvent.f_157810_, $$2, GameEvent.Context.m_223719_(p_42943_.m_43723_(), $$1.m_8055_($$2.m_7495_())));
        }
        $$4.m_41774_(1);
        return InteractionResult.m_19078_($$1.f_46443_);
    }
}

