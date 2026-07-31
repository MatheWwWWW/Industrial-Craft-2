/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class TeamCommand {
    private static final SimpleCommandExceptionType f_138862_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.add.duplicate"));
    private static final SimpleCommandExceptionType f_138864_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.empty.unchanged"));
    private static final SimpleCommandExceptionType f_138865_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.name.unchanged"));
    private static final SimpleCommandExceptionType f_138866_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.color.unchanged"));
    private static final SimpleCommandExceptionType f_138867_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.friendlyfire.alreadyEnabled"));
    private static final SimpleCommandExceptionType f_138868_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.friendlyfire.alreadyDisabled"));
    private static final SimpleCommandExceptionType f_138869_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
    private static final SimpleCommandExceptionType f_138870_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
    private static final SimpleCommandExceptionType f_138871_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.nametagVisibility.unchanged"));
    private static final SimpleCommandExceptionType f_138872_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.deathMessageVisibility.unchanged"));
    private static final SimpleCommandExceptionType f_138873_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.team.option.collisionRule.unchanged"));

    public static void m_138877_(CommandDispatcher<CommandSourceStack> p_138878_) {
        p_138878_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("team").requires(p_183713_ -> p_183713_.m_6761_(2))).then(((LiteralArgumentBuilder)Commands.m_82127_("list").executes(p_183711_ -> TeamCommand.m_138881_((CommandSourceStack)p_183711_.getSource()))).then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes(p_138876_ -> TeamCommand.m_138943_((CommandSourceStack)p_138876_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138876_, "team")))))).then(Commands.m_82127_("add").then(((RequiredArgumentBuilder)Commands.m_82129_("team", StringArgumentType.word()).executes(p_138995_ -> TeamCommand.m_138910_((CommandSourceStack)p_138995_.getSource(), StringArgumentType.getString((CommandContext)p_138995_, (String)"team")))).then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes(p_138993_ -> TeamCommand.m_138913_((CommandSourceStack)p_138993_.getSource(), StringArgumentType.getString((CommandContext)p_138993_, (String)"team"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138993_, "displayName"))))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes(p_138991_ -> TeamCommand.m_138926_((CommandSourceStack)p_138991_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138991_, "team")))))).then(Commands.m_82127_("empty").then(Commands.m_82129_("team", TeamArgument.m_112088_()).executes(p_138989_ -> TeamCommand.m_138883_((CommandSourceStack)p_138989_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138989_, "team")))))).then(Commands.m_82127_("join").then(((RequiredArgumentBuilder)Commands.m_82129_("team", TeamArgument.m_112088_()).executes(p_138987_ -> TeamCommand.m_138894_((CommandSourceStack)p_138987_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138987_, "team"), Collections.singleton(((CommandSourceStack)p_138987_.getSource()).m_81374_().m_6302_())))).then(Commands.m_82129_("members", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes(p_138985_ -> TeamCommand.m_138894_((CommandSourceStack)p_138985_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138985_, "team"), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138985_, "members"))))))).then(Commands.m_82127_("leave").then(Commands.m_82129_("members", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).executes(p_138983_ -> TeamCommand.m_138917_((CommandSourceStack)p_138983_.getSource(), ScoreHolderArgument.m_108246_((CommandContext<CommandSourceStack>)p_138983_, "members")))))).then(Commands.m_82127_("modify").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("team", TeamArgument.m_112088_()).then(Commands.m_82127_("displayName").then(Commands.m_82129_("displayName", ComponentArgument.m_87114_()).executes(p_138981_ -> TeamCommand.m_138902_((CommandSourceStack)p_138981_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138981_, "team"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138981_, "displayName")))))).then(Commands.m_82127_("color").then(Commands.m_82129_("value", ColorArgument.m_85463_()).executes(p_138979_ -> TeamCommand.m_138898_((CommandSourceStack)p_138979_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138979_, "team"), ColorArgument.m_85466_((CommandContext<CommandSourceStack>)p_138979_, "value")))))).then(Commands.m_82127_("friendlyFire").then(Commands.m_82129_("allowed", BoolArgumentType.bool()).executes(p_138977_ -> TeamCommand.m_138937_((CommandSourceStack)p_138977_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138977_, "team"), BoolArgumentType.getBool((CommandContext)p_138977_, (String)"allowed")))))).then(Commands.m_82127_("seeFriendlyInvisibles").then(Commands.m_82129_("allowed", BoolArgumentType.bool()).executes(p_138975_ -> TeamCommand.m_138906_((CommandSourceStack)p_138975_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138975_, "team"), BoolArgumentType.getBool((CommandContext)p_138975_, (String)"allowed")))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("nametagVisibility").then(Commands.m_82127_("never").executes(p_138973_ -> TeamCommand.m_138890_((CommandSourceStack)p_138973_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138973_, "team"), Team.Visibility.NEVER)))).then(Commands.m_82127_("hideForOtherTeams").executes(p_138971_ -> TeamCommand.m_138890_((CommandSourceStack)p_138971_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138971_, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS)))).then(Commands.m_82127_("hideForOwnTeam").executes(p_138969_ -> TeamCommand.m_138890_((CommandSourceStack)p_138969_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138969_, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM)))).then(Commands.m_82127_("always").executes(p_138967_ -> TeamCommand.m_138890_((CommandSourceStack)p_138967_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138967_, "team"), Team.Visibility.ALWAYS))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("deathMessageVisibility").then(Commands.m_82127_("never").executes(p_138965_ -> TeamCommand.m_138929_((CommandSourceStack)p_138965_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138965_, "team"), Team.Visibility.NEVER)))).then(Commands.m_82127_("hideForOtherTeams").executes(p_138963_ -> TeamCommand.m_138929_((CommandSourceStack)p_138963_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138963_, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS)))).then(Commands.m_82127_("hideForOwnTeam").executes(p_138961_ -> TeamCommand.m_138929_((CommandSourceStack)p_138961_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138961_, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM)))).then(Commands.m_82127_("always").executes(p_138959_ -> TeamCommand.m_138929_((CommandSourceStack)p_138959_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138959_, "team"), Team.Visibility.ALWAYS))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("collisionRule").then(Commands.m_82127_("never").executes(p_138957_ -> TeamCommand.m_138886_((CommandSourceStack)p_138957_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138957_, "team"), Team.CollisionRule.NEVER)))).then(Commands.m_82127_("pushOwnTeam").executes(p_138955_ -> TeamCommand.m_138886_((CommandSourceStack)p_138955_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138955_, "team"), Team.CollisionRule.PUSH_OWN_TEAM)))).then(Commands.m_82127_("pushOtherTeams").executes(p_138953_ -> TeamCommand.m_138886_((CommandSourceStack)p_138953_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138953_, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS)))).then(Commands.m_82127_("always").executes(p_138951_ -> TeamCommand.m_138886_((CommandSourceStack)p_138951_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138951_, "team"), Team.CollisionRule.ALWAYS))))).then(Commands.m_82127_("prefix").then(Commands.m_82129_("prefix", ComponentArgument.m_87114_()).executes(p_138942_ -> TeamCommand.m_138933_((CommandSourceStack)p_138942_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138942_, "team"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138942_, "prefix")))))).then(Commands.m_82127_("suffix").then(Commands.m_82129_("suffix", ComponentArgument.m_87114_()).executes(p_138923_ -> TeamCommand.m_138946_((CommandSourceStack)p_138923_.getSource(), TeamArgument.m_112091_((CommandContext<CommandSourceStack>)p_138923_, "team"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_138923_, "suffix"))))))));
    }

    private static int m_138917_(CommandSourceStack p_138918_, Collection<String> p_138919_) {
        ServerScoreboard $$2 = p_138918_.m_81377_().m_129896_();
        for (String $$3 : p_138919_) {
            $$2.m_83495_($$3);
        }
        if (p_138919_.size() == 1) {
            p_138918_.m_81354_(Component.m_237110_("commands.team.leave.success.single", p_138919_.iterator().next()), true);
        } else {
            p_138918_.m_81354_(Component.m_237110_("commands.team.leave.success.multiple", p_138919_.size()), true);
        }
        return p_138919_.size();
    }

    private static int m_138894_(CommandSourceStack p_138895_, PlayerTeam p_138896_, Collection<String> p_138897_) {
        ServerScoreboard $$3 = p_138895_.m_81377_().m_129896_();
        for (String $$4 : p_138897_) {
            ((Scoreboard)$$3).m_6546_($$4, p_138896_);
        }
        if (p_138897_.size() == 1) {
            p_138895_.m_81354_(Component.m_237110_("commands.team.join.success.single", p_138897_.iterator().next(), p_138896_.m_83367_()), true);
        } else {
            p_138895_.m_81354_(Component.m_237110_("commands.team.join.success.multiple", p_138897_.size(), p_138896_.m_83367_()), true);
        }
        return p_138897_.size();
    }

    private static int m_138890_(CommandSourceStack p_138891_, PlayerTeam p_138892_, Team.Visibility p_138893_) throws CommandSyntaxException {
        if (p_138892_.m_7470_() == p_138893_) {
            throw f_138871_.create();
        }
        p_138892_.m_83346_(p_138893_);
        p_138891_.m_81354_(Component.m_237110_("commands.team.option.nametagVisibility.success", p_138892_.m_83367_(), p_138893_.m_83581_()), true);
        return 0;
    }

    private static int m_138929_(CommandSourceStack p_138930_, PlayerTeam p_138931_, Team.Visibility p_138932_) throws CommandSyntaxException {
        if (p_138931_.m_7468_() == p_138932_) {
            throw f_138872_.create();
        }
        p_138931_.m_83358_(p_138932_);
        p_138930_.m_81354_(Component.m_237110_("commands.team.option.deathMessageVisibility.success", p_138931_.m_83367_(), p_138932_.m_83581_()), true);
        return 0;
    }

    private static int m_138886_(CommandSourceStack p_138887_, PlayerTeam p_138888_, Team.CollisionRule p_138889_) throws CommandSyntaxException {
        if (p_138888_.m_7156_() == p_138889_) {
            throw f_138873_.create();
        }
        p_138888_.m_83344_(p_138889_);
        p_138887_.m_81354_(Component.m_237110_("commands.team.option.collisionRule.success", p_138888_.m_83367_(), p_138889_.m_83557_()), true);
        return 0;
    }

    private static int m_138906_(CommandSourceStack p_138907_, PlayerTeam p_138908_, boolean p_138909_) throws CommandSyntaxException {
        if (p_138908_.m_6259_() == p_138909_) {
            if (p_138909_) {
                throw f_138869_.create();
            }
            throw f_138870_.create();
        }
        p_138908_.m_83362_(p_138909_);
        p_138907_.m_81354_(Component.m_237110_("commands.team.option.seeFriendlyInvisibles." + (p_138909_ ? "enabled" : "disabled"), p_138908_.m_83367_()), true);
        return 0;
    }

    private static int m_138937_(CommandSourceStack p_138938_, PlayerTeam p_138939_, boolean p_138940_) throws CommandSyntaxException {
        if (p_138939_.m_6260_() == p_138940_) {
            if (p_138940_) {
                throw f_138867_.create();
            }
            throw f_138868_.create();
        }
        p_138939_.m_83355_(p_138940_);
        p_138938_.m_81354_(Component.m_237110_("commands.team.option.friendlyfire." + (p_138940_ ? "enabled" : "disabled"), p_138939_.m_83367_()), true);
        return 0;
    }

    private static int m_138902_(CommandSourceStack p_138903_, PlayerTeam p_138904_, Component p_138905_) throws CommandSyntaxException {
        if (p_138904_.m_83364_().equals(p_138905_)) {
            throw f_138865_.create();
        }
        p_138904_.m_83353_(p_138905_);
        p_138903_.m_81354_(Component.m_237110_("commands.team.option.name.success", p_138904_.m_83367_()), true);
        return 0;
    }

    private static int m_138898_(CommandSourceStack p_138899_, PlayerTeam p_138900_, ChatFormatting p_138901_) throws CommandSyntaxException {
        if (p_138900_.m_7414_() == p_138901_) {
            throw f_138866_.create();
        }
        p_138900_.m_83351_(p_138901_);
        p_138899_.m_81354_(Component.m_237110_("commands.team.option.color.success", p_138900_.m_83367_(), p_138901_.m_126666_()), true);
        return 0;
    }

    private static int m_138883_(CommandSourceStack p_138884_, PlayerTeam p_138885_) throws CommandSyntaxException {
        ServerScoreboard $$2 = p_138884_.m_81377_().m_129896_();
        ArrayList $$3 = Lists.newArrayList(p_138885_.m_6809_());
        if ($$3.isEmpty()) {
            throw f_138864_.create();
        }
        for (String $$4 : $$3) {
            ((Scoreboard)$$2).m_6519_($$4, p_138885_);
        }
        p_138884_.m_81354_(Component.m_237110_("commands.team.empty.success", $$3.size(), p_138885_.m_83367_()), true);
        return $$3.size();
    }

    private static int m_138926_(CommandSourceStack p_138927_, PlayerTeam p_138928_) {
        ServerScoreboard $$2 = p_138927_.m_81377_().m_129896_();
        $$2.m_83475_(p_138928_);
        p_138927_.m_81354_(Component.m_237110_("commands.team.remove.success", p_138928_.m_83367_()), true);
        return $$2.m_83491_().size();
    }

    private static int m_138910_(CommandSourceStack p_138911_, String p_138912_) throws CommandSyntaxException {
        return TeamCommand.m_138913_(p_138911_, p_138912_, Component.m_237113_(p_138912_));
    }

    private static int m_138913_(CommandSourceStack p_138914_, String p_138915_, Component p_138916_) throws CommandSyntaxException {
        ServerScoreboard $$3 = p_138914_.m_81377_().m_129896_();
        if ($$3.m_83489_(p_138915_) != null) {
            throw f_138862_.create();
        }
        PlayerTeam $$4 = $$3.m_83492_(p_138915_);
        $$4.m_83353_(p_138916_);
        p_138914_.m_81354_(Component.m_237110_("commands.team.add.success", $$4.m_83367_()), true);
        return $$3.m_83491_().size();
    }

    private static int m_138943_(CommandSourceStack p_138944_, PlayerTeam p_138945_) {
        Collection<String> $$2 = p_138945_.m_6809_();
        if ($$2.isEmpty()) {
            p_138944_.m_81354_(Component.m_237110_("commands.team.list.members.empty", p_138945_.m_83367_()), false);
        } else {
            p_138944_.m_81354_(Component.m_237110_("commands.team.list.members.success", p_138945_.m_83367_(), $$2.size(), ComponentUtils.m_130743_($$2)), false);
        }
        return $$2.size();
    }

    private static int m_138881_(CommandSourceStack p_138882_) {
        Collection<PlayerTeam> $$1 = p_138882_.m_81377_().m_129896_().m_83491_();
        if ($$1.isEmpty()) {
            p_138882_.m_81354_(Component.m_237115_("commands.team.list.teams.empty"), false);
        } else {
            p_138882_.m_81354_(Component.m_237110_("commands.team.list.teams.success", $$1.size(), ComponentUtils.m_178440_($$1, PlayerTeam::m_83367_)), false);
        }
        return $$1.size();
    }

    private static int m_138933_(CommandSourceStack p_138934_, PlayerTeam p_138935_, Component p_138936_) {
        p_138935_.m_83360_(p_138936_);
        p_138934_.m_81354_(Component.m_237110_("commands.team.option.prefix.success", p_138936_), false);
        return 1;
    }

    private static int m_138946_(CommandSourceStack p_138947_, PlayerTeam p_138948_, Component p_138949_) {
        p_138948_.m_83365_(p_138949_);
        p_138947_.m_81354_(Component.m_237110_("commands.team.option.suffix.success", p_138949_), false);
        return 1;
    }
}

