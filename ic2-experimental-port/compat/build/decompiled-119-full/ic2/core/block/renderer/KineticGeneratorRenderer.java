/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.ints.Int2ReferenceMap
 *  it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ic2.api.tile.IRotorProvider;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

public class KineticGeneratorRenderer<T extends BlockEntity>
implements BlockEntityRenderer<T> {
    private static final Int2ReferenceMap<ModelPart> rotorModels = new Int2ReferenceOpenHashMap();

    public KineticGeneratorRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void m_6922_(T t, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, int n2) {
        MeshDefinition meshDefinition;
        int n3 = ((IRotorProvider)t).getRotorDiameter();
        if (n3 == 0) {
            return;
        }
        float f2 = ((IRotorProvider)t).getAngle();
        ResourceLocation resourceLocation = ((IRotorProvider)t).getRotorRenderTexture();
        ModelPart modelPart = (ModelPart)rotorModels.get(n3);
        if (modelPart == null) {
            meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.m_171576_();
            partDefinition.m_171599_("1", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, -4.0f, 1.0f, (float)(n3 * 8), 8.0f), PartPose.m_171423_((float)-8.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.5f, (float)0.0f));
            partDefinition.m_171599_("2", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, -4.0f, 1.0f, (float)(n3 * 8), 8.0f), PartPose.m_171423_((float)-8.0f, (float)0.0f, (float)0.0f, (float)3.1f, (float)0.5f, (float)0.0f));
            partDefinition.m_171599_("3", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, -4.0f, 1.0f, (float)(n3 * 8), 8.0f), PartPose.m_171423_((float)-8.0f, (float)0.0f, (float)0.0f, (float)4.7f, (float)0.0f, (float)0.5f));
            partDefinition.m_171599_("4", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0f, 0.0f, -4.0f, 1.0f, (float)(n3 * 8), 8.0f), PartPose.m_171423_((float)-8.0f, (float)0.0f, (float)0.0f, (float)1.5f, (float)0.0f, (float)-0.5f));
            modelPart = LayerDefinition.m_171565_((MeshDefinition)meshDefinition, (int)32, (int)256).m_171564_();
            rotorModels.put(n3, (Object)modelPart);
        }
        meshDefinition = ((IRotorProvider)t).getFacing();
        poseStack.m_85836_();
        poseStack.m_85837_(0.5, 0.5, 0.5);
        switch (1.$SwitchMap$net$minecraft$util$math$Direction[meshDefinition.ordinal()]) {
            case 1: {
                poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-90.0f));
                break;
            }
            case 2: {
                poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-180.0f));
                break;
            }
            case 3: {
                poseStack.m_85845_(Vector3f.f_122225_.m_122240_(-270.0f));
                break;
            }
            case 4: {
                poseStack.m_85845_(Vector3f.f_122227_.m_122240_(-90.0f));
                break;
            }
        }
        poseStack.m_85845_(Vector3f.f_122223_.m_122240_(f2));
        poseStack.m_85837_(-0.2, 0.0, 0.0);
        n = LevelRenderer.m_109541_((BlockAndTintGetter)t.m_58904_(), (BlockPos)t.m_58899_().m_121945_((Direction)meshDefinition));
        modelPart.m_104301_(poseStack, multiBufferSource.m_6299_(RenderType.m_110446_((ResourceLocation)resourceLocation)), n, n2);
        poseStack.m_85849_();
    }
}

