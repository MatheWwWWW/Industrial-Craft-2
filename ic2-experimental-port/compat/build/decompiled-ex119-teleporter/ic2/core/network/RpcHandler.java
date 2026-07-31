/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 */
package ic2.core.network;

import ic2.core.IC2;
import ic2.core.network.DataEncoder;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.IRpcProvider;
import ic2.core.network.Rpc;
import ic2.core.network.SubPacketType;
import ic2.core.util.LogCategory;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.server.level.ServerPlayer;

public class RpcHandler {
    private static ConcurrentMap<String, IRpcProvider<?>> providers = new ConcurrentHashMap();
    private static ConcurrentMap<Integer, Rpc<?>> pending = new ConcurrentHashMap();

    public static boolean registerProvider(IRpcProvider<?> iRpcProvider) {
        return providers.putIfAbsent(iRpcProvider.getClass().getName(), iRpcProvider) == null;
    }

    public static <V> Rpc<V> run(Class<? extends IRpcProvider<V>> clazz, Object ... objectArray) {
        int n;
        Rpc rpc;
        Rpc rpc2 = new Rpc();
        while ((rpc = pending.putIfAbsent(n = IC2.random.m_188502_(), rpc2)) != null) {
        }
        IC2.network.get(false).initiateRpc(n, clazz, objectArray);
        return rpc2;
    }

    static void processRpcRequest(GrowingBuffer growingBuffer, ServerPlayer serverPlayer) throws IOException {
        int n = growingBuffer.readInt();
        String string = growingBuffer.readString();
        IRpcProvider iRpcProvider = (IRpcProvider)providers.get(string);
        if (iRpcProvider == null) {
            IC2.log.warn(LogCategory.Network, "Invalid RPC request from %s.", serverPlayer.m_7755_());
            return;
        }
        Object[] objectArray = (Object[])DataEncoder.decode(growingBuffer);
        Object v = iRpcProvider.executeRpc(objectArray);
        GrowingBuffer growingBuffer2 = new GrowingBuffer(256);
        SubPacketType.Rpc.writeTo(growingBuffer2);
        growingBuffer2.writeInt(n);
        DataEncoder.encode(growingBuffer2, v, true);
        growingBuffer2.flip();
        IC2.network.get(true).sendS2CPacket(serverPlayer, growingBuffer2, true);
    }

    public static void onDisconnect() {
        for (Rpc rpc : pending.values()) {
            rpc.cancel(true);
        }
        pending.clear();
    }

    static void processRpcResponse(GrowingBuffer growingBuffer) throws IOException {
        int n = growingBuffer.readInt();
        Rpc rpc = (Rpc)pending.remove(n);
        if (rpc == null) {
            IC2.log.warn(LogCategory.Network, "RPC %d wasn't found while trying to process its response.", n);
        } else {
            Object object = DataEncoder.decode(growingBuffer);
            rpc.finish(object);
        }
    }
}

