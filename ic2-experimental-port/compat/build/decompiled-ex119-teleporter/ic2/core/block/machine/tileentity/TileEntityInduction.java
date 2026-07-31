/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.audio.SingleSound;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableSmelting;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityInduction
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IGuiValueProvider,
INetworkTileEntityEventListener {
    private static final short maxHeat = 10000;
    public final InvSlotProcessableSmelting inputSlotA = new InvSlotProcessableSmelting(this, "inputA", 1);
    public final InvSlotProcessableSmelting inputSlotB = new InvSlotProcessableSmelting(this, "inputB", 1);
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotOutput outputSlotA = new InvSlotOutput(this, "outputA", 1);
    public final InvSlotOutput outputSlotB = new InvSlotOutput(this, "outputB", 1);
    protected final Redstone redstone;
    protected AudioSource audioSource;
    protected SingleSound startingSound;
    protected SingleSound finishingSound;
    @GuiSynced
    public short heat = 0;
    @GuiSynced
    public short progress = 0;

    public TileEntityInduction(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.INDUCTION_FURNACE, blockPos, blockState, 10000, 2);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
        this.redstone = this.addComponent(new Redstone(this));
        this.comparator.setUpdate(() -> this.heat * 15 / 10000);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.heat = compoundTag.m_128448_("heat");
        this.progress = compoundTag.m_128448_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128376_("heat", this.heat);
        compoundTag.m_128376_("progress", this.progress);
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
        if (IC2.sideProxy.isRendering()) {
            if (this.startingSound != null) {
                if (!this.startingSound.isComplete()) {
                    this.startingSound.cancel();
                }
                this.startingSound = null;
            }
            if (this.finishingSound != null) {
                this.finishingSound.remove();
                this.finishingSound = null;
            }
            if (this.audioSource != null) {
                IC2.audioManager.removeSources(this);
                this.audioSource = null;
            }
        }
    }

    @Override
    protected void updateEntityServer() {
        boolean bl;
        super.updateEntityServer();
        boolean bl2 = false;
        boolean bl3 = this.getActive();
        if (this.heat == 0) {
            bl3 = false;
        }
        if (this.progress >= 4000) {
            this.operate();
            bl2 = true;
            this.progress = 0;
            bl3 = false;
        }
        if (((bl = this.canOperate()) || this.redstone.hasRedstoneInput()) && this.energy.useEnergy(1.0)) {
            if (this.heat < 10000) {
                this.heat = (short)(this.heat + 1);
            }
            bl3 = true;
        } else {
            this.heat = (short)(this.heat - Math.min(this.heat, 4));
        }
        if (!bl3 || this.progress == 0) {
            if (bl) {
                if (this.energy.getEnergy() >= 15.0) {
                    bl3 = true;
                    IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
                }
            } else {
                if (bl2) {
                    IC2.network.get(true).initiateTileEntityEvent(this, 3, true);
                }
                this.progress = 0;
            }
        } else if (!bl || this.energy.getEnergy() < 15.0) {
            if (!bl) {
                this.progress = 0;
            }
            bl3 = false;
            IC2.network.get(true).initiateTileEntityEvent(this, 1, true);
        }
        if (bl3 && bl) {
            this.progress = (short)(this.progress + this.heat / 30);
            this.energy.useEnergy(15.0);
        }
        if (bl2 |= this.upgradeSlot.tickNoMark()) {
            this.m_6596_();
        }
        if (bl3 != this.getActive()) {
            this.setActive(bl3);
        }
    }

    public String getHeat() {
        return this.heat * 100 / 10000 + "%";
    }

    public int gaugeProgressScaled(int n) {
        return n * this.progress / 4000;
    }

    public void operate() {
        this.operate(this.inputSlotA, this.outputSlotA);
        this.operate(this.inputSlotB, this.outputSlotB);
    }

    public void operate(InvSlotProcessableSmelting invSlotProcessableSmelting, InvSlotOutput invSlotOutput) {
        if (!this.canOperate(invSlotProcessableSmelting, invSlotOutput)) {
            return;
        }
        MachineRecipeResult machineRecipeResult = invSlotProcessableSmelting.process();
        invSlotOutput.add((ItemStack)machineRecipeResult.getOutput());
        invSlotProcessableSmelting.consume(machineRecipeResult);
    }

    public boolean canOperate() {
        return this.canOperate(this.inputSlotA, this.outputSlotA) || this.canOperate(this.inputSlotB, this.outputSlotB);
    }

    public boolean canOperate(InvSlotProcessableSmelting invSlotProcessableSmelting, InvSlotOutput invSlotOutput) {
        if (invSlotProcessableSmelting.isEmpty()) {
            return false;
        }
        MachineRecipeResult machineRecipeResult = invSlotProcessableSmelting.process();
        if (machineRecipeResult == null) {
            return false;
        }
        return invSlotOutput.canAdd((ItemStack)machineRecipeResult.getOutput());
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

    @Override
    public double getGuiValue(String string) {
        if ("progress".equals(string)) {
            return (double)this.gaugeProgressScaled(1000) / 1000.0;
        }
        throw new IllegalArgumentException();
    }

    public String getStartingSoundFile() {
        return "Machines/Induction Furnace/InductionStart.ogg";
    }

    public String getStartSoundFile() {
        return "Machines/Induction Furnace/InductionLoop.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/Induction Furnace/InductionStop.ogg";
    }

    @Override
    public void onNetworkEvent(int n) {
        if (this.audioSource == null && this.getStartSoundFile() != null) {
            this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, this.getStartSoundFile(), true, false, IC2.audioManager.getDefaultVolume());
        }
        switch (n) {
            case 0: {
                if (this.startingSound != null) break;
                if (this.finishingSound != null) {
                    this.finishingSound.remove();
                    this.finishingSound = null;
                }
                this.startingSound = IC2.audioManager.playOnce(this, PositionSpec.Center, this.getStartingSoundFile(), false, IC2.audioManager.getDefaultVolume());
                if (this.audioSource == null) break;
                this.startingSound.onFinish(this.audioSource::play);
                break;
            }
            case 2: {
                break;
            }
            case 1: 
            case 3: {
                if (this.audioSource == null) break;
                this.audioSource.stop();
                if (this.startingSound != null) {
                    if (!this.startingSound.isComplete()) {
                        this.startingSound.cancel();
                    }
                    this.startingSound = null;
                }
                this.finishingSound = IC2.audioManager.playOnce(this, PositionSpec.Center, this.getInterruptSoundFile(), false, IC2.audioManager.getDefaultVolume());
            }
        }
    }
}

