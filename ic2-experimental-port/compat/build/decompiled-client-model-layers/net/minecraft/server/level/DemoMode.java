/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DemoMode
extends ServerPlayerGameMode {
    public static final int f_143201_ = 5;
    public static final int f_143202_ = 120500;
    private boolean f_140734_;
    private boolean f_140735_;
    private int f_140736_;
    private int f_140737_;

    public DemoMode(ServerPlayer p_143204_) {
        super(p_143204_);
    }

    @Override
    public void m_7712_() {
        super.m_7712_();
        ++this.f_140737_;
        long $$0 = this.f_9244_.m_46467_();
        long $$1 = $$0 / 24000L + 1L;
        if (!this.f_140734_ && this.f_140737_ > 20) {
            this.f_140734_ = true;
            this.f_9245_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 0.0f));
        }
        boolean bl = this.f_140735_ = $$0 > 120500L;
        if (this.f_140735_) {
            ++this.f_140736_;
        }
        if ($$0 % 24000L == 500L) {
            if ($$1 <= 6L) {
                if ($$1 == 6L) {
                    this.f_9245_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 104.0f));
                } else {
                    this.f_9245_.m_213846_(Component.m_237115_("demo.day." + $$1));
                }
            }
        } else if ($$1 == 1L) {
            if ($$0 == 100L) {
                this.f_9245_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 101.0f));
            } else if ($$0 == 175L) {
                this.f_9245_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 102.0f));
            } else if ($$0 == 250L) {
                this.f_9245_.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 103.0f));
            }
        } else if ($$1 == 5L && $$0 % 24000L == 22000L) {
            this.f_9245_.m_213846_(Component.m_237115_("demo.day.warning"));
        }
    }

    private void m_140757_() {
        if (this.f_140736_ > 100) {
            this.f_9245_.m_213846_(Component.m_237115_("demo.reminder"));
            this.f_140736_ = 0;
        }
    }

    @Override
    public void m_214168_(BlockPos p_214976_, ServerboundPlayerActionPacket.Action p_214977_, Direction p_214978_, int p_214979_, int p_214980_) {
        if (this.f_140735_) {
            this.m_140757_();
            return;
        }
        super.m_214168_(p_214976_, p_214977_, p_214978_, p_214979_, p_214980_);
    }

    @Override
    public InteractionResult m_6261_(ServerPlayer p_140742_, Level p_140743_, ItemStack p_140744_, InteractionHand p_140745_) {
        if (this.f_140735_) {
            this.m_140757_();
            return InteractionResult.PASS;
        }
        return super.m_6261_(p_140742_, p_140743_, p_140744_, p_140745_);
    }

    @Override
    public InteractionResult m_7179_(ServerPlayer p_140747_, Level p_140748_, ItemStack p_140749_, InteractionHand p_140750_, BlockHitResult p_140751_) {
        if (this.f_140735_) {
            this.m_140757_();
            return InteractionResult.PASS;
        }
        return super.m_7179_(p_140747_, p_140748_, p_140749_, p_140750_, p_140751_);
    }
}

