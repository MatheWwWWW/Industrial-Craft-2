/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.network;

import ic2.api.network.IGrowingBuffer;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.IC2;
import ic2.core.WorldData;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBlock;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.NetworkManager;
import ic2.core.network.TeUpdateDataClient;
import ic2.core.network.TeUpdateDataServer;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import ic2.core.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

class TeUpdate {
    static final boolean debug = System.getProperty("ic2.network.debug.teupdate") != null;

    TeUpdate() {
    }

    public static void send(WorldData worldData, NetworkManager network) throws IOException {
        if (worldData.tesToUpdate.isEmpty()) {
            return;
        }
        IdentityHashMap<EntityPlayerMP, GrowingBuffer> buffers = new IdentityHashMap<EntityPlayerMP, GrowingBuffer>();
        ArrayList playersInRange = new ArrayList();
        GrowingBuffer commonBuffer = new GrowingBuffer();
        for (Map.Entry<TileEntity, TeUpdateDataServer> entry : worldData.tesToUpdate.entrySet()) {
            TileEntity te = entry.getKey();
            NetworkManager.getPlayersInRange(te.func_145831_w(), te.func_174877_v(), playersInRange);
            if (playersInRange.isEmpty()) continue;
            TeUpdateDataServer updateData = entry.getValue();
            DataEncoder.encode(commonBuffer, te.func_174877_v(), false);
            commonBuffer.mark();
            commonBuffer.writeShort(0);
            for (String field : updateData.getGlobalFields()) {
                NetworkManager.writeFieldData(te, field, commonBuffer);
            }
            commonBuffer.flip();
            for (EntityPlayerMP player : playersInRange) {
                Collection<String> playerFields = updateData.getPlayerFields(player);
                int fieldCount = updateData.getGlobalFields().size() + playerFields.size();
                if (fieldCount == 0) continue;
                if (fieldCount > 65535) {
                    throw new RuntimeException("too many fields for " + te + ": " + fieldCount);
                }
                commonBuffer.reset();
                commonBuffer.writeShort(fieldCount);
                commonBuffer.rewind();
                GrowingBuffer playerBuffer = (GrowingBuffer)buffers.get(player);
                if (playerBuffer == null) {
                    playerBuffer = new GrowingBuffer(0);
                    buffers.put(player, playerBuffer);
                    playerBuffer.writeInt(player.func_130014_f_().field_73011_w.getDimension());
                }
                commonBuffer.writeTo(playerBuffer);
                commonBuffer.rewind();
                for (String field : playerFields) {
                    NetworkManager.writeFieldData(te, field, playerBuffer);
                }
            }
            commonBuffer.clear();
            playersInRange.clear();
        }
        worldData.tesToUpdate.clear();
        for (Map.Entry<Object, TeUpdateDataServer> entry : buffers.entrySet()) {
            EntityPlayerMP player = (EntityPlayerMP)entry.getKey();
            GrowingBuffer playerBuffer = (GrowingBuffer)((Object)entry.getValue());
            playerBuffer.flip();
            network.sendLargePacket(player, 0, playerBuffer);
        }
    }

    static void receive(GrowingBuffer buffer) throws IOException {
        final int dimensionId = buffer.readInt();
        final TeUpdateDataClient updateData = new TeUpdateDataClient();
        while (buffer.hasAvailable()) {
            BlockPos pos = DataEncoder.decode((IGrowingBuffer)buffer, BlockPos.class);
            int fieldCount = buffer.readUnsignedShort();
            TeUpdateDataClient.TeData teData = updateData.addTe(pos, fieldCount);
            for (int i = 0; i < fieldCount; ++i) {
                String fieldName = buffer.readString();
                Object value = DataEncoder.decode(buffer);
                if (fieldName.equals("teBlk")) {
                    String name = (String)value;
                    if (name.startsWith("Old-")) {
                        teData.teClass = TeBlockRegistry.getOld(name);
                        continue;
                    }
                    teData.teClass = TeBlockRegistry.get(name).getTeClass();
                    continue;
                }
                teData.addField(fieldName, value);
            }
            if (teData.teClass == null) continue;
            for (TeUpdateDataClient.FieldData fieldData : teData.getFields()) {
                fieldData.field = ReflectionUtil.getFieldRecursive(teData.teClass, fieldData.name);
            }
        }
        if (debug) {
            TeUpdate.printDebugOutput(dimensionId, updateData);
        }
        IC2.platform.requestTick(false, new Runnable(){

            @Override
            public void run() {
                World world = IC2.platform.getPlayerWorld();
                if (world == null || world.field_73011_w.getDimension() != dimensionId) {
                    return;
                }
                for (TeUpdateDataClient.TeData update : updateData.getTes()) {
                    try {
                        TeUpdate.apply(update, world);
                    }
                    catch (Throwable t) {
                        IC2.log.warn(LogCategory.Network, t, "TE update at %s failed.", Util.formatPosition((IBlockAccess)world, update.pos));
                    }
                }
            }
        });
    }

