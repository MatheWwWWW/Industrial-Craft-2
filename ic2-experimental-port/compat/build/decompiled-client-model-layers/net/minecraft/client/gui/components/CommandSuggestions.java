/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.context.CommandContextBuilder
 *  com.mojang.brigadier.context.ParsedArgument
 *  com.mojang.brigadier.context.ParsedCommandNode
 *  com.mojang.brigadier.context.StringRange
 *  com.mojang.brigadier.context.SuggestionContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class CommandSuggestions {
    private static final Pattern f_93847_ = Pattern.compile("(\\s+)");
    private static final Style f_93848_ = Style.f_131099_.m_131140_(ChatFormatting.RED);
    private static final Style f_93849_ = Style.f_131099_.m_131140_(ChatFormatting.GRAY);
    private static final List<Style> f_93850_ = (List)Stream.of(ChatFormatting.AQUA, ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.LIGHT_PURPLE, ChatFormatting.GOLD).map(Style.f_131099_::m_131140_).collect(ImmutableList.toImmutableList());
    final Minecraft f_93851_;
    final Screen f_93852_;
    final EditBox f_93853_;
    final Font f_93854_;
    private final boolean f_93855_;
    private final boolean f_93856_;
    final int f_93857_;
    final int f_93858_;
    final boolean f_93859_;
    final int f_93860_;
    private final List<FormattedCharSequence> f_93861_ = Lists.newArrayList();
    private int f_93862_;
    private int f_93863_;
    @Nullable
    private ParseResults<SharedSuggestionProvider> f_93864_;
    @Nullable
    private CompletableFuture<Suggestions> f_93865_;
    @Nullable
    private SuggestionsList f_93866_;
    private boolean f_93867_;
    boolean f_93868_;

    public CommandSuggestions(Minecraft p_93871_, Screen p_93872_, EditBox p_93873_, Font p_93874_, boolean p_93875_, boolean p_93876_, int p_93877_, int p_93878_, boolean p_93879_, int p_93880_) {
        this.f_93851_ = p_93871_;
        this.f_93852_ = p_93872_;
        this.f_93853_ = p_93873_;
        this.f_93854_ = p_93874_;
        this.f_93855_ = p_93875_;
        this.f_93856_ = p_93876_;
        this.f_93857_ = p_93877_;
        this.f_93858_ = p_93878_;
        this.f_93859_ = p_93879_;
        this.f_93860_ = p_93880_;
        p_93873_.m_94149_(this::m_93914_);
    }

    public void m_93922_(boolean p_93923_) {
        this.f_93867_ = p_93923_;
        if (!p_93923_) {
            this.f_93866_ = null;
        }
    }

    public boolean m_93888_(int p_93889_, int p_93890_, int p_93891_) {
        if (this.f_93866_ != null && this.f_93866_.m_93988_(p_93889_, p_93890_, p_93891_)) {
            return true;
        }
        if (this.f_93852_.m_7222_() == this.f_93853_ && p_93889_ == 258) {
            this.m_93930_(true);
            return true;
        }
        return false;
    }

    public boolean m_93882_(double p_93883_) {
        return this.f_93866_ != null && this.f_93866_.m_93971_(Mth.m_14008_(p_93883_, -1.0, 1.0));
    }

    public boolean m_93884_(double p_93885_, double p_93886_, int p_93887_) {
        return this.f_93866_ != null && this.f_93866_.m_93975_((int)p_93885_, (int)p_93886_, p_93887_);
    }

    public void m_93930_(boolean p_93931_) {
        Suggestions $$1;
        if (this.f_93865_ != null && this.f_93865_.isDone() && !($$1 = this.f_93865_.join()).isEmpty()) {
            int $$2 = 0;
            for (Suggestion $$3 : $$1.getList()) {
                $$2 = Math.max($$2, this.f_93854_.m_92895_($$3.getText()));
            }
            int $$4 = Mth.m_14045_(this.f_93853_.m_94211_($$1.getRange().getStart()), 0, this.f_93853_.m_94211_(0) + this.f_93853_.m_94210_() - $$2);
            int $$5 = this.f_93859_ ? this.f_93852_.f_96544_ - 12 : 72;
            this.f_93866_ = new SuggestionsList($$4, $$5, $$2, this.m_93898_($$1), p_93931_);
        }
    }

    public void m_241889_() {
        this.f_93866_ = null;
    }

    private List<Suggestion> m_93898_(Suggestions p_93899_) {
        String $$1 = this.f_93853_.m_94155_().substring(0, this.f_93853_.m_94207_());
        int $$2 = CommandSuggestions.m_93912_($$1);
        String $$3 = $$1.substring($$2).toLowerCase(Locale.ROOT);
        ArrayList $$4 = Lists.newArrayList();
        ArrayList $$5 = Lists.newArrayList();
        for (Suggestion $$6 : p_93899_.getList()) {
            if ($$6.getText().startsWith($$3) || $$6.getText().startsWith("minecraft:" + $$3)) {
                $$4.add($$6);
                continue;
            }
            $$5.add($$6);
        }
        $$4.addAll($$5);
        return $$4;
    }

    public void m_93881_() {
        boolean $$2;
        String $$0 = this.f_93853_.m_94155_();
        if (this.f_93864_ != null && !this.f_93864_.getReader().getString().equals($$0)) {
            this.f_93864_ = null;
        }
        if (!this.f_93868_) {
            this.f_93853_.m_94167_(null);
            this.f_93866_ = null;
        }
        this.f_93861_.clear();
        StringReader $$1 = new StringReader($$0);
        boolean bl = $$2 = $$1.canRead() && $$1.peek() == '/';
        if ($$2) {
            $$1.skip();
        }
        boolean $$3 = this.f_93855_ || $$2;
        int $$4 = this.f_93853_.m_94207_();
        if ($$3) {
            int $$6;
            CommandDispatcher<SharedSuggestionProvider> $$5 = this.f_93851_.f_91074_.f_108617_.m_105146_();
            if (this.f_93864_ == null) {
                this.f_93864_ = $$5.parse($$1, (Object)this.f_93851_.f_91074_.f_108617_.m_105137_());
            }
            int n = $$6 = this.f_93856_ ? $$1.getCursor() : 1;
            if (!($$4 < $$6 || this.f_93866_ != null && this.f_93868_)) {
                this.f_93865_ = $$5.getCompletionSuggestions(this.f_93864_, $$4);
                this.f_93865_.thenRun(() -> {
                    if (!this.f_93865_.isDone()) {
                        return;
                    }
                    this.m_93932_();
                });
            }
        } else {
            String $$7 = $$0.substring(0, $$4);
            int $$8 = CommandSuggestions.m_93912_($$7);
            Collection<String> $$9 = this.f_93851_.f_91074_.f_108617_.m_105137_().m_240700_();
            this.f_93865_ = SharedSuggestionProvider.m_82970_($$9, new SuggestionsBuilder($$7, $$8));
        }
    }

    private static int m_93912_(String p_93913_) {
        if (Strings.isNullOrEmpty((String)p_93913_)) {
            return 0;
        }
        int $$1 = 0;
        Matcher $$2 = f_93847_.matcher(p_93913_);
        while ($$2.find()) {
            $$1 = $$2.end();
        }
        return $$1;
    }

    private static FormattedCharSequence m_93896_(CommandSyntaxException p_93897_) {
        Component $$1 = ComponentUtils.m_130729_(p_93897_.getRawMessage());
        String $$2 = p_93897_.getContext();
        if ($$2 == null) {
            return $$1.m_7532_();
        }
        return Component.m_237110_("command.context.parse_error", $$1, p_93897_.getCursor(), $$2).m_7532_();
    }

    private void m_93932_() {
        if (this.f_93853_.m_94207_() == this.f_93853_.m_94155_().length()) {
            if (this.f_93865_.join().isEmpty() && !this.f_93864_.getExceptions().isEmpty()) {
                int $$0 = 0;
                for (Map.Entry $$1 : this.f_93864_.getExceptions().entrySet()) {
                    CommandSyntaxException $$2 = (CommandSyntaxException)((Object)$$1.getValue());
                    if ($$2.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                        ++$$0;
                        continue;
                    }
                    this.f_93861_.add(CommandSuggestions.m_93896_($$2));
                }
                if ($$0 > 0) {
                    this.f_93861_.add(CommandSuggestions.m_93896_(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
                }
            } else if (this.f_93864_.getReader().canRead()) {
                this.f_93861_.add(CommandSuggestions.m_93896_(Commands.m_82097_(this.f_93864_)));
            }
        }
        this.f_93862_ = 0;
        this.f_93863_ = this.f_93852_.f_96543_;
        if (this.f_93861_.isEmpty()) {
            this.m_93920_(ChatFormatting.GRAY);
        }
        this.f_93866_ = null;
        if (this.f_93867_ && this.f_93851_.f_91066_.m_231813_().m_231551_().booleanValue()) {
            this.m_93930_(false);
        }
    }

    private void m_93920_(ChatFormatting p_93921_) {
        CommandContextBuilder $$1 = this.f_93864_.getContext();
        SuggestionContext $$2 = $$1.findSuggestionContext(this.f_93853_.m_94207_());
        Map $$3 = this.f_93851_.f_91074_.f_108617_.m_105146_().getSmartUsage($$2.parent, (Object)this.f_93851_.f_91074_.f_108617_.m_105137_());
        ArrayList $$4 = Lists.newArrayList();
        int $$5 = 0;
        Style $$6 = Style.f_131099_.m_131140_(p_93921_);
        for (Map.Entry $$7 : $$3.entrySet()) {
            if ($$7.getKey() instanceof LiteralCommandNode) continue;
            $$4.add(FormattedCharSequence.m_13714_((String)$$7.getValue(), $$6));
            $$5 = Math.max($$5, this.f_93854_.m_92895_((String)$$7.getValue()));
        }
        if (!$$4.isEmpty()) {
            this.f_93861_.addAll($$4);
            this.f_93862_ = Mth.m_14045_(this.f_93853_.m_94211_($$2.startPos), 0, this.f_93853_.m_94211_(0) + this.f_93853_.m_94210_() - $$5);
            this.f_93863_ = $$5;
        }
    }

    private FormattedCharSequence m_93914_(String p_93915_, int p_93916_) {
        if (this.f_93864_ != null) {
            return CommandSuggestions.m_93892_(this.f_93864_, p_93915_, p_93916_);
        }
        return FormattedCharSequence.m_13714_(p_93915_, Style.f_131099_);
    }

    @Nullable
    static String m_93927_(String p_93928_, String p_93929_) {
        if (p_93929_.startsWith(p_93928_)) {
            return p_93929_.substring(p_93928_.length());
        }
        return null;
    }

    private static FormattedCharSequence m_93892_(ParseResults<SharedSuggestionProvider> p_93893_, String p_93894_, int p_93895_) {
        int $$10;
        ArrayList $$3 = Lists.newArrayList();
        int $$4 = 0;
        int $$5 = -1;
        CommandContextBuilder $$6 = p_93893_.getContext().getLastChild();
        for (ParsedArgument $$7 : $$6.getArguments().values()) {
            int $$8;
            if (++$$5 >= f_93850_.size()) {
                $$5 = 0;
            }
            if (($$8 = Math.max($$7.getRange().getStart() - p_93895_, 0)) >= p_93894_.length()) break;
            int $$9 = Math.min($$7.getRange().getEnd() - p_93895_, p_93894_.length());
            if ($$9 <= 0) continue;
            $$3.add(FormattedCharSequence.m_13714_(p_93894_.substring($$4, $$8), f_93849_));
            $$3.add(FormattedCharSequence.m_13714_(p_93894_.substring($$8, $$9), f_93850_.get($$5)));
            $$4 = $$9;
        }
        if (p_93893_.getReader().canRead() && ($$10 = Math.max(p_93893_.getReader().getCursor() - p_93895_, 0)) < p_93894_.length()) {
            int $$11 = Math.min($$10 + p_93893_.getReader().getRemainingLength(), p_93894_.length());
            $$3.add(FormattedCharSequence.m_13714_(p_93894_.substring($$4, $$10), f_93849_));
            $$3.add(FormattedCharSequence.m_13714_(p_93894_.substring($$10, $$11), f_93848_));
            $$4 = $$11;
        }
        $$3.add(FormattedCharSequence.m_13714_(p_93894_.substring($$4), f_93849_));
        return FormattedCharSequence.m_13722_($$3);
    }

    public void m_93900_(PoseStack p_93901_, int p_93902_, int p_93903_) {
        if (!this.m_241972_(p_93901_, p_93902_, p_93903_)) {
            this.m_242028_(p_93901_);
        }
    }

    public boolean m_241972_(PoseStack p_242180_, int p_242347_, int p_242469_) {
        if (this.f_93866_ != null) {
            this.f_93866_.m_93979_(p_242180_, p_242347_, p_242469_);
            return true;
        }
        return false;
    }

    public void m_242028_(PoseStack p_242357_) {
        int $$1 = 0;
        for (FormattedCharSequence $$2 : this.f_93861_) {
            int $$3 = this.f_93859_ ? this.f_93852_.f_96544_ - 14 - 13 - 12 * $$1 : 72 + 12 * $$1;
            GuiComponent.m_93172_(p_242357_, this.f_93862_ - 1, $$3, this.f_93862_ + this.f_93863_ + 1, $$3 + 12, this.f_93860_);
            this.f_93854_.m_92744_(p_242357_, $$2, this.f_93862_, $$3 + 2, -1);
            ++$$1;
        }
    }

    public String m_93924_() {
        if (this.f_93866_ != null) {
            return "\n" + this.f_93866_.m_168847_();
        }
        return "";
    }

    @Nullable
    public CommandNode<SharedSuggestionProvider> m_232478_(int p_232479_) {
        return this.f_93864_ != null ? CommandSuggestions.m_232480_(p_232479_, this.f_93864_.getContext()) : null;
    }

    @Nullable
    public ParseResults<SharedSuggestionProvider> m_242637_() {
        return this.f_93864_;
    }

    @Nullable
    private static <S> CommandNode<S> m_232480_(int p_232481_, CommandContextBuilder<S> p_232482_) {
        StringRange $$2 = p_232482_.getRange();
        if (p_232481_ < $$2.getStart()) {
            return null;
        }
        List $$3 = p_232482_.getNodes();
        if (p_232481_ <= $$2.getEnd()) {
            for (ParsedCommandNode $$4 : $$3) {
                StringRange $$5 = $$4.getRange();
                if (p_232481_ < $$5.getStart() || p_232481_ > $$5.getEnd()) continue;
                return $$4.getNode();
            }
        } else {
            if (p_232482_.getChild() != null) {
                return CommandSuggestions.m_232480_(p_232481_, p_232482_.getChild());
            }
            if (!$$3.isEmpty()) {
                ParsedCommandNode $$6 = (ParsedCommandNode)$$3.get($$3.size() - 1);
                return $$6.getNode();
            }
        }
        return p_232482_.getRootNode();
    }

    public class SuggestionsList {
        private final Rect2i f_93947_;
        private final String f_93948_;
        private final List<Suggestion> f_93949_;
        private int f_93950_;
        private int f_93951_;
        private Vec2 f_93952_ = Vec2.f_82462_;
        private boolean f_93953_;
        private int f_93954_;

        SuggestionsList(int p_93957_, int p_93958_, int p_93959_, List<Suggestion> p_93960_, boolean p_93961_) {
            int $$6 = p_93957_ - 1;
            int $$7 = CommandSuggestions.this.f_93859_ ? p_93958_ - 3 - Math.min(p_93960_.size(), CommandSuggestions.this.f_93858_) * 12 : p_93958_;
            this.f_93947_ = new Rect2i($$6, $$7, p_93959_ + 1, Math.min(p_93960_.size(), CommandSuggestions.this.f_93858_) * 12);
            this.f_93948_ = CommandSuggestions.this.f_93853_.m_94155_();
            this.f_93954_ = p_93961_ ? -1 : 0;
            this.f_93949_ = p_93960_;
            this.m_93986_(0);
        }

        public void m_93979_(PoseStack p_93980_, int p_93981_, int p_93982_) {
            Message $$14;
            boolean $$8;
            int $$3 = Math.min(this.f_93949_.size(), CommandSuggestions.this.f_93858_);
            int $$4 = -5592406;
            boolean $$5 = this.f_93950_ > 0;
            boolean $$6 = this.f_93949_.size() > this.f_93950_ + $$3;
            boolean $$7 = $$5 || $$6;
            boolean bl = $$8 = this.f_93952_.f_82470_ != (float)p_93981_ || this.f_93952_.f_82471_ != (float)p_93982_;
            if ($$8) {
                this.f_93952_ = new Vec2(p_93981_, p_93982_);
            }
            if ($$7) {
                GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() - 1, this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_(), CommandSuggestions.this.f_93860_);
                GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() + this.f_93947_.m_110091_(), this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_() + this.f_93947_.m_110091_() + 1, CommandSuggestions.this.f_93860_);
                if ($$5) {
                    for (int $$9 = 0; $$9 < this.f_93947_.m_110090_(); ++$$9) {
                        if ($$9 % 2 != 0) continue;
                        GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_() + $$9, this.f_93947_.m_110086_() - 1, this.f_93947_.m_110085_() + $$9 + 1, this.f_93947_.m_110086_(), -1);
                    }
                }
                if ($$6) {
                    for (int $$10 = 0; $$10 < this.f_93947_.m_110090_(); ++$$10) {
                        if ($$10 % 2 != 0) continue;
                        GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_() + $$10, this.f_93947_.m_110086_() + this.f_93947_.m_110091_(), this.f_93947_.m_110085_() + $$10 + 1, this.f_93947_.m_110086_() + this.f_93947_.m_110091_() + 1, -1);
                    }
                }
            }
            boolean $$11 = false;
            for (int $$12 = 0; $$12 < $$3; ++$$12) {
                Suggestion $$13 = this.f_93949_.get($$12 + this.f_93950_);
                GuiComponent.m_93172_(p_93980_, this.f_93947_.m_110085_(), this.f_93947_.m_110086_() + 12 * $$12, this.f_93947_.m_110085_() + this.f_93947_.m_110090_(), this.f_93947_.m_110086_() + 12 * $$12 + 12, CommandSuggestions.this.f_93860_);
                if (p_93981_ > this.f_93947_.m_110085_() && p_93981_ < this.f_93947_.m_110085_() + this.f_93947_.m_110090_() && p_93982_ > this.f_93947_.m_110086_() + 12 * $$12 && p_93982_ < this.f_93947_.m_110086_() + 12 * $$12 + 12) {
                    if ($$8) {
                        this.m_93986_($$12 + this.f_93950_);
                    }
                    $$11 = true;
                }
                CommandSuggestions.this.f_93854_.m_92750_(p_93980_, $$13.getText(), this.f_93947_.m_110085_() + 1, this.f_93947_.m_110086_() + 2 + 12 * $$12, $$12 + this.f_93950_ == this.f_93951_ ? -256 : -5592406);
            }
            if ($$11 && ($$14 = this.f_93949_.get(this.f_93951_).getTooltip()) != null) {
                CommandSuggestions.this.f_93852_.m_96602_(p_93980_, ComponentUtils.m_130729_($$14), p_93981_, p_93982_);
            }
        }

        public boolean m_93975_(int p_93976_, int p_93977_, int p_93978_) {
            if (!this.f_93947_.m_110087_(p_93976_, p_93977_)) {
                return false;
            }
            int $$3 = (p_93977_ - this.f_93947_.m_110086_()) / 12 + this.f_93950_;
            if ($$3 >= 0 && $$3 < this.f_93949_.size()) {
                this.m_93986_($$3);
                this.m_93970_();
            }
            return true;
        }

        public boolean m_93971_(double p_93972_) {
            int $$2;
            int $$1 = (int)(CommandSuggestions.this.f_93851_.f_91067_.m_91589_() * (double)CommandSuggestions.this.f_93851_.m_91268_().m_85445_() / (double)CommandSuggestions.this.f_93851_.m_91268_().m_85443_());
            if (this.f_93947_.m_110087_($$1, $$2 = (int)(CommandSuggestions.this.f_93851_.f_91067_.m_91594_() * (double)CommandSuggestions.this.f_93851_.m_91268_().m_85446_() / (double)CommandSuggestions.this.f_93851_.m_91268_().m_85444_()))) {
                this.f_93950_ = Mth.m_14045_((int)((double)this.f_93950_ - p_93972_), 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
                return true;
            }
            return false;
        }

        public boolean m_93988_(int p_93989_, int p_93990_, int p_93991_) {
            if (p_93989_ == 265) {
                this.m_93973_(-1);
                this.f_93953_ = false;
                return true;
            }
            if (p_93989_ == 264) {
                this.m_93973_(1);
                this.f_93953_ = false;
                return true;
            }
            if (p_93989_ == 258) {
                if (this.f_93953_) {
                    this.m_93973_(Screen.m_96638_() ? -1 : 1);
                }
                this.m_93970_();
                return true;
            }
            if (p_93989_ == 256) {
                CommandSuggestions.this.m_241889_();
                return true;
            }
            return false;
        }

        public void m_93973_(int p_93974_) {
            this.m_93986_(this.f_93951_ + p_93974_);
            int $$1 = this.f_93950_;
            int $$2 = this.f_93950_ + CommandSuggestions.this.f_93858_ - 1;
            if (this.f_93951_ < $$1) {
                this.f_93950_ = Mth.m_14045_(this.f_93951_, 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
            } else if (this.f_93951_ > $$2) {
                this.f_93950_ = Mth.m_14045_(this.f_93951_ + CommandSuggestions.this.f_93857_ - CommandSuggestions.this.f_93858_, 0, Math.max(this.f_93949_.size() - CommandSuggestions.this.f_93858_, 0));
            }
        }

        public void m_93986_(int p_93987_) {
            this.f_93951_ = p_93987_;
            if (this.f_93951_ < 0) {
                this.f_93951_ += this.f_93949_.size();
            }
            if (this.f_93951_ >= this.f_93949_.size()) {
                this.f_93951_ -= this.f_93949_.size();
            }
            Suggestion $$1 = this.f_93949_.get(this.f_93951_);
            CommandSuggestions.this.f_93853_.m_94167_(CommandSuggestions.m_93927_(CommandSuggestions.this.f_93853_.m_94155_(), $$1.apply(this.f_93948_)));
            if (this.f_93954_ != this.f_93951_) {
                CommandSuggestions.this.f_93851_.m_240477_().m_168785_(this.m_168847_());
            }
        }

        public void m_93970_() {
            Suggestion $$0 = this.f_93949_.get(this.f_93951_);
            CommandSuggestions.this.f_93868_ = true;
            CommandSuggestions.this.f_93853_.m_94144_($$0.apply(this.f_93948_));
            int $$1 = $$0.getRange().getStart() + $$0.getText().length();
            CommandSuggestions.this.f_93853_.m_94196_($$1);
            CommandSuggestions.this.f_93853_.m_94208_($$1);
            this.m_93986_(this.f_93951_);
            CommandSuggestions.this.f_93868_ = false;
            this.f_93953_ = true;
        }

        Component m_168847_() {
            this.f_93954_ = this.f_93951_;
            Suggestion $$0 = this.f_93949_.get(this.f_93951_);
            Message $$1 = $$0.getTooltip();
            if ($$1 != null) {
                return Component.m_237110_("narration.suggestion.tooltip", this.f_93951_ + 1, this.f_93949_.size(), $$0.getText(), $$1);
            }
            return Component.m_237110_("narration.suggestion", this.f_93951_ + 1, this.f_93949_.size(), $$0.getText());
        }
    }
}

