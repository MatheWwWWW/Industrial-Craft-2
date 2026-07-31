/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

public class LocateCommand {
    private static final DynamicCommandExceptionType f_214451_ = new DynamicCommandExceptionType(p_201831_ -> Component.m_237110_("commands.locate.structure.not_found", p_201831_));
    private static final DynamicCommandExceptionType f_214452_ = new DynamicCommandExceptionType(p_207534_ -> Component.m_237110_("commands.locate.structure.invalid", p_207534_));
    private static final DynamicCommandExceptionType f_214453_ = new DynamicCommandExceptionType(p_214514_ -> Component.m_237110_("commands.locate.biome.not_found", p_214514_));
    private static final DynamicCommandExceptionType f_214454_ = new DynamicCommandExceptionType(p_214512_ -> Component.m_237110_("commands.locate.biome.invalid", p_214512_));
    private static final DynamicCommandExceptionType f_214455_ = new DynamicCommandExceptionType(p_214505_ -> Component.m_237110_("commands.locate.poi.not_found", p_214505_));
    private static final DynamicCommandExceptionType f_214456_ = new DynamicCommandExceptionType(p_214496_ -> Component.m_237110_("commands.locate.poi.invalid", p_214496_));
    private static final int f_214457_ = 100;
    private static final int f_214458_ = 6400;
    private static final int f_214459_ = 32;
    private static final int f_214460_ = 64;
    private static final int f_214461_ = 256;

