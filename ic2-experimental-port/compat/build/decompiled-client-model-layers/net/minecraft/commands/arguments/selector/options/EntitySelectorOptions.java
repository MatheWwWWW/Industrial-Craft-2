/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments.selector.options;

import com.google.common.collect.Maps;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Team;

public class EntitySelectorOptions {
    private static final Map<String, Option> f_121392_ = Maps.newHashMap();
    public static final DynamicCommandExceptionType f_121384_ = new DynamicCommandExceptionType(p_121520_ -> Component.m_237110_("argument.entity.options.unknown", p_121520_));
    public static final DynamicCommandExceptionType f_121385_ = new DynamicCommandExceptionType(p_121516_ -> Component.m_237110_("argument.entity.options.inapplicable", p_121516_));
    public static final SimpleCommandExceptionType f_121386_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.options.distance.negative"));
    public static final SimpleCommandExceptionType f_121387_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.options.level.negative"));
    public static final SimpleCommandExceptionType f_121388_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.entity.options.limit.toosmall"));
    public static final DynamicCommandExceptionType f_121389_ = new DynamicCommandExceptionType(p_121508_ -> Component.m_237110_("argument.entity.options.sort.irreversible", p_121508_));
    public static final DynamicCommandExceptionType f_121390_ = new DynamicCommandExceptionType(p_121493_ -> Component.m_237110_("argument.entity.options.mode.invalid", p_121493_));
    public static final DynamicCommandExceptionType f_121391_ = new DynamicCommandExceptionType(p_121452_ -> Component.m_237110_("argument.entity.options.type.invalid", p_121452_));

    private static void m_121453_(String p_121454_, Modifier p_121455_, Predicate<EntitySelectorParser> p_121456_, Component p_121457_) {
        f_121392_.put(p_121454_, new Option(p_121455_, p_121456_, p_121457_));
    }

