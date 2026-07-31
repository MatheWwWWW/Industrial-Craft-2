/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Quaternion
 *  com.mojang.math.Transformation
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.BlockModelShaper
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.item.renders.models;

import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import ic2.api.events.RetextureEvent;
import ic2.core.item.tool.electric.ObscuratorTool;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class ObscuratorModel
extends BaseItemModel<ObscuratorModel> {
    static final float NORTH_Z = 0.468625f;
    static final float SOUTH_Z = 0.531375f;
    static final Transformation MATRIX = new Transformation(new Vector3f(-0.25f, 0.0f, 0.0f), null, null, null);
    static final Vector3f[] OFFSETS = new Vector3f[]{new Vector3f(), new Vector3f(1.0f, 0.25f, 0.0f), new Vector3f(0.75f, 1.25f, 0.0f), new Vector3f(-0.25f, 1.0f, 0.0f)};
    static final Object2ObjectLinkedOpenHashMap<RenderEntry, List<BakedQuad>> QUADS_STORAGE = new Object2ObjectLinkedOpenHashMap();
    List<BakedQuad> baseQuads = CollectionUtils.createList();
    RenderEntry lastEntry = null;

    public ObscuratorModel(TextureAtlasSprite sprite) {
        this.setParticleTexture(sprite);
    }

    @Override
    public void init() {
        this.baseQuads.addAll(QuadBaker.createQuadsFromTexture(-1, this.m_6160_(), this.getTransformMap().f_111792_));
        this.quads.addAll(this.baseQuads);
        this.initOther(new ObscuratorModel(this.m_6160_()));
        QUADS_STORAGE.clear();
    }

    @Override
    public ItemOverrides m_7343_() {
        return ObscuratorOverrides.INSTANCE;
    }

    public void syncQuads(ItemStack stack) {
        RetextureEvent.TextureContainer container = ObscuratorTool.getContainer(stack);
        if (container == null && this.lastEntry != null) {
            this.quads.clear();
            this.quads.addAll(this.baseQuads);
            this.lastEntry = null;
            this.syncQuads();
        } else if (container != null) {
            RenderEntry entry = new RenderEntry(container);
            if (entry.equals(this.lastEntry)) {
                return;
            }
            List<BakedQuad> storage = (List<BakedQuad>)QUADS_STORAGE.getAndMoveToLast((Object)entry);
            if (storage == null) {
                storage = entry.createQuads(this);
                QUADS_STORAGE.putAndMoveToLast((Object)entry, storage);
                if (QUADS_STORAGE.size() > 500) {
                    QUADS_STORAGE.removeFirst();
                }
            }
            this.quads.clear();
            this.quads.addAll(this.baseQuads);
            this.quads.addAll(storage);
            this.lastEntry = entry;
            this.syncQuads();
        }
    }

    public static class RenderEntry {
        BlockState state;
        Direction side;
        RetextureEvent.Rotation[] rotations;

        public RenderEntry(RetextureEvent.TextureContainer container) {
            this.state = container.getState();
            this.side = container.getSide();
            this.rotations = container.getRotations();
        }

        public int hashCode() {
            return Objects.hash(this.state, this.side, Arrays.hashCode((Object[])this.rotations));
        }

        public boolean equals(Object obj) {
            if (obj instanceof RenderEntry) {
                RenderEntry entry = (RenderEntry)obj;
                return entry.state.equals(this.state) && entry.side == this.side && Arrays.equals((Object[])entry.rotations, (Object[])this.rotations);
            }
            return false;
        }

        public List<BakedQuad> createQuads(BaseModel model) {
            BlockModelShaper shapes = Minecraft.m_91087_().m_91289_().m_110907_();
            BakedModel shape = shapes.m_110893_(this.state);
            if (shape == shapes.m_110881_().m_119409_()) {
                return CollectionUtils.createList();
            }
            ObjectList quads = CollectionUtils.createList();
            int colorState = 0;
            for (BakedQuad quad : shape.getQuads(this.state, this.side, RandomSource.m_216335_((long)this.state.m_60726_(BlockPos.f_121853_)), ModelData.EMPTY, null)) {
                RetextureEvent.Rotation rotation = this.rotations[colorState];
                Transformation matrix = new Transformation(OFFSETS[rotation.getIndex()], new Quaternion(0.0f, 0.0f, (float)rotation.getRotation(), true), null, null);
                BakedQuad newQuad = QuadBaker.genQuad(matrix.m_121096_(MATRIX), 6.0f, 2.0f, 14.0f, 10.0f, 0.468625f, quad.m_173410_(), Direction.NORTH, -1, -1);
                quads.add((BakedQuad)QuadBaker.copyWithTint(newQuad, quad.m_111304_() ? colorState : -1));
                newQuad = QuadBaker.genQuad(matrix.m_121096_(MATRIX), 6.0f, 2.0f, 14.0f, 10.0f, 0.531375f, quad.m_173410_(), Direction.SOUTH, -1, -1);
                quads.add((BakedQuad)QuadBaker.copyWithTint(newQuad, quad.m_111304_() ? colorState : -1));
                ++colorState;
            }
            return quads;
        }
    }

    public static class ObscuratorOverrides
    extends ItemOverrides {
        public static final ObscuratorOverrides INSTANCE = new ObscuratorOverrides();

        public BakedModel m_173464_(BakedModel model, ItemStack stack, ClientLevel worldIn, LivingEntity entityIn, int randomValue) {
            if (model instanceof ObscuratorModel) {
                ((ObscuratorModel)model).syncQuads(stack);
            }
            return model;
        }
    }
}

