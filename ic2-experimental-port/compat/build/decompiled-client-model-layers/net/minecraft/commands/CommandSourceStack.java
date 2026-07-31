/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ResultConsumer
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.OutgoingPlayerChatMessage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.TaskChainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class CommandSourceStack
implements SharedSuggestionProvider {
    public static final SimpleCommandExceptionType f_81286_ = new SimpleCommandExceptionType((Message)Component.m_237115_("permissions.requires.player"));
    public static final SimpleCommandExceptionType f_81287_ = new SimpleCommandExceptionType((Message)Component.m_237115_("permissions.requires.entity"));
    private final CommandSource f_81288_;
    private final Vec3 f_81289_;
    private final ServerLevel f_81290_;
    private final int f_81291_;
    private final String f_81292_;
    private final Component f_81293_;
    private final MinecraftServer f_81294_;
    private final boolean f_81295_;
    @Nullable
    private final Entity f_81296_;
    @Nullable
    private final ResultConsumer<CommandSourceStack> f_81297_;
    private final EntityAnchorArgument.Anchor f_81298_;
    private final Vec2 f_81299_;
    private final CommandSigningContext f_230878_;
    private final TaskChainer f_241659_;

    public CommandSourceStack(CommandSource p_81302_, Vec3 p_81303_, Vec2 p_81304_, ServerLevel p_81305_, int p_81306_, String p_81307_, Component p_81308_, MinecraftServer p_81309_, @Nullable Entity p_81310_) {
        this(p_81302_, p_81303_, p_81304_, p_81305_, p_81306_, p_81307_, p_81308_, p_81309_, p_81310_, false, (ResultConsumer<CommandSourceStack>)((ResultConsumer)(p_81361_, p_81362_, p_81363_) -> {}), EntityAnchorArgument.Anchor.FEET, CommandSigningContext.f_242494_, TaskChainer.f_241608_);
    }

    protected CommandSourceStack(CommandSource p_242362_, Vec3 p_242272_, Vec2 p_242166_, ServerLevel p_242273_, int p_242279_, String p_242187_, Component p_242467_, MinecraftServer p_242416_, @Nullable Entity p_242300_, boolean p_242243_, @Nullable ResultConsumer<CommandSourceStack> p_242375_, EntityAnchorArgument.Anchor p_242201_, CommandSigningContext p_242188_, TaskChainer p_242249_) {
        this.f_81288_ = p_242362_;
        this.f_81289_ = p_242272_;
        this.f_81290_ = p_242273_;
        this.f_81295_ = p_242243_;
        this.f_81296_ = p_242300_;
        this.f_81291_ = p_242279_;
        this.f_81292_ = p_242187_;
        this.f_81293_ = p_242467_;
        this.f_81294_ = p_242416_;
        this.f_81297_ = p_242375_;
        this.f_81298_ = p_242201_;
        this.f_81299_ = p_242166_;
        this.f_230878_ = p_242188_;
        this.f_241659_ = p_242249_;
    }

    public CommandSourceStack m_165484_(CommandSource p_165485_) {
        if (this.f_81288_ == p_165485_) {
            return this;
        }
        return new CommandSourceStack(p_165485_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81329_(Entity p_81330_) {
        if (this.f_81296_ == p_81330_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, p_81330_.m_7755_().getString(), p_81330_.m_5446_(), this.f_81294_, p_81330_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81348_(Vec3 p_81349_) {
        if (this.f_81289_.equals(p_81349_)) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, p_81349_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81346_(Vec2 p_81347_) {
        if (this.f_81299_.m_82476_(p_81347_)) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, p_81347_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81334_(ResultConsumer<CommandSourceStack> p_81335_) {
        if (Objects.equals(this.f_81297_, p_81335_)) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, p_81335_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81336_(ResultConsumer<CommandSourceStack> p_81337_, BinaryOperator<ResultConsumer<CommandSourceStack>> p_81338_) {
        ResultConsumer $$2 = (ResultConsumer)p_81338_.apply(this.f_81297_, p_81337_);
        return this.m_81334_((ResultConsumer<CommandSourceStack>)$$2);
    }

    public CommandSourceStack m_81324_() {
        if (this.f_81295_ || this.f_81288_.m_142559_()) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, true, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81325_(int p_81326_) {
        if (p_81326_ == this.f_81291_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, p_81326_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81358_(int p_81359_) {
        if (p_81359_ <= this.f_81291_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, p_81359_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81350_(EntityAnchorArgument.Anchor p_81351_) {
        if (p_81351_ == this.f_81298_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, p_81351_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81327_(ServerLevel p_81328_) {
        if (p_81328_ == this.f_81290_) {
            return this;
        }
        double $$1 = DimensionType.m_63908_(this.f_81290_.m_6042_(), p_81328_.m_6042_());
        Vec3 $$2 = new Vec3(this.f_81289_.f_82479_ * $$1, this.f_81289_.f_82480_, this.f_81289_.f_82481_ * $$1);
        return new CommandSourceStack(this.f_81288_, $$2, this.f_81299_, p_81328_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, this.f_241659_);
    }

    public CommandSourceStack m_81331_(Entity p_81332_, EntityAnchorArgument.Anchor p_81333_) {
        return this.m_81364_(p_81333_.m_90377_(p_81332_));
    }

    public CommandSourceStack m_81364_(Vec3 p_81365_) {
        Vec3 $$1 = this.f_81298_.m_90379_(this);
        double $$2 = p_81365_.f_82479_ - $$1.f_82479_;
        double $$3 = p_81365_.f_82480_ - $$1.f_82480_;
        double $$4 = p_81365_.f_82481_ - $$1.f_82481_;
        double $$5 = Math.sqrt($$2 * $$2 + $$4 * $$4);
        float $$6 = Mth.m_14177_((float)(-(Mth.m_14136_($$3, $$5) * 57.2957763671875)));
        float $$7 = Mth.m_14177_((float)(Mth.m_14136_($$4, $$2) * 57.2957763671875) - 90.0f);
        return this.m_81346_(new Vec2($$6, $$7));
    }

    public CommandSourceStack m_230893_(CommandSigningContext p_230894_) {
        if (p_230894_ == this.f_230878_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, p_230894_, this.f_241659_);
    }

    public CommandSourceStack m_241952_(TaskChainer p_242228_) {
        if (p_242228_ == this.f_241659_) {
            return this;
        }
        return new CommandSourceStack(this.f_81288_, this.f_81289_, this.f_81299_, this.f_81290_, this.f_81291_, this.f_81292_, this.f_81293_, this.f_81294_, this.f_81296_, this.f_81295_, this.f_81297_, this.f_81298_, this.f_230878_, p_242228_);
    }

    public Component m_81357_() {
        return this.f_81293_;
    }

    public String m_81368_() {
        return this.f_81292_;
    }

    public ChatSender m_230895_() {
        if (this.f_81296_ != null) {
            return this.f_81296_.m_217000_();
        }
        return ChatSender.f_240891_;
    }

    @Override
    public boolean m_6761_(int p_81370_) {
        return this.f_81291_ >= p_81370_;
    }

    public Vec3 m_81371_() {
        return this.f_81289_;
    }

    public ServerLevel m_81372_() {
        return this.f_81290_;
    }

    @Nullable
    public Entity m_81373_() {
        return this.f_81296_;
    }

    public Entity m_81374_() throws CommandSyntaxException {
        if (this.f_81296_ == null) {
            throw f_81287_.create();
        }
        return this.f_81296_;
    }

    public ServerPlayer m_81375_() throws CommandSyntaxException {
        Entity entity = this.f_81296_;
        if (entity instanceof ServerPlayer) {
            ServerPlayer $$0 = (ServerPlayer)entity;
            return $$0;
        }
        throw f_81286_.create();
    }

    @Nullable
    public ServerPlayer m_230896_() {
        ServerPlayer $$0;
        Entity entity = this.f_81296_;
        return entity instanceof ServerPlayer ? ($$0 = (ServerPlayer)entity) : null;
    }

    public boolean m_230897_() {
        return this.f_81296_ instanceof ServerPlayer;
    }

    public Vec2 m_81376_() {
        return this.f_81299_;
    }

    public MinecraftServer m_81377_() {
        return this.f_81294_;
    }

    public EntityAnchorArgument.Anchor m_81378_() {
        return this.f_81298_;
    }

    public CommandSigningContext m_230898_() {
        return this.f_230878_;
    }

    public TaskChainer m_241923_() {
        return this.f_241659_;
    }

    public boolean m_243061_(ServerPlayer p_243268_) {
        ServerPlayer $$1 = this.m_230896_();
        if (p_243268_ == $$1) {
            return false;
        }
        return $$1 != null && $$1.m_143387_() || p_243268_.m_143387_();
    }

    public void m_243079_(OutgoingPlayerChatMessage p_243226_, boolean p_243216_, ChatType.Bound p_243244_) {
        if (this.f_81295_) {
            return;
        }
        ServerPlayer $$3 = this.m_230896_();
        if ($$3 != null) {
            $$3.m_243093_(p_243226_, p_243216_, p_243244_);
        } else {
            this.f_81288_.m_213846_(p_243244_.m_240977_(p_243226_.m_240962_()));
        }
    }

    public void m_243053_(Component p_243331_) {
        if (this.f_81295_) {
            return;
        }
        ServerPlayer $$1 = this.m_230896_();
        if ($$1 != null) {
            $$1.m_213846_(p_243331_);
        } else {
            this.f_81288_.m_213846_(p_243331_);
        }
    }

    public void m_81354_(Component p_81355_, boolean p_81356_) {
        if (this.f_81288_.m_6999_() && !this.f_81295_) {
            this.f_81288_.m_213846_(p_81355_);
        }
        if (p_81356_ && this.f_81288_.m_6102_() && !this.f_81295_) {
            this.m_81366_(p_81355_);
        }
    }

    private void m_81366_(Component p_81367_) {
        MutableComponent $$1 = Component.m_237110_("chat.type.admin", this.m_81357_(), p_81367_).m_130944_(ChatFormatting.GRAY, ChatFormatting.ITALIC);
        if (this.f_81294_.m_129900_().m_46207_(GameRules.f_46144_)) {
            for (ServerPlayer $$2 : this.f_81294_.m_6846_().m_11314_()) {
                if ($$2 == this.f_81288_ || !this.f_81294_.m_6846_().m_11303_($$2.m_36316_())) continue;
                $$2.m_213846_($$1);
            }
        }
        if (this.f_81288_ != this.f_81294_ && this.f_81294_.m_129900_().m_46207_(GameRules.f_46141_)) {
            this.f_81294_.m_213846_($$1);
        }
    }

    public void m_81352_(Component p_81353_) {
        if (this.f_81288_.m_7028_() && !this.f_81295_) {
            this.f_81288_.m_213846_(Component.m_237119_().m_7220_(p_81353_).m_130940_(ChatFormatting.RED));
        }
    }

    public void m_81342_(CommandContext<CommandSourceStack> p_81343_, boolean p_81344_, int p_81345_) {
        if (this.f_81297_ != null) {
            this.f_81297_.onCommandComplete(p_81343_, p_81344_, p_81345_);
        }
    }

    @Override
    public Collection<String> m_5982_() {
        return Lists.newArrayList((Object[])this.f_81294_.m_7641_());
    }

    @Override
    public Collection<String> m_5983_() {
        return this.f_81294_.m_129896_().m_83488_();
    }

    @Override
    public Collection<ResourceLocation> m_5984_() {
        return Registry.f_122821_.m_6566_();
    }

    @Override
    public Stream<ResourceLocation> m_6860_() {
        return this.f_81294_.m_129894_().m_44073_();
    }

    @Override
    public CompletableFuture<Suggestions> m_212155_(CommandContext<?> p_212324_) {
        return Suggestions.empty();
    }

    @Override
    public CompletableFuture<Suggestions> m_212095_(ResourceKey<? extends Registry<?>> p_212330_, SharedSuggestionProvider.ElementSuggestionType p_212331_, SuggestionsBuilder p_212332_, CommandContext<?> p_212333_) {
        return this.m_5894_().m_6632_(p_212330_).map(p_212328_ -> {
            this.m_212335_((Registry<?>)p_212328_, p_212331_, p_212332_);
            return p_212332_.buildFuture();
        }).orElseGet(Suggestions::empty);
    }

    @Override
    public Set<ResourceKey<Level>> m_6553_() {
        return this.f_81294_.m_129784_();
    }

    @Override
    public RegistryAccess m_5894_() {
        return this.f_81294_.m_206579_();
    }
}

