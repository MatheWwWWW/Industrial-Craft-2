/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.client.resources.model.ModelBakery
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.client.resources.model.UnbakedModel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.client.model.geometry.IGeometryBakingContext
 *  org.jetbrains.annotations.Nullable
 */
package ic2.forge.model;

import com.mojang.datafixers.util.Pair;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import ic2.core.block.wiring.DynamicCableModel;
import ic2.forge.model.Ic2Model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.jetbrains.annotations.Nullable;

final class DynamicCableModelForge
extends DynamicCableModel<List<BakedQuad>[], List<BakedQuad>[]>
implements Ic2Model {
    private static final ModelProperty<List<BakedQuad>[]> MESH_DATA = new ModelProperty();
    private static final int stride = 8;

    protected DynamicCableModelForge(CableType cableType, int n, CableFoam cableFoam, boolean bl) {
        super(cableType, n, cableFoam, bl);
    }

    public BakedModel bake(IGeometryBakingContext iGeometryBakingContext, ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides itemOverrides, ResourceLocation resourceLocation) {
        return this.m_7611_(modelBakery, function, modelState, resourceLocation);
    }

    public Collection<Material> getMaterials(IGeometryBakingContext iGeometryBakingContext, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        return this.m_5500_(function, set);
    }

    public ModelData getModelData(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, ModelData modelData) {
        List[] listArray = (List[])this.getMesh(blockState);
        modelData = modelData.derive().with(MESH_DATA, (Object)listArray).build();
        assert (modelData.get(MESH_DATA) == listArray);
        return modelData;
    }

    public List<BakedQuad> getQuads(BlockState blockState, Direction direction, RandomSource randomSource, ModelData modelData, @Nullable RenderType renderType) {
        List[] listArray = (List[])modelData.get(MESH_DATA);
        return listArray[DynamicCableModelForge.getIdx(direction)];
    }

    @Override
    protected List<BakedQuad>[] generateMesh(DyeColor dyeColor, int n) {
        int n2;
        List[] listArray = new List[7];
        for (n2 = 0; n2 < listArray.length; ++n2) {
            listArray[n2] = new ArrayList();
        }
        this.generateQuads(dyeColor, n, listArray);
        for (n2 = 0; n2 < listArray.length; ++n2) {
            if (!listArray[n2].isEmpty()) continue;
            listArray[n2] = Collections.emptyList();
        }
        return listArray;
    }

    @Override
    protected void emitQuad(List<BakedQuad>[] listArray, Direction direction, float f, float f2, float f3, float f4, float f5, TextureAtlasSprite textureAtlasSprite) {
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        switch (direction) {
            case DOWN: {
                f10 = f11 = f5;
                f9 = f;
                f8 = f2;
                f7 = f3;
                f6 = f4;
                break;
            }
            case UP: {
                f10 = f11 = 1.0f - f5;
                f9 = f;
                f8 = f2;
                f7 = f3;
                f6 = f4;
                break;
            }
            case NORTH: {
                f8 = f6 = f5;
                f9 = f;
                f10 = f2;
                f7 = f3;
                f11 = f4;
                break;
            }
            case SOUTH: {
                f8 = f6 = 1.0f - f5;
                f9 = f;
                f10 = f2;
                f7 = f3;
                f11 = f4;
                break;
            }
            case WEST: {
                f9 = f7 = f5;
                f10 = f2;
                f8 = f;
                f11 = f4;
                f6 = f3;
                break;
            }
            case EAST: {
                f9 = f7 = 1.0f - f5;
                f10 = f2;
                f8 = f;
                f11 = f4;
                f6 = f3;
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        this.emitQuad(listArray, direction, f9, f10, f8, f7, f11, f6, textureAtlasSprite);
    }

    @Override
    protected void emitQuad(List<BakedQuad>[] listArray, Direction direction, float f, float f2, float f3, float f4, float f5, float f6, TextureAtlasSprite textureAtlasSprite) {
        float f7;
        int[] nArray = new int[32];
        float f8 = textureAtlasSprite.m_118409_();
        float f9 = textureAtlasSprite.m_118411_();
        float f10 = textureAtlasSprite.m_118410_();
        float f11 = textureAtlasSprite.m_118412_();
        int n = DynamicCableModelForge.packNormals(direction.m_122429_(), direction.m_122430_(), direction.m_122431_());
        switch (direction) {
            case UP: {
                f7 = 1.0f - f5;
                DynamicCableModelForge.vertex(0, f, f5, f3, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(f3, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f, f5, f6, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(f6, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f4, f5, f6, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(f6, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f4, f5, f3, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(f3, f9, f11), n, nArray);
                break;
            }
            case DOWN: {
                f7 = f2;
                DynamicCableModelForge.vertex(0, f, f2, f6, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(1.0f - f6, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f, f2, f3, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(1.0f - f3, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f4, f2, f3, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(1.0f - f3, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f4, f2, f6, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(1.0f - f6, f9, f11), n, nArray);
                break;
            }
            case EAST: {
                f7 = 1.0f - f4;
                DynamicCableModelForge.vertex(0, f4, f5, f6, DynamicCableModelForge.map(1.0f - f6, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f4, f2, f6, DynamicCableModelForge.map(1.0f - f6, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f4, f2, f3, DynamicCableModelForge.map(1.0f - f3, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f4, f5, f3, DynamicCableModelForge.map(1.0f - f3, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                break;
            }
            case WEST: {
                f7 = f;
                DynamicCableModelForge.vertex(0, f, f5, f3, DynamicCableModelForge.map(f3, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f, f2, f3, DynamicCableModelForge.map(f3, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f, f2, f6, DynamicCableModelForge.map(f6, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f, f5, f6, DynamicCableModelForge.map(f6, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                break;
            }
            case SOUTH: {
                f7 = 1.0f - f6;
                DynamicCableModelForge.vertex(0, f, f5, f6, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f, f2, f6, DynamicCableModelForge.map(f, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f4, f2, f6, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f4, f5, f6, DynamicCableModelForge.map(f4, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                break;
            }
            case NORTH: {
                f7 = f3;
                DynamicCableModelForge.vertex(0, f4, f5, f3, DynamicCableModelForge.map(1.0f - f4, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(1, f4, f2, f3, DynamicCableModelForge.map(1.0f - f4, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(2, f, f2, f3, DynamicCableModelForge.map(1.0f - f, f8, f10), DynamicCableModelForge.map(1.0f - f2, f9, f11), n, nArray);
                DynamicCableModelForge.vertex(3, f, f5, f3, DynamicCableModelForge.map(1.0f - f, f8, f10), DynamicCableModelForge.map(1.0f - f5, f9, f11), n, nArray);
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
        BakedQuad bakedQuad = new BakedQuad(nArray, -1, direction, textureAtlasSprite, true);
        Direction direction2 = (double)Math.abs(f7) < 1.0E-5 ? direction : null;
        listArray[DynamicCableModelForge.getIdx(direction2)].add(bakedQuad);
    }

    private static float map(float f, float f2, float f3) {
        return f2 + f * (f3 - f2);
    }

    private static void vertex(int n, float f, float f2, float f3, float f4, float f5, int n2, int[] nArray) {
        int n3 = n * 8;
        nArray[n3++] = Float.floatToRawIntBits(f);
        nArray[n3++] = Float.floatToRawIntBits(f2);
        nArray[n3++] = Float.floatToRawIntBits(f3);
        nArray[n3++] = -1;
        nArray[n3++] = Float.floatToRawIntBits(f4);
        nArray[n3++] = Float.floatToRawIntBits(f5);
        nArray[++n3] = n2;
    }

    private static int packNormals(float f, float f2, float f3) {
        return DynamicCableModelForge.mapFloatToByte(f) | DynamicCableModelForge.mapFloatToByte(f2) << 8 | DynamicCableModelForge.mapFloatToByte(f3) << 16;
    }

    private static int mapFloatToByte(float f) {
        assert (f >= -1.0f && f <= 1.0f);
        return Math.round(f * 127.0f) & 0xFF;
    }

    private static int getIdx(Direction direction) {
        return direction == null ? 6 : direction.ordinal();
    }
}

