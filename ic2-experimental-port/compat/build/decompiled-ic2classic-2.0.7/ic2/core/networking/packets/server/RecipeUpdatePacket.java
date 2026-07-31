/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.networking.packets.server;

import ic2.core.IC2;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.networking.packets.IC2Packet;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RecipeUpdatePacket
extends IC2Packet {
    byte[] data;

    public RecipeUpdatePacket() {
    }

    public RecipeUpdatePacket(List<IRecipeList> recipes) {
        this.data = this.toByteArray(recipes);
    }

    private byte[] toByteArray(List<IRecipeList> recipes) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        buffer.writeByte(recipes.size());
        for (int i = 0; i < recipes.size(); ++i) {
            recipes.get(i).writeRecipes(buffer);
        }
        byte[] data = new byte[buffer.writerIndex()];
        buffer.readBytes(data);
        return data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130087_(this.data);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.data = buffer.m_130052_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handlePacket(Player source) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])this.data));
        List<IRecipeList> list = IC2.RECIPES.get(false).getLists();
        int left = buffer.readByte();
        for (int i = 0; i < left; ++i) {
            list.get(i).readRecipes(buffer);
        }
    }
}

