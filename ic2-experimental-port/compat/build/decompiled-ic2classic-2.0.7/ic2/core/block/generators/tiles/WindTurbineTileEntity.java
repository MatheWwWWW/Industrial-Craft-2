/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.generators.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IAirSpeed;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.WindComparator;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.WindTurbineContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.wind.WindManager;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.geometry.Box;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class WindTurbineTileEntity
extends BaseGeneratorTileEntity
implements IAirSpeed,
IAreaOfEffect {
    public static final AABBUtil.IBlockFilter FILTER = new AABBUtil.IBlockFilter(){

        @Override
        public boolean isValid(LevelReader world, BlockPos pos) {
            return world.m_46859_(pos);
        }

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60795_();
        }
    };
    double freeSpace = 1.0;
    @NetworkInfo
    public SubProduction subproduction = new SubProduction();
    int visualId = -1;

    public WindTurbineTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
        this.production = 16;
        this.maxStorage = 32;
        this.addGuiFields("subproduction");
        this.addComparator(new WindComparator("air_speed", ComparatorNames.AIR_SPEED, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.setSlotAccess(0, Direction.UP, AccessRule.DISABLED);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "sub", this.subproduction.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.subproduction.load(compound.m_128469_("sub"));
    }

    @Override
    public ResourceLocation getProcessingSound() {
        return IC2Sounds.WINDMILL_PROCESSING;
    }

    @Override
    public float getCurrentSpeed() {
        return this.subproduction.getProduction();
    }

    @Override
    public float getMaxSpeed() {
        return 1600.0f;
    }

    @Override
    public int getMaxFuel() {
        return 0;
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(-4.0, -2.0, -4.0, 4.0, 4.0, 4.0).m_82338_(this.f_58858_);
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2140824293;
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
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new WindTurbineContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_WIND_TURBINE;
    }

    @Override
    public boolean needsEnergy() {
        return true;
    }

    @Override
    public boolean gainEnergy() {
        boolean init = this.subproduction.needsInit();
        if (this.clock(128) || init) {
            if (this.clock(1024) || init) {
                this.freeSpace = (double)AABBUtil.countBlocks(this.f_58857_, this.m_58899_(), new Box(-4, -2, -4, 4, 4, 4).offset((Vec3i)this.m_58899_()), FILTER, 0, DirectionList.ALL, 525) / 525.0;
            }
            double value = WindManager.INSTANCE.getAirSpeed(this.m_58904_(), new AABB(-4.0, -2.0, -4.0, 4.0, 4.0, 4.0).m_82338_(this.m_58899_()), this.getFacing().m_122435_(), 90.0f);
            this.subproduction.setProduction((int)(16.0 * (Math.abs(value) / 100.0) * this.freeSpace * 100.0));
            this.updateGuiField("subproduction");
        }
        if (this.storage < this.maxStorage) {
            int passive = IC2.CONFIG.windTurbinePassive.get();
            if (this.subproduction.update(passive)) {
                this.storage += this.subproduction.consume(passive, true);
                this.updateGuiFields("subproduction");
                return true;
            }
            return this.subproduction.getProduction() > 0;
        }
        return false;
    }

    @Override
    public float getEUProduction() {
        return this.subproduction.getProduction(IC2.CONFIG.windTurbinePassive.get());
    }

    @Override
    public boolean gainFuel() {
        return false;
    }
}

