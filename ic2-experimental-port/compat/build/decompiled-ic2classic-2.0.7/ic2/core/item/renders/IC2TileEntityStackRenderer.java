/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.cables.AdvancedComparatorTileEntity;
import ic2.core.block.personal.tile.PersonalChestTileEntity;
import ic2.core.item.renders.entitymodel.ModelShield;
import ic2.core.item.wearable.base.IC2ShieldBase;
import ic2.core.platform.registries.IC2Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class IC2TileEntityStackRenderer
extends BlockEntityWithoutLevelRenderer {
    public static final IC2TileEntityStackRenderer INSTANCE = new IC2TileEntityStackRenderer();
    private ModelShield shield = new ModelShield();
    private PersonalChestTileEntity personal_chest = new PersonalChestTileEntity(BlockPos.f_121853_, IC2Blocks.PERSONAL_CHEST.m_49966_());
    private AdvancedComparatorTileEntity comparator = new AdvancedComparatorTileEntity(BlockPos.f_121853_, IC2Blocks.ADVANCED_COMPARATOR.m_49966_());
    private BlockEntityRenderDispatcher dispatcher;

    public IC2TileEntityStackRenderer() {
        this(Minecraft.m_91087_());
    }

    public IC2TileEntityStackRenderer(Minecraft mc) {
        super(mc.m_167982_(), mc.m_167973_());
        this.dispatcher = mc.m_167982_();
        this.personal_chest.setFacingSilent(Direction.NORTH);
    }

    public void m_108829_(ItemStack stack, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (stack.m_41720_() instanceof IC2ShieldBase) {
            ResourceLocation location = ((IC2ShieldBase)stack.m_41720_()).getTexture(stack);
            matrixStack.m_85836_();
            matrixStack.m_85841_(1.0f, -1.0f, -1.0f);
            this.shield.render(matrixStack, buffer.m_6299_(RenderType.m_110452_((ResourceLocation)location)), combinedLight, combinedOverlay);
            matrixStack.m_85849_();
        } else {
            Item item = stack.m_41720_();
            if (item != Items.f_41852_) {
                if (item == IC2Blocks.PERSONAL_CHEST.m_5456_()) {
                    this.dispatcher.m_112272_((BlockEntity)this.personal_chest, matrixStack, buffer, combinedLight, combinedOverlay);
                    return;
                }
                if (item == IC2Blocks.ADVANCED_COMPARATOR.m_5456_()) {
                    this.dispatcher.m_112272_((BlockEntity)this.comparator, matrixStack, buffer, combinedLight, combinedOverlay);
                    return;
                }
            }
            super.m_108829_(stack, p_239207_2_, matrixStack, buffer, combinedLight, combinedOverlay);
        }
    }
}

