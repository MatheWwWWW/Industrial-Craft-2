/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Ordering
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class Gui
extends GuiComponent {
    private static final ResourceLocation f_92981_ = new ResourceLocation("textures/misc/vignette.png");
    private static final ResourceLocation f_92982_ = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation f_92983_ = new ResourceLocation("textures/misc/pumpkinblur.png");
    private static final ResourceLocation f_168665_ = new ResourceLocation("textures/misc/spyglass_scope.png");
    private static final ResourceLocation f_168666_ = new ResourceLocation("textures/misc/powder_snow_outline.png");
    private static final Component f_92984_ = Component.m_237115_("demo.demoExpired");
    private static final Component f_193830_ = Component.m_237115_("menu.savingLevel");
    private static final int f_168667_ = 0xFFFFFF;
    private static final float f_168668_ = 5.0f;
    private static final int f_168669_ = 10;
    private static final int f_168670_ = 10;
    private static final String f_168671_ = ": ";
    private static final float f_168672_ = 0.2f;
    private static final int f_168673_ = 9;
    private static final int f_168674_ = 8;
    private static final float f_193831_ = 0.2f;
    private final RandomSource f_92985_ = RandomSource.m_216327_();
    private final Minecraft f_92986_;
    private final ItemRenderer f_92987_;
    private final ChatComponent f_92988_;
    private int f_92989_;
    @Nullable
    private Component f_92990_;
    private int f_92991_;
    private boolean f_92992_;
    private boolean f_238167_;
    public float f_92980_ = 1.0f;
    private int f_92993_;
    private ItemStack f_92994_ = ItemStack.f_41583_;
    private final DebugScreenOverlay f_92995_;
    private final SubtitleOverlay f_92996_;
    private final SpectatorGui f_92997_;
    private final PlayerTabOverlay f_92998_;
    private final BossHealthOverlay f_92999_;
    private int f_93000_;
    @Nullable
    private Component f_93001_;
    @Nullable
    private Component f_93002_;
    private int f_92970_;
    private int f_92971_;
    private int f_92972_;
    private int f_92973_;
    private int f_92974_;
    private long f_92975_;
    private long f_92976_;
    private int f_92977_;
    private int f_92978_;
    private float f_193828_;
    private float f_193829_;
    private float f_168664_;

    public Gui(Minecraft p_232355_, ItemRenderer p_232356_) {
        this.f_92986_ = p_232355_;
        this.f_92987_ = p_232356_;
        this.f_92995_ = new DebugScreenOverlay(p_232355_);
        this.f_92997_ = new SpectatorGui(p_232355_);
        this.f_92988_ = new ChatComponent(p_232355_);
        this.f_92998_ = new PlayerTabOverlay(p_232355_, this);
        this.f_92999_ = new BossHealthOverlay(p_232355_);
        this.f_92996_ = new SubtitleOverlay(p_232355_);
        this.m_93006_();
    }

    public void m_93006_() {
        this.f_92970_ = 10;
        this.f_92971_ = 70;
        this.f_92972_ = 20;
    }

    public void m_93030_(PoseStack p_93031_, float p_93032_) {
        float $$5;
        this.f_92977_ = this.f_92986_.m_91268_().m_85445_();
        this.f_92978_ = this.f_92986_.m_91268_().m_85446_();
        Font $$2 = this.m_93082_();
        RenderSystem.m_69478_();
        if (Minecraft.m_91405_()) {
            this.m_93067_(this.f_92986_.m_91288_());
        } else {
            RenderSystem.m_69482_();
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_69453_();
        }
        float $$3 = this.f_92986_.m_91297_();
        this.f_168664_ = Mth.m_14179_(0.5f * $$3, this.f_168664_, 1.125f);
        if (this.f_92986_.f_91066_.m_92176_().m_90612_()) {
            if (this.f_92986_.f_91074_.m_150108_()) {
                this.m_168675_(this.f_168664_);
            } else {
                this.f_168664_ = 0.5f;
                ItemStack $$4 = this.f_92986_.f_91074_.m_150109_().m_36052_(3);
                if ($$4.m_150930_(Blocks.f_50143_.m_5456_())) {
                    this.m_168708_(f_92983_, 1.0f);
                }
            }
        }
        if (this.f_92986_.f_91074_.m_146888_() > 0) {
            this.m_168708_(f_168666_, this.f_92986_.f_91074_.m_146889_());
        }
        if (($$5 = Mth.m_14179_(p_93032_, this.f_92986_.f_91074_.f_108590_, this.f_92986_.f_91074_.f_108589_)) > 0.0f && !this.f_92986_.f_91074_.m_21023_(MobEffects.f_19604_)) {
            this.m_93007_($$5);
        }
        if (this.f_92986_.f_91072_.m_105295_() == GameType.SPECTATOR) {
            this.f_92997_.m_193837_(p_93031_);
        } else if (!this.f_92986_.f_91066_.f_92062_) {
            this.m_93009_(p_93032_, p_93031_);
        }
        if (!this.f_92986_.f_91066_.f_92062_) {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_93098_);
            RenderSystem.m_69478_();
            this.m_93080_(p_93031_);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_69453_();
            this.f_92986_.m_91307_().m_6180_("bossHealth");
            this.f_92999_.m_93704_(p_93031_);
            this.f_92986_.m_91307_().m_7238_();
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157456_(0, f_93098_);
            if (this.f_92986_.f_91072_.m_105205_()) {
                this.m_93083_(p_93031_);
            }
            this.m_93086_(p_93031_);
            RenderSystem.m_69461_();
            int $$6 = this.f_92977_ / 2 - 91;
            if (this.f_92986_.f_91074_.m_108633_()) {
                this.m_93033_(p_93031_, $$6);
            } else if (this.f_92986_.f_91072_.m_105288_()) {
                this.m_93071_(p_93031_, $$6);
            }
            if (this.f_92986_.f_91066_.f_92130_ && this.f_92986_.f_91072_.m_105295_() != GameType.SPECTATOR) {
                this.m_93069_(p_93031_);
            } else if (this.f_92986_.f_91074_.m_5833_()) {
                this.f_92997_.m_94773_(p_93031_);
            }
        }
        if (this.f_92986_.f_91074_.m_36318_() > 0) {
            this.f_92986_.m_91307_().m_6180_("sleep");
            RenderSystem.m_69465_();
            float $$7 = this.f_92986_.f_91074_.m_36318_();
            float $$8 = $$7 / 100.0f;
            if ($$8 > 1.0f) {
                $$8 = 1.0f - ($$7 - 100.0f) / 10.0f;
            }
            int $$9 = (int)(220.0f * $$8) << 24 | 0x101020;
            Gui.m_93172_(p_93031_, 0, 0, this.f_92977_, this.f_92978_, $$9);
            RenderSystem.m_69482_();
            this.f_92986_.m_91307_().m_7238_();
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.f_92986_.m_91402_()) {
            this.m_93077_(p_93031_);
        }
        this.m_93028_(p_93031_);
        if (this.f_92986_.f_91066_.f_92063_) {
            this.f_92995_.m_94056_(p_93031_);
        }
        if (!this.f_92986_.f_91066_.f_92062_) {
            Objective $$25;
            int $$24;
            if (this.f_92990_ != null && this.f_92991_ > 0) {
                this.f_92986_.m_91307_().m_6180_("overlayMessage");
                float $$10 = (float)this.f_92991_ - p_93032_;
                int $$11 = (int)($$10 * 255.0f / 20.0f);
                if ($$11 > 255) {
                    $$11 = 255;
                }
                if ($$11 > 8) {
                    p_93031_.m_85836_();
                    p_93031_.m_85837_(this.f_92977_ / 2, this.f_92978_ - 68, 0.0);
                    RenderSystem.m_69478_();
                    RenderSystem.m_69453_();
                    int $$12 = 0xFFFFFF;
                    if (this.f_92992_) {
                        $$12 = Mth.m_14169_($$10 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                    }
                    int $$13 = $$11 << 24 & 0xFF000000;
                    int $$14 = $$2.m_92852_(this.f_92990_);
                    this.m_93039_(p_93031_, $$2, -4, $$14, 0xFFFFFF | $$13);
                    $$2.m_92763_(p_93031_, this.f_92990_, -$$14 / 2, -4.0f, $$12 | $$13);
                    RenderSystem.m_69461_();
                    p_93031_.m_85849_();
                }
                this.f_92986_.m_91307_().m_7238_();
            }
            if (this.f_93001_ != null && this.f_93000_ > 0) {
                this.f_92986_.m_91307_().m_6180_("titleAndSubtitle");
                float $$15 = (float)this.f_93000_ - p_93032_;
                int $$16 = 255;
                if (this.f_93000_ > this.f_92972_ + this.f_92971_) {
                    float $$17 = (float)(this.f_92970_ + this.f_92971_ + this.f_92972_) - $$15;
                    $$16 = (int)($$17 * 255.0f / (float)this.f_92970_);
                }
                if (this.f_93000_ <= this.f_92972_) {
                    $$16 = (int)($$15 * 255.0f / (float)this.f_92972_);
                }
                if (($$16 = Mth.m_14045_($$16, 0, 255)) > 8) {
                    p_93031_.m_85836_();
                    p_93031_.m_85837_(this.f_92977_ / 2, this.f_92978_ / 2, 0.0);
                    RenderSystem.m_69478_();
                    RenderSystem.m_69453_();
                    p_93031_.m_85836_();
                    p_93031_.m_85841_(4.0f, 4.0f, 4.0f);
                    int $$18 = $$16 << 24 & 0xFF000000;
                    int $$19 = $$2.m_92852_(this.f_93001_);
                    this.m_93039_(p_93031_, $$2, -10, $$19, 0xFFFFFF | $$18);
                    $$2.m_92763_(p_93031_, this.f_93001_, -$$19 / 2, -10.0f, 0xFFFFFF | $$18);
                    p_93031_.m_85849_();
                    if (this.f_93002_ != null) {
                        p_93031_.m_85836_();
                        p_93031_.m_85841_(2.0f, 2.0f, 2.0f);
                        int $$20 = $$2.m_92852_(this.f_93002_);
                        this.m_93039_(p_93031_, $$2, 5, $$20, 0xFFFFFF | $$18);
                        $$2.m_92763_(p_93031_, this.f_93002_, -$$20 / 2, 5.0f, 0xFFFFFF | $$18);
                        p_93031_.m_85849_();
                    }
                    RenderSystem.m_69461_();
                    p_93031_.m_85849_();
                }
                this.f_92986_.m_91307_().m_7238_();
            }
            this.f_92996_.m_94642_(p_93031_);
            Scoreboard $$21 = this.f_92986_.f_91073_.m_6188_();
            Objective $$22 = null;
            PlayerTeam $$23 = $$21.m_83500_(this.f_92986_.f_91074_.m_6302_());
            if ($$23 != null && ($$24 = $$23.m_7414_().m_126656_()) >= 0) {
                $$22 = $$21.m_83416_(3 + $$24);
            }
            Objective objective = $$25 = $$22 != null ? $$22 : $$21.m_83416_(1);
            if ($$25 != null) {
                this.m_93036_(p_93031_, $$25);
            }
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            p_93031_.m_85836_();
            p_93031_.m_85837_(0.0, this.f_92978_ - 48, 0.0);
            this.f_92986_.m_91307_().m_6180_("chat");
            this.f_92988_.m_93780_(p_93031_, this.f_92989_);
            this.f_92986_.m_91307_().m_7238_();
            p_93031_.m_85849_();
            $$25 = $$21.m_83416_(0);
            if (this.f_92986_.f_91066_.f_92099_.m_90857_() && (!this.f_92986_.m_91090_() || this.f_92986_.f_91074_.f_108617_.m_105142_().size() > 1 || $$25 != null)) {
                this.f_92998_.m_94556_(true);
                this.f_92998_.m_94544_(p_93031_, this.f_92977_, $$21, $$25);
            } else {
                this.f_92998_.m_94556_(false);
            }
            this.m_193834_(p_93031_);
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void m_93039_(PoseStack p_93040_, Font p_93041_, int p_93042_, int p_93043_, int p_93044_) {
        int $$5 = this.f_92986_.f_91066_.m_92170_(0.0f);
        if ($$5 != 0) {
            int $$6 = -p_93043_ / 2;
            Gui.m_93172_(p_93040_, $$6 - 2, p_93042_ - 2, $$6 + p_93043_ + 2, p_93042_ + p_93041_.f_92710_ + 2, FastColor.ARGB32.m_13657_($$5, p_93044_));
        }
    }

    private void m_93080_(PoseStack p_93081_) {
        Options $$1 = this.f_92986_.f_91066_;
        if (!$$1.m_92176_().m_90612_()) {
            return;
        }
        if (this.f_92986_.f_91072_.m_105295_() == GameType.SPECTATOR && !this.m_93024_(this.f_92986_.f_91077_)) {
            return;
        }
        if ($$1.f_92063_ && !$$1.f_92062_ && !this.f_92986_.f_91074_.m_36330_() && !$$1.m_231824_().m_231551_().booleanValue()) {
            Camera $$2 = this.f_92986_.f_91063_.m_109153_();
            PoseStack $$3 = RenderSystem.m_157191_();
            $$3.m_85836_();
            $$3.m_85837_(this.f_92977_ / 2, this.f_92978_ / 2, this.m_93252_());
            $$3.m_85845_(Vector3f.f_122222_.m_122240_($$2.m_90589_()));
            $$3.m_85845_(Vector3f.f_122225_.m_122240_($$2.m_90590_()));
            $$3.m_85841_(-1.0f, -1.0f, -1.0f);
            RenderSystem.m_157182_();
            RenderSystem.m_69881_(10);
            $$3.m_85849_();
            RenderSystem.m_157182_();
        } else {
            RenderSystem.m_69416_(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            int $$4 = 15;
            this.m_93228_(p_93081_, (this.f_92977_ - 15) / 2, (this.f_92978_ - 15) / 2, 0, 0, 15, 15);
            if (this.f_92986_.f_91066_.m_232120_().m_231551_() == AttackIndicatorStatus.CROSSHAIR) {
                float $$5 = this.f_92986_.f_91074_.m_36403_(0.0f);
                boolean $$6 = false;
                if (this.f_92986_.f_91076_ != null && this.f_92986_.f_91076_ instanceof LivingEntity && $$5 >= 1.0f) {
                    $$6 = this.f_92986_.f_91074_.m_36333_() > 5.0f;
                    $$6 &= this.f_92986_.f_91076_.m_6084_();
                }
                int $$7 = this.f_92978_ / 2 - 7 + 16;
                int $$8 = this.f_92977_ / 2 - 8;
                if ($$6) {
                    this.m_93228_(p_93081_, $$8, $$7, 68, 94, 16, 16);
                } else if ($$5 < 1.0f) {
                    int $$9 = (int)($$5 * 17.0f);
                    this.m_93228_(p_93081_, $$8, $$7, 36, 94, 16, 4);
                    this.m_93228_(p_93081_, $$8, $$7, 52, 94, $$9, 4);
                }
            }
        }
    }

    private boolean m_93024_(HitResult p_93025_) {
        if (p_93025_ == null) {
            return false;
        }
        if (p_93025_.m_6662_() == HitResult.Type.ENTITY) {
            return ((EntityHitResult)p_93025_).m_82443_() instanceof MenuProvider;
        }
        if (p_93025_.m_6662_() == HitResult.Type.BLOCK) {
            ClientLevel $$2 = this.f_92986_.f_91073_;
            BlockPos $$1 = ((BlockHitResult)p_93025_).m_82425_();
            return $$2.m_8055_($$1).m_60750_($$2, $$1) != null;
        }
        return false;
    }

    protected void m_93028_(PoseStack p_93029_) {
        EffectRenderingInventoryScreen $$2;
        Screen screen;
        Collection<MobEffectInstance> $$1 = this.f_92986_.f_91074_.m_21220_();
        if ($$1.isEmpty() || (screen = this.f_92986_.f_91080_) instanceof EffectRenderingInventoryScreen && ($$2 = (EffectRenderingInventoryScreen)screen).m_194018_()) {
            return;
        }
        RenderSystem.m_69478_();
        int $$3 = 0;
        int $$4 = 0;
        MobEffectTextureManager $$5 = this.f_92986_.m_91306_();
        ArrayList $$6 = Lists.newArrayListWithExpectedSize((int)$$1.size());
        RenderSystem.m_157456_(0, AbstractContainerScreen.f_97725_);
        for (MobEffectInstance $$7 : Ordering.natural().reverse().sortedCopy($$1)) {
            MobEffect $$8 = $$7.m_19544_();
            if (!$$7.m_19575_()) continue;
            int $$9 = this.f_92977_;
            int $$10 = 1;
            if (this.f_92986_.m_91402_()) {
                $$10 += 15;
            }
            if ($$8.m_19486_()) {
                $$9 -= 25 * ++$$3;
            } else {
                $$9 -= 25 * ++$$4;
                $$10 += 26;
            }
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            float $$11 = 1.0f;
            if ($$7.m_19571_()) {
                this.m_93228_(p_93029_, $$9, $$10, 165, 166, 24, 24);
            } else {
                this.m_93228_(p_93029_, $$9, $$10, 141, 166, 24, 24);
                if ($$7.m_19557_() <= 200) {
                    int $$12 = 10 - $$7.m_19557_() / 20;
                    $$11 = Mth.m_14036_((float)$$7.m_19557_() / 10.0f / 5.0f * 0.5f, 0.0f, 0.5f) + Mth.m_14089_((float)$$7.m_19557_() * (float)Math.PI / 5.0f) * Mth.m_14036_((float)$$12 / 10.0f * 0.25f, 0.0f, 0.25f);
                }
            }
            TextureAtlasSprite $$13 = $$5.m_118732_($$8);
            int $$14 = $$9;
            int $$15 = $$10;
            float $$16 = $$11;
            $$6.add(() -> {
                RenderSystem.m_157456_(0, $$13.m_118414_().m_118330_());
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, $$16);
                Gui.m_93200_(p_93029_, $$14 + 3, $$15 + 3, this.m_93252_(), 18, 18, $$13);
            });
        }
        $$6.forEach(Runnable::run);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void m_93009_(float p_93010_, PoseStack p_93011_) {
        float $$14;
        Player $$2 = this.m_93092_();
        if ($$2 == null) {
            return;
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_92982_);
        ItemStack $$3 = $$2.m_21206_();
        HumanoidArm $$4 = $$2.m_5737_().m_20828_();
        int $$5 = this.f_92977_ / 2;
        int $$6 = this.m_93252_();
        int $$7 = 182;
        int $$8 = 91;
        this.m_93250_(-90);
        this.m_93228_(p_93011_, $$5 - 91, this.f_92978_ - 22, 0, 0, 182, 22);
        this.m_93228_(p_93011_, $$5 - 91 - 1 + $$2.m_150109_().f_35977_ * 20, this.f_92978_ - 22 - 1, 0, 22, 24, 22);
        if (!$$3.m_41619_()) {
            if ($$4 == HumanoidArm.LEFT) {
                this.m_93228_(p_93011_, $$5 - 91 - 29, this.f_92978_ - 23, 24, 22, 29, 24);
            } else {
                this.m_93228_(p_93011_, $$5 + 91, this.f_92978_ - 23, 53, 22, 29, 24);
            }
        }
        this.m_93250_($$6);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        int $$9 = 1;
        for (int $$10 = 0; $$10 < 9; ++$$10) {
            int $$11 = $$5 - 90 + $$10 * 20 + 2;
            int $$12 = this.f_92978_ - 16 - 3;
            this.m_168677_($$11, $$12, p_93010_, $$2, $$2.m_150109_().f_35974_.get($$10), $$9++);
        }
        if (!$$3.m_41619_()) {
            int $$13 = this.f_92978_ - 16 - 3;
            if ($$4 == HumanoidArm.LEFT) {
                this.m_168677_($$5 - 91 - 26, $$13, p_93010_, $$2, $$3, $$9++);
            } else {
                this.m_168677_($$5 + 91 + 10, $$13, p_93010_, $$2, $$3, $$9++);
            }
        }
        if (this.f_92986_.f_91066_.m_232120_().m_231551_() == AttackIndicatorStatus.HOTBAR && ($$14 = this.f_92986_.f_91074_.m_36403_(0.0f)) < 1.0f) {
            int $$15 = this.f_92978_ - 20;
            int $$16 = $$5 + 91 + 6;
            if ($$4 == HumanoidArm.RIGHT) {
                $$16 = $$5 - 91 - 22;
            }
            RenderSystem.m_157456_(0, GuiComponent.f_93098_);
            int $$17 = (int)($$14 * 19.0f);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            this.m_93228_(p_93011_, $$16, $$15, 0, 94, 18, 18);
            this.m_93228_(p_93011_, $$16, $$15 + 18 - $$17, 18, 112 - $$17, 18, $$17);
        }
        RenderSystem.m_69461_();
    }

    public void m_93033_(PoseStack p_93034_, int p_93035_) {
        this.f_92986_.m_91307_().m_6180_("jumpBar");
        RenderSystem.m_157456_(0, GuiComponent.f_93098_);
        float $$2 = this.f_92986_.f_91074_.m_108634_();
        int $$3 = 182;
        int $$4 = (int)($$2 * 183.0f);
        int $$5 = this.f_92978_ - 32 + 3;
        this.m_93228_(p_93034_, p_93035_, $$5, 0, 84, 182, 5);
        if ($$4 > 0) {
            this.m_93228_(p_93034_, p_93035_, $$5, 0, 89, $$4, 5);
        }
        this.f_92986_.m_91307_().m_7238_();
    }

    public void m_93071_(PoseStack p_93072_, int p_93073_) {
        this.f_92986_.m_91307_().m_6180_("expBar");
        RenderSystem.m_157456_(0, GuiComponent.f_93098_);
        int $$2 = this.f_92986_.f_91074_.m_36323_();
        if ($$2 > 0) {
            int $$3 = 182;
            int $$4 = (int)(this.f_92986_.f_91074_.f_36080_ * 183.0f);
            int $$5 = this.f_92978_ - 32 + 3;
            this.m_93228_(p_93072_, p_93073_, $$5, 0, 64, 182, 5);
            if ($$4 > 0) {
                this.m_93228_(p_93072_, p_93073_, $$5, 0, 69, $$4, 5);
            }
        }
        this.f_92986_.m_91307_().m_7238_();
        if (this.f_92986_.f_91074_.f_36078_ > 0) {
            this.f_92986_.m_91307_().m_6180_("expLevel");
            String $$6 = "" + this.f_92986_.f_91074_.f_36078_;
            int $$7 = (this.f_92977_ - this.m_93082_().m_92895_($$6)) / 2;
            int $$8 = this.f_92978_ - 31 - 4;
            this.m_93082_().m_92883_(p_93072_, $$6, $$7 + 1, $$8, 0);
            this.m_93082_().m_92883_(p_93072_, $$6, $$7 - 1, $$8, 0);
            this.m_93082_().m_92883_(p_93072_, $$6, $$7, $$8 + 1, 0);
            this.m_93082_().m_92883_(p_93072_, $$6, $$7, $$8 - 1, 0);
            this.m_93082_().m_92883_(p_93072_, $$6, $$7, $$8, 8453920);
            this.f_92986_.m_91307_().m_7238_();
        }
    }

    public void m_93069_(PoseStack p_93070_) {
        this.f_92986_.m_91307_().m_6180_("selectedItemName");
        if (this.f_92993_ > 0 && !this.f_92994_.m_41619_()) {
            int $$5;
            MutableComponent $$1 = Component.m_237119_().m_7220_(this.f_92994_.m_41786_()).m_130940_(this.f_92994_.m_41791_().f_43022_);
            if (this.f_92994_.m_41788_()) {
                $$1.m_130940_(ChatFormatting.ITALIC);
            }
            int $$2 = this.m_93082_().m_92852_($$1);
            int $$3 = (this.f_92977_ - $$2) / 2;
            int $$4 = this.f_92978_ - 59;
            if (!this.f_92986_.f_91072_.m_105205_()) {
                $$4 += 14;
            }
            if (($$5 = (int)((float)this.f_92993_ * 256.0f / 10.0f)) > 255) {
                $$5 = 255;
            }
            if ($$5 > 0) {
                RenderSystem.m_69478_();
                RenderSystem.m_69453_();
                Gui.m_93172_(p_93070_, $$3 - 2, $$4 - 2, $$3 + $$2 + 2, $$4 + this.m_93082_().f_92710_ + 2, this.f_92986_.f_91066_.m_92143_(0));
                this.m_93082_().m_92763_(p_93070_, $$1, $$3, $$4, 0xFFFFFF + ($$5 << 24));
                RenderSystem.m_69461_();
            }
        }
        this.f_92986_.m_91307_().m_7238_();
    }

    public void m_93077_(PoseStack p_93078_) {
        MutableComponent $$2;
        this.f_92986_.m_91307_().m_6180_("demo");
        if (this.f_92986_.f_91073_.m_46467_() >= 120500L) {
            Component $$1 = f_92984_;
        } else {
            $$2 = Component.m_237110_("demo.remainingTime", StringUtil.m_14404_((int)(120500L - this.f_92986_.f_91073_.m_46467_())));
        }
        int $$3 = this.m_93082_().m_92852_($$2);
        this.m_93082_().m_92763_(p_93078_, $$2, this.f_92977_ - $$3 - 10, 5.0f, 0xFFFFFF);
        this.f_92986_.m_91307_().m_7238_();
    }

    private void m_93036_(PoseStack p_93037_, Objective p_93038_) {
        int $$7;
        Scoreboard $$2 = p_93038_.m_83313_();
        List<Object> $$3 = $$2.m_83498_(p_93038_);
        List $$4 = $$3.stream().filter(p_93027_ -> p_93027_.m_83405_() != null && !p_93027_.m_83405_().startsWith("#")).collect(Collectors.toList());
        $$3 = $$4.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip($$4, (int)($$3.size() - 15))) : $$4;
        ArrayList $$5 = Lists.newArrayListWithCapacity((int)$$3.size());
        Component $$6 = p_93038_.m_83322_();
        int $$8 = $$7 = this.m_93082_().m_92852_($$6);
        int $$9 = this.m_93082_().m_92895_(f_168671_);
        for (Score $$10 : $$3) {
            PlayerTeam $$11 = $$2.m_83500_($$10.m_83405_());
            MutableComponent $$12 = PlayerTeam.m_83348_($$11, Component.m_237113_($$10.m_83405_()));
            $$5.add(Pair.of((Object)$$10, (Object)$$12));
            $$8 = Math.max($$8, this.m_93082_().m_92852_($$12) + $$9 + this.m_93082_().m_92895_(Integer.toString($$10.m_83400_())));
        }
        int $$13 = $$3.size() * this.m_93082_().f_92710_;
        int $$14 = this.f_92978_ / 2 + $$13 / 3;
        int $$15 = 3;
        int $$16 = this.f_92977_ - $$8 - 3;
        int $$17 = 0;
        int $$18 = this.f_92986_.f_91066_.m_92170_(0.3f);
        int $$19 = this.f_92986_.f_91066_.m_92170_(0.4f);
        for (Pair $$20 : $$5) {
            Score $$21 = (Score)$$20.getFirst();
            Component $$22 = (Component)$$20.getSecond();
            String $$23 = "" + ChatFormatting.RED + $$21.m_83400_();
            int $$24 = $$16;
            int $$25 = $$14 - ++$$17 * this.m_93082_().f_92710_;
            int $$26 = this.f_92977_ - 3 + 2;
            Gui.m_93172_(p_93037_, $$24 - 2, $$25, $$26, $$25 + this.m_93082_().f_92710_, $$18);
            this.m_93082_().m_92889_(p_93037_, $$22, $$24, $$25, -1);
            this.m_93082_().m_92883_(p_93037_, $$23, $$26 - this.m_93082_().m_92895_($$23), $$25, -1);
            if ($$17 != $$3.size()) continue;
            Gui.m_93172_(p_93037_, $$24 - 2, $$25 - this.m_93082_().f_92710_ - 1, $$26, $$25 - 1, $$19);
            Gui.m_93172_(p_93037_, $$24 - 2, $$25 - 1, $$26, $$25, $$18);
            this.m_93082_().m_92889_(p_93037_, $$6, $$24 + $$8 / 2 - $$7 / 2, $$25 - this.m_93082_().f_92710_, -1);
        }
    }

    private Player m_93092_() {
        if (!(this.f_92986_.m_91288_() instanceof Player)) {
            return null;
        }
        return (Player)this.f_92986_.m_91288_();
    }

    private LivingEntity m_93093_() {
        Player $$0 = this.m_93092_();
        if ($$0 != null) {
            Entity $$1 = $$0.m_20202_();
            if ($$1 == null) {
                return null;
            }
            if ($$1 instanceof LivingEntity) {
                return (LivingEntity)$$1;
            }
        }
        return null;
    }

    private int m_93022_(LivingEntity p_93023_) {
        if (p_93023_ == null || !p_93023_.m_20152_()) {
            return 0;
        }
        float $$1 = p_93023_.m_21233_();
        int $$2 = (int)($$1 + 0.5f) / 2;
        if ($$2 > 30) {
            $$2 = 30;
        }
        return $$2;
    }

    private int m_93012_(int p_93013_) {
        return (int)Math.ceil((double)p_93013_ / 10.0);
    }

    private void m_93083_(PoseStack p_93084_) {
        Player $$1 = this.m_93092_();
        if ($$1 == null) {
            return;
        }
        int $$2 = Mth.m_14167_($$1.m_21223_());
        boolean $$3 = this.f_92976_ > (long)this.f_92989_ && (this.f_92976_ - (long)this.f_92989_) / 3L % 2L == 1L;
        long $$4 = Util.m_137550_();
        if ($$2 < this.f_92973_ && $$1.f_19802_ > 0) {
            this.f_92975_ = $$4;
            this.f_92976_ = this.f_92989_ + 20;
        } else if ($$2 > this.f_92973_ && $$1.f_19802_ > 0) {
            this.f_92975_ = $$4;
            this.f_92976_ = this.f_92989_ + 10;
        }
        if ($$4 - this.f_92975_ > 1000L) {
            this.f_92973_ = $$2;
            this.f_92974_ = $$2;
            this.f_92975_ = $$4;
        }
        this.f_92973_ = $$2;
        int $$5 = this.f_92974_;
        this.f_92985_.m_188584_(this.f_92989_ * 312871);
        FoodData $$6 = $$1.m_36324_();
        int $$7 = $$6.m_38702_();
        int $$8 = this.f_92977_ / 2 - 91;
        int $$9 = this.f_92977_ / 2 + 91;
        int $$10 = this.f_92978_ - 39;
        float $$11 = Math.max((float)$$1.m_21133_(Attributes.f_22276_), (float)Math.max($$5, $$2));
        int $$12 = Mth.m_14167_($$1.m_6103_());
        int $$13 = Mth.m_14167_(($$11 + (float)$$12) / 2.0f / 10.0f);
        int $$14 = Math.max(10 - ($$13 - 2), 3);
        int $$15 = $$10 - ($$13 - 1) * $$14 - 10;
        int $$16 = $$10 - 10;
        int $$17 = $$1.m_21230_();
        int $$18 = -1;
        if ($$1.m_21023_(MobEffects.f_19605_)) {
            $$18 = this.f_92989_ % Mth.m_14167_($$11 + 5.0f);
        }
        this.f_92986_.m_91307_().m_6180_("armor");
        for (int $$19 = 0; $$19 < 10; ++$$19) {
            if ($$17 <= 0) continue;
            int $$20 = $$8 + $$19 * 8;
            if ($$19 * 2 + 1 < $$17) {
                this.m_93228_(p_93084_, $$20, $$15, 34, 9, 9, 9);
            }
            if ($$19 * 2 + 1 == $$17) {
                this.m_93228_(p_93084_, $$20, $$15, 25, 9, 9, 9);
            }
            if ($$19 * 2 + 1 <= $$17) continue;
            this.m_93228_(p_93084_, $$20, $$15, 16, 9, 9, 9);
        }
        this.f_92986_.m_91307_().m_6182_("health");
        this.m_168688_(p_93084_, $$1, $$8, $$10, $$14, $$18, $$11, $$2, $$5, $$12, $$3);
        LivingEntity $$21 = this.m_93093_();
        int $$22 = this.m_93022_($$21);
        if ($$22 == 0) {
            this.f_92986_.m_91307_().m_6182_("food");
            for (int $$23 = 0; $$23 < 10; ++$$23) {
                int $$24 = $$10;
                int $$25 = 16;
                int $$26 = 0;
                if ($$1.m_21023_(MobEffects.f_19612_)) {
                    $$25 += 36;
                    $$26 = 13;
                }
                if ($$1.m_36324_().m_38722_() <= 0.0f && this.f_92989_ % ($$7 * 3 + 1) == 0) {
                    $$24 += this.f_92985_.m_188503_(3) - 1;
                }
                int $$27 = $$9 - $$23 * 8 - 9;
                this.m_93228_(p_93084_, $$27, $$24, 16 + $$26 * 9, 27, 9, 9);
                if ($$23 * 2 + 1 < $$7) {
                    this.m_93228_(p_93084_, $$27, $$24, $$25 + 36, 27, 9, 9);
                }
                if ($$23 * 2 + 1 != $$7) continue;
                this.m_93228_(p_93084_, $$27, $$24, $$25 + 45, 27, 9, 9);
            }
            $$16 -= 10;
        }
        this.f_92986_.m_91307_().m_6182_("air");
        int $$28 = $$1.m_6062_();
        int $$29 = Math.min($$1.m_20146_(), $$28);
        if ($$1.m_204029_(FluidTags.f_13131_) || $$29 < $$28) {
            int $$30 = this.m_93012_($$22) - 1;
            $$16 -= $$30 * 10;
            int $$31 = Mth.m_14165_((double)($$29 - 2) * 10.0 / (double)$$28);
            int $$32 = Mth.m_14165_((double)$$29 * 10.0 / (double)$$28) - $$31;
            for (int $$33 = 0; $$33 < $$31 + $$32; ++$$33) {
                if ($$33 < $$31) {
                    this.m_93228_(p_93084_, $$9 - $$33 * 8 - 9, $$16, 16, 18, 9, 9);
                    continue;
                }
                this.m_93228_(p_93084_, $$9 - $$33 * 8 - 9, $$16, 25, 18, 9, 9);
            }
        }
        this.f_92986_.m_91307_().m_7238_();
    }

    private void m_168688_(PoseStack p_168689_, Player p_168690_, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_) {
        HeartType $$11 = HeartType.m_168732_(p_168690_);
        int $$12 = 9 * (p_168690_.f_19853_.m_6106_().m_5466_() ? 5 : 0);
        int $$13 = Mth.m_14165_((double)p_168695_ / 2.0);
        int $$14 = Mth.m_14165_((double)p_168698_ / 2.0);
        int $$15 = $$13 * 2;
        for (int $$16 = $$13 + $$14 - 1; $$16 >= 0; --$$16) {
            int $$23;
            boolean $$22;
            int $$17 = $$16 / 10;
            int $$18 = $$16 % 10;
            int $$19 = p_168691_ + $$18 * 8;
            int $$20 = p_168692_ - $$17 * p_168693_;
            if (p_168696_ + p_168698_ <= 4) {
                $$20 += this.f_92985_.m_188503_(2);
            }
            if ($$16 < $$13 && $$16 == p_168694_) {
                $$20 -= 2;
            }
            this.m_168700_(p_168689_, HeartType.CONTAINER, $$19, $$20, $$12, p_168699_, false);
            int $$21 = $$16 * 2;
            boolean bl = $$22 = $$16 >= $$13;
            if ($$22 && ($$23 = $$21 - $$15) < p_168698_) {
                boolean $$24 = $$23 + 1 == p_168698_;
                this.m_168700_(p_168689_, $$11 == HeartType.WITHERED ? $$11 : HeartType.ABSORBING, $$19, $$20, $$12, false, $$24);
            }
            if (p_168699_ && $$21 < p_168697_) {
                boolean $$25 = $$21 + 1 == p_168697_;
                this.m_168700_(p_168689_, $$11, $$19, $$20, $$12, true, $$25);
            }
            if ($$21 >= p_168696_) continue;
            boolean $$26 = $$21 + 1 == p_168696_;
            this.m_168700_(p_168689_, $$11, $$19, $$20, $$12, false, $$26);
        }
    }

    private void m_168700_(PoseStack p_168701_, HeartType p_168702_, int p_168703_, int p_168704_, int p_168705_, boolean p_168706_, boolean p_168707_) {
        this.m_93228_(p_168701_, p_168703_, p_168704_, p_168702_.m_168734_(p_168707_, p_168706_), p_168705_, 9, 9);
    }

    private void m_93086_(PoseStack p_93087_) {
        LivingEntity $$1 = this.m_93093_();
        if ($$1 == null) {
            return;
        }
        int $$2 = this.m_93022_($$1);
        if ($$2 == 0) {
            return;
        }
        int $$3 = (int)Math.ceil($$1.m_21223_());
        this.f_92986_.m_91307_().m_6182_("mountHealth");
        int $$4 = this.f_92978_ - 39;
        int $$5 = this.f_92977_ / 2 + 91;
        int $$6 = $$4;
        int $$7 = 0;
        boolean $$8 = false;
        while ($$2 > 0) {
            int $$9 = Math.min($$2, 10);
            $$2 -= $$9;
            for (int $$10 = 0; $$10 < $$9; ++$$10) {
                int $$11 = 52;
                int $$12 = 0;
                int $$13 = $$5 - $$10 * 8 - 9;
                this.m_93228_(p_93087_, $$13, $$6, 52 + $$12 * 9, 9, 9, 9);
                if ($$10 * 2 + 1 + $$7 < $$3) {
                    this.m_93228_(p_93087_, $$13, $$6, 88, 9, 9, 9);
                }
                if ($$10 * 2 + 1 + $$7 != $$3) continue;
                this.m_93228_(p_93087_, $$13, $$6, 97, 9, 9, 9);
            }
            $$6 -= 10;
            $$7 += 20;
        }
    }

    private void m_168708_(ResourceLocation p_168709_, float p_168710_) {
        RenderSystem.m_69465_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69453_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, p_168710_);
        RenderSystem.m_157456_(0, p_168709_);
        Tesselator $$2 = Tesselator.m_85913_();
        BufferBuilder $$3 = $$2.m_85915_();
        $$3.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$3.m_5483_(0.0, this.f_92978_, -90.0).m_7421_(0.0f, 1.0f).m_5752_();
        $$3.m_5483_(this.f_92977_, this.f_92978_, -90.0).m_7421_(1.0f, 1.0f).m_5752_();
        $$3.m_5483_(this.f_92977_, 0.0, -90.0).m_7421_(1.0f, 0.0f).m_5752_();
        $$3.m_5483_(0.0, 0.0, -90.0).m_7421_(0.0f, 0.0f).m_5752_();
        $$2.m_85914_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69482_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void m_168675_(float p_168676_) {
        float $$3;
        RenderSystem.m_69465_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69453_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_168665_);
        Tesselator $$1 = Tesselator.m_85913_();
        BufferBuilder $$2 = $$1.m_85915_();
        float $$4 = $$3 = (float)Math.min(this.f_92977_, this.f_92978_);
        float $$5 = Math.min((float)this.f_92977_ / $$3, (float)this.f_92978_ / $$4) * p_168676_;
        float $$6 = $$3 * $$5;
        float $$7 = $$4 * $$5;
        float $$8 = ((float)this.f_92977_ - $$6) / 2.0f;
        float $$9 = ((float)this.f_92978_ - $$7) / 2.0f;
        float $$10 = $$8 + $$6;
        float $$11 = $$9 + $$7;
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$2.m_5483_($$8, $$11, -90.0).m_7421_(0.0f, 1.0f).m_5752_();
        $$2.m_5483_($$10, $$11, -90.0).m_7421_(1.0f, 1.0f).m_5752_();
        $$2.m_5483_($$10, $$9, -90.0).m_7421_(1.0f, 0.0f).m_5752_();
        $$2.m_5483_($$8, $$9, -90.0).m_7421_(0.0f, 0.0f).m_5752_();
        $$1.m_85914_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderSystem.m_69472_();
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        $$2.m_5483_(0.0, this.f_92978_, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, this.f_92978_, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(0.0, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(0.0, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, 0.0, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(0.0, 0.0, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(0.0, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_($$8, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_($$8, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(0.0, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_($$10, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, $$11, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_(this.f_92977_, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$2.m_5483_($$10, $$9, -90.0).m_6122_(0, 0, 0, 255).m_5752_();
        $$1.m_85914_();
        RenderSystem.m_69493_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69482_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void m_93020_(Entity p_93021_) {
        if (p_93021_ == null) {
            return;
        }
        BlockPos $$1 = new BlockPos(p_93021_.m_20185_(), p_93021_.m_20188_(), p_93021_.m_20189_());
        float $$2 = LightTexture.m_234316_(p_93021_.f_19853_.m_6042_(), p_93021_.f_19853_.m_46803_($$1));
        float $$3 = Mth.m_14036_(1.0f - $$2, 0.0f, 1.0f);
        this.f_92980_ += ($$3 - this.f_92980_) * 0.01f;
    }

    private void m_93067_(Entity p_93068_) {
        WorldBorder $$1 = this.f_92986_.f_91073_.m_6857_();
        float $$2 = (float)$$1.m_61925_(p_93068_);
        double $$3 = Math.min($$1.m_61966_() * (double)$$1.m_61967_() * 1000.0, Math.abs($$1.m_61961_() - $$1.m_61959_()));
        double $$4 = Math.max((double)$$1.m_61968_(), $$3);
        $$2 = (double)$$2 < $$4 ? 1.0f - (float)((double)$$2 / $$4) : 0.0f;
        RenderSystem.m_69465_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69416_(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        if ($$2 > 0.0f) {
            $$2 = Mth.m_14036_($$2, 0.0f, 1.0f);
            RenderSystem.m_157429_(0.0f, $$2, $$2, 1.0f);
        } else {
            float $$5 = this.f_92980_;
            $$5 = Mth.m_14036_($$5, 0.0f, 1.0f);
            RenderSystem.m_157429_($$5, $$5, $$5, 1.0f);
        }
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_92981_);
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$7.m_5483_(0.0, this.f_92978_, -90.0).m_7421_(0.0f, 1.0f).m_5752_();
        $$7.m_5483_(this.f_92977_, this.f_92978_, -90.0).m_7421_(1.0f, 1.0f).m_5752_();
        $$7.m_5483_(this.f_92977_, 0.0, -90.0).m_7421_(1.0f, 0.0f).m_5752_();
        $$7.m_5483_(0.0, 0.0, -90.0).m_7421_(0.0f, 0.0f).m_5752_();
        $$6.m_85914_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69482_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69453_();
    }

    private void m_93007_(float p_93008_) {
        if (p_93008_ < 1.0f) {
            p_93008_ *= p_93008_;
            p_93008_ *= p_93008_;
            p_93008_ = p_93008_ * 0.8f + 0.2f;
        }
        RenderSystem.m_69465_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69453_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, p_93008_);
        RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        TextureAtlasSprite $$1 = this.f_92986_.m_91289_().m_110907_().m_110882_(Blocks.f_50142_.m_49966_());
        float $$2 = $$1.m_118409_();
        float $$3 = $$1.m_118411_();
        float $$4 = $$1.m_118410_();
        float $$5 = $$1.m_118412_();
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$7.m_5483_(0.0, this.f_92978_, -90.0).m_7421_($$2, $$5).m_5752_();
        $$7.m_5483_(this.f_92977_, this.f_92978_, -90.0).m_7421_($$4, $$5).m_5752_();
        $$7.m_5483_(this.f_92977_, 0.0, -90.0).m_7421_($$4, $$3).m_5752_();
        $$7.m_5483_(0.0, 0.0, -90.0).m_7421_($$2, $$3).m_5752_();
        $$6.m_85914_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69482_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void m_168677_(int p_168678_, int p_168679_, float p_168680_, Player p_168681_, ItemStack p_168682_, int p_168683_) {
        if (p_168682_.m_41619_()) {
            return;
        }
        PoseStack $$6 = RenderSystem.m_157191_();
        float $$7 = (float)p_168682_.m_41612_() - p_168680_;
        if ($$7 > 0.0f) {
            float $$8 = 1.0f + $$7 / 5.0f;
            $$6.m_85836_();
            $$6.m_85837_(p_168678_ + 8, p_168679_ + 12, 0.0);
            $$6.m_85841_(1.0f / $$8, ($$8 + 1.0f) / 2.0f, 1.0f);
            $$6.m_85837_(-(p_168678_ + 8), -(p_168679_ + 12), 0.0);
            RenderSystem.m_157182_();
        }
        this.f_92987_.m_174229_(p_168681_, p_168682_, p_168678_, p_168679_, p_168683_);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        if ($$7 > 0.0f) {
            $$6.m_85849_();
            RenderSystem.m_157182_();
        }
        this.f_92987_.m_115169_(this.f_92986_.f_91062_, p_168682_, p_168678_, p_168679_);
    }

    public void m_193832_(boolean p_193833_) {
        this.m_193836_();
        if (!p_193833_) {
            this.m_93066_();
        }
    }

    private void m_93066_() {
        if (this.f_92991_ > 0) {
            --this.f_92991_;
        }
        if (this.f_93000_ > 0) {
            --this.f_93000_;
            if (this.f_93000_ <= 0) {
                this.f_93001_ = null;
                this.f_93002_ = null;
            }
        }
        ++this.f_92989_;
        Entity $$0 = this.f_92986_.m_91288_();
        if ($$0 != null) {
            this.m_93020_($$0);
        }
        if (this.f_92986_.f_91074_ != null) {
            ItemStack $$1 = this.f_92986_.f_91074_.m_150109_().m_36056_();
            if ($$1.m_41619_()) {
                this.f_92993_ = 0;
            } else if (this.f_92994_.m_41619_() || !$$1.m_150930_(this.f_92994_.m_41720_()) || !$$1.m_41786_().equals(this.f_92994_.m_41786_())) {
                this.f_92993_ = 40;
            } else if (this.f_92993_ > 0) {
                --this.f_92993_;
            }
            this.f_92994_ = $$1;
        }
    }

    private void m_193836_() {
        IntegratedServer $$0 = this.f_92986_.m_91092_();
        boolean $$1 = $$0 != null && $$0.m_195518_();
        this.f_193829_ = this.f_193828_;
        this.f_193828_ = Mth.m_14179_(0.2f, this.f_193828_, $$1 ? 1.0f : 0.0f);
    }

    public void m_93055_(Component p_93056_) {
        MutableComponent $$1 = Component.m_237110_("record.nowPlaying", p_93056_);
        this.m_93063_($$1, true);
        this.f_92986_.m_240477_().m_168785_($$1);
    }

    public void m_93063_(Component p_93064_, boolean p_93065_) {
        this.m_238397_(false);
        this.f_92990_ = p_93064_;
        this.f_92991_ = 60;
        this.f_92992_ = p_93065_;
    }

    public void m_238397_(boolean p_238398_) {
        this.f_238167_ = p_238398_;
    }

    public boolean m_238351_() {
        return this.f_238167_ && this.f_92991_ > 0;
    }

    public void m_168684_(int p_168685_, int p_168686_, int p_168687_) {
        if (p_168685_ >= 0) {
            this.f_92970_ = p_168685_;
        }
        if (p_168686_ >= 0) {
            this.f_92971_ = p_168686_;
        }
        if (p_168687_ >= 0) {
            this.f_92972_ = p_168687_;
        }
        if (this.f_93000_ > 0) {
            this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
        }
    }

    public void m_168711_(Component p_168712_) {
        this.f_93002_ = p_168712_;
    }

    public void m_168714_(Component p_168715_) {
        this.f_93001_ = p_168715_;
        this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
    }

    public void m_168713_() {
        this.f_93001_ = null;
        this.f_93002_ = null;
        this.f_93000_ = 0;
    }

    public ChatComponent m_93076_() {
        return this.f_92988_;
    }

    public int m_93079_() {
        return this.f_92989_;
    }

    public Font m_93082_() {
        return this.f_92986_.f_91062_;
    }

    public SpectatorGui m_93085_() {
        return this.f_92997_;
    }

    public PlayerTabOverlay m_93088_() {
        return this.f_92998_;
    }

    public void m_93089_() {
        this.f_92998_.m_94529_();
        this.f_92999_.m_93703_();
        this.f_92986_.m_91300_().m_94919_();
        this.f_92986_.f_91066_.f_92063_ = false;
        this.f_92988_.m_93795_(true);
    }

    public BossHealthOverlay m_93090_() {
        return this.f_92999_;
    }

    public void m_93091_() {
        this.f_92995_.m_94040_();
    }

    private void m_193834_(PoseStack p_193835_) {
        int $$1;
        if (this.f_92986_.f_91066_.m_231834_().m_231551_().booleanValue() && (this.f_193828_ > 0.0f || this.f_193829_ > 0.0f) && ($$1 = Mth.m_14143_(255.0f * Mth.m_14036_(Mth.m_14179_(this.f_92986_.m_91296_(), this.f_193829_, this.f_193828_), 0.0f, 1.0f))) > 8) {
            Font $$2 = this.m_93082_();
            int $$3 = $$2.m_92852_(f_193830_);
            int $$4 = 0xFFFFFF | $$1 << 24 & 0xFF000000;
            $$2.m_92763_(p_193835_, f_193830_, this.f_92977_ - $$3 - 10, this.f_92978_ - 15, $$4);
        }
    }

    static final class HeartType
    extends Enum<HeartType> {
        public static final /* enum */ HeartType CONTAINER = new HeartType(0, false);
        public static final /* enum */ HeartType NORMAL = new HeartType(2, true);
        public static final /* enum */ HeartType POISIONED = new HeartType(4, true);
        public static final /* enum */ HeartType WITHERED = new HeartType(6, true);
        public static final /* enum */ HeartType ABSORBING = new HeartType(8, false);
        public static final /* enum */ HeartType FROZEN = new HeartType(9, false);
        private final int f_168722_;
        private final boolean f_168723_;
        private static final /* synthetic */ HeartType[] $VALUES;

        public static HeartType[] values() {
            return (HeartType[])$VALUES.clone();
        }

        public static HeartType valueOf(String p_168738_) {
            return Enum.valueOf(HeartType.class, p_168738_);
        }

        private HeartType(int p_168729_, boolean p_168730_) {
            this.f_168722_ = p_168729_;
            this.f_168723_ = p_168730_;
        }

        public int m_168734_(boolean p_168735_, boolean p_168736_) {
            int $$5;
            if (this == CONTAINER) {
                boolean $$2 = p_168736_;
            } else {
                int $$3 = p_168735_ ? 1 : 0;
                int $$4 = this.f_168723_ && p_168736_ ? 2 : 0;
                $$5 = $$3 + $$4;
            }
            return 16 + (this.f_168722_ * 2 + $$5) * 9;
        }

        static HeartType m_168732_(Player p_168733_) {
            HeartType $$4;
            if (p_168733_.m_21023_(MobEffects.f_19614_)) {
                HeartType $$1 = POISIONED;
            } else if (p_168733_.m_21023_(MobEffects.f_19615_)) {
                HeartType $$2 = WITHERED;
            } else if (p_168733_.m_146890_()) {
                HeartType $$3 = FROZEN;
            } else {
                $$4 = NORMAL;
            }
            return $$4;
        }

        private static /* synthetic */ HeartType[] m_168731_() {
            return new HeartType[]{CONTAINER, NORMAL, POISIONED, WITHERED, ABSORBING, FROZEN};
        }

        static {
            $VALUES = HeartType.m_168731_();
        }
    }
}

