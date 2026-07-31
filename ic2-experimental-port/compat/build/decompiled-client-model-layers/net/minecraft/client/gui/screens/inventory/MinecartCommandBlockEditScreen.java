/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;

public class MinecartCommandBlockEditScreen
extends AbstractCommandBlockEditScreen {
    private final BaseCommandBlock f_99214_;

    public MinecartCommandBlockEditScreen(BaseCommandBlock p_99216_) {
        this.f_99214_ = p_99216_;
    }

    @Override
    public BaseCommandBlock m_6556_() {
        return this.f_99214_;
    }

    @Override
    int m_7821_() {
        return 150;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_97646_.m_94144_(this.m_6556_().m_45438_());
    }

    @Override
    protected void m_6372_(BaseCommandBlock p_99218_) {
        if (p_99218_ instanceof MinecartCommandBlock.MinecartCommandBase) {
            MinecartCommandBlock.MinecartCommandBase $$1 = (MinecartCommandBlock.MinecartCommandBase)p_99218_;
            this.f_96541_.m_91403_().m_104955_(new ServerboundSetCommandMinecartPacket($$1.m_38543_().m_19879_(), this.f_97646_.m_94155_(), p_99218_.m_45440_()));
        }
    }
}

