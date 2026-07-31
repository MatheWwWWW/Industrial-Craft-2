/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.jetbrains.annotations.NotNull
 */
package ic2.core.block.machines.tiles.luv;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.registries.IFusionRecipeList;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.blocks.ValveBlock;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.features.multiblock.IMultiBlockClickable;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.machines.containers.luv.FusionReactorContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.item.reactor.ReactorUraniumRod;
import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.math.MathUtils;
import ic2.core.utils.math.StructureBuilder;
import ic2.core.utils.math.geometry.Box;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class FusionReactorTileEntity
extends BaseMultiBlockTileEntity
implements IFluidHandler,
ITileGui,
IMultiBlockClickable,
IWrenchRemovable {
    public static final int REQUIRED_LAVA = 10000;
    @NetworkInfo
    public int storedFluid = 0;
    @NetworkInfo
    public int heat = 0;
    @NetworkInfo
    public FuelInfo renderInfo = new FuelInfo(this);
    @NetworkInfo
    public int material = 0;
    @NetworkInfo
    public int maxMaterial = 0;
    @NetworkInfo
    public float productionRate = 1.0f;
    public float producedFuel = 0.0f;
    boolean recalculate = true;
    @NetworkInfo
    float production = 0.0f;
    @NetworkInfo
    int rodCount = 0;
    @NetworkInfo
    int individualRods = 0;

    public FusionReactorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 7);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.addNetworkFields("storedFluid", "renderInfo");
        this.addGuiFields("heat", "material", "maxMaterial", "productionRate", "production", "rodCount", "individualRods");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] array = MathUtils.fromTo(0, 6);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, array);
        handler.registerSlotAccess(AccessRule.IMPORT, 6);
        handler.registerSlotsForSide(DirectionList.ALL, array);
        handler.registerSlotsForSide(DirectionList.ALL, 6);
        handler.registerInputFilter(new ClassFilter(ReactorUraniumRod.class), array);
        handler.registerInputFilter(this::isValidFuel, 6);
        handler.registerNamedSlot(SlotType.INPUT, array);
        handler.registerNamedSlot(SlotType.FUEL, 6);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.55;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FUSION_REACTOR;
    }

    public boolean isValidFuel(ItemStack stack) {
        return IC2.RECIPES.get((boolean)this.isSimulating()).fusionFuel.getFuel(stack.m_41720_()) != null;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("lava", this.storedFluid);
        compound.m_128405_("heat", this.heat);
        compound.m_128405_("material", this.material);
        compound.m_128405_("maxMaterial", this.maxMaterial);
        compound.m_128350_("produced", this.producedFuel);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.storedFluid = compound.m_128451_("lava");
        this.heat = compound.m_128451_("heat");
        this.material = compound.m_128451_("material");
        this.maxMaterial = compound.m_128451_("maxMaterial");
        this.producedFuel = compound.m_128457_("produced");
    }

    private FluidStack create(int amount) {
        return new FluidStack((Fluid)Fluids.f_76195_, amount);
    }

    private int accessible() {
        return Math.max(0, this.storedFluid - 10000);
    }

    public float getMaterial() {
        return this.maxMaterial == 0 ? 0.0f : (float)this.material / (float)this.maxMaterial;
    }

    public AABB getBox(float progress) {
        AABB box = this.boundingBox.toInclusiveAxis().m_82406_(1.0);
        return box.m_82310_(0.0, Math.max((double)0.001f, (box.f_82292_ - box.f_82289_) * (double)(1.0f - progress)), 0.0).m_82386_((double)(-this.f_58858_.m_123341_()), (double)(-this.f_58858_.m_123342_()), (double)(-this.f_58858_.m_123343_()));
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FusionReactorContainer(this, player, windowID);
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.isValid;
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.isValid;
    }

    @Override
    public void onStructureTick() {
        IFusionRecipeList.FusionFuel fuel;
        if (this.invClock(10)) {
            return;
        }
        if (this.recalculate) {
            this.recalculate = false;
            this.rodCount = 0;
            this.production = 0.0f;
            this.individualRods = 0;
            for (int i = 0; i < 6; ++i) {
                ItemStack stack = (ItemStack)this.inventory.get(i);
                Item item = stack.m_41720_();
                if (!(item instanceof ReactorUraniumRod)) continue;
                ReactorUraniumRod comp = (ReactorUraniumRod)item;
                IUraniumRod rod = comp.getUranium();
                this.production += (float)rod.getFusionHeat();
                this.rodCount += comp.getRodCount();
                ++this.individualRods;
            }
            this.production /= (float)Math.max(1, this.individualRods);
            this.updateGuiFields("production", "rodCount", "individualRods");
        }
        if (this.material <= 0 && !((ItemStack)this.inventory.get(6)).m_41619_() && (fuel = this.gainFuel(true)) != null) {
            this.maxMaterial = this.material = fuel.fuelValue();
            this.productionRate = fuel.productionRate();
            this.updateGuiFields("material", "maxMaterial", "productionRate");
        }
        if (this.heat < 48000 && this.production > 2.0f) {
            this.heat = (int)Math.min(48000.0f, (float)this.heat + Math.min(5.0f, this.production - 2.0f) * (float)this.individualRods * 10.0f);
            this.damageRods(this.rodCount / Math.max(1, this.individualRods));
            this.updateGuiField("heat");
        } else if (this.heat == 48000 && this.material > 0 && this.storedFluid < 522000) {
            boolean fullProduction = this.storedFluid >= 10000;
            this.producedFuel += (fullProduction ? this.production : Math.min(5.0f, this.production - 2.0f)) * (float)this.rodCount * this.productionRate * 10.0f / 6.0f;
            int added = Math.min(Math.min(this.material, (int)this.producedFuel), 522000 - this.storedFluid);
            if (added > 0) {
                this.material -= added;
                this.producedFuel -= (float)added;
                this.storedFluid += added;
                if (this.storedFluid < 10000) {
                    this.heat = (int)((float)this.heat - (1.0f - Mth.m_14036_((float)((float)this.storedFluid / 10000.0f), (float)0.0f, (float)1.0f)) * 24000.0f);
                    this.damageRods(this.rodCount / Math.max(1, this.individualRods));
                }
                this.updateGuiFields("material", "storedFluid");
            }
            if (this.clock(200)) {
                this.damageRods(1);
            }
        }
        if (this.clock(this.rodCount == 0 ? 10 : 10240)) {
            if (this.rodCount == 0 && this.heat > 24000 || this.storedFluid <= 0 && this.heat > 0) {
                this.heat = Math.max(this.rodCount == 0 && this.heat > 24000 ? 24000 : 0, this.heat - 10);
                this.updateGuiField("heat");
            } else if (this.storedFluid > 0) {
                --this.storedFluid;
                this.updateTileField("storedFluid");
            }
        }
    }

    protected void damageRods(int amount) {
        for (int i = 0; i < 6; ++i) {
            ItemStack stack = (ItemStack)this.inventory.get(i);
            Item item = stack.m_41720_();
            if (!(item instanceof ReactorUraniumRod)) continue;
            ReactorUraniumRod rod = (ReactorUraniumRod)item;
            int damage = stack.m_41773_();
            if (damage + amount > stack.m_41776_()) {
                this.inventory.set(i, (Object)(this.f_58857_.f_46441_.m_188503_(3) == 0 ? rod.getUranium().createNearDepletedRod(rod.getRodCount()) : ItemStack.f_41583_));
                continue;
            }
            stack.m_41721_(damage + amount);
        }
    }

    public IFusionRecipeList.FusionFuel gainFuel(boolean consume) {
        ItemStack stack = (ItemStack)this.inventory.get(6);
        if (stack.m_41619_()) {
            return null;
        }
        IFusionRecipeList.FusionFuel fuel = IC2.RECIPES.get((boolean)this.isSimulating()).fusionFuel.getFuel(stack.m_41720_());
        if (fuel.consumed() && consume) {
            if (stack.hasCraftingRemainingItem()) {
                this.inventory.set(6, (Object)stack.getCraftingRemainingItem());
            } else {
                stack.m_41774_(1);
            }
        }
        return fuel;
    }

    public long getTicksTillHeatup() {
        if (this.storedFluid >= 10000 || this.production <= 2.0f) {
            return 0L;
        }
        float heatProduction = Math.min(5.0f, this.production - 2.0f) * (float)this.individualRods;
        float lavaProduction = heatProduction * (float)this.rodCount;
        long ticks = (int)((float)(48000 - this.heat) / heatProduction);
        int totalLava = this.storedFluid;
        float lavaProduced = 0.0f;
        while (totalLava < 10000) {
            int consume = (int)(lavaProduced += lavaProduction / 6.0f);
            if (consume <= 0) continue;
            lavaProduced -= (float)consume;
            ticks = (long)((float)ticks + (1.0f - Mth.m_14036_((float)((totalLava += consume) / 10000), (float)0.0f, (float)1.0f)) * 24000.0f / heatProduction);
        }
        return ticks;
    }

    public int getTanks() {
        return 1;
    }

    @NotNull
    public FluidStack getFluidInTank(int tank) {
        return this.create(this.accessible());
    }

    public int getTankCapacity(int tank) {
        return 512000;
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return false;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return 0;
    }

    @NotNull
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.getFluid() != Fluids.f_76195_) {
            return FluidStack.EMPTY;
        }
        int left = Math.min(this.accessible(), resource.getAmount());
        if (left <= 0) {
            return FluidStack.EMPTY;
        }
        if (action.execute()) {
            this.storedFluid -= left;
            this.updateTileField("storedFluid");
        }
        return this.create(left);
    }

    @NotNull
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        int left = Math.min(this.accessible(), maxDrain);
        if (left <= 0) {
            return FluidStack.EMPTY;
        }
        if (action.execute()) {
            this.storedFluid -= left;
            this.updateTileField("storedFluid");
        }
        return this.create(left);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating()) {
            this.updateTileField("renderInfo");
            if (slot < 6) {
                this.recalculate = true;
            }
        }
    }

    private boolean isReinforcedStone(BlockState state) {
        return state.m_60713_(IC2Blocks.REINFORCED_STONE) || state.m_60713_(IC2Blocks.REINFORCED_BRICK) || state.m_60713_(IC2Blocks.REINFORCED_CRACKED_STONE);
    }

    private boolean isReinforcedGlass(BlockState state) {
        return state.m_60713_(IC2Blocks.REINFORCED_GLASS) || state.m_60713_(IC2Blocks.REINFORCED_CLEAR_GLASS);
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        if (!this.boundingBox.isBorder(structurePos)) {
            return newState.m_60795_();
        }
        if (newState.m_60734_() instanceof ValveBlock) {
            return true;
        }
        if (newState.m_60713_(IC2Blocks.FUSION_REACTOR)) {
            return true;
        }
        return this.boundingBox.isEdge(structurePos) || this.boundingBox.getMinY() == structurePos.m_123342_() ? this.isReinforcedStone(newState) : this.isReinforcedGlass(newState);
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        Box box = new Box(0, 0, 0, 4, 4, 4);
        int i = 1;
        while (builder.cubicStructure(i, 5, 5, 5, pos)) {
            if (box.isBorder((BlockPos)pos)) {
                if (builder.isBlock(T -> T instanceof ValveBlock)) {
                    this.children.add(builder.getTile(BaseLinkingTileEntity.class));
                } else {
                    if ((box.isEdge((BlockPos)pos) || pos.m_123342_() == box.getMaxY()) && !builder.isState(this::isReinforcedStone)) {
                        return false;
                    }
                    if (!box.isEdge((BlockPos)pos) && pos.m_123342_() != box.getMaxY() && !builder.isState(this::isReinforcedGlass)) {
                        return false;
                    }
                }
            } else if (!builder.isAir()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public static void loadRecipes(IFusionRecipeList list) {
        list.addFuel(IC2Items.COBBLEGEN_UPGRADE, 1000, 1.0f, false);
        list.addFuel(Items.f_42594_, 1000, 1.0f, true);
        list.addFuel(Items.f_42048_, 1000, 1.5f, true);
    }

    public static class FuelInfo
    implements INetworkDataBuffer {
        final FusionReactorTileEntity tile;
        Block block = Blocks.f_50016_;
        int[] colors = new int[12];

        public FuelInfo(FusionReactorTileEntity tile) {
            this.tile = tile;
        }

        public boolean isVisible(Direction dir) {
            return this.colors[6 + dir.m_122411_()] > 0;
        }

        public int getColor(Direction dir) {
            return this.colors[dir.m_122411_()];
        }

        public int getCount(Direction dir) {
            return this.colors[6 + dir.m_122411_()];
        }

        @Override
        public void write(IOutputBuffer buffer) {
            for (int i = 0; i < 6; ++i) {
                ItemStack stack = this.tile.getStackInSlot(i);
                Item item = stack.m_41720_();
                if (item instanceof ReactorUraniumRod) {
                    ReactorUraniumRod comp = (ReactorUraniumRod)item;
                    IUraniumRod rod = comp.getUranium();
                    buffer.writeInt(rod.getColor());
                    buffer.writeVarInt(comp.getRodCount());
                    continue;
                }
                buffer.writeInt(-1);
                buffer.writeVarInt(0);
            }
            ItemStack stack = this.tile.getStackInSlot(6);
            Block block = stack.m_150930_(IC2Items.COBBLEGEN_UPGRADE) ? Blocks.f_50652_ : Block.m_49814_((Item)stack.m_41720_());
            buffer.writeForgeEntry(block == null ? Blocks.f_50016_ : block, ForgeRegistries.BLOCKS);
        }

        @Override
        public void read(IInputBuffer buffer) {
            for (int i = 0; i < 6; ++i) {
                this.colors[i] = buffer.readInt();
                this.colors[i + 6] = buffer.readVarInt();
            }
            this.block = (Block)buffer.readForgeRegistryEntry(ForgeRegistries.BLOCKS);
        }

        public BlockState getMeltingBlock() {
            return this.block.m_49966_();
        }
    }

    public static class Tank
    implements IFluidTank {
        FusionReactorTileEntity tile;
        boolean heatTank;

        public Tank(FusionReactorTileEntity tile, boolean heatTank) {
            this.tile = tile;
            this.heatTank = heatTank;
        }

        public FluidStack getFluid() {
            return new FluidStack((Fluid)Fluids.f_76195_, this.heatTank ? Math.min(10000, this.tile.storedFluid) : Math.max(0, this.tile.storedFluid - 10000));
        }

        public int getFluidAmount() {
            return this.heatTank ? Math.min(10000, this.tile.storedFluid) : Math.max(0, this.tile.storedFluid - 10000);
        }

        public int getCapacity() {
            return this.heatTank ? 10000 : this.tile.getTankCapacity(0);
        }

        public boolean isFluidValid(FluidStack stack) {
            return false;
        }

        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return 0;
        }

        @NotNull
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }

        @NotNull
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }
    }
}

