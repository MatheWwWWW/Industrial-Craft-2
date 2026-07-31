/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IProbeInfo
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.probeplugin.info;

import ic2.api.items.readers.IWrenchTool;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.registries.IC2Items;
import ic2.probeplugin.info.ITileInfoComponent;
import ic2.probeplugin.override.components.Panel;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class WrenchComponent
implements ITileInfoComponent<IWrenchableTile> {
    @Override
    public boolean hasValidReader(PlayerHandler handler) {
        return true;
    }

    @Override
    public void addInfo(IProbeInfo info, Player player, Direction dir, IWrenchableTile tile) {
        if (tile.canRemoveBlock(player) && tile.isHarvestWrenchRequired(player)) {
            ItemStack tool = this.getTool(player);
            double chance = tool.m_41720_() instanceof IWrenchTool ? ((IWrenchTool)tool.m_41720_()).getActualLoss(tool, tile.getDropRate(player)) : 0.0;
            Panel panel = new Panel(Panel.Type.HORIZONTAL);
            panel.item(new ItemStack((ItemLike)IC2Items.WRENCH)).text((Component)Component.m_237115_((String)(Mth.m_14107_((double)(chance * 100.0)) + "% Drop Chance")).m_130940_(ChatFormatting.GRAY), panel.defaultTextStyle().topPadding(5));
            info.element((IElement)panel);
        }
    }

    private ItemStack getTool(Player player) {
        ItemStack held = player.m_21205_();
        return held.m_41720_() instanceof IWrenchTool ? held : player.m_21206_();
    }
}

