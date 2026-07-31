/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler
 *  top.theillusivec4.curios.common.inventory.CosmeticCurioSlot
 */
package ic2.curioplugin.core.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;

public class ToggleCosmeticCurioSlot
extends CosmeticCurioSlot {
    MutableBoolean visible;

    public ToggleCosmeticCurioSlot(Player player, IDynamicStackHandler handler, int index, String identifier, int xPosition, int yPosition, MutableBoolean visible) {
        super(player, handler, index, identifier, xPosition, yPosition);
        this.visible = visible;
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean m_6659_() {
        return this.visible.getValue();
    }

    public boolean m_5857_(ItemStack stack) {
        return this.visible.getValue() != false && super.m_5857_(stack);
    }

    public boolean m_8010_(Player playerIn) {
        return this.visible.getValue() != false && super.m_8010_(playerIn);
    }
}

