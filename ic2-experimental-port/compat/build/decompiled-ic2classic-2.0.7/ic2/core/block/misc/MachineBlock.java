/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.misc;

import ic2.core.block.base.IC2Block;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.item.base.IC2BlockItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.utils.helpers.Tool;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MachineBlock
extends IC2Block
implements IBlockModel {
    String type;

    public MachineBlock(String blockName, String type) {
        super(blockName, BaseMachineBlock.BASE_MACHINE);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.type = type;
    }

    @Override
    public BlockItem createItem() {
        return new IC2BlockItem(this);
    }

    @Override
    public List<BlockState> getModelStates() {
        return Collections.singletonList(this.m_49966_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getSpriteForState(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlockIC2(this.type).get(side.m_122433_());
    }
}

