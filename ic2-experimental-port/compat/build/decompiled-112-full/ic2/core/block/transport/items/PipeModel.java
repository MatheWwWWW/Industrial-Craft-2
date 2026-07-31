/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.common.model.IModelState
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.transport.items;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.block.transport.TileEntityFluidPipe;
import ic2.core.block.transport.TileEntityPipe;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.model.AbstractModel;
import ic2.core.model.BasicBakedBlockModel;
import ic2.core.model.ISpecialParticleModel;
import ic2.core.model.ModelUtil;
import ic2.core.model.VdUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class PipeModel
extends AbstractModel
implements ISpecialParticleModel {
    private final Map<ResourceLocation, TextureAtlasSprite> textures = PipeModel.generateTextureLocations();
    private final LoadingCache<TileEntityPipe.PipeRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder().maximumSize(256L).expireAfterAccess(5L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<TileEntityPipe.PipeRenderState, IBakedModel>(){

        public IBakedModel load(TileEntityPipe.PipeRenderState key) throws Exception {
            return PipeModel.this.generateModel(key);
        }
    });

    @Override
    public Collection<ResourceLocation> getTextures() {
        return this.textures.keySet();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : this.textures.entrySet()) {
            entry.setValue(bakedTextureGetter.apply(entry.getKey()));
        }
        return this;
    }

    private static Map<ResourceLocation, TextureAtlasSprite> generateTextureLocations() {
        HashMap<ResourceLocation, TextureAtlasSprite> ret = new HashMap<ResourceLocation, TextureAtlasSprite>();
        ret.put(new ResourceLocation("ic2", "blocks/transport/pipe_side"), null);
        StringBuilder name = new StringBuilder();
        name.append("blocks/transport/");
        name.append("pipe");
        int reset0 = name.length();
        for (PipeSize size : PipeSize.values()) {
            name.append('_');
            name.append(size.name());
            ret.put(new ResourceLocation("ic2", name.toString()), null);
            name.setLength(reset0);
        }
        return ret;
    }

    private static ResourceLocation getTextureLocation(PipeSize size) {
        String loc = "blocks/transport/pipe_" + size.getName();
        return new ResourceLocation("ic2", loc);
    }

    private static ResourceLocation getSideTextureLocation() {
        String loc = "blocks/transport/pipe_side";
        return new ResourceLocation("ic2", loc);
    }

    @Override
    public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
        if (!(rawState instanceof Ic2BlockState.Ic2BlockStateInstance)) {
            return ModelUtil.getMissingModel().func_188616_a(rawState, side, rand);
        }
        Ic2BlockState.Ic2BlockStateInstance state = (Ic2BlockState.Ic2BlockStateInstance)rawState;
        if (!state.hasValue(TileEntityFluidPipe.renderStateProperty)) {
            return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
        }
        TileEntityPipe.PipeRenderState prop = (TileEntityPipe.PipeRenderState)state.getValue(TileEntityFluidPipe.renderStateProperty);
        try {
            return ((IBakedModel)this.modelCache.get((Object)prop)).func_188616_a((IBlockState)state, side, rand);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private IBakedModel generateModel(TileEntityPipe.PipeRenderState prop) {
        float xE;
        float yE;
        float zE;
        float xS;
        float yS;
        float zS;
        PipeType type = prop.type;
        int color = 0xFF000000 | ((byte)type.blue & 0xFF) << 16 | ((byte)type.green & 0xFF) << 8 | (byte)type.red & 0xFF;
        float th = prop.size.thickness;
        float sp = (1.0f - th) / 2.0f;
        EnumFacing pFacing = EnumFacing.values()[prop.facing];
        List[] faceQuads = new List[EnumFacing.field_82609_l.length];
        for (int i = 0; i < faceQuads.length; ++i) {
            faceQuads[i] = new ArrayList();
        }
        List<Object> generalQuads = new ArrayList<BakedQuad>();
        TextureAtlasSprite sideSprite = this.textures.get(PipeModel.getSideTextureLocation());
        TextureAtlasSprite sizeSprite = this.textures.get(PipeModel.getTextureLocation(prop.size));
        int totalConnections = Integer.bitCount(prop.connectivity);
        if (totalConnections == 0) {
            float zE2;
            float zS2;
            float yS2 = zS2 = sp;
            float xS2 = zS2;
            float yE2 = zE2 = sp + th;
            float xE2 = zE2;
            VdUtil.addCuboid(xS2, yS2, zS2, xE2, yE2, zE2, color, Util.allFacings, sizeSprite, faceQuads, generalQuads);
        } else if (totalConnections == 1) {
            EnumFacing connected = EnumFacing.field_82609_l[Integer.numberOfTrailingZeros(prop.connectivity)];
            for (EnumFacing facing : EnumFacing.field_82609_l) {
                yS = zS = sp;
                xS = zS;
                yE = zE = sp + th;
                xE = zE;
                if (facing == connected) {
                    switch (facing) {
                        case DOWN: {
                            yS = 0.0f;
                            yE = sp;
                            break;
                        }
                        case UP: {
                            yS = sp + th;
                            yE = 1.0f;
                            break;
                        }
                        case NORTH: {
                            zS = 0.0f;
                            zE = sp;
                            break;
                        }
                        case SOUTH: {
                            zS = sp + th;
                            zE = 1.0f;
                            break;
                        }
                        case WEST: {
                            xS = 0.0f;
                            xE = sp;
                            break;
                        }
                        case EAST: {
                            xS = sp + th;
                            xE = 1.0f;
                            break;
                        }
                        default: {
                            throw new RuntimeException();
                        }
                    }
                    VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, faceQuads, generalQuads);
                    VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.complementOf(EnumSet.of(facing, facing.func_176734_d())), sideSprite, faceQuads, generalQuads);
                    continue;
                }
                if (facing == connected.func_176734_d()) {
                    VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, faceQuads, generalQuads);
                    continue;
                }
                VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sideSprite, faceQuads, generalQuads);
            }
        } else {
            for (EnumFacing facing : EnumFacing.field_82609_l) {
                boolean hasConnection = (prop.connectivity & 1 << facing.ordinal()) != 0;
                yS = zS = sp;
                xS = zS;
                yE = zE = sp + th;
                xE = zE;
                if (hasConnection) {
                    switch (facing) {
                        case DOWN: {
                            yS = 0.0f;
                            yE = sp;
                            break;
                        }
                        case UP: {
                            yS = sp + th;
                            yE = 1.0f;
                            break;
                        }
                        case NORTH: {
                            zS = 0.0f;
                            zE = sp;
                            break;
                        }
                        case SOUTH: {
                            zS = sp + th;
                            zE = 1.0f;
                            break;
                        }
                        case WEST: {
                            xS = 0.0f;
                            xE = sp;
                            break;
                        }
                        case EAST: {
                            xS = sp + th;
                            xE = 1.0f;
                            break;
                        }
                        default: {
                            throw new RuntimeException();
                        }
                    }
                    VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, faceQuads, generalQuads);
                    VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.complementOf(EnumSet.of(facing, facing.func_176734_d())), sideSprite, faceQuads, generalQuads);
                    continue;
                }
                VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sideSprite, faceQuads, generalQuads);
            }
        }
        float cs = 1.0f;
        float ch = 0.1f;
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            boolean hasCover = (prop.covers & 1 << facing.ordinal()) != 0;
            float zS3 = 0.0f;
            float yS3 = 0.0f;
            float xS3 = 0.0f;
            float zE3 = 1.0f;
            float yE3 = 1.0f;
            float xE3 = 1.0f;
            if (!hasCover) continue;
            switch (facing) {
                case DOWN: {
                    yS3 = 0.0f;
                    yE3 = ch;
                    break;
                }
                case UP: {
                    yS3 = cs - ch;
                    yE3 = 1.0f;
                    break;
                }
                case NORTH: {
                    zS3 = 0.0f;
                    zE3 = ch;
                    break;
                }
                case SOUTH: {
                    zS3 = cs - ch;
                    zE3 = 1.0f;
                    break;
                }
                case WEST: {
                    xS3 = 0.0f;
                    xE3 = ch;
                    break;
                }
                case EAST: {
                    xS3 = cs - ch;
                    xE3 = 1.0f;
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }
            VdUtil.addCuboid(xS3, yS3, zS3, xE3, yE3, zE3, color, Util.allFacings, sideSprite, faceQuads, generalQuads);
        }
        int used = 0;
        for (int i = 0; i < faceQuads.length; ++i) {
            if (faceQuads[i].isEmpty()) {
                faceQuads[i] = Collections.emptyList();
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
        return new BasicBakedBlockModel(faceQuads, generalQuads, sizeSprite);
    }

    @Override
    public void onReload() {
        this.modelCache.invalidateAll();
    }

    @Override
    public TextureAtlasSprite func_177554_e() {
        return this.textures.get(PipeModel.getSideTextureLocation());
    }

    @Override
    public boolean needsEnhancing(IBlockState state) {
        return true;
    }

    @Override
    public void enhanceParticle(Particle particle, Ic2BlockState.Ic2BlockStateInstance state) {
        if (state.hasValue(TileEntityPipe.renderStateProperty)) {
            TileEntityPipe.PipeRenderState prop = state.getValue(TileEntityPipe.renderStateProperty);
            particle.func_70538_b((float)prop.type.red / 255.0f, (float)prop.type.green / 255.0f, (float)prop.type.blue / 255.0f);
        }
    }
}

