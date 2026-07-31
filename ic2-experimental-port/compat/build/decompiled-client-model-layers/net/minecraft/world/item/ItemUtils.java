/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.stream.Stream;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUtils {
    public static InteractionResultHolder<ItemStack> m_150959_(Level p_150960_, Player p_150961_, InteractionHand p_150962_) {
        p_150961_.m_6672_(p_150962_);
        return InteractionResultHolder.m_19096_(p_150961_.m_21120_(p_150962_));
    }

    public static ItemStack m_41817_(ItemStack p_41818_, Player p_41819_, ItemStack p_41820_, boolean p_41821_) {
        boolean $$4 = p_41819_.m_150110_().f_35937_;
        if (p_41821_ && $$4) {
            if (!p_41819_.m_150109_().m_36063_(p_41820_)) {
                p_41819_.m_150109_().m_36054_(p_41820_);
            }
            return p_41818_;
        }
        if (!$$4) {
            p_41818_.m_41774_(1);
        }
        if (p_41818_.m_41619_()) {
            return p_41820_;
        }
        if (!p_41819_.m_150109_().m_36054_(p_41820_)) {
            p_41819_.m_36176_(p_41820_, false);
        }
        return p_41818_;
    }

    public static ItemStack m_41813_(ItemStack p_41814_, Player p_41815_, ItemStack p_41816_) {
        return ItemUtils.m_41817_(p_41814_, p_41815_, p_41816_, true);
    }

    public static void m_150952_(ItemEntity p_150953_, Stream<ItemStack> p_150954_) {
        Level $$2 = p_150953_.f_19853_;
        if ($$2.f_46443_) {
            return;
        }
        p_150954_.forEach(p_150958_ -> $$2.m_7967_(new ItemEntity($$2, p_150953_.m_20185_(), p_150953_.m_20186_(), p_150953_.m_20189_(), (ItemStack)p_150958_)));
    }
}

