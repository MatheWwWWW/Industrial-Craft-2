/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 */
package ic2.core.block.base.blocks;

import ic2.core.block.base.ICamouflageBlock;
import ic2.core.block.base.blocks.BaseTexturedBlock;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class ValveBlock
extends BaseTexturedBlock
implements ICamouflageBlock,
ICustomBlockModel {
    ITextureProvider overlay;

    public ValveBlock(String blockName, ITextureProvider provider, ITextureProvider overlay, BlockBehaviour.Properties properties, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, properties.m_60955_().m_60924_((T, V, X) -> false).m_60918_(SoundType.f_56743_).m_60913_(5.0f, 25.0f), provider, type);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.overlay = overlay;
    }

    public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ICamouflageBlock.CamuflageWrapper(this));
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

