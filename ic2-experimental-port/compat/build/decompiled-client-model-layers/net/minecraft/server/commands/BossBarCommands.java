/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;

public class BossBarCommands {
    private static final DynamicCommandExceptionType f_136571_ = new DynamicCommandExceptionType(p_136636_ -> Component.m_237110_("commands.bossbar.create.failed", p_136636_));
    private static final DynamicCommandExceptionType f_136572_ = new DynamicCommandExceptionType(p_136623_ -> Component.m_237110_("commands.bossbar.unknown", p_136623_));
    private static final SimpleCommandExceptionType f_136573_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.players.unchanged"));
    private static final SimpleCommandExceptionType f_136574_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.name.unchanged"));
    private static final SimpleCommandExceptionType f_136575_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.color.unchanged"));
    private static final SimpleCommandExceptionType f_136576_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.style.unchanged"));
    private static final SimpleCommandExceptionType f_136577_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.value.unchanged"));
    private static final SimpleCommandExceptionType f_136578_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.max.unchanged"));
    private static final SimpleCommandExceptionType f_136579_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.visibility.unchanged.hidden"));
    private static final SimpleCommandExceptionType f_136580_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.bossbar.set.visibility.unchanged.visible"));
    public static final SuggestionProvider<CommandSourceStack> f_136570_ = (p_136587_, p_136588_) -> SharedSuggestionProvider.m_82926_(((CommandSourceStack)p_136587_.getSource()).m_81377_().m_129901_().m_136292_(), p_136588_);

