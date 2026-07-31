/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
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
import ic2.core.block.machine.gui.GuiMatter;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.init.MainConfig;
import ic2.core.item.type.CraftingItemType;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.recipe.MatterAmplifierRecipeManager;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public final InvSlotProcessable<IRecipeInput, Integer, ItemStack> amplifierSlot = new InvSlotProcessable<IRecipeInput, Integer, ItemStack>((IInventorySlotHolder)this, "scrap", 1, Recipes.matterAmplifier){

        @Override
        protected ItemStack getInput(ItemStack stack) {
            return stack;
        }

        @Override
        protected void setInput(ItemStack input) {
            this.put(input);
        }
    };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    public final InvSlotConsumableLiquid containerslot = new InvSlotConsumableLiquidByList(this, "container", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, FluidName.uu_matter.getInstance());
    @GuiSynced
    public final FluidTank fluidTank;
    protected final Redstone redstone;
    protected final Fluids fluids;

    public TileEntityMatter() {
        super(Math.round(1000000.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), DEFAULT_TIER);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.redstone = this.addComponent(new Redstone(this));
        this.redstone.subscribe(new Redstone.IRedstoneChangeHandler(){

            @Override
            public void onRedstoneChange(int newLevel) {
                TileEntityMatter.this.energy.setEnabled(newLevel == 0);
            }
        });
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTank("fluidTank", 8000, Fluids.fluidPredicate(FluidName.uu_matter.getInstance()));
        this.comparator.setUpdate(() -> {
            int count = TileEntityMatter.calcRedstoneFromInvSlots(new InvSlot[]{this.amplifierSlot});
            if (count > 0) {
                return count;
            }
            if (this.scrap > 0) {
                return 1;
            }
            return 0;
        });
    }

    public static void init() {
        Recipes.matterAmplifier = new MatterAmplifierRecipeManager();
        TileEntityMatter.addAmplifier(ItemName.crafting.getItemStack(CraftingItemType.scrap), 1, 5000);
        TileEntityMatter.addAmplifier(ItemName.crafting.getItemStack(CraftingItemType.scrap_box), 1, 45000);
    }

    public static void addAmplifier(ItemStack input, int amount, int amplification) {
        TileEntityMatter.addAmplifier(Recipes.inputFactory.forStack(input, amount), amplification);
    }

    public static void addAmplifier(String input, int amount, int amplification) {
        TileEntityMatter.addAmplifier(Recipes.inputFactory.forOreDict(input, amount), amplification);
    }

    public static void addAmplifier(IRecipeInput input, int amplification) {
        Recipes.matterAmplifier.addRecipe(input, amplification, null, false);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.scrap = nbt.func_74762_e("scrap");
        this.lastEnergy = nbt.func_74769_h("lastEnergy");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("scrap", this.scrap);
        nbt.func_74780_a("lastEnergy", this.lastEnergy);
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            this.setUpgradestat();
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
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
        boolean needsInvUpdate = false;
        needsInvUpdate |= this.upgradeSlot.tickNoMark();
        if (this.redstone.hasRedstoneInput() || this.energy.getEnergy() <= 0.0) {
            this.setState(0);
            this.setActive(false);
        } else {
            MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe;
            if (this.scrap > 0) {
                double bonus = Math.min((double)this.scrap, this.energy.getEnergy() - this.lastEnergy);
                if (bonus > 0.0) {
                    this.energy.forceAddEnergy(5.0 * bonus);
                    this.scrap = (int)((double)this.scrap - bonus);
                }
                this.setState(2);
            } else {
                this.setState(1);
            }
            this.setActive(true);
            if (this.scrap < 10000 && (recipe = this.amplifierSlot.process()) != null) {
                this.amplifierSlot.consume(recipe);
                this.scrap += recipe.getOutput().intValue();
            }
            if (this.energy.getEnergy() >= this.energy.getCapacity()) {
                needsInvUpdate = this.attemptGeneration();
            }
            this.lastEnergy = this.energy.getEnergy();
            if (needsInvUpdate |= this.containerslot.processFromTank((IFluidTank)this.fluidTank, this.outputSlot)) {
                this.func_70296_d();
            }
        }
    }

    public boolean amplificationIsAvailable() {
        if (this.scrap > 0) {
            return true;
        }
        MachineRecipeResult<IRecipeInput, Integer, ItemStack> recipe = this.amplifierSlot.process();
        return recipe != null && recipe.getOutput() > 0;
    }

    public boolean attemptGeneration() {
        if (this.fluidTank.getFluidAmount() + 1 > this.fluidTank.getCapacity()) {
            return false;
        }
        this.fluidTank.fillInternal(new FluidStack(FluidName.uu_matter.getInstance(), 1), true);
        this.energy.useEnergy(this.energy.getCapacity());
        return true;
    }

    public String getProgressAsString() {
        int p = (int)Math.min(100.0 * this.energy.getFillRatio(), 100.0);
        return "" + p + "%";
    }

    public ContainerBase<TileEntityMatter> getGuiContainer(EntityPlayer player) {
        return new ContainerMatter(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiMatter(new ContainerMatter(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    private void setState(int aState) {
        this.state = aState;
        if (this.prevState != this.state) {
            IC2.network.get(true).updateTileEntityField(this, "state");
        }
        this.prevState = this.state;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("state");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("state") && this.prevState != this.state) {
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
        super.onNetworkUpdate(field);
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (IC2.platform.isSimulating()) {
            this.setUpgradestat();
        }
    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        this.energy.setSinkTier(TileEntityMatter.applyModifier(DEFAULT_TIER, this.upgradeSlot.extraTier, 1.0));
    }

    private static int applyModifier(int base, int extra, double multiplier) {
        double ret = Math.round(((double)base + (double)extra) * multiplier);
        return ret > 2.147483647E9 ? Integer.MAX_VALUE : (int)ret;
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
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
    public float getExplosionPower(int tier, float defaultPower) {
        return 15.0f;
    }
}

