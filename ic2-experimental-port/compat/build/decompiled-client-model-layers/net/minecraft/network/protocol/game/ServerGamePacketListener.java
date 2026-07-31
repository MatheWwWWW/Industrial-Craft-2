/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.protocol.game.ServerPacketListener;
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

public interface ServerGamePacketListener
extends ServerPacketListener {
    public void m_7953_(ServerboundSwingPacket var1);

    public void m_7388_(ServerboundChatPacket var1);

    public void m_214047_(ServerboundChatCommandPacket var1);

    public void m_213866_(ServerboundChatPreviewPacket var1);

    public void m_241885_(ServerboundChatAckPacket var1);

    public void m_6272_(ServerboundClientCommandPacket var1);

    public void m_5617_(ServerboundClientInformationPacket var1);

    public void m_6557_(ServerboundContainerButtonClickPacket var1);

    public void m_5914_(ServerboundContainerClickPacket var1);

    public void m_7191_(ServerboundPlaceRecipePacket var1);

    public void m_7951_(ServerboundContainerClosePacket var1);

    public void m_7423_(ServerboundCustomPayloadPacket var1);

    public void m_6946_(ServerboundInteractPacket var1);

    public void m_5683_(ServerboundKeepAlivePacket var1);

    public void m_7185_(ServerboundMovePlayerPacket var1);

    public void m_142110_(ServerboundPongPacket var1);

    public void m_6828_(ServerboundPlayerAbilitiesPacket var1);

    public void m_7502_(ServerboundPlayerActionPacket var1);

    public void m_5681_(ServerboundPlayerCommandPacket var1);

    public void m_5918_(ServerboundPlayerInputPacket var1);

    public void m_7798_(ServerboundSetCarriedItemPacket var1);

    public void m_5964_(ServerboundSetCreativeModeSlotPacket var1);

    public void m_5527_(ServerboundSignUpdatePacket var1);

    public void m_6371_(ServerboundUseItemOnPacket var1);

    public void m_5760_(ServerboundUseItemPacket var1);

    public void m_6936_(ServerboundTeleportToEntityPacket var1);

    public void m_7529_(ServerboundResourcePackPacket var1);

    public void m_5938_(ServerboundPaddleBoatPacket var1);

    public void m_5659_(ServerboundMoveVehiclePacket var1);

    public void m_7376_(ServerboundAcceptTeleportationPacket var1);

    public void m_7411_(ServerboundRecipeBookSeenRecipePacket var1);

    public void m_7982_(ServerboundRecipeBookChangeSettingsPacket var1);

    public void m_6947_(ServerboundSeenAdvancementsPacket var1);

    public void m_7741_(ServerboundCommandSuggestionPacket var1);

    public void m_7192_(ServerboundSetCommandBlockPacket var1);

    public void m_6629_(ServerboundSetCommandMinecartPacket var1);

    public void m_7965_(ServerboundPickItemPacket var1);

    public void m_5591_(ServerboundRenameItemPacket var1);

    public void m_5712_(ServerboundSetBeaconPacket var1);

    public void m_7424_(ServerboundSetStructureBlockPacket var1);

    public void m_6321_(ServerboundSelectTradePacket var1);

    public void m_6829_(ServerboundEditBookPacket var1);

    public void m_7548_(ServerboundEntityTagQuery var1);

    public void m_6780_(ServerboundBlockEntityTagQuery var1);

    public void m_8019_(ServerboundSetJigsawBlockPacket var1);

    public void m_6449_(ServerboundJigsawGeneratePacket var1);

    public void m_7477_(ServerboundChangeDifficultyPacket var1);

    public void m_7728_(ServerboundLockDifficultyPacket var1);
}

