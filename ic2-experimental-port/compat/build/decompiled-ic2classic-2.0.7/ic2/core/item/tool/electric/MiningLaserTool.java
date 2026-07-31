/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.boss.EnderDragonPart
 *  net.minecraft.world.entity.boss.enderdragon.EndCrystal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentCategory
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.item.tool.electric;

import ic2.api.events.LaserEvent;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricEnchantable;
import ic2.api.items.electric.IMiningDrill;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.entity.misc.MiningLaserEntity;
import ic2.core.entity.misc.TrackingMiningLaserEntity;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import ic2.core.platform.rendering.features.item.IToolModel;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class MiningLaserTool
extends IC2ElectricItem
implements ISimpleItemModel,
IMiningDrill,
IElectricEnchantable,
IToolModel {
    public static final int[] MODE_COST = new int[]{1250, 100, 5000, 0, 2500, 10000, 5000, 10000};

    public MiningLaserTool() {
        super("mining_laser");
        this.capacity = 200000;
        this.tier = 2;
        this.transferLimit = 120;
    }

    @Override
    protected int getEnergyCost(ItemStack stack) {
        return 80;
    }

    public Component getModeName(int mode) {
        switch (mode) {
            case 0: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.mining");
            }
            case 1: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.low_focus");
            }
            case 2: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.long_range");
            }
            case 3: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.horizontal");
            }
            case 4: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.super_heat");
            }
            case 5: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.scatter");
            }
            case 6: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.explosive");
            }
            case 7: {
                return this.translate("tooltip.item.ic2.mining_laser.mode.tracking");
            }
        }
        return this.translate("tooltip.item.ic2.error");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        helper.addSimpleToolTip("tooltip.item.ic2.mining_laser.mode", this.getModeName(this.getMode(stack)));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.mining_laser.mode_switch", new Object[0]));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItemIC2("tools").get("mining_laser");
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.m_21120_(handIn);
        if (IC2.PLATFORM.isRendering()) {
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        int mode = this.getMode(stack);
        if (IC2.KEYBOARD.isModeSwitchKeyDown(playerIn)) {
            ++mode;
            this.setMode(stack, mode %= 8);
            playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.mining_laser.mode", new Object[]{this.getModeName(mode)}), false);
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        if (mode != 3 && mode != 7) {
            if (!ElectricItem.MANAGER.use(stack, MODE_COST[mode], (LivingEntity)playerIn)) {
                return InteractionResultHolder.m_19098_((Object)stack);
            }
            if (mode == 5) {
                boolean didShoot = false;
                for (int x = -2; x <= 2; ++x) {
                    for (int y = -2; y <= 2; ++y) {
                        MiningLaserEntity laser = this.getMiningLaser(IC2Entities.MINING_LASER, mode, worldIn, playerIn, playerIn.m_146908_() + (float)(20 * x), playerIn.m_146909_() + (float)(20 * y));
                        LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent((LevelAccessor)worldIn, laser, (LivingEntity)playerIn, laser.range, laser.power, laser.blockBreaks, laser.explosive, laser.smelt, stack);
                        if (MinecraftForge.EVENT_BUS.post((Event)event)) continue;
                        worldIn.m_7967_((Entity)laser);
                        didShoot = true;
                    }
                }
                if (didShoot) {
                    IC2.AUDIO.playSound(playerIn, IC2Sounds.LASER_SCATTER, AudioManager.SoundType.ITEM);
                }
                return didShoot ? InteractionResultHolder.m_19090_((Object)stack) : InteractionResultHolder.m_19100_((Object)stack);
            }
            MiningLaserEntity laser = this.getMiningLaser(IC2Entities.MINING_LASER, mode, worldIn, playerIn, 0.0, 0.0);
            if (laser != null) {
                LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent((LevelAccessor)worldIn, laser, (LivingEntity)playerIn, laser.range, laser.power, laser.blockBreaks, laser.explosive, laser.smelt, stack);
                MinecraftForge.EVENT_BUS.post((Event)event);
                if (event.isCanceled()) {
                    return InteractionResultHolder.m_19100_((Object)stack);
                }
                worldIn.m_7967_((Entity)laser);
                IC2.AUDIO.playSound(playerIn, this.getShootingSound(mode), AudioManager.SoundType.ITEM);
                return InteractionResultHolder.m_19090_((Object)stack);
            }
        } else if (mode == 7) {
            EntityHitResult result = MiningLaserTool.rayTraceEntities(worldIn, (LivingEntity)playerIn, false, 60.0);
            if (result != null) {
                Entity entity = result.m_82443_();
                if ((entity instanceof LivingEntity || entity instanceof EnderDragonPart || entity instanceof EndCrystal) && ElectricItem.MANAGER.use(stack, MODE_COST[mode], (LivingEntity)playerIn)) {
                    TrackingMiningLaserEntity laser = new TrackingMiningLaserEntity(IC2Entities.TRACKING_MINING_LASER, worldIn, (LivingEntity)playerIn, 4.0f, 64.0f, 5, entity);
                    LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent((LevelAccessor)worldIn, laser, (LivingEntity)playerIn, laser.range, laser.power, laser.blockBreaks, laser.explosive, laser.smelt, stack);
                    MinecraftForge.EVENT_BUS.post((Event)event);
                    if (event.isCanceled() || !laser.canHitPlayer((HitResult)result)) {
                        return InteractionResultHolder.m_19100_((Object)stack);
                    }
                    worldIn.m_7967_((Entity)laser);
                    IC2.AUDIO.playSound(playerIn, this.getShootingSound(mode), AudioManager.SoundType.ITEM);
                }
            } else {
                playerIn.m_5661_((Component)this.translate("tooltip.item.ic2.mining_laser.error.no_target"), false);
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return InteractionResultHolder.m_19098_((Object)stack);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.m_43723_();
        if (player != null && !IC2.KEYBOARD.isModeSwitchKeyDown(player) && this.getMode(stack) == 3) {
            if (Math.abs(player.m_20186_() + (double)player.m_20192_() - 0.1 - ((double)context.m_8083_().m_123342_() + 0.5)) < 1.5) {
                if (!ElectricItem.MANAGER.canUse(stack, 3000)) {
                    return InteractionResult.PASS;
                }
                if (IC2.PLATFORM.isRendering()) {
                    return InteractionResult.SUCCESS;
                }
                if (ElectricItem.MANAGER.use(stack, 3000, (LivingEntity)player)) {
                    MiningLaserEntity laser = new MiningLaserEntity(IC2Entities.MINING_LASER, context.m_43725_(), (LivingEntity)player, 1.5f, 5.0f, Integer.MAX_VALUE, false, this.getRotationIndex((int)player.m_146908_(), 8), 0.0, (float)context.m_8083_().m_123342_() + 0.5f);
                    LaserEvent.LaserShootEvent event = new LaserEvent.LaserShootEvent((LevelAccessor)context.m_43725_(), laser, (LivingEntity)player, laser.range, laser.power, laser.blockBreaks, laser.explosive, laser.smelt, stack);
                    MinecraftForge.EVENT_BUS.post((Event)event);
                    if (event.isCanceled()) {
                        return InteractionResult.FAIL;
                    }
                    context.m_43725_().m_7967_((Entity)laser);
                    IC2.AUDIO.playSound(player, IC2Sounds.LASER_DEFAULT, AudioManager.SoundType.ITEM);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
            player.m_5661_((Component)this.translate("tooltip.item.ic2.mining_laser.error.steep"), false);
            return InteractionResult.FAIL;
        }
        return super.onItemUseFirst(stack, context);
    }

    private int getRotationIndex(int playerRotation, int rotations) {
        int spot;
        int rotationPerIndex = 360 / rotations;
        int rotation = playerRotation % 360;
        if (rotation < 0) {
            rotation = 360 + rotation;
        }
        return (Math.abs(rotation - (spot = rotation / rotationPerIndex) * rotationPerIndex) > Math.abs(rotation - (spot + 1) * rotationPerIndex) ? ++spot % rotations : spot) * rotationPerIndex;
    }

    public int getMode(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("mode");
    }

    public void setMode(ItemStack stack, int mode) {
        stack.m_41784_().m_128405_("mode", mode);
    }

    @Override
    public boolean canMineBlock(ItemStack drill, BlockState state, LevelReader access, BlockPos pos) {
        return true;
    }

    @Override
    public int getMiningBoost(ItemStack stack, BlockState state) {
        return 100;
    }

    @Override
    public int getExtraEnergyCost(ItemStack stack) {
        return 80;
    }

    @Override
    public boolean canDrillBeUsed(ItemStack stack) {
        return ElectricItem.MANAGER.canUse(stack, 250);
    }

    @Override
    public void onDrillUsed(ItemStack stack) {
        ElectricItem.MANAGER.discharge(stack, 250, 2, true, false, false);
    }

    public ResourceLocation getShootingSound(int mode) {
        switch (mode) {
            case 1: {
                return IC2Sounds.LASER_LOW_FOCUS;
            }
            case 3: {
                return IC2Sounds.LASER_LONG_RANGE;
            }
            case 6: {
                return IC2Sounds.LASER_EXPLOSIVE;
            }
        }
        return IC2Sounds.LASER_DEFAULT;
    }

    public MiningLaserEntity getMiningLaser(EntityType<MiningLaserEntity> type, int mode, Level world, Player player, double yaw, double pitch) {
        switch (mode) {
            case 0: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 2.0f, 5.0f, Integer.MAX_VALUE, false);
            }
            case 1: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 1.1f, 5.0f, 1, false);
            }
            case 2: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 2.0f, 32.0f, Integer.MAX_VALUE, false);
            }
            case 4: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 2.0f, 8.0f, Integer.MAX_VALUE, false, true);
            }
            case 5: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 2.0f, 12.0f, Integer.MAX_VALUE, false, yaw, pitch);
            }
            case 6: {
                return new MiningLaserEntity(type, world, (LivingEntity)player, 2.0f, 12.0f, Integer.MAX_VALUE, true);
            }
        }
        return null;
    }

    @Override
    public InteractionResult getEnchantmentCompatibility(ItemStack stack, Enchantment ench) {
        return ench == Enchantments.f_44986_ ? InteractionResult.SUCCESS : (ench == Enchantments.f_44962_ ? InteractionResult.FAIL : InteractionResult.PASS);
    }

    @Override
    public EnchantmentCategory getEnchantmentType(ItemStack stack) {
        return EnchantmentCategory.BREAKABLE;
    }
}

