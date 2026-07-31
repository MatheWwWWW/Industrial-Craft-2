/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.network;

import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.api.network.tile.PacketRange;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface INetworkManager {
    public void registerDataBuffer(ResourceLocation var1, Class<? extends INetworkDataBuffer> var2, Supplier<INetworkDataBuffer> var3);

    public void startGuiTracking(BlockEntity var1, Player var2);

    public void sendInitialGuiData(INetworkFieldProvider var1, Player var2);

    public void updateGuiData(BlockEntity var1, Player var2);

    public void updateTileField(BlockEntity var1, String var2);

    public void updateTileFields(BlockEntity var1, String ... var2);

    public void updateGuiField(BlockEntity var1, String var2);

    public void updateGuiFields(BlockEntity var1, String ... var2);

    public void sendInitialData(INetworkFieldProvider var1, CompoundTag var2);

    public void handleInitialChange(BlockEntity var1, CompoundTag var2);

    public void requestInitialData(INetworkFieldProvider var1);

    public void sendTileEvent(BlockEntity var1, int var2, int var3, PacketRange var4);

    public void sendTileDataBufferEvent(BlockEntity var1, String var2, INetworkDataBuffer var3, PacketRange var4);

    public void sendClientTileEvent(BlockEntity var1, int var2, int var3);

    public void sendClientTileDataBufferEvent(BlockEntity var1, String var2, INetworkDataBuffer var3);

    public void sendItemEvent(Player var1, ItemStack var2, int var3, int var4);

    public void sendItemBuffer(Player var1, ItemStack var2, String var3, INetworkDataBuffer var4);

    public void sendClientItemEvent(ItemStack var1, int var2, int var3);

    public void sendClientItemBuffer(ItemStack var1, String var2, INetworkDataBuffer var3);
}

