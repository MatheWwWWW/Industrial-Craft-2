/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  net.minecraft.block.state.IBlockState
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
package ic2.core.block.wiring;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ic2.core.block.BlockFoam;
import ic2.core.block.TileEntityWall;
import ic2.core.block.comp.Obscuration;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.model.AbstractModel;
import ic2.core.model.BasicBakedBlockModel;
import ic2.core.model.ISpecialParticleModel;
import ic2.core.model.ModelUtil;
import ic2.core.model.VdUtil;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import ic2.core.util.Ic2Color;
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
public class CableModel
extends AbstractModel
implements ISpecialParticleModel {
    private final Map<ResourceLocation, TextureAtlasSprite> textures = CableModel.generateTextureLocations();
    private final LoadingCache<TileEntityCable.CableRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder().maximumSize(256L).expireAfterAccess(5L, TimeUnit.MINUTES).build((CacheLoader)new CacheLoader<TileEntityCable.CableRenderState, IBakedModel>(){

        public IBakedModel load(TileEntityCable.CableRenderState key) throws Exception {
            return CableModel.this.generateModel(key);
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
        StringBuilder name = new StringBuilder();
        name.append("blocks/wiring/cable/");
        int reset0 = name.length();
        for (CableType type : CableType.values) {
            name.append(type.name());
            name.append("_cable");
            int reset1 = name.length();
            for (int insulation = 0; insulation <= type.maxInsulation; ++insulation) {
                if (type.maxInsulation != 0) {
                    name.append('_');
                    name.append(insulation);
                }
                if (insulation >= type.minColoredInsulation) {
                    name.append('_');
                    int reset2 = name.length();
                    for (Ic2Color color : Ic2Color.values) {
                        name.append(color.name());
                        ret.put(new ResourceLocation("ic2", name.toString()), null);
                        name.setLength(reset2);
                    }
                } else {
                    ret.put(new ResourceLocation("ic2", name.toString()), null);
                    if (type == CableType.splitter || type == CableType.detector) {
                        ret.put(new ResourceLocation("ic2", name.toString() + "_active"), null);
                    }
                }
                name.setLength(reset1);
            }
            name.setLength(reset0);
        }
        return ret;
    }

    private static ResourceLocation getTextureLocation(CableType type, int insulation, Ic2Color color, boolean active) {
        String loc = "blocks/wiring/cable/" + type.getName(insulation, color);
        if (active) {
            loc = loc + "_active";
        }
        return new ResourceLocation("ic2", loc);
    }

    @Override
    public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
        if (!(rawState instanceof Ic2BlockState.Ic2BlockStateInstance)) {
            return ModelUtil.getMissingModel().func_188616_a(rawState, side, rand);
        }
        Ic2BlockState.Ic2BlockStateInstance state = (Ic2BlockState.Ic2BlockStateInstance)rawState;
        if (!state.hasValue(TileEntityCable.renderStateProperty)) {
            return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
        }
        TileEntityCable.CableRenderState prop = state.getValue(TileEntityCable.renderStateProperty);
        if (prop.foam == CableFoam.Soft) {
            return ModelUtil.getBlockModel(BlockName.foam.getBlockState(BlockFoam.FoamType.normal)).func_188616_a((IBlockState)state, side, rand);
        }
        if (prop.foam == CableFoam.Hardened) {
            TileEntityWall.WallRenderState wallProp = state.getValue(TileEntityWall.renderStateProperty);
            if (wallProp == null) {
                return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
            }
            if (wallProp.obscurations == null) {
                return ModelUtil.getBlockModel(BlockName.wall.getBlockState(wallProp.color)).func_188616_a((IBlockState)state, side, rand);
            }
            IBakedModel model = ModelUtil.getBlockModel(BlockName.te.getBlockState(TeBlock.wall));
            return model.func_188616_a((IBlockState)state, side, rand);
        }
        try {
            return ((IBakedModel)this.modelCache.get((Object)prop)).func_188616_a((IBlockState)state, side, rand);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private IBakedModel generateModel(TileEntityCable.CableRenderState prop) {
        float th = prop.type.thickness + (float)(prop.insulation * 2) * 0.0625f;
        float sp = (1.0f - th) / 2.0f;
        List[] faceQuads = new List[EnumFacing.field_82609_l.length];
        for (int i = 0; i < faceQuads.length; ++i) {
            faceQuads[i] = new ArrayList();
        }
        ArrayList<BakedQuad> generalQuads = new ArrayList();
        TextureAtlasSprite sprite = this.textures.get(CableModel.getTextureLocation(prop.type, prop.insulation, prop.color, prop.active));
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            float zE;
            float zS;
            boolean hasConnection = (prop.connectivity & 1 << facing.ordinal()) != 0;
            float yS = zS = sp;
            float xS = zS;
            float yE = zE = sp + th;
            float xE = zE;
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
                VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, EnumSet.complementOf(EnumSet.of(facing.func_176734_d())), sprite, faceQuads, generalQuads);
                continue;
            }
            VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, EnumSet.of(facing), sprite, faceQuads, generalQuads);
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
        return new BasicBakedBlockModel(faceQuads, generalQuads, sprite);
    }

    @Override
    public void onReload() {
        this.modelCache.invalidateAll();
    }

    @Override
    public boolean needsEnhancing(IBlockState state) {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture(Ic2BlockState.Ic2BlockStateInstance state) {
        if (!state.hasValue(TileEntityCable.renderStateProperty)) {
            return ModelUtil.getMissingModel().func_177554_e();
        }
        TileEntityCable.CableRenderState prop = state.getValue(TileEntityCable.renderStateProperty);
        if (prop.foam == CableFoam.Soft) {
            return ModelUtil.getBlockModel(BlockName.foam.getBlockState(BlockFoam.FoamType.normal)).func_177554_e();
        }
        if (prop.foam == CableFoam.Hardened) {
            TileEntityWall.WallRenderState wallProp = state.getValue(TileEntityWall.renderStateProperty);
            if (wallProp == null) {
                return ModelUtil.getMissingModel().func_177554_e();
            }
            if (wallProp.obscurations == null) {
                return ModelUtil.getBlockModel(BlockName.wall.getBlockState(wallProp.color)).func_177554_e();
            }
            Obscuration.ObscurationData data = wallProp.obscurations[EnumFacing.UP.ordinal()];
            if (data == null) {
                return ModelUtil.getBlockModel(BlockName.wall.getBlockState(wallProp.color)).func_177554_e();
            }
            return ModelUtil.getBlockModel(data.state).func_177554_e();
        }
        return this.textures.get(CableModel.getTextureLocation(prop.type, prop.insulation, prop.color, prop.active));
    }
}

