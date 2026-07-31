/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.Projectile
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.item.tool;

import ic2.api.network.INetworkItemEventListener;
import ic2.core.IC2;
import ic2.core.entity.LaserBulletEntity;
import ic2.core.init.Localization;
import ic2.core.item.PriorityUsableItem;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.Vector3;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemToolMiningLaser
extends ItemElectricTool
implements INetworkItemEventListener,
PriorityUsableItem {
    private static final int EventShotMining = 0;
    private static final int EventShotLowFocus = 1;
    private static final int EventShotLongRange = 2;
    private static final int EventShotHorizontal = 3;
    private static final int EventShotSuperHeat = 4;
    private static final int EventShotScatter = 5;
    private static final int EventShotExplosive = 6;
    private static final int EventShot3x3 = 7;

    public ItemToolMiningLaser(Item.Properties properties) {
        super(properties, 100);
        this.maxCharge = 300000;
        this.transferLimit = 512;
        this.tier = 3;
    }

    @Override
    public boolean m_142522_(ItemStack itemStack) {
        return !itemStack.m_41786_().getString().equals("ic2:tab_icon");
    }

    @Override
    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        String string;
        super.m_7373_(itemStack, level, list, tooltipFlag);
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        switch (compoundTag.m_128451_("laserSetting")) {
            case 0: {
                string = Localization.translate("ic2.tooltip.mode.mining");
                break;
            }
            case 1: {
                string = Localization.translate("ic2.tooltip.mode.lowFocus");
                break;
            }
            case 2: {
                string = Localization.translate("ic2.tooltip.mode.longRange");
                break;
            }
            case 3: {
                string = Localization.translate("ic2.tooltip.mode.horizontal");
                break;
            }
            case 4: {
                string = Localization.translate("ic2.tooltip.mode.superHeat");
                break;
            }
            case 5: {
                string = Localization.translate("ic2.tooltip.mode.scatter");
                break;
            }
            case 6: {
                string = Localization.translate("ic2.tooltip.mode.explosive");
                break;
            }
            case 7: {
                string = Localization.translate("ic2.tooltip.mode.3x3");
                break;
            }
            default: {
                return;
            }
        }
        list.add((Component)Component.m_237110_((String)"ic2.tooltip.mode", (Object[])new Object[]{string}));
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        String string = Localization.translate(ItemToolMiningLaser.getModeString(compoundTag.m_128451_("laserSetting")));
        LinkedList<String> linkedList = new LinkedList<String>(super.getHudInfo(itemStack, bl));
        linkedList.add(Localization.translate("ic2.tooltip.mode", string));
        return linkedList;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (!IC2.sideProxy.isSimulating()) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        int n = compoundTag.m_128451_("laserSetting");
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            n = (n + 1) % 8;
            compoundTag.m_128405_("laserSetting", n);
            IC2.sideProxy.messagePlayer(player, "ic2.tooltip.mode", ItemToolMiningLaser.getModeString(n));
        } else {
            int n2 = (new int[]{1250, 100, 5000, 0, 2500, 10000, 5000, 7500})[n];
            if (!this.consumeEnergy(itemStack, n2, (LivingEntity)player)) {
                return new InteractionResultHolder(InteractionResult.FAIL, (Object)itemStack);
            }
            switch (n) {
                case 0: {
                    if (!this.shootLaser(itemStack, level, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false)) break;
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 0, true);
                    break;
                }
                case 1: {
                    if (!this.shootLaser(itemStack, level, (LivingEntity)player, 4.0f, 5.0f, 1, false, false)) break;
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 1, true);
                    break;
                }
                case 2: {
                    if (!this.shootLaser(itemStack, level, (LivingEntity)player, Float.POSITIVE_INFINITY, 20.0f, Integer.MAX_VALUE, false, false)) break;
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 2, true);
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    if (!this.shootLaser(itemStack, level, (LivingEntity)player, Float.POSITIVE_INFINITY, 8.0f, Integer.MAX_VALUE, false, true)) break;
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 4, true);
                    break;
                }
                case 5: {
                    Vector3 vector3 = Util.getLook((Entity)player);
                    Vector3 vector32 = vector3.copy().cross(Vector3.UP);
                    if (vector32.lengthSquared() < 1.0E-4) {
                        double d = Math.toRadians(player.m_146908_()) - 1.5707963267948966;
                        vector32.set(Math.sin(d), 0.0, -Math.cos(d));
                    } else {
                        vector32.normalize();
                    }
                    Vector3 vector33 = vector32.copy().cross(vector3);
                    vector3.scale(8.0);
                    for (int i = -2; i <= 2; ++i) {
                        for (int j = -2; j <= 2; ++j) {
                            Vector3 vector34 = vector3.copy().addScaled(vector32, i).addScaled(vector33, j).normalize();
                            this.shootLaser(itemStack, level, vector34, (LivingEntity)player, Float.POSITIVE_INFINITY, 12.0f, Integer.MAX_VALUE, false, false);
                        }
                    }
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 5, true);
                    break;
                }
                case 6: {
                    if (!this.shootLaser(itemStack, level, (LivingEntity)player, Float.POSITIVE_INFINITY, 12.0f, Integer.MAX_VALUE, true, false)) break;
                    IC2.network.get(true).initiateItemEvent(player, itemStack, 6, true);
                    break;
                }
            }
        }
        return super.m_7203_(level, player, interactionHand);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        Level level = useOnContext.m_43725_();
        Player player = useOnContext.m_43723_();
        BlockPos blockPos = useOnContext.m_8083_();
        if (level.f_46443_) {
            return InteractionResult.PASS;
        }
        if (player == null) {
            return InteractionResult.PASS;
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (!(IC2.keyboard.isModeSwitchKeyDown(player) || compoundTag.m_128451_("laserSetting") != 3 && compoundTag.m_128451_("laserSetting") != 7)) {
            Vector3 vector3 = Util.getLook((Entity)player);
            double d = vector3.dot(Vector3.UP);
            if (Math.abs(d) < 1.0 / Math.sqrt(2.0)) {
                if (this.consumeEnergy(itemStack, 3000.0, (LivingEntity)player)) {
                    vector3.y = 0.0;
                    vector3.normalize();
                    Vector3 vector32 = Util.getEyePosition((Entity)player);
                    vector32.y = (double)blockPos.m_123342_() + 0.5;
                    vector32 = ItemToolMiningLaser.adjustStartPos(vector32, vector3);
                    if (compoundTag.m_128451_("laserSetting") == 3) {
                        if (this.shootLaser(itemStack, level, vector32, vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false)) {
                            IC2.network.get(true).initiateItemEvent(player, itemStack, 3, true);
                        }
                    } else if (compoundTag.m_128451_("laserSetting") == 7 && this.shootLaser(itemStack, level, vector32, vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false)) {
                        this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y - 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y + 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        if (player.m_6350_().equals((Object)Direction.SOUTH) || player.m_6350_().equals((Object)Direction.NORTH)) {
                            this.shootLaser(itemStack, level, new Vector3(vector32.x - 1.0, vector32.y, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x + 1.0, vector32.y, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x - 1.0, vector32.y - 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x + 1.0, vector32.y - 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x - 1.0, vector32.y + 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x + 1.0, vector32.y + 1.0, vector32.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        }
                        if (player.m_6350_().equals((Object)Direction.EAST) || player.m_6350_().equals((Object)Direction.WEST)) {
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y, vector32.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y, vector32.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y - 1.0, vector32.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y - 1.0, vector32.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y + 1.0, vector32.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                            this.shootLaser(itemStack, level, new Vector3(vector32.x, vector32.y + 1.0, vector32.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        }
                        IC2.network.get(true).initiateItemEvent(player, itemStack, 7, true);
                    }
                }
            } else if (compoundTag.m_128451_("laserSetting") == 7) {
                if (this.consumeEnergy(itemStack, 3000.0, (LivingEntity)player)) {
                    vector3.x = 0.0;
                    vector3.z = 0.0;
                    vector3.normalize();
                    Vector3 vector33 = Util.getEyePosition((Entity)player);
                    vector33.x = (double)blockPos.m_123341_() + 0.5;
                    vector33.z = (double)blockPos.m_123343_() + 0.5;
                    vector33 = ItemToolMiningLaser.adjustStartPos(vector33, vector3);
                    if (this.shootLaser(itemStack, level, vector33, vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false)) {
                        this.shootLaser(itemStack, level, new Vector3(vector33.x + 1.0, vector33.y, vector33.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x - 1.0, vector33.y, vector33.z), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x + 1.0, vector33.y, vector33.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x - 1.0, vector33.y, vector33.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x + 1.0, vector33.y, vector33.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x - 1.0, vector33.y, vector33.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x, vector33.y, vector33.z + 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        this.shootLaser(itemStack, level, new Vector3(vector33.x, vector33.y, vector33.z - 1.0), vector3, (LivingEntity)player, Float.POSITIVE_INFINITY, 5.0f, Integer.MAX_VALUE, false, false);
                        IC2.network.get(true).initiateItemEvent(player, itemStack, 7, true);
                    }
                }
            } else {
                IC2.sideProxy.messagePlayer(player, "Mining laser aiming angle too steep", new Object[0]);
            }
        }
        return InteractionResult.FAIL;
    }

    private static Vector3 adjustStartPos(Vector3 vector3, Vector3 vector32) {
        return vector3.addScaled(vector32, 0.2);
    }

    private void setLaserVelocity(Projectile projectile, Entity entity, Vector3 vector3, float f, float f2) {
        projectile.m_6686_(vector3.x, vector3.y, vector3.z, f, f2);
        Vec3 vec3 = entity.m_20184_();
        projectile.m_20256_(projectile.m_20184_().m_82520_(vec3.f_82479_, entity.m_20096_() ? 0.0 : vec3.f_82480_, vec3.f_82481_));
    }

    public boolean shootLaser(ItemStack itemStack, Level level, LivingEntity livingEntity, float f, float f2, int n, boolean bl, boolean bl2) {
        Vector3 vector3 = Util.getLook((Entity)livingEntity);
        return this.shootLaser(itemStack, level, vector3, livingEntity, f, f2, n, bl, bl2);
    }

    public boolean shootLaser(ItemStack itemStack, Level level, Vector3 vector3, LivingEntity livingEntity, float f, float f2, int n, boolean bl, boolean bl2) {
        Vector3 vector32 = ItemToolMiningLaser.adjustStartPos(Util.getEyePosition((Entity)livingEntity), vector3);
        return this.shootLaser(itemStack, level, vector32, vector3, livingEntity, f, f2, n, bl, bl2);
    }

    public boolean shootLaser(ItemStack itemStack, Level level, Vector3 vector3, Vector3 vector32, LivingEntity livingEntity, float f, float f2, int n, boolean bl, boolean bl2) {
        LaserBulletEntity laserBulletEntity = new LaserBulletEntity(level, vector3, vector32, livingEntity, f, f2, n, bl);
        laserBulletEntity.init(livingEntity, f, f2, n, bl, bl2, true);
        this.setLaserVelocity((Projectile)laserBulletEntity, (Entity)livingEntity, vector32, 3.0f, 1.0f);
        level.m_7967_((Entity)laserBulletEntity);
        return true;
    }

    public Rarity m_41460_(ItemStack itemStack) {
        return Rarity.UNCOMMON;
    }

    private void playShotSound(Player player, SoundEvent soundEvent) {
        player.m_6330_(soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public void onNetworkEvent(ItemStack itemStack, Player player, int n) {
        switch (n) {
            case 0: 
            case 3: 
            case 4: {
                this.playShotSound(player, Ic2SoundEvents.ITEM_LASER_SHOOT);
                break;
            }
            case 5: 
            case 7: {
                this.playShotSound(player, Ic2SoundEvents.ITEM_LASER_SCATTER);
                break;
            }
            case 1: {
                this.playShotSound(player, Ic2SoundEvents.ITEM_LASER_LOW_FOCUS);
                break;
            }
            case 2: {
                this.playShotSound(player, Ic2SoundEvents.ITEM_LASER_LONG_RANGE);
                break;
            }
            case 6: {
                this.playShotSound(player, Ic2SoundEvents.ITEM_LASER_EXPLOSIVE);
            }
        }
    }

    private static String getModeString(int n) {
        return switch (n) {
            case 0 -> "ic2.tooltip.mode.mining";
            case 1 -> "ic2.tooltip.mode.lowFocus";
            case 2 -> "ic2.tooltip.mode.longRange";
            case 3 -> "ic2.tooltip.mode.horizontal";
            case 4 -> "ic2.tooltip.mode.superHeat";
            case 5 -> "ic2.tooltip.mode.scatter";
            case 6 -> "ic2.tooltip.mode.explosive";
            case 7 -> "ic2.tooltip.mode.3x3";
            default -> throw new NoSuchElementException("No such mode: " + n);
        };
    }
}

