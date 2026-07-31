/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPreviewPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayChatPreviewPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;

public interface ClientGamePacketListener
extends PacketListener {
    public void m_6771_(ClientboundAddEntityPacket var1);

    public void m_7708_(ClientboundAddExperienceOrbPacket var1);

    public void m_7957_(ClientboundSetObjectivePacket var1);

    public void m_6482_(ClientboundAddPlayerPacket var1);

    public void m_7791_(ClientboundAnimatePacket var1);

    public void m_7271_(ClientboundAwardStatsPacket var1);

    public void m_8076_(ClientboundRecipePacket var1);

    public void m_5943_(ClientboundBlockDestructionPacket var1);

    public void m_8047_(ClientboundOpenSignEditorPacket var1);

    public void m_7545_(ClientboundBlockEntityDataPacket var1);

    public void m_7364_(ClientboundBlockEventPacket var1);

    public void m_6773_(ClientboundBlockUpdatePacket var1);

    public void m_213990_(ClientboundSystemChatPacket var1);

    public void m_213629_(ClientboundPlayerChatPacket var1);

    public void m_240948_(ClientboundPlayerChatHeaderPacket var1);

    public void m_213565_(ClientboundChatPreviewPacket var1);

    public void m_214045_(ClientboundSetDisplayChatPreviewPacket var1);

    public void m_241037_(ClientboundDeleteChatPacket var1);

    public void m_5771_(ClientboundSectionBlocksUpdatePacket var1);

    public void m_7633_(ClientboundMapItemDataPacket var1);

    public void m_7776_(ClientboundContainerClosePacket var1);

    public void m_6837_(ClientboundContainerSetContentPacket var1);

    public void m_6905_(ClientboundHorseScreenOpenPacket var1);

    public void m_7257_(ClientboundContainerSetDataPacket var1);

    public void m_5735_(ClientboundContainerSetSlotPacket var1);

    public void m_7413_(ClientboundCustomPayloadPacket var1);

    public void m_6008_(ClientboundDisconnectPacket var1);

    public void m_7628_(ClientboundEntityEventPacket var1);

    public void m_5599_(ClientboundSetEntityLinkPacket var1);

    public void m_6403_(ClientboundSetPassengersPacket var1);

    public void m_7345_(ClientboundExplodePacket var1);

    public void m_7616_(ClientboundGameEventPacket var1);

    public void m_7231_(ClientboundKeepAlivePacket var1);

    public void m_183388_(ClientboundLevelChunkWithLightPacket var1);

    public void m_5729_(ClientboundForgetLevelChunkPacket var1);

    public void m_7704_(ClientboundLevelEventPacket var1);

    public void m_5998_(ClientboundLoginPacket var1);

    public void m_7865_(ClientboundMoveEntityPacket var1);

    public void m_5682_(ClientboundPlayerPositionPacket var1);

    public void m_7406_(ClientboundLevelParticlesPacket var1);

    public void m_141955_(ClientboundPingPacket var1);

    public void m_5767_(ClientboundPlayerAbilitiesPacket var1);

    public void m_7039_(ClientboundPlayerInfoPacket var1);

    public void m_182047_(ClientboundRemoveEntitiesPacket var1);

    public void m_6476_(ClientboundRemoveMobEffectPacket var1);

    public void m_7992_(ClientboundRespawnPacket var1);

    public void m_6176_(ClientboundRotateHeadPacket var1);

    public void m_5612_(ClientboundSetCarriedItemPacket var1);

    public void m_5556_(ClientboundSetDisplayObjectivePacket var1);

    public void m_6455_(ClientboundSetEntityDataPacket var1);

    public void m_8048_(ClientboundSetEntityMotionPacket var1);

    public void m_7277_(ClientboundSetEquipmentPacket var1);

    public void m_6747_(ClientboundSetExperiencePacket var1);

    public void m_5547_(ClientboundSetHealthPacket var1);

    public void m_5582_(ClientboundSetPlayerTeamPacket var1);

    public void m_7519_(ClientboundSetScorePacket var1);

    public void m_6571_(ClientboundSetDefaultSpawnPositionPacket var1);

    public void m_7885_(ClientboundSetTimePacket var1);

    public void m_8068_(ClientboundSoundPacket var1);

    public void m_5863_(ClientboundSoundEntityPacket var1);

    public void m_6490_(ClientboundCustomSoundPacket var1);

    public void m_8001_(ClientboundTakeItemEntityPacket var1);

    public void m_6435_(ClientboundTeleportEntityPacket var1);

    public void m_7710_(ClientboundUpdateAttributesPacket var1);

    public void m_7915_(ClientboundUpdateMobEffectPacket var1);

    public void m_5859_(ClientboundUpdateTagsPacket var1);

    public void m_142234_(ClientboundPlayerCombatEndPacket var1);

    public void m_142058_(ClientboundPlayerCombatEnterPacket var1);

    public void m_142747_(ClientboundPlayerCombatKillPacket var1);

    public void m_6664_(ClientboundChangeDifficultyPacket var1);

    public void m_6447_(ClientboundSetCameraPacket var1);

    public void m_142237_(ClientboundInitializeBorderPacket var1);

    public void m_142686_(ClientboundSetBorderLerpSizePacket var1);

    public void m_142238_(ClientboundSetBorderSizePacket var1);

    public void m_142056_(ClientboundSetBorderWarningDelayPacket var1);

    public void m_142696_(ClientboundSetBorderWarningDistancePacket var1);

    public void m_142612_(ClientboundSetBorderCenterPacket var1);

    public void m_6235_(ClientboundTabListPacket var1);

    public void m_5587_(ClientboundResourcePackPacket var1);

    public void m_7685_(ClientboundBossEventPacket var1);

    public void m_7701_(ClientboundCooldownPacket var1);

    public void m_7410_(ClientboundMoveVehiclePacket var1);

    public void m_5498_(ClientboundUpdateAdvancementsPacket var1);

    public void m_7553_(ClientboundSelectAdvancementsTabPacket var1);

    public void m_7339_(ClientboundPlaceGhostRecipePacket var1);

    public void m_7443_(ClientboundCommandsPacket var1);

    public void m_7183_(ClientboundStopSoundPacket var1);

    public void m_7589_(ClientboundCommandSuggestionsPacket var1);

    public void m_6327_(ClientboundUpdateRecipesPacket var1);

    public void m_7244_(ClientboundPlayerLookAtPacket var1);

    public void m_6148_(ClientboundTagQueryPacket var1);

    public void m_183514_(ClientboundLightUpdatePacket var1);

    public void m_6503_(ClientboundOpenBookPacket var1);

    public void m_5980_(ClientboundOpenScreenPacket var1);

    public void m_7330_(ClientboundMerchantOffersPacket var1);

    public void m_7299_(ClientboundSetChunkCacheRadiusPacket var1);

    public void m_183623_(ClientboundSetSimulationDistancePacket var1);

    public void m_8065_(ClientboundSetChunkCacheCenterPacket var1);

    public void m_214108_(ClientboundBlockChangedAckPacket var1);

    public void m_142456_(ClientboundSetActionBarTextPacket var1);

    public void m_141913_(ClientboundSetSubtitleTextPacket var1);

    public void m_142442_(ClientboundSetTitleTextPacket var1);

    public void m_142185_(ClientboundSetTitlesAnimationPacket var1);

    public void m_142766_(ClientboundClearTitlesPacket var1);

    public void m_213672_(ClientboundServerDataPacket var1);

    public void m_240695_(ClientboundCustomChatCompletionsPacket var1);
}

