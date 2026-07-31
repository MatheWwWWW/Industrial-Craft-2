/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.tool;

import ic2.core.GuiIC2;
import ic2.core.gui.Text;
import ic2.core.item.tool.ContainerToolbox;
import ic2.core.ref.ItemName;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiToolbox
extends GuiIC2<ContainerToolbox> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIToolbox.png");

    public GuiToolbox(ContainerToolbox container) {
        super(container);
        this.addElement(Text.create(this, 65, 11, ItemName.tool_box.getItemStack().func_82833_r(), 0, false));
    }

    @Override
    protected void drawBackgroundAndTitle(float partialTicks, int mouseX, int mouseY) {
        this.bindTexture();
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

