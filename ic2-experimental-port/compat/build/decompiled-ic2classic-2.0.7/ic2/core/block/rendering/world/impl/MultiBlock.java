/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.ColorResolver
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.lighting.LevelLightEngine
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.world.impl;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ic2.core.IC2;
import ic2.core.platform.rendering.IC2Models;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class MultiBlock {
    Long2ObjectMap<BlockState> formedStates = new Long2ObjectLinkedOpenHashMap();
    Long2ObjectMap<BlockState> unformedStates = new Long2ObjectLinkedOpenHashMap();
    Long2ObjectMap<BlockEntity> tileCache = new Long2ObjectLinkedOpenHashMap();
    Long2ObjectMap<ModelData> modelData = new Long2ObjectLinkedOpenHashMap();
    LongSet disabledTileEntities = new LongOpenHashSet();
    AABB bounds = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    Vec3 offset = new Vec3(0.0, 0.0, 0.0);
    int limit;

    public MultiBlock() {
        this.modelData.defaultReturnValue((Object)ModelData.EMPTY);
    }

    public MultiBlock addBlock(Block block, int x, int y, int z) {
        return this.addBlock(block.m_49966_(), new BlockPos(x, y, z));
    }

    public MultiBlock addBlock(BlockState state, int x, int y, int z) {
        return this.addBlock(state, new BlockPos(x, y, z));
    }

    public MultiBlock addBlock(Block block, BlockPos pos) {
        return this.addBlock(block.m_49966_(), pos);
    }

    public MultiBlock addBlock(BlockState state, BlockPos pos) {
        this.formedStates.put(pos.m_121878_(), (Object)state);
        this.bounds = this.bounds.m_82367_(new AABB(pos));
        return this;
    }

    public MultiBlock addUnformedBlock(Block block, int x, int y, int z) {
        return this.addUnformedBlock(block.m_49966_(), new BlockPos(x, y, z));
    }

    public MultiBlock addUnformedBlock(BlockState state, int x, int y, int z) {
        return this.addUnformedBlock(state, new BlockPos(x, y, z));
    }

    public MultiBlock addUnformedBlock(Block block, BlockPos pos) {
        return this.addUnformedBlock(block.m_49966_(), pos);
    }

    public MultiBlock addUnformedBlock(BlockState state, BlockPos pos) {
        this.unformedStates.put(pos.m_121878_(), (Object)state);
        return this;
    }

    public MultiBlock setOffset(double x, double y, double z) {
        this.offset = new Vec3(x, y, z);
        return this;
    }

    public <T> MultiBlock withModelData(ModelProperty<T> prop, T value) {
        return this.withModelData(ModelData.builder().with(prop, value).build());
    }

    public MultiBlock withModelData(ModelData modelData) {
        this.modelData.defaultReturnValue((Object)modelData);
        return this;
    }

    public <T> MultiBlock withPositionedModelData(ModelProperty<T> prop, T value, BlockPos pos) {
        return this.withPositionedModelData(ModelData.builder().with(prop, value).build(), pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
    }

    public <T> MultiBlock withPositionedModelData(ModelProperty<T> prop, T value, int x, int y, int z) {
        return this.withPositionedModelData(ModelData.builder().with(prop, value).build(), x, y, z);
    }

    public MultiBlock withPositionedModelData(ModelData modelData, BlockPos pos) {
        return this.withPositionedModelData(modelData, pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
    }

    public MultiBlock withPositionedModelData(ModelData modelData, int x, int y, int z) {
        this.modelData.put(BlockPos.m_121882_((int)x, (int)y, (int)z), (Object)modelData);
        return this;
    }

    public MultiBlock build() {
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.formedStates)) {
            this.unformedStates.putIfAbsent(entry.getLongKey(), (Object)((BlockState)entry.getValue()).m_60734_().m_49966_());
        }
        return this;
    }

    public MultiBlock copy() {
        MultiBlock block = new MultiBlock();
        block.bounds = this.bounds;
        block.offset = this.offset;
        block.modelData.putAll(this.modelData);
        block.modelData.defaultReturnValue((Object)((ModelData)this.modelData.defaultReturnValue()));
        block.formedStates.putAll(this.formedStates);
        block.unformedStates.putAll(this.unformedStates);
        return block;
    }

    public void setYLimit(int limit) {
        this.limit = limit;
    }

    public AABB getBounds() {
        return this.bounds;
    }

    public Vec3 getOffset() {
        return this.offset;
    }

    private boolean isEmptyBlock(Level world, BlockPos pos) {
        BlockState state = world.m_8055_(pos);
        return state.m_60795_() || !state.m_60767_().m_76334_();
    }

    public boolean isFullyBuild(Level world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.formedStates)) {
            if (!this.isEmptyBlock(world, (BlockPos)mutable.m_122188_(entry.getLongKey()).m_122193_((Vec3i)pos))) continue;
            return false;
        }
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void renderMultiBlock(PoseStack stack, VertexConsumer helper, Level world, BlockPos location, Rotation rotate, boolean formed) {
        if (this.formedStates.isEmpty()) {
            return;
        }
        BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
        BlockEntityRenderDispatcher entityDispatcher = Minecraft.m_91087_().m_167982_();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        RandomSource source = RandomSource.m_216327_();
        TintGetter tint = new TintGetter(world);
        for (RenderType type : RenderType.m_110506_()) {
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(formed ? this.formedStates : this.unformedStates)) {
                BlockState state = (BlockState)entry.getValue();
                long longPos = entry.getLongKey();
                pos.m_122190_((Vec3i)pos.m_122188_(longPos).m_7954_(rotate)).m_122193_((Vec3i)location);
                source.m_188584_(state.m_60726_((BlockPos)pos));
                if (!IC2Models.getRenderTypes(state, source).contains(type) || !this.isEmptyBlock(world, (BlockPos)pos) || (double)(pos.m_123342_() - location.m_123342_()) >= this.bounds.f_82292_ - (double)this.limit) continue;
                stack.m_85836_();
                stack.m_85837_((double)(pos.m_123341_() - location.m_123341_()), (double)(pos.m_123342_() - location.m_123342_()), (double)(pos.m_123343_() - location.m_123343_()));
                if (state.m_60799_() == RenderShape.MODEL) {
                    dispatcher.renderBatched(state.rotate((LevelAccessor)world, (BlockPos)pos, rotate), (BlockPos)pos, (BlockAndTintGetter)tint, stack, helper, false, world.f_46441_, formed ? (ModelData)this.modelData.get(longPos) : ModelData.EMPTY, type);
                    this.disabledTileEntities.add(longPos);
                }
                stack.m_85849_();
            }
        }
        if (Tesselator.m_85913_().m_85915_().m_85732_()) {
            return;
        }
        MultiBufferSource.BufferSource impl = MultiBufferSource.m_109898_((BufferBuilder)Tesselator.m_85913_().m_85915_());
        for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(formed ? this.formedStates : this.unformedStates)) {
            BlockEntity tile;
            BlockState state = (BlockState)entry.getValue();
            long longPos = entry.getLongKey();
            Block block = state.m_60734_();
            if (!(block instanceof EntityBlock)) continue;
            EntityBlock entity = (EntityBlock)block;
            if (this.disabledTileEntities.contains(longPos)) continue;
            pos.m_122190_((Vec3i)pos.m_122188_(longPos).m_7954_(rotate));
            if ((double)pos.m_123342_() >= this.bounds.f_82292_ - (double)this.limit) continue;
            if (location.m_123342_() <= 256) {
                pos.m_122193_((Vec3i)location);
            }
            if ((tile = (BlockEntity)this.tileCache.computeIfAbsent(longPos, T -> entity.m_142194_(pos.m_122190_((Vec3i)pos.m_122188_(longPos).m_7954_(rotate)).m_122193_((Vec3i)location).m_7949_(), state))) == null) continue;
            try {
                stack.m_85836_();
                stack.m_85837_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                entityDispatcher.m_112265_(tile).m_6922_(tile, 0.0f, stack, (MultiBufferSource)impl, 0xF000F0, OverlayTexture.f_118083_);
                stack.m_85849_();
            }
            catch (Exception e) {
                IC2.LOGGER.catching((Throwable)e);
                this.disabledTileEntities.add(longPos);
                stack.m_85849_();
            }
        }
        impl.m_109911_();
    }

    private static class TintGetter
    implements BlockAndTintGetter {
        Level level;

        public TintGetter(Level level) {
            this.level = level;
        }

        public BlockEntity m_7702_(BlockPos p_45570_) {
            return this.level.m_7702_(p_45570_);
        }

        public BlockState m_8055_(BlockPos p_45571_) {
            return this.level.m_8055_(p_45571_);
        }

        public FluidState m_6425_(BlockPos p_45569_) {
            return this.level.m_6425_(p_45569_);
        }

        public int m_141928_() {
            return this.level.m_141928_();
        }

        public int m_141937_() {
            return this.level.m_141937_();
        }

        public int m_45517_(LightLayer p_45518_, BlockPos p_45519_) {
            return 15;
        }

        public int m_45524_(BlockPos p_45525_, int p_45526_) {
            return 15;
        }

        public float m_7717_(Direction p_45522_, boolean p_45523_) {
            return this.level.m_7717_(p_45522_, p_45523_);
        }

        public int m_7146_(BlockPos p_45572_) {
            return super.m_7146_(p_45572_);
        }

        public LevelLightEngine m_5518_() {
            return this.level.m_5518_();
        }

        public int m_6171_(BlockPos p_45520_, ColorResolver p_45521_) {
            return this.level.m_6171_(p_45520_, p_45521_);
        }
    }
}

