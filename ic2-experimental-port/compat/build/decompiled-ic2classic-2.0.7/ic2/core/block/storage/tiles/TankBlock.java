/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.storage.tiles;

import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.NoStateMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.ICustomBlockModel;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.blocks.CamouflageableBlock;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Tool;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class TankBlock
extends NoStateMachineBlock
implements ICamouflageBlock,
ICustomBlockModel {
    ITextureProvider overlay;

    public TankBlock(String blockName, ITextureProvider provider, ITextureProvider overlay, BlockEntityType<? extends BaseTileEntity> type) {
        super(blockName, IBlockDropProvider.SELF, provider, BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60955_().m_60924_((T, V, X) -> false).m_60918_(SoundType.f_56743_).m_60913_(5.0f, 25.0f), type);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.overlay = overlay;
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
    }

    public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public BaseModel getForCustomState(BlockState state) {
        return new CamouflageableBlock(state, this, this.overlay, false);
    }

    @Override
    public List<BlockState> getModelStates() {
        return CollectionUtils.createList();
    }
}

