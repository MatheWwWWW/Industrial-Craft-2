/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Joiner
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BuiltInModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public class BlockModel
implements UnbakedModel {
    private static final Logger f_111420_ = LogUtils.getLogger();
    private static final FaceBakery f_111421_ = new FaceBakery();
    @VisibleForTesting
    static final Gson f_111415_ = new GsonBuilder().registerTypeAdapter(BlockModel.class, (Object)new Deserializer()).registerTypeAdapter(BlockElement.class, (Object)new BlockElement.Deserializer()).registerTypeAdapter(BlockElementFace.class, (Object)new BlockElementFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, (Object)new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransform.class, (Object)new ItemTransform.Deserializer()).registerTypeAdapter(ItemTransforms.class, (Object)new ItemTransforms.Deserializer()).registerTypeAdapter(ItemOverride.class, (Object)new ItemOverride.Deserializer()).create();
    private static final char f_173419_ = '#';
    public static final String f_173418_ = "particle";
    private final List<BlockElement> f_111422_;
    @Nullable
    private final GuiLight f_111423_;
    private final boolean f_111424_;
    private final ItemTransforms f_111425_;
    private final List<ItemOverride> f_111426_;
    public String f_111416_ = "";
    @VisibleForTesting
    protected final Map<String, Either<Material, String>> f_111417_;
    @Nullable
    protected BlockModel f_111418_;
    @Nullable
    protected ResourceLocation f_111419_;

    public static BlockModel m_111461_(Reader p_111462_) {
        return GsonHelper.m_13776_(f_111415_, p_111462_, BlockModel.class);
    }

    public static BlockModel m_111463_(String p_111464_) {
        return BlockModel.m_111461_(new StringReader(p_111464_));
    }

    public BlockModel(@Nullable ResourceLocation p_111429_, List<BlockElement> p_111430_, Map<String, Either<Material, String>> p_111431_, boolean p_111432_, @Nullable GuiLight p_111433_, ItemTransforms p_111434_, List<ItemOverride> p_111435_) {
        this.f_111422_ = p_111430_;
        this.f_111424_ = p_111432_;
        this.f_111423_ = p_111433_;
        this.f_111417_ = p_111431_;
        this.f_111419_ = p_111429_;
        this.f_111425_ = p_111434_;
        this.f_111426_ = p_111435_;
    }

    public List<BlockElement> m_111436_() {
        if (this.f_111422_.isEmpty() && this.f_111418_ != null) {
            return this.f_111418_.m_111436_();
        }
        return this.f_111422_;
    }

    public boolean m_111476_() {
        if (this.f_111418_ != null) {
            return this.f_111418_.m_111476_();
        }
        return this.f_111424_;
    }

    public GuiLight m_111479_() {
        if (this.f_111423_ != null) {
            return this.f_111423_;
        }
        if (this.f_111418_ != null) {
            return this.f_111418_.m_111479_();
        }
        return GuiLight.SIDE;
    }

    public boolean m_173420_() {
        return this.f_111419_ == null || this.f_111418_ != null && this.f_111418_.m_173420_();
    }

    public List<ItemOverride> m_111484_() {
        return this.f_111426_;
    }

    private ItemOverrides m_111446_(ModelBakery p_111447_, BlockModel p_111448_) {
        if (this.f_111426_.isEmpty()) {
            return ItemOverrides.f_111734_;
        }
        return new ItemOverrides(p_111447_, p_111448_, p_111447_::m_119341_, this.f_111426_);
    }

    @Override
    public Collection<ResourceLocation> m_7970_() {
        HashSet $$0 = Sets.newHashSet();
        for (ItemOverride $$1 : this.f_111426_) {
            $$0.add($$1.m_111718_());
        }
        if (this.f_111419_ != null) {
            $$0.add(this.f_111419_);
        }
        return $$0;
    }

    @Override
    public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> p_111469_, Set<Pair<String, String>> p_111470_) {
        LinkedHashSet $$2 = Sets.newLinkedHashSet();
        BlockModel $$3 = this;
        while ($$3.f_111419_ != null && $$3.f_111418_ == null) {
            $$2.add($$3);
            UnbakedModel $$4 = p_111469_.apply($$3.f_111419_);
            if ($$4 == null) {
                f_111420_.warn("No parent '{}' while loading model '{}'", (Object)this.f_111419_, (Object)$$3);
            }
            if ($$2.contains($$4)) {
                f_111420_.warn("Found 'parent' loop while loading model '{}' in chain: {} -> {}", new Object[]{$$3, $$2.stream().map(Object::toString).collect(Collectors.joining(" -> ")), this.f_111419_});
                $$4 = null;
            }
            if ($$4 == null) {
                $$3.f_111419_ = ModelBakery.f_119230_;
                $$4 = p_111469_.apply($$3.f_111419_);
            }
            if (!($$4 instanceof BlockModel)) {
                throw new IllegalStateException("BlockModel parent has to be a block model.");
            }
            $$3.f_111418_ = (BlockModel)$$4;
            $$3 = $$3.f_111418_;
        }
        HashSet $$5 = Sets.newHashSet((Object[])new Material[]{this.m_111480_(f_173418_)});
        for (BlockElement $$6 : this.m_111436_()) {
            for (BlockElementFace $$7 : $$6.f_111310_.values()) {
                Material $$8 = this.m_111480_($$7.f_111356_);
                if (Objects.equals($$8.m_119203_(), MissingTextureAtlasSprite.m_118071_())) {
                    p_111470_.add((Pair<String, String>)Pair.of((Object)$$7.f_111356_, (Object)this.f_111416_));
                }
                $$5.add($$8);
            }
        }
        this.f_111426_.forEach(p_111475_ -> {
            UnbakedModel $$4 = (UnbakedModel)p_111469_.apply(p_111475_.m_111718_());
            if (Objects.equals($$4, this)) {
                return;
            }
            $$5.addAll($$4.m_5500_(p_111469_, p_111470_));
        });
        if (this.m_111490_() == ModelBakery.f_119232_) {
            ItemModelGenerator.f_111635_.forEach(p_111467_ -> $$5.add(this.m_111480_((String)p_111467_)));
        }
        return $$5;
    }

    @Override
    public BakedModel m_7611_(ModelBakery p_111457_, Function<Material, TextureAtlasSprite> p_111458_, ModelState p_111459_, ResourceLocation p_111460_) {
        return this.m_111449_(p_111457_, this, p_111458_, p_111459_, p_111460_, true);
    }

    public BakedModel m_111449_(ModelBakery p_111450_, BlockModel p_111451_, Function<Material, TextureAtlasSprite> p_111452_, ModelState p_111453_, ResourceLocation p_111454_, boolean p_111455_) {
        TextureAtlasSprite $$6 = p_111452_.apply(this.m_111480_(f_173418_));
        if (this.m_111490_() == ModelBakery.f_119233_) {
            return new BuiltInModel(this.m_111491_(), this.m_111446_(p_111450_, p_111451_), $$6, this.m_111479_().m_111526_());
        }
        SimpleBakedModel.Builder $$7 = new SimpleBakedModel.Builder(this, this.m_111446_(p_111450_, p_111451_), p_111455_).m_119528_($$6);
        for (BlockElement $$8 : this.m_111436_()) {
            for (Direction $$9 : $$8.f_111310_.keySet()) {
                BlockElementFace $$10 = $$8.f_111310_.get($$9);
                TextureAtlasSprite $$11 = p_111452_.apply(this.m_111480_($$10.f_111356_));
                if ($$10.f_111354_ == null) {
                    $$7.m_119526_(BlockModel.m_111437_($$8, $$10, $$11, $$9, p_111453_, p_111454_));
                    continue;
                }
                $$7.m_119530_(Direction.m_122384_(p_111453_.m_6189_().m_121104_(), $$10.f_111354_), BlockModel.m_111437_($$8, $$10, $$11, $$9, p_111453_, p_111454_));
            }
        }
        return $$7.m_119533_();
    }

    private static BakedQuad m_111437_(BlockElement p_111438_, BlockElementFace p_111439_, TextureAtlasSprite p_111440_, Direction p_111441_, ModelState p_111442_, ResourceLocation p_111443_) {
        return f_111421_.m_111600_(p_111438_.f_111308_, p_111438_.f_111309_, p_111439_, p_111440_, p_111441_, p_111442_, p_111438_.f_111311_, p_111438_.f_111312_, p_111443_);
    }

    public boolean m_111477_(String p_111478_) {
        return !MissingTextureAtlasSprite.m_118071_().equals(this.m_111480_(p_111478_).m_119203_());
    }

    public Material m_111480_(String p_111481_) {
        if (BlockModel.m_111488_(p_111481_)) {
            p_111481_ = p_111481_.substring(1);
        }
        ArrayList $$1 = Lists.newArrayList();
        Either<Material, String> $$2;
        Optional $$3;
        while (!($$3 = ($$2 = this.m_111485_(p_111481_)).left()).isPresent()) {
            p_111481_ = (String)$$2.right().get();
            if ($$1.contains(p_111481_)) {
                f_111420_.warn("Unable to resolve texture due to reference chain {}->{} in {}", new Object[]{Joiner.on((String)"->").join((Iterable)$$1), p_111481_, this.f_111416_});
                return new Material(TextureAtlas.f_118259_, MissingTextureAtlasSprite.m_118071_());
            }
            $$1.add(p_111481_);
        }
        return (Material)$$3.get();
    }

    private Either<Material, String> m_111485_(String p_111486_) {
        BlockModel $$1 = this;
        while ($$1 != null) {
            Either<Material, String> $$2 = $$1.f_111417_.get(p_111486_);
            if ($$2 != null) {
                return $$2;
            }
            $$1 = $$1.f_111418_;
        }
        return Either.left((Object)new Material(TextureAtlas.f_118259_, MissingTextureAtlasSprite.m_118071_()));
    }

    static boolean m_111488_(String p_111489_) {
        return p_111489_.charAt(0) == '#';
    }

    public BlockModel m_111490_() {
        return this.f_111418_ == null ? this : this.f_111418_.m_111490_();
    }

    public ItemTransforms m_111491_() {
        ItemTransform $$0 = this.m_111444_(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
        ItemTransform $$1 = this.m_111444_(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        ItemTransform $$2 = this.m_111444_(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
        ItemTransform $$3 = this.m_111444_(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
        ItemTransform $$4 = this.m_111444_(ItemTransforms.TransformType.HEAD);
        ItemTransform $$5 = this.m_111444_(ItemTransforms.TransformType.GUI);
        ItemTransform $$6 = this.m_111444_(ItemTransforms.TransformType.GROUND);
        ItemTransform $$7 = this.m_111444_(ItemTransforms.TransformType.FIXED);
        return new ItemTransforms($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    private ItemTransform m_111444_(ItemTransforms.TransformType p_111445_) {
        if (this.f_111418_ != null && !this.f_111425_.m_111810_(p_111445_)) {
            return this.f_111418_.m_111444_(p_111445_);
        }
        return this.f_111425_.m_111808_(p_111445_);
    }

    public String toString() {
        return this.f_111416_;
    }

    public static final class GuiLight
    extends Enum<GuiLight> {
        public static final /* enum */ GuiLight FRONT = new GuiLight("front");
        public static final /* enum */ GuiLight SIDE = new GuiLight("side");
        private final String f_111519_;
        private static final /* synthetic */ GuiLight[] $VALUES;

        public static GuiLight[] values() {
            return (GuiLight[])$VALUES.clone();
        }

        public static GuiLight valueOf(String p_111530_) {
            return Enum.valueOf(GuiLight.class, p_111530_);
        }

        private GuiLight(String p_111525_) {
            this.f_111519_ = p_111525_;
        }

        public static GuiLight m_111527_(String p_111528_) {
            for (GuiLight $$1 : GuiLight.values()) {
                if (!$$1.f_111519_.equals(p_111528_)) continue;
                return $$1;
            }
            throw new IllegalArgumentException("Invalid gui light: " + p_111528_);
        }

        public boolean m_111526_() {
            return this == SIDE;
        }

        private static /* synthetic */ GuiLight[] m_173422_() {
            return new GuiLight[]{FRONT, SIDE};
        }

        static {
            $VALUES = GuiLight.m_173422_();
        }
    }

    public static class Deserializer
    implements JsonDeserializer<BlockModel> {
        private static final boolean f_173421_ = true;

        public BlockModel deserialize(JsonElement p_111498_, Type p_111499_, JsonDeserializationContext p_111500_) throws JsonParseException {
            JsonObject $$3 = p_111498_.getAsJsonObject();
            List<BlockElement> $$4 = this.m_111506_(p_111500_, $$3);
            String $$5 = this.m_111511_($$3);
            Map<String, Either<Material, String>> $$6 = this.m_111509_($$3);
            boolean $$7 = this.m_111501_($$3);
            ItemTransforms $$8 = ItemTransforms.f_111786_;
            if ($$3.has("display")) {
                JsonObject $$9 = GsonHelper.m_13930_($$3, "display");
                $$8 = (ItemTransforms)p_111500_.deserialize((JsonElement)$$9, ItemTransforms.class);
            }
            List<ItemOverride> $$10 = this.m_111494_(p_111500_, $$3);
            GuiLight $$11 = null;
            if ($$3.has("gui_light")) {
                $$11 = GuiLight.m_111527_(GsonHelper.m_13906_($$3, "gui_light"));
            }
            ResourceLocation $$12 = $$5.isEmpty() ? null : new ResourceLocation($$5);
            return new BlockModel($$12, $$4, $$6, $$7, $$11, $$8, $$10);
        }

        protected List<ItemOverride> m_111494_(JsonDeserializationContext p_111495_, JsonObject p_111496_) {
            ArrayList $$2 = Lists.newArrayList();
            if (p_111496_.has("overrides")) {
                JsonArray $$3 = GsonHelper.m_13933_(p_111496_, "overrides");
                for (JsonElement $$4 : $$3) {
                    $$2.add((ItemOverride)p_111495_.deserialize($$4, ItemOverride.class));
                }
            }
            return $$2;
        }

        private Map<String, Either<Material, String>> m_111509_(JsonObject p_111510_) {
            ResourceLocation $$1 = TextureAtlas.f_118259_;
            HashMap $$2 = Maps.newHashMap();
            if (p_111510_.has("textures")) {
                JsonObject $$3 = GsonHelper.m_13930_(p_111510_, "textures");
                for (Map.Entry $$4 : $$3.entrySet()) {
                    $$2.put((String)$$4.getKey(), Deserializer.m_111503_($$1, ((JsonElement)$$4.getValue()).getAsString()));
                }
            }
            return $$2;
        }

        private static Either<Material, String> m_111503_(ResourceLocation p_111504_, String p_111505_) {
            if (BlockModel.m_111488_(p_111505_)) {
                return Either.right((Object)p_111505_.substring(1));
            }
            ResourceLocation $$2 = ResourceLocation.m_135820_(p_111505_);
            if ($$2 == null) {
                throw new JsonParseException(p_111505_ + " is not valid resource location");
            }
            return Either.left((Object)new Material(p_111504_, $$2));
        }

        private String m_111511_(JsonObject p_111512_) {
            return GsonHelper.m_13851_(p_111512_, "parent", "");
        }

        protected boolean m_111501_(JsonObject p_111502_) {
            return GsonHelper.m_13855_(p_111502_, "ambientocclusion", true);
        }

        protected List<BlockElement> m_111506_(JsonDeserializationContext p_111507_, JsonObject p_111508_) {
            ArrayList $$2 = Lists.newArrayList();
            if (p_111508_.has("elements")) {
                for (JsonElement $$3 : GsonHelper.m_13933_(p_111508_, "elements")) {
                    $$2.add((BlockElement)p_111507_.deserialize($$3, BlockElement.class));
                }
            }
            return $$2;
        }

        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }

    public static class LoopException
    extends RuntimeException {
        public LoopException(String p_173424_) {
            super(p_173424_);
        }
    }
}

