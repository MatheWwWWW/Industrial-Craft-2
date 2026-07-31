/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

public class EnchantedBookItem
extends Item {
    public static final String f_150830_ = "StoredEnchantments";

    public EnchantedBookItem(Item.Properties p_41149_) {
        super(p_41149_);
    }

    @Override
    public boolean m_5812_(ItemStack p_41166_) {
        return true;
    }

    @Override
    public boolean m_8120_(ItemStack p_41168_) {
        return false;
    }

    public static ListTag m_41163_(ItemStack p_41164_) {
        CompoundTag $$1 = p_41164_.m_41783_();
        if ($$1 != null) {
            return $$1.m_128437_(f_150830_, 10);
        }
        return new ListTag();
    }

    @Override
    public void m_7373_(ItemStack p_41157_, @Nullable Level p_41158_, List<Component> p_41159_, TooltipFlag p_41160_) {
        super.m_7373_(p_41157_, p_41158_, p_41159_, p_41160_);
        ItemStack.m_41709_(p_41159_, EnchantedBookItem.m_41163_(p_41157_));
    }

    public static void m_41153_(ItemStack p_41154_, EnchantmentInstance p_41155_) {
        ListTag $$2 = EnchantedBookItem.m_41163_(p_41154_);
        boolean $$3 = true;
        ResourceLocation $$4 = EnchantmentHelper.m_182432_(p_41155_.f_44947_);
        for (int $$5 = 0; $$5 < $$2.size(); ++$$5) {
            CompoundTag $$6 = $$2.m_128728_($$5);
            ResourceLocation $$7 = EnchantmentHelper.m_182446_($$6);
            if ($$7 == null || !$$7.equals($$4)) continue;
            if (EnchantmentHelper.m_182438_($$6) < p_41155_.f_44948_) {
                EnchantmentHelper.m_182440_($$6, p_41155_.f_44948_);
            }
            $$3 = false;
            break;
        }
        if ($$3) {
            $$2.add(EnchantmentHelper.m_182443_($$4, p_41155_.f_44948_));
        }
        p_41154_.m_41784_().m_128365_(f_150830_, $$2);
    }

    public static ItemStack m_41161_(EnchantmentInstance p_41162_) {
        ItemStack $$1 = new ItemStack(Items.f_42690_);
        EnchantedBookItem.m_41153_($$1, p_41162_);
        return $$1;
    }

    @Override
    public void m_6787_(CreativeModeTab p_41151_, NonNullList<ItemStack> p_41152_) {
        block4: {
            block3: {
                if (p_41151_ != CreativeModeTab.f_40754_) break block3;
                for (Enchantment $$2 : Registry.f_122825_) {
                    if ($$2.f_44672_ == null) continue;
                    for (int $$3 = $$2.m_44702_(); $$3 <= $$2.m_6586_(); ++$$3) {
                        p_41152_.add(EnchantedBookItem.m_41161_(new EnchantmentInstance($$2, $$3)));
                    }
                }
                break block4;
            }
            if (p_41151_.m_40795_().length == 0) break block4;
            for (Enchantment $$4 : Registry.f_122825_) {
                if (!p_41151_.m_40776_($$4.f_44672_)) continue;
                p_41152_.add(EnchantedBookItem.m_41161_(new EnchantmentInstance($$4, $$4.m_6586_())));
            }
        }
    }
}

