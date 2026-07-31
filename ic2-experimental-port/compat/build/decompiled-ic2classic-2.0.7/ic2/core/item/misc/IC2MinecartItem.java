/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.vehicle.AbstractMinecart
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseRailBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.RailShape
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.core.item.base.IC2Item;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import java.util.function.BiFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IC2MinecartItem
extends IC2Item
implements ISimpleItemModel {
    BiFunction<Level, Vec3, AbstractMinecart> creator;
    String texture;

    public IC2MinecartItem(String itemName, String texture, BiFunction<Level, Vec3, AbstractMinecart> creator) {
        super(itemName);
        this.texture = texture;
        this.creator = creator;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(this.id.m_135827_(), "minecart").get(this.texture);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        BlockPos pos;
        Level level = context.m_43725_();
        BlockState state = level.m_8055_(pos = context.m_8083_());
        if (!state.m_204336_(BlockTags.f_13034_)) {
            return InteractionResult.FAIL;
        }
        ItemStack itemstack = context.m_43722_();
        if (!level.f_46443_) {
            RailShape railshape = state.m_60734_() instanceof BaseRailBlock ? ((BaseRailBlock)state.m_60734_()).getRailDirection(state, (BlockGetter)level, pos, null) : RailShape.NORTH_SOUTH;
            double d0 = 0.0;
            if (railshape.m_61745_()) {
                d0 = 0.5;
            }
            AbstractMinecart abstractminecart = this.creator.apply(level, Vec3.m_82514_((Vec3i)pos, (double)d0));
            if (itemstack.m_41788_()) {
                abstractminecart.m_6593_(itemstack.m_41786_());
            }
            level.m_7967_((Entity)abstractminecart);
            level.m_220407_(GameEvent.f_157810_, pos, GameEvent.Context.m_223719_((Entity)context.m_43723_(), (BlockState)level.m_8055_(pos.m_7495_())));
        }
        itemstack.m_41774_(1);
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }
}

