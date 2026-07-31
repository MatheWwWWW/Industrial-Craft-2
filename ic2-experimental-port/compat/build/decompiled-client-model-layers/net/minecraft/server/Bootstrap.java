/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.locale.Language;
import net.minecraft.server.DebugLoggedPrintStream;
import net.minecraft.server.LoggedPrintStream;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;

public class Bootstrap {
    public static final PrintStream f_135866_ = System.out;
    private static volatile boolean f_135867_;
    private static final Logger f_135868_;

    public static void m_135870_() {
        if (f_135867_) {
            return;
        }
        f_135867_ = true;
        if (Registry.f_122897_.m_6566_().isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
        }
        FireBlock.m_53484_();
        ComposterBlock.m_51988_();
        if (EntityType.m_20613_(EntityType.f_20532_) == null) {
            throw new IllegalStateException("Failed loading EntityTypes");
        }
        PotionBrewing.m_43499_();
        EntitySelectorOptions.m_121426_();
        DispenseItemBehavior.m_123402_();
        CauldronInteraction.m_175649_();
        Registry.m_206101_();
        Bootstrap.m_135890_();
    }

    private static <T> void m_135871_(Iterable<T> p_135872_, Function<T, String> p_135873_, Set<String> p_135874_) {
        Language $$3 = Language.m_128107_();
        p_135872_.forEach(p_135883_ -> {
            String $$4 = (String)p_135873_.apply(p_135883_);
            if (!$$3.m_6722_($$4)) {
                p_135874_.add($$4);
            }
        });
    }

    private static void m_135877_(final Set<String> p_135878_) {
        final Language $$1 = Language.m_128107_();
        GameRules.m_46164_(new GameRules.GameRuleTypeVisitor(){

            @Override
            public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_135897_, GameRules.Type<T> p_135898_) {
                if (!$$1.m_6722_(p_135897_.m_46331_())) {
                    p_135878_.add(p_135897_.m_46328_());
                }
            }
        });
    }

    public static Set<String> m_135886_() {
        TreeSet<String> $$0 = new TreeSet<String>();
        Bootstrap.m_135871_(Registry.f_122866_, Attribute::m_22087_, $$0);
        Bootstrap.m_135871_(Registry.f_122826_, EntityType::m_20675_, $$0);
        Bootstrap.m_135871_(Registry.f_122823_, MobEffect::m_19481_, $$0);
        Bootstrap.m_135871_(Registry.f_122827_, Item::m_5524_, $$0);
        Bootstrap.m_135871_(Registry.f_122825_, Enchantment::m_44704_, $$0);
        Bootstrap.m_135871_(Registry.f_122824_, Block::m_7705_, $$0);
        Bootstrap.m_135871_(Registry.f_122832_, p_135885_ -> "stat." + p_135885_.toString().replace(':', '.'), $$0);
        Bootstrap.m_135877_($$0);
        return $$0;
    }

    public static void m_179912_(Supplier<String> p_179913_) {
        if (!f_135867_) {
            throw Bootstrap.m_179916_(p_179913_);
        }
    }

    private static RuntimeException m_179916_(Supplier<String> p_179917_) {
        try {
            String $$1 = p_179917_.get();
            return new IllegalArgumentException("Not bootstrapped (called from " + $$1 + ")");
        }
        catch (Exception $$2) {
            IllegalArgumentException $$3 = new IllegalArgumentException("Not bootstrapped (failed to resolve location)");
            $$3.addSuppressed($$2);
            return $$3;
        }
    }

    public static void m_135889_() {
        Bootstrap.m_179912_(() -> "validate");
        if (SharedConstants.f_136183_) {
            Bootstrap.m_135886_().forEach(p_179915_ -> f_135868_.error("Missing translations: {}", p_179915_));
            Commands.m_82138_();
            Bootstrap.m_197757_();
        }
        DefaultAttributes.m_22296_();
    }

    private static void m_197757_() {
        BuiltinRegistries.f_123865_.m_123024_().forEach(p_197754_ -> {
            List<HolderSet<PlacedFeature>> $$1 = p_197754_.m_47536_().m_47818_();
            $$1.stream().flatMap(HolderSet::m_203614_).forEach(p_206844_ -> {
                if (!((PlacedFeature)p_206844_.m_203334_()).f_191776_().contains(BiomeFilter.m_191561_())) {
                    Util.m_143785_("Placed feature " + BuiltinRegistries.f_194653_.m_7854_((PlacedFeature)p_206844_.m_203334_()) + " is missing BiomeFilter.biome()");
                }
            });
        });
    }

    private static void m_135890_() {
        if (f_135868_.isDebugEnabled()) {
            System.setErr(new DebugLoggedPrintStream("STDERR", System.err));
            System.setOut(new DebugLoggedPrintStream("STDOUT", f_135866_));
        } else {
            System.setErr(new LoggedPrintStream("STDERR", System.err));
            System.setOut(new LoggedPrintStream("STDOUT", f_135866_));
        }
    }

    public static void m_135875_(String p_135876_) {
        f_135866_.println(p_135876_);
    }

    static {
        f_135868_ = LogUtils.getLogger();
    }
}

