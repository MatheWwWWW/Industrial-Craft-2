/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlackstoneReplaceProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.LavaSubmergedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.NopProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public interface StructureProcessorType<P extends StructureProcessor> {
    public static final StructureProcessorType<BlockIgnoreProcessor> f_74456_ = StructureProcessorType.m_74476_("block_ignore", BlockIgnoreProcessor.f_74045_);
    public static final StructureProcessorType<BlockRotProcessor> f_74457_ = StructureProcessorType.m_74476_("block_rot", BlockRotProcessor.f_74074_);
    public static final StructureProcessorType<GravityProcessor> f_74458_ = StructureProcessorType.m_74476_("gravity", GravityProcessor.f_74100_);
    public static final StructureProcessorType<JigsawReplacementProcessor> f_74459_ = StructureProcessorType.m_74476_("jigsaw_replacement", JigsawReplacementProcessor.f_74121_);
    public static final StructureProcessorType<RuleProcessor> f_74460_ = StructureProcessorType.m_74476_("rule", RuleProcessor.f_74292_);
    public static final StructureProcessorType<NopProcessor> f_74461_ = StructureProcessorType.m_74476_("nop", NopProcessor.f_74174_);
    public static final StructureProcessorType<BlockAgeProcessor> f_74462_ = StructureProcessorType.m_74476_("block_age", BlockAgeProcessor.f_74009_);
    public static final StructureProcessorType<BlackstoneReplaceProcessor> f_74463_ = StructureProcessorType.m_74476_("blackstone_replace", BlackstoneReplaceProcessor.f_73993_);
    public static final StructureProcessorType<LavaSubmergedBlockProcessor> f_74464_ = StructureProcessorType.m_74476_("lava_submerged_block", LavaSubmergedBlockProcessor.f_74134_);
    public static final StructureProcessorType<ProtectedBlockProcessor> f_163784_ = StructureProcessorType.m_74476_("protected_blocks", ProtectedBlockProcessor.f_163749_);
    public static final Codec<StructureProcessor> f_74465_ = Registry.f_122891_.m_194605_().dispatch("processor_type", StructureProcessor::m_6953_, StructureProcessorType::m_74481_);
    public static final Codec<StructureProcessorList> f_74466_ = f_74465_.listOf().xmap(StructureProcessorList::new, StructureProcessorList::m_74425_);
    public static final Codec<StructureProcessorList> f_74467_ = Codec.either((Codec)f_74466_.fieldOf("processors").codec(), f_74466_).xmap(p_74471_ -> (StructureProcessorList)p_74471_.map(p_163788_ -> p_163788_, p_163786_ -> p_163786_), Either::left);
    public static final Codec<Holder<StructureProcessorList>> f_74468_ = RegistryFileCodec.m_135589_(Registry.f_122883_, f_74467_);

    public Codec<P> m_74481_();

    public static <P extends StructureProcessor> StructureProcessorType<P> m_74476_(String p_74477_, Codec<P> p_74478_) {
        return Registry.m_122961_(Registry.f_122891_, p_74477_, () -> p_74478_);
    }
}

