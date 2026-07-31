/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleColumnAmbientSoundHandler
implements AmbientSoundHandler {
    private final LocalPlayer f_119662_;
    private boolean f_119663_;
    private boolean f_119664_ = true;

    public BubbleColumnAmbientSoundHandler(LocalPlayer p_119666_) {
        this.f_119662_ = p_119666_;
    }

    @Override
    public void m_7551_() {
        Level $$0 = this.f_119662_.f_19853_;
        BlockState $$1 = $$0.m_46847_(this.f_119662_.m_20191_().m_82377_(0.0, -0.4f, 0.0).m_82406_(1.0E-6)).filter(p_119669_ -> p_119669_.m_60713_(Blocks.f_50628_)).findFirst().orElse(null);
        if ($$1 != null) {
            if (!this.f_119663_ && !this.f_119664_ && $$1.m_60713_(Blocks.f_50628_) && !this.f_119662_.m_5833_()) {
                boolean $$2 = $$1.m_61143_(BubbleColumnBlock.f_50956_);
                if ($$2) {
                    this.f_119662_.m_5496_(SoundEvents.f_11777_, 1.0f, 1.0f);
                } else {
                    this.f_119662_.m_5496_(SoundEvents.f_11775_, 1.0f, 1.0f);
                }
            }
            this.f_119663_ = true;
        } else {
            this.f_119663_ = false;
        }
        this.f_119664_ = false;
    }
}

