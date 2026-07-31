/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.IPatternStorage;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerReplicator;
import ic2.core.block.machine.gui.GuiReplicator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.FluidName;
import ic2.core.util.StackUtil;
import ic2.core.uu.UuIndex;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityReplicator
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock,
INetworkClientTileEntityEventListener {
    private static final double uuPerTickBase = 1.0E-4;
    private static final double euPerTickBase = 512.0;
    private static final int defaultTier = 4;
    private static final int defaultEnergyStorage = 2000000;
    private double uuPerTick = 1.0E-4;
    private double euPerTick = 512.0;
    private double extraUuStored = 0.0;
    public double uuProcessed = 0.0;
    public ItemStack pattern;
    private Mode mode = Mode.STOPPED;
    public int index;
    public int maxIndex;
    public double patternUu;
    public double patternEu;
    public final InvSlotConsumableLiquid fluidSlot = new InvSlotConsumableLiquidByList(this, "fluid", 1, FluidName.uu_matter.getInstance());
    public final InvSlotOutput cellSlot = new InvSlotOutput(this, "cell", 1);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
    @GuiSynced
    public final FluidTank fluidTank;
    protected final Fluids fluids = this.addComponent(new Fluids(this));

    public TileEntityReplicator() {
        super(2000000, 4);
        this.fluidTank = this.fluids.addTank("fluidTank", 16000, Fluids.fluidPredicate(FluidName.uu_matter.getInstance()));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
            needsInvUpdate = this.gainFluid();
        }
        boolean newActive = false;
        if (this.mode != Mode.STOPPED && this.energy.getEnergy() >= this.euPerTick && this.pattern != null && this.outputSlot.canAdd(this.pattern)) {
            boolean finish;
            double uuRemaining = this.patternUu - this.uuProcessed;
            if (uuRemaining <= this.uuPerTick) {
                finish = true;
            } else {
                uuRemaining = this.uuPerTick;
                finish = false;
            }
            if (this.consumeUu(uuRemaining)) {
                newActive = true;
                this.energy.useEnergy(this.euPerTick);
                this.uuProcessed += uuRemaining;
                if (finish) {
                    this.uuProcessed = 0.0;
                    if (this.mode == Mode.SINGLE) {
                        this.mode = Mode.STOPPED;
                    } else {
                        this.refreshInfo();
                    }
                    if (this.pattern != null) {
                        this.outputSlot.add(this.pattern);
                        needsInvUpdate = true;
                    }
                }
            }
        }
        this.setActive(newActive);
        if (needsInvUpdate |= this.upgradeSlot.tickNoMark()) {
            this.func_70296_d();
        }
    }

    private boolean consumeUu(double amount) {
        if (amount <= this.extraUuStored) {
            this.extraUuStored -= amount;
            return true;
        }
        int toDrain = (int)Math.ceil((amount -= this.extraUuStored) * 1000.0);
        FluidStack drained = this.fluidTank.drainInternal(toDrain, false);
        if (drained != null && drained.getFluid() == FluidName.uu_matter.getInstance() && drained.amount == toDrain) {
            this.fluidTank.drainInternal(toDrain, true);
            this.extraUuStored = amount < 0.0 ? -(amount -= (double)drained.amount / 1000.0) : 0.0;
            return true;
        }
        return false;
    }

    public void refreshInfo() {
        IPatternStorage storage = this.getPatternStorage();
        ItemStack oldPattern = this.pattern;
        if (storage == null) {
            this.pattern = null;
        } else {
            List<ItemStack> patterns = storage.getPatterns();
            if (this.index < 0 || this.index >= patterns.size()) {
                this.index = 0;
            }
            this.maxIndex = patterns.size();
            if (patterns.isEmpty()) {
                this.pattern = null;
            } else {
                this.pattern = patterns.get(this.index);
                this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
                if (!StackUtil.checkItemEqualityStrict(this.pattern, oldPattern)) {
                    this.uuProcessed = 0.0;
                    this.mode = Mode.STOPPED;
                }
            }
        }
        if (this.pattern == null) {
            this.uuProcessed = 0.0;
            this.mode = Mode.STOPPED;
        }
    }

    public IPatternStorage getPatternStorage() {
        World world = this.func_145831_w();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof IPatternStorage)) continue;
            return (IPatternStorage)target;
        }
        return null;
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        this.uuPerTick = 1.0E-4 / this.upgradeSlot.processTimeMultiplier;
        this.euPerTick = (512.0 + (double)this.upgradeSlot.extraEnergyDemand) * this.upgradeSlot.energyDemandMultiplier;
        this.energy.setSinkTier(TileEntityReplicator.applyModifier(4, this.upgradeSlot.extraTier, 1.0));
        this.energy.setCapacity(TileEntityReplicator.applyModifier(2000000, this.upgradeSlot.extraEnergyStorage, this.upgradeSlot.energyStorageMultiplier));
    }

    private static int applyModifier(int base, int extra, double multiplier) {
        double ret = Math.round(((double)base + (double)extra) * multiplier);
        return ret > 2.147483647E9 ? Integer.MAX_VALUE : (int)ret;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiReplicator(new ContainerReplicator(player, this));
    }

    public ContainerBase<TileEntityReplicator> getGuiContainer(EntityPlayer player) {
        return new ContainerReplicator(player, this);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            this.setOverclockRates();
            this.refreshInfo();
        }
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (IC2.platform.isSimulating()) {
            this.setOverclockRates();
        }
    }

    public boolean gainFluid() {
        return this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.cellSlot);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.extraUuStored = nbt.func_74769_h("extraUuStored");
        this.uuProcessed = nbt.func_74769_h("uuProcessed");
        this.index = nbt.func_74762_e("index");
        int modeIdx = nbt.func_74762_e("mode");
        this.mode = modeIdx < Mode.values().length ? Mode.values()[modeIdx] : Mode.STOPPED;
        NBTTagCompound contentTag = nbt.func_74775_l("pattern");
        this.pattern = new ItemStack(contentTag);
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74780_a("extraUuStored", this.extraUuStored);
        nbt.func_74780_a("uuProcessed", this.uuProcessed);
        nbt.func_74768_a("index", this.index);
        nbt.func_74768_a("mode", this.mode.ordinal());
        if (this.pattern != null) {
            NBTTagCompound contentTag = new NBTTagCompound();
            this.pattern.func_77955_b(contentTag);
            nbt.func_74782_a("pattern", (NBTBase)contentTag);
        }
        return nbt;
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        switch (event) {
            case 0: 
            case 1: {
                List<ItemStack> patterns;
                IPatternStorage storage;
                if (this.mode != Mode.STOPPED || (storage = this.getPatternStorage()) == null || (patterns = storage.getPatterns()).isEmpty()) break;
                this.index = event == 0 ? (this.index <= 0 ? patterns.size() - 1 : --this.index) : (this.index >= patterns.size() - 1 ? 0 : ++this.index);
                this.refreshInfo();
                break;
            }
            case 3: {
                if (this.mode == Mode.STOPPED) break;
                this.uuProcessed = 0.0;
                this.mode = Mode.STOPPED;
                break;
            }
            case 4: {
                if (this.pattern == null) break;
                this.mode = Mode.SINGLE;
                if (player == null) break;
                IC2.achievements.issueAchievement(player, "replicateObject");
                break;
            }
            case 5: {
                if (this.pattern == null) break;
                this.mode = Mode.CONTINUOUS;
                if (player == null) break;
                IC2.achievements.issueAchievement(player, "replicateObject");
            }
        }
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    public Mode getMode() {
        return this.mode;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[]{UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming});
    }

    public static enum Mode {
        STOPPED,
        SINGLE,
        CONTINUOUS;

    }
}

