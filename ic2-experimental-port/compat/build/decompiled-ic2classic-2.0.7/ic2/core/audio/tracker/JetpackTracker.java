/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.tracker;

import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.audio.tracker.IAudioTracker;
import ic2.core.item.wearable.base.IC2JetpackBase;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JetpackTracker
implements IAudioTracker {
    Item item = null;
    Player player;
    int index = 0;
    IC2JetpackBase.JetpackUseMode lastMode = IC2JetpackBase.JetpackUseMode.NONE;
    IAudioSource source;

    public JetpackTracker(Player player) {
        this.player = player;
    }

    @Override
    public void onTick() {
        if (this.index == 0) {
            ItemStack stack = this.player.m_6844_(EquipmentSlot.CHEST);
            IC2JetpackBase jetpack = this.getJetpack(stack);
            if (jetpack != null) {
                this.index = 1;
                this.item = jetpack;
                IC2JetpackBase.JetpackUseMode newMode = jetpack.getJetpackUse(stack);
                if (newMode != this.lastMode && newMode != IC2JetpackBase.JetpackUseMode.NONE) {
                    this.source = jetpack.createAudioSource(stack, this.player, newMode);
                    if (this.source != null) {
                        this.source.play();
                    }
                }
                this.lastMode = newMode;
            } else {
                stack = IC2.CURIO_PLUGIN.getJetpack(this.player);
                jetpack = this.getJetpack(stack);
                if (jetpack != null) {
                    this.index = 2;
                    this.item = jetpack;
                    IC2JetpackBase.JetpackUseMode newMode = jetpack.getJetpackUse(stack);
                    if (newMode != this.lastMode && newMode != IC2JetpackBase.JetpackUseMode.NONE) {
                        this.source = jetpack.createAudioSource(stack, this.player, newMode);
                        if (this.source != null) {
                            this.source.play();
                        }
                    }
                    this.lastMode = newMode;
                }
            }
        } else {
            IC2JetpackBase.JetpackUseMode currentMode;
            ItemStack stack = this.getInventoryStack();
            IC2JetpackBase jetpack = this.getJetpack(stack);
            if (jetpack != this.item) {
                this.index = 0;
                this.item = jetpack;
                this.lastMode = IC2JetpackBase.JetpackUseMode.NONE;
                if (this.source != null) {
                    this.source.remove();
                    this.source = null;
                }
                return;
            }
            if (this.source != null && !this.source.isValid()) {
                this.lastMode = IC2JetpackBase.JetpackUseMode.NONE;
            }
            if ((currentMode = jetpack.getJetpackUse(stack)) != this.lastMode) {
                this.lastMode = currentMode;
                if (this.source != null) {
                    this.source.remove();
                }
                if (this.lastMode == IC2JetpackBase.JetpackUseMode.NONE) {
                    return;
                }
                this.source = jetpack.createAudioSource(stack, this.player, this.lastMode);
                if (this.source != null) {
                    this.source.play();
                }
            }
        }
    }

    @Override
    public boolean isValid(Level world) {
        return this.player.f_19853_ == world && this.player.m_6084_();
    }

    public ItemStack getInventoryStack() {
        if (this.index == 0) {
            return ItemStack.f_41583_;
        }
        if (this.index == 1) {
            return this.player.m_6844_(EquipmentSlot.CHEST);
        }
        if (this.index == 2) {
            return IC2.CURIO_PLUGIN.getJetpack(this.player);
        }
        return ItemStack.f_41583_;
    }

    public IC2JetpackBase getJetpack(ItemStack stack) {
        if (stack.m_41720_() instanceof IC2JetpackBase.IIndirectJetpack) {
            return ((IC2JetpackBase.IIndirectJetpack)stack.m_41720_()).getJetpack(stack);
        }
        if (stack.m_41720_() instanceof IC2JetpackBase) {
            return (IC2JetpackBase)stack.m_41720_();
        }
        return null;
    }
}

