/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.RecipeShownListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeBookComponent
extends GuiComponent
implements PlaceRecipe<Ingredient>,
Widget,
GuiEventListener,
NarratableEntry,
RecipeShownListener {
    protected static final ResourceLocation f_100268_ = new ResourceLocation("textures/gui/recipe_book.png");
    private static final Component f_100273_ = Component.m_237115_("gui.recipebook.search_hint").m_130940_(ChatFormatting.ITALIC).m_130940_(ChatFormatting.GRAY);
    public static final int f_170042_ = 147;
    public static final int f_170043_ = 166;
    private static final int f_170044_ = 86;
    private static final Component f_100274_ = Component.m_237115_("gui.recipebook.toggleRecipes.craftable");
    private static final Component f_100275_ = Component.m_237115_("gui.recipebook.toggleRecipes.all");
    private int f_100276_;
    private int f_100277_;
    private int f_100278_;
    protected final GhostRecipe f_100269_ = new GhostRecipe();
    private final List<RecipeBookTabButton> f_100279_ = Lists.newArrayList();
    @Nullable
    private RecipeBookTabButton f_100280_;
    protected StateSwitchingButton f_100270_;
    protected RecipeBookMenu<?> f_100271_;
    protected Minecraft f_100272_;
    @Nullable
    private EditBox f_100281_;
    private String f_100282_ = "";
    private ClientRecipeBook f_100283_;
    private final RecipeBookPage f_100284_ = new RecipeBookPage();
    private final StackedContents f_100285_ = new StackedContents();
    private int f_100286_;
    private boolean f_100287_;
    private boolean f_170041_;
    private boolean f_181400_;

    public void m_100309_(int p_100310_, int p_100311_, Minecraft p_100312_, boolean p_100313_, RecipeBookMenu<?> p_100314_) {
        this.f_100272_ = p_100312_;
        this.f_100277_ = p_100310_;
        this.f_100278_ = p_100311_;
        this.f_100271_ = p_100314_;
        this.f_181400_ = p_100313_;
        p_100312_.f_91074_.f_36096_ = p_100314_;
        this.f_100283_ = p_100312_.f_91074_.m_108631_();
        this.f_100286_ = p_100312_.f_91074_.m_150109_().m_36072_();
        this.f_170041_ = this.m_170050_();
        if (this.f_170041_) {
            this.m_181404_();
        }
        p_100312_.f_91068_.m_90926_(true);
    }

    public void m_181404_() {
        this.f_100276_ = this.f_181400_ ? 0 : 86;
        int $$0 = (this.f_100277_ - 147) / 2 - this.f_100276_;
        int $$1 = (this.f_100278_ - 166) / 2;
        this.f_100285_.m_36453_();
        this.f_100272_.f_91074_.m_150109_().m_36010_(this.f_100285_);
        this.f_100271_.m_5816_(this.f_100285_);
        String $$2 = this.f_100281_ != null ? this.f_100281_.m_94155_() : "";
        this.f_100281_ = new EditBox(this.f_100272_.f_91062_, $$0 + 25, $$1 + 14, 80, this.f_100272_.f_91062_.f_92710_ + 5, Component.m_237115_("itemGroup.search"));
        this.f_100281_.m_94199_(50);
        this.f_100281_.m_94182_(false);
        this.f_100281_.m_94194_(true);
        this.f_100281_.m_94202_(0xFFFFFF);
        this.f_100281_.m_94144_($$2);
        this.f_100284_.m_100428_(this.f_100272_, $$0, $$1);
        this.f_100284_.m_100432_(this);
        this.f_100270_ = new StateSwitchingButton($$0 + 110, $$1 + 12, 26, 16, this.f_100283_.m_12689_(this.f_100271_));
        this.m_5674_();
        this.f_100279_.clear();
        for (RecipeBookCategories $$3 : RecipeBookCategories.m_92269_(this.f_100271_.m_5867_())) {
            this.f_100279_.add(new RecipeBookTabButton($$3));
        }
        if (this.f_100280_ != null) {
            this.f_100280_ = this.f_100279_.stream().filter(p_100329_ -> p_100329_.m_100455_().equals((Object)this.f_100280_.m_100455_())).findFirst().orElse(null);
        }
        if (this.f_100280_ == null) {
            this.f_100280_ = this.f_100279_.get(0);
        }
        this.f_100280_.m_94635_(true);
        this.m_100382_(false);
        this.m_100351_();
    }

    @Override
    public boolean m_5755_(boolean p_100372_) {
        return false;
    }

    protected void m_5674_() {
        this.f_100270_.m_94624_(152, 41, 28, 18, f_100268_);
    }

    public void m_100373_() {
        this.f_100272_.f_91068_.m_90926_(false);
    }

    public int m_181401_(int p_181402_, int p_181403_) {
        int $$3;
        if (this.m_100385_() && !this.f_181400_) {
            int $$2 = 177 + (p_181402_ - p_181403_ - 200) / 2;
        } else {
            $$3 = (p_181402_ - p_181403_) / 2;
        }
        return $$3;
    }

    public void m_100384_() {
        this.m_100369_(!this.m_100385_());
    }

    public boolean m_100385_() {
        return this.f_170041_;
    }

    private boolean m_170050_() {
        return this.f_100283_.m_12691_(this.f_100271_.m_5867_());
    }

    protected void m_100369_(boolean p_100370_) {
        if (p_100370_) {
            this.m_181404_();
        }
        this.f_170041_ = p_100370_;
        this.f_100283_.m_12693_(this.f_100271_.m_5867_(), p_100370_);
        if (!p_100370_) {
            this.f_100284_.m_100440_();
        }
        this.m_100388_();
    }

    public void m_6904_(@Nullable Slot p_100315_) {
        if (p_100315_ != null && p_100315_.f_40219_ < this.f_100271_.m_6653_()) {
            this.f_100269_.m_100140_();
            if (this.m_100385_()) {
                this.m_100389_();
            }
        }
    }

    private void m_100382_(boolean p_100383_) {
        List<RecipeCollection> $$1 = this.f_100283_.m_90623_(this.f_100280_.m_100455_());
        $$1.forEach(p_100381_ -> p_100381_.m_100501_(this.f_100285_, this.f_100271_.m_6635_(), this.f_100271_.m_6656_(), this.f_100283_));
        ArrayList $$2 = Lists.newArrayList($$1);
        $$2.removeIf(p_100368_ -> !p_100368_.m_100498_());
        $$2.removeIf(p_100360_ -> !p_100360_.m_100515_());
        String $$3 = this.f_100281_.m_94155_();
        if (!$$3.isEmpty()) {
            ObjectLinkedOpenHashSet $$4 = new ObjectLinkedOpenHashSet(this.f_100272_.m_231372_(SearchRegistry.f_119943_).m_6293_($$3.toLowerCase(Locale.ROOT)));
            $$2.removeIf(arg_0 -> RecipeBookComponent.m_100332_((ObjectSet)$$4, arg_0));
        }
        if (this.f_100283_.m_12689_(this.f_100271_)) {
            $$2.removeIf(p_100331_ -> !p_100331_.m_100512_());
        }
        this.f_100284_.m_100436_($$2, p_100383_);
    }

    private void m_100351_() {
        int $$0 = (this.f_100277_ - 147) / 2 - this.f_100276_ - 30;
        int $$1 = (this.f_100278_ - 166) / 2 + 3;
        int $$2 = 27;
        int $$3 = 0;
        for (RecipeBookTabButton $$4 : this.f_100279_) {
            RecipeBookCategories $$5 = $$4.m_100455_();
            if ($$5 == RecipeBookCategories.CRAFTING_SEARCH || $$5 == RecipeBookCategories.FURNACE_SEARCH) {
                $$4.f_93624_ = true;
                $$4.m_94621_($$0, $$1 + 27 * $$3++);
                continue;
            }
            if (!$$4.m_100449_(this.f_100283_)) continue;
            $$4.m_94621_($$0, $$1 + 27 * $$3++);
            $$4.m_100451_(this.f_100272_);
        }
    }

    public void m_100386_() {
        boolean $$0 = this.m_170050_();
        if (this.m_100385_() != $$0) {
            this.m_100369_($$0);
        }
        if (!this.m_100385_()) {
            return;
        }
        if (this.f_100286_ != this.f_100272_.f_91074_.m_150109_().m_36072_()) {
            this.m_100389_();
            this.f_100286_ = this.f_100272_.f_91074_.m_150109_().m_36072_();
        }
        this.f_100281_.m_94120_();
    }

    private void m_100389_() {
        this.f_100285_.m_36453_();
        this.f_100272_.f_91074_.m_150109_().m_36010_(this.f_100285_);
        this.f_100271_.m_5816_(this.f_100285_);
        this.m_100382_(false);
    }

    @Override
    public void m_6305_(PoseStack p_100319_, int p_100320_, int p_100321_, float p_100322_) {
        if (!this.m_100385_()) {
            return;
        }
        p_100319_.m_85836_();
        p_100319_.m_85837_(0.0, 0.0, 100.0);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_100268_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        int $$4 = (this.f_100277_ - 147) / 2 - this.f_100276_;
        int $$5 = (this.f_100278_ - 166) / 2;
        this.m_93228_(p_100319_, $$4, $$5, 1, 1, 147, 166);
        if (!this.f_100281_.m_93696_() && this.f_100281_.m_94155_().isEmpty()) {
            RecipeBookComponent.m_93243_(p_100319_, this.f_100272_.f_91062_, f_100273_, $$4 + 25, $$5 + 14, -1);
        } else {
            this.f_100281_.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
        }
        for (RecipeBookTabButton $$6 : this.f_100279_) {
            $$6.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
        }
        this.f_100270_.m_6305_(p_100319_, p_100320_, p_100321_, p_100322_);
        this.f_100284_.m_100421_(p_100319_, $$4, $$5, p_100320_, p_100321_, p_100322_);
        p_100319_.m_85849_();
    }

    public void m_100361_(PoseStack p_100362_, int p_100363_, int p_100364_, int p_100365_, int p_100366_) {
        if (!this.m_100385_()) {
            return;
        }
        this.f_100284_.m_100417_(p_100362_, p_100365_, p_100366_);
        if (this.f_100270_.m_198029_()) {
            Component $$5 = this.m_100390_();
            if (this.f_100272_.f_91080_ != null) {
                this.f_100272_.f_91080_.m_96602_(p_100362_, $$5, p_100365_, p_100366_);
            }
        }
        this.m_100374_(p_100362_, p_100363_, p_100364_, p_100365_, p_100366_);
    }

    private Component m_100390_() {
        return this.f_100270_.m_94620_() ? this.m_5815_() : f_100275_;
    }

    protected Component m_5815_() {
        return f_100274_;
    }

    private void m_100374_(PoseStack p_100375_, int p_100376_, int p_100377_, int p_100378_, int p_100379_) {
        ItemStack $$5 = null;
        for (int $$6 = 0; $$6 < this.f_100269_.m_100158_(); ++$$6) {
            GhostRecipe.GhostIngredient $$7 = this.f_100269_.m_100141_($$6);
            int $$8 = $$7.m_100169_() + p_100376_;
            int $$9 = $$7.m_100170_() + p_100377_;
            if (p_100378_ < $$8 || p_100379_ < $$9 || p_100378_ >= $$8 + 16 || p_100379_ >= $$9 + 16) continue;
            $$5 = $$7.m_100171_();
        }
        if ($$5 != null && this.f_100272_.f_91080_ != null) {
            this.f_100272_.f_91080_.m_96597_(p_100375_, this.f_100272_.f_91080_.m_96555_($$5), p_100378_, p_100379_);
        }
    }

    public void m_6545_(PoseStack p_100323_, int p_100324_, int p_100325_, boolean p_100326_, float p_100327_) {
        this.f_100269_.m_100149_(p_100323_, this.f_100272_, p_100324_, p_100325_, p_100326_, p_100327_);
    }

    @Override
    public boolean m_6375_(double p_100294_, double p_100295_, int p_100296_) {
        if (!this.m_100385_() || this.f_100272_.f_91074_.m_5833_()) {
            return false;
        }
        if (this.f_100284_.m_100409_(p_100294_, p_100295_, p_100296_, (this.f_100277_ - 147) / 2 - this.f_100276_, (this.f_100278_ - 166) / 2, 147, 166)) {
            Recipe<?> $$3 = this.f_100284_.m_100408_();
            RecipeCollection $$4 = this.f_100284_.m_100439_();
            if ($$3 != null && $$4 != null) {
                if (!$$4.m_100506_($$3) && this.f_100269_.m_100159_() == $$3) {
                    return false;
                }
                this.f_100269_.m_100140_();
                this.f_100272_.f_91072_.m_105217_(this.f_100272_.f_91074_.f_36096_.f_38840_, $$3, Screen.m_96638_());
                if (!this.m_100393_()) {
                    this.m_100369_(false);
                }
            }
            return true;
        }
        if (this.f_100281_.m_6375_(p_100294_, p_100295_, p_100296_)) {
            return true;
        }
        if (this.f_100270_.m_6375_(p_100294_, p_100295_, p_100296_)) {
            boolean $$5 = this.m_100391_();
            this.f_100270_.m_94635_($$5);
            this.m_100388_();
            this.m_100382_(false);
            return true;
        }
        for (RecipeBookTabButton $$6 : this.f_100279_) {
            if (!$$6.m_6375_(p_100294_, p_100295_, p_100296_)) continue;
            if (this.f_100280_ != $$6) {
                if (this.f_100280_ != null) {
                    this.f_100280_.m_94635_(false);
                }
                this.f_100280_ = $$6;
                this.f_100280_.m_94635_(true);
                this.m_100382_(true);
            }
            return true;
        }
        return false;
    }

    private boolean m_100391_() {
        RecipeBookType $$0 = this.f_100271_.m_5867_();
        boolean $$1 = !this.f_100283_.m_12704_($$0);
        this.f_100283_.m_12706_($$0, $$1);
        return $$1;
    }

    public boolean m_100297_(double p_100298_, double p_100299_, int p_100300_, int p_100301_, int p_100302_, int p_100303_, int p_100304_) {
        if (!this.m_100385_()) {
            return true;
        }
        boolean $$7 = p_100298_ < (double)p_100300_ || p_100299_ < (double)p_100301_ || p_100298_ >= (double)(p_100300_ + p_100302_) || p_100299_ >= (double)(p_100301_ + p_100303_);
        boolean $$8 = (double)(p_100300_ - 147) < p_100298_ && p_100298_ < (double)p_100300_ && (double)p_100301_ < p_100299_ && p_100299_ < (double)(p_100301_ + p_100303_);
        return $$7 && !$$8 && !this.f_100280_.m_198029_();
    }

    @Override
    public boolean m_7933_(int p_100306_, int p_100307_, int p_100308_) {
        this.f_100287_ = false;
        if (!this.m_100385_() || this.f_100272_.f_91074_.m_5833_()) {
            return false;
        }
        if (p_100306_ == 256 && !this.m_100393_()) {
            this.m_100369_(false);
            return true;
        }
        if (this.f_100281_.m_7933_(p_100306_, p_100307_, p_100308_)) {
            this.m_100392_();
            return true;
        }
        if (this.f_100281_.m_93696_() && this.f_100281_.m_94213_() && p_100306_ != 256) {
            return true;
        }
        if (this.f_100272_.f_91066_.f_92098_.m_90832_(p_100306_, p_100307_) && !this.f_100281_.m_93696_()) {
            this.f_100287_ = true;
            this.f_100281_.m_94178_(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7920_(int p_100356_, int p_100357_, int p_100358_) {
        this.f_100287_ = false;
        return GuiEventListener.super.m_7920_(p_100356_, p_100357_, p_100358_);
    }

    @Override
    public boolean m_5534_(char p_100291_, int p_100292_) {
        if (this.f_100287_) {
            return false;
        }
        if (!this.m_100385_() || this.f_100272_.f_91074_.m_5833_()) {
            return false;
        }
        if (this.f_100281_.m_5534_(p_100291_, p_100292_)) {
            this.m_100392_();
            return true;
        }
        return GuiEventListener.super.m_5534_(p_100291_, p_100292_);
    }

    @Override
    public boolean m_5953_(double p_100353_, double p_100354_) {
        return false;
    }

    private void m_100392_() {
        String $$0 = this.f_100281_.m_94155_().toLowerCase(Locale.ROOT);
        this.m_100335_($$0);
        if (!$$0.equals(this.f_100282_)) {
            this.m_100382_(false);
            this.f_100282_ = $$0;
        }
    }

    private void m_100335_(String p_100336_) {
        if ("excitedze".equals(p_100336_)) {
            LanguageManager $$1 = this.f_100272_.m_91102_();
            LanguageInfo $$2 = $$1.m_118976_("en_pt");
            if ($$1.m_118983_().compareTo($$2) == 0) {
                return;
            }
            $$1.m_118974_($$2);
            this.f_100272_.f_91066_.f_92075_ = $$2.getCode();
            this.f_100272_.m_91391_();
            this.f_100272_.f_91066_.m_92169_();
        }
    }

    private boolean m_100393_() {
        return this.f_100276_ == 86;
    }

    public void m_100387_() {
        this.m_100351_();
        if (this.m_100385_()) {
            this.m_100382_(false);
        }
    }

    @Override
    public void m_7262_(List<Recipe<?>> p_100344_) {
        for (Recipe<?> $$1 : p_100344_) {
            this.f_100272_.f_91074_.m_108675_($$1);
        }
    }

    public void m_7173_(Recipe<?> p_100316_, List<Slot> p_100317_) {
        ItemStack $$2 = p_100316_.m_8043_();
        this.f_100269_.m_100147_(p_100316_);
        this.f_100269_.m_100143_(Ingredient.m_43927_($$2), p_100317_.get((int)0).f_40220_, p_100317_.get((int)0).f_40221_);
        this.m_135408_(this.f_100271_.m_6635_(), this.f_100271_.m_6656_(), this.f_100271_.m_6636_(), p_100316_, p_100316_.m_7527_().iterator(), 0);
    }

    @Override
    public void m_5817_(Iterator<Ingredient> p_100338_, int p_100339_, int p_100340_, int p_100341_, int p_100342_) {
        Ingredient $$5 = p_100338_.next();
        if (!$$5.m_43947_()) {
            Slot $$6 = (Slot)this.f_100271_.f_38839_.get(p_100339_);
            this.f_100269_.m_100143_($$5, $$6.f_40220_, $$6.f_40221_);
        }
    }

    protected void m_100388_() {
        if (this.f_100272_.m_91403_() != null) {
            RecipeBookType $$0 = this.f_100271_.m_5867_();
            boolean $$1 = this.f_100283_.m_12684_().m_12734_($$0);
            boolean $$2 = this.f_100283_.m_12684_().m_12754_($$0);
            this.f_100272_.m_91403_().m_104955_(new ServerboundRecipeBookChangeSettingsPacket($$0, $$1, $$2));
        }
    }

    @Override
    public NarratableEntry.NarrationPriority m_142684_() {
        return this.f_170041_ ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_170046_) {
        ArrayList $$1 = Lists.newArrayList();
        this.f_100284_.m_170053_(p_170049_ -> {
            if (p_170049_.m_142518_()) {
                $$1.add(p_170049_);
            }
        });
        $$1.add(this.f_100281_);
        $$1.add(this.f_100270_);
        $$1.addAll(this.f_100279_);
        Screen.NarratableSearchResult $$2 = Screen.m_169400_($$1, null);
        if ($$2 != null) {
            $$2.f_169420_.m_142291_(p_170046_.m_142047_());
        }
    }

    private static /* synthetic */ boolean m_100332_(ObjectSet p_100333_, RecipeCollection p_100334_) {
        return !p_100333_.contains((Object)p_100334_);
    }
}

