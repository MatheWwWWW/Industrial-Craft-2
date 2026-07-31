/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.social;

import com.google.common.base.Strings;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.screens.social.PlayerEntry;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;

public class SocialInteractionsPlayerList
extends ContainerObjectSelectionList<PlayerEntry> {
    private final SocialInteractionsScreen f_100692_;
    private final List<PlayerEntry> f_100694_ = Lists.newArrayList();
    @Nullable
    private String f_100695_;

    public SocialInteractionsPlayerList(SocialInteractionsScreen p_100697_, Minecraft p_100698_, int p_100699_, int p_100700_, int p_100701_, int p_100702_, int p_100703_) {
        super(p_100698_, p_100699_, p_100700_, p_100701_, p_100702_, p_100703_);
        this.f_100692_ = p_100697_;
        this.m_93488_(false);
        this.m_93496_(false);
    }

    @Override
    public void m_6305_(PoseStack p_100705_, int p_100706_, int p_100707_, float p_100708_) {
        double $$4 = this.f_93386_.m_91268_().m_85449_();
        RenderSystem.m_69488_((int)((double)this.m_5747_() * $$4), (int)((double)(this.f_93389_ - this.f_93391_) * $$4), (int)((double)(this.m_5756_() + 6) * $$4), (int)((double)(this.f_93389_ - (this.f_93389_ - this.f_93391_) - this.f_93390_ - 4) * $$4));
        super.m_6305_(p_100705_, p_100706_, p_100707_, p_100708_);
        RenderSystem.m_69471_();
    }

    public void m_240702_(Collection<UUID> p_240798_, double p_240792_, boolean p_240829_) {
        HashMap<UUID, PlayerEntry> $$3 = new HashMap<UUID, PlayerEntry>();
        this.m_240718_(p_240798_, $$3);
        this.m_240708_($$3, p_240829_);
        this.m_240705_($$3.values(), p_240792_);
    }

    private void m_240718_(Collection<UUID> p_240813_, Map<UUID, PlayerEntry> p_240796_) {
        ClientPacketListener $$2 = this.f_93386_.f_91074_.f_108617_;
        for (UUID $$3 : p_240813_) {
            PlayerInfo $$4 = $$2.m_104949_($$3);
            if ($$4 == null) continue;
            UUID $$5 = $$4.m_105312_().getId();
            boolean $$6 = $$4.m_233764_() != null;
            p_240796_.put($$5, new PlayerEntry(this.f_93386_, this.f_100692_, $$5, $$4.m_105312_().getName(), $$4::m_105337_, $$6));
        }
    }

    private void m_240708_(Map<UUID, PlayerEntry> p_240780_, boolean p_240827_) {
        Collection<GameProfile> $$2 = this.f_93386_.m_239211_().f_238743_().m_240298_().m_240328_();
        for (GameProfile $$3 : $$2) {
            PlayerEntry $$5;
            if (p_240827_) {
                PlayerEntry $$4 = p_240780_.computeIfAbsent($$3.getId(), p_243147_ -> {
                    PlayerEntry $$2 = new PlayerEntry(this.f_93386_, this.f_100692_, $$3.getId(), $$3.getName(), (Supplier<ResourceLocation>)Suppliers.memoize(() -> this.f_93386_.m_91109_().m_240306_($$3)), true);
                    $$2.m_100619_(true);
                    return $$2;
                });
            } else {
                $$5 = p_240780_.get($$3.getId());
                if ($$5 == null) continue;
            }
            $$5.m_240730_(true);
        }
    }

    private void m_240704_() {
        this.f_100694_.sort(Comparator.comparing(p_240744_ -> {
            if (p_240744_.m_100618_().equals(this.f_93386_.m_91094_().m_240411_())) {
                return 0;
            }
            if (p_240744_.m_100618_().version() == 2) {
                return 3;
            }
            if (p_240744_.m_240694_()) {
                return 1;
            }
            return 2;
        }).thenComparing(p_240745_ -> {
            int $$1;
            if (!p_240745_.m_100600_().isBlank() && (($$1 = p_240745_.m_100600_().codePointAt(0)) == 95 || $$1 >= 97 && $$1 <= 122 || $$1 >= 65 && $$1 <= 90 || $$1 >= 48 && $$1 <= 57)) {
                return 0;
            }
            return 1;
        }).thenComparing(PlayerEntry::m_100600_, String::compareToIgnoreCase));
    }

    private void m_240705_(Collection<PlayerEntry> p_240809_, double p_240830_) {
        this.f_100694_.clear();
        this.f_100694_.addAll(p_240809_);
        this.m_240704_();
        this.m_100725_();
        this.m_5988_(this.f_100694_);
        this.m_93410_(p_240830_);
    }

    private void m_100725_() {
        if (this.f_100695_ != null) {
            this.f_100694_.removeIf(p_100710_ -> !p_100710_.m_100600_().toLowerCase(Locale.ROOT).contains(this.f_100695_));
            this.m_5988_(this.f_100694_);
        }
    }

    public void m_100717_(String p_100718_) {
        this.f_100695_ = p_100718_;
    }

    public boolean m_100724_() {
        return this.f_100694_.isEmpty();
    }

    public void m_100714_(PlayerInfo p_100715_, SocialInteractionsScreen.Page p_100716_) {
        UUID $$2 = p_100715_.m_105312_().getId();
        for (PlayerEntry $$3 : this.f_100694_) {
            if (!$$3.m_100618_().equals($$2)) continue;
            $$3.m_100619_(false);
            return;
        }
        if ((p_100716_ == SocialInteractionsScreen.Page.ALL || this.f_93386_.m_91266_().m_100684_($$2)) && (Strings.isNullOrEmpty((String)this.f_100695_) || p_100715_.m_105312_().getName().toLowerCase(Locale.ROOT).contains(this.f_100695_))) {
            boolean $$4 = p_100715_.m_233764_() != null;
            PlayerEntry $$5 = new PlayerEntry(this.f_93386_, this.f_100692_, p_100715_.m_105312_().getId(), p_100715_.m_105312_().getName(), p_100715_::m_105337_, $$4);
            this.m_7085_($$5);
            this.f_100694_.add($$5);
        }
    }

    public void m_100722_(UUID p_100723_) {
        for (PlayerEntry $$1 : this.f_100694_) {
            if (!$$1.m_100618_().equals(p_100723_)) continue;
            $$1.m_100619_(true);
            return;
        }
    }
}

