/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.tracker;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.item.tool.electric.ChainsawTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChainsawTracker
implements IAudioTracker {
    Player player;
    byte lastHeld = 0;
    IAudioSource source;
    Item item;

    public ChainsawTracker(Player player, Item item) {
        this.player = player;
        this.item = item;
    }

    @Override
    public void onTick() {
        if (this.lastHeld == 0) {
            ItemStack stack = this.player.m_21205_();
            if (stack.m_150930_(this.item) && this.hasPower(stack)) {
                this.lastHeld = 1;
                this.source = IC2.AUDIO.createSource(this.player, ChainsawTool.IDLE, AudioManager.SoundType.ITEM, 1.0f, true, false);
                if (this.source != null) {
                    this.source.play();
                }
                return;
            }
            stack = this.player.m_21206_();
            if (stack.m_150930_(this.item) && this.hasPower(stack)) {
                this.lastHeld = (byte)2;
                this.source = IC2.AUDIO.createSource(this.player, ChainsawTool.IDLE, AudioManager.SoundType.ITEM, 1.0f, true, false);
                if (this.source != null) {
                    this.source.play();
                }
            }
        } else {
            ItemStack stack = this.player.m_21120_(this.lastHeld == 1 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            if (!stack.m_150930_(this.item) || !this.hasPower(stack)) {
                this.lastHeld = 0;
                if (this.source != null) {
                    this.source.remove();
                    this.source = null;
                }
                IC2.AUDIO.playSound(this.player, ChainsawTool.STOP, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            }
            if (this.source != null && !this.source.isValid()) {
                this.source = null;
                this.lastHeld = 0;
            }
        }
    }

    @Override
    public boolean isValid(Level world) {
        return this.player.m_20193_() == world && this.player.m_6084_();
    }

    private boolean hasPower(ItemStack stack) {
        return ElectricItem.MANAGER.getCharge(stack) > 0;
    }
}

