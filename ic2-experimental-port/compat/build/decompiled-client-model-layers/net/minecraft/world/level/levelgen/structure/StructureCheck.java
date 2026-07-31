/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2BooleanMap
 *  it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.visitors.CollectFields;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class StructureCheck {
    private static final Logger f_197235_ = LogUtils.getLogger();
    private static final int f_197236_ = -1;
    private final ChunkScanAccess f_197237_;
    private final RegistryAccess f_197238_;
    private final Registry<Biome> f_197239_;
    private final Registry<Structure> f_204945_;
    private final StructureTemplateManager f_226709_;
    private final ResourceKey<Level> f_197241_;
    private final ChunkGenerator f_197242_;
    private final RandomState f_226710_;
    private final LevelHeightAccessor f_197243_;
    private final BiomeSource f_197244_;
    private final long f_197245_;
    private final DataFixer f_197246_;
    private final Long2ObjectMap<Object2IntMap<Structure>> f_197247_ = new Long2ObjectOpenHashMap();
    private final Map<Structure, Long2BooleanMap> f_197248_ = new HashMap<Structure, Long2BooleanMap>();

    public StructureCheck(ChunkScanAccess p_226712_, RegistryAccess p_226713_, StructureTemplateManager p_226714_, ResourceKey<Level> p_226715_, ChunkGenerator p_226716_, RandomState p_226717_, LevelHeightAccessor p_226718_, BiomeSource p_226719_, long p_226720_, DataFixer p_226721_) {
        this.f_197237_ = p_226712_;
        this.f_197238_ = p_226713_;
        this.f_226709_ = p_226714_;
        this.f_197241_ = p_226715_;
        this.f_197242_ = p_226716_;
        this.f_226710_ = p_226717_;
        this.f_197243_ = p_226718_;
        this.f_197244_ = p_226719_;
        this.f_197245_ = p_226720_;
        this.f_197246_ = p_226721_;
        this.f_197239_ = p_226713_.m_206191_(Registry.f_122885_);
        this.f_204945_ = p_226713_.m_206191_(Registry.f_235725_);
    }

    public StructureCheckResult m_226729_(ChunkPos p_226730_, Structure p_226731_, boolean p_226732_) {
        long $$3 = p_226730_.m_45588_();
        Object2IntMap $$4 = (Object2IntMap)this.f_197247_.get($$3);
        if ($$4 != null) {
            return this.m_226751_((Object2IntMap<Structure>)$$4, p_226731_, p_226732_);
        }
        StructureCheckResult $$5 = this.m_226733_(p_226730_, p_226731_, p_226732_, $$3);
        if ($$5 != null) {
            return $$5;
        }
        boolean $$6 = this.f_197248_.computeIfAbsent(p_226731_, p_226739_ -> new Long2BooleanOpenHashMap()).computeIfAbsent($$3, p_226728_ -> this.m_226755_(p_226730_, p_226731_));
        if (!$$6) {
            return StructureCheckResult.START_NOT_PRESENT;
        }
        return StructureCheckResult.CHUNK_LOAD_NEEDED;
    }

    private boolean m_226755_(ChunkPos p_226756_, Structure p_226757_) {
        return p_226757_.m_214086_(new Structure.GenerationContext(this.f_197238_, this.f_197242_, this.f_197244_, this.f_226710_, this.f_226709_, this.f_197245_, p_226756_, this.f_197243_, p_226757_.m_226559_()::m_203333_)).isPresent();
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private StructureCheckResult m_226733_(ChunkPos p_226734_, Structure p_226735_, boolean p_226736_, long p_226737_) {
        void $$11;
        CollectFields $$4 = new CollectFields(new FieldSelector(IntTag.f_128670_, "DataVersion"), new FieldSelector("Level", "Structures", CompoundTag.f_128326_, "Starts"), new FieldSelector("structures", CompoundTag.f_128326_, "starts"));
        try {
            this.f_197237_.m_196358_(p_226734_, $$4).join();
        }
        catch (Exception $$5) {
            f_197235_.warn("Failed to read chunk {}", (Object)p_226734_, (Object)$$5);
            return StructureCheckResult.CHUNK_LOAD_NEEDED;
        }
        Tag $$6 = $$4.m_197713_();
        if (!($$6 instanceof CompoundTag)) {
            return null;
        }
        CompoundTag $$7 = (CompoundTag)$$6;
        int $$8 = ChunkStorage.m_63505_($$7);
        if ($$8 <= 1493) {
            return StructureCheckResult.CHUNK_LOAD_NEEDED;
        }
        ChunkStorage.m_196918_($$7, this.f_197241_, this.f_197242_.m_187743_());
        try {
            CompoundTag $$9 = NbtUtils.m_129213_(this.f_197246_, DataFixTypes.CHUNK, $$7, $$8);
        }
        catch (Exception $$10) {
            f_197235_.warn("Failed to partially datafix chunk {}", (Object)p_226734_, (Object)$$10);
            return StructureCheckResult.CHUNK_LOAD_NEEDED;
        }
        Object2IntMap<Structure> $$12 = this.m_197311_((CompoundTag)$$11);
        if ($$12 == null) {
            return null;
        }
        this.m_197263_(p_226737_, $$12);
        return this.m_226751_($$12, p_226735_, p_226736_);
    }

    @Nullable
    private Object2IntMap<Structure> m_197311_(CompoundTag p_197312_) {
        if (!p_197312_.m_128425_("structures", 10)) {
            return null;
        }
        CompoundTag $$1 = p_197312_.m_128469_("structures");
        if (!$$1.m_128425_("starts", 10)) {
            return null;
        }
        CompoundTag $$2 = $$1.m_128469_("starts");
        if ($$2.m_128456_()) {
            return Object2IntMaps.emptyMap();
        }
        Object2IntOpenHashMap $$3 = new Object2IntOpenHashMap();
        Registry<Structure> $$4 = this.f_197238_.m_175515_(Registry.f_235725_);
        for (String $$5 : $$2.m_128431_()) {
            String $$9;
            CompoundTag $$8;
            Structure $$7;
            ResourceLocation $$6 = ResourceLocation.m_135820_($$5);
            if ($$6 == null || ($$7 = $$4.m_7745_($$6)) == null || ($$8 = $$2.m_128469_($$5)).m_128456_() || "INVALID".equals($$9 = $$8.m_128461_("id"))) continue;
            int $$10 = $$8.m_128451_("references");
            $$3.put((Object)$$7, $$10);
        }
        return $$3;
    }

    private static Object2IntMap<Structure> m_197298_(Object2IntMap<Structure> p_197299_) {
        return p_197299_.isEmpty() ? Object2IntMaps.emptyMap() : p_197299_;
    }

    private StructureCheckResult m_226751_(Object2IntMap<Structure> p_226752_, Structure p_226753_, boolean p_226754_) {
        int $$3 = p_226752_.getOrDefault((Object)p_226753_, -1);
        return $$3 != -1 && (!p_226754_ || $$3 == 0) ? StructureCheckResult.START_PRESENT : StructureCheckResult.START_NOT_PRESENT;
    }

    public void m_197282_(ChunkPos p_197283_, Map<Structure, StructureStart> p_197284_) {
        long $$2 = p_197283_.m_45588_();
        Object2IntOpenHashMap $$3 = new Object2IntOpenHashMap();
        p_197284_.forEach((arg_0, arg_1) -> StructureCheck.m_226747_((Object2IntMap)$$3, arg_0, arg_1));
        this.m_197263_($$2, (Object2IntMap<Structure>)$$3);
    }

    private void m_197263_(long p_197264_, Object2IntMap<Structure> p_197265_) {
        this.f_197247_.put(p_197264_, StructureCheck.m_197298_(p_197265_));
        this.f_197248_.values().forEach(p_209956_ -> p_209956_.remove(p_197264_));
    }

    public void m_226722_(ChunkPos p_226723_, Structure p_226724_) {
        this.f_197247_.compute(p_226723_.m_45588_(), (p_226745_, p_226746_) -> {
            if (p_226746_ == null || p_226746_.isEmpty()) {
                p_226746_ = new Object2IntOpenHashMap();
            }
            p_226746_.computeInt((Object)p_226724_, (p_226741_, p_226742_) -> p_226742_ == null ? 1 : p_226742_ + 1);
            return p_226746_;
        });
    }

    private static /* synthetic */ void m_226747_(Object2IntMap p_226748_, Structure p_226749_, StructureStart p_226750_) {
        if (p_226750_.m_73603_()) {
            p_226748_.put((Object)p_226749_, p_226750_.m_73608_());
        }
    }
}

