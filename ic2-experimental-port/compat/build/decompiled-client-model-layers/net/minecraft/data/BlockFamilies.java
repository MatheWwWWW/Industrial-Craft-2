/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockFamilies {
    private static final Map<Block, BlockFamily> f_175904_ = Maps.newHashMap();
    private static final String f_175905_ = "wooden";
    private static final String f_175906_ = "has_planks";
    public static final BlockFamily f_175896_ = BlockFamilies.m_175935_(Blocks.f_50744_).m_175963_(Blocks.f_50308_).m_175982_(Blocks.f_50482_).m_175984_(Blocks.f_50477_).m_175990_(Blocks.f_50171_).m_175965_(Blocks.f_50151_, Blocks.f_50161_).m_175986_(Blocks.f_50402_).m_175988_(Blocks.f_50372_).m_175980_(Blocks.f_50487_).m_175994_(Blocks.f_50220_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175907_ = BlockFamilies.m_175935_(Blocks.f_50742_).m_175963_(Blocks.f_50253_).m_175982_(Blocks.f_50480_).m_175984_(Blocks.f_50475_).m_175990_(Blocks.f_50169_).m_175965_(Blocks.f_50150_, Blocks.f_50160_).m_175986_(Blocks.f_50400_).m_175988_(Blocks.f_50270_).m_175980_(Blocks.f_50485_).m_175994_(Blocks.f_50218_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175908_ = BlockFamilies.m_175935_(Blocks.f_50655_).m_175963_(Blocks.f_50669_).m_175982_(Blocks.f_50661_).m_175984_(Blocks.f_50665_).m_175990_(Blocks.f_50659_).m_175965_(Blocks.f_50673_, Blocks.f_50675_).m_175986_(Blocks.f_50657_).m_175988_(Blocks.f_50667_).m_175980_(Blocks.f_50671_).m_175994_(Blocks.f_50663_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175909_ = BlockFamilies.m_175935_(Blocks.f_50743_).m_175963_(Blocks.f_50254_).m_175982_(Blocks.f_50481_).m_175984_(Blocks.f_50476_).m_175990_(Blocks.f_50170_).m_175965_(Blocks.f_50152_, Blocks.f_50162_).m_175986_(Blocks.f_50401_).m_175988_(Blocks.f_50271_).m_175980_(Blocks.f_50486_).m_175994_(Blocks.f_50219_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175910_ = BlockFamilies.m_175935_(Blocks.f_50705_).m_175963_(Blocks.f_50251_).m_175982_(Blocks.f_50132_).m_175984_(Blocks.f_50192_).m_175990_(Blocks.f_50167_).m_175965_(Blocks.f_50095_, Blocks.f_50158_).m_175986_(Blocks.f_50398_).m_175988_(Blocks.f_50086_).m_175980_(Blocks.f_50154_).m_175994_(Blocks.f_50216_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175911_ = BlockFamilies.m_175935_(Blocks.f_50745_).m_175963_(Blocks.f_50309_).m_175982_(Blocks.f_50483_).m_175984_(Blocks.f_50478_).m_175990_(Blocks.f_50172_).m_175965_(Blocks.f_50153_, Blocks.f_50163_).m_175986_(Blocks.f_50403_).m_175988_(Blocks.f_50373_).m_175980_(Blocks.f_50488_).m_175994_(Blocks.f_50221_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175912_ = BlockFamilies.m_175935_(Blocks.f_50741_).m_175963_(Blocks.f_50252_).m_175982_(Blocks.f_50479_).m_175984_(Blocks.f_50474_).m_175990_(Blocks.f_50168_).m_175965_(Blocks.f_50149_, Blocks.f_50159_).m_175986_(Blocks.f_50399_).m_175988_(Blocks.f_50269_).m_175980_(Blocks.f_50484_).m_175994_(Blocks.f_50217_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_175913_ = BlockFamilies.m_175935_(Blocks.f_50656_).m_175963_(Blocks.f_50670_).m_175982_(Blocks.f_50662_).m_175984_(Blocks.f_50666_).m_175990_(Blocks.f_50660_).m_175965_(Blocks.f_50674_, Blocks.f_50676_).m_175986_(Blocks.f_50658_).m_175988_(Blocks.f_50668_).m_175980_(Blocks.f_50672_).m_175994_(Blocks.f_50664_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_235985_ = BlockFamilies.m_175935_(Blocks.f_220865_).m_175963_(Blocks.f_220846_).m_175986_(Blocks.f_220851_).m_175988_(Blocks.f_220848_).m_175982_(Blocks.f_220852_).m_175984_(Blocks.f_220850_).m_175990_(Blocks.f_220840_).m_175965_(Blocks.f_220841_, Blocks.f_220839_).m_175980_(Blocks.f_220853_).m_175994_(Blocks.f_220842_).m_175968_("wooden").m_175973_("has_planks").m_175962_();
    public static final BlockFamily f_235986_ = BlockFamilies.m_175935_(Blocks.f_220844_).m_175996_(Blocks.f_220854_).m_175988_(Blocks.f_220845_).m_175986_(Blocks.f_220849_).m_175962_();
    public static final BlockFamily f_175914_ = BlockFamilies.m_175935_(Blocks.f_50334_).m_175996_(Blocks.f_50611_).m_175988_(Blocks.f_50639_).m_175986_(Blocks.f_50600_).m_175992_(Blocks.f_50387_).m_175962_();
    public static final BlockFamily f_175915_ = BlockFamilies.m_175935_(Blocks.f_50387_).m_175988_(Blocks.f_50641_).m_175986_(Blocks.f_50602_).m_175962_();
    public static final BlockFamily f_175916_ = BlockFamilies.m_175935_(Blocks.f_50730_).m_175996_(Blocks.f_50732_).m_175988_(Blocks.f_50731_).m_175986_(Blocks.f_50733_).m_175992_(Blocks.f_50734_).m_175962_();
    public static final BlockFamily f_175917_ = BlockFamilies.m_175935_(Blocks.f_50734_).m_175996_(Blocks.f_50711_).m_175990_(Blocks.f_50709_).m_175963_(Blocks.f_50710_).m_175988_(Blocks.f_50707_).m_175986_(Blocks.f_50708_).m_175992_(Blocks.f_50735_).m_175971_(Blocks.f_50737_).m_175962_();
    public static final BlockFamily f_175918_ = BlockFamilies.m_175935_(Blocks.f_50735_).m_175996_(Blocks.f_50740_).m_175988_(Blocks.f_50739_).m_175986_(Blocks.f_50738_).m_175976_(Blocks.f_50736_).m_175962_();
    public static final BlockFamily f_175919_ = BlockFamilies.m_175935_(Blocks.f_50076_).m_175996_(Blocks.f_50604_).m_175988_(Blocks.f_50193_).m_175986_(Blocks.f_50410_).m_175962_();
    public static final BlockFamily f_175920_ = BlockFamilies.m_175935_(Blocks.f_50443_).m_175996_(Blocks.f_50614_).m_175988_(Blocks.f_50634_).m_175986_(Blocks.f_50648_).m_175962_();
    public static final BlockFamily f_175921_ = BlockFamilies.m_175935_(Blocks.f_50223_).m_175996_(Blocks.f_50607_).m_175988_(Blocks.f_50631_).m_175986_(Blocks.f_50645_).m_175962_();
    public static final BlockFamily f_175922_ = BlockFamilies.m_175935_(Blocks.f_152504_).m_175978_(Blocks.f_152510_).m_175970_().m_175962_();
    public static final BlockFamily f_175923_ = BlockFamilies.m_175935_(Blocks.f_152510_).m_175986_(Blocks.f_152570_).m_175988_(Blocks.f_152566_).m_175970_().m_175962_();
    public static final BlockFamily f_175924_ = BlockFamilies.m_175935_(Blocks.f_152571_).m_175978_(Blocks.f_152578_).m_175968_("waxed_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175925_ = BlockFamilies.m_175935_(Blocks.f_152578_).m_175986_(Blocks.f_152586_).m_175988_(Blocks.f_152582_).m_175968_("waxed_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175926_ = BlockFamilies.m_175935_(Blocks.f_152503_).m_175978_(Blocks.f_152509_).m_175970_().m_175962_();
    public static final BlockFamily f_175927_ = BlockFamilies.m_175935_(Blocks.f_152509_).m_175986_(Blocks.f_152569_).m_175988_(Blocks.f_152565_).m_175970_().m_175962_();
    public static final BlockFamily f_175928_ = BlockFamilies.m_175935_(Blocks.f_152573_).m_175978_(Blocks.f_152577_).m_175968_("waxed_exposed_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175929_ = BlockFamilies.m_175935_(Blocks.f_152577_).m_175986_(Blocks.f_152585_).m_175988_(Blocks.f_152581_).m_175968_("waxed_exposed_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175930_ = BlockFamilies.m_175935_(Blocks.f_152502_).m_175978_(Blocks.f_152508_).m_175970_().m_175962_();
    public static final BlockFamily f_175931_ = BlockFamilies.m_175935_(Blocks.f_152508_).m_175986_(Blocks.f_152568_).m_175988_(Blocks.f_152564_).m_175970_().m_175962_();
    public static final BlockFamily f_175870_ = BlockFamilies.m_175935_(Blocks.f_152572_).m_175978_(Blocks.f_152576_).m_175968_("waxed_weathered_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175871_ = BlockFamilies.m_175935_(Blocks.f_152576_).m_175986_(Blocks.f_152584_).m_175988_(Blocks.f_152580_).m_175968_("waxed_weathered_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175872_ = BlockFamilies.m_175935_(Blocks.f_152501_).m_175978_(Blocks.f_152507_).m_175970_().m_175962_();
    public static final BlockFamily f_175873_ = BlockFamilies.m_175935_(Blocks.f_152507_).m_175986_(Blocks.f_152567_).m_175988_(Blocks.f_152563_).m_175970_().m_175962_();
    public static final BlockFamily f_175874_ = BlockFamilies.m_175935_(Blocks.f_152574_).m_175978_(Blocks.f_152575_).m_175968_("waxed_oxidized_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175875_ = BlockFamilies.m_175935_(Blocks.f_152575_).m_175986_(Blocks.f_152583_).m_175988_(Blocks.f_152579_).m_175968_("waxed_oxidized_cut_copper").m_175970_().m_175962_();
    public static final BlockFamily f_175876_ = BlockFamilies.m_175935_(Blocks.f_50652_).m_175996_(Blocks.f_50274_).m_175988_(Blocks.f_50157_).m_175986_(Blocks.f_50409_).m_175962_();
    public static final BlockFamily f_175877_ = BlockFamilies.m_175935_(Blocks.f_50079_).m_175996_(Blocks.f_50275_).m_175988_(Blocks.f_50633_).m_175986_(Blocks.f_50647_).m_175962_();
    public static final BlockFamily f_175878_ = BlockFamilies.m_175935_(Blocks.f_50228_).m_175996_(Blocks.f_50615_).m_175988_(Blocks.f_50642_).m_175986_(Blocks.f_50603_).m_175992_(Blocks.f_50281_).m_175962_();
    public static final BlockFamily f_175879_ = BlockFamilies.m_175935_(Blocks.f_50281_).m_175988_(Blocks.f_50632_).m_175986_(Blocks.f_50646_).m_175962_();
    public static final BlockFamily f_175880_ = BlockFamilies.m_175935_(Blocks.f_50122_).m_175996_(Blocks.f_50608_).m_175988_(Blocks.f_50638_).m_175986_(Blocks.f_50651_).m_175992_(Blocks.f_50175_).m_175962_();
    public static final BlockFamily f_175881_ = BlockFamilies.m_175935_(Blocks.f_50175_).m_175988_(Blocks.f_50629_).m_175986_(Blocks.f_50643_).m_175962_();
    public static final BlockFamily f_175882_ = BlockFamilies.m_175935_(Blocks.f_50197_).m_175982_(Blocks.f_50198_).m_175996_(Blocks.f_50610_).m_175988_(Blocks.f_50199_).m_175986_(Blocks.f_50412_).m_175971_(Blocks.f_50712_).m_175976_(Blocks.f_50713_).m_175962_();
    public static final BlockFamily f_175883_ = BlockFamilies.m_175935_(Blocks.f_50452_).m_175986_(Blocks.f_50601_).m_175988_(Blocks.f_50640_).m_175996_(Blocks.f_50612_).m_175962_();
    public static final BlockFamily f_175884_ = BlockFamilies.m_175935_(Blocks.f_50377_).m_175996_(Blocks.f_50605_).m_175988_(Blocks.f_50380_).m_175986_(Blocks.f_50383_).m_175962_();
    public static final BlockFamily f_175885_ = BlockFamilies.m_175935_(Blocks.f_50492_).m_175988_(Blocks.f_50442_).m_175986_(Blocks.f_50469_).m_175975_().m_175962_();
    public static final BlockFamily f_175886_ = BlockFamilies.m_175935_(Blocks.f_50378_).m_175988_(Blocks.f_50381_).m_175986_(Blocks.f_50384_).m_175962_();
    public static final BlockFamily f_175887_ = BlockFamilies.m_175935_(Blocks.f_50379_).m_175988_(Blocks.f_50382_).m_175986_(Blocks.f_50385_).m_175962_();
    public static final BlockFamily f_175888_ = BlockFamilies.m_175935_(Blocks.f_50333_).m_175988_(Blocks.f_50284_).m_175986_(Blocks.f_50413_).m_175971_(Blocks.f_50282_).m_175975_().m_175962_();
    public static final BlockFamily f_175889_ = BlockFamilies.m_175935_(Blocks.f_50472_).m_175988_(Blocks.f_50637_).m_175986_(Blocks.f_50650_).m_175962_();
    public static final BlockFamily f_175890_ = BlockFamilies.m_175935_(Blocks.f_50062_).m_175996_(Blocks.f_50613_).m_175988_(Blocks.f_50263_).m_175986_(Blocks.f_50406_).m_175971_(Blocks.f_50063_).m_175978_(Blocks.f_50064_).m_175975_().m_175962_();
    public static final BlockFamily f_175891_ = BlockFamilies.m_175935_(Blocks.f_50064_).m_175986_(Blocks.f_50407_).m_175962_();
    public static final BlockFamily f_175892_ = BlockFamilies.m_175935_(Blocks.f_50471_).m_175986_(Blocks.f_50649_).m_175988_(Blocks.f_50636_).m_175962_();
    public static final BlockFamily f_175893_ = BlockFamilies.m_175935_(Blocks.f_50394_).m_175996_(Blocks.f_50606_).m_175988_(Blocks.f_50397_).m_175986_(Blocks.f_50467_).m_175971_(Blocks.f_50395_).m_175978_(Blocks.f_50396_).m_175975_().m_175962_();
    public static final BlockFamily f_175894_ = BlockFamilies.m_175935_(Blocks.f_50396_).m_175986_(Blocks.f_50468_).m_175962_();
    public static final BlockFamily f_175895_ = BlockFamilies.m_175935_(Blocks.f_50473_).m_175986_(Blocks.f_50644_).m_175988_(Blocks.f_50630_).m_175962_();
    public static final BlockFamily f_175897_ = BlockFamilies.m_175935_(Blocks.f_50069_).m_175986_(Blocks.f_50404_).m_175990_(Blocks.f_50165_).m_175963_(Blocks.f_50124_).m_175988_(Blocks.f_50635_).m_175962_();
    public static final BlockFamily f_175898_ = BlockFamilies.m_175935_(Blocks.f_50222_).m_175996_(Blocks.f_50609_).m_175988_(Blocks.f_50194_).m_175986_(Blocks.f_50411_).m_175971_(Blocks.f_50225_).m_175976_(Blocks.f_50224_).m_175975_().m_175962_();
    public static final BlockFamily f_175899_ = BlockFamilies.m_175935_(Blocks.f_152550_).m_175962_();
    public static final BlockFamily f_175900_ = BlockFamilies.m_175935_(Blocks.f_152551_).m_175986_(Blocks.f_152553_).m_175988_(Blocks.f_152552_).m_175996_(Blocks.f_152554_).m_175971_(Blocks.f_152593_).m_175992_(Blocks.f_152555_).m_175962_();
    public static final BlockFamily f_175901_ = BlockFamilies.m_175935_(Blocks.f_152555_).m_175986_(Blocks.f_152557_).m_175988_(Blocks.f_152556_).m_175996_(Blocks.f_152558_).m_175962_();
    public static final BlockFamily f_175902_ = BlockFamilies.m_175935_(Blocks.f_152589_).m_175986_(Blocks.f_152591_).m_175988_(Blocks.f_152590_).m_175996_(Blocks.f_152592_).m_175976_(Blocks.f_152594_).m_175962_();
    public static final BlockFamily f_175903_ = BlockFamilies.m_175935_(Blocks.f_152559_).m_175986_(Blocks.f_152561_).m_175988_(Blocks.f_152560_).m_175996_(Blocks.f_152562_).m_175976_(Blocks.f_152595_).m_175962_();

    private static BlockFamily.Builder m_175935_(Block p_175936_) {
        BlockFamily.Builder $$1 = new BlockFamily.Builder(p_175936_);
        BlockFamily $$2 = f_175904_.put(p_175936_, $$1.m_175962_());
        if ($$2 != null) {
            throw new IllegalStateException("Duplicate family definition for " + Registry.f_122824_.m_7981_(p_175936_));
        }
        return $$1;
    }

    public static Stream<BlockFamily> m_175934_() {
        return f_175904_.values().stream();
    }
}

