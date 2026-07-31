/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface EntityRendererProvider<T extends Entity> {
    public EntityRenderer<T> m_174009_(Context var1);

    public static class Context {
        private final EntityRenderDispatcher f_174011_;
        private final ItemRenderer f_174012_;
        private final BlockRenderDispatcher f_234587_;
        private final ItemInHandRenderer f_234588_;
        private final ResourceManager f_174013_;
        private final EntityModelSet f_174014_;
        private final Font f_174015_;

        public Context(EntityRenderDispatcher p_234590_, ItemRenderer p_234591_, BlockRenderDispatcher p_234592_, ItemInHandRenderer p_234593_, ResourceManager p_234594_, EntityModelSet p_234595_, Font p_234596_) {
            this.f_174011_ = p_234590_;
            this.f_174012_ = p_234591_;
            this.f_234587_ = p_234592_;
            this.f_234588_ = p_234593_;
            this.f_174013_ = p_234594_;
            this.f_174014_ = p_234595_;
            this.f_174015_ = p_234596_;
        }

        public EntityRenderDispatcher m_174022_() {
            return this.f_174011_;
        }

        public ItemRenderer m_174025_() {
            return this.f_174012_;
        }

        public BlockRenderDispatcher m_234597_() {
            return this.f_234587_;
        }

        public ItemInHandRenderer m_234598_() {
            return this.f_234588_;
        }

        public ResourceManager m_174026_() {
            return this.f_174013_;
        }

        public EntityModelSet m_174027_() {
            return this.f_174014_;
        }

        public ModelPart m_174023_(ModelLayerLocation p_174024_) {
            return this.f_174014_.m_171103_(p_174024_);
        }

        public Font m_174028_() {
            return this.f_174015_;
        }
    }
}

