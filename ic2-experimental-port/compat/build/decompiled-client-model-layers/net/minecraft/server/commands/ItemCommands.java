/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ItemCommands {
    static final Dynamic3CommandExceptionType f_180236_ = new Dynamic3CommandExceptionType((p_180355_, p_180356_, p_180357_) -> Component.m_237110_("commands.item.target.not_a_container", p_180355_, p_180356_, p_180357_));
    private static final Dynamic3CommandExceptionType f_180238_ = new Dynamic3CommandExceptionType((p_180347_, p_180348_, p_180349_) -> Component.m_237110_("commands.item.source.not_a_container", p_180347_, p_180348_, p_180349_));
    static final DynamicCommandExceptionType f_180237_ = new DynamicCommandExceptionType(p_180361_ -> Component.m_237110_("commands.item.target.no_such_slot", p_180361_));
    private static final DynamicCommandExceptionType f_180239_ = new DynamicCommandExceptionType(p_180353_ -> Component.m_237110_("commands.item.source.no_such_slot", p_180353_));
    private static final DynamicCommandExceptionType f_180240_ = new DynamicCommandExceptionType(p_180342_ -> Component.m_237110_("commands.item.target.no_changes", p_180342_));
    private static final Dynamic2CommandExceptionType f_180241_ = new Dynamic2CommandExceptionType((p_180344_, p_180345_) -> Component.m_237110_("commands.item.target.no_changed.known_item", p_180344_, p_180345_));
    private static final SuggestionProvider<CommandSourceStack> f_180242_ = (p_180253_, p_180254_) -> {
        ItemModifierManager $$2 = ((CommandSourceStack)p_180253_.getSource()).m_81377_().m_177926_();
        return SharedSuggestionProvider.m_82926_($$2.m_165088_(), p_180254_);
    };

    public static void m_214448_(CommandDispatcher<CommandSourceStack> p_214449_, CommandBuildContext p_214450_) {
        p_214449_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("item").requires(p_180256_ -> p_180256_.m_6761_(2))).then(((LiteralArgumentBuilder)Commands.m_82127_("replace").then(Commands.m_82127_("block").then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)Commands.m_82129_("slot", SlotArgument.m_111276_()).then(Commands.m_82127_("with").then(((RequiredArgumentBuilder)Commands.m_82129_("item", ItemArgument.m_235279_(p_214450_)).executes(p_180383_ -> ItemCommands.m_180291_((CommandSourceStack)p_180383_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180383_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180383_, "slot"), ItemArgument.m_120963_(p_180383_, "item").m_120980_(1, false)))).then(Commands.m_82129_("count", IntegerArgumentType.integer((int)1, (int)64)).executes(p_180381_ -> ItemCommands.m_180291_((CommandSourceStack)p_180381_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180381_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180381_, "slot"), ItemArgument.m_120963_(p_180381_, "item").m_120980_(IntegerArgumentType.getInteger((CommandContext)p_180381_, (String)"count"), true))))))).then(((LiteralArgumentBuilder)Commands.m_82127_("from").then(Commands.m_82127_("block").then(Commands.m_82129_("source", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)Commands.m_82129_("sourceSlot", SlotArgument.m_111276_()).executes(p_180379_ -> ItemCommands.m_180301_((CommandSourceStack)p_180379_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180379_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180379_, "sourceSlot"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180379_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180379_, "slot")))).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180377_ -> ItemCommands.m_180307_((CommandSourceStack)p_180377_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180377_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180377_, "sourceSlot"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180377_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180377_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180377_, "modifier")))))))).then(Commands.m_82127_("entity").then(Commands.m_82129_("source", EntityArgument.m_91449_()).then(((RequiredArgumentBuilder)Commands.m_82129_("sourceSlot", SlotArgument.m_111276_()).executes(p_180375_ -> ItemCommands.m_180257_((CommandSourceStack)p_180375_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_180375_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180375_, "sourceSlot"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180375_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180375_, "slot")))).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180373_ -> ItemCommands.m_180263_((CommandSourceStack)p_180373_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_180373_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180373_, "sourceSlot"), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180373_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180373_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180373_, "modifier")))))))))))).then(Commands.m_82127_("entity").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(((RequiredArgumentBuilder)Commands.m_82129_("slot", SlotArgument.m_111276_()).then(Commands.m_82127_("with").then(((RequiredArgumentBuilder)Commands.m_82129_("item", ItemArgument.m_235279_(p_214450_)).executes(p_180371_ -> ItemCommands.m_180331_((CommandSourceStack)p_180371_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180371_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180371_, "slot"), ItemArgument.m_120963_(p_180371_, "item").m_120980_(1, false)))).then(Commands.m_82129_("count", IntegerArgumentType.integer((int)1, (int)64)).executes(p_180369_ -> ItemCommands.m_180331_((CommandSourceStack)p_180369_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180369_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180369_, "slot"), ItemArgument.m_120963_(p_180369_, "item").m_120980_(IntegerArgumentType.getInteger((CommandContext)p_180369_, (String)"count"), true))))))).then(((LiteralArgumentBuilder)Commands.m_82127_("from").then(Commands.m_82127_("block").then(Commands.m_82129_("source", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)Commands.m_82129_("sourceSlot", SlotArgument.m_111276_()).executes(p_180367_ -> ItemCommands.m_180314_((CommandSourceStack)p_180367_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180367_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180367_, "sourceSlot"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180367_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180367_, "slot")))).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180365_ -> ItemCommands.m_180320_((CommandSourceStack)p_180365_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180365_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180365_, "sourceSlot"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180365_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180365_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180365_, "modifier")))))))).then(Commands.m_82127_("entity").then(Commands.m_82129_("source", EntityArgument.m_91449_()).then(((RequiredArgumentBuilder)Commands.m_82129_("sourceSlot", SlotArgument.m_111276_()).executes(p_180363_ -> ItemCommands.m_180270_((CommandSourceStack)p_180363_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_180363_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180363_, "sourceSlot"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180363_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180363_, "slot")))).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180359_ -> ItemCommands.m_180276_((CommandSourceStack)p_180359_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_180359_, "source"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180359_, "sourceSlot"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180359_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180359_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180359_, "modifier"))))))))))))).then(((LiteralArgumentBuilder)Commands.m_82127_("modify").then(Commands.m_82127_("block").then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(Commands.m_82129_("slot", SlotArgument.m_111276_()).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180351_ -> ItemCommands.m_180296_((CommandSourceStack)p_180351_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_180351_, "pos"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180351_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180351_, "modifier")))))))).then(Commands.m_82127_("entity").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("slot", SlotArgument.m_111276_()).then(Commands.m_82129_("modifier", ResourceLocationArgument.m_106984_()).suggests(f_180242_).executes(p_180251_ -> ItemCommands.m_180336_((CommandSourceStack)p_180251_.getSource(), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_180251_, "targets"), SlotArgument.m_111279_((CommandContext<CommandSourceStack>)p_180251_, "slot"), ResourceLocationArgument.m_171031_((CommandContext<CommandSourceStack>)p_180251_, "modifier")))))))));
    }

    private static int m_180296_(CommandSourceStack p_180297_, BlockPos p_180298_, int p_180299_, LootItemFunction p_180300_) throws CommandSyntaxException {
        Container $$4 = ItemCommands.m_180327_(p_180297_, p_180298_, f_180236_);
        if (p_180299_ < 0 || p_180299_ >= $$4.m_6643_()) {
            throw f_180237_.create((Object)p_180299_);
        }
        ItemStack $$5 = ItemCommands.m_180283_(p_180297_, p_180300_, $$4.m_8020_(p_180299_));
        $$4.m_6836_(p_180299_, $$5);
        p_180297_.m_81354_(Component.m_237110_("commands.item.block.set.success", p_180298_.m_123341_(), p_180298_.m_123342_(), p_180298_.m_123343_(), $$5.m_41611_()), true);
        return 1;
    }

    private static int m_180336_(CommandSourceStack p_180337_, Collection<? extends Entity> p_180338_, int p_180339_, LootItemFunction p_180340_) throws CommandSyntaxException {
        HashMap $$4 = Maps.newHashMapWithExpectedSize((int)p_180338_.size());
        for (Entity entity : p_180338_) {
            ItemStack $$7;
            SlotAccess $$6 = entity.m_141942_(p_180339_);
            if ($$6 == SlotAccess.f_147290_ || !$$6.m_142104_($$7 = ItemCommands.m_180283_(p_180337_, p_180340_, $$6.m_142196_().m_41777_()))) continue;
            $$4.put(entity, $$7);
            if (!(entity instanceof ServerPlayer)) continue;
            ((ServerPlayer)entity).f_36096_.m_38946_();
        }
        if ($$4.isEmpty()) {
            throw f_180240_.create((Object)p_180339_);
        }
        if ($$4.size() == 1) {
            Map.Entry $$8 = $$4.entrySet().iterator().next();
            p_180337_.m_81354_(Component.m_237110_("commands.item.entity.set.success.single", ((Entity)$$8.getKey()).m_5446_(), ((ItemStack)$$8.getValue()).m_41611_()), true);
        } else {
            p_180337_.m_81354_(Component.m_237110_("commands.item.entity.set.success.multiple", $$4.size()), true);
        }
        return $$4.size();
    }

    private static int m_180291_(CommandSourceStack p_180292_, BlockPos p_180293_, int p_180294_, ItemStack p_180295_) throws CommandSyntaxException {
        Container $$4 = ItemCommands.m_180327_(p_180292_, p_180293_, f_180236_);
        if (p_180294_ < 0 || p_180294_ >= $$4.m_6643_()) {
            throw f_180237_.create((Object)p_180294_);
        }
        $$4.m_6836_(p_180294_, p_180295_);
        p_180292_.m_81354_(Component.m_237110_("commands.item.block.set.success", p_180293_.m_123341_(), p_180293_.m_123342_(), p_180293_.m_123343_(), p_180295_.m_41611_()), true);
        return 1;
    }

    private static Container m_180327_(CommandSourceStack p_180328_, BlockPos p_180329_, Dynamic3CommandExceptionType p_180330_) throws CommandSyntaxException {
        BlockEntity $$3 = p_180328_.m_81372_().m_7702_(p_180329_);
        if (!($$3 instanceof Container)) {
            throw p_180330_.create((Object)p_180329_.m_123341_(), (Object)p_180329_.m_123342_(), (Object)p_180329_.m_123343_());
        }
        return (Container)((Object)$$3);
    }

    private static int m_180331_(CommandSourceStack p_180332_, Collection<? extends Entity> p_180333_, int p_180334_, ItemStack p_180335_) throws CommandSyntaxException {
        ArrayList $$4 = Lists.newArrayListWithCapacity((int)p_180333_.size());
        for (Entity entity : p_180333_) {
            SlotAccess $$6 = entity.m_141942_(p_180334_);
            if ($$6 == SlotAccess.f_147290_ || !$$6.m_142104_(p_180335_.m_41777_())) continue;
            $$4.add(entity);
            if (!(entity instanceof ServerPlayer)) continue;
            ((ServerPlayer)entity).f_36096_.m_38946_();
        }
        if ($$4.isEmpty()) {
            throw f_180241_.create((Object)p_180335_.m_41611_(), (Object)p_180334_);
        }
        if ($$4.size() == 1) {
            p_180332_.m_81354_(Component.m_237110_("commands.item.entity.set.success.single", ((Entity)$$4.iterator().next()).m_5446_(), p_180335_.m_41611_()), true);
        } else {
            p_180332_.m_81354_(Component.m_237110_("commands.item.entity.set.success.multiple", $$4.size(), p_180335_.m_41611_()), true);
        }
        return $$4.size();
    }

    private static int m_180314_(CommandSourceStack p_180315_, BlockPos p_180316_, int p_180317_, Collection<? extends Entity> p_180318_, int p_180319_) throws CommandSyntaxException {
        return ItemCommands.m_180331_(p_180315_, p_180318_, p_180319_, ItemCommands.m_180287_(p_180315_, p_180316_, p_180317_));
    }

    private static int m_180320_(CommandSourceStack p_180321_, BlockPos p_180322_, int p_180323_, Collection<? extends Entity> p_180324_, int p_180325_, LootItemFunction p_180326_) throws CommandSyntaxException {
        return ItemCommands.m_180331_(p_180321_, p_180324_, p_180325_, ItemCommands.m_180283_(p_180321_, p_180326_, ItemCommands.m_180287_(p_180321_, p_180322_, p_180323_)));
    }

    private static int m_180301_(CommandSourceStack p_180302_, BlockPos p_180303_, int p_180304_, BlockPos p_180305_, int p_180306_) throws CommandSyntaxException {
        return ItemCommands.m_180291_(p_180302_, p_180305_, p_180306_, ItemCommands.m_180287_(p_180302_, p_180303_, p_180304_));
    }

    private static int m_180307_(CommandSourceStack p_180308_, BlockPos p_180309_, int p_180310_, BlockPos p_180311_, int p_180312_, LootItemFunction p_180313_) throws CommandSyntaxException {
        return ItemCommands.m_180291_(p_180308_, p_180311_, p_180312_, ItemCommands.m_180283_(p_180308_, p_180313_, ItemCommands.m_180287_(p_180308_, p_180309_, p_180310_)));
    }

    private static int m_180257_(CommandSourceStack p_180258_, Entity p_180259_, int p_180260_, BlockPos p_180261_, int p_180262_) throws CommandSyntaxException {
        return ItemCommands.m_180291_(p_180258_, p_180261_, p_180262_, ItemCommands.m_180245_(p_180259_, p_180260_));
    }

    private static int m_180263_(CommandSourceStack p_180264_, Entity p_180265_, int p_180266_, BlockPos p_180267_, int p_180268_, LootItemFunction p_180269_) throws CommandSyntaxException {
        return ItemCommands.m_180291_(p_180264_, p_180267_, p_180268_, ItemCommands.m_180283_(p_180264_, p_180269_, ItemCommands.m_180245_(p_180265_, p_180266_)));
    }

    private static int m_180270_(CommandSourceStack p_180271_, Entity p_180272_, int p_180273_, Collection<? extends Entity> p_180274_, int p_180275_) throws CommandSyntaxException {
        return ItemCommands.m_180331_(p_180271_, p_180274_, p_180275_, ItemCommands.m_180245_(p_180272_, p_180273_));
    }

    private static int m_180276_(CommandSourceStack p_180277_, Entity p_180278_, int p_180279_, Collection<? extends Entity> p_180280_, int p_180281_, LootItemFunction p_180282_) throws CommandSyntaxException {
        return ItemCommands.m_180331_(p_180277_, p_180280_, p_180281_, ItemCommands.m_180283_(p_180277_, p_180282_, ItemCommands.m_180245_(p_180278_, p_180279_)));
    }

    private static ItemStack m_180283_(CommandSourceStack p_180284_, LootItemFunction p_180285_, ItemStack p_180286_) {
        ServerLevel $$3 = p_180284_.m_81372_();
        LootContext.Builder $$4 = new LootContext.Builder($$3).m_78972_(LootContextParams.f_81460_, p_180284_.m_81371_()).m_78984_(LootContextParams.f_81455_, p_180284_.m_81373_());
        return (ItemStack)p_180285_.apply(p_180286_, $$4.m_78975_(LootContextParamSets.f_81412_));
    }

    private static ItemStack m_180245_(Entity p_180246_, int p_180247_) throws CommandSyntaxException {
        SlotAccess $$2 = p_180246_.m_141942_(p_180247_);
        if ($$2 == SlotAccess.f_147290_) {
            throw f_180239_.create((Object)p_180247_);
        }
        return $$2.m_142196_().m_41777_();
    }

    private static ItemStack m_180287_(CommandSourceStack p_180288_, BlockPos p_180289_, int p_180290_) throws CommandSyntaxException {
        Container $$3 = ItemCommands.m_180327_(p_180288_, p_180289_, f_180238_);
        if (p_180290_ < 0 || p_180290_ >= $$3.m_6643_()) {
            throw f_180239_.create((Object)p_180290_);
        }
        return $$3.m_8020_(p_180290_).m_41777_();
    }
}

