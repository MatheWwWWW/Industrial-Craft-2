/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.FormattedCharSequence
 */
package ic2.core.wiki.components.builders;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.base.IPageBuilder;
import ic2.core.wiki.components.HeaderComponent;
import ic2.core.wiki.components.TextComponent;
import ic2.core.wiki.components.builders.IWikiObj;
import ic2.core.wiki.helper.WikiTextParser;
import ic2.core.wiki.helper.text.HeaderTextObject;
import ic2.core.wiki.helper.text.ITextObject;
import ic2.core.wiki.helper.text.ImageTextObject;
import ic2.core.wiki.helper.text.NewPageObject;
import ic2.core.wiki.helper.text.PaddingObject;
import ic2.core.wiki.helper.text.ResetHeaderObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class TextObj
implements IWikiObj {
    Component text;
    Component header;
    boolean cutOff;
    int maxWidth = 118;

    public TextObj(String s) {
        this((Component)Component.m_237115_((String)s));
    }

    public TextObj(String s, ChatFormatting ... formats) {
        this((Component)Component.m_237115_((String)s).m_130944_(formats));
    }

    public TextObj(Component text) {
        this.text = text;
    }

    public TextObj setCutOff() {
        this.cutOff = true;
        return this;
    }

    public TextObj setMaxWidth(int pixels) {
        this.maxWidth = pixels;
        return this;
    }

    public TextObj withHeader(String text) {
        return this.withHeader((Component)Component.m_237115_((String)text).m_130940_(ChatFormatting.BOLD));
    }

    public TextObj withHeader(Component header) {
        this.header = header;
        return this;
    }

    @Override
    public void provideLang(Consumer<Component> comp) {
        comp.accept(this.text);
        if (this.header != null) {
            comp.accept(this.header);
        }
    }

    @Override
    public void buildComponents(IPageBuilder builder) {
        block14: {
            int rowsLeft;
            List<ITextObject> pages;
            block15: {
                block13: {
                    pages = WikiTextParser.convertMultiline(this.text, this.maxWidth);
                    if (pages.isEmpty()) {
                        return;
                    }
                    rowsLeft = builder.getHeightLeft() / 9;
                    if (pages.size() != 1 || rowsLeft < pages.get(0).getText().size()) break block13;
                    builder.addComponent(new TextComponent(pages.get(0).getText()));
                    break block14;
                }
                if (!this.cutOff) break block15;
                builder.addComponent(new TextComponent((List<FormattedCharSequence>)new ObjectArrayList(pages.get(0).getText().subList(0, rowsLeft))));
                break block14;
            }
            Component currentHeader = this.header;
            int m = pages.size();
            for (int i = 0; i < m; ++i) {
                block17: {
                    ITextObject obj;
                    block18: {
                        block16: {
                            obj = pages.get(i);
                            if (!(obj instanceof HeaderTextObject)) break block16;
                            HeaderTextObject object = (HeaderTextObject)obj;
                            currentHeader = object.getHeader();
                            if (object.shouldApplyInstantly()) {
                                builder.clearPage();
                                builder.addComponent(new HeaderComponent(currentHeader));
                            }
                            break block17;
                        }
                        if (!(obj instanceof ResetHeaderObject)) break block18;
                        ResetHeaderObject object = (ResetHeaderObject)obj;
                        currentHeader = this.header;
                        if (!object.shouldApplyInstantly()) break block17;
                        builder.clearPage();
                        if (currentHeader == null) break block17;
                        builder.addComponent(new HeaderComponent(currentHeader));
                        break block17;
                    }
                    if (obj instanceof ImageTextObject) {
                        ImageTextObject object = (ImageTextObject)obj;
                        builder.addComponent(object.createComponent());
                    } else if (obj instanceof NewPageObject) {
                        builder.setEndOfPage();
                    } else if (obj instanceof PaddingObject) {
                        PaddingObject object = (PaddingObject)obj;
                        builder.addComponent(object.create());
                    } else {
                        List<FormattedCharSequence> lines = obj.getText();
                        int offset = 0;
                        while (offset < lines.size()) {
                            ObjectList subList = CollectionUtils.createList();
                            for (int j = 0; j + offset < lines.size() && j < rowsLeft; ++j) {
                                subList.add((FormattedCharSequence)lines.get(j + offset));
                            }
                            builder.addComponent(new TextComponent((List<FormattedCharSequence>)subList));
                            if ((offset += subList.size()) < lines.size()) {
                                builder.setEndOfPage();
                                if (currentHeader != null) {
                                    builder.addComponent(new HeaderComponent(currentHeader));
                                }
                            }
                            rowsLeft = builder.getHeightLeft() / 9;
                        }
                    }
                }
                rowsLeft = builder.getHeightLeft() / 9;
            }
        }
    }
}

