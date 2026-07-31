/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class UpgradesWidget
extends GuiElement<UpgradesWidget> {
    private final List<ItemStack> compatibleUpgrades;
    private static final int xCoord = 96;
    private static final int yCoord = 128;
    private static final int iWidth = 10;
    private static final int iHeight = 10;

    public UpgradesWidget(Ic2Gui<?> ic2Gui, int n, int n2, IUpgradableBlock iUpgradableBlock) {
        super(ic2Gui, n, n2, 10, 10);
        this.compatibleUpgrades = UpgradesWidget.getCompatibleUpgrades(iUpgradableBlock);
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        UpgradesWidget.bindCommonTexture();
        this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, 96.0, 128.0);
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        list.add((Component)Component.m_237115_((String)"ic2.generic.text.upgrade"));
        for (ItemStack itemStack : this.compatibleUpgrades) {
            list.add(itemStack.m_41786_());
        }
        return list;
    }

    private static List<ItemStack> getCompatibleUpgrades(IUpgradableBlock iUpgradableBlock) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        Set<UpgradableProperty> set = iUpgradableBlock.getUpgradableProperties();
        for (ItemStack itemStack : UpgradeRegistry.getUpgrades()) {
            IUpgradeItem iUpgradeItem = (IUpgradeItem)itemStack.m_41720_();
            if (!iUpgradeItem.isSuitableFor(itemStack, set)) continue;
            arrayList.add(itemStack);
        }
        return arrayList;
    }
}

