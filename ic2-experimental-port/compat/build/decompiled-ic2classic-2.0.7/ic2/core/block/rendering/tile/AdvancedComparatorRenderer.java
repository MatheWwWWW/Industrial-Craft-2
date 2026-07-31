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
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.ComparatorMode
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.block.cables.AdvancedComparatorBlock;
import ic2.core.block.cables.AdvancedComparatorTileEntity;
import ic2.core.block.rendering.models.ComparatorModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.Property;

public class AdvancedComparatorRenderer
implements BlockEntityRenderer<AdvancedComparatorTileEntity> {
    private static final ResourceLocation COMPARE_OFF = new ResourceLocation("ic2", "textures/models/comparator/compare_off.png");
    private static final ResourceLocation COMPARE_ON = new ResourceLocation("ic2", "textures/models/comparator/compare_on.png");
    private static final ResourceLocation SUBTRACT_OFF = new ResourceLocation("ic2", "textures/models/comparator/subtract_off.png");
    private static final ResourceLocation SUBTRACT_ON = new ResourceLocation("ic2", "textures/models/comparator/subtract_on.png");
    ComparatorModel model = new ComparatorModel();

    public AdvancedComparatorRenderer(BlockEntityRendererProvider.Context context) {
    }

    public ResourceLocation getTexture(boolean on, ComparatorMode mode) {
        if (mode == ComparatorMode.COMPARE) {
            return on ? COMPARE_ON : COMPARE_OFF;
        }
        return on ? SUBTRACT_ON : SUBTRACT_OFF;
    }

    public void render(AdvancedComparatorTileEntity te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = te.m_58900_();
        ComparatorMode mode = (ComparatorMode)state.m_61143_(AdvancedComparatorBlock.MODE);
        Direction facing = (Direction)state.m_61143_((Property)AdvancedComparatorBlock.FACING);
        Direction rotation = (Direction)state.m_61143_((Property)AdvancedComparatorBlock.ROTATION);
        boolean active = (Boolean)state.m_61143_((Property)AdvancedComparatorBlock.f_52496_);
        matrixStackIn.m_85836_();
        block0 : switch (facing) {
            case DOWN: {
                matrixStackIn.m_85845_(new Quaternion(0.0f, -rotation.m_122435_(), 0.0f, true));
                switch (rotation.m_122416_()) {
                    case 0: {
                        matrixStackIn.m_85837_(0.0, 0.0, 1.0);
                        break;
                    }
                    case 2: {
                        matrixStackIn.m_85837_(-1.0, 0.0, 0.0);
                        break;
                    }
                    case 3: {
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                    }
                }
                break;
            }
            case UP: {
                matrixStackIn.m_85845_(new Quaternion(-180.0f, 0.0f, 0.0f, true));
                matrixStackIn.m_85837_(0.0, -1.0, -1.0);
                matrixStackIn.m_85845_(new Quaternion(0.0f, -rotation.m_122435_(), 0.0f, true));
                switch (rotation.m_122416_()) {
                    case 0: {
                        matrixStackIn.m_85837_(1.0, 0.0, 0.0);
                        matrixStackIn.m_85845_(new Quaternion(0.0f, -180.0f, 0.0f, true));
                        break;
                    }
                    case 2: {
                        matrixStackIn.m_85837_(0.0, 0.0, -1.0);
                        matrixStackIn.m_85845_(new Quaternion(0.0f, -180.0f, 0.0f, true));
                        break;
                    }
                    case 3: {
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                    }
                }
                break;
            }
            case NORTH: {
                matrixStackIn.m_85845_(new Quaternion(90.0f, 0.0f, 0.0f, true));
                switch (rotation.m_122411_()) {
                    case 1: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                        break;
                    }
                    case 4: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, -90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(-1.0, 0.0, 0.0);
                        break;
                    }
                    case 5: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(0.0, 0.0, 1.0);
                    }
                }
                break;
            }
            case SOUTH: {
                matrixStackIn.m_85845_(new Quaternion(-90.0f, 0.0f, 0.0f, true));
                matrixStackIn.m_85837_(0.0, -1.0, 1.0);
                switch (rotation.m_122411_()) {
                    case 0: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                        break;
                    }
                    case 4: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, -90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(-1.0, 0.0, 0.0);
                        break;
                    }
                    case 5: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(0.0, 0.0, 1.0);
                    }
                }
                break;
            }
            case EAST: {
                matrixStackIn.m_85845_(new Quaternion(0.0f, 0.0f, 90.0f, true));
                matrixStackIn.m_85837_(1.0, -1.0, 0.0);
                switch (rotation.m_122411_()) {
                    case 0: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        matrixStackIn.m_85837_(1.0, 0.0, -1.0);
                    }
                    case 1: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(-1.0, 0.0, 0.0);
                        break;
                    }
                    case 2: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        break;
                    }
                    case 3: {
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                    }
                }
                break;
            }
            case WEST: {
                matrixStackIn.m_85845_(new Quaternion(0.0f, 0.0f, -90.0f, true));
                switch (rotation.m_122411_()) {
                    case 0: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        matrixStackIn.m_85837_(1.0, 0.0, -1.0);
                    }
                    case 1: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, -90.0f, 0.0f, true));
                        matrixStackIn.m_85837_(0.0, 0.0, 1.0);
                        break block0;
                    }
                    case 2: {
                        matrixStackIn.m_85845_(new Quaternion(0.0f, 180.0f, 0.0f, true));
                        break block0;
                    }
                    case 3: {
                        matrixStackIn.m_85837_(-1.0, 0.0, 1.0);
                    }
                }
            }
        }
        this.model.render(matrixStackIn, bufferIn.m_6299_(RenderType.m_110446_((ResourceLocation)this.getTexture(active, mode))), combinedLightIn, combinedOverlayIn);
        matrixStackIn.m_85849_();
    }
}

