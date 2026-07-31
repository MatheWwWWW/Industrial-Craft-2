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
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generators.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.SlagGenContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.FuelFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SlagGenTileEntity
extends BaseGeneratorTileEntity {
    int scrapCounter = 0;
    boolean scrapFuel;
    @NetworkInfo
    int maxFuel = 0;

    public SlagGenTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
        this.tier = 2;
        this.production = IC2.CONFIG.slagGenOutput.get();
        this.maxStorage = 8000;
        this.scrapCounter = 80 + new Random().nextInt(160);
        this.addGuiFields("maxFuel");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 2);
        handler.registerInputFilter(FuelFilter.WITH_LAVA, 1);
        handler.registerOutputFilter(FuelFilter.NOT_WITH_LAVA, 1);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
        handler.registerNamedSlot(SlotType.FUEL, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "max_fuel", this.maxFuel, 0);
        NBTUtils.putBoolean(compound, "scrap", this.scrapFuel, false);
        NBTUtils.putShort(compound, "scrap_counter", this.scrapCounter, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.maxFuel = compound.m_128451_("max_fuel");
        this.scrapFuel = compound.m_128471_("scrap");
        this.scrapCounter = compound.m_128451_("scrap_counter");
    }

    @Override
    public int getMaxFuel() {
        return this.maxFuel;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new SlagGenContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_SLAG;
    }

    @Override
    public ResourceLocation getProcessingSound() {
        return IC2Sounds.GENERATOR_PROCESSING;
    }

    @Override
    public boolean needsEnergy() {
        return this.fuel > 0;
    }

    @Override
    public float getEUProduction() {
        return this.fuel > 0 ? (float)this.production : 0.0f;
    }

    @Override
    public boolean gainEnergy() {
        if (super.gainEnergy()) {
            if (!this.scrapFuel && --this.scrapCounter <= 0) {
                this.scrapCounter = this.getNextScrapCounter();
                this.setOrGrow(2, new ItemStack((ItemLike)IC2Items.SCRAP), true);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean gainFuel() {
        ItemStack stack = (ItemStack)this.inventory.get(1);
        int newFuel = IC2.RECIPES.get(this.isSimulating()).getFuel(stack, false) / 16;
        if (newFuel > 0) {
            this.scrapFuel = false;
            if (stack.m_41720_() == IC2Items.SCRAP || stack.m_41720_() == IC2Items.SCRAPBOX) {
                newFuel /= 10;
                this.scrapFuel = true;
            }
            this.fuel = newFuel;
            this.maxFuel = newFuel;
            this.updateGuiField("maxFuel");
            if (stack.hasCraftingRemainingItem()) {
                this.inventory.set(1, (Object)stack.getCraftingRemainingItem());
            } else {
                stack.m_41774_(1);
            }
            return true;
        }
        return false;
    }

    public int getNextScrapCounter() {
        return 80 + this.f_58857_.f_46441_.m_188503_(160);
    }
}