    public static void m_121426_() {
        if (!f_121392_.isEmpty()) {
            return;
        }
        EntitySelectorOptions.m_121453_("name", p_121425_ -> {
            int $$1 = p_121425_.m_121346_().getCursor();
            boolean $$2 = p_121425_.m_121330_();
            String $$3 = p_121425_.m_121346_().readString();
            if (p_121425_.m_121380_() && !$$2) {
                p_121425_.m_121346_().setCursor($$1);
                throw f_121385_.createWithContext((ImmutableStringReader)p_121425_.m_121346_(), (Object)"name");
            }
            if ($$2) {
                p_121425_.m_121315_(true);
            } else {
                p_121425_.m_121302_(true);
            }
            p_121425_.m_121272_(p_175209_ -> p_175209_.m_7755_().getString().equals($$3) != $$2);
        }, p_121423_ -> !p_121423_.m_121379_(), Component.m_237115_("argument.entity.options.name.description"));
        EntitySelectorOptions.m_121453_("distance", p_121421_ -> {
            int $$1 = p_121421_.m_121346_().getCursor();
            MinMaxBounds.Doubles $$2 = MinMaxBounds.Doubles.m_154793_(p_121421_.m_121346_());
            if ($$2.m_55305_() != null && (Double)$$2.m_55305_() < 0.0 || $$2.m_55326_() != null && (Double)$$2.m_55326_() < 0.0) {
                p_121421_.m_121346_().setCursor($$1);
                throw f_121386_.createWithContext((ImmutableStringReader)p_121421_.m_121346_());
            }
            p_121421_.m_175127_($$2);
            p_121421_.m_121352_();
        }, p_121419_ -> p_121419_.m_175142_().m_55327_(), Component.m_237115_("argument.entity.options.distance.description"));
        EntitySelectorOptions.m_121453_("level", p_121417_ -> {
            int $$1 = p_121417_.m_121346_().getCursor();
            MinMaxBounds.Ints $$2 = MinMaxBounds.Ints.m_55375_(p_121417_.m_121346_());
            if ($$2.m_55305_() != null && (Integer)$$2.m_55305_() < 0 || $$2.m_55326_() != null && (Integer)$$2.m_55326_() < 0) {
                p_121417_.m_121346_().setCursor($$1);
                throw f_121387_.createWithContext((ImmutableStringReader)p_121417_.m_121346_());
            }
            p_121417_.m_121245_($$2);
            p_121417_.m_121279_(false);
        }, p_121415_ -> p_121415_.m_121361_().m_55327_(), Component.m_237115_("argument.entity.options.level.description"));
        EntitySelectorOptions.m_121453_("x", p_121413_ -> {
            p_121413_.m_121352_();
            p_121413_.m_121231_(p_121413_.m_121346_().readDouble());
        }, p_121411_ -> p_121411_.m_121371_() == null, Component.m_237115_("argument.entity.options.x.description"));
        EntitySelectorOptions.m_121453_("y", p_121409_ -> {
            p_121409_.m_121352_();
            p_121409_.m_121282_(p_121409_.m_121346_().readDouble());
        }, p_121407_ -> p_121407_.m_121372_() == null, Component.m_237115_("argument.entity.options.y.description"));
        EntitySelectorOptions.m_121453_("z", p_121405_ -> {
            p_121405_.m_121352_();
            p_121405_.m_121305_(p_121405_.m_121346_().readDouble());
        }, p_121403_ -> p_121403_.m_121373_() == null, Component.m_237115_("argument.entity.options.z.description"));
        EntitySelectorOptions.m_121453_("dx", p_121401_ -> {
            p_121401_.m_121352_();
            p_121401_.m_121318_(p_121401_.m_121346_().readDouble());
        }, p_121399_ -> p_121399_.m_121374_() == null, Component.m_237115_("argument.entity.options.dx.description"));
        EntitySelectorOptions.m_121453_("dy", p_121397_ -> {
            p_121397_.m_121352_();
            p_121397_.m_121331_(p_121397_.m_121346_().readDouble());
        }, p_121395_ -> p_121395_.m_121375_() == null, Component.m_237115_("argument.entity.options.dy.description"));
        EntitySelectorOptions.m_121453_("dz", p_121562_ -> {
            p_121562_.m_121352_();
            p_121562_.m_121339_(p_121562_.m_121346_().readDouble());
        }, p_121560_ -> p_121560_.m_121376_() == null, Component.m_237115_("argument.entity.options.dz.description"));
        EntitySelectorOptions.m_121453_("x_rotation", p_121558_ -> p_121558_.m_121252_(WrappedMinMaxBounds.m_75359_(p_121558_.m_121346_(), true, Mth::m_14177_)), p_121556_ -> p_121556_.m_121367_() == WrappedMinMaxBounds.f_75350_, Component.m_237115_("argument.entity.options.x_rotation.description"));
        EntitySelectorOptions.m_121453_("y_rotation", p_121554_ -> p_121554_.m_121289_(WrappedMinMaxBounds.m_75359_(p_121554_.m_121346_(), true, Mth::m_14177_)), p_121552_ -> p_121552_.m_121370_() == WrappedMinMaxBounds.f_75350_, Component.m_237115_("argument.entity.options.y_rotation.description"));
        EntitySelectorOptions.m_121453_("limit", p_121550_ -> {
            int $$1 = p_121550_.m_121346_().getCursor();
            int $$2 = p_121550_.m_121346_().readInt();
            if ($$2 < 1) {
                p_121550_.m_121346_().setCursor($$1);
                throw f_121388_.createWithContext((ImmutableStringReader)p_121550_.m_121346_());
            }
            p_121550_.m_121237_($$2);
            p_121550_.m_121328_(true);
        }, p_121548_ -> !p_121548_.m_121378_() && !p_121548_.m_121381_(), Component.m_237115_("argument.entity.options.limit.description"));
        EntitySelectorOptions.m_121453_("sort", p_121546_ -> {
            void $$7;
            int $$1 = p_121546_.m_121346_().getCursor();
            String $$2 = p_121546_.m_121346_().readUnquotedString();
            p_121546_.m_121270_((p_175153_, p_175154_) -> SharedSuggestionProvider.m_82970_(Arrays.asList("nearest", "furthest", "random", "arbitrary"), p_175153_));
            switch ($$2) {
                case "nearest": {
                    BiConsumer<Vec3, List<? extends Entity>> $$3 = EntitySelectorParser.f_121197_;
                    break;
                }
                case "furthest": {
                    BiConsumer<Vec3, List<? extends Entity>> $$4 = EntitySelectorParser.f_121198_;
                    break;
                }
                case "random": {
                    BiConsumer<Vec3, List<? extends Entity>> $$5 = EntitySelectorParser.f_121199_;
                    break;
                }
                case "arbitrary": {
                    BiConsumer<Vec3, List<? extends Entity>> $$6 = EntitySelectorParser.f_121196_;
                    break;
                }
                default: {
                    p_121546_.m_121346_().setCursor($$1);
                    throw f_121389_.createWithContext((ImmutableStringReader)p_121546_.m_121346_(), (Object)$$2);
                }
            }
            p_121546_.m_121268_((BiConsumer<Vec3, List<? extends Entity>>)$$7);
            p_121546_.m_121336_(true);
        }, p_121544_ -> !p_121544_.m_121378_() && !p_121544_.m_121382_(), Component.m_237115_("argument.entity.options.sort.description"));
        EntitySelectorOptions.m_121453_("gamemode", p_121542_ -> {
            p_121542_.m_121270_((p_175193_, p_175194_) -> {
                String $$3 = p_175193_.getRemaining().toLowerCase(Locale.ROOT);
                boolean $$4 = !p_121542_.m_121222_();
                boolean $$5 = true;
                if (!$$3.isEmpty()) {
                    if ($$3.charAt(0) == '!') {
                        $$4 = false;
                        $$3 = $$3.substring(1);
                    } else {
                        $$5 = false;
                    }
                }
                for (GameType $$6 : GameType.values()) {
                    if (!$$6.m_46405_().toLowerCase(Locale.ROOT).startsWith($$3)) continue;
                    if ($$5) {
                        p_175193_.suggest("!" + $$6.m_46405_());
                    }
                    if (!$$4) continue;
                    p_175193_.suggest($$6.m_46405_());
                }
                return p_175193_.buildFuture();
            });
            int $$1 = p_121542_.m_121346_().getCursor();
            boolean $$2 = p_121542_.m_121330_();
            if (p_121542_.m_121222_() && !$$2) {
                p_121542_.m_121346_().setCursor($$1);
                throw f_121385_.createWithContext((ImmutableStringReader)p_121542_.m_121346_(), (Object)"gamemode");
            }
            String $$3 = p_121542_.m_121346_().readUnquotedString();
            GameType $$4 = GameType.m_46402_($$3, null);
            if ($$4 == null) {
                p_121542_.m_121346_().setCursor($$1);
                throw f_121390_.createWithContext((ImmutableStringReader)p_121542_.m_121346_(), (Object)$$3);
            }
            p_121542_.m_121279_(false);
            p_121542_.m_121272_(p_175190_ -> {
                if (!(p_175190_ instanceof ServerPlayer)) {
                    return false;
                }
                GameType $$3 = ((ServerPlayer)p_175190_).f_8941_.m_9290_();
                return $$2 ? $$3 != $$4 : $$3 == $$4;
            });
            if ($$2) {
                p_121542_.m_121350_(true);
            } else {
                p_121542_.m_121344_(true);
            }
        }, p_121540_ -> !p_121540_.m_121383_(), Component.m_237115_("argument.entity.options.gamemode.description"));
        EntitySelectorOptions.m_121453_("team", p_121538_ -> {
            boolean $$1 = p_121538_.m_121330_();
            String $$2 = p_121538_.m_121346_().readUnquotedString();
            p_121538_.m_121272_(p_175198_ -> {
                if (!(p_175198_ instanceof LivingEntity)) {
                    return false;
                }
                Team $$3 = p_175198_.m_5647_();
                String $$4 = $$3 == null ? "" : $$3.m_5758_();
                return $$4.equals($$2) != $$1;
            });
            if ($$1) {
                p_121538_.m_121359_(true);
            } else {
                p_121538_.m_121356_(true);
            }
        }, p_121536_ -> !p_121536_.m_121223_(), Component.m_237115_("argument.entity.options.team.description"));
        EntitySelectorOptions.m_121453_("type", p_121534_ -> {
            p_121534_.m_121270_((p_175161_, p_175162_) -> {
                SharedSuggestionProvider.m_82929_(Registry.f_122826_.m_6566_(), p_175161_, String.valueOf('!'));
                SharedSuggestionProvider.m_205106_(Registry.f_122826_.m_203613_().map(TagKey::f_203868_), p_175161_, "!#");
                if (!p_121534_.m_121226_()) {
                    SharedSuggestionProvider.m_82926_(Registry.f_122826_.m_6566_(), p_175161_);
                    SharedSuggestionProvider.m_205106_(Registry.f_122826_.m_203613_().map(TagKey::f_203868_), p_175161_, String.valueOf('#'));
                }
                return p_175161_.buildFuture();
            });
            int $$1 = p_121534_.m_121346_().getCursor();
            boolean $$2 = p_121534_.m_121330_();
            if (p_121534_.m_121226_() && !$$2) {
                p_121534_.m_121346_().setCursor($$1);
                throw f_121385_.createWithContext((ImmutableStringReader)p_121534_.m_121346_(), (Object)"type");
            }
            if ($$2) {
                p_121534_.m_121224_();
            }
            if (p_121534_.m_121338_()) {
                TagKey<EntityType<?>> $$3 = TagKey.m_203882_(Registry.f_122903_, ResourceLocation.m_135818_(p_121534_.m_121346_()));
                p_121534_.m_121272_(p_205691_ -> p_205691_.m_6095_().m_204039_($$3) != $$2);
            } else {
                ResourceLocation $$4 = ResourceLocation.m_135818_(p_121534_.m_121346_());
                EntityType<?> $$5 = Registry.f_122826_.m_6612_($$4).orElseThrow(() -> {
                    p_121534_.m_121346_().setCursor($$1);
                    return f_121391_.createWithContext((ImmutableStringReader)p_121534_.m_121346_(), (Object)$$4.toString());
                });
                if (Objects.equals(EntityType.f_20532_, $$5) && !$$2) {
                    p_121534_.m_121279_(false);
                }
                p_121534_.m_121272_(p_175151_ -> Objects.equals($$5, p_175151_.m_6095_()) != $$2);
                if (!$$2) {
                    p_121534_.m_121241_($$5);
                }
            }
        }, p_121532_ -> !p_121532_.m_121225_(), Component.m_237115_("argument.entity.options.type.description"));
        EntitySelectorOptions.m_121453_("tag", p_121530_ -> {
            boolean $$1 = p_121530_.m_121330_();
            String $$2 = p_121530_.m_121346_().readUnquotedString();
            p_121530_.m_121272_(p_175166_ -> {
                if ("".equals($$2)) {
                    return p_175166_.m_19880_().isEmpty() != $$1;
                }
                return p_175166_.m_19880_().contains($$2) != $$1;
            });
        }, p_121528_ -> true, Component.m_237115_("argument.entity.options.tag.description"));
        EntitySelectorOptions.m_121453_("nbt", p_121526_ -> {
            boolean $$1 = p_121526_.m_121330_();
            CompoundTag $$2 = new TagParser(p_121526_.m_121346_()).m_129373_();
            p_121526_.m_121272_(p_175176_ -> {
                ItemStack $$4;
                CompoundTag $$3 = p_175176_.m_20240_(new CompoundTag());
                if (p_175176_ instanceof ServerPlayer && !($$4 = ((ServerPlayer)p_175176_).m_150109_().m_36056_()).m_41619_()) {
                    $$3.m_128365_("SelectedItem", $$4.m_41739_(new CompoundTag()));
                }
                return NbtUtils.m_129235_($$2, $$3, true) != $$1;
            });
        }, p_121524_ -> true, Component.m_237115_("argument.entity.options.nbt.description"));
        EntitySelectorOptions.m_121453_("scores", p_121522_ -> {
            StringReader $$1 = p_121522_.m_121346_();
            HashMap $$2 = Maps.newHashMap();
            $$1.expect('{');
            $$1.skipWhitespace();
            while ($$1.canRead() && $$1.peek() != '}') {
                $$1.skipWhitespace();
                String $$3 = $$1.readUnquotedString();
                $$1.skipWhitespace();
                $$1.expect('=');
                $$1.skipWhitespace();
                MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55375_($$1);
                $$2.put($$3, $$4);
                $$1.skipWhitespace();
                if (!$$1.canRead() || $$1.peek() != ',') continue;
                $$1.skip();
            }
            $$1.expect('}');
            if (!$$2.isEmpty()) {
                p_121522_.m_121272_(p_175201_ -> {
                    ServerScoreboard $$2 = p_175201_.m_20194_().m_129896_();
                    String $$3 = p_175201_.m_6302_();
                    for (Map.Entry $$4 : $$2.entrySet()) {
                        Objective $$5 = $$2.m_83477_((String)$$4.getKey());
                        if ($$5 == null) {
                            return false;
                        }
                        if (!$$2.m_83461_($$3, $$5)) {
                            return false;
                        }
                        Score $$6 = $$2.m_83471_($$3, $$5);
                        int $$7 = $$6.m_83400_();
                        if (((MinMaxBounds.Ints)$$4.getValue()).m_55390_($$7)) continue;
                        return false;
                    }
                    return true;
                });
            }
            p_121522_.m_121365_(true);
        }, p_121518_ -> !p_121518_.m_121227_(), Component.m_237115_("argument.entity.options.scores.description"));
        EntitySelectorOptions.m_121453_("advancements", p_121514_ -> {
            StringReader $$1 = p_121514_.m_121346_();
            HashMap $$2 = Maps.newHashMap();
            $$1.expect('{');
            $$1.skipWhitespace();
            while ($$1.canRead() && $$1.peek() != '}') {
                $$1.skipWhitespace();
                ResourceLocation $$3 = ResourceLocation.m_135818_($$1);
                $$1.skipWhitespace();
                $$1.expect('=');
                $$1.skipWhitespace();
                if ($$1.canRead() && $$1.peek() == '{') {
                    HashMap $$4 = Maps.newHashMap();
                    $$1.skipWhitespace();
                    $$1.expect('{');
                    $$1.skipWhitespace();
                    while ($$1.canRead() && $$1.peek() != '}') {
                        $$1.skipWhitespace();
                        String $$5 = $$1.readUnquotedString();
                        $$1.skipWhitespace();
                        $$1.expect('=');
                        $$1.skipWhitespace();
                        boolean $$6 = $$1.readBoolean();
                        $$4.put($$5, p_175186_ -> p_175186_.m_12911_() == $$6);
                        $$1.skipWhitespace();
                        if (!$$1.canRead() || $$1.peek() != ',') continue;
                        $$1.skip();
                    }
                    $$1.skipWhitespace();
                    $$1.expect('}');
                    $$1.skipWhitespace();
                    $$2.put($$3, p_175169_ -> {
                        for (Map.Entry $$2 : $$4.entrySet()) {
                            CriterionProgress $$3 = p_175169_.m_8214_((String)$$2.getKey());
                            if ($$3 != null && ((Predicate)$$2.getValue()).test($$3)) continue;
                            return false;
                        }
                        return true;
                    });
                } else {
                    boolean $$7 = $$1.readBoolean();
                    $$2.put($$3, p_175183_ -> p_175183_.m_8193_() == $$7);
                }
                $$1.skipWhitespace();
                if (!$$1.canRead() || $$1.peek() != ',') continue;
                $$1.skip();
            }
            $$1.expect('}');
            if (!$$2.isEmpty()) {
                p_121514_.m_121272_(p_175172_ -> {
                    if (!(p_175172_ instanceof ServerPlayer)) {
                        return false;
                    }
                    ServerPlayer $$2 = (ServerPlayer)p_175172_;
                    PlayerAdvancements $$3 = $$2.m_8960_();
                    ServerAdvancementManager $$4 = $$2.m_20194_().m_129889_();
                    for (Map.Entry $$5 : $$2.entrySet()) {
                        Advancement $$6 = $$4.m_136041_((ResourceLocation)$$5.getKey());
                        if ($$6 != null && ((Predicate)$$5.getValue()).test($$3.m_135996_($$6))) continue;
                        return false;
                    }
                    return true;
                });
                p_121514_.m_121279_(false);
            }
            p_121514_.m_121368_(true);
        }, p_121506_ -> !p_121506_.m_121228_(), Component.m_237115_("argument.entity.options.advancements.description"));
        EntitySelectorOptions.m_121453_("predicate", p_121487_ -> {
            boolean $$1 = p_121487_.m_121330_();
            ResourceLocation $$2 = ResourceLocation.m_135818_(p_121487_.m_121346_());
            p_121487_.m_121272_(p_175180_ -> {
                if (!(p_175180_.f_19853_ instanceof ServerLevel)) {
                    return false;
                }
                ServerLevel $$3 = (ServerLevel)p_175180_.f_19853_;
                LootItemCondition $$4 = $$3.m_7654_().m_129899_().m_79252_($$2);
                if ($$4 == null) {
                    return false;
                }
                LootContext $$5 = new LootContext.Builder($$3).m_78972_(LootContextParams.f_81455_, p_175180_).m_78972_(LootContextParams.f_81460_, p_175180_.m_20182_()).m_78975_(LootContextParamSets.f_81413_);
                return $$1 ^ $$4.test($$5);
            });
        }, p_121435_ -> true, Component.m_237115_("argument.entity.options.predicate.description"));
    }

