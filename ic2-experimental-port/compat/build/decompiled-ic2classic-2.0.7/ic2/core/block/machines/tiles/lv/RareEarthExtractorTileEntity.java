/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.queue.SimpleStackOutput;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.api.tiles.readers.ISubProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.containers.lv.RareEarthExtractorContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.MachineFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RareEarthExtractorTileEntity
extends BaseMachineTileEntity
implements ITickListener,
ITileGui,
IInventoryMachine,
ISubProgressMachine {
    public static final ResourceLocation EMPTY = new ResourceLocation("empty");
    public static final ResourceLocation RARE_EARTH = new ResourceLocation("ic2:rare_earth");
    public static final ResourceLocation ALUMINIUM = new ResourceLocation("ic2:aluminium");
    @NetworkInfo
    public ResourceLocation currentID = EMPTY;
    @NetworkInfo
    public float materialProgress = 0.0f;
    @NetworkInfo
    public float progress = 0.0f;
    public IFilter gui_Filter = T -> this.getRegistry().getOutputFor(T) != null;

    public RareEarthExtractorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2, 1, 35, 1120, 32);
        this.sensitive = false;
        this.setFuelSlot(0);
        this.addGuiFields("currentID", "materialProgress", "progress");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new MachineFilter(this), 1);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.RARE_EARTH_EXTRACTOR;
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1);
        this.inOut[1] = new RangedInventory(this, 2).setOutputOnly();
    }

    @Override
    protected void handleMods() {
    }

    public IRareEarthRegistry getRegistry() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).rare_earth;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.currentID = new ResourceLocation(compound.m_128461_("recipe_id"));
        this.materialProgress = compound.m_128457_("material");
        this.progress = compound.m_128457_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128359_("recipe_id", this.currentID.toString());
        compound.m_128350_("material", this.materialProgress);
        compound.m_128350_("progress", this.progress);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new RareEarthExtractorContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        if (this.addItemsToInventory() && this.canProcess() && this.hasEnergy(this.energyConsume) && !((ItemStack)this.inventory.get(1)).m_41619_()) {
            IRareEarthRegistry.RareEntry entry = this.getRegistry().getOutputFor((ItemStack)this.inventory.get(1));
            if (entry == null || !this.canAdd(entry)) {
                if (this.progress != 0.0f) {
                    this.progress = 0.0f;
                    this.updateGuiField("progress");
                }
                this.setActive(false);
                return;
            }
            if (!entry.matches(this.currentID)) {
                this.currentID = entry.getID();
                this.materialProgress = 0.0f;
                this.updateGuiField("materialProgress");
                this.updateGuiField("currentID");
            }
            this.setActive(true);
            this.progress += this.progressPerTick;
            this.useEnergy(this.energyConsume);
            if (this.progress >= 35.0f) {
                this.progress = 0.0f;
                this.materialProgress += entry.getValue();
                this.updateGuiField("materialProgress");
                ItemStack stack = (ItemStack)this.inventory.get(1);
                if (stack.hasCraftingRemainingItem()) {
                    this.outputs.add(new SimpleStackOutput(stack, 1));
                } else {
                    stack.m_41774_(1);
                }
                if (this.materialProgress >= 1000.0f) {
                    this.materialProgress -= 1000.0f;
                    this.outputs.add(new SimpleStackOutput(entry.getOutput().m_41777_(), 2));
                }
                this.notifyListeners();
                this.storage.onRecipeFinishedPost((NonNullList<ItemStack>)this.inventory, this, null, this.outputs);
            }
            this.updateGuiField("progress");
        } else {
            if (this.progress != 0.0f) {
                this.progress = Math.max(0.0f, ((ItemStack)this.inventory.get(1)).m_41619_() ? 0.0f : this.progress - this.progressPerTick);
                this.updateGuiField("progress");
            }
            this.setActive(false);
        }
        this.storage.onTick((NonNullList<ItemStack>)this.inventory, this);
        this.handleComparators();
    }

    private boolean canAdd(IRareEarthRegistry.RareEntry entry) {
        if (this.materialProgress + entry.getValue() < 1000.0f || ((ItemStack)this.inventory.get(2)).m_41619_()) {
            return true;
        }
        return StackUtil.isStackEqual((ItemStack)this.inventory.get(2), entry.getOutput()) && ((ItemStack)this.inventory.get(2)).m_41613_() < ((ItemStack)this.inventory.get(2)).m_41741_();
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return BasicMachineTileEntity.BASIC_MACHINE_UPGRADES;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return 35.0f;
    }

    @Override
    public float getSubProgress() {
        return this.materialProgress;
    }

    @Override
    public float getMaxSubProgress() {
        return 1000.0f;
    }

    public ItemStack getCurrentOutput() {
        return this.getRegistry().getOutputFor(this.currentID);
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        if (this.getRegistry().getOutputFor(stack) == null) {
            return 0;
        }
        ItemStack inv = (ItemStack)this.inventory.get(1);
        if (inv.m_41619_()) {
            return stack.m_41741_();
        }
        return StackUtil.isStackEqual(inv, stack) ? StackUtil.getStackSizeLeft(inv) : 0;
    }

    public static void loadRecipes(IRareEarthRegistry registry) {
        registry.registerOutput(RARE_EARTH, new ItemStack((ItemLike)IC2Items.DUST_RARE_EARTH));
        registry.registerInput(RARE_EARTH, 3.125f, new ItemStack((ItemLike)Items.f_41905_), new ItemStack((ItemLike)Items.f_151034_));
        registry.registerInput(RARE_EARTH, 62.5f, new ItemStack((ItemLike)Items.f_41958_), new ItemStack((ItemLike)Items.f_42064_), new ItemStack((ItemLike)Items.f_42170_), new ItemStack((ItemLike)Items.f_151048_));
        registry.registerInput(RARE_EARTH, 7.8125f, new ItemStack((ItemLike)Items.f_42048_));
        registry.registerInput(RARE_EARTH, 100.0f, new ItemStack((ItemLike)Items.f_42093_));
        registry.registerInput(RARE_EARTH, 200.0f, new ItemStack((ItemLike)Items.f_41999_), new ItemStack((ItemLike)Items.f_42049_), new ItemStack((ItemLike)Items.f_42050_));
        registry.registerOutput(ALUMINIUM, new ItemStack((ItemLike)IC2Items.DUST_ALUMINIUM));
        registry.registerInput(ALUMINIUM, 500.0f, new ItemStack((ItemLike)Items.f_42461_), new ItemStack((ItemLike)Items.f_42199_), new ItemStack((ItemLike)Items.f_42163_), new ItemStack((ItemLike)Items.f_42164_), new ItemStack((ItemLike)Items.f_42165_), new ItemStack((ItemLike)Items.f_42166_), new ItemStack((ItemLike)Items.f_42167_), new ItemStack((ItemLike)Items.f_42168_), new ItemStack((ItemLike)Items.f_42169_), new ItemStack((ItemLike)Items.f_42118_), new ItemStack((ItemLike)Items.f_42119_), new ItemStack((ItemLike)Items.f_42120_), new ItemStack((ItemLike)Items.f_42121_), new ItemStack((ItemLike)Items.f_42122_), new ItemStack((ItemLike)Items.f_42123_), new ItemStack((ItemLike)Items.f_42124_), new ItemStack((ItemLike)Items.f_42125_), new ItemStack((ItemLike)Items.f_42126_));
        registry.registerInput(ALUMINIUM, 100.0f, new ItemStack((ItemLike)Items.f_42329_));
    }
}

