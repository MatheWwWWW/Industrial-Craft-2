/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSink
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class TextHelper {
    public static String convertText(Component component) {
        StringConsumer consumer = new StringConsumer();
        component.m_7532_().m_13731_((FormattedCharSink)consumer);
        return consumer.getBuilder().toString();
    }

    public static void splitString(Component component, Consumer<Component> listener) {
        for (String s : TextHelper.convertText(component).split("\n")) {
            listener.accept((Component)Component.m_237113_((String)s));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class StringConsumer
    implements FormattedCharSink {
        StringBuilder builder = new StringBuilder();
        ChatFormatting formatting = ChatFormatting.RESET;

        public boolean m_6411_(int index, Style style, int character) {
            ChatFormatting format = this.getFormat(style);
            if (format != this.formatting) {
                this.formatting = format;
                this.builder.append(format.toString());
            }
            this.builder.append((char)character);
            return true;
        }

        protected ChatFormatting getFormat(Style style) {
            ChatFormatting format = style.m_131135_() == null ? null : ChatFormatting.m_126657_((String)style.m_131135_().m_131274_());
            return format == null ? ChatFormatting.RESET : format;
        }

        public StringBuilder getBuilder() {
            return this.builder;
        }
    }
}

