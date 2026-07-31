/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMaps
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntArrayTag
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
import ic2.core.inventory.inv.SimpleInventory;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class V1Encoder
implements IEncoder {
    @Override
    public CompoundTag createDecodedData(String data) {
        ByteShifter shifter = new ByteShifter(data);
        if (shifter.readInt(6) != 63) {
            return null;
        }
        if (shifter.readInt(5) != 1) {
            return null;
        }
        CompoundTag nbt = new CompoundTag();
        nbt.m_128379_("Steam", shifter.readBoolean());
        nbt.m_128405_("Size", shifter.readInt(3));
        nbt.m_128405_("MaxHeat", shifter.readInt(20));
        ListTag list = new ListTag();
        int expectedNameLength = shifter.readInt(6);
        StringBuilder name = new StringBuilder(63);
        for (int i = expectedNameLength; i > 0; --i) {
            name.append((char)shifter.readChar());
        }
        nbt.m_128359_("PlanName", name.reverse().toString());
        int expectedTypes = shifter.readInt(6);
        for (int i = 0; i < expectedTypes; ++i) {
            CompoundTag entry = new CompoundTag();
            entry.m_128376_("ID", (short)shifter.readInt(this.getBitLimit()));
            ListTag slotList = new ListTag();
            int expectedSlots = shifter.readInt(6);
            for (int slot = 0; slot < expectedSlots; ++slot) {
                int currentSlot = shifter.readInt(6);
                int stackSize = shifter.readInt(6) + 1;
                slotList.add((Object)new IntArrayTag(new int[]{currentSlot, stackSize}));
            }
            entry.m_128365_("Slots", (Tag)slotList);
            list.add((Object)entry);
        }
        nbt.m_128365_("Data", (Tag)list);
        return nbt;
    }

    @Override
    public ReactorSetup createSetup(String data, boolean simulate) {
        CompoundTag mappedData = this.createDecodedData(data);
        if (mappedData == null) {
            return null;
        }
        ReactorLogic logic = new ReactorLogic(IReactorProvider.dummy(mappedData.m_128451_("Size"), mappedData.m_128471_("Steam")));
        ReactorInventory inventory = new ReactorInventory((mappedData.m_128451_("Size") + 3) * 6, logic);
        boolean valid = false;
        ListTag list = mappedData.m_128437_("Data", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbt = list.m_128728_(i);
            ItemStack stack = PlannerRegistry.getByID(nbt.m_128448_("ID"));
            if (stack.m_41619_()) continue;
            ListTag slotList = nbt.m_128437_("Slots", 11);
            for (int x = 0; x < slotList.size(); ++x) {
                int[] array = slotList.m_128767_(x);
                stack = PlannerRegistry.setStackSize(stack.m_41777_(), array[1]);
                logic.setStackInSlot(array[0], stack);
                ((SimpleInventory)inventory).setStackInSlot(array[0], stack);
                valid = true;
            }
        }
        if (!valid) {
            return null;
        }
        logic.validateLogic();
        ReactorSetup setup = this.finishSetup(logic.prediction, logic.isSteamReactor(), mappedData.m_128451_("Size"), mappedData.m_128451_("MaxHeat"));
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
        planner.setSteamReactor(decodedData.m_128471_("Steam"));
        planner.setReactorSize(decodedData.m_128451_("Size"));
        planner.setupName = decodedData.m_128461_("PlanName");
        planner.getSettings().startingHeat = decodedData.m_128451_("MaxHeat");
        ListTag list = decodedData.m_128437_("Data", 10);
        boolean valid = false;
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbt = list.m_128728_(i);
            ItemStack stack = PlannerRegistry.getByID(nbt.m_128448_("ID"));
            if (stack.m_41619_()) continue;
            ListTag slotList = nbt.m_128437_("Slots", 11);
            for (int x = 0; x < slotList.size(); ++x) {
                int[] array = slotList.m_128767_(x);
                planner.components.setStackInSlot(array[0], PlannerRegistry.setStackSize(stack.m_41777_(), array[1]));
                valid = true;
            }
        }
        if (valid) {
            planner.simulation.validateLogic();
        }
    }

    @Override
    public String createEncodedData(ReactorPlannerTileEntity planner) {
        ByteShifter shifter = new ByteShifter();
        Short2ObjectLinkedOpenHashMap storage = new Short2ObjectLinkedOpenHashMap();
        for (int i = 0; i < 54; ++i) {
            short id;
            ItemStack stack = planner.components.getStackInSlot(i);
            if (stack.m_41619_() || (id = PlannerRegistry.getId(stack)) >> this.getBitLimit() > 0) continue;
            IntList list = (IntList)storage.get(id);
            if (list == null) {
                list = new IntArrayList();
                storage.put(id, (Object)list);
            }
            list.add(i);
        }
        for (Short2ObjectMap.Entry entry : Short2ObjectMaps.fastIterable((Short2ObjectMap)storage)) {
            IntListIterator intListIterator = ((IntList)entry.getValue()).iterator();
            while (intListIterator.hasNext()) {
                int slot = (Integer)intListIterator.next();
                ItemStack stack = planner.components.getStackInSlot(slot);
                if (stack.m_41619_()) continue;
                shifter.writeInteger(PlannerRegistry.getStackSize(stack) - 1, 6);
                shifter.writeInteger(slot, 6);
            }
            shifter.writeInteger(((IntList)entry.getValue()).size(), 6);
            shifter.writeInteger(entry.getShortKey(), this.getBitLimit());
        }
        shifter.writeInteger(storage.size(), 6);
        char[] chars = planner.setupName.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            shifter.writeCharacter(chars[i]);
        }
        shifter.writeInteger(chars.length, 6);
        shifter.writeInteger(Math.min(planner.simulation.maxHeat - 100, planner.getSettings().startingHeat), 20);
        shifter.writeInteger(planner.getReactorSize(), 3);
        shifter.writeBoolean(planner.isSteamReactor());
        shifter.writeInteger(1, 5);
        shifter.writeInteger(63, 6);
        return shifter.getDataCode();
    }

    @Override
    public String getName() {
        return "V1 Encoder";
    }

    @Override
    public boolean hasBitLimit() {
        return true;
    }

    @Override
    public int getBitLimit() {
        return 10;
    }
}