    public static void m_137858_(CommandDispatcher<CommandSourceStack> p_137859_) {
        p_137859_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("locate").requires(p_214470_ -> p_214470_.m_6761_(2))).then(Commands.m_82127_("structure").then(Commands.m_82129_("structure", ResourceOrTagLocationArgument.m_210968_(Registry.f_235725_)).executes(p_214507_ -> LocateCommand.m_214471_((CommandSourceStack)p_214507_.getSource(), ResourceOrTagLocationArgument.m_210955_((CommandContext<CommandSourceStack>)p_214507_, "structure", Registry.f_235725_, f_214452_)))))).then(Commands.m_82127_("biome").then(Commands.m_82129_("biome", ResourceOrTagLocationArgument.m_210968_(Registry.f_122885_)).executes(p_214500_ -> LocateCommand.m_214501_((CommandSourceStack)p_214500_.getSource(), ResourceOrTagLocationArgument.m_210955_((CommandContext<CommandSourceStack>)p_214500_, "biome", Registry.f_122885_, f_214454_)))))).then(Commands.m_82127_("poi").then(Commands.m_82129_("poi", ResourceOrTagLocationArgument.m_210968_(Registry.f_122810_)).executes(p_214465_ -> LocateCommand.m_214508_((CommandSourceStack)p_214465_.getSource(), ResourceOrTagLocationArgument.m_210955_((CommandContext<CommandSourceStack>)p_214465_, "poi", Registry.f_122810_, f_214456_))))));
    }

    private static Optional<? extends HolderSet.ListBacked<Structure>> m_214483_(ResourceOrTagLocationArgument.Result<Structure> p_214484_, Registry<Structure> p_214485_) {
        return (Optional)p_214484_.m_207418_().map(p_214494_ -> p_214485_.m_203636_((ResourceKey<Structure>)p_214494_).map(p_214491_ -> HolderSet.m_205809_(p_214491_)), p_214485_::m_203431_);
    }

    private static int m_214471_(CommandSourceStack p_214472_, ResourceOrTagLocationArgument.Result<Structure> p_214473_) throws CommandSyntaxException {
        Registry<Structure> $$2 = p_214472_.m_81372_().m_5962_().m_175515_(Registry.f_235725_);
        HolderSet $$3 = LocateCommand.m_214483_(p_214473_, $$2).orElseThrow(() -> f_214452_.create((Object)p_214473_.m_207276_()));
        BlockPos $$4 = new BlockPos(p_214472_.m_81371_());
        ServerLevel $$5 = p_214472_.m_81372_();
        Pair<BlockPos, Holder<Structure>> $$6 = $$5.m_7726_().m_8481_().m_223037_($$5, $$3, $$4, 100, false);
        if ($$6 == null) {
            throw f_214451_.create((Object)p_214473_.m_207276_());
        }
        return LocateCommand.m_214474_(p_214472_, p_214473_, $$4, $$6, "commands.locate.structure.success", false);
    }

    private static int m_214501_(CommandSourceStack p_214502_, ResourceOrTagLocationArgument.Result<Biome> p_214503_) throws CommandSyntaxException {
        BlockPos $$2 = new BlockPos(p_214502_.m_81371_());
        Pair<BlockPos, Holder<Biome>> $$3 = p_214502_.m_81372_().m_215069_(p_214503_, $$2, 6400, 32, 64);
        if ($$3 == null) {
            throw f_214453_.create((Object)p_214503_.m_207276_());
        }
        return LocateCommand.m_214474_(p_214502_, p_214503_, $$2, $$3, "commands.locate.biome.success", true);
    }

    private static int m_214508_(CommandSourceStack p_214509_, ResourceOrTagLocationArgument.Result<PoiType> p_214510_) throws CommandSyntaxException {
        BlockPos $$2 = new BlockPos(p_214509_.m_81371_());
        ServerLevel $$3 = p_214509_.m_81372_();
        Optional<Pair<Holder<PoiType>, BlockPos>> $$4 = $$3.m_8904_().m_218002_(p_214510_, $$2, 256, PoiManager.Occupancy.ANY);
        if ($$4.isEmpty()) {
            throw f_214455_.create((Object)p_214510_.m_207276_());
        }
        return LocateCommand.m_214474_(p_214509_, p_214510_, $$2, $$4.get().swap(), "commands.locate.poi.success", false);
    }

    public static int m_214474_(CommandSourceStack p_214475_, ResourceOrTagLocationArgument.Result<?> p_214476_, BlockPos p_214477_, Pair<BlockPos, ? extends Holder<?>> p_214478_, String p_214479_, boolean p_214480_) {
        BlockPos $$6 = (BlockPos)p_214478_.getFirst();
        String $$7 = (String)p_214476_.m_207418_().map(p_214498_ -> p_214498_.m_135782_().toString(), p_214468_ -> "#" + p_214468_.f_203868_() + " (" + ((Holder)p_214478_.getSecond()).m_203543_().map(p_214463_ -> p_214463_.m_135782_().toString()).orElse("[unregistered]") + ")");
        int $$8 = p_214480_ ? Mth.m_14143_(Mth.m_14116_((float)p_214477_.m_123331_($$6))) : Mth.m_14143_(LocateCommand.m_137853_(p_214477_.m_123341_(), p_214477_.m_123343_(), $$6.m_123341_(), $$6.m_123343_()));
        String $$9 = p_214480_ ? String.valueOf($$6.m_123342_()) : "~";
        MutableComponent $$10 = ComponentUtils.m_130748_(Component.m_237110_("chat.coordinates", $$6.m_123341_(), $$9, $$6.m_123343_())).m_130938_(p_214489_ -> p_214489_.m_131140_(ChatFormatting.GREEN).m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + $$6.m_123341_() + " " + $$9 + " " + $$6.m_123343_())).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, Component.m_237115_("chat.coordinates.tooltip"))));
        p_214475_.m_81354_(Component.m_237110_(p_214479_, $$7, $$10, $$8), false);
        return $$8;
    }

    private static float m_137853_(int p_137854_, int p_137855_, int p_137856_, int p_137857_) {
        int $$4 = p_137856_ - p_137854_;
        int $$5 = p_137857_ - p_137855_;
        return Mth.m_14116_($$4 * $$4 + $$5 * $$5);
    }
}

