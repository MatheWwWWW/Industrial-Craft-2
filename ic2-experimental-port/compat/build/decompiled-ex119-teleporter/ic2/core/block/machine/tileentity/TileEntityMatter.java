/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
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
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerMatter;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.MatterAmplifierRecipeManager;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityMatter
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
IExplosionPowerOverride {
    private static final int DEFAULT_TIER = ConfigUtil.getInt(MainConfig.get(), "balance/matterFabricatorTier");
    public int scrap = 0;
    private double lastEnergy;
    private static final int StateIdle = 0;
    private static final int StateRunning = 1;
    private static final int StateRunningScrap = 2;
    private int state = 0;
    private int prevState = 0;
    public boolean redstonePowered = false;
    private AudioSource audioSource;
    private AudioSource audioSourceScrap;
    public final InvSlotUpgrade upgradeSlot;
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
    public final InvSlotConsumableLiquid containerslot;
    @GuiSynced
    public final Ic2FluidTank fluidTank;
    protected final Redstone redstone;
    protected final Fluids fluids;

    public TileEntityMatter(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MATTER_GENERATOR, blockPos, blockState, Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), DEFAULT_TIER);
        this.containerslot = new InvSlotConsumableLiquidByList(this, "container", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, Ic2Fluids.UU_MATTER.still);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.redstone = this.addComponent(new Redstone(this));
        this.redstone.subscribe(new Redstone.IRedstoneChangeHandler(){

            @Override
            public void onRedstoneChange(int n) {
                TileEntityMatter.this.energy.setEnabled(n == 0);
            }
        });
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTank("fluidTank", 8000, Fluids.fluidPredicate(Ic2Fluids.UU_MATTER.still));
        this.comparator.setUpdate(() -> {
            int n = TileEntityMatter.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
            if (n > 0) {
                return n;
            }
            if (this.scrap > 0) {
                return 1;
            }
            return 0;
        });
    }

    public static void init() {
        Recipes.matterAmplifier = new MatterAmplifierRecipeManager();
        TileEntityMatter.addAmplifier(new ItemStack((ItemLike)Ic2Items.SCRAP), 1, 5000);
        TileEntityMatter.addAmplifier(new ItemStack((ItemLike)Ic2Items.SCRAP_BOX), 1, 45000);
    }

    public static void addAmplifier(ItemStack itemStack, int n, int n2) {
        TileEntityMatter.addAmplifier(Recipes.inputFactory.forStack(itemStack, n), n2);
    }

    public static void addAmplifier(IRecipeInput iRecipeInput, int n) {
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
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_) {
            this.setUpgradestat();
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
            this.audioSourceScrap = null;
        }
        super.onUnloaded();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.redstonePowered = false;
        boolean bl = false;
        bl |= this.upgradeSlot.tickNoMark();
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.setState(0);
            this.setActive(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> machineRecipeResult;
            if (this.scrap > 0) {
                double d = Math.min((double)this.scrap, this.energy.getEnergy() - this.lastEnergy);
                if (d > 0.0) {
                    this.energy.forceAddEnergy(5.0 * d);
                    this.scrap = (int)((double)this.scrap - d);
                }
                this.setState(2);
            } else {
                this.setState(1);
            }
            this.setActive(true);
            if (this.scrap < 10000 && (machineRecipeResult = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(machineRecipeResult);
                this.scrap += machineRecipeResult.getOutput().intValue();
            }
            if (this.energy.getEnergy() >= this.energy.getCapacity()) {
                bl = this.attemptGeneration();
            }
            this.lastEnergy = this.energy.getEnergy();
            if (bl |= this.containerslot.processFromTank(this.fluidTank, this.outputSlot)) {
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
        if (this.fluidTank.getFluidAmount() + 1 > this.fluidTank.getCapacity()) {
            return false;
        }
        this.fluidTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.UU_MATTER.still, 1), false);
        this.energy.useEnergy(this.energy.getCapacity());
        return true;
    }

    public String getProgressAsString() {
        int n = (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
        return n + "%";
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerMatter(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerMatter(n, inventory, this);
    }

    private void setState(int n) {
        this.state = n;
        if (this.prevState != this.state) {
            IC2.network.get(true).updateTileEntityField(this, "state");
        }
        this.prevState = this.state;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("state");
        arrayList.addAll(super.getNetworkedFields());
        return arrayList;
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("state") && this.prevState != this.state) {
            switch (this.state) {
                case 0: {
                    if (this.audioSource != null) {
                        this.audioSource.stop();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.stop();
                    break;
                }
                case 1: {
                    if (this.audioSource == null) {
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSource != null) {
                        this.audioSource.play();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.stop();
                    break;
                }
                case 2: {
                    if (this.audioSource == null) {
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSourceScrap == null) {
                        this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSource != null) {
                        this.audioSource.play();
                    }
                    if (this.audioSourceScrap == null) break;
                    this.audioSourceScrap.play();
                }
            }
            this.prevState = this.state;
        }
        super.onNetworkUpdate(string);
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setUpgradestat();
        }
    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        this.energy.setSinkTier(TileEntityMatter.applyModifier(DEFAULT_TIER, this.upgradeSlot.extraTier, 1.0));
    }

    private static int applyModifier(int n, int n2, double d) {
        double d2 = Math.round(((double)n + (double)n2) * d);
        return d2 > 2.147483647E9 ? Integer.MAX_VALUE : (int)d2;
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
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
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

