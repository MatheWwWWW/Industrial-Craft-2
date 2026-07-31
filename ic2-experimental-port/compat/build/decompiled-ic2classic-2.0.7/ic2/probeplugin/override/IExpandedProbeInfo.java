/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.CompoundText
 *  mcjty.theoneprobe.api.IElement
 *  mcjty.theoneprobe.api.IEntityStyle
 *  mcjty.theoneprobe.api.IIconStyle
 *  mcjty.theoneprobe.api.IItemStyle
 *  mcjty.theoneprobe.api.ILayoutStyle
 *  mcjty.theoneprobe.api.IProbeInfo
 *  mcjty.theoneprobe.api.IProgressStyle
 *  mcjty.theoneprobe.api.ITextStyle
 *  mcjty.theoneprobe.api.TankReference
 *  mcjty.theoneprobe.apiimpl.elements.ElementEntity
 *  mcjty.theoneprobe.apiimpl.elements.ElementIcon
 *  mcjty.theoneprobe.apiimpl.elements.ElementItemLabel
 *  mcjty.theoneprobe.apiimpl.elements.ElementItemStack
 *  mcjty.theoneprobe.apiimpl.elements.ElementPadding
 *  mcjty.theoneprobe.apiimpl.elements.ElementProgress
 *  mcjty.theoneprobe.apiimpl.elements.ElementTank
 *  mcjty.theoneprobe.apiimpl.elements.ElementText
 *  mcjty.theoneprobe.apiimpl.styles.EntityStyle
 *  mcjty.theoneprobe.apiimpl.styles.ItemStyle
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.probeplugin.override;

