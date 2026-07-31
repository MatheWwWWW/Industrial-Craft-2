/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.Slot
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.util.StackUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public class SlotGrid
extends GuiElement<SlotGrid> {
    private final SlotStyle style;
    private final int border;
    private final int spacing;

    public SlotGrid(Ic2Gui<?> ic2Gui, int n, int n2, SlotStyle slotStyle) {
        this(ic2Gui, n, n2, 1, 1, slotStyle);
    }

    public SlotGrid(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, SlotStyle slotStyle) {
        this(ic2Gui, n, n2, n3, n4, slotStyle, 0, 0);
    }

    public SlotGrid(Ic2Gui<?> ic2Gui, int n, int n2, SlotStyle slotStyle, int n3) {
        this(ic2Gui, n, n2, 1, 1, slotStyle, n3, 0);
    }

    public SlotGrid(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, SlotStyle slotStyle, int n5, int n6) {
        super(ic2Gui, n - n5, n2 - n5, n3 * slotStyle.width + 2 * n5 + (n3 - 1) * n6, n4 * slotStyle.height + 2 * n5 + (n4 - 1) * n6);
        this.style = slotStyle;
        this.border = n5;
        this.spacing = n6;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        super.drawBackground(poseStack, n, n2);
        if (this.style.background != null) {
            SlotGrid.bindTexture(this.style.background);
            int n3 = this.x + this.border;
            int n4 = this.y + this.border;
            int n5 = this.x + this.width - this.border;
            int n6 = this.y + this.height - this.border;
            int n7 = this.style.width + this.spacing;
            int n8 = this.style.height + this.spacing;
            for (int i = n4; i < n6; i += n8) {
                for (int j = n3; j < n5; j += n7) {
                    this.gui.drawTexturedRect(poseStack, j, i, this.style.width, this.style.height, this.style.u, this.style.v);
                }
            }
        }
    }

    @Override
    protected boolean suppressTooltip(int n, int n2) {
        if (!StackUtil.isEmpty(this.gui.getContainer().m_142621_())) {
            return false;
        }
        Slot slot = this.gui.getFocusedSlot();
        return slot != null && slot.m_6657_();
    }

    public static final class SlotStyle {
        public static final SlotStyle Normal = new SlotStyle(103, 7, 18, 18);
        public static final SlotStyle Large = new SlotStyle(99, 35, 26, 26);
        public static final SlotStyle Plain = new SlotStyle(16, 16);
        private static final Map<String, SlotStyle> map = SlotStyle.getMap();
        public static final int refSize = 16;
        public final int u;
        public final int v;
        public final int width;
        public final int height;
        public final ResourceLocation background;

        public SlotStyle(int n, int n2, int n3, int n4) {
            this(n, n2, n3, n4, GuiElement.commonTexture);
        }

        public SlotStyle(int n, int n2) {
            this(0, 0, n, n2, null);
        }

        public SlotStyle(int n, int n2, int n3, int n4, ResourceLocation resourceLocation) {
            this.u = n;
            this.v = n2;
            this.width = n3;
            this.height = n4;
            this.background = resourceLocation;
        }

        public static void registerVarient(String string, SlotStyle slotStyle) {
            assert (string != null && slotStyle != null);
            SlotStyle slotStyle2 = map.put(string.toLowerCase(Locale.ENGLISH), slotStyle);
            if (slotStyle2 != null) {
                throw new RuntimeException("Duplicate slot instance for name! " + string + " -> " + slotStyle2 + " and " + slotStyle);
            }
        }

        public static SlotStyle get(String string) {
            return map.get(string);
        }

        private static Map<String, SlotStyle> getMap() {
            HashMap<String, SlotStyle> hashMap = new HashMap<String, SlotStyle>(6, 0.5f);
            hashMap.put("normal", Normal);
            hashMap.put("large", Large);
            hashMap.put("plain", Plain);
            return hashMap;
        }
    }
}

