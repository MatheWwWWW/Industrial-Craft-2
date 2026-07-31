/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.tool;

import ic2.api.blocks.IWrenchable;
import ic2.api.blocks.WrenchHelper;
import ic2.api.items.readers.IWrenchTool;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WrenchTool
extends IC2Item
implements IToolModel,
ISimpleItemModel,
IWrenchTool {
    public WrenchTool() {
        this("wrench");
    }

    public WrenchTool(String itemName) {
        this(itemName, null);
    }

    protected WrenchTool(String itemName, @Nullable PropertiesBuilder properties) {
        super(itemName, (properties == null ? new PropertiesBuilder() : properties).maxDamage(160).group(IC2.IC2_MAIN_GROUP));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/wrench").get("manual");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        return InteractionResultHolder.m_19090_((Object)playerIn.m_21120_(handIn));
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Direction newFacing;
        if (!this.canTakeDamage(stack, 1)) {
            return InteractionResult.PASS;
        }
        Level world = context.m_43725_();
        BlockPos pos = context.m_8083_();
        Player player = context.m_43723_();
        BlockState state = world.m_8055_(pos);
        IWrenchable wrench = IWrenchable.WrenchRegistry.INSTANCE.getWrenchable(state);
        if (wrench == null) {
            return InteractionResult.PASS;
        }
        Direction old = wrench.getFacing(state, world, pos);
        if (old != null && (newFacing = WrenchHelper.getFacingFromIndex(context.m_43719_(), WrenchHelper.getDirectionIndex(context), player)) != old && wrench.setFacing(state, world, pos, context.m_43723_(), newFacing)) {
            this.damageWrench(stack, 1, player, context.m_43724_());
            this.playWrenchSound(stack, player);
            return InteractionResult.SUCCESS;
        }
        if (this.canTakeDamage(stack, 5) && wrench.doSpecialAction(state, world, pos, context.m_43719_(), player, context.m_43720_().m_82546_(Vec3.m_82528_((Vec3i)pos)))) {
            this.damageWrench(stack, 5, context.m_43723_(), context.m_43724_());
            this.playWrenchSound(stack, player);
            return InteractionResult.SUCCESS;
        }
        if (this.canTakeDamage(stack, this.canOverrideLoss(stack) ? 100 : 10) && wrench.canRemoveBlock(state, world, pos, player)) {
            if (world.f_46443_) {
                return InteractionResult.SUCCESS;
            }
            boolean originalDrop = world.f_46441_.m_188500_() <= wrench.getDropRate(state, world, pos, player) * this.applyFortune(stack, this.getChanceModifier(stack));
            this.damageWrench(stack, 10, player, context.m_43724_());
            this.playWrenchSound(stack, player);
            if (!originalDrop && this.canOverrideLoss(stack)) {
                originalDrop = true;
                this.onLossPrevented(player, stack);
                if (this.hasBigCost(stack)) {
                    this.damageItem(stack, 200, (LivingEntity)player, WrenchTool.get(context.m_43724_()));
                }
            }
            for (ItemStack drop : originalDrop ? wrench.getDrops(state, world, pos, player) : Block.m_49874_((BlockState)state, (ServerLevel)((ServerLevel)world), (BlockPos)pos, (BlockEntity)world.m_7702_(pos), (Entity)player, (ItemStack)stack)) {
                Block.m_49840_((Level)world, (BlockPos)pos, (ItemStack)drop);
            }
            world.m_7471_(pos, false);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void playWrenchSound(ItemStack stack, Player player) {
        IC2.AUDIO.playSound(player, IC2Sounds.WRENCH, AudioManager.SoundType.ITEM);
    }

    public boolean canOverrideLoss(ItemStack stack) {
        return false;
    }

    public boolean canTakeDamage(ItemStack stack, int amount) {
        return true;
    }

    public void damageWrench(ItemStack stack, int amount, Player player, InteractionHand hand) {
        stack.m_41622_(amount, (LivingEntity)player, WrenchTool.get(hand));
    }

    public void onLossPrevented(Player player, ItemStack stack) {
    }

    public boolean hasBigCost(ItemStack stack) {
        return true;
    }

    public double getChanceModifier(ItemStack stack) {
        return 1.0;
    }

    public double applyFortune(ItemStack stack, double base) {
        int fortune = EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44987_, (ItemStack)stack);
        for (int i = 0; i < fortune; ++i) {
            base += base * 0.03;
        }
        return base;
    }

    @Override
    public double getActualLoss(ItemStack stack, double originalLoss) {
        if ((originalLoss *= this.applyFortune(stack, this.getChanceModifier(stack))) < 1.0 && this.canOverrideLoss(stack) && this.canTakeDamage(stack, 100)) {
            return 1.0;
        }
        return originalLoss;
    }
}

