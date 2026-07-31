/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tool;

import ic2.api.item.IMiningDrill;
import ic2.core.IC2;
import ic2.core.IHitSoundOverride;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import java.util.Arrays;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemDrill
extends ItemElectricTool
implements IMiningDrill,
IHitSoundOverride {
    private final float extraSpeedMultiplier;

    public ItemDrill(Item.Properties properties, int n, Tier tier, int n2, int n3, int n4, float f) {
        super(properties, n, tier, Arrays.asList(BlockTags.f_144282_, BlockTags.f_144283_));
        this.maxCharge = n2;
        this.transferLimit = n3;
        this.tier = n4;
        this.extraSpeedMultiplier = f / tier.m_6624_();
    }

    @Override
    public float m_8102_(ItemStack itemStack, BlockState blockState) {
        float f = super.m_8102_(itemStack, blockState);
        if (f == 1.0f) {
            return f;
        }
        Player player = ItemDrill.getPlayerHoldingItem(itemStack);
        if (player != null) {
            if (player.m_204029_(FluidTags.f_13131_) && !EnchantmentHelper.m_44934_((LivingEntity)player)) {
                f *= 5.0f;
            }
            if (!player.m_20096_()) {
                f *= 5.0f;
            }
        }
        return f * this.extraSpeedMultiplier;
    }

    private static Player getPlayerHoldingItem(ItemStack itemStack) {
        if (IC2.sideProxy.isRendering()) {
            Player player = IC2.sideProxy.getPlayerInstance();
            if (player != null && player.m_150109_().m_36056_() == itemStack) {
                return player;
            }
        } else {
            MinecraftServer minecraftServer = IC2.envProxy.getServer();
            if (minecraftServer == null) {
                return null;
            }
            for (Player player : minecraftServer.m_6846_().m_11314_()) {
                if (player.m_150109_().m_36056_() != itemStack) continue;
                return player;
            }
        }
        return null;
    }

    @Override
    public int energyUse(ItemStack itemStack, Level level, BlockPos blockPos, BlockState blockState) {
        Item item = itemStack.m_41720_();
        if (item == Ic2Items.DRILL) {
            return 6;
        }
        if (item == Ic2Items.DIAMOND_DRILL) {
            return 20;
        }
        if (item == Ic2Items.IRIDIUM_DRILL) {
            return 200;
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(itemStack));
    }

    @Override
    public int breakTime(ItemStack itemStack, Level level, BlockPos blockPos, BlockState blockState) {
        Item item = itemStack.m_41720_();
        if (itemStack.m_41720_() == Ic2Items.DRILL) {
            return 200;
        }
        if (itemStack.m_41720_() == Ic2Items.DIAMOND_DRILL) {
            return 50;
        }
        if (itemStack.m_41720_() == Ic2Items.IRIDIUM_DRILL) {
            return 20;
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(itemStack));
    }

    @Override
    public boolean breakBlock(ItemStack itemStack, Level level, BlockPos blockPos, BlockState blockState) {
        Item item = itemStack.m_41720_();
        if (item == Ic2Items.DRILL) {
            return this.tryUsePower(itemStack, 50.0);
        }
        if (item == Ic2Items.DIAMOND_DRILL) {
            return this.tryUsePower(itemStack, 80.0);
        }
        if (item == Ic2Items.IRIDIUM_DRILL) {
            return this.tryUsePower(itemStack, 800.0);
        }
        throw new IllegalArgumentException("Invalid drill: " + StackUtil.toStringSafe(itemStack));
    }

    @Override
    protected SoundEvent getIdleSound(LivingEntity livingEntity, ItemStack itemStack) {
        return Ic2SoundEvents.ITEM_DRILL_IDLE;
    }

    @Override
    public SoundEvent getHitSoundForBlock(LocalPlayer localPlayer, Level level, BlockPos blockPos, ItemStack itemStack) {
        return null;
    }

    @Override
    public SoundEvent getBreakSoundForBlock(LocalPlayer localPlayer, Level level, BlockPos blockPos, ItemStack itemStack) {
        Block block = level.m_8055_(blockPos).m_60734_();
        return block.m_155943_() >= 3.0f ? Ic2SoundEvents.ITEM_DRILL_HARD : Ic2SoundEvents.ITEM_DRILL_SOFT;
    }
}

