/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.sound.Sound;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityClassicMassFabricator
extends TileEntityElectricMachine
implements IHasGui,
IExplosionPowerOverride {
    private Sound scrapSound;
    @GuiSynced
    public int scrap = 0;
    private double lastEnergy;
    public final InvSlotProcessable<IRecipeInput, Integer, ItemStack> amplifierSlot = new InvSlotProcessable<IRecipeInput, Integer, ItemStack>((IInventorySlotHolder)this, "scrap", 1, level -> Recipes.matterAmplifier){

        @Override
        protected ItemStack getInput(ItemStack itemStack) {
            return itemStack;
        }

        @Override
        protected void setInput(ItemStack itemStack) {
            this.put(itemStack);
        }
    };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    protected final Redstone redstone = this.addComponent(new Redstone(this));

    public TileEntityClassicMassFabricator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.CLASSIC_MASS_FABRICATOR, blockPos, blockState, Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), TileEntityMassFabricator.DEFAULT_TIER, false);
        this.redstone.subscribe(n -> this.energy.setEnabled(n == 0));
        this.comparator.setUpdate(() -> {
            int n = TileEntityClassicMassFabricator.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
            if (n > 0) {
                return n;
            }
            if (this.scrap > 0) {
                return 1;
            }
            return 0;
        });
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.scrap = compoundTag.m_128451_("scrap");
        this.lastEnergy = compoundTag.m_128459_("lastEnergy");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("scrap", this.scrap);
        compoundTag.m_128347_("lastEnergy", this.lastEnergy);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.shutdown(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> machineRecipeResult;
            if (this.scrap > 0) {
                double d = Math.min((double)this.scrap, this.energy.getEnergy() - this.lastEnergy);
                if (d > 0.0) {
                    this.energy.forceAddEnergy(5.0 * d);
                    this.scrap = (int)((double)this.scrap - d);
                }
                this.playScrapSound();
            }
            this.activate();
            if (this.scrap < 10000 && (machineRecipeResult = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(machineRecipeResult);
                this.scrap += machineRecipeResult.getOutput().intValue();
            }
            if (this.energy.getEnergy() >= this.energy.getCapacity()) {
                bl = this.attemptGeneration();
            }
            this.lastEnergy = this.energy.getEnergy();
            if (bl) {
                this.m_6596_();
            }
        }
    }

    public boolean amplificationIsAvailable() {
        if (this.scrap > 0) {
            return true;
        }
        MachineRecipeResult<IRecipeInput, Integer, ItemStack> machineRecipeResult = this.amplifierSlot.process();
        return machineRecipeResult != null && machineRecipeResult.getOutput() > 0;
    }

    public boolean attemptGeneration() {
        if (this.outputSlot.add(new ItemStack((ItemLike)Ic2Items.UU_MATTER)) == 0) {
            this.energy.useEnergy(this.energy.getCapacity());
            return true;
        }
        return false;
    }

    @Override
    protected void initSound() {
        super.initSound();
        if (this.scrapSound == null) {
            this.scrapSound = IC2.soundManager.createSound((Object)this, Ic2SoundEvents.MACHINE_FABRICATOR_SCRAP, SoundSource.BLOCKS, this.f_58858_, 1.0f, 1.0f);
        }
    }

    @Override
    protected void clearSound() {
        super.clearSound();
        this.scrapSound = null;
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_FABRICATOR_LOOP;
    }

    public void playScrapSound() {
        this.scrapSound.play();
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    public String getProgressAsString() {
        int n = (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
        return n + "%";
    }

    @Override
    public boolean getGuiState(String string) {
        if ("scrap".equals(string)) {
            return this.scrap > 0;
        }
        if ("dev".equals(string)) {
            return Util.inDev();
        }
        return super.getGuiState(string);
    }

    @Override
    public boolean shouldExplode() {
        return true;
    }

    @Override
    public float getExplosionPower(int n, float f) {
        return 15.0f;
    }
}

