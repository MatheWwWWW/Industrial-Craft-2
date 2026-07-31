/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.client.model.VillagerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.entity.VillagerRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.npc.VillagerData
 *  net.minecraft.world.entity.npc.VillagerProfession
 *  net.minecraft.world.entity.npc.VillagerType
 *  net.minecraft.world.level.entity.LevelEntityGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.components.hv.villager;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.logic.villager.IVillagerTarget;
import ic2.core.block.machines.logic.villager.VillagerTradeList;
import ic2.core.block.machines.tiles.hv.VillagerOMatTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.platform.corehacks.mixins.server.LevelMixin;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.IterableWrapper;
import ic2.core.utils.math.geometry.Box2i;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class VillagerSelectorComponent
extends GuiWidget {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/hv/gui_villager_selector.png");
    private static final Box2i HIT_BOX = new Box2i(-112, 112, 16, 26);
    SliderComponent sliders = this.addChild(new SliderComponent(new Box2i(-19, 112, 12, 82), new Box2i(0, 201, 12, 15), 5).setCustomTexture(TEXTURE));
    VillagerOMatTileEntity tile;
    Object2IntMap<UUID> xpPoints = new Object2IntOpenHashMap();
    List<VillagerEntry> entries = CollectionUtils.createList();
    Villager villager;
    VillagerEntry selected = null;
    VillagerEntry hovered = null;
    Consumer<VillagerEntry> result;

    public VillagerSelectorComponent(VillagerOMatTileEntity tile, Consumer<VillagerEntry> result) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.result = result;
        this.villager = new Villager(EntityType.f_20492_, tile.m_58904_());
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    public void reloadVillagerData() {
        this.xpPoints.clear();
        LevelEntityGetter<Entity> getter = ((LevelMixin)this.gui.getMinecraft().f_91073_).getEntityAccessor();
        Object2ObjectSortedMap<VillagerProfession, List> villagers = CollectionUtils.createLinkedMap();
        for (VillagerTradeList villagerTradeList : this.tile.trades.getVisibleVillagerTrades()) {
            Entity entity = (Entity)getter.m_142694_(villagerTradeList.getOwner());
            if (!(entity instanceof Villager)) continue;
            Villager villager = (Villager)entity;
            villagers.computeIfAbsent(villager.m_7141_().m_35571_(), this::map).add(villager);
            this.xpPoints.put((Object)villagerTradeList.getOwner(), villagerTradeList.getXp());
        }
        this.entries.clear();
        if (villagers.size() > 0) {
            this.entries.add(new VillagerEntry(IVillagerTarget.any(), null, VillagerProfession.f_35585_));
            for (Map.Entry entry : villagers.entrySet()) {
                this.entries.add(new VillagerEntry(IVillagerTarget.group((VillagerProfession)entry.getKey()), null, (VillagerProfession)entry.getKey()));
                for (Villager villager : (List)entry.getValue()) {
                    this.entries.add(new VillagerEntry(IVillagerTarget.simple(villager), villager, villager.m_7141_().m_35571_()));
                }
            }
            if (this.selected == null) {
                this.setSelcted(this.entries.get(0));
            } else {
                this.setSelcted(this.selected);
            }
        }
        this.sliders.setMax(this.entries.size());
    }

    private List<Villager> map(VillagerProfession profession) {
        return CollectionUtils.createList();
    }

    private void setSelcted(VillagerEntry entry) {
        this.selected = entry;
        if (this.result != null) {
            this.result.accept(entry);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        this.reloadVillagerData();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft() - 118;
        int y = this.gui.getGuiTop();
        this.gui.bindTexture(TEXTURE);
        this.gui.drawTextureRegion(matrix, x, y, 0.0f, 0.0f, 118.0f, 201.0f);
        this.toggleHeadMode(true);
        boolean found = false;
        for (int i = 0; i < 15 && this.sliders.getCurrent() + i < this.entries.size(); ++i) {
            VillagerEntry entry = this.entries.get(this.sliders.getCurrent() + i);
            this.villager.m_34375_(this.getData(entry));
            int xOff = i % 5 * 18;
            int yOff = i / 5 * 28;
            this.renderVillager(HIT_BOX.getX() + xOff, HIT_BOX.getY() + 48 + yOff, true, matrix, entry);
            if (!HIT_BOX.isInBox(mouseX - xOff, mouseY - yOff)) continue;
            this.hovered = entry;
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + HIT_BOX.getX() + xOff, this.gui.getGuiTop() + HIT_BOX.getY() + yOff, HIT_BOX.getWidth(), HIT_BOX.getHeight(), -2130706433);
            RenderSystem.m_69461_();
            found = true;
        }
        if (!found) {
            this.hovered = null;
        }
        this.toggleHeadMode(false);
        this.villager.m_34375_(this.getData(this.villager.m_7141_()));
        this.renderVillager(-60, 103, false, matrix, this.hovered != null ? this.hovered : (this.selected != null ? this.selected : null));
    }

    private VillagerData getData(VillagerEntry entry) {
        return new VillagerData(this.getType(entry), entry.profession(), this.getLevel(entry));
    }

    private VillagerData getData(VillagerData input) {
        if (this.hovered != null) {
            return new VillagerData(this.getType(this.hovered), this.hovered.profession(), this.getLevel(this.hovered));
        }
        if (this.selected != null) {
            return new VillagerData(this.getType(this.selected), this.selected.profession(), this.getLevel(this.selected));
        }
        return new VillagerData(VillagerType.f_35821_, VillagerProfession.f_35585_, 1);
    }

    private int getLevel(VillagerEntry entry) {
        return entry.villager() == null ? 1 : entry.villager().m_7141_().m_35576_();
    }

    private VillagerType getType(VillagerEntry entry) {
        return entry.villager() == null ? VillagerType.f_35821_ : entry.villager().m_7141_().m_35560_();
    }

    private void toggleHeadMode(boolean enable) {
        VillagerRenderer entity = (VillagerRenderer)Minecraft.m_91087_().m_91290_().m_114382_((Entity)this.villager);
        ModelPart root = ((VillagerModel)entity.m_7200_()).m_142109_();
        for (ModelPart part : IterableWrapper.wrap(root.m_171331_())) {
            part.f_104207_ = !enable;
        }
        if (enable) {
            root.f_104207_ = true;
            for (ModelPart part : IterableWrapper.wrap(((VillagerModel)entity.m_7200_()).m_5585_().m_171331_())) {
                part.f_104207_ = true;
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void renderVillager(int x, int y, boolean scale, PoseStack stack, VillagerEntry entry) {
        int i = this.gui.getGuiLeft();
        int j = this.gui.getGuiTop();
        PoseStack matrix = RenderSystem.m_157191_();
        matrix.m_85836_();
        matrix.m_85837_((double)(i + x + (scale ? 8 : 0)), (double)(j + y), 0.0);
        if (scale) {
            matrix.m_85841_(0.75f, 0.75f, 1.0f);
        }
        InventoryScreen.m_98850_((int)0, (int)0, (int)30, (float)0.0f, (float)0.0f, (LivingEntity)this.villager);
        matrix.m_85849_();
        RenderSystem.m_157182_();
        if (entry != null && entry.villager() != null) {
            this.gui.bindTexture(TEXTURE);
            float progress = Mth.m_14036_((float)((float)this.xpPoints.getInt((Object)entry.villager().m_20148_()) / (float)VillagerData.m_35577_((int)entry.villager().m_7141_().m_35576_())), (float)0.0f, (float)1.0f);
            if (scale) {
                this.gui.drawTextureRegion(stack, i + x, j + y - 28, 0.0f, 216.0f, 16.0f, 5.0f);
                this.gui.drawTextureRegion(stack, i + x, j + y - 28, 0.0f, 221.0f, progress * 16.0f, 5.0f);
            } else {
                this.gui.drawTextureRegion(stack, i + x - 28, j + y - 72, 0.0f, 226.0f, 56.0f, 5.0f);
                this.gui.drawTextureRegion(stack, i + x - 28, j + y - 72, 0.0f, 231.0f, progress * 56.0f, 5.0f);
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (this.hovered != null) {
            Component comp = this.hovered.target().isGroup() ? this.hovered.target().getGroupName() : (this.hovered.villager() == null ? this.getTypeName(this.villager.m_7141_().m_35571_()) : this.hovered.villager().m_7755_());
            this.gui.drawCenterString(matrix, comp, -60, 20, -1);
        } else if (this.selected != null) {
            Component comp = this.selected.target().isGroup() ? this.selected.target().getGroupName() : (this.selected.villager() == null ? this.getTypeName(this.villager.m_7141_().m_35571_()) : this.selected.villager().m_7755_());
            this.gui.drawCenterString(matrix, comp, -60, 20, -1);
        } else {
            this.gui.drawCenterString(matrix, this.getTypeName(this.villager.m_7141_().m_35571_()), -60, 20, -1);
        }
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.villager.selector"), -60, 6, 0x404040);
    }

    protected Component getTypeName(VillagerProfession profession) {
        ResourceLocation profName = ForgeRegistries.VILLAGER_PROFESSIONS.getKey((Object)profession);
        return Component.m_237115_((String)("entity.minecraft.villager." + (String)(!"minecraft".equals(profName.m_135827_()) ? profName.m_135827_() + "." : "") + profName.m_135815_()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        for (int i = 0; i < 15 && this.sliders.getCurrent() + i < this.entries.size(); ++i) {
            int xOff = i % 5 * 18;
            int yOff = i / 5 * 28;
            if (!HIT_BOX.isInBox(mouseX - xOff, mouseY - yOff)) continue;
            VillagerEntry entry = this.entries.get(this.sliders.getCurrent() + i);
            if (entry.target().isGroup()) {
                tooltips.accept(entry.target().getGroupName());
                continue;
            }
            if (entry.villager() == null) continue;
            tooltips.accept(entry.villager().m_7755_());
            tooltips.accept((Component)Component.m_237113_((String)" "));
            tooltips.accept((Component)this.translate("gui.ic2.villager.level", entry.villager().m_7141_().m_35576_()));
            if (entry.villager().m_7141_().m_35576_() < 5) {
                tooltips.accept((Component)this.translate("gui.ic2.villager.level.xp", this.xpPoints.getInt((Object)entry.villager().m_20148_()), VillagerData.m_35577_((int)entry.villager().m_7141_().m_35576_())));
            }
            tooltips.accept((Component)this.translate("gui.ic2.villager.highlight"));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < 15 && this.sliders.getCurrent() + i < this.entries.size(); ++i) {
            int xOff = i % 5 * 18;
            int yOff = i / 5 * 28;
            if (!HIT_BOX.isInBox(mouseX - xOff, mouseY - yOff)) continue;
            if (Screen.m_96638_()) {
                Villager villager = this.entries.get((int)(this.sliders.getCurrent() + i)).villager;
                if (villager != null) {
                    this.tile.sendToServer(3, villager.m_19879_());
                }
            } else {
                this.setSelcted(this.entries.get(this.sliders.getCurrent() + i));
            }
            return true;
        }
        return false;
    }

    public record VillagerEntry(IVillagerTarget target, Villager villager, VillagerProfession profession) {
    }
}

