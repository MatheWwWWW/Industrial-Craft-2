/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.shorts.Short2ObjectMaps
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.nbt.TagParser
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.planner.encoder.impl;

import ic2.core.block.machines.logic.planner.IReactorProvider;
import ic2.core.block.machines.logic.planner.PlannerRegistry;
import ic2.core.block.machines.logic.planner.encoder.IEncoder;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.logic.planner.newLogic.ReactorInventory;
import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.block.machines.logic.planner.newLogic.SimulationResult;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.inv.SimpleInventory;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMaps;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.item.ItemStack;

public class OldNBTEncoder
implements IEncoder {
    @Override
    public CompoundTag createDecodedData(String data) {
        try {
            DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(new BigInteger(data, 36).toByteArray())));
            String exportedNBT = stream.readUTF();
            stream.close();
            if (exportedNBT.startsWith("{") && exportedNBT.endsWith("}")) {
                return TagParser.m_129359_((String)exportedNBT);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    @Override
    public ReactorSetup createSetup(String data, boolean simulate) {
        CompoundTag mappedData = this.createDecodedData(data);
        if (mappedData == null) {
            return null;
        }
        ReactorLogic logic = new ReactorLogic(IReactorProvider.dummy(6, mappedData.m_128471_("SteamReactor")));
        ReactorInventory inventory = new ReactorInventory(54, logic);
        ListTag list = mappedData.m_128437_("Data", 10);
        boolean valid = false;
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbt = list.m_128728_(i);
            short id = nbt.m_128448_("ID");
            int[] array = nbt.m_128465_("Slots");
            ItemStack item = PlannerRegistry.getByID(id);
            for (int z = 0; z < array.length; ++z) {
                int slot = array[z];
                if (slot == -1) continue;
                ((SimpleInventory)inventory).setStackInSlot(slot, item.m_41777_());
                valid = true;
            }
        }
        if (!valid) {
            return null;
        }
        logic.validateLogic();
        ReactorSetup setup = this.finishSetup(logic.prediction, logic.isSteamReactor(), 6, 0);
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
        planner.setSteamReactor(decodedData.m_128471_("SteamReactor"));
        planner.setReactorSize(6);
        ReactorLogic base = planner.simulation;
        ListTag list = decodedData.m_128437_("Data", 10);
        boolean valid = false;
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag data = list.m_128728_(i);
            short id = data.m_128448_("ID");
            int[] array = data.m_128465_("Slots");
            ItemStack item = PlannerRegistry.getByID(id);
            for (int z = 0; z < array.length; ++z) {
                int slot = array[z];
                if (slot == -1) continue;
                planner.components.setStackInSlot(slot, item.m_41777_());
                valid = true;
            }
        }
        if (valid) {
            base.validateLogic();
        }
    }

    @Override
    public String createEncodedData(ReactorPlannerTileEntity planner) {
        Short2ObjectLinkedOpenHashMap amounts = new Short2ObjectLinkedOpenHashMap();
        for (int i = 0; i < 54; ++i) {
            short id;
            ItemStack stack = planner.components.getStackInSlot(i);
            if (stack.m_41619_() || (id = PlannerRegistry.getId(stack)) == -1) continue;
            IntList list = (IntList)amounts.get(id);
            if (list == null) {
                list = new IntArrayList();
                amounts.put(id, (Object)list);
            }
            list.add(i);
        }
        ListTag dataList = new ListTag();
        for (Short2ObjectMap.Entry entry : Short2ObjectMaps.fastIterable((Short2ObjectMap)amounts)) {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128376_("ID", entry.getShortKey());
            IntList slots = (IntList)entry.getValue();
            if (slots.size() == 1) {
                slots.add(-1);
            }
            nbt.m_128385_("Slots", slots.toIntArray());
            dataList.add((Object)nbt);
        }
        CompoundTag nbt = new CompoundTag();
        nbt.m_128365_("Data", (Tag)dataList);
        nbt.m_128379_("SteamReactor", planner.isSteamReactor());
        try {
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(array));
            stream.writeUTF(nbt.toString());
            stream.close();
            return new BigInteger(array.toByteArray()).toString(36);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "First (NBT)";
    }

    @Override
    public boolean hasBitLimit() {
        return false;
    }

    @Override
    public int getBitLimit() {
        return 0;
    }
}

