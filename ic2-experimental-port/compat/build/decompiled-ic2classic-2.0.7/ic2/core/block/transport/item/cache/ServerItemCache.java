/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.transport.item.cache;

import ic2.api.tiles.tubes.IItemCache;
import ic2.core.utils.helpers.StackUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ServerItemCache
implements IItemCache,
Hash.Strategy<ItemStack>,
ToIntFunction<ItemStack> {
    int id = 1;
    Object2IntMap<ItemStack> reverseIds = new Object2IntOpenCustomHashMap((Hash.Strategy)this);
    Int2ObjectMap<ItemStack> ids = new Int2ObjectOpenHashMap();

    @Override
    public Supplier<ItemStack> getItem(int id) {
        throw new IllegalStateException("Server can not provide Items");
    }

    @Override
    public int registerItem(ItemStack stack) {
        return this.reverseIds.computeIfAbsent((Object)stack.m_41777_(), (ToIntFunction)this);
    }

    @Override
    public void updateCache() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearCache() {
        this.id = 1;
        this.reverseIds.clear();
        this.ids.clear();
    }

    public byte[] createData(int[] requestsIds) {
        ByteBuf buf = Unpooled.buffer();
        FriendlyByteBuf buffer = new FriendlyByteBuf(buf);
        buffer.m_130130_(requestsIds.length);
        for (int i = 0; i < requestsIds.length; ++i) {
            buffer.m_130130_(requestsIds[i]);
            buffer.m_130055_((ItemStack)this.ids.get(requestsIds[i]));
        }
        byte[] data = new byte[buf.writerIndex()];
        buf.readBytes(data);
        return data;
    }

    @Override
    public int applyAsInt(ItemStack value) {
        if (this.id < 1) {
            this.id = 1;
        }
        this.ids.put(this.id, (Object)value);
        return this.id++;
    }

    public int hashCode(ItemStack stack) {
        return Objects.hash(ForgeRegistries.ITEMS.getKey((Object)stack.m_41720_()).toString(), stack.m_41783_());
    }

    public boolean equals(ItemStack key, ItemStack value) {
        return key != null && value != null && StackUtil.isStackEqual(key, value);
    }
}

