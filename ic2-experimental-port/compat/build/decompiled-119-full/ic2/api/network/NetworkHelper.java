/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.network;

import ic2.api.network.INetworkManager;
import ic2.api.util.CoreAccessRef;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class NetworkHelper {
    private static INetworkManager serverInstance;
    private static INetworkManager clientInstance;

    public static void updateTileEntityField(BlockEntity blockEntity, String string) {
        NetworkHelper.getNetworkManager(blockEntity.m_58904_().f_46443_).updateTileEntityField(blockEntity, string);
    }

    public static void initiateTileEntityEvent(BlockEntity blockEntity, int n, boolean bl) {
        NetworkHelper.getNetworkManager(blockEntity.m_58904_().f_46443_).initiateTileEntityEvent(blockEntity, n, bl);
    }

    public static void initiateItemEvent(Player player, ItemStack itemStack, int n, boolean bl) {
        NetworkHelper.getNetworkManager(player.m_20193_().f_46443_).initiateItemEvent(player, itemStack, n, bl);
    }

    public static void sendInitialData(BlockEntity blockEntity) {
        NetworkHelper.getNetworkManager(blockEntity.m_58904_().f_46443_).sendInitialData(blockEntity);
    }

    public static void initiateClientTileEntityEvent(BlockEntity blockEntity, int n) {
        NetworkHelper.getNetworkManager(blockEntity.m_58904_().f_46443_).initiateClientTileEntityEvent(blockEntity, n);
    }

    public static void initiateClientItemEvent(ItemStack itemStack, int n) {
        NetworkHelper.getNetworkManager(true).initiateClientItemEvent(itemStack, n);
    }

    public static INetworkManager getNetworkManager(boolean bl) {
        INetworkManager iNetworkManager;
        if (bl) {
            iNetworkManager = clientInstance;
            if (iNetworkManager == null) {
                clientInstance = iNetworkManager = CoreAccessRef.get().getClientNetworkManager();
            }
        } else {
            iNetworkManager = serverInstance;
            if (iNetworkManager == null) {
                serverInstance = iNetworkManager = CoreAccessRef.get().getServerNetworkManager();
            }
        }
        return iNetworkManager;
    }
}

