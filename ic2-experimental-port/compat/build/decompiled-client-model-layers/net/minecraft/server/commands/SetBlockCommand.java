/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class SetBlockCommand {
    private static final SimpleCommandExceptionType f_138597_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.setblock.failed"));

    public static void m_214730_(CommandDispatcher<CommandSourceStack> p_214731_, CommandBuildContext p_214732_) {
        p_214731_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("setblock").requires(p_138606_ -> p_138606_.m_6761_(2))).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("block", BlockStateArgument.m_234650_(p_214732_)).executes(p_138618_ -> SetBlockCommand.m_138607_((CommandSourceStack)p_138618_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138618_, "pos"), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_138618_, "block"), Mode.REPLACE, null))).then(Commands.m_82127_("destroy").executes(p_138616_ -> SetBlockCommand.m_138607_((CommandSourceStack)p_138616_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138616_, "pos"), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_138616_, "block"), Mode.DESTROY, null)))).then(Commands.m_82127_("keep").executes(p_138614_ -> SetBlockCommand.m_138607_((CommandSourceStack)p_138614_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138614_, "pos"), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_138614_, "block"), Mode.REPLACE, p_180517_ -> p_180517_.m_61175_().m_46859_(p_180517_.m_61176_()))))).then(Commands.m_82127_("replace").executes(p_138604_ -> SetBlockCommand.m_138607_((CommandSourceStack)p_138604_.getSource(), BlockPosArgument.m_118242_((CommandContext<CommandSourceStack>)p_138604_, "pos"), BlockStateArgument.m_116123_((CommandContext<CommandSourceStack>)p_138604_, "block"), Mode.REPLACE, null))))));
    }

    private static int m_138607_(CommandSourceStack p_138608_, BlockPos p_138609_, BlockInput p_138610_, Mode p_138611_, @Nullable Predicate<BlockInWorld> p_138612_) throws CommandSyntaxException {
        boolean $$8;
        ServerLevel $$5 = p_138608_.m_81372_();
        if (p_138612_ != null && !p_138612_.test(new BlockInWorld($$5, p_138609_, true))) {
            throw f_138597_.create();
        }
        if (p_138611_ == Mode.DESTROY) {
            $$5.m_46961_(p_138609_, true);
            boolean $$6 = !p_138610_.m_114669_().m_60795_() || !$$5.m_8055_(p_138609_).m_60795_();
        } else {
            BlockEntity $$7 = $$5.m_7702_(p_138609_);
            Clearable.m_18908_($$7);
            $$8 = true;
        }
        if ($$8 && !p_138610_.m_114670_($$5, p_138609_, 2)) {
            throw f_138597_.create();
        }
        $$5.m_6289_(p_138609_, p_138610_.m_114669_().m_60734_());
        p_138608_.m_81354_(Component.m_237110_("commands.setblock.success", p_138609_.m_123341_(), p_138609_.m_123342_(), p_138609_.m_123343_()), true);
        return 1;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode REPLACE = new Mode();
        public static final /* enum */ Mode DESTROY = new Mode();
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_138632_) {
            return Enum.valueOf(Mode.class, p_138632_);
        }

        private static /* synthetic */ Mode[] m_180518_() {
            return new Mode[]{REPLACE, DESTROY};
        }

        static {
            $VALUES = Mode.m_180518_();
        }
    }

    public static interface Filter {
        @Nullable
        public BlockInput m_138619_(BoundingBox var1, BlockPos var2, BlockInput var3, ServerLevel var4);
    }
}

