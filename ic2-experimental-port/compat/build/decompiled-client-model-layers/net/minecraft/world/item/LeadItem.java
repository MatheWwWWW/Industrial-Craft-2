/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class LeadItem
extends Item {
    public LeadItem(Item.Properties p_42828_) {
        super(p_42828_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_42834_) {
        BlockPos $$2;
        Level $$1 = p_42834_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_42834_.m_8083_());
        if ($$3.m_204336_(BlockTags.f_13039_)) {
            Player $$4 = p_42834_.m_43723_();
            if (!$$1.f_46443_ && $$4 != null) {
                LeadItem.m_42829_($$4, $$1, $$2);
            }
            $$1.m_220407_(GameEvent.f_157791_, $$2, GameEvent.Context.m_223717_($$4));
            return InteractionResult.m_19078_($$1.f_46443_);
        }
        return InteractionResult.PASS;
    }

    public static InteractionResult m_42829_(Player p_42830_, Level p_42831_, BlockPos p_42832_) {
        LeashFenceKnotEntity $$3 = null;
        boolean $$4 = false;
        double $$5 = 7.0;
        int $$6 = p_42832_.m_123341_();
        int $$7 = p_42832_.m_123342_();
        int $$8 = p_42832_.m_123343_();
        List<Mob> $$9 = p_42831_.m_45976_(Mob.class, new AABB((double)$$6 - 7.0, (double)$$7 - 7.0, (double)$$8 - 7.0, (double)$$6 + 7.0, (double)$$7 + 7.0, (double)$$8 + 7.0));
        for (Mob $$10 : $$9) {
            if ($$10.m_21524_() != p_42830_) continue;
            if ($$3 == null) {
                $$3 = LeashFenceKnotEntity.m_31844_(p_42831_, p_42832_);
                $$3.m_7084_();
            }
            $$10.m_21463_($$3, true);
            $$4 = true;
        }
        return $$4 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}

