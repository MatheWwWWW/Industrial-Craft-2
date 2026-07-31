/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;

public class RealmsSlotOptionsScreen
extends RealmsScreen {
    private static final int f_167511_ = 2;
    public static final List<Difficulty> f_89870_ = ImmutableList.of((Object)((Object)Difficulty.PEACEFUL), (Object)((Object)Difficulty.EASY), (Object)((Object)Difficulty.NORMAL), (Object)((Object)Difficulty.HARD));
    private static final int f_167512_ = 0;
    public static final List<GameType> f_89871_ = ImmutableList.of((Object)((Object)GameType.SURVIVAL), (Object)((Object)GameType.CREATIVE), (Object)((Object)GameType.ADVENTURE));
    private static final Component f_89876_ = Component.m_237115_("mco.configure.world.edit.slot.name");
    static final Component f_167513_ = Component.m_237115_("mco.configure.world.spawnProtection");
    private static final Component f_231308_ = Component.m_237115_("mco.configure.world.spawn_toggle.title").m_130944_(ChatFormatting.RED, ChatFormatting.BOLD);
    private EditBox f_89877_;
    protected final RealmsConfigureWorldScreen f_89872_;
    private int f_89878_;
    private int f_89879_;
    private final RealmsWorldOptions f_89881_;
    private final RealmsServer.WorldType f_89882_;
    private Difficulty f_89852_;
    private GameType f_89853_;
    private final String f_231309_;
    private String f_231310_;
    private boolean f_89854_;
    private boolean f_89855_;
    private boolean f_89856_;
    private boolean f_89857_;
    int f_89858_;
    private boolean f_89859_;
    private boolean f_89860_;
    SettingsSlider f_89865_;

    public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen p_89886_, RealmsWorldOptions p_89887_, RealmsServer.WorldType p_89888_, int p_89889_) {
        super(Component.m_237115_("mco.configure.world.buttons.options"));
        this.f_89872_ = p_89886_;
        this.f_89881_ = p_89887_;
        this.f_89882_ = p_89888_;
        this.f_89852_ = RealmsSlotOptionsScreen.m_167524_(f_89870_, p_89887_.f_87605_, 2);
        this.f_89853_ = RealmsSlotOptionsScreen.m_167524_(f_89871_, p_89887_.f_87606_, 0);
        this.f_231309_ = p_89887_.m_87633_(p_89889_);
        this.m_231313_(p_89887_.m_87626_(p_89889_));
        if (p_89888_ == RealmsServer.WorldType.NORMAL) {
            this.f_89854_ = p_89887_.f_87598_;
            this.f_89858_ = p_89887_.f_87602_;
            this.f_89860_ = p_89887_.f_87604_;
            this.f_89856_ = p_89887_.f_87599_;
            this.f_89857_ = p_89887_.f_87600_;
            this.f_89855_ = p_89887_.f_87601_;
            this.f_89859_ = p_89887_.f_87603_;
        } else {
            this.f_89854_ = true;
            this.f_89858_ = 0;
            this.f_89860_ = false;
            this.f_89856_ = true;
            this.f_89857_ = true;
            this.f_89855_ = true;
            this.f_89859_ = true;
        }
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public void m_86600_() {
        this.f_89877_.m_94120_();
    }

    @Override
    public boolean m_7933_(int p_89891_, int p_89892_, int p_89893_) {
        if (p_89891_ == 256) {
            this.f_96541_.m_91152_(this.f_89872_);
            return true;
        }
        return super.m_7933_(p_89891_, p_89892_, p_89893_);
    }

    private static <T> T m_167524_(List<T> p_167525_, int p_167526_, int p_167527_) {
        try {
            return p_167525_.get(p_167526_);
        }
        catch (IndexOutOfBoundsException $$3) {
            return p_167525_.get(p_167527_);
        }
    }

    private static <T> int m_167528_(List<T> p_167529_, T p_167530_, int p_167531_) {
        int $$3 = p_167529_.indexOf(p_167530_);
        return $$3 == -1 ? p_167531_ : $$3;
    }

    @Override
    public void m_7856_() {
        this.f_89879_ = 170;
        this.f_89878_ = this.f_96543_ / 2 - this.f_89879_;
        int $$0 = this.f_96543_ / 2 + 10;
        if (this.f_89882_ != RealmsServer.WorldType.NORMAL) {
            MutableComponent $$3;
            if (this.f_89882_ == RealmsServer.WorldType.ADVENTUREMAP) {
                MutableComponent $$1 = Component.m_237115_("mco.configure.world.edit.subscreen.adventuremap");
            } else if (this.f_89882_ == RealmsServer.WorldType.INSPIRATION) {
                MutableComponent $$2 = Component.m_237115_("mco.configure.world.edit.subscreen.inspiration");
            } else {
                $$3 = Component.m_237115_("mco.configure.world.edit.subscreen.experience");
            }
            this.m_175073_(new RealmsLabel($$3, this.f_96543_ / 2, 26, 0xFF0000));
        }
        this.f_89877_ = new EditBox(this.f_96541_.f_91062_, this.f_89878_ + 2, RealmsSlotOptionsScreen.m_120774_(1), this.f_89879_ - 4, 20, null, Component.m_237115_("mco.configure.world.edit.slot.name"));
        this.f_89877_.m_94199_(10);
        this.f_89877_.m_94144_(this.f_231310_);
        this.f_89877_.m_94151_(this::m_231313_);
        this.m_94725_(this.f_89877_);
        CycleButton<Boolean> $$4 = this.m_142416_(CycleButton.m_168916_(this.f_89854_).m_168936_($$0, RealmsSlotOptionsScreen.m_120774_(1), this.f_89879_, 20, Component.m_237115_("mco.configure.world.pvp"), (p_167546_, p_167547_) -> {
            this.f_89854_ = p_167547_;
        }));
        this.m_142416_(CycleButton.m_168894_(GameType::m_151500_).m_232502_(f_89871_).m_168948_(this.f_89853_).m_168936_(this.f_89878_, RealmsSlotOptionsScreen.m_120774_(3), this.f_89879_, 20, Component.m_237115_("selectWorld.gameMode"), (p_167515_, p_167516_) -> {
            this.f_89853_ = p_167516_;
        }));
        MutableComponent $$5 = Component.m_237115_("mco.configure.world.spawn_toggle.message");
        CycleButton<Boolean> $$6 = this.m_142416_(CycleButton.m_168916_(this.f_89856_).m_168936_($$0, RealmsSlotOptionsScreen.m_120774_(3), this.f_89879_, 20, Component.m_237115_("mco.configure.world.spawnAnimals"), this.m_231323_($$5, p_231329_ -> {
            this.f_89856_ = p_231329_;
        })));
        CycleButton<Boolean> $$7 = CycleButton.m_168916_(this.f_89852_ != Difficulty.PEACEFUL && this.f_89857_).m_168936_($$0, RealmsSlotOptionsScreen.m_120774_(5), this.f_89879_, 20, Component.m_237115_("mco.configure.world.spawnMonsters"), this.m_231323_($$5, p_231327_ -> {
            this.f_89857_ = p_231327_;
        }));
        this.m_142416_(CycleButton.m_168894_(Difficulty::m_19033_).m_232502_(f_89870_).m_168948_(this.f_89852_).m_168936_(this.f_89878_, RealmsSlotOptionsScreen.m_120774_(5), this.f_89879_, 20, Component.m_237115_("options.difficulty"), (p_167519_, p_167520_) -> {
            this.f_89852_ = p_167520_;
            if (this.f_89882_ == RealmsServer.WorldType.NORMAL) {
                boolean $$3;
                p_167518_.f_93623_ = $$3 = this.f_89852_ != Difficulty.PEACEFUL;
                $$7.m_168892_($$3 && this.f_89857_);
            }
        }));
        this.m_142416_($$7);
        this.f_89865_ = this.m_142416_(new SettingsSlider(this.f_89878_, RealmsSlotOptionsScreen.m_120774_(7), this.f_89879_, this.f_89858_, 0.0f, 16.0f));
        CycleButton<Boolean> $$8 = this.m_142416_(CycleButton.m_168916_(this.f_89855_).m_168936_($$0, RealmsSlotOptionsScreen.m_120774_(7), this.f_89879_, 20, Component.m_237115_("mco.configure.world.spawnNPCs"), this.m_231323_(Component.m_237115_("mco.configure.world.spawn_toggle.message.npc"), p_231312_ -> {
            this.f_89855_ = p_231312_;
        })));
        CycleButton<Boolean> $$9 = this.m_142416_(CycleButton.m_168916_(this.f_89860_).m_168936_(this.f_89878_, RealmsSlotOptionsScreen.m_120774_(9), this.f_89879_, 20, Component.m_237115_("mco.configure.world.forceGameMode"), (p_167534_, p_167535_) -> {
            this.f_89860_ = p_167535_;
        }));
        CycleButton<Boolean> $$10 = this.m_142416_(CycleButton.m_168916_(this.f_89859_).m_168936_($$0, RealmsSlotOptionsScreen.m_120774_(9), this.f_89879_, 20, Component.m_237115_("mco.configure.world.commandBlocks"), (p_167522_, p_167523_) -> {
            this.f_89859_ = p_167523_;
        }));
        if (this.f_89882_ != RealmsServer.WorldType.NORMAL) {
            $$4.f_93623_ = false;
            $$6.f_93623_ = false;
            $$8.f_93623_ = false;
            $$7.f_93623_ = false;
            this.f_89865_.f_93623_ = false;
            $$10.f_93623_ = false;
            $$9.f_93623_ = false;
        }
        if (this.f_89852_ == Difficulty.PEACEFUL) {
            $$7.f_93623_ = false;
        }
        this.m_142416_(new Button(this.f_89878_, RealmsSlotOptionsScreen.m_120774_(13), this.f_89879_, 20, Component.m_237115_("mco.configure.world.buttons.done"), p_89910_ -> this.m_89940_()));
        this.m_142416_(new Button($$0, RealmsSlotOptionsScreen.m_120774_(13), this.f_89879_, 20, CommonComponents.f_130656_, p_89905_ -> this.f_96541_.m_91152_(this.f_89872_)));
        this.m_7787_(this.f_89877_);
    }

    private CycleButton.OnValueChange<Boolean> m_231323_(Component p_231324_, Consumer<Boolean> p_231325_) {
        return (p_231318_, p_231319_) -> {
            if (p_231319_.booleanValue()) {
                p_231325_.accept(true);
            } else {
                this.f_96541_.m_91152_(new ConfirmScreen(p_231322_ -> {
                    if (p_231322_) {
                        p_231325_.accept(false);
                    }
                    this.f_96541_.m_91152_(this);
                }, f_231308_, p_231324_, CommonComponents.f_130659_, CommonComponents.f_130656_));
            }
        };
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(this.m_96636_(), this.m_175075_());
    }

    @Override
    public void m_6305_(PoseStack p_89895_, int p_89896_, int p_89897_, float p_89898_) {
        this.m_7333_(p_89895_);
        RealmsSlotOptionsScreen.m_93215_(p_89895_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        this.f_96547_.m_92889_(p_89895_, f_89876_, this.f_89878_ + this.f_89879_ / 2 - this.f_96547_.m_92852_(f_89876_) / 2, RealmsSlotOptionsScreen.m_120774_(0) - 5, 0xFFFFFF);
        this.f_89877_.m_6305_(p_89895_, p_89896_, p_89897_, p_89898_);
        super.m_6305_(p_89895_, p_89896_, p_89897_, p_89898_);
    }

    private void m_231313_(String p_231314_) {
        this.f_231310_ = p_231314_.equals(this.f_231309_) ? "" : p_231314_;
    }

    private void m_89940_() {
        int $$0 = RealmsSlotOptionsScreen.m_167528_(f_89870_, this.f_89852_, 2);
        int $$1 = RealmsSlotOptionsScreen.m_167528_(f_89871_, this.f_89853_, 0);
        if (this.f_89882_ == RealmsServer.WorldType.ADVENTUREMAP || this.f_89882_ == RealmsServer.WorldType.EXPERIENCE || this.f_89882_ == RealmsServer.WorldType.INSPIRATION) {
            this.f_89872_.m_88444_(new RealmsWorldOptions(this.f_89881_.f_87598_, this.f_89881_.f_87599_, this.f_89881_.f_87600_, this.f_89881_.f_87601_, this.f_89881_.f_87602_, this.f_89881_.f_87603_, $$0, $$1, this.f_89881_.f_87604_, this.f_231310_));
        } else {
            boolean $$2 = this.f_89882_ == RealmsServer.WorldType.NORMAL && this.f_89852_ != Difficulty.PEACEFUL && this.f_89857_;
            this.f_89872_.m_88444_(new RealmsWorldOptions(this.f_89854_, this.f_89856_, $$2, this.f_89855_, this.f_89858_, this.f_89859_, $$0, $$1, this.f_89860_, this.f_231310_));
        }
    }

    class SettingsSlider
    extends AbstractSliderButton {
        private final double f_89942_;
        private final double f_89943_;

        public SettingsSlider(int p_89946_, int p_89947_, int p_89948_, int p_89949_, float p_89950_, float p_89951_) {
            super(p_89946_, p_89947_, p_89948_, 20, CommonComponents.f_237098_, 0.0);
            this.f_89942_ = p_89950_;
            this.f_89943_ = p_89951_;
            this.f_93577_ = (Mth.m_14036_(p_89949_, p_89950_, p_89951_) - p_89950_) / (p_89951_ - p_89950_);
            this.m_5695_();
        }

        @Override
        public void m_5697_() {
            if (!RealmsSlotOptionsScreen.this.f_89865_.f_93623_) {
                return;
            }
            RealmsSlotOptionsScreen.this.f_89858_ = (int)Mth.m_14139_(Mth.m_14008_(this.f_93577_, 0.0, 1.0), this.f_89942_, this.f_89943_);
        }

        @Override
        protected void m_5695_() {
            this.m_93666_(CommonComponents.m_178393_(f_167513_, RealmsSlotOptionsScreen.this.f_89858_ == 0 ? CommonComponents.f_130654_ : Component.m_237113_(String.valueOf(RealmsSlotOptionsScreen.this.f_89858_))));
        }

        @Override
        public void m_5716_(double p_89954_, double p_89955_) {
        }

        @Override
        public void m_7691_(double p_89957_, double p_89958_) {
        }
    }
}

