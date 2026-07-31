/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.OptionalInt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;

public class ItemFrameRenderer<T extends ItemFrame>
extends EntityRenderer<T> {
    private static final ModelResourceLocation f_115044_ = new ModelResourceLocation("item_frame", "map=false");
    private static final ModelResourceLocation f_115045_ = new ModelResourceLocation("item_frame", "map=true");
    private static final ModelResourceLocation f_174201_ = new ModelResourceLocation("glow_item_frame", "map=false");
    private static final ModelResourceLocation f_174202_ = new ModelResourceLocation("glow_item_frame", "map=true");
    public static final int f_174199_ = 5;
    public static final int f_174200_ = 30;
    private final ItemRenderer f_115047_;
    private final BlockRenderDispatcher f_234645_;

    public ItemFrameRenderer(EntityRendererProvider.Context p_174204_) {
        super(p_174204_);
        this.f_115047_ = p_174204_.m_174025_();
        this.f_234645_ = p_174204_.m_234597_();
    }

    @Override
    protected int m_6086_(T p_174216_, BlockPos p_174217_) {
        if (((Entity)p_174216_).m_6095_() == EntityType.f_147033_) {
            return Math.max(5, super.m_6086_(p_174216_, p_174217_));
        }
        return super.m_6086_(p_174216_, p_174217_);
    }

    @Override
    public void m_7392_(T p_115076_, float p_115077_, float p_115078_, PoseStack p_115079_, MultiBufferSource p_115080_, int p_115081_) {
        super.m_7392_(p_115076_, p_115077_, p_115078_, p_115079_, p_115080_, p_115081_);
        p_115079_.m_85836_();
        Direction $$6 = ((HangingEntity)p_115076_).m_6350_();
        Vec3 $$7 = this.m_7860_(p_115076_, p_115078_);
        p_115079_.m_85837_(-$$7.m_7096_(), -$$7.m_7098_(), -$$7.m_7094_());
        double $$8 = 0.46875;
        p_115079_.m_85837_((double)$$6.m_122429_() * 0.46875, (double)$$6.m_122430_() * 0.46875, (double)$$6.m_122431_() * 0.46875);
        p_115079_.m_85845_(Vector3f.f_122223_.m_122240_(((Entity)p_115076_).m_146909_()));
        p_115079_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - ((Entity)p_115076_).m_146908_()));
        boolean $$9 = ((Entity)p_115076_).m_20145_();
        ItemStack $$10 = ((ItemFrame)p_115076_).m_31822_();
        if (!$$9) {
            ModelManager $$11 = this.f_234645_.m_110907_().m_110881_();
            ModelResourceLocation $$12 = this.m_174212_(p_115076_, $$10);
            p_115079_.m_85836_();
            p_115079_.m_85837_(-0.5, -0.5, -0.5);
            this.f_234645_.m_110937_().m_111067_(p_115079_.m_85850_(), p_115080_.m_6299_(Sheets.m_110789_()), null, $$11.m_119422_($$12), 1.0f, 1.0f, 1.0f, p_115081_, OverlayTexture.f_118083_);
            p_115079_.m_85849_();
        }
        if (!$$10.m_41619_()) {
            OptionalInt $$13 = ((ItemFrame)p_115076_).m_218868_();
            if ($$9) {
                p_115079_.m_85837_(0.0, 0.0, 0.5);
            } else {
                p_115079_.m_85837_(0.0, 0.0, 0.4375);
            }
            int $$14 = $$13.isPresent() ? ((ItemFrame)p_115076_).m_31823_() % 4 * 2 : ((ItemFrame)p_115076_).m_31823_();
            p_115079_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$14 * 360.0f / 8.0f));
            if ($$13.isPresent()) {
                p_115079_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
                float $$15 = 0.0078125f;
                p_115079_.m_85841_(0.0078125f, 0.0078125f, 0.0078125f);
                p_115079_.m_85837_(-64.0, -64.0, 0.0);
                MapItemSavedData $$16 = MapItem.m_151128_($$13.getAsInt(), ((ItemFrame)p_115076_).f_19853_);
                p_115079_.m_85837_(0.0, 0.0, -1.0);
                if ($$16 != null) {
                    int $$17 = this.m_174208_(p_115076_, 15728850, p_115081_);
                    Minecraft.m_91087_().f_91063_.m_109151_().m_168771_(p_115079_, p_115080_, $$13.getAsInt(), $$16, true, $$17);
                }
            } else {
                int $$18 = this.m_174208_(p_115076_, 0xF000F0, p_115081_);
                p_115079_.m_85841_(0.5f, 0.5f, 0.5f);
                this.f_115047_.m_174269_($$10, ItemTransforms.TransformType.FIXED, $$18, OverlayTexture.f_118083_, p_115079_, p_115080_, ((Entity)p_115076_).m_19879_());
            }
        }
        p_115079_.m_85849_();
    }

    private int m_174208_(T p_174209_, int p_174210_, int p_174211_) {
        return ((Entity)p_174209_).m_6095_() == EntityType.f_147033_ ? p_174210_ : p_174211_;
    }

    private ModelResourceLocation m_174212_(T p_174213_, ItemStack p_174214_) {
        boolean $$2;
        boolean bl = $$2 = ((Entity)p_174213_).m_6095_() == EntityType.f_147033_;
        if (p_174214_.m_150930_(Items.f_42573_)) {
            return $$2 ? f_174202_ : f_115045_;
        }
        return $$2 ? f_174201_ : f_115044_;
    }

    @Override
    public Vec3 m_7860_(T p_115073_, float p_115074_) {
        return new Vec3((float)((HangingEntity)p_115073_).m_6350_().m_122429_() * 0.3f, -0.25, (float)((HangingEntity)p_115073_).m_6350_().m_122431_() * 0.3f);
    }

    @Override
    public ResourceLocation m_5478_(T p_115071_) {
        return TextureAtlas.f_118259_;
    }

    @Override
    protected boolean m_6512_(T p_115091_) {
        if (!Minecraft.m_91404_() || ((ItemFrame)p_115091_).m_31822_().m_41619_() || !((ItemFrame)p_115091_).m_31822_().m_41788_() || this.f_114476_.f_114359_ != p_115091_) {
            return false;
        }
        double $$1 = this.f_114476_.m_114471_((Entity)p_115091_);
        float $$2 = ((Entity)p_115091_).m_20163_() ? 32.0f : 64.0f;
        return $$1 < (double)($$2 * $$2);
    }

    @Override
    protected void m_7649_(T p_115083_, Component p_115084_, PoseStack p_115085_, MultiBufferSource p_115086_, int p_115087_) {
        super.m_7649_(p_115083_, ((ItemFrame)p_115083_).m_31822_().m_41786_(), p_115085_, p_115086_, p_115087_);
    }
}

