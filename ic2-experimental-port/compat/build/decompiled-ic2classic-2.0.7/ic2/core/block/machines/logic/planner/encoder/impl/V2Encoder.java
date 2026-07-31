/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.HashCommon
 *  it.unimi.dsi.fastutil.shorts.ShortArrayList
 *  it.unimi.dsi.fastutil.shorts.ShortCollection
 *  it.unimi.dsi.fastutil.shorts.ShortOpenHashSet
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
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import java.util.Arrays;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class V2Encoder
implements IEncoder {
    @Override
    public CompoundTag createDecodedData(String data) {
        ByteShifter shifter = new ByteShifter(data);
        if (shifter.readInt(2) != 2) {
            return null;
        }
        CompoundTag nbt = new CompoundTag();
        nbt.m_128379_("steam", shifter.readBoolean());
        nbt.m_128405_("size", shifter.readInt(3));
        nbt.m_128405_("max_heat", shifter.readBoolean() ? shifter.readInt(shifter.readBoolean() ? 20 : 15) : 0);
        StringBuilder name = new StringBuilder(63);
        for (int i = shifter.readInt(6); i > 0; --i) {
            name.append((char)shifter.readChar());
        }
        nbt.m_128359_("name", name.reverse().toString());
        ShortArrayList list = new ShortArrayList();
        int idBits = shifter.readInt(4) + 1;
        int m = shifter.readInt(6);
        for (int i = 0; i < m; ++i) {
            list.add((short)shifter.readInt(idBits));
        }
        ListTag slotList = new ListTag();
        int bits = shifter.readInt(4) + 1;
        int m2 = shifter.readInt(9);
        for (int x = 8; x >= 0; --x) {
            if ((m2 & 1 << x) == 0) continue;
            int n = shifter.readInt(6);
            for (int y = 5; y >= 0; --y) {
                if ((n & 1 << y) == 0) continue;
                short id = list.getShort(shifter.readInt(bits));
                int size = shifter.readBoolean() ? shifter.readInt(6) + 1 : 1;
                slotList.add((Object)new IntArrayTag(new int[]{x, y, id, size}));
            }
        }
        nbt.m_128365_("items", (Tag)slotList);
        return nbt;
    }

    @Override
    public ReactorSetup createSetup(String data, boolean simulate) {
        CompoundTag mappedData = this.createDecodedData(data);
        if (mappedData == null) {
            return null;
        }
        int size = mappedData.m_128451_("size");
        ReactorLogic logic = new ReactorLogic(IReactorProvider.dummy(size, mappedData.m_128471_("steam")));
        ReactorInventory inventory = new ReactorInventory(54, logic);
        boolean valid = false;
        for (IntArrayTag nbt : NBTListWrapper.wrap(mappedData.m_128437_("items", 11), IntArrayTag.class)) {
            int[] array = nbt.m_128648_();
            ItemStack stack = PlannerRegistry.getByID(array[2]);
            if (stack.m_41619_()) continue;
            ((SimpleInventory)inventory).setStackInSlot(array[1] * 9 + array[0], PlannerRegistry.setStackSize(stack.m_41777_(), array[3]));
            valid = true;
        }
        if (!valid) {
            return null;
        }
        logic.validateLogic();
        ReactorSetup setup = this.finishSetup(logic.prediction, logic.isSteamReactor(), size, mappedData.m_128451_("max_heat"));
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
        planner.setSteamReactor(decodedData.m_128471_("steam"));
        planner.setReactorSize(decodedData.m_128451_("size"));
        planner.setupName = decodedData.m_128461_("name");
        planner.getSettings().startingHeat = decodedData.m_128451_("max_heat");
        boolean valid = false;
        for (IntArrayTag nbt : NBTListWrapper.wrap(decodedData.m_128437_("items", 11), IntArrayTag.class)) {
            int[] array = nbt.m_128648_();
            ItemStack stack = PlannerRegistry.getByID(array[2]);
            if (stack.m_41619_()) continue;
            planner.components.setStackInSlot(array[1] * 9 + array[0], PlannerRegistry.setStackSize(stack.m_41777_(), array[3]));
            valid = true;
        }
        if (valid) {
            planner.simulation.validateLogic();
        }
    }

    @Override
    public String createEncodedData(ReactorPlannerTileEntity planner) {
        short[] slotToId = new short[54];
        Arrays.fill(slotToId, (short)-1);
        int largestId = 0;
        ShortOpenHashSet storage = new ShortOpenHashSet();
        for (int i = 0; i < 54; ++i) {
            short id;
            ItemStack stack = planner.components.getStackInSlot(i);
            if (stack.m_41619_() || (id = PlannerRegistry.getId(stack)) >> this.getBitLimit() > 0) continue;
            storage.add(id);
            slotToId[i] = id;
            largestId = Math.max(id, largestId);
        }
        ShortArrayList list = new ShortArrayList((ShortCollection)storage);
        int bits = Integer.bitCount(HashCommon.nextPowerOfTwo((int)(list.size() + 1)) - 1);
        ByteShifter shifter = new ByteShifter();
        int rows = 0;
        int m = planner.getReactorSize() + 3;
        for (int x = 0; x < m; ++x) {
            int count = 0;
            for (int y = 0; y < 6; ++y) {
                short id;
                ItemStack stack = planner.components.getStackInSlot(y * 9 + x);
                if (stack.m_41619_() || (id = slotToId[y * 9 + x]) < 0) continue;
                int size = PlannerRegistry.getStackSize(stack);
                if (size > 1) {
                    shifter.writeInteger(size - 1, 6);
                }
                shifter.writeBoolean(size > 1);
                shifter.writeInteger(list.indexOf(id), bits);
                count |= 1 << y;
            }
            if (count <= 0) continue;
            shifter.writeInteger(count, 6);
            rows |= 1 << x;
        }
        shifter.writeInteger(rows, 9);
        shifter.writeInteger(bits - 1, 4);
        int idBits = Integer.bitCount(HashCommon.nextPowerOfTwo((int)(largestId + 1)) - 1);
        for (int i = list.size() - 1; i >= 0; --i) {
            shifter.writeInteger(list.getShort(i), idBits);
        }
        shifter.writeInteger(list.size(), 6);
        shifter.writeInteger(idBits - 1, 4);
        char[] chars = planner.setupName.toCharArray();
        for (int i = 0; i < chars.length && i < 63; ++i) {
            shifter.writeCharacter(chars[i]);
        }
        shifter.writeInteger(Math.min(63, chars.length), 6);
        int heat = Math.max(0, Math.min(planner.simulation.maxHeat - 100, planner.getSettings().startingHeat));
        if (heat > 0) {
            int heatBitsRequired = Integer.bitCount(HashCommon.nextPowerOfTwo((int)(heat + 1)) - 1);
            shifter.writeInteger(heat, heatBitsRequired > 15 ? 20 : 15);
            shifter.writeBoolean(heatBitsRequired > 15);
        }
        shifter.writeBoolean(heat > 0);
        shifter.writeInteger(planner.getReactorSize(), 3);
        shifter.writeBoolean(planner.isSteamReactor());
        shifter.writeInteger(2, 2);
        return shifter.getDataCode();
    }

    @Override
    public String getName() {
        return "V2 Encoder";
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

