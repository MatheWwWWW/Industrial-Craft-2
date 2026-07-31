/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.UserApiService
 */
package net.minecraft.client.gui.screens.social;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.UserApiService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.multiplayer.PlayerInfo;

public class PlayerSocialManager {
    private final Minecraft f_100668_;
    private final Set<UUID> f_100669_ = Sets.newHashSet();
    private final UserApiService f_100670_;
    private final Map<String, UUID> f_100671_ = Maps.newHashMap();
    private boolean f_194054_;
    private CompletableFuture<?> f_194055_ = CompletableFuture.completedFuture(null);

    public PlayerSocialManager(Minecraft p_194057_, UserApiService p_194058_) {
        this.f_100668_ = p_194057_;
        this.f_100670_ = p_194058_;
    }

    public void m_100680_(UUID p_100681_) {
        this.f_100669_.add(p_100681_);
    }

    public void m_100682_(UUID p_100683_) {
        this.f_100669_.remove(p_100683_);
    }

    public boolean m_100684_(UUID p_100685_) {
        return this.m_100686_(p_100685_) || this.m_100688_(p_100685_);
    }

    public boolean m_100686_(UUID p_100687_) {
        return this.f_100669_.contains(p_100687_);
    }

    public void m_194059_() {
        this.f_194054_ = true;
        this.f_194055_ = this.f_194055_.thenRunAsync(() -> ((UserApiService)this.f_100670_).refreshBlockList(), Util.m_183992_());
    }

    public void m_194060_() {
        this.f_194054_ = false;
    }

    public boolean m_100688_(UUID p_100689_) {
        if (!this.f_194054_) {
            return false;
        }
        this.f_194055_.join();
        return this.f_100670_.isBlockedPlayer(p_100689_);
    }

    public Set<UUID> m_100675_() {
        return this.f_100669_;
    }

    public UUID m_100678_(String p_100679_) {
        return this.f_100671_.getOrDefault(p_100679_, Util.f_137441_);
    }

    public void m_100676_(PlayerInfo p_100677_) {
        Screen $$2;
        GameProfile $$1 = p_100677_.m_105312_();
        if ($$1.isComplete()) {
            this.f_100671_.put($$1.getName(), $$1.getId());
        }
        if (($$2 = this.f_100668_.f_91080_) instanceof SocialInteractionsScreen) {
            SocialInteractionsScreen $$3 = (SocialInteractionsScreen)$$2;
            $$3.m_100775_(p_100677_);
        }
    }

    public void m_100690_(UUID p_100691_) {
        Screen $$1 = this.f_100668_.f_91080_;
        if ($$1 instanceof SocialInteractionsScreen) {
            SocialInteractionsScreen $$2 = (SocialInteractionsScreen)$$1;
            $$2.m_100779_(p_100691_);
        }
    }
}

