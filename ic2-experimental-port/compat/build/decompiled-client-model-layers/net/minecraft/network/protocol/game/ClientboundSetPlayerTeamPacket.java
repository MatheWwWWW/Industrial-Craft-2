/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.scores.PlayerTeam;

public class ClientboundSetPlayerTeamPacket
implements Packet<ClientGamePacketListener> {
    private static final int f_179309_ = 0;
    private static final int f_179310_ = 1;
    private static final int f_179311_ = 2;
    private static final int f_179312_ = 3;
    private static final int f_179313_ = 4;
    private static final int f_179314_ = 40;
    private static final int f_179315_ = 40;
    private final int f_133295_;
    private final String f_133287_;
    private final Collection<String> f_133294_;
    private final Optional<Parameters> f_179316_;

    private ClientboundSetPlayerTeamPacket(String p_179318_, int p_179319_, Optional<Parameters> p_179320_, Collection<String> p_179321_) {
        this.f_133287_ = p_179318_;
        this.f_133295_ = p_179319_;
        this.f_179316_ = p_179320_;
        this.f_133294_ = ImmutableList.copyOf(p_179321_);
    }

    public static ClientboundSetPlayerTeamPacket m_179332_(PlayerTeam p_179333_, boolean p_179334_) {
        return new ClientboundSetPlayerTeamPacket(p_179333_.m_5758_(), p_179334_ ? 0 : 2, Optional.of(new Parameters(p_179333_)), p_179334_ ? p_179333_.m_6809_() : ImmutableList.of());
    }

    public static ClientboundSetPlayerTeamPacket m_179326_(PlayerTeam p_179327_) {
        return new ClientboundSetPlayerTeamPacket(p_179327_.m_5758_(), 1, Optional.empty(), (Collection<String>)ImmutableList.of());
    }

    public static ClientboundSetPlayerTeamPacket m_179328_(PlayerTeam p_179329_, String p_179330_, Action p_179331_) {
        return new ClientboundSetPlayerTeamPacket(p_179329_.m_5758_(), p_179331_ == Action.ADD ? 3 : 4, Optional.empty(), (Collection<String>)ImmutableList.of((Object)p_179330_));
    }

    public ClientboundSetPlayerTeamPacket(FriendlyByteBuf p_179323_) {
        this.f_133287_ = p_179323_.m_130277_();
        this.f_133295_ = p_179323_.readByte();
        this.f_179316_ = ClientboundSetPlayerTeamPacket.m_179336_(this.f_133295_) ? Optional.of(new Parameters(p_179323_)) : Optional.empty();
        this.f_133294_ = ClientboundSetPlayerTeamPacket.m_179324_(this.f_133295_) ? p_179323_.m_236845_(FriendlyByteBuf::m_130277_) : ImmutableList.of();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133313_) {
        p_133313_.m_130070_(this.f_133287_);
        p_133313_.writeByte(this.f_133295_);
        if (ClientboundSetPlayerTeamPacket.m_179336_(this.f_133295_)) {
            this.f_179316_.orElseThrow(() -> new IllegalStateException("Parameters not present, but method is" + this.f_133295_)).m_179364_(p_133313_);
        }
        if (ClientboundSetPlayerTeamPacket.m_179324_(this.f_133295_)) {
            p_133313_.m_236828_(this.f_133294_, FriendlyByteBuf::m_130070_);
        }
    }

    private static boolean m_179324_(int p_179325_) {
        return p_179325_ == 0 || p_179325_ == 3 || p_179325_ == 4;
    }

    private static boolean m_179336_(int p_179337_) {
        return p_179337_ == 0 || p_179337_ == 2;
    }

    @Nullable
    public Action m_179335_() {
        switch (this.f_133295_) {
            case 0: 
            case 3: {
                return Action.ADD;
            }
            case 4: {
                return Action.REMOVE;
            }
        }
        return null;
    }

    @Nullable
    public Action m_179338_() {
        switch (this.f_133295_) {
            case 0: {
                return Action.ADD;
            }
            case 1: {
                return Action.REMOVE;
            }
        }
        return null;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133310_) {
        p_133310_.m_5582_(this);
    }

    public String m_133311_() {
        return this.f_133287_;
    }

    public Collection<String> m_133315_() {
        return this.f_133294_;
    }

    public Optional<Parameters> m_179339_() {
        return this.f_179316_;
    }

    public static class Parameters {
        private final Component f_179352_;
        private final Component f_179353_;
        private final Component f_179354_;
        private final String f_179355_;
        private final String f_179356_;
        private final ChatFormatting f_179357_;
        private final int f_179358_;

        public Parameters(PlayerTeam p_179360_) {
            this.f_179352_ = p_179360_.m_83364_();
            this.f_179358_ = p_179360_.m_83378_();
            this.f_179355_ = p_179360_.m_7470_().f_83567_;
            this.f_179356_ = p_179360_.m_7156_().f_83543_;
            this.f_179357_ = p_179360_.m_7414_();
            this.f_179353_ = p_179360_.m_83370_();
            this.f_179354_ = p_179360_.m_83371_();
        }

        public Parameters(FriendlyByteBuf p_179362_) {
            this.f_179352_ = p_179362_.m_130238_();
            this.f_179358_ = p_179362_.readByte();
            this.f_179355_ = p_179362_.m_130136_(40);
            this.f_179356_ = p_179362_.m_130136_(40);
            this.f_179357_ = p_179362_.m_130066_(ChatFormatting.class);
            this.f_179353_ = p_179362_.m_130238_();
            this.f_179354_ = p_179362_.m_130238_();
        }

        public Component m_179363_() {
            return this.f_179352_;
        }

        public int m_179366_() {
            return this.f_179358_;
        }

        public ChatFormatting m_179367_() {
            return this.f_179357_;
        }

        public String m_179368_() {
            return this.f_179355_;
        }

        public String m_179369_() {
            return this.f_179356_;
        }

        public Component m_179370_() {
            return this.f_179353_;
        }

        public Component m_179371_() {
            return this.f_179354_;
        }

        public void m_179364_(FriendlyByteBuf p_179365_) {
            p_179365_.m_130083_(this.f_179352_);
            p_179365_.writeByte(this.f_179358_);
            p_179365_.m_130070_(this.f_179355_);
            p_179365_.m_130070_(this.f_179356_);
            p_179365_.m_130068_(this.f_179357_);
            p_179365_.m_130083_(this.f_179353_);
            p_179365_.m_130083_(this.f_179354_);
        }
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action ADD = new Action();
        public static final /* enum */ Action REMOVE = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_179350_) {
            return Enum.valueOf(Action.class, p_179350_);
        }

        private static /* synthetic */ Action[] m_179348_() {
            return new Action[]{ADD, REMOVE};
        }

        static {
            $VALUES = Action.m_179348_();
        }
    }
}

