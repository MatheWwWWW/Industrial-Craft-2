/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.math.Quaternion
 *  com.mojang.math.Transformation
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemModelShaper
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockModelShaper
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockModel
 *  net.minecraft.client.renderer.block.model.ItemTransform
 *  net.minecraft.client.renderer.block.model.ItemTransforms
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.ChunkRenderTypeSet
 *  net.minecraftforge.client.event.ModelEvent$BakingCompleted
 *  net.minecraftforge.client.model.SimpleModelState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.geometry.UnbakedGeometryHelper
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.platform.rendering;

import com.google.common.collect.ImmutableMap;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import ic2.core.IC2;
import ic2.core.platform.rendering.features.ICachedModel;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.features.block.IDoubleSidedModel;
import ic2.core.platform.rendering.features.block.IEmptyBlockModel;
import ic2.core.platform.rendering.features.item.ICustomItemModel;
import ic2.core.platform.rendering.features.item.ICustomItemModelTransform;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.platform.rendering.features.item.ILayeredItemModel;
import ic2.core.platform.rendering.features.item.IMultiItemModel;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.DoubleSidedBlockModel;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import ic2.core.platform.rendering.models.items.MultiItemModel;
import ic2.core.platform.rendering.models.items.SimpleItemModel;
import ic2.core.platform.rendering.models.items.SimpleLayeredItemModel;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.plugins.IRegistryProvider;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

public class IC2Models {
    private static final ResourceLocation BUILT_IN = new ResourceLocation("minecraft:builtin/generated");
    public static final ResourceLocation HELD_LOCATION = new ResourceLocation("minecraft:models/item/handheld");
    public static final ResourceLocation ITEM_LOCATION = new ResourceLocation("minecraft:models/item/generated");
    public static final ResourceLocation BLOCK_LOCATION = new ResourceLocation("minecraft:models/block/block");
    public static final IC2Models INSTANCE = new IC2Models();
    static Map<ResourceLocation, ItemTransforms> TRANSFORM_CACHE = CollectionUtils.createMap();
    static ItemTransforms[] DEFAULT_TYPES = new ItemTransforms[3];
    Map<BaseModel, IRegistryProvider> allModels = CollectionUtils.createLinkedMap();
    List<ICachedModel> cached = CollectionUtils.createList();

    public static ItemTransforms getItemTransforms() {
        return DEFAULT_TYPES[0];
    }

    public static ItemTransforms getToolTransforms() {
        return DEFAULT_TYPES[1];
    }

    public static ItemTransforms getBlockTransforms() {
        return DEFAULT_TYPES[2];
    }

    public boolean loadModels() {
        boolean crashed = false;
        for (Map.Entry<BaseModel, IRegistryProvider> entry : this.allModels.entrySet()) {
            BaseModel model = entry.getKey();
            if (model instanceof ICachedModel) {
                this.cached.add((ICachedModel)((Object)model));
            }
            try {
                model.init();
            }
            catch (Exception e) {
                crashed = true;
                IC2.LOGGER.error("[" + entry.getValue().getRegistryName() + "] Model has a Crash. Finishing Model Loading for easier fixing, then Aborting Game. Error: ", (Throwable)e);
            }
        }
        return crashed;
    }

    @SubscribeEvent
    public void onModelLoadEvent(ModelEvent.BakingCompleted event) {
        for (ICachedModel model : this.cached) {
            model.clearCache();
        }
        this.cached.clear();
        this.allModels.clear();
        TRANSFORM_CACHE.clear();
        IC2Models.DEFAULT_TYPES[1] = IC2Models.getTransformMap(ITEM_LOCATION);
        IC2Models.DEFAULT_TYPES[0] = IC2Models.getTransformMap(HELD_LOCATION);
        IC2Models.DEFAULT_TYPES[2] = IC2Models.getTransformMap(BLOCK_LOCATION);
        this.handleBlocks(event);
        this.handleItems(event);
        if (this.loadModels()) {
            IC2.LOGGER.info("Found Crashing Models. Closing Game. Fix Models first");
            ServerLifecycleHooks.handleExit((int)0);
        }
    }

    public static boolean hasCustomModelStates(ResourceLocation location) {
        Block block = (Block)ForgeRegistries.BLOCKS.getValue(location);
        return block instanceof IBlockModel || block instanceof ICustomBlockModel || block instanceof IEmptyBlockModel;
    }

    public static boolean hasCustomItemModel(ResourceLocation location) {
        Item item = (Item)ForgeRegistries.ITEMS.getValue(location);
        return item instanceof ISimpleItemModel || item instanceof IItemModel || item instanceof ICustomItemModel || IC2Models.hasCustomModelStates(location);
    }

