/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.customBlocks;

import ic2.core.IC2;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.machines.NoStateMachineBlock;
import ic2.core.block.rendering.block.FluidExpansionModel;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.features.IRenderType;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluidExpansionBlock
extends NoStateMachineBlock
implements ICustomBlockModel,
IRenderType {
    public FluidExpansionBlock() {
        super("fluid_expansion", IBlockDropProvider.SELF_OR_MACHINE, ITextureProvider.multiState(() -> IC2.CONFIG.fancyFluidExpansion.get() ? 0 : 1, ITextureProvider.noStateIC2("machine/nv/fluid_expansion"), ITextureProvider.noStateIC2("machine/nv/fluid_expansion_opaque")), IC2Tiles.FLUID_EXPANSION);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public RenderType getType() {
        return IC2.CONFIG.fancyFluidExpansion.get() ? RenderType.m_110463_() : RenderType.m_110451_();
    }

    @Override
    public List<BlockState> getModelStates() {
        return ObjectLists.emptyList();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new FluidExpansionModel(state, this);
    }
}

