/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.model.IModelState
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.crop;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.crop.IC2Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.model.AbstractModel;
import ic2.core.model.BasicBakedBlockModel;
import ic2.core.model.VdUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class CropModel
extends AbstractModel {
    private static final ResourceLocation STICK = new ResourceLocation("ic2", "blocks/crop/stick");
    private static final ResourceLocation UPGRADED_STICK = new ResourceLocation("ic2", "blocks/crop/stick_upgraded");
    private static final Function<ResourceLocation, TextureAtlasSprite> MISSING = location -> Minecraft.func_71410_x().func_147117_R().func_174944_f();
    static final Map<ResourceLocation, TextureAtlasSprite> textures = new HashMap<ResourceLocation, TextureAtlasSprite>();
    private final LoadingCache<TileEntityCrop.CropRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder().maximumSize(256L).expireAfterAccess(5L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<TileEntityCrop.CropRenderState, IBakedModel>(){

        public IBakedModel load(TileEntityCrop.CropRenderState key) throws Exception {
            if (key.crop == null || key.size <= 0) {
                return CropModel.this.generateStickModel(key.crosscrop);
            }
            return CropModel.this.generateModel(key);
        }
    });

    @Override
    public Collection<ResourceLocation> getTextures() {
        if (textures.isEmpty()) {
            IC2Crops.needsToPost = false;
            MinecraftForge.EVENT_BUS.post((Event)new Crops.CropRegisterEvent());
            for (CropCard crop : Crops.instance.getCrops()) {
                for (ResourceLocation aux : crop.getTexturesLocation()) {
                    textures.put(aux, null);
                }
            }
            textures.put(STICK, null);
            textures.put(UPGRADED_STICK, null);
        }
        return textures.keySet();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : textures.entrySet()) {
            entry.setValue(bakedTextureGetter.apply(entry.getKey()));
        }
        return this;
    }

    private static ResourceLocation getTextureLocation(CropCard crop, int currentSize) {
        return crop.getTexturesLocation().get(currentSize - 1);
    }

    @Override
    public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
        Ic2BlockState.Ic2BlockStateInstance state;
        TileEntityCrop.CropRenderState prop = rawState instanceof Ic2BlockState.Ic2BlockStateInstance && (state = (Ic2BlockState.Ic2BlockStateInstance)rawState).hasValue(TileEntityCrop.renderStateProperty) ? state.getValue(TileEntityCrop.renderStateProperty) : new TileEntityCrop.CropRenderState(null, 0, false);
        try {
            return ((IBakedModel)this.modelCache.get((Object)prop)).func_188616_a(rawState, side, rand);
        }
        catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    IBakedModel generateModel(TileEntityCrop.CropRenderState prop) {
        List[] faceQuads = new List[EnumFacing.field_176754_o.length];
        for (int index = 0; index < faceQuads.length; ++index) {
            faceQuads[index] = new ArrayList();
        }
        ArrayList<BakedQuad> generalQuads = new ArrayList();
        TextureAtlasSprite cropSprite = textures.computeIfAbsent(CropModel.getTextureLocation(prop.crop, prop.size), MISSING);
        for (EnumFacing facing : EnumFacing.field_176754_o) {
            int offsetX = facing.func_82601_c();
            int offsetZ = facing.func_82599_e();
            float x = (float)Math.abs(offsetX) * (0.5f + (float)offsetX * 0.25f);
            float z = (float)Math.abs(offsetZ) * (0.5f + (float)offsetZ * 0.25f);
            float xS = offsetX == 0 ? 0.0f : x;
            float xE = offsetX == 0 ? 1.0f : x;
            float zS = offsetZ == 0 ? 0.0f : z;
            float zE = offsetZ == 0 ? 1.0f : z;
            VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing), cropSprite, faceQuads, generalQuads, -0.0625f);
            VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing.func_176734_d()), cropSprite, faceQuads, generalQuads, -0.0625f);
        }
        int used = 0;
        for (int index = 0; index < faceQuads.length; ++index) {
            if (faceQuads[index].isEmpty()) {
                faceQuads[index] = Collections.emptyList();
                continue;
            }
            ++used;
        }
        if (used == 0) {
            faceQuads = null;
        }
        if (generalQuads.isEmpty()) {
            generalQuads = Collections.emptyList();
        }
        return new BasicBakedBlockModel(faceQuads, generalQuads, cropSprite);
    }

    IBakedModel generateStickModel(boolean crosscrop) {
        List[] faceQuads = new List[EnumFacing.field_176754_o.length];
        for (int index = 0; index < faceQuads.length; ++index) {
            faceQuads[index] = new ArrayList();
        }
        ArrayList<BakedQuad> generalQuads = new ArrayList();
        TextureAtlasSprite stickSprite = textures.get(STICK);
        TextureAtlasSprite upgradedStickSprite = textures.get(UPGRADED_STICK);
        for (EnumFacing facing : EnumFacing.field_176754_o) {
            float zE;
            int offsetX = facing.func_82601_c();
            int offsetZ = facing.func_82599_e();
            float x = (float)Math.abs(offsetX) * (0.5f + (float)offsetX * 0.25f);
            float z = (float)Math.abs(offsetZ) * (0.5f + (float)offsetZ * 0.25f);
            float xS = offsetX == 0 ? 0.0f : x;
            float xE = offsetX == 0 ? 1.0f : x;
            float zS = offsetZ == 0 ? 0.0f : z;
            float f = zE = offsetZ == 0 ? 1.0f : z;
            if (!crosscrop) {
                VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing), stickSprite, faceQuads, generalQuads, -0.0625f);
                VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing.func_176734_d()), stickSprite, faceQuads, generalQuads, -0.0625f);
                continue;
            }
            VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing), upgradedStickSprite, faceQuads, generalQuads, -0.0625f);
            VdUtil.addFlippedCuboidWithYOffset(xS, 0.001f, zS, xE, 1.0f, zE, -1, EnumSet.of(facing.func_176734_d()), upgradedStickSprite, faceQuads, generalQuads, -0.0625f);
        }
        int used = 0;
        for (int index = 0; index < faceQuads.length; ++index) {
            if (faceQuads[index].isEmpty()) {
                faceQuads[index] = Collections.emptyList();
                continue;
            }
            ++used;
        }
        if (used == 0) {
            faceQuads = null;
        }
        if (generalQuads.isEmpty()) {
            generalQuads = Collections.emptyList();
        }
        return new BasicBakedBlockModel(faceQuads, generalQuads, stickSprite);
    }

    @Override
    public TextureAtlasSprite func_177554_e() {
        return textures.get(STICK);
    }

    @Override
    public void onReload() {
        this.modelCache.invalidateAll();
    }
}

