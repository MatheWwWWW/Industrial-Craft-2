/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.fixes.References;

public class OptionsKeyLwjgl3Fix
extends DataFix {
    public static final String f_145573_ = "key.unknown";
    private static final Int2ObjectMap<String> f_16627_ = (Int2ObjectMap)DataFixUtils.make((Object)new Int2ObjectOpenHashMap(), p_16640_ -> {
        p_16640_.put(0, (Object)f_145573_);
        p_16640_.put(11, (Object)"key.0");
        p_16640_.put(2, (Object)"key.1");
        p_16640_.put(3, (Object)"key.2");
        p_16640_.put(4, (Object)"key.3");
        p_16640_.put(5, (Object)"key.4");
        p_16640_.put(6, (Object)"key.5");
        p_16640_.put(7, (Object)"key.6");
        p_16640_.put(8, (Object)"key.7");
        p_16640_.put(9, (Object)"key.8");
        p_16640_.put(10, (Object)"key.9");
        p_16640_.put(30, (Object)"key.a");
        p_16640_.put(40, (Object)"key.apostrophe");
        p_16640_.put(48, (Object)"key.b");
        p_16640_.put(43, (Object)"key.backslash");
        p_16640_.put(14, (Object)"key.backspace");
        p_16640_.put(46, (Object)"key.c");
        p_16640_.put(58, (Object)"key.caps.lock");
        p_16640_.put(51, (Object)"key.comma");
        p_16640_.put(32, (Object)"key.d");
        p_16640_.put(211, (Object)"key.delete");
        p_16640_.put(208, (Object)"key.down");
        p_16640_.put(18, (Object)"key.e");
        p_16640_.put(207, (Object)"key.end");
        p_16640_.put(28, (Object)"key.enter");
        p_16640_.put(13, (Object)"key.equal");
        p_16640_.put(1, (Object)"key.escape");
        p_16640_.put(33, (Object)"key.f");
        p_16640_.put(59, (Object)"key.f1");
        p_16640_.put(68, (Object)"key.f10");
        p_16640_.put(87, (Object)"key.f11");
        p_16640_.put(88, (Object)"key.f12");
        p_16640_.put(100, (Object)"key.f13");
        p_16640_.put(101, (Object)"key.f14");
        p_16640_.put(102, (Object)"key.f15");
        p_16640_.put(103, (Object)"key.f16");
        p_16640_.put(104, (Object)"key.f17");
        p_16640_.put(105, (Object)"key.f18");
        p_16640_.put(113, (Object)"key.f19");
        p_16640_.put(60, (Object)"key.f2");
        p_16640_.put(61, (Object)"key.f3");
        p_16640_.put(62, (Object)"key.f4");
        p_16640_.put(63, (Object)"key.f5");
        p_16640_.put(64, (Object)"key.f6");
        p_16640_.put(65, (Object)"key.f7");
        p_16640_.put(66, (Object)"key.f8");
        p_16640_.put(67, (Object)"key.f9");
        p_16640_.put(34, (Object)"key.g");
        p_16640_.put(41, (Object)"key.grave.accent");
        p_16640_.put(35, (Object)"key.h");
        p_16640_.put(199, (Object)"key.home");
        p_16640_.put(23, (Object)"key.i");
        p_16640_.put(210, (Object)"key.insert");
        p_16640_.put(36, (Object)"key.j");
        p_16640_.put(37, (Object)"key.k");
        p_16640_.put(82, (Object)"key.keypad.0");
        p_16640_.put(79, (Object)"key.keypad.1");
        p_16640_.put(80, (Object)"key.keypad.2");
        p_16640_.put(81, (Object)"key.keypad.3");
        p_16640_.put(75, (Object)"key.keypad.4");
        p_16640_.put(76, (Object)"key.keypad.5");
        p_16640_.put(77, (Object)"key.keypad.6");
        p_16640_.put(71, (Object)"key.keypad.7");
        p_16640_.put(72, (Object)"key.keypad.8");
        p_16640_.put(73, (Object)"key.keypad.9");
        p_16640_.put(78, (Object)"key.keypad.add");
        p_16640_.put(83, (Object)"key.keypad.decimal");
        p_16640_.put(181, (Object)"key.keypad.divide");
        p_16640_.put(156, (Object)"key.keypad.enter");
        p_16640_.put(141, (Object)"key.keypad.equal");
        p_16640_.put(55, (Object)"key.keypad.multiply");
        p_16640_.put(74, (Object)"key.keypad.subtract");
        p_16640_.put(38, (Object)"key.l");
        p_16640_.put(203, (Object)"key.left");
        p_16640_.put(56, (Object)"key.left.alt");
        p_16640_.put(26, (Object)"key.left.bracket");
        p_16640_.put(29, (Object)"key.left.control");
        p_16640_.put(42, (Object)"key.left.shift");
        p_16640_.put(219, (Object)"key.left.win");
        p_16640_.put(50, (Object)"key.m");
        p_16640_.put(12, (Object)"key.minus");
        p_16640_.put(49, (Object)"key.n");
        p_16640_.put(69, (Object)"key.num.lock");
        p_16640_.put(24, (Object)"key.o");
        p_16640_.put(25, (Object)"key.p");
        p_16640_.put(209, (Object)"key.page.down");
        p_16640_.put(201, (Object)"key.page.up");
        p_16640_.put(197, (Object)"key.pause");
        p_16640_.put(52, (Object)"key.period");
        p_16640_.put(183, (Object)"key.print.screen");
        p_16640_.put(16, (Object)"key.q");
        p_16640_.put(19, (Object)"key.r");
        p_16640_.put(205, (Object)"key.right");
        p_16640_.put(184, (Object)"key.right.alt");
        p_16640_.put(27, (Object)"key.right.bracket");
        p_16640_.put(157, (Object)"key.right.control");
        p_16640_.put(54, (Object)"key.right.shift");
        p_16640_.put(220, (Object)"key.right.win");
        p_16640_.put(31, (Object)"key.s");
        p_16640_.put(70, (Object)"key.scroll.lock");
        p_16640_.put(39, (Object)"key.semicolon");
        p_16640_.put(53, (Object)"key.slash");
        p_16640_.put(57, (Object)"key.space");
        p_16640_.put(20, (Object)"key.t");
        p_16640_.put(15, (Object)"key.tab");
        p_16640_.put(22, (Object)"key.u");
        p_16640_.put(200, (Object)"key.up");
        p_16640_.put(47, (Object)"key.v");
        p_16640_.put(17, (Object)"key.w");
        p_16640_.put(45, (Object)"key.x");
        p_16640_.put(21, (Object)"key.y");
        p_16640_.put(44, (Object)"key.z");
    });

    public OptionsKeyLwjgl3Fix(Schema p_16630_, boolean p_16631_) {
        super(p_16630_, p_16631_);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("OptionsKeyLwjgl3Fix", this.getInputSchema().getType(References.f_16775_), p_16633_ -> p_16633_.update(DSL.remainderFinder(), p_145575_ -> p_145575_.getMapValues().map(p_145578_ -> p_145575_.createMap(p_145578_.entrySet().stream().map(p_145580_ -> {
            if (((Dynamic)p_145580_.getKey()).asString("").startsWith("key_")) {
                int $$1 = Integer.parseInt(((Dynamic)p_145580_.getValue()).asString(""));
                if ($$1 < 0) {
                    String $$6;
                    int $$2 = $$1 + 100;
                    if ($$2 == 0) {
                        String $$3 = "key.mouse.left";
                    } else if ($$2 == 1) {
                        String $$4 = "key.mouse.right";
                    } else if ($$2 == 2) {
                        String $$5 = "key.mouse.middle";
                    } else {
                        $$6 = "key.mouse." + ($$2 + 1);
                    }
                    return Pair.of((Object)((Dynamic)p_145580_.getKey()), (Object)((Dynamic)p_145580_.getValue()).createString($$6));
                }
                String $$7 = (String)f_16627_.getOrDefault($$1, (Object)f_145573_);
                return Pair.of((Object)((Dynamic)p_145580_.getKey()), (Object)((Dynamic)p_145580_.getValue()).createString($$7));
            }
            return Pair.of((Object)((Dynamic)p_145580_.getKey()), (Object)((Dynamic)p_145580_.getValue()));
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)))).result().orElse(p_145575_)));
    }
}

