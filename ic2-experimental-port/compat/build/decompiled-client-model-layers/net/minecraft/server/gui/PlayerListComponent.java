/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.gui;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;

public class PlayerListComponent
extends JList<String> {
    private final MinecraftServer f_139950_;
    private int f_139951_;

    public PlayerListComponent(MinecraftServer p_139953_) {
        this.f_139950_ = p_139953_;
        p_139953_.m_129946_(this::m_139954_);
    }

    public void m_139954_() {
        if (this.f_139951_++ % 20 == 0) {
            Vector<String> $$0 = new Vector<String>();
            for (int $$1 = 0; $$1 < this.f_139950_.m_6846_().m_11314_().size(); ++$$1) {
                $$0.add(this.f_139950_.m_6846_().m_11314_().get($$1).m_36316_().getName());
            }
            this.setListData($$0);
        }
    }
}

