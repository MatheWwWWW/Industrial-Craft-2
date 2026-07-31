/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.tileentity;

import ic2.core.IC2;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.sound.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityBase
extends TileEntityInventory {
    protected Sound loopingSound;
    protected Sound startSound;
    protected Sound stopSound;
    protected Sound interruptSound;

    public TileEntityBase(BlockEntityType<? extends TileEntityInventory> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.getActive() && this.isLoopingSoundIdling()) {
            this.loopingSound.play();
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.hasSound()) {
            IC2.soundManager.removeAllSound(this);
            this.clearSound();
        }
        super.onUnloaded();
    }

    @Override
    protected void onLoaded() {
        this.initSound();
        super.onLoaded();
    }

    public void setActiveState(boolean bl) {
        if (bl) {
            this.activate();
        } else {
            this.shutdown(false);
        }
    }

    public void activate() {
        if (this.getActive()) {
            return;
        }
        this.teBlock.setActive(this.f_58857_, this.f_58858_, this.m_58900_(), true);
        this.startPlaySound();
    }

    public void shutdown(boolean bl) {
        if (!this.getActive()) {
            return;
        }
        this.teBlock.setActive(this.f_58857_, this.f_58858_, this.m_58900_(), false);
        this.stopStartSound();
        this.stopLoopingSound();
        if (bl) {
            this.playInterruptSound();
        } else {
            this.playStopSound();
        }
    }

    public boolean startPlaySound() {
        if (this.startSound != null) {
            this.startSound.playOnce();
            return true;
        }
        this.playLoopingSound();
        return false;
    }

    public boolean playLoopingSound() {
        if (this.loopingSound != null) {
            this.loopingSound.play();
            return true;
        }
        return false;
    }

    public boolean stopLoopingSound() {
        if (this.loopingSound != null) {
            this.loopingSound.stop();
            return true;
        }
        return false;
    }

    public boolean stopStartSound() {
        if (this.startSound != null) {
            this.startSound.stop();
            return true;
        }
        return false;
    }

    public boolean playStopSound() {
        if (this.stopSound != null) {
            this.stopSound.playOnce();
            return true;
        }
        return false;
    }

    public boolean playInterruptSound() {
        if (this.interruptSound != null) {
            this.interruptSound.playOnce();
            return true;
        }
        return false;
    }

    protected boolean hasSound() {
        return this.startSound != null || this.loopingSound != null || this.stopSound != null || this.interruptSound != null;
    }

    protected void initSound() {
        SoundEvent soundEvent = this.getStartSoundEvent();
        SoundEvent soundEvent2 = this.getStopSoundEvent();
        SoundEvent soundEvent3 = this.getLoopingSoundEvent();
        SoundEvent soundEvent4 = this.getInterruptSoundEvent();
        if (soundEvent2 != null && this.stopSound == null) {
            this.stopSound = IC2.soundManager.createSound((Object)this, soundEvent2, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
        }
        if (soundEvent3 != null && this.loopingSound == null) {
            this.loopingSound = IC2.soundManager.createSound((Object)this, soundEvent3, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
        }
        if (soundEvent4 != null && this.interruptSound == null) {
            this.interruptSound = IC2.soundManager.createSound((Object)this, soundEvent4, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
        }
        if (soundEvent != null && this.startSound == null) {
            this.startSound = IC2.soundManager.createSound((Object)this, soundEvent, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
            if (this.loopingSound != null) {
                this.startSound.onFinish(this.loopingSound::play);
            }
        }
    }

    protected void clearSound() {
        this.startSound = null;
        this.loopingSound = null;
        this.stopSound = null;
        this.interruptSound = null;
    }

    protected boolean isLoopingSoundPlaying() {
        return this.loopingSound != null && this.loopingSound.isPlaying();
    }

    protected boolean isLoopingSoundIdling() {
        return this.loopingSound != null && !this.loopingSound.isPlaying();
    }

    public SoundEvent getStartSoundEvent() {
        return null;
    }

    public SoundEvent getLoopingSoundEvent() {
        return null;
    }

    public SoundEvent getStopSoundEvent() {
        return null;
    }

    public SoundEvent getInterruptSoundEvent() {
        return null;
    }
}

