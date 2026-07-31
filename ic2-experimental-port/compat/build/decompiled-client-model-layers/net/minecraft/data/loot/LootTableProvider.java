/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.google.common.collect.Sets$SetView
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.FishingLoot;
import net.minecraft.data.loot.GiftLoot;
import net.minecraft.data.loot.PiglinBarterLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.slf4j.Logger;

public class LootTableProvider
implements DataProvider {
    private static final Logger f_124431_ = LogUtils.getLogger();
    private final DataGenerator.PathProvider f_236267_;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> f_124434_ = ImmutableList.of((Object)Pair.of(FishingLoot::new, (Object)LootContextParamSets.f_81414_), (Object)Pair.of(ChestLoot::new, (Object)LootContextParamSets.f_81411_), (Object)Pair.of(EntityLoot::new, (Object)LootContextParamSets.f_81415_), (Object)Pair.of(BlockLoot::new, (Object)LootContextParamSets.f_81421_), (Object)Pair.of(PiglinBarterLoot::new, (Object)LootContextParamSets.f_81417_), (Object)Pair.of(GiftLoot::new, (Object)LootContextParamSets.f_81416_));

    public LootTableProvider(DataGenerator p_124437_) {
        this.f_236267_ = p_124437_.m_236036_(DataGenerator.Target.DATA_PACK, "loot_tables");
    }

    @Override
    public void m_213708_(CachedOutput p_236269_) {
        HashMap $$1 = Maps.newHashMap();
        this.f_124434_.forEach(p_124458_ -> ((Consumer)((Supplier)p_124458_.getFirst()).get()).accept((p_176077_, p_176078_) -> {
            if ($$1.put(p_176077_, p_176078_.m_79165_((LootContextParamSet)p_124458_.getSecond()).m_79167_()) != null) {
                throw new IllegalStateException("Duplicate loot table " + p_176077_);
            }
        }));
        ValidationContext $$2 = new ValidationContext(LootContextParamSets.f_81420_, p_124465_ -> null, $$1::get);
        Sets.SetView $$3 = Sets.difference(BuiltInLootTables.m_78766_(), $$1.keySet());
        for (ResourceLocation $$4 : $$3) {
            $$2.m_79357_("Missing built-in table: " + $$4);
        }
        $$1.forEach((p_124441_, p_124442_) -> LootTables.m_79202_($$2, p_124441_, p_124442_));
        Multimap<String, String> $$5 = $$2.m_79352_();
        if (!$$5.isEmpty()) {
            $$5.forEach((p_124446_, p_124447_) -> f_124431_.warn("Found validation problem in {}: {}", p_124446_, p_124447_));
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        }
        $$1.forEach((p_236272_, p_236273_) -> {
            Path $$3 = this.f_236267_.m_236048_((ResourceLocation)p_236272_);
            try {
                DataProvider.m_236072_(p_236269_, LootTables.m_79200_(p_236273_), $$3);
            }
            catch (IOException $$4) {
                f_124431_.error("Couldn't save loot table {}", (Object)$$3, (Object)$$4);
            }
        });
    }

    @Override
    public String m_6055_() {
        return "LootTables";
    }
}

