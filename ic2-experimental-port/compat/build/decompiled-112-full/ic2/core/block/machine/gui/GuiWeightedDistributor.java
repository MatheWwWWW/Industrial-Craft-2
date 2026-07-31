/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import ic2.core.ContainerBase;
import ic2.core.GuiIC2;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.StickyVanillaButton;
import ic2.core.gui.Text;
import ic2.core.gui.dynamic.TextProvider;
import java.util.List;
import java.util.Locale;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public abstract class GuiWeightedDistributor<T extends ContainerBase<? extends IWeightedDistributor>>
extends GuiIC2<T> {
    protected final StickyVanillaButton[][] buttons = new StickyVanillaButton[5][6];

    public GuiWeightedDistributor(T container, int height) {
        super(container, height);
        for (int y = 0; y < 5; ++y) {
            TextProvider.ITextProvider text;
            final int row = y;
            for (int col = 0; col < 6; ++col) {
                final EnumFacing facing = EnumFacing.func_82600_a((int)GuiWeightedDistributor.facingOffset(col));
                StickyVanillaButton stickyVanillaButton = new StickyVanillaButton(this, 63 + col * 18, 17 + y * 18, 16, 16, new IClickHandler(){

                    private void rebalance(int change) {
                        block0: for (int i = change + 1; i < GuiWeightedDistributor.this.buttons.length; ++i) {
                            for (int side = 0; side < 6; ++side) {
                                StickyVanillaButton button = GuiWeightedDistributor.this.buttons[i][side];
                                if (!button.isOn()) continue;
                                GuiWeightedDistributor.this.buttons[i - 1][side].setOn(true);
                                button.setOn(false);
                                continue block0;
                            }
                        }
                    }

                    private int findNextEmptyRow(int start) {
                        while (start-- > 0) {
                            for (StickyVanillaButton button : GuiWeightedDistributor.this.buttons[start]) {
                                if (!button.isOn()) continue;
                                return start + 1;
                            }
                        }
                        return 0;
                    }

                    @Override
                    public void onClick(MouseButton mouse) {
                        boolean switchingOff = false;
                        int aim = GuiWeightedDistributor.buttonOffset(facing.func_176745_a());
                        for (int i = 0; i < GuiWeightedDistributor.this.buttons.length; ++i) {
                            if (!GuiWeightedDistributor.this.buttons[i][aim].isOn()) continue;
                            GuiWeightedDistributor.this.buttons[i][aim].setOn(false);
                            switchingOff = i == row;
                            this.rebalance(i);
                            break;
                        }
                        if (!switchingOff) {
                            StickyVanillaButton[] switches = GuiWeightedDistributor.this.buttons[this.findNextEmptyRow(row)];
                            int aim2 = GuiWeightedDistributor.buttonOffset(facing.func_176745_a());
                            for (int i = 0; i < switches.length; ++i) {
                                switches[i].setOn(i == aim2);
                            }
                        }
                        List<EnumFacing> priorities = ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).getPriority();
                        priorities.clear();
                        block2: for (StickyVanillaButton[] switches : GuiWeightedDistributor.this.buttons) {
                            for (int i = 0; i < switches.length; ++i) {
                                if (!switches[i].isOn()) continue;
                                priorities.add(EnumFacing.func_82600_a((int)GuiWeightedDistributor.facingOffset(i)));
                                continue block2;
                            }
                        }
                        ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).updatePriority(false);
                    }
                }).withDisableHandler(new IEnableHandler(){

                    @Override
                    public boolean isEnabled() {
                        return ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).getFacing() != facing;
                    }
                }).withText(facing.func_176610_l().substring(0, 1).toUpperCase(Locale.ENGLISH)).withTooltip(GuiWeightedDistributor.getNameForFacing(facing));
                this.buttons[y][col] = stickyVanillaButton;
                this.addElement(stickyVanillaButton);
            }
            switch (y) {
                case 0: {
                    text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.highest");
                    break;
                }
                case 1: {
                    text = TextProvider.of("\u2191");
                    break;
                }
                case 2: {
                    text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.priority");
                    break;
                }
                case 3: {
                    text = TextProvider.of("\u2193");
                    break;
                }
                case 4: {
                    text = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.lowest");
                    break;
                }
                default: {
                    throw new IllegalStateException("Ended up being on y=" + y);
                }
            }
            this.addElement(Text.create(this, 8, 21 + y * 18, text, 0x404040, false));
        }
        int end = 0;
        for (EnumFacing side : ((IWeightedDistributor)((ContainerBase)((Object)container)).base).getPriority()) {
            this.buttons[end++][GuiWeightedDistributor.buttonOffset(side.func_176745_a())].setOn(true);
        }
    }

    static int facingOffset(int facing) {
        return (facing + 1) % 6;
    }

    static int buttonOffset(int facing) {
        return (facing + 5) % 6;
    }

    private static String getNameForFacing(EnumFacing facing) {
        switch (facing) {
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
        throw new IllegalStateException("Unexpected direction: " + facing);
    }
}

