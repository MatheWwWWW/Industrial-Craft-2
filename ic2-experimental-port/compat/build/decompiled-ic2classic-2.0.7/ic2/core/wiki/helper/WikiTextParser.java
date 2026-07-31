/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.wiki.helper;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.helper.text.HeaderTextObject;
import ic2.core.wiki.helper.text.ITextObject;
import ic2.core.wiki.helper.text.ImageTextObject;
import ic2.core.wiki.helper.text.NewPageObject;
import ic2.core.wiki.helper.text.PaddingObject;
import ic2.core.wiki.helper.text.ResetHeaderObject;
import ic2.core.wiki.helper.text.TextObject;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableObject;

@OnlyIn(value=Dist.CLIENT)
public class WikiTextParser {
    public static List<ITextObject> convertMultiline(Component comp, int maxWidth) {
        ObjectList result = CollectionUtils.createList();
        MutableObject transformed = new MutableObject((Object)Component.m_237113_((String)""));
        Runnable finish = () -> {
            result.add(new TextObject(Minecraft.m_91087_().f_91062_.m_92923_((FormattedText)transformed.getValue(), maxWidth)));
            transformed.setValue((Object)Component.m_237113_((String)""));
        };
        comp.m_7451_((K, V) -> {
            int end;
            String newString = V;
            int start = newString.indexOf("\u00a7<");
            while (start >= 0 && (end = newString.indexOf(">", start) + 1) > start) {
                String toReplace = newString.substring(start, end);
                String[] arguments = toReplace.substring(2, toReplace.length() - 1).split(";");
                if (arguments.length > 0) {
                    KeyMapping mapping;
                    if (start > 0) {
                        ((MutableComponent)transformed.getValue()).m_7220_((Component)Component.m_237113_((String)newString.substring(0, start)).m_6270_(K));
                    }
                    if (arguments[0].equalsIgnoreCase("redirect")) {
                        if (arguments.length >= 4) {
                            Style style = K.m_131142_(WikiTextParser.createClickEvent(arguments)).m_131136_(Boolean.valueOf(Boolean.parseBoolean(arguments[1]))).m_131144_(WikiTextParser.createHoverEvent(arguments));
                            ((MutableComponent)transformed.getValue()).m_7220_((Component)Component.m_237115_((String)arguments[2]).m_6270_(style));
                        }
                    } else if (arguments[0].equalsIgnoreCase("newPage")) {
                        finish.run();
                        result.add(new NewPageObject());
                    } else if (arguments[0].equalsIgnoreCase("header")) {
                        finish.run();
                        if (arguments.length >= 2) {
                            result.add(new HeaderTextObject((Component)Component.m_237115_((String)arguments[1]).m_130940_(ChatFormatting.BOLD), arguments.length >= 3 && Boolean.parseBoolean(arguments[2])));
                        }
                    } else if (arguments[0].equalsIgnoreCase("reset_header")) {
                        finish.run();
                        result.add(new ResetHeaderObject(arguments.length >= 2 && Boolean.parseBoolean(arguments[1])));
                    } else if (arguments[0].equalsIgnoreCase("image")) {
                        finish.run();
                        if (arguments.length >= 3) {
                            ImageTextObject object = new ImageTextObject(arguments[1], Float.parseFloat(arguments[2]));
                            if (arguments.length >= 5) {
                                object.setOffset(Float.parseFloat(arguments[3]));
                                object.setWidth(Float.parseFloat(arguments[4]));
                            }
                            result.add(object);
                        }
                    } else if (arguments[0].equalsIgnoreCase("padding")) {
                        finish.run();
                        if (arguments.length >= 2) {
                            result.add(new PaddingObject(Integer.parseInt(arguments[1])));
                        }
                    } else if (arguments[0].equalsIgnoreCase("key") && arguments.length >= 2 && (mapping = WikiTextParser.getKey(arguments[1])) != null) {
                        ((MutableComponent)transformed.getValue()).m_7220_((Component)mapping.m_90863_().m_6881_().m_6270_(Style.f_131099_.m_131136_(Boolean.valueOf(true))));
                    }
                    newString = newString.substring(end);
                    if (newString.startsWith("\n")) {
                        newString = newString.substring(1);
                    }
                }
                start = newString.indexOf("\u00a7<");
            }
            if (newString.length() > 0) {
                ((MutableComponent)transformed.getValue()).m_7220_((Component)Component.m_237113_((String)newString));
            }
            return Optional.empty();
        }, Style.f_131099_);
        finish.run();
        return result;
    }

    @OnlyIn(value=Dist.CLIENT)
    private static KeyMapping getKey(String name) {
        for (KeyMapping binding : Minecraft.m_91087_().f_91066_.f_92059_) {
            if (!binding.m_90860_().equals(name)) continue;
            return binding;
        }
        return null;
    }

    public static List<FormattedCharSequence> convert(Component comp, int maxWidth) {
        MutableComponent transformed = Component.m_237113_((String)"");
        comp.m_7451_((K, V) -> {
            int end;
            String newString = V;
            int start = newString.indexOf("\u00a7<");
            while (start >= 0 && (end = newString.indexOf(">") + 1) > start) {
                String toReplace = newString.substring(start, end);
                String[] arguments = toReplace.substring(2, toReplace.length() - 1).split(";");
                if (arguments.length >= 4) {
                    if (start > 0) {
                        transformed.m_7220_((Component)Component.m_237113_((String)newString.substring(0, start)).m_6270_(K));
                    }
                    if (arguments[0].equalsIgnoreCase("redirect")) {
                        Style style = K.m_131142_(WikiTextParser.createClickEvent(arguments)).m_131136_(Boolean.valueOf(Boolean.parseBoolean(arguments[1]))).m_131144_(WikiTextParser.createHoverEvent(arguments));
                        transformed.m_7220_((Component)Component.m_237115_((String)arguments[2]).m_6270_(style));
                    }
                    newString = newString.substring(end);
                }
                start = newString.indexOf("\u00a7<");
            }
            if (newString.length() > 0) {
                transformed.m_7220_((Component)Component.m_237113_((String)newString));
            }
            return Optional.empty();
        }, Style.f_131099_);
        return Minecraft.m_91087_().f_91062_.m_92923_((FormattedText)transformed, maxWidth);
    }

    private static ClickEvent createClickEvent(String[] data) {
        return new ClickEvent(ClickEvent.Action.CHANGE_PAGE, data[0] + ";" + data[3] + ";" + (data.length > 4 ? data[4] : "0"));
    }

    private static HoverEvent createHoverEvent(String[] data) {
        return new HoverEvent(HoverEvent.Action.f_130831_, (Object)Component.m_237113_((String)("Open " + data[2])));
    }
}

