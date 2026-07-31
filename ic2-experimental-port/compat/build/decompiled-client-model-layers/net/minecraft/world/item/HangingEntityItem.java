/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class HangingEntityItem
extends Item {
    private final EntityType<? extends HangingEntity> f_41322_;

    public HangingEntityItem(EntityType<? extends HangingEntity> p_41324_, Item.Properties p_41325_) {
        super(p_41325_);
        this.f_41322_ = p_41324_;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public InteractionResult m_6225_(UseOnContext p_41331_) {
        void $$11;
        BlockPos $$1 = p_41331_.m_8083_();
        Direction $$2 = p_41331_.m_43719_();
        BlockPos $$3 = $$1.m_121945_($$2);
        Player $$4 = p_41331_.m_43723_();
        ItemStack $$5 = p_41331_.m_43722_();
        if ($$4 != null && !this.m_5595_($$4, $$2, $$5, $$3)) {
            return InteractionResult.FAIL;
        }
        Level $$6 = p_41331_.m_43725_();
        if (this.f_41322_ == EntityType.f_20506_) {
            Optional<Painting> $$7 = Painting.m_218887_($$6, $$3, $$2);
            if ($$7.isEmpty()) {
                return InteractionResult.CONSUME;
            }
            HangingEntity $$8 = $$7.get();
        } else if (this.f_41322_ == EntityType.f_20462_) {
            ItemFrame $$9 = new ItemFrame($$6, $$3, $$2);
        } else if (this.f_41322_ == EntityType.f_147033_) {
            GlowItemFrame $$10 = new GlowItemFrame($$6, $$3, $$2);
        } else {
            return InteractionResult.m_19078_($$6.f_46443_);
        }
        CompoundTag $$12 = $$5.m_41783_();
        if ($$12 != null) {
            EntityType.m_20620_($$6, $$4, (Entity)$$11, $$12);
        }
        if ($$11.m_7088_()) {
            if (!$$6.f_46443_) {
                $$11.m_7084_();
                $$6.m_220400_($$4, GameEvent.f_157810_, $$11.m_20182_());
                $$6.m_7967_((Entity)$$11);
            }
            $$5.m_41774_(1);
            return InteractionResult.m_19078_($$6.f_46443_);
        }
        return InteractionResult.CONSUME;
    }

    protected boolean m_5595_(Player p_41326_, Direction p_41327_, ItemStack p_41328_, BlockPos p_41329_) {
        return !p_41327_.m_122434_().m_122478_() && p_41326_.m_36204_(p_41329_, p_41327_, p_41328_);
    }
}

