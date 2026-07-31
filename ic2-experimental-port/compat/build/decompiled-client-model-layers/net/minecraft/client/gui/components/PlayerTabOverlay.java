/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Ordering
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class PlayerTabOverlay
extends GuiComponent {
    private static final Ordering<PlayerInfo> f_94518_ = Ordering.from((Comparator)new PlayerInfoComparator());
    public static final int f_169049_ = 20;
    public static final int f_169050_ = 16;
    public static final int f_169051_ = 25;
    public static final int f_169052_ = 52;
    public static final int f_169053_ = 61;
    public static final int f_169054_ = 160;
    public static final int f_169055_ = 169;
    public static final int f_169056_ = 70;
    public static final int f_169057_ = 79;
    private final Minecraft f_94519_;
    private final Gui f_94520_;
    @Nullable
    private Component f_94521_;
    @Nullable
    private Component f_94522_;
    private long f_94523_;
    private boolean f_94524_;

    public PlayerTabOverlay(Minecraft p_94527_, Gui p_94528_) {
        this.f_94519_ = p_94527_;
        this.f_94520_ = p_94528_;
    }

    public Component m_94549_(PlayerInfo p_94550_) {
        if (p_94550_.m_105342_() != null) {
            return this.m_94551_(p_94550_, p_94550_.m_105342_().m_6881_());
        }
        return this.m_94551_(p_94550_, PlayerTeam.m_83348_(p_94550_.m_105340_(), Component.m_237113_(p_94550_.m_105312_().getName())));
    }

    private Component m_94551_(PlayerInfo p_94552_, MutableComponent p_94553_) {
        return p_94552_.m_105325_() == GameType.SPECTATOR ? p_94553_.m_130940_(ChatFormatting.ITALIC) : p_94553_;
    }

    public void m_94556_(boolean p_94557_) {
        if (p_94557_ && !this.f_94524_) {
            this.f_94523_ = Util.m_137550_();
        }
        this.f_94524_ = p_94557_;
    }

    public void m_94544_(PoseStack p_94545_, int p_94546_, Scoreboard p_94547_, @Nullable Objective p_94548_) {
        int $$16;
        boolean $$13;
        int $$10;
        ClientPacketListener $$4 = this.f_94519_.f_91074_.f_108617_;
        List $$5 = f_94518_.sortedCopy($$4.m_105142_());
        int $$6 = 0;
        int $$7 = 0;
        for (PlayerInfo $$8 : $$5) {
            int $$9 = this.f_94519_.f_91062_.m_92852_(this.m_94549_($$8));
            $$6 = Math.max($$6, $$9);
            if (p_94548_ == null || p_94548_.m_83324_() == ObjectiveCriteria.RenderType.HEARTS) continue;
            $$9 = this.f_94519_.f_91062_.m_92895_(" " + p_94547_.m_83471_($$8.m_105312_().getName(), p_94548_).m_83400_());
            $$7 = Math.max($$7, $$9);
        }
        $$5 = $$5.subList(0, Math.min($$5.size(), 80));
        int $$11 = $$10 = $$5.size();
        int $$12 = 1;
        while ($$11 > 20) {
            $$11 = ($$10 + ++$$12 - 1) / $$12;
        }
        boolean bl = $$13 = this.f_94519_.m_91090_() || this.f_94519_.m_91403_().m_6198_().m_129535_();
        if (p_94548_ != null) {
            if (p_94548_.m_83324_() == ObjectiveCriteria.RenderType.HEARTS) {
                int $$14 = 90;
            } else {
                int $$15 = $$7;
            }
        } else {
            $$16 = 0;
        }
        int $$17 = Math.min($$12 * (($$13 ? 9 : 0) + $$6 + $$16 + 13), p_94546_ - 50) / $$12;
        int $$18 = p_94546_ / 2 - ($$17 * $$12 + ($$12 - 1) * 5) / 2;
        int $$19 = 10;
        int $$20 = $$17 * $$12 + ($$12 - 1) * 5;
        List<FormattedCharSequence> $$21 = null;
        if (this.f_94522_ != null) {
            $$21 = this.f_94519_.f_91062_.m_92923_(this.f_94522_, p_94546_ - 50);
            for (FormattedCharSequence formattedCharSequence : $$21) {
                $$20 = Math.max($$20, this.f_94519_.f_91062_.m_92724_(formattedCharSequence));
            }
        }
        List<FormattedCharSequence> $$23 = null;
        if (this.f_94521_ != null) {
            $$23 = this.f_94519_.f_91062_.m_92923_(this.f_94521_, p_94546_ - 50);
            for (FormattedCharSequence $$24 : $$23) {
                $$20 = Math.max($$20, this.f_94519_.f_91062_.m_92724_($$24));
            }
        }
        if ($$21 != null) {
            PlayerTabOverlay.m_93172_(p_94545_, p_94546_ / 2 - $$20 / 2 - 1, $$19 - 1, p_94546_ / 2 + $$20 / 2 + 1, $$19 + $$21.size() * this.f_94519_.f_91062_.f_92710_, Integer.MIN_VALUE);
            for (FormattedCharSequence $$25 : $$21) {
                int $$26 = this.f_94519_.f_91062_.m_92724_($$25);
                this.f_94519_.f_91062_.m_92744_(p_94545_, $$25, p_94546_ / 2 - $$26 / 2, $$19, -1);
                $$19 += this.f_94519_.f_91062_.f_92710_;
            }
            ++$$19;
        }
        PlayerTabOverlay.m_93172_(p_94545_, p_94546_ / 2 - $$20 / 2 - 1, $$19 - 1, p_94546_ / 2 + $$20 / 2 + 1, $$19 + $$11 * 9, Integer.MIN_VALUE);
        int n = this.f_94519_.f_91066_.m_92143_(0x20FFFFFF);
        for (int $$28 = 0; $$28 < $$10; ++$$28) {
            int $$38;
            int $$39;
            int $$29 = $$28 / $$11;
            int $$30 = $$28 % $$11;
            int $$31 = $$18 + $$29 * $$17 + $$29 * 5;
            int $$32 = $$19 + $$30 * 9;
            PlayerTabOverlay.m_93172_(p_94545_, $$31, $$32, $$31 + $$17, $$32 + 8, n);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            if ($$28 >= $$5.size()) continue;
            PlayerInfo $$33 = (PlayerInfo)$$5.get($$28);
            GameProfile $$34 = $$33.m_105312_();
            if ($$13) {
                Player $$35 = this.f_94519_.f_91073_.m_46003_($$34.getId());
                boolean $$36 = $$35 != null && LivingEntityRenderer.m_194453_($$35);
                boolean $$37 = $$35 != null && $$35.m_36170_(PlayerModelPart.HAT);
                RenderSystem.m_157456_(0, $$33.m_105337_());
                PlayerFaceRenderer.m_240132_(p_94545_, $$31, $$32, 8, $$37, $$36);
                $$31 += 9;
            }
            this.f_94519_.f_91062_.m_92763_(p_94545_, this.m_94549_($$33), $$31, $$32, $$33.m_105325_() == GameType.SPECTATOR ? -1862270977 : -1);
            if (p_94548_ != null && $$33.m_105325_() != GameType.SPECTATOR && ($$39 = ($$38 = $$31 + $$6 + 1) + $$16) - $$38 > 5) {
                this.m_94530_(p_94548_, $$32, $$34.getName(), $$38, $$39, $$33, p_94545_);
            }
            this.m_94538_(p_94545_, $$17, $$31 - ($$13 ? 9 : 0), $$32, $$33);
        }
        if ($$23 != null) {
            PlayerTabOverlay.m_93172_(p_94545_, p_94546_ / 2 - $$20 / 2 - 1, ($$19 += $$11 * 9 + 1) - 1, p_94546_ / 2 + $$20 / 2 + 1, $$19 + $$23.size() * this.f_94519_.f_91062_.f_92710_, Integer.MIN_VALUE);
            for (FormattedCharSequence $$40 : $$23) {
                int $$41 = this.f_94519_.f_91062_.m_92724_($$40);
                this.f_94519_.f_91062_.m_92744_(p_94545_, $$40, p_94546_ / 2 - $$41 / 2, $$19, -1);
                $$19 += this.f_94519_.f_91062_.f_92710_;
            }
        }
    }

    protected void m_94538_(PoseStack p_94539_, int p_94540_, int p_94541_, int p_94542_, PlayerInfo p_94543_) {
        int $$11;
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_93098_);
        boolean $$5 = false;
        if (p_94543_.m_105330_() < 0) {
            int $$6 = 5;
        } else if (p_94543_.m_105330_() < 150) {
            boolean $$7 = false;
        } else if (p_94543_.m_105330_() < 300) {
            boolean $$8 = true;
        } else if (p_94543_.m_105330_() < 600) {
            int $$9 = 2;
        } else if (p_94543_.m_105330_() < 1000) {
            int $$10 = 3;
        } else {
            $$11 = 4;
        }
        this.m_93250_(this.m_93252_() + 100);
        this.m_93228_(p_94539_, p_94541_ + p_94540_ - 11, p_94542_, 0, 176 + $$11 * 8, 10, 8);
        this.m_93250_(this.m_93252_() - 100);
    }

    private void m_94530_(Objective p_94531_, int p_94532_, String p_94533_, int p_94534_, int p_94535_, PlayerInfo p_94536_, PoseStack p_94537_) {
        int $$7 = p_94531_.m_83313_().m_83471_(p_94533_, p_94531_).m_83400_();
        if (p_94531_.m_83324_() == ObjectiveCriteria.RenderType.HEARTS) {
            boolean $$11;
            RenderSystem.m_157456_(0, f_93098_);
            long $$8 = Util.m_137550_();
            if (this.f_94523_ == p_94536_.m_105347_()) {
                if ($$7 < p_94536_.m_105343_()) {
                    p_94536_.m_105315_($$8);
                    p_94536_.m_105328_(this.f_94520_.m_93079_() + 20);
                } else if ($$7 > p_94536_.m_105343_()) {
                    p_94536_.m_105315_($$8);
                    p_94536_.m_105328_(this.f_94520_.m_93079_() + 10);
                }
            }
            if ($$8 - p_94536_.m_105345_() > 1000L || this.f_94523_ != p_94536_.m_105347_()) {
                p_94536_.m_105326_($$7);
                p_94536_.m_105331_($$7);
                p_94536_.m_105315_($$8);
            }
            p_94536_.m_105333_(this.f_94523_);
            p_94536_.m_105326_($$7);
            int $$9 = Mth.m_14167_((float)Math.max($$7, p_94536_.m_105344_()) / 2.0f);
            int $$10 = Math.max(Mth.m_14167_($$7 / 2), Math.max(Mth.m_14167_(p_94536_.m_105344_() / 2), 10));
            boolean bl = $$11 = p_94536_.m_105346_() > (long)this.f_94520_.m_93079_() && (p_94536_.m_105346_() - (long)this.f_94520_.m_93079_()) / 3L % 2L == 1L;
            if ($$9 > 0) {
                int $$12 = Mth.m_14143_(Math.min((float)(p_94535_ - p_94534_ - 4) / (float)$$10, 9.0f));
                if ($$12 > 3) {
                    for (int $$13 = $$9; $$13 < $$10; ++$$13) {
                        this.m_93228_(p_94537_, p_94534_ + $$13 * $$12, p_94532_, $$11 ? 25 : 16, 0, 9, 9);
                    }
                    for (int $$14 = 0; $$14 < $$9; ++$$14) {
                        this.m_93228_(p_94537_, p_94534_ + $$14 * $$12, p_94532_, $$11 ? 25 : 16, 0, 9, 9);
                        if ($$11) {
                            if ($$14 * 2 + 1 < p_94536_.m_105344_()) {
                                this.m_93228_(p_94537_, p_94534_ + $$14 * $$12, p_94532_, 70, 0, 9, 9);
                            }
                            if ($$14 * 2 + 1 == p_94536_.m_105344_()) {
                                this.m_93228_(p_94537_, p_94534_ + $$14 * $$12, p_94532_, 79, 0, 9, 9);
                            }
                        }
                        if ($$14 * 2 + 1 < $$7) {
                            this.m_93228_(p_94537_, p_94534_ + $$14 * $$12, p_94532_, $$14 >= 10 ? 160 : 52, 0, 9, 9);
                        }
                        if ($$14 * 2 + 1 != $$7) continue;
                        this.m_93228_(p_94537_, p_94534_ + $$14 * $$12, p_94532_, $$14 >= 10 ? 169 : 61, 0, 9, 9);
                    }
                } else {
                    float $$15 = Mth.m_14036_((float)$$7 / 20.0f, 0.0f, 1.0f);
                    int $$16 = (int)((1.0f - $$15) * 255.0f) << 16 | (int)($$15 * 255.0f) << 8;
                    String $$17 = "" + (float)$$7 / 2.0f;
                    if (p_94535_ - this.f_94519_.f_91062_.m_92895_($$17 + "hp") >= p_94534_) {
                        $$17 = $$17 + "hp";
                    }
                    this.f_94519_.f_91062_.m_92750_(p_94537_, $$17, (p_94535_ + p_94534_) / 2 - this.f_94519_.f_91062_.m_92895_($$17) / 2, p_94532_, $$16);
                }
            }
        } else {
            String $$18 = "" + ChatFormatting.YELLOW + $$7;
            this.f_94519_.f_91062_.m_92750_(p_94537_, $$18, p_94535_ - this.f_94519_.f_91062_.m_92895_($$18), p_94532_, 0xFFFFFF);
        }
    }

    public void m_94554_(@Nullable Component p_94555_) {
        this.f_94521_ = p_94555_;
    }

    public void m_94558_(@Nullable Component p_94559_) {
        this.f_94522_ = p_94559_;
    }

    public void m_94529_() {
        this.f_94522_ = null;
        this.f_94521_ = null;
    }

    static class PlayerInfoComparator
    implements Comparator<PlayerInfo> {
        PlayerInfoComparator() {
        }

        @Override
        public int compare(PlayerInfo p_94564_, PlayerInfo p_94565_) {
            PlayerTeam $$2 = p_94564_.m_105340_();
            PlayerTeam $$3 = p_94565_.m_105340_();
            return ComparisonChain.start().compareTrueFirst(p_94564_.m_105325_() != GameType.SPECTATOR, p_94565_.m_105325_() != GameType.SPECTATOR).compare((Comparable)((Object)($$2 != null ? $$2.m_5758_() : "")), (Comparable)((Object)($$3 != null ? $$3.m_5758_() : ""))).compare((Object)p_94564_.m_105312_().getName(), (Object)p_94565_.m_105312_().getName(), String::compareToIgnoreCase).result();
        }

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.compare((PlayerInfo)object, (PlayerInfo)object2);
        }
    }
}

