/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class JungleTemplePiece
extends ScatteredFeaturePiece {
    public static final int f_227659_ = 12;
    public static final int f_227660_ = 15;
    private boolean f_227661_;
    private boolean f_227662_;
    private boolean f_227663_;
    private boolean f_227664_;
    private static final MossStoneSelector f_227665_ = new MossStoneSelector();

    public JungleTemplePiece(RandomSource p_227668_, int p_227669_, int p_227670_) {
        super(StructurePieceType.f_210101_, p_227669_, 64, p_227670_, 12, 10, 15, JungleTemplePiece.m_226760_(p_227668_));
    }

    public JungleTemplePiece(CompoundTag p_227672_) {
        super(StructurePieceType.f_210101_, p_227672_);
        this.f_227661_ = p_227672_.m_128471_("placedMainChest");
        this.f_227662_ = p_227672_.m_128471_("placedHiddenChest");
        this.f_227663_ = p_227672_.m_128471_("placedTrap1");
        this.f_227664_ = p_227672_.m_128471_("placedTrap2");
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_227682_, CompoundTag p_227683_) {
        super.m_183620_(p_227682_, p_227683_);
        p_227683_.m_128379_("placedMainChest", this.f_227661_);
        p_227683_.m_128379_("placedHiddenChest", this.f_227662_);
        p_227683_.m_128379_("placedTrap1", this.f_227663_);
        p_227683_.m_128379_("placedTrap2", this.f_227664_);
    }

    @Override
    public void m_213694_(WorldGenLevel p_227674_, StructureManager p_227675_, ChunkGenerator p_227676_, RandomSource p_227677_, BoundingBox p_227678_, ChunkPos p_227679_, BlockPos p_227680_) {
        if (!this.m_72803_(p_227674_, p_227678_, 0)) {
            return;
        }
        this.m_226776_(p_227674_, p_227678_, 0, -4, 0, this.f_72787_ - 1, 0, this.f_72789_ - 1, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 1, 2, 9, 2, 2, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 1, 12, 9, 2, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 1, 3, 2, 2, 11, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 9, 1, 3, 9, 2, 11, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 1, 3, 1, 10, 6, 1, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 1, 3, 13, 10, 6, 13, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 1, 3, 2, 1, 6, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 10, 3, 2, 10, 6, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 3, 2, 9, 3, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 6, 2, 9, 6, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 3, 7, 3, 8, 7, 11, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 4, 8, 4, 7, 8, 10, false, p_227677_, f_227665_);
        this.m_73535_(p_227674_, p_227678_, 3, 1, 3, 8, 2, 11);
        this.m_73535_(p_227674_, p_227678_, 4, 3, 6, 7, 3, 9);
        this.m_73535_(p_227674_, p_227678_, 2, 4, 2, 9, 5, 12);
        this.m_73535_(p_227674_, p_227678_, 4, 6, 5, 7, 6, 9);
        this.m_73535_(p_227674_, p_227678_, 5, 7, 6, 6, 7, 8);
        this.m_73535_(p_227674_, p_227678_, 5, 1, 2, 6, 2, 2);
        this.m_73535_(p_227674_, p_227678_, 5, 2, 12, 6, 2, 12);
        this.m_73535_(p_227674_, p_227678_, 5, 5, 1, 6, 5, 1);
        this.m_73535_(p_227674_, p_227678_, 5, 5, 13, 6, 5, 13);
        this.m_73434_(p_227674_, Blocks.f_50016_.m_49966_(), 1, 5, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50016_.m_49966_(), 10, 5, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50016_.m_49966_(), 1, 5, 9, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50016_.m_49966_(), 10, 5, 9, p_227678_);
        for (int $$7 = 0; $$7 <= 14; $$7 += 14) {
            this.m_226776_(p_227674_, p_227678_, 2, 4, $$7, 2, 5, $$7, false, p_227677_, f_227665_);
            this.m_226776_(p_227674_, p_227678_, 4, 4, $$7, 4, 5, $$7, false, p_227677_, f_227665_);
            this.m_226776_(p_227674_, p_227678_, 7, 4, $$7, 7, 5, $$7, false, p_227677_, f_227665_);
            this.m_226776_(p_227674_, p_227678_, 9, 4, $$7, 9, 5, $$7, false, p_227677_, f_227665_);
        }
        this.m_226776_(p_227674_, p_227678_, 5, 6, 0, 6, 6, 0, false, p_227677_, f_227665_);
        for (int $$8 = 0; $$8 <= 11; $$8 += 11) {
            for (int $$9 = 2; $$9 <= 12; $$9 += 2) {
                this.m_226776_(p_227674_, p_227678_, $$8, 4, $$9, $$8, 5, $$9, false, p_227677_, f_227665_);
            }
            this.m_226776_(p_227674_, p_227678_, $$8, 6, 5, $$8, 6, 5, false, p_227677_, f_227665_);
            this.m_226776_(p_227674_, p_227678_, $$8, 6, 9, $$8, 6, 9, false, p_227677_, f_227665_);
        }
        this.m_226776_(p_227674_, p_227678_, 2, 7, 2, 2, 9, 2, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 9, 7, 2, 9, 9, 2, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 2, 7, 12, 2, 9, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 9, 7, 12, 9, 9, 12, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 4, 9, 4, 4, 9, 4, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 7, 9, 4, 7, 9, 4, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 4, 9, 10, 4, 9, 10, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 7, 9, 10, 7, 9, 10, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 5, 9, 7, 6, 9, 7, false, p_227677_, f_227665_);
        BlockState $$10 = (BlockState)Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
        BlockState $$11 = (BlockState)Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
        BlockState $$12 = (BlockState)Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
        BlockState $$13 = (BlockState)Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
        this.m_73434_(p_227674_, $$13, 5, 9, 6, p_227678_);
        this.m_73434_(p_227674_, $$13, 6, 9, 6, p_227678_);
        this.m_73434_(p_227674_, $$12, 5, 9, 8, p_227678_);
        this.m_73434_(p_227674_, $$12, 6, 9, 8, p_227678_);
        this.m_73434_(p_227674_, $$13, 4, 0, 0, p_227678_);
        this.m_73434_(p_227674_, $$13, 5, 0, 0, p_227678_);
        this.m_73434_(p_227674_, $$13, 6, 0, 0, p_227678_);
        this.m_73434_(p_227674_, $$13, 7, 0, 0, p_227678_);
        this.m_73434_(p_227674_, $$13, 4, 1, 8, p_227678_);
        this.m_73434_(p_227674_, $$13, 4, 2, 9, p_227678_);
        this.m_73434_(p_227674_, $$13, 4, 3, 10, p_227678_);
        this.m_73434_(p_227674_, $$13, 7, 1, 8, p_227678_);
        this.m_73434_(p_227674_, $$13, 7, 2, 9, p_227678_);
        this.m_73434_(p_227674_, $$13, 7, 3, 10, p_227678_);
        this.m_226776_(p_227674_, p_227678_, 4, 1, 9, 4, 1, 9, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 7, 1, 9, 7, 1, 9, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 4, 1, 10, 7, 2, 10, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 5, 4, 5, 6, 4, 5, false, p_227677_, f_227665_);
        this.m_73434_(p_227674_, $$10, 4, 4, 5, p_227678_);
        this.m_73434_(p_227674_, $$11, 7, 4, 5, p_227678_);
        for (int $$14 = 0; $$14 < 4; ++$$14) {
            this.m_73434_(p_227674_, $$12, 5, 0 - $$14, 6 + $$14, p_227678_);
            this.m_73434_(p_227674_, $$12, 6, 0 - $$14, 6 + $$14, p_227678_);
            this.m_73535_(p_227674_, p_227678_, 5, 0 - $$14, 7 + $$14, 6, 0 - $$14, 9 + $$14);
        }
        this.m_73535_(p_227674_, p_227678_, 1, -3, 12, 10, -1, 13);
        this.m_73535_(p_227674_, p_227678_, 1, -3, 1, 3, -1, 13);
        this.m_73535_(p_227674_, p_227678_, 1, -3, 1, 9, -1, 5);
        for (int $$15 = 1; $$15 <= 13; $$15 += 2) {
            this.m_226776_(p_227674_, p_227678_, 1, -3, $$15, 1, -2, $$15, false, p_227677_, f_227665_);
        }
        for (int $$16 = 2; $$16 <= 12; $$16 += 2) {
            this.m_226776_(p_227674_, p_227678_, 1, -1, $$16, 3, -1, $$16, false, p_227677_, f_227665_);
        }
        this.m_226776_(p_227674_, p_227678_, 2, -2, 1, 5, -2, 1, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 7, -2, 1, 9, -2, 1, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 6, -3, 1, 6, -3, 1, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 6, -1, 1, 6, -1, 1, false, p_227677_, f_227665_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.EAST)).m_61124_(TripWireHookBlock.f_57669_, true), 1, -3, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.WEST)).m_61124_(TripWireHookBlock.f_57669_, true), 4, -3, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57594_, true)).m_61124_(TripWireBlock.f_57596_, true)).m_61124_(TripWireBlock.f_57591_, true), 2, -3, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57594_, true)).m_61124_(TripWireBlock.f_57596_, true)).m_61124_(TripWireBlock.f_57591_, true), 3, -3, 8, p_227678_);
        BlockState $$17 = (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE);
        this.m_73434_(p_227674_, $$17, 5, -3, 7, p_227678_);
        this.m_73434_(p_227674_, $$17, 5, -3, 6, p_227678_);
        this.m_73434_(p_227674_, $$17, 5, -3, 5, p_227678_);
        this.m_73434_(p_227674_, $$17, 5, -3, 4, p_227678_);
        this.m_73434_(p_227674_, $$17, 5, -3, 3, p_227678_);
        this.m_73434_(p_227674_, $$17, 5, -3, 2, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 5, -3, 1, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 4, -3, 1, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 3, -3, 1, p_227678_);
        if (!this.f_227663_) {
            this.f_227663_ = this.m_226819_(p_227674_, p_227678_, p_227677_, 3, -2, 1, Direction.NORTH, BuiltInLootTables.f_78687_);
        }
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57836_, true), 3, -2, 2, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.NORTH)).m_61124_(TripWireHookBlock.f_57669_, true), 7, -3, 1, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.SOUTH)).m_61124_(TripWireHookBlock.f_57669_, true), 7, -3, 5, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, true)).m_61124_(TripWireBlock.f_57595_, true)).m_61124_(TripWireBlock.f_57591_, true), 7, -3, 2, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, true)).m_61124_(TripWireBlock.f_57595_, true)).m_61124_(TripWireBlock.f_57591_, true), 7, -3, 3, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, true)).m_61124_(TripWireBlock.f_57595_, true)).m_61124_(TripWireBlock.f_57591_, true), 7, -3, 4, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 8, -3, 6, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE), 9, -3, 6, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.UP), 9, -3, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 9, -3, 4, p_227678_);
        this.m_73434_(p_227674_, $$17, 9, -2, 4, p_227678_);
        if (!this.f_227664_) {
            this.f_227664_ = this.m_226819_(p_227674_, p_227678_, p_227677_, 9, -2, 3, Direction.WEST, BuiltInLootTables.f_78687_);
        }
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57835_, true), 8, -1, 3, p_227678_);
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57835_, true), 8, -2, 3, p_227678_);
        if (!this.f_227661_) {
            this.f_227661_ = this.m_213787_(p_227674_, p_227678_, p_227677_, 8, -3, 3, BuiltInLootTables.f_78686_);
        }
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 9, -3, 2, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 8, -3, 1, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 4, -3, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 5, -2, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 5, -1, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 6, -3, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 7, -2, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 7, -1, 5, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 8, -3, 5, p_227678_);
        this.m_226776_(p_227674_, p_227678_, 9, -1, 1, 9, -1, 5, false, p_227677_, f_227665_);
        this.m_73535_(p_227674_, p_227678_, 8, -3, 8, 10, -1, 10);
        this.m_73434_(p_227674_, Blocks.f_50225_.m_49966_(), 8, -2, 11, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50225_.m_49966_(), 9, -2, 11, p_227678_);
        this.m_73434_(p_227674_, Blocks.f_50225_.m_49966_(), 10, -2, 11, p_227678_);
        BlockState $$18 = (BlockState)((BlockState)Blocks.f_50164_.m_49966_().m_61124_(LeverBlock.f_54117_, Direction.NORTH)).m_61124_(LeverBlock.f_53179_, AttachFace.WALL);
        this.m_73434_(p_227674_, $$18, 8, -2, 12, p_227678_);
        this.m_73434_(p_227674_, $$18, 9, -2, 12, p_227678_);
        this.m_73434_(p_227674_, $$18, 10, -2, 12, p_227678_);
        this.m_226776_(p_227674_, p_227678_, 8, -3, 8, 8, -3, 10, false, p_227677_, f_227665_);
        this.m_226776_(p_227674_, p_227678_, 10, -3, 8, 10, -3, 10, false, p_227677_, f_227665_);
        this.m_73434_(p_227674_, Blocks.f_50079_.m_49966_(), 10, -2, 9, p_227678_);
        this.m_73434_(p_227674_, $$17, 8, -2, 9, p_227678_);
        this.m_73434_(p_227674_, $$17, 8, -2, 10, p_227678_);
        this.m_73434_(p_227674_, (BlockState)((BlockState)((BlockState)((BlockState)Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE)).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 10, -1, 9, p_227678_);
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.UP), 9, -2, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.WEST), 10, -2, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.WEST), 10, -1, 8, p_227678_);
        this.m_73434_(p_227674_, (BlockState)Blocks.f_50146_.m_49966_().m_61124_(RepeaterBlock.f_54117_, Direction.NORTH), 10, -2, 10, p_227678_);
        if (!this.f_227662_) {
            this.f_227662_ = this.m_213787_(p_227674_, p_227678_, p_227677_, 9, -3, 10, BuiltInLootTables.f_78686_);
        }
    }

    static class MossStoneSelector
    extends StructurePiece.BlockSelector {
        MossStoneSelector() {
        }

        @Override
        public void m_213766_(RandomSource p_227686_, int p_227687_, int p_227688_, int p_227689_, boolean p_227690_) {
            this.f_73553_ = p_227686_.m_188501_() < 0.4f ? Blocks.f_50652_.m_49966_() : Blocks.f_50079_.m_49966_();
        }
    }
}

