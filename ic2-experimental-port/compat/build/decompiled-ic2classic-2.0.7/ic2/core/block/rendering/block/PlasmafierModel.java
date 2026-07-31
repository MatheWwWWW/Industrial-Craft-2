/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.block;

import com.mojang.math.Vector3f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IColoredBlockModel;
import ic2.core.platform.rendering.features.block.ILockedBlockUV;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import ic2.core.platform.rendering.misc.ModelTransform;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;

public class PlasmafierModel
extends SimpleBlockModel {
    public PlasmafierModel(BlockState state, IBlockModel model) {
        super(state, model);
    }

    @Override
    public void init() {
        IRotatableBlock facing;
        super.init();
        boolean locked = this.state.m_60734_() instanceof ILockedBlockUV;
        Direction blockFacing = Direction.NORTH;
        BlockModelRotation rotation = BlockModelRotation.X0_Y0;
        if (this.model instanceof IRotatableBlock && (facing = (IRotatableBlock)((Object)this.model)).hasRotation(this.state)) {
            blockFacing = facing.getRotation(this.state);
            Tuple<Integer, Integer> result = QuadBaker.getModelRotation(blockFacing);
            rotation = BlockModelRotation.m_119153_((int)((Integer)result.m_14418_()), (int)((Integer)result.m_14419_()));
        }
        IColoredBlockModel colorModel = this.model instanceof IColoredBlockModel ? (IColoredBlockModel)((Object)this.model) : null;
        Vector3f max = new Vector3f(0.005f, 0.005f, 0.005f);
        Vector3f min = new Vector3f(15.995f, 15.995f, 15.995f);
        for (Direction side : DirectionList.ALL) {
            BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
            BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side.m_122424_(), 0));
            TextureAtlasSprite sprite = IC2Models.getSafeIcon(this.model.getSpriteForState(this.state, side.m_122424_()));
            this.quads[6].add(locked ? QuadBaker.BAKER.m_111600_(QuadBaker.getMin(side, min, max), QuadBaker.getMax(side, min, max), face, sprite, side, (ModelState)new ModelTransform((ModelState)sideRotation, true), null, true, LOCATION) : QuadBaker.BAKER.m_111600_(QuadBaker.getMin(side, min, max), QuadBaker.getMax(side, min, max), face, sprite, side, (ModelState)sideRotation, null, true, LOCATION));
        }
    }

    @Override
    public boolean m_7547_() {
        return true;
    }

    @Override
    public boolean m_7541_() {
        return false;
    }
}

