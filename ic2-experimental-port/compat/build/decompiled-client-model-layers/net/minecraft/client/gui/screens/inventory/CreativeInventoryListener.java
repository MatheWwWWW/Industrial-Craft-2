/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

public class CreativeInventoryListener
implements ContainerListener {
    private final Minecraft f_98490_;

    public CreativeInventoryListener(Minecraft p_98492_) {
        this.f_98490_ = p_98492_;
    }

    @Override
    public void m_7934_(AbstractContainerMenu p_98498_, int p_98499_, ItemStack p_98500_) {
        this.f_98490_.f_91072_.m_105241_(p_98500_, p_98499_);
    }

    @Override
    public void m_142153_(AbstractContainerMenu p_169732_, int p_169733_, int p_169734_) {
    }
}

