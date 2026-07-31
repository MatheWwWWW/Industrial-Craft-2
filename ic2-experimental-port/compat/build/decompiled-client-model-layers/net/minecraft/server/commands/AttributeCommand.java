/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeCommand {
    private static final DynamicCommandExceptionType f_136434_ = new DynamicCommandExceptionType(p_212443_ -> Component.m_237110_("commands.attribute.failed.entity", p_212443_));
    private static final Dynamic2CommandExceptionType f_136435_ = new Dynamic2CommandExceptionType((p_212445_, p_212446_) -> Component.m_237110_("commands.attribute.failed.no_attribute", p_212445_, p_212446_));
    private static final Dynamic3CommandExceptionType f_136436_ = new Dynamic3CommandExceptionType((p_212448_, p_212449_, p_212450_) -> Component.m_237110_("commands.attribute.failed.no_modifier", p_212449_, p_212448_, p_212450_));
    private static final Dynamic3CommandExceptionType f_136437_ = new Dynamic3CommandExceptionType((p_136497_, p_136498_, p_136499_) -> Component.m_237110_("commands.attribute.failed.modifier_already_present", p_136499_, p_136498_, p_136497_));

    public static void m_136444_(CommandDispatcher<CommandSourceStack> p_136445_) {
        p_136445_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("attribute").requires(p_212441_ -> p_212441_.m_6761_(2))).then(Commands.m_82129_("target", EntityArgument.m_91449_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("attribute", ResourceKeyArgument.m_212386_(Registry.f_122916_)).then(((LiteralArgumentBuilder)Commands.m_82127_("get").executes(p_212452_ -> AttributeCommand.m_136453_((CommandSourceStack)p_212452_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_212452_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_212452_, "attribute"), 1.0))).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes(p_136522_ -> AttributeCommand.m_136453_((CommandSourceStack)p_136522_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136522_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136522_, "attribute"), DoubleArgumentType.getDouble((CommandContext)p_136522_, (String)"scale")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("base").then(Commands.m_82127_("set").then(Commands.m_82129_("value", DoubleArgumentType.doubleArg()).executes(p_136520_ -> AttributeCommand.m_136502_((CommandSourceStack)p_136520_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136520_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136520_, "attribute"), DoubleArgumentType.getDouble((CommandContext)p_136520_, (String)"value")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("get").executes(p_136518_ -> AttributeCommand.m_136491_((CommandSourceStack)p_136518_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136518_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136518_, "attribute"), 1.0))).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes(p_136516_ -> AttributeCommand.m_136491_((CommandSourceStack)p_136516_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136516_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136516_, "attribute"), DoubleArgumentType.getDouble((CommandContext)p_136516_, (String)"scale"))))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("modifier").then(Commands.m_82127_("add").then(Commands.m_82129_("uuid", UuidArgument.m_113850_()).then(Commands.m_82129_("name", StringArgumentType.string()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("value", DoubleArgumentType.doubleArg()).then(Commands.m_82127_("add").executes(p_136514_ -> AttributeCommand.m_136469_((CommandSourceStack)p_136514_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136514_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136514_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136514_, "uuid"), StringArgumentType.getString((CommandContext)p_136514_, (String)"name"), DoubleArgumentType.getDouble((CommandContext)p_136514_, (String)"value"), AttributeModifier.Operation.ADDITION)))).then(Commands.m_82127_("multiply").executes(p_136512_ -> AttributeCommand.m_136469_((CommandSourceStack)p_136512_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136512_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136512_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136512_, "uuid"), StringArgumentType.getString((CommandContext)p_136512_, (String)"name"), DoubleArgumentType.getDouble((CommandContext)p_136512_, (String)"value"), AttributeModifier.Operation.MULTIPLY_TOTAL)))).then(Commands.m_82127_("multiply_base").executes(p_136510_ -> AttributeCommand.m_136469_((CommandSourceStack)p_136510_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136510_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136510_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136510_, "uuid"), StringArgumentType.getString((CommandContext)p_136510_, (String)"name"), DoubleArgumentType.getDouble((CommandContext)p_136510_, (String)"value"), AttributeModifier.Operation.MULTIPLY_BASE)))))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("uuid", UuidArgument.m_113850_()).executes(p_136508_ -> AttributeCommand.m_136458_((CommandSourceStack)p_136508_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136508_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136508_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136508_, "uuid")))))).then(Commands.m_82127_("value").then(Commands.m_82127_("get").then(((RequiredArgumentBuilder)Commands.m_82129_("uuid", UuidArgument.m_113850_()).executes(p_136501_ -> AttributeCommand.m_136463_((CommandSourceStack)p_136501_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136501_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136501_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136501_, "uuid"), 1.0))).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes(p_136490_ -> AttributeCommand.m_136463_((CommandSourceStack)p_136490_.getSource(), EntityArgument.m_91452_((CommandContext<CommandSourceStack>)p_136490_, "target"), ResourceKeyArgument.m_212370_((CommandContext<CommandSourceStack>)p_136490_, "attribute"), UuidArgument.m_113853_((CommandContext<CommandSourceStack>)p_136490_, "uuid"), DoubleArgumentType.getDouble((CommandContext)p_136490_, (String)"scale")))))))))));
    }

    private static AttributeInstance m_136441_(Entity p_136442_, Attribute p_136443_) throws CommandSyntaxException {
        AttributeInstance $$2 = AttributeCommand.m_136439_(p_136442_).m_21204_().m_22146_(p_136443_);
        if ($$2 == null) {
            throw f_136435_.create((Object)p_136442_.m_7755_(), (Object)Component.m_237115_(p_136443_.m_22087_()));
        }
        return $$2;
    }

    private static LivingEntity m_136439_(Entity p_136440_) throws CommandSyntaxException {
        if (!(p_136440_ instanceof LivingEntity)) {
            throw f_136434_.create((Object)p_136440_.m_7755_());
        }
        return (LivingEntity)p_136440_;
    }

    private static LivingEntity m_136486_(Entity p_136487_, Attribute p_136488_) throws CommandSyntaxException {
        LivingEntity $$2 = AttributeCommand.m_136439_(p_136487_);
        if (!$$2.m_21204_().m_22171_(p_136488_)) {
            throw f_136435_.create((Object)p_136487_.m_7755_(), (Object)Component.m_237115_(p_136488_.m_22087_()));
        }
        return $$2;
    }

    private static int m_136453_(CommandSourceStack p_136454_, Entity p_136455_, Attribute p_136456_, double p_136457_) throws CommandSyntaxException {
        LivingEntity $$4 = AttributeCommand.m_136486_(p_136455_, p_136456_);
        double $$5 = $$4.m_21133_(p_136456_);
        p_136454_.m_81354_(Component.m_237110_("commands.attribute.value.get.success", Component.m_237115_(p_136456_.m_22087_()), p_136455_.m_7755_(), $$5), false);
        return (int)($$5 * p_136457_);
    }

    private static int m_136491_(CommandSourceStack p_136492_, Entity p_136493_, Attribute p_136494_, double p_136495_) throws CommandSyntaxException {
        LivingEntity $$4 = AttributeCommand.m_136486_(p_136493_, p_136494_);
        double $$5 = $$4.m_21172_(p_136494_);
        p_136492_.m_81354_(Component.m_237110_("commands.attribute.base_value.get.success", Component.m_237115_(p_136494_.m_22087_()), p_136493_.m_7755_(), $$5), false);
        return (int)($$5 * p_136495_);
    }

    private static int m_136463_(CommandSourceStack p_136464_, Entity p_136465_, Attribute p_136466_, UUID p_136467_, double p_136468_) throws CommandSyntaxException {
        LivingEntity $$5 = AttributeCommand.m_136486_(p_136465_, p_136466_);
        AttributeMap $$6 = $$5.m_21204_();
        if (!$$6.m_22154_(p_136466_, p_136467_)) {
            throw f_136436_.create((Object)p_136465_.m_7755_(), (Object)Component.m_237115_(p_136466_.m_22087_()), (Object)p_136467_);
        }
        double $$7 = $$6.m_22173_(p_136466_, p_136467_);
        p_136464_.m_81354_(Component.m_237110_("commands.attribute.modifier.value.get.success", p_136467_, Component.m_237115_(p_136466_.m_22087_()), p_136465_.m_7755_(), $$7), false);
        return (int)($$7 * p_136468_);
    }

    private static int m_136502_(CommandSourceStack p_136503_, Entity p_136504_, Attribute p_136505_, double p_136506_) throws CommandSyntaxException {
        AttributeCommand.m_136441_(p_136504_, p_136505_).m_22100_(p_136506_);
        p_136503_.m_81354_(Component.m_237110_("commands.attribute.base_value.set.success", Component.m_237115_(p_136505_.m_22087_()), p_136504_.m_7755_(), p_136506_), false);
        return 1;
    }

    private static int m_136469_(CommandSourceStack p_136470_, Entity p_136471_, Attribute p_136472_, UUID p_136473_, String p_136474_, double p_136475_, AttributeModifier.Operation p_136476_) throws CommandSyntaxException {
        AttributeModifier $$8;
        AttributeInstance $$7 = AttributeCommand.m_136441_(p_136471_, p_136472_);
        if ($$7.m_22109_($$8 = new AttributeModifier(p_136473_, p_136474_, p_136475_, p_136476_))) {
            throw f_136437_.create((Object)p_136471_.m_7755_(), (Object)Component.m_237115_(p_136472_.m_22087_()), (Object)p_136473_);
        }
        $$7.m_22125_($$8);
        p_136470_.m_81354_(Component.m_237110_("commands.attribute.modifier.add.success", p_136473_, Component.m_237115_(p_136472_.m_22087_()), p_136471_.m_7755_()), false);
        return 1;
    }

    private static int m_136458_(CommandSourceStack p_136459_, Entity p_136460_, Attribute p_136461_, UUID p_136462_) throws CommandSyntaxException {
        AttributeInstance $$4 = AttributeCommand.m_136441_(p_136460_, p_136461_);
        if ($$4.m_22127_(p_136462_)) {
            p_136459_.m_81354_(Component.m_237110_("commands.attribute.modifier.remove.success", p_136462_, Component.m_237115_(p_136461_.m_22087_()), p_136460_.m_7755_()), false);
            return 1;
        }
        throw f_136436_.create((Object)p_136460_.m_7755_(), (Object)Component.m_237115_(p_136461_.m_22087_()), (Object)p_136462_);
    }
}

