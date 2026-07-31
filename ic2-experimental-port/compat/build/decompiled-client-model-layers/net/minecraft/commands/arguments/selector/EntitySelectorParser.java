/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Doubles
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.selector;

import com.google.common.primitives.Doubles;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntitySelectorParser {
    public static final char f_175112_ = '@';
    private static final char f_175116_ = '[';
    private static final char f_175117_ = ']';
    public static final char f_175113_ = '=';
    private static final char f_175118_ = ',';
    public static final char f_175114_ = '!';
    public static final char f_175115_ = '#';
    private static final char f_175119_ = 'p';
    private static final char f_175120_ = 'a';
    private static final char f_175121_ = 'r';
    private static final char f_175122_ = 's';
    private static final char f_175123_ = 'e';
    public static final SimpleCommandExceptionType f_121190_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.invalid"));
    public static final DynamicCommandExceptionType f_121191_ = new DynamicCommandExceptionType(p_121301_ -> Component.m_237110_("argument.entity.selector.unknown", p_121301_));
    public static final SimpleCommandExceptionType f_121192_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.selector.not_allowed"));
    public static final SimpleCommandExceptionType f_121193_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.selector.missing"));
    public static final SimpleCommandExceptionType f_121194_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.options.unterminated"));
    public static final DynamicCommandExceptionType f_121195_ = new DynamicCommandExceptionType(p_121267_ -> Component.m_237110_("argument.entity.options.valueless", p_121267_));
    public static final BiConsumer<Vec3, List<? extends Entity>> f_121196_ = (p_121326_, p_121327_) -> {};
    public static final BiConsumer<Vec3, List<? extends Entity>> f_121197_ = (p_121313_, p_121314_) -> p_121314_.sort((p_175140_, p_175141_) -> Doubles.compare((double)p_175140_.m_20238_((Vec3)p_121313_), (double)p_175141_.m_20238_((Vec3)p_121313_)));
    public static final BiConsumer<Vec3, List<? extends Entity>> f_121198_ = (p_121298_, p_121299_) -> p_121299_.sort((p_175131_, p_175132_) -> Doubles.compare((double)p_175132_.m_20238_((Vec3)p_121298_), (double)p_175131_.m_20238_((Vec3)p_121298_)));
    public static final BiConsumer<Vec3, List<? extends Entity>> f_121199_ = (p_121264_, p_121265_) -> Collections.shuffle(p_121265_);
    public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> f_121200_ = (p_121363_, p_121364_) -> p_121363_.buildFuture();
    private final StringReader f_121201_;
    private final boolean f_121202_;
    private int f_121203_;
    private boolean f_121204_;
    private boolean f_121205_;
    private MinMaxBounds.Doubles f_121206_ = MinMaxBounds.Doubles.f_154779_;
    private MinMaxBounds.Ints f_121207_ = MinMaxBounds.Ints.f_55364_;
    @Nullable
    private Double f_121208_;
    @Nullable
    private Double f_121209_;
    @Nullable
    private Double f_121210_;
    @Nullable
    private Double f_121211_;
    @Nullable
    private Double f_121212_;
    @Nullable
    private Double f_121213_;
    private WrappedMinMaxBounds f_121214_ = WrappedMinMaxBounds.f_75350_;
    private WrappedMinMaxBounds f_121215_ = WrappedMinMaxBounds.f_75350_;
    private Predicate<Entity> f_121170_ = p_121321_ -> true;
    private BiConsumer<Vec3, List<? extends Entity>> f_121171_ = f_121196_;
    private boolean f_121172_;
    @Nullable
    private String f_121173_;
    private int f_121174_;
    @Nullable
    private UUID f_121175_;
    private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> f_121176_ = f_121200_;
    private boolean f_121177_;
    private boolean f_121178_;
    private boolean f_121179_;
    private boolean f_121180_;
    private boolean f_121181_;
    private boolean f_121182_;
    private boolean f_121183_;
    private boolean f_121184_;
    @Nullable
    private EntityType<?> f_121185_;
    private boolean f_121186_;
    private boolean f_121187_;
    private boolean f_121188_;
    private boolean f_121189_;

    public EntitySelectorParser(StringReader p_121218_) {
        this(p_121218_, true);
    }

    public EntitySelectorParser(StringReader p_121220_, boolean p_121221_) {
        this.f_121201_ = p_121220_;
        this.f_121202_ = p_121221_;
    }

    public EntitySelector m_121230_() {
        Function<Vec3, Vec3> $$5;
        AABB $$3;
        if (this.f_121211_ != null || this.f_121212_ != null || this.f_121213_ != null) {
            AABB $$0 = this.m_121233_(this.f_121211_ == null ? 0.0 : this.f_121211_, this.f_121212_ == null ? 0.0 : this.f_121212_, this.f_121213_ == null ? 0.0 : this.f_121213_);
        } else if (this.f_121206_.m_55326_() != null) {
            double $$1 = (Double)this.f_121206_.m_55326_();
            AABB $$2 = new AABB(-$$1, -$$1, -$$1, $$1 + 1.0, $$1 + 1.0, $$1 + 1.0);
        } else {
            $$3 = null;
        }
        if (this.f_121208_ == null && this.f_121209_ == null && this.f_121210_ == null) {
            Function<Vec3, Vec3> $$4 = p_121292_ -> p_121292_;
        } else {
            $$5 = p_121258_ -> new Vec3(this.f_121208_ == null ? p_121258_.f_82479_ : this.f_121208_, this.f_121209_ == null ? p_121258_.f_82480_ : this.f_121209_, this.f_121210_ == null ? p_121258_.f_82481_ : this.f_121210_);
        }
        return new EntitySelector(this.f_121203_, this.f_121204_, this.f_121205_, this.f_121170_, this.f_121206_, $$5, $$3, this.f_121171_, this.f_121172_, this.f_121173_, this.f_121175_, this.f_121185_, this.f_121189_);
    }

    private AABB m_121233_(double p_121234_, double p_121235_, double p_121236_) {
        boolean $$3 = p_121234_ < 0.0;
        boolean $$4 = p_121235_ < 0.0;
        boolean $$5 = p_121236_ < 0.0;
        double $$6 = $$3 ? p_121234_ : 0.0;
        double $$7 = $$4 ? p_121235_ : 0.0;
        double $$8 = $$5 ? p_121236_ : 0.0;
        double $$9 = ($$3 ? 0.0 : p_121234_) + 1.0;
        double $$10 = ($$4 ? 0.0 : p_121235_) + 1.0;
        double $$11 = ($$5 ? 0.0 : p_121236_) + 1.0;
        return new AABB($$6, $$7, $$8, $$9, $$10, $$11);
    }

    private void m_121229_() {
        if (this.f_121214_ != WrappedMinMaxBounds.f_75350_) {
            this.f_121170_ = this.f_121170_.and(this.m_121254_(this.f_121214_, Entity::m_146909_));
        }
        if (this.f_121215_ != WrappedMinMaxBounds.f_75350_) {
            this.f_121170_ = this.f_121170_.and(this.m_121254_(this.f_121215_, Entity::m_146908_));
        }
        if (!this.f_121207_.m_55327_()) {
            this.f_121170_ = this.f_121170_.and(p_175126_ -> {
                if (!(p_175126_ instanceof ServerPlayer)) {
                    return false;
                }
                return this.f_121207_.m_55390_(((ServerPlayer)p_175126_).f_36078_);
            });
        }
    }

    private Predicate<Entity> m_121254_(WrappedMinMaxBounds p_121255_, ToDoubleFunction<Entity> p_121256_) {
        double $$2 = Mth.m_14177_(p_121255_.m_75358_() == null ? 0.0f : p_121255_.m_75358_().floatValue());
        double $$3 = Mth.m_14177_(p_121255_.m_75366_() == null ? 359.0f : p_121255_.m_75366_().floatValue());
        return p_175137_ -> {
            double $$4 = Mth.m_14175_(p_121256_.applyAsDouble((Entity)p_175137_));
            if ($$2 > $$3) {
                return $$4 >= $$2 || $$4 <= $$3;
            }
            return $$4 >= $$2 && $$4 <= $$3;
        };
    }

    protected void m_121281_() throws CommandSyntaxException {
        this.f_121189_ = true;
        this.f_121176_ = this::m_121322_;
        if (!this.f_121201_.canRead()) {
            throw f_121193_.createWithContext((ImmutableStringReader)this.f_121201_);
        }
        int $$0 = this.f_121201_.getCursor();
        char $$1 = this.f_121201_.read();
        if ($$1 == 'p') {
            this.f_121203_ = 1;
            this.f_121204_ = false;
            this.f_121171_ = f_121197_;
            this.m_121241_(EntityType.f_20532_);
        } else if ($$1 == 'a') {
            this.f_121203_ = Integer.MAX_VALUE;
            this.f_121204_ = false;
            this.f_121171_ = f_121196_;
            this.m_121241_(EntityType.f_20532_);
        } else if ($$1 == 'r') {
            this.f_121203_ = 1;
            this.f_121204_ = false;
            this.f_121171_ = f_121199_;
            this.m_121241_(EntityType.f_20532_);
        } else if ($$1 == 's') {
            this.f_121203_ = 1;
            this.f_121204_ = true;
            this.f_121172_ = true;
        } else if ($$1 == 'e') {
            this.f_121203_ = Integer.MAX_VALUE;
            this.f_121204_ = true;
            this.f_121171_ = f_121196_;
            this.f_121170_ = Entity::m_6084_;
        } else {
            this.f_121201_.setCursor($$0);
            throw f_121191_.createWithContext((ImmutableStringReader)this.f_121201_, (Object)("@" + String.valueOf($$1)));
        }
        this.f_121176_ = this::m_121333_;
        if (this.f_121201_.canRead() && this.f_121201_.peek() == '[') {
            this.f_121201_.skip();
            this.f_121176_ = this::m_121341_;
            this.m_121317_();
        }
    }

    protected void m_121304_() throws CommandSyntaxException {
        if (this.f_121201_.canRead()) {
            this.f_121176_ = this::m_121309_;
        }
        int $$0 = this.f_121201_.getCursor();
        String $$1 = this.f_121201_.readString();
        try {
            this.f_121175_ = UUID.fromString($$1);
            this.f_121204_ = true;
        }
        catch (IllegalArgumentException $$2) {
            if ($$1.isEmpty() || $$1.length() > 16) {
                this.f_121201_.setCursor($$0);
                throw f_121190_.createWithContext((ImmutableStringReader)this.f_121201_);
            }
            this.f_121204_ = false;
            this.f_121173_ = $$1;
        }
        this.f_121203_ = 1;
    }

    protected void m_121317_() throws CommandSyntaxException {
        this.f_121176_ = this::m_121347_;
        this.f_121201_.skipWhitespace();
        while (this.f_121201_.canRead() && this.f_121201_.peek() != ']') {
            this.f_121201_.skipWhitespace();
            int $$0 = this.f_121201_.getCursor();
            String $$1 = this.f_121201_.readString();
            EntitySelectorOptions.Modifier $$2 = EntitySelectorOptions.m_121447_(this, $$1, $$0);
            this.f_121201_.skipWhitespace();
            if (!this.f_121201_.canRead() || this.f_121201_.peek() != '=') {
                this.f_121201_.setCursor($$0);
                throw f_121195_.createWithContext((ImmutableStringReader)this.f_121201_, (Object)$$1);
            }
            this.f_121201_.skip();
            this.f_121201_.skipWhitespace();
            this.f_121176_ = f_121200_;
            $$2.m_121563_(this);
            this.f_121201_.skipWhitespace();
            this.f_121176_ = this::m_121353_;
            if (!this.f_121201_.canRead()) continue;
            if (this.f_121201_.peek() == ',') {
                this.f_121201_.skip();
                this.f_121176_ = this::m_121347_;
                continue;
            }
            if (this.f_121201_.peek() == ']') break;
            throw f_121194_.createWithContext((ImmutableStringReader)this.f_121201_);
        }
        if (!this.f_121201_.canRead()) {
            throw f_121194_.createWithContext((ImmutableStringReader)this.f_121201_);
        }
        this.f_121201_.skip();
        this.f_121176_ = f_121200_;
    }

    public boolean m_121330_() {
        this.f_121201_.skipWhitespace();
        if (this.f_121201_.canRead() && this.f_121201_.peek() == '!') {
            this.f_121201_.skip();
            this.f_121201_.skipWhitespace();
            return true;
        }
        return false;
    }

    public boolean m_121338_() {
        this.f_121201_.skipWhitespace();
        if (this.f_121201_.canRead() && this.f_121201_.peek() == '#') {
            this.f_121201_.skip();
            this.f_121201_.skipWhitespace();
            return true;
        }
        return false;
    }

    public StringReader m_121346_() {
        return this.f_121201_;
    }

    public void m_121272_(Predicate<Entity> p_121273_) {
        this.f_121170_ = this.f_121170_.and(p_121273_);
    }

    public void m_121352_() {
        this.f_121205_ = true;
    }

    public MinMaxBounds.Doubles m_175142_() {
        return this.f_121206_;
    }

    public void m_175127_(MinMaxBounds.Doubles p_175128_) {
        this.f_121206_ = p_175128_;
    }

    public MinMaxBounds.Ints m_121361_() {
        return this.f_121207_;
    }

    public void m_121245_(MinMaxBounds.Ints p_121246_) {
        this.f_121207_ = p_121246_;
    }

    public WrappedMinMaxBounds m_121367_() {
        return this.f_121214_;
    }

    public void m_121252_(WrappedMinMaxBounds p_121253_) {
        this.f_121214_ = p_121253_;
    }

    public WrappedMinMaxBounds m_121370_() {
        return this.f_121215_;
    }

    public void m_121289_(WrappedMinMaxBounds p_121290_) {
        this.f_121215_ = p_121290_;
    }

    @Nullable
    public Double m_121371_() {
        return this.f_121208_;
    }

    @Nullable
    public Double m_121372_() {
        return this.f_121209_;
    }

    @Nullable
    public Double m_121373_() {
        return this.f_121210_;
    }

    public void m_121231_(double p_121232_) {
        this.f_121208_ = p_121232_;
    }

    public void m_121282_(double p_121283_) {
        this.f_121209_ = p_121283_;
    }

    public void m_121305_(double p_121306_) {
        this.f_121210_ = p_121306_;
    }

    public void m_121318_(double p_121319_) {
        this.f_121211_ = p_121319_;
    }

    public void m_121331_(double p_121332_) {
        this.f_121212_ = p_121332_;
    }

    public void m_121339_(double p_121340_) {
        this.f_121213_ = p_121340_;
    }

    @Nullable
    public Double m_121374_() {
        return this.f_121211_;
    }

    @Nullable
    public Double m_121375_() {
        return this.f_121212_;
    }

    @Nullable
    public Double m_121376_() {
        return this.f_121213_;
    }

    public void m_121237_(int p_121238_) {
        this.f_121203_ = p_121238_;
    }

    public void m_121279_(boolean p_121280_) {
        this.f_121204_ = p_121280_;
    }

    public BiConsumer<Vec3, List<? extends Entity>> m_175146_() {
        return this.f_121171_;
    }

    public void m_121268_(BiConsumer<Vec3, List<? extends Entity>> p_121269_) {
        this.f_121171_ = p_121269_;
    }

    public EntitySelector m_121377_() throws CommandSyntaxException {
        this.f_121174_ = this.f_121201_.getCursor();
        this.f_121176_ = this::m_121286_;
        if (this.f_121201_.canRead() && this.f_121201_.peek() == '@') {
            if (!this.f_121202_) {
                throw f_121192_.createWithContext((ImmutableStringReader)this.f_121201_);
            }
            this.f_121201_.skip();
            this.m_121281_();
        } else {
            this.m_121304_();
        }
        this.m_121229_();
        return this.m_121230_();
    }

    private static void m_121247_(SuggestionsBuilder p_121248_) {
        p_121248_.suggest("@p", (Message)Component.m_237115_("argument.entity.selector.nearestPlayer"));
        p_121248_.suggest("@a", (Message)Component.m_237115_("argument.entity.selector.allPlayers"));
        p_121248_.suggest("@r", (Message)Component.m_237115_("argument.entity.selector.randomPlayer"));
        p_121248_.suggest("@s", (Message)Component.m_237115_("argument.entity.selector.self"));
        p_121248_.suggest("@e", (Message)Component.m_237115_("argument.entity.selector.allEntities"));
    }

    private CompletableFuture<Suggestions> m_121286_(SuggestionsBuilder p_121287_, Consumer<SuggestionsBuilder> p_121288_) {
        p_121288_.accept(p_121287_);
        if (this.f_121202_) {
            EntitySelectorParser.m_121247_(p_121287_);
        }
        return p_121287_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_121309_(SuggestionsBuilder p_121310_, Consumer<SuggestionsBuilder> p_121311_) {
        SuggestionsBuilder $$2 = p_121310_.createOffset(this.f_121174_);
        p_121311_.accept($$2);
        return p_121310_.add($$2).buildFuture();
    }

    private CompletableFuture<Suggestions> m_121322_(SuggestionsBuilder p_121323_, Consumer<SuggestionsBuilder> p_121324_) {
        SuggestionsBuilder $$2 = p_121323_.createOffset(p_121323_.getStart() - 1);
        EntitySelectorParser.m_121247_($$2);
        p_121323_.add($$2);
        return p_121323_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_121333_(SuggestionsBuilder p_121334_, Consumer<SuggestionsBuilder> p_121335_) {
        p_121334_.suggest(String.valueOf('['));
        return p_121334_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_121341_(SuggestionsBuilder p_121342_, Consumer<SuggestionsBuilder> p_121343_) {
        p_121342_.suggest(String.valueOf(']'));
        EntitySelectorOptions.m_121440_(this, p_121342_);
        return p_121342_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_121347_(SuggestionsBuilder p_121348_, Consumer<SuggestionsBuilder> p_121349_) {
        EntitySelectorOptions.m_121440_(this, p_121348_);
        return p_121348_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_121353_(SuggestionsBuilder p_121354_, Consumer<SuggestionsBuilder> p_121355_) {
        p_121354_.suggest(String.valueOf(','));
        p_121354_.suggest(String.valueOf(']'));
        return p_121354_.buildFuture();
    }

    private CompletableFuture<Suggestions> m_175143_(SuggestionsBuilder p_175144_, Consumer<SuggestionsBuilder> p_175145_) {
        p_175144_.suggest(String.valueOf('='));
        return p_175144_.buildFuture();
    }

    public boolean m_121378_() {
        return this.f_121172_;
    }

    public void m_121270_(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> p_121271_) {
        this.f_121176_ = p_121271_;
    }

    public CompletableFuture<Suggestions> m_121249_(SuggestionsBuilder p_121250_, Consumer<SuggestionsBuilder> p_121251_) {
        return this.f_121176_.apply(p_121250_.createOffset(this.f_121201_.getCursor()), p_121251_);
    }

    public boolean m_121379_() {
        return this.f_121177_;
    }

    public void m_121302_(boolean p_121303_) {
        this.f_121177_ = p_121303_;
    }

    public boolean m_121380_() {
        return this.f_121178_;
    }

    public void m_121315_(boolean p_121316_) {
        this.f_121178_ = p_121316_;
    }

    public boolean m_121381_() {
        return this.f_121179_;
    }

    public void m_121328_(boolean p_121329_) {
        this.f_121179_ = p_121329_;
    }

    public boolean m_121382_() {
        return this.f_121180_;
    }

    public void m_121336_(boolean p_121337_) {
        this.f_121180_ = p_121337_;
    }

    public boolean m_121383_() {
        return this.f_121181_;
    }

    public void m_121344_(boolean p_121345_) {
        this.f_121181_ = p_121345_;
    }

    public boolean m_121222_() {
        return this.f_121182_;
    }

    public void m_121350_(boolean p_121351_) {
        this.f_121182_ = p_121351_;
    }

    public boolean m_121223_() {
        return this.f_121183_;
    }

    public void m_121356_(boolean p_121357_) {
        this.f_121183_ = p_121357_;
    }

    public boolean m_175124_() {
        return this.f_121184_;
    }

    public void m_121359_(boolean p_121360_) {
        this.f_121184_ = p_121360_;
    }

    public void m_121241_(EntityType<?> p_121242_) {
        this.f_121185_ = p_121242_;
    }

    public void m_121224_() {
        this.f_121186_ = true;
    }

    public boolean m_121225_() {
        return this.f_121185_ != null;
    }

    public boolean m_121226_() {
        return this.f_121186_;
    }

    public boolean m_121227_() {
        return this.f_121187_;
    }

    public void m_121365_(boolean p_121366_) {
        this.f_121187_ = p_121366_;
    }

    public boolean m_121228_() {
        return this.f_121188_;
    }

    public void m_121368_(boolean p_121369_) {
        this.f_121188_ = p_121369_;
    }
}