    public static Modifier m_121447_(EntitySelectorParser p_121448_, String p_121449_, int p_121450_) throws CommandSyntaxException {
        Option $$3 = f_121392_.get(p_121449_);
        if ($$3 != null) {
            if ($$3.f_121566_.test(p_121448_)) {
                return $$3.f_121565_;
            }
            throw f_121385_.createWithContext((ImmutableStringReader)p_121448_.m_121346_(), (Object)p_121449_);
        }
        p_121448_.m_121346_().setCursor(p_121450_);
        throw f_121384_.createWithContext((ImmutableStringReader)p_121448_.m_121346_(), (Object)p_121449_);
    }

    public static void m_121440_(EntitySelectorParser p_121441_, SuggestionsBuilder p_121442_) {
        String $$2 = p_121442_.getRemaining().toLowerCase(Locale.ROOT);
        for (Map.Entry<String, Option> $$3 : f_121392_.entrySet()) {
            if (!$$3.getValue().f_121566_.test(p_121441_) || !$$3.getKey().toLowerCase(Locale.ROOT).startsWith($$2)) continue;
            p_121442_.suggest($$3.getKey() + "=", (Message)$$3.getValue().f_121567_);
        }
    }

    static class Option {
        public final Modifier f_121565_;
        public final Predicate<EntitySelectorParser> f_121566_;
        public final Component f_121567_;

        Option(Modifier p_121569_, Predicate<EntitySelectorParser> p_121570_, Component p_121571_) {
            this.f_121565_ = p_121569_;
            this.f_121566_ = p_121570_;
            this.f_121567_ = p_121571_;
        }
    }

    public static interface Modifier {
        public void m_121563_(EntitySelectorParser var1) throws CommandSyntaxException;
    }
}

