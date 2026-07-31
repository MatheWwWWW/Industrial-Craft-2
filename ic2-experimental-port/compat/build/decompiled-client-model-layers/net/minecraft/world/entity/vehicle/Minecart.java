/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.vehicle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Minecart
extends AbstractMinecart {
    public Minecart(EntityType<?> p_38470_, Level p_38471_) {
        super(p_38470_, p_38471_);
    }

    public Minecart(Level p_38473_, double p_38474_, double p_38475_, double p_38476_) {
        super(EntityType.f_20469_, p_38473_, p_38474_, p_38475_, p_38476_);
    }

    @Override
    public InteractionResult m_6096_(Player p_38483_, InteractionHand p_38484_) {
        if (p_38483_.m_36341_()) {
            return InteractionResult.PASS;
        }
        if (this.m_20160_()) {
            return InteractionResult.PASS;
        }
        if (!this.f_19853_.f_46443_) {
            return p_38483_.m_20329_(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected Item m_213728_() {
        return Items.f_42449_;
    }

    @Override
    public void m_6025_(int p_38478_, int p_38479_, int p_38480_, boolean p_38481_) {
        if (p_38481_) {
            if (this.m_20160_()) {
                this.m_20153_();
            }
            if (this.m_38176_() == 0) {
                this.m_38160_(-this.m_38177_());
                this.m_38154_(10);
                this.m_38109_(50.0f);
                this.m_5834_();
            }
        }
    }

    @Override
    public AbstractMinecart.Type m_6064_() {
        return AbstractMinecart.Type.RIDEABLE;
    }
}

