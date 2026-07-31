/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.block.model.ItemTransforms
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.Material
 *  net.minecraft.client.resources.model.ModelBakery
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.client.resources.model.UnbakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.block.wiring;

import com.mojang.datafixers.util.Pair;
import ic2.core.IC2;
import ic2.core.block.wiring.CableBlock;
import ic2.core.block.wiring.CableFoam;
import ic2.core.block.wiring.CableType;
import ic2.core.util.Util;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class DynamicCableModel<T, E>
implements UnbakedModel,
BakedModel {
    private final CableType type;
    private final int insulation;
    private final CableFoam foam;
    private final boolean active;
    private Map<DyeColor, TextureAtlasSprite> sprites;
    private TextureAtlasSprite blackSprite;
    private TextureAtlasSprite particleTexture;
    private final Int2ObjectMap<T> cache = new Int2ObjectOpenHashMap();
    private final StampedLock cacheLock = new StampedLock();

    protected DynamicCableModel(CableType cableType, int n, CableFoam cableFoam, boolean bl) {
        this.type = cableType;
        this.insulation = n;
        this.foam = cableFoam;
        this.active = bl;
    }

    public Collection<ResourceLocation> m_7970_() {
        return Collections.emptyList();
    }

    public Collection<Material> m_5500_(Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        if (this.insulation >= this.type.minColoredInsulation) {
            ArrayList<Material> arrayList = new ArrayList<Material>(16);
            for (DyeColor dyeColor : DyeColor.values()) {
                arrayList.add(DynamicCableModel.getTextureId(this.type, this.insulation, dyeColor, false));
            }
            return arrayList;
        }
        return Collections.singletonList(DynamicCableModel.getTextureId(this.type, this.insulation, DyeColor.BLACK, this.active));
    }

    private static Material getTextureId(CableType cableType, int n, DyeColor dyeColor, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append("blocks/wiring/cable/");
        stringBuilder.append(cableType.name());
        stringBuilder.append("_cable_");
        stringBuilder.append(n);
        if (n >= cableType.minColoredInsulation) {
            stringBuilder.append('_');
            stringBuilder.append(dyeColor.m_7912_());
        }
        if (bl && (cableType == CableType.detector || cableType == CableType.splitter)) {
            stringBuilder.append("_active");
        }
        return DynamicCableModel.getTextureId(stringBuilder.toString());
    }

    private static Material getTextureId(String string) {
        ResourceLocation resourceLocation = TextureAtlas.f_118259_;
        return new Material(resourceLocation, IC2.getIdentifier(string));
    }

    public BakedModel m_7611_(ModelBakery modelBakery, Function<Material, TextureAtlasSprite> function, ModelState modelState, ResourceLocation resourceLocation) {
        this.blackSprite = function.apply(DynamicCableModel.getTextureId(this.type, this.insulation, DyeColor.BLACK, this.active));
        this.particleTexture = !this.foam.isPresent() ? this.blackSprite : (this.foam.isSoft() ? function.apply(DynamicCableModel.getTextureId("blocks/cf/foam")) : function.apply(DynamicCableModel.getTextureId("blocks/cf/wall_".concat(this.foam.getColor().m_7912_()))));
        if (this.insulation >= this.type.minColoredInsulation) {
            this.sprites = new EnumMap<DyeColor, TextureAtlasSprite>(DyeColor.class);
            for (DyeColor dyeColor : DyeColor.values()) {
                TextureAtlasSprite textureAtlasSprite = dyeColor == DyeColor.BLACK ? this.blackSprite : function.apply(DynamicCableModel.getTextureId(this.type, this.insulation, dyeColor, this.active));
                this.sprites.put(dyeColor, textureAtlasSprite);
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
        return this.particleTexture;
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
    protected T getMesh(BlockState blockState) {
        Object object;
        DyeColor dyeColor = CableBlock.getColor(blockState, this.type, this.insulation);
        int n = 0;
        int n2 = dyeColor.ordinal() << 6 | n;
        long l = this.cacheLock.readLock();
        try {
            object = this.cache.get(n2);
            if (object != null) {
                Object object2 = object;
                return (T)object2;
            }
        }
        finally {
            this.cacheLock.unlock(l);
        }
        object = this.generateMesh(dyeColor, n);
        l = this.cacheLock.readLock();
        try {
            Object object3 = this.cache.get(n2);
            if (object3 != null) {
                Object object4 = object3;
                return (T)object4;
            }
            long l2 = this.cacheLock.tryConvertToWriteLock(l);
            if (l2 != 0L) {
                l = l2;
            } else {
                this.cacheLock.unlock(l);
                l = this.cacheLock.writeLock();
            }
            object3 = this.cache.putIfAbsent(n2, object);
            Object object5 = object3 != null ? object3 : object;
            return (T)object5;
        }
        finally {
            this.cacheLock.unlock(l);
        }
    }

    protected abstract T generateMesh(DyeColor var1, int var2);

    protected void generateQuads(DyeColor dyeColor, int n, E e) {
        TextureAtlasSprite textureAtlasSprite = dyeColor == DyeColor.BLACK ? this.blackSprite : this.sprites.get(dyeColor);
        float f = this.type.getThickness(this.insulation);
        float f2 = (1.0f - f) / 2.0f;
        float f3 = f2 + f;
        for (Direction direction : Util.ALL_DIRS) {
            boolean bl;
            boolean bl2 = bl = (n & 1 << direction.ordinal()) != 0;
            if (bl) {
                this.emitQuad(e, direction, f2, f2, f3, f3, 0.0f, textureAtlasSprite);
                if (!this.foam.isPresent()) {
                    float f4;
                    float f5;
                    float f6 = f5 = f2;
                    float f7 = f5;
                    float f8 = f4 = f3;
                    float f9 = f4;
                    switch (direction) {
                        case DOWN: {
                            f6 = 0.0f;
                            f8 = f2;
                            break;
                        }
                        case UP: {
                            f6 = f3;
                            f8 = 1.0f;
                            break;
                        }
                        case NORTH: {
                            f5 = 0.0f;
                            f4 = f2;
                            break;
                        }
                        case SOUTH: {
                            f5 = f3;
                            f4 = 1.0f;
                            break;
                        }
                        case WEST: {
                            f7 = 0.0f;
                            f9 = f2;
                            break;
                        }
                        case EAST: {
                            f7 = f3;
                            f9 = 1.0f;
                            break;
                        }
                        default: {
                            throw new RuntimeException();
                        }
                    }
                    for (Direction direction2 : Util.ALL_DIRS) {
                        if (direction2.m_122434_() == direction.m_122434_()) continue;
                        this.emitQuad(e, direction2, f7, f6, f5, f9, f8, f4, textureAtlasSprite);
                    }
                    continue;
                }
                this.emitQuad(e, direction, 0.0f, 0.0f, f2, 1.0f, 0.0f, this.particleTexture);
                this.emitQuad(e, direction, f3, 0.0f, 1.0f, 1.0f, 0.0f, this.particleTexture);
                this.emitQuad(e, direction, f2, 0.0f, f3, f2, 0.0f, this.particleTexture);
                this.emitQuad(e, direction, f2, f3, f3, 1.0f, 0.0f, this.particleTexture);
                continue;
            }
            if (!this.foam.isPresent()) {
                this.emitQuad(e, direction, f2, f2, f3, f3, f2, textureAtlasSprite);
                continue;
            }
            this.emitQuad(e, direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, this.particleTexture);
        }
    }

    protected abstract void emitQuad(E var1, Direction var2, float var3, float var4, float var5, float var6, float var7, TextureAtlasSprite var8);

    protected abstract void emitQuad(E var1, Direction var2, float var3, float var4, float var5, float var6, float var7, float var8, TextureAtlasSprite var9);
}

