/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.tags;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class ItemTagsProvider
extends TagsProvider<Item> {
    private final Function<TagKey<Block>, TagBuilder> f_126528_ = p_126531_::m_236451_;

    public ItemTagsProvider(DataGenerator p_126530_, BlockTagsProvider p_126531_) {
        super(p_126530_, Registry.f_122827_);
    }

    @Override
    protected void m_6577_() {
        this.m_206421_(BlockTags.f_13089_, ItemTags.f_13167_);
        this.m_206421_(BlockTags.f_13090_, ItemTags.f_13168_);
        this.m_206421_(BlockTags.f_13091_, ItemTags.f_13169_);
        this.m_206421_(BlockTags.f_13092_, ItemTags.f_13170_);
        this.m_206421_(BlockTags.f_13093_, ItemTags.f_13171_);
        this.m_206421_(BlockTags.f_215838_, ItemTags.f_215867_);
        this.m_206421_(BlockTags.f_13095_, ItemTags.f_13173_);
        this.m_206421_(BlockTags.f_13096_, ItemTags.f_13174_);
        this.m_206421_(BlockTags.f_13097_, ItemTags.f_13175_);
        this.m_206421_(BlockTags.f_13098_, ItemTags.f_13176_);
        this.m_206421_(BlockTags.f_13100_, ItemTags.f_13177_);
        this.m_206421_(BlockTags.f_13103_, ItemTags.f_13179_);
        this.m_206421_(BlockTags.f_13104_, ItemTags.f_13180_);
        this.m_206421_(BlockTags.f_13108_, ItemTags.f_13184_);
        this.m_206421_(BlockTags.f_13107_, ItemTags.f_13183_);
        this.m_206421_(BlockTags.f_13109_, ItemTags.f_13185_);
        this.m_206421_(BlockTags.f_13110_, ItemTags.f_13186_);
        this.m_206421_(BlockTags.f_13112_, ItemTags.f_13188_);
        this.m_206421_(BlockTags.f_215840_, ItemTags.f_215869_);
        this.m_206421_(BlockTags.f_13111_, ItemTags.f_13187_);
        this.m_206421_(BlockTags.f_13113_, ItemTags.f_13189_);
        this.m_206421_(BlockTags.f_13027_, ItemTags.f_13190_);
        this.m_206421_(BlockTags.f_13078_, ItemTags.f_215862_);
        this.m_206421_(BlockTags.f_13105_, ItemTags.f_13181_);
        this.m_206421_(BlockTags.f_215839_, ItemTags.f_215868_);
        this.m_206421_(BlockTags.f_13106_, ItemTags.f_13182_);
        this.m_206421_(BlockTags.f_13029_, ItemTags.f_13137_);
        this.m_206421_(BlockTags.f_13031_, ItemTags.f_13139_);
        this.m_206421_(BlockTags.f_13032_, ItemTags.f_13140_);
        this.m_206421_(BlockTags.f_13030_, ItemTags.f_13138_);
        this.m_206421_(BlockTags.f_13033_, ItemTags.f_13141_);
        this.m_206421_(BlockTags.f_13034_, ItemTags.f_13142_);
        this.m_206421_(BlockTags.f_13035_, ItemTags.f_13143_);
        this.m_206421_(BlockTags.f_13102_, ItemTags.f_13178_);
        this.m_206421_(BlockTags.f_13036_, ItemTags.f_13144_);
        this.m_206421_(BlockTags.f_13037_, ItemTags.f_13145_);
        this.m_206421_(BlockTags.f_13038_, ItemTags.f_13146_);
        this.m_206421_(BlockTags.f_13039_, ItemTags.f_13147_);
        this.m_206421_(BlockTags.f_13040_, ItemTags.f_13148_);
        this.m_206421_(BlockTags.f_13041_, ItemTags.f_13149_);
        this.m_206421_(BlockTags.f_13085_, ItemTags.f_13154_);
        this.m_206421_(BlockTags.f_144265_, ItemTags.f_144319_);
        this.m_206421_(BlockTags.f_215836_, ItemTags.f_215865_);
        this.m_206421_(BlockTags.f_13043_, ItemTags.f_13152_);
        this.m_206421_(BlockTags.f_144258_, ItemTags.f_144312_);
        this.m_206421_(BlockTags.f_144259_, ItemTags.f_144313_);
        this.m_206421_(BlockTags.f_144260_, ItemTags.f_144314_);
        this.m_206421_(BlockTags.f_144261_, ItemTags.f_144315_);
        this.m_206421_(BlockTags.f_144262_, ItemTags.f_144316_);
        this.m_206421_(BlockTags.f_144263_, ItemTags.f_144317_);
        this.m_206421_(BlockTags.f_144264_, ItemTags.f_144318_);
        this.m_206421_(BlockTags.f_144274_, ItemTags.f_198160_);
        this.m_206421_(BlockTags.f_198156_, ItemTags.f_198161_);
        this.m_206421_(BlockTags.f_215821_, ItemTags.f_215863_);
        this.m_206424_(ItemTags.f_13191_).m_126584_((Item[])new Item[]{Items.f_42660_, Items.f_42661_, Items.f_42662_, Items.f_42663_, Items.f_42664_, Items.f_42665_, Items.f_42666_, Items.f_42667_, Items.f_42668_, Items.f_42669_, Items.f_42670_, Items.f_42671_, Items.f_42672_, Items.f_42673_, Items.f_42727_, Items.f_42728_});
        this.m_206424_(ItemTags.f_13155_).m_126584_((Item[])new Item[]{Items.f_42453_, Items.f_42742_, Items.f_42743_, Items.f_42744_, Items.f_42745_, Items.f_42746_, Items.f_220204_}).m_206428_(ItemTags.f_215864_);
        this.m_206424_(ItemTags.f_215864_).m_126584_((Item[])new Item[]{Items.f_220207_, Items.f_220208_, Items.f_220200_, Items.f_220201_, Items.f_220202_, Items.f_220203_, Items.f_220205_});
        this.m_206424_(ItemTags.f_13156_).m_126584_((Item[])new Item[]{Items.f_42526_, Items.f_42530_, Items.f_42527_, Items.f_42531_, Items.f_42529_, Items.f_42528_});
        this.m_206421_(BlockTags.f_13066_, ItemTags.f_13157_);
        this.m_206424_(ItemTags.f_13159_).m_126584_((Item[])new Item[]{Items.f_42752_, Items.f_42701_, Items.f_42702_, Items.f_42703_, Items.f_42704_, Items.f_42705_, Items.f_42706_, Items.f_42707_, Items.f_42708_, Items.f_42709_, Items.f_42710_, Items.f_42711_});
        this.m_206424_(ItemTags.f_13158_).m_206428_(ItemTags.f_13159_).m_126582_(Items.f_42712_).m_126582_(Items.f_186363_).m_126582_(Items.f_220217_);
        this.m_206424_(ItemTags.f_13160_).m_126584_((Item[])new Item[]{Items.f_42413_, Items.f_42414_});
        this.m_206424_(ItemTags.f_13161_).m_126584_((Item[])new Item[]{Items.f_42412_, Items.f_42738_, Items.f_42737_});
        this.m_206424_(ItemTags.f_13162_).m_126584_((Item[])new Item[]{Items.f_42615_, Items.f_42614_});
        this.m_206424_(ItemTags.f_13164_).m_126584_((Item[])new Item[]{Items.f_42418_, Items.f_42616_, Items.f_42415_, Items.f_42417_, Items.f_42416_});
        this.m_206424_(ItemTags.f_13150_).m_126582_(Items.f_42053_).m_126582_(Items.f_42779_).m_126582_(Items.f_42782_);
        this.m_206424_(ItemTags.f_13151_).m_206428_(ItemTags.f_13152_).m_126584_((Item[])new Item[]{Items.f_41912_, Items.f_42758_, Items.f_42150_, Items.f_42417_, Items.f_42777_, Items.f_42524_, Items.f_42677_, Items.f_42546_, Items.f_42436_, Items.f_42437_, Items.f_42476_, Items.f_42477_, Items.f_42478_, Items.f_42479_, Items.f_42652_, Items.f_42430_, Items.f_42432_, Items.f_42431_, Items.f_42433_, Items.f_42434_, Items.f_151053_, Items.f_150997_});
        this.m_206424_(ItemTags.f_144309_).m_126582_(Items.f_42454_);
        this.m_206424_(ItemTags.f_144310_).m_126584_((Item[])new Item[]{Items.f_42485_, Items.f_42486_});
        this.m_206424_(ItemTags.f_144311_).m_126584_((Item[])new Item[]{Items.f_42780_, Items.f_151079_});
        this.m_206424_(ItemTags.f_13153_).m_126584_((Item[])new Item[]{Items.f_41844_, Items.f_41879_, Items.f_41895_, Items.f_41887_, Items.f_41843_, Items.f_41851_, Items.f_41894_, Items.f_41886_, Items.f_42797_, Items.f_42798_, Items.f_41920_, Items.f_41921_, Items.f_41974_, Items.f_41975_, Items.f_42044_, Items.f_42045_, Items.f_42062_, Items.f_42063_, Items.f_42036_, Items.f_42037_, Items.f_42114_, Items.f_42115_, Items.f_42090_, Items.f_42144_, Items.f_42348_, Items.f_42349_, Items.f_42444_, Items.f_42445_});
        this.m_206424_(ItemTags.f_13165_).m_126584_((Item[])new Item[]{Items.f_42594_, Items.f_42755_, Items.f_151035_});
        this.m_206424_(ItemTags.f_13166_).m_126584_((Item[])new Item[]{Items.f_42594_, Items.f_42755_, Items.f_151035_});
        this.m_206424_(ItemTags.f_144320_).m_126584_((Item[])new Item[]{Items.f_42463_, Items.f_42462_, Items.f_42408_, Items.f_42407_, Items.f_42654_});
        this.m_206424_(ItemTags.f_144321_).m_126582_(Items.f_42459_);
        this.m_206424_(ItemTags.f_144323_).m_126584_((Item[])new Item[]{Items.f_42390_, Items.f_42432_, Items.f_42385_, Items.f_42395_, Items.f_42427_, Items.f_42422_});
        this.m_206424_(ItemTags.f_215866_).m_126582_(Items.f_42522_).m_126582_(Items.f_220211_);
    }

    protected void m_206421_(TagKey<Block> p_206422_, TagKey<Item> p_206423_) {
        TagBuilder $$2 = this.m_236451_(p_206423_);
        TagBuilder $$3 = this.f_126528_.apply(p_206422_);
        $$3.m_215904_().forEach($$2::m_215902_);
    }
}