    private void handleBlocks(ModelEvent.BakingCompleted event) {
        ItemModelShaper mesher = Minecraft.m_91087_().m_91291_().m_115103_();
        for (Block block : ForgeRegistries.BLOCKS) {
            int count = 0;
            if (block instanceof IBlockModel) {
                IBlockModel simple = (IBlockModel)block;
                boolean doubleModel = block instanceof IDoubleSidedModel;
                for (BlockState state : simple.getModelStates()) {
                    SimpleBlockModel model = doubleModel ? new DoubleSidedBlockModel(state, simple) : new SimpleBlockModel(state, simple);
                    model.setTransforms(DEFAULT_TYPES[2]);
                    Block block2 = state.m_60734_();
                    if (block2 instanceof IRenderType) {
                        IRenderType type = (IRenderType)block2;
                        model.setRenderTypes(this.create(state, type));
                    }
                    this.allModels.put(model, (IRegistryProvider)block);
                    this.putIfNotPresent(event.getModels(), model, BlockModelShaper.m_110895_((BlockState)state));
                    ++count;
                }
            }
            if (block instanceof ICustomBlockModel) {
                ICustomBlockModel custom = (ICustomBlockModel)block;
                for (BlockState state : custom.getCustomStates()) {
                    BaseModel model = custom.getForCustomState(state);
                    model.setTransforms(DEFAULT_TYPES[2]);
                    Block block3 = state.m_60734_();
                    if (block3 instanceof IRenderType) {
                        IRenderType type = (IRenderType)block3;
                        model.setRenderTypes(this.create(state, type));
                    }
                    this.allModels.put(model, (IRegistryProvider)block);
                    this.putIfNotPresent(event.getModels(), model, BlockModelShaper.m_110895_((BlockState)state));
                    ++count;
                }
            }
            if (count <= 0) continue;
            mesher.m_109396_(block.m_5456_(), BlockModelShaper.m_110895_((BlockState)block.m_49966_()));
        }
    }

    private void handleItems(ModelEvent.BakingCompleted event) {
        ItemModelShaper mesher = Minecraft.m_91087_().m_91291_().m_115103_();
        for (Item item : ForgeRegistries.ITEMS) {
            BaseModel model;
            IRegistryProvider simple;
            ArrayList<BaseModel> models = new ArrayList<BaseModel>();
            if (item instanceof ISimpleItemModel && (simple = (ISimpleItemModel)item).shouldLoadModel()) {
                ItemStack stack = new ItemStack((ItemLike)item);
                BaseItemModel model2 = this.isLayered(stack) ? new SimpleLayeredItemModel(stack, simple.getTexture(), (ILayeredItemModel)item) : new SimpleItemModel(stack, simple.getTexture());
                model2.setTransforms(IC2Models.getTransforms(stack, DEFAULT_TYPES[1]));
                models.add(model2);
            }
            if (item instanceof IItemModel) {
                simple = (IItemModel)item;
                for (ItemStack stack : simple.getModelTypes()) {
                    model = this.isLayered(stack) ? new SimpleLayeredItemModel(stack, simple.getSprite(stack), (ILayeredItemModel)item) : new SimpleItemModel(stack, simple.getSprite(stack));
                    model.setTransforms(IC2Models.getTransforms(stack, DEFAULT_TYPES[1]));
                    models.add(model);
                }
            }
            if (item instanceof ICustomItemModel) {
                ICustomItemModel custom = (ICustomItemModel)item;
                for (ItemStack stack : custom.getCustomTypes()) {
                    model = custom.getModel(stack);
                    model.setTransforms(IC2Models.getTransforms(stack, DEFAULT_TYPES[1]));
                    models.add(model);
                }
            }
            if (models.isEmpty()) continue;
            for (BaseModel model3 : models) {
                this.allModels.put(model3, (IRegistryProvider)item);
            }
            ModelResourceLocation location = IC2Models.fromItem(item);
            if (item instanceof IMultiItemModel && models.size() > 1) {
                this.putIfNotPresent(event.getModels(), new MultiItemModel(models, (IMultiItemModel)item), location);
                mesher.m_109396_(item, location);
                continue;
            }
            this.putIfNotPresent(event.getModels(), (BakedModel)models.get(0), location);
            mesher.m_109396_(item, location);
        }
    }

    private void putIfNotPresent(Map<ResourceLocation, BakedModel> models, BakedModel model, ModelResourceLocation location) {
        if (IC2Models.isMissingModel(models.get(location))) {
            models.put((ResourceLocation)location, model);
        }
    }

    public static boolean isMissingModel(BakedModel model) {
        return model.getParticleIcon(ModelData.EMPTY) == Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(MissingTextureAtlasSprite.m_118071_());
    }

    public static List<BakedQuad> generateItemQuads(int tintedIndex, TextureAtlasSprite sprite, ItemTransform transform) {
        return UnbakedGeometryHelper.bakeElements((List)UnbakedGeometryHelper.createUnbakedItemElements((int)tintedIndex, (TextureAtlasSprite)sprite), $ -> sprite, (ModelState)new SimpleModelState(IC2Models.convert(transform)), (ResourceLocation)new ResourceLocation("ic2:item_baker"));
    }

