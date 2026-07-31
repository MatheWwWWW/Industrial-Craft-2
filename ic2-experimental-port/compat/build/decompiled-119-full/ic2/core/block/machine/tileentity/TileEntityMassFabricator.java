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
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.sound.Sound;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Util;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityMassFabricator
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IExplosionPowerOverride {
    @GuiSynced
    public int scrap = 0;
    @GuiSynced
    public int consumedScrap = 0;
    protected double maxScrapConsumption = EnergyNet.instance.getPowerFromTier(DEFAULT_TIER);
    public static final int DEFAULT_TIER = ConfigUtil.getInt(MainConfig.get(), "balance/massFabricatorTier");
    private static final int REQUIRED_SCRAP = Util.roundToNegInf(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor"));
    private static final int SCRAP_FACTOR = 10;
    private Sound scrapSound;
    private byte scrapCounter = 0;
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
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    protected final Redstone redstone = this.addComponent(new Redstone(this));

    public TileEntityMassFabricator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MASS_FABRICATOR, blockPos, blockState, Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), DEFAULT_TIER, false);
        this.redstone.subscribe(n -> this.energy.setEnabled(n == 0));
        this.comparator.setUpdate(() -> {
            int n = TileEntityMassFabricator.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
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
        this.consumedScrap = compoundTag.m_128451_("consumedScrap");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("scrap", this.scrap);
        compoundTag.m_128405_("consumedScrap", this.consumedScrap);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            this.updateUpgrades();
        }
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (!this.m_58904_().f_46443_) {
            this.updateUpgrades();
        }
    }

    public void updateUpgrades() {
        this.upgradeSlot.onChanged();
        int n = this.upgradeSlot.getTier(DEFAULT_TIER);
        this.energy.setSinkTier(n);
        this.dischargeSlot.setTier(n);
        this.maxScrapConsumption = EnergyNet.instance.getPowerFromTier(n);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = this.upgradeSlot.tickNoMark();
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.shutdown(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> machineRecipeResult;
            if (this.scrap < 100000 && (machineRecipeResult = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(machineRecipeResult);
                this.scrap += machineRecipeResult.getOutput() * 10;
            }
            assert (this.scrap >= 0);
            double d = Math.min(Math.min((double)this.scrap, this.energy.getEnergy() - (double)this.consumedScrap), this.maxScrapConsumption);
            assert (d >= 0.0);
            boolean bl2 = false;
            if (d > 0.0) {
                this.consumedScrap = (int)((double)this.consumedScrap + d);
                this.scrap = (int)((double)this.scrap - d);
                bl2 = true;
                if (this.energy.getEnergy() >= this.energy.getCapacity() && this.consumedScrap >= REQUIRED_SCRAP) {
                    ItemStack itemStack = new ItemStack((ItemLike)Ic2Items.UU_MATTER);
                    if (this.outputSlot.canAdd(itemStack)) {
                        this.outputSlot.add(itemStack);
                        this.energy.useEnergy(this.energy.getCapacity());
                        this.consumedScrap = 0;
                        bl = true;
                    } else {
                        bl2 = false;
                    }
                }
            }
            this.setActiveState(bl2);
        }
        if (bl) {
            this.m_6596_();
        }
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
    public boolean playLoopingSound() {
        this.scrapSound.play();
        return super.playLoopingSound();
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_FABRICATOR_LOOP;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }

    public int getScrap() {
        return this.scrap / 10;
    }

    public int getScrapProgress() {
        return (int)Math.min(100.0f * ((float)this.consumedScrap / (float)REQUIRED_SCRAP), 100.0f);
    }

    public int getEnergyProgress() {
        return (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
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
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        list.add("You probably want the " + Localization.translate(Ic2Items.MATTER_GENERATOR.m_5524_()));
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
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
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

