/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ServerboundInteractPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134030_;
    private final Action f_134031_;
    private final boolean f_134034_;
    static final Action f_179595_ = new Action(){

        @Override
        public ActionType m_142249_() {
            return ActionType.ATTACK;
        }

        @Override
        public void m_142457_(Handler p_179624_) {
            p_179624_.m_141994_();
        }

        @Override
        public void m_142450_(FriendlyByteBuf p_179622_) {
        }
    };

    private ServerboundInteractPacket(int p_179598_, boolean p_179599_, Action p_179600_) {
        this.f_134030_ = p_179598_;
        this.f_134031_ = p_179600_;
        this.f_134034_ = p_179599_;
    }

    public static ServerboundInteractPacket m_179605_(Entity p_179606_, boolean p_179607_) {
        return new ServerboundInteractPacket(p_179606_.m_19879_(), p_179607_, f_179595_);
    }

    public static ServerboundInteractPacket m_179608_(Entity p_179609_, boolean p_179610_, InteractionHand p_179611_) {
        return new ServerboundInteractPacket(p_179609_.m_19879_(), p_179610_, new InteractionAction(p_179611_));
    }

    public static ServerboundInteractPacket m_179612_(Entity p_179613_, boolean p_179614_, InteractionHand p_179615_, Vec3 p_179616_) {
        return new ServerboundInteractPacket(p_179613_.m_19879_(), p_179614_, new InteractionAtLocationAction(p_179615_, p_179616_));
    }

    public ServerboundInteractPacket(FriendlyByteBuf p_179602_) {
        this.f_134030_ = p_179602_.m_130242_();
        ActionType $$1 = p_179602_.m_130066_(ActionType.class);
        this.f_134031_ = $$1.f_179630_.apply(p_179602_);
        this.f_134034_ = p_179602_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134058_) {
        p_134058_.m_130130_(this.f_134030_);
        p_134058_.m_130068_(this.f_134031_.m_142249_());
        this.f_134031_.m_142450_(p_134058_);
        p_134058_.writeBoolean(this.f_134034_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134055_) {
        p_134055_.m_6946_(this);
    }

    @Nullable
    public Entity m_179603_(ServerLevel p_179604_) {
        return p_179604_.m_143317_(this.f_134030_);
    }

    public boolean m_134061_() {
        return this.f_134034_;
    }

    public void m_179617_(Handler p_179618_) {
        this.f_134031_.m_142457_(p_179618_);
    }

    static interface Action {
        public ActionType m_142249_();

        public void m_142457_(Handler var1);

        public void m_142450_(FriendlyByteBuf var1);
    }

    static class InteractionAction
    implements Action {
        private final InteractionHand f_179646_;

        InteractionAction(InteractionHand p_179648_) {
            this.f_179646_ = p_179648_;
        }

        private InteractionAction(FriendlyByteBuf p_179650_) {
            this.f_179646_ = p_179650_.m_130066_(InteractionHand.class);
        }

        @Override
        public ActionType m_142249_() {
            return ActionType.INTERACT;
        }

        @Override
        public void m_142457_(Handler p_179655_) {
            p_179655_.m_142299_(this.f_179646_);
        }

        @Override
        public void m_142450_(FriendlyByteBuf p_179653_) {
            p_179653_.m_130068_(this.f_179646_);
        }
    }

    static class InteractionAtLocationAction
    implements Action {
        private final InteractionHand f_179656_;
        private final Vec3 f_179657_;

        InteractionAtLocationAction(InteractionHand p_179659_, Vec3 p_179660_) {
            this.f_179656_ = p_179659_;
            this.f_179657_ = p_179660_;
        }

        private InteractionAtLocationAction(FriendlyByteBuf p_179662_) {
            this.f_179657_ = new Vec3(p_179662_.readFloat(), p_179662_.readFloat(), p_179662_.readFloat());
            this.f_179656_ = p_179662_.m_130066_(InteractionHand.class);
        }

        @Override
        public ActionType m_142249_() {
            return ActionType.INTERACT_AT;
        }

        @Override
        public void m_142457_(Handler p_179667_) {
            p_179667_.m_142143_(this.f_179656_, this.f_179657_);
        }

        @Override
        public void m_142450_(FriendlyByteBuf p_179665_) {
            p_179665_.writeFloat((float)this.f_179657_.f_82479_);
            p_179665_.writeFloat((float)this.f_179657_.f_82480_);
            p_179665_.writeFloat((float)this.f_179657_.f_82481_);
            p_179665_.m_130068_(this.f_179656_);
        }
    }

    static final class ActionType
    extends Enum<ActionType> {
        public static final /* enum */ ActionType INTERACT = new ActionType(InteractionAction::new);
        public static final /* enum */ ActionType ATTACK = new ActionType(p_179639_ -> f_179595_);
        public static final /* enum */ ActionType INTERACT_AT = new ActionType(InteractionAtLocationAction::new);
        final Function<FriendlyByteBuf, Action> f_179630_;
        private static final /* synthetic */ ActionType[] $VALUES;

        public static ActionType[] values() {
            return (ActionType[])$VALUES.clone();
        }

        public static ActionType valueOf(String p_179641_) {
            return Enum.valueOf(ActionType.class, p_179641_);
        }

        private ActionType(Function<FriendlyByteBuf, Action> p_179636_) {
            this.f_179630_ = p_179636_;
        }

        private static /* synthetic */ ActionType[] m_179637_() {
            return new ActionType[]{INTERACT, ATTACK, INTERACT_AT};
        }

        static {
            $VALUES = ActionType.m_179637_();
        }
    }

    public static interface Handler {
        public void m_142299_(InteractionHand var1);

        public void m_142143_(InteractionHand var1, Vec3 var2);

        public void m_141994_();
    }
}

