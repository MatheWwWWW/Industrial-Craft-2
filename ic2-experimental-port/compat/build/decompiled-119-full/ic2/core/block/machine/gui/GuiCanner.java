/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerCanner;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomButton;
import ic2.core.gui.CycleHandler;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.INumericValueHandler;
import ic2.core.gui.RecipeButton;
import ic2.core.gui.TankGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GuiCanner
extends Ic2Gui<ContainerCanner> {
    public static final ResourceLocation texture = IC2.getIdentifier("textures/gui/guicanner.png");

    public GuiCanner(ContainerCanner containerCanner, Inventory inventory, Component component) {
        super(containerCanner, inventory, component, 184);
        this.addElement(EnergyGauge.asBolt(this, 12, 62, (Ic2TileEntity)containerCanner.base));
        CycleHandler cycleHandler = new CycleHandler(176, 18, 226, 32, 14, true, 4, new INumericValueHandler(){

            @Override
            public int getValue() {
                return ((TileEntityCanner)((ContainerCanner)((GuiCanner)GuiCanner.this).f_97732_).base).getMode().ordinal();
            }

            @Override
            public void onChange(int n) {
                IC2.network.get(false).initiateClientTileEntityEvent((BlockEntity)((ContainerCanner)((GuiCanner)GuiCanner.this).f_97732_).base, 0 + n);
            }
        });
        this.addElement((GuiElement<?>)new CustomButton(this, 63, 81, 50, 14, cycleHandler, texture, cycleHandler).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                switch (((TileEntityCanner)((ContainerCanner)((GuiCanner)GuiCanner.this).f_97732_).base).getMode()) {
                    case BottleSolid: {
                        return "ic2.Canner.gui.switch.BottleSolid";
                    }
                    case EmptyLiquid: {
                        return "ic2.Canner.gui.switch.EmptyLiquid";
                    }
                    case BottleLiquid: {
                        return "ic2.Canner.gui.switch.BottleLiquid";
                    }
                    case EnrichLiquid: {
                        return "ic2.Canner.gui.switch.EnrichLiquid";
                    }
                }
                return null;
            }
        }));
        this.addElement((GuiElement<?>)new CustomButton(this, 77, 64, 22, 13, this.createEventSender(TileEntityCanner.eventSwapTanks)).withTooltip("ic2.Canner.gui.switchTanks"));
        this.addElement(TankGauge.createNormal(this, 39, 42, ((TileEntityCanner)containerCanner.base).getInputTank()));
        this.addElement(TankGauge.createNormal(this, 117, 42, ((TileEntityCanner)containerCanner.base).getOutputTank()));
        if (RecipeButton.canUse()) {
            for (final TileEntityCanner.Mode mode : TileEntityCanner.Mode.values) {
                this.addElement((GuiElement<?>)new RecipeButton(this, 74, 22, 23, 14, new String[]{"canner_" + mode}).withEnableHandler(new IEnableHandler(){

                    @Override
                    public boolean isEnabled() {
                        return ((TileEntityCanner)((ContainerCanner)((GuiCanner)GuiCanner.this).f_97732_).base).getMode() == mode;
                    }
                }));
            }
        }
    }

    @Override
    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        super.m_7286_(poseStack, f, n, n2);
        this.bindTexture();
        switch (((TileEntityCanner)((ContainerCanner)this.f_97732_).base).getMode()) {
            case BottleSolid: {
                this.drawTexturedRect(poseStack, 59.0, 53.0, 9.0, 18.0, 3.0, 4.0);
                this.drawTexturedRect(poseStack, 99.0, 53.0, 18.0, 23.0, 3.0, 4.0);
                break;
            }
            case EmptyLiquid: {
                this.drawTexturedRect(poseStack, 71.0, 43.0, 26.0, 18.0, 196.0, 0.0);
                this.drawTexturedRect(poseStack, 59.0, 53.0, 9.0, 18.0, 3.0, 4.0);
                break;
            }
            case BottleLiquid: {
                this.drawTexturedRect(poseStack, 99.0, 53.0, 18.0, 23.0, 3.0, 4.0);
                this.drawTexturedRect(poseStack, 71.0, 43.0, 26.0, 18.0, 196.0, 0.0);
                break;
            }
        }
        int n3 = Math.round(((TileEntityCanner)((ContainerCanner)this.f_97732_).base).getProgress() * 23.0f);
        if (n3 > 0) {
            this.drawTexturedRect(poseStack, 74.0, 22.0, n3, 14.0, 233.0, 0.0);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }
}

