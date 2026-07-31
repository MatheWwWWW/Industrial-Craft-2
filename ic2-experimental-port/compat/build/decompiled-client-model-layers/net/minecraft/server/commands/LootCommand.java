/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootCommand {
    public static final SuggestionProvider<CommandSourceStack> f_137877_ = (p_137910_, p_137911_) -> {
        LootTables $$2 = ((CommandSourceStack)p_137910_.getSource()).m_81377_().m_129898_();
        return SharedSuggestionProvider.m_82926_($$2.m_79195_(), p_137911_);
    };
    private static final DynamicCommandExceptionType f_137878_ = new DynamicCommandExceptionType(p_137999_ -> Component.m_237110_("commands.drop.no_held_items", p_137999_));
    private static final DynamicCommandExceptionType f_137879_ = new DynamicCommandExceptionType(p_137977_ -> Component.m_237110_("commands.drop.no_loot_table", p_137977_));

    public static void m_214515_(CommandDispatcher<CommandSourceStack> p_214516_, CommandBuildContext p_214517_) {
        p_214516_.register(LootCommand.m_137902_((LiteralArgumentBuilder)Commands.m_82127_("loot").requires(p_137937_ -> p_137937_.m_6761_(2)), (p_214520_, p_214521_) -> p_214520_.then(Commands.m_82127_("fish").then(Commands.m_82129_("loot_table", ResourceLocationArgument.m_106984_()).suggests(f_137877_).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_180421_ -> LootCommand.m_137926_((CommandContext<CommandSourceStack>)p_180421_, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_180421_, "loot_table"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180421_, "pos"), ItemStack.f_41583_, p_214521_))).then(Commands.m_82129_("tool", ItemArgument.m_235279_(p_214517_)).executes(p_180418_ -> LootCommand.m_137926_((CommandContext<CommandSourceStack>)p_180418_, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_180418_, "loot_table"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180418_, "pos"), ItemArgument.m_120963_(p_180418_, "tool").m_120980_(1, false), p_214521_)))).then(Commands.m_82127_("mainhand").executes(p_180415_ -> LootCommand.m_137926_((CommandContext<CommandSourceStack>)p_180415_, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_180415_, "loot_table"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180415_, "pos"), LootCommand.m_137938_((CommandSourceStack)p_180415_.getSource(), EquipmentSlot.MAINHAND), p_214521_)))).then(Commands.m_82127_("offhand").executes(p_180412_ -> LootCommand.m_137926_((CommandContext<CommandSourceStack>)p_180412_, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_180412_, "loot_table"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180412_, "pos"), LootCommand.m_137938_((CommandSourceStack)p_180412_.getSource(), EquipmentSlot.OFFHAND), p_214521_)))))).then(Commands.m_82127_("loot").then(Commands.m_82129_("loot_table", ResourceLocationArgument.m_106984_()).suggests(f_137877_).executes(p_180409_ -> LootCommand.m_137932_((CommandContext<CommandSourceStack>)p_180409_, ResourceLocationArgument.m_107011_((CommandContext<CommandSourceStack>)p_180409_, "loot_table"), p_214521_)))).then(Commands.m_82127_("kill").then(Commands.m_82129_("target", EntityArgument.m_91449_()).executes(p_180406_ -> LootCommand.m_137905_((CommandContext<CommandSourceStack>)p_180406_, EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_180406_, "target"), p_214521_)))).then(Commands.m_82127_("mine").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes(p_180403_ -> LootCommand.m_137912_((CommandContext<CommandSourceStack>)p_180403_, BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180403_, "pos"), ItemStack.f_41583_, p_214521_))).then(Commands.m_82129_("tool", ItemArgument.m_235279_(p_214517_)).executes(p_180400_ -> LootCommand.m_137912_((CommandContext<CommandSourceStack>)p_180400_, BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180400_, "pos"), ItemArgument.m_120963_(p_180400_, "tool").m_120980_(1, false), p_214521_)))).then(Commands.m_82127_("mainhand").executes(p_180397_ -> LootCommand.m_137912_((CommandContext<CommandSourceStack>)p_180397_, BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180397_, "pos"), LootCommand.m_137938_((CommandSourceStack)p_180397_.getSource(), EquipmentSlot.MAINHAND), p_214521_)))).then(Commands.m_82127_("offhand").executes(p_180394_ -> LootCommand.m_137912_((CommandContext<CommandSourceStack>)p_180394_, BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180394_, "pos"), LootCommand.m_137938_((CommandSourceStack)p_180394_.getSource(), EquipmentSlot.OFFHAND), p_214521_)))))));
    }

    private static <T extends ArgumentBuilder<CommandSourceStack, T>> T m_137902_(T p_137903_, TailProvider p_137904_) {
        return (T)p_137903_.then(((LiteralArgumentBuilder)Commands.m_82127_("replace").then(Commands.m_82127_("entity").then(Commands.m_82129_("entities", EntityArgument.m_91460_()).then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("slot", SlotArgument.m_111276_()), (p_138032_, p_138033_, p_138034_) -> LootCommand.m_137978_(EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138032_, "entities"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_138032_, "slot"), p_138033_.size(), p_138033_, p_138034_)).then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("count", IntegerArgumentType.integer((int)0)), (p_138025_, p_138026_, p_138027_) -> LootCommand.m_137978_(EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138025_, "entities"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_138025_, "slot"), IntegerArgumentType.getInteger((CommandContext)p_138025_, (String)"count"), p_138026_, p_138027_))))))).then(Commands.m_82127_("block").then(Commands.m_82129_("targetPos", BlockPosArgument.m_118239_()).then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("slot", SlotArgument.m_111276_()), (p_138018_, p_138019_, p_138020_) -> LootCommand.m_137953_((CommandSourceStack)p_138018_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138018_, "targetPos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_138018_, "slot"), p_138019_.size(), p_138019_, p_138020_)).then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("count", IntegerArgumentType.integer((int)0)), (p_138011_, p_138012_, p_138013_) -> LootCommand.m_137953_((CommandSourceStack)p_138011_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138011_, "targetPos"), IntegerArgumentType.getInteger((CommandContext)p_138011_, (String)"slot"), IntegerArgumentType.getInteger((CommandContext)p_138011_, (String)"count"), p_138012_, p_138013_))))))).then(Commands.m_82127_("insert").then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("targetPos", BlockPosArgument.m_118239_()), (p_138004_, p_138005_, p_138006_) -> LootCommand.m_137960_((CommandSourceStack)p_138004_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138004_, "targetPos"), p_138005_, p_138006_)))).then(Commands.m_82127_("give").then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("players", EntityArgument.m_91470_()), (p_137992_, p_137993_, p_137994_) -> LootCommand.m_137984_(EntityArgument.m_91477_((CommandContext<CommandSourceStack>)p_137992_, "players"), p_137993_, p_137994_)))).then(Commands.m_82127_("spawn").then(p_137904_.m_138053_((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_("targetPos", Vec3Argument.m_120841_()), (p_137918_, p_137919_, p_137920_) -> LootCommand.m_137945_((CommandSourceStack)p_137918_.getSource(), Vec3Argument.m_120844_((CommandContext<CommandSourceStack>)p_137918_, "targetPos"), p_137919_, p_137920_))));
    }

    private static Container m_137950_(CommandSourceStack p_137951_, BlockPos p_137952_) throws CommandSyntaxException {
        BlockEntity $$2 = p_137951_.m_81372_().m_7702_(p_137952_);
        if (!($$2 instanceof Container)) {
            throw ItemCommands.f_180236_.create((Object)p_137952_.m_123341_(), (Object)p_137952_.m_123342_(), (Object)p_137952_.m_123343_());
        }
        return (Container)((Object)$$2);
    }

    private static int m_137960_(CommandSourceStack p_137961_, BlockPos p_137962_, List<ItemStack> p_137963_, Callback p_137964_) throws CommandSyntaxException {
        Container $$4 = LootCommand.m_137950_(p_137961_, p_137962_);
        ArrayList $$5 = Lists.newArrayListWithCapacity((int)p_137963_.size());
        for (ItemStack $$6 : p_137963_) {
            if (!LootCommand.m_137885_($$4, $$6.m_41777_())) continue;
            $$4.m_6596_();
            $$5.add($$6);
        }
        p_137964_.m_138047_($$5);
        return $$5.size();
    }

    private static boolean m_137885_(Container p_137886_, ItemStack p_137887_) {
        boolean $$2 = false;
        for (int $$3 = 0; $$3 < p_137886_.m_6643_() && !p_137887_.m_41619_(); ++$$3) {
            ItemStack $$4 = p_137886_.m_8020_($$3);
            if (!p_137886_.m_7013_($$3, p_137887_)) continue;
            if ($$4.m_41619_()) {
                p_137886_.m_6836_($$3, p_137887_);
                $$2 = true;
                break;
            }
            if (!LootCommand.m_137894_($$4, p_137887_)) continue;
            int $$5 = p_137887_.m_41741_() - $$4.m_41613_();
            int $$6 = Math.min(p_137887_.m_41613_(), $$5);
            p_137887_.m_41774_($$6);
            $$4.m_41769_($$6);
            $$2 = true;
        }
        return $$2;
    }

    private static int m_137953_(CommandSourceStack p_137954_, BlockPos p_137955_, int p_137956_, int p_137957_, List<ItemStack> p_137958_, Callback p_137959_) throws CommandSyntaxException {
        Container $$6 = LootCommand.m_137950_(p_137954_, p_137955_);
        int $$7 = $$6.m_6643_();
        if (p_137956_ < 0 || p_137956_ >= $$7) {
            throw ItemCommands.f_180237_.create((Object)p_137956_);
        }
        ArrayList $$8 = Lists.newArrayListWithCapacity((int)p_137958_.size());
        for (int $$9 = 0; $$9 < p_137957_; ++$$9) {
            ItemStack $$11;
            int $$10 = p_137956_ + $$9;
            ItemStack itemStack = $$11 = $$9 < p_137958_.size() ? p_137958_.get($$9) : ItemStack.f_41583_;
            if (!$$6.m_7013_($$10, $$11)) continue;
            $$6.m_6836_($$10, $$11);
            $$8.add($$11);
        }
        p_137959_.m_138047_($$8);
        return $$8.size();
    }

    private static boolean m_137894_(ItemStack p_137895_, ItemStack p_137896_) {
        return p_137895_.m_150930_(p_137896_.m_41720_()) && p_137895_.m_41773_() == p_137896_.m_41773_() && p_137895_.m_41613_() <= p_137895_.m_41741_() && Objects.equals(p_137895_.m_41783_(), p_137896_.m_41783_());
    }

    private static int m_137984_(Collection<ServerPlayer> p_137985_, List<ItemStack> p_137986_, Callback p_137987_) throws CommandSyntaxException {
        ArrayList $$3 = Lists.newArrayListWithCapacity((int)p_137986_.size());
        for (ItemStack $$4 : p_137986_) {
            for (ServerPlayer $$5 : p_137985_) {
                if (!$$5.m_150109_().m_36054_($$4.m_41777_())) continue;
                $$3.add($$4);
            }
        }
        p_137987_.m_138047_($$3);
        return $$3.size();
    }

    private static void m_137888_(Entity p_137889_, List<ItemStack> p_137890_, int p_137891_, int p_137892_, List<ItemStack> p_137893_) {
        for (int $$5 = 0; $$5 < p_137892_; ++$$5) {
            ItemStack $$6 = $$5 < p_137890_.size() ? p_137890_.get($$5) : ItemStack.f_41583_;
            SlotAccess $$7 = p_137889_.m_141942_(p_137891_ + $$5);
            if ($$7 == SlotAccess.f_147290_ || !$$7.m_142104_($$6.m_41777_())) continue;
            p_137893_.add($$6);
        }
    }

    private static int m_137978_(Collection<? extends Entity> p_137979_, int p_137980_, int p_137981_, List<ItemStack> p_137982_, Callback p_137983_) throws CommandSyntaxException {
        ArrayList $$5 = Lists.newArrayListWithCapacity((int)p_137982_.size());
        for (Entity entity : p_137979_) {
            if (entity instanceof ServerPlayer) {
                ServerPlayer $$7 = (ServerPlayer)entity;
                LootCommand.m_137888_(entity, p_137982_, p_137980_, p_137981_, $$5);
                $$7.f_36096_.m_38946_();
                continue;
            }
            LootCommand.m_137888_(entity, p_137982_, p_137980_, p_137981_, $$5);
        }
        p_137983_.m_138047_($$5);
        return $$5.size();
    }

    private static int m_137945_(CommandSourceStack p_137946_, Vec3 p_137947_, List<ItemStack> p_137948_, Callback p_137949_) throws CommandSyntaxException {
        ServerLevel $$4 = p_137946_.m_81372_();
        p_137948_.forEach(p_137884_ -> {
            ItemEntity $$3 = new ItemEntity($$4, p_137883_.f_82479_, p_137883_.f_82480_, p_137883_.f_82481_, p_137884_.m_41777_());
            $$3.m_32060_();
            $$4.m_7967_($$3);
        });
        p_137949_.m_138047_(p_137948_);
        return p_137948_.size();
    }

    private static void m_137965_(CommandSourceStack p_137966_, List<ItemStack> p_137967_) {
        if (p_137967_.size() == 1) {
            ItemStack $$2 = p_137967_.get(0);
            p_137966_.m_81354_(Component.m_237110_("commands.drop.success.single", $$2.m_41613_(), $$2.m_41611_()), false);
        } else {
            p_137966_.m_81354_(Component.m_237110_("commands.drop.success.multiple", p_137967_.size()), false);
        }
    }

    private static void m_137968_(CommandSourceStack p_137969_, List<ItemStack> p_137970_, ResourceLocation p_137971_) {
        if (p_137970_.size() == 1) {
            ItemStack $$3 = p_137970_.get(0);
            p_137969_.m_81354_(Component.m_237110_("commands.drop.success.single_with_table", $$3.m_41613_(), $$3.m_41611_(), p_137971_), false);
        } else {
            p_137969_.m_81354_(Component.m_237110_("commands.drop.success.multiple_with_table", p_137970_.size(), p_137971_), false);
        }
    }

    private static ItemStack m_137938_(CommandSourceStack p_137939_, EquipmentSlot p_137940_) throws CommandSyntaxException {
        Entity $$2 = p_137939_.m_81374_();
        if ($$2 instanceof LivingEntity) {
            return ((LivingEntity)$$2).m_6844_(p_137940_);
        }
        throw f_137878_.create((Object)$$2.m_5446_());
    }

    private static int m_137912_(CommandContext<CommandSourceStack> p_137913_, BlockPos p_137914_, ItemStack p_137915_, DropConsumer p_137916_) throws CommandSyntaxException {
        CommandSourceStack $$4 = (CommandSourceStack)p_137913_.getSource();
        ServerLevel $$5 = $$4.m_81372_();
        BlockState $$6 = $$5.m_8055_(p_137914_);
        BlockEntity $$7 = $$5.m_7702_(p_137914_);
        LootContext.Builder $$8 = new LootContext.Builder($$5).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_137914_)).m_78972_(LootContextParams.f_81461_, $$6).m_78984_(LootContextParams.f_81462_, $$7).m_78984_(LootContextParams.f_81455_, $$4.m_81373_()).m_78972_(LootContextParams.f_81463_, p_137915_);
        List<ItemStack> $$9 = $$6.m_60724_($$8);
        return p_137916_.m_138049_(p_137913_, $$9, p_137944_ -> LootCommand.m_137968_($$4, p_137944_, $$6.m_60734_().m_60589_()));
    }

    private static int m_137905_(CommandContext<CommandSourceStack> p_137906_, Entity p_137907_, DropConsumer p_137908_) throws CommandSyntaxException {
        if (!(p_137907_ instanceof LivingEntity)) {
            throw f_137879_.create((Object)p_137907_.m_5446_());
        }
        ResourceLocation $$3 = ((LivingEntity)p_137907_).m_5743_();
        CommandSourceStack $$4 = (CommandSourceStack)p_137906_.getSource();
        LootContext.Builder $$5 = new LootContext.Builder($$4.m_81372_());
        Entity $$6 = $$4.m_81373_();
        if ($$6 instanceof Player) {
            $$5.m_78972_(LootContextParams.f_81456_, (Player)$$6);
        }
        $$5.m_78972_(LootContextParams.f_81457_, DamageSource.f_19319_);
        $$5.m_78984_(LootContextParams.f_81459_, $$6);
        $$5.m_78984_(LootContextParams.f_81458_, $$6);
        $$5.m_78972_(LootContextParams.f_81455_, p_137907_);
        $$5.m_78972_(LootContextParams.f_81460_, $$4.m_81371_());
        LootTable $$7 = $$4.m_81377_().m_129898_().m_79217_($$3);
        ObjectArrayList<ItemStack> $$8 = $$7.m_230922_($$5.m_78975_(LootContextParamSets.f_81415_));
        return p_137908_.m_138049_(p_137906_, (List<ItemStack>)$$8, p_137975_ -> LootCommand.m_137968_($$4, p_137975_, $$3));
    }

    private static int m_137932_(CommandContext<CommandSourceStack> p_137933_, ResourceLocation p_137934_, DropConsumer p_137935_) throws CommandSyntaxException {
        CommandSourceStack $$3 = (CommandSourceStack)p_137933_.getSource();
        LootContext.Builder $$4 = new LootContext.Builder($$3.m_81372_()).m_78984_(LootContextParams.f_81455_, $$3.m_81373_()).m_78972_(LootContextParams.f_81460_, $$3.m_81371_());
        return LootCommand.m_137921_(p_137933_, p_137934_, $$4.m_78975_(LootContextParamSets.f_81411_), p_137935_);
    }

    private static int m_137926_(CommandContext<CommandSourceStack> p_137927_, ResourceLocation p_137928_, BlockPos p_137929_, ItemStack p_137930_, DropConsumer p_137931_) throws CommandSyntaxException {
        CommandSourceStack $$5 = (CommandSourceStack)p_137927_.getSource();
        LootContext $$6 = new LootContext.Builder($$5.m_81372_()).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_137929_)).m_78972_(LootContextParams.f_81463_, p_137930_).m_78984_(LootContextParams.f_81455_, $$5.m_81373_()).m_78975_(LootContextParamSets.f_81414_);
        return LootCommand.m_137921_(p_137927_, p_137928_, $$6, p_137931_);
    }

    private static int m_137921_(CommandContext<CommandSourceStack> p_137922_, ResourceLocation p_137923_, LootContext p_137924_, DropConsumer p_137925_) throws CommandSyntaxException {
        CommandSourceStack $$4 = (CommandSourceStack)p_137922_.getSource();
        LootTable $$5 = $$4.m_81377_().m_129898_().m_79217_(p_137923_);
        ObjectArrayList<ItemStack> $$6 = $$5.m_230922_(p_137924_);
        return p_137925_.m_138049_(p_137922_, (List<ItemStack>)$$6, p_137997_ -> LootCommand.m_137965_($$4, p_137997_));
    }

    @FunctionalInterface
    static interface TailProvider {
        public ArgumentBuilder<CommandSourceStack, ?> m_138053_(ArgumentBuilder<CommandSourceStack, ?> var1, DropConsumer var2);
    }

    @FunctionalInterface
    static interface DropConsumer {
        public int m_138049_(CommandContext<CommandSourceStack> var1, List<ItemStack> var2, Callback var3) throws CommandSyntaxException;
    }

    @FunctionalInterface
    static interface Callback {
        public void m_138047_(List<ItemStack> var1) throws CommandSyntaxException;
    }
}

