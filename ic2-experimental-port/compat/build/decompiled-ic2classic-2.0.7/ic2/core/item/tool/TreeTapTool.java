/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.misc.tiles.BarrelTileEntity;
import ic2.core.block.resource.RubberwoodLogBlock;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TreeTapTool
extends IC2Item
implements ISimpleItemModel,
IToolModel {
    public TreeTapTool() {
        this("tree_tap", null);
    }

    protected TreeTapTool(String itemName, @Nullable PropertiesBuilder properties) {
        super(itemName, (properties == null ? new PropertiesBuilder() : properties).group(IC2.IC2_MAIN_GROUP).maxDamage(16));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/treetap").get("manual");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        return InteractionResultHolder.m_19090_((Object)player.m_21120_(hand));
    }

    public InteractionResult m_6225_(UseOnContext context) {
        BarrelTileEntity barrel;
        BlockEntity tile;
        BlockState state = context.m_43725_().m_8055_(context.m_8083_());
        if (state.m_60734_() instanceof RubberwoodLogBlock) {
            boolean server = IC2.PLATFORM.isSimulating();
            if (server && TreeTapTool.extractRubber(state, context.m_43725_(), context.m_8083_(), context.m_43719_(), context.m_43722_(), null)) {
                IC2.AUDIO.playSound(context.m_43723_(), IC2Sounds.TREE_TAP, AudioManager.SoundType.ITEM);
                context.m_43722_().m_41622_(1, (LivingEntity)context.m_43723_(), TreeTapTool.get(context.m_43724_()));
                return InteractionResult.SUCCESS;
            }
            if (!server && ((Boolean)state.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() && state.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) == context.m_43719_()) {
                return InteractionResult.SUCCESS;
            }
        } else if (state.m_60734_() == IC2Blocks.BARREL && context.m_43719_().m_122434_().m_122479_() && this == IC2Items.TREETAP && (tile = context.m_43725_().m_7702_(context.m_8083_())) instanceof BarrelTileEntity && !(barrel = (BarrelTileEntity)tile).hasTreeTap() && barrel.isSimulating()) {
            barrel.setFacing(context.m_43719_());
            barrel.setHasTreeTap(true);
            barrel.openBarrel();
            context.m_43722_().m_41774_(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static boolean extractRubber(BlockState state, Level world, BlockPos pos, Direction side, ItemStack stack, List<ItemStack> list) {
        if (!((Boolean)state.m_61143_((Property)RubberwoodLogBlock.RESIN)).booleanValue() || state.m_61143_((Property)RubberwoodLogBlock.RESIN_FACING) != side) {
            return false;
        }
        if (((Boolean)state.m_61143_((Property)RubberwoodLogBlock.COLLECTABLE)).booleanValue()) {
            world.m_46597_(pos, (BlockState)state.m_61124_((Property)RubberwoodLogBlock.COLLECTABLE, (Comparable)Boolean.valueOf(false)));
            ItemStack drop = new ItemStack((ItemLike)IC2Items.STICKY_RESIN, TreeTapTool.getResinAmount(stack, 1, 3));
            if (list != null) {
                list.add(drop);
            } else {
                Block.m_152435_((Level)world, (BlockPos)pos.m_121945_(side), (Direction)side.m_122424_(), (ItemStack)drop);
            }
            return true;
        }
        if (world.f_46441_.m_188503_(5) == 0) {
            world.m_46597_(pos, (BlockState)state.m_61124_((Property)RubberwoodLogBlock.RESIN, (Comparable)Boolean.valueOf(false)));
        }
        if (world.f_46441_.m_188503_(5) == 0) {
            ItemStack drop = new ItemStack((ItemLike)IC2Items.STICKY_RESIN, TreeTapTool.getResinAmount(stack, 1, 3));
            if (list != null) {
                list.add(drop);
            } else {
                Block.m_152435_((Level)world, (BlockPos)pos.m_121945_(side), (Direction)side.m_122424_(), (ItemStack)drop);
            }
        }
        return true;
    }

    public static int getResinAmount(ItemStack stack, int base, int extra) {
        return IC2Item.RANDOM.m_188503_(extra + EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44987_, (ItemStack)stack)) + base;
    }

    public static boolean extractRubber(UseOnContext context, List<ItemStack> list) {
        BlockState state = context.m_43725_().m_8055_(context.m_8083_());
        return TreeTapTool.extractRubber(state, context.m_43725_(), context.m_8083_(), context.m_43719_(), context.m_43722_(), list);
    }
}

