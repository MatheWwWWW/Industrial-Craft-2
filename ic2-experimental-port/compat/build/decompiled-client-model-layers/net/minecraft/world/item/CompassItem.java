/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.item;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public class CompassItem
extends Item
implements Vanishable {
    private static final Logger f_40715_ = LogUtils.getLogger();
    public static final String f_150786_ = "LodestonePos";
    public static final String f_150787_ = "LodestoneDimension";
    public static final String f_150788_ = "LodestoneTracked";

    public CompassItem(Item.Properties p_40718_) {
        super(p_40718_);
    }

    public static boolean m_40736_(ItemStack p_40737_) {
        CompoundTag $$1 = p_40737_.m_41783_();
        return $$1 != null && ($$1.m_128441_(f_150787_) || $$1.m_128441_(f_150786_));
    }

    private static Optional<ResourceKey<Level>> m_40727_(CompoundTag p_40728_) {
        return Level.f_46427_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_40728_.m_128423_(f_150787_)).result();
    }

    @Nullable
    public static GlobalPos m_220021_(CompoundTag p_220022_) {
        Optional<ResourceKey<Level>> $$3;
        boolean $$1 = p_220022_.m_128441_(f_150786_);
        boolean $$2 = p_220022_.m_128441_(f_150787_);
        if ($$1 && $$2 && ($$3 = CompassItem.m_40727_(p_220022_)).isPresent()) {
            BlockPos $$4 = NbtUtils.m_129239_(p_220022_.m_128469_(f_150786_));
            return GlobalPos.m_122643_($$3.get(), $$4);
        }
        return null;
    }

    @Nullable
    public static GlobalPos m_220019_(Level p_220020_) {
        return p_220020_.m_6042_().f_63858_() ? GlobalPos.m_122643_(p_220020_.m_46472_(), p_220020_.m_220360_()) : null;
    }

    @Override
    public boolean m_5812_(ItemStack p_40739_) {
        return CompassItem.m_40736_(p_40739_) || super.m_5812_(p_40739_);
    }

    @Override
    public void m_6883_(ItemStack p_40720_, Level p_40721_, Entity p_40722_, int p_40723_, boolean p_40724_) {
        if (p_40721_.f_46443_) {
            return;
        }
        if (CompassItem.m_40736_(p_40720_)) {
            BlockPos $$7;
            CompoundTag $$5 = p_40720_.m_41784_();
            if ($$5.m_128441_(f_150788_) && !$$5.m_128471_(f_150788_)) {
                return;
            }
            Optional<ResourceKey<Level>> $$6 = CompassItem.m_40727_($$5);
            if ($$6.isPresent() && $$6.get() == p_40721_.m_46472_() && $$5.m_128441_(f_150786_) && (!p_40721_.m_46739_($$7 = NbtUtils.m_129239_($$5.m_128469_(f_150786_))) || !((ServerLevel)p_40721_).m_8904_().m_217874_(PoiTypes.f_218065_, $$7))) {
                $$5.m_128473_(f_150786_);
            }
        }
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_40726_) {
        BlockPos $$1 = p_40726_.m_8083_();
        Level $$2 = p_40726_.m_43725_();
        if ($$2.m_8055_($$1).m_60713_(Blocks.f_50729_)) {
            boolean $$5;
            $$2.m_5594_(null, $$1, SoundEvents.f_12107_, SoundSource.PLAYERS, 1.0f, 1.0f);
            Player $$3 = p_40726_.m_43723_();
            ItemStack $$4 = p_40726_.m_43722_();
            boolean bl = $$5 = !$$3.m_150110_().f_35937_ && $$4.m_41613_() == 1;
            if ($$5) {
                this.m_40732_($$2.m_46472_(), $$1, $$4.m_41784_());
            } else {
                ItemStack $$6 = new ItemStack(Items.f_42522_, 1);
                CompoundTag $$7 = $$4.m_41782_() ? $$4.m_41783_().m_6426_() : new CompoundTag();
                $$6.m_41751_($$7);
                if (!$$3.m_150110_().f_35937_) {
                    $$4.m_41774_(1);
                }
                this.m_40732_($$2.m_46472_(), $$1, $$7);
                if (!$$3.m_150109_().m_36054_($$6)) {
                    $$3.m_36176_($$6, false);
                }
            }
            return InteractionResult.m_19078_($$2.f_46443_);
        }
        return super.m_6225_(p_40726_);
    }

    private void m_40732_(ResourceKey<Level> p_40733_, BlockPos p_40734_, CompoundTag p_40735_) {
        p_40735_.m_128365_(f_150786_, NbtUtils.m_129224_(p_40734_));
        Level.f_46427_.encodeStart((DynamicOps)NbtOps.f_128958_, p_40733_).resultOrPartial(arg_0 -> ((Logger)f_40715_).error(arg_0)).ifPresent(p_40731_ -> p_40735_.m_128365_(f_150787_, (Tag)p_40731_));
        p_40735_.m_128379_(f_150788_, true);
    }

    @Override
    public String m_5671_(ItemStack p_40741_) {
        return CompassItem.m_40736_(p_40741_) ? "item.minecraft.lodestone_compass" : super.m_5671_(p_40741_);
    }
}

