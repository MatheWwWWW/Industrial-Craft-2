/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.stats.Stats
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Material
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.IForgeShearable
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 */
package ic2.core.item.tool.electric;

import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.item.base.ElectricToolItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.utils.helpers.Tool;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ChainsawTool
extends ElectricToolItem
implements ISimpleItemModel {
    public static final ResourceLocation IDLE = new ResourceLocation("ic2", "sounds/tools/chainsaw/chainsaw_idle.ogg");
    public static final ResourceLocation STOP = new ResourceLocation("ic2", "sounds/tools/chainsaw/chainsaw_stop.ogg");
    public static final ResourceLocation USE_1 = new ResourceLocation("ic2", "sounds/tools/chainsaw/chainsaw_use_one.ogg");
    public static final ResourceLocation USE_2 = new ResourceLocation("ic2", "sounds/tools/chainsaw/chainsaw_use_two.ogg");
    int cost;

    public ChainsawTool() {
        this("chainsaw", 2, 50, 8.0f);
    }

    public ChainsawTool(String itemName, int level, int cost, float speed) {
        super(itemName, speed, 1.0f, 0.0f, new PropertiesBuilder().group(IC2.IC2_MAIN_GROUP));
        this.registerTools(Tool.AXE.withLevel(level));
        this.tier = 1;
        this.transferLimit = 100;
        this.capacity = 10000;
        this.miningSpeed = 12.0f;
        this.cost = cost;
    }

    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return Items.f_42386_.m_8096_(state) || state.m_60734_() == Blocks.f_50033_;
    }

    @Override
    public float m_8102_(ItemStack stack, BlockState state) {
        Material material = state.m_60767_();
        if (!ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack))) {
            return 1.0f;
        }
        if (material == Material.f_76320_ || material == Material.f_76300_ || material == Material.f_76274_ || material == Material.f_76271_ || material == Material.f_76301_ || material == Material.f_76304_ || material == Material.f_76302_) {
            return this.miningSpeed;
        }
        return super.m_8102_(stack, state);
    }

    public InteractionResult m_6880_(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity.f_19853_.f_46443_) {
            return InteractionResult.PASS;
        }
        if (entity instanceof IForgeShearable) {
            IForgeShearable target = (IForgeShearable)entity;
            BlockPos pos = entity.m_20183_();
            if (target.isShearable(stack, entity.f_19853_, pos) && ElectricItem.MANAGER.canUse(stack, this.getEnergyCost(stack) * 2)) {
                RandomSource rand = entity.m_217043_();
                for (ItemStack item : target.onSheared(player, stack, entity.f_19853_, pos, EnchantmentHelper.getTagEnchantmentLevel((Enchantment)Enchantments.f_44987_, (ItemStack)stack))) {
                    ItemEntity itemEntity = entity.m_5552_(item, 1.0f);
                    itemEntity.m_20256_(itemEntity.m_20184_().m_82520_((double)((rand.m_188501_() - rand.m_188501_()) * 0.1f), (double)(rand.m_188501_() * 0.05f), (double)((rand.m_188501_() - rand.m_188501_()) * 0.1f)));
                }
                ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack) * 2, (LivingEntity)player);
                IC2.AUDIO.playSound(player, player.f_19853_.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player)) {
            return true;
        }
        Player player = (Player)attacker;
        if (ElectricItem.MANAGER.use(stack, this.cost, attacker)) {
            target.m_6469_(DamageSource.m_19344_((Player)player), 10.0f);
        } else {
            target.m_6469_(DamageSource.m_19344_((Player)player), 1.0f);
        }
        IC2.AUDIO.playSound(attacker, target.f_19853_.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
        if (target.m_21223_() <= 0.0f) {
            player.m_36220_(IC2Stats.CHAINSAW_KILLS);
            if (target instanceof Creeper) {
                IC2Advancements.ID_TRIGGER.onTrigger((Player)attacker, new ResourceLocation("ic2:creeper_kill"));
            }
        }
        return false;
    }

    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if (player.f_19853_.f_46443_ || player.m_7500_()) {
            return false;
        }
        BlockState state = player.f_19853_.m_8055_(pos);
        Block block = state.m_60734_();
        if ((state.m_204336_(BlockTags.f_13035_) || block == Blocks.f_50033_) && ElectricItem.MANAGER.canUse(itemstack, this.getEnergyCost(itemstack))) {
            ItemStack drop = new ItemStack((ItemLike)block);
            float f = 0.7f;
            double d = (double)(player.f_19853_.f_46441_.m_188501_() * f) + (double)(1.0f - f) * 0.5;
            double d1 = (double)(player.f_19853_.f_46441_.m_188501_() * f) + (double)(1.0f - f) * 0.5;
            double d2 = (double)(player.f_19853_.f_46441_.m_188501_() * f) + (double)(1.0f - f) * 0.5;
            ItemEntity itemEntity = new ItemEntity(player.f_19853_, (double)pos.m_123341_() + d, (double)pos.m_123342_() + d1, (double)pos.m_123343_() + d2, drop);
            itemEntity.m_32060_();
            player.f_19853_.m_7967_((Entity)itemEntity);
            ElectricItem.MANAGER.use(itemstack, this.getEnergyCost(itemstack), (LivingEntity)player);
            IC2.AUDIO.playSound(player, player.f_19853_.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            player.f_19853_.m_46597_(pos, Blocks.f_50016_.m_49966_());
            player.m_36246_(Stats.f_12949_.m_12902_((Object)block));
            if (block == Blocks.f_50033_) {
                player.m_36220_(IC2Stats.BLOCKS_SAWED);
            }
            player.f_19853_.m_46597_(pos, Blocks.f_50016_.m_49966_());
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        IC2.AUDIO.playSound(entity, world.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
        if (entity instanceof Player) {
            ((Player)entity).m_36220_(IC2Stats.BLOCKS_SAWED);
        }
        return super.m_6813_(stack, world, state, pos, entity);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        BlockPos pos;
        Level world = context.m_43725_();
        BlockState stripped = world.m_8055_(pos = context.m_8083_()).getToolModifiedState(context, ToolActions.AXE_STRIP, false);
        if (stripped != null) {
            Player player = context.m_43723_();
            IC2.AUDIO.playSound(player, world.f_46441_.m_188499_() ? USE_1 : USE_2, AudioManager.SoundType.ITEM, 1.0f, 1.0f);
            if (!world.f_46443_) {
                world.m_7731_(pos, stripped, 11);
                ItemStack stack = context.m_43722_();
                ElectricItem.MANAGER.use(stack, this.getEnergyCost(stack), (LivingEntity)player);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.AXE_STRIP;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return this.cost;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools/chainsaw").get("base");
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44987_ || ench == Enchantments.f_44982_ || ench == Enchantments.f_44984_ || ench == Enchantments.f_44977_ || ench == Enchantments.f_44978_ || ench == Enchantments.f_44979_ ? InteractionResult.SUCCESS : super.getEnchantmentCompatibility(stack, ench);
    }
}

