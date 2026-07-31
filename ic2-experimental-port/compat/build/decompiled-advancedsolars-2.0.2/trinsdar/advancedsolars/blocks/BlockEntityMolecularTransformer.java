/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.energy.EnergyNet
 *  ic2.api.energy.tile.IEnergyEmitter
 *  ic2.api.energy.tile.IEnergySink
 *  ic2.api.energy.tile.IEnergyTile
 *  ic2.api.network.buffer.NetworkInfo
 *  ic2.api.recipes.registries.IElectrolyzerRecipeList$ElectrolyzerRecipe
 *  ic2.api.tiles.readers.IEUStorage
 *  ic2.api.util.DirectionList
 *  ic2.core.block.base.features.ITickListener
 *  ic2.core.block.base.features.IWrenchableTile
 *  ic2.core.block.base.misc.comparator.BaseComparator
 *  ic2.core.block.base.misc.comparator.ComparatorNames
 *  ic2.core.block.base.misc.comparator.types.base.EUComparator
 *  ic2.core.block.base.tiles.BaseInventoryTileEntity
 *  ic2.core.inventory.base.ITileGui
 *  ic2.core.inventory.container.IC2Container
 *  ic2.core.inventory.handler.AccessRule
 *  ic2.core.inventory.handler.InventoryHandler
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package trinsdar.advancedsolars.blocks;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import trinsdar.advancedsolars.blocks.ContainerMolecularTransformer;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

public class BlockEntityMolecularTransformer
extends BaseInventoryTileEntity
implements IEnergySink,
IEUStorage,
IWrenchableTile,
ITileGui,
ITickListener {
    @NetworkInfo
    public int energyInPerTick = 0;
    @NetworkInfo
    public int energy = 0;
    @NetworkInfo
    public int maxEnergy = 0;
    public IElectrolyzerRecipeList.ElectrolyzerRecipe entry;
    @NetworkInfo
    public ItemStack input = ItemStack.f_41583_;
    @NetworkInfo
    public ItemStack output = ItemStack.f_41583_;
    boolean addedToEnet;
    boolean consumedInputs = false;
    private int energyAccepted = 0;
    private int ticker = 0;

    public BlockEntityMolecularTransformer(BlockPos pos, BlockState state) {
        super(pos, state, 2);
        this.addGuiFields(new String[]{"energy", "maxEnergy", "energyInPerTick", "input", "output"});
        this.addComparator((BaseComparator)new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, (IEUStorage)this));
    }

    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.IMPORT, new int[]{0});
        handler.registerSlotAccess(AccessRule.EXPORT, new int[]{1});
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), new int[]{0});
        handler.registerSlotsForSide(DirectionList.UP.invert(), new int[]{1});
        handler.registerInputFilter((i, s) -> this.isValidItem(s, i, true), new int[]{0});
    }

    public BlockEntityType<?> createType() {
        return Registry.MOLECULAR_TRANSFORMER_TYPE;
    }

    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ContainerMolecularTransformer(this, player, windowID);
    }

    public int getStoredEU() {
        return this.energy;
    }

    public int getMaxEU() {
        return this.maxEnergy;
    }

    public int getTier() {
        return 6;
    }

    public boolean isValidItem(ItemStack stack, int slot, boolean input) {
        ItemStack compare = (ItemStack)this.inventory.get(slot);
        if (!compare.m_41619_()) {
            return StackUtil.isStackEqual((ItemStack)stack, (ItemStack)compare);
        }
        return AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(stack, true, false) != null;
    }

    public void onTick() {
        boolean active = false;
        if (this.shouldProcess()) {
            int needed;
            if (this.energy == 0 && !this.consumedInputs) {
                ((ItemStack)this.inventory.get(0)).m_41774_(this.entry.getInput().m_41613_());
                this.consumedInputs = true;
            }
            if (this.energyAccepted > 0) {
                this.energy += this.energyAccepted;
                this.updateGuiField("energy");
                this.energyAccepted = 0;
                this.ticker = 0;
            }
            if (this.maxEnergy != (needed = this.entry.getEnergy())) {
                this.maxEnergy = needed;
                this.updateGuiField("maxEnergy");
            }
            if (this.energy >= this.maxEnergy) {
                this.energy = 0;
                this.updateGuiField("energy");
                this.consumedInputs = false;
                this.setOrGrow(1, this.entry.getOutput().m_41777_(), true);
            }
            active = true;
        }
        this.setActive(active);
        this.handleComparators();
    }

    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("energy", this.energy);
        compound.m_128405_("maxEnergy", this.maxEnergy);
        compound.m_128379_("consumedInputs", this.consumedInputs);
        compound.m_128365_("input", (Tag)this.input.m_41739_(new CompoundTag()));
        compound.m_128365_("output", (Tag)this.output.m_41739_(new CompoundTag()));
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.maxEnergy = compound.m_128451_("maxEnergy");
        this.consumedInputs = compound.m_128471_("consumedInputs");
        this.input = ItemStack.m_41712_((CompoundTag)compound.m_128469_("input"));
        this.output = ItemStack.m_41712_((CompoundTag)compound.m_128469_("output"));
        this.entry = AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(this.input, true, true);
    }

    public boolean shouldProcess() {
        if (this.consumedInputs) {
            return true;
        }
        if (!((ItemStack)this.inventory.get(0)).m_41619_()) {
            this.entry = AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe((ItemStack)this.inventory.get(0), true, true);
            if (this.entry == null) {
                this.input = ItemStack.f_41583_;
                this.output = ItemStack.f_41583_;
                this.updateGuiFields(new String[]{"input", "output"});
            } else {
                this.input = this.entry.getInput();
                this.output = this.entry.getOutput();
                this.updateGuiFields(new String[]{"input", "output"});
            }
            return this.entry != null && StackUtil.canFitInto((ItemStack)((ItemStack)this.inventory.get(1)), (ItemStack)this.entry.getOutput(), (int)22);
        }
        if (this.entry != null) {
            this.entry = null;
            this.energy = 0;
            this.input = ItemStack.f_41583_;
            this.output = ItemStack.f_41583_;
            this.updateGuiFields(new String[]{"input", "output", "energy"});
        }
        return false;
    }

    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile((IEnergyTile)this);
        }
    }

    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile((IEnergyTile)this);
        }
        super.onUnloaded(chunk);
    }

    public int getSinkTier() {
        return 6;
    }

    public int getRequestedEnergy() {
        return this.maxEnergy <= 0 ? 0 : this.maxEnergy - this.energy;
    }

    public int acceptEnergy(Direction direction, int amount, int voltage) {
        this.energyInPerTick = amount;
        if (this.maxEnergy <= 0 || this.entry == null) {
            return 0;
        }
        int added = Math.min(amount, this.maxEnergy - (this.energy + this.energyAccepted));
        if (added > 0) {
            this.energyAccepted += added;
        }
        return amount > 0 ? amount - added : 0;
    }

    public boolean canAcceptEnergy(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return true;
    }

    public boolean canSetFacing(Direction direction) {
        return false;
    }

    public boolean canRemoveBlock(Player player) {
        return true;
    }

    public double getDropRate(Player player) {
        return 1.0;
    }
}

