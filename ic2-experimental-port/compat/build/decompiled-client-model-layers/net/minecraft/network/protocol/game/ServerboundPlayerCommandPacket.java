/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.entity.Entity;

public class ServerboundPlayerCommandPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134301_;
    private final Action f_134302_;
    private final int f_134303_;

    public ServerboundPlayerCommandPacket(Entity p_134306_, Action p_134307_) {
        this(p_134306_, p_134307_, 0);
    }

    public ServerboundPlayerCommandPacket(Entity p_134309_, Action p_134310_, int p_134311_) {
        this.f_134301_ = p_134309_.m_19879_();
        this.f_134302_ = p_134310_;
        this.f_134303_ = p_134311_;
    }

    public ServerboundPlayerCommandPacket(FriendlyByteBuf p_179714_) {
        this.f_134301_ = p_179714_.m_130242_();
        this.f_134302_ = p_179714_.m_130066_(Action.class);
        this.f_134303_ = p_179714_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134319_) {
        p_134319_.m_130130_(this.f_134301_);
        p_134319_.m_130068_(this.f_134302_);
        p_134319_.m_130130_(this.f_134303_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134317_) {
        p_134317_.m_5681_(this);
    }

    public int m_179715_() {
        return this.f_134301_;
    }

    public Action m_134320_() {
        return this.f_134302_;
    }

    public int m_134321_() {
        return this.f_134303_;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action PRESS_SHIFT_KEY = new Action();
        public static final /* enum */ Action RELEASE_SHIFT_KEY = new Action();
        public static final /* enum */ Action STOP_SLEEPING = new Action();
        public static final /* enum */ Action START_SPRINTING = new Action();
        public static final /* enum */ Action STOP_SPRINTING = new Action();
        public static final /* enum */ Action START_RIDING_JUMP = new Action();
        public static final /* enum */ Action STOP_RIDING_JUMP = new Action();
        public static final /* enum */ Action OPEN_INVENTORY = new Action();
        public static final /* enum */ Action START_FALL_FLYING = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_134337_) {
            return Enum.valueOf(Action.class, p_134337_);
        }

        private static /* synthetic */ Action[] m_179716_() {
            return new Action[]{PRESS_SHIFT_KEY, RELEASE_SHIFT_KEY, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, START_RIDING_JUMP, STOP_RIDING_JUMP, OPEN_INVENTORY, START_FALL_FLYING};
        }

        static {
            $VALUES = Action.m_179716_();
        }
    }
}

