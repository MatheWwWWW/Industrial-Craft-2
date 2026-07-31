/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.TypeAdapterFactory
 */
package net.minecraft.network.protocol.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.status.ClientStatusPacketListener;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

public class ClientboundStatusResponsePacket
implements Packet<ClientStatusPacketListener> {
    private static final Gson f_134885_ = new GsonBuilder().registerTypeAdapter(ServerStatus.Version.class, (Object)new ServerStatus.Version.Serializer()).registerTypeAdapter(ServerStatus.Players.class, (Object)new ServerStatus.Players.Serializer()).registerTypeAdapter(ServerStatus.class, (Object)new ServerStatus.Serializer()).registerTypeHierarchyAdapter(Component.class, (Object)new Component.Serializer()).registerTypeHierarchyAdapter(Style.class, (Object)new Style.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory()).create();
    private final ServerStatus f_134886_;

    public ClientboundStatusResponsePacket(ServerStatus p_134890_) {
        this.f_134886_ = p_134890_;
    }

    public ClientboundStatusResponsePacket(FriendlyByteBuf p_179834_) {
        this.f_134886_ = GsonHelper.m_13794_(f_134885_, p_179834_.m_130136_(Short.MAX_VALUE), ServerStatus.class);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134899_) {
        p_134899_.m_130070_(f_134885_.toJson((Object)this.f_134886_));
    }

    @Override
    public void m_5797_(ClientStatusPacketListener p_134896_) {
        p_134896_.m_6440_(this);
    }

    public ServerStatus m_134897_() {
        return this.f_134886_;
    }
}

