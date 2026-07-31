/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.DiggerItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Tier
 *  net.minecraft.world.item.Tiers
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.api.network.INetworkItemEventListener;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.ElectricItemTooltipHandler;
import ic2.core.ref.Ic2BlockTags;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.sound.Sound;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class ItemElectricTool
extends DiggerItem
implements IElectricItem,
INetworkItemEventListener,
IItemHudInfo {
    public double operationEnergyCost;
    private final Collection<TagKey<Block>> effectiveBlocks;
    public int maxCharge;
    public int transferLimit;
    public int tier;
    protected Sound idleSound;
    protected Sound startSound;
    protected Sound stopSound;
    protected boolean wasEquipped;

    protected ItemElectricTool(Item.Properties properties, int n) {
        this(properties, n, (Tier)Tiers.IRON, Collections.emptyList());
    }

    protected ItemElectricTool(Item.Properties properties, int n, Tier tier, Collection<TagKey<Block>> collection) {
        this(properties, 2.0f, -3.0f, n, tier, collection);
    }

    private ItemElectricTool(Item.Properties properties, float f, float f2, int n, Tier tier, Collection<TagKey<Block>> collection) {
        super(f, f2, tier, collection.isEmpty() ? Ic2BlockTags.EMPTY : collection.iterator().next(), properties);
        this.operationEnergyCost = n;
        this.effectiveBlocks = collection;
    }

    public static boolean consumeEnergy(ItemStack itemStack, double d, int n, LivingEntity livingEntity) {
        if (!(itemStack.m_41720_() instanceof IElectricItem)) {
            return false;
        }
        if (!ElectricItem.manager.canUse(itemStack, d)) {
            return false;
        }
        boolean bl = livingEntity == null ? Util.isSimilar(ElectricItem.manager.discharge(itemStack, d, n, true, false, false), d) : ElectricItem.manager.use(itemStack, d, livingEntity);
        if (ElectricItem.manager.getCharge(itemStack) <= 0.0 && livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            IC2.network.get(true).initiateItemEvent(player, itemStack, 0, true);
        }
        return bl;
    }

    @Override
    public void onNetworkEvent(ItemStack itemStack, Player player, int n) {
        player.m_5496_(this.getShutdownSound(), 1.0f, 1.0f);
    }

    public boolean consumeEnergy(ItemStack itemStack, double d, LivingEntity livingEntity) {
        return ItemElectricTool.consumeEnergy(itemStack, d, this.tier, livingEntity);
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(ElectricItem.manager.getToolTip(itemStack));
        linkedList.add(Localization.translate("ic2.item.tooltip.PowerTier", this.tier));
        return linkedList;
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        ElectricItem.manager.use(useOnContext.m_43722_(), 0.0, (LivingEntity)useOnContext.m_43723_());
        return super.m_6225_(useOnContext);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ElectricItem.manager.use(StackUtil.get(player, interactionHand), 0.0, (LivingEntity)player);
        return super.m_7203_(level, player, interactionHand);
    }

    public float m_8102_(ItemStack itemStack, BlockState blockState) {
        if (!this.isEffective(blockState) || !ElectricItem.manager.canUse(itemStack, this.operationEnergyCost)) {
            return 1.0f;
        }
        return this.f_40980_;
    }

    public boolean m_8096_(BlockState blockState) {
        int n = this.m_43314_().m_6604_();
        if (n < 3 && blockState.m_204336_(BlockTags.f_144284_) || n < 2 && blockState.m_204336_(BlockTags.f_144285_) || n < 1 && blockState.m_204336_(BlockTags.f_144286_)) {
            return false;
        }
        return this.isEffective(blockState);
    }

    private boolean isEffective(BlockState blockState) {
        for (TagKey<Block> tagKey : this.effectiveBlocks) {
            if (!blockState.m_204336_(tagKey)) continue;
            return true;
        }
        return false;
    }

    public boolean m_7579_(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        return true;
    }

    public int m_6473_() {
        return 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    public boolean m_6813_(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (blockState.m_60800_((BlockGetter)level, blockPos) != 0.0f) {
            this.consumeEnergy(itemStack, this.operationEnergyCost, livingEntity);
        }
        return true;
    }

    public boolean m_8120_(ItemStack itemStack) {
        return false;
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        ElectricItemManager.addChargeVariants((Item)this, nonNullList);
    }

    public void m_7373_(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        ElectricItemTooltipHandler.addTooltip(itemStack, list);
    }

    protected ItemStack getItemStack(double d) {
        ItemStack itemStack = new ItemStack((ItemLike)this);
        ElectricItem.manager.charge(itemStack, d, Integer.MAX_VALUE, true, false);
        return itemStack;
    }

    public void m_6883_(ItemStack itemStack, Level level, Entity entity, int n, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = bl && entity instanceof LivingEntity;
        if (IC2.sideProxy.isRendering()) {
            LivingEntity livingEntity;
            ItemStack itemStack2;
            if (bl2 && !this.wasEquipped) {
                this.initSound((LivingEntity)entity, itemStack);
                if (this.idleSound != null) {
                    this.idleSound.play();
                }
                if (this.startSound != null) {
                    this.startSound.playOnce();
                }
            } else if (!bl2 && this.idleSound != null && entity instanceof LivingEntity && ((itemStack2 = (livingEntity = (LivingEntity)entity).m_6844_(EquipmentSlot.MAINHAND)) == null || itemStack2.m_41720_() != this || itemStack2 == itemStack)) {
                if (this.stopSound != null) {
                    this.stopSound.playOnce();
                }
                this.clearSound(livingEntity);
            }
            this.wasEquipped = bl2;
        }
    }

    protected void initSound(LivingEntity livingEntity, ItemStack itemStack) {
        SoundEvent soundEvent;
        SoundEvent soundEvent2;
        SoundEvent soundEvent3;
        if (this.idleSound == null && (soundEvent3 = this.getIdleSound(livingEntity, itemStack)) != null) {
            this.idleSound = IC2.soundManager.createSound((Object)livingEntity, soundEvent3, SoundSource.PLAYERS, livingEntity, 1.0f, 1.0f);
        }
        if (this.startSound == null && (soundEvent2 = this.getStartSound(livingEntity, itemStack)) != null) {
            this.stopSound = IC2.soundManager.createSound((Object)livingEntity, soundEvent2, SoundSource.PLAYERS, livingEntity, 1.0f, 1.0f);
        }
        if (this.stopSound == null && (soundEvent = this.getStopSound(livingEntity, itemStack)) != null) {
            this.stopSound = IC2.soundManager.createSound((Object)livingEntity, soundEvent, SoundSource.PLAYERS, livingEntity, 1.0f, 1.0f);
        }
    }

    protected void clearSound(LivingEntity livingEntity) {
        if (this.idleSound != null) {
            IC2.soundManager.removeSound(livingEntity, this.idleSound);
            this.idleSound = null;
        }
        if (this.startSound != null) {
            IC2.soundManager.removeSound(livingEntity, this.startSound);
            this.startSound = null;
        }
        if (this.stopSound != null) {
            IC2.soundManager.removeSound(livingEntity, this.stopSound);
            this.stopSound = null;
        }
    }

    public boolean onDroppedByPlayer(ItemStack itemStack, Player player) {
        this.clearSound((LivingEntity)player);
        return true;
    }

    protected SoundEvent getIdleSound(LivingEntity livingEntity, ItemStack itemStack) {
        return null;
    }

    protected SoundEvent getStopSound(LivingEntity livingEntity, ItemStack itemStack) {
        return null;
    }

    protected SoundEvent getStartSound(LivingEntity livingEntity, ItemStack itemStack) {
        return null;
    }

    public SoundEvent getShutdownSound() {
        return Ic2SoundEvents.ITEM_ELECTRIC_SHUTDOWN;
    }

    public boolean m_142522_(ItemStack itemStack) {
        return true;
    }

    public int m_142158_(ItemStack itemStack) {
        return (int)Math.round(ElectricItem.manager.getChargeLevel(itemStack) * 13.0);
    }

    public int m_142159_(ItemStack itemStack) {
        return Mth.m_14169_((float)((float)(ElectricItem.manager.getChargeLevel(itemStack) / 3.0)), (float)1.0f, (float)1.0f);
    }

    public boolean canUse(ItemStack itemStack) {
        return ElectricItem.manager.canUse(itemStack, this.operationEnergyCost);
    }
}

