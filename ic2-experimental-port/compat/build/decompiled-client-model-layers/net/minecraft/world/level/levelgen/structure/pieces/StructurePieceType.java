/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.pieces;

import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasurePieces;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.structures.IglooPieces;
import net.minecraft.world.level.levelgen.structure.structures.JungleTemplePiece;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public interface StructurePieceType {
    public static final StructurePieceType f_210121_ = StructurePieceType.m_210152_(MineshaftPieces.MineShaftCorridor::new, "MSCorridor");
    public static final StructurePieceType f_210126_ = StructurePieceType.m_210152_(MineshaftPieces.MineShaftCrossing::new, "MSCrossing");
    public static final StructurePieceType f_210127_ = StructurePieceType.m_210152_(MineshaftPieces.MineShaftRoom::new, "MSRoom");
    public static final StructurePieceType f_210128_ = StructurePieceType.m_210152_(MineshaftPieces.MineShaftStairs::new, "MSStairs");
    public static final StructurePieceType f_210129_ = StructurePieceType.m_210152_(NetherFortressPieces.BridgeCrossing::new, "NeBCr");
    public static final StructurePieceType f_210130_ = StructurePieceType.m_210152_(NetherFortressPieces.BridgeEndFiller::new, "NeBEF");
    public static final StructurePieceType f_210131_ = StructurePieceType.m_210152_(NetherFortressPieces.BridgeStraight::new, "NeBS");
    public static final StructurePieceType f_210132_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleCorridorStairsPiece::new, "NeCCS");
    public static final StructurePieceType f_210133_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleCorridorTBalconyPiece::new, "NeCTB");
    public static final StructurePieceType f_210134_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleEntrance::new, "NeCE");
    public static final StructurePieceType f_210135_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleSmallCorridorCrossingPiece::new, "NeSCSC");
    public static final StructurePieceType f_210136_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleSmallCorridorLeftTurnPiece::new, "NeSCLT");
    public static final StructurePieceType f_210137_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleSmallCorridorPiece::new, "NeSC");
    public static final StructurePieceType f_210138_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleSmallCorridorRightTurnPiece::new, "NeSCRT");
    public static final StructurePieceType f_210139_ = StructurePieceType.m_210152_(NetherFortressPieces.CastleStalkRoom::new, "NeCSR");
    public static final StructurePieceType f_210140_ = StructurePieceType.m_210152_(NetherFortressPieces.MonsterThrone::new, "NeMT");
    public static final StructurePieceType f_210141_ = StructurePieceType.m_210152_(NetherFortressPieces.RoomCrossing::new, "NeRC");
    public static final StructurePieceType f_210142_ = StructurePieceType.m_210152_(NetherFortressPieces.StairsRoom::new, "NeSR");
    public static final StructurePieceType f_210143_ = StructurePieceType.m_210152_(NetherFortressPieces.StartPiece::new, "NeStart");
    public static final StructurePieceType f_210144_ = StructurePieceType.m_210152_(StrongholdPieces.ChestCorridor::new, "SHCC");
    public static final StructurePieceType f_210145_ = StructurePieceType.m_210152_(StrongholdPieces.FillerCorridor::new, "SHFC");
    public static final StructurePieceType f_210146_ = StructurePieceType.m_210152_(StrongholdPieces.FiveCrossing::new, "SH5C");
    public static final StructurePieceType f_210147_ = StructurePieceType.m_210152_(StrongholdPieces.LeftTurn::new, "SHLT");
    public static final StructurePieceType f_210148_ = StructurePieceType.m_210152_(StrongholdPieces.Library::new, "SHLi");
    public static final StructurePieceType f_210149_ = StructurePieceType.m_210152_(StrongholdPieces.PortalRoom::new, "SHPR");
    public static final StructurePieceType f_210150_ = StructurePieceType.m_210152_(StrongholdPieces.PrisonHall::new, "SHPH");
    public static final StructurePieceType f_210095_ = StructurePieceType.m_210152_(StrongholdPieces.RightTurn::new, "SHRT");
    public static final StructurePieceType f_210096_ = StructurePieceType.m_210152_(StrongholdPieces.RoomCrossing::new, "SHRC");
    public static final StructurePieceType f_210097_ = StructurePieceType.m_210152_(StrongholdPieces.StairsDown::new, "SHSD");
    public static final StructurePieceType f_210098_ = StructurePieceType.m_210152_(StrongholdPieces.StartPiece::new, "SHStart");
    public static final StructurePieceType f_210099_ = StructurePieceType.m_210152_(StrongholdPieces.Straight::new, "SHS");
    public static final StructurePieceType f_210100_ = StructurePieceType.m_210152_(StrongholdPieces.StraightStairsDown::new, "SHSSD");
    public static final StructurePieceType f_210101_ = StructurePieceType.m_210152_(JungleTemplePiece::new, "TeJP");
    public static final StructurePieceType f_210102_ = StructurePieceType.m_210155_(OceanRuinPieces.OceanRuinPiece::new, "ORP");
    public static final StructurePieceType f_210103_ = StructurePieceType.m_210155_(IglooPieces.IglooPiece::new, "Iglu");
    public static final StructurePieceType f_210104_ = StructurePieceType.m_210155_(RuinedPortalPiece::new, "RUPO");
    public static final StructurePieceType f_210105_ = StructurePieceType.m_210152_(SwampHutPiece::new, "TeSH");
    public static final StructurePieceType f_210106_ = StructurePieceType.m_210152_(DesertPyramidPiece::new, "TeDP");
    public static final StructurePieceType f_210107_ = StructurePieceType.m_210152_(OceanMonumentPieces.MonumentBuilding::new, "OMB");
    public static final StructurePieceType f_210108_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentCoreRoom::new, "OMCR");
    public static final StructurePieceType f_210109_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentDoubleXRoom::new, "OMDXR");
    public static final StructurePieceType f_210110_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentDoubleXYRoom::new, "OMDXYR");
    public static final StructurePieceType f_210111_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentDoubleYRoom::new, "OMDYR");
    public static final StructurePieceType f_210112_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentDoubleYZRoom::new, "OMDYZR");
    public static final StructurePieceType f_210113_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentDoubleZRoom::new, "OMDZR");
    public static final StructurePieceType f_210114_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentEntryRoom::new, "OMEntry");
    public static final StructurePieceType f_210115_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentPenthouse::new, "OMPenthouse");
    public static final StructurePieceType f_210116_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentSimpleRoom::new, "OMSimple");
    public static final StructurePieceType f_210117_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentSimpleTopRoom::new, "OMSimpleT");
    public static final StructurePieceType f_210118_ = StructurePieceType.m_210152_(OceanMonumentPieces.OceanMonumentWingRoom::new, "OMWR");
    public static final StructurePieceType f_210119_ = StructurePieceType.m_210155_(EndCityPieces.EndCityPiece::new, "ECP");
    public static final StructurePieceType f_210120_ = StructurePieceType.m_210155_(WoodlandMansionPieces.WoodlandMansionPiece::new, "WMP");
    public static final StructurePieceType f_210122_ = StructurePieceType.m_210152_(BuriedTreasurePieces.BuriedTreasurePiece::new, "BTP");
    public static final StructurePieceType f_210123_ = StructurePieceType.m_210155_(ShipwreckPieces.ShipwreckPiece::new, "Shipwreck");
    public static final StructurePieceType f_210124_ = StructurePieceType.m_210155_(NetherFossilPieces.NetherFossilPiece::new, "NeFos");
    public static final StructurePieceType f_210125_ = StructurePieceType.m_210158_(PoolElementStructurePiece::new, "jigsaw");

    public StructurePiece m_207333_(StructurePieceSerializationContext var1, CompoundTag var2);

    private static StructurePieceType m_210158_(StructurePieceType p_210159_, String p_210160_) {
        return Registry.m_122961_(Registry.f_122843_, p_210160_.toLowerCase(Locale.ROOT), p_210159_);
    }

    private static StructurePieceType m_210152_(ContextlessType p_210153_, String p_210154_) {
        return StructurePieceType.m_210158_(p_210153_, p_210154_);
    }

    private static StructurePieceType m_210155_(StructureTemplateType p_210156_, String p_210157_) {
        return StructurePieceType.m_210158_(p_210156_, p_210157_);
    }

    public static interface ContextlessType
    extends StructurePieceType {
        public StructurePiece m_210166_(CompoundTag var1);

        @Override
        default public StructurePiece m_207333_(StructurePieceSerializationContext p_210164_, CompoundTag p_210165_) {
            return this.m_210166_(p_210165_);
        }
    }

    public static interface StructureTemplateType
    extends StructurePieceType {
        public StructurePiece m_226962_(StructureTemplateManager var1, CompoundTag var2);

        @Override
        default public StructurePiece m_207333_(StructurePieceSerializationContext p_210169_, CompoundTag p_210170_) {
            return this.m_226962_(p_210169_.f_226956_(), p_210170_);
        }
    }
}

