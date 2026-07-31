/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BundleItem
extends Item {
    private static final String f_150721_ = "Items";
    public static final int f_150720_ = 64;
    private static final int f_150722_ = 4;
    private static final int f_150723_ = Mth.m_14159_(0.4f, 0.4f, 1.0f);

    public BundleItem(Item.Properties p_150726_) {
        super(p_150726_);
    }

    public static float m_150766_(ItemStack p_150767_) {
        return (float)BundleItem.m_150778_(p_150767_) / 64.0f;
    }

    @Override
    public boolean m_142207_(ItemStack p_150733_, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
        if (p_150735_ != ClickAction.SECONDARY) {
            return false;
        }
        ItemStack $$4 = p_150734_.m_7993_();
        if ($$4.m_41619_()) {
            this.m_186342_(p_150736_);
            BundleItem.m_150780_(p_150733_).ifPresent(p_150740_ -> BundleItem.m_150763_(p_150733_, p_150734_.m_150659_((ItemStack)p_150740_)));
        } else if ($$4.m_41720_().m_142095_()) {
            int $$5 = (64 - BundleItem.m_150778_(p_150733_)) / BundleItem.m_150776_($$4);
            int $$6 = BundleItem.m_150763_(p_150733_, p_150734_.m_150647_($$4.m_41613_(), $$5, p_150736_));
            if ($$6 > 0) {
                this.m_186351_(p_150736_);
            }
        }
        return true;
    }

    @Override
    public boolean m_142305_(ItemStack p_150742_, ItemStack p_150743_, Slot p_150744_, ClickAction p_150745_, Player p_150746_, SlotAccess p_150747_) {
        if (p_150745_ != ClickAction.SECONDARY || !p_150744_.m_150651_(p_150746_)) {
            return false;
        }
        if (p_150743_.m_41619_()) {
            BundleItem.m_150780_(p_150742_).ifPresent(p_186347_ -> {
                this.m_186342_(p_150746_);
                p_150747_.m_142104_((ItemStack)p_186347_);
            });
        } else {
            int $$6 = BundleItem.m_150763_(p_150742_, p_150743_);
            if ($$6 > 0) {
                this.m_186351_(p_150746_);
                p_150743_.m_41774_($$6);
            }
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_150760_, Player p_150761_, InteractionHand p_150762_) {
        ItemStack $$3 = p_150761_.m_21120_(p_150762_);
        if (BundleItem.m_150729_($$3, p_150761_)) {
            this.m_186353_(p_150761_);
            p_150761_.m_36246_(Stats.f_12982_.m_12902_(this));
            return InteractionResultHolder.m_19092_($$3, p_150760_.m_5776_());
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    @Override
    public boolean m_142522_(ItemStack p_150769_) {
        return BundleItem.m_150778_(p_150769_) > 0;
    }

    @Override
    public int m_142158_(ItemStack p_150771_) {
        return Math.min(1 + 12 * BundleItem.m_150778_(p_150771_) / 64, 13);
    }

    @Override
    public int m_142159_(ItemStack p_150773_) {
        return f_150723_;
    }

    private static int m_150763_(ItemStack p_150764_, ItemStack p_150765_) {
        if (p_150765_.m_41619_() || !p_150765_.m_41720_().m_142095_()) {
            return 0;
        }
        CompoundTag $$2 = p_150764_.m_41784_();
        if (!$$2.m_128441_(f_150721_)) {
            $$2.m_128365_(f_150721_, new ListTag());
        }
        int $$3 = BundleItem.m_150778_(p_150764_);
        int $$4 = BundleItem.m_150776_(p_150765_);
        int $$5 = Math.min(p_150765_.m_41613_(), (64 - $$3) / $$4);
        if ($$5 == 0) {
            return 0;
        }
        ListTag $$6 = $$2.m_128437_(f_150721_, 10);
        Optional<CompoundTag> $$7 = BundleItem.m_150756_(p_150765_, $$6);
        if ($$7.isPresent()) {
            CompoundTag $$8 = $$7.get();
            ItemStack $$9 = ItemStack.m_41712_($$8);
            $$9.m_41769_($$5);
            $$9.m_41739_($$8);
            $$6.remove($$8);
            $$6.add(0, $$8);
        } else {
            ItemStack $$10 = p_150765_.m_41777_();
            $$10.m_41764_($$5);
            CompoundTag $$11 = new CompoundTag();
            $$10.m_41739_($$11);
            $$6.add(0, $$11);
        }
        return $$5;
    }

    private static Optional<CompoundTag> m_150756_(ItemStack p_150757_, ListTag p_150758_) {
        if (p_150757_.m_150930_(Items.f_151058_)) {
            return Optional.empty();
        }
        return p_150758_.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter(p_186350_ -> ItemStack.m_150942_(ItemStack.m_41712_(p_186350_), p_150757_)).findFirst();
    }

    private static int m_150776_(ItemStack p_150777_) {
        CompoundTag $$1;
        if (p_150777_.m_150930_(Items.f_151058_)) {
            return 4 + BundleItem.m_150778_(p_150777_);
        }
        if ((p_150777_.m_150930_(Items.f_42786_) || p_150777_.m_150930_(Items.f_42785_)) && p_150777_.m_41782_() && ($$1 = BlockItem.m_186336_(p_150777_)) != null && !$$1.m_128437_("Bees", 10).isEmpty()) {
            return 64;
        }
        return 64 / p_150777_.m_41741_();
    }

    private static int m_150778_(ItemStack p_150779_) {
        return BundleItem.m_150782_(p_150779_).mapToInt(p_186356_ -> BundleItem.m_150776_(p_186356_) * p_186356_.m_41613_()).sum();
    }

    private static Optional<ItemStack> m_150780_(ItemStack p_150781_) {
        CompoundTag $$1 = p_150781_.m_41784_();
        if (!$$1.m_128441_(f_150721_)) {
            return Optional.empty();
        }
        ListTag $$2 = $$1.m_128437_(f_150721_, 10);
        if ($$2.isEmpty()) {
            return Optional.empty();
        }
        boolean $$3 = false;
        CompoundTag $$4 = $$2.m_128728_(0);
        ItemStack $$5 = ItemStack.m_41712_($$4);
        $$2.remove(0);
        if ($$2.isEmpty()) {
            p_150781_.m_41749_(f_150721_);
        }
        return Optional.of($$5);
    }

    private static boolean m_150729_(ItemStack p_150730_, Player p_150731_) {
        CompoundTag $$2 = p_150730_.m_41784_();
        if (!$$2.m_128441_(f_150721_)) {
            return false;
        }
        if (p_150731_ instanceof ServerPlayer) {
            ListTag $$3 = $$2.m_128437_(f_150721_, 10);
            for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                CompoundTag $$5 = $$3.m_128728_($$4);
                ItemStack $$6 = ItemStack.m_41712_($$5);
                p_150731_.m_36176_($$6, true);
            }
        }
        p_150730_.m_41749_(f_150721_);
        return true;
    }

    private static Stream<ItemStack> m_150782_(ItemStack p_150783_) {
        CompoundTag $$1 = p_150783_.m_41783_();
        if ($$1 == null) {
            return Stream.empty();
        }
        ListTag $$2 = $$1.m_128437_(f_150721_, 10);
        return $$2.stream().map(CompoundTag.class::cast).map(ItemStack::m_41712_);
    }

    @Override
    public Optional<TooltipComponent> m_142422_(ItemStack p_150775_) {
        NonNullList<ItemStack> $$1 = NonNullList.m_122779_();
        BundleItem.m_150782_(p_150775_).forEach($$1::add);
        return Optional.of(new BundleTooltip($$1, BundleItem.m_150778_(p_150775_)));
    }

    @Override
    public void m_7373_(ItemStack p_150749_, Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {
        p_150751_.add(Component.m_237110_("item.minecraft.bundle.fullness", BundleItem.m_150778_(p_150749_), 64).m_130940_(ChatFormatting.GRAY));
    }

    @Override
    public void m_142023_(ItemEntity p_150728_) {
        ItemUtils.m_150952_(p_150728_, BundleItem.m_150782_(p_150728_.m_32055_()));
    }

    private void m_186342_(Entity p_186343_) {
        p_186343_.m_5496_(SoundEvents.f_184216_, 0.8f, 0.8f + p_186343_.m_9236_().m_213780_().m_188501_() * 0.4f);
    }

    private void m_186351_(Entity p_186352_) {
        p_186352_.m_5496_(SoundEvents.f_184215_, 0.8f, 0.8f + p_186352_.m_9236_().m_213780_().m_188501_() * 0.4f);
    }

    private void m_186353_(Entity p_186354_) {
        p_186354_.m_5496_(SoundEvents.f_184214_, 0.8f, 0.8f + p_186354_.m_9236_().m_213780_().m_188501_() * 0.4f);
    }
}

