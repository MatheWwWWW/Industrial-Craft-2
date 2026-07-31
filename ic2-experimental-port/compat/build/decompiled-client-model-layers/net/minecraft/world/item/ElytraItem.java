/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ElytraItem
extends Item
implements Wearable {
    public ElytraItem(Item.Properties p_41132_) {
        super(p_41132_);
        DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
    }

    public static boolean m_41140_(ItemStack p_41141_) {
        return p_41141_.m_41773_() < p_41141_.m_41776_() - 1;
    }

    @Override
    public boolean m_6832_(ItemStack p_41134_, ItemStack p_41135_) {
        return p_41135_.m_150930_(Items.f_42714_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41137_, Player p_41138_, InteractionHand p_41139_) {
        ItemStack $$3 = p_41138_.m_21120_(p_41139_);
        EquipmentSlot $$4 = Mob.m_147233_($$3);
        ItemStack $$5 = p_41138_.m_6844_($$4);
        if ($$5.m_41619_()) {
            p_41138_.m_8061_($$4, $$3.m_41777_());
            if (!p_41137_.m_5776_()) {
                p_41138_.m_36246_(Stats.f_12982_.m_12902_(this));
            }
            $$3.m_41764_(0);
            return InteractionResultHolder.m_19092_($$3, p_41137_.m_5776_());
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    @Override
    @Nullable
    public SoundEvent m_142602_() {
        return SoundEvents.f_11674_;
    }
}