    public static void m_136582_(CommandDispatcher<CommandSourceStack> p_136583_) {
        p_136583_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("bossbar").requires(p_136627_ -> p_136627_.m_6761_(2))).then(Commands.m_82127_("add").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).then(Commands.m_82129_("name", ComponentArgument.m_87114_()).executes(p_136693_ -> BossBarCommands.m_136591_((CommandSourceStack)p_136693_.getSource(), ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_136693_, "id"), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_136693_, "name"))))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).executes(p_136691_ -> BossBarCommands.m_136649_((CommandSourceStack)p_136691_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136691_)))))).then(Commands.m_82127_("list").executes(p_136689_ -> BossBarCommands.m_136589_((CommandSourceStack)p_136689_.getSource())))).then(Commands.m_82127_("set").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).then(Commands.m_82127_("name").then(Commands.m_82129_("name", ComponentArgument.m_87114_()).executes(p_136687_ -> BossBarCommands.m_136614_((CommandSourceStack)p_136687_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136687_), ComponentArgument.m_87117_((CommandContext<CommandSourceStack>)p_136687_, "name")))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("color").then(Commands.m_82127_("pink").executes(p_136685_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136685_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136685_), BossEvent.BossBarColor.PINK)))).then(Commands.m_82127_("blue").executes(p_136683_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136683_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136683_), BossEvent.BossBarColor.BLUE)))).then(Commands.m_82127_("red").executes(p_136681_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136681_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136681_), BossEvent.BossBarColor.RED)))).then(Commands.m_82127_("green").executes(p_136679_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136679_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136679_), BossEvent.BossBarColor.GREEN)))).then(Commands.m_82127_("yellow").executes(p_136677_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136677_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136677_), BossEvent.BossBarColor.YELLOW)))).then(Commands.m_82127_("purple").executes(p_136675_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136675_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136675_), BossEvent.BossBarColor.PURPLE)))).then(Commands.m_82127_("white").executes(p_136673_ -> BossBarCommands.m_136602_((CommandSourceStack)p_136673_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136673_), BossEvent.BossBarColor.WHITE))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("style").then(Commands.m_82127_("progress").executes(p_136671_ -> BossBarCommands.m_136606_((CommandSourceStack)p_136671_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136671_), BossEvent.BossBarOverlay.PROGRESS)))).then(Commands.m_82127_("notched_6").executes(p_136669_ -> BossBarCommands.m_136606_((CommandSourceStack)p_136669_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136669_), BossEvent.BossBarOverlay.NOTCHED_6)))).then(Commands.m_82127_("notched_10").executes(p_136667_ -> BossBarCommands.m_136606_((CommandSourceStack)p_136667_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136667_), BossEvent.BossBarOverlay.NOTCHED_10)))).then(Commands.m_82127_("notched_12").executes(p_136665_ -> BossBarCommands.m_136606_((CommandSourceStack)p_136665_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136665_), BossEvent.BossBarOverlay.NOTCHED_12)))).then(Commands.m_82127_("notched_20").executes(p_136663_ -> BossBarCommands.m_136606_((CommandSourceStack)p_136663_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136663_), BossEvent.BossBarOverlay.NOTCHED_20))))).then(Commands.m_82127_("value").then(Commands.m_82129_("value", IntegerArgumentType.integer((int)0)).executes(p_136661_ -> BossBarCommands.m_136598_((CommandSourceStack)p_136661_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136661_), IntegerArgumentType.getInteger((CommandContext)p_136661_, (String)"value")))))).then(Commands.m_82127_("max").then(Commands.m_82129_("max", IntegerArgumentType.integer((int)1)).executes(p_136659_ -> BossBarCommands.m_136631_((CommandSourceStack)p_136659_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136659_), IntegerArgumentType.getInteger((CommandContext)p_136659_, (String)"max")))))).then(Commands.m_82127_("visible").then(Commands.m_82129_("visible", BoolArgumentType.bool()).executes(p_136657_ -> BossBarCommands.m_136618_((CommandSourceStack)p_136657_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136657_), BoolArgumentType.getBool((CommandContext)p_136657_, (String)"visible")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("players").executes(p_136655_ -> BossBarCommands.m_136610_((CommandSourceStack)p_136655_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136655_), Collections.emptyList()))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes(p_136653_ -> BossBarCommands.m_136610_((CommandSourceStack)p_136653_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136653_), EntityArgument.m_91471_((CommandContext<CommandSourceStack>)p_136653_, "targets")))))))).then(Commands.m_82127_("get").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).then(Commands.m_82127_("value").executes(p_136648_ -> BossBarCommands.m_136595_((CommandSourceStack)p_136648_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136648_))))).then(Commands.m_82127_("max").executes(p_136643_ -> BossBarCommands.m_136628_((CommandSourceStack)p_136643_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136643_))))).then(Commands.m_82127_("visible").executes(p_136638_ -> BossBarCommands.m_136639_((CommandSourceStack)p_136638_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136638_))))).then(Commands.m_82127_("players").executes(p_136625_ -> BossBarCommands.m_136644_((CommandSourceStack)p_136625_.getSource(), BossBarCommands.m_136584_((CommandContext<CommandSourceStack>)p_136625_)))))));
    }

    private static int m_136595_(CommandSourceStack p_136596_, CustomBossEvent p_136597_) {
        p_136596_.m_81354_(Component.m_237110_("commands.bossbar.get.value", p_136597_.m_136288_(), p_136597_.m_136282_()), true);
        return p_136597_.m_136282_();
    }

    private static int m_136628_(CommandSourceStack p_136629_, CustomBossEvent p_136630_) {
        p_136629_.m_81354_(Component.m_237110_("commands.bossbar.get.max", p_136630_.m_136288_(), p_136630_.m_136285_()), true);
        return p_136630_.m_136285_();
    }

    private static int m_136639_(CommandSourceStack p_136640_, CustomBossEvent p_136641_) {
        if (p_136641_.m_8323_()) {
            p_136640_.m_81354_(Component.m_237110_("commands.bossbar.get.visible.visible", p_136641_.m_136288_()), true);
            return 1;
        }
        p_136640_.m_81354_(Component.m_237110_("commands.bossbar.get.visible.hidden", p_136641_.m_136288_()), true);
        return 0;
    }

    private static int m_136644_(CommandSourceStack p_136645_, CustomBossEvent p_136646_) {
        if (p_136646_.m_8324_().isEmpty()) {
            p_136645_.m_81354_(Component.m_237110_("commands.bossbar.get.players.none", p_136646_.m_136288_()), true);
        } else {
            p_136645_.m_81354_(Component.m_237110_("commands.bossbar.get.players.some", p_136646_.m_136288_(), p_136646_.m_8324_().size(), ComponentUtils.m_178440_(p_136646_.m_8324_(), Player::m_5446_)), true);
        }
        return p_136646_.m_8324_().size();
    }

    private static int m_136618_(CommandSourceStack p_136619_, CustomBossEvent p_136620_, boolean p_136621_) throws CommandSyntaxException {
        if (p_136620_.m_8323_() == p_136621_) {
            if (p_136621_) {
                throw f_136580_.create();
            }
            throw f_136579_.create();
        }
        p_136620_.m_8321_(p_136621_);
        if (p_136621_) {
            p_136619_.m_81354_(Component.m_237110_("commands.bossbar.set.visible.success.visible", p_136620_.m_136288_()), true);
        } else {
            p_136619_.m_81354_(Component.m_237110_("commands.bossbar.set.visible.success.hidden", p_136620_.m_136288_()), true);
        }
        return 0;
    }

    private static int m_136598_(CommandSourceStack p_136599_, CustomBossEvent p_136600_, int p_136601_) throws CommandSyntaxException {
        if (p_136600_.m_136282_() == p_136601_) {
            throw f_136577_.create();
        }
        p_136600_.m_136264_(p_136601_);
        p_136599_.m_81354_(Component.m_237110_("commands.bossbar.set.value.success", p_136600_.m_136288_(), p_136601_), true);
        return p_136601_;
    }

    private static int m_136631_(CommandSourceStack p_136632_, CustomBossEvent p_136633_, int p_136634_) throws CommandSyntaxException {
        if (p_136633_.m_136285_() == p_136634_) {
            throw f_136578_.create();
        }
        p_136633_.m_136278_(p_136634_);
        p_136632_.m_81354_(Component.m_237110_("commands.bossbar.set.max.success", p_136633_.m_136288_(), p_136634_), true);
        return p_136634_;
    }

    private static int m_136602_(CommandSourceStack p_136603_, CustomBossEvent p_136604_, BossEvent.BossBarColor p_136605_) throws CommandSyntaxException {
        if (p_136604_.m_18862_().equals((Object)p_136605_)) {
            throw f_136575_.create();
        }
        p_136604_.m_6451_(p_136605_);
        p_136603_.m_81354_(Component.m_237110_("commands.bossbar.set.color.success", p_136604_.m_136288_()), true);
        return 0;
    }

    private static int m_136606_(CommandSourceStack p_136607_, CustomBossEvent p_136608_, BossEvent.BossBarOverlay p_136609_) throws CommandSyntaxException {
        if (p_136608_.m_18863_().equals((Object)p_136609_)) {
            throw f_136576_.create();
        }
        p_136608_.m_5648_(p_136609_);
        p_136607_.m_81354_(Component.m_237110_("commands.bossbar.set.style.success", p_136608_.m_136288_()), true);
        return 0;
    }

    private static int m_136614_(CommandSourceStack p_136615_, CustomBossEvent p_136616_, Component p_136617_) throws CommandSyntaxException {
        MutableComponent $$3 = ComponentUtils.m_130731_(p_136615_, p_136617_, null, 0);
        if (p_136616_.m_18861_().equals($$3)) {
            throw f_136574_.create();
        }
        p_136616_.m_6456_($$3);
        p_136615_.m_81354_(Component.m_237110_("commands.bossbar.set.name.success", p_136616_.m_136288_()), true);
        return 0;
    }

    private static int m_136610_(CommandSourceStack p_136611_, CustomBossEvent p_136612_, Collection<ServerPlayer> p_136613_) throws CommandSyntaxException {
        boolean $$3 = p_136612_.m_136268_(p_136613_);
        if (!$$3) {
            throw f_136573_.create();
        }
        if (p_136612_.m_8324_().isEmpty()) {
            p_136611_.m_81354_(Component.m_237110_("commands.bossbar.set.players.success.none", p_136612_.m_136288_()), true);
        } else {
            p_136611_.m_81354_(Component.m_237110_("commands.bossbar.set.players.success.some", p_136612_.m_136288_(), p_136613_.size(), ComponentUtils.m_178440_(p_136613_, Player::m_5446_)), true);
        }
        return p_136612_.m_8324_().size();
    }

    private static int m_136589_(CommandSourceStack p_136590_) {
        Collection<CustomBossEvent> $$1 = p_136590_.m_81377_().m_129901_().m_136304_();
        if ($$1.isEmpty()) {
            p_136590_.m_81354_(Component.m_237115_("commands.bossbar.list.bars.none"), false);
        } else {
            p_136590_.m_81354_(Component.m_237110_("commands.bossbar.list.bars.some", $$1.size(), ComponentUtils.m_178440_($$1, CustomBossEvent::m_136288_)), false);
        }
        return $$1.size();
    }

    private static int m_136591_(CommandSourceStack p_136592_, ResourceLocation p_136593_, Component p_136594_) throws CommandSyntaxException {
        CustomBossEvents $$3 = p_136592_.m_81377_().m_129901_();
        if ($$3.m_136297_(p_136593_) != null) {
            throw f_136571_.create((Object)p_136593_.toString());
        }
        CustomBossEvent $$4 = $$3.m_136299_(p_136593_, ComponentUtils.m_130731_(p_136592_, p_136594_, null, 0));
        p_136592_.m_81354_(Component.m_237110_("commands.bossbar.create.success", $$4.m_136288_()), true);
        return $$3.m_136304_().size();
    }

    private static int m_136649_(CommandSourceStack p_136650_, CustomBossEvent p_136651_) {
        CustomBossEvents $$2 = p_136650_.m_81377_().m_129901_();
        p_136651_.m_7706_();
        $$2.m_136302_(p_136651_);
        p_136650_.m_81354_(Component.m_237110_("commands.bossbar.remove.success", p_136651_.m_136288_()), true);
        return $$2.m_136304_().size();
    }

    public static CustomBossEvent m_136584_(CommandContext<CommandSourceStack> p_136585_) throws CommandSyntaxException {
        ResourceLocation $$1 = ResourceLocationArgument.m_107011_(p_136585_, "id");
        CustomBossEvent $$2 = ((CommandSourceStack)p_136585_.getSource()).m_81377_().m_129901_().m_136297_($$1);
        if ($$2 == null) {
            throw f_136572_.create((Object)$$1.toString());
        }
        return $$2;
    }
}

