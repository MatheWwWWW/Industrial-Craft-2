/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.logging.LogUtils;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.SignedMessageValidator;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.PlayerTeam;
import org.slf4j.Logger;

public class PlayerInfo {
    private static final Logger f_233758_ = LogUtils.getLogger();
    private final GameProfile f_105298_;
    private final Map<MinecraftProfileTexture.Type, ResourceLocation> f_105299_ = Maps.newEnumMap(MinecraftProfileTexture.Type.class);
    private GameType f_105300_;
    private int f_105301_;
    private boolean f_105302_;
    @Nullable
    private String f_105303_;
    @Nullable
    private Component f_105304_;
    private int f_105305_;
    private int f_105306_;
    private long f_105307_;
    private long f_105308_;
    private long f_105309_;
    @Nullable
    private final ProfilePublicKey f_233759_;
    private final SignedMessageValidator f_240895_;

    public PlayerInfo(ClientboundPlayerInfoPacket.PlayerUpdate p_233762_, SignatureValidator p_233763_, boolean p_242970_) {
        this.f_105298_ = p_233762_.m_132763_();
        this.f_105300_ = p_233762_.m_132765_();
        this.f_105301_ = p_233762_.m_132764_();
        this.f_105304_ = p_233762_.m_132766_();
        ProfilePublicKey $$3 = null;
        try {
            ProfilePublicKey.Data $$4 = p_233762_.m_237784_();
            if ($$4 != null) {
                $$3 = ProfilePublicKey.m_243358_(p_233763_, this.f_105298_.getId(), $$4, ProfilePublicKey.f_243350_);
            }
        }
        catch (Exception $$5) {
            f_233758_.error("Failed to validate publicKey property for profile {}", (Object)this.f_105298_.getId(), (Object)$$5);
        }
        this.f_233759_ = $$3;
        this.f_240895_ = SignedMessageValidator.m_242959_($$3, p_242970_);
    }

    public GameProfile m_105312_() {
        return this.f_105298_;
    }

    @Nullable
    public ProfilePublicKey m_233764_() {
        return this.f_233759_;
    }

    public SignedMessageValidator m_241043_() {
        return this.f_240895_;
    }

    @Nullable
    public GameType m_105325_() {
        return this.f_105300_;
    }

    protected void m_105317_(GameType p_105318_) {
        this.f_105300_ = p_105318_;
    }

    public int m_105330_() {
        return this.f_105301_;
    }

    protected void m_105313_(int p_105314_) {
        this.f_105301_ = p_105314_;
    }

    public boolean m_171808_() {
        return this.m_105338_() != null;
    }

    public boolean m_105335_() {
        return this.m_105337_() != null;
    }

    public String m_105336_() {
        if (this.f_105303_ == null) {
            return DefaultPlayerSkin.m_118629_(this.f_105298_.getId());
        }
        return this.f_105303_;
    }

    public ResourceLocation m_105337_() {
        this.m_105341_();
        return (ResourceLocation)MoreObjects.firstNonNull((Object)this.f_105299_.get(MinecraftProfileTexture.Type.SKIN), (Object)DefaultPlayerSkin.m_118627_(this.f_105298_.getId()));
    }

    @Nullable
    public ResourceLocation m_105338_() {
        this.m_105341_();
        return this.f_105299_.get(MinecraftProfileTexture.Type.CAPE);
    }

    @Nullable
    public ResourceLocation m_105339_() {
        this.m_105341_();
        return this.f_105299_.get(MinecraftProfileTexture.Type.ELYTRA);
    }

    @Nullable
    public PlayerTeam m_105340_() {
        return Minecraft.m_91087_().f_91073_.m_6188_().m_83500_(this.m_105312_().getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void m_105341_() {
        PlayerInfo playerInfo = this;
        synchronized (playerInfo) {
            if (!this.f_105302_) {
                this.f_105302_ = true;
                Minecraft.m_91087_().m_91109_().m_118817_(this.f_105298_, (p_105320_, p_105321_, p_105322_) -> {
                    this.f_105299_.put(p_105320_, p_105321_);
                    if (p_105320_ == MinecraftProfileTexture.Type.SKIN) {
                        this.f_105303_ = p_105322_.getMetadata("model");
                        if (this.f_105303_ == null) {
                            this.f_105303_ = "default";
                        }
                    }
                }, true);
            }
        }
    }

    public void m_105323_(@Nullable Component p_105324_) {
        this.f_105304_ = p_105324_;
    }

    @Nullable
    public Component m_105342_() {
        return this.f_105304_;
    }

    public int m_105343_() {
        return this.f_105305_;
    }

    public void m_105326_(int p_105327_) {
        this.f_105305_ = p_105327_;
    }

    public int m_105344_() {
        return this.f_105306_;
    }

    public void m_105331_(int p_105332_) {
        this.f_105306_ = p_105332_;
    }

    public long m_105345_() {
        return this.f_105307_;
    }

    public void m_105315_(long p_105316_) {
        this.f_105307_ = p_105316_;
    }

    public long m_105346_() {
        return this.f_105308_;
    }

    public void m_105328_(long p_105329_) {
        this.f_105308_ = p_105329_;
    }

    public long m_105347_() {
        return this.f_105309_;
    }

    public void m_105333_(long p_105334_) {
        this.f_105309_ = p_105334_;
    }
}

