/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.wearable.base;

import ic2.api.network.item.INetworkItemEvent;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioSource;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.item.base.IC2ElectricItem;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IArmorToolTip;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.RocketModePacket;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class IC2JetpackBase
extends IC2AdvancedArmorBase
implements IArmorToolTip,
INetworkItemEvent {
    public static ResourceLocation full = new ResourceLocation("ic2", "sounds/tools/jetpack/jetpack_loop.ogg");
    public static ResourceLocation empty = new ResourceLocation("ic2", "sounds/tools/jetpack/jetpack_fire.ogg");
    public static final String DISABLED = "disabled";
    public static final String TOGGLE_TIMER = "ToggleTimer";
    public static final String JETPACK_TICKER = "JetpackTicker";
    public static final String HOVER_MODE = "HoverMode";
    public static final String ROCKET_MODE = "RocketMode";
    public static final String ROCKET_DELAY = "RocketDelay";
    public static final String LAST_USE_MODE = "LastUseMode";
    public static final String USED_ENERGY = "UsedEnergy";
    public static final String FUEL_USE_TIME = "FuelUseTime";
    public static final String FUNKY_MODE = "SpecialMode";
    public static final String DOWNWARDS_ACCELLERATING = "SpecialMovement";
    public static final String DOWNWARDS_ACCELLERATING_TICKER = "SpecialMovementTicker";

    public IC2JetpackBase(String itemName, EquipmentSlot slot, @Nullable PropertiesBuilder props) {
        super(itemName, slot, props);
    }

    public abstract boolean canDoRocketMode(ItemStack var1);

    public abstract boolean canDoAdvHoverMode(ItemStack var1);

    public abstract boolean isElectricJetpack(ItemStack var1);

    public abstract float getPower(ItemStack var1);

    public abstract float getThruster(ItemStack var1, HoverMode var2);

    public abstract float getDropPercentage(ItemStack var1);

    public abstract int getMaxHeight(ItemStack var1, int var2);

    public abstract int getFuel(ItemStack var1);

    public abstract int getMaxFuel(ItemStack var1);

    public abstract int getMaxRocketCharge(ItemStack var1);

    public abstract int getFuelCost(ItemStack var1, HoverMode var2);

    public abstract void useEnergy(Player var1, ItemStack var2, int var3);

    public boolean canDisable(ItemStack stack) {
        return true;
    }

    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        return create ? stack.m_41784_() : StackUtil.getNbtData(stack);
    }

    public IAudioSource createAudioSource(ItemStack stack, Player player, JetpackUseMode mode) {
        return IC2.AUDIO.createSource(player, mode == JetpackUseMode.FULL ? full : empty, AudioManager.SoundType.BACKPACK, 1.0f, true, false);
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.f_43901_;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt;
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.JUMP_KEY, "tooltip.item.ic2.jetpack.jump_to_fly", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.jetpack.mode_switch", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.jetpack.quick_toggle", new Object[0]));
        if (this.canDisable(stack)) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.TOGGLE_KEY, "tooltip.item.ic2.jetpack.toggle", new Object[0]));
        }
        if (this.canDoRocketMode(stack)) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.BOOST_KEY, "tooltip.item.ic2.jetpack.toggle_rocket", new Object[0]));
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BOOST_KEY, "tooltip.item.ic2.jetpack.charge_rocket", new Object[0]));
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.jetpack.release_rocket", new Object[0]));
        }
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip("", new Object[0]);
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
        if ((nbt = this.getNBTData(stack, false)).m_128471_(ROCKET_MODE)) {
            helper.addSimpleToolTip("tooltip.item.ic2.jetpack.rocket_charge", (int)((double)nbt.m_128451_(USED_ENERGY) / (double)this.getMaxRocketCharge(stack) * 100.0));
            helper.addSimpleToolTip("", new Object[0]);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTip(ItemStack armor, ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        CompoundTag nbt;
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.JUMP_KEY, "tooltip.item.ic2.jetpack.jump_to_fly", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, "tooltip.item.ic2.jetpack.mode_switch", new Object[0]));
        helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.jetpack.quick_toggle", new Object[0]));
        if (this.canDisable(stack)) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.TOGGLE_KEY, "tooltip.item.ic2.jetpack.toggle", new Object[0]));
        }
        if (this.canDoRocketMode(stack)) {
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.MODE_KEY, KeyHelper.BOOST_KEY, "tooltip.item.ic2.jetpack.toggle_rocket", new Object[0]));
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.BOOST_KEY, "tooltip.item.ic2.jetpack.charge_rocket", new Object[0]));
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.item.ic2.jetpack.release_rocket", new Object[0]));
        }
        if (this instanceof IHasHeldGui) {
            helper.addKeybindingTooltip("", new Object[0]);
            helper.addKeybindingTooltip(this.buildKeyDescription(KeyHelper.RIGHT_CLICK, KeyHelper.SIDE_INV_KEY, "tooltip.ic2.open_item_inventory", new Object[0]));
        }
        if ((nbt = this.getNBTData(stack, false)).m_128471_(ROCKET_MODE)) {
            helper.addSimpleToolTip("tooltip.item.ic2.jetpack.rocket_charge", (int)((double)nbt.m_128451_(USED_ENERGY) / (double)this.getMaxRocketCharge(stack) * 100.0));
            helper.addSimpleToolTip("", new Object[0]);
        }
    }

    public JetpackUseMode useJetpack(ItemStack stack, Player player, PlayerHandler handler, HoverMode hoverMode, Entity affectedEntity) {
        byte time;
        float thruster;
        float forwardPower;
        int fuel = this.getFuel(stack);
        if (fuel <= 0) {
            return JetpackUseMode.NONE;
        }
        JetpackUseMode jetpackMode = JetpackUseMode.NONE;
        boolean electric = this.isElectricJetpack(stack);
        float power = this.getPower(stack);
        float dropPercentage = this.getDropPercentage(stack);
        if ((double)fuel / (double)this.getMaxFuel(stack) <= (double)dropPercentage) {
            power = (float)((double)power * ((double)fuel / (double)((float)this.getMaxFuel(stack) * dropPercentage)));
            jetpackMode = JetpackUseMode.DROPPED;
        }
        boolean elytra = player.m_21255_();
        if (handler.forwardKeyDown && !elytra && (forwardPower = power * (thruster = this.getThruster(stack, hoverMode)) * 2.0f) > 0.0f) {
            affectedEntity.m_19920_(0.02f, new Vec3(0.0, 0.0, 0.4 * (double)forwardPower));
        }
        int maxFlightHeight = this.getMaxHeight(stack, player.f_19853_.m_151558_());
        double y = affectedEntity.m_20186_();
        if (y > (double)(maxFlightHeight - 25)) {
            if (y > (double)maxFlightHeight) {
                y = maxFlightHeight;
            }
            power = (float)((double)power * (((double)maxFlightHeight - y) / 25.0));
        }
        Vec3 motion = affectedEntity.m_20184_();
        CompoundTag nbt = this.getNBTData(stack, true);
        if (nbt.m_128471_(FUNKY_MODE) && !player.m_20096_()) {
            affectedEntity.m_20256_(motion.m_82520_(0.0, Math.min(motion.f_82480_ + (double)(power * 0.2f), (double)0.6f), 0.0));
            if (IC2.PLATFORM.isSimulating()) {
                player.getPersistentData().m_128379_(DOWNWARDS_ACCELLERATING, affectedEntity.m_20184_().f_82480_ < 0.0);
                player.getPersistentData().m_128405_(DOWNWARDS_ACCELLERATING_TICKER, 40);
            }
        } else if (elytra) {
            float thruster2 = this.getThruster(stack, hoverMode);
            float forwardPower2 = power * thruster2 * 2.0f;
            if (forwardPower2 > 0.0f) {
                affectedEntity.m_19920_(0.1f, new Vec3(0.0, 0.0, 0.4 * (double)Math.min(forwardPower2, 0.7f)));
            }
        } else {
            affectedEntity.m_20334_(motion.f_82479_, Math.min(motion.f_82480_ + (double)(power * 0.2f), (double)0.6f), motion.f_82481_);
        }
        if (hoverMode != HoverMode.NONE) {
            float maxHoverY;
            float f = maxHoverY = hoverMode == HoverMode.BASIC ? -0.2f : 0.0f;
            if (!player.m_6144_() || !handler.jumpKeyDown) {
                float f2 = maxHoverY = hoverMode == HoverMode.BASIC || player.m_6144_() ? -0.2f : 0.0f;
                if (electric && handler.jumpKeyDown) {
                    maxHoverY = hoverMode == HoverMode.BASIC ? 0.1f : 0.3f;
                }
            }
            Vec3 affectedMotion = affectedEntity.m_20184_();
            if (affectedMotion.f_82480_ > (double)maxHoverY) {
                affectedEntity.m_20334_(affectedMotion.f_82479_, Math.max(motion.f_82480_, (double)maxHoverY), affectedMotion.f_82481_);
            }
        }
        if ((time = nbt.m_128445_(FUEL_USE_TIME)) == 0) {
            this.useEnergy(player, stack, this.getFuelCost(stack, hoverMode));
            if (jetpackMode != JetpackUseMode.DROPPED) {
                jetpackMode = JetpackUseMode.FULL;
            }
            if (hoverMode == HoverMode.BASIC && !handler.jumpKeyDown) {
                time = 5;
                nbt.m_128344_(FUEL_USE_TIME, time);
            }
        } else if (time <= 1 && jetpackMode != JetpackUseMode.DROPPED) {
            jetpackMode = JetpackUseMode.FULL;
        }
        if (!nbt.m_128471_(FUNKY_MODE)) {
            affectedEntity.f_19789_ = 0.0f;
            affectedEntity.f_19787_ = 0.0f;
            IC2.PLATFORM.resetPlayerInAirTime((Entity)player);
            IC2.NETWORKING.get().sendClientItemEvent(stack, 100, 0);
        }
        if (jetpackMode != JetpackUseMode.NONE) {
            float yOff = elytra ? 0.2f : 0.75f;
            Vec2 offset = this.createOffset(player, -0.65f, elytra);
            affectedEntity.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_175834_, player.m_20185_() + (double)offset.f_82470_, player.m_20186_() + (double)yOff, player.m_20189_() + (double)offset.f_82471_, 0.0, 0.0, 0.0);
            offset = this.createOffset(player, 0.65f, elytra);
            affectedEntity.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_175834_, player.m_20185_() + (double)offset.f_82470_, player.m_20186_() + (double)yOff, player.m_20189_() + (double)offset.f_82471_, 0.0, 0.0, 0.0);
        }
        return jetpackMode;
    }

    private Vec2 createOffset(Player player, float offset, boolean elytra) {
        float yRotation = player.f_20883_ - (float)Math.toDegrees(offset);
        float scale = elytra ? 0.1f : 0.25f;
        float xOff = Mth.m_14031_((float)(-yRotation * ((float)Math.PI / 180) - (float)Math.PI)) * scale;
        float zOff = Mth.m_14089_((float)(-yRotation * ((float)Math.PI / 180) - (float)Math.PI)) * scale;
        return new Vec2(xOff, zOff);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        byte time;
        int accellerationTicker;
        CompoundTag nbt = this.getNBTData(stack, true);
        boolean disabled = nbt.m_128471_(DISABLED);
        byte jetpackTicker = nbt.m_128445_(JETPACK_TICKER);
        byte toggleTimer = nbt.m_128445_(TOGGLE_TIMER);
        PlayerHandler handler = PlayerHandler.getHandler(player);
        Entity entity = player.m_20201_();
        boolean server = IC2.PLATFORM.isSimulating();
        if (disabled) {
            if (jetpackTicker > 0) {
                jetpackTicker = (byte)(jetpackTicker - 1);
                nbt.m_128344_(JETPACK_TICKER, jetpackTicker);
            } else if (handler.toggleKeyDown && !handler.screenOpen) {
                nbt.m_128344_(JETPACK_TICKER, (byte)10);
                nbt.m_128379_(DISABLED, false);
                if (server) {
                    player.m_5661_((Component)this.translate("info.item.ic2.jetpack.on"), false);
                }
            }
            return;
        }
        if (this.canDisable(stack) && handler.toggleKeyDown && !handler.screenOpen && jetpackTicker <= 0) {
            nbt.m_128344_(JETPACK_TICKER, (byte)10);
            nbt.m_128379_(DISABLED, true);
            nbt.m_128344_(LAST_USE_MODE, JetpackUseMode.NONE.index);
            if (server) {
                player.m_5661_((Component)this.translate("info.item.ic2.jetpack.off"), false);
            }
            return;
        }
        if (jetpackTicker > 0) {
            jetpackTicker = (byte)(jetpackTicker - 1);
            nbt.m_128344_(JETPACK_TICKER, jetpackTicker);
        }
        if (server && (accellerationTicker = player.getPersistentData().m_128451_(DOWNWARDS_ACCELLERATING_TICKER)) > 0) {
            player.getPersistentData().m_128405_(DOWNWARDS_ACCELLERATING_TICKER, --accellerationTicker);
            if (accellerationTicker == 0) {
                player.getPersistentData().m_128379_(DOWNWARDS_ACCELLERATING, false);
            }
        }
        HoverMode hoverMode = HoverMode.byIndex(nbt.m_128445_(HOVER_MODE));
        boolean rocketMode = nbt.m_128471_(ROCKET_MODE);
        short rocketDelay = nbt.m_128448_(ROCKET_DELAY);
        boolean jetpackUsed = false;
        JetpackUseMode useMode = JetpackUseMode.NONE;
        if (rocketMode && !this.canDoRocketMode(stack)) {
            rocketMode = false;
            nbt.m_128379_(ROCKET_MODE, false);
        }
        if (handler.jumpKeyDown && handler.modeSwitchKeyDown && !handler.altKeyDown && toggleTimer <= 0) {
            toggleTimer = 10;
            hoverMode = hoverMode.getNext(this.canDoAdvHoverMode(stack));
            nbt.m_128344_(HOVER_MODE, hoverMode.index);
            if (hoverMode == HoverMode.BASIC || hoverMode == HoverMode.ADV) {
                if (server) {
                    player.m_5661_((Component)this.translate(hoverMode == HoverMode.BASIC ? "info.item.ic2.jetpack.hover_on" : "info.item.ic2.jetpack.adv_hover_on"), false);
                }
                if (rocketMode) {
                    rocketMode = false;
                    nbt.m_128379_(ROCKET_MODE, false);
                    if (server) {
                        player.m_5661_((Component)this.translate("info.item.ic2.jetpack.rocket_mode_off_forced"), false);
                    }
                    this.validateRocketCharge(nbt);
                }
            } else if (server) {
                player.m_5661_((Component)this.translate("info.item.ic2.jetpack.hover_off"), false);
            }
        }
        if (handler.modeSwitchKeyDown && handler.boostKeyDown && this.canDoRocketMode(stack) && toggleTimer == 0) {
            toggleTimer = 10;
            rocketMode = !rocketMode;
            nbt.m_128379_(ROCKET_MODE, rocketMode);
            if (rocketMode) {
                if (server) {
                    player.m_5661_((Component)this.translate("info.item.ic2.jetpack.rocket_mode_on"), false);
                }
                if (hoverMode != HoverMode.NONE) {
                    hoverMode = HoverMode.NONE;
                    nbt.m_128344_(HOVER_MODE, hoverMode.index);
                    if (server) {
                        player.m_5661_((Component)this.translate("info.item.ic2.jetpack.hover_off_forced"), false);
                    }
                }
            } else {
                if (server) {
                    player.m_5661_((Component)this.translate("info.item.ic2.jetpack.rocket_mode_off"), false);
                }
                this.validateRocketCharge(nbt);
            }
        }
        if (rocketMode && handler.boostKeyDown && rocketDelay == 0) {
            jetpackUsed = this.chargeRocketMode(player, stack);
            useMode = JetpackUseMode.FULL;
        }
        if (handler.altKeyDown && rocketMode && rocketDelay <= 0) {
            rocketDelay = this.releaseRocket(player, stack, entity);
            nbt.m_128379_(ROCKET_MODE, false);
            world.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11913_, SoundSource.PLAYERS, 4.0f, (1.0f + (world.f_46441_.m_188501_() - world.f_46441_.m_188501_()) * 0.2f) * 0.7f);
            if (server && rocketDelay > 0) {
                player.m_36220_(IC2Stats.ROCKET_MODE_USED);
            }
        } else if (handler.altKeyDown && rocketDelay <= 0 && !handler.modeSwitchKeyDown) {
            hoverMode = hoverMode.getNext(false);
            toggleTimer = 10;
        }
        if (handler.jumpKeyDown && !rocketMode && rocketDelay == 0 || hoverMode != HoverMode.NONE && (hoverMode == HoverMode.ADV && !entity.m_20096_() || hoverMode == HoverMode.BASIC && !entity.m_20096_() && entity.m_20184_().f_82480_ < -0.15)) {
            useMode = this.useJetpack(stack, player, handler, hoverMode, entity);
            boolean bl = jetpackUsed = useMode != JetpackUseMode.NONE;
        }
        if (toggleTimer > 0) {
            toggleTimer = (byte)(toggleTimer - 1);
            nbt.m_128344_(TOGGLE_TIMER, toggleTimer);
        }
        if (rocketDelay > 0) {
            rocketDelay = (short)(rocketDelay - 1);
            nbt.m_128376_(ROCKET_DELAY, rocketDelay);
        }
        if ((time = nbt.m_128445_(FUEL_USE_TIME)) > 0) {
            time = (byte)(time - 1);
            nbt.m_128344_(FUEL_USE_TIME, time);
        }
        if (server && jetpackUsed) {
            player.m_36220_(IC2Stats.JETPACK_FLY_TIME);
        }
        nbt.m_128344_(LAST_USE_MODE, useMode.index);
    }

    public short releaseRocket(Player player, ItemStack stack, Entity affectedEntity) {
        CompoundTag nbt = this.getNBTData(stack, true);
        int charge = nbt.m_128451_(USED_ENERGY);
        if (charge <= 0) {
            return 0;
        }
        nbt.m_128405_(USED_ENERGY, 0);
        this.useEnergy(player, stack, 50);
        int charges = charge / this.getFuelCost(stack, HoverMode.NONE) / 50;
        float speed = this.getPower(stack) * (float)charges / 4.0f;
        if (player.m_21255_()) {
            float thruster = this.getThruster(stack, HoverMode.NONE);
            float power = this.getPower(stack);
            float forwardPower = power * thruster * 2.0f * Math.min(speed * 2.0f, 10.0f);
            if (forwardPower > 0.0f) {
                affectedEntity.m_19920_(10.0f, new Vec3(0.0, 0.0, 0.4 * (double)forwardPower));
                if (player instanceof ServerPlayer) {
                    PacketManager.INSTANCE.sendToPlayer(new RocketModePacket(true, forwardPower, affectedEntity.m_19879_()), player);
                }
            }
            return (short)(speed * 10.0f);
        }
        affectedEntity.m_20256_(affectedEntity.m_20184_().m_82520_(0.0, (double)Math.min(speed, 10.0f), 0.0));
        if (player instanceof ServerPlayer) {
            PacketManager.INSTANCE.sendToPlayer(new RocketModePacket(false, Math.min(speed, 10.0f), affectedEntity.m_19879_()), player);
        }
        affectedEntity.f_19789_ = 0.0f;
        affectedEntity.f_19787_ = 0.0f;
        IC2.PLATFORM.resetPlayerInAirTime((Entity)player);
        return (short)(speed * 10.0f);
    }

    public boolean chargeRocketMode(Player player, ItemStack stack) {
        if (this.getFuel(stack) == 0) {
            return false;
        }
        CompoundTag nbt = this.getNBTData(stack, true);
        int chargedEnergy = nbt.m_128451_(USED_ENERGY);
        if (chargedEnergy > this.getMaxRocketCharge(stack)) {
            return false;
        }
        int used = Math.min(this.getFuelCost(stack, HoverMode.NONE) * 5, this.getFuel(stack));
        this.useEnergy(player, stack, used);
        nbt.m_128405_(USED_ENERGY, chargedEnergy + used);
        return true;
    }

    public void validateRocketCharge(CompoundTag nbt) {
        nbt.m_128473_(USED_ENERGY);
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isClient() || key != 100) {
            return;
        }
        IC2.PLATFORM.resetPlayerInAirTime((Entity)player);
    }

    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    public int m_142158_(ItemStack stack) {
        return (int)Math.round((double)this.getFuel(stack) / (double)this.getMaxFuel(stack) * 13.0);
    }

    public int m_142159_(ItemStack stack) {
        if (this.isElectricJetpack(stack)) {
            return IC2ElectricItem.getRGBDurability(stack);
        }
        return super.m_142159_(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        return true;
    }

    public JetpackUseMode getJetpackUse(ItemStack stack) {
        return JetpackUseMode.byIndex(this.getNBTData(stack, false).m_128451_(LAST_USE_MODE));
    }

    public static enum JetpackUseMode implements CollectionUtils.IIndexEnum
    {
        FULL(0),
        DROPPED(1),
        NONE(2);

        final byte index;
        static final JetpackUseMode[] VALUES;

        private JetpackUseMode(int index) {
            this.index = (byte)index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public static JetpackUseMode byIndex(int index) {
            return VALUES[index % VALUES.length];
        }

        static {
            VALUES = (JetpackUseMode[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])JetpackUseMode.values());
        }
    }

    public static enum HoverMode implements CollectionUtils.IIndexEnum
    {
        NONE(0),
        BASIC(1),
        ADV(2);

        final byte index;
        static final HoverMode[] VALUES;

        private HoverMode(int index) {
            this.index = (byte)index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public static HoverMode byIndex(int index) {
            return VALUES[index % VALUES.length];
        }

        public HoverMode getNext(boolean canAdv) {
            switch (this) {
                case NONE: {
                    return BASIC;
                }
                case BASIC: {
                    return canAdv ? ADV : NONE;
                }
            }
            return NONE;
        }

        static {
            VALUES = (HoverMode[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])HoverMode.values());
        }
    }

    public static interface IIndirectJetpack {
        public IC2JetpackBase getJetpack(ItemStack var1);
    }
}

