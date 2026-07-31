/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.tracker;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.item.base.features.ISoundBattery;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BatteryTracker
implements IAudioTracker {
    Player player;
    InteractionHand activeHand;
    IAudioSource source;

    public BatteryTracker(Player player) {
        this.player = player;
    }

    @Override
    public void onTick() {
        boolean usesItem = this.player.m_6117_();
        if (usesItem == (this.activeHand == null)) {
            if (!usesItem) {
                this.activeHand = null;
                if (this.source != null) {
                    this.source.remove();
                    this.source = null;
                }
            } else {
                ItemStack stack;
                this.player.m_7655_();
                this.activeHand = this.player.m_7655_();
                if (this.source != null) {
                    this.source.remove();
                    this.source = null;
                }
                if ((stack = this.player.m_21120_(this.activeHand)).m_41720_() instanceof ISoundBattery) {
                    ISoundBattery battery = (ISoundBattery)stack.m_41720_();
                    if (!battery.wantsToPlay(stack)) {
                        return;
                    }
                    this.source = IC2.AUDIO.createSource(this.player, battery.getSound(stack), AudioManager.SoundType.ITEM, 4.0f, true, true);
                    if (this.source != null) {
                        this.source.play();
                    }
                }
            }
        }
    }

    @Override
    public boolean isValid(Level world) {
        return this.player.m_6084_() && this.player.m_20193_() == world;
    }
}

