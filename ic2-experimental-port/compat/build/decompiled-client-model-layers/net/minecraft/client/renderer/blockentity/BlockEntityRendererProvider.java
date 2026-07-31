/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;

@FunctionalInterface
public interface BlockEntityRendererProvider<T extends BlockEntity> {
    public BlockEntityRenderer<T> m_173570_(Context var1);

    public static class Context {
        private final BlockEntityRenderDispatcher f_173572_;
        private final BlockRenderDispatcher f_173573_;
        private final ItemRenderer f_234437_;
        private final EntityRenderDispatcher f_234438_;
        private final EntityModelSet f_173574_;
        private final Font f_173575_;

        public Context(BlockEntityRenderDispatcher p_234440_, BlockRenderDispatcher p_234441_, ItemRenderer p_234442_, EntityRenderDispatcher p_234443_, EntityModelSet p_234444_, Font p_234445_) {
            this.f_173572_ = p_234440_;
            this.f_173573_ = p_234441_;
            this.f_234437_ = p_234442_;
            this.f_234438_ = p_234443_;
            this.f_173574_ = p_234444_;
            this.f_173575_ = p_234445_;
        }

        public BlockEntityRenderDispatcher m_173581_() {
            return this.f_173572_;
        }

        public BlockRenderDispatcher m_173584_() {
            return this.f_173573_;
        }

        public EntityRenderDispatcher m_234446_() {
            return this.f_234438_;
        }

        public ItemRenderer m_234447_() {
            return this.f_234437_;
        }

        public EntityModelSet m_173585_() {
            return this.f_173574_;
        }

        public ModelPart m_173582_(ModelLayerLocation p_173583_) {
            return this.f_173574_.m_171103_(p_173583_);
        }

        public Font m_173586_() {
            return this.f_173575_;
        }
    }
}

