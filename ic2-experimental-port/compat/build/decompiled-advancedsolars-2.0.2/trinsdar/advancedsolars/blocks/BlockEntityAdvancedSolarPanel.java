/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.api.energy.tile.IEnergySource
 *  ic2.api.items.electric.ElectricItem
 *  ic2.api.tiles.readers.IEUProducer
 *  ic2.api.util.DirectionList
 *  ic2.core.block.base.features.ITickListener
 *  ic2.core.block.base.features.ITileActivityProvider
 *  ic2.core.block.base.features.IWrenchableTile
 *  ic2.core.block.base.misc.comparator.BaseComparator
 *  ic2.core.block.base.misc.comparator.ComparatorNames
 *  ic2.core.block.base.misc.comparator.types.base.FlagComparator
 *  ic2.core.block.base.tiles.BaseTileEntity
 *  ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity
 *  ic2.core.inventory.base.ITileGui
 *  ic2.core.inventory.container.IC2Container
 *  ic2.core.inventory.filter.special.ElectricItemFilter
 *  ic2.core.inventory.handler.AccessRule
 *  ic2.core.inventory.handler.InventoryHandler
 *  ic2.core.inventory.handler.SlotType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.Biome$Precipitation
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package trinsdar.advancedsolars.blocks;

import ic2.api.energy.tile.IEnergySource;
import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import trinsdar.advancedsolars.blocks.ContainerAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;
import trinsdar.advancedsolars.util.Registry;

public class BlockEntityAdvancedSolarPanel
extends BaseGeneratorTileEntity
implements ITickListener,
IEnergySource,
ITileGui,
IWrenchableTile,
IEUProducer,
ITileActivityProvider {
    Supplier<Double> config;
    int ticker;
    protected int lowerProduction;
    int maxOutput;
    boolean day = true;

    public BlockEntityAdvancedSolarPanel(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.tier = 1;
        this.ticker = 127;
        this.production = 16;
        this.lowerProduction = 2;
        this.maxStorage = 32000;
        this.maxOutput = 32;
        this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.ADVANCED_SOLAR_GENERATION_MULTIPLIER;
        this.addComparator((BaseComparator)FlagComparator.createTile((String)"active", (Component)ComparatorNames.ACTIVE, (BaseTileEntity)this));
        this.addGuiFields(new String[]{"day"});
    }

    public BlockEntityType<?> createType() {
        return Registry.ADVANCED_SOLAR_PANEL_TYPE;
    }

    protected void addSlots(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.UP.invert());
        handler.registerBlockAccess(DirectionList.UP.invert(), AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, new int[]{0, 1, 2, 3});
        for (int i = 0; i < 4; ++i) {
            handler.setSlotAccess(i, Direction.UP, AccessRule.DISABLED);
        }
        handler.registerSlotsForSide(DirectionList.UP.invert(), new int[]{0, 1, 2, 3});
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, new int[]{0, 1, 2, 3});
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, new int[]{0, 1, 2, 3});
        handler.registerNamedSlot(SlotType.CHARGE, new int[]{0, 1, 2, 3});
    }

    public IC2Container createContainer(Player player, InteractionHand interactionHand, Direction direction, int i) {
        return new ContainerAdvancedSolarPanel(this, player, i);
    }

    public int getMaxOutput() {
        return this.maxOutput;
    }

    public boolean isConverting() {
        if (this.skyBlockCheck()) {
            if (this.isSunVisible()) {
                return this.storage + this.production <= this.maxStorage;
            }
            return this.storage + this.lowerProduction <= this.maxStorage;
        }
        return false;
    }

    public boolean gainFuel() {
        return false;
    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            if (this.skyBlockCheck()) {
                this.storage = this.isSunVisible() ? (this.storage += this.production) : (this.storage += this.lowerProduction);
            }
            return true;
        }
        return false;
    }

    public void onTick() {
        if (this.clock(64)) {
            this.day = this.isSunVisible();
            this.updateGuiField("day");
        }
        int oldEnergy = this.storage;
        boolean active = this.gainEnergy();
        if (this.storage > 0) {
            for (ItemStack tStack : this.inventory) {
                if (this.storage <= 0) break;
                if (tStack.m_41619_()) continue;
                int charged = ElectricItem.MANAGER.charge(tStack, this.storage, this.tier, false, false);
                this.storage -= charged;
            }
            if (this.storage > this.maxStorage) {
                this.storage = this.maxStorage;
            }
        }
        this.setActive(active);
        if (oldEnergy != this.storage) {
            this.updateGuiField("storage");
        }
        this.handleComparators();
    }

    public int getOutput() {
        if (this.skyBlockCheck()) {
            if (this.day) {
                return (int)((double)this.production * this.config.get());
            }
            return (int)((double)this.lowerProduction * this.config.get());
        }
        return 0;
    }

    public boolean skyBlockCheck() {
        return this.m_58904_().m_46861_(this.getPosition().m_7494_()) && this.m_58904_().m_6042_().f_223549_();
    }

    public boolean isSunVisible() {
        return BlockEntityAdvancedSolarPanel.isSunVisible(Objects.requireNonNull(this.f_58857_), this.getPosition().m_7494_());
    }

    public static boolean isSunVisible(@NotNull Level world, BlockPos pos) {
        if (!world.m_46461_()) {
            return false;
        }
        Holder biome = world.m_204166_(pos);
        return ((Biome)biome.get()).m_47530_() == Biome.Precipitation.NONE || !world.m_46471_() && !world.m_46470_();
    }

    public boolean isDay() {
        return this.day;
    }

    public double getDropRate(Player player) {
        return 1.0;
    }

    public float getEUProduction() {
        if (this.isSunVisible()) {
            return Math.min(this.storage, this.production);
        }
        return Math.min(this.storage, this.lowerProduction);
    }

    protected void consumeFuel() {
    }

    public boolean needsFuel() {
        return false;
    }

    public int getMaxFuel() {
        return 0;
    }

    public static class BlockEntityUltimateHybridSolarPanel
    extends BlockEntityAdvancedSolarPanel {
        public BlockEntityUltimateHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.tier = 4;
            this.production = 1024;
            this.lowerProduction = 128;
            this.maxStorage = 1000000;
            this.maxOutput = 2048;
            this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.ULTIMATE_HYBRID_SOLAR_PANEL_TYPE;
        }
    }

    public static class BlockEntityHybridSolarPanel
    extends BlockEntityAdvancedSolarPanel {
        public BlockEntityHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.tier = 2;
            this.production = 128;
            this.lowerProduction = 16;
            this.maxStorage = 100000;
            this.maxOutput = 128;
            this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.HYBRID_SOLAR_GENERATION_MULTIPLIER;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.HYBRID_SOLAR_PANEL_TYPE;
        }
    }
}

