/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.tracker;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.item.tool.electric.NanoSaber;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NanosaberTracker
implements IAudioTracker {
    Player player;
    boolean hasActiveNanosaber = false;
    IAudioSource source;

    public NanosaberTracker(Player player) {
        this.player = player;
    }

    @Override
    public void onTick() {
        ItemStack mainhand = this.player.m_21205_();
        ItemStack offhand = this.player.m_21206_();
        if (!this.hasActiveNanosaber) {
            if (this.isActiveNanoSaber(mainhand) || this.isActiveNanoSaber(offhand)) {
                this.hasActiveNanosaber = true;
                this.source = IC2.AUDIO.createSource(this.player, NanoSaber.saberIdle, AudioManager.SoundType.ITEM, IC2.AUDIO.getVolumeForType(AudioManager.SoundType.ITEM), true, false);
                if (this.source != null) {
                    this.source.play();
                }
            }
        } else if (!this.isActiveNanoSaber(mainhand) && !this.isActiveNanoSaber(offhand)) {
            this.hasActiveNanosaber = false;
            if (this.source != null) {
                this.source.remove();
                this.source = null;
            }
        }
    }

    @Override
    public boolean isValid(Level world) {
        return this.player.m_20193_() == world && this.player.m_6084_();
    }

    private boolean isActiveNanoSaber(ItemStack stack) {
        return stack.m_41720_() instanceof NanoSaber && StackUtil.getNbtData(stack).m_128471_("active");
    }
}

