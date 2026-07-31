/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.block;

import ic2.core.block.cables.luminator.ConstructionLightBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ConstructionLightItemBlock
extends IC2BlockItem
implements ISimpleItemModel {
    public ConstructionLightItemBlock(Block block) {
        super(block);
    }

    protected boolean m_7429_(BlockPlaceContext context, BlockState state) {
        if (!context.m_43725_().m_8055_(context.m_8083_().m_7494_()).m_60629_(context)) {
            return false;
        }
        FluidState bottom = context.m_43725_().m_6425_(context.m_8083_());
        FluidState top = context.m_43725_().m_6425_(context.m_8083_().m_7494_());
        return context.m_43725_().m_7731_(context.m_8083_(), (BlockState)((BlockState)((BlockState)state.m_61124_((Property)ConstructionLightBlock.TOP, (Comparable)Boolean.valueOf(false))).m_61124_((Property)ConstructionLightBlock.WATER, (Comparable)Boolean.valueOf(bottom.m_76152_() == Fluids.f_76193_))).m_61124_((Property)ConstructionLightBlock.LAVA, (Comparable)Boolean.valueOf(bottom.m_76152_() == Fluids.f_76195_)), 11) && context.m_43725_().m_7731_(context.m_8083_().m_7494_(), (BlockState)((BlockState)((BlockState)state.m_61124_((Property)ConstructionLightBlock.TOP, (Comparable)Boolean.valueOf(true))).m_61124_((Property)ConstructionLightBlock.WATER, (Comparable)Boolean.valueOf(top.m_76152_() == Fluids.f_76193_))).m_61124_((Property)ConstructionLightBlock.LAVA, (Comparable)Boolean.valueOf(top.m_76152_() == Fluids.f_76195_)), 11);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("misc").get("construction_light");
    }
}

