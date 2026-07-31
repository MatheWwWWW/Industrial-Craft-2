/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RowButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RealmsPendingInvitesScreen
extends RealmsScreen {
    static final Logger f_88874_ = LogUtils.getLogger();
    static final ResourceLocation f_88875_ = new ResourceLocation("realms", "textures/gui/realms/accept_icon.png");
    static final ResourceLocation f_88876_ = new ResourceLocation("realms", "textures/gui/realms/reject_icon.png");
    private static final Component f_88877_ = Component.m_237115_("mco.invites.nopending");
    static final Component f_88878_ = Component.m_237115_("mco.invites.button.accept");
    static final Component f_88879_ = Component.m_237115_("mco.invites.button.reject");
    private final Screen f_88880_;
    @Nullable
    Component f_88881_;
    boolean f_88882_;
    PendingInvitationSelectionList f_88883_;
    int f_88885_ = -1;
    private Button f_88886_;
    private Button f_88887_;

    public RealmsPendingInvitesScreen(Screen p_88890_) {
        super(Component.m_237115_("mco.invites.title"));
        this.f_88880_ = p_88890_;
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_88883_ = new PendingInvitationSelectionList();
        new Thread("Realms-pending-invitations-fetcher"){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                RealmsClient $$0 = RealmsClient.m_87169_();
                try {
                    List<PendingInvite> $$1 = $$0.m_87261_().f_87432_;
                    List $$2 = $$1.stream().map(p_88969_ -> new Entry((PendingInvite)p_88969_)).collect(Collectors.toList());
                    RealmsPendingInvitesScreen.this.f_96541_.execute(() -> RealmsPendingInvitesScreen.this.f_88883_.m_5988_($$2));
                }
                catch (RealmsServiceException $$3) {
                    f_88874_.error("Couldn't list invites");
                }
                finally {
                    RealmsPendingInvitesScreen.this.f_88882_ = true;
                }
            }
        }.start();
        this.m_7787_(this.f_88883_);
        this.f_88886_ = this.m_142416_(new Button(this.f_96543_ / 2 - 174, this.f_96544_ - 32, 100, 20, Component.m_237115_("mco.invites.button.accept"), p_88940_ -> {
            this.m_88932_(this.f_88885_);
            this.f_88885_ = -1;
            this.m_88957_();
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 50, this.f_96544_ - 32, 100, 20, CommonComponents.f_130655_, p_88930_ -> this.f_96541_.m_91152_(new RealmsMainScreen(this.f_88880_))));
        this.f_88887_ = this.m_142416_(new Button(this.f_96543_ / 2 + 74, this.f_96544_ - 32, 100, 20, Component.m_237115_("mco.invites.button.reject"), p_88920_ -> {
            this.m_88922_(this.f_88885_);
            this.f_88885_ = -1;
            this.m_88957_();
        }));
        this.m_88957_();
    }

    @Override
    public boolean m_7933_(int p_88895_, int p_88896_, int p_88897_) {
        if (p_88895_ == 256) {
            this.f_96541_.m_91152_(new RealmsMainScreen(this.f_88880_));
            return true;
        }
        return super.m_7933_(p_88895_, p_88896_, p_88897_);
    }

    void m_88892_(int p_88893_) {
        this.f_88883_.m_89057_(p_88893_);
    }

    void m_88922_(final int p_88923_) {
        if (p_88923_ < this.f_88883_.m_5773_()) {
            new Thread("Realms-reject-invitation"){

                @Override
                public void run() {
                    try {
                        RealmsClient $$0 = RealmsClient.m_87169_();
                        $$0.m_87219_(((Entry)RealmsPendingInvitesScreen.this.f_88883_.m_6702_().get((int)p_88923_)).f_88992_.f_87422_);
                        RealmsPendingInvitesScreen.this.f_96541_.execute(() -> RealmsPendingInvitesScreen.this.m_88892_(p_88923_));
                    }
                    catch (RealmsServiceException $$1) {
                        f_88874_.error("Couldn't reject invite");
                    }
                }
            }.start();
        }
    }

    void m_88932_(final int p_88933_) {
        if (p_88933_ < this.f_88883_.m_5773_()) {
            new Thread("Realms-accept-invitation"){

                @Override
                public void run() {
                    try {
                        RealmsClient $$0 = RealmsClient.m_87169_();
                        $$0.m_87201_(((Entry)RealmsPendingInvitesScreen.this.f_88883_.m_6702_().get((int)p_88933_)).f_88992_.f_87422_);
                        RealmsPendingInvitesScreen.this.f_96541_.execute(() -> RealmsPendingInvitesScreen.this.m_88892_(p_88933_));
                    }
                    catch (RealmsServiceException $$1) {
                        f_88874_.error("Couldn't accept invite");
                    }
                }
            }.start();
        }
    }

    @Override
    public void m_6305_(PoseStack p_88899_, int p_88900_, int p_88901_, float p_88902_) {
        this.f_88881_ = null;
        this.m_7333_(p_88899_);
        this.f_88883_.m_6305_(p_88899_, p_88900_, p_88901_, p_88902_);
        RealmsPendingInvitesScreen.m_93215_(p_88899_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 12, 0xFFFFFF);
        if (this.f_88881_ != null) {
            this.m_88903_(p_88899_, this.f_88881_, p_88900_, p_88901_);
        }
        if (this.f_88883_.m_5773_() == 0 && this.f_88882_) {
            RealmsPendingInvitesScreen.m_93215_(p_88899_, this.f_96547_, f_88877_, this.f_96543_ / 2, this.f_96544_ / 2 - 20, 0xFFFFFF);
        }
        super.m_6305_(p_88899_, p_88900_, p_88901_, p_88902_);
    }

    protected void m_88903_(PoseStack p_88904_, @Nullable Component p_88905_, int p_88906_, int p_88907_) {
        if (p_88905_ == null) {
            return;
        }
        int $$4 = p_88906_ + 12;
        int $$5 = p_88907_ - 12;
        int $$6 = this.f_96547_.m_92852_(p_88905_);
        this.m_93179_(p_88904_, $$4 - 3, $$5 - 3, $$4 + $$6 + 3, $$5 + 8 + 3, -1073741824, -1073741824);
        this.f_96547_.m_92763_(p_88904_, p_88905_, $$4, $$5, 0xFFFFFF);
    }

    void m_88957_() {
        this.f_88886_.f_93624_ = this.m_88962_(this.f_88885_);
        this.f_88887_.f_93624_ = this.m_88962_(this.f_88885_);
    }

    private boolean m_88962_(int p_88963_) {
        return p_88963_ != -1;
    }

    class PendingInvitationSelectionList
    extends RealmsObjectSelectionList<Entry> {
        public PendingInvitationSelectionList() {
            super(RealmsPendingInvitesScreen.this.f_96543_, RealmsPendingInvitesScreen.this.f_96544_, 32, RealmsPendingInvitesScreen.this.f_96544_ - 40, 36);
        }

        public void m_89057_(int p_89058_) {
            this.m_93514_(p_89058_);
        }

        @Override
        public int m_5775_() {
            return this.m_5773_() * 36;
        }

        @Override
        public int m_5759_() {
            return 260;
        }

        @Override
        public boolean m_5694_() {
            return RealmsPendingInvitesScreen.this.m_7222_() == this;
        }

        @Override
        public void m_7733_(PoseStack p_89051_) {
            RealmsPendingInvitesScreen.this.m_7333_(p_89051_);
        }

        @Override
        public void m_7109_(int p_89049_) {
            super.m_7109_(p_89049_);
            this.m_89060_(p_89049_);
        }

        public void m_89060_(int p_89061_) {
            RealmsPendingInvitesScreen.this.f_88885_ = p_89061_;
            RealmsPendingInvitesScreen.this.m_88957_();
        }

        @Override
        public void m_6987_(@Nullable Entry p_89053_) {
            super.m_6987_(p_89053_);
            RealmsPendingInvitesScreen.this.f_88885_ = this.m_6702_().indexOf(p_89053_);
            RealmsPendingInvitesScreen.this.m_88957_();
        }
    }

    class Entry
    extends ObjectSelectionList.Entry<Entry> {
        private static final int f_167427_ = 38;
        final PendingInvite f_88992_;
        private final List<RowButton> f_88993_;

        Entry(PendingInvite p_88996_) {
            this.f_88992_ = p_88996_;
            this.f_88993_ = Arrays.asList(new AcceptRowButton(), new RejectRowButton());
        }

        @Override
        public void m_6311_(PoseStack p_89006_, int p_89007_, int p_89008_, int p_89009_, int p_89010_, int p_89011_, int p_89012_, int p_89013_, boolean p_89014_, float p_89015_) {
            this.m_89016_(p_89006_, this.f_88992_, p_89009_, p_89008_, p_89012_, p_89013_);
        }

        @Override
        public boolean m_6375_(double p_88998_, double p_88999_, int p_89000_) {
            RowButton.m_88036_(RealmsPendingInvitesScreen.this.f_88883_, this, this.f_88993_, p_89000_, p_88998_, p_88999_);
            return true;
        }

        private void m_89016_(PoseStack p_89017_, PendingInvite p_89018_, int p_89019_, int p_89020_, int p_89021_, int p_89022_) {
            RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, p_89018_.f_87423_, p_89019_ + 38, p_89020_ + 1, 0xFFFFFF);
            RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, p_89018_.f_87424_, p_89019_ + 38, p_89020_ + 12, 0x6C6C6C);
            RealmsPendingInvitesScreen.this.f_96547_.m_92883_(p_89017_, RealmsUtil.m_90223_(p_89018_.f_87426_), p_89019_ + 38, p_89020_ + 24, 0x6C6C6C);
            RowButton.m_88028_(p_89017_, this.f_88993_, RealmsPendingInvitesScreen.this.f_88883_, p_89019_, p_89020_, p_89021_, p_89022_);
            RealmsTextureManager.m_90187_(p_89018_.f_87425_, () -> {
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                PlayerFaceRenderer.m_240071_(p_89017_, p_89019_, p_89020_, 32);
            });
        }

        @Override
        public Component m_142172_() {
            Component $$0 = CommonComponents.m_178396_(Component.m_237113_(this.f_88992_.f_87423_), Component.m_237113_(this.f_88992_.f_87424_), Component.m_237113_(RealmsUtil.m_90223_(this.f_88992_.f_87426_)));
            return Component.m_237110_("narrator.select", $$0);
        }

        class AcceptRowButton
        extends RowButton {
            AcceptRowButton() {
                super(15, 15, 215, 5);
            }

            @Override
            protected void m_7537_(PoseStack p_89031_, int p_89032_, int p_89033_, boolean p_89034_) {
                RenderSystem.m_157456_(0, f_88875_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                float $$4 = p_89034_ ? 19.0f : 0.0f;
                GuiComponent.m_93133_(p_89031_, p_89032_, p_89033_, $$4, 0.0f, 18, 18, 37, 18);
                if (p_89034_) {
                    RealmsPendingInvitesScreen.this.f_88881_ = f_88878_;
                }
            }

            @Override
            public void m_7516_(int p_89029_) {
                RealmsPendingInvitesScreen.this.m_88932_(p_89029_);
            }
        }

        class RejectRowButton
        extends RowButton {
            RejectRowButton() {
                super(15, 15, 235, 5);
            }

            @Override
            protected void m_7537_(PoseStack p_89041_, int p_89042_, int p_89043_, boolean p_89044_) {
                RenderSystem.m_157456_(0, f_88876_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                float $$4 = p_89044_ ? 19.0f : 0.0f;
                GuiComponent.m_93133_(p_89041_, p_89042_, p_89043_, $$4, 0.0f, 18, 18, 37, 18);
                if (p_89044_) {
                    RealmsPendingInvitesScreen.this.f_88881_ = f_88879_;
                }
            }

            @Override
            public void m_7516_(int p_89039_) {
                RealmsPendingInvitesScreen.this.m_88922_(p_89039_);
            }
        }
    }
}

