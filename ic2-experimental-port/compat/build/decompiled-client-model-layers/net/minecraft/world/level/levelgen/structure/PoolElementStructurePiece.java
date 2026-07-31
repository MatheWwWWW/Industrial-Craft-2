/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class PoolElementStructurePiece
extends StructurePiece {
    private static final Logger f_72600_ = LogUtils.getLogger();
    protected final StructurePoolElement f_72597_;
    protected BlockPos f_72598_;
    private final int f_72601_;
    protected final Rotation f_72599_;
    private final List<JigsawJunction> f_72602_ = Lists.newArrayList();
    private final StructureTemplateManager f_226493_;

    public PoolElementStructurePiece(StructureTemplateManager p_226495_, StructurePoolElement p_226496_, BlockPos p_226497_, int p_226498_, Rotation p_226499_, BoundingBox p_226500_) {
        super(StructurePieceType.f_210125_, 0, p_226500_);
        this.f_226493_ = p_226495_;
        this.f_72597_ = p_226496_;
        this.f_72598_ = p_226497_;
        this.f_72601_ = p_226498_;
        this.f_72599_ = p_226499_;
    }

    public PoolElementStructurePiece(StructurePieceSerializationContext p_192406_, CompoundTag p_192407_) {
        super(StructurePieceType.f_210125_, p_192407_);
        this.f_226493_ = p_192406_.f_226956_();
        this.f_72598_ = new BlockPos(p_192407_.m_128451_("PosX"), p_192407_.m_128451_("PosY"), p_192407_.m_128451_("PosZ"));
        this.f_72601_ = p_192407_.m_128451_("ground_level_delta");
        RegistryOps<Tag> $$2 = RegistryOps.m_206821_(NbtOps.f_128958_, p_192406_.f_192763_());
        this.f_72597_ = (StructurePoolElement)StructurePoolElement.f_210468_.parse($$2, (Object)p_192407_.m_128469_("pool_element")).resultOrPartial(arg_0 -> ((Logger)f_72600_).error(arg_0)).orElseThrow(() -> new IllegalStateException("Invalid pool element found"));
        this.f_72599_ = Rotation.valueOf(p_192407_.m_128461_("rotation"));
        this.f_73383_ = this.f_72597_.m_214015_(this.f_226493_, this.f_72598_, this.f_72599_);
        ListTag $$3 = p_192407_.m_128437_("junctions", 10);
        this.f_72602_.clear();
        $$3.forEach(p_204943_ -> this.f_72602_.add(JigsawJunction.m_210253_(new Dynamic($$2, p_204943_))));
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_192425_, CompoundTag p_192426_) {
        p_192426_.m_128405_("PosX", this.f_72598_.m_123341_());
        p_192426_.m_128405_("PosY", this.f_72598_.m_123342_());
        p_192426_.m_128405_("PosZ", this.f_72598_.m_123343_());
        p_192426_.m_128405_("ground_level_delta", this.f_72601_);
        RegistryOps<Tag> $$2 = RegistryOps.m_206821_(NbtOps.f_128958_, p_192425_.f_192763_());
        StructurePoolElement.f_210468_.encodeStart($$2, (Object)this.f_72597_).resultOrPartial(arg_0 -> ((Logger)f_72600_).error(arg_0)).ifPresent(p_163125_ -> p_192426_.m_128365_("pool_element", (Tag)p_163125_));
        p_192426_.m_128359_("rotation", this.f_72599_.name());
        ListTag $$3 = new ListTag();
        for (JigsawJunction $$4 : this.f_72602_) {
            $$3.add((Tag)$$4.m_210255_($$2).getValue());
        }
        p_192426_.m_128365_("junctions", $$3);
    }

    @Override
    public void m_213694_(WorldGenLevel p_226502_, StructureManager p_226503_, ChunkGenerator p_226504_, RandomSource p_226505_, BoundingBox p_226506_, ChunkPos p_226507_, BlockPos p_226508_) {
        this.m_226509_(p_226502_, p_226503_, p_226504_, p_226505_, p_226506_, p_226508_, false);
    }

    public void m_226509_(WorldGenLevel p_226510_, StructureManager p_226511_, ChunkGenerator p_226512_, RandomSource p_226513_, BoundingBox p_226514_, BlockPos p_226515_, boolean p_226516_) {
        this.f_72597_.m_213695_(this.f_226493_, p_226510_, p_226511_, p_226512_, this.f_72598_, p_226515_, this.f_72599_, p_226514_, p_226513_, p_226516_);
    }

    @Override
    public void m_6324_(int p_72616_, int p_72617_, int p_72618_) {
        super.m_6324_(p_72616_, p_72617_, p_72618_);
        this.f_72598_ = this.f_72598_.m_7918_(p_72616_, p_72617_, p_72618_);
    }

    @Override
    public Rotation m_6830_() {
        return this.f_72599_;
    }

    public String toString() {
        return String.format(Locale.ROOT, "<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.f_72598_, this.f_72599_, this.f_72597_);
    }

    public StructurePoolElement m_209918_() {
        return this.f_72597_;
    }

    public BlockPos m_72646_() {
        return this.f_72598_;
    }

    public int m_72647_() {
        return this.f_72601_;
    }

    public void m_209916_(JigsawJunction p_209917_) {
        this.f_72602_.add(p_209917_);
    }

    public List<JigsawJunction> m_72648_() {
        return this.f_72602_;
    }
}

