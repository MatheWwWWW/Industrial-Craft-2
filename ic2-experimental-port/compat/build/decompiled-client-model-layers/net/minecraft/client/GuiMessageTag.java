/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public record GuiMessageTag(int f_240386_, @Nullable Icon f_240355_, @Nullable Component f_240381_, @Nullable String f_240342_) {
    private static final Component f_240380_ = Component.m_237115_("chat.tag.not_secure").m_130940_(ChatFormatting.UNDERLINE);
    private static final Component f_240377_ = Component.m_237115_("chat.tag.modified").m_130940_(ChatFormatting.UNDERLINE);
    private static final Component f_243011_ = Component.m_237115_("chat.tag.filtered").m_130940_(ChatFormatting.UNDERLINE);
    private static final int f_240665_ = 0xA0A0A0;
    private static final int f_240384_ = 15224664;
    private static final int f_240357_ = 15386724;
    private static final GuiMessageTag f_240673_ = new GuiMessageTag(0xA0A0A0, null, null, "System");
    private static final GuiMessageTag f_240362_ = new GuiMessageTag(15224664, Icon.CHAT_NOT_SECURE, f_240380_, "Not Secure");
    private static final GuiMessageTag f_243014_ = new GuiMessageTag(15386724, Icon.CHAT_MODIFIED, f_243011_, "Filtered");
    static final ResourceLocation f_240343_ = new ResourceLocation("textures/gui/chat_tags.png");

    public static GuiMessageTag m_240701_() {
        return f_240673_;
    }

    public static GuiMessageTag m_240400_() {
        return f_240362_;
    }

    public static GuiMessageTag m_240466_(String p_242878_) {
        MutableComponent $$1 = Component.m_237110_("chat.tag.modified.original", p_242878_);
        MutableComponent $$2 = Component.m_237119_().m_7220_(f_240377_).m_7220_(CommonComponents.f_178388_).m_7220_($$1);
        return new GuiMessageTag(15386724, Icon.CHAT_MODIFIED, $$2, "Modified");
    }

    public static GuiMessageTag m_243051_() {
        return f_243014_;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{GuiMessageTag.class, "indicatorColor;icon;text;logTag", "f_240386_", "f_240355_", "f_240381_", "f_240342_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GuiMessageTag.class, "indicatorColor;icon;text;logTag", "f_240386_", "f_240355_", "f_240381_", "f_240342_"}, this);
    }

    @Override
    public final boolean equals(Object p_240542_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GuiMessageTag.class, "indicatorColor;icon;text;logTag", "f_240386_", "f_240355_", "f_240381_", "f_240342_"}, this, p_240542_);
    }

    public static final class Icon
    extends Enum<Icon> {
        public static final /* enum */ Icon CHAT_NOT_SECURE = new Icon(0, 0, 9, 9);
        public static final /* enum */ Icon CHAT_MODIFIED = new Icon(9, 0, 9, 9);
        public final int f_240366_;
        public final int f_240349_;
        public final int f_240358_;
        public final int f_240372_;
        private static final /* synthetic */ Icon[] $VALUES;

        public static Icon[] values() {
            return (Icon[])$VALUES.clone();
        }

        public static Icon valueOf(String p_240592_) {
            return Enum.valueOf(Icon.class, p_240592_);
        }

        private Icon(int p_240599_, int p_240544_, int p_240607_, int p_240531_) {
            this.f_240366_ = p_240599_;
            this.f_240349_ = p_240544_;
            this.f_240358_ = p_240607_;
            this.f_240372_ = p_240531_;
        }

        public void m_240420_(PoseStack p_243334_, int p_240533_, int p_240626_) {
            RenderSystem.m_157456_(0, f_240343_);
            GuiComponent.m_93133_(p_243334_, p_240533_, p_240626_, this.f_240366_, this.f_240349_, this.f_240358_, this.f_240372_, 32, 32);
        }

        private static /* synthetic */ Icon[] m_240404_() {
            return new Icon[]{CHAT_NOT_SECURE, CHAT_MODIFIED};
        }

        static {
            $VALUES = Icon.m_240404_();
        }
    }
}

