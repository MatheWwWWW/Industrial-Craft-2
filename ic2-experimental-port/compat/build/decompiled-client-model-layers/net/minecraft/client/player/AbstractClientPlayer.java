/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.player;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;

public abstract class AbstractClientPlayer
extends Player {
    private static final String f_172517_ = "http://skins.minecraft.net/MinecraftSkins/%s.png";
    @Nullable
    private PlayerInfo f_108546_;
    public float f_108542_;
    public float f_108543_;
    public float f_108544_;
    public final ClientLevel f_108545_;

    public AbstractClientPlayer(ClientLevel p_234112_, GameProfile p_234113_, @Nullable ProfilePublicKey p_234114_) {
        super(p_234112_, p_234112_.m_220360_(), p_234112_.m_220361_(), p_234113_, p_234114_);
        this.f_108545_ = p_234112_;
    }

    @Override
    public boolean m_5833_() {
        PlayerInfo $$0 = Minecraft.m_91087_().m_91403_().m_104949_(this.m_36316_().getId());
        return $$0 != null && $$0.m_105325_() == GameType.SPECTATOR;
    }

    @Override
    public boolean m_7500_() {
        PlayerInfo $$0 = Minecraft.m_91087_().m_91403_().m_104949_(this.m_36316_().getId());
        return $$0 != null && $$0.m_105325_() == GameType.CREATIVE;
    }

    public boolean m_108555_() {
        return this.m_108558_() != null;
    }

    @Nullable
    protected PlayerInfo m_108558_() {
        if (this.f_108546_ == null) {
            this.f_108546_ = Minecraft.m_91087_().m_91403_().m_104949_(this.m_20148_());
        }
        return this.f_108546_;
    }

    public boolean m_108559_() {
        PlayerInfo $$0 = this.m_108558_();
        return $$0 != null && $$0.m_105335_();
    }

    public ResourceLocation m_108560_() {
        PlayerInfo $$0 = this.m_108558_();
        return $$0 == null ? DefaultPlayerSkin.m_118627_(this.m_20148_()) : $$0.m_105337_();
    }

    @Nullable
    public ResourceLocation m_108561_() {
        PlayerInfo $$0 = this.m_108558_();
        return $$0 == null ? null : $$0.m_105338_();
    }

    public boolean m_108562_() {
        return this.m_108558_() != null;
    }

    @Nullable
    public ResourceLocation m_108563_() {
        PlayerInfo $$0 = this.m_108558_();
        return $$0 == null ? null : $$0.m_105339_();
    }

    public static void m_172521_(ResourceLocation p_172522_, String p_172523_) {
        TextureManager $$2 = Minecraft.m_91087_().m_91097_();
        AbstractTexture $$3 = $$2.m_174786_(p_172522_, MissingTextureAtlasSprite.m_118080_());
        if ($$3 == MissingTextureAtlasSprite.m_118080_()) {
            $$3 = new HttpTexture(null, String.format(Locale.ROOT, f_172517_, StringUtil.m_14406_(p_172523_)), DefaultPlayerSkin.m_118627_(UUIDUtil.m_235879_(p_172523_)), true, null);
            $$2.m_118495_(p_172522_, $$3);
        }
    }

    public static ResourceLocation m_108556_(String p_108557_) {
        return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars((CharSequence)StringUtil.m_14406_(p_108557_)));
    }

    public String m_108564_() {
        PlayerInfo $$0 = this.m_108558_();
        return $$0 == null ? DefaultPlayerSkin.m_118629_(this.m_20148_()) : $$0.m_105336_();
    }

    public float m_108565_() {
        float $$0 = 1.0f;
        if (this.m_150110_().f_35935_) {
            $$0 *= 1.1f;
        }
        if (this.m_150110_().m_35947_() == 0.0f || Float.isNaN($$0 *= ((float)this.m_21133_(Attributes.f_22279_) / this.m_150110_().m_35947_() + 1.0f) / 2.0f) || Float.isInfinite($$0)) {
            $$0 = 1.0f;
        }
        ItemStack $$1 = this.m_21211_();
        if (this.m_6117_()) {
            if ($$1.m_150930_(Items.f_42411_)) {
                int $$2 = this.m_21252_();
                float $$3 = (float)$$2 / 20.0f;
                $$3 = $$3 > 1.0f ? 1.0f : ($$3 *= $$3);
                $$0 *= 1.0f - $$3 * 0.15f;
            } else if (Minecraft.m_91087_().f_91066_.m_92176_().m_90612_() && this.m_150108_()) {
                return 0.1f;
            }
        }
        return Mth.m_14179_(Minecraft.m_91087_().f_91066_.m_231925_().m_231551_().floatValue(), 1.0f, $$0);
    }
}

