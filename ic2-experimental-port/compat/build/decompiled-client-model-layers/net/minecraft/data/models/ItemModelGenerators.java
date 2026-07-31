/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 */
package net.minecraft.data.models;

import com.google.gson.JsonElement;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ItemModelGenerators {
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> f_125080_;

    public ItemModelGenerators(BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125082_) {
        this.f_125080_ = p_125082_;
    }

    private void m_125088_(Item p_125089_, ModelTemplate p_125090_) {
        p_125090_.m_125612_(ModelLocationUtils.m_125571_(p_125089_), TextureMapping.m_125766_(p_125089_), this.f_125080_);
    }

    private void m_125091_(Item p_125092_, String p_125093_, ModelTemplate p_125094_) {
        p_125094_.m_125612_(ModelLocationUtils.m_125573_(p_125092_, p_125093_), TextureMapping.m_125820_(TextureMapping.m_125745_(p_125092_, p_125093_)), this.f_125080_);
    }

    private void m_125084_(Item p_125085_, Item p_125086_, ModelTemplate p_125087_) {
        p_125087_.m_125612_(ModelLocationUtils.m_125571_(p_125085_), TextureMapping.m_125766_(p_125086_), this.f_125080_);
    }

    private void m_236321_(Item p_236322_) {
        for (int $$1 = 0; $$1 < 32; ++$$1) {
            if ($$1 == 16) continue;
            this.m_125091_(p_236322_, String.format(Locale.ROOT, "_%02d", $$1), ModelTemplates.f_125658_);
        }
    }

    private void m_236323_(Item p_236324_) {
        for (int $$1 = 1; $$1 < 64; ++$$1) {
            this.m_125091_(p_236324_, String.format(Locale.ROOT, "_%02d", $$1), ModelTemplates.f_125658_);
        }
    }

    public void m_125083_() {
        this.m_125088_(Items.f_42745_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220202_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151049_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42410_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42650_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42412_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42674_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_41911_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42579_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42732_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42734_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42743_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220200_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42498_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42593_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42585_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42494_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42499_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42517_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42399_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42406_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42460_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42495_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42446_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42684_, ModelTemplates.f_125660_);
        this.m_125088_(Items.f_42685_, ModelTemplates.f_125660_);
        this.m_125088_(Items.f_42467_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42465_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42464_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42466_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42414_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42519_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42581_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42730_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42461_, ModelTemplates.f_125658_);
        this.m_236323_(Items.f_42524_);
        this.m_125088_(Items.f_42413_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42458_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42657_, ModelTemplates.f_125658_);
        this.m_236321_(Items.f_42522_);
        this.m_236321_(Items.f_220211_);
        this.m_125088_(Items.f_42580_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42582_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42530_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42659_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42486_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42698_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42531_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42572_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151051_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151052_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42721_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42492_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42746_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220203_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42415_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42391_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42475_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42473_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42472_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42392_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42653_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42474_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42390_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42389_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42388_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42735_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42576_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42521_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42616_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42690_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42545_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42584_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42729_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42612_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42592_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42688_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42613_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42484_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42409_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42720_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42520_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42586_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42590_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42546_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_186364_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151079_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42525_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151056_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151063_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151053_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42436_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42433_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42479_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42677_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42477_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42476_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42434_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42652_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42478_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42432_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42431_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42430_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42417_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42587_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42490_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42496_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42403_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42716_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42784_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42787_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42694_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42532_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151050_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42386_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42471_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42469_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42468_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42387_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42651_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42416_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42470_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42749_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42385_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42384_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42383_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42617_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42744_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220201_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42750_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42534_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42448_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42454_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42654_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42538_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42491_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42540_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42537_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42542_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220204_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220205_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42676_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42575_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42455_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42449_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42723_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42400_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220218_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42710_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42752_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42702_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42701_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42703_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42704_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42705_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42706_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42712_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42707_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42708_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42711_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42709_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_186363_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220217_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42658_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42656_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42715_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42396_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42483_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42481_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42480_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42397_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42418_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42482_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42395_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42419_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42394_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42393_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42691_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42686_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42453_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220207_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42536_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42487_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42516_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42714_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42725_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42489_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42675_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42731_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42485_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151055_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42696_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42695_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42529_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42456_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42687_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42493_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42692_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42697_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42648_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42649_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42699_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42497_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42583_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42450_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42527_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42457_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42355_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42574_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42748_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42722_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42518_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42452_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220224_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42737_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42591_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42742_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220208_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151059_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42398_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42428_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42429_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42427_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42426_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42425_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42501_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42718_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42693_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42747_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42713_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42528_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42459_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_151057_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_220210_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42354_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42447_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42405_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42535_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42423_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42424_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42422_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42421_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42420_, ModelTemplates.f_125659_);
        this.m_125088_(Items.f_42614_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42615_, ModelTemplates.f_125658_);
        this.m_125088_(Items.f_42539_, ModelTemplates.f_125658_);
        this.m_125084_(Items.f_42751_, Items.f_42398_, ModelTemplates.f_125659_);
        this.m_125084_(Items.f_42437_, Items.f_42436_, ModelTemplates.f_125658_);
    }
}

