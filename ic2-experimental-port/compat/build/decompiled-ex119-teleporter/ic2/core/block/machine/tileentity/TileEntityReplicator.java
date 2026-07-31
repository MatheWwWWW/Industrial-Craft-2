/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
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
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.uu.UuIndex;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput cellSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotUpgrade upgradeSlot;
    @GuiSynced
    public final Ic2FluidTank fluidTank;
    protected final Fluids fluids;

    public TileEntityReplicator(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.REPLICATOR, blockPos, blockState, 2000000, 4);
        this.fluidSlot = new InvSlotConsumableLiquidByList(this, "fluid", 1, Ic2Fluids.UU_MATTER.still);
        this.cellSlot = new InvSlotOutput(this, "cell", 1);
        this.outputSlot = new InvSlotOutput(this, "output", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTank("fluidTank", 16000, Fluids.fluidPredicate(Ic2Fluids.UU_MATTER.still));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
            bl = this.gainFluid();
        }
        boolean bl2 = false;
        if (this.mode != Mode.STOPPED && this.energy.getEnergy() >= this.euPerTick && this.pattern != null && this.outputSlot.canAdd(this.pattern)) {
            boolean bl3;
            double d = this.patternUu - this.uuProcessed;
            if (d <= this.uuPerTick) {
                bl3 = true;
            } else {
                d = this.uuPerTick;
                bl3 = false;
            }
            if (this.consumeUu(d)) {
                bl2 = true;
                this.energy.useEnergy(this.euPerTick);
                this.uuProcessed += d;
                if (bl3) {
                    this.uuProcessed = 0.0;
                    if (this.mode == Mode.SINGLE) {
                        this.mode = Mode.STOPPED;
                    } else {
                        this.refreshInfo();
                    }
                    if (this.pattern != null) {
                        this.outputSlot.add(this.pattern);
                        bl = true;
                    }
                }
            }
        }
        this.setActive(bl2);
        if (bl |= this.upgradeSlot.tickNoMark()) {
            this.m_6596_();
        }
    }

    private boolean consumeUu(double d) {
        if (d <= this.extraUuStored) {
            this.extraUuStored -= d;
            return true;
        }
        int n = (int)Math.ceil((d -= this.extraUuStored) * 1000.0);
        Ic2FluidStack ic2FluidStack = this.fluidTank.drainMbUnchecked(n, true);
        if (ic2FluidStack != null && ic2FluidStack.getFluid() == Ic2Fluids.UU_MATTER.still && ic2FluidStack.getAmountMb() == n) {
            this.fluidTank.drainMbUnchecked(n, false);
            this.extraUuStored = d < 0.0 ? -(d -= (double)ic2FluidStack.getAmountMb() / 1000.0) : 0.0;
            return true;
        }
        return false;
    }

    public void refreshInfo() {
        IPatternStorage iPatternStorage = this.getPatternStorage();
        ItemStack itemStack = this.pattern;
        if (iPatternStorage == null) {
            this.pattern = null;
        } else {
            List<ItemStack> list = iPatternStorage.getPatterns();
            if (this.index < 0 || this.index >= list.size()) {
                this.index = 0;
            }
            this.maxIndex = list.size();
            if (list.isEmpty()) {
                this.pattern = null;
            } else {
                this.pattern = list.get(this.index);
                this.patternUu = UuIndex.instance.getInBuckets(this.pattern);
                if (!StackUtil.checkItemEqualityStrict(this.pattern, itemStack)) {
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
        Level level = this.m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof IPatternStorage)) continue;
            return (IPatternStorage)blockEntity;
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

    private static int applyModifier(int n, int n2, double d) {
        double d2 = Math.round(((double)n + (double)n2) * d);
        return d2 > 2.147483647E9 ? Integer.MAX_VALUE : (int)d2;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerReplicator(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerReplicator(n, inventory, this);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.sideProxy.isSimulating()) {
            this.setOverclockRates();
            this.refreshInfo();
        }
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (IC2.sideProxy.isSimulating()) {
            this.setOverclockRates();
        }
    }

    public boolean gainFluid() {
        return this.fluidSlot.processIntoTank(this.fluidTank, this.cellSlot);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.extraUuStored = compoundTag.m_128459_("extraUuStored");
        this.uuProcessed = compoundTag.m_128459_("uuProcessed");
        this.index = compoundTag.m_128451_("index");
        int n = compoundTag.m_128451_("mode");
        this.mode = n < Mode.values().length ? Mode.values()[n] : Mode.STOPPED;
        CompoundTag compoundTag2 = compoundTag.m_128469_("pattern");
        this.pattern = ItemStack.m_41712_((CompoundTag)compoundTag2);
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128347_("extraUuStored", this.extraUuStored);
        compoundTag.m_128347_("uuProcessed", this.uuProcessed);
        compoundTag.m_128405_("index", this.index);
        compoundTag.m_128405_("mode", this.mode.ordinal());
        if (this.pattern != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            this.pattern.m_41739_(compoundTag2);
            compoundTag.m_128365_("pattern", (Tag)compoundTag2);
        }
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        switch (n) {
            case 0: 
            case 1: {
                List<ItemStack> list;
                IPatternStorage iPatternStorage;
                if (this.mode != Mode.STOPPED || (iPatternStorage = this.getPatternStorage()) == null || (list = iPatternStorage.getPatterns()).isEmpty()) break;
                this.index = n == 0 ? (this.index <= 0 ? list.size() - 1 : --this.index) : (this.index >= list.size() - 1 ? 0 : ++this.index);
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
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double d) {
        return this.energy.useEnergy(d);
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

