/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.block.model.ItemTransforms
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.client.resources.model.ModelBakery
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.client.resources.model.UnbakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.block;

import com.mojang.datafixers.util.Pair;
import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.util.LogCategory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class DynamicBeModel<T>
implements UnbakedModel,
BakedModel {
    protected final Ic2TileEntityBlock block;
    private final ResourceLocation backingModelId;
    private BakedModel baseModel;
    private BakedModel activeBaseModel;
    private final T[] cache;
    private final StampedLock cacheLock = new StampedLock();

    protected DynamicBeModel(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.m_135827_(), resourceLocation.m_135815_().substring(resourceLocation.m_135815_().lastIndexOf(47) + 1));
        Block block = (Block)Registry.f_122824_.m_7745_(resourceLocation2);
        if (!(block instanceof Ic2TileEntityBlock)) {
            throw new IllegalArgumentException("invalid id: " + resourceLocation);
        }
        this.block = (Ic2TileEntityBlock)block;
        this.backingModelId = new ResourceLocation(resourceLocation.m_135827_(), resourceLocation.m_135815_().replace("block/be/", "block/"));
        this.cache = new Object[(this.block.facingProperty != null ? 6 : 1) * (this.block.canActive() ? 2 : 1)];
    }

    public Collection<ResourceLocation> m_7970_() {
        if (this.block.canActive()) {
            return Arrays.asList(this.backingModelId, this.getActiveModelId());
        }
        return Collections.singletonList(this.backingModelId);
    }

    private ResourceLocation getActiveModelId() {
        return new ResourceLocation(this.backingModelId.m_135827_(), this.backingModelId.m_135815_().concat("_active"));
    }

    public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        HashSet<Material> hashSet = new HashSet<Material>();
        for (ResourceLocation resourceLocation : this.m_7970_()) {
            UnbakedModel unbakedModel = function.apply(resourceLocation);
            if (unbakedModel == null) {
                IC2.log.warn(LogCategory.Resource, "Missing model %s", resourceLocation);
                continue;
            }
            hashSet.addAll(unbakedModel.m_5500_(function, set));
        }
        return hashSet;
    }

    public BakedModel m_7611_(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        this.baseModel = modelBakery.m_119349_(this.backingModelId, modelState);
        if (this.baseModel == null) {
            throw new IllegalStateException("missing model " + this.backingModelId);
        }
        if (this.block.canActive()) {
            this.activeBaseModel = modelBakery.m_119349_(this.getActiveModelId(), modelState);
            if (this.activeBaseModel == null) {
                throw new IllegalStateException("missing model " + this.getActiveModelId());
            }
        }
        return this;
    }

    public List<BakedQuad> m_213637_(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return Collections.emptyList();
    }

    public boolean m_7541_() {
        return true;
    }

    public boolean m_7539_() {
        return true;
    }

    public boolean m_7547_() {
        return true;
    }

    public boolean m_7521_() {
        return false;
    }

    public TextureAtlasSprite m_6160_() {
        return this.baseModel.m_6160_();
    }

    public ItemTransforms m_7442_() {
        return ItemTransforms.f_111786_;
    }

    public ItemOverrides m_7343_() {
        return ItemOverrides.f_111734_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected T getMesh(BlockState blockState, boolean bl) {
        int n;
        Direction direction;
        if (this.block.facingProperty != null) {
            direction = (Direction)blockState.m_61143_(this.block.facingProperty);
            n = direction.ordinal();
        } else {
            direction = Direction.NORTH;
            n = 0;
        }
        if (bl) {
            n += this.cache.length >>> 1;
        }
        long l = this.cacheLock.readLock();
        try {
            T t = this.cache[n];
            if (t != null) {
                T t2 = t;
                return t2;
            }
        }
        finally {
            this.cacheLock.unlock(l);
        }
        T t = this.generateMesh(bl ? this.activeBaseModel : this.baseModel, switch (direction) {
            case Direction.DOWN -> 1;
            case Direction.UP -> 3;
            case Direction.NORTH -> 0;
            case Direction.SOUTH -> 2;
            case Direction.WEST -> 3;
            case Direction.EAST -> 1;
            default -> throw new IllegalStateException();
        }, direction.m_122434_() == Direction.Axis.Y);
        l = this.cacheLock.readLock();
        try {
            T t3 = this.cache[n];
            if (t3 != null) {
                T t4 = t3;
                return t4;
            }
            long l2 = this.cacheLock.tryConvertToWriteLock(l);
            if (l2 != 0L) {
                l = l2;
            } else {
                this.cacheLock.unlock(l);
                l = this.cacheLock.writeLock();
            }
            t3 = this.cache[n];
            if (t3 != null) {
                T t5 = t3;
                return t5;
            }
            this.cache[n] = t;
            T t6 = t;
            return t6;
        }
        finally {
            this.cacheLock.unlock(l);
        }
    }

    protected abstract T generateMesh(BakedModel var1, int var2, boolean var3);

    protected static BakedQuad rotateQuad(BakedQuad bakedQuad, int n, boolean bl) {
        int n2;
        int n3;
        if ((n &= 3) == 0) {
            return bakedQuad;
        }
        int[] nArray = bakedQuad.m_111303_();
        int[] nArray2 = Arrays.copyOf(nArray, nArray.length);
        int n4 = nArray.length >>> 2;
        if (bl) {
            n3 = 2;
            n2 = 1;
        } else {
            n3 = 0;
            n2 = 2;
        }
        for (int i = 0; i < 4; ++i) {
            float f;
            int n5 = i * n4;
            float f2 = Float.intBitsToFloat(nArray[n5 + n3]);
            float f3 = Float.intBitsToFloat(nArray[n5 + n2]);
            switch (n) {
                case 1: {
                    f = 1.0f - f3;
                    f3 = f2;
                    break;
                }
                case 2: {
                    f = 1.0f - f2;
                    f3 = 1.0f - f3;
                    break;
                }
                case 3: {
                    f = f3;
                    f3 = 1.0f - f2;
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            nArray2[n5 + n3] = Float.floatToRawIntBits(f);
            nArray2[n5 + n2] = Float.floatToRawIntBits(f3);
        }
        return new BakedQuad(nArray2, bakedQuad.m_111305_(), DynamicBeModel.rotateFace(bakedQuad.m_111306_(), n, bl), bakedQuad.m_173410_(), bakedQuad.m_111307_());
    }

    protected static Direction rotateFace(Direction direction, int n, boolean bl) {
        block3: {
            block2: {
                n &= 3;
                if (!bl || direction.m_122434_() == Direction.Axis.X) break block2;
                for (int i = 0; i < n; ++i) {
                    direction = direction.m_175362_(Direction.Axis.X);
                }
                break block3;
            }
            if (bl || direction.m_122434_() == Direction.Axis.Y) break block3;
            for (int i = 0; i < n; ++i) {
                direction = direction.m_122427_();
            }
        }
        return direction;
    }
}

