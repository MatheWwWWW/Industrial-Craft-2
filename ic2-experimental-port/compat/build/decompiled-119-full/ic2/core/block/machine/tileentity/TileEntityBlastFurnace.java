/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.api.energy.tile.IHeatSource;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityBlastFurnace
extends TileEntityInventory
implements IUpgradableBlock,
IHasGui,
IGuiValueProvider {
    public int heat = 0;
    public static int maxHeat = 50000;
    @GuiSynced
    public float guiHeat;
    protected final Redstone redstone;
    protected final Fluids fluids;
    protected int progress = 0;
    protected int progressNeeded = 300;
    @GuiSynced
    protected float guiProgress;
    public final InvSlotProcessableGeneric inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder<?>)this, "input", 1, (Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>>)Recipes.blastfurnace);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 2){

        @Override
        public void onPickupFromSlot(Player player, ItemStack itemStack) {
            if (player != null && itemStack.m_41720_() == Ic2Items.STEEL_INGOT) {
                IC2.achievements.issueAchievement(player, "acquireRefinedIron");
            }
        }
    };
    public final InvSlotConsumableLiquidByList tankInputSlot;
    public final InvSlotOutput tankOutputSlot;
    public final InvSlotUpgrade upgradeSlot;
    @GuiSynced
    public final Ic2FluidTank fluidTank;

    public TileEntityBlastFurnace(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.BLAST_FURNACE, blockPos, blockState);
        this.tankInputSlot = new InvSlotConsumableLiquidByList(this, "cellInput", 1, Ic2Fluids.AIR.still);
        this.tankOutputSlot = new InvSlotOutput(this, "cellOutput", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
        this.redstone = this.addComponent(new Redstone(this));
        this.fluids = this.addComponent(new Fluids(this));
        this.fluidTank = this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(Ic2Fluids.AIR.still));
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        this.heatup();
        MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> machineRecipeResult = this.getOutput();
        if (machineRecipeResult != null && this.isHot()) {
            this.setActive(true);
            if (machineRecipeResult.getRecipe().getMetaData().m_128451_("fluid") <= this.fluidTank.getFluidAmount()) {
                ++this.progress;
                this.fluidTank.drainMbUnchecked(machineRecipeResult.getRecipe().getMetaData().m_128451_("fluid"), false);
            }
            this.progressNeeded = machineRecipeResult.getRecipe().getMetaData().m_128451_("duration");
            if (this.progress >= machineRecipeResult.getRecipe().getMetaData().m_128451_("duration")) {
                this.operateOnce(machineRecipeResult, machineRecipeResult.getOutput());
                bl = true;
                this.progress = 0;
            }
        } else {
            if (machineRecipeResult == null) {
                this.progress = 0;
            }
            this.setActive(false);
        }
        if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
            this.gainFluid();
        }
        this.guiProgress = (float)this.progress / (float)this.progressNeeded;
        this.guiHeat = (float)this.heat / (float)maxHeat;
        if (bl |= this.upgradeSlot.tickNoMark()) {
            super.m_6596_();
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
        if (machineRecipeResult == null || machineRecipeResult.getRecipe().getMetaData() == null) {
            return null;
        }
        if (this.outputSlot.canAdd((Collection)machineRecipeResult.getOutput())) {
            return machineRecipeResult;
        }
        return null;
    }

    public boolean gainFluid() {
        return this.tankInputSlot.processIntoTank(this.fluidTank, this.tankOutputSlot);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.heat = compoundTag.m_128451_("heat");
        this.progress = compoundTag.m_128451_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("heat", this.heat);
        compoundTag.m_128405_("progress", this.progress);
    }

    private void heatup() {
        int n = 0;
        int n2 = 0;
        if (!(this.inputSlot.isEmpty() && this.progress < 1 || this.heat > maxHeat)) {
            n = maxHeat - this.heat + 100;
        } else if (this.redstone.hasRedstoneInput() && this.heat <= maxHeat) {
            n = maxHeat - this.heat + 100;
        }
        if (n > 0) {
            Direction direction = this.getFacing();
            BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(direction));
            if (blockEntity instanceof IHeatSource) {
                n2 = ((IHeatSource)blockEntity).drawHeat(direction.m_122424_(), n, false);
                this.heat += n2;
            }
            if (n2 == 0) {
                this.heat -= Math.min(this.heat, 1);
            }
        } else {
            this.heat -= Math.min(this.heat, 1);
        }
    }

    public boolean isHot() {
        return this.heat >= maxHeat;
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
    public double getGuiValue(String string) {
        if (string.equals("progress")) {
            return this.guiProgress;
        }
        if (string.equals("heat")) {
            return this.guiHeat;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public double getEnergy() {
        return 0.0;
    }

    @Override
    public boolean useEnergy(double d) {
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming);
    }
}

