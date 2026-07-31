/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.block.generators.tiles;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.WaterGenContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.config.ic2.PassiveGeneratorSetting;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class WaterMillTileEntity
extends BaseGeneratorTileEntity
implements IClickable,
IAreaOfEffect {
    public static final AABBUtil.IBlockFilter WATER_FILTER = T -> T.m_60819_().m_76152_() == Fluids.f_76193_;
    public static final int MAX_FUEL = 2000;
    public SubProduction water = new SubProduction();
    int visualId = -1;

    public WaterMillTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
        this.production = this.getSetting().getProduction();
        this.maxStorage = 2;
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 1, 2);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerInputFilter(new FluidFilter(new Fluid[]{Fluids.f_76193_}), 1);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
        handler.registerNamedSlot(SlotType.FUEL, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "water", this.water.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.water.load(compound.m_128469_("water"));
    }

    protected PassiveGeneratorSetting getSetting() {
        return IC2.CONFIG.waterMill;
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(this.f_58858_).m_82400_(1.0);
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2146005810;
    }

    @Override
    public void setVisualizationId(int id) {
        this.visualId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.visualId;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        FluidTank tank;
        ItemStack stack = player.m_21120_(hand);
        if (!stack.m_41619_() && FluidHelper.drainContainers(stack, player, (IFluidHandler)(tank = new FluidTank((2000 - this.fuel) * 2, T -> T.getFluid() == Fluids.f_76193_)))) {
            this.fuel += tank.getFluidAmount() / 2;
            return true;
        }
        return false;
    }

    @Override
    public int getMaxFuel() {
        return 2000;
    }

    @Override
    public ResourceLocation getProcessingSound() {
        return IC2Sounds.WATERMILL_PROCESSING;
    }

    @Override
    protected boolean isAudioActive() {
        return super.isAudioActive() && this.fuel > 1;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new WaterGenContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_WATER_MILL;
    }

    @Override
    public boolean gainEnergy() {
        boolean gain = super.gainEnergy();
        return this.water.getProduction() > 0 || gain;
    }

    @Override
    public boolean hasDelayedActivity() {
        return true;
    }

    @Override
    public boolean needsFuel() {
        return true;
    }

    @Override
    public float getEUProduction() {
        return this.fuel > 1 ? (float)this.production : this.water.getProduction(this.getSetting().getPassiveProduction()) * (float)this.production;
    }

    @Override
    public boolean gainFuel() {
        if (this.fuel <= 1500 && !((ItemStack)this.inventory.get(1)).m_41619_()) {
            FluidTank tank = new FluidTank((2000 - this.fuel) * 2, T -> T.getFluid() == Fluids.f_76193_);
            if (FluidHelper.drainContainers(this, 1, 2, (IFluidHandler)tank)) {
                this.fuel += tank.getFluidAmount() / 2;
            }
            return true;
        }
        if (this.fuel <= 0) {
            this.flowPower();
            return true;
        }
        return false;
    }

    protected void flowPower() {
        int amount;
        if (this.clock(128)) {
            this.updateWaterCount();
        }
        if (this.water.update(amount = this.getSetting().getPassiveProduction())) {
            this.fuel += this.water.consume(amount, true);
        }
    }

    public void updateWaterCount() {
        if (this.isAreaLoaded(1)) {
            this.water.setProduction(AABBUtil.countBlocks(this.m_58904_(), this.m_58899_(), 1, WATER_FILTER, 0, DirectionList.ALL) * 2);
        }
    }

    public static class HVWaterMillTileEntity
    extends WaterMillTileEntity {
        public HVWaterMillTileEntity(BlockPos pos, BlockState state) {
            super(pos, state);
            this.maxStorage = 2048;
        }

        @Override
        protected PassiveGeneratorSetting getSetting() {
            return IC2.CONFIG.hvWaterMill;
        }

        @Override
        public double getDropRate(Player player) {
            return 1.0;
        }

        @Override
        public AABB getAreaOfEffect() {
            return new AABB(this.f_58858_).m_82400_(2.0);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.GENERATOR_WATER_MILL_HV;
        }

        @Override
        public void updateWaterCount() {
            if (this.isAreaLoaded(2)) {
                this.water.setProduction(Math.min(45, (int)(0.4 * (double)AABBUtil.countBlocks(this.m_58904_(), this.m_58899_(), 2, WATER_FILTER, 0, DirectionList.ALL))));
            }
        }

        @Override
        public boolean isHarvestWrenchRequired(Player player) {
            return false;
        }
    }

    public static class MVWaterMillTileEntity
    extends WaterMillTileEntity {
        public MVWaterMillTileEntity(BlockPos pos, BlockState state) {
            super(pos, state);
            this.maxStorage = 256;
        }

        @Override
        protected PassiveGeneratorSetting getSetting() {
            return IC2.CONFIG.mvWaterMill;
        }

        @Override
        public double getDropRate(Player player) {
            return 1.0;
        }

        @Override
        public AABB getAreaOfEffect() {
            return new AABB(this.f_58858_).m_82400_(2.0);
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.GENERATOR_WATER_MILL_MV;
        }

        @Override
        public void updateWaterCount() {
            if (this.isAreaLoaded(2)) {
                this.water.setProduction(Math.min(58, (int)(0.55 * (double)AABBUtil.countBlocks(this.m_58904_(), this.m_58899_(), 2, WATER_FILTER, 0, DirectionList.ALL))));
            }
        }

        @Override
        public boolean isHarvestWrenchRequired(Player player) {
            return false;
        }
    }

    public static class LVWaterMillTileEntity
    extends WaterMillTileEntity {
        public LVWaterMillTileEntity(BlockPos pos, BlockState state) {
            super(pos, state);
            this.maxStorage = 32;
        }

        @Override
        protected PassiveGeneratorSetting getSetting() {
            return IC2.CONFIG.lvWaterMill;
        }

        @Override
        public double getDropRate(Player player) {
            return 1.0;
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.GENERATOR_WATER_MILL_LV;
        }

        @Override
        public void updateWaterCount() {
            if (this.isAreaLoaded(1)) {
                this.water.setProduction((int)(0.75 * (double)AABBUtil.countBlocks(this.m_58904_(), this.m_58899_(), 1, WATER_FILTER, 0, DirectionList.ALL) * 2.0));
            }
        }

        @Override
        public boolean isHarvestWrenchRequired(Player player) {
            return false;
        }
    }
}

