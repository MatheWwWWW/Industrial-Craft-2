/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.gui;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Gui;
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
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GuiIndustrialWorkbench
extends Ic2Gui<ContainerIndustrialWorkbench> {
    public static Predicate<Screen> jeiScreenRecipesGuiCheck;
    private static final ResourceLocation TEXTURE;

    public GuiIndustrialWorkbench(ContainerIndustrialWorkbench containerIndustrialWorkbench, Inventory inventory, Component component) {
        super(containerIndustrialWorkbench, inventory, component, 194, 228);
        this.addElement((GuiElement<?>)new Area(this, 173, 3, 18, 108){

            @Override
            protected boolean suppressTooltip(int n, int n2) {
                for (GuiElement guiElement : GuiIndustrialWorkbench.this.elements) {
                    if (!guiElement.isEnabled() || guiElement == this || !guiElement.contains(n, n2)) continue;
                    return true;
                }
                return false;
            }
        }.withTooltip("ic2.IndustrialWorkbench.gui.adjacent"));
        for (final Direction direction : Util.ALL_DIRS) {
            this.addElement((GuiElement<?>)((CustomButton)((CustomButton)new CustomButton(this, 173, 3 + (direction.m_122411_() + 5) % 6 * 18, 18, 18, new IClickHandler(){
                private boolean firstOpen = true;
                private boolean jei = false;

                @Override
                public void onClick(MouseButton mouseButton) {
                    TileEntityIndustrialWorkbench tileEntityIndustrialWorkbench = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).base;
                    assert (tileEntityIndustrialWorkbench.m_58898_());
                    BlockEntity blockEntity = tileEntityIndustrialWorkbench.m_58904_().m_7702_(tileEntityIndustrialWorkbench.m_58899_().m_121945_(direction));
                    assert (blockEntity instanceof IHasGui);
                    if (!(blockEntity instanceof IPersonalBlock) || ((IPersonalBlock)blockEntity).permitsAccess(((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).player.m_36316_())) {
                        closeHandler = this::onScreenClose;
                        IC2.network.get(false).requestGUI((IHasGui)blockEntity);
                    } else {
                        IC2.sideProxy.messagePlayer(((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).player, "Owned by " + ((IPersonalBlock)blockEntity).getOwner().getName(), new Object[0]);
                    }
                }

                private void onScreenClose() {
                    if (this.keepOpen(SideProxyClient.mc.f_91080_)) {
                        return;
                    }
                    if (!this.firstOpen) {
                        closeHandler = null;
                        IC2.network.get(false).requestGUI((IHasGui)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).base);
                    } else {
                        this.firstOpen = false;
                    }
                }

                private boolean keepOpen(Screen screen) {
                    if (jeiScreenRecipesGuiCheck == null) {
                        return false;
                    }
                    if (jeiScreenRecipesGuiCheck.test(screen)) {
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
                    TileEntityIndustrialWorkbench tileEntityIndustrialWorkbench = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).base;
                    return tileEntityIndustrialWorkbench.m_58898_() && tileEntityIndustrialWorkbench.m_58904_().m_7702_(tileEntityIndustrialWorkbench.m_58899_().m_121945_(direction)) instanceof IHasGui;
                }
            })).withIcon(new Supplier<ItemStack>(){

                @Override
                public ItemStack get() {
                    TileEntityIndustrialWorkbench tileEntityIndustrialWorkbench = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).base;
                    assert (tileEntityIndustrialWorkbench.m_58898_());
                    BlockPos blockPos = tileEntityIndustrialWorkbench.m_58899_().m_121945_(direction);
                    BlockState blockState = tileEntityIndustrialWorkbench.m_58904_().m_8055_(blockPos);
                    return StackUtil.getPickStack(tileEntityIndustrialWorkbench.m_58904_(), blockPos, blockState, ((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).player);
                }
            })).withTooltip(new Supplier<String>(){

                private String getSideName() {
                    switch (direction) {
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
                    throw new IllegalStateException("Unexpected direction: " + direction);
                }

                @Override
                public String get() {
                    TileEntityIndustrialWorkbench tileEntityIndustrialWorkbench = (TileEntityIndustrialWorkbench)((ContainerIndustrialWorkbench)((GuiIndustrialWorkbench)GuiIndustrialWorkbench.this).f_97732_).base;
                    assert (tileEntityIndustrialWorkbench.m_58898_());
                    BlockEntity blockEntity = tileEntityIndustrialWorkbench.m_58904_().m_7702_(tileEntityIndustrialWorkbench.m_58899_().m_121945_(direction));
                    assert (blockEntity instanceof IHasGui);
                    return IHasGui.getBeName(blockEntity).getString() + "\n" + ChatFormatting.DARK_GRAY + Localization.translate(this.getSideName());
                }
            }));
        }
        this.addElement((GuiElement<?>)new VanillaButton(this, 93, 42, 16, 16, new IClickHandler(){

            @Override
            public void onClick(MouseButton mouseButton) {
                IC2.network.get(false).sendContainerEvent((ContainerBase)GuiIndustrialWorkbench.this.f_97732_, "clear");
            }
        }).withTooltip("Clear"));
        this.addElement(Image.create(this, 94, 43, 14, 14, GuiElement.commonTexture, 256, 256, 210, 47, 224, 61));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        TEXTURE = new ResourceLocation("ic2", "textures/gui/guiindustrialworkbench.png");
    }
}

