/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.OptionalDynamic
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.util.datafix.fixes.References;

public class SavedDataFeaturePoolElementFix
extends DataFix {
    private static final Pattern f_145641_ = Pattern.compile("\\[(\\d+)\\]");
    private static final Set<String> f_145642_ = Sets.newHashSet((Object[])new String[]{"minecraft:jigsaw", "minecraft:nvi", "minecraft:pcp", "minecraft:bastionremnant", "minecraft:runtime"});
    private static final Set<String> f_145643_ = Sets.newHashSet((Object[])new String[]{"minecraft:tree", "minecraft:flower", "minecraft:block_pile", "minecraft:random_patch"});

    public SavedDataFeaturePoolElementFix(Schema p_145646_) {
        super(p_145646_, false);
    }

    public TypeRewriteRule makeRule() {
        return this.writeFixAndRead("SavedDataFeaturePoolElementFix", this.getInputSchema().getType(References.f_16790_), this.getOutputSchema().getType(References.f_16790_), SavedDataFeaturePoolElementFix::m_145662_);
    }

    private static <T> Dynamic<T> m_145662_(Dynamic<T> p_145663_) {
        return p_145663_.update("Children", SavedDataFeaturePoolElementFix::m_145664_);
    }

    private static <T> Dynamic<T> m_145664_(Dynamic<T> p_145665_) {
        return p_145665_.asStreamOpt().map(SavedDataFeaturePoolElementFix::m_145660_).map(arg_0 -> p_145665_.createList(arg_0)).result().orElse(p_145665_);
    }

    private static Stream<? extends Dynamic<?>> m_145660_(Stream<? extends Dynamic<?>> p_145661_) {
        return p_145661_.map(p_145667_ -> {
            String $$1 = p_145667_.get("id").asString("");
            if (!f_145642_.contains($$1)) {
                return p_145667_;
            }
            OptionalDynamic $$2 = p_145667_.get("pool_element");
            if (!$$2.get("element_type").asString("").equals("minecraft:feature_pool_element")) {
                return p_145667_;
            }
            return p_145667_.update("pool_element", p_145669_ -> p_145669_.update("feature", SavedDataFeaturePoolElementFix::m_145647_));
        });
    }

    private static <T> OptionalDynamic<T> m_145649_(Dynamic<T> p_145650_, String ... p_145651_) {
        if (p_145651_.length == 0) {
            throw new IllegalArgumentException("Missing path");
        }
        OptionalDynamic $$2 = p_145650_.get(p_145651_[0]);
        for (int $$3 = 1; $$3 < p_145651_.length; ++$$3) {
            String $$4 = p_145651_[$$3];
            Matcher $$5 = f_145641_.matcher($$4);
            if ($$5.matches()) {
                int $$6 = Integer.parseInt($$5.group(1));
                List $$7 = $$2.asList(Function.identity());
                if ($$6 >= 0 && $$6 < $$7.size()) {
                    $$2 = new OptionalDynamic(p_145650_.getOps(), DataResult.success((Object)((Dynamic)$$7.get($$6))));
                    continue;
                }
                $$2 = new OptionalDynamic(p_145650_.getOps(), DataResult.error((String)("Missing id:" + $$6)));
                continue;
            }
            $$2 = $$2.get($$4);
        }
        return $$2;
    }

    @VisibleForTesting
    protected static Dynamic<?> m_145647_(Dynamic<?> p_145648_) {
        Optional<String> $$1 = SavedDataFeaturePoolElementFix.m_145652_(SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "type").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "name").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "config", "state_provider", "type").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "config", "state_provider", "state", "Name").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "config", "state_provider", "entries", "[0]", "data", "Name").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "config", "foliage_placer", "type").asString(""), SavedDataFeaturePoolElementFix.m_145649_(p_145648_, "config", "leaves_provider", "state", "Name").asString(""));
        if ($$1.isPresent()) {
            return p_145648_.createString($$1.get());
        }
        return p_145648_;
    }

    /*
     * WARNING - void declaration
     */
    private static Optional<String> m_145652_(String p_145653_, String p_145654_, String p_145655_, String p_145656_, String p_145657_, String p_145658_, String p_145659_) {
        void $$10;
        if (!p_145653_.isEmpty()) {
            String $$7 = p_145653_;
        } else if (!p_145654_.isEmpty()) {
            if ("minecraft:normal_tree".equals(p_145654_)) {
                String $$8 = "minecraft:tree";
            } else {
                String $$9 = p_145654_;
            }
        } else {
            return Optional.empty();
        }
        if (f_145643_.contains($$10)) {
            if ("minecraft:random_patch".equals($$10)) {
                if ("minecraft:simple_state_provider".equals(p_145655_)) {
                    if ("minecraft:sweet_berry_bush".equals(p_145656_)) {
                        return Optional.of("minecraft:patch_berry_bush");
                    }
                    if ("minecraft:cactus".equals(p_145656_)) {
                        return Optional.of("minecraft:patch_cactus");
                    }
                } else if ("minecraft:weighted_state_provider".equals(p_145655_) && ("minecraft:grass".equals(p_145657_) || "minecraft:fern".equals(p_145657_))) {
                    return Optional.of("minecraft:patch_taiga_grass");
                }
            } else if ("minecraft:block_pile".equals($$10)) {
                if ("minecraft:simple_state_provider".equals(p_145655_) || "minecraft:rotated_block_provider".equals(p_145655_)) {
                    if ("minecraft:hay_block".equals(p_145656_)) {
                        return Optional.of("minecraft:pile_hay");
                    }
                    if ("minecraft:melon".equals(p_145656_)) {
                        return Optional.of("minecraft:pile_melon");
                    }
                    if ("minecraft:snow".equals(p_145656_)) {
                        return Optional.of("minecraft:pile_snow");
                    }
                } else if ("minecraft:weighted_state_provider".equals(p_145655_)) {
                    if ("minecraft:packed_ice".equals(p_145657_) || "minecraft:blue_ice".equals(p_145657_)) {
                        return Optional.of("minecraft:pile_ice");
                    }
                    if ("minecraft:jack_o_lantern".equals(p_145657_) || "minecraft:pumpkin".equals(p_145657_)) {
                        return Optional.of("minecraft:pile_pumpkin");
                    }
                }
            } else {
                if ("minecraft:flower".equals($$10)) {
                    return Optional.of("minecraft:flower_plain");
                }
                if ("minecraft:tree".equals($$10)) {
                    if ("minecraft:acacia_foliage_placer".equals(p_145658_)) {
                        return Optional.of("minecraft:acacia");
                    }
                    if ("minecraft:blob_foliage_placer".equals(p_145658_) && "minecraft:oak_leaves".equals(p_145659_)) {
                        return Optional.of("minecraft:oak");
                    }
                    if ("minecraft:pine_foliage_placer".equals(p_145658_)) {
                        return Optional.of("minecraft:pine");
                    }
                    if ("minecraft:spruce_foliage_placer".equals(p_145658_)) {
                        return Optional.of("minecraft:spruce");
                    }
                }
            }
        }
        return Optional.empty();
    }
}

