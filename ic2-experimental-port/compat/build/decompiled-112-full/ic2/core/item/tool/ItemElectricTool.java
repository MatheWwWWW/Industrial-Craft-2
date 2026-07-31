/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.init.Localization;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.IPseudoDamageItem;
import ic2.core.item.ItemToolIC2;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.IToolClass;
import ic2.core.ref.ItemName;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ItemElectricTool
extends ItemToolIC2
implements IPseudoDamageItem,
IElectricItem,
IItemHudInfo {
    public double operationEnergyCost;
    public int maxCharge;
    public int transferLimit;
    public int tier;
    protected AudioSource audioSource;
    protected boolean wasEquipped;

    protected ItemElectricTool(ItemName name, int operationEnergyCost) {
        this(name, operationEnergyCost, HarvestLevel.Iron, Collections.emptySet());
    }

    protected ItemElectricTool(ItemName name, int operationEnergyCost, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses) {
        this(name, 2.0f, -3.0f, operationEnergyCost, harvestLevel, toolClasses, new HashSet<Block>());
    }

    private ItemElectricTool(ItemName name, float damage, float speed, int operationEnergyCost, HarvestLevel harvestLevel, Set<? extends IToolClass> toolClasses, Set<Block> mineableBlocks) {
        super(name, damage, speed, harvestLevel, toolClasses, mineableBlocks);
        this.operationEnergyCost = operationEnergyCost;
        this.func_77656_e(27);
        this.setNoRepair();
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
        ElectricItem.manager.use(StackUtil.get(player, hand), 0.0, (EntityLivingBase)player);
        return super.func_180614_a(player, world, pos, hand, side, xOffset, yOffset, zOffset);
    }

    public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand hand) {
        ElectricItem.manager.use(StackUtil.get(player, hand), 0.0, (EntityLivingBase)player);
        return super.func_77659_a(world, player, hand);
    }

    @Override
    public float func_150893_a(ItemStack stack, IBlockState state) {
        if (!ElectricItem.manager.canUse(stack, this.operationEnergyCost)) {
            return 1.0f;
        }
        return super.func_150893_a(stack, state);
    }

    public boolean func_77644_a(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
        return true;
    }

    public int func_77619_b() {
        return 0;
    }

    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }

    public boolean func_179218_a(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase user) {
        if (state.func_185887_b(world, pos) != 0.0f) {
            if (user != null) {
                ElectricItem.manager.use(stack, this.operationEnergyCost, user);
            } else {
                ElectricItem.manager.discharge(stack, this.operationEnergyCost, this.tier, true, false, false);
            }
        }
        return true;
    }

    public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return false;
    }

    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
        return false;
    }

    public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.func_194125_a(tab)) {
            return;
        }
        ElectricItemManager.addChargeVariants((Item)this, subItems);
    }

    @Override
    public List<String> getHudInfo(ItemStack stack, boolean advanced) {
        LinkedList<String> info = new LinkedList<String>();
        info.add(ElectricItem.manager.getToolTip(stack));
        info.add(Localization.translate("ic2.item.tooltip.PowerTier", this.tier));
        return info;
    }

    protected ItemStack getItemStack(double charge) {
        ItemStack ret = new ItemStack((Item)this);
        ElectricItem.manager.charge(ret, charge, Integer.MAX_VALUE, true, false);
        return ret;
    }

    public void func_77663_a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        boolean isEquipped;
        boolean bl = isEquipped = flag && entity instanceof EntityLivingBase;
        if (IC2.platform.isRendering()) {
            if (isEquipped && !this.wasEquipped) {
                String initSound;
                String sound;
                if (this.audioSource == null && (sound = this.getIdleSound((EntityLivingBase)entity, itemstack)) != null) {
                    this.audioSource = IC2.audioManager.createSource(entity, PositionSpec.Hand, sound, true, false, IC2.audioManager.getDefaultVolume());
                }
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
                if ((initSound = this.getStartSound((EntityLivingBase)entity, itemstack)) != null) {
                    IC2.audioManager.playOnce(entity, PositionSpec.Hand, initSound, true, IC2.audioManager.getDefaultVolume());
                }
            } else if (!isEquipped && this.audioSource != null) {
                EntityLivingBase theEntity;
                ItemStack stack;
                if (entity instanceof EntityLivingBase && ((stack = (theEntity = (EntityLivingBase)entity).func_184582_a(EntityEquipmentSlot.MAINHAND)) == null || stack.func_77973_b() != this || stack == itemstack)) {
                    this.removeAudioSource();
                    String sound = this.getStopSound(theEntity, itemstack);
                    if (sound != null) {
                        IC2.audioManager.playOnce(entity, PositionSpec.Hand, sound, true, IC2.audioManager.getDefaultVolume());
                    }
                }
            } else if (this.audioSource != null) {
                this.audioSource.updatePosition();
            }
            this.wasEquipped = isEquipped;
        }
    }

    protected void removeAudioSource() {
        if (this.audioSource != null) {
            this.audioSource.stop();
            this.audioSource.remove();
            this.audioSource = null;
        }
    }

    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        this.removeAudioSource();
        return true;
    }

    protected String getIdleSound(EntityLivingBase player, ItemStack stack) {
        return null;
    }

    protected String getStopSound(EntityLivingBase player, ItemStack stack) {
        return null;
    }

    protected String getStartSound(EntityLivingBase player, ItemStack stack) {
        return null;
    }

    public void setDamage(ItemStack stack, int damage) {
        int prev = this.getDamage(stack);
        if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
            IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", damage - prev);
        }
    }

    @Override
    public void setStackDamage(ItemStack stack, int damage) {
        super.setDamage(stack, damage);
    }
}

