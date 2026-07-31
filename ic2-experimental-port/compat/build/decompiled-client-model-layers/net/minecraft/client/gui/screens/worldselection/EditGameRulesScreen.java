/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.GameRules;

public class EditGameRulesScreen
extends Screen {
    private final Consumer<Optional<GameRules>> f_101044_;
    private RuleList f_101045_;
    private final Set<RuleEntry> f_101046_ = Sets.newHashSet();
    private Button f_101047_;
    @Nullable
    private List<FormattedCharSequence> f_101048_;
    private final GameRules f_101049_;

    public EditGameRulesScreen(GameRules p_101051_, Consumer<Optional<GameRules>> p_101052_) {
        super(Component.m_237115_("editGamerule.title"));
        this.f_101049_ = p_101051_;
        this.f_101044_ = p_101052_;
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        super.m_7856_();
        this.f_101045_ = new RuleList(this.f_101049_);
        this.m_7787_(this.f_101045_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ - 29, 150, 20, CommonComponents.f_130656_, p_101073_ -> this.f_101044_.accept(Optional.empty())));
        this.f_101047_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 29, 150, 20, CommonComponents.f_130655_, p_101059_ -> this.f_101044_.accept(Optional.of(this.f_101049_))));
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public void m_7379_() {
        this.f_101044_.accept(Optional.empty());
    }

    @Override
    public void m_6305_(PoseStack p_101054_, int p_101055_, int p_101056_, float p_101057_) {
        this.f_101048_ = null;
        this.f_101045_.m_6305_(p_101054_, p_101055_, p_101056_, p_101057_);
        EditGameRulesScreen.m_93215_(p_101054_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 0xFFFFFF);
        super.m_6305_(p_101054_, p_101055_, p_101056_, p_101057_);
        if (this.f_101048_ != null) {
            this.m_96617_(p_101054_, this.f_101048_, p_101055_, p_101056_);
        }
    }

    void m_101081_(@Nullable List<FormattedCharSequence> p_101082_) {
        this.f_101048_ = p_101082_;
    }

    private void m_101094_() {
        this.f_101047_.f_93623_ = this.f_101046_.isEmpty();
    }

    void m_101060_(RuleEntry p_101061_) {
        this.f_101046_.add(p_101061_);
        this.m_101094_();
    }

    void m_101074_(RuleEntry p_101075_) {
        this.f_101046_.remove(p_101075_);
        this.m_101094_();
    }

    public class RuleList
    extends ContainerObjectSelectionList<RuleEntry> {
        public RuleList(final GameRules p_101203_) {
            super(EditGameRulesScreen.this.f_96541_, EditGameRulesScreen.this.f_96543_, EditGameRulesScreen.this.f_96544_, 43, EditGameRulesScreen.this.f_96544_ - 32, 24);
            final HashMap $$2 = Maps.newHashMap();
            GameRules.m_46164_(new GameRules.GameRuleTypeVisitor(){

                @Override
                public void m_6891_(GameRules.Key<GameRules.BooleanValue> p_101238_, GameRules.Type<GameRules.BooleanValue> p_101239_) {
                    this.m_101224_(p_101238_, (p_101228_, p_101229_, p_101230_, p_101231_) -> new BooleanRuleEntry(p_101228_, p_101229_, p_101230_, (GameRules.BooleanValue)p_101231_));
                }

                @Override
                public void m_6894_(GameRules.Key<GameRules.IntegerValue> p_101241_, GameRules.Type<GameRules.IntegerValue> p_101242_) {
                    this.m_101224_(p_101241_, (p_101233_, p_101234_, p_101235_, p_101236_) -> new IntegerRuleEntry(p_101233_, p_101234_, p_101235_, (GameRules.IntegerValue)p_101236_));
                }

                private <T extends GameRules.Value<T>> void m_101224_(GameRules.Key<T> p_101225_, EntryFactory<T> p_101226_) {
                    String $$13;
                    ImmutableList $$12;
                    MutableComponent $$22 = Component.m_237115_(p_101225_.m_46331_());
                    MutableComponent $$3 = Component.m_237113_(p_101225_.m_46328_()).m_130940_(ChatFormatting.YELLOW);
                    T $$4 = p_101203_.m_46170_(p_101225_);
                    String $$5 = ((GameRules.Value)$$4).m_5831_();
                    MutableComponent $$6 = Component.m_237110_("editGamerule.default", Component.m_237113_($$5)).m_130940_(ChatFormatting.GRAY);
                    String $$7 = p_101225_.m_46331_() + ".description";
                    if (I18n.m_118936_($$7)) {
                        ImmutableList.Builder $$8 = ImmutableList.builder().add((Object)$$3.m_7532_());
                        MutableComponent $$9 = Component.m_237115_($$7);
                        EditGameRulesScreen.this.f_96547_.m_92923_($$9, 150).forEach(arg_0 -> ((ImmutableList.Builder)$$8).add(arg_0));
                        ImmutableList $$10 = $$8.add((Object)$$6.m_7532_()).build();
                        String $$11 = $$9.getString() + "\n" + $$6.getString();
                    } else {
                        $$12 = ImmutableList.of((Object)$$3.m_7532_(), (Object)$$6.m_7532_());
                        $$13 = $$6.getString();
                    }
                    $$2.computeIfAbsent(p_101225_.m_46332_(), p_101223_ -> Maps.newHashMap()).put(p_101225_, p_101226_.m_101154_($$22, (List<FormattedCharSequence>)$$12, $$13, $$4));
                }
            });
            $$2.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(p_101210_ -> {
                this.m_7085_(new CategoryRuleEntry(Component.m_237115_(((GameRules.Category)((Object)((Object)p_101210_.getKey()))).m_46274_()).m_130944_(ChatFormatting.BOLD, ChatFormatting.YELLOW)));
                ((Map)p_101210_.getValue()).entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(GameRules.Key::m_46328_))).forEach(p_170229_ -> this.m_7085_((RuleEntry)p_170229_.getValue()));
            });
        }

        @Override
        public void m_6305_(PoseStack p_101205_, int p_101206_, int p_101207_, float p_101208_) {
            super.m_6305_(p_101205_, p_101206_, p_101207_, p_101208_);
            RuleEntry $$4 = (RuleEntry)this.m_168795_();
            if ($$4 != null) {
                EditGameRulesScreen.this.m_101081_($$4.f_101193_);
            }
        }
    }

    public class IntegerRuleEntry
    extends GameRuleEntry {
        private final EditBox f_101172_;

        public IntegerRuleEntry(Component p_101175_, List<FormattedCharSequence> p_101176_, String p_101177_, GameRules.IntegerValue p_101178_) {
            super(p_101176_, p_101175_);
            this.f_101172_ = new EditBox(((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_, 10, 5, 42, 20, p_101175_.m_6881_().m_130946_("\n").m_130946_(p_101177_).m_130946_("\n"));
            this.f_101172_.m_94144_(Integer.toString(p_101178_.m_46288_()));
            this.f_101172_.m_94151_(p_101181_ -> {
                if (p_101178_.m_46314_((String)p_101181_)) {
                    this.f_101172_.m_94202_(0xE0E0E0);
                    EditGameRulesScreen.this.m_101074_(this);
                } else {
                    this.f_101172_.m_94202_(0xFF0000);
                    EditGameRulesScreen.this.m_101060_(this);
                }
            });
            this.f_101160_.add(this.f_101172_);
        }

        @Override
        public void m_6311_(PoseStack p_101183_, int p_101184_, int p_101185_, int p_101186_, int p_101187_, int p_101188_, int p_101189_, int p_101190_, boolean p_101191_, float p_101192_) {
            this.m_101166_(p_101183_, p_101185_, p_101186_);
            this.f_101172_.f_93620_ = p_101186_ + p_101187_ - 44;
            this.f_101172_.f_93621_ = p_101185_;
            this.f_101172_.m_6305_(p_101183_, p_101189_, p_101190_, p_101192_);
        }
    }

    public class BooleanRuleEntry
    extends GameRuleEntry {
        private final CycleButton<Boolean> f_101098_;

        public BooleanRuleEntry(Component p_101101_, List<FormattedCharSequence> p_101102_, String p_101103_, GameRules.BooleanValue p_101104_) {
            super(p_101102_, p_101101_);
            this.f_101098_ = CycleButton.m_168916_(p_101104_.m_46223_()).m_168929_().m_168959_(p_170219_ -> p_170219_.m_168904_().m_130946_("\n").m_130946_(p_101103_)).m_168936_(10, 5, 44, 20, p_101101_, (p_170215_, p_170216_) -> p_101104_.m_46246_((boolean)p_170216_, null));
            this.f_101160_.add(this.f_101098_);
        }

        @Override
        public void m_6311_(PoseStack p_101109_, int p_101110_, int p_101111_, int p_101112_, int p_101113_, int p_101114_, int p_101115_, int p_101116_, boolean p_101117_, float p_101118_) {
            this.m_101166_(p_101109_, p_101111_, p_101112_);
            this.f_101098_.f_93620_ = p_101112_ + p_101113_ - 45;
            this.f_101098_.f_93621_ = p_101111_;
            this.f_101098_.m_6305_(p_101109_, p_101115_, p_101116_, p_101118_);
        }
    }

    public abstract class GameRuleEntry
    extends RuleEntry {
        private final List<FormattedCharSequence> f_101159_;
        protected final List<AbstractWidget> f_101160_;

        public GameRuleEntry(List<FormattedCharSequence> p_101164_, Component p_101165_) {
            super(p_101164_);
            this.f_101160_ = Lists.newArrayList();
            this.f_101159_ = ((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_.m_92923_(p_101165_, 175);
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return this.f_101160_;
        }

        @Override
        public List<? extends NarratableEntry> m_142437_() {
            return this.f_101160_;
        }

        protected void m_101166_(PoseStack p_101167_, int p_101168_, int p_101169_) {
            if (this.f_101159_.size() == 1) {
                ((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_.m_92877_(p_101167_, this.f_101159_.get(0), p_101169_, p_101168_ + 5, 0xFFFFFF);
            } else if (this.f_101159_.size() >= 2) {
                ((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_.m_92877_(p_101167_, this.f_101159_.get(0), p_101169_, p_101168_, 0xFFFFFF);
                ((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_.m_92877_(p_101167_, this.f_101159_.get(1), p_101169_, p_101168_ + 10, 0xFFFFFF);
            }
        }
    }

    @FunctionalInterface
    static interface EntryFactory<T extends GameRules.Value<T>> {
        public RuleEntry m_101154_(Component var1, List<FormattedCharSequence> var2, String var3, T var4);
    }

    public class CategoryRuleEntry
    extends RuleEntry {
        final Component f_101138_;

        public CategoryRuleEntry(Component p_101141_) {
            super(null);
            this.f_101138_ = p_101141_;
        }

        @Override
        public void m_6311_(PoseStack p_101143_, int p_101144_, int p_101145_, int p_101146_, int p_101147_, int p_101148_, int p_101149_, int p_101150_, boolean p_101151_, float p_101152_) {
            GuiComponent.m_93215_(p_101143_, ((EditGameRulesScreen)EditGameRulesScreen.this).f_96541_.f_91062_, this.f_101138_, p_101146_ + p_101147_ / 2, p_101145_ + 5, 0xFFFFFF);
        }

        @Override
        public List<? extends GuiEventListener> m_6702_() {
            return ImmutableList.of();
        }

        @Override
        public List<? extends NarratableEntry> m_142437_() {
            return ImmutableList.of((Object)new NarratableEntry(){

                @Override
                public NarratableEntry.NarrationPriority m_142684_() {
                    return NarratableEntry.NarrationPriority.HOVERED;
                }

                @Override
                public void m_142291_(NarrationElementOutput p_170225_) {
                    p_170225_.m_169146_(NarratedElementType.TITLE, CategoryRuleEntry.this.f_101138_);
                }
            });
        }
    }

    public static abstract class RuleEntry
    extends ContainerObjectSelectionList.Entry<RuleEntry> {
        @Nullable
        final List<FormattedCharSequence> f_101193_;

        public RuleEntry(@Nullable List<FormattedCharSequence> p_194062_) {
            this.f_101193_ = p_194062_;
        }
    }
}

