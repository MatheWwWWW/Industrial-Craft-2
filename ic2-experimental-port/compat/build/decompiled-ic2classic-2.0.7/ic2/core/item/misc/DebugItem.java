/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.misc;

import ic2.api.tiles.tubes.ITube;
import ic2.core.IC2;
import ic2.core.block.base.features.IProfileListener;
import ic2.core.block.personal.tile.TradeOMatTileEntity;
import ic2.core.block.personal.trade.ItemTrade;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.item.base.IC2Item;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.DebugPacket;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DebugItem
extends IC2Item
implements ISimpleItemModel {
    public DebugItem(String itemName) {
        super(itemName);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("no_use");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isSimulating() && !playerIn.m_6144_()) {
            CompoundTag nbt = stack.m_41784_();
            boolean value = !nbt.m_128471_("active");
            nbt.m_128379_("active", value);
            playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.debug.tile." + (value ? "enabled" : "disabled")), false);
        }
        return InteractionResultHolder.m_19090_((Object)stack);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.m_43723_() != null && context.m_43723_().m_6144_()) {
            BlockEntity tile = context.m_43725_().m_7702_(context.m_8083_());
            if (tile instanceof ITube) {
                TubeNet.INSTANCE.printLag((ITube)tile, context.m_43723_());
                return InteractionResult.SUCCESS;
            }
            if (tile instanceof TradeOMatTileEntity) {
                if (IC2.PLATFORM.isSimulating()) {
                    TradeOMatTileEntity trade = (TradeOMatTileEntity)tile;
                    CompoundTag data = stack.m_41784_();
                    if (data.m_128441_("trades")) {
                        ((ItemTrade)trade.getTrade()).load(data.m_128469_("trades"));
                        data.m_128473_("trades");
                        context.m_43723_().m_5661_((Component)this.string("Inserted Data"), false);
                    } else {
                        data.m_128365_("trades", (Tag)((ItemTrade)trade.getTrade()).save(new CompoundTag()));
                        context.m_43723_().m_5661_((Component)this.string("Copied Data"), false);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            if (tile instanceof IProfileListener) {
                context.m_43723_().m_5661_((Component)((IProfileListener)tile).showResults(), false);
                return InteractionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(stack, context);
    }

    public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (IC2.PLATFORM.isRendering() || !(entityIn instanceof Player) || worldIn.m_46467_() % 5L == 0L) {
            return;
        }
        if (stack.m_41784_().m_128471_("active")) {
            Vec3 start = entityIn.m_20299_(1.0f);
            Vec3 end = start.m_82549_(entityIn.m_20252_(1.0f).m_82542_(10.0, 10.0, 10.0));
            BlockHitResult result = worldIn.m_45547_(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entityIn));
            BlockEntity tile = worldIn.m_7702_(result.m_82425_());
            long[] data = new long[]{NetworkManager.countNetworkBytes(tile, true, false), NetworkManager.countNetworkBytes(tile, false, false), NetworkManager.countNetworkBytes(tile, false, true)};
            PacketManager.INSTANCE.sendToPlayer(new DebugPacket(worldIn.m_8055_(result.m_82425_()), tile == null ? null : tile.m_187480_(), data), (Player)entityIn);
        }
    }
}