import ic2.probeplugin.override.components.Panel;
import ic2.probeplugin.override.styles.IIconStyleBuilder;
import ic2.probeplugin.override.styles.ILayoutStyleBuilder;
import ic2.probeplugin.override.styles.IProgressStyleBuilder;
import ic2.probeplugin.override.styles.ITextStyleBuilder;
import java.util.List;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IEntityStyle;
import mcjty.theoneprobe.api.IIconStyle;
import mcjty.theoneprobe.api.IItemStyle;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.ITextStyle;
import mcjty.theoneprobe.api.TankReference;
import mcjty.theoneprobe.apiimpl.elements.ElementEntity;
import mcjty.theoneprobe.apiimpl.elements.ElementIcon;
import mcjty.theoneprobe.apiimpl.elements.ElementItemLabel;
import mcjty.theoneprobe.apiimpl.elements.ElementItemStack;
import mcjty.theoneprobe.apiimpl.elements.ElementPadding;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.apiimpl.elements.ElementTank;
import mcjty.theoneprobe.apiimpl.elements.ElementText;
import mcjty.theoneprobe.apiimpl.styles.EntityStyle;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IExpandedProbeInfo
extends IProbeInfo {
    default public ILayoutStyle defaultLayoutStyle() {
        return ILayoutStyleBuilder.create();
    }

    default public IIconStyle defaultIconStyle() {
        return IIconStyleBuilder.create();
    }

    default public IProgressStyle defaultProgressStyle() {
        return IProgressStyleBuilder.create();
    }

    default public ITextStyle defaultTextStyle() {
        return ITextStyleBuilder.create();
    }

    default public IItemStyle defaultItemStyle() {
        return new ItemStyle();
    }

    default public IEntityStyle defaultEntityStyle() {
        return new EntityStyle();
    }

    default public IExpandedProbeInfo icon(ResourceLocation icon, int u, int v, int w, int h) {
        return this.icon(icon, u, v, w, h, this.defaultIconStyle());
    }

    default public IExpandedProbeInfo icon(ResourceLocation icon, int u, int v, int w, int h, IIconStyle style) {
        return this.element((IElement)new ElementIcon(icon, u, v, w, h, style));
    }

    default public IExpandedProbeInfo entity(String entity) {
        return this.entity(entity, this.defaultEntityStyle());
    }

    default public IExpandedProbeInfo entity(String entity, IEntityStyle style) {
        return this.element((IElement)new ElementEntity(entity, style));
    }

    default public IExpandedProbeInfo entity(Entity entity) {
        return this.entity(entity, this.defaultEntityStyle());
    }

    default public IExpandedProbeInfo entity(Entity entity, IEntityStyle style) {
        return this.element((IElement)new ElementEntity(entity, style));
    }

    default public IExpandedProbeInfo text(String text) {
        return this.text((Component)Component.m_237115_((String)text), this.defaultTextStyle());
    }

    default public IExpandedProbeInfo text(String text, Object ... args) {
        return this.text((Component)Component.m_237110_((String)text, (Object[])args), this.defaultTextStyle());
    }

    default public IExpandedProbeInfo text(Component text) {
        return this.text(text, this.defaultTextStyle());
    }

    default public IExpandedProbeInfo text(Component text, ITextStyle style) {
        return this.element((IElement)new ElementText(text, style));
    }

    default public IExpandedProbeInfo text(CompoundText text) {
        return this.element((IElement)new ElementText(text.get(), ITextStyleBuilder.create()).setLegacy());
    }

    default public IExpandedProbeInfo text(CompoundText text, ITextStyle style) {
        return this.element((IElement)new ElementText(text.get(), ITextStyleBuilder.create()).setLegacy());
    }

    default public IProbeInfo mcText(Component text) {
        return this.element((IElement)new ElementText(text));
    }

    default public IProbeInfo mcText(Component text, ITextStyle style) {
        return this.element((IElement)new ElementText(text, style));
    }

    default public IExpandedProbeInfo item(ItemStack stack) {
        return this.item(stack, this.defaultItemStyle());
    }

    default public IExpandedProbeInfo item(ItemStack stack, IItemStyle style) {
        return this.element((IElement)new ElementItemStack(stack, style));
    }

    default public IExpandedProbeInfo itemLabel(ItemStack stack) {
        return this.itemLabel(stack, this.defaultTextStyle());
    }

    default public IExpandedProbeInfo itemLabel(ItemStack stack, ITextStyle style) {
        return this.element((IElement)new ElementItemLabel(stack));
    }

    default public IExpandedProbeInfo progress(int current, int max) {
        return this.progress(current, max, this.defaultProgressStyle());
    }

    default public IExpandedProbeInfo progress(long current, long max) {
        return this.progress(current, max, this.defaultProgressStyle());
    }

    default public IExpandedProbeInfo progress(long current, long max, IProgressStyle style) {
        return this.progress(current, max, style);
    }

    default public IExpandedProbeInfo progress(int current, int max, IProgressStyle style) {
        return this.element((IElement)new ElementProgress((long)current, (long)max, style));
    }

    default public IExpandedProbeInfo tank(TankReference ref) {
        return this.tank(ref, IProgressStyleBuilder.create());
    }

    default public IExpandedProbeInfo tank(TankReference ref, IProgressStyle style) {
        return this.element((IElement)new ElementTank(ref, style));
    }

    default public IExpandedProbeInfo horizontal() {
        return this.horizontal(this.defaultLayoutStyle());
    }

    default public IExpandedProbeInfo horizontal(ILayoutStyle style) {
        Panel panel = new Panel(style, false);
        this.element(panel);
        return panel;
    }

    default public IExpandedProbeInfo vertical() {
        return this.vertical(this.defaultLayoutStyle());
    }

    default public IExpandedProbeInfo vertical(ILayoutStyle style) {
        Panel panel = new Panel(style, true);
        this.element(panel);
        return panel;
    }

    default public IExpandedProbeInfo padding(int xPadding, int yPadding) {
        return this.element((IElement)new ElementPadding(xPadding, yPadding));
    }

    public List<IElement> getChildren();

    public IExpandedProbeInfo element(IElement var1);
}

