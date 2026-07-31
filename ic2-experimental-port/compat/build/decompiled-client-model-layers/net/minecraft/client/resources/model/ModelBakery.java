/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Splitter
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.tuple.Triple
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.block.model.multipart.Selector;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.texture.AtlasSet;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;

public class ModelBakery {
    public static final Material f_119219_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/fire_0"));
    public static final Material f_119220_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/fire_1"));
    public static final Material f_119221_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/lava_flow"));
    public static final Material f_119222_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/water_flow"));
    public static final Material f_119223_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("block/water_overlay"));
    public static final Material f_119224_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/banner_base"));
    public static final Material f_119225_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/shield_base"));
    public static final Material f_119226_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/shield_base_nopattern"));
    public static final int f_174875_ = 10;
    public static final List<ResourceLocation> f_119227_ = IntStream.range(0, 10).mapToObj(p_119253_ -> new ResourceLocation("block/destroy_stage_" + p_119253_)).collect(Collectors.toList());
    public static final List<ResourceLocation> f_119228_ = f_119227_.stream().map(p_119371_ -> new ResourceLocation("textures/" + p_119371_.m_135815_() + ".png")).collect(Collectors.toList());
    public static final List<RenderType> f_119229_ = f_119228_.stream().map(RenderType::m_110494_).collect(Collectors.toList());
    private static final Set<Material> f_119234_ = Util.m_137469_(Sets.newHashSet(), p_119313_ -> {
        p_119313_.add(f_119222_);
        p_119313_.add(f_119221_);
        p_119313_.add(f_119223_);
        p_119313_.add(f_119219_);
        p_119313_.add(f_119220_);
        p_119313_.add(BellRenderer.f_112227_);
        p_119313_.add(ConduitRenderer.f_112378_);
        p_119313_.add(ConduitRenderer.f_112379_);
        p_119313_.add(ConduitRenderer.f_112380_);
        p_119313_.add(ConduitRenderer.f_112381_);
        p_119313_.add(ConduitRenderer.f_112382_);
        p_119313_.add(ConduitRenderer.f_112383_);
        p_119313_.add(EnchantTableRenderer.f_112405_);
        p_119313_.add(f_119224_);
        p_119313_.add(f_119225_);
        p_119313_.add(f_119226_);
        for (ResourceLocation $$1 : f_119227_) {
            p_119313_.add(new Material(TextureAtlas.f_118259_, $$1));
        }
        p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39693_));
        p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39694_));
        p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39695_));
        p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39696_));
        p_119313_.add(new Material(TextureAtlas.f_118259_, InventoryMenu.f_39697_));
        Sheets.m_110780_(p_119313_::add);
    });
    static final int f_174876_ = -1;
    private static final int f_174877_ = 0;
    private static final Logger f_119235_ = LogUtils.getLogger();
    private static final String f_174878_ = "builtin/";
    private static final String f_174879_ = "builtin/generated";
    private static final String f_174880_ = "builtin/entity";
    private static final String f_174881_ = "missing";
    public static final ModelResourceLocation f_119230_ = new ModelResourceLocation("builtin/missing", "missing");
    private static final String f_119236_ = f_119230_.toString();
    @VisibleForTesting
    public static final String f_119231_ = ("{    'textures': {       'particle': '" + MissingTextureAtlasSprite.m_118071_().m_135815_() + "',       'missingno': '" + MissingTextureAtlasSprite.m_118071_().m_135815_() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '\"');
    private static final Map<String, String> f_119237_ = Maps.newHashMap((Map)ImmutableMap.of((Object)"missing", (Object)f_119231_));
    private static final Splitter f_119238_ = Splitter.on((char)',');
    private static final Splitter f_119239_ = Splitter.on((char)'=').limit(2);
    public static final BlockModel f_119232_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"front\"}"), p_119359_ -> {
        p_119359_.f_111416_ = "generation marker";
    });
    public static final BlockModel f_119233_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"side\"}"), p_119297_ -> {
        p_119297_.f_111416_ = "block entity marker";
    });
    private static final StateDefinition<Block, BlockState> f_119240_ = new StateDefinition.Builder(Blocks.f_50016_).m_61104_(BooleanProperty.m_61465_("map")).m_61101_(Block::m_49966_, BlockState::new);
    private static final ItemModelGenerator f_119241_ = new ItemModelGenerator();
    private static final Map<ResourceLocation, StateDefinition<Block, BlockState>> f_119242_ = ImmutableMap.of((Object)new ResourceLocation("item_frame"), f_119240_, (Object)new ResourceLocation("glow_item_frame"), f_119240_);
    private final ResourceManager f_119243_;
    @Nullable
    private AtlasSet f_119244_;
    private final BlockColors f_119209_;
    private final Set<ResourceLocation> f_119210_ = Sets.newHashSet();
    private final BlockModelDefinition.Context f_119211_ = new BlockModelDefinition.Context();
    private final Map<ResourceLocation, UnbakedModel> f_119212_ = Maps.newHashMap();
    private final Map<Triple<ResourceLocation, Transformation, Boolean>, BakedModel> f_119213_ = Maps.newHashMap();
    private final Map<ResourceLocation, UnbakedModel> f_119214_ = Maps.newHashMap();
    private final Map<ResourceLocation, BakedModel> f_119215_ = Maps.newHashMap();
    private final Map<ResourceLocation, Pair<TextureAtlas, TextureAtlas.Preparations>> f_119216_;
    private int f_119217_ = 1;
    private final Object2IntMap<BlockState> f_119218_ = (Object2IntMap)Util.m_137469_(new Object2IntOpenHashMap(), p_119309_ -> p_119309_.defaultReturnValue(-1));

    public ModelBakery(ResourceManager p_119247_, BlockColors p_119248_, ProfilerFiller p_119249_, int p_119250_) {
        this.f_119243_ = p_119247_;
        this.f_119209_ = p_119248_;
        p_119249_.m_6180_("missing_model");
        try {
            this.f_119212_.put(f_119230_, this.m_119364_(f_119230_));
            this.m_119306_(f_119230_);
        }
        catch (IOException $$4) {
            f_119235_.error("Error loading missing model, should never happen :(", (Throwable)$$4);
            throw new RuntimeException($$4);
        }
        p_119249_.m_6182_("static_definitions");
        f_119242_.forEach((p_119347_, p_119348_) -> p_119348_.m_61056_().forEach(p_174905_ -> this.m_119306_(BlockModelShaper.m_110889_(p_119347_, p_174905_))));
        p_119249_.m_6182_("blocks");
        for (Block $$5 : Registry.f_122824_) {
            $$5.m_49965_().m_61056_().forEach(p_119264_ -> this.m_119306_(BlockModelShaper.m_110895_(p_119264_)));
        }
        p_119249_.m_6182_("items");
        for (ResourceLocation $$6 : Registry.f_122827_.m_6566_()) {
            this.m_119306_(new ModelResourceLocation($$6, "inventory"));
        }
        p_119249_.m_6182_("special");
        this.m_119306_(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
        this.m_119306_(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
        p_119249_.m_6182_("textures");
        LinkedHashSet $$7 = Sets.newLinkedHashSet();
        Set $$8 = this.f_119214_.values().stream().flatMap(p_119340_ -> p_119340_.m_5500_(this::m_119341_, $$7).stream()).collect(Collectors.toSet());
        $$8.addAll(f_119234_);
        $$7.stream().filter(p_119357_ -> !((String)p_119357_.getSecond()).equals(f_119236_)).forEach(p_119292_ -> f_119235_.warn("Unable to resolve texture reference: {} in {}", p_119292_.getFirst(), p_119292_.getSecond()));
        Map<ResourceLocation, List<Material>> $$9 = $$8.stream().collect(Collectors.groupingBy(Material::m_119193_));
        p_119249_.m_6182_("stitching");
        this.f_119216_ = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, List<Material>> $$10 : $$9.entrySet()) {
            TextureAtlas $$11 = new TextureAtlas($$10.getKey());
            TextureAtlas.Preparations $$12 = $$11.m_118307_(this.f_119243_, $$10.getValue().stream().map(Material::m_119203_), p_119249_, p_119250_);
            this.f_119216_.put($$10.getKey(), (Pair<TextureAtlas, TextureAtlas.Preparations>)Pair.of((Object)$$11, (Object)$$12));
        }
        p_119249_.m_7238_();
    }

    public AtlasSet m_119298_(TextureManager p_119299_, ProfilerFiller p_119300_) {
        p_119300_.m_6180_("atlas");
        for (Pair<TextureAtlas, TextureAtlas.Preparations> $$2 : this.f_119216_.values()) {
            TextureAtlas $$3 = (TextureAtlas)$$2.getFirst();
            TextureAtlas.Preparations $$4 = (TextureAtlas.Preparations)$$2.getSecond();
            $$3.m_118312_($$4);
            p_119299_.m_118495_($$3.m_118330_(), $$3);
            p_119299_.m_174784_($$3.m_118330_());
            $$3.m_118322_($$4);
        }
        this.f_119244_ = new AtlasSet(this.f_119216_.values().stream().map(Pair::getFirst).collect(Collectors.toList()));
        p_119300_.m_6182_("baking");
        this.f_119214_.keySet().forEach(p_119369_ -> {
            BakedModel $$1 = null;
            try {
                $$1 = this.m_119349_((ResourceLocation)p_119369_, BlockModelRotation.X0_Y0);
            }
            catch (Exception $$2) {
                f_119235_.warn("Unable to bake model: '{}': {}", p_119369_, (Object)$$2);
            }
            if ($$1 != null) {
                this.f_119215_.put((ResourceLocation)p_119369_, $$1);
            }
        });
        p_119300_.m_7238_();
        return this.f_119244_;
    }

    private static Predicate<BlockState> m_119273_(StateDefinition<Block, BlockState> p_119274_, String p_119275_) {
        HashMap $$2 = Maps.newHashMap();
        for (String $$3 : f_119238_.split((CharSequence)p_119275_)) {
            Iterator $$4 = f_119239_.split((CharSequence)$$3).iterator();
            if (!$$4.hasNext()) continue;
            String $$5 = (String)$$4.next();
            Property<?> $$6 = p_119274_.m_61081_($$5);
            if ($$6 != null && $$4.hasNext()) {
                String $$7 = (String)$$4.next();
                Object $$8 = ModelBakery.m_119276_($$6, $$7);
                if ($$8 != null) {
                    $$2.put($$6, $$8);
                    continue;
                }
                throw new RuntimeException("Unknown value: '" + $$7 + "' for blockstate property: '" + $$5 + "' " + $$6.m_6908_());
            }
            if ($$5.isEmpty()) continue;
            throw new RuntimeException("Unknown blockstate property: '" + $$5 + "'");
        }
        Block $$9 = p_119274_.m_61091_();
        return p_119262_ -> {
            if (p_119262_ == null || !p_119262_.m_60713_($$9)) {
                return false;
            }
            for (Map.Entry $$3 : $$2.entrySet()) {
                if (Objects.equals(p_119262_.m_61143_((Property)$$3.getKey()), $$3.getValue())) continue;
                return false;
            }
            return true;
        };
    }

    @Nullable
    static <T extends Comparable<T>> T m_119276_(Property<T> p_119277_, String p_119278_) {
        return (T)((Comparable)p_119277_.m_6215_(p_119278_).orElse(null));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public UnbakedModel m_119341_(ResourceLocation p_119342_) {
        if (this.f_119212_.containsKey(p_119342_)) {
            return this.f_119212_.get(p_119342_);
        }
        if (this.f_119210_.contains(p_119342_)) {
            throw new IllegalStateException("Circular reference while loading " + p_119342_);
        }
        this.f_119210_.add(p_119342_);
        UnbakedModel $$1 = this.f_119212_.get(f_119230_);
        while (!this.f_119210_.isEmpty()) {
            ResourceLocation $$2 = this.f_119210_.iterator().next();
            try {
                if (this.f_119212_.containsKey($$2)) continue;
                this.m_119362_($$2);
            }
            catch (BlockStateDefinitionException $$3) {
                f_119235_.warn($$3.getMessage());
                this.f_119212_.put($$2, $$1);
            }
            catch (Exception $$4) {
                f_119235_.warn("Unable to load model: '{}' referenced from: {}: {}", new Object[]{$$2, p_119342_, $$4});
                this.f_119212_.put($$2, $$1);
            }
            finally {
                this.f_119210_.remove($$2);
            }
        }
        return this.f_119212_.getOrDefault(p_119342_, $$1);
    }

    private void m_119362_(ResourceLocation p_119363_) throws Exception {
        if (!(p_119363_ instanceof ModelResourceLocation)) {
            this.m_119352_(p_119363_, this.m_119364_(p_119363_));
            return;
        }
        ModelResourceLocation $$1 = (ModelResourceLocation)p_119363_;
        if (Objects.equals($$1.m_119448_(), "inventory")) {
            ResourceLocation $$2 = new ResourceLocation(p_119363_.m_135827_(), "item/" + p_119363_.m_135815_());
            BlockModel $$3 = this.m_119364_($$2);
            this.m_119352_($$1, $$3);
            this.f_119212_.put($$2, $$3);
        } else {
            ResourceLocation $$4 = new ResourceLocation(p_119363_.m_135827_(), p_119363_.m_135815_());
            StateDefinition $$5 = Optional.ofNullable(f_119242_.get($$4)).orElseGet(() -> Registry.f_122824_.m_7745_($$4).m_49965_());
            this.f_119211_.m_111552_($$5);
            ImmutableList $$6 = ImmutableList.copyOf(this.f_119209_.m_92575_((Block)$$5.m_61091_()));
            ImmutableList $$7 = $$5.m_61056_();
            HashMap $$8 = Maps.newHashMap();
            $$7.forEach(p_119330_ -> $$8.put(BlockModelShaper.m_110889_($$4, p_119330_), p_119330_));
            HashMap $$9 = Maps.newHashMap();
            ResourceLocation $$10 = new ResourceLocation(p_119363_.m_135827_(), "blockstates/" + p_119363_.m_135815_() + ".json");
            UnbakedModel $$11 = this.f_119212_.get(f_119230_);
            ModelGroupKey $$12 = new ModelGroupKey((List<UnbakedModel>)ImmutableList.of((Object)$$11), (List<Object>)ImmutableList.of());
            Pair $$13 = Pair.of((Object)$$11, () -> $$12);
            try {
                List<Pair> $$14 = this.f_119243_.m_213829_($$10).stream().map(p_242079_ -> {
                    Pair pair;
                    block8: {
                        BufferedReader $$2 = p_242079_.m_215508_();
                        try {
                            pair = Pair.of((Object)p_242079_.m_215506_(), (Object)BlockModelDefinition.m_111540_(this.f_119211_, $$2));
                            if ($$2 == null) break block8;
                        }
                        catch (Throwable throwable) {
                            try {
                                if ($$2 != null) {
                                    try {
                                        ((Reader)$$2).close();
                                    }
                                    catch (Throwable throwable2) {
                                        throwable.addSuppressed(throwable2);
                                    }
                                }
                                throw throwable;
                            }
                            catch (Exception $$3) {
                                throw new BlockStateDefinitionException(String.format(Locale.ROOT, "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", $$10, p_242079_.m_215506_(), $$3.getMessage()));
                            }
                        }
                        ((Reader)$$2).close();
                    }
                    return pair;
                }).toList();
                for (Pair $$15 : $$14) {
                    MultiPart $$19;
                    BlockModelDefinition $$16 = (BlockModelDefinition)$$15.getSecond();
                    IdentityHashMap $$17 = Maps.newIdentityHashMap();
                    if ($$16.m_111543_()) {
                        MultiPart $$18 = $$16.m_111544_();
                        $$7.forEach(arg_0 -> ModelBakery.m_119322_($$17, $$18, (List)$$6, arg_0));
                    } else {
                        $$19 = null;
                    }
                    $$16.m_111539_().forEach((arg_0, arg_1) -> ModelBakery.m_119279_($$7, $$5, $$17, (List)$$6, $$19, $$13, $$16, $$10, $$15, arg_0, arg_1));
                    $$9.putAll($$17);
                }
            }
            catch (BlockStateDefinitionException $$21) {
                throw $$21;
            }
            catch (Exception $$22) {
                throw new BlockStateDefinitionException(String.format(Locale.ROOT, "Exception loading blockstate definition: '%s': %s", $$10, $$22));
            }
            finally {
                HashMap $$23 = Maps.newHashMap();
                $$8.forEach((p_119336_, p_119337_) -> {
                    Pair $$6 = (Pair)$$9.get(p_119337_);
                    if ($$6 == null) {
                        f_119235_.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", (Object)$$10, p_119336_);
                        $$6 = $$13;
                    }
                    this.m_119352_((ResourceLocation)p_119336_, (UnbakedModel)$$6.getFirst());
                    try {
                        ModelGroupKey $$7 = (ModelGroupKey)((Supplier)$$6.getSecond()).get();
                        $$23.computeIfAbsent($$7, p_174894_ -> Sets.newIdentityHashSet()).add(p_119337_);
                    }
                    catch (Exception $$8) {
                        f_119235_.warn("Exception evaluating model definition: '{}'", p_119336_, (Object)$$8);
                    }
                });
                $$23.forEach((p_119304_, p_119305_) -> {
                    Iterator $$2 = p_119305_.iterator();
                    while ($$2.hasNext()) {
                        BlockState $$3 = (BlockState)$$2.next();
                        if ($$3.m_60799_() == RenderShape.MODEL) continue;
                        $$2.remove();
                        this.f_119218_.put((Object)$$3, 0);
                    }
                    if (p_119305_.size() > 1) {
                        this.m_119310_((Iterable<BlockState>)p_119305_);
                    }
                });
            }
        }
    }

    private void m_119352_(ResourceLocation p_119353_, UnbakedModel p_119354_) {
        this.f_119212_.put(p_119353_, p_119354_);
        this.f_119210_.addAll(p_119354_.m_7970_());
    }

    private void m_119306_(ModelResourceLocation p_119307_) {
        UnbakedModel $$1 = this.m_119341_(p_119307_);
        this.f_119212_.put(p_119307_, $$1);
        this.f_119214_.put(p_119307_, $$1);
    }

    private void m_119310_(Iterable<BlockState> p_119311_) {
        int $$1 = this.f_119217_++;
        p_119311_.forEach(p_119256_ -> this.f_119218_.put(p_119256_, $$1));
    }

    @Nullable
    public BakedModel m_119349_(ResourceLocation p_119350_, ModelState p_119351_) {
        BlockModel $$4;
        Triple $$2 = Triple.of((Object)p_119350_, (Object)p_119351_.m_6189_(), (Object)p_119351_.m_7538_());
        if (this.f_119213_.containsKey($$2)) {
            return this.f_119213_.get($$2);
        }
        if (this.f_119244_ == null) {
            throw new IllegalStateException("bake called too early");
        }
        UnbakedModel $$3 = this.m_119341_(p_119350_);
        if ($$3 instanceof BlockModel && ($$4 = (BlockModel)$$3).m_111490_() == f_119232_) {
            return f_119241_.m_111670_(this.f_119244_::m_117971_, $$4).m_111449_(this, $$4, this.f_119244_::m_117971_, p_119351_, p_119350_, false);
        }
        BakedModel $$5 = $$3.m_7611_(this, this.f_119244_::m_117971_, p_119351_, p_119350_);
        this.f_119213_.put((Triple<ResourceLocation, Transformation, Boolean>)$$2, $$5);
        return $$5;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private BlockModel m_119364_(ResourceLocation p_119365_) throws IOException {
        String $$2;
        Reader $$1;
        block8: {
            block7: {
                BlockModel blockModel;
                $$1 = null;
                try {
                    $$2 = p_119365_.m_135815_();
                    if (!f_174879_.equals($$2)) break block7;
                    blockModel = f_119232_;
                }
                catch (Throwable throwable) {
                    IOUtils.closeQuietly($$1);
                    throw throwable;
                }
                IOUtils.closeQuietly($$1);
                return blockModel;
            }
            if (!f_174880_.equals($$2)) break block8;
            BlockModel blockModel = f_119233_;
            IOUtils.closeQuietly($$1);
            return blockModel;
        }
        if ($$2.startsWith(f_174878_)) {
            String $$3 = $$2.substring(f_174878_.length());
            String $$4 = f_119237_.get($$3);
            if ($$4 == null) {
                throw new FileNotFoundException(p_119365_.toString());
            }
            $$1 = new StringReader($$4);
        } else {
            $$1 = this.f_119243_.m_215597_(new ResourceLocation(p_119365_.m_135827_(), "models/" + p_119365_.m_135815_() + ".json"));
        }
        BlockModel $$5 = BlockModel.m_111461_($$1);
        $$5.f_111416_ = p_119365_.toString();
        BlockModel blockModel = $$5;
        IOUtils.closeQuietly((Reader)$$1);
        return blockModel;
    }

    public Map<ResourceLocation, BakedModel> m_119251_() {
        return this.f_119215_;
    }

    public Object2IntMap<BlockState> m_119355_() {
        return this.f_119218_;
    }

    private static /* synthetic */ void m_119279_(ImmutableList p_119280_, StateDefinition p_119281_, Map p_119282_, List p_119283_, MultiPart p_119284_, Pair p_119285_, BlockModelDefinition p_119286_, ResourceLocation p_119287_, Pair p_119288_, String p_119289_, MultiVariant p_119290_) {
        try {
            p_119280_.stream().filter(ModelBakery.m_119273_(p_119281_, p_119289_)).forEach(p_174902_ -> {
                Pair $$7 = p_119282_.put(p_174902_, Pair.of((Object)p_119290_, () -> ModelGroupKey.m_119383_(p_174902_, p_119290_, p_119283_)));
                if ($$7 != null && $$7.getFirst() != p_119284_) {
                    p_119282_.put(p_174902_, p_119285_);
                    throw new RuntimeException("Overlapping definition with: " + (String)p_119286_.m_111539_().entrySet().stream().filter(p_174892_ -> p_174892_.getValue() == $$7.getFirst()).findFirst().get().getKey());
                }
            });
        }
        catch (Exception $$11) {
            f_119235_.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", new Object[]{p_119287_, p_119288_.getFirst(), p_119289_, $$11.getMessage()});
        }
    }

    private static /* synthetic */ void m_119322_(Map p_119323_, MultiPart p_119324_, List p_119325_, BlockState p_119326_) {
        p_119323_.put(p_119326_, Pair.of((Object)p_119324_, () -> ModelGroupKey.m_119379_(p_119326_, p_119324_, p_119325_)));
    }

    static class BlockStateDefinitionException
    extends RuntimeException {
        public BlockStateDefinitionException(String p_119373_) {
            super(p_119373_);
        }
    }

    static class ModelGroupKey {
        private final List<UnbakedModel> f_119374_;
        private final List<Object> f_119375_;

        public ModelGroupKey(List<UnbakedModel> p_119377_, List<Object> p_119378_) {
            this.f_119374_ = p_119377_;
            this.f_119375_ = p_119378_;
        }

        public boolean equals(Object p_119395_) {
            if (this == p_119395_) {
                return true;
            }
            if (p_119395_ instanceof ModelGroupKey) {
                ModelGroupKey $$1 = (ModelGroupKey)p_119395_;
                return Objects.equals(this.f_119374_, $$1.f_119374_) && Objects.equals(this.f_119375_, $$1.f_119375_);
            }
            return false;
        }

        public int hashCode() {
            return 31 * this.f_119374_.hashCode() + this.f_119375_.hashCode();
        }

        public static ModelGroupKey m_119379_(BlockState p_119380_, MultiPart p_119381_, Collection<Property<?>> p_119382_) {
            StateDefinition<Block, BlockState> $$3 = p_119380_.m_60734_().m_49965_();
            List $$4 = (List)p_119381_.m_111967_().stream().filter(p_119393_ -> p_119393_.m_112021_($$3).test(p_119380_)).map(Selector::m_112020_).collect(ImmutableList.toImmutableList());
            List<Object> $$5 = ModelGroupKey.m_119387_(p_119380_, p_119382_);
            return new ModelGroupKey($$4, $$5);
        }

        public static ModelGroupKey m_119383_(BlockState p_119384_, UnbakedModel p_119385_, Collection<Property<?>> p_119386_) {
            List<Object> $$3 = ModelGroupKey.m_119387_(p_119384_, p_119386_);
            return new ModelGroupKey((List<UnbakedModel>)ImmutableList.of((Object)p_119385_), $$3);
        }

        private static List<Object> m_119387_(BlockState p_119388_, Collection<Property<?>> p_119389_) {
            return (List)p_119389_.stream().map(p_119388_::m_61143_).collect(ImmutableList.toImmutableList());
        }
    }
}

