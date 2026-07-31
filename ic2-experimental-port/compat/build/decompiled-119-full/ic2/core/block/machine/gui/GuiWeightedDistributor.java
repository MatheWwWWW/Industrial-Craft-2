/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.ContainerBase;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.StickyVanillaButton;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class GuiWeightedDistributor<T extends ContainerBase<? extends IWeightedDistributor>>
extends Ic2Gui<T> {
    protected final StickyVanillaButton[][] buttons = new StickyVanillaButton[5][6];

    /*
     * WARNING - void declaration
     */
    public GuiWeightedDistributor(T t, Inventory inventory, Component component, int n) {
        super(t, inventory, component, n);
        int n2;
        for (n2 = 0; n2 < 5; ++n2) {
            void iTextProvider;
            void var7_10;
            final int n3 = n2;
            boolean i = false;
            while (var7_10 < 6) {
                final Direction direction = Direction.m_122376_((int)GuiWeightedDistributor.facingOffset((int)var7_10));
                StickyVanillaButton stickyVanillaButton = new StickyVanillaButton(this, 63 + var7_10 * 18, 17 + n2 * 18, 16, 16, new IClickHandler(){

                    private void rebalance(int n) {
                        block0: for (int i = n + 1; i < GuiWeightedDistributor.this.buttons.length; ++i) {
                            for (int j = 0; j < 6; ++j) {
                                StickyVanillaButton stickyVanillaButton = GuiWeightedDistributor.this.buttons[i][j];
                                if (!stickyVanillaButton.isOn()) continue;
                                GuiWeightedDistributor.this.buttons[i - 1][j].setOn(true);
                                stickyVanillaButton.setOn(false);
                                continue block0;
                            }
                        }
                    }

                    private int findNextEmptyRow(int n) {
                        while (n-- > 0) {
                            for (StickyVanillaButton stickyVanillaButton : GuiWeightedDistributor.this.buttons[n]) {
                                if (!stickyVanillaButton.isOn()) continue;
                                return n + 1;
                            }
                        }
                        return 0;
                    }

                    @Override
                    public void onClick(MouseButton mouseButton) {
                        boolean bl = false;
                        int n = GuiWeightedDistributor.buttonOffset(direction.m_122411_());
                        for (int i = 0; i < GuiWeightedDistributor.this.buttons.length; ++i) {
                            if (!GuiWeightedDistributor.this.buttons[i][n].isOn()) continue;
                            GuiWeightedDistributor.this.buttons[i][n].setOn(false);
                            bl = i == n3;
                            this.rebalance(i);
                            break;
                        }
                        if (!bl) {
                            StickyVanillaButton[] stickyVanillaButtonArray = GuiWeightedDistributor.this.buttons[this.findNextEmptyRow(n3)];
                            int n2 = GuiWeightedDistributor.buttonOffset(direction.m_122411_());
                            for (n = 0; n < stickyVanillaButtonArray.length; ++n) {
                                stickyVanillaButtonArray[n].setOn(n == n2);
                            }
                        }
                        List<Direction> list = ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).getPriority();
                        list.clear();
                        block2: for (StickyVanillaButton[] stickyVanillaButtonArray : GuiWeightedDistributor.this.buttons) {
                            for (int i = 0; i < stickyVanillaButtonArray.length; ++i) {
                                if (!stickyVanillaButtonArray[i].isOn()) continue;
                                list.add(Direction.m_122376_((int)GuiWeightedDistributor.facingOffset(i)));
                                continue block2;
                            }
                        }
                        ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).updatePriority(false);
                    }
                }).withDisableHandler(new IEnableHandler(){

                    @Override
                    public boolean isEnabled() {
                        return ((IWeightedDistributor)((ContainerBase)((Object)GuiWeightedDistributor.this.getContainer())).base).getFacing() != direction;
                    }
                }).withText(direction.m_7912_().substring(0, 1).toUpperCase(Locale.ENGLISH)).withTooltip(GuiWeightedDistributor.getNameForFacing(direction));
                this.buttons[n2][var7_10] = stickyVanillaButton;
                this.addElement(stickyVanillaButton);
                ++var7_10;
            }
            switch (n2) {
                case 0: {
                    TextProvider.ITextProvider iTextProvider2 = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.highest");
                    break;
                }
                case 1: {
                    TextProvider.ITextProvider iTextProvider3 = TextProvider.of("\u2191");
                    break;
                }
                case 2: {
                    TextProvider.ITextProvider iTextProvider4 = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.priority");
                    break;
                }
                case 3: {
                    TextProvider.ITextProvider iTextProvider5 = TextProvider.of("\u2193");
                    break;
                }
                case 4: {
                    TextProvider.ITextProvider iTextProvider6 = TextProvider.ofTranslated("ic2.WeightedDistributor.gui.lowest");
                    break;
                }
                default: {
                    throw new IllegalStateException("Ended up being on y=" + n2);
                }
            }
            this.addElement(TextLabel.create(this, 8, 21 + n2 * 18, (TextProvider.ITextProvider)iTextProvider, 0x404040, false));
        }
        n2 = 0;
        for (Direction direction : ((IWeightedDistributor)((ContainerBase)((Object)t)).base).getPriority()) {
            this.buttons[n2++][GuiWeightedDistributor.buttonOffset(direction.m_122411_())].setOn(true);
        }
    }

    static int facingOffset(int n) {
        return (n + 1) % 6;
    }

    static int buttonOffset(int n) {
        return (n + 5) % 6;
    }

    private static String getNameForFacing(Direction direction) {
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
}

