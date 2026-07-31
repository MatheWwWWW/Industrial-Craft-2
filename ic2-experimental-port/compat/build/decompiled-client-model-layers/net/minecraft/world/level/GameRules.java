/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicLike
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;

public class GameRules {
    public static final int f_151487_ = 3;
    static final Logger f_46128_ = LogUtils.getLogger();
    private static final Map<Key<?>, Type<?>> f_46129_ = Maps.newTreeMap(Comparator.comparing(p_46218_ -> p_46218_.f_46323_));
    public static final Key<BooleanValue> f_46131_ = GameRules.m_46189_("doFireTick", Category.UPDATES, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46132_ = GameRules.m_46189_("mobGriefing", Category.MOBS, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46133_ = GameRules.m_46189_("keepInventory", Category.PLAYER, BooleanValue.m_46250_(false));
    public static final Key<BooleanValue> f_46134_ = GameRules.m_46189_("doMobSpawning", Category.SPAWNING, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46135_ = GameRules.m_46189_("doMobLoot", Category.DROPS, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46136_ = GameRules.m_46189_("doTileDrops", Category.DROPS, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46137_ = GameRules.m_46189_("doEntityDrops", Category.DROPS, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46138_ = GameRules.m_46189_("commandBlockOutput", Category.CHAT, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46139_ = GameRules.m_46189_("naturalRegeneration", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46140_ = GameRules.m_46189_("doDaylightCycle", Category.UPDATES, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46141_ = GameRules.m_46189_("logAdminCommands", Category.CHAT, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46142_ = GameRules.m_46189_("showDeathMessages", Category.CHAT, BooleanValue.m_46250_(true));
    public static final Key<IntegerValue> f_46143_ = GameRules.m_46189_("randomTickSpeed", Category.UPDATES, IntegerValue.m_46312_(3));
    public static final Key<BooleanValue> f_46144_ = GameRules.m_46189_("sendCommandFeedback", Category.CHAT, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46145_ = GameRules.m_46189_("reducedDebugInfo", Category.MISC, BooleanValue.m_46252_(false, (p_46212_, p_46213_) -> {
        byte $$2 = p_46213_.m_46223_() ? (byte)22 : (byte)23;
        for (ServerPlayer $$3 : p_46212_.m_6846_().m_11314_()) {
            $$3.f_8906_.m_9829_(new ClientboundEntityEventPacket($$3, $$2));
        }
    }));
    public static final Key<BooleanValue> f_46146_ = GameRules.m_46189_("spectatorsGenerateChunks", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<IntegerValue> f_46147_ = GameRules.m_46189_("spawnRadius", Category.PLAYER, IntegerValue.m_46312_(10));
    public static final Key<BooleanValue> f_46148_ = GameRules.m_46189_("disableElytraMovementCheck", Category.PLAYER, BooleanValue.m_46250_(false));
    public static final Key<IntegerValue> f_46149_ = GameRules.m_46189_("maxEntityCramming", Category.MOBS, IntegerValue.m_46312_(24));
    public static final Key<BooleanValue> f_46150_ = GameRules.m_46189_("doWeatherCycle", Category.UPDATES, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46151_ = GameRules.m_46189_("doLimitedCrafting", Category.PLAYER, BooleanValue.m_46250_(false));
    public static final Key<IntegerValue> f_46152_ = GameRules.m_46189_("maxCommandChainLength", Category.MISC, IntegerValue.m_46312_(65536));
    public static final Key<BooleanValue> f_46153_ = GameRules.m_46189_("announceAdvancements", Category.CHAT, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46154_ = GameRules.m_46189_("disableRaids", Category.MOBS, BooleanValue.m_46250_(false));
    public static final Key<BooleanValue> f_46155_ = GameRules.m_46189_("doInsomnia", Category.SPAWNING, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46156_ = GameRules.m_46189_("doImmediateRespawn", Category.PLAYER, BooleanValue.m_46252_(false, (p_46200_, p_46201_) -> {
        for (ServerPlayer $$2 : p_46200_.m_6846_().m_11314_()) {
            $$2.f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132164_, p_46201_.m_46223_() ? 1.0f : 0.0f));
        }
    }));
    public static final Key<BooleanValue> f_46121_ = GameRules.m_46189_("drowningDamage", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46122_ = GameRules.m_46189_("fallDamage", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46123_ = GameRules.m_46189_("fireDamage", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_151485_ = GameRules.m_46189_("freezeDamage", Category.PLAYER, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46124_ = GameRules.m_46189_("doPatrolSpawning", Category.SPAWNING, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46125_ = GameRules.m_46189_("doTraderSpawning", Category.SPAWNING, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_220347_ = GameRules.m_46189_("doWardenSpawning", Category.SPAWNING, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46126_ = GameRules.m_46189_("forgiveDeadPlayers", Category.MOBS, BooleanValue.m_46250_(true));
    public static final Key<BooleanValue> f_46127_ = GameRules.m_46189_("universalAnger", Category.MOBS, BooleanValue.m_46250_(false));
    public static final Key<IntegerValue> f_151486_ = GameRules.m_46189_("playersSleepingPercentage", Category.PLAYER, IntegerValue.m_46312_(100));
    private final Map<Key<?>, Value<?>> f_46130_;

    private static <T extends Value<T>> Key<T> m_46189_(String p_46190_, Category p_46191_, Type<T> p_46192_) {
        Key $$3 = new Key(p_46190_, p_46191_);
        Type<T> $$4 = f_46129_.put($$3, p_46192_);
        if ($$4 != null) {
            throw new IllegalStateException("Duplicate game rule registration for " + p_46190_);
        }
        return $$3;
    }

    public GameRules(DynamicLike<?> p_46160_) {
        this();
        this.m_46183_(p_46160_);
    }

    public GameRules() {
        this.f_46130_ = (Map)f_46129_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, p_46210_ -> ((Type)p_46210_.getValue()).m_46352_()));
    }

    private GameRules(Map<Key<?>, Value<?>> p_46162_) {
        this.f_46130_ = p_46162_;
    }

    public <T extends Value<T>> T m_46170_(Key<T> p_46171_) {
        return (T)this.f_46130_.get(p_46171_);
    }

    public CompoundTag m_46163_() {
        CompoundTag $$0 = new CompoundTag();
        this.f_46130_.forEach((p_46197_, p_46198_) -> $$0.m_128359_(p_46197_.f_46323_, p_46198_.m_5831_()));
        return $$0;
    }

    private void m_46183_(DynamicLike<?> p_46184_) {
        this.f_46130_.forEach((p_46187_, p_46188_) -> p_46184_.get(p_46187_.f_46323_).asString().result().ifPresent(p_46188_::m_7377_));
    }

    public GameRules m_46202_() {
        return new GameRules((Map)this.f_46130_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, p_46194_ -> ((Value)p_46194_.getValue()).m_5590_())));
    }

    public static void m_46164_(GameRuleTypeVisitor p_46165_) {
        f_46129_.forEach((p_46205_, p_46206_) -> GameRules.m_46166_(p_46165_, p_46205_, p_46206_));
    }

    private static <T extends Value<T>> void m_46166_(GameRuleTypeVisitor p_46167_, Key<?> p_46168_, Type<?> p_46169_) {
        Key<?> $$3 = p_46168_;
        Type<?> $$4 = p_46169_;
        p_46167_.m_6889_($$3, $$4);
        $$4.m_46353_(p_46167_, $$3);
    }

    public void m_46176_(GameRules p_46177_, @Nullable MinecraftServer p_46178_) {
        p_46177_.f_46130_.keySet().forEach(p_46182_ -> this.m_46172_((Key)p_46182_, p_46177_, p_46178_));
    }

    private <T extends Value<T>> void m_46172_(Key<T> p_46173_, GameRules p_46174_, @Nullable MinecraftServer p_46175_) {
        T $$3 = p_46174_.m_46170_(p_46173_);
        ((Value)this.m_46170_(p_46173_)).m_5614_($$3, p_46175_);
    }

    public boolean m_46207_(Key<BooleanValue> p_46208_) {
        return this.m_46170_(p_46208_).m_46223_();
    }

    public int m_46215_(Key<IntegerValue> p_46216_) {
        return this.m_46170_(p_46216_).m_46288_();
    }

    public static final class Key<T extends Value<T>> {
        final String f_46323_;
        private final Category f_46324_;

        public Key(String p_46326_, Category p_46327_) {
            this.f_46323_ = p_46326_;
            this.f_46324_ = p_46327_;
        }

        public String toString() {
            return this.f_46323_;
        }

        public boolean equals(Object p_46334_) {
            if (this == p_46334_) {
                return true;
            }
            return p_46334_ instanceof Key && ((Key)p_46334_).f_46323_.equals(this.f_46323_);
        }

        public int hashCode() {
            return this.f_46323_.hashCode();
        }

        public String m_46328_() {
            return this.f_46323_;
        }

        public String m_46331_() {
            return "gamerule." + this.f_46323_;
        }

        public Category m_46332_() {
            return this.f_46324_;
        }
    }

    public static final class Category
    extends Enum<Category> {
        public static final /* enum */ Category PLAYER = new Category("gamerule.category.player");
        public static final /* enum */ Category MOBS = new Category("gamerule.category.mobs");
        public static final /* enum */ Category SPAWNING = new Category("gamerule.category.spawning");
        public static final /* enum */ Category DROPS = new Category("gamerule.category.drops");
        public static final /* enum */ Category UPDATES = new Category("gamerule.category.updates");
        public static final /* enum */ Category CHAT = new Category("gamerule.category.chat");
        public static final /* enum */ Category MISC = new Category("gamerule.category.misc");
        private final String f_46267_;
        private static final /* synthetic */ Category[] $VALUES;

        public static Category[] values() {
            return (Category[])$VALUES.clone();
        }

        public static Category valueOf(String p_46276_) {
            return Enum.valueOf(Category.class, p_46276_);
        }

        private Category(String p_46273_) {
            this.f_46267_ = p_46273_;
        }

        public String m_46274_() {
            return this.f_46267_;
        }

        private static /* synthetic */ Category[] m_151488_() {
            return new Category[]{PLAYER, MOBS, SPAWNING, DROPS, UPDATES, CHAT, MISC};
        }

        static {
            $VALUES = Category.m_151488_();
        }
    }

    public static class Type<T extends Value<T>> {
        private final Supplier<ArgumentType<?>> f_46337_;
        private final Function<Type<T>, T> f_46338_;
        final BiConsumer<MinecraftServer, T> f_46339_;
        private final VisitorCaller<T> f_46340_;

        Type(Supplier<ArgumentType<?>> p_46342_, Function<Type<T>, T> p_46343_, BiConsumer<MinecraftServer, T> p_46344_, VisitorCaller<T> p_46345_) {
            this.f_46337_ = p_46342_;
            this.f_46338_ = p_46343_;
            this.f_46339_ = p_46344_;
            this.f_46340_ = p_46345_;
        }

        public RequiredArgumentBuilder<CommandSourceStack, ?> m_46358_(String p_46359_) {
            return Commands.m_82129_(p_46359_, this.f_46337_.get());
        }

        public T m_46352_() {
            return (T)((Value)this.f_46338_.apply(this));
        }

        public void m_46353_(GameRuleTypeVisitor p_46354_, Key<T> p_46355_) {
            this.f_46340_.m_46374_(p_46354_, p_46355_, this);
        }
    }

    public static abstract class Value<T extends Value<T>> {
        protected final Type<T> f_46360_;

        public Value(Type<T> p_46362_) {
            this.f_46360_ = p_46362_;
        }

        protected abstract void m_5528_(CommandContext<CommandSourceStack> var1, String var2);

        public void m_46370_(CommandContext<CommandSourceStack> p_46371_, String p_46372_) {
            this.m_5528_(p_46371_, p_46372_);
            this.m_46368_(((CommandSourceStack)p_46371_.getSource()).m_81377_());
        }

        protected void m_46368_(@Nullable MinecraftServer p_46369_) {
            if (p_46369_ != null) {
                this.f_46360_.f_46339_.accept(p_46369_, (MinecraftServer)this.m_5589_());
            }
        }

        protected abstract void m_7377_(String var1);

        public abstract String m_5831_();

        public String toString() {
            return this.m_5831_();
        }

        public abstract int m_6855_();

        protected abstract T m_5589_();

        protected abstract T m_5590_();

        public abstract void m_5614_(T var1, @Nullable MinecraftServer var2);
    }

    public static interface GameRuleTypeVisitor {
        default public <T extends Value<T>> void m_6889_(Key<T> p_46278_, Type<T> p_46279_) {
        }

        default public void m_6891_(Key<BooleanValue> p_46280_, Type<BooleanValue> p_46281_) {
        }

        default public void m_6894_(Key<IntegerValue> p_46282_, Type<IntegerValue> p_46283_) {
        }
    }

    public static class BooleanValue
    extends Value<BooleanValue> {
        private boolean f_46219_;

        static Type<BooleanValue> m_46252_(boolean p_46253_, BiConsumer<MinecraftServer, BooleanValue> p_46254_) {
            return new Type<BooleanValue>(BoolArgumentType::bool, p_46242_ -> new BooleanValue((Type<BooleanValue>)p_46242_, p_46253_), p_46254_, GameRuleTypeVisitor::m_6891_);
        }

        static Type<BooleanValue> m_46250_(boolean p_46251_) {
            return BooleanValue.m_46252_(p_46251_, (p_46236_, p_46237_) -> {});
        }

        public BooleanValue(Type<BooleanValue> p_46221_, boolean p_46222_) {
            super(p_46221_);
            this.f_46219_ = p_46222_;
        }

        @Override
        protected void m_5528_(CommandContext<CommandSourceStack> p_46231_, String p_46232_) {
            this.f_46219_ = BoolArgumentType.getBool(p_46231_, (String)p_46232_);
        }

        public boolean m_46223_() {
            return this.f_46219_;
        }

        public void m_46246_(boolean p_46247_, @Nullable MinecraftServer p_46248_) {
            this.f_46219_ = p_46247_;
            this.m_46368_(p_46248_);
        }

        @Override
        public String m_5831_() {
            return Boolean.toString(this.f_46219_);
        }

        @Override
        protected void m_7377_(String p_46234_) {
            this.f_46219_ = Boolean.parseBoolean(p_46234_);
        }

        @Override
        public int m_6855_() {
            return this.f_46219_ ? 1 : 0;
        }

        @Override
        protected BooleanValue m_5589_() {
            return this;
        }

        @Override
        protected BooleanValue m_5590_() {
            return new BooleanValue(this.f_46360_, this.f_46219_);
        }

        @Override
        public void m_5614_(BooleanValue p_46225_, @Nullable MinecraftServer p_46226_) {
            this.f_46219_ = p_46225_.f_46219_;
            this.m_46368_(p_46226_);
        }

        @Override
        protected /* synthetic */ Value m_5590_() {
            return this.m_5590_();
        }

        @Override
        protected /* synthetic */ Value m_5589_() {
            return this.m_5589_();
        }
    }

    public static class IntegerValue
    extends Value<IntegerValue> {
        private int f_46284_;

        private static Type<IntegerValue> m_46294_(int p_46295_, BiConsumer<MinecraftServer, IntegerValue> p_46296_) {
            return new Type<IntegerValue>(IntegerArgumentType::integer, p_46293_ -> new IntegerValue((Type<IntegerValue>)p_46293_, p_46295_), p_46296_, GameRuleTypeVisitor::m_6894_);
        }

        static Type<IntegerValue> m_46312_(int p_46313_) {
            return IntegerValue.m_46294_(p_46313_, (p_46309_, p_46310_) -> {});
        }

        public IntegerValue(Type<IntegerValue> p_46286_, int p_46287_) {
            super(p_46286_);
            this.f_46284_ = p_46287_;
        }

        @Override
        protected void m_5528_(CommandContext<CommandSourceStack> p_46304_, String p_46305_) {
            this.f_46284_ = IntegerArgumentType.getInteger(p_46304_, (String)p_46305_);
        }

        public int m_46288_() {
            return this.f_46284_;
        }

        public void m_151489_(int p_151490_, @Nullable MinecraftServer p_151491_) {
            this.f_46284_ = p_151490_;
            this.m_46368_(p_151491_);
        }

        @Override
        public String m_5831_() {
            return Integer.toString(this.f_46284_);
        }

        @Override
        protected void m_7377_(String p_46307_) {
            this.f_46284_ = IntegerValue.m_46317_(p_46307_);
        }

        public boolean m_46314_(String p_46315_) {
            try {
                this.f_46284_ = Integer.parseInt(p_46315_);
                return true;
            }
            catch (NumberFormatException numberFormatException) {
                return false;
            }
        }

        private static int m_46317_(String p_46318_) {
            if (!p_46318_.isEmpty()) {
                try {
                    return Integer.parseInt(p_46318_);
                }
                catch (NumberFormatException $$1) {
                    f_46128_.warn("Failed to parse integer {}", (Object)p_46318_);
                }
            }
            return 0;
        }

        @Override
        public int m_6855_() {
            return this.f_46284_;
        }

        @Override
        protected IntegerValue m_5589_() {
            return this;
        }

        @Override
        protected IntegerValue m_5590_() {
            return new IntegerValue(this.f_46360_, this.f_46284_);
        }

        @Override
        public void m_5614_(IntegerValue p_46298_, @Nullable MinecraftServer p_46299_) {
            this.f_46284_ = p_46298_.f_46284_;
            this.m_46368_(p_46299_);
        }

        @Override
        protected /* synthetic */ Value m_5590_() {
            return this.m_5590_();
        }

        @Override
        protected /* synthetic */ Value m_5589_() {
            return this.m_5589_();
        }
    }

    static interface VisitorCaller<T extends Value<T>> {
        public void m_46374_(GameRuleTypeVisitor var1, Key<T> var2, Type<T> var3);
    }
}

