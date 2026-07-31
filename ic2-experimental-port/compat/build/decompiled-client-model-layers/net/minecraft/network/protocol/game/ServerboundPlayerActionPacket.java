/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundPlayerActionPacket
implements Packet<ServerGamePacketListener> {
    private final BlockPos f_134267_;
    private final Direction f_134268_;
    private final Action f_134269_;
    private final int f_237981_;

    public ServerboundPlayerActionPacket(Action p_237983_, BlockPos p_237984_, Direction p_237985_, int p_237986_) {
        this.f_134269_ = p_237983_;
        this.f_134267_ = p_237984_.m_7949_();
        this.f_134268_ = p_237985_;
        this.f_237981_ = p_237986_;
    }

    public ServerboundPlayerActionPacket(Action p_134272_, BlockPos p_134273_, Direction p_134274_) {
        this(p_134272_, p_134273_, p_134274_, 0);
    }

    public ServerboundPlayerActionPacket(FriendlyByteBuf p_179711_) {
        this.f_134269_ = p_179711_.m_130066_(Action.class);
        this.f_134267_ = p_179711_.m_130135_();
        this.f_134268_ = Direction.m_122376_(p_179711_.readUnsignedByte());
        this.f_237981_ = p_179711_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134283_) {
        p_134283_.m_130068_(this.f_134269_);
        p_134283_.m_130064_(this.f_134267_);
        p_134283_.writeByte(this.f_134268_.m_122411_());
        p_134283_.m_130130_(this.f_237981_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134280_) {
        p_134280_.m_7502_(this);
    }

    public BlockPos m_134281_() {
        return this.f_134267_;
    }

    public Direction m_134284_() {
        return this.f_134268_;
    }

    public Action m_134285_() {
        return this.f_134269_;
    }

    public int m_237987_() {
        return this.f_237981_;
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action START_DESTROY_BLOCK = new Action();
        public static final /* enum */ Action ABORT_DESTROY_BLOCK = new Action();
        public static final /* enum */ Action STOP_DESTROY_BLOCK = new Action();
        public static final /* enum */ Action DROP_ALL_ITEMS = new Action();
        public static final /* enum */ Action DROP_ITEM = new Action();
        public static final /* enum */ Action RELEASE_USE_ITEM = new Action();
        public static final /* enum */ Action SWAP_ITEM_WITH_OFFHAND = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_134299_) {
            return Enum.valueOf(Action.class, p_134299_);
        }

        private static /* synthetic */ Action[] m_179712_() {
            return new Action[]{START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM, SWAP_ITEM_WITH_OFFHAND};
        }

        static {
            $VALUES = Action.m_179712_();
        }
    }
}

