/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;

public class ShipwreckStructure
extends Structure {
    public static final Codec<ShipwreckStructure> f_229384_ = RecordCodecBuilder.create(p_229401_ -> p_229401_.group(ShipwreckStructure.m_226567_(p_229401_), (App)Codec.BOOL.fieldOf("is_beached").forGetter(p_229399_ -> p_229399_.f_229385_)).apply((Applicative)p_229401_, ShipwreckStructure::new));
    public final boolean f_229385_;

    public ShipwreckStructure(Structure.StructureSettings p_229388_, boolean p_229389_) {
        super(p_229388_);
        this.f_229385_ = p_229389_;
    }

    @Override
    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext p_229391_) {
        Heightmap.Types $$1 = this.f_229385_ ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
        return ShipwreckStructure.m_226585_(p_229391_, $$1, p_229394_ -> this.m_229395_((StructurePiecesBuilder)p_229394_, p_229391_));
    }

    private void m_229395_(StructurePiecesBuilder p_229396_, Structure.GenerationContext p_229397_) {
        Rotation $$2 = Rotation.m_221990_(p_229397_.f_226626_());
        BlockPos $$3 = new BlockPos(p_229397_.f_226628_().m_45604_(), 90, p_229397_.f_226628_().m_45605_());
        ShipwreckPieces.m_229345_(p_229397_.f_226625_(), $$3, $$2, p_229396_, p_229397_.f_226626_(), this.f_229385_);
    }

    @Override
    public StructureType<?> m_213658_() {
        return StructureType.f_226874_;
    }
}

