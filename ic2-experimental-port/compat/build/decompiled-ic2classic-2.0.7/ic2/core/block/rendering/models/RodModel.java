/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.client.renderer.RenderType
 */
package ic2.core.block.rendering.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import java.util.List;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class RodModel
extends Model {
    RodInfo[] info = new RodInfo[3];

    public RodModel() {
        super(RenderType::m_110446_);
        this.info[0] = RodModel.create1x1();
        this.info[1] = RodModel.create2x1();
        this.info[2] = RodModel.create2x2();
    }

    public static RodInfo create1x1() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.m_171576_();
        root.m_171599_("frame", RodModel.addRod(CubeListBuilder.m_171558_(), true), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)0.0f));
        root.m_171599_("uranium", RodModel.addRod(CubeListBuilder.m_171558_(), false), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)0.0f));
        RodInfo info = new RodInfo();
        ModelPart main = root.m_171583_(32, 16);
        info.setMain(main);
        RodModel.add(info, main);
        return info;
    }

    public static RodInfo create2x1() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.m_171576_();
        CubeDeformation formation = CubeDeformation.f_171458_;
        RodModel.addRod(root, "left", PartPose.m_171419_((float)-3.0f, (float)24.0f, (float)0.0f));
        RodModel.addRod(root, "right", PartPose.m_171419_((float)3.0f, (float)24.0f, (float)0.0f));
        root.m_171599_("connection", CubeListBuilder.m_171558_().m_171514_(20, 4).m_171488_(-1.0f, -3.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 11).m_171488_(-1.0f, -14.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)0.0f));
        RodInfo info = new RodInfo();
        ModelPart main = root.m_171583_(32, 16);
        info.setMain(main);
        RodModel.add(info, main.m_171324_("left"));
        RodModel.add(info, main.m_171324_("right"));
        info.addRods(main.m_171324_("connection"));
        return info;
    }

    public static RodInfo create2x2() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.m_171576_();
        CubeDeformation formation = CubeDeformation.f_171458_;
        PartDefinition front = root.m_171599_("front", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)3.0f));
        RodModel.addRod(front, "right", PartPose.m_171419_((float)3.0f, (float)0.0f, (float)0.0f));
        RodModel.addRod(front, "left", PartPose.m_171419_((float)-3.0f, (float)0.0f, (float)0.0f));
        PartDefinition back = root.m_171599_("back", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)-3.0f));
        RodModel.addRod(back, "right", PartPose.m_171419_((float)3.0f, (float)0.0f, (float)0.0f));
        RodModel.addRod(back, "left", PartPose.m_171419_((float)-3.0f, (float)0.0f, (float)0.0f));
        root.m_171599_("connection", CubeListBuilder.m_171558_().m_171514_(20, 4).m_171488_(-1.0f, -3.0f, 2.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 11).m_171488_(-1.0f, -14.0f, 2.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 11).m_171488_(-1.0f, -14.0f, -4.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 4).m_171488_(-1.0f, -3.0f, -4.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 6).m_171488_(2.0f, -3.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 6).m_171488_(-4.0f, -3.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 12).m_171488_(2.0f, -14.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation).m_171514_(20, 12).m_171488_(-4.0f, -14.0f, -1.0f, 2.0f, 2.0f, 2.0f, formation), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)0.0f));
        RodInfo info = new RodInfo();
        ModelPart main = root.m_171583_(32, 16);
        info.setMain(main);
        RodModel.add(info, main.m_171324_("front").m_171324_("left"));
        RodModel.add(info, main.m_171324_("front").m_171324_("right"));
        RodModel.add(info, main.m_171324_("back").m_171324_("left"));
        RodModel.add(info, main.m_171324_("back").m_171324_("right"));
        info.addRods(main.m_171324_("connection"));
        return info;
    }

    private static void add(RodInfo info, ModelPart part) {
        info.addRods(part.m_171324_("frame"));
        info.addUranium(part.m_171324_("uranium"));
    }

    private static void addRod(PartDefinition root, String name, PartPose pose) {
        PartDefinition right = root.m_171599_(name, CubeListBuilder.m_171558_(), pose);
        right.m_171599_("frame", RodModel.addRod(CubeListBuilder.m_171558_(), true), PartPose.f_171404_);
        right.m_171599_("uranium", RodModel.addRod(CubeListBuilder.m_171558_(), false), PartPose.f_171404_);
    }

    private static CubeListBuilder addRod(CubeListBuilder builder, boolean frame) {
        CubeDeformation formation = CubeDeformation.f_171458_;
        if (frame) {
            builder.m_171514_(16, 3).m_171488_(-1.0f, -1.0f, -1.0f, 2.0f, 1.0f, 2.0f, formation).m_171514_(16, 0).m_171488_(-1.0f, -16.0f, -1.0f, 2.0f, 1.0f, 2.0f, formation).m_171514_(16, 4).m_171488_(-2.0f, -3.0f, -2.0f, 4.0f, 2.0f, 4.0f, formation).m_171514_(16, 10).m_171488_(-2.0f, -15.0f, -2.0f, 4.0f, 2.0f, 4.0f, formation).m_171514_(0, 0).m_171488_(-2.0f, -13.0f, 1.0f, 1.0f, 10.0f, 1.0f, formation).m_171514_(0, 0).m_171488_(-2.0f, -13.0f, -2.0f, 1.0f, 10.0f, 1.0f, formation).m_171514_(0, 0).m_171488_(1.0f, -13.0f, -2.0f, 1.0f, 10.0f, 1.0f, formation).m_171514_(0, 0).m_171488_(1.0f, -13.0f, 1.0f, 1.0f, 10.0f, 1.0f, formation);
        } else {
            builder.m_171514_(4, 0).m_171488_(1.0f, -13.0f, -1.0f, 1.0f, 10.0f, 2.0f, formation).m_171514_(10, 0).m_171488_(-1.0f, -13.0f, -2.0f, 2.0f, 10.0f, 1.0f, formation).m_171514_(10, 0).m_171488_(-1.0f, -13.0f, 1.0f, 2.0f, 10.0f, 1.0f, formation).m_171514_(4, 0).m_171488_(-2.0f, -13.0f, -1.0f, 1.0f, 10.0f, 2.0f, formation);
        }
        return builder;
    }

    public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.info[this.getRods(0)].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, packedOverlayIn);
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color, int rods) {
        this.info[this.getRods(rods)].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
    }

    private int getRods(int value) {
        switch (value) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 4: {
                return 2;
            }
        }
        return 0;
    }

    public static class RodInfo {
        ModelPart mainModel;
        List<ModelPart> rods = CollectionUtils.createList();
        List<ModelPart> uranium = CollectionUtils.createList();

        public void addRods(ModelPart part) {
            this.rods.add(part);
        }

        public void addUranium(ModelPart part) {
            this.uranium.add(part);
        }

        public void setMain(ModelPart part) {
            this.mainModel = part;
        }

        public void render(PoseStack matrix, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
            this.toggle(this.uranium, false);
            this.mainModel.m_104301_(matrix, bufferIn, packedLightIn, packedOverlayIn);
            this.toggle(this.uranium, true);
            this.toggle(this.rods, false);
            this.mainModel.m_104306_(matrix, bufferIn, packedLightIn, packedOverlayIn, ColorUtils.getFloatR(color), ColorUtils.getFloatG(color), ColorUtils.getFloatB(color), 1.0f);
            this.toggle(this.rods, true);
        }

        private void toggle(List<ModelPart> parts, boolean value) {
            for (ModelPart part : parts) {
                part.f_104207_ = value;
            }
        }
    }
}

