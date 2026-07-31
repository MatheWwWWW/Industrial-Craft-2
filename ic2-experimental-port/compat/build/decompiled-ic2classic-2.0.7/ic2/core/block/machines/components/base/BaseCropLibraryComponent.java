/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.base;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropSeed;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.base.tiles.impls.BaseCropLibraryTileEntity;
import ic2.core.block.machines.logic.crop.CropSorter;
import ic2.core.block.machines.tiles.mv.SimpleCropLibraryTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class BaseCropLibraryComponent
extends GuiWidget
implements ICloseableComponent {
    protected BaseCropLibraryTileEntity tile;

    public BaseCropLibraryComponent(BaseCropLibraryTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 3, y - 22, 15, 20, (Component)this.string("<"), T -> this.tile.sendToServer(0, -1)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 162, y - 22, 15, 20, (Component)this.string(">"), T -> this.tile.sendToServer(0, 1)));
        gui.addRenderableWidget(2, new ExtendedButton(x + 135, y + 90, 15, 10, (Component)this.string("D"), T -> this.tile.sendToServer(2, 1)));
        gui.addRenderableWidget(3, new ExtendedButton(x + 152, y + 90, 15, 10, (Component)this.string("U"), T -> this.tile.sendToServer(2, -1)));
        gui.addRenderableWidget(4, new ItemCheckBox(x + 5, y + 91, 14, 14, T -> this.setVisible(!this.tile.sorting), new ItemStack((ItemLike)Items.f_42351_)));
        gui.addRenderableWidget((int)5, new ExtendedButton((int)(x - 69), (int)(y + 115), (int)30, (int)13, (Component)this.translate((String)"gui.ic2.base_crop_library.next"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$5(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((BaseCropLibraryComponent)this))).f_93624_ = this.tile.sorting;
        gui.addRenderableWidget((int)6, new ItemCheckBox((int)(x - 30), (int)(y + 110), (int)20, (int)20, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$6(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((BaseCropLibraryComponent)this), (ItemStack)new ItemStack((ItemLike)Items.f_41978_), (boolean)this.tile.inverting)).f_93624_ = this.tile.sorting;
        gui.addOpenerWidget(4);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen screen) {
        this.tile.sendToServer(3, 0);
        this.tile.sorting = false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void setVisible(boolean visible) {
        this.gui.closeAllComponents();
        this.tile.sendToServer(3, visible ? 1 : 0);
        this.tile.sorting = visible;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(4, ItemCheckBox.class).setChecked(this.tile.sorting);
        gui.getButton((int)5).f_93624_ = this.tile.sorting;
        gui.getCastedButton((int)6, ItemCheckBox.class).setChecked((boolean)this.tile.inverting).f_93624_ = this.tile.sorting;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        if (this.tile.sorting) {
            this.gui.drawTextureRegion(matrix, x - 73, y + 90, 176.0f, 0.0f, 74.0f, 44.0f);
        }
        for (int i = 0; i < 5; ++i) {
            int index = this.tile.cropOffset * 5 + i;
            if (index > this.tile.syncer.size()) continue;
            ResourceLocation location = index == 0 ? null : this.tile.syncer.get(index - 1);
            this.drawTopTab(matrix, 20 + i * 28, -28, location == null ? new ItemStack((ItemLike)Items.f_42517_) : ICropRegistry.INSTANCE.getDisplayItem(location), this.tile.crop == null ? location == null : this.tile.crop.id().equals((Object)location));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (this.tile.syncer.isActive()) {
            ItemStack stack = this.getCrop(this.gui.getSlotUnderMouse());
            if (!stack.m_41619_()) {
                ICropSeed seed = (ICropSeed)stack.m_41720_();
                ICrop crop = seed.getCrop(stack);
                this.gui.getFont().m_92857_((FormattedText)crop.getName(), 5, 5, 80, 0xFFFFFF);
                int height = this.gui.getFont().m_92923_((FormattedText)crop.getName(), 80).size() * 9 - 9;
                this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.tier", crop.getProperties().getTier()), 5, 15 + height, 0xFFFFFF);
                this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.growth", seed.getGrowth(stack)), 5, 30 + height, 11403055);
                this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.gain", seed.getGain(stack)), 5, 40 + height, 15649024);
                this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.resistance", seed.getResistance(stack)), 5, 50 + height, 52945);
                int count = StackUtil.getNbtData(stack).m_128451_("crop_count");
                if (count > 255) {
                    this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.count_enough"), 5, 65 + height, 0xFFFFFF);
                    return;
                }
                this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.count", count), 5, 65 + height, 0xFFFFFF);
            }
        } else {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.base_crop_library.no_power"), 5, 5, 0xFFFFFF);
        }
        if (this.tile.sorting) {
            this.gui.drawString(matrix, ((CropSorter)SimpleCropLibraryTileEntity.SORTERS.get(this.tile.sorter)).getName(), -70, 95, 0x404040);
        }
    }

    public ItemStack getCrop(Slot slot) {
        ItemStack stack;
        if (slot instanceof LockedSlot && (stack = slot.m_7993_()).m_41720_() instanceof ICropSeed) {
            return stack;
        }
        return ItemStack.f_41583_;
    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        int index;
        for (int i = 0; i < 5 && (index = this.tile.cropOffset * 5 + i) <= this.tile.syncer.size(); ++i) {
            int xPos = 20 + i * 28;
            if (mouseY < -28 || mouseY > 0 || mouseX < xPos || mouseX > xPos + 28) continue;
            if (index == 0) {
                tooltips.accept((Component)this.translate("gui.ic2.base_crop_library.crop.all"));
                continue;
            }
            ICrop crop = ICropRegistry.INSTANCE.getCrop(this.tile.syncer.get(index - 1));
            tooltips.accept((Component)(crop == null ? this.translate("gui.ic2.base_crop_library.crop.unknown") : crop.getName()));
        }
    }

    @Override
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        if (this.gui.getSlotUnderMouse() instanceof LockedSlot) {
            this.tile.sendToServer(2, -scroll);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < 5; ++i) {
            int index = this.tile.cropOffset * 5 + i;
            if (index > this.tile.syncer.size()) {
                return false;
            }
            int xPos = 20 + i * 28;
            if (mouseY < -28 || mouseY > 0 || mouseX < xPos || mouseX > xPos + 28) continue;
            IC2.AUDIO.playSound(this.tile, SoundEvents.f_12490_.m_11660_(), AudioManager.SoundType.STATIC, 0.2f, 1.0f);
            this.tile.sendToServer(1, index);
            return true;
        }
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    private void drawTopTab(PoseStack matrix, int xOffset, int yOffset, ItemStack item, boolean enabled) {
        if (item.m_41619_()) {
            item = new ItemStack((ItemLike)IC2Blocks.NUKE);
        }
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        ItemRenderer render = this.gui.getRenderItem();
        matrix.m_85836_();
        this.gui.drawTextureRegion(matrix, x + xOffset, y + yOffset, enabled ? 28.0f : 0.0f, 222.0f, 28.0f, enabled ? 31.0f : 28.0f);
        Lighting.m_84931_();
        this.gui.setZLevel(200);
        render.f_115093_ = 200.0f;
        render.m_115203_(item, x + xOffset + 6, y + yOffset + 8);
        this.gui.setZLevel(0);
        render.f_115093_ = 0.0f;
        matrix.m_85849_();
        this.gui.bindDefaultTexture();
    }

    private /* synthetic */ void lambda$init$6(Button T) {
        this.tile.sendToServer(5, 0);
    }

    private /* synthetic */ void lambda$init$5(Button T) {
        this.tile.sendToServer(4, 0);
    }
}

