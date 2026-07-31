/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.rcon;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class RconConsoleSource
implements CommandSource {
    private static final String f_144022_ = "Rcon";
    private static final Component f_11500_ = Component.m_237113_("Rcon");
    private final StringBuffer f_11501_ = new StringBuffer();
    private final MinecraftServer f_11502_;

    public RconConsoleSource(MinecraftServer p_11505_) {
        this.f_11502_ = p_11505_;
    }

    public void m_11512_() {
        this.f_11501_.setLength(0);
    }

    public String m_11513_() {
        return this.f_11501_.toString();
    }

    public CommandSourceStack m_11514_() {
        ServerLevel $$0 = this.f_11502_.m_129783_();
        return new CommandSourceStack(this, Vec3.m_82528_($$0.m_220360_()), Vec2.f_82462_, $$0, 4, f_144022_, f_11500_, this.f_11502_, null);
    }

    @Override
    public void m_213846_(Component p_215653_) {
        this.f_11501_.append(p_215653_.getString());
    }

    @Override
    public boolean m_6999_() {
        return true;
    }

    @Override
    public boolean m_7028_() {
        return true;
    }

    @Override
    public boolean m_6102_() {
        return this.f_11502_.m_6983_();
    }
}

