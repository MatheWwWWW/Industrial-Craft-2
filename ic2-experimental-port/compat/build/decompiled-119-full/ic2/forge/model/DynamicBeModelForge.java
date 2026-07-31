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
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.client.model.geometry.IGeometryBakingContext
 *  org.jetbrains.annotations.Nullable
 */
package ic2.forge.model;

import com.mojang.datafixers.util.Pair;
import ic2.core.block.DynamicBeModel;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.util.Util;
import ic2.forge.model.Ic2Model;
import java.util.ArrayList;
import java.util.Collection;
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
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import org.jetbrains.annotations.Nullable;

final class DynamicBeModelForge
extends DynamicBeModel<List<BakedQuad>[]>
implements Ic2Model {
    private static final ModelProperty<List<BakedQuad>[]> MESH_DATA = new ModelProperty();

    protected DynamicBeModelForge(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public BakedModel bake(IGeometryBakingContext iGeometryBakingContext, ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides itemOverrides, ResourceLocation resourceLocation) {
        return this.m_7611_(modelBakery, function, modelState, resourceLocation);
    }

    public Collection<Material> getMaterials(IGeometryBakingContext iGeometryBakingContext, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        return this.m_5500_(function, set);
    }

    public ModelData getModelData(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, ModelData modelData) {
        BlockEntity blockEntity;
        boolean bl = this.block.canActive() && (blockEntity = blockAndTintGetter.m_7702_(blockPos)) instanceof Ic2TileEntity && ((Ic2TileEntity)blockEntity).getActive();
        List[] listArray = (List[])this.getMesh(blockState, bl);
        modelData = modelData.derive().with(MESH_DATA, (Object)listArray).build();
        assert (modelData.get(MESH_DATA) == listArray);
        return modelData;
    }

    public List<BakedQuad> getQuads(BlockState blockState, Direction direction, RandomSource randomSource, ModelData modelData, @Nullable RenderType renderType) {
        List[] listArray = (List[])modelData.get(MESH_DATA);
        return listArray[DynamicBeModelForge.getIdx(direction)];
    }

    @Override
    protected List<BakedQuad>[] generateMesh(BakedModel bakedModel, int n, boolean bl) {
        List[] listArray = new List[7];
        for (int i = 0; i < listArray.length; ++i) {
            Direction direction = i < 6 ? Util.ALL_DIRS[i] : null;
            ArrayList<BakedQuad> arrayList = bakedModel.getQuads(null, direction, null, null, null);
            int n2 = i;
            if (n != 0) {
                if (direction != null) {
                    n2 = DynamicBeModelForge.rotateFace(direction, n, bl).ordinal();
                }
                if (!arrayList.isEmpty()) {
                    ArrayList<BakedQuad> arrayList2 = new ArrayList<BakedQuad>(arrayList.size());
                    for (BakedQuad bakedQuad : arrayList) {
                        arrayList2.add(DynamicBeModelForge.rotateQuad(bakedQuad, n, bl));
                    }
                    arrayList = arrayList2;
                }
            }
            listArray[n2] = arrayList;
        }
        return listArray;
    }

    private static int getIdx(Direction direction) {
        return direction == null ? 6 : direction.ordinal();
    }
}

