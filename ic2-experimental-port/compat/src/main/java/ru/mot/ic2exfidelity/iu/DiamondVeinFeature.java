package ru.mot.ic2exfidelity.iu;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

/** Finite underground ore cluster with a surface deposit marker in new chunks. */
public final class DiamondVeinFeature extends Feature<NoneFeatureConfiguration> {
    public DiamondVeinFeature() { super(NoneFeatureConfiguration.f_67815_); }
    @Override public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var world = context.m_159774_();
        var random = context.m_225041_();
        BlockPos origin = context.m_159777_();
        int y = world.m_141937_() + 12 + random.m_188503_(20);
        int count = 0;
        for (int i = 0; i < 400; i++) {
            BlockPos pos = new BlockPos(origin.m_123341_() + random.m_188503_(16), y + random.m_188503_(12), origin.m_123343_() + random.m_188503_(16));
            if (!world.m_8055_(pos).m_204336_(Tags.Blocks.STONE)) continue;
            int chance = random.m_188503_(100);
            if (chance < 6) {
                world.m_7731_(pos, (chance == 0 ? Blocks.f_152479_ : Blocks.f_152474_).m_49966_(), 2);
                count++;
            }
        }
        if (count == 0) return false;
        BlockPos surface = world.m_5452_(Heightmap.Types.WORLD_SURFACE_WG, origin.m_7918_(8, 0, 8));
        if (world.m_46859_(surface) && world.m_8055_(surface.m_7495_()).m_60767_().m_76333_()) {
            world.m_7731_(surface, ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamondvein", "diamond_deposits/deposits_diamond")).m_49966_(), 2);
        }
        return true;
    }
}
