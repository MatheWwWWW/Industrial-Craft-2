/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class EndCityPieces {
    private static final int f_227420_ = 8;
    static final SectionGenerator f_227421_ = new SectionGenerator(){

        @Override
        public void m_213717_() {
        }

        @Override
        public boolean m_214120_(StructureTemplateManager p_227456_, int p_227457_, EndCityPiece p_227458_, BlockPos p_227459_, List<StructurePiece> p_227460_, RandomSource p_227461_) {
            if (p_227457_ > 8) {
                return false;
            }
            Rotation $$6 = p_227458_.m_226913_().m_74404_();
            EndCityPiece $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, p_227458_, p_227459_, "base_floor", $$6, true));
            int $$8 = p_227461_.m_188503_(3);
            if ($$8 == 0) {
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 4, -1), "base_roof", $$6, true));
            } else if ($$8 == 1) {
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 8, -1), "second_roof", $$6, false));
                EndCityPieces.m_227436_(p_227456_, f_227423_, p_227457_ + 1, $$7, null, p_227460_, p_227461_);
            } else if ($$8 == 2) {
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 4, -1), "third_floor_2", $$6, false));
                $$7 = EndCityPieces.m_227450_(p_227460_, EndCityPieces.m_227429_(p_227456_, $$7, new BlockPos(-1, 8, -1), "third_roof", $$6, true));
                EndCityPieces.m_227436_(p_227456_, f_227423_, p_227457_ + 1, $$7, null, p_227460_, p_227461_);
            }
            return true;
        }
    };
    static final List<Tuple<Rotation, BlockPos>> f_227422_ = Lists.newArrayList((Object[])new Tuple[]{new Tuple<Rotation, BlockPos>(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple<Rotation, BlockPos>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6))});
    static final SectionGenerator f_227423_ = new SectionGenerator(){

        @Override
        public void m_213717_() {
        }

        @Override
        public boolean m_214120_(StructureTemplateManager p_227465_, int p_227466_, EndCityPiece p_227467_, BlockPos p_227468_, List<StructurePiece> p_227469_, RandomSource p_227470_) {
            Rotation $$6 = p_227467_.m_226913_().m_74404_();
            EndCityPiece $$7 = p_227467_;
            $$7 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$7, new BlockPos(3 + p_227470_.m_188503_(2), -3, 3 + p_227470_.m_188503_(2)), "tower_base", $$6, true));
            $$7 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$7, new BlockPos(0, 7, 0), "tower_piece", $$6, true));
            EndCityPiece $$8 = p_227470_.m_188503_(3) == 0 ? $$7 : null;
            int $$9 = 1 + p_227470_.m_188503_(3);
            for (int $$10 = 0; $$10 < $$9; ++$$10) {
                $$7 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$7, new BlockPos(0, 4, 0), "tower_piece", $$6, true));
                if ($$10 >= $$9 - 1 || !p_227470_.m_188499_()) continue;
                $$8 = $$7;
            }
            if ($$8 != null) {
                for (Tuple<Rotation, BlockPos> $$11 : f_227422_) {
                    if (!p_227470_.m_188499_()) continue;
                    EndCityPiece $$12 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$8, $$11.m_14419_(), "bridge_end", $$6.m_55952_($$11.m_14418_()), true));
                    EndCityPieces.m_227436_(p_227465_, f_227424_, p_227466_ + 1, $$12, null, p_227469_, p_227470_);
                }
                $$7 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
            } else if (p_227466_ == 7) {
                $$7 = EndCityPieces.m_227450_(p_227469_, EndCityPieces.m_227429_(p_227465_, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
            } else {
                return EndCityPieces.m_227436_(p_227465_, f_227426_, p_227466_ + 1, $$7, null, p_227469_, p_227470_);
            }
            return true;
        }
    };
    static final SectionGenerator f_227424_ = new SectionGenerator(){
        public boolean f_227471_;

        @Override
        public void m_213717_() {
            this.f_227471_ = false;
        }

        @Override
        public boolean m_214120_(StructureTemplateManager p_227475_, int p_227476_, EndCityPiece p_227477_, BlockPos p_227478_, List<StructurePiece> p_227479_, RandomSource p_227480_) {
            Rotation $$6 = p_227477_.m_226913_().m_74404_();
            int $$7 = p_227480_.m_188503_(4) + 1;
            EndCityPiece $$8 = EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, p_227477_, new BlockPos(0, 0, -4), "bridge_piece", $$6, true));
            $$8.m_226758_(-1);
            int $$9 = 0;
            for (int $$10 = 0; $$10 < $$7; ++$$10) {
                if (p_227480_.m_188499_()) {
                    $$8 = EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, $$8, new BlockPos(0, $$9, -4), "bridge_piece", $$6, true));
                    $$9 = 0;
                    continue;
                }
                $$8 = p_227480_.m_188499_() ? EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, $$8, new BlockPos(0, $$9, -4), "bridge_steep_stairs", $$6, true)) : EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, $$8, new BlockPos(0, $$9, -8), "bridge_gentle_stairs", $$6, true));
                $$9 = 4;
            }
            if (this.f_227471_ || p_227480_.m_188503_(10 - p_227476_) != 0) {
                if (!EndCityPieces.m_227436_(p_227475_, f_227421_, p_227476_ + 1, $$8, new BlockPos(-3, $$9 + 1, -11), p_227479_, p_227480_)) {
                    return false;
                }
            } else {
                EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, $$8, new BlockPos(-8 + p_227480_.m_188503_(8), $$9, -70 + p_227480_.m_188503_(10)), "ship", $$6, true));
                this.f_227471_ = true;
            }
            $$8 = EndCityPieces.m_227450_(p_227479_, EndCityPieces.m_227429_(p_227475_, $$8, new BlockPos(4, $$9, 0), "bridge_end", $$6.m_55952_(Rotation.CLOCKWISE_180), true));
            $$8.m_226758_(-1);
            return true;
        }
    };
    static final List<Tuple<Rotation, BlockPos>> f_227425_ = Lists.newArrayList((Object[])new Tuple[]{new Tuple<Rotation, BlockPos>(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple<Rotation, BlockPos>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple<Rotation, BlockPos>(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12))});
    static final SectionGenerator f_227426_ = new SectionGenerator(){

        @Override
        public void m_213717_() {
        }

        @Override
        public boolean m_214120_(StructureTemplateManager p_227484_, int p_227485_, EndCityPiece p_227486_, BlockPos p_227487_, List<StructurePiece> p_227488_, RandomSource p_227489_) {
            Rotation $$6 = p_227486_.m_226913_().m_74404_();
            EndCityPiece $$7 = EndCityPieces.m_227450_(p_227488_, EndCityPieces.m_227429_(p_227484_, p_227486_, new BlockPos(-3, 4, -3), "fat_tower_base", $$6, true));
            $$7 = EndCityPieces.m_227450_(p_227488_, EndCityPieces.m_227429_(p_227484_, $$7, new BlockPos(0, 4, 0), "fat_tower_middle", $$6, true));
            for (int $$8 = 0; $$8 < 2 && p_227489_.m_188503_(3) != 0; ++$$8) {
                $$7 = EndCityPieces.m_227450_(p_227488_, EndCityPieces.m_227429_(p_227484_, $$7, new BlockPos(0, 8, 0), "fat_tower_middle", $$6, true));
                for (Tuple<Rotation, BlockPos> $$9 : f_227425_) {
                    if (!p_227489_.m_188499_()) continue;
                    EndCityPiece $$10 = EndCityPieces.m_227450_(p_227488_, EndCityPieces.m_227429_(p_227484_, $$7, $$9.m_14419_(), "bridge_end", $$6.m_55952_($$9.m_14418_()), true));
                    EndCityPieces.m_227436_(p_227484_, f_227424_, p_227485_ + 1, $$10, null, p_227488_, p_227489_);
                }
            }
            $$7 = EndCityPieces.m_227450_(p_227488_, EndCityPieces.m_227429_(p_227484_, $$7, new BlockPos(-2, 8, -2), "fat_tower_top", $$6, true));
            return true;
        }
    };

    static EndCityPiece m_227429_(StructureTemplateManager p_227430_, EndCityPiece p_227431_, BlockPos p_227432_, String p_227433_, Rotation p_227434_, boolean p_227435_) {
        EndCityPiece $$6 = new EndCityPiece(p_227430_, p_227433_, p_227431_.m_226912_(), p_227434_, p_227435_);
        BlockPos $$7 = p_227431_.m_226911_().m_74566_(p_227431_.m_226913_(), p_227432_, $$6.m_226913_(), BlockPos.f_121853_);
        $$6.m_6324_($$7.m_123341_(), $$7.m_123342_(), $$7.m_123343_());
        return $$6;
    }

    public static void m_227444_(StructureTemplateManager p_227445_, BlockPos p_227446_, Rotation p_227447_, List<StructurePiece> p_227448_, RandomSource p_227449_) {
        f_227426_.m_213717_();
        f_227421_.m_213717_();
        f_227424_.m_213717_();
        f_227423_.m_213717_();
        EndCityPiece $$5 = EndCityPieces.m_227450_(p_227448_, new EndCityPiece(p_227445_, "base_floor", p_227446_, p_227447_, true));
        $$5 = EndCityPieces.m_227450_(p_227448_, EndCityPieces.m_227429_(p_227445_, $$5, new BlockPos(-1, 0, -1), "second_floor_1", p_227447_, false));
        $$5 = EndCityPieces.m_227450_(p_227448_, EndCityPieces.m_227429_(p_227445_, $$5, new BlockPos(-1, 4, -1), "third_floor_1", p_227447_, false));
        $$5 = EndCityPieces.m_227450_(p_227448_, EndCityPieces.m_227429_(p_227445_, $$5, new BlockPos(-1, 8, -1), "third_roof", p_227447_, true));
        EndCityPieces.m_227436_(p_227445_, f_227423_, 1, $$5, null, p_227448_, p_227449_);
    }

    static EndCityPiece m_227450_(List<StructurePiece> p_227451_, EndCityPiece p_227452_) {
        p_227451_.add(p_227452_);
        return p_227452_;
    }

    static boolean m_227436_(StructureTemplateManager p_227437_, SectionGenerator p_227438_, int p_227439_, EndCityPiece p_227440_, BlockPos p_227441_, List<StructurePiece> p_227442_, RandomSource p_227443_) {
        if (p_227439_ > 8) {
            return false;
        }
        ArrayList $$7 = Lists.newArrayList();
        if (p_227438_.m_214120_(p_227437_, p_227439_, p_227440_, p_227441_, $$7, p_227443_)) {
            boolean $$8 = false;
            int $$9 = p_227443_.m_188502_();
            for (StructurePiece $$10 : $$7) {
                $$10.m_226758_($$9);
                StructurePiece $$11 = StructurePiece.m_192648_(p_227442_, $$10.m_73547_());
                if ($$11 == null || $$11.m_73548_() == p_227440_.m_73548_()) continue;
                $$8 = true;
                break;
            }
            if (!$$8) {
                p_227442_.addAll($$7);
                return true;
            }
        }
        return false;
    }

    public static class EndCityPiece
    extends TemplateStructurePiece {
        public EndCityPiece(StructureTemplateManager p_227491_, String p_227492_, BlockPos p_227493_, Rotation p_227494_, boolean p_227495_) {
            super(StructurePieceType.f_210119_, 0, p_227491_, EndCityPiece.m_227502_(p_227492_), p_227492_, EndCityPiece.m_227513_(p_227495_, p_227494_), p_227493_);
        }

        public EndCityPiece(StructureTemplateManager p_227497_, CompoundTag p_227498_) {
            super(StructurePieceType.f_210119_, p_227498_, p_227497_, p_227512_ -> EndCityPiece.m_227513_(p_227498_.m_128471_("OW"), Rotation.valueOf(p_227498_.m_128461_("Rot"))));
        }

        private static StructurePlaceSettings m_227513_(boolean p_227514_, Rotation p_227515_) {
            BlockIgnoreProcessor $$2 = p_227514_ ? BlockIgnoreProcessor.f_74046_ : BlockIgnoreProcessor.f_74048_;
            return new StructurePlaceSettings().m_74392_(true).m_74383_($$2).m_74379_(p_227515_);
        }

        @Override
        protected ResourceLocation m_142415_() {
            return EndCityPiece.m_227502_(this.f_163658_);
        }

        private static ResourceLocation m_227502_(String p_227503_) {
            return new ResourceLocation("end_city/" + p_227503_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227500_, CompoundTag p_227501_) {
            super.m_183620_(p_227500_, p_227501_);
            p_227501_.m_128359_("Rot", this.f_73657_.m_74404_().name());
            p_227501_.m_128379_("OW", this.f_73657_.m_74411_().get(0) == BlockIgnoreProcessor.f_74046_);
        }

        @Override
        protected void m_213704_(String p_227505_, BlockPos p_227506_, ServerLevelAccessor p_227507_, RandomSource p_227508_, BoundingBox p_227509_) {
            if (p_227505_.startsWith("Chest")) {
                BlockPos $$5 = p_227506_.m_7495_();
                if (p_227509_.m_71051_($$5)) {
                    RandomizableContainerBlockEntity.m_222766_(p_227507_, p_227508_, $$5, BuiltInLootTables.f_78741_);
                }
            } else if (p_227509_.m_71051_(p_227506_) && Level.m_46741_(p_227506_)) {
                if (p_227505_.startsWith("Sentry")) {
                    Shulker $$6 = EntityType.f_20521_.m_20615_(p_227507_.m_6018_());
                    $$6.m_6034_((double)p_227506_.m_123341_() + 0.5, p_227506_.m_123342_(), (double)p_227506_.m_123343_() + 0.5);
                    p_227507_.m_7967_($$6);
                } else if (p_227505_.startsWith("Elytra")) {
                    ItemFrame $$7 = new ItemFrame(p_227507_.m_6018_(), p_227506_, this.f_73657_.m_74404_().m_55954_(Direction.SOUTH));
                    $$7.m_31789_(new ItemStack(Items.f_42741_), false);
                    p_227507_.m_7967_($$7);
                }
            }
        }
    }

    static interface SectionGenerator {
        public void m_213717_();

        public boolean m_214120_(StructureTemplateManager var1, int var2, EndCityPiece var3, BlockPos var4, List<StructurePiece> var5, RandomSource var6);
    }
}

