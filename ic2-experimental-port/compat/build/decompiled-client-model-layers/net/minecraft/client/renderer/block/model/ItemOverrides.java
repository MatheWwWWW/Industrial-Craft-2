/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemOverrides {
    public static final ItemOverrides f_111734_ = new ItemOverrides();
    private final BakedOverride[] f_111735_;
    private final ResourceLocation[] f_173461_;

    private ItemOverrides() {
        this.f_111735_ = new BakedOverride[0];
        this.f_173461_ = new ResourceLocation[0];
    }

    public ItemOverrides(ModelBakery p_111740_, BlockModel p_111741_, Function<ResourceLocation, UnbakedModel> p_111742_, List<ItemOverride> p_111743_) {
        this.f_173461_ = (ResourceLocation[])p_111743_.stream().flatMap(ItemOverride::m_173449_).map(ItemOverride.Predicate::m_173459_).distinct().toArray(ResourceLocation[]::new);
        Object2IntOpenHashMap $$4 = new Object2IntOpenHashMap();
        for (int $$5 = 0; $$5 < this.f_173461_.length; ++$$5) {
            $$4.put((Object)this.f_173461_[$$5], $$5);
        }
        ArrayList $$6 = Lists.newArrayList();
        for (int $$7 = p_111743_.size() - 1; $$7 >= 0; --$$7) {
            ItemOverride $$8 = p_111743_.get($$7);
            BakedModel $$9 = this.m_173470_(p_111740_, p_111741_, p_111742_, $$8);
            PropertyMatcher[] $$10 = (PropertyMatcher[])$$8.m_173449_().map(arg_0 -> ItemOverrides.m_173475_((Object2IntMap)$$4, arg_0)).toArray(PropertyMatcher[]::new);
            $$6.add(new BakedOverride($$10, $$9));
        }
        this.f_111735_ = $$6.toArray(new BakedOverride[0]);
    }

    @Nullable
    private BakedModel m_173470_(ModelBakery p_173471_, BlockModel p_173472_, Function<ResourceLocation, UnbakedModel> p_173473_, ItemOverride p_173474_) {
        UnbakedModel $$4 = p_173473_.apply(p_173474_.m_111718_());
        if (Objects.equals($$4, p_173472_)) {
            return null;
        }
        return p_173471_.m_119349_(p_173474_.m_111718_(), BlockModelRotation.X0_Y0);
    }

    @Nullable
    public BakedModel m_173464_(BakedModel p_173465_, ItemStack p_173466_, @Nullable ClientLevel p_173467_, @Nullable LivingEntity p_173468_, int p_173469_) {
        if (this.f_111735_.length != 0) {
            Item $$5 = p_173466_.m_41720_();
            int $$6 = this.f_173461_.length;
            float[] $$7 = new float[$$6];
            for (int $$8 = 0; $$8 < $$6; ++$$8) {
                ResourceLocation $$9 = this.f_173461_[$$8];
                ItemPropertyFunction $$10 = ItemProperties.m_117829_($$5, $$9);
                $$7[$$8] = $$10 != null ? $$10.m_141951_(p_173466_, p_173467_, p_173468_, p_173469_) : Float.NEGATIVE_INFINITY;
            }
            for (BakedOverride $$11 : this.f_111735_) {
                if (!$$11.m_173485_($$7)) continue;
                BakedModel $$12 = $$11.f_173481_;
                if ($$12 == null) {
                    return p_173465_;
                }
                return $$12;
            }
        }
        return p_173465_;
    }

    private static /* synthetic */ PropertyMatcher m_173475_(Object2IntMap p_173476_, ItemOverride.Predicate p_173477_) {
        int $$2 = p_173476_.getInt((Object)p_173477_.m_173459_());
        return new PropertyMatcher($$2, p_173477_.m_173460_());
    }

    static class BakedOverride {
        private final PropertyMatcher[] f_173480_;
        @Nullable
        final BakedModel f_173481_;

        BakedOverride(PropertyMatcher[] p_173483_, @Nullable BakedModel p_173484_) {
            this.f_173480_ = p_173483_;
            this.f_173481_ = p_173484_;
        }

        boolean m_173485_(float[] p_173486_) {
            for (PropertyMatcher $$1 : this.f_173480_) {
                float $$2 = p_173486_[$$1.f_173487_];
                if (!($$2 < $$1.f_173488_)) continue;
                return false;
            }
            return true;
        }
    }

    static class PropertyMatcher {
        public final int f_173487_;
        public final float f_173488_;

        PropertyMatcher(int p_173490_, float p_173491_) {
            this.f_173487_ = p_173490_;
            this.f_173488_ = p_173491_;
        }
    }
}

