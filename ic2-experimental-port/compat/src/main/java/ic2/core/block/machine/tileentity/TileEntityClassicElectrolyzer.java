package ic2.core.block.machine.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Experimental's classic water electrolyzer plus IC2 Classic's one-way
 * 20,000-EU dead-magnet charging operation. The extra operation resolves the
 * two upstream mods' electrolyzer-id collision without changing the water
 * cell cycle or replacing a machine process with crafting.
 */
public class TileEntityClassicElectrolyzer extends TileEntityBase implements IHasGui {
    private static final double MAGNET_ENERGY = 20_000.0;

    public TileEntityElectricBlock mfe = null;
    public int ticker = IC2.random.m_188503_(16);
    public final InvSlotConsumable waterSlot = new InvSlotConsumable(
            this, "water", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP) {
        @Override
        public boolean accepts(ItemStack stack) {
            Item item = stack.m_41720_();
            return item == Ic2Items.WATER_CELL || item == compatItem("dead_magnet");
        }
    };
    public final InvSlotConsumable hydrogenSlot = new InvSlotConsumable(
            this, "hydrogen", InvSlot.Access.IO, 1, InvSlot.InvSide.BOTTOM) {
        @Override
        public boolean accepts(ItemStack stack) {
            Item item = stack.m_41720_();
            return item == Ic2Items.ELECTROLYZED_WATER_CELL || item == compatItem("magnet");
        }
    };
    @GuiSynced
    protected final Energy energy = addComponent(
            new Energy(this, MAGNET_ENERGY, Util.noFacings, Util.noFacings, 1));

    public TileEntityClassicElectrolyzer(BlockPos pos, BlockState state) {
        super((BlockEntityType<? extends TileEntityInventory>) Ic2BlockEntities.CLASSIC_ELECTROLYZER,
                pos, state);
        comparator.setUpdate(energy::getComparatorValue);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean changed = false;
        boolean active = false;
        if (++ticker % 16 == 0) {
            mfe = lookForMFE();
        }
        if (mfe == null) {
            return;
        }

        if (isDeadMagnet(waterSlot.get())) {
            if (shouldDrain() && canChargeMagnet()) {
                changed = chargeMagnet();
                active = true;
            }
        } else {
            if (shouldDrain() && canDrain()) {
                changed |= drain();
                active = true;
            }
            if (shouldPower() && (canPower() || energy.getEnergy() > 0.0)) {
                changed |= power();
                active = true;
            }
        }
        setActiveState(active);
        if (changed) {
            m_6596_();
        }
    }

    private boolean canChargeMagnet() {
        ItemStack output = hydrogenSlot.get();
        Item magnet = compatItem("magnet");
        return magnet != null && (output.m_41619_()
                || output.m_41720_() == magnet
                && output.m_41613_() < Math.min(hydrogenSlot.getStackSizeLimit(), output.m_41741_()));
    }

    private boolean chargeMagnet() {
        double rate = processRate();
        if (!mfe.energy.useEnergy(rate)) {
            return false;
        }
        energy.addEnergy(rate);
        if (!energy.useEnergy(MAGNET_ENERGY)) {
            return false;
        }
        waterSlot.consume(1);
        Item magnet = compatItem("magnet");
        if (hydrogenSlot.isEmpty()) {
            hydrogenSlot.put(new ItemStack(magnet));
        } else {
            hydrogenSlot.put(StackUtil.incSize(hydrogenSlot.get()));
        }
        return true;
    }

    private static boolean isDeadMagnet(ItemStack stack) {
        Item deadMagnet = compatItem("dead_magnet");
        return deadMagnet != null && !stack.m_41619_() && stack.m_41720_() == deadMagnet;
    }

    private static Item compatItem(String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation("ic2", id));
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_ELECTROLYZER_LOOP;
    }

    public boolean shouldDrain() {
        return mfe != null && mfe.energy.getFillRatio() >= 0.7;
    }

    public boolean shouldPower() {
        return mfe != null && mfe.energy.getFillRatio() <= 0.3;
    }

    public boolean canDrain() {
        return waterSlot.consume(1, true, false) != null
                && (hydrogenSlot.isEmpty()
                || hydrogenSlot.get().m_41720_() == Ic2Items.ELECTROLYZED_WATER_CELL
                && StackUtil.getSize(hydrogenSlot.get())
                < Math.min(hydrogenSlot.getStackSizeLimit(), hydrogenSlot.get().m_41741_()));
    }

    public boolean canPower() {
        return !hydrogenSlot.isEmpty()
                && hydrogenSlot.get().m_41720_() == Ic2Items.ELECTROLYZED_WATER_CELL
                && (waterSlot.isEmpty()
                || waterSlot.get().m_41720_() == Ic2Items.WATER_CELL
                && StackUtil.getSize(waterSlot.get())
                < Math.min(waterSlot.getStackSizeLimit(), waterSlot.get().m_41741_()));
    }

    public boolean drain() {
        double rate = processRate();
        if (!mfe.energy.useEnergy(rate)) {
            return false;
        }
        energy.addEnergy(rate);
        if (energy.useEnergy(20_000.0)) {
            waterSlot.consume(1);
            if (hydrogenSlot.isEmpty()) {
                hydrogenSlot.put(new ItemStack((ItemLike) Ic2Items.ELECTROLYZED_WATER_CELL));
            } else {
                hydrogenSlot.put(StackUtil.incSize(hydrogenSlot.get()));
            }
            return true;
        }
        return false;
    }

    public boolean power() {
        if (energy.getEnergy() > 0.0) {
            double amount = Math.min(energy.getEnergy(), (double) processRate());
            energy.useEnergy(amount);
            mfe.energy.addEnergy(amount);
            return false;
        }
        energy.forceAddEnergy(12_000 + 2_000 * mfe.energy.getSinkTier());
        hydrogenSlot.consume(1);
        if (waterSlot.isEmpty()) {
            waterSlot.put(new ItemStack((ItemLike) Ic2Items.WATER_CELL));
        } else {
            waterSlot.put(StackUtil.incSize(waterSlot.get()));
        }
        return true;
    }

    public int processRate() {
        return switch (mfe.energy.getSinkTier()) {
            default -> 2;
            case 2 -> 8;
            case 3 -> 32;
            case 4 -> 128;
        };
    }

    public TileEntityElectricBlock lookForMFE() {
        Level level = m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity tile = level.m_7702_(f_58858_.m_121945_(direction));
            if (tile instanceof TileEntityElectricBlock storage) {
                return storage;
            }
        }
        return null;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(syncId, inventory, this);
    }

    public double getInternalEnergyForTest() {
        return energy.getEnergy();
    }
}
