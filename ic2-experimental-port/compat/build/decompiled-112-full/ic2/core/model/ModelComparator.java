/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.BlockStateContainer$StateImplementation
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockRendererDispatcher
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.block.model.SimpleBakedModel
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.property.IExtendedBlockState
 */
package ic2.core.model;

import ic2.core.block.state.Ic2BlockState;
import ic2.core.model.BasicBakedBlockModel;
import ic2.core.util.Util;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelComparator {
    private static final EnumFacing[] facings = new EnumFacing[]{null, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST};
    private static final Byte UNCACHEABLE = -1;
    private static final ConcurrentMap<CacheKey, Byte> cache = new ConcurrentHashMap<CacheKey, Byte>();

    public static boolean isEqual(IBlockState stateA, IBlockState stateB, World world, BlockPos pos) {
        Byte cacheResult;
        CacheKey cacheKey;
        assert (stateA != stateB);
        int renderMask = 0;
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            boolean renderB;
            boolean renderA = stateA.func_185894_c((IBlockAccess)world, pos, facing);
            if (renderA != (renderB = stateB.func_185894_c((IBlockAccess)world, pos, facing))) {
                return false;
            }
            if (!renderA) continue;
            renderMask = (byte)(renderMask | 1 << facing.ordinal());
        }
        if (stateA.getClass() == stateB.getClass() && (stateA.getClass() == BlockStateContainer.StateImplementation.class || stateA instanceof Ic2BlockState.Ic2BlockStateInstance && !((Ic2BlockState.Ic2BlockStateInstance)stateA).hasExtraProperties() && !((Ic2BlockState.Ic2BlockStateInstance)stateB).hasExtraProperties() || stateA instanceof IExtendedBlockState && ((IExtendedBlockState)stateA).getClean() == stateA && ((IExtendedBlockState)stateB).getClean() == stateB)) {
            cacheKey = new CacheKey(stateA, stateB);
            cacheResult = (Byte)cache.get(cacheKey);
            if (cacheResult != null && cacheResult != UNCACHEABLE) {
                return (cacheResult | ~renderMask) == -1;
            }
        } else {
            cacheKey = null;
            cacheResult = UNCACHEABLE;
        }
        assert (cacheResult == null || cacheResult == UNCACHEABLE);
        BlockRendererDispatcher renderer = Minecraft.func_71410_x().func_175602_ab();
        IBakedModel modelA = renderer.func_184389_a(stateA);
        IBakedModel modelB = renderer.func_184389_a(stateB);
        Class<?> modelCls = modelA.getClass();
        if (modelB.getClass() != modelCls) {
            if (cacheResult == null) {
                cache.putIfAbsent(cacheKey, (byte)0);
            }
            return false;
        }
        if (cacheResult == null && modelCls != SimpleBakedModel.class && modelCls != BasicBakedBlockModel.class && !modelCls.getName().equals("net.minecraftforge.client.model.ModelLoader$VanillaModelWrapper$1")) {
            if (Util.inDev()) assert (false);
            cacheResult = UNCACHEABLE;
            cache.putIfAbsent(cacheKey, UNCACHEABLE);
        }
        long rand = MathHelper.func_180186_a((Vec3i)pos);
        byte equal = 63;
        block1: for (EnumFacing facing : facings) {
            if (cacheResult != null && facing != null && (renderMask & 1 << facing.ordinal()) == 0) continue;
            List quadsA = modelA.func_188616_a(stateA, facing, rand);
            List quadsB = modelB.func_188616_a(stateB, facing, rand);
            if (quadsA.size() != quadsB.size()) {
                if (cacheResult != null) {
                    return false;
                }
                if (facing == null) {
                    equal = 0;
                    break;
                }
                equal = (byte)(equal & ~(1 << facing.ordinal()));
                continue;
            }
            if (quadsA.isEmpty()) continue;
            for (int i = 0; i < quadsA.size(); ++i) {
                if (Arrays.equals(((BakedQuad)quadsA.get(i)).func_178209_a(), ((BakedQuad)quadsB.get(i)).func_178209_a())) continue;
                if (cacheResult != null) {
                    return false;
                }
                if (facing == null) {
                    equal = 0;
                    break block1;
                }
                equal = (byte)(equal & ~(1 << facing.ordinal()));
                continue block1;
            }
        }
        if (cacheResult != null) {
            return true;
        }
        cache.putIfAbsent(cacheKey, equal);
        return (equal | ~renderMask) == -1;
    }

    public static void onReload() {
        cache.clear();
    }

    private static class CacheKey {
        private final IBlockState stateA;
        private final IBlockState stateB;

        CacheKey(IBlockState stateA, IBlockState stateB) {
            this.stateA = stateA;
            this.stateB = stateB;
        }

        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != CacheKey.class) {
                return false;
            }
            CacheKey o = (CacheKey)obj;
            return this.stateA == o.stateA && this.stateB == o.stateB || this.stateA == o.stateB && this.stateB == o.stateA;
        }

        public int hashCode() {
            return System.identityHashCode(this.stateA) ^ System.identityHashCode(this.stateB);
        }
    }
}

