/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.planner.encoder.impl;

import ic2.core.block.machines.logic.planner.IReactorProvider;
import ic2.core.block.machines.logic.planner.PlannerRegistry;
import ic2.core.block.machines.logic.planner.encoder.ByteShifter;
import ic2.core.block.machines.logic.planner.encoder.IEncoder;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.logic.planner.newLogic.ReactorInventory;
import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.block.machines.logic.planner.newLogic.SimulationResult;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class TalonEncoder
implements IEncoder {
    @Override
    public CompoundTag createDecodedData(String data) {
        if (!data.startsWith("http://www.talonfiremage.pwp.blueyonder.co.uk/v3/reactorplanner.html")) {
            return null;
        }
        if (!data.contains("?")) {
            return null;
        }
        ByteShifter shift = new ByteShifter(data.substring(data.indexOf(63) + 1));
        CompoundTag nbt = new CompoundTag();
        nbt.m_128405_("Heat", shift.readInt(10) * 100);
        ListTag list = new ListTag();
        for (int x = 8; x >= 0; --x) {
            for (int y = 5; y >= 0; --y) {
                int nextType = shift.readInt(7);
                if (nextType == 0) continue;
                int stackSize = 1;
                if (nextType > 64) {
                    stackSize = nextType - 64 + 1;
                    nextType = shift.readInt(7);
                }
                CompoundTag nbtData = new CompoundTag();
                nbtData.m_128405_("Slot", y * 9 + x);
                nbtData.m_128376_("ID", this.convertID(nextType));
                nbtData.m_128344_("StackSize", (byte)stackSize);
                list.add((Object)nbtData);
            }
        }
        nbt.m_128365_("ReactorData", (Tag)list);
        nbt.m_128344_("Size", (byte)shift.readInt(3));
        return nbt;
    }

    @Override
    public ReactorSetup createSetup(String data, boolean simulate) {
        CompoundTag mappedData = this.createDecodedData(data);
        if (mappedData == null) {
            return null;
        }
        ReactorLogic logic = new ReactorLogic(IReactorProvider.dummy(mappedData.m_128451_("Size"), false));
        ReactorInventory inventory = new ReactorInventory((mappedData.m_128451_("Size") + 3) * 6, logic);
        boolean valid = false;
        ListTag list = mappedData.m_128437_("ReactorData", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbt = list.m_128728_(i);
            ItemStack stack = PlannerRegistry.getByID(nbt.m_128448_("ID"));
            if (stack.m_41619_()) continue;
            int slot = nbt.m_128451_("Slot");
            stack = PlannerRegistry.setStackSize(stack.m_41777_(), nbt.m_128451_("StackSize"));
            logic.setStackInSlot(slot, stack.m_41777_());
            valid = true;
        }
        if (!valid) {
            return null;
        }
        logic.validateLogic();
        ReactorSetup setup = this.finishSetup(logic.prediction, false, mappedData.m_128451_("Size"), mappedData.m_128451_("Heat"));
        setup.setup = data;
        setup.items = inventory;
        if (simulate) {
            SimulationResult sim = new SimulationResult();
            logic.simulate(sim);
            setup.simulation = sim;
        }
        return setup;
    }

    @Override
    public void processData(CompoundTag decodedData, ReactorPlannerTileEntity planner) {
        planner.setSteamReactor(false);
        planner.setReactorSize(decodedData.m_128451_("Size"));
        boolean valid = false;
        ListTag list = decodedData.m_128437_("ReactorData", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbt = list.m_128728_(i);
            ItemStack stack = PlannerRegistry.getByID(nbt.m_128448_("ID"));
            if (stack.m_41619_()) continue;
            int slot = nbt.m_128451_("Slot");
            planner.components.setStackInSlot(slot, PlannerRegistry.setStackSize(stack.m_41777_(), nbt.m_128451_("StackSize")));
            valid = true;
        }
        if (valid) {
            planner.getSettings().startingHeat = decodedData.m_128451_("Heat");
            planner.simulation.validateLogic();
        }
    }

    @Override
    public String createEncodedData(ReactorPlannerTileEntity planner) {
        ByteShifter shifter = new ByteShifter();
        shifter.writeInteger(0, 16);
        shifter.writeInteger(planner.getReactorSize(), 3);
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 6; ++y) {
                ItemStack stack = planner.components.getStackInSlot(y * 9 + x);
                if (stack.m_41619_()) {
                    shifter.writeInteger(0, 7);
                    continue;
                }
                int id = this.convertBack(PlannerRegistry.getId(stack));
                shifter.writeInteger(id, 7);
                int count = PlannerRegistry.getStackSize(stack);
                if (count <= 1) continue;
                shifter.writeInteger(64 + (count - 1), 7);
            }
        }
        shifter.writeInteger(planner.getSettings().startingHeat / 100, 10);
        return this.getURL() + "?" + shifter.getDataCode();
    }

    @Override
    public String getName() {
        return "Talon";
    }

    @Override
    public boolean hasBitLimit() {
        return true;
    }

    @Override
    public int getBitLimit() {
        return 7;
    }

    public short convertID(int id) {
        switch (id) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 36;
            }
            case 5: {
                return 34;
            }
            case 6: {
                return 35;
            }
            case 7: {
                return 18;
            }
            case 8: {
                return 19;
            }
            case 9: {
                return 21;
            }
            case 10: {
                return 20;
            }
            case 11: {
                return 26;
            }
            case 12: {
                return 15;
            }
            case 13: {
                return 16;
            }
            case 14: {
                return 27;
            }
            case 15: {
                return 28;
            }
            case 16: {
                return 29;
            }
            case 17: {
                return 30;
            }
            case 18: {
                return 31;
            }
            case 19: {
                return 33;
            }
            case 20: {
                return 32;
            }
            case 21: {
                return 12;
            }
            case 22: {
                return 13;
            }
            case 23: {
                return 14;
            }
            case 24: {
                return 17;
            }
        }
        return -1;
    }

    public int convertBack(short id) {
        switch (id) {
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
            case 2: {
                return 3;
            }
            case 36: {
                return 4;
            }
            case 34: {
                return 5;
            }
            case 35: {
                return 6;
            }
            case 18: {
                return 7;
            }
            case 19: {
                return 8;
            }
            case 21: {
                return 9;
            }
            case 20: {
                return 10;
            }
            case 26: {
                return 11;
            }
            case 15: {
                return 12;
            }
            case 16: {
                return 13;
            }
            case 27: {
                return 14;
            }
            case 28: {
                return 15;
            }
            case 29: {
                return 16;
            }
            case 30: {
                return 17;
            }
            case 31: {
                return 18;
            }
            case 33: {
                return 19;
            }
            case 32: {
                return 20;
            }
            case 12: {
                return 21;
            }
            case 13: {
                return 22;
            }
            case 14: {
                return 23;
            }
            case 17: {
                return 24;
            }
        }
        return 0;
    }

    public String getURL() {
        return "http://www.talonfiremage.pwp.blueyonder.co.uk/v3/reactorplanner.html";
    }
}

