/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.context.CommandContextBuilder
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.RootCommandNode
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.commands;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mojang.logging.LogUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.RegistryAccess;
import net.minecraft.gametest.framework.TestCommand;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.server.commands.AdvancementCommands;
import net.minecraft.server.commands.AttributeCommand;
import net.minecraft.server.commands.BanIpCommands;
import net.minecraft.server.commands.BanListCommands;
import net.minecraft.server.commands.BanPlayerCommands;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.server.commands.ClearInventoryCommands;
import net.minecraft.server.commands.CloneCommands;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.commands.DeOpCommands;
import net.minecraft.server.commands.DebugCommand;
import net.minecraft.server.commands.DefaultGameModeCommands;
import net.minecraft.server.commands.DifficultyCommand;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.commands.EmoteCommands;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.server.commands.ForceLoadCommand;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.server.commands.GameModeCommand;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.commands.HelpCommand;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.server.commands.JfrCommand;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.commands.ListPlayersCommand;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.commands.OpCommand;
import net.minecraft.server.commands.PardonCommand;
import net.minecraft.server.commands.PardonIpCommand;
import net.minecraft.server.commands.ParticleCommand;
import net.minecraft.server.commands.PerfCommand;
import net.minecraft.server.commands.PlaceCommand;
import net.minecraft.server.commands.PlaySoundCommand;
import net.minecraft.server.commands.PublishCommand;
import net.minecraft.server.commands.RecipeCommand;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.commands.SaveAllCommand;
import net.minecraft.server.commands.SaveOffCommand;
import net.minecraft.server.commands.SaveOnCommand;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.server.commands.ScheduleCommand;
import net.minecraft.server.commands.ScoreboardCommand;
import net.minecraft.server.commands.SeedCommand;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.SetPlayerIdleTimeoutCommand;
import net.minecraft.server.commands.SetSpawnCommand;
import net.minecraft.server.commands.SetWorldSpawnCommand;
import net.minecraft.server.commands.SpectateCommand;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.commands.StopCommand;
import net.minecraft.server.commands.StopSoundCommand;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.TagCommand;
import net.minecraft.server.commands.TeamCommand;
import net.minecraft.server.commands.TeamMsgCommand;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.commands.TellRawCommand;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.server.commands.TriggerCommand;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.commands.WhitelistCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class Commands {
    private static final Logger f_82089_ = LogUtils.getLogger();
    public static final int f_165682_ = 0;
    public static final int f_165683_ = 1;
    public static final int f_165684_ = 2;
    public static final int f_165685_ = 3;
    public static final int f_165686_ = 4;
    private final CommandDispatcher<CommandSourceStack> f_82090_ = new CommandDispatcher();

    public Commands(CommandSelection p_230943_, CommandBuildContext p_230944_) {
        AdvancementCommands.m_136310_(this.f_82090_);
        AttributeCommand.m_136444_(this.f_82090_);
        ExecuteCommand.m_214434_(this.f_82090_, p_230944_);
        BossBarCommands.m_136582_(this.f_82090_);
        ClearInventoryCommands.m_214420_(this.f_82090_, p_230944_);
        CloneCommands.m_214423_(this.f_82090_, p_230944_);
        DataCommands.m_139365_(this.f_82090_);
        DataPackCommand.m_136808_(this.f_82090_);
        DebugCommand.m_136905_(this.f_82090_);
        DefaultGameModeCommands.m_136926_(this.f_82090_);
        DifficultyCommand.m_136938_(this.f_82090_);
        EffectCommands.m_136953_(this.f_82090_);
        EmoteCommands.m_136985_(this.f_82090_);
        EnchantCommand.m_137008_(this.f_82090_);
        ExperienceCommand.m_137306_(this.f_82090_);
        FillCommand.m_214442_(this.f_82090_, p_230944_);
        ForceLoadCommand.m_137676_(this.f_82090_);
        FunctionCommand.m_137714_(this.f_82090_);
        GameModeCommand.m_137729_(this.f_82090_);
        GameRuleCommand.m_137744_(this.f_82090_);
        GiveCommand.m_214445_(this.f_82090_, p_230944_);
        HelpCommand.m_137787_(this.f_82090_);
        ItemCommands.m_214448_(this.f_82090_, p_230944_);
        KickCommand.m_137795_(this.f_82090_);
        KillCommand.m_137807_(this.f_82090_);
        ListPlayersCommand.m_137820_(this.f_82090_);
        LocateCommand.m_137858_(this.f_82090_);
        LootCommand.m_214515_(this.f_82090_, p_230944_);
        MsgCommand.m_138060_(this.f_82090_);
        ParticleCommand.m_138122_(this.f_82090_);
        PlaceCommand.m_214547_(this.f_82090_);
        PlaySoundCommand.m_138156_(this.f_82090_);
        ReloadCommand.m_138226_(this.f_82090_);
        RecipeCommand.m_138200_(this.f_82090_);
        SayCommand.m_138409_(this.f_82090_);
        ScheduleCommand.m_138419_(this.f_82090_);
        ScoreboardCommand.m_138468_(this.f_82090_);
        SeedCommand.m_138589_(this.f_82090_, p_230943_ != CommandSelection.INTEGRATED);
        SetBlockCommand.m_214730_(this.f_82090_, p_230944_);
        SetSpawnCommand.m_138643_(this.f_82090_);
        SetWorldSpawnCommand.m_138660_(this.f_82090_);
        SpectateCommand.m_138677_(this.f_82090_);
        SpreadPlayersCommand.m_138696_(this.f_82090_);
        StopSoundCommand.m_138794_(this.f_82090_);
        SummonCommand.m_138814_(this.f_82090_);
        TagCommand.m_138836_(this.f_82090_);
        TeamCommand.m_138877_(this.f_82090_);
        TeamMsgCommand.m_138999_(this.f_82090_);
        TeleportCommand.m_139008_(this.f_82090_);
        TellRawCommand.m_139063_(this.f_82090_);
        TimeCommand.m_139071_(this.f_82090_);
        TitleCommand.m_139102_(this.f_82090_);
        TriggerCommand.m_139141_(this.f_82090_);
        WeatherCommand.m_139166_(this.f_82090_);
        WorldBorderCommand.m_139246_(this.f_82090_);
        if (JvmProfiler.f_185340_.m_183609_()) {
            JfrCommand.m_183645_(this.f_82090_);
        }
        if (SharedConstants.f_136183_) {
            TestCommand.m_127946_(this.f_82090_);
        }
        if (p_230943_.f_82145_) {
            BanIpCommands.m_136527_(this.f_82090_);
            BanListCommands.m_136543_(this.f_82090_);
            BanPlayerCommands.m_136558_(this.f_82090_);
            DeOpCommands.m_136888_(this.f_82090_);
            OpCommand.m_138079_(this.f_82090_);
            PardonCommand.m_138093_(this.f_82090_);
            PardonIpCommand.m_138108_(this.f_82090_);
            PerfCommand.m_180437_(this.f_82090_);
            SaveAllCommand.m_138271_(this.f_82090_);
            SaveOffCommand.m_138284_(this.f_82090_);
            SaveOnCommand.m_138292_(this.f_82090_);
            SetPlayerIdleTimeoutCommand.m_138634_(this.f_82090_);
            StopCommand.m_138785_(this.f_82090_);
            WhitelistCommand.m_139201_(this.f_82090_);
        }
        if (p_230943_.f_82144_) {
            PublishCommand.m_138184_(this.f_82090_);
        }
        this.f_82090_.setConsumer((p_230954_, p_230955_, p_230956_) -> ((CommandSourceStack)p_230954_.getSource()).m_81342_((CommandContext<CommandSourceStack>)p_230954_, p_230955_, p_230956_));
    }

    public static <S> ParseResults<S> m_242611_(ParseResults<S> p_242928_, UnaryOperator<S> p_242890_) {
        CommandContextBuilder $$2 = p_242928_.getContext();
        CommandContextBuilder $$3 = $$2.withSource(p_242890_.apply($$2.getSource()));
        return new ParseResults($$3, p_242928_.getReader(), p_242928_.getExceptions());
    }

    public int m_230957_(CommandSourceStack p_230958_, String p_230959_) {
        p_230959_ = p_230959_.startsWith("/") ? p_230959_.substring(1) : p_230959_;
        return this.m_242674_((ParseResults<CommandSourceStack>)this.f_82090_.parse(p_230959_, (Object)p_230958_), p_230959_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int m_242674_(ParseResults<CommandSourceStack> p_242844_, String p_242841_) {
        CommandSourceStack $$2 = (CommandSourceStack)p_242844_.getContext().getSource();
        $$2.m_81377_().m_129905_().m_6521_(() -> "/" + p_242841_);
        try {
            int n = this.f_82090_.execute(p_242844_);
            return n;
        }
        catch (CommandRuntimeException $$3) {
            $$2.m_81352_($$3.m_79226_());
            int n = 0;
            return n;
        }
        catch (CommandSyntaxException $$4) {
            int $$5;
            $$2.m_81352_(ComponentUtils.m_130729_($$4.getRawMessage()));
            if ($$4.getInput() != null && $$4.getCursor() >= 0) {
                $$5 = Math.min($$4.getInput().length(), $$4.getCursor());
                MutableComponent $$6 = Component.m_237119_().m_130940_(ChatFormatting.GRAY).m_130938_(p_82134_ -> p_82134_.m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + p_242841_)));
                if ($$5 > 10) {
                    $$6.m_7220_(CommonComponents.f_238772_);
                }
                $$6.m_130946_($$4.getInput().substring(Math.max(0, $$5 - 10), $$5));
                if ($$5 < $$4.getInput().length()) {
                    MutableComponent $$7 = Component.m_237113_($$4.getInput().substring($$5)).m_130944_(ChatFormatting.RED, ChatFormatting.UNDERLINE);
                    $$6.m_7220_($$7);
                }
                $$6.m_7220_(Component.m_237115_("command.context.here").m_130944_(ChatFormatting.RED, ChatFormatting.ITALIC));
                $$2.m_81352_($$6);
            }
            $$5 = 0;
            return $$5;
        }
        catch (Exception $$8) {
            MutableComponent $$9 = Component.m_237113_($$8.getMessage() == null ? $$8.getClass().getName() : $$8.getMessage());
            if (f_82089_.isDebugEnabled()) {
                f_82089_.error("Command exception: /{}", (Object)p_242841_, (Object)$$8);
                StackTraceElement[] $$10 = $$8.getStackTrace();
                for (int $$11 = 0; $$11 < Math.min($$10.length, 3); ++$$11) {
                    $$9.m_130946_("\n\n").m_130946_($$10[$$11].getMethodName()).m_130946_("\n ").m_130946_($$10[$$11].getFileName()).m_130946_(":").m_130946_(String.valueOf($$10[$$11].getLineNumber()));
                }
            }
            $$2.m_81352_(Component.m_237115_("command.failed").m_130938_(p_82137_ -> p_82137_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, $$9))));
            if (SharedConstants.f_136183_) {
                $$2.m_81352_(Component.m_237113_(Util.m_137575_($$8)));
                f_82089_.error("'/{}' threw an exception", (Object)p_242841_, (Object)$$8);
            }
            int n = 0;
            return n;
        }
        finally {
            $$2.m_81377_().m_129905_().m_7238_();
        }
    }

    public void m_82095_(ServerPlayer p_82096_) {
        HashMap $$1 = Maps.newHashMap();
        RootCommandNode $$2 = new RootCommandNode();
        $$1.put(this.f_82090_.getRoot(), $$2);
        this.m_82112_((CommandNode<CommandSourceStack>)this.f_82090_.getRoot(), (CommandNode<SharedSuggestionProvider>)$$2, p_82096_.m_20203_(), $$1);
        p_82096_.f_8906_.m_9829_(new ClientboundCommandsPacket((RootCommandNode<SharedSuggestionProvider>)$$2));
    }

    private void m_82112_(CommandNode<CommandSourceStack> p_82113_, CommandNode<SharedSuggestionProvider> p_82114_, CommandSourceStack p_82115_, Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> p_82116_) {
        for (CommandNode $$4 : p_82113_.getChildren()) {
            RequiredArgumentBuilder $$6;
            if (!$$4.canUse((Object)p_82115_)) continue;
            ArgumentBuilder $$5 = $$4.createBuilder();
            $$5.requires(p_82126_ -> true);
            if ($$5.getCommand() != null) {
                $$5.executes(p_82102_ -> 0);
            }
            if ($$5 instanceof RequiredArgumentBuilder && ($$6 = (RequiredArgumentBuilder)$$5).getSuggestionsProvider() != null) {
                $$6.suggests(SuggestionProviders.m_121664_((SuggestionProvider<SharedSuggestionProvider>)$$6.getSuggestionsProvider()));
            }
            if ($$5.getRedirect() != null) {
                $$5.redirect(p_82116_.get($$5.getRedirect()));
            }
            CommandNode $$7 = $$5.build();
            p_82116_.put((CommandNode<CommandSourceStack>)$$4, (CommandNode<SharedSuggestionProvider>)$$7);
            p_82114_.addChild($$7);
            if ($$4.getChildren().isEmpty()) continue;
            this.m_82112_((CommandNode<CommandSourceStack>)$$4, (CommandNode<SharedSuggestionProvider>)$$7, p_82115_, p_82116_);
        }
    }

    public static LiteralArgumentBuilder<CommandSourceStack> m_82127_(String p_82128_) {
        return LiteralArgumentBuilder.literal((String)p_82128_);
    }

    public static <T> RequiredArgumentBuilder<CommandSourceStack, T> m_82129_(String p_82130_, ArgumentType<T> p_82131_) {
        return RequiredArgumentBuilder.argument((String)p_82130_, p_82131_);
    }

    public static Predicate<String> m_82120_(ParseFunction p_82121_) {
        return p_82124_ -> {
            try {
                p_82121_.m_82160_(new StringReader(p_82124_));
                return true;
            }
            catch (CommandSyntaxException $$2) {
                return false;
            }
        };
    }

    public CommandDispatcher<CommandSourceStack> m_82094_() {
        return this.f_82090_;
    }

    @Nullable
    public static <S> CommandSyntaxException m_82097_(ParseResults<S> p_82098_) {
        if (!p_82098_.getReader().canRead()) {
            return null;
        }
        if (p_82098_.getExceptions().size() == 1) {
            return (CommandSyntaxException)((Object)p_82098_.getExceptions().values().iterator().next());
        }
        if (p_82098_.getContext().getRange().isEmpty()) {
            return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(p_82098_.getReader());
        }
        return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(p_82098_.getReader());
    }

    public static void m_82138_() {
        CommandBuildContext $$0 = new CommandBuildContext(RegistryAccess.f_123049_.get());
        $$0.m_227135_(CommandBuildContext.MissingTagAccessPolicy.RETURN_EMPTY);
        CommandDispatcher<CommandSourceStack> $$1 = new Commands(CommandSelection.ALL, $$0).m_82094_();
        RootCommandNode $$2 = $$1.getRoot();
        $$1.findAmbiguities((p_230947_, p_230948_, p_230949_, p_230950_) -> f_82089_.warn("Ambiguity between arguments {} and {} with inputs: {}", new Object[]{$$1.getPath(p_230948_), $$1.getPath(p_230949_), p_230950_}));
        Set<ArgumentType<?>> $$3 = ArgumentUtils.m_235417_($$2);
        Set $$4 = $$3.stream().filter(p_230961_ -> !ArgumentTypeInfos.m_235391_(p_230961_.getClass())).collect(Collectors.toSet());
        if (!$$4.isEmpty()) {
            f_82089_.warn("Missing type registration for following arguments:\n {}", (Object)$$4.stream().map(p_230952_ -> "\t" + p_230952_).collect(Collectors.joining(",\n")));
            throw new IllegalStateException("Unregistered argument types");
        }
    }

    public static final class CommandSelection
    extends Enum<CommandSelection> {
        public static final /* enum */ CommandSelection ALL = new CommandSelection(true, true);
        public static final /* enum */ CommandSelection DEDICATED = new CommandSelection(false, true);
        public static final /* enum */ CommandSelection INTEGRATED = new CommandSelection(true, false);
        final boolean f_82144_;
        final boolean f_82145_;
        private static final /* synthetic */ CommandSelection[] $VALUES;

        public static CommandSelection[] values() {
            return (CommandSelection[])$VALUES.clone();
        }

        public static CommandSelection valueOf(String p_82158_) {
            return Enum.valueOf(CommandSelection.class, p_82158_);
        }

        private CommandSelection(boolean p_82151_, boolean p_82152_) {
            this.f_82144_ = p_82151_;
            this.f_82145_ = p_82152_;
        }

        private static /* synthetic */ CommandSelection[] m_165687_() {
            return new CommandSelection[]{ALL, DEDICATED, INTEGRATED};
        }

        static {
            $VALUES = CommandSelection.m_165687_();
        }
    }

    @FunctionalInterface
    public static interface ParseFunction {
        public void m_82160_(StringReader var1) throws CommandSyntaxException;
    }
}

