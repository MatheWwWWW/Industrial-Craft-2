/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.ScreenNarrationCollector;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.slf4j.Logger;

public abstract class Screen
extends AbstractContainerEventHandler
implements Widget {
    private static final Logger f_96536_ = LogUtils.getLogger();
    private static final Set<String> f_96537_ = Sets.newHashSet((Object[])new String[]{"http", "https"});
    private static final int f_169366_ = 2;
    private static final Component f_169367_ = Component.m_237115_("narrator.screen.usage");
    protected final Component f_96539_;
    private final List<GuiEventListener> f_96540_ = Lists.newArrayList();
    private final List<NarratableEntry> f_169368_ = Lists.newArrayList();
    @Nullable
    protected Minecraft f_96541_;
    protected ItemRenderer f_96542_;
    public int f_96543_;
    public int f_96544_;
    private final List<Widget> f_169369_ = Lists.newArrayList();
    public boolean f_96546_;
    protected Font f_96547_;
    @Nullable
    private URI f_96538_;
    private static final long f_169370_;
    private static final long f_169371_;
    private static final long f_169372_ = 750L;
    private static final long f_169373_ = 200L;
    private static final long f_169374_ = 200L;
    private final ScreenNarrationCollector f_169375_ = new ScreenNarrationCollector();
    private long f_169376_ = Long.MIN_VALUE;
    private long f_169377_ = Long.MAX_VALUE;
    @Nullable
    private NarratableEntry f_169365_;

    protected Screen(Component p_96550_) {
        this.f_96539_ = p_96550_;
    }

    public Component m_96636_() {
        return this.f_96539_;
    }

    public Component m_142562_() {
        return this.m_96636_();
    }

    @Override
    public void m_6305_(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        for (Widget $$4 : this.f_169369_) {
            $$4.m_6305_(p_96562_, p_96563_, p_96564_, p_96565_);
        }
    }

    @Override
    public boolean m_7933_(int p_96552_, int p_96553_, int p_96554_) {
        if (p_96552_ == 256 && this.m_6913_()) {
            this.m_7379_();
            return true;
        }
        if (p_96552_ == 258) {
            boolean $$3;
            boolean bl = $$3 = !Screen.m_96638_();
            if (!this.m_5755_($$3)) {
                this.m_5755_($$3);
            }
            return false;
        }
        return super.m_7933_(p_96552_, p_96553_, p_96554_);
    }

    public boolean m_6913_() {
        return true;
    }

    public void m_7379_() {
        this.f_96541_.m_91152_(null);
    }

    protected <T extends GuiEventListener & Widget> T m_142416_(T p_169406_) {
        this.f_169369_.add(p_169406_);
        return this.m_7787_(p_169406_);
    }

    protected <T extends Widget> T m_169394_(T p_169395_) {
        this.f_169369_.add(p_169395_);
        return p_169395_;
    }

    protected <T extends GuiEventListener & NarratableEntry> T m_7787_(T p_96625_) {
        this.f_96540_.add(p_96625_);
        this.f_169368_.add(p_96625_);
        return p_96625_;
    }

    protected void m_169411_(GuiEventListener p_169412_) {
        if (p_169412_ instanceof Widget) {
            this.f_169369_.remove((Widget)((Object)p_169412_));
        }
        if (p_169412_ instanceof NarratableEntry) {
            this.f_169368_.remove((NarratableEntry)((Object)p_169412_));
        }
        this.f_96540_.remove(p_169412_);
    }

    protected void m_169413_() {
        this.f_169369_.clear();
        this.f_96540_.clear();
        this.f_169368_.clear();
    }

    protected void m_6057_(PoseStack p_96566_, ItemStack p_96567_, int p_96568_, int p_96569_) {
        this.m_169388_(p_96566_, this.m_96555_(p_96567_), p_96567_.m_150921_(), p_96568_, p_96569_);
    }

    public void m_169388_(PoseStack p_169389_, List<Component> p_169390_, Optional<TooltipComponent> p_169391_, int p_169392_, int p_169393_) {
        List<ClientTooltipComponent> $$5 = p_169390_.stream().map(Component::m_7532_).map(ClientTooltipComponent::m_169948_).collect(Collectors.toList());
        p_169391_.ifPresent(p_169399_ -> $$5.add(1, ClientTooltipComponent.m_169950_(p_169399_)));
        this.m_169383_(p_169389_, $$5, p_169392_, p_169393_);
    }

    public List<Component> m_96555_(ItemStack p_96556_) {
        return p_96556_.m_41651_(this.f_96541_.f_91074_, this.f_96541_.f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }

    public void m_96602_(PoseStack p_96603_, Component p_96604_, int p_96605_, int p_96606_) {
        this.m_96617_(p_96603_, Arrays.asList(p_96604_.m_7532_()), p_96605_, p_96606_);
    }

    public void m_96597_(PoseStack p_96598_, List<Component> p_96599_, int p_96600_, int p_96601_) {
        this.m_96617_(p_96598_, Lists.transform(p_96599_, Component::m_7532_), p_96600_, p_96601_);
    }

    public void m_96617_(PoseStack p_96618_, List<? extends FormattedCharSequence> p_96619_, int p_96620_, int p_96621_) {
        this.m_169383_(p_96618_, p_96619_.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), p_96620_, p_96621_);
    }

    private void m_169383_(PoseStack p_169384_, List<ClientTooltipComponent> p_169385_, int p_169386_, int p_169387_) {
        if (p_169385_.isEmpty()) {
            return;
        }
        int $$4 = 0;
        int $$5 = p_169385_.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent $$6 : p_169385_) {
            int $$7 = $$6.m_142069_(this.f_96547_);
            if ($$7 > $$4) {
                $$4 = $$7;
            }
            $$5 += $$6.m_142103_();
        }
        int $$8 = p_169386_ + 12;
        int $$9 = p_169387_ - 12;
        int $$10 = $$4;
        int $$11 = $$5;
        if ($$8 + $$4 > this.f_96543_) {
            $$8 -= 28 + $$4;
        }
        if ($$9 + $$11 + 6 > this.f_96544_) {
            $$9 = this.f_96544_ - $$11 - 6;
        }
        p_169384_.m_85836_();
        int $$12 = -267386864;
        int $$13 = 0x505000FF;
        int $$14 = 1344798847;
        int $$15 = 400;
        float $$16 = this.f_96542_.f_115093_;
        this.f_96542_.f_115093_ = 400.0f;
        Tesselator $$17 = Tesselator.m_85913_();
        BufferBuilder $$18 = $$17.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        $$18.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        Matrix4f $$19 = p_169384_.m_85850_().m_85861_();
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 - 4, $$8 + $$10 + 3, $$9 - 3, 400, -267386864, -267386864);
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 + $$11 + 3, $$8 + $$10 + 3, $$9 + $$11 + 4, 400, -267386864, -267386864);
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 - 3, $$8 + $$10 + 3, $$9 + $$11 + 3, 400, -267386864, -267386864);
        Screen.m_93123_($$19, $$18, $$8 - 4, $$9 - 3, $$8 - 3, $$9 + $$11 + 3, 400, -267386864, -267386864);
        Screen.m_93123_($$19, $$18, $$8 + $$10 + 3, $$9 - 3, $$8 + $$10 + 4, $$9 + $$11 + 3, 400, -267386864, -267386864);
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 - 3 + 1, $$8 - 3 + 1, $$9 + $$11 + 3 - 1, 400, 0x505000FF, 1344798847);
        Screen.m_93123_($$19, $$18, $$8 + $$10 + 2, $$9 - 3 + 1, $$8 + $$10 + 3, $$9 + $$11 + 3 - 1, 400, 0x505000FF, 1344798847);
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 - 3, $$8 + $$10 + 3, $$9 - 3 + 1, 400, 0x505000FF, 0x505000FF);
        Screen.m_93123_($$19, $$18, $$8 - 3, $$9 + $$11 + 2, $$8 + $$10 + 3, $$9 + $$11 + 3, 400, 1344798847, 1344798847);
        RenderSystem.m_69482_();
        RenderSystem.m_69472_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        BufferUploader.m_231202_($$18.m_231175_());
        RenderSystem.m_69461_();
        RenderSystem.m_69493_();
        MultiBufferSource.BufferSource $$20 = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
        p_169384_.m_85837_(0.0, 0.0, 400.0);
        int $$21 = $$9;
        for (int $$22 = 0; $$22 < p_169385_.size(); ++$$22) {
            ClientTooltipComponent $$23 = p_169385_.get($$22);
            $$23.m_142440_(this.f_96547_, $$8, $$21, $$19, $$20);
            $$21 += $$23.m_142103_() + ($$22 == 0 ? 2 : 0);
        }
        $$20.m_109911_();
        p_169384_.m_85849_();
        $$21 = $$9;
        for (int $$24 = 0; $$24 < p_169385_.size(); ++$$24) {
            ClientTooltipComponent $$25 = p_169385_.get($$24);
            $$25.m_183452_(this.f_96547_, $$8, $$21, p_169384_, this.f_96542_, 400);
            $$21 += $$25.m_142103_() + ($$24 == 0 ? 2 : 0);
        }
        this.f_96542_.f_115093_ = $$16;
    }

    protected void m_96570_(PoseStack p_96571_, @Nullable Style p_96572_, int p_96573_, int p_96574_) {
        if (p_96572_ == null || p_96572_.m_131186_() == null) {
            return;
        }
        HoverEvent $$4 = p_96572_.m_131186_();
        HoverEvent.ItemStackInfo $$5 = $$4.m_130823_(HoverEvent.Action.f_130832_);
        if ($$5 != null) {
            this.m_6057_(p_96571_, $$5.m_130898_(), p_96573_, p_96574_);
        } else {
            HoverEvent.EntityTooltipInfo $$6 = $$4.m_130823_(HoverEvent.Action.f_130833_);
            if ($$6 != null) {
                if (this.f_96541_.f_91066_.f_92125_) {
                    this.m_96597_(p_96571_, $$6.m_130884_(), p_96573_, p_96574_);
                }
            } else {
                Component $$7 = $$4.m_130823_(HoverEvent.Action.f_130831_);
                if ($$7 != null) {
                    this.m_96617_(p_96571_, this.f_96541_.f_91062_.m_92923_($$7, Math.max(this.f_96543_ / 2, 200)), p_96573_, p_96574_);
                }
            }
        }
    }

    protected void m_6697_(String p_96587_, boolean p_96588_) {
    }

    public boolean m_5561_(@Nullable Style p_96592_) {
        if (p_96592_ == null) {
            return false;
        }
        ClickEvent $$1 = p_96592_.m_131182_();
        if (Screen.m_96638_()) {
            if (p_96592_.m_131189_() != null) {
                this.m_6697_(p_96592_.m_131189_(), false);
            }
        } else if ($$1 != null) {
            block24: {
                if ($$1.m_130622_() == ClickEvent.Action.OPEN_URL) {
                    if (!this.f_96541_.f_91066_.m_231815_().m_231551_().booleanValue()) {
                        return false;
                    }
                    try {
                        URI $$2 = new URI($$1.m_130623_());
                        String $$3 = $$2.getScheme();
                        if ($$3 == null) {
                            throw new URISyntaxException($$1.m_130623_(), "Missing protocol");
                        }
                        if (!f_96537_.contains($$3.toLowerCase(Locale.ROOT))) {
                            throw new URISyntaxException($$1.m_130623_(), "Unsupported protocol: " + $$3.toLowerCase(Locale.ROOT));
                        }
                        if (this.f_96541_.f_91066_.m_231816_().m_231551_().booleanValue()) {
                            this.f_96538_ = $$2;
                            this.f_96541_.m_91152_(new ConfirmLinkScreen(this::m_96622_, $$1.m_130623_(), false));
                            break block24;
                        }
                        this.m_96589_($$2);
                    }
                    catch (URISyntaxException $$4) {
                        f_96536_.error("Can't open url for {}", (Object)$$1, (Object)$$4);
                    }
                } else if ($$1.m_130622_() == ClickEvent.Action.OPEN_FILE) {
                    URI $$5 = new File($$1.m_130623_()).toURI();
                    this.m_96589_($$5);
                } else if ($$1.m_130622_() == ClickEvent.Action.SUGGEST_COMMAND) {
                    this.m_6697_(SharedConstants.m_136190_($$1.m_130623_()), true);
                } else if ($$1.m_130622_() == ClickEvent.Action.RUN_COMMAND) {
                    String $$6 = SharedConstants.m_136190_($$1.m_130623_());
                    if ($$6.startsWith("/")) {
                        if (!this.f_96541_.f_91074_.m_242614_($$6.substring(1))) {
                            f_96536_.error("Not allowed to run command with signed argument from click event: '{}'", (Object)$$6);
                        }
                    } else {
                        f_96536_.error("Failed to run command without '/' prefix from click event: '{}'", (Object)$$6);
                    }
                } else if ($$1.m_130622_() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
                    this.f_96541_.f_91068_.m_90911_($$1.m_130623_());
                } else {
                    f_96536_.error("Don't know how to handle {}", (Object)$$1);
                }
            }
            return true;
        }
        return false;
    }

    public final void m_6575_(Minecraft p_96607_, int p_96608_, int p_96609_) {
        this.f_96541_ = p_96607_;
        this.f_96542_ = p_96607_.m_91291_();
        this.f_96547_ = p_96607_.f_91062_;
        this.f_96543_ = p_96608_;
        this.f_96544_ = p_96609_;
        this.m_232761_();
        this.m_169407_(false);
        this.m_169378_(f_169370_);
    }

    protected void m_232761_() {
        this.m_169413_();
        this.m_7522_(null);
        this.m_7856_();
    }

    @Override
    public List<? extends GuiEventListener> m_6702_() {
        return this.f_96540_;
    }

    protected void m_7856_() {
    }

    public void m_86600_() {
    }

    public void m_7861_() {
    }

    public void m_7333_(PoseStack p_96557_) {
        this.m_96558_(p_96557_, 0);
    }

    public void m_96558_(PoseStack p_96559_, int p_96560_) {
        if (this.f_96541_.f_91073_ != null) {
            this.m_93179_(p_96559_, 0, 0, this.f_96543_, this.f_96544_, -1072689136, -804253680);
        } else {
            this.m_96626_(p_96560_);
        }
    }

    public void m_96626_(int p_96627_) {
        Tesselator $$1 = Tesselator.m_85913_();
        BufferBuilder $$2 = $$1.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_(0, f_93096_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        float $$3 = 32.0f;
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        $$2.m_5483_(0.0, this.f_96544_, 0.0).m_7421_(0.0f, (float)this.f_96544_ / 32.0f + (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
        $$2.m_5483_(this.f_96543_, this.f_96544_, 0.0).m_7421_((float)this.f_96543_ / 32.0f, (float)this.f_96544_ / 32.0f + (float)p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
        $$2.m_5483_(this.f_96543_, 0.0, 0.0).m_7421_((float)this.f_96543_ / 32.0f, p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
        $$2.m_5483_(0.0, 0.0, 0.0).m_7421_(0.0f, p_96627_).m_6122_(64, 64, 64, 255).m_5752_();
        $$1.m_85914_();
    }

    public boolean m_7043_() {
        return true;
    }

    private void m_96622_(boolean p_96623_) {
        if (p_96623_) {
            this.m_96589_(this.f_96538_);
        }
        this.f_96538_ = null;
        this.f_96541_.m_91152_(this);
    }

    private void m_96589_(URI p_96590_) {
        Util.m_137581_().m_137648_(p_96590_);
    }

    public static boolean m_96637_() {
        if (Minecraft.f_91002_) {
            return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 343) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 347);
        }
        return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 341) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 345);
    }

    public static boolean m_96638_() {
        return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344);
    }

    public static boolean m_96639_() {
        return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 342) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 346);
    }

    public static boolean m_96628_(int p_96629_) {
        return p_96629_ == 88 && Screen.m_96637_() && !Screen.m_96638_() && !Screen.m_96639_();
    }

    public static boolean m_96630_(int p_96631_) {
        return p_96631_ == 86 && Screen.m_96637_() && !Screen.m_96638_() && !Screen.m_96639_();
    }

    public static boolean m_96632_(int p_96633_) {
        return p_96633_ == 67 && Screen.m_96637_() && !Screen.m_96638_() && !Screen.m_96639_();
    }

    public static boolean m_96634_(int p_96635_) {
        return p_96635_ == 65 && Screen.m_96637_() && !Screen.m_96638_() && !Screen.m_96639_();
    }

    public void m_6574_(Minecraft p_96575_, int p_96576_, int p_96577_) {
        this.m_6575_(p_96575_, p_96576_, p_96577_);
    }

    public static void m_96579_(Runnable p_96580_, String p_96581_, String p_96582_) {
        try {
            p_96580_.run();
        }
        catch (Throwable $$3) {
            CrashReport $$4 = CrashReport.m_127521_($$3, p_96581_);
            CrashReportCategory $$5 = $$4.m_127514_("Affected screen");
            $$5.m_128165_("Screen name", () -> p_96582_);
            throw new ReportedException($$4);
        }
    }

    protected boolean m_96583_(String p_96584_, char p_96585_, int p_96586_) {
        int $$3 = p_96584_.indexOf(58);
        int $$4 = p_96584_.indexOf(47);
        if (p_96585_ == ':') {
            return ($$4 == -1 || p_96586_ <= $$4) && $$3 == -1;
        }
        if (p_96585_ == '/') {
            return p_96586_ > $$3;
        }
        return p_96585_ == '_' || p_96585_ == '-' || p_96585_ >= 'a' && p_96585_ <= 'z' || p_96585_ >= '0' && p_96585_ <= '9' || p_96585_ == '.';
    }

    @Override
    public boolean m_5953_(double p_96595_, double p_96596_) {
        return true;
    }

    public void m_7400_(List<Path> p_96591_) {
    }

    private void m_169380_(long p_169381_, boolean p_169382_) {
        this.f_169377_ = Util.m_137550_() + p_169381_;
        if (p_169382_) {
            this.f_169376_ = Long.MIN_VALUE;
        }
    }

    private void m_169378_(long p_169379_) {
        this.f_169376_ = Util.m_137550_() + p_169379_;
    }

    public void m_169414_() {
        this.m_169380_(750L, false);
    }

    public void m_169415_() {
        this.m_169380_(200L, true);
    }

    public void m_169416_() {
        this.m_169380_(200L, true);
    }

    private boolean m_169419_() {
        return this.f_96541_.m_240477_().m_93316_();
    }

    public void m_169417_() {
        long $$0;
        if (this.m_169419_() && ($$0 = Util.m_137550_()) > this.f_169377_ && $$0 > this.f_169376_) {
            this.m_169409_(true);
            this.f_169377_ = Long.MAX_VALUE;
        }
    }

    public void m_169407_(boolean p_169408_) {
        if (this.m_169419_()) {
            this.m_169409_(p_169408_);
        }
    }

    private void m_169409_(boolean p_169410_) {
        this.f_169375_.m_169186_(this::m_142228_);
        String $$1 = this.f_169375_.m_169188_(!p_169410_);
        if (!$$1.isEmpty()) {
            this.f_96541_.m_240477_().m_93319_($$1);
        }
    }

    protected void m_142228_(NarrationElementOutput p_169396_) {
        p_169396_.m_169146_(NarratedElementType.TITLE, this.m_142562_());
        p_169396_.m_169146_(NarratedElementType.USAGE, f_169367_);
        this.m_142227_(p_169396_);
    }

    protected void m_142227_(NarrationElementOutput p_169403_) {
        ImmutableList $$1 = (ImmutableList)this.f_169368_.stream().filter(NarratableEntry::m_142518_).collect(ImmutableList.toImmutableList());
        NarratableSearchResult $$2 = Screen.m_169400_((List<? extends NarratableEntry>)$$1, this.f_169365_);
        if ($$2 != null) {
            if ($$2.f_169422_.m_169123_()) {
                this.f_169365_ = $$2.f_169420_;
            }
            if ($$1.size() > 1) {
                p_169403_.m_169146_(NarratedElementType.POSITION, Component.m_237110_("narrator.position.screen", $$2.f_169421_ + 1, $$1.size()));
                if ($$2.f_169422_ == NarratableEntry.NarrationPriority.FOCUSED) {
                    p_169403_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.component_list.usage"));
                }
            }
            $$2.f_169420_.m_142291_(p_169403_.m_142047_());
        }
    }

    @Nullable
    public static NarratableSearchResult m_169400_(List<? extends NarratableEntry> p_169401_, @Nullable NarratableEntry p_169402_) {
        NarratableSearchResult $$2 = null;
        NarratableSearchResult $$3 = null;
        int $$5 = p_169401_.size();
        for (int $$4 = 0; $$4 < $$5; ++$$4) {
            NarratableEntry $$6 = p_169401_.get($$4);
            NarratableEntry.NarrationPriority $$7 = $$6.m_142684_();
            if ($$7.m_169123_()) {
                if ($$6 == p_169402_) {
                    $$3 = new NarratableSearchResult($$6, $$4, $$7);
                    continue;
                }
                return new NarratableSearchResult($$6, $$4, $$7);
            }
            if ($$7.compareTo($$2 != null ? $$2.f_169422_ : NarratableEntry.NarrationPriority.NONE) <= 0) continue;
            $$2 = new NarratableSearchResult($$6, $$4, $$7);
        }
        return $$2 != null ? $$2 : $$3;
    }

    public void m_169418_() {
        this.m_169380_(f_169371_, false);
    }

    protected static void m_202376_(AbstractWidget ... p_202377_) {
        for (AbstractWidget $$1 : p_202377_) {
            $$1.f_93624_ = false;
        }
    }

    static {
        f_169371_ = f_169370_ = TimeUnit.SECONDS.toMillis(2L);
    }

    public static class NarratableSearchResult {
        public final NarratableEntry f_169420_;
        public final int f_169421_;
        public final NarratableEntry.NarrationPriority f_169422_;

        public NarratableSearchResult(NarratableEntry p_169424_, int p_169425_, NarratableEntry.NarrationPriority p_169426_) {
            this.f_169420_ = p_169424_;
            this.f_169421_ = p_169425_;
            this.f_169422_ = p_169426_;
        }
    }
}

