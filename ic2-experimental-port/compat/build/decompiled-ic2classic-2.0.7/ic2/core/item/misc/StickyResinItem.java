/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.misc;

import ic2.core.item.base.IC2Item;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class StickyResinItem
extends IC2Item
implements ISimpleItemModel {
    public StickyResinItem() {
        super("sticky_resin");
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("misc").get("resin");
    }

    public InteractionResult m_6225_(UseOnContext context) {
        Player player = context.m_43723_();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        ItemStack stack = player.m_21120_(context.m_43724_());
        BlockState state = world.m_8055_(pos);
        if (state.m_60734_() == Blocks.f_50039_ && state.m_61143_((Property)BlockStateProperties.f_61372_) == context.m_43719_() && !((Boolean)state.m_61143_((Property)BlockStateProperties.f_61432_)).booleanValue()) {
            world.m_46597_(pos, (BlockState)Blocks.f_50032_.m_49966_().m_61124_((Property)BlockStateProperties.f_61372_, (Comparable)((Direction)state.m_61143_((Property)BlockStateProperties.f_61372_))));
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return InteractionResult.SUCCESS;
        }
        pos = pos.m_7494_();
        state = world.m_8055_(pos);
        if (!world.m_46859_(pos) || !IC2Blocks.RESIN_SHEET.m_7898_(state, (LevelReader)world, pos)) {
            return InteractionResult.PASS;
        }
        world.m_46597_(pos, IC2Blocks.RESIN_SHEET.m_49966_());
        if (!player.m_7500_()) {
            stack.m_41774_(1);
        }
        return InteractionResult.SUCCESS;
    }
}

