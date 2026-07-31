/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.social;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsPlayerList;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SocialInteractionsScreen
extends Screen {
    protected static final ResourceLocation f_100736_ = new ResourceLocation("textures/gui/social_interactions.png");
    private static final Component f_100737_ = Component.m_237115_("gui.socialInteractions.tab_all");
    private static final Component f_100738_ = Component.m_237115_("gui.socialInteractions.tab_hidden");
    private static final Component f_100739_ = Component.m_237115_("gui.socialInteractions.tab_blocked");
    private static final Component f_100740_ = f_100737_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
    private static final Component f_100741_ = f_100738_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
    private static final Component f_100742_ = f_100739_.m_6879_().m_130940_(ChatFormatting.UNDERLINE);
    private static final Component f_100743_ = Component.m_237115_("gui.socialInteractions.search_hint").m_130940_(ChatFormatting.ITALIC).m_130940_(ChatFormatting.GRAY);
    static final Component f_100744_ = Component.m_237115_("gui.socialInteractions.search_empty").m_130940_(ChatFormatting.GRAY);
    private static final Component f_100745_ = Component.m_237115_("gui.socialInteractions.empty_hidden").m_130940_(ChatFormatting.GRAY);
    private static final Component f_100746_ = Component.m_237115_("gui.socialInteractions.empty_blocked").m_130940_(ChatFormatting.GRAY);
    private static final Component f_100747_ = Component.m_237115_("gui.socialInteractions.blocking_hint");
    private static final String f_170139_ = "https://aka.ms/javablocking";
    private static final int f_170140_ = 8;
    private static final int f_170130_ = 16;
    private static final int f_170131_ = 236;
    private static final int f_170132_ = 16;
    private static final int f_170133_ = 64;
    public static final int f_170137_ = 88;
    public static final int f_170138_ = 78;
    private static final int f_170134_ = 238;
    private static final int f_170135_ = 20;
    private static final int f_170136_ = 36;
    SocialInteractionsPlayerList f_100748_;
    EditBox f_100749_;
    private String f_100726_ = "";
    private Page f_100727_ = Page.ALL;
    private Button f_100728_;
    private Button f_100729_;
    private Button f_100730_;
    private Button f_100731_;
    @Nullable
    private Component f_100732_;
    private int f_100733_;
    private boolean f_100734_;
    @Nullable
    private Runnable f_100735_;

    public SocialInteractionsScreen() {
        super(Component.m_237115_("gui.socialInteractions.title"));
        this.m_100767_(Minecraft.m_91087_());
    }

    private int m_100799_() {
        return Math.max(52, this.f_96544_ - 128 - 16);
    }

    private int m_100800_() {
        return this.m_100799_() / 16;
    }

    private int m_100801_() {
        return 80 + this.m_100800_() * 16 - 8;
    }

    private int m_100802_() {
        return (this.f_96543_ - 238) / 2;
    }

    @Override
    public Component m_142562_() {
        if (this.f_100732_ != null) {
            return CommonComponents.m_178398_(super.m_142562_(), this.f_100732_);
        }
        return super.m_142562_();
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        this.f_100749_.m_94120_();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        if (this.f_100734_) {
            this.f_100748_.m_93437_(this.f_96543_, this.f_96544_, 88, this.m_100801_());
        } else {
            this.f_100748_ = new SocialInteractionsPlayerList(this, this.f_96541_, this.f_96543_, this.f_96544_, 88, this.m_100801_(), 36);
        }
        int $$0 = this.f_100748_.m_5759_() / 3;
        int $$1 = this.f_100748_.m_5747_();
        int $$2 = this.f_100748_.m_93520_();
        int $$3 = this.f_96547_.m_92852_(f_100747_) + 40;
        int $$4 = 64 + 16 * this.m_100800_();
        int $$5 = (this.f_96543_ - $$3) / 2 + 3;
        this.f_100728_ = this.m_142416_(new Button($$1, 45, $$0, 20, f_100737_, p_240243_ -> this.m_100771_(Page.ALL)));
        this.f_100729_ = this.m_142416_(new Button(($$1 + $$2 - $$0) / 2 + 1, 45, $$0, 20, f_100738_, p_100791_ -> this.m_100771_(Page.HIDDEN)));
        this.f_100730_ = this.m_142416_(new Button($$2 - $$0 + 1, 45, $$0, 20, f_100739_, p_100785_ -> this.m_100771_(Page.BLOCKED)));
        String $$6 = this.f_100749_ != null ? this.f_100749_.m_94155_() : "";
        this.f_100749_ = new EditBox(this.f_96547_, this.m_100802_() + 28, 78, 196, 16, f_100743_){

            @Override
            protected MutableComponent m_5646_() {
                if (!SocialInteractionsScreen.this.f_100749_.m_94155_().isEmpty() && SocialInteractionsScreen.this.f_100748_.m_100724_()) {
                    return super.m_5646_().m_130946_(", ").m_7220_(f_100744_);
                }
                return super.m_5646_();
            }
        };
        this.f_100749_.m_94199_(16);
        this.f_100749_.m_94182_(false);
        this.f_100749_.m_94194_(true);
        this.f_100749_.m_94202_(0xFFFFFF);
        this.f_100749_.m_94144_($$6);
        this.f_100749_.m_94151_(this::m_100788_);
        this.m_7787_(this.f_100749_);
        this.m_7787_(this.f_100748_);
        this.f_100731_ = this.m_142416_(new Button($$5, $$4, $$3, 20, f_100747_, p_100770_ -> this.f_96541_.m_91152_(new ConfirmLinkScreen(p_170143_ -> {
            if (p_170143_) {
                Util.m_137581_().m_137646_(f_170139_);
            }
            this.f_96541_.m_91152_(this);
        }, f_170139_, true))));
        this.f_100734_ = true;
        this.m_100771_(this.f_100727_);
    }

    private void m_100771_(Page p_100772_) {
        this.f_100727_ = p_100772_;
        this.f_100728_.m_93666_(f_100737_);
        this.f_100729_.m_93666_(f_100738_);
        this.f_100730_.m_93666_(f_100739_);
        boolean $$1 = false;
        switch (p_100772_) {
            case ALL: {
                this.f_100728_.m_93666_(f_100740_);
                Collection<UUID> $$2 = this.f_96541_.f_91074_.f_108617_.m_105143_();
                this.f_100748_.m_240702_($$2, this.f_100748_.m_93517_(), true);
                break;
            }
            case HIDDEN: {
                this.f_100729_.m_93666_(f_100741_);
                Set<UUID> $$3 = this.f_96541_.m_91266_().m_100675_();
                $$1 = $$3.isEmpty();
                this.f_100748_.m_240702_($$3, this.f_100748_.m_93517_(), false);
                break;
            }
            case BLOCKED: {
                this.f_100730_.m_93666_(f_100742_);
                PlayerSocialManager $$4 = this.f_96541_.m_91266_();
                Set<UUID> $$5 = this.f_96541_.f_91074_.f_108617_.m_105143_().stream().filter($$4::m_100688_).collect(Collectors.toSet());
                $$1 = $$5.isEmpty();
                this.f_100748_.m_240702_($$5, this.f_100748_.m_93517_(), false);
            }
        }
        GameNarrator $$6 = this.f_96541_.m_240477_();
        if (!this.f_100749_.m_94155_().isEmpty() && this.f_100748_.m_100724_() && !this.f_100749_.m_93696_()) {
            $$6.m_168785_(f_100744_);
        } else if ($$1) {
            if (p_100772_ == Page.HIDDEN) {
                $$6.m_168785_(f_100745_);
            } else if (p_100772_ == Page.BLOCKED) {
                $$6.m_168785_(f_100746_);
            }
        }
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public void m_7333_(PoseStack p_100761_) {
        int $$1 = this.m_100802_() + 3;
        super.m_7333_(p_100761_);
        RenderSystem.m_157456_(0, f_100736_);
        this.m_93228_(p_100761_, $$1, 64, 1, 1, 236, 8);
        int $$2 = this.m_100800_();
        for (int $$3 = 0; $$3 < $$2; ++$$3) {
            this.m_93228_(p_100761_, $$1, 72 + 16 * $$3, 1, 10, 236, 16);
        }
        this.m_93228_(p_100761_, $$1, 72 + 16 * $$2, 1, 27, 236, 8);
        this.m_93228_(p_100761_, $$1 + 10, 76, 243, 1, 12, 12);
    }

    @Override
    public void m_6305_(PoseStack p_100763_, int p_100764_, int p_100765_, float p_100766_) {
        this.m_100767_(this.f_96541_);
        this.m_7333_(p_100763_);
        if (this.f_100732_ != null) {
            SocialInteractionsScreen.m_93243_(p_100763_, this.f_96541_.f_91062_, this.f_100732_, this.m_100802_() + 8, 35, -1);
        }
        if (!this.f_100748_.m_100724_()) {
            this.f_100748_.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
        } else if (!this.f_100749_.m_94155_().isEmpty()) {
            SocialInteractionsScreen.m_93215_(p_100763_, this.f_96541_.f_91062_, f_100744_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
        } else if (this.f_100727_ == Page.HIDDEN) {
            SocialInteractionsScreen.m_93215_(p_100763_, this.f_96541_.f_91062_, f_100745_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
        } else if (this.f_100727_ == Page.BLOCKED) {
            SocialInteractionsScreen.m_93215_(p_100763_, this.f_96541_.f_91062_, f_100746_, this.f_96543_ / 2, (78 + this.m_100801_()) / 2, -1);
        }
        if (!this.f_100749_.m_93696_() && this.f_100749_.m_94155_().isEmpty()) {
            SocialInteractionsScreen.m_93243_(p_100763_, this.f_96541_.f_91062_, f_100743_, this.f_100749_.f_93620_, this.f_100749_.f_93621_, -1);
        } else {
            this.f_100749_.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
        }
        this.f_100731_.f_93624_ = this.f_100727_ == Page.BLOCKED;
        super.m_6305_(p_100763_, p_100764_, p_100765_, p_100766_);
        if (this.f_100735_ != null) {
            this.f_100735_.run();
        }
    }

    @Override
    public boolean m_6375_(double p_100753_, double p_100754_, int p_100755_) {
        if (this.f_100749_.m_93696_()) {
            this.f_100749_.m_6375_(p_100753_, p_100754_, p_100755_);
        }
        return super.m_6375_(p_100753_, p_100754_, p_100755_) || this.f_100748_.m_6375_(p_100753_, p_100754_, p_100755_);
    }

    @Override
    public boolean m_7933_(int p_100757_, int p_100758_, int p_100759_) {
        if (!this.f_100749_.m_93696_() && this.f_96541_.f_91066_.f_92101_.m_90832_(p_100757_, p_100758_)) {
            this.f_96541_.m_91152_(null);
            return true;
        }
        return super.m_7933_(p_100757_, p_100758_, p_100759_);
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    private void m_100788_(String p_100789_) {
        if (!(p_100789_ = p_100789_.toLowerCase(Locale.ROOT)).equals(this.f_100726_)) {
            this.f_100748_.m_100717_(p_100789_);
            this.f_100726_ = p_100789_;
            this.m_100771_(this.f_100727_);
        }
    }

    private void m_100767_(Minecraft p_100768_) {
        int $$1 = p_100768_.m_91403_().m_105142_().size();
        if (this.f_100733_ != $$1) {
            String $$2 = "";
            ServerData $$3 = p_100768_.m_91089_();
            if (p_100768_.m_91090_()) {
                $$2 = p_100768_.m_91092_().m_129916_();
            } else if ($$3 != null) {
                $$2 = $$3.f_105362_;
            }
            this.f_100732_ = $$1 > 1 ? Component.m_237110_("gui.socialInteractions.server_label.multiple", $$2, $$1) : Component.m_237110_("gui.socialInteractions.server_label.single", $$2, $$1);
            this.f_100733_ = $$1;
        }
    }

    public void m_100775_(PlayerInfo p_100776_) {
        this.f_100748_.m_100714_(p_100776_, this.f_100727_);
    }

    public void m_100779_(UUID p_100780_) {
        this.f_100748_.m_100722_(p_100780_);
    }

    public void m_100777_(@Nullable Runnable p_100778_) {
        this.f_100735_ = p_100778_;
    }

    public static final class Page
    extends Enum<Page> {
        public static final /* enum */ Page ALL = new Page();
        public static final /* enum */ Page HIDDEN = new Page();
        public static final /* enum */ Page BLOCKED = new Page();
        private static final /* synthetic */ Page[] $VALUES;

        public static Page[] values() {
            return (Page[])$VALUES.clone();
        }

        public static Page valueOf(String p_100824_) {
            return Enum.valueOf(Page.class, p_100824_);
        }

        private static /* synthetic */ Page[] m_170144_() {
            return new Page[]{ALL, HIDDEN, BLOCKED};
        }

        static {
            $VALUES = Page.m_170144_();
        }
    }
}

