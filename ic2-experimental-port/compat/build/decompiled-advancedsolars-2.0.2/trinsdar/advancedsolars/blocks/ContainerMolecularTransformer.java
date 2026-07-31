/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.inventory.base.IHasGui
 *  ic2.core.inventory.base.IHasInventory
 *  ic2.core.inventory.container.ContainerComponent
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.inventory.slot.FilterSlot
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 */
package trinsdar.advancedsolars.blocks;

import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;
import trinsdar.advancedsolars.gui.GuiCompMTEnergyBar;
import trinsdar.advancedsolars.gui.MolecularTransformerStringComp;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;

public class ContainerMolecularTransformer
extends ContainerComponent<BlockEntityMolecularTransformer> {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("advanced_solars", "textures/gui/molecular_transformer.png");

    public ContainerMolecularTransformer(BlockEntityMolecularTransformer key, Player player, int id) {
        super((IHasGui)key, player, id);
        this.m_38897_((Slot)new FilterSlot((IHasInventory)key, 0, 26, 9, i -> AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(i, true, false) != null));
        this.m_38897_(FilterSlot.createOutputSlot((IHasInventory)key, (int)1, (int)26, (int)59));
        this.addComponent(new GuiCompMTEnergyBar(key));
        this.addComponent(new MolecularTransformerStringComp(key));
        this.addPlayerInventory(player.m_150109_());
    }

    public void onGuiLoaded(IC2Screen screen) {
        screen.setGuiName((Component)Component.m_237119_());
        screen.clearFlag(1);
    }

    public ResourceLocation getTexture() {
        return GUI_TEXTURE;
    }
}

