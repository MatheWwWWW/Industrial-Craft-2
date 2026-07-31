/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.core.item.base.IC2Item;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MinerRemoteItem
extends IC2Item
implements ISimpleItemModel {
    public MinerRemoteItem() {
        super("miner_remote");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("miner_remote");
    }

    public static BlockPos getPos(ItemStack stack) {
        CompoundTag tag = StackUtil.getNbtData(stack);
        return tag.m_128441_("target_pos") ? BlockPos.m_122022_((long)tag.m_128454_("target_pos")) : BlockPos.f_121853_;
    }

    public static void setPos(ItemStack stack, BlockPos pos) {
        stack.m_41784_().m_128356_("target_pos", pos.m_121878_());
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!playerIn.m_6144_()) {
            this.markMiners(worldIn, playerIn.m_20183_(), playerIn);
            return InteractionResultHolder.m_19090_((Object)playerIn.m_21120_(handIn));
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    private void markMiners(Level world, BlockPos pos, Player player) {
        AABBUtil.getBlockEntities(world, T -> {
            if (T instanceof MinerTileEntity) {
                BlockPos relative = T.m_58899_().m_121996_((Vec3i)pos);
                player.m_5661_((Component)this.translate("tooltip.item.ic2.mining_remote.location." + (T instanceof RocketMinerTileEntity ? "rocket_miner" : "miner"), new Object[]{this.pos(T.m_58899_()), this.pos(relative), (int)Math.sqrt(T.m_58899_().m_123331_((Vec3i)pos))}), false);
            }
        });
        if (world instanceof ServerLevel) {
            ServerLevel server = (ServerLevel)world;
            server.m_142646_().m_142690_(IC2Entities.ROCKET_MINER, T -> {
                BlockPos relative = T.m_20183_().m_121996_((Vec3i)pos);
                player.m_5661_((Component)this.translate("tooltip.item.ic2.mining_remote.location.rocket_miner", new Object[]{this.pos(T.m_20183_()), this.pos(relative), (int)Math.sqrt(T.m_20183_().m_123331_((Vec3i)pos))}), false);
            });
        }
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.m_7078_()) {
            MinerRemoteItem.setPos(stack, context.m_8083_());
            if (IC2.PLATFORM.isSimulating()) {
                context.m_43723_().m_5661_((Component)this.translate("tooltip.item.ic2.mining_remote.location.refuel_set", new Object[]{this.pos(context.m_8083_())}), false);
            }
            return InteractionResult.SUCCESS;
        }
        return super.onItemUseFirst(stack, context);
    }
}

