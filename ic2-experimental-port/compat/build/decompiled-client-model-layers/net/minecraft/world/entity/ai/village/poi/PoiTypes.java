/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 */
package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class PoiTypes {
    public static final ResourceKey<PoiType> f_218047_ = PoiTypes.m_218090_("armorer");
    public static final ResourceKey<PoiType> f_218048_ = PoiTypes.m_218090_("butcher");
    public static final ResourceKey<PoiType> f_218049_ = PoiTypes.m_218090_("cartographer");
    public static final ResourceKey<PoiType> f_218050_ = PoiTypes.m_218090_("cleric");
    public static final ResourceKey<PoiType> f_218051_ = PoiTypes.m_218090_("farmer");
    public static final ResourceKey<PoiType> f_218052_ = PoiTypes.m_218090_("fisherman");
    public static final ResourceKey<PoiType> f_218053_ = PoiTypes.m_218090_("fletcher");
    public static final ResourceKey<PoiType> f_218054_ = PoiTypes.m_218090_("leatherworker");
    public static final ResourceKey<PoiType> f_218055_ = PoiTypes.m_218090_("librarian");
    public static final ResourceKey<PoiType> f_218056_ = PoiTypes.m_218090_("mason");
    public static final ResourceKey<PoiType> f_218057_ = PoiTypes.m_218090_("shepherd");
    public static final ResourceKey<PoiType> f_218058_ = PoiTypes.m_218090_("toolsmith");
    public static final ResourceKey<PoiType> f_218059_ = PoiTypes.m_218090_("weaponsmith");
    public static final ResourceKey<PoiType> f_218060_ = PoiTypes.m_218090_("home");
    public static final ResourceKey<PoiType> f_218061_ = PoiTypes.m_218090_("meeting");
    public static final ResourceKey<PoiType> f_218062_ = PoiTypes.m_218090_("beehive");
    public static final ResourceKey<PoiType> f_218063_ = PoiTypes.m_218090_("bee_nest");
    public static final ResourceKey<PoiType> f_218064_ = PoiTypes.m_218090_("nether_portal");
    public static final ResourceKey<PoiType> f_218065_ = PoiTypes.m_218090_("lodestone");
    public static final ResourceKey<PoiType> f_218066_ = PoiTypes.m_218090_("lightning_rod");
    private static final Set<BlockState> f_218068_ = (Set)ImmutableList.of((Object)Blocks.f_50028_, (Object)Blocks.f_50029_, (Object)Blocks.f_50025_, (Object)Blocks.f_50026_, (Object)Blocks.f_50023_, (Object)Blocks.f_50021_, (Object)Blocks.f_50027_, (Object)Blocks.f_50017_, (Object)Blocks.f_50022_, (Object)Blocks.f_50019_, (Object)Blocks.f_50068_, (Object)Blocks.f_50067_, (Object[])new Block[]{Blocks.f_50020_, Blocks.f_50024_, Blocks.f_50066_, Blocks.f_50018_}).stream().flatMap(p_218097_ -> p_218097_.m_49965_().m_61056_().stream()).filter(p_218095_ -> p_218095_.m_61143_(BedBlock.f_49440_) == BedPart.HEAD).collect(ImmutableSet.toImmutableSet());
    private static final Set<BlockState> f_218069_ = (Set)ImmutableList.of((Object)Blocks.f_50256_, (Object)Blocks.f_152477_, (Object)Blocks.f_152476_, (Object)Blocks.f_152478_).stream().flatMap(p_218093_ -> p_218093_.m_49965_().m_61056_().stream()).collect(ImmutableSet.toImmutableSet());
    private static final Map<BlockState, Holder<PoiType>> f_218070_ = Maps.newHashMap();
    protected static final Set<BlockState> f_218067_ = new ObjectOpenHashSet(f_218070_.keySet());

    private static Set<BlockState> m_218073_(Block p_218074_) {
        return ImmutableSet.copyOf(p_218074_.m_49965_().m_61056_());
    }

    private static ResourceKey<PoiType> m_218090_(String p_218091_) {
        return ResourceKey.m_135785_(Registry.f_122810_, new ResourceLocation(p_218091_));
    }

    private static PoiType m_218084_(Registry<PoiType> p_218085_, ResourceKey<PoiType> p_218086_, Set<BlockState> p_218087_, int p_218088_, int p_218089_) {
        PoiType $$5 = new PoiType(p_218087_, p_218088_, p_218089_);
        Registry.m_194579_(p_218085_, p_218086_, $$5);
        PoiTypes.m_218077_(p_218085_.m_206081_(p_218086_));
        return $$5;
    }

    private static void m_218077_(Holder<PoiType> p_218078_) {
        p_218078_.m_203334_().f_27325_().forEach(p_218081_ -> {
            Holder<PoiType> $$2 = f_218070_.put((BlockState)p_218081_, p_218078_);
            if ($$2 != null) {
                throw Util.m_137570_(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", p_218081_)));
            }
        });
    }

    public static Optional<Holder<PoiType>> m_218075_(BlockState p_218076_) {
        return Optional.ofNullable(f_218070_.get(p_218076_));
    }

    public static PoiType m_218082_(Registry<PoiType> p_218083_) {
        PoiTypes.m_218084_(p_218083_, f_218047_, PoiTypes.m_218073_(Blocks.f_50620_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218048_, PoiTypes.m_218073_(Blocks.f_50619_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218049_, PoiTypes.m_218073_(Blocks.f_50621_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218050_, PoiTypes.m_218073_(Blocks.f_50255_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218051_, PoiTypes.m_218073_(Blocks.f_50715_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218052_, PoiTypes.m_218073_(Blocks.f_50618_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218053_, PoiTypes.m_218073_(Blocks.f_50622_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218054_, f_218069_, 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218055_, PoiTypes.m_218073_(Blocks.f_50624_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218056_, PoiTypes.m_218073_(Blocks.f_50679_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218057_, PoiTypes.m_218073_(Blocks.f_50617_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218058_, PoiTypes.m_218073_(Blocks.f_50625_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218059_, PoiTypes.m_218073_(Blocks.f_50623_), 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218060_, f_218068_, 1, 1);
        PoiTypes.m_218084_(p_218083_, f_218061_, PoiTypes.m_218073_(Blocks.f_50680_), 32, 6);
        PoiTypes.m_218084_(p_218083_, f_218062_, PoiTypes.m_218073_(Blocks.f_50718_), 0, 1);
        PoiTypes.m_218084_(p_218083_, f_218063_, PoiTypes.m_218073_(Blocks.f_50717_), 0, 1);
        PoiTypes.m_218084_(p_218083_, f_218064_, PoiTypes.m_218073_(Blocks.f_50142_), 0, 1);
        PoiTypes.m_218084_(p_218083_, f_218065_, PoiTypes.m_218073_(Blocks.f_50729_), 0, 1);
        return PoiTypes.m_218084_(p_218083_, f_218066_, PoiTypes.m_218073_(Blocks.f_152587_), 0, 1);
    }
}

