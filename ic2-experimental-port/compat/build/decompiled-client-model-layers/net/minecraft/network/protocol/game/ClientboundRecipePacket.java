/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBookSettings;

public class ClientboundRecipePacket
implements Packet<ClientGamePacketListener> {
    private final State f_132849_;
    private final List<ResourceLocation> f_132850_;
    private final List<ResourceLocation> f_132851_;
    private final RecipeBookSettings f_132852_;

    public ClientboundRecipePacket(State p_132855_, Collection<ResourceLocation> p_132856_, Collection<ResourceLocation> p_132857_, RecipeBookSettings p_132858_) {
        this.f_132849_ = p_132855_;
        this.f_132850_ = ImmutableList.copyOf(p_132856_);
        this.f_132851_ = ImmutableList.copyOf(p_132857_);
        this.f_132852_ = p_132858_;
    }

    public ClientboundRecipePacket(FriendlyByteBuf p_179162_) {
        this.f_132849_ = p_179162_.m_130066_(State.class);
        this.f_132852_ = RecipeBookSettings.m_12752_(p_179162_);
        this.f_132850_ = p_179162_.m_236845_(FriendlyByteBuf::m_130281_);
        this.f_132851_ = this.f_132849_ == State.INIT ? p_179162_.m_236845_(FriendlyByteBuf::m_130281_) : ImmutableList.of();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132867_) {
        p_132867_.m_130068_(this.f_132849_);
        this.f_132852_.m_12761_(p_132867_);
        p_132867_.m_236828_(this.f_132850_, FriendlyByteBuf::m_130085_);
        if (this.f_132849_ == State.INIT) {
            p_132867_.m_236828_(this.f_132851_, FriendlyByteBuf::m_130085_);
        }
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132864_) {
        p_132864_.m_8076_(this);
    }

    public List<ResourceLocation> m_132865_() {
        return this.f_132850_;
    }

    public List<ResourceLocation> m_132868_() {
        return this.f_132851_;
    }

    public RecipeBookSettings m_132869_() {
        return this.f_132852_;
    }

    public State m_132870_() {
        return this.f_132849_;
    }

    public static final class State
    extends Enum<State> {
        public static final /* enum */ State INIT = new State();
        public static final /* enum */ State ADD = new State();
        public static final /* enum */ State REMOVE = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_132880_) {
            return Enum.valueOf(State.class, p_132880_);
        }

        private static /* synthetic */ State[] m_179163_() {
            return new State[]{INIT, ADD, REMOVE};
        }

        static {
            $VALUES = State.m_179163_();
        }
    }
}

