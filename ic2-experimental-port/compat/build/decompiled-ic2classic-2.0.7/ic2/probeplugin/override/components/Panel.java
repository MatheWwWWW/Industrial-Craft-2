/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.ILayoutStyle
 *  mcjty.theoneprobe.apiimpl.ProbeInfo
 *  mcjty.theoneprobe.rendering.RenderHelper
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.probeplugin.override.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.probeplugin.override.IExpandedProbeInfo;
import ic2.probeplugin.override.styles.ILayoutStyleBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.apiimpl.ProbeInfo;
import mcjty.theoneprobe.rendering.RenderHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Panel
implements IExpandedProbeInfo,
IElement {
    public static final ResourceLocation ELEMENT_ID = new ResourceLocation("ic2", "panel");
    List<IElement> children = new ObjectArrayList();
    protected ElementAlignment horizontal;
    protected ElementAlignment vertical;
    protected ILayoutStyle layout;
    protected Integer borderColor;
    protected int spacing;

    public Panel(Type type) {
        this(ILayoutStyleBuilder.create(), type);
    }

    public Panel(ILayoutStyle layout, Type type) {
        this(layout, type == Type.VERTICAL);
    }

    public Panel(boolean vertical) {
        this(ILayoutStyleBuilder.create(), vertical);
    }

    public Panel(ILayoutStyle layout, boolean vertical) {
        if (vertical) {
            this.vertical = layout.getAlignment();
        } else {
            this.horizontal = layout.getAlignment();
        }
        this.layout = layout;
        this.borderColor = layout.getBorderColor();
        this.spacing = layout.getSpacing();
    }

    public Panel(FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            this.vertical = (ElementAlignment)buffer.m_130066_(ElementAlignment.class);
        } else {
            this.horizontal = (ElementAlignment)buffer.m_130066_(ElementAlignment.class);
        }
        if (buffer.readBoolean()) {
            this.borderColor = buffer.readInt();
        }
        this.spacing = buffer.readInt();
        this.layout = ILayoutStyleBuilder.create().topPadding(buffer.readInt()).bottomPadding(buffer.readInt()).leftPadding(buffer.readInt()).rightPadding(buffer.readInt());
        this.children.addAll(ProbeInfo.createElements((FriendlyByteBuf)buffer));
    }

    public ILayoutStyle getLayout() {
        return this.layout;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.vertical != null);
        buffer.m_130068_((Enum)(this.vertical != null ? this.vertical : this.horizontal));
        buffer.writeBoolean(this.borderColor != null);
        if (this.borderColor != null) {
            buffer.writeInt(this.borderColor.intValue());
        }
        buffer.writeInt(this.spacing).writeInt(this.layout.getTopPadding()).writeInt(this.layout.getBottomPadding()).writeInt(this.layout.getLeftPadding()).writeInt(this.layout.getRightPadding());
        ProbeInfo.writeElements(this.children, (FriendlyByteBuf)buffer);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void render(PoseStack matrix, int x, int y) {
        block14: {
            block13: {
                if (this.borderColor != null) {
                    int w = this.getWidth();
                    int h = this.getHeight();
                    RenderHelper.drawHorizontalLine((PoseStack)matrix, (int)x, (int)y, (int)(x + w - 1), (int)this.borderColor);
                    RenderHelper.drawHorizontalLine((PoseStack)matrix, (int)x, (int)(y + h - 1), (int)(x + w - 1), (int)this.borderColor);
                    RenderHelper.drawVerticalLine((PoseStack)matrix, (int)x, (int)y, (int)(y + h - 1), (int)this.borderColor);
                    RenderHelper.drawVerticalLine((PoseStack)matrix, (int)(x + w - 1), (int)y, (int)(y + h), (int)this.borderColor);
                    x += 3;
                    y += 3;
                }
                if (this.horizontal == null) break block13;
                x += this.layout.getLeftPadding();
                int totHeight = this.getHeight() - this.getYPadding();
                for (IElement element : this.children) {
                    int h = element.getHeight();
                    int cy = y;
                    switch (this.horizontal) {
                        case ALIGN_TOPLEFT: {
                            break;
                        }
                        case ALIGN_CENTER: {
                            cy = y + (totHeight - h) / 2;
                            break;
                        }
                        case ALIGN_BOTTOMRIGHT: {
                            cy = y + totHeight - h;
                        }
                    }
                    element.render(matrix, x, cy + this.layout.getTopPadding());
                    x += element.getWidth() + this.spacing;
                }
                break block14;
            }
            if (this.vertical == null) break block14;
            y += this.layout.getTopPadding();
            int totWidth = this.getWidth() - this.getXPadding();
            for (IElement element : this.children) {
                int w = element.getWidth();
                int cx = x;
                switch (this.vertical) {
                    case ALIGN_TOPLEFT: {
                        break;
                    }
                    case ALIGN_CENTER: {
                        cx = x + (totWidth - w) / 2;
                        break;
                    }
                    case ALIGN_BOTTOMRIGHT: {
                        cx = x + totWidth - w;
                    }
                }
                element.render(matrix, cx + this.layout.getLeftPadding(), y);
                y += element.getHeight() + this.spacing;
            }
        }
    }

    public int getHeight() {
        int h = 0;
        if (this.vertical != null) {
            for (IElement element : this.children) {
                h += element.getHeight();
            }
            return h + this.spacing * (this.children.size() - 1) + this.getBorderSpacing() + this.getYPadding();
        }
        for (IElement element : this.children) {
            h = Math.max(element.getHeight(), h);
        }
        return h + this.getBorderSpacing() + this.getYPadding();
    }

    public int getWidth() {
        int w = 0;
        if (this.horizontal != null) {
            for (IElement element : this.children) {
                w += element.getWidth();
            }
            return w + this.spacing * (this.children.size() - 1) + this.getBorderSpacing() + this.getXPadding();
        }
        for (IElement element : this.children) {
            w = Math.max(element.getWidth(), w);
        }
        return w + this.getBorderSpacing() + this.getXPadding();
    }

    protected int getYPadding() {
        return this.layout.getBottomPadding() + this.layout.getTopPadding();
    }

    protected int getXPadding() {
        return this.layout.getLeftPadding() + this.layout.getRightPadding();
    }

    protected int getBorderSpacing() {
        return this.borderColor == null ? 0 : 6;
    }

    public ResourceLocation getID() {
        return ELEMENT_ID;
    }

    @Override
    public IExpandedProbeInfo element(IElement element) {
        this.children.add(element);
        return this;
    }

    @Override
    public List<IElement> getChildren() {
        return this.children;
    }

    public List<IElement> getElements() {
        return this.getChildren();
    }

    public static enum Type {
        VERTICAL,
        HORIZONTAL;

    }
}

