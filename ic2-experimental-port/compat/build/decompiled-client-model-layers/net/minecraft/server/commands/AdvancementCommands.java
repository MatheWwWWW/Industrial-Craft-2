/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementCommands {
    private static final SuggestionProvider<CommandSourceStack> f_136308_ = (p_136344_, p_136345_) -> {
        Collection<Advancement> $$2 = ((CommandSourceStack)p_136344_.getSource()).m_81377_().m_129889_().m_136028_();
        return SharedSuggestionProvider.m_82957_($$2.stream().map(Advancement::m_138327_), p_136345_);
    };

    public static void m_136310_(CommandDispatcher<CommandSourceStack> p_136311_) {
        p_136311_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("advancement").requires(p_136318_ -> p_136318_.m_6761_(2))).then(Commands.m_82127_("grant").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82127_("only").then(((RequiredArgumentBuilder)Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136363_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136363_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136363_, "targets"), Action.GRANT, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136363_, "advancement"), Mode.ONLY)))).then(Commands.m_82129_("criterion", StringArgumentType.greedyString()).suggests((p_136339_, p_136340_) -> SharedSuggestionProvider.m_82970_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136339_, "advancement").m_138325_().keySet(), p_136340_)).executes(p_136361_ -> AdvancementCommands.m_136324_((CommandSourceStack)p_136361_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136361_, "targets"), Action.GRANT, ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136361_, "advancement"), StringArgumentType.getString((CommandContext)p_136361_, (String)"criterion"))))))).then(Commands.m_82127_("from").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136359_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136359_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136359_, "targets"), Action.GRANT, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136359_, "advancement"), Mode.FROM)))))).then(Commands.m_82127_("until").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136357_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136357_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136357_, "targets"), Action.GRANT, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136357_, "advancement"), Mode.UNTIL)))))).then(Commands.m_82127_("through").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136355_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136355_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136355_, "targets"), Action.GRANT, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136355_, "advancement"), Mode.THROUGH)))))).then(Commands.m_82127_("everything").executes(p_136353_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136353_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136353_, "targets"), Action.GRANT, ((CommandSourceStack)p_136353_.getSource()).m_81377_().m_129889_().m_136028_())))))).then(Commands.m_82127_("revoke").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82127_("only").then(((RequiredArgumentBuilder)Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136351_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136351_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136351_, "targets"), Action.REVOKE, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136351_, "advancement"), Mode.ONLY)))).then(Commands.m_82129_("criterion", StringArgumentType.greedyString()).suggests((p_136315_, p_136316_) -> SharedSuggestionProvider.m_82970_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136315_, "advancement").m_138325_().keySet(), p_136316_)).executes(p_136349_ -> AdvancementCommands.m_136324_((CommandSourceStack)p_136349_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136349_, "targets"), Action.REVOKE, ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136349_, "advancement"), StringArgumentType.getString((CommandContext)p_136349_, (String)"criterion"))))))).then(Commands.m_82127_("from").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136347_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136347_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136347_, "targets"), Action.REVOKE, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136347_, "advancement"), Mode.FROM)))))).then(Commands.m_82127_("until").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136342_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136342_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136342_, "targets"), Action.REVOKE, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136342_, "advancement"), Mode.UNTIL)))))).then(Commands.m_82127_("through").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes(p_136337_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136337_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136337_, "targets"), Action.REVOKE, AdvancementCommands.m_136333_(ResourceLocationArgument.m_106987_((CommandContext<CommandSourceStack>)p_136337_, "advancement"), Mode.THROUGH)))))).then(Commands.m_82127_("everything").executes(p_136313_ -> AdvancementCommands.m_136319_((CommandSourceStack)p_136313_.getSource(), EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_136313_, "targets"), Action.REVOKE, ((CommandSourceStack)p_136313_.getSource()).m_81377_().m_129889_().m_136028_()))))));
    }

    private static int m_136319_(CommandSourceStack p_136320_, Collection<ServerPlayer> p_136321_, Action p_136322_, Collection<Advancement> p_136323_) {
        int $$4 = 0;
        for (ServerPlayer $$5 : p_136321_) {
            $$4 += p_136322_.m_136379_($$5, p_136323_);
        }
        if ($$4 == 0) {
            if (p_136323_.size() == 1) {
                if (p_136321_.size() == 1) {
                    throw new CommandRuntimeException(Component.m_237110_(p_136322_.m_136378_() + ".one.to.one.failure", p_136323_.iterator().next().m_138330_(), p_136321_.iterator().next().m_5446_()));
                }
                throw new CommandRuntimeException(Component.m_237110_(p_136322_.m_136378_() + ".one.to.many.failure", p_136323_.iterator().next().m_138330_(), p_136321_.size()));
            }
            if (p_136321_.size() == 1) {
                throw new CommandRuntimeException(Component.m_237110_(p_136322_.m_136378_() + ".many.to.one.failure", p_136323_.size(), p_136321_.iterator().next().m_5446_()));
            }
            throw new CommandRuntimeException(Component.m_237110_(p_136322_.m_136378_() + ".many.to.many.failure", p_136323_.size(), p_136321_.size()));
        }
        if (p_136323_.size() == 1) {
            if (p_136321_.size() == 1) {
                p_136320_.m_81354_(Component.m_237110_(p_136322_.m_136378_() + ".one.to.one.success", p_136323_.iterator().next().m_138330_(), p_136321_.iterator().next().m_5446_()), true);
            } else {
                p_136320_.m_81354_(Component.m_237110_(p_136322_.m_136378_() + ".one.to.many.success", p_136323_.iterator().next().m_138330_(), p_136321_.size()), true);
            }
        } else if (p_136321_.size() == 1) {
            p_136320_.m_81354_(Component.m_237110_(p_136322_.m_136378_() + ".many.to.one.success", p_136323_.size(), p_136321_.iterator().next().m_5446_()), true);
        } else {
            p_136320_.m_81354_(Component.m_237110_(p_136322_.m_136378_() + ".many.to.many.success", p_136323_.size(), p_136321_.size()), true);
        }
        return $$4;
    }

    private static int m_136324_(CommandSourceStack p_136325_, Collection<ServerPlayer> p_136326_, Action p_136327_, Advancement p_136328_, String p_136329_) {
        int $$5 = 0;
        if (!p_136328_.m_138325_().containsKey(p_136329_)) {
            throw new CommandRuntimeException(Component.m_237110_("commands.advancement.criterionNotFound", p_136328_.m_138330_(), p_136329_));
        }
        for (ServerPlayer $$6 : p_136326_) {
            if (!p_136327_.m_5753_($$6, p_136328_, p_136329_)) continue;
            ++$$5;
        }
        if ($$5 == 0) {
            if (p_136326_.size() == 1) {
                throw new CommandRuntimeException(Component.m_237110_(p_136327_.m_136378_() + ".criterion.to.one.failure", p_136329_, p_136328_.m_138330_(), p_136326_.iterator().next().m_5446_()));
            }
            throw new CommandRuntimeException(Component.m_237110_(p_136327_.m_136378_() + ".criterion.to.many.failure", p_136329_, p_136328_.m_138330_(), p_136326_.size()));
        }
        if (p_136326_.size() == 1) {
            p_136325_.m_81354_(Component.m_237110_(p_136327_.m_136378_() + ".criterion.to.one.success", p_136329_, p_136328_.m_138330_(), p_136326_.iterator().next().m_5446_()), true);
        } else {
            p_136325_.m_81354_(Component.m_237110_(p_136327_.m_136378_() + ".criterion.to.many.success", p_136329_, p_136328_.m_138330_(), p_136326_.size()), true);
        }
        return $$5;
    }

    private static List<Advancement> m_136333_(Advancement p_136334_, Mode p_136335_) {
        ArrayList $$2 = Lists.newArrayList();
        if (p_136335_.f_136417_) {
            for (Advancement $$3 = p_136334_.m_138319_(); $$3 != null; $$3 = $$3.m_138319_()) {
                $$2.add($$3);
            }
        }
        $$2.add(p_136334_);
        if (p_136335_.f_136418_) {
            AdvancementCommands.m_136330_(p_136334_, $$2);
        }
        return $$2;
    }

    private static void m_136330_(Advancement p_136331_, List<Advancement> p_136332_) {
        for (Advancement $$2 : p_136331_.m_138322_()) {
            p_136332_.add($$2);
            AdvancementCommands.m_136330_($$2, p_136332_);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static abstract class Action
    extends Enum<Action> {
        public static final /* enum */ Action GRANT = new Action("grant"){

            @Override
            protected boolean m_6070_(ServerPlayer p_136395_, Advancement p_136396_) {
                AdvancementProgress $$2 = p_136395_.m_8960_().m_135996_(p_136396_);
                if ($$2.m_8193_()) {
                    return false;
                }
                for (String $$3 : $$2.m_8219_()) {
                    p_136395_.m_8960_().m_135988_(p_136396_, $$3);
                }
                return true;
            }

            @Override
            protected boolean m_5753_(ServerPlayer p_136398_, Advancement p_136399_, String p_136400_) {
                return p_136398_.m_8960_().m_135988_(p_136399_, p_136400_);
            }
        };
        public static final /* enum */ Action REVOKE = new Action("revoke"){

            @Override
            protected boolean m_6070_(ServerPlayer p_136406_, Advancement p_136407_) {
                AdvancementProgress $$2 = p_136406_.m_8960_().m_135996_(p_136407_);
                if (!$$2.m_8206_()) {
                    return false;
                }
                for (String $$3 : $$2.m_8220_()) {
                    p_136406_.m_8960_().m_135998_(p_136407_, $$3);
                }
                return true;
            }

            @Override
            protected boolean m_5753_(ServerPlayer p_136409_, Advancement p_136410_, String p_136411_) {
                return p_136409_.m_8960_().m_135998_(p_136410_, p_136411_);
            }
        };
        private final String f_136366_;
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_136388_) {
            return Enum.valueOf(Action.class, p_136388_);
        }

        Action(String p_136372_) {
            this.f_136366_ = "commands.advancement." + p_136372_;
        }

        public int m_136379_(ServerPlayer p_136380_, Iterable<Advancement> p_136381_) {
            int $$2 = 0;
            for (Advancement $$3 : p_136381_) {
                if (!this.m_6070_(p_136380_, $$3)) continue;
                ++$$2;
            }
            return $$2;
        }

        protected abstract boolean m_6070_(ServerPlayer var1, Advancement var2);

        protected abstract boolean m_5753_(ServerPlayer var1, Advancement var2, String var3);

        protected String m_136378_() {
            return this.f_136366_;
        }

        private static /* synthetic */ Action[] m_180018_() {
            return new Action[]{GRANT, REVOKE};
        }

        static {
            $VALUES = Action.m_180018_();
        }
    }

    static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode ONLY = new Mode(false, false);
        public static final /* enum */ Mode THROUGH = new Mode(true, true);
        public static final /* enum */ Mode FROM = new Mode(false, true);
        public static final /* enum */ Mode UNTIL = new Mode(true, false);
        public static final /* enum */ Mode EVERYTHING = new Mode(true, true);
        final boolean f_136417_;
        final boolean f_136418_;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_136431_) {
            return Enum.valueOf(Mode.class, p_136431_);
        }

        private Mode(boolean p_136424_, boolean p_136425_) {
            this.f_136417_ = p_136424_;
            this.f_136418_ = p_136425_;
        }

        private static /* synthetic */ Mode[] m_180019_() {
            return new Mode[]{ONLY, THROUGH, FROM, UNTIL, EVERYTHING};
        }

        static {
            $VALUES = Mode.m_180019_();
        }
    }
}

