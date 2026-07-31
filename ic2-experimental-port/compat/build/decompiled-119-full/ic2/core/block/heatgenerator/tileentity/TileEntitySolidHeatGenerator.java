/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.heatgenerator.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.tileentity.TileEntityHeatSourceInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.ConfigUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntitySolidHeatGenerator
extends TileEntityHeatSourceInventory
implements IHasGui,
IGuiValueProvider {
    private int heatbuffer = 0;
    public int activityMeter = 0;
    public int ticksSinceLastActiveUpdate;
    @GuiSynced
    public int fuel = 0;
    @GuiSynced
    public int itemFuelTime = 0;
    public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, false);
    public final InvSlotOutput outputslot = new InvSlotOutput(this, "output", 1);
    public static final int emittedHU = Math.round(20.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/solid"));

    public TileEntitySolidHeatGenerator(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityHeatSourceInventory>)Ic2BlockEntities.SOLID_HEAT_GENERATOR, blockPos, blockState);
        this.ticksSinceLastActiveUpdate = IC2.random.m_188503_(256);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean bl = false;
        if (this.needsFuel()) {
            bl = this.gainFuel();
        }
        boolean bl2 = this.gainheat();
        if (bl) {
            this.m_6596_();
        }
        if (!this.delayActiveUpdate()) {
            this.setActive(bl2);
        } else {
            if (this.ticksSinceLastActiveUpdate % 256 == 0) {
                this.setActive(this.activityMeter > 0);
                this.activityMeter = 0;
            }
            this.activityMeter = bl2 ? ++this.activityMeter : --this.activityMeter;
            ++this.ticksSinceLastActiveUpdate;
        }
    }

    public boolean gainheat() {
        if (this.isConverting()) {
            this.heatbuffer += this.getMaxHeatEmittedPerTick();
            --this.fuel;
            if (this.fuel == 0 && (int)(Math.random() * 2.0) == 1) {
                this.outputslot.add(new ItemStack((ItemLike)Ic2Items.ASHES));
            }
            return true;
        }
        return false;
    }

    public boolean needsFuel() {
        return this.fuel <= 0 && this.getHeatBuffer() == 0;
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.fuel = compoundTag.m_128451_("fuel");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("fuel", this.fuel);
    }

    public boolean delayActiveUpdate() {
        return false;
    }

    public boolean gainFuel() {
        if (this.outputslot.canAdd(new ItemStack((ItemLike)Ic2Items.ASHES))) {
            int n = this.fuelSlot.consumeFuel() / 4;
            if (n == 0) {
                return false;
            }
            this.fuel += n;
            this.itemFuelTime = n;
            return true;
        }
        return false;
    }

    public boolean isConverting() {
        return this.fuel > 0;
    }

    @Override
    protected int fillHeatBuffer(int n) {
        if (this.heatbuffer - n >= 0) {
            this.heatbuffer -= n;
            return n;
        }
        n = this.heatbuffer;
        this.heatbuffer = 0;
        return n;
    }

    @Override
    public int getMaxHeatEmittedPerTick() {
        return emittedHU;
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
        if ("fuel".equals(string)) {
            return this.fuel == 0 ? 0.0 : (double)this.fuel / (double)this.itemFuelTime;
        }
        throw new IllegalArgumentException("Unexpected value requested: " + string);
    }
}

