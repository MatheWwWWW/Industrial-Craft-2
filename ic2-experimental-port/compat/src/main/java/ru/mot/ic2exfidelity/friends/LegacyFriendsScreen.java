package ru.mot.ic2exfidelity.friends;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

/** Classic-style online-player friend and action editor. */
public final class LegacyFriendsScreen extends Ic2Gui<LegacyFriendsMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(
            "ic2", "textures/gui_sprites/misc/gui_friends.png");
    private final List<ExtendedButton> friendButtons = new ArrayList<>();
    private ExtendedButton actionButton;
    private ExtendedButton saveButton;
    private UUID selected;
    private boolean selectedBreakPermission;
    private int offset;

    public LegacyFriendsScreen(
            LegacyFriendsMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 187, 137);
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        friendButtons.clear();
        for (int row = 0; row < 4; row++) {
            int selectedRow = row;
            ExtendedButton button = new ExtendedButton(
                    f_97735_ + 87, f_97736_ + 32 + row * 15,
                    14, 14, Component.m_237113_(" "),
                    ignored -> toggleFriend(selectedRow));
            friendButtons.add(button);
            m_142416_(button);
        }
        actionButton = new ExtendedButton(
                f_97735_ + 123, f_97736_ + 32, 55, 20,
                Component.m_237115_("gui.ic2.friends.player_break_iridium"),
                ignored -> toggleAction());
        m_142416_(actionButton);
        saveButton = new ExtendedButton(
                f_97735_ + 50, f_97736_ + 95, 80, 13,
                Component.m_237115_("gui.ic2.friends.save"),
                ignored -> save());
        m_142416_(saveButton);
        refreshButtons();
    }

    @Override
    protected void drawBackgroundAndTitle(
            PoseStack pose, float partialTick, int mouseX, int mouseY) {
        bindTexture();
        m_93228_(pose, f_97735_, f_97736_, 0, 0, f_97726_, f_97727_);
        refreshButtons();
    }

    @Override
    protected void drawForegroundLayer(PoseStack pose, int mouseX, int mouseY) {
        List<PlayerInfo> players = players();
        drawXCenteredString(pose, f_97726_ / 2, 11,
                Component.m_237115_("gui.ic2.friends"), 0x404040, false);
        drawString(pose, 82, 22,
                offset + "/" + Math.max(0, players.size() - 4),
                0x404040, false);
        for (int row = 0; row < 4 && row + offset < players.size(); row++) {
            GameProfile profile = players.get(row + offset).m_105312_();
            String prefix = profile.getId().equals(selected) ? "> " : "";
            drawString(pose, 27, 35 + row * 15,
                    prefix + profile.getName(), 0x404040, false);
        }
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int x = (int) mouseX - f_97735_;
            int y = (int) mouseY - f_97736_;
            if (x >= 6 && x <= 86 && y >= 32 && y < 92) {
                int row = (y - 32) / 15;
                List<PlayerInfo> players = players();
                if (row + offset < players.size()) {
                    GameProfile profile = players.get(row + offset).m_105312_();
                    LegacyFriendManager.FriendEntry entry =
                            LegacyFriendClientState.get(profile.getId());
                    if (entry != null) {
                        selected = profile.getId();
                        selectedBreakPermission = entry.canBreakIridium();
                        refreshButtons();
                        return true;
                    }
                }
            }
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    @Override
    public boolean m_6050_(double mouseX, double mouseY, double amount) {
        List<PlayerInfo> players = players();
        if (amount < 0.0) {
            offset = Math.min(Math.max(0, players.size() - 4), offset + 1);
        } else if (amount > 0.0) {
            offset = Math.max(0, offset - 1);
        }
        refreshButtons();
        return true;
    }

    private void toggleFriend(int row) {
        List<PlayerInfo> players = players();
        int index = row + offset;
        if (index < 0 || index >= players.size()) {
            return;
        }
        GameProfile profile = players.get(index).m_105312_();
        LegacyFriendManager.FriendEntry existing =
                LegacyFriendClientState.get(profile.getId());
        boolean enabled = existing == null;
        LegacyFriendClientState.put(
                profile.getId(), profile.getName(), enabled, false);
        LegacyFriendNetwork.update(
                profile.getId(), profile.getName(), enabled, false);
        if (!enabled && profile.getId().equals(selected)) {
            selected = null;
            selectedBreakPermission = false;
        }
        refreshButtons();
    }

    private void toggleAction() {
        if (selected != null) {
            selectedBreakPermission = !selectedBreakPermission;
            refreshButtons();
        }
    }

    private void save() {
        if (selected == null) {
            return;
        }
        LegacyFriendManager.FriendEntry entry =
                LegacyFriendClientState.get(selected);
        if (entry == null) {
            selected = null;
            refreshButtons();
            return;
        }
        LegacyFriendClientState.put(
                selected, entry.name(), true, selectedBreakPermission);
        LegacyFriendNetwork.update(
                selected, entry.name(), true, selectedBreakPermission);
        selected = null;
        selectedBreakPermission = false;
        refreshButtons();
    }

    private void refreshButtons() {
        List<PlayerInfo> players = players();
        offset = Math.min(offset, Math.max(0, players.size() - 4));
        for (int row = 0; row < friendButtons.size(); row++) {
            ExtendedButton button = friendButtons.get(row);
            boolean visible = row + offset < players.size();
            button.f_93624_ = visible;
            if (!visible) {
                continue;
            }
            UUID id = players.get(row + offset).m_105312_().getId();
            boolean friend = LegacyFriendClientState.get(id) != null;
            button.m_93666_(Component.m_237113_(friend ? "✓" : " "));
            button.f_93623_ = selected == null || !selected.equals(id);
        }
        if (actionButton != null) {
            actionButton.f_93623_ = selected != null;
            actionButton.m_93666_(Component.m_237113_(
                    selectedBreakPermission ? "Иридий ✓" : "Иридий —"));
        }
        if (saveButton != null) {
            saveButton.f_93623_ = selected != null;
        }
    }

    private List<PlayerInfo> players() {
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91074_ == null
                || minecraft.f_91074_.f_108617_ == null) {
            return List.of();
        }
        UUID self = minecraft.f_91074_.m_20148_();
        List<PlayerInfo> result = new ArrayList<>(
                minecraft.f_91074_.f_108617_.m_105142_());
        result.removeIf(info -> self.equals(info.m_105312_().getId()));
        result.sort(Comparator.comparing(
                info -> info.m_105312_().getName(),
                String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    @Override
    protected ResourceLocation getTexture() {
        return BACKGROUND;
    }
}
