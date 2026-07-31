/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FoodOnAStickItem<T extends Entity>
extends Item {
    private final EntityType<T> f_41304_;
    private final int f_41305_;

    public FoodOnAStickItem(Item.Properties p_41307_, EntityType<T> p_41308_, int p_41309_) {
        super(p_41307_);
        this.f_41304_ = p_41308_;
        this.f_41305_ = p_41309_;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_41314_, Player p_41315_, InteractionHand p_41316_) {
        ItemSteerable $$5;
        ItemStack $$3 = p_41315_.m_21120_(p_41316_);
        if (p_41314_.f_46443_) {
            return InteractionResultHolder.m_19098_($$3);
        }
        Entity $$4 = p_41315_.m_20202_();
        if (p_41315_.m_20159_() && $$4 instanceof ItemSteerable && $$4.m_6095_() == this.f_41304_ && ($$5 = (ItemSteerable)((Object)$$4)).m_6746_()) {
            $$3.m_41622_(this.f_41305_, p_41315_, p_41312_ -> p_41312_.m_21190_(p_41316_));
            if ($$3.m_41619_()) {
                ItemStack $$6 = new ItemStack(Items.f_42523_);
                $$6.m_41751_($$3.m_41783_());
                return InteractionResultHolder.m_19090_($$6);
            }
            return InteractionResultHolder.m_19090_($$3);
        }
        p_41315_.m_36246_(Stats.f_12982_.m_12902_(this));
        return InteractionResultHolder.m_19098_($$3);
    }
}

