/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
 */
package net.minecraft.server.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.scores.Team;

public class SpreadPlayersCommand {
    private static final int f_180523_ = 10000;
    private static final Dynamic4CommandExceptionType f_138693_ = new Dynamic4CommandExceptionType((p_138745_, p_138746_, p_138747_, p_138748_) -> Component.m_237110_("commands.spreadplayers.failed.teams", p_138745_, p_138746_, p_138747_, p_138748_));
    private static final Dynamic4CommandExceptionType f_138694_ = new Dynamic4CommandExceptionType((p_138723_, p_138724_, p_138725_, p_138726_) -> Component.m_237110_("commands.spreadplayers.failed.entities", p_138723_, p_138724_, p_138725_, p_138726_));
    private static final Dynamic2CommandExceptionType f_201848_ = new Dynamic2CommandExceptionType((p_201854_, p_201855_) -> Component.m_237110_("commands.spreadplayers.failed.invalid.height", p_201854_, p_201855_));

    public static void m_138696_(CommandDispatcher<CommandSourceStack> p_138697_) {
        p_138697_.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("spreadplayers").requires(p_201852_ -> p_201852_.m_6761_(2))).then(Commands.m_82129_("center", Vec2Argument.m_120822_()).then(Commands.m_82129_("spreadDistance", FloatArgumentType.floatArg((float)0.0f)).then(((RequiredArgumentBuilder)Commands.m_82129_("maxRange", FloatArgumentType.floatArg((float)1.0f)).then(Commands.m_82129_("respectTeams", BoolArgumentType.bool()).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes(p_138699_ -> SpreadPlayersCommand.m_138702_((CommandSourceStack)p_138699_.getSource(), Vec2Argument.m_120825_((CommandContext<CommandSourceStack>)p_138699_, "center"), FloatArgumentType.getFloat((CommandContext)p_138699_, (String)"spreadDistance"), FloatArgumentType.getFloat((CommandContext)p_138699_, (String)"maxRange"), ((CommandSourceStack)p_138699_.getSource()).m_81372_().m_151558_(), BoolArgumentType.getBool((CommandContext)p_138699_, (String)"respectTeams"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_138699_, "targets")))))).then(Commands.m_82127_("under").then(Commands.m_82129_("maxHeight", IntegerArgumentType.integer()).then(Commands.m_82129_("respectTeams", BoolArgumentType.bool()).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes(p_201850_ -> SpreadPlayersCommand.m_138702_((CommandSourceStack)p_201850_.getSource(), Vec2Argument.m_120825_((CommandContext<CommandSourceStack>)p_201850_, "center"), FloatArgumentType.getFloat((CommandContext)p_201850_, (String)"spreadDistance"), FloatArgumentType.getFloat((CommandContext)p_201850_, (String)"maxRange"), IntegerArgumentType.getInteger((CommandContext)p_201850_, (String)"maxHeight"), BoolArgumentType.getBool((CommandContext)p_201850_, (String)"respectTeams"), EntityArgument.m_91461_((CommandContext<CommandSourceStack>)p_201850_, "targets")))))))))));
    }

    private static int m_138702_(CommandSourceStack p_138703_, Vec2 p_138704_, float p_138705_, float p_138706_, int p_138707_, boolean p_138708_, Collection<? extends Entity> p_138709_) throws CommandSyntaxException {
        ServerLevel $$7 = p_138703_.m_81372_();
        int $$8 = $$7.m_141937_();
        if (p_138707_ < $$8) {
            throw f_201848_.create((Object)p_138707_, (Object)$$8);
        }
        RandomSource $$9 = RandomSource.m_216327_();
        double $$10 = p_138704_.f_82470_ - p_138706_;
        double $$11 = p_138704_.f_82471_ - p_138706_;
        double $$12 = p_138704_.f_82470_ + p_138706_;
        double $$13 = p_138704_.f_82471_ + p_138706_;
        Position[] $$14 = SpreadPlayersCommand.m_214733_($$9, p_138708_ ? SpreadPlayersCommand.m_138727_(p_138709_) : p_138709_.size(), $$10, $$11, $$12, $$13);
        SpreadPlayersCommand.m_214740_(p_138704_, p_138705_, $$7, $$9, $$10, $$11, $$12, $$13, p_138707_, $$14, p_138708_);
        double $$15 = SpreadPlayersCommand.m_138729_(p_138709_, $$7, $$14, p_138707_, p_138708_);
        p_138703_.m_81354_(Component.m_237110_("commands.spreadplayers.success." + (p_138708_ ? "teams" : "entities"), $$14.length, Float.valueOf(p_138704_.f_82470_), Float.valueOf(p_138704_.f_82471_), String.format(Locale.ROOT, "%.2f", $$15)), true);
        return $$14.length;
    }

    private static int m_138727_(Collection<? extends Entity> p_138728_) {
        HashSet $$1 = Sets.newHashSet();
        for (Entity entity : p_138728_) {
            if (entity instanceof Player) {
                $$1.add(entity.m_5647_());
                continue;
            }
            $$1.add(null);
        }
        return $$1.size();
    }

    private static void m_214740_(Vec2 p_214741_, double p_214742_, ServerLevel p_214743_, RandomSource p_214744_, double p_214745_, double p_214746_, double p_214747_, double p_214748_, int p_214749_, Position[] p_214750_, boolean p_214751_) throws CommandSyntaxException {
        int $$13;
        boolean $$11 = true;
        double $$12 = 3.4028234663852886E38;
        for ($$13 = 0; $$13 < 10000 && $$11; ++$$13) {
            $$11 = false;
            $$12 = 3.4028234663852886E38;
            for (int $$14 = 0; $$14 < p_214750_.length; ++$$14) {
                Position $$15 = p_214750_[$$14];
                int $$16 = 0;
                Position $$17 = new Position();
                for (int $$18 = 0; $$18 < p_214750_.length; ++$$18) {
                    if ($$14 == $$18) continue;
                    Position $$19 = p_214750_[$$18];
                    double $$20 = $$15.m_138767_($$19);
                    $$12 = Math.min($$20, $$12);
                    if (!($$20 < p_214742_)) continue;
                    ++$$16;
                    $$17.f_138749_ += $$19.f_138749_ - $$15.f_138749_;
                    $$17.f_138750_ += $$19.f_138750_ - $$15.f_138750_;
                }
                if ($$16 > 0) {
                    $$17.f_138749_ /= (double)$$16;
                    $$17.f_138750_ /= (double)$$16;
                    double $$21 = $$17.m_180525_();
                    if ($$21 > 0.0) {
                        $$17.m_138752_();
                        $$15.m_138776_($$17);
                    } else {
                        $$15.m_214752_(p_214744_, p_214745_, p_214746_, p_214747_, p_214748_);
                    }
                    $$11 = true;
                }
                if (!$$15.m_138753_(p_214745_, p_214746_, p_214747_, p_214748_)) continue;
                $$11 = true;
            }
            if ($$11) continue;
            for (Position $$22 : p_214750_) {
                if ($$22.m_138773_(p_214743_, p_214749_)) continue;
                $$22.m_214752_(p_214744_, p_214745_, p_214746_, p_214747_, p_214748_);
                $$11 = true;
            }
        }
        if ($$12 == 3.4028234663852886E38) {
            $$12 = 0.0;
        }
        if ($$13 >= 10000) {
            if (p_214751_) {
                throw f_138693_.create((Object)p_214750_.length, (Object)Float.valueOf(p_214741_.f_82470_), (Object)Float.valueOf(p_214741_.f_82471_), (Object)String.format(Locale.ROOT, "%.2f", $$12));
            }
            throw f_138694_.create((Object)p_214750_.length, (Object)Float.valueOf(p_214741_.f_82470_), (Object)Float.valueOf(p_214741_.f_82471_), (Object)String.format(Locale.ROOT, "%.2f", $$12));
        }
    }

    private static double m_138729_(Collection<? extends Entity> p_138730_, ServerLevel p_138731_, Position[] p_138732_, int p_138733_, boolean p_138734_) {
        double $$5 = 0.0;
        int $$6 = 0;
        HashMap $$7 = Maps.newHashMap();
        for (Entity entity : p_138730_) {
            Position $$11;
            if (p_138734_) {
                Team $$9;
                Team team = $$9 = entity instanceof Player ? entity.m_5647_() : null;
                if (!$$7.containsKey($$9)) {
                    $$7.put($$9, p_138732_[$$6++]);
                }
                Position $$10 = (Position)$$7.get($$9);
            } else {
                $$11 = p_138732_[$$6++];
            }
            entity.m_20324_((double)Mth.m_14107_($$11.f_138749_) + 0.5, $$11.m_138758_(p_138731_, p_138733_), (double)Mth.m_14107_($$11.f_138750_) + 0.5);
            double $$12 = Double.MAX_VALUE;
            for (Position $$13 : p_138732_) {
                if ($$11 == $$13) continue;
                double $$14 = $$11.m_138767_($$13);
                $$12 = Math.min($$14, $$12);
            }
            $$5 += $$12;
        }
        if (p_138730_.size() < 2) {
            return 0.0;
        }
        return $$5 /= (double)p_138730_.size();
    }

    private static Position[] m_214733_(RandomSource p_214734_, int p_214735_, double p_214736_, double p_214737_, double p_214738_, double p_214739_) {
        Position[] $$6 = new Position[p_214735_];
        for (int $$7 = 0; $$7 < $$6.length; ++$$7) {
            Position $$8 = new Position();
            $$8.m_214752_(p_214734_, p_214736_, p_214737_, p_214738_, p_214739_);
            $$6[$$7] = $$8;
        }
        return $$6;
    }

    static class Position {
        double f_138749_;
        double f_138750_;

        Position() {
        }

        double m_138767_(Position p_138768_) {
            double $$1 = this.f_138749_ - p_138768_.f_138749_;
            double $$2 = this.f_138750_ - p_138768_.f_138750_;
            return Math.sqrt($$1 * $$1 + $$2 * $$2);
        }

        void m_138752_() {
            double $$0 = this.m_180525_();
            this.f_138749_ /= $$0;
            this.f_138750_ /= $$0;
        }

        double m_180525_() {
            return Math.sqrt(this.f_138749_ * this.f_138749_ + this.f_138750_ * this.f_138750_);
        }

        public void m_138776_(Position p_138777_) {
            this.f_138749_ -= p_138777_.f_138749_;
            this.f_138750_ -= p_138777_.f_138750_;
        }

        public boolean m_138753_(double p_138754_, double p_138755_, double p_138756_, double p_138757_) {
            boolean $$4 = false;
            if (this.f_138749_ < p_138754_) {
                this.f_138749_ = p_138754_;
                $$4 = true;
            } else if (this.f_138749_ > p_138756_) {
                this.f_138749_ = p_138756_;
                $$4 = true;
            }
            if (this.f_138750_ < p_138755_) {
                this.f_138750_ = p_138755_;
                $$4 = true;
            } else if (this.f_138750_ > p_138757_) {
                this.f_138750_ = p_138757_;
                $$4 = true;
            }
            return $$4;
        }

        public int m_138758_(BlockGetter p_138759_, int p_138760_) {
            BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos(this.f_138749_, (double)(p_138760_ + 1), this.f_138750_);
            boolean $$3 = p_138759_.m_8055_($$2).m_60795_();
            $$2.m_122173_(Direction.DOWN);
            boolean $$4 = p_138759_.m_8055_($$2).m_60795_();
            while ($$2.m_123342_() > p_138759_.m_141937_()) {
                $$2.m_122173_(Direction.DOWN);
                boolean $$5 = p_138759_.m_8055_($$2).m_60795_();
                if (!$$5 && $$4 && $$3) {
                    return $$2.m_123342_() + 1;
                }
                $$3 = $$4;
                $$4 = $$5;
            }
            return p_138760_ + 1;
        }

        public boolean m_138773_(BlockGetter p_138774_, int p_138775_) {
            BlockPos $$2 = new BlockPos(this.f_138749_, (double)(this.m_138758_(p_138774_, p_138775_) - 1), this.f_138750_);
            BlockState $$3 = p_138774_.m_8055_($$2);
            Material $$4 = $$3.m_60767_();
            return $$2.m_123342_() < p_138775_ && !$$4.m_76332_() && $$4 != Material.f_76309_;
        }

        public void m_214752_(RandomSource p_214753_, double p_214754_, double p_214755_, double p_214756_, double p_214757_) {
            this.f_138749_ = Mth.m_216263_(p_214753_, p_214754_, p_214756_);
            this.f_138750_ = Mth.m_216263_(p_214753_, p_214755_, p_214757_);
        }
    }
}

