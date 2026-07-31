/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.util;

import java.util.Set;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DelegatingInventory
implements Container {
    private final Container parent;

    public DelegatingInventory(Container container) {
        this.parent = container;
    }

    public void m_6211_() {
        this.parent.m_6211_();
    }

    public int m_6643_() {
        return this.parent.m_6643_();
    }

    public boolean m_7983_() {
        return this.parent.m_7983_();
    }

    public ItemStack m_8020_(int n) {
        return this.parent.m_8020_(n);
    }

    public ItemStack m_7407_(int n, int n2) {
        return this.parent.m_7407_(n, n2);
    }

    public ItemStack m_8016_(int n) {
        return this.parent.m_8016_(n);
    }

    public void m_6836_(int n, ItemStack itemStack) {
        this.parent.m_6836_(n, itemStack);
    }

    public int m_6893_() {
        return this.parent.m_6893_();
    }

    public void m_6596_() {
        this.parent.m_6596_();
    }

    public boolean m_6542_(Player player) {
        return this.parent.m_6542_(player);
    }

    public void m_5856_(Player player) {
        this.parent.m_5856_(player);
    }

    public void m_5785_(Player player) {
        this.parent.m_5785_(player);
    }

    public boolean m_7013_(int n, ItemStack itemStack) {
        return this.parent.m_7013_(n, itemStack);
    }

    public int m_18947_(Item item) {
        return this.parent.m_18947_(item);
    }

    public boolean m_18949_(Set<Item> set) {
        return this.parent.m_18949_(set);
    }
}

