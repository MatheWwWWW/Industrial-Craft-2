/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.misc.explosive.DynamiteBlock;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DynamiteRemoteToolItem
extends IC2Item
implements IToolModel,
ISimpleItemModel {
    public static ResourceLocation AUDIO_FILE = new ResourceLocation("ic2", "sounds/tools/dynamite_remote.ogg");

    public DynamiteRemoteToolItem() {
        super("dynamite_remote", new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP).maxStackSize(1));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("explosive").get("dynamite-o-remote");
    }

    public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        int count = this.getStoredLinkCount(stack);
        if (count > 0) {
            tooltip.add((Component)this.translate("tooltip.item.ic2.dynamite_remote.count", new Object[]{count}));
        }
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (this.getStoredLinkCount(stack) > 0 && playerIn.m_6144_()) {
            if (IC2.PLATFORM.isSimulating()) {
                this.blowUp(worldIn, stack);
                IC2.AUDIO.playSound(playerIn, AUDIO_FILE, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public int getStoredLinkCount(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128437_("positions", 10).size();
    }

    public void blowUp(Level world, ItemStack stack) {
        String dim = world.m_46472_().m_135782_().toString();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        Iterator<CompoundTag> iter = NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_("positions", 10), CompoundTag.class).iterator();
        while (iter.hasNext()) {
            CompoundTag nbt = iter.next();
            mutable.m_122188_(nbt.m_128454_("pos"));
            if (!dim.equals(nbt.m_128461_("dim")) || !world.m_46749_((BlockPos)mutable)) continue;
            BlockState state = world.m_8055_((BlockPos)mutable);
            IC2.LOGGER.info("Test: " + state);
            if (state.m_60734_() != IC2Blocks.DYNAMITE) continue;
            DynamiteBlock.ignite(state, world, (BlockPos)mutable);
            iter.remove();
        }
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos;
        Level world = context.m_43725_();
        BlockState state = world.m_8055_(pos = context.m_8083_());
        if (state.m_60734_() == IC2Blocks.DYNAMITE && !context.m_43723_().m_6144_()) {
            if (DynamiteBlock.isLinked(state)) {
                if (this.removeDynamite(world, pos, stack)) {
                    DynamiteBlock.setLinked(state, world, pos, false);
                    return InteractionResult.SUCCESS;
                }
            } else {
                DynamiteBlock.setLinked(state, world, pos, true);
            }
            this.addDynamite(world, pos, stack);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void addDynamite(Level world, BlockPos pos, ItemStack stack) {
        ListTag list = stack.m_41784_().m_128437_("positions", 10);
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_("dim", world.m_46472_().m_135782_().toString());
        nbt.m_128356_("pos", pos.m_121878_());
        list.add((Object)nbt);
        stack.m_41700_("positions", (Tag)list);
    }

    public boolean removeDynamite(Level world, BlockPos pos, ItemStack stack) {
        String dim = world.m_46472_().m_135782_().toString();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        Iterator<CompoundTag> iter = NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_("positions", 10), CompoundTag.class).iterator();
        while (iter.hasNext()) {
            CompoundTag nbt = iter.next();
            mutable.m_122188_(nbt.m_128454_("pos"));
            if (!dim.equals(nbt.m_128461_("dim")) || !mutable.equals((Object)pos)) continue;
            iter.remove();
            return true;
        }
        return false;
    }
}

