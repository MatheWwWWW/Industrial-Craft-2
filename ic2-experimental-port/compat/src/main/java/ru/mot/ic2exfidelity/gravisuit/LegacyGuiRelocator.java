package ru.mot.ic2exfidelity.gravisuit;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/** Original add/list relocator screens backed by server-authoritative packets. */
public final class LegacyGuiRelocator extends Ic2Gui<LegacyContainerRelocator> {
    private static final ResourceLocation ADD = new ResourceLocation(
            "gravisuit", "textures/gui/relocator_add.png");
    private static final ResourceLocation DISPLAY = new ResourceLocation(
            "gravisuit", "textures/gui/relocator_display.png");
    private EditBox nameBox;

    public LegacyGuiRelocator(
            LegacyContainerRelocator menu, Inventory inventory, Component title) {
        super(menu, inventory, title, menu.base.isAddMode() ? 66 : 116);
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (getContainer().base.isAddMode()) {
            nameBox = new EditBox(f_96547_, f_97735_ + 14, f_97736_ + 19,
                    148, 16, Component.m_237119_());
            nameBox.m_94199_(32_500);
            m_142416_(nameBox);
        }
    }

    @Override
    protected void drawBackgroundAndTitle(
            PoseStack pose, float partialTick, int mouseX, int mouseY) {
        bindTexture();
        m_93228_(pose, f_97735_, f_97736_, 0, 0, f_97726_, f_97727_);
        if (!getContainer().base.isAddMode()) {
            drawRows(pose, mouseX - f_97735_, mouseY - f_97736_);
        }
    }

    @Override
    protected void drawForegroundLayer(PoseStack pose, int mouseX, int mouseY) {
        if (getContainer().base.isAddMode()) {
            return;
        }
        List<String> names = locationNames();
        for (int i = 0; i < names.size() && i < 10; i++) {
            drawString(pose, 4, 5 + i * 11, names.get(i),
                    Color.WHITE.getRGB(), false);
        }
    }

    private void drawRows(PoseStack pose, int mouseX, int mouseY) {
        CompoundTag root = held().m_41784_();
        String defaultName = root.m_128461_("DefaultLocation");
        List<String> names = locationNames();
        for (int i = 0; i < names.size() && i < 10; i++) {
            int rowY = 3 + i * 11;
            int sourceY = names.get(i).equals(defaultName) ? 155 : 116;
            if (within(mouseX, 4, 160) && within(mouseY, rowY + 1, rowY + 10)) {
                sourceY = 129;
            } else if (within(mouseX, 163, 172)
                    && within(mouseY, rowY + 1, rowY + 10)) {
                sourceY = 142;
            }
            m_93228_(pose, f_97735_ + 3, f_97736_ + rowY,
                    0, sourceY, 170, 11);
        }
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.m_6375_(mouseX, mouseY, button);
        }
        int x = (int) mouseX - f_97735_;
        int y = (int) mouseY - f_97736_;
        if (getContainer().base.isAddMode()) {
            if (within(x, 60, 84) && within(y, 44, 55)) {
                addLocation();
                return true;
            }
            if (within(x, 92, 116) && within(y, 44, 55)) {
                nameBox.m_94144_("");
                return true;
            }
            return super.m_6375_(mouseX, mouseY, button);
        }

        List<String> names = locationNames();
        for (int i = 0; i < names.size() && i < 10; i++) {
            int rowY = 4 + i * 11;
            String name = names.get(i);
            if (within(y, rowY, rowY + 9) && within(x, 163, 172)) {
                LegacyRelocatorNetwork.remove(getContainer().base.hand(), name);
                held().m_41784_().m_128469_("Locations").m_128473_(name);
                return true;
            }
            if (within(y, rowY, rowY + 9) && within(x, 4, 160)) {
                if (Screen.m_96638_()
                        || held().m_41784_().m_128445_("mode") != 0) {
                    LegacyRelocatorNetwork.setDefault(
                            getContainer().base.hand(), name);
                    held().m_41784_().m_128359_("DefaultLocation", name);
                } else {
                    LegacyRelocatorNetwork.teleport(
                            getContainer().base.hand(), name);
                    Minecraft.m_91087_().f_91074_.m_6915_();
                }
                return true;
            }
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    private void addLocation() {
        String name = nameBox.m_94155_();
        if (name == null || name.isEmpty()) {
            return;
        }
        CompoundTag root = held().m_41784_();
        CompoundTag locations = root.m_128469_("Locations");
        if (locations.m_128403_(name) || locations.m_128440_() >= 11) {
            return;
        }
        var player = Minecraft.m_91087_().f_91074_;
        LegacyRelocatorData location = new LegacyRelocatorData(
                new BlockPos(player.m_20182_()).m_121878_(),
                player.m_9236_().m_46472_().m_135782_().toString(), name);
        locations.m_128365_(name, location.write());
        root.m_128365_("Locations", locations);
        LegacyRelocatorNetwork.add(getContainer().base.hand(), location);
        nameBox.m_94144_("");
    }

    private List<String> locationNames() {
        CompoundTag root = held().m_41784_();
        if (!root.m_128403_("Locations")) {
            return List.of();
        }
        return new ArrayList<>(root.m_128469_("Locations").m_128431_());
    }

    private net.minecraft.world.item.ItemStack held() {
        return getContainer().base.player.m_21120_(getContainer().base.hand());
    }

    private static boolean within(int value, int minimum, int maximum) {
        return value >= minimum && value <= maximum;
    }

    @Override
    protected ResourceLocation getTexture() {
        return getContainer().base.isAddMode() ? ADD : DISPLAY;
    }
}
