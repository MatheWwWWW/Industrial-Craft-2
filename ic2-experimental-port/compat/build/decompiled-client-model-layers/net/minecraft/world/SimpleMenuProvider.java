/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;

public final class SimpleMenuProvider
implements MenuProvider {
    private final Component f_19199_;
    private final MenuConstructor f_19200_;

    public SimpleMenuProvider(MenuConstructor p_19202_, Component p_19203_) {
        this.f_19200_ = p_19202_;
        this.f_19199_ = p_19203_;
    }

    @Override
    public Component m_5446_() {
        return this.f_19199_;
    }

    @Override
    public AbstractContainerMenu m_7208_(int p_19205_, Inventory p_19206_, Player p_19207_) {
        return this.f_19200_.m_7208_(p_19205_, p_19206_, p_19207_);
    }
}

