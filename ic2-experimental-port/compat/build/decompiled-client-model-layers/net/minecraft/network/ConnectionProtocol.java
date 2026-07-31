/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.network;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
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
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundChatPreviewPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.util.VisibleForDebug;
import org.slf4j.Logger;

public final class ConnectionProtocol
extends Enum<ConnectionProtocol> {
    public static final /* enum */ ConnectionProtocol HANDSHAKING = new ConnectionProtocol(-1, ConnectionProtocol.m_129600_().m_129625_(PacketFlow.SERVERBOUND, new PacketSet().m_178330_(ClientIntentionPacket.class, ClientIntentionPacket::new)));
    public static final /* enum */ ConnectionProtocol PLAY = new ConnectionProtocol(0, ConnectionProtocol.m_129600_().m_129625_(PacketFlow.CLIENTBOUND, new PacketSet().m_178330_(ClientboundAddEntityPacket.class, ClientboundAddEntityPacket::new).m_178330_(ClientboundAddExperienceOrbPacket.class, ClientboundAddExperienceOrbPacket::new).m_178330_(ClientboundAddPlayerPacket.class, ClientboundAddPlayerPacket::new).m_178330_(ClientboundAnimatePacket.class, ClientboundAnimatePacket::new).m_178330_(ClientboundAwardStatsPacket.class, ClientboundAwardStatsPacket::new).m_178330_(ClientboundBlockChangedAckPacket.class, ClientboundBlockChangedAckPacket::new).m_178330_(ClientboundBlockDestructionPacket.class, ClientboundBlockDestructionPacket::new).m_178330_(ClientboundBlockEntityDataPacket.class, ClientboundBlockEntityDataPacket::new).m_178330_(ClientboundBlockEventPacket.class, ClientboundBlockEventPacket::new).m_178330_(ClientboundBlockUpdatePacket.class, ClientboundBlockUpdatePacket::new).m_178330_(ClientboundBossEventPacket.class, ClientboundBossEventPacket::new).m_178330_(ClientboundChangeDifficultyPacket.class, ClientboundChangeDifficultyPacket::new).m_178330_(ClientboundChatPreviewPacket.class, ClientboundChatPreviewPacket::new).m_178330_(ClientboundClearTitlesPacket.class, ClientboundClearTitlesPacket::new).m_178330_(ClientboundCommandSuggestionsPacket.class, ClientboundCommandSuggestionsPacket::new).m_178330_(ClientboundCommandsPacket.class, ClientboundCommandsPacket::new).m_178330_(ClientboundContainerClosePacket.class, ClientboundContainerClosePacket::new).m_178330_(ClientboundContainerSetContentPacket.class, ClientboundContainerSetContentPacket::new).m_178330_(ClientboundContainerSetDataPacket.class, ClientboundContainerSetDataPacket::new).m_178330_(ClientboundContainerSetSlotPacket.class, ClientboundContainerSetSlotPacket::new).m_178330_(ClientboundCooldownPacket.class, ClientboundCooldownPacket::new).m_178330_(ClientboundCustomChatCompletionsPacket.class, ClientboundCustomChatCompletionsPacket::new).m_178330_(ClientboundCustomPayloadPacket.class, ClientboundCustomPayloadPacket::new).m_178330_(ClientboundCustomSoundPacket.class, ClientboundCustomSoundPacket::new).m_178330_(ClientboundDeleteChatPacket.class, ClientboundDeleteChatPacket::new).m_178330_(ClientboundDisconnectPacket.class, ClientboundDisconnectPacket::new).m_178330_(ClientboundEntityEventPacket.class, ClientboundEntityEventPacket::new).m_178330_(ClientboundExplodePacket.class, ClientboundExplodePacket::new).m_178330_(ClientboundForgetLevelChunkPacket.class, ClientboundForgetLevelChunkPacket::new).m_178330_(ClientboundGameEventPacket.class, ClientboundGameEventPacket::new).m_178330_(ClientboundHorseScreenOpenPacket.class, ClientboundHorseScreenOpenPacket::new).m_178330_(ClientboundInitializeBorderPacket.class, ClientboundInitializeBorderPacket::new).m_178330_(ClientboundKeepAlivePacket.class, ClientboundKeepAlivePacket::new).m_178330_(ClientboundLevelChunkWithLightPacket.class, ClientboundLevelChunkWithLightPacket::new).m_178330_(ClientboundLevelEventPacket.class, ClientboundLevelEventPacket::new).m_178330_(ClientboundLevelParticlesPacket.class, ClientboundLevelParticlesPacket::new).m_178330_(ClientboundLightUpdatePacket.class, ClientboundLightUpdatePacket::new).m_178330_(ClientboundLoginPacket.class, ClientboundLoginPacket::new).m_178330_(ClientboundMapItemDataPacket.class, ClientboundMapItemDataPacket::new).m_178330_(ClientboundMerchantOffersPacket.class, ClientboundMerchantOffersPacket::new).m_178330_(ClientboundMoveEntityPacket.Pos.class, ClientboundMoveEntityPacket.Pos::m_179000_).m_178330_(ClientboundMoveEntityPacket.PosRot.class, ClientboundMoveEntityPacket.PosRot::m_179002_).m_178330_(ClientboundMoveEntityPacket.Rot.class, ClientboundMoveEntityPacket.Rot::m_179004_).m_178330_(ClientboundMoveVehiclePacket.class, ClientboundMoveVehiclePacket::new).m_178330_(ClientboundOpenBookPacket.class, ClientboundOpenBookPacket::new).m_178330_(ClientboundOpenScreenPacket.class, ClientboundOpenScreenPacket::new).m_178330_(ClientboundOpenSignEditorPacket.class, ClientboundOpenSignEditorPacket::new).m_178330_(ClientboundPingPacket.class, ClientboundPingPacket::new).m_178330_(ClientboundPlaceGhostRecipePacket.class, ClientboundPlaceGhostRecipePacket::new).m_178330_(ClientboundPlayerAbilitiesPacket.class, ClientboundPlayerAbilitiesPacket::new).m_178330_(ClientboundPlayerChatHeaderPacket.class, ClientboundPlayerChatHeaderPacket::new).m_178330_(ClientboundPlayerChatPacket.class, ClientboundPlayerChatPacket::new).m_178330_(ClientboundPlayerCombatEndPacket.class, ClientboundPlayerCombatEndPacket::new).m_178330_(ClientboundPlayerCombatEnterPacket.class, ClientboundPlayerCombatEnterPacket::new).m_178330_(ClientboundPlayerCombatKillPacket.class, ClientboundPlayerCombatKillPacket::new).m_178330_(ClientboundPlayerInfoPacket.class, ClientboundPlayerInfoPacket::new).m_178330_(ClientboundPlayerLookAtPacket.class, ClientboundPlayerLookAtPacket::new).m_178330_(ClientboundPlayerPositionPacket.class, ClientboundPlayerPositionPacket::new).m_178330_(ClientboundRecipePacket.class, ClientboundRecipePacket::new).m_178330_(ClientboundRemoveEntitiesPacket.class, ClientboundRemoveEntitiesPacket::new).m_178330_(ClientboundRemoveMobEffectPacket.class, ClientboundRemoveMobEffectPacket::new).m_178330_(ClientboundResourcePackPacket.class, ClientboundResourcePackPacket::new).m_178330_(ClientboundRespawnPacket.class, ClientboundRespawnPacket::new).m_178330_(ClientboundRotateHeadPacket.class, ClientboundRotateHeadPacket::new).m_178330_(ClientboundSectionBlocksUpdatePacket.class, ClientboundSectionBlocksUpdatePacket::new).m_178330_(ClientboundSelectAdvancementsTabPacket.class, ClientboundSelectAdvancementsTabPacket::new).m_178330_(ClientboundServerDataPacket.class, ClientboundServerDataPacket::new).m_178330_(ClientboundSetActionBarTextPacket.class, ClientboundSetActionBarTextPacket::new).m_178330_(ClientboundSetBorderCenterPacket.class, ClientboundSetBorderCenterPacket::new).m_178330_(ClientboundSetBorderLerpSizePacket.class, ClientboundSetBorderLerpSizePacket::new).m_178330_(ClientboundSetBorderSizePacket.class, ClientboundSetBorderSizePacket::new).m_178330_(ClientboundSetBorderWarningDelayPacket.class, ClientboundSetBorderWarningDelayPacket::new).m_178330_(ClientboundSetBorderWarningDistancePacket.class, ClientboundSetBorderWarningDistancePacket::new).m_178330_(ClientboundSetCameraPacket.class, ClientboundSetCameraPacket::new).m_178330_(ClientboundSetCarriedItemPacket.class, ClientboundSetCarriedItemPacket::new).m_178330_(ClientboundSetChunkCacheCenterPacket.class, ClientboundSetChunkCacheCenterPacket::new).m_178330_(ClientboundSetChunkCacheRadiusPacket.class, ClientboundSetChunkCacheRadiusPacket::new).m_178330_(ClientboundSetDefaultSpawnPositionPacket.class, ClientboundSetDefaultSpawnPositionPacket::new).m_178330_(ClientboundSetDisplayChatPreviewPacket.class, ClientboundSetDisplayChatPreviewPacket::new).m_178330_(ClientboundSetDisplayObjectivePacket.class, ClientboundSetDisplayObjectivePacket::new).m_178330_(ClientboundSetEntityDataPacket.class, ClientboundSetEntityDataPacket::new).m_178330_(ClientboundSetEntityLinkPacket.class, ClientboundSetEntityLinkPacket::new).m_178330_(ClientboundSetEntityMotionPacket.class, ClientboundSetEntityMotionPacket::new).m_178330_(ClientboundSetEquipmentPacket.class, ClientboundSetEquipmentPacket::new).m_178330_(ClientboundSetExperiencePacket.class, ClientboundSetExperiencePacket::new).m_178330_(ClientboundSetHealthPacket.class, ClientboundSetHealthPacket::new).m_178330_(ClientboundSetObjectivePacket.class, ClientboundSetObjectivePacket::new).m_178330_(ClientboundSetPassengersPacket.class, ClientboundSetPassengersPacket::new).m_178330_(ClientboundSetPlayerTeamPacket.class, ClientboundSetPlayerTeamPacket::new).m_178330_(ClientboundSetScorePacket.class, ClientboundSetScorePacket::new).m_178330_(ClientboundSetSimulationDistancePacket.class, ClientboundSetSimulationDistancePacket::new).m_178330_(ClientboundSetSubtitleTextPacket.class, ClientboundSetSubtitleTextPacket::new).m_178330_(ClientboundSetTimePacket.class, ClientboundSetTimePacket::new).m_178330_(ClientboundSetTitleTextPacket.class, ClientboundSetTitleTextPacket::new).m_178330_(ClientboundSetTitlesAnimationPacket.class, ClientboundSetTitlesAnimationPacket::new).m_178330_(ClientboundSoundEntityPacket.class, ClientboundSoundEntityPacket::new).m_178330_(ClientboundSoundPacket.class, ClientboundSoundPacket::new).m_178330_(ClientboundStopSoundPacket.class, ClientboundStopSoundPacket::new).m_178330_(ClientboundSystemChatPacket.class, ClientboundSystemChatPacket::new).m_178330_(ClientboundTabListPacket.class, ClientboundTabListPacket::new).m_178330_(ClientboundTagQueryPacket.class, ClientboundTagQueryPacket::new).m_178330_(ClientboundTakeItemEntityPacket.class, ClientboundTakeItemEntityPacket::new).m_178330_(ClientboundTeleportEntityPacket.class, ClientboundTeleportEntityPacket::new).m_178330_(ClientboundUpdateAdvancementsPacket.class, ClientboundUpdateAdvancementsPacket::new).m_178330_(ClientboundUpdateAttributesPacket.class, ClientboundUpdateAttributesPacket::new).m_178330_(ClientboundUpdateMobEffectPacket.class, ClientboundUpdateMobEffectPacket::new).m_178330_(ClientboundUpdateRecipesPacket.class, ClientboundUpdateRecipesPacket::new).m_178330_(ClientboundUpdateTagsPacket.class, ClientboundUpdateTagsPacket::new)).m_129625_(PacketFlow.SERVERBOUND, new PacketSet().m_178330_(ServerboundAcceptTeleportationPacket.class, ServerboundAcceptTeleportationPacket::new).m_178330_(ServerboundBlockEntityTagQuery.class, ServerboundBlockEntityTagQuery::new).m_178330_(ServerboundChangeDifficultyPacket.class, ServerboundChangeDifficultyPacket::new).m_178330_(ServerboundChatAckPacket.class, ServerboundChatAckPacket::new).m_178330_(ServerboundChatCommandPacket.class, ServerboundChatCommandPacket::new).m_178330_(ServerboundChatPacket.class, ServerboundChatPacket::new).m_178330_(ServerboundChatPreviewPacket.class, ServerboundChatPreviewPacket::new).m_178330_(ServerboundClientCommandPacket.class, ServerboundClientCommandPacket::new).m_178330_(ServerboundClientInformationPacket.class, ServerboundClientInformationPacket::new).m_178330_(ServerboundCommandSuggestionPacket.class, ServerboundCommandSuggestionPacket::new).m_178330_(ServerboundContainerButtonClickPacket.class, ServerboundContainerButtonClickPacket::new).m_178330_(ServerboundContainerClickPacket.class, ServerboundContainerClickPacket::new).m_178330_(ServerboundContainerClosePacket.class, ServerboundContainerClosePacket::new).m_178330_(ServerboundCustomPayloadPacket.class, ServerboundCustomPayloadPacket::new).m_178330_(ServerboundEditBookPacket.class, ServerboundEditBookPacket::new).m_178330_(ServerboundEntityTagQuery.class, ServerboundEntityTagQuery::new).m_178330_(ServerboundInteractPacket.class, ServerboundInteractPacket::new).m_178330_(ServerboundJigsawGeneratePacket.class, ServerboundJigsawGeneratePacket::new).m_178330_(ServerboundKeepAlivePacket.class, ServerboundKeepAlivePacket::new).m_178330_(ServerboundLockDifficultyPacket.class, ServerboundLockDifficultyPacket::new).m_178330_(ServerboundMovePlayerPacket.Pos.class, ServerboundMovePlayerPacket.Pos::m_179685_).m_178330_(ServerboundMovePlayerPacket.PosRot.class, ServerboundMovePlayerPacket.PosRot::m_179687_).m_178330_(ServerboundMovePlayerPacket.Rot.class, ServerboundMovePlayerPacket.Rot::m_179689_).m_178330_(ServerboundMovePlayerPacket.StatusOnly.class, ServerboundMovePlayerPacket.StatusOnly::m_179697_).m_178330_(ServerboundMoveVehiclePacket.class, ServerboundMoveVehiclePacket::new).m_178330_(ServerboundPaddleBoatPacket.class, ServerboundPaddleBoatPacket::new).m_178330_(ServerboundPickItemPacket.class, ServerboundPickItemPacket::new).m_178330_(ServerboundPlaceRecipePacket.class, ServerboundPlaceRecipePacket::new).m_178330_(ServerboundPlayerAbilitiesPacket.class, ServerboundPlayerAbilitiesPacket::new).m_178330_(ServerboundPlayerActionPacket.class, ServerboundPlayerActionPacket::new).m_178330_(ServerboundPlayerCommandPacket.class, ServerboundPlayerCommandPacket::new).m_178330_(ServerboundPlayerInputPacket.class, ServerboundPlayerInputPacket::new).m_178330_(ServerboundPongPacket.class, ServerboundPongPacket::new).m_178330_(ServerboundRecipeBookChangeSettingsPacket.class, ServerboundRecipeBookChangeSettingsPacket::new).m_178330_(ServerboundRecipeBookSeenRecipePacket.class, ServerboundRecipeBookSeenRecipePacket::new).m_178330_(ServerboundRenameItemPacket.class, ServerboundRenameItemPacket::new).m_178330_(ServerboundResourcePackPacket.class, ServerboundResourcePackPacket::new).m_178330_(ServerboundSeenAdvancementsPacket.class, ServerboundSeenAdvancementsPacket::new).m_178330_(ServerboundSelectTradePacket.class, ServerboundSelectTradePacket::new).m_178330_(ServerboundSetBeaconPacket.class, ServerboundSetBeaconPacket::new).m_178330_(ServerboundSetCarriedItemPacket.class, ServerboundSetCarriedItemPacket::new).m_178330_(ServerboundSetCommandBlockPacket.class, ServerboundSetCommandBlockPacket::new).m_178330_(ServerboundSetCommandMinecartPacket.class, ServerboundSetCommandMinecartPacket::new).m_178330_(ServerboundSetCreativeModeSlotPacket.class, ServerboundSetCreativeModeSlotPacket::new).m_178330_(ServerboundSetJigsawBlockPacket.class, ServerboundSetJigsawBlockPacket::new).m_178330_(ServerboundSetStructureBlockPacket.class, ServerboundSetStructureBlockPacket::new).m_178330_(ServerboundSignUpdatePacket.class, ServerboundSignUpdatePacket::new).m_178330_(ServerboundSwingPacket.class, ServerboundSwingPacket::new).m_178330_(ServerboundTeleportToEntityPacket.class, ServerboundTeleportToEntityPacket::new).m_178330_(ServerboundUseItemOnPacket.class, ServerboundUseItemOnPacket::new).m_178330_(ServerboundUseItemPacket.class, ServerboundUseItemPacket::new)));
    public static final /* enum */ ConnectionProtocol STATUS = new ConnectionProtocol(1, ConnectionProtocol.m_129600_().m_129625_(PacketFlow.SERVERBOUND, new PacketSet().m_178330_(ServerboundStatusRequestPacket.class, ServerboundStatusRequestPacket::new).m_178330_(ServerboundPingRequestPacket.class, ServerboundPingRequestPacket::new)).m_129625_(PacketFlow.CLIENTBOUND, new PacketSet().m_178330_(ClientboundStatusResponsePacket.class, ClientboundStatusResponsePacket::new).m_178330_(ClientboundPongResponsePacket.class, ClientboundPongResponsePacket::new)));
    public static final /* enum */ ConnectionProtocol LOGIN = new ConnectionProtocol(2, ConnectionProtocol.m_129600_().m_129625_(PacketFlow.CLIENTBOUND, new PacketSet().m_178330_(ClientboundLoginDisconnectPacket.class, ClientboundLoginDisconnectPacket::new).m_178330_(ClientboundHelloPacket.class, ClientboundHelloPacket::new).m_178330_(ClientboundGameProfilePacket.class, ClientboundGameProfilePacket::new).m_178330_(ClientboundLoginCompressionPacket.class, ClientboundLoginCompressionPacket::new).m_178330_(ClientboundCustomQueryPacket.class, ClientboundCustomQueryPacket::new)).m_129625_(PacketFlow.SERVERBOUND, new PacketSet().m_178330_(ServerboundHelloPacket.class, ServerboundHelloPacket::new).m_178330_(ServerboundKeyPacket.class, ServerboundKeyPacket::new).m_178330_(ServerboundCustomQueryPacket.class, ServerboundCustomQueryPacket::new)));
    private static final int f_178316_ = -1;
    private static final int f_178317_ = 2;
    private static final ConnectionProtocol[] f_129571_;
    private static final Map<Class<? extends Packet<?>>, ConnectionProtocol> f_129572_;
    private final int f_129573_;
    private final Map<PacketFlow, ? extends PacketSet<?>> f_129574_;
    private static final /* synthetic */ ConnectionProtocol[] $VALUES;

    public static ConnectionProtocol[] values() {
        return (ConnectionProtocol[])$VALUES.clone();
    }

    public static ConnectionProtocol valueOf(String p_129602_) {
        return Enum.valueOf(ConnectionProtocol.class, p_129602_);
    }

    private static ProtocolBuilder m_129600_() {
        return new ProtocolBuilder();
    }

    private ConnectionProtocol(int p_129580_, ProtocolBuilder p_129581_) {
        this.f_129573_ = p_129580_;
        this.f_129574_ = p_129581_.f_129619_;
    }

    @Nullable
    public Integer m_129597_(PacketFlow p_129598_, Packet<?> p_129599_) {
        return this.f_129574_.get((Object)p_129598_).m_129614_(p_129599_.getClass());
    }

    @VisibleForDebug
    public Int2ObjectMap<Class<? extends Packet<?>>> m_195620_(PacketFlow p_195621_) {
        Int2ObjectOpenHashMap $$1 = new Int2ObjectOpenHashMap();
        PacketSet<?> $$2 = this.f_129574_.get((Object)p_195621_);
        if ($$2 == null) {
            return Int2ObjectMaps.emptyMap();
        }
        $$2.f_129604_.forEach((arg_0, arg_1) -> ConnectionProtocol.m_195609_((Int2ObjectMap)$$1, arg_0, arg_1));
        return $$1;
    }

    @Nullable
    public Packet<?> m_178321_(PacketFlow p_178322_, int p_178323_, FriendlyByteBuf p_178324_) {
        return this.f_129574_.get((Object)p_178322_).m_178327_(p_178323_, p_178324_);
    }

    public int m_129582_() {
        return this.f_129573_;
    }

    @Nullable
    public static ConnectionProtocol m_129583_(int p_129584_) {
        if (p_129584_ < -1 || p_129584_ > 2) {
            return null;
        }
        return f_129571_[p_129584_ - -1];
    }

    public static ConnectionProtocol m_129592_(Packet<?> p_129593_) {
        return f_129572_.get(p_129593_.getClass());
    }

    private static /* synthetic */ void m_195609_(Int2ObjectMap p_195610_, Class p_195611_, Integer p_195612_) {
        p_195610_.put(p_195612_.intValue(), (Object)p_195611_);
    }

    private static /* synthetic */ ConnectionProtocol[] m_178325_() {
        return new ConnectionProtocol[]{HANDSHAKING, PLAY, STATUS, LOGIN};
    }

    static {
        $VALUES = ConnectionProtocol.m_178325_();
        f_129571_ = new ConnectionProtocol[4];
        f_129572_ = Maps.newHashMap();
        for (ConnectionProtocol $$0 : ConnectionProtocol.values()) {
            int $$1 = $$0.m_129582_();
            if ($$1 < -1 || $$1 > 2) {
                throw new Error("Invalid protocol ID " + $$1);
            }
            ConnectionProtocol.f_129571_[$$1 - -1] = $$0;
            $$0.f_129574_.forEach((p_195618_, p_195619_) -> p_195619_.m_129609_().forEach(p_195615_ -> {
                if (f_129572_.containsKey(p_195615_) && f_129572_.get(p_195615_) != $$0) {
                    throw new IllegalStateException("Packet " + p_195615_ + " is already assigned to protocol " + (Object)((Object)f_129572_.get(p_195615_)) + " - can't reassign to " + $$0);
                }
                f_129572_.put((Class<Packet<?>>)p_195615_, $$0);
            }));
        }
    }

    static class ProtocolBuilder {
        final Map<PacketFlow, PacketSet<?>> f_129619_ = Maps.newEnumMap(PacketFlow.class);

        ProtocolBuilder() {
        }

        public <T extends PacketListener> ProtocolBuilder m_129625_(PacketFlow p_129626_, PacketSet<T> p_129627_) {
            this.f_129619_.put(p_129626_, p_129627_);
            return this;
        }
    }

    static class PacketSet<T extends PacketListener> {
        private static final Logger f_202573_ = LogUtils.getLogger();
        final Object2IntMap<Class<? extends Packet<T>>> f_129604_ = (Object2IntMap)Util.m_137469_(new Object2IntOpenHashMap(), p_129613_ -> p_129613_.defaultReturnValue(-1));
        private final List<Function<FriendlyByteBuf, ? extends Packet<T>>> f_178326_ = Lists.newArrayList();

        PacketSet() {
        }

        public <P extends Packet<T>> PacketSet<T> m_178330_(Class<P> p_178331_, Function<FriendlyByteBuf, P> p_178332_) {
            int $$2 = this.f_178326_.size();
            int $$3 = this.f_129604_.put(p_178331_, $$2);
            if ($$3 != -1) {
                String $$4 = "Packet " + p_178331_ + " is already registered to ID " + $$3;
                f_202573_.error(LogUtils.FATAL_MARKER, $$4);
                throw new IllegalArgumentException($$4);
            }
            this.f_178326_.add(p_178332_);
            return this;
        }

        @Nullable
        public Integer m_129614_(Class<?> p_129615_) {
            int $$1 = this.f_129604_.getInt(p_129615_);
            return $$1 == -1 ? null : Integer.valueOf($$1);
        }

        @Nullable
        public Packet<?> m_178327_(int p_178328_, FriendlyByteBuf p_178329_) {
            Function<FriendlyByteBuf, Packet<FriendlyByteBuf>> $$2 = this.f_178326_.get(p_178328_);
            return $$2 != null ? $$2.apply(p_178329_) : null;
        }

        public Iterable<Class<? extends Packet<?>>> m_129609_() {
            return Iterables.unmodifiableIterable((Iterable)this.f_129604_.keySet());
        }
    }
}

