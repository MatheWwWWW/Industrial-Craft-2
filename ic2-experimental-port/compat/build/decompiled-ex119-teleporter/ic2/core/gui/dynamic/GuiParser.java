/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui.dynamic;

import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.IGuiConditionProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Tuple;
import ic2.core.util.XmlUtil;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class GuiParser {
    public static GuiNode parse(ResourceLocation resourceLocation, Class<?> clazz) {
        String string = String.format("/assets/%s/guidef/%s.xml", resourceLocation.m_135827_(), resourceLocation.m_135815_());
        InputStream inputStream = null;
        try {
            inputStream = GuiParser.class.getResourceAsStream(string);
            if (inputStream == null) {
                throw new FileNotFoundException("Could not load " + string + " from the classpath.");
            }
            inputStream = new BufferedInputStream(inputStream);
            GuiNode guiNode = GuiParser.parse(inputStream, clazz);
            return guiNode;
        }
        catch (Exception exception) {
            throw new RuntimeException("Error reading/parsing GUI definition " + resourceLocation + " from " + string, exception);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            catch (IOException iOException) {}
        }
    }

    private static GuiNode parse(InputStream inputStream, Class<?> clazz) throws SAXException, IOException {
        inputStream = new BufferedInputStream(inputStream);
        SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser sAXParser = sAXParserFactory.newSAXParser();
            XMLReader xMLReader = sAXParser.getXMLReader();
            SaxHandler saxHandler = new SaxHandler(clazz);
            xMLReader.setContentHandler(saxHandler);
            xMLReader.parse(new InputSource(inputStream));
            return saxHandler.getResult();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            throw new RuntimeException(parserConfigurationException);
        }
    }

    public static class GuiNode
    extends ParentNode {
        private final Class<?> baseClass;
        final int width;
        final int height;

        GuiNode(Attributes attributes, Class<?> clazz) throws SAXException {
            super(null);
            this.baseClass = clazz;
            this.width = XmlUtil.getIntAttr(attributes, "width");
            this.height = XmlUtil.getIntAttr(attributes, "height");
        }

        @Override
        public NodeType getType() {
            return NodeType.gui;
        }

        @Override
        public Class<?> getBaseClass() {
            return this.baseClass;
        }
    }

    private static class SaxHandler
    extends DefaultHandler {
        private final Class<?> baseClass;
        private ParentNode parentNode;
        private Node currentNode;

        public SaxHandler(Class<?> clazz) {
            this.baseClass = clazz;
        }

        @Override
        public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
            NodeType nodeType = NodeType.get(string3);
            if (nodeType == null) {
                nodeType = NodeType.get(string3.toLowerCase(Locale.ENGLISH));
            }
            if (nodeType == null) {
                throw new SAXException("invalid element: " + string3);
            }
            if (nodeType == NodeType.gui) {
                if (this.parentNode != null) {
                    throw new SAXException("invalid gui element location");
                }
            } else if (this.parentNode == null) {
                throw new SAXException("invalid " + string3 + " element location");
            }
            switch (nodeType) {
                case gui: {
                    this.parentNode = new GuiNode(attributes, this.baseClass);
                    this.currentNode = this.parentNode;
                    break;
                }
                case environment: {
                    this.currentNode = new EnvironmentNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    this.parentNode = (ParentNode)this.currentNode;
                    break;
                }
                case key: {
                    this.currentNode = KeyOnlyNode.getForKey(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    this.parentNode = (ParentNode)this.currentNode;
                    break;
                }
                case only: {
                    this.currentNode = LogicalNode.getForValue(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    this.parentNode = (ParentNode)this.currentNode;
                    break;
                }
                case tooltip: {
                    this.currentNode = new TooltipNode(this.parentNode, attributes.getValue("text"));
                    this.parentNode.addNode(this.currentNode);
                    this.parentNode = (ParentNode)this.currentNode;
                    break;
                }
                case button: {
                    this.currentNode = new ButtonNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case energygauge: {
                    this.currentNode = new EnergyGaugeNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case gauge: {
                    this.currentNode = new GaugeNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case image: {
                    this.currentNode = new ImageNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case playerinventory: {
                    this.currentNode = new PlayerInventoryNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case slot: {
                    this.currentNode = new SlotNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case slotgrid: {
                    this.currentNode = new SlotGridNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case slothologram: {
                    this.currentNode = new SlotHologramNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case text: {
                    this.currentNode = new TextNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case fluidtank: {
                    this.currentNode = new FluidTankNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case fluidslot: {
                    this.currentNode = new FluidSlotNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                }
            }
        }

        @Override
        public void characters(char[] cArray, int n, int n2) throws SAXException {
            while (n2 > 0 && Character.isWhitespace(cArray[n])) {
                ++n;
                --n2;
            }
            while (n2 > 0 && Character.isWhitespace(cArray[n + n2 - 1])) {
                --n2;
            }
            if (n2 != 0) {
                if (this.currentNode == null) {
                    throw new SAXException("unexpected characters");
                }
                this.currentNode.setContent(new String(cArray, n, n2));
            }
        }

        @Override
        public void endElement(String string, String string2, String string3) throws SAXException {
            if (this.currentNode == this.parentNode) {
                if (this.currentNode.getType() == NodeType.gui) {
                    this.currentNode = null;
                } else {
                    this.parentNode = this.parentNode.parent;
                    this.currentNode = this.parentNode;
                }
            } else {
                this.currentNode = this.parentNode;
            }
        }

        public GuiNode getResult() {
            return (GuiNode)this.parentNode;
        }
    }

    public static class FluidSlotNode
    extends Node {
        final int x;
        final int y;
        final String name;

        FluidSlotNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.name = XmlUtil.getAttr(attributes, "name");
        }

        @Override
        public NodeType getType() {
            return NodeType.fluidslot;
        }
    }

    public static class FluidTankNode
    extends Node {
        final int x;
        final int y;
        final String name;
        final int width;
        final int height;
        final boolean mirrored;
        final TankType type;

        FluidTankNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.name = XmlUtil.getAttr(attributes, "name");
            String string = XmlUtil.getAttr(attributes, "type", "normal");
            this.type = TankType.get(string);
            if (this.type == null) {
                throw new SAXException("Invalid type: " + string);
            }
            if (this.type == TankType.PLAIN) {
                this.width = XmlUtil.getIntAttr(attributes, "width");
                this.height = XmlUtil.getIntAttr(attributes, "height");
            } else {
                this.height = -1;
                this.width = -1;
            }
            this.mirrored = this.type == TankType.BORDERLESS ? XmlUtil.getBoolAttr(attributes, "mirrored") : false;
        }

        @Override
        public NodeType getType() {
            return NodeType.fluidtank;
        }

        public static enum TankType {
            NORMAL,
            PLAIN,
            BORDERLESS;


            public static TankType get(String string) {
                for (TankType tankType : TankType.values()) {
                    if (!tankType.name().equalsIgnoreCase(string)) continue;
                    return tankType;
                }
                return null;
            }
        }
    }

    public static class TextNode
    extends Node {
        private static final int defaultColor = 0x404040;
        public final int x;
        public final int y;
        public final int width;
        public final int height;
        public final int xOffset;
        public final int yOffset;
        public final boolean centerX;
        public final boolean centerY;
        public final boolean rightAligned;
        public final TextLabel.TextAlignment align;
        public final int color;
        public final boolean shadow;
        public TextProvider.ITextProvider text;

        TextNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x", 0);
            this.y = XmlUtil.getIntAttr(attributes, "y", 0);
            this.width = XmlUtil.getIntAttr(attributes, "width", -1);
            this.height = XmlUtil.getIntAttr(attributes, "height", -1);
            this.xOffset = XmlUtil.getIntAttr(attributes, "xoffset", 0);
            this.yOffset = XmlUtil.getIntAttr(attributes, "yoffset", 0);
            String string = XmlUtil.getAttr(attributes, "align", "start");
            this.align = TextLabel.TextAlignment.get(string);
            if (this.align == null) {
                throw new SAXException("invalid alignment: " + string);
            }
            String string2 = XmlUtil.getAttr(attributes, "center", this.align == TextLabel.TextAlignment.Center ? "x" : "");
            this.centerX = string2.indexOf(120) != -1;
            this.centerY = string2.indexOf(121) != -1;
            this.rightAligned = XmlUtil.getBoolAttr(attributes, "right", false);
            this.color = XmlUtil.getIntAttr(attributes, "color", 0x404040);
            this.shadow = attributes.getIndex("shadow") != -1;
        }

        @Override
        public NodeType getType() {
            return NodeType.text;
        }

        @Override
        public void setContent(String string) throws SAXException {
            this.text = TextProvider.parse(string, this.parent.getBaseClass());
        }
    }

    public static class SlotHologramNode
    extends SlotNode {
        public final int index;
        public final int stackSizeLimit;

        SlotHologramNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode, attributes);
            this.index = XmlUtil.getIntAttr(attributes, "index", 0);
            this.stackSizeLimit = XmlUtil.getIntAttr(attributes, "stack", 64);
        }

        @Override
        public NodeType getType() {
            return NodeType.slothologram;
        }
    }

    public static class SlotGridNode
    extends Node {
        public final int x;
        public final int y;
        public final String name;
        public final int spacing;
        public final int offset;
        public final int rows;
        public final int cols;
        public final boolean vertical;
        public final SlotGrid.SlotStyle style;

        SlotGridNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.name = XmlUtil.getAttr(attributes, "name");
            this.spacing = XmlUtil.getIntAttr(attributes, "spacing", 0);
            this.offset = XmlUtil.getIntAttr(attributes, "offset", 0);
            this.rows = XmlUtil.getIntAttr(attributes, "rows", -1);
            this.cols = XmlUtil.getIntAttr(attributes, "cols", -1);
            this.vertical = XmlUtil.getBoolAttr(attributes, "vertical", false);
            String string = XmlUtil.getAttr(attributes, "style", "normal");
            this.style = SlotGrid.SlotStyle.get(string);
            if (this.style == null) {
                throw new SAXException("invalid slot style: " + string);
            }
        }

        @Override
        public NodeType getType() {
            return NodeType.slotgrid;
        }

        public SlotGridDimension getDimension(int n) {
            n -= this.offset;
            if (!this.vertical) {
                if (this.cols > 0) {
                    return new SlotGridDimension(Math.max(this.rows, (n + this.cols - 1) / this.cols), this.cols);
                }
                if (this.rows > 0) {
                    return new SlotGridDimension(this.rows, (n + this.rows - 1) / this.rows);
                }
                int n2 = (int)Math.floor(Math.sqrt(n));
                return new SlotGridDimension((n + n2 - 1) / n2, n2);
            }
            if (this.rows > 0) {
                return new SlotGridDimension(this.rows, Math.max(this.cols, (n + this.rows - 1) / this.rows));
            }
            if (this.cols > 0) {
                return new SlotGridDimension((n + this.cols - 1) / this.cols, this.cols);
            }
            int n3 = (int)Math.floor(Math.sqrt(n));
            return new SlotGridDimension(n3, (n + n3 - 1) / n3);
        }

        public static class SlotGridDimension {
            public final int rows;
            public final int cols;

            public SlotGridDimension(int n, int n2) {
                this.rows = n;
                this.cols = n2;
            }
        }
    }

    public static class SlotNode
    extends Node {
        public final int x;
        public final int y;
        public final String name;
        public final int index;
        public final SlotGrid.SlotStyle style;

        SlotNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.name = XmlUtil.getAttr(attributes, "name");
            this.index = XmlUtil.getIntAttr(attributes, "index", 0);
            String string = XmlUtil.getAttr(attributes, "style", "normal");
            this.style = SlotGrid.SlotStyle.get(string);
            if (this.style == null) {
                throw new SAXException("invalid slot style: " + string);
            }
        }

        @Override
        public NodeType getType() {
            return NodeType.slot;
        }
    }

    protected static class PlayerInventoryNode
    extends Node {
        final int x;
        final int y;
        final int spacing;
        final int hotbarOffset;
        final boolean showTitle;
        final SlotGrid.SlotStyle style;

        PlayerInventoryNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.spacing = XmlUtil.getIntAttr(attributes, "spacing", 0);
            this.hotbarOffset = XmlUtil.getIntAttr(attributes, "hotbaroffset", 58);
            String string = XmlUtil.getAttr(attributes, "style", "normal");
            this.style = SlotGrid.SlotStyle.get(string);
            if (this.style == null) {
                throw new SAXException("Invalid inventory slot style: " + string);
            }
            this.showTitle = XmlUtil.getBoolAttr(attributes, "title", true);
        }

        @Override
        public NodeType getType() {
            return NodeType.playerinventory;
        }
    }

    public static class ImageNode
    extends Node {
        public final int x;
        public final int y;
        public final int width;
        public final int height;
        public final int baseWidth;
        public final int baseHeight;
        public final int u1;
        public final int v1;
        public final int u2;
        public final int v2;
        public final ResourceLocation src;

        ImageNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            Object object;
            String string;
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.width = XmlUtil.getIntAttr(attributes, "width", -1);
            this.height = XmlUtil.getIntAttr(attributes, "height", -1);
            this.baseWidth = XmlUtil.getIntAttr(attributes, "basewidth", "basesize", 0);
            this.baseHeight = XmlUtil.getIntAttr(attributes, "baseheight", "basesize", 0);
            this.u1 = XmlUtil.getIntAttr(attributes, "u", "u1", 0);
            this.v1 = XmlUtil.getIntAttr(attributes, "v", "v1", 0);
            this.u2 = XmlUtil.getIntAttr(attributes, "u2", -1);
            this.v2 = XmlUtil.getIntAttr(attributes, "v2", -1);
            String string2 = XmlUtil.getAttr(attributes, "src");
            if (string2.isEmpty()) {
                throw new SAXException("empty src");
            }
            int n = string2.indexOf(58);
            if (n == -1) {
                string = "ic2";
                object = string2;
            } else {
                string = string2.substring(0, n);
                object = string2.substring(n + 1);
            }
            if (!((String)object).endsWith(".png")) {
                object = (String)object + ".png";
            }
            this.src = new ResourceLocation(string, (String)object);
        }

        @Override
        public NodeType getType() {
            return NodeType.image;
        }
    }

    public static class GaugeNode
    extends Node {
        public final int x;
        public final int y;
        public final String name;
        public final Gauge.IGaugeStyle style;
        public final boolean activeLinked;

        GaugeNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.name = XmlUtil.getAttr(attributes, "name");
            String string = XmlUtil.getAttr(attributes, "style", "normal");
            this.style = Gauge.GaugeStyle.get(string);
            if (this.style == null) {
                throw new SAXException("invalid gauge style: " + string);
            }
            this.activeLinked = XmlUtil.getBoolAttr(attributes, "active", false);
        }

        @Override
        public NodeType getType() {
            return NodeType.gauge;
        }
    }

    public static class EnergyGaugeNode
    extends Node {
        public final int x;
        public final int y;
        public final EnergyGauge.EnergyGaugeStyle style;

        EnergyGaugeNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            String string = XmlUtil.getAttr(attributes, "style", "bolt");
            this.style = EnergyGauge.EnergyGaugeStyle.get(string);
            if (this.style == null) {
                throw new SAXException("invalid gauge style: " + string);
            }
        }

        @Override
        public NodeType getType() {
            return NodeType.energygauge;
        }
    }

    public static class ButtonNode
    extends Node {
        final int x;
        final int y;
        final int width;
        final int height;
        final int eventID;
        final String eventName;
        final ItemStack icon;
        TextProvider.ITextProvider text;
        final ButtonType type;

        ButtonNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            this.x = XmlUtil.getIntAttr(attributes, "x");
            this.y = XmlUtil.getIntAttr(attributes, "y");
            this.width = XmlUtil.getIntAttr(attributes, "width", 16);
            this.height = XmlUtil.getIntAttr(attributes, "height", 16);
            Tuple.T2<Integer, String> t2 = this.getEventID(attributes);
            if (t2.a == null) {
                this.eventID = 0;
                this.eventName = (String)t2.b;
            } else {
                this.eventID = (Integer)t2.a;
                this.eventName = null;
            }
            String string = XmlUtil.getAttr(attributes, "type", "vanilla");
            this.type = ButtonType.get(string);
            if (this.type == null) {
                throw new SAXException("Invalid button type: " + string);
            }
            String string2 = XmlUtil.getAttr(attributes, "icon", "none");
            if ("none".equals(string2)) {
                this.icon = null;
            } else {
                try {
                    this.icon = ConfigUtil.asStack(string2);
                }
                catch (ParseException parseException) {
                    throw new SAXException("Invalid/Unknown icon requested: " + string2, parseException);
                }
            }
        }

        protected Tuple.T2<Integer, String> getEventID(Attributes attributes) throws SAXException {
            String string;
            Integer n;
            try {
                n = XmlUtil.getIntAttr(attributes, "event");
                string = null;
            }
            catch (NumberFormatException numberFormatException) {
                n = null;
                string = XmlUtil.getAttr(attributes, "event");
            }
            return new Tuple.T2<Integer, String>(n, string);
        }

        @Override
        public NodeType getType() {
            return NodeType.button;
        }

        @Override
        public void setContent(String string) throws SAXException {
            this.text = TextProvider.parse(string, this.parent.getBaseClass());
        }

        public static enum ButtonType {
            VANILLA,
            CUSTOM,
            TRANSPARENT,
            RECIPE;


            public static ButtonType get(String string) {
                for (ButtonType buttonType : ButtonType.values()) {
                    if (!buttonType.name().equalsIgnoreCase(string)) continue;
                    return buttonType;
                }
                return null;
            }
        }
    }

    public static class TooltipNode
    extends ParentNode {
        final String tooltip;

        public TooltipNode(ParentNode parentNode, String string) {
            super(parentNode);
            this.tooltip = string;
        }

        @Override
        public NodeType getType() {
            return NodeType.tooltip;
        }

        @Override
        void addElement(DynamicGui<?> dynamicGui, GuiElement<?> guiElement) {
            this.parent.addElement(dynamicGui, (GuiElement<?>)guiElement.withTooltip(this.tooltip));
        }
    }

    public static class LogicalNode
    extends ParentNode {
        final String condition;
        protected final boolean inverted;

        static LogicalNode getForValue(ParentNode parentNode, Attributes attributes) throws SAXException {
            return new LogicalNode(parentNode, XmlUtil.getAttr(attributes, "if"), attributes.getValue("inverted") != null);
        }

        protected LogicalNode(ParentNode parentNode, String string, boolean bl) {
            super(parentNode);
            this.condition = string;
            this.inverted = bl;
        }

        @Override
        public final NodeType getType() {
            return NodeType.only;
        }

        @Override
        protected Collection<IEnableHandler> addHandlers(DynamicGui<?> dynamicGui, GuiElement<?> guiElement, Collection<IEnableHandler> collection) {
            if (!(((DynamicContainer)((Object)dynamicGui.getContainer())).base instanceof IGuiConditionProvider)) {
                throw new RuntimeException("Invalid base " + ((DynamicContainer)((Object)dynamicGui.getContainer())).base + " for conditional elements");
            }
            IEnableHandler iEnableHandler = () -> ((IGuiConditionProvider)((DynamicContainer)((Object)((Object)dynamicGui.getContainer()))).base).getGuiState(this.condition);
            collection.add(this.inverted ? IEnableHandler.EnableHandlers.not(iEnableHandler) : iEnableHandler);
            return super.addHandlers(dynamicGui, guiElement, collection);
        }
    }

    static class AltOnlyNode
    extends KeyOnlyNode {
        AltOnlyNode(ParentNode parentNode, boolean bl) {
            super(parentNode, bl);
        }

        @Override
        protected boolean isKeyDown() {
            return Screen.m_96639_();
        }
    }

    static class ControlOnlyNode
    extends KeyOnlyNode {
        ControlOnlyNode(ParentNode parentNode, boolean bl) {
            super(parentNode, bl);
        }

        @Override
        protected boolean isKeyDown() {
            return Screen.m_96637_();
        }
    }

    static class ShiftOnlyNode
    extends KeyOnlyNode {
        ShiftOnlyNode(ParentNode parentNode, boolean bl) {
            super(parentNode, bl);
        }

        @Override
        protected boolean isKeyDown() {
            return Screen.m_96638_();
        }
    }

    public static abstract class KeyOnlyNode
    extends ParentNode {
        protected final boolean inverted;

        static KeyOnlyNode getForKey(ParentNode parentNode, Attributes attributes) throws SAXException {
            boolean bl;
            String string = XmlUtil.getAttr(attributes, "key");
            boolean bl2 = bl = attributes.getValue("inverted") != null;
            if ("shift".equalsIgnoreCase(string)) {
                return new ShiftOnlyNode(parentNode, bl);
            }
            if ("control".equalsIgnoreCase(string)) {
                return new ControlOnlyNode(parentNode, bl);
            }
            if ("alt".equalsIgnoreCase(string)) {
                return new AltOnlyNode(parentNode, bl);
            }
            throw new SAXException("Invalid/Unsupported key name: " + string);
        }

        protected KeyOnlyNode(ParentNode parentNode, boolean bl) {
            super(parentNode);
            this.inverted = bl;
        }

        @Override
        public final NodeType getType() {
            return NodeType.key;
        }

        @Override
        protected Collection<IEnableHandler> addHandlers(DynamicGui<?> dynamicGui, GuiElement<?> guiElement, Collection<IEnableHandler> collection) {
            collection.add(this.inverted ? IEnableHandler.EnableHandlers.not(this::isKeyDown) : this::isKeyDown);
            return super.addHandlers(dynamicGui, guiElement, collection);
        }

        protected abstract boolean isKeyDown();
    }

    public static class EnvironmentNode
    extends ParentNode {
        public final GuiEnvironment environment;

        EnvironmentNode(ParentNode parentNode, Attributes attributes) throws SAXException {
            super(parentNode);
            String string = XmlUtil.getAttr(attributes, "name");
            this.environment = GuiEnvironment.get(string);
            if (this.environment == null) {
                throw new SAXException("invalid environment name: " + string);
            }
        }

        @Override
        public NodeType getType() {
            return NodeType.environment;
        }
    }

    public static abstract class ParentNode
    extends Node {
        final List<Node> children = new ArrayList<Node>();

        ParentNode(ParentNode parentNode) {
            super(parentNode);
        }

        public void addNode(Node node) {
            this.children.add(node);
        }

        public Iterable<Node> getNodes() {
            return this.children;
        }

        public Class<?> getBaseClass() {
            return this.parent.getBaseClass();
        }

        void addElement(DynamicGui<?> dynamicGui, GuiElement<?> guiElement) {
            Collection<IEnableHandler> collection = this.addHandlers(dynamicGui, guiElement, new ArrayList<IEnableHandler>());
            if (!collection.isEmpty()) {
                guiElement.withEnableHandler(IEnableHandler.EnableHandlers.and(collection.toArray(new IEnableHandler[collection.size()])));
            }
            dynamicGui.addElement(guiElement);
        }

        protected Collection<IEnableHandler> addHandlers(DynamicGui<?> dynamicGui, GuiElement<?> guiElement, Collection<IEnableHandler> collection) {
            return collection;
        }
    }

    public static abstract class Node {
        final ParentNode parent;

        Node(ParentNode parentNode) {
            this.parent = parentNode;
        }

        public abstract NodeType getType();

        public void setContent(String string) throws SAXException {
            throw new SAXException("unexpected characters");
        }
    }

    public static enum NodeType {
        gui,
        environment,
        key,
        only,
        tooltip,
        button,
        energygauge,
        gauge,
        image,
        playerinventory,
        slot,
        slotgrid,
        slothologram,
        text,
        fluidtank,
        fluidslot;

        private static Map<String, NodeType> map;

        public static NodeType get(String string) {
            return map.get(string);
        }

        private static Map<String, NodeType> getMap() {
            NodeType[] nodeTypeArray = NodeType.values();
            HashMap<String, NodeType> hashMap = new HashMap<String, NodeType>(nodeTypeArray.length);
            for (NodeType nodeType : nodeTypeArray) {
                hashMap.put(nodeType.name(), nodeType);
            }
            return hashMap;
        }

        static {
            map = NodeType.getMap();
        }
    }
}

