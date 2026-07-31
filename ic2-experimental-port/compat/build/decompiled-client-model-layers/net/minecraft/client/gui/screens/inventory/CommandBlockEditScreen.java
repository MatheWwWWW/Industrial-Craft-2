/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;

public class CommandBlockEditScreen
extends AbstractCommandBlockEditScreen {
    private final CommandBlockEntity f_98374_;
    private CycleButton<CommandBlockEntity.Mode> f_98375_;
    private CycleButton<Boolean> f_98376_;
    private CycleButton<Boolean> f_98377_;
    private CommandBlockEntity.Mode f_98378_ = CommandBlockEntity.Mode.REDSTONE;
    private boolean f_98379_;
    private boolean f_98380_;

    public CommandBlockEditScreen(CommandBlockEntity p_98382_) {
        this.f_98374_ = p_98382_;
    }

    @Override
    BaseCommandBlock m_6556_() {
        return this.f_98374_.m_59141_();
    }

    @Override
    int m_7821_() {
        return 135;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_98375_ = this.m_142416_(CycleButton.m_168894_(p_169719_ -> {
            switch (p_169719_) {
                case SEQUENCE: {
                    return Component.m_237115_("advMode.mode.sequence");
                }
                case AUTO: {
                    return Component.m_237115_("advMode.mode.auto");
                }
            }
            return Component.m_237115_("advMode.mode.redstone");
        }).m_168961_((CommandBlockEntity.Mode[])CommandBlockEntity.Mode.values()).m_168929_().m_168948_(this.f_98378_).m_168936_(this.f_96543_ / 2 - 50 - 100 - 4, 165, 100, 20, Component.m_237115_("advMode.mode"), (p_169721_, p_169722_) -> {
            this.f_98378_ = p_169722_;
        }));
        this.f_98376_ = this.m_142416_(CycleButton.m_168896_(Component.m_237115_("advMode.mode.conditional"), Component.m_237115_("advMode.mode.unconditional")).m_168929_().m_168948_(this.f_98379_).m_168936_(this.f_96543_ / 2 - 50, 165, 100, 20, Component.m_237115_("advMode.type"), (p_169727_, p_169728_) -> {
            this.f_98379_ = p_169728_;
        }));
        this.f_98377_ = this.m_142416_(CycleButton.m_168896_(Component.m_237115_("advMode.mode.autoexec.bat"), Component.m_237115_("advMode.mode.redstoneTriggered")).m_168929_().m_168948_(this.f_98380_).m_168936_(this.f_96543_ / 2 + 50 + 4, 165, 100, 20, Component.m_237115_("advMode.triggering"), (p_169724_, p_169725_) -> {
            this.f_98380_ = p_169725_;
        }));
        this.m_169729_(false);
    }

    private void m_169729_(boolean p_169730_) {
        this.f_97648_.f_93623_ = p_169730_;
        this.f_97650_.f_93623_ = p_169730_;
        this.f_98375_.f_93623_ = p_169730_;
        this.f_98376_.f_93623_ = p_169730_;
        this.f_98377_.f_93623_ = p_169730_;
    }

    public void m_98398_() {
        BaseCommandBlock $$0 = this.f_98374_.m_59141_();
        this.f_97646_.m_94144_($$0.m_45438_());
        boolean $$1 = $$0.m_45440_();
        this.f_98378_ = this.f_98374_.m_59148_();
        this.f_98379_ = this.f_98374_.m_59151_();
        this.f_98380_ = this.f_98374_.m_59143_();
        this.f_97650_.m_168892_($$1);
        this.f_98375_.m_168892_(this.f_98378_);
        this.f_98376_.m_168892_(this.f_98379_);
        this.f_98377_.m_168892_(this.f_98380_);
        this.m_169598_($$1);
        this.m_169729_(true);
    }

    @Override
    public void m_6574_(Minecraft p_98386_, int p_98387_, int p_98388_) {
        super.m_6574_(p_98386_, p_98387_, p_98388_);
        this.m_169729_(true);
    }

    @Override
    protected void m_6372_(BaseCommandBlock p_98384_) {
        this.f_96541_.m_91403_().m_104955_(new ServerboundSetCommandBlockPacket(new BlockPos(p_98384_.m_6607_()), this.f_97646_.m_94155_(), this.f_98378_, p_98384_.m_45440_(), this.f_98379_, this.f_98380_));
    }
}

