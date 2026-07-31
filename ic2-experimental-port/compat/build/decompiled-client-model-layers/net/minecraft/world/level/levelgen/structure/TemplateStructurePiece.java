/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Function;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public abstract class TemplateStructurePiece
extends StructurePiece {
    private static final Logger f_73659_ = LogUtils.getLogger();
    protected final String f_163658_;
    protected StructureTemplate f_73656_;
    protected StructurePlaceSettings f_73657_;
    protected BlockPos f_73658_;

    public TemplateStructurePiece(StructurePieceType p_226886_, int p_226887_, StructureTemplateManager p_226888_, ResourceLocation p_226889_, String p_226890_, StructurePlaceSettings p_226891_, BlockPos p_226892_) {
        super(p_226886_, p_226887_, p_226888_.m_230359_(p_226889_).m_74633_(p_226891_, p_226892_));
        this.m_73519_(Direction.NORTH);
        this.f_163658_ = p_226890_;
        this.f_73658_ = p_226892_;
        this.f_73656_ = p_226888_.m_230359_(p_226889_);
        this.f_73657_ = p_226891_;
    }

    public TemplateStructurePiece(StructurePieceType p_226894_, CompoundTag p_226895_, StructureTemplateManager p_226896_, Function<ResourceLocation, StructurePlaceSettings> p_226897_) {
        super(p_226894_, p_226895_);
        this.m_73519_(Direction.NORTH);
        this.f_163658_ = p_226895_.m_128461_("Template");
        this.f_73658_ = new BlockPos(p_226895_.m_128451_("TPX"), p_226895_.m_128451_("TPY"), p_226895_.m_128451_("TPZ"));
        ResourceLocation $$4 = this.m_142415_();
        this.f_73656_ = p_226896_.m_230359_($$4);
        this.f_73657_ = p_226897_.apply($$4);
        this.f_73383_ = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
    }

    protected ResourceLocation m_142415_() {
        return new ResourceLocation(this.f_163658_);
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_192690_, CompoundTag p_192691_) {
        p_192691_.m_128405_("TPX", this.f_73658_.m_123341_());
        p_192691_.m_128405_("TPY", this.f_73658_.m_123342_());
        p_192691_.m_128405_("TPZ", this.f_73658_.m_123343_());
        p_192691_.m_128359_("Template", this.f_163658_);
    }

    @Override
    public void m_213694_(WorldGenLevel p_226899_, StructureManager p_226900_, ChunkGenerator p_226901_, RandomSource p_226902_, BoundingBox p_226903_, ChunkPos p_226904_, BlockPos p_226905_) {
        this.f_73657_.m_74381_(p_226903_);
        this.f_73383_ = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
        if (this.f_73656_.m_230328_(p_226899_, this.f_73658_, p_226905_, this.f_73657_, p_226902_, 2)) {
            List<StructureTemplate.StructureBlockInfo> $$7 = this.f_73656_.m_74603_(this.f_73658_, this.f_73657_, Blocks.f_50677_);
            for (StructureTemplate.StructureBlockInfo $$8 : $$7) {
                StructureMode $$9;
                if ($$8.f_74677_ == null || ($$9 = StructureMode.valueOf($$8.f_74677_.m_128461_("mode"))) != StructureMode.DATA) continue;
                this.m_213704_($$8.f_74677_.m_128461_("metadata"), $$8.f_74675_, p_226899_, p_226902_, p_226903_);
            }
            List<StructureTemplate.StructureBlockInfo> $$10 = this.f_73656_.m_74603_(this.f_73658_, this.f_73657_, Blocks.f_50678_);
            for (StructureTemplate.StructureBlockInfo $$11 : $$10) {
                if ($$11.f_74677_ == null) continue;
                String $$12 = $$11.f_74677_.m_128461_("final_state");
                BlockState $$13 = Blocks.f_50016_.m_49966_();
                try {
                    $$13 = BlockStateParser.m_234704_(Registry.f_122824_, $$12, true).f_234748_();
                }
                catch (CommandSyntaxException $$14) {
                    f_73659_.error("Error while parsing blockstate {} in jigsaw block @ {}", (Object)$$12, (Object)$$11.f_74675_);
                }
                p_226899_.m_7731_($$11.f_74675_, $$13, 3);
            }
        }
    }

    protected abstract void m_213704_(String var1, BlockPos var2, ServerLevelAccessor var3, RandomSource var4, BoundingBox var5);

    @Override
    @Deprecated
    public void m_6324_(int p_73668_, int p_73669_, int p_73670_) {
        super.m_6324_(p_73668_, p_73669_, p_73670_);
        this.f_73658_ = this.f_73658_.m_7918_(p_73668_, p_73669_, p_73670_);
    }

    @Override
    public Rotation m_6830_() {
        return this.f_73657_.m_74404_();
    }

    public StructureTemplate m_226911_() {
        return this.f_73656_;
    }

    public BlockPos m_226912_() {
        return this.f_73658_;
    }

    public StructurePlaceSettings m_226913_() {
        return this.f_73657_;
    }
}

