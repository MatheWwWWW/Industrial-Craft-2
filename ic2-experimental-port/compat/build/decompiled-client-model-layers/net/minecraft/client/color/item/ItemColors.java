/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.color.item;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ItemColors {
    private static final int f_168642_ = -1;
    private final IdMapper<ItemColor> f_92674_ = new IdMapper(32);

    public static ItemColors m_92683_(BlockColors p_92684_) {
        ItemColors $$1 = new ItemColors();
        $$1.m_92689_((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem)((Object)p_92708_.m_41720_())).m_41121_(p_92708_), Items.f_42407_, Items.f_42408_, Items.f_42462_, Items.f_42463_, Items.f_42654_);
        $$1.m_92689_((p_92705_, p_92706_) -> GrassColor.m_46415_(0.5, 1.0), Blocks.f_50359_, Blocks.f_50360_);
        $$1.m_92689_((p_92702_, p_92703_) -> {
            int[] $$3;
            if (p_92703_ != 1) {
                return -1;
            }
            CompoundTag $$2 = p_92702_.m_41737_("Explosion");
            int[] nArray = $$3 = $$2 != null && $$2.m_128425_("Colors", 11) ? $$2.m_128465_("Colors") : null;
            if ($$3 == null || $$3.length == 0) {
                return 0x8A8A8A;
            }
            if ($$3.length == 1) {
                return $$3[0];
            }
            int $$4 = 0;
            int $$5 = 0;
            int $$6 = 0;
            for (int $$7 : $$3) {
                $$4 += ($$7 & 0xFF0000) >> 16;
                $$5 += ($$7 & 0xFF00) >> 8;
                $$6 += ($$7 & 0xFF) >> 0;
            }
            return ($$4 /= $$3.length) << 16 | ($$5 /= $$3.length) << 8 | ($$6 /= $$3.length);
        }, Items.f_42689_);
        $$1.m_92689_((p_92699_, p_92700_) -> p_92700_ > 0 ? -1 : PotionUtils.m_43575_(p_92699_), Items.f_42589_, Items.f_42736_, Items.f_42739_);
        for (SpawnEggItem $$2 : SpawnEggItem.m_43233_()) {
            $$1.m_92689_((p_92681_, p_92682_) -> $$2.m_43211_(p_92682_), $$2);
        }
        $$1.m_92689_((p_92687_, p_92688_) -> {
            BlockState $$3 = ((BlockItem)p_92687_.m_41720_()).m_40614_().m_49966_();
            return p_92684_.m_92577_($$3, null, null, p_92688_);
        }, Blocks.f_50440_, Blocks.f_50034_, Blocks.f_50035_, Blocks.f_50191_, Blocks.f_50050_, Blocks.f_50051_, Blocks.f_50052_, Blocks.f_50053_, Blocks.f_50054_, Blocks.f_50055_, Blocks.f_50196_);
        $$1.m_92689_((p_92696_, p_92697_) -> FoliageColor.m_220346_(), Blocks.f_220838_);
        $$1.m_92689_((p_92693_, p_92694_) -> p_92694_ == 0 ? PotionUtils.m_43575_(p_92693_) : -1, Items.f_42738_);
        $$1.m_92689_((p_232352_, p_232353_) -> p_232353_ == 0 ? -1 : MapItem.m_42918_(p_232352_), Items.f_42573_);
        return $$1;
    }

    public int m_92676_(ItemStack p_92677_, int p_92678_) {
        ItemColor $$2 = this.f_92674_.m_7942_(Registry.f_122827_.m_7447_(p_92677_.m_41720_()));
        return $$2 == null ? -1 : $$2.m_92671_(p_92677_, p_92678_);
    }

    public void m_92689_(ItemColor p_92690_, ItemLike ... p_92691_) {
        for (ItemLike $$2 : p_92691_) {
            this.f_92674_.m_122664_(p_92690_, Item.m_41393_($$2.m_5456_()));
        }
    }
}

