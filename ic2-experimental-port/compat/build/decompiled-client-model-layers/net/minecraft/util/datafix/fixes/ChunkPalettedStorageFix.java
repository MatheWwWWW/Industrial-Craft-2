/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.util.datafix.PackedBitStorage;
import net.minecraft.util.datafix.fixes.BlockStateData;
import net.minecraft.util.datafix.fixes.References;
import org.slf4j.Logger;

public class ChunkPalettedStorageFix
extends DataFix {
    private static final int f_145212_ = 128;
    private static final int f_145213_ = 64;
    private static final int f_145214_ = 32;
    private static final int f_145215_ = 16;
    private static final int f_145216_ = 8;
    private static final int f_145217_ = 4;
    private static final int f_145218_ = 2;
    private static final int f_145219_ = 1;
    static final Logger f_15035_ = LogUtils.getLogger();
    static final BitSet f_15036_ = new BitSet(256);
    static final BitSet f_15037_ = new BitSet(256);
    static final Dynamic<?> f_15038_ = BlockStateData.m_14956_("{Name:'minecraft:pumpkin'}");
    static final Dynamic<?> f_15039_ = BlockStateData.m_14956_("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
    static final Dynamic<?> f_15040_ = BlockStateData.m_14956_("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
    static final Dynamic<?> f_15041_ = BlockStateData.m_14956_("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
    static final Dynamic<?> f_15042_ = BlockStateData.m_14956_("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
    static final Dynamic<?> f_15043_ = BlockStateData.m_14956_("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
    static final Dynamic<?> f_15044_ = BlockStateData.m_14956_("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
    static final Dynamic<?> f_15045_ = BlockStateData.m_14956_("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
    static final Dynamic<?> f_15046_ = BlockStateData.m_14956_("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
    static final Dynamic<?> f_15047_ = BlockStateData.m_14956_("{Name:'minecraft:peony',Properties:{half:'upper'}}");
    static final Map<String, Dynamic<?>> f_15048_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15111_ -> {
        p_15111_.put("minecraft:air0", BlockStateData.m_14956_("{Name:'minecraft:flower_pot'}"));
        p_15111_.put("minecraft:red_flower0", BlockStateData.m_14956_("{Name:'minecraft:potted_poppy'}"));
        p_15111_.put("minecraft:red_flower1", BlockStateData.m_14956_("{Name:'minecraft:potted_blue_orchid'}"));
        p_15111_.put("minecraft:red_flower2", BlockStateData.m_14956_("{Name:'minecraft:potted_allium'}"));
        p_15111_.put("minecraft:red_flower3", BlockStateData.m_14956_("{Name:'minecraft:potted_azure_bluet'}"));
        p_15111_.put("minecraft:red_flower4", BlockStateData.m_14956_("{Name:'minecraft:potted_red_tulip'}"));
        p_15111_.put("minecraft:red_flower5", BlockStateData.m_14956_("{Name:'minecraft:potted_orange_tulip'}"));
        p_15111_.put("minecraft:red_flower6", BlockStateData.m_14956_("{Name:'minecraft:potted_white_tulip'}"));
        p_15111_.put("minecraft:red_flower7", BlockStateData.m_14956_("{Name:'minecraft:potted_pink_tulip'}"));
        p_15111_.put("minecraft:red_flower8", BlockStateData.m_14956_("{Name:'minecraft:potted_oxeye_daisy'}"));
        p_15111_.put("minecraft:yellow_flower0", BlockStateData.m_14956_("{Name:'minecraft:potted_dandelion'}"));
        p_15111_.put("minecraft:sapling0", BlockStateData.m_14956_("{Name:'minecraft:potted_oak_sapling'}"));
        p_15111_.put("minecraft:sapling1", BlockStateData.m_14956_("{Name:'minecraft:potted_spruce_sapling'}"));
        p_15111_.put("minecraft:sapling2", BlockStateData.m_14956_("{Name:'minecraft:potted_birch_sapling'}"));
        p_15111_.put("minecraft:sapling3", BlockStateData.m_14956_("{Name:'minecraft:potted_jungle_sapling'}"));
        p_15111_.put("minecraft:sapling4", BlockStateData.m_14956_("{Name:'minecraft:potted_acacia_sapling'}"));
        p_15111_.put("minecraft:sapling5", BlockStateData.m_14956_("{Name:'minecraft:potted_dark_oak_sapling'}"));
        p_15111_.put("minecraft:red_mushroom0", BlockStateData.m_14956_("{Name:'minecraft:potted_red_mushroom'}"));
        p_15111_.put("minecraft:brown_mushroom0", BlockStateData.m_14956_("{Name:'minecraft:potted_brown_mushroom'}"));
        p_15111_.put("minecraft:deadbush0", BlockStateData.m_14956_("{Name:'minecraft:potted_dead_bush'}"));
        p_15111_.put("minecraft:tallgrass2", BlockStateData.m_14956_("{Name:'minecraft:potted_fern'}"));
        p_15111_.put("minecraft:cactus0", BlockStateData.m_14952_(2240));
    });
    static final Map<String, Dynamic<?>> f_15049_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15108_ -> {
        ChunkPalettedStorageFix.m_15077_(p_15108_, 0, "skeleton", "skull");
        ChunkPalettedStorageFix.m_15077_(p_15108_, 1, "wither_skeleton", "skull");
        ChunkPalettedStorageFix.m_15077_(p_15108_, 2, "zombie", "head");
        ChunkPalettedStorageFix.m_15077_(p_15108_, 3, "player", "head");
        ChunkPalettedStorageFix.m_15077_(p_15108_, 4, "creeper", "head");
        ChunkPalettedStorageFix.m_15077_(p_15108_, 5, "dragon", "head");
    });
    static final Map<String, Dynamic<?>> f_15050_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15105_ -> {
        ChunkPalettedStorageFix.m_15082_(p_15105_, "oak_door", 1024);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "iron_door", 1136);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "spruce_door", 3088);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "birch_door", 3104);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "jungle_door", 3120);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "acacia_door", 3136);
        ChunkPalettedStorageFix.m_15082_(p_15105_, "dark_oak_door", 3152);
    });
    static final Map<String, Dynamic<?>> f_15051_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15102_ -> {
        for (int $$1 = 0; $$1 < 26; ++$$1) {
            p_15102_.put("true" + $$1, BlockStateData.m_14956_("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + $$1 + "'}}"));
            p_15102_.put("false" + $$1, BlockStateData.m_14956_("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + $$1 + "'}}"));
        }
    });
    private static final Int2ObjectMap<String> f_15052_ = (Int2ObjectMap)DataFixUtils.make((Object)new Int2ObjectOpenHashMap(), p_15070_ -> {
        p_15070_.put(0, (Object)"white");
        p_15070_.put(1, (Object)"orange");
        p_15070_.put(2, (Object)"magenta");
        p_15070_.put(3, (Object)"light_blue");
        p_15070_.put(4, (Object)"yellow");
        p_15070_.put(5, (Object)"lime");
        p_15070_.put(6, (Object)"pink");
        p_15070_.put(7, (Object)"gray");
        p_15070_.put(8, (Object)"light_gray");
        p_15070_.put(9, (Object)"cyan");
        p_15070_.put(10, (Object)"purple");
        p_15070_.put(11, (Object)"blue");
        p_15070_.put(12, (Object)"brown");
        p_15070_.put(13, (Object)"green");
        p_15070_.put(14, (Object)"red");
        p_15070_.put(15, (Object)"black");
    });
    static final Map<String, Dynamic<?>> f_15053_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15095_ -> {
        for (Int2ObjectMap.Entry $$1 : f_15052_.int2ObjectEntrySet()) {
            if (Objects.equals($$1.getValue(), "red")) continue;
            ChunkPalettedStorageFix.m_15073_(p_15095_, $$1.getIntKey(), (String)$$1.getValue());
        }
    });
    static final Map<String, Dynamic<?>> f_15054_ = (Map)DataFixUtils.make((Object)Maps.newHashMap(), p_15072_ -> {
        for (Int2ObjectMap.Entry $$1 : f_15052_.int2ObjectEntrySet()) {
            if (Objects.equals($$1.getValue(), "white")) continue;
            ChunkPalettedStorageFix.m_15096_(p_15072_, 15 - $$1.getIntKey(), (String)$$1.getValue());
        }
    });
    static final Dynamic<?> f_15055_;
    private static final int f_145211_ = 4096;

    public ChunkPalettedStorageFix(Schema p_15058_, boolean p_15059_) {
        super(p_15058_, p_15059_);
    }

    private static void m_15077_(Map<String, Dynamic<?>> p_15078_, int p_15079_, String p_15080_, String p_15081_) {
        p_15078_.put(p_15079_ + "north", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'north'}}"));
        p_15078_.put(p_15079_ + "east", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'east'}}"));
        p_15078_.put(p_15079_ + "south", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'south'}}"));
        p_15078_.put(p_15079_ + "west", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'west'}}"));
        for (int $$4 = 0; $$4 < 16; ++$$4) {
            p_15078_.put("" + p_15079_ + $$4, BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_" + p_15081_ + "',Properties:{rotation:'" + $$4 + "'}}"));
        }
    }

    private static void m_15082_(Map<String, Dynamic<?>> p_15083_, String p_15084_, int p_15085_) {
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 4));
        p_15083_.put("minecraft:" + p_15084_ + "eastlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperleftfalsefalse", BlockStateData.m_14952_(p_15085_ + 8));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperleftfalsetrue", BlockStateData.m_14952_(p_15085_ + 10));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 9));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperrightfalsetrue", BlockStateData.m_14952_(p_15085_ + 11));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "eastupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 3));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 7));
        p_15083_.put("minecraft:" + p_15084_ + "northlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "northupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 1));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 5));
        p_15083_.put("minecraft:" + p_15084_ + "southlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "southupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 2));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 6));
        p_15083_.put("minecraft:" + p_15084_ + "westlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
        p_15083_.put("minecraft:" + p_15084_ + "westupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
    }

    private static void m_15073_(Map<String, Dynamic<?>> p_15074_, int p_15075_, String p_15076_) {
        p_15074_.put("southfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
        p_15074_.put("westfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
        p_15074_.put("northfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
        p_15074_.put("eastfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
        p_15074_.put("southfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
        p_15074_.put("westfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
        p_15074_.put("northfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
        p_15074_.put("eastfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
        p_15074_.put("southtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
        p_15074_.put("westtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
        p_15074_.put("northtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
        p_15074_.put("easttruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
    }

    private static void m_15096_(Map<String, Dynamic<?>> p_15097_, int p_15098_, String p_15099_) {
        for (int $$3 = 0; $$3 < 16; ++$$3) {
            p_15097_.put($$3 + "_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_banner',Properties:{rotation:'" + $$3 + "'}}"));
        }
        p_15097_.put("north_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'north'}}"));
        p_15097_.put("south_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'south'}}"));
        p_15097_.put("west_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'west'}}"));
        p_15097_.put("east_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'east'}}"));
    }

    public static String m_15064_(Dynamic<?> p_15065_) {
        return p_15065_.get("Name").asString("");
    }

    public static String m_15066_(Dynamic<?> p_15067_, String p_15068_) {
        return p_15067_.get("Properties").get(p_15068_).asString("");
    }

    public static int m_15061_(CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> p_15062_, Dynamic<?> p_15063_) {
        int $$2 = p_15062_.m_7447_(p_15063_);
        if ($$2 == -1) {
            $$2 = p_15062_.m_13569_(p_15063_);
        }
        return $$2;
    }

    private Dynamic<?> m_15092_(Dynamic<?> p_15093_) {
        Optional $$1 = p_15093_.get("Level").result();
        if ($$1.isPresent() && ((Dynamic)$$1.get()).get("Sections").asStreamOpt().result().isPresent()) {
            return p_15093_.set("Level", new UpgradeChunk((Dynamic)$$1.get()).m_15223_());
        }
        return p_15093_;
    }

    public TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        Type $$1 = this.getOutputSchema().getType(References.f_16773_);
        return this.writeFixAndRead("ChunkPalettedStorageFix", $$0, $$1, this::m_15092_);
    }

    public static int m_15086_(boolean p_15087_, boolean p_15088_, boolean p_15089_, boolean p_15090_) {
        int $$4 = 0;
        if (p_15089_) {
            $$4 = p_15088_ ? ($$4 |= 2) : (p_15087_ ? ($$4 |= 0x80) : ($$4 |= 1));
        } else if (p_15090_) {
            $$4 = p_15087_ ? ($$4 |= 0x20) : (p_15088_ ? ($$4 |= 8) : ($$4 |= 0x10));
        } else if (p_15088_) {
            $$4 |= 4;
        } else if (p_15087_) {
            $$4 |= 0x40;
        }
        return $$4;
    }

    static {
        f_15037_.set(2);
        f_15037_.set(3);
        f_15037_.set(110);
        f_15037_.set(140);
        f_15037_.set(144);
        f_15037_.set(25);
        f_15037_.set(86);
        f_15037_.set(26);
        f_15037_.set(176);
        f_15037_.set(177);
        f_15037_.set(175);
        f_15037_.set(64);
        f_15037_.set(71);
        f_15037_.set(193);
        f_15037_.set(194);
        f_15037_.set(195);
        f_15037_.set(196);
        f_15037_.set(197);
        f_15036_.set(54);
        f_15036_.set(146);
        f_15036_.set(25);
        f_15036_.set(26);
        f_15036_.set(51);
        f_15036_.set(53);
        f_15036_.set(67);
        f_15036_.set(108);
        f_15036_.set(109);
        f_15036_.set(114);
        f_15036_.set(128);
        f_15036_.set(134);
        f_15036_.set(135);
        f_15036_.set(136);
        f_15036_.set(156);
        f_15036_.set(163);
        f_15036_.set(164);
        f_15036_.set(180);
        f_15036_.set(203);
        f_15036_.set(55);
        f_15036_.set(85);
        f_15036_.set(113);
        f_15036_.set(188);
        f_15036_.set(189);
        f_15036_.set(190);
        f_15036_.set(191);
        f_15036_.set(192);
        f_15036_.set(93);
        f_15036_.set(94);
        f_15036_.set(101);
        f_15036_.set(102);
        f_15036_.set(160);
        f_15036_.set(106);
        f_15036_.set(107);
        f_15036_.set(183);
        f_15036_.set(184);
        f_15036_.set(185);
        f_15036_.set(186);
        f_15036_.set(187);
        f_15036_.set(132);
        f_15036_.set(139);
        f_15036_.set(199);
        f_15055_ = BlockStateData.m_14952_(0);
    }

    static final class UpgradeChunk {
        private int f_15215_;
        private final Section[] f_15216_ = new Section[16];
        private final Dynamic<?> f_15217_;
        private final int f_15218_;
        private final int f_15219_;
        private final Int2ObjectMap<Dynamic<?>> f_15220_ = new Int2ObjectLinkedOpenHashMap(16);

        public UpgradeChunk(Dynamic<?> p_15222_) {
            this.f_15217_ = p_15222_;
            this.f_15218_ = p_15222_.get("xPos").asInt(0) << 4;
            this.f_15219_ = p_15222_.get("zPos").asInt(0) << 4;
            p_15222_.get("TileEntities").asStreamOpt().result().ifPresent(p_15241_ -> p_15241_.forEach(p_145228_ -> {
                int $$3;
                int $$1 = p_145228_.get("x").asInt(0) - this.f_15218_ & 0xF;
                int $$2 = p_145228_.get("y").asInt(0);
                int $$4 = $$2 << 8 | ($$3 = p_145228_.get("z").asInt(0) - this.f_15219_ & 0xF) << 4 | $$1;
                if (this.f_15220_.put($$4, p_145228_) != null) {
                    f_15035_.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", new Object[]{this.f_15218_, this.f_15219_, $$1, $$2, $$3});
                }
            }));
            boolean $$1 = p_15222_.get("convertedFromAlphaFormat").asBoolean(false);
            p_15222_.get("Sections").asStreamOpt().result().ifPresent(p_15235_ -> p_15235_.forEach(p_145226_ -> {
                Section $$1 = new Section((Dynamic<?>)p_145226_);
                this.f_15215_ = $$1.m_15209_(this.f_15215_);
                this.f_15216_[$$1.f_15185_] = $$1;
            }));
            for (Section $$2 : this.f_15216_) {
                if ($$2 == null) continue;
                block14: for (Map.Entry $$3 : $$2.f_15190_.entrySet()) {
                    int $$4 = $$2.f_15185_ << 12;
                    switch ((Integer)$$3.getKey()) {
                        case 2: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$7;
                                int $$5 = (Integer)intListIterator.next();
                                Dynamic<?> $$6 = this.m_15224_($$5 |= $$4);
                                if (!"minecraft:grass_block".equals(ChunkPalettedStorageFix.m_15064_($$6)) || !"minecraft:snow".equals($$7 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(UpgradeChunk.m_15226_($$5, Direction.UP)))) && !"minecraft:snow_layer".equals($$7)) continue;
                                this.m_15229_($$5, f_15040_);
                            }
                            continue block14;
                        }
                        case 3: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$10;
                                int $$8 = (Integer)intListIterator.next();
                                Dynamic<?> $$9 = this.m_15224_($$8 |= $$4);
                                if (!"minecraft:podzol".equals(ChunkPalettedStorageFix.m_15064_($$9)) || !"minecraft:snow".equals($$10 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(UpgradeChunk.m_15226_($$8, Direction.UP)))) && !"minecraft:snow_layer".equals($$10)) continue;
                                this.m_15229_($$8, f_15039_);
                            }
                            continue block14;
                        }
                        case 110: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$13;
                                int $$11 = (Integer)intListIterator.next();
                                Dynamic<?> $$12 = this.m_15224_($$11 |= $$4);
                                if (!"minecraft:mycelium".equals(ChunkPalettedStorageFix.m_15064_($$12)) || !"minecraft:snow".equals($$13 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(UpgradeChunk.m_15226_($$11, Direction.UP)))) && !"minecraft:snow_layer".equals($$13)) continue;
                                this.m_15229_($$11, f_15041_);
                            }
                            continue block14;
                        }
                        case 25: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int $$14 = (Integer)intListIterator.next();
                                Dynamic<?> $$15 = this.m_15242_($$14 |= $$4);
                                if ($$15 == null) continue;
                                String $$16 = Boolean.toString($$15.get("powered").asBoolean(false)) + (byte)Math.min(Math.max($$15.get("note").asInt(0), 0), 24);
                                this.m_15229_($$14, f_15051_.getOrDefault($$16, f_15051_.get("false0")));
                            }
                            continue block14;
                        }
                        case 26: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$21;
                                int $$20;
                                int $$17 = (Integer)intListIterator.next();
                                Dynamic<?> $$18 = this.m_15236_($$17 |= $$4);
                                Dynamic<?> $$19 = this.m_15224_($$17);
                                if ($$18 == null || ($$20 = $$18.get("color").asInt(0)) == 14 || $$20 < 0 || $$20 >= 16 || !f_15053_.containsKey($$21 = ChunkPalettedStorageFix.m_15066_($$19, "facing") + ChunkPalettedStorageFix.m_15066_($$19, "occupied") + ChunkPalettedStorageFix.m_15066_($$19, "part") + $$20)) continue;
                                this.m_15229_($$17, f_15053_.get($$21));
                            }
                            continue block14;
                        }
                        case 176: 
                        case 177: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$26;
                                int $$25;
                                int $$22 = (Integer)intListIterator.next();
                                Dynamic<?> $$23 = this.m_15236_($$22 |= $$4);
                                Dynamic<?> $$24 = this.m_15224_($$22);
                                if ($$23 == null || ($$25 = $$23.get("Base").asInt(0)) == 15 || $$25 < 0 || $$25 >= 16 || !f_15054_.containsKey($$26 = ChunkPalettedStorageFix.m_15066_($$24, (Integer)$$3.getKey() == 176 ? "rotation" : "facing") + "_" + $$25)) continue;
                                this.m_15229_($$22, f_15054_.get($$26));
                            }
                            continue block14;
                        }
                        case 86: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$29;
                                int $$27 = (Integer)intListIterator.next();
                                Dynamic<?> $$28 = this.m_15224_($$27 |= $$4);
                                if (!"minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.m_15064_($$28)) || !"minecraft:grass_block".equals($$29 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(UpgradeChunk.m_15226_($$27, Direction.DOWN)))) && !"minecraft:dirt".equals($$29)) continue;
                                this.m_15229_($$27, f_15038_);
                            }
                            continue block14;
                        }
                        case 140: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int $$30 = (Integer)intListIterator.next();
                                Dynamic<?> $$31 = this.m_15242_($$30 |= $$4);
                                if ($$31 == null) continue;
                                String $$32 = $$31.get("Item").asString("") + $$31.get("Data").asInt(0);
                                this.m_15229_($$30, f_15048_.getOrDefault($$32, f_15048_.get("minecraft:air0")));
                            }
                            continue block14;
                        }
                        case 144: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                String $$38;
                                int $$33 = (Integer)intListIterator.next();
                                Dynamic<?> $$34 = this.m_15236_($$33 |= $$4);
                                if ($$34 == null) continue;
                                String $$35 = String.valueOf($$34.get("SkullType").asInt(0));
                                String $$36 = ChunkPalettedStorageFix.m_15066_(this.m_15224_($$33), "facing");
                                if ("up".equals($$36) || "down".equals($$36)) {
                                    String $$37 = $$35 + String.valueOf($$34.get("Rot").asInt(0));
                                } else {
                                    $$38 = $$35 + $$36;
                                }
                                $$34.remove("SkullType");
                                $$34.remove("facing");
                                $$34.remove("Rot");
                                this.m_15229_($$33, f_15049_.getOrDefault($$38, f_15049_.get("0north")));
                            }
                            continue block14;
                        }
                        case 64: 
                        case 71: 
                        case 193: 
                        case 194: 
                        case 195: 
                        case 196: 
                        case 197: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                Dynamic<?> $$41;
                                int $$39 = (Integer)intListIterator.next();
                                Dynamic<?> $$40 = this.m_15224_($$39 |= $$4);
                                if (!ChunkPalettedStorageFix.m_15064_($$40).endsWith("_door") || !"lower".equals(ChunkPalettedStorageFix.m_15066_($$41 = this.m_15224_($$39), "half"))) continue;
                                int $$42 = UpgradeChunk.m_15226_($$39, Direction.UP);
                                Dynamic<?> $$43 = this.m_15224_($$42);
                                String $$44 = ChunkPalettedStorageFix.m_15064_($$41);
                                if (!$$44.equals(ChunkPalettedStorageFix.m_15064_($$43))) continue;
                                String $$45 = ChunkPalettedStorageFix.m_15066_($$41, "facing");
                                String $$46 = ChunkPalettedStorageFix.m_15066_($$41, "open");
                                String $$47 = $$1 ? "left" : ChunkPalettedStorageFix.m_15066_($$43, "hinge");
                                String $$48 = $$1 ? "false" : ChunkPalettedStorageFix.m_15066_($$43, "powered");
                                this.m_15229_($$39, f_15050_.get($$44 + $$45 + "lower" + $$47 + $$46 + $$48));
                                this.m_15229_($$42, f_15050_.get($$44 + $$45 + "upper" + $$47 + $$46 + $$48));
                            }
                            continue block14;
                        }
                        case 175: {
                            IntListIterator intListIterator = ((IntList)$$3.getValue()).iterator();
                            while (intListIterator.hasNext()) {
                                int $$49 = (Integer)intListIterator.next();
                                Dynamic<?> $$50 = this.m_15224_($$49 |= $$4);
                                if (!"upper".equals(ChunkPalettedStorageFix.m_15066_($$50, "half"))) continue;
                                Dynamic<?> $$51 = this.m_15224_(UpgradeChunk.m_15226_($$49, Direction.DOWN));
                                String $$52 = ChunkPalettedStorageFix.m_15064_($$51);
                                if ("minecraft:sunflower".equals($$52)) {
                                    this.m_15229_($$49, f_15042_);
                                    continue;
                                }
                                if ("minecraft:lilac".equals($$52)) {
                                    this.m_15229_($$49, f_15043_);
                                    continue;
                                }
                                if ("minecraft:tall_grass".equals($$52)) {
                                    this.m_15229_($$49, f_15044_);
                                    continue;
                                }
                                if ("minecraft:large_fern".equals($$52)) {
                                    this.m_15229_($$49, f_15045_);
                                    continue;
                                }
                                if ("minecraft:rose_bush".equals($$52)) {
                                    this.m_15229_($$49, f_15046_);
                                    continue;
                                }
                                if (!"minecraft:peony".equals($$52)) continue;
                                this.m_15229_($$49, f_15047_);
                            }
                            break;
                        }
                    }
                }
            }
        }

        @Nullable
        private Dynamic<?> m_15236_(int p_15237_) {
            return (Dynamic)this.f_15220_.get(p_15237_);
        }

        @Nullable
        private Dynamic<?> m_15242_(int p_15243_) {
            return (Dynamic)this.f_15220_.remove(p_15243_);
        }

        public static int m_15226_(int p_15227_, Direction p_15228_) {
            switch (p_15228_.m_15157_()) {
                case X: {
                    int $$2 = (p_15227_ & 0xF) + p_15228_.m_15156_().m_15181_();
                    return $$2 < 0 || $$2 > 15 ? -1 : p_15227_ & 0xFFFFFFF0 | $$2;
                }
                case Y: {
                    int $$3 = (p_15227_ >> 8) + p_15228_.m_15156_().m_15181_();
                    return $$3 < 0 || $$3 > 255 ? -1 : p_15227_ & 0xFF | $$3 << 8;
                }
                case Z: {
                    int $$4 = (p_15227_ >> 4 & 0xF) + p_15228_.m_15156_().m_15181_();
                    return $$4 < 0 || $$4 > 15 ? -1 : p_15227_ & 0xFFFFFF0F | $$4 << 4;
                }
            }
            return -1;
        }

        private void m_15229_(int p_15230_, Dynamic<?> p_15231_) {
            if (p_15230_ < 0 || p_15230_ > 65535) {
                return;
            }
            Section $$2 = this.m_15244_(p_15230_);
            if ($$2 == null) {
                return;
            }
            $$2.m_15202_(p_15230_ & 0xFFF, p_15231_);
        }

        @Nullable
        private Section m_15244_(int p_15245_) {
            int $$1 = p_15245_ >> 12;
            return $$1 < this.f_15216_.length ? this.f_15216_[$$1] : null;
        }

        public Dynamic<?> m_15224_(int p_15225_) {
            if (p_15225_ < 0 || p_15225_ > 65535) {
                return f_15055_;
            }
            Section $$1 = this.m_15244_(p_15225_);
            if ($$1 == null) {
                return f_15055_;
            }
            return $$1.m_15197_(p_15225_ & 0xFFF);
        }

        public Dynamic<?> m_15223_() {
            Dynamic $$0 = this.f_15217_;
            $$0 = this.f_15220_.isEmpty() ? $$0.remove("TileEntities") : $$0.set("TileEntities", $$0.createList(this.f_15220_.values().stream()));
            Dynamic $$1 = $$0.emptyMap();
            ArrayList $$2 = Lists.newArrayList();
            for (Section $$3 : this.f_15216_) {
                if ($$3 == null) continue;
                $$2.add($$3.m_15196_());
                $$1 = $$1.set(String.valueOf($$3.f_15185_), $$1.createIntList(Arrays.stream($$3.f_15191_.toIntArray())));
            }
            Dynamic $$4 = $$0.emptyMap();
            $$4 = $$4.set("Sides", $$4.createByte((byte)this.f_15215_));
            $$4 = $$4.set("Indices", $$1);
            return $$0.set("UpgradeData", $$4).set("Sections", $$4.createList($$2.stream()));
        }
    }

    public static final class Direction
    extends Enum<Direction> {
        public static final /* enum */ Direction DOWN = new Direction(AxisDirection.NEGATIVE, Axis.Y);
        public static final /* enum */ Direction UP = new Direction(AxisDirection.POSITIVE, Axis.Y);
        public static final /* enum */ Direction NORTH = new Direction(AxisDirection.NEGATIVE, Axis.Z);
        public static final /* enum */ Direction SOUTH = new Direction(AxisDirection.POSITIVE, Axis.Z);
        public static final /* enum */ Direction WEST = new Direction(AxisDirection.NEGATIVE, Axis.X);
        public static final /* enum */ Direction EAST = new Direction(AxisDirection.POSITIVE, Axis.X);
        private final Axis f_15147_;
        private final AxisDirection f_15148_;
        private static final /* synthetic */ Direction[] $VALUES;

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        public static Direction valueOf(String p_15159_) {
            return Enum.valueOf(Direction.class, p_15159_);
        }

        private Direction(AxisDirection p_15154_, Axis p_15155_) {
            this.f_15147_ = p_15155_;
            this.f_15148_ = p_15154_;
        }

        public AxisDirection m_15156_() {
            return this.f_15148_;
        }

        public Axis m_15157_() {
            return this.f_15147_;
        }

        private static /* synthetic */ Direction[] m_145222_() {
            return new Direction[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
        }

        static {
            $VALUES = Direction.m_145222_();
        }

        public static final class Axis
        extends Enum<Axis> {
            public static final /* enum */ Axis X = new Axis();
            public static final /* enum */ Axis Y = new Axis();
            public static final /* enum */ Axis Z = new Axis();
            private static final /* synthetic */ Axis[] $VALUES;

            public static Axis[] values() {
                return (Axis[])$VALUES.clone();
            }

            public static Axis valueOf(String p_15170_) {
                return Enum.valueOf(Axis.class, p_15170_);
            }

            private static /* synthetic */ Axis[] m_145223_() {
                return new Axis[]{X, Y, Z};
            }

            static {
                $VALUES = Axis.m_145223_();
            }
        }

        public static final class AxisDirection
        extends Enum<AxisDirection> {
            public static final /* enum */ AxisDirection POSITIVE = new AxisDirection(1);
            public static final /* enum */ AxisDirection NEGATIVE = new AxisDirection(-1);
            private final int f_15174_;
            private static final /* synthetic */ AxisDirection[] $VALUES;

            public static AxisDirection[] values() {
                return (AxisDirection[])$VALUES.clone();
            }

            public static AxisDirection valueOf(String p_15183_) {
                return Enum.valueOf(AxisDirection.class, p_15183_);
            }

            private AxisDirection(int p_15180_) {
                this.f_15174_ = p_15180_;
            }

            public int m_15181_() {
                return this.f_15174_;
            }

            private static /* synthetic */ AxisDirection[] m_145224_() {
                return new AxisDirection[]{POSITIVE, NEGATIVE};
            }

            static {
                $VALUES = AxisDirection.m_145224_();
            }
        }
    }

    static class DataLayer {
        private static final int f_145220_ = 2048;
        private static final int f_145221_ = 4;
        private final byte[] f_15129_;

        public DataLayer() {
            this.f_15129_ = new byte[2048];
        }

        public DataLayer(byte[] p_15132_) {
            this.f_15129_ = p_15132_;
            if (p_15132_.length != 2048) {
                throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + p_15132_.length);
            }
        }

        public int m_15135_(int p_15136_, int p_15137_, int p_15138_) {
            int $$3 = this.m_15139_(p_15137_ << 8 | p_15138_ << 4 | p_15136_);
            if (this.m_15133_(p_15137_ << 8 | p_15138_ << 4 | p_15136_)) {
                return this.f_15129_[$$3] & 0xF;
            }
            return this.f_15129_[$$3] >> 4 & 0xF;
        }

        private boolean m_15133_(int p_15134_) {
            return (p_15134_ & 1) == 0;
        }

        private int m_15139_(int p_15140_) {
            return p_15140_ >> 1;
        }
    }

    static class Section {
        private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> f_15186_ = CrudeIncrementalIntIdentityHashBiMap.m_184237_(32);
        private final List<Dynamic<?>> f_15187_;
        private final Dynamic<?> f_15188_;
        private final boolean f_15189_;
        final Int2ObjectMap<IntList> f_15190_ = new Int2ObjectLinkedOpenHashMap();
        final IntList f_15191_ = new IntArrayList();
        public final int f_15185_;
        private final Set<Dynamic<?>> f_15192_ = Sets.newIdentityHashSet();
        private final int[] f_15193_ = new int[4096];

        public Section(Dynamic<?> p_15195_) {
            this.f_15187_ = Lists.newArrayList();
            this.f_15188_ = p_15195_;
            this.f_15185_ = p_15195_.get("Y").asInt(0);
            this.f_15189_ = p_15195_.get("Blocks").result().isPresent();
        }

        public Dynamic<?> m_15197_(int p_15198_) {
            if (p_15198_ < 0 || p_15198_ > 4095) {
                return f_15055_;
            }
            Dynamic<?> $$1 = this.f_15186_.m_7942_(this.f_15193_[p_15198_]);
            return $$1 == null ? f_15055_ : $$1;
        }

        public void m_15202_(int p_15203_, Dynamic<?> p_15204_) {
            if (this.f_15192_.add(p_15204_)) {
                this.f_15187_.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.m_15064_(p_15204_)) ? f_15055_ : p_15204_);
            }
            this.f_15193_[p_15203_] = ChunkPalettedStorageFix.m_15061_(this.f_15186_, p_15204_);
        }

        public int m_15209_(int p_15210_) {
            if (!this.f_15189_) {
                return p_15210_;
            }
            ByteBuffer $$1 = (ByteBuffer)this.f_15188_.get("Blocks").asByteBufferOpt().result().get();
            DataLayer $$2 = this.f_15188_.get("Data").asByteBufferOpt().map(p_15214_ -> new DataLayer(DataFixUtils.toArray((ByteBuffer)p_15214_))).result().orElseGet(DataLayer::new);
            DataLayer $$3 = this.f_15188_.get("Add").asByteBufferOpt().map(p_15208_ -> new DataLayer(DataFixUtils.toArray((ByteBuffer)p_15208_))).result().orElseGet(DataLayer::new);
            this.f_15192_.add(f_15055_);
            ChunkPalettedStorageFix.m_15061_(this.f_15186_, f_15055_);
            this.f_15187_.add(f_15055_);
            for (int $$4 = 0; $$4 < 4096; ++$$4) {
                int $$5 = $$4 & 0xF;
                int $$6 = $$4 >> 8 & 0xF;
                int $$7 = $$4 >> 4 & 0xF;
                int $$8 = $$3.m_15135_($$5, $$6, $$7) << 12 | ($$1.get($$4) & 0xFF) << 4 | $$2.m_15135_($$5, $$6, $$7);
                if (f_15037_.get($$8 >> 4)) {
                    this.m_15199_($$8 >> 4, $$4);
                }
                if (f_15036_.get($$8 >> 4)) {
                    int $$9 = ChunkPalettedStorageFix.m_15086_($$5 == 0, $$5 == 15, $$7 == 0, $$7 == 15);
                    if ($$9 == 0) {
                        this.f_15191_.add($$4);
                    } else {
                        p_15210_ |= $$9;
                    }
                }
                this.m_15202_($$4, BlockStateData.m_14952_($$8));
            }
            return p_15210_;
        }

        private void m_15199_(int p_15200_, int p_15201_) {
            IntList $$2 = (IntList)this.f_15190_.get(p_15200_);
            if ($$2 == null) {
                $$2 = new IntArrayList();
                this.f_15190_.put(p_15200_, (Object)$$2);
            }
            $$2.add(p_15201_);
        }

        public Dynamic<?> m_15196_() {
            Dynamic $$0 = this.f_15188_;
            if (!this.f_15189_) {
                return $$0;
            }
            $$0 = $$0.set("Palette", $$0.createList(this.f_15187_.stream()));
            int $$1 = Math.max(4, DataFixUtils.ceillog2((int)this.f_15192_.size()));
            PackedBitStorage $$2 = new PackedBitStorage($$1, 4096);
            for (int $$3 = 0; $$3 < this.f_15193_.length; ++$$3) {
                $$2.m_14564_($$3, this.f_15193_[$$3]);
            }
            $$0 = $$0.set("BlockStates", $$0.createLongList(Arrays.stream($$2.m_14561_())));
            $$0 = $$0.remove("Blocks");
            $$0 = $$0.remove("Data");
            $$0 = $$0.remove("Add");
            return $$0;
        }
    }
}