    public static ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource source) {
        return IC2Models.getRenderTypes(state, source, ModelData.EMPTY);
    }

    public static ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource source, ModelData data) {
        return Minecraft.m_91087_().m_91289_().m_110910_(state).getRenderTypes(state, source, data);
    }

    private boolean isLayered(ItemStack stack) {
        ILayeredItemModel layer;
        Item item = stack.m_41720_();
        return item instanceof ILayeredItemModel && (layer = (ILayeredItemModel)item).isLayered(stack);
    }

    public static ModelResourceLocation fromItem(Item item) {
        return new ModelResourceLocation(ForgeRegistries.ITEMS.getKey((Object)item), "inventory");
    }

    public static TextureAtlasSprite getSafeIcon(TextureAtlasSprite sprite) {
        return sprite != null ? sprite : (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(MissingTextureAtlasSprite.m_118071_());
    }

    public static ItemTransforms getTransforms(ItemStack stack, ItemTransforms defaultValue) {
        ICustomItemModelTransform custom;
        Item item = stack.m_41720_();
        if (item instanceof ICustomItemModelTransform && (custom = (ICustomItemModelTransform)item).hasCustomTransform(stack)) {
            return IC2Models.getTransformMap(custom.getCustomTransform(stack));
        }
        return defaultValue;
    }

    private static Transformation convert(ItemTransform transform) {
        return new Transformation(transform.f_111756_, Quaternion.m_175225_((Vector3f)transform.f_111755_), transform.f_111757_, Quaternion.m_175225_((Vector3f)transform.rightRotation));
    }

    private ChunkRenderTypeSet create(BlockState state, IRenderType type) {
        ObjectList types = CollectionUtils.createList();
        for (RenderType toCheck : RenderType.m_110506_()) {
            if (!type.canRenderInLayer(state, toCheck)) continue;
            types.add((RenderType)toCheck);
        }
        return ChunkRenderTypeSet.of(types);
    }

    public static ItemTransforms getTransformMap(ResourceLocation location) {
        ItemTransforms result = TRANSFORM_CACHE.get(location);
        if (result == null) {
            result = IC2Models.getTransforms(location);
            TRANSFORM_CACHE.put(location, result);
        }
        return result;
    }

    public static ItemTransforms getTransformMap(ResourceLocation base, ResourceLocation customID, ResourceLocation other) {
        ItemTransforms result = TRANSFORM_CACHE.get(customID);
        if (result == null) {
            result = IC2Models.merge(IC2Models.getTransforms(other), IC2Models.getTransforms(base));
            TRANSFORM_CACHE.put(customID, result);
        }
        return result;
    }

    public static ItemTransforms getTransformMap(ResourceLocation base, ResourceLocation customID, String jsonText) {
        ItemTransforms result = TRANSFORM_CACHE.get(customID);
        if (result == null) {
            result = IC2Models.merge(IC2Models.getTransforms(jsonText), IC2Models.getTransforms(base));
            TRANSFORM_CACHE.put(customID, result);
        }
        return result;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ItemTransforms getTransforms(ResourceLocation location) {
        Optional resource = Minecraft.m_91087_().m_91098_().m_213713_(new ResourceLocation(location.m_135827_(), location.m_135815_() + ".json"));
        if (!resource.isPresent()) return ItemTransforms.f_111786_;
        try (BufferedReader reader = ((Resource)resource.get()).m_215508_();){
            BlockModel model = BlockModel.m_111461_((Reader)reader);
            if (model.getParentLocation() != null && !model.getParentLocation().equals((Object)BUILT_IN)) {
                ItemTransforms itemTransforms2 = IC2Models.merge(model.m_111491_(), IC2Models.getTransformMap(new ResourceLocation(model.getParentLocation().m_135827_(), "models/" + model.getParentLocation().m_135815_())));
                return itemTransforms2;
            }
            ItemTransforms itemTransforms = model.m_111491_();
            return itemTransforms;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ItemTransforms.f_111786_;
    }

    public static ItemTransforms merge(ItemTransforms base, ItemTransforms other) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (ItemTransforms.TransformType type : ItemTransforms.TransformType.values()) {
            if (!type.isModded()) continue;
            builder.put((Object)type, (Object)IC2Models.select(base, other, type));
        }
        return new ItemTransforms(IC2Models.select(base, other, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND), IC2Models.select(base, other, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND), IC2Models.select(base, other, ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND), IC2Models.select(base, other, ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND), IC2Models.select(base, other, ItemTransforms.TransformType.HEAD), IC2Models.select(base, other, ItemTransforms.TransformType.GUI), IC2Models.select(base, other, ItemTransforms.TransformType.GROUND), IC2Models.select(base, other, ItemTransforms.TransformType.FIXED), builder.build());
    }

    private static ItemTransform select(ItemTransforms base, ItemTransforms other, ItemTransforms.TransformType selected) {
        return base.m_111810_(selected) ? base.m_111808_(selected) : other.m_111808_(selected);
    }

    public static ItemTransforms getTransforms(String json) {
        try {
            return BlockModel.m_111461_((Reader)new StringReader(json)).m_111491_();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ItemTransforms.f_111786_;
        }
    }
}

