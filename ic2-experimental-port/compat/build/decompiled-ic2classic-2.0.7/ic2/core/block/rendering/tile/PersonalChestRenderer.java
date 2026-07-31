/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.block.personal.PersonalBlock;
import ic2.core.block.personal.tile.PersonalChestTileEntity;
import ic2.core.block.rendering.models.PersonalChestModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.Property;

public class PersonalChestRenderer
implements BlockEntityRenderer<PersonalChestTileEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/models/tile/personal_safe.png");
    PersonalChestModel model = new PersonalChestModel();

    public PersonalChestRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(PersonalChestTileEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        tile.animation.tick();
        matrixStackIn.m_85836_();
        matrixStackIn.m_85837_(0.0, 1.0, 1.0);
        matrixStackIn.m_85841_(1.0f, -1.0f, -1.0f);
        matrixStackIn.m_85837_(0.5, 0.5, 0.5);
        matrixStackIn.m_85845_(new Quaternion(0.0f, (tile.m_58898_() ? (Direction)tile.m_58900_().m_61143_((Property)PersonalBlock.FACING) : tile.getFacing()).m_122435_(), 0.0f, true));
        matrixStackIn.m_85837_(-0.5, -0.5, -0.5);
        float moveAngle = tile.animation.prevLidAngle + (tile.animation.lidAngle - tile.animation.prevLidAngle) * partialTicks;
        moveAngle = 1.0f - moveAngle;
        moveAngle = 1.0f - moveAngle * moveAngle * moveAngle;
        this.model.render(matrixStackIn, bufferIn.m_6299_(RenderType.m_110452_((ResourceLocation)TEXTURE)), combinedLightIn, combinedOverlayIn, moveAngle * 3.141593f * 0.65f);
        matrixStackIn.m_85849_();
    }
}

