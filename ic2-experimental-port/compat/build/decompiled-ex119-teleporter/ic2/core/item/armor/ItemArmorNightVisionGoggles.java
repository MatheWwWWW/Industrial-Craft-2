/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.armor.ItemArmorUtility;
import ic2.core.ref.Ic2ArmorMaterials;
import ic2.core.util.StackUtil;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemArmorNightVisionGoggles
extends ItemArmorUtility
implements IElectricItem,
IItemHudInfo {
    public ItemArmorNightVisionGoggles(Item.Properties properties) {
        super(Ic2ArmorMaterials.NIGHT_VISION_GOGGLES, properties, EquipmentSlot.HEAD);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 200000.0;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 200.0;
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(ElectricItem.manager.getToolTip(itemStack));
        return linkedList;
    }

    public void m_6883_(ItemStack itemStack, Level level, Entity entity, int n, boolean bl) {
        int n2;
        super.m_6883_(itemStack, level, entity, n, bl);
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (n != this.f_40377_.m_20749_()) {
            return;
        }
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        boolean bl2 = compoundTag.m_128471_("active");
        byte by = compoundTag.m_128445_("toggleTimer");
        if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && by == 0) {
            by = 10;
            boolean bl3 = bl2 = !bl2;
            if (IC2.sideProxy.isSimulating()) {
                compoundTag.m_128379_("active", bl2);
                if (bl2) {
                    IC2.sideProxy.messagePlayer(player, "Nightvision enabled.", new Object[0]);
                } else {
                    IC2.sideProxy.messagePlayer(player, "Nightvision disabled.", new Object[0]);
                }
            }
        }
        if (IC2.sideProxy.isSimulating() && by > 0) {
            by = (byte)(by - 1);
            compoundTag.m_128344_("toggleTimer", by);
        }
        if (bl2 && IC2.sideProxy.isSimulating() && (n2 = player.m_20193_().m_46803_(new BlockPos(player.m_20182_()))) <= 8 && ElectricItem.manager.use(itemStack, 1.0, (LivingEntity)player)) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19611_, 300, 0, true, true));
            return;
        }
        if (IC2.sideProxy.isSimulating()) {
            player.m_21195_(MobEffects.f_19611_);
        }
    }

    public void m_6787_(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        if (!this.m_220152_(creativeModeTab)) {
            return;
        }
        ElectricItemManager.addChargeVariants((Item)this, nonNullList);
    }

    public boolean m_6832_(ItemStack itemStack, ItemStack itemStack2) {
        return false;
    }
}

