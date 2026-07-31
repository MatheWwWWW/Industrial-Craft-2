/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.transport.item.cache;

import ic2.api.tiles.tubes.IItemCache;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.custom.CacheRequestPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ClientItemCache
implements IItemCache {
    Int2ObjectMap<Wrapper> wrappers = new Int2ObjectOpenHashMap();
    IntSet missingIds = new IntLinkedOpenHashSet();

    @Override
    public Supplier<ItemStack> getItem(int id) {
        Wrapper wrapper = (Wrapper)this.wrappers.get(id);
        if (wrapper == null) {
            this.missingIds.add(id);
            wrapper = new Wrapper();
            this.wrappers.put(id, (Object)wrapper);
        }
        return wrapper;
    }

    @Override
    public int registerItem(ItemStack stack) {
        throw new IllegalStateException("Can not register Items on the Client");
    }

    @Override
    public void clearCache() {
        this.wrappers.clear();
        this.missingIds.clear();
    }

    public void onDataReceived(FriendlyByteBuf buffer) {
        int m = buffer.m_130242_();
        for (int i = 0; i < m; ++i) {
            int id = buffer.m_130242_();
            Wrapper wrapper = (Wrapper)this.wrappers.get(id);
            if (wrapper == null) {
                wrapper = new Wrapper();
                this.wrappers.put(id, (Object)wrapper);
            }
            wrapper.set(buffer.m_130267_());
        }
    }

    @Override
    public void updateCache() {
        if (this.missingIds.size() > 0) {
            int[] ids = this.missingIds.toIntArray();
            this.missingIds.clear();
            PacketManager.INSTANCE.sendToServer(new CacheRequestPacket(ids));
        }
    }

    class Wrapper
    implements Supplier<ItemStack> {
        ItemStack item = ItemStack.f_41583_;

        Wrapper() {
        }

        public void set(ItemStack stack) {
            this.item = stack;
        }

        @Override
        public ItemStack get() {
            return this.item;
        }
    }
}

