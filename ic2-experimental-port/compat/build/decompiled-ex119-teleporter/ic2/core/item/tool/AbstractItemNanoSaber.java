/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.sound.item.ISwingSoundItem;
import ic2.core.IC2;
import ic2.core.item.armor.ItemArmorNanoSuit;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.slot.ArmorSlot;
import ic2.core.util.StackUtil;
import java.util.Collections;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractItemNanoSaber
extends ItemElectricTool
implements ISwingSoundItem {
    public static int ticker = 0;
    private int soundTicker = 0;
    public static final int ANIMATION_FRAME = 4;

    public AbstractItemNanoSaber(Item.Properties properties) {
        super(properties, 10, (Tier)Tiers.DIAMOND, Collections.emptyList());
        this.maxCharge = 160000;
        this.transferLimit = 500;
        this.tier = 3;
    }

    @Override
    public boolean consumeEnergy(ItemStack itemStack, double d, LivingEntity livingEntity) {
        if (!super.consumeEnergy(itemStack, d, livingEntity)) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            AbstractItemNanoSaber.setActive(compoundTag, false);
            return false;
        }
        return true;
    }

    @Override
    public float m_8102_(ItemStack itemStack, BlockState blockState) {
        if (AbstractItemNanoSaber.isActive(itemStack)) {
            Entity entity;
            ++this.soundTicker;
            if (IC2.sideProxy.isRendering() && this.soundTicker % 4 == 0 && (entity = itemStack.m_41609_()) != null) {
                entity.m_5496_(this.getRandomSwingSound(), 1.0f, 1.0f);
            }
            return blockState.m_60734_() == Blocks.f_50033_ ? 50.0f : 4.0f;
        }
        return 1.0f;
    }

    @Override
    public boolean m_7579_(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (!AbstractItemNanoSaber.isActive(itemStack)) {
            return true;
        }
        if (IC2.sideProxy.isSimulating()) {
            this.consumeEnergy(itemStack, 400.0, livingEntity2);
            if (!(livingEntity2 instanceof ServerPlayer) || !(livingEntity instanceof Player) || ((ServerPlayer)livingEntity2).m_7099_((Player)livingEntity)) {
                for (EquipmentSlot equipmentSlot : ArmorSlot.getAll()) {
                    if (!ElectricItem.manager.canUse(itemStack, 2000.0)) break;
                    ItemStack itemStack2 = livingEntity.m_6844_(equipmentSlot);
                    if (itemStack2 == null) continue;
                    double d = 0.0;
                    if (itemStack2.m_41720_() instanceof ItemArmorNanoSuit) {
                        d = 48000.0;
                    } else if (itemStack2.m_41720_() instanceof ItemArmorQuantumSuit) {
                        d = 300000.0;
                    }
                    if (!(d > 0.0)) continue;
                    this.consumeEnergy(itemStack2, d, null);
                    if (!ElectricItem.manager.canUse(itemStack2, 1.0)) {
                        livingEntity.m_8061_(equipmentSlot, null);
                    }
                    this.consumeEnergy(itemStack, 2000.0, livingEntity2);
                }
            }
        }
        return true;
    }

    public SoundEvent getRandomSwingSound() {
        return switch (IC2.random.m_188503_(3)) {
            default -> Ic2SoundEvents.ITEM_NANOSABER_SWING1;
            case 1 -> Ic2SoundEvents.ITEM_NANOSABER_SWING2;
            case 2 -> Ic2SoundEvents.ITEM_NANOSABER_SWING3;
        };
    }

    public boolean m_6777_(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        if (player.m_7500_()) {
            return false;
        }
        return super.m_6777_(blockState, level, blockPos, player);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext useOnContext) {
        return super.m_6225_(useOnContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (level.f_46443_) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (AbstractItemNanoSaber.isActive(compoundTag)) {
            AbstractItemNanoSaber.setActive(compoundTag, false);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
        }
        if (ElectricItem.manager.canUse(itemStack, 16.0)) {
            AbstractItemNanoSaber.setActive(compoundTag, true);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
        }
        return super.m_7203_(level, player, interactionHand);
    }

    @Override
    public void m_6883_(ItemStack itemStack, Level level, Entity entity, int n, boolean bl) {
        super.m_6883_(itemStack, level, entity, n, bl && AbstractItemNanoSaber.isActive(itemStack));
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (!AbstractItemNanoSaber.isActive(compoundTag)) {
            ticker = 0;
            return;
        }
        if (++ticker % 16 == 0 && entity instanceof ServerPlayer) {
            if (n < 9) {
                this.consumeEnergy(itemStack, 64.0, (LivingEntity)((Player)entity));
            } else if (ticker % 64 == 0) {
                this.consumeEnergy(itemStack, 16.0, (LivingEntity)((Player)entity));
            }
        }
    }

    public float getActiveData() {
        return ticker > 0 ? (float)(Math.floor((double)ticker / 20.0 * 4.0) % 10.0 + 1.0) / 10.0f : 0.0f;
    }

    public Rarity m_41460_(ItemStack itemStack) {
        return Rarity.UNCOMMON;
    }

    public static boolean isActive(ItemStack itemStack) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        return AbstractItemNanoSaber.isActive(compoundTag);
    }

    public static boolean isActive(CompoundTag compoundTag) {
        return compoundTag.m_128471_("active");
    }

    private static void setActive(CompoundTag compoundTag, boolean bl) {
        compoundTag.m_128379_("active", bl);
    }

    @Override
    protected SoundEvent getIdleSound(LivingEntity livingEntity, ItemStack itemStack) {
        return Ic2SoundEvents.ITEM_NANOSABER_IDLE;
    }

    @Override
    protected SoundEvent getStartSound(LivingEntity livingEntity, ItemStack itemStack) {
        return Ic2SoundEvents.ITEM_NANOSABER_POWER_UP;
    }

    @Override
    public SoundEvent getSwingSound(LivingEntity livingEntity, InteractionHand interactionHand) {
        return this.getRandomSwingSound();
    }
}

