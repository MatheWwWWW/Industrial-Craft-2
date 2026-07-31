/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.BossEvent;

public class ClientboundBossEventPacket
implements Packet<ClientGamePacketListener> {
    private static final int f_178629_ = 1;
    private static final int f_178630_ = 2;
    private static final int f_178631_ = 4;
    private final UUID f_131750_;
    private final Operation f_131751_;
    static final Operation f_178632_ = new Operation(){

        @Override
        public OperationType m_142659_() {
            return OperationType.REMOVE;
        }

        @Override
        public void m_142282_(UUID p_178660_, Handler p_178661_) {
            p_178661_.m_142751_(p_178660_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178663_) {
        }
    };

    private ClientboundBossEventPacket(UUID p_178635_, Operation p_178636_) {
        this.f_131750_ = p_178635_;
        this.f_131751_ = p_178636_;
    }

    public ClientboundBossEventPacket(FriendlyByteBuf p_178638_) {
        this.f_131750_ = p_178638_.m_130259_();
        OperationType $$1 = p_178638_.m_130066_(OperationType.class);
        this.f_131751_ = $$1.f_178710_.apply(p_178638_);
    }

    public static ClientboundBossEventPacket m_178639_(BossEvent p_178640_) {
        return new ClientboundBossEventPacket(p_178640_.m_18860_(), new AddOperation(p_178640_));
    }

    public static ClientboundBossEventPacket m_178641_(UUID p_178642_) {
        return new ClientboundBossEventPacket(p_178642_, f_178632_);
    }

    public static ClientboundBossEventPacket m_178649_(BossEvent p_178650_) {
        return new ClientboundBossEventPacket(p_178650_.m_18860_(), new UpdateProgressOperation(p_178650_.m_142717_()));
    }

    public static ClientboundBossEventPacket m_178651_(BossEvent p_178652_) {
        return new ClientboundBossEventPacket(p_178652_.m_18860_(), new UpdateNameOperation(p_178652_.m_18861_()));
    }

    public static ClientboundBossEventPacket m_178653_(BossEvent p_178654_) {
        return new ClientboundBossEventPacket(p_178654_.m_18860_(), new UpdateStyleOperation(p_178654_.m_18862_(), p_178654_.m_18863_()));
    }

    public static ClientboundBossEventPacket m_178655_(BossEvent p_178656_) {
        return new ClientboundBossEventPacket(p_178656_.m_18860_(), new UpdatePropertiesOperation(p_178656_.m_18864_(), p_178656_.m_18865_(), p_178656_.m_18866_()));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131773_) {
        p_131773_.m_130077_(this.f_131750_);
        p_131773_.m_130068_(this.f_131751_.m_142659_());
        this.f_131751_.m_142264_(p_131773_);
    }

    static int m_178645_(boolean p_178646_, boolean p_178647_, boolean p_178648_) {
        int $$3 = 0;
        if (p_178646_) {
            $$3 |= 1;
        }
        if (p_178647_) {
            $$3 |= 2;
        }
        if (p_178648_) {
            $$3 |= 4;
        }
        return $$3;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131770_) {
        p_131770_.m_7685_(this);
    }

    public void m_178643_(Handler p_178644_) {
        this.f_131751_.m_142282_(this.f_131750_, p_178644_);
    }

    static interface Operation {
        public OperationType m_142659_();

        public void m_142282_(UUID var1, Handler var2);

        public void m_142264_(FriendlyByteBuf var1);
    }

    static final class OperationType
    extends Enum<OperationType> {
        public static final /* enum */ OperationType ADD = new OperationType(AddOperation::new);
        public static final /* enum */ OperationType REMOVE = new OperationType(p_178719_ -> f_178632_);
        public static final /* enum */ OperationType UPDATE_PROGRESS = new OperationType(UpdateProgressOperation::new);
        public static final /* enum */ OperationType UPDATE_NAME = new OperationType(UpdateNameOperation::new);
        public static final /* enum */ OperationType UPDATE_STYLE = new OperationType(UpdateStyleOperation::new);
        public static final /* enum */ OperationType UPDATE_PROPERTIES = new OperationType(UpdatePropertiesOperation::new);
        final Function<FriendlyByteBuf, Operation> f_178710_;
        private static final /* synthetic */ OperationType[] $VALUES;

        public static OperationType[] values() {
            return (OperationType[])$VALUES.clone();
        }

        public static OperationType valueOf(String p_178721_) {
            return Enum.valueOf(OperationType.class, p_178721_);
        }

        private OperationType(Function<FriendlyByteBuf, Operation> p_178716_) {
            this.f_178710_ = p_178716_;
        }

        private static /* synthetic */ OperationType[] m_178717_() {
            return new OperationType[]{ADD, REMOVE, UPDATE_PROGRESS, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES};
        }

        static {
            $VALUES = OperationType.m_178717_();
        }
    }

    static class AddOperation
    implements Operation {
        private final Component f_178664_;
        private final float f_178665_;
        private final BossEvent.BossBarColor f_178666_;
        private final BossEvent.BossBarOverlay f_178667_;
        private final boolean f_178668_;
        private final boolean f_178669_;
        private final boolean f_178670_;

        AddOperation(BossEvent p_178672_) {
            this.f_178664_ = p_178672_.m_18861_();
            this.f_178665_ = p_178672_.m_142717_();
            this.f_178666_ = p_178672_.m_18862_();
            this.f_178667_ = p_178672_.m_18863_();
            this.f_178668_ = p_178672_.m_18864_();
            this.f_178669_ = p_178672_.m_18865_();
            this.f_178670_ = p_178672_.m_18866_();
        }

        private AddOperation(FriendlyByteBuf p_178674_) {
            this.f_178664_ = p_178674_.m_130238_();
            this.f_178665_ = p_178674_.readFloat();
            this.f_178666_ = p_178674_.m_130066_(BossEvent.BossBarColor.class);
            this.f_178667_ = p_178674_.m_130066_(BossEvent.BossBarOverlay.class);
            short $$1 = p_178674_.readUnsignedByte();
            this.f_178668_ = ($$1 & 1) > 0;
            this.f_178669_ = ($$1 & 2) > 0;
            this.f_178670_ = ($$1 & 4) > 0;
        }

        @Override
        public OperationType m_142659_() {
            return OperationType.ADD;
        }

        @Override
        public void m_142282_(UUID p_178677_, Handler p_178678_) {
            p_178678_.m_142107_(p_178677_, this.f_178664_, this.f_178665_, this.f_178666_, this.f_178667_, this.f_178668_, this.f_178669_, this.f_178670_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178680_) {
            p_178680_.m_130083_(this.f_178664_);
            p_178680_.writeFloat(this.f_178665_);
            p_178680_.m_130068_(this.f_178666_);
            p_178680_.m_130068_(this.f_178667_);
            p_178680_.writeByte(ClientboundBossEventPacket.m_178645_(this.f_178668_, this.f_178669_, this.f_178670_));
        }
    }

    static class UpdateProgressOperation
    implements Operation {
        private final float f_178734_;

        UpdateProgressOperation(float p_178736_) {
            this.f_178734_ = p_178736_;
        }

        private UpdateProgressOperation(FriendlyByteBuf p_178738_) {
            this.f_178734_ = p_178738_.readFloat();
        }

        @Override
        public OperationType m_142659_() {
            return OperationType.UPDATE_PROGRESS;
        }

        @Override
        public void m_142282_(UUID p_178741_, Handler p_178742_) {
            p_178742_.m_142653_(p_178741_, this.f_178734_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178744_) {
            p_178744_.writeFloat(this.f_178734_);
        }
    }

    static class UpdateNameOperation
    implements Operation {
        private final Component f_178723_;

        UpdateNameOperation(Component p_178727_) {
            this.f_178723_ = p_178727_;
        }

        private UpdateNameOperation(FriendlyByteBuf p_178725_) {
            this.f_178723_ = p_178725_.m_130238_();
        }

        @Override
        public OperationType m_142659_() {
            return OperationType.UPDATE_NAME;
        }

        @Override
        public void m_142282_(UUID p_178730_, Handler p_178731_) {
            p_178731_.m_142366_(p_178730_, this.f_178723_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178733_) {
            p_178733_.m_130083_(this.f_178723_);
        }
    }

    static class UpdateStyleOperation
    implements Operation {
        private final BossEvent.BossBarColor f_178760_;
        private final BossEvent.BossBarOverlay f_178761_;

        UpdateStyleOperation(BossEvent.BossBarColor p_178763_, BossEvent.BossBarOverlay p_178764_) {
            this.f_178760_ = p_178763_;
            this.f_178761_ = p_178764_;
        }

        private UpdateStyleOperation(FriendlyByteBuf p_178766_) {
            this.f_178760_ = p_178766_.m_130066_(BossEvent.BossBarColor.class);
            this.f_178761_ = p_178766_.m_130066_(BossEvent.BossBarOverlay.class);
        }

        @Override
        public OperationType m_142659_() {
            return OperationType.UPDATE_STYLE;
        }

        @Override
        public void m_142282_(UUID p_178769_, Handler p_178770_) {
            p_178770_.m_142358_(p_178769_, this.f_178760_, this.f_178761_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178772_) {
            p_178772_.m_130068_(this.f_178760_);
            p_178772_.m_130068_(this.f_178761_);
        }
    }

    static class UpdatePropertiesOperation
    implements Operation {
        private final boolean f_178745_;
        private final boolean f_178746_;
        private final boolean f_178747_;

        UpdatePropertiesOperation(boolean p_178751_, boolean p_178752_, boolean p_178753_) {
            this.f_178745_ = p_178751_;
            this.f_178746_ = p_178752_;
            this.f_178747_ = p_178753_;
        }

        private UpdatePropertiesOperation(FriendlyByteBuf p_178749_) {
            short $$1 = p_178749_.readUnsignedByte();
            this.f_178745_ = ($$1 & 1) > 0;
            this.f_178746_ = ($$1 & 2) > 0;
            this.f_178747_ = ($$1 & 4) > 0;
        }

        @Override
        public OperationType m_142659_() {
            return OperationType.UPDATE_PROPERTIES;
        }

        @Override
        public void m_142282_(UUID p_178756_, Handler p_178757_) {
            p_178757_.m_142513_(p_178756_, this.f_178745_, this.f_178746_, this.f_178747_);
        }

        @Override
        public void m_142264_(FriendlyByteBuf p_178759_) {
            p_178759_.writeByte(ClientboundBossEventPacket.m_178645_(this.f_178745_, this.f_178746_, this.f_178747_));
        }
    }

    public static interface Handler {
        default public void m_142107_(UUID p_178689_, Component p_178690_, float p_178691_, BossEvent.BossBarColor p_178692_, BossEvent.BossBarOverlay p_178693_, boolean p_178694_, boolean p_178695_, boolean p_178696_) {
        }

        default public void m_142751_(UUID p_178681_) {
        }

        default public void m_142653_(UUID p_178682_, float p_178683_) {
        }

        default public void m_142366_(UUID p_178687_, Component p_178688_) {
        }

        default public void m_142358_(UUID p_178684_, BossEvent.BossBarColor p_178685_, BossEvent.BossBarOverlay p_178686_) {
        }

        default public void m_142513_(UUID p_178697_, boolean p_178698_, boolean p_178699_, boolean p_178700_) {
        }
    }
}

