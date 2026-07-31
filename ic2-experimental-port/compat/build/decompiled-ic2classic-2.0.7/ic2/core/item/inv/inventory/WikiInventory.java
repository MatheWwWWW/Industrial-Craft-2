/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.OptionInstance
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.inventory;

import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.WikiBoxContainer;
import ic2.core.item.tool.WikiItem;
import ic2.core.wiki.base.book.BuildWiki;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WikiInventory
extends PortableInventory {
    public WikiInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new WikiBoxContainer(this, player, this.getID(), windowID);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public Screen createGui(Player player, InteractionHand hand, Direction side, IC2Container container) {
        return new WikiGui((ContainerComponent)container);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    public BuildWiki getWiki() {
        return ((WikiItem)this.stack.m_41720_()).getWiki();
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class WikiGui
    extends ComponentContainerScreen {
        int last = -1;

        public WikiGui(ContainerComponent<?> container) {
            super(container);
        }

        @Override
        protected void m_7856_() {
            OptionInstance scale;
            int value;
            Minecraft mc = this.getMinecraft();
            if (this.last == -1 && (value = ((Integer)(scale = mc.f_91066_.m_231928_()).m_231551_()).intValue()) != 0) {
                scale.m_231514_((Object)0);
                this.last = value;
                mc.m_5741_();
            }
            super.m_7856_();
        }

        public boolean m_7043_() {
            return IC2.CONFIG.pauseWiki.get();
        }

        public void m_7379_() {
            if (this.last != -1) {
                Minecraft mc = this.getMinecraft();
                mc.f_91066_.m_231928_().m_231514_((Object)this.last);
                mc.m_5741_();
                this.last = -1;
            }
            super.m_7379_();
        }

        @Override
        public void m_7861_() {
            if (this.last != -1) {
                Minecraft mc = this.getMinecraft();
                mc.f_91066_.m_231928_().m_231514_((Object)this.last);
                int i = mc.m_91268_().m_85385_(this.last, mc.m_91390_());
                mc.m_91268_().m_85378_((double)i);
                this.last = -1;
            }
            super.m_7861_();
        }
    }
}

