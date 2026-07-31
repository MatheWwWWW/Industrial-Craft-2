/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.gui.GuiComponent
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.inventory.custom.components;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.SubItemCheckbox;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.friend.FriendSyncRequest;
import ic2.core.networking.packets.client.friend.FriendUpdatePacket;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class FriendComponent
extends GuiWidget {
    static final int GRAY = ColorUtils.darker(ColorUtils.GRAY);
    public static final Box2i LEFT_SLIDER = new Box2i(104, 32, 12, 60);
    public static final Box2i RIGHT_SLIDER = new Box2i(166, 32, 12, 60);
    public static final Box2i TEXTURE = new Box2i(0, 241, 12, 15);
    UUID current = null;
    Friend friend = null;
    List<Action> allActions = Action.getAllActions();
    int lastRight = 0;
    SliderComponent leftSlider = this.addChild(new SliderComponent(LEFT_SLIDER, TEXTURE).setNonEmptyRows(3));
    SliderComponent rightSlider = this.addChild(new SliderComponent(RIGHT_SLIDER, TEXTURE).setNonEmptyRows(3));

    public FriendComponent() {
        super(Box2i.EMPTY_BOX);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.GUI_CLOSE);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        for (int i = 0; i + this.lastRight < this.allActions.size() && i < 6; ++i) {
            Action action = this.allActions.get(i + this.lastRight);
            int index = i;
            gui.addRenderableWidget(10 + i, new SubItemCheckbox(x + 123 + i % 2 * 20, y + 32 + i / 2 * 20, 20, 20, T -> this.toggleAction(index), action.getMainIcon(), action.getSubIcon())).setToolTip(action.getLocal());
        }
        FriendManager friend = FriendManager.getClientFriends();
        friend.setIgnoreUpdates(true);
        List<PlayerInfo> infos = this.getInfos();
        for (int i = 0; i < 4; ++i) {
            int index = i;
            boolean visible = i < infos.size();
            boolean value = visible && friend.getFriend(gui.getPlayerID(), infos.get(i + this.leftSlider.getCurrent()).m_105312_().getId()) != null;
            gui.addRenderableWidget((int)i, new ItemCheckBox((int)(x + 87), (int)(y + 32 + i * 15), (int)14, (int)14, (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((FriendComponent)this, (int)index), (ItemStack)new ItemStack((ItemLike)Items.f_42740_), (boolean)value)).setToolTip((String)"Friend?").f_93624_ = visible;
        }
        gui.addRenderableWidget((int)7, new ExtendedButton((int)(x + 50), (int)(y + 95), (int)80, (int)13, (Component)this.translate((String)"gui.ic2.friends.save"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((FriendComponent)this))).f_93623_ = this.current != null;
        this.rightSlider.setMax(Mth.m_14165_((double)((double)this.allActions.size() / 2.0)));
    }

    @OnlyIn(value=Dist.CLIENT)
    private void toggleFriend(int index) {
        List<PlayerInfo> infos = this.getInfos();
        if ((index += this.leftSlider.getCurrent()) < 0 || index >= infos.size()) {
            return;
        }
        FriendManager friend = FriendManager.getClientFriends();
        GameProfile profile = infos.get(index).m_105312_();
        if (friend.getFriend(this.gui.getPlayerID(), profile.getId()) == null) {
            friend.addFriend(this.gui.getPlayerID(), new Friend(profile));
        } else {
            friend.removeFriend(this.gui.getPlayerID(), profile.getId());
        }
        PacketManager.INSTANCE.sendToServer(new FriendUpdatePacket(friend.getFriends(this.gui.getPlayerID())));
    }

    private void toggleAction(int index) {
        if ((index += this.rightSlider.getCurrent() * 2) < 0 || index >= this.allActions.size() || this.friend == null) {
            return;
        }
        Action action = this.allActions.get(index);
        if (this.friend.canApplyAction(action)) {
            this.friend.removeAction(action);
        } else {
            this.friend.addAction(action);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void save() {
        if (this.current == null) {
            return;
        }
        PacketManager.INSTANCE.sendToServer(new FriendUpdatePacket(FriendManager.getClientFriends().getFriends(this.gui.getPlayerID())));
        this.friend = null;
        this.current = null;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onClose() {
        FriendManager.getClientFriends().setIgnoreUpdates(false);
        PacketManager.INSTANCE.sendToServer(new FriendSyncRequest());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        int i;
        int actual;
        gui.getButton((int)7).f_93623_ = this.current != null;
        this.leftSlider.setMax(this.getInfos().size());
        FriendManager friend = FriendManager.getClientFriends();
        List<PlayerInfo> infos = this.getInfos();
        for (int i2 = 0; i2 + this.leftSlider.getCurrent() < infos.size() && i2 < 4; ++i2) {
            if (i2 + this.leftSlider.getCurrent() >= infos.size()) {
                gui.getCastedButton((int)i2, ItemCheckBox.class).f_93624_ = false;
                continue;
            }
            UUID entry = infos.get(i2 + this.leftSlider.getCurrent()).m_105312_().getId();
            boolean value = friend.getFriend(gui.getPlayerID(), entry) != null;
            ItemCheckBox checkbox = gui.getCastedButton(i2, ItemCheckBox.class).setChecked(value);
            checkbox.f_93623_ = this.current == null || !this.current.equals(entry);
            checkbox.f_93624_ = true;
        }
        if (this.lastRight != this.rightSlider.getCurrent()) {
            this.lastRight = this.rightSlider.getCurrent();
            actual = this.lastRight * 2;
            for (int i3 = 0; i3 + actual < this.allActions.size() && i3 < 6; ++i3) {
                Action action = this.allActions.get(i3 + actual);
                gui.getCastedButton(10 + i3, SubItemCheckbox.class).setIcons(action.getMainIcon(), action.getSubIcon()).setToolTip(action.getLocal());
            }
        }
        actual = this.lastRight * 2;
        int left = 6;
        for (i = 0; i + actual < this.allActions.size() && i < 6; ++i) {
            SubItemCheckbox box = gui.getCastedButton(10 + i, SubItemCheckbox.class);
            box.setChecked((boolean)(this.friend != null && this.friend.canApplyAction((Action)this.allActions.get((int)(i + actual))) ? true : false)).f_93623_ = this.current != null;
            box.f_93624_ = true;
            --left;
        }
        if (left > 0) {
            for (i = 6 - left; i < 6; ++i) {
                gui.getCastedButton((int)(10 + i), SubItemCheckbox.class).f_93624_ = false;
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        List<PlayerInfo> infos = this.getInfos();
        for (int i = 0; i + this.leftSlider.getCurrent() < infos.size() && i < 4; ++i) {
            PlayerInfo info = infos.get(i + this.leftSlider.getCurrent());
            UUID id = info.m_105312_().getId();
            if (id != null && mouseX >= 6 && mouseX <= 86 && mouseY >= 32 + i * 15 && mouseY < 47 + i * 15) {
                this.gui.drawColoredRegion(matrix, x + 6, y + 32 + i * 15, 95.0f, 14.0f, ColorUtils.LIGHT_GRAY);
            } else if (id != null && id.equals(this.current)) {
                this.gui.drawColoredRegion(matrix, x + 6, y + 32 + i * 15, 95.0f, 14.0f, GRAY);
            }
            this.gui.bindTexture(info.m_105337_());
            GuiComponent.m_93160_((PoseStack)matrix, (int)(x + 6), (int)(y + 32 + i * 15), (int)14, (int)14, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)64, (int)64);
            RenderSystem.m_69478_();
            GuiComponent.m_93160_((PoseStack)matrix, (int)(x + 6), (int)(y + 32 + i * 15), (int)14, (int)14, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)64, (int)64);
            RenderSystem.m_69461_();
            this.gui.drawColoredRegion(matrix, x + 6, y + 46 + i * 15, 95.0f, 1.0f, -13158601);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int index = 0;
        List<PlayerInfo> infos = this.getInfos();
        this.gui.drawRightString(matrix, (Component)this.string(this.leftSlider.getCurrent() + "/" + Math.max(0, infos.size() - 3)), 117, 22, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.string(this.rightSlider.getCurrent() + "/" + Math.max(0, (this.allActions.size() - 6) / 2)), 179, 22, 0x404040);
        for (int i = 0; i + this.leftSlider.getCurrent() < infos.size() && i < 4; ++i) {
            PlayerInfo info = infos.get(i + this.leftSlider.getCurrent());
            MutableComponent text = this.string(info.m_105312_().getName());
            if (text == null) continue;
            this.gui.drawString(matrix, (Component)text, 27, 35 + index * 15, 0x404040);
            ++index;
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public List<PlayerInfo> getInfos() {
        ObjectList infos = CollectionUtils.createList();
        infos.addAll(this.gui.getPlayer().f_108617_.m_105142_());
        infos.removeIf(T -> this.gui.getPlayerID().equals(T.m_105312_().getId()));
        return infos;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        Friend friend;
        if (mouseY < 32 || mouseX < 6 || mouseX > 86) {
            return false;
        }
        int index = (mouseY - 32) / 15;
        List<PlayerInfo> infos = this.getInfos();
        if (index >= 4 || index + this.leftSlider.getCurrent() >= infos.size()) {
            return false;
        }
        UUID id = infos.get(index + this.leftSlider.getCurrent()).m_105312_().getId();
        if (id != null && (friend = FriendManager.getClientFriends().getFriend(this.gui.getPlayerID(), id)) != null) {
            IC2.AUDIO.playSound(this.gui.getPlayer(), SoundEvents.f_12490_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 1.0f);
            this.current = id;
            this.friend = friend;
            return true;
        }
        return false;
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.save();
    }

    private /* synthetic */ void lambda$init$1(int index, Button T) {
        this.toggleFriend(index);
    }
}

