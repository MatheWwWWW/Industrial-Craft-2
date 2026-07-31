/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WrittenBookItem
extends Item {
    public static final int f_151235_ = 16;
    public static final int f_151236_ = 32;
    public static final int f_151237_ = 1024;
    public static final int f_151238_ = Short.MAX_VALUE;
    public static final int f_151239_ = 100;
    public static final int f_151240_ = 2;
    public static final String f_151241_ = "title";
    public static final String f_151242_ = "filtered_title";
    public static final String f_151243_ = "author";
    public static final String f_151244_ = "pages";
    public static final String f_151245_ = "filtered_pages";
    public static final String f_151246_ = "generation";
    public static final String f_151247_ = "resolved";

    public WrittenBookItem(Item.Properties p_43455_) {
        super(p_43455_);
    }

    public static boolean m_43471_(@Nullable CompoundTag p_43472_) {
        if (!WritableBookItem.m_43452_(p_43472_)) {
            return false;
        }
        if (!p_43472_.m_128425_(f_151241_, 8)) {
            return false;
        }
        String $$1 = p_43472_.m_128461_(f_151241_);
        if ($$1.length() > 32) {
            return false;
        }
        return p_43472_.m_128425_(f_151243_, 8);
    }

    public static int m_43473_(ItemStack p_43474_) {
        return p_43474_.m_41783_().m_128451_(f_151246_);
    }

    public static int m_43477_(ItemStack p_43478_) {
        CompoundTag $$1 = p_43478_.m_41783_();
        return $$1 != null ? $$1.m_128437_(f_151244_, 8).size() : 0;
    }

    @Override
    public Component m_7626_(ItemStack p_43480_) {
        String $$2;
        CompoundTag $$1 = p_43480_.m_41783_();
        if ($$1 != null && !StringUtil.m_14408_($$2 = $$1.m_128461_(f_151241_))) {
            return Component.m_237113_($$2);
        }
        return super.m_7626_(p_43480_);
    }

    @Override
    public void m_7373_(ItemStack p_43457_, @Nullable Level p_43458_, List<Component> p_43459_, TooltipFlag p_43460_) {
        if (p_43457_.m_41782_()) {
            CompoundTag $$4 = p_43457_.m_41783_();
            String $$5 = $$4.m_128461_(f_151243_);
            if (!StringUtil.m_14408_($$5)) {
                p_43459_.add(Component.m_237110_("book.byAuthor", $$5).m_130940_(ChatFormatting.GRAY));
            }
            p_43459_.add(Component.m_237115_("book.generation." + $$4.m_128451_(f_151246_)).m_130940_(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_43466_) {
        BlockPos $$2;
        Level $$1 = p_43466_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_43466_.m_8083_());
        if ($$3.m_60713_(Blocks.f_50624_)) {
            return LecternBlock.m_153566_(p_43466_.m_43723_(), $$1, $$2, $$3, p_43466_.m_43722_()) ? InteractionResult.m_19078_($$1.f_46443_) : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43468_, Player p_43469_, InteractionHand p_43470_) {
        ItemStack $$3 = p_43469_.m_21120_(p_43470_);
        p_43469_.m_6986_($$3, p_43470_);
        p_43469_.m_36246_(Stats.f_12982_.m_12902_(this));
        return InteractionResultHolder.m_19092_($$3, p_43468_.m_5776_());
    }

    public static boolean m_43461_(ItemStack p_43462_, @Nullable CommandSourceStack p_43463_, @Nullable Player p_43464_) {
        CompoundTag $$3 = p_43462_.m_41783_();
        if ($$3 == null || $$3.m_128471_(f_151247_)) {
            return false;
        }
        $$3.m_128379_(f_151247_, true);
        if (!WrittenBookItem.m_43471_($$3)) {
            return false;
        }
        ListTag $$4 = $$3.m_128437_(f_151244_, 8);
        ListTag $$5 = new ListTag();
        for (int $$6 = 0; $$6 < $$4.size(); ++$$6) {
            String $$7 = WrittenBookItem.m_151248_(p_43463_, p_43464_, $$4.m_128778_($$6));
            if ($$7.length() > Short.MAX_VALUE) {
                return false;
            }
            $$5.add($$6, StringTag.m_129297_($$7));
        }
        if ($$3.m_128425_(f_151245_, 10)) {
            CompoundTag $$8 = $$3.m_128469_(f_151245_);
            CompoundTag $$9 = new CompoundTag();
            for (String $$10 : $$8.m_128431_()) {
                String $$11 = WrittenBookItem.m_151248_(p_43463_, p_43464_, $$8.m_128461_($$10));
                if ($$11.length() > Short.MAX_VALUE) {
                    return false;
                }
                $$9.m_128359_($$10, $$11);
            }
            $$3.m_128365_(f_151245_, $$9);
        }
        $$3.m_128365_(f_151244_, $$5);
        return true;
    }

    private static String m_151248_(@Nullable CommandSourceStack p_151249_, @Nullable Player p_151250_, String p_151251_) {
        MutableComponent $$5;
        try {
            MutableComponent $$3 = Component.Serializer.m_130714_(p_151251_);
            $$3 = ComponentUtils.m_130731_(p_151249_, $$3, p_151250_, 0);
        }
        catch (Exception $$4) {
            $$5 = Component.m_237113_(p_151251_);
        }
        return Component.Serializer.m_130703_($$5);
    }

    @Override
    public boolean m_5812_(ItemStack p_43476_) {
        return true;
    }
}

