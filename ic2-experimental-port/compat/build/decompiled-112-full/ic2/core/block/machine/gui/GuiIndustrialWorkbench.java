/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Supplier
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.gui.Area;
import ic2.core.gui.CustomButton;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.Image;
import ic2.core.gui.MouseButton;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiIndustrialWorkbench
extends GuiIC2<ContainerIndustrialWorkbench> {
    public static Predicate<GuiScreen> jeiScreenRecipesGuiCheck;
    private static final ResourceLocation TEXTURE;

    public GuiIndustrialWorkbench(ContainerIndustrialWorkbench container) {
        super(container, 194, 228);
        this.addElement((GuiElement<?>)new Area(this, 173, 3, 18, 108){

            @Override
            protected boolean suppressTooltip(int mouseX, int mouseY) {
                for (GuiElement element : GuiIndustrialWorkbench.this.elements) {
                    if (!element.isEnabled() || element == this || !element.contains(mouseX, mouseY)) continue;
                    return true;
                }
                return false;
            }
        }.withTooltip("ic2.IndustrialWorkbench.gui.adjacent"));
        for (final EnumFacing side : EnumFacing.field_82609_l) {
            this.addElement((GuiElement<?>)((CustomButton)((CustomButton)new CustomButton(this, 173, 3 + (side.func_176745_a() + 5) % 6 * 18, 18, 18, new IClickHandler(){
                private boolean firstOpen = true;
                private boolean jei = false;

                @Override
                public void onClick(MouseButton button) {
                    TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).base;
                    assert (base.func_145830_o());
                    TileEntity neighbour = base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side));
                    assert (neighbour instanceof IHasGui);
                    if (!(neighbour instanceof IPersonalBlock) || ((IPersonalBlock)neighbour).permitsAccess(((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).player.func_146103_bH())) {
                        IC2.network.get(false).requestGUI((IHasGui)neighbour);
                        MinecraftForge.EVENT_BUS.register((Object)this);
                    } else {
                        IC2.platform.messagePlayer(((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).player, "Owned by " + ((IPersonalBlock)neighbour).getOwner().getName(), new Object[0]);
                    }
                }

                @SubscribeEvent
                public void waitForClose(GuiOpenEvent event) {
                    if (this.keepOpen(event.getGui())) {
                        return;
                    }
                    if (!this.firstOpen) {
                        IC2.network.get(false).requestGUI((IHasGui)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).base);
                        event.setGui((GuiScreen)GuiIndustrialWorkbench.this);
                        MinecraftForge.EVENT_BUS.unregister((Object)this);
                    } else {
                        this.firstOpen = false;
                    }
                }

                private boolean keepOpen(GuiScreen screen) {
                    if (jeiScreenRecipesGuiCheck == null) {
                        return false;
                    }
                    if (jeiScreenRecipesGuiCheck.apply((Object)screen)) {
                        this.jei = true;
                        return true;
                    }
                    if (this.jei) {
                        this.jei = false;
                        return true;
                    }
                    return false;
                }
            }).withEnableHandler(new IEnableHandler(){

                @Override
                public boolean isEnabled() {
                    TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).base;
                    return base.func_145830_o() && base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side)) instanceof IHasGui;
                }
            })).withIcon(new Supplier<ItemStack>(){

                public ItemStack get() {
                    TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).base;
                    assert (base.func_145830_o());
                    BlockPos pos = base.func_174877_v().func_177972_a(side);
                    IBlockState state = base.func_145831_w().func_180495_p(pos);
                    return state.func_177230_c().getPickBlock(state, null, base.func_145831_w(), pos, ((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).player);
                }
            })).withTooltip(new Supplier<String>(){

                private String getSideName() {
                    switch (side) {
                        case WEST: {
                            return "ic2.dir.West";
                        }
                        case EAST: {
                            return "ic2.dir.East";
                        }
                        case DOWN: {
                            return "ic2.dir.Bottom";
                        }
                        case UP: {
                            return "ic2.dir.Top";
                        }
                        case NORTH: {
                            return "ic2.dir.North";
                        }
                        case SOUTH: {
                            return "ic2.dir.South";
                        }
                    }
                    throw new IllegalStateException("Unexpected direction: " + side);
                }

                public String get() {
                    TileEntityIndustrialWorkbench base = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).container).base;
                    assert (base.func_145830_o());
                    TileEntity neighbour = base.func_145831_w().func_175625_s(base.func_174877_v().func_177972_a(side));
                    assert (neighbour instanceof IHasGui);
                    return Localization.translate(((IHasGui)neighbour).func_70005_c_()) + '\n' + TextFormatting.DARK_GRAY + Localization.translate(this.getSideName());
                }
            }));
        }
        int cancelX = 93;
        int cancelY = 42;
        this.addElement((GuiElement<?>)new VanillaButton(this, 93, 42, 16, 16, new IClickHandler(){

            @Override
            public void onClick(MouseButton button) {
                IC2.network.get(false).sendContainerEvent(GuiIndustrialWorkbench.this.container, "clear");
            }
        }).withTooltip("Clear"));
        this.addElement(Image.create(this, 94, 43, 14, 14, GuiElement.commonTexture, 256, 256, 210, 47, 224, 61));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIIndustrialWorkbench.png");
    }
}

