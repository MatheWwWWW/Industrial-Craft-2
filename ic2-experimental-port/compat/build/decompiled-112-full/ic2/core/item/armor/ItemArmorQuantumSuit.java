/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.MobEffects
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.EnumRarity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ISpecialArmor$ArmorProperties
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingFallEvent
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IHazmatLike;
import ic2.api.item.IItemHudProvider;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.init.Localization;
import ic2.core.init.MainConfig;
import ic2.core.item.ItemTinCan;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorQuantumSuit
extends ItemArmorElectric
implements IJetpack,
IHazmatLike,
IItemHudProvider {
    private static final int defaultColor = -1;
    protected static final Map<Potion, Integer> potionRemovalCost = new IdentityHashMap<Potion, Integer>();
    private float jumpCharge;

    public ItemArmorQuantumSuit(ItemName name, EntityEquipmentSlot armorType) {
        super(name, "quantum", armorType, 1.0E7, 12000.0, 4);
        if (armorType == EntityEquipmentSlot.FEET) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
        potionRemovalCost.put(MobEffects.field_76436_u, 10000);
        potionRemovalCost.put(IC2Potion.radiation, 10000);
        potionRemovalCost.put(MobEffects.field_82731_v, 25000);
    }

    @Override
    protected boolean hasOverlayTexture() {
        return true;
    }

    public boolean func_82816_b_(ItemStack stack) {
        return this.func_82814_b(stack) != -1;
    }

    public void func_82815_c(ItemStack stack) {
        NBTTagCompound nbt = this.getDisplayNbt(stack, false);
        if (nbt == null || !nbt.func_150297_b("color", 3)) {
            return;
        }
        nbt.func_82580_o("color");
        if (nbt.func_82582_d()) {
            stack.func_77978_p().func_82580_o("display");
        }
    }

    public int func_82814_b(ItemStack stack) {
        NBTTagCompound nbt = this.getDisplayNbt(stack, false);
        if (nbt == null || !nbt.func_150297_b("color", 3)) {
            return -1;
        }
        return nbt.func_74762_e("color");
    }

    public void func_82813_b(ItemStack stack, int color) {
        NBTTagCompound nbt = this.getDisplayNbt(stack, true);
        nbt.func_74768_a("color", color);
    }

    private NBTTagCompound getDisplayNbt(ItemStack stack, boolean create) {
        NBTTagCompound ret;
        NBTTagCompound nbt = stack.func_77978_p();
        if (nbt == null) {
            if (!create) {
                return null;
            }
            nbt = new NBTTagCompound();
            stack.func_77982_d(nbt);
        }
        if (!nbt.func_150297_b("display", 10)) {
            if (!create) {
                return null;
            }
            ret = new NBTTagCompound();
            nbt.func_74782_a("display", (NBTBase)ret);
        } else {
            ret = nbt.func_74775_l("display");
        }
        return ret;
    }

    @Override
    public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) > 0.0;
    }

    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
        int energyPerDamage = this.getEnergyPerDamage();
        int damageLimit = Integer.MAX_VALUE;
        if (energyPerDamage > 0) {
            damageLimit = (int)Math.min((double)damageLimit, 25.0 * ElectricItem.manager.getCharge(armor) / (double)energyPerDamage);
        }
        if (source == DamageSource.field_76379_h) {
            if (this.field_77881_a == EntityEquipmentSlot.FEET) {
                return new ISpecialArmor.ArmorProperties(10, 1.0, damageLimit);
            }
            if (this.field_77881_a == EntityEquipmentSlot.LEGS) {
                return new ISpecialArmor.ArmorProperties(9, 0.8, damageLimit);
            }
        }
        double absorptionRatio = this.getBaseAbsorptionRatio() * this.getDamageAbsorptionRatio();
        return new ISpecialArmor.ArmorProperties(8, absorptionRatio, damageLimit);
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onEntityLivingFallEvent(LivingFallEvent event) {
        EntityLivingBase entity;
        ItemStack armor;
        if (IC2.platform.isSimulating() && event.getEntity() instanceof EntityLivingBase && (armor = (entity = (EntityLivingBase)event.getEntity()).func_184582_a(EntityEquipmentSlot.FEET)) != null && armor.func_77973_b() == this) {
            int fallDamage = Math.max((int)event.getDistance() - 10, 0);
            double energyCost = this.getEnergyPerDamage() * fallDamage;
            if (energyCost <= ElectricItem.manager.getCharge(armor)) {
                ElectricItem.manager.discharge(armor, energyCost, Integer.MAX_VALUE, true, false, false);
                event.setCanceled(true);
            }
        }
    }

    @Override
    public double getDamageAbsorptionRatio() {
        if (this.field_77881_a == EntityEquipmentSlot.CHEST) {
            return 1.2;
        }
        return 1.0;
    }

    @Override
    public int getEnergyPerDamage() {
        return 20000;
    }

    @SideOnly(value=Side.CLIENT)
    public EnumRarity func_77613_e(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        byte toggleTimer = nbtData.func_74771_c("toggleTimer");
        boolean ret = false;
        switch (this.field_77881_a) {
            case HEAD: {
                IC2.platform.profilerStartSection("QuantumHelmet");
                int air = player.func_70086_ai();
                if (ElectricItem.manager.canUse(stack, 1000.0) && air < 100) {
                    player.func_70050_g(air + 200);
                    ElectricItem.manager.use(stack, 1000.0, null);
                    ret = true;
                } else if (air <= 0) {
                    IC2.achievements.issueAchievement(player, "starveWithQHelmet");
                }
                if (ElectricItem.manager.canUse(stack, 1000.0) && player.func_71024_bL().func_75121_c()) {
                    int slot = -1;
                    for (int i = 0; i < player.field_71071_by.field_70462_a.size(); ++i) {
                        ItemStack playerStack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
                        if (StackUtil.isEmpty(playerStack) || playerStack.func_77973_b() != ItemName.filled_tin_can.getInstance()) continue;
                        slot = i;
                        break;
                    }
                    if (slot > -1) {
                        ItemStack playerStack = (ItemStack)player.field_71071_by.field_70462_a.get(slot);
                        ItemTinCan can = (ItemTinCan)playerStack.func_77973_b();
                        ActionResult<ItemStack> result = can.onEaten(player, playerStack);
                        if (StackUtil.isEmpty(playerStack = (ItemStack)result.func_188398_b())) {
                            player.field_71071_by.field_70462_a.set(slot, (Object)StackUtil.emptyStack);
                        }
                        if (result.func_188397_a() == EnumActionResult.SUCCESS) {
                            ElectricItem.manager.use(stack, 1000.0, null);
                        }
                        ret = true;
                    }
                } else if (player.func_71024_bL().func_75116_a() <= 0) {
                    IC2.achievements.issueAchievement(player, "starveWithQHelmet");
                }
                for (PotionEffect effect : new LinkedList(player.func_70651_bq())) {
                    Potion potion = effect.func_188419_a();
                    Integer cost = potionRemovalCost.get(potion);
                    if (cost == null || !ElectricItem.manager.canUse(stack, (cost = Integer.valueOf(cost * (effect.func_76458_c() + 1))).intValue())) continue;
                    ElectricItem.manager.use(stack, cost.intValue(), null);
                    IC2.platform.removePotion((EntityLivingBase)player, potion);
                }
                boolean Nightvision = nbtData.func_74767_n("Nightvision");
                short hubmode = nbtData.func_74765_d("HudMode");
                if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    boolean bl = Nightvision = !Nightvision;
                    if (IC2.platform.isSimulating()) {
                        nbtData.func_74757_a("Nightvision", Nightvision);
                        if (Nightvision) {
                            IC2.platform.messagePlayer(player, "Nightvision enabled.", new Object[0]);
                        } else {
                            IC2.platform.messagePlayer(player, "Nightvision disabled.", new Object[0]);
                        }
                    }
                }
                if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    hubmode = hubmode == HudMode.getMaxMode() ? (short)0 : (short)(hubmode + 1);
                    if (IC2.platform.isSimulating()) {
                        nbtData.func_74777_a("HudMode", hubmode);
                        IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
                    }
                }
                if (IC2.platform.isSimulating() && toggleTimer > 0) {
                    toggleTimer = (byte)(toggleTimer - 1);
                    nbtData.func_74774_a("toggleTimer", toggleTimer);
                }
                if (Nightvision && IC2.platform.isSimulating() && ElectricItem.manager.use(stack, 1.0, (EntityLivingBase)player)) {
                    BlockPos pos = new BlockPos((int)Math.floor(player.field_70165_t), (int)Math.floor(player.field_70163_u), (int)Math.floor(player.field_70161_v));
                    int skylight = player.func_130014_f_().func_175671_l(pos);
                    if (skylight > 8) {
                        IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76439_r);
                        player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 100, 0, true, true));
                    } else {
                        IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76440_q);
                        player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 300, 0, true, true));
                    }
                    ret = true;
                }
                IC2.platform.profilerEndSection();
                break;
            }
            case CHEST: {
                IC2.platform.profilerStartSection("QuantumBodyarmor");
                player.func_70066_B();
                IC2.platform.profilerEndSection();
                break;
            }
            case LEGS: {
                IC2.platform.profilerStartSection("QuantumLeggings");
                boolean enableQuantumSpeedOnSprint = IC2.platform.isRendering() ? ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint") : true;
                if (ElectricItem.manager.canUse(stack, 1000.0) && (player.field_70122_E || player.func_70090_H()) && IC2.keyboard.isForwardKeyDown(player) && (enableQuantumSpeedOnSprint && player.func_70051_ag() || !enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player))) {
                    byte speedTicker = nbtData.func_74771_c("speedTicker");
                    if ((speedTicker = (byte)(speedTicker + 1)) >= 10) {
                        speedTicker = 0;
                        ElectricItem.manager.use(stack, 1000.0, null);
                        ret = true;
                    }
                    nbtData.func_74774_a("speedTicker", speedTicker);
                    float speed = 0.22f;
                    if (player.func_70090_H()) {
                        speed = 0.1f;
                        if (IC2.keyboard.isJumpKeyDown(player)) {
                            player.field_70181_x += (double)0.1f;
                        }
                    }
                    if (speed > 0.0f) {
                        player.func_191958_b(0.0f, 0.0f, 1.0f, speed);
                    }
                }
                IC2.platform.profilerEndSection();
                break;
            }
            case FEET: {
                IC2.platform.profilerStartSection("QuantumBoots");
                if (IC2.platform.isSimulating()) {
                    boolean wasOnGround;
                    boolean bl = wasOnGround = nbtData.func_74764_b("wasOnGround") ? nbtData.func_74767_n("wasOnGround") : true;
                    if (wasOnGround && !player.field_70122_E && IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
                        ElectricItem.manager.use(stack, 4000.0, null);
                        ret = true;
                    }
                    if (player.field_70122_E != wasOnGround) {
                        nbtData.func_74757_a("wasOnGround", player.field_70122_E);
                    }
                } else {
                    if (ElectricItem.manager.canUse(stack, 4000.0) && player.field_70122_E) {
                        this.jumpCharge = 1.0f;
                    }
                    if (player.field_70181_x >= 0.0 && this.jumpCharge > 0.0f && !player.func_70090_H()) {
                        if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
                            if (this.jumpCharge == 1.0f) {
                                player.field_70159_w *= 3.5;
                                player.field_70179_y *= 3.5;
                            }
                            player.field_70181_x += (double)(this.jumpCharge * 0.3f);
                            this.jumpCharge = (float)((double)this.jumpCharge * 0.75);
                        } else if (this.jumpCharge < 1.0f) {
                            this.jumpCharge = 0.0f;
                        }
                    }
                }
                IC2.platform.profilerEndSection();
                break;
            }
        }
        if (ret) {
            player.field_71069_bz.func_75142_b();
        }
    }

    @Override
    public int func_77619_b() {
        return 0;
    }

    @Override
    public boolean drainEnergy(ItemStack pack, int amount) {
        return ElectricItem.manager.discharge(pack, amount + 6, Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 1.0f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    @Override
    public double getChargeLevel(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
    }

    @Override
    public boolean isJetpackActive(ItemStack stack) {
        return true;
    }

    @Override
    public float getHoverMultiplier(ItemStack stack, boolean upwards) {
        return 0.1f;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack stack) {
        return 0.9f;
    }

    @Override
    public boolean doesProvideHUD(ItemStack stack) {
        return this.field_77881_a == EntityEquipmentSlot.HEAD && ElectricItem.manager.getCharge(stack) > 0.0;
    }

    @Override
    public HudMode getHudMode(ItemStack stack) {
        return HudMode.getFromID(StackUtil.getOrCreateNbtData(stack).func_74765_d("HudMode"));
    }
}

