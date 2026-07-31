/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.comp;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.recipe.SmeltingRecipeManager;
import ic2.core.util.StackUtil;
import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class Process
extends TileEntityComponent {
    protected int progress = 0;
    public int defaultEnergyConsume;
    public int operationDuration;
    public int defaultTier;
    public int defaultEnergyStorage;
    public int energyConsume;
    public int operationLength;
    public int operationsPerTick;
    private final InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> inputSlot;
    private final InvSlotOutput outputSlot;
    private InvSlotUpgrade upgradeSlot;

    public static Process asFurnace(TileEntityInventory tileEntityInventory) {
        return Process.asFurnace(tileEntityInventory, 3, 100, 1, 4);
    }

    public static Process asFurnace(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, level -> SmeltingRecipeManager.SmeltingBridge.INSTANCE, n, n2, n3, n4);
    }

    public static Process asMacerator(TileEntityInventory tileEntityInventory) {
        return Process.asMacerator(tileEntityInventory, 2, 300, 1, 4);
    }

    public static Process asMacerator(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.macerator, n, n2, n3, n4);
    }

    public static Process asExtractor(TileEntityInventory tileEntityInventory) {
        return Process.asExtractor(tileEntityInventory, 2, 300, 1, 4);
    }

    public static Process asExtractor(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.extractor, n, n2, n3, n4);
    }

    public static Process asCompressor(TileEntityInventory tileEntityInventory) {
        return Process.asCompressor(tileEntityInventory, 2, 300, 1, 4);
    }

    public static Process asCompressor(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.compressor, n, n2, n3, n4);
    }

    public static Process asCentrifuge(TileEntityInventory tileEntityInventory) {
        return Process.asCentrifuge(tileEntityInventory, 48, 500, 3, 4);
    }

    public static Process asCentrifuge(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.centrifuge, n, n2, n3, n4);
    }

    public static Process asRecycler(TileEntityInventory tileEntityInventory) {
        return Process.asRecycler(tileEntityInventory, 1, 45, 1, 4);
    }

    public static Process asRecycler(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, level -> Recipes.recycler, n, n2, n3, n4);
    }

    public static Process asOreWasher(TileEntityInventory tileEntityInventory) {
        return Process.asOreWasher(tileEntityInventory, 16, 500, 3, 4);
    }

    public static Process asOreWasher(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.oreWashing, n, n2, n3, n4);
    }

    public static Process asBlockCutter(TileEntityInventory tileEntityInventory) {
        return Process.asBlockCutter(tileEntityInventory, 48, 900, 1, 4);
    }

    public static Process asBlockCutter(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.blockcutter, n, n2, n3, n4);
    }

    public static Process asBlastFurnace(TileEntityInventory tileEntityInventory) {
        return Process.asBlastFurnace(tileEntityInventory, 2, 300, 1, 4);
    }

    public static Process asBlastFurnace(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.blastfurnace, n, n2, n3, n4);
    }

    public static Process asExtruder(TileEntityInventory tileEntityInventory) {
        return Process.asExtruder(tileEntityInventory, 10, 200, 1, 4);
    }

    public static Process asExtruder(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.metalformerExtruding, n, n2, n3, n4);
    }

    public static Process asCutter(TileEntityInventory tileEntityInventory) {
        return Process.asCutter(tileEntityInventory, 10, 200, 1, 4);
    }

    public static Process asCutter(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.metalformerCutting, n, n2, n3, n4);
    }

    public static Process asRollingMachine(TileEntityInventory tileEntityInventory) {
        return Process.asRollingMachine(tileEntityInventory, 10, 200, 1, 4);
    }

    public static Process asRollingMachine(TileEntityInventory tileEntityInventory, int n, int n2, int n3, int n4) {
        return new Process(tileEntityInventory, Recipes.metalformerRolling, n, n2, n3, n4);
    }

    public Process(TileEntityInventory tileEntityInventory, Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>> iGetter) {
        this(tileEntityInventory, iGetter, 2, 100, 1, 0);
    }

    public Process(TileEntityInventory tileEntityInventory, Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>> iGetter, int n, int n2, int n3, int n4) {
        this(tileEntityInventory, new InvSlotProcessableGeneric((IInventorySlotHolder<?>)tileEntityInventory, "input", 1, iGetter), n, n2, n3, n4);
    }

    protected Process(TileEntityInventory tileEntityInventory, InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> invSlotProcessable, int n, int n2, int n3, int n4) {
        super(tileEntityInventory);
        this.operationDuration = n2;
        assert (invSlotProcessable != null);
        this.inputSlot = invSlotProcessable;
        this.outputSlot = new InvSlotOutput(tileEntityInventory, "output", n3);
        if (tileEntityInventory instanceof IUpgradableBlock && n4 > 0) {
            this.upgradeSlot = InvSlotUpgrade.createUnchecked(tileEntityInventory, "upgrade", n4);
        }
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.progress = compoundTag.m_128451_("progress");
    }

    public void writeToNBT(CompoundTag compoundTag) {
        compoundTag.m_128405_("progress", this.progress);
    }

    public static int applyModifier(int n, int n2, double d) {
        double d2 = Math.round(((double)n + (double)n2) * d);
        return d2 > 2.147483647E9 ? Integer.MAX_VALUE : (int)d2;
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        double d = (double)this.progress / (double)this.operationDuration;
        double d2 = ((double)this.operationDuration + (double)this.upgradeSlot.extraProcessTime) * 64.0 * this.upgradeSlot.processTimeMultiplier;
        this.operationsPerTick = (int)Math.min(Math.ceil(64.0 / d2), 2.147483647E9);
        this.operationDuration = (int)Math.round(d2 * (double)this.operationsPerTick / 64.0);
        this.energyConsume = Process.applyModifier(this.defaultEnergyConsume, this.upgradeSlot.extraEnergyDemand, this.upgradeSlot.energyDemandMultiplier);
        if (this.operationDuration < 1) {
            this.operationDuration = 1;
        }
        this.progress = (short)Math.floor(d * (double)this.operationDuration + 0.1);
    }

    public void operate(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult) {
        for (int i = 0; i < this.operationsPerTick; ++i) {
            Collection<ItemStack> collection = StackUtil.copy(machineRecipeResult.getOutput());
            if (this.parent instanceof IUpgradableBlock) {
                for (int j = 0; j < this.upgradeSlot.size(); ++j) {
                    ItemStack itemStack = this.upgradeSlot.get(j);
                    if (StackUtil.isEmpty(itemStack) || !(itemStack.m_41720_() instanceof IUpgradeItem)) continue;
                    ((IUpgradeItem)itemStack.m_41720_()).onProcessEnd(itemStack, (IUpgradableBlock)((Object)this.parent), collection);
                }
            }
            this.operateOnce(machineRecipeResult, collection);
            machineRecipeResult = this.getOutput();
            if (machineRecipeResult == null) break;
        }
    }

    public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult, Collection<ItemStack> collection) {
        this.inputSlot.consume(machineRecipeResult);
        this.outputSlot.add(collection);
    }

    public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
        if (this.inputSlot.isEmpty()) {
            return null;
        }
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = this.inputSlot.process();
        if (machineRecipeResult == null) {
            return null;
        }
        if (this.outputSlot.canAdd(machineRecipeResult.getOutput())) {
            return machineRecipeResult;
        }
        return null;
    }

    public int getProgress() {
        return this.progress;
    }

    public double getProgressRatio() {
        return this.progress / this.operationDuration;
    }
}