    private static void printDebugOutput(int dimensionId, TeUpdateDataClient data) {
        StringBuilder out = new StringBuilder();
        out.append("dimension: ");
        out.append(dimensionId);
        out.append(", ");
        out.append(data.getTes().size());
        out.append(" tes:\n");
        for (TeUpdateDataClient.TeData te : data.getTes()) {
            out.append("  pos: ");
            out.append(te.pos.func_177958_n());
            out.append('/');
            out.append(te.pos.func_177956_o());
            out.append('/');
            out.append(te.pos.func_177952_p());
            out.append(", ");
            out.append(te.getFields().size());
            out.append(" fields:\n");
            for (TeUpdateDataClient.FieldData field : te.getFields()) {
                out.append("    ");
                out.append(field.name);
                out.append(" = ");
                out.append(field.value);
                if (field.value != null) {
                    out.append(" (");
                    out.append(field.value.getClass().getSimpleName());
                    out.append(')');
                }
                out.append('\n');
            }
            if (te.teClass != null) {
                out.append("    TE Class: ");
                out.append(te.teClass.getName());
                out.append('\n');
                continue;
            }
            out.append("    no TE Class\n");
        }
        out.setLength(out.length() - 1);
        IC2.log.info(LogCategory.Network, "Received TE Update:\n" + out.toString());
    }

    private static void apply(TeUpdateDataClient.TeData update, World world) {
        if (!world.func_175668_a(update.pos, false)) {
            if (debug) {
                IC2.log.info(LogCategory.Network, "Skipping update at %s, chunk not loaded.", Util.formatPosition((IBlockAccess)world, update.pos));
            }
            return;
        }
        TileEntity te = world.func_175625_s(update.pos);
        if (update.teClass != null && (te == null || te.getClass() != update.teClass || te.func_145837_r() || te.func_145831_w() != world)) {
            if (debug) {
                IC2.log.info(LogCategory.Network, "Instantiating %s with %s.", Util.formatPosition((IBlockAccess)world, update.pos), update.teClass.getName());
            }
            te = TileEntityBlock.instantiate(update.teClass);
            world.func_175690_a(update.pos, te);
            assert (!te.func_145837_r());
            assert (te.func_145831_w() == world);
        } else {
            if (te == null) {
                if (debug) {
                    IC2.log.info(LogCategory.Network, "Can't apply update at %s, no te and no teClass.", Util.formatPosition((IBlockAccess)world, update.pos));
                }
                return;
            }
            if (te.func_145837_r() || te.func_145831_w() != world) {
                if (debug) {
                    IC2.log.warn(LogCategory.Network, "Can't apply update at %s, invalid te and no teClass.", Util.formatPosition((IBlockAccess)world, update.pos));
                }
                return;
            }
            if (debug) {
                IC2.log.info(LogCategory.Network, "TE class at %s unchanged.", Util.formatPosition((IBlockAccess)world, update.pos));
            }
        }
        for (TeUpdateDataClient.FieldData fieldUpdate : update.getFields()) {
            Object value = DataEncoder.getValue(fieldUpdate.value);
            if (fieldUpdate.field != null) {
                ReflectionUtil.setValue(te, fieldUpdate.field, value);
            } else {
                ReflectionUtil.setValueRecursive(te, fieldUpdate.name, value);
            }
            if (!(te instanceof INetworkUpdateListener)) continue;
            ((INetworkUpdateListener)te).onNetworkUpdate(fieldUpdate.name);
        }
    }
}

