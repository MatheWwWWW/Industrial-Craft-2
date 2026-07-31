/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ItemBlockRenderTypes {
    private static final Map<Block, RenderType> f_109275_ = Util.m_137469_(Maps.newHashMap(), p_109296_ -> {
        RenderType $$1 = RenderType.m_110503_();
        p_109296_.put(Blocks.f_50267_, $$1);
        RenderType $$2 = RenderType.m_110457_();
        p_109296_.put(Blocks.f_50440_, $$2);
        p_109296_.put(Blocks.f_50183_, $$2);
        p_109296_.put(Blocks.f_50185_, $$2);
        p_109296_.put(Blocks.f_50266_, $$2);
        p_109296_.put(Blocks.f_50332_, $$2);
        p_109296_.put(Blocks.f_50184_, $$2);
        p_109296_.put(Blocks.f_50053_, $$2);
        p_109296_.put(Blocks.f_50050_, $$2);
        p_109296_.put(Blocks.f_50051_, $$2);
        p_109296_.put(Blocks.f_50054_, $$2);
        p_109296_.put(Blocks.f_50052_, $$2);
        p_109296_.put(Blocks.f_50055_, $$2);
        p_109296_.put(Blocks.f_152470_, $$2);
        p_109296_.put(Blocks.f_152471_, $$2);
        p_109296_.put(Blocks.f_220833_, $$2);
        p_109296_.put(Blocks.f_220838_, $$2);
        RenderType $$3 = RenderType.m_110463_();
        p_109296_.put(Blocks.f_50746_, $$3);
        p_109296_.put(Blocks.f_50747_, $$3);
        p_109296_.put(Blocks.f_50748_, $$3);
        p_109296_.put(Blocks.f_50749_, $$3);
        p_109296_.put(Blocks.f_50750_, $$3);
        p_109296_.put(Blocks.f_50751_, $$3);
        p_109296_.put(Blocks.f_50058_, $$3);
        p_109296_.put(Blocks.f_50066_, $$3);
        p_109296_.put(Blocks.f_50067_, $$3);
        p_109296_.put(Blocks.f_50068_, $$3);
        p_109296_.put(Blocks.f_50017_, $$3);
        p_109296_.put(Blocks.f_50018_, $$3);
        p_109296_.put(Blocks.f_50019_, $$3);
        p_109296_.put(Blocks.f_50020_, $$3);
        p_109296_.put(Blocks.f_50021_, $$3);
        p_109296_.put(Blocks.f_50022_, $$3);
        p_109296_.put(Blocks.f_50023_, $$3);
        p_109296_.put(Blocks.f_50024_, $$3);
        p_109296_.put(Blocks.f_50025_, $$3);
        p_109296_.put(Blocks.f_50026_, $$3);
        p_109296_.put(Blocks.f_50027_, $$3);
        p_109296_.put(Blocks.f_50028_, $$3);
        p_109296_.put(Blocks.f_50029_, $$3);
        p_109296_.put(Blocks.f_50030_, $$3);
        p_109296_.put(Blocks.f_50031_, $$3);
        p_109296_.put(Blocks.f_50033_, $$3);
        p_109296_.put(Blocks.f_50034_, $$3);
        p_109296_.put(Blocks.f_50035_, $$3);
        p_109296_.put(Blocks.f_50036_, $$3);
        p_109296_.put(Blocks.f_50037_, $$3);
        p_109296_.put(Blocks.f_50038_, $$3);
        p_109296_.put(Blocks.f_50111_, $$3);
        p_109296_.put(Blocks.f_50112_, $$3);
        p_109296_.put(Blocks.f_50113_, $$3);
        p_109296_.put(Blocks.f_50114_, $$3);
        p_109296_.put(Blocks.f_50115_, $$3);
        p_109296_.put(Blocks.f_50116_, $$3);
        p_109296_.put(Blocks.f_50117_, $$3);
        p_109296_.put(Blocks.f_50118_, $$3);
        p_109296_.put(Blocks.f_50119_, $$3);
        p_109296_.put(Blocks.f_50120_, $$3);
        p_109296_.put(Blocks.f_50121_, $$3);
        p_109296_.put(Blocks.f_50070_, $$3);
        p_109296_.put(Blocks.f_50071_, $$3);
        p_109296_.put(Blocks.f_50072_, $$3);
        p_109296_.put(Blocks.f_50073_, $$3);
        p_109296_.put(Blocks.f_50081_, $$3);
        p_109296_.put(Blocks.f_50082_, $$3);
        p_109296_.put(Blocks.f_50139_, $$3);
        p_109296_.put(Blocks.f_50140_, $$3);
        p_109296_.put(Blocks.f_50083_, $$3);
        p_109296_.put(Blocks.f_50084_, $$3);
        p_109296_.put(Blocks.f_50085_, $$3);
        p_109296_.put(Blocks.f_50088_, $$3);
        p_109296_.put(Blocks.f_50092_, $$3);
        p_109296_.put(Blocks.f_50154_, $$3);
        p_109296_.put(Blocks.f_50155_, $$3);
        p_109296_.put(Blocks.f_50156_, $$3);
        p_109296_.put(Blocks.f_50166_, $$3);
        p_109296_.put(Blocks.f_50174_, $$3);
        p_109296_.put(Blocks.f_50123_, $$3);
        p_109296_.put(Blocks.f_50128_, $$3);
        p_109296_.put(Blocks.f_50130_, $$3);
        p_109296_.put(Blocks.f_50146_, $$3);
        p_109296_.put(Blocks.f_50216_, $$3);
        p_109296_.put(Blocks.f_50217_, $$3);
        p_109296_.put(Blocks.f_50218_, $$3);
        p_109296_.put(Blocks.f_50219_, $$3);
        p_109296_.put(Blocks.f_50220_, $$3);
        p_109296_.put(Blocks.f_50221_, $$3);
        p_109296_.put(Blocks.f_50663_, $$3);
        p_109296_.put(Blocks.f_50664_, $$3);
        p_109296_.put(Blocks.f_220842_, $$3);
        p_109296_.put(Blocks.f_50187_, $$3);
        p_109296_.put(Blocks.f_50188_, $$3);
        p_109296_.put(Blocks.f_50189_, $$3);
        p_109296_.put(Blocks.f_50190_, $$3);
        p_109296_.put(Blocks.f_50191_, $$3);
        p_109296_.put(Blocks.f_152475_, $$3);
        p_109296_.put(Blocks.f_50196_, $$3);
        p_109296_.put(Blocks.f_50200_, $$3);
        p_109296_.put(Blocks.f_50255_, $$3);
        p_109296_.put(Blocks.f_50262_, $$3);
        p_109296_.put(Blocks.f_50273_, $$3);
        p_109296_.put(Blocks.f_50276_, $$3);
        p_109296_.put(Blocks.f_50277_, $$3);
        p_109296_.put(Blocks.f_50278_, $$3);
        p_109296_.put(Blocks.f_50279_, $$3);
        p_109296_.put(Blocks.f_50280_, $$3);
        p_109296_.put(Blocks.f_50229_, $$3);
        p_109296_.put(Blocks.f_50230_, $$3);
        p_109296_.put(Blocks.f_220847_, $$3);
        p_109296_.put(Blocks.f_50231_, $$3);
        p_109296_.put(Blocks.f_50232_, $$3);
        p_109296_.put(Blocks.f_50233_, $$3);
        p_109296_.put(Blocks.f_50234_, $$3);
        p_109296_.put(Blocks.f_50235_, $$3);
        p_109296_.put(Blocks.f_50236_, $$3);
        p_109296_.put(Blocks.f_50237_, $$3);
        p_109296_.put(Blocks.f_50238_, $$3);
        p_109296_.put(Blocks.f_50239_, $$3);
        p_109296_.put(Blocks.f_50240_, $$3);
        p_109296_.put(Blocks.f_50241_, $$3);
        p_109296_.put(Blocks.f_50242_, $$3);
        p_109296_.put(Blocks.f_50243_, $$3);
        p_109296_.put(Blocks.f_50244_, $$3);
        p_109296_.put(Blocks.f_50245_, $$3);
        p_109296_.put(Blocks.f_50246_, $$3);
        p_109296_.put(Blocks.f_50247_, $$3);
        p_109296_.put(Blocks.f_50248_, $$3);
        p_109296_.put(Blocks.f_152601_, $$3);
        p_109296_.put(Blocks.f_152602_, $$3);
        p_109296_.put(Blocks.f_50249_, $$3);
        p_109296_.put(Blocks.f_50250_, $$3);
        p_109296_.put(Blocks.f_50328_, $$3);
        p_109296_.put(Blocks.f_50285_, $$3);
        p_109296_.put(Blocks.f_50376_, $$3);
        p_109296_.put(Blocks.f_50355_, $$3);
        p_109296_.put(Blocks.f_50356_, $$3);
        p_109296_.put(Blocks.f_50357_, $$3);
        p_109296_.put(Blocks.f_50358_, $$3);
        p_109296_.put(Blocks.f_50359_, $$3);
        p_109296_.put(Blocks.f_50360_, $$3);
        p_109296_.put(Blocks.f_50484_, $$3);
        p_109296_.put(Blocks.f_50485_, $$3);
        p_109296_.put(Blocks.f_50486_, $$3);
        p_109296_.put(Blocks.f_50487_, $$3);
        p_109296_.put(Blocks.f_50488_, $$3);
        p_109296_.put(Blocks.f_220853_, $$3);
        p_109296_.put(Blocks.f_50489_, $$3);
        p_109296_.put(Blocks.f_50490_, $$3);
        p_109296_.put(Blocks.f_50491_, $$3);
        p_109296_.put(Blocks.f_50444_, $$3);
        p_109296_.put(Blocks.f_50575_, $$3);
        p_109296_.put(Blocks.f_50576_, $$3);
        p_109296_.put(Blocks.f_50578_, $$3);
        p_109296_.put(Blocks.f_50589_, $$3);
        p_109296_.put(Blocks.f_50590_, $$3);
        p_109296_.put(Blocks.f_50591_, $$3);
        p_109296_.put(Blocks.f_50592_, $$3);
        p_109296_.put(Blocks.f_50593_, $$3);
        p_109296_.put(Blocks.f_50594_, $$3);
        p_109296_.put(Blocks.f_50595_, $$3);
        p_109296_.put(Blocks.f_50596_, $$3);
        p_109296_.put(Blocks.f_50597_, $$3);
        p_109296_.put(Blocks.f_50598_, $$3);
        p_109296_.put(Blocks.f_50547_, $$3);
        p_109296_.put(Blocks.f_50548_, $$3);
        p_109296_.put(Blocks.f_50549_, $$3);
        p_109296_.put(Blocks.f_50550_, $$3);
        p_109296_.put(Blocks.f_50551_, $$3);
        p_109296_.put(Blocks.f_50552_, $$3);
        p_109296_.put(Blocks.f_50553_, $$3);
        p_109296_.put(Blocks.f_50554_, $$3);
        p_109296_.put(Blocks.f_50555_, $$3);
        p_109296_.put(Blocks.f_50556_, $$3);
        p_109296_.put(Blocks.f_50557_, $$3);
        p_109296_.put(Blocks.f_50558_, $$3);
        p_109296_.put(Blocks.f_50559_, $$3);
        p_109296_.put(Blocks.f_50560_, $$3);
        p_109296_.put(Blocks.f_50561_, $$3);
        p_109296_.put(Blocks.f_50562_, $$3);
        p_109296_.put(Blocks.f_50563_, $$3);
        p_109296_.put(Blocks.f_50564_, $$3);
        p_109296_.put(Blocks.f_50565_, $$3);
        p_109296_.put(Blocks.f_50566_, $$3);
        p_109296_.put(Blocks.f_50567_, $$3);
        p_109296_.put(Blocks.f_50569_, $$3);
        p_109296_.put(Blocks.f_50570_, $$3);
        p_109296_.put(Blocks.f_50571_, $$3);
        p_109296_.put(Blocks.f_50572_, $$3);
        p_109296_.put(Blocks.f_50616_, $$3);
        p_109296_.put(Blocks.f_50679_, $$3);
        p_109296_.put(Blocks.f_50681_, $$3);
        p_109296_.put(Blocks.f_50682_, $$3);
        p_109296_.put(Blocks.f_50683_, $$3);
        p_109296_.put(Blocks.f_50684_, $$3);
        p_109296_.put(Blocks.f_50685_, $$3);
        p_109296_.put(Blocks.f_50702_, $$3);
        p_109296_.put(Blocks.f_50703_, $$3);
        p_109296_.put(Blocks.f_50704_, $$3);
        p_109296_.put(Blocks.f_50653_, $$3);
        p_109296_.put(Blocks.f_50694_, $$3);
        p_109296_.put(Blocks.f_50700_, $$3);
        p_109296_.put(Blocks.f_50691_, $$3);
        p_109296_.put(Blocks.f_50654_, $$3);
        p_109296_.put(Blocks.f_50693_, $$3);
        p_109296_.put(Blocks.f_50725_, $$3);
        p_109296_.put(Blocks.f_50726_, $$3);
        p_109296_.put(Blocks.f_50727_, $$3);
        p_109296_.put(Blocks.f_50728_, $$3);
        p_109296_.put(Blocks.f_50671_, $$3);
        p_109296_.put(Blocks.f_50672_, $$3);
        p_109296_.put(Blocks.f_152588_, $$3);
        p_109296_.put(Blocks.f_152495_, $$3);
        p_109296_.put(Blocks.f_152494_, $$3);
        p_109296_.put(Blocks.f_152493_, $$3);
        p_109296_.put(Blocks.f_152492_, $$3);
        p_109296_.put(Blocks.f_152587_, $$3);
        p_109296_.put(Blocks.f_152538_, $$3);
        p_109296_.put(Blocks.f_152539_, $$3);
        p_109296_.put(Blocks.f_152540_, $$3);
        p_109296_.put(Blocks.f_152542_, $$3);
        p_109296_.put(Blocks.f_152541_, $$3);
        p_109296_.put(Blocks.f_152543_, $$3);
        p_109296_.put(Blocks.f_152545_, $$3);
        p_109296_.put(Blocks.f_152546_, $$3);
        p_109296_.put(Blocks.f_152547_, $$3);
        p_109296_.put(Blocks.f_152548_, $$3);
        p_109296_.put(Blocks.f_152500_, $$3);
        p_109296_.put(Blocks.f_220856_, $$3);
        p_109296_.put(Blocks.f_220858_, $$3);
        p_109296_.put(Blocks.f_220831_, $$3);
        p_109296_.put(Blocks.f_220832_, $$3);
        RenderType $$4 = RenderType.m_110466_();
        p_109296_.put(Blocks.f_50126_, $$4);
        p_109296_.put(Blocks.f_50142_, $$4);
        p_109296_.put(Blocks.f_50147_, $$4);
        p_109296_.put(Blocks.f_50148_, $$4);
        p_109296_.put(Blocks.f_50202_, $$4);
        p_109296_.put(Blocks.f_50203_, $$4);
        p_109296_.put(Blocks.f_50204_, $$4);
        p_109296_.put(Blocks.f_50205_, $$4);
        p_109296_.put(Blocks.f_50206_, $$4);
        p_109296_.put(Blocks.f_50207_, $$4);
        p_109296_.put(Blocks.f_50208_, $$4);
        p_109296_.put(Blocks.f_50209_, $$4);
        p_109296_.put(Blocks.f_50210_, $$4);
        p_109296_.put(Blocks.f_50211_, $$4);
        p_109296_.put(Blocks.f_50212_, $$4);
        p_109296_.put(Blocks.f_50213_, $$4);
        p_109296_.put(Blocks.f_50214_, $$4);
        p_109296_.put(Blocks.f_50215_, $$4);
        p_109296_.put(Blocks.f_50303_, $$4);
        p_109296_.put(Blocks.f_50304_, $$4);
        p_109296_.put(Blocks.f_50305_, $$4);
        p_109296_.put(Blocks.f_50306_, $$4);
        p_109296_.put(Blocks.f_50307_, $$4);
        p_109296_.put(Blocks.f_50361_, $$4);
        p_109296_.put(Blocks.f_50362_, $$4);
        p_109296_.put(Blocks.f_50363_, $$4);
        p_109296_.put(Blocks.f_50364_, $$4);
        p_109296_.put(Blocks.f_50365_, $$4);
        p_109296_.put(Blocks.f_50366_, $$4);
        p_109296_.put(Blocks.f_50367_, $$4);
        p_109296_.put(Blocks.f_50368_, $$4);
        p_109296_.put(Blocks.f_50369_, $$4);
        p_109296_.put(Blocks.f_50370_, $$4);
        p_109296_.put(Blocks.f_50371_, $$4);
        p_109296_.put(Blocks.f_50374_, $$4);
        p_109296_.put(Blocks.f_50719_, $$4);
        p_109296_.put(Blocks.f_50449_, $$4);
        p_109296_.put(Blocks.f_50628_, $$4);
        p_109296_.put(Blocks.f_152498_, $$4);
        p_109296_.put(Blocks.f_220862_, $$4);
    });
    private static final Map<Fluid, RenderType> f_109276_ = Util.m_137469_(Maps.newHashMap(), p_109290_ -> {
        RenderType $$1 = RenderType.m_110466_();
        p_109290_.put(Fluids.f_76192_, $$1);
        p_109290_.put(Fluids.f_76193_, $$1);
    });
    private static boolean f_109277_;

    public static RenderType m_109282_(BlockState p_109283_) {
        Block $$1 = p_109283_.m_60734_();
        if ($$1 instanceof LeavesBlock) {
            return f_109277_ ? RenderType.m_110457_() : RenderType.m_110451_();
        }
        RenderType $$2 = f_109275_.get($$1);
        if ($$2 != null) {
            return $$2;
        }
        return RenderType.m_110451_();
    }

    public static RenderType m_109293_(BlockState p_109294_) {
        Block $$1 = p_109294_.m_60734_();
        if ($$1 instanceof LeavesBlock) {
            return f_109277_ ? RenderType.m_110457_() : RenderType.m_110451_();
        }
        RenderType $$2 = f_109275_.get($$1);
        if ($$2 != null) {
            if ($$2 == RenderType.m_110466_()) {
                return RenderType.m_110469_();
            }
            return $$2;
        }
        return RenderType.m_110451_();
    }

    public static RenderType m_109284_(BlockState p_109285_, boolean p_109286_) {
        RenderType $$2 = ItemBlockRenderTypes.m_109282_(p_109285_);
        if ($$2 == RenderType.m_110466_()) {
            if (!Minecraft.m_91085_()) {
                return Sheets.m_110792_();
            }
            return p_109286_ ? Sheets.m_110792_() : Sheets.m_110791_();
        }
        return Sheets.m_110790_();
    }

    public static RenderType m_109279_(ItemStack p_109280_, boolean p_109281_) {
        Item $$2 = p_109280_.m_41720_();
        if ($$2 instanceof BlockItem) {
            Block $$3 = ((BlockItem)$$2).m_40614_();
            return ItemBlockRenderTypes.m_109284_($$3.m_49966_(), p_109281_);
        }
        return p_109281_ ? Sheets.m_110792_() : Sheets.m_110791_();
    }

    public static RenderType m_109287_(FluidState p_109288_) {
        RenderType $$1 = f_109276_.get(p_109288_.m_76152_());
        if ($$1 != null) {
            return $$1;
        }
        return RenderType.m_110451_();
    }

    public static void m_109291_(boolean p_109292_) {
        f_109277_ = p_109292_;
    }
}

