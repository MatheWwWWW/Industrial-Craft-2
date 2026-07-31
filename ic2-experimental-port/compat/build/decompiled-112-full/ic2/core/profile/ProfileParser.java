/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.JsonToNBT
 *  net.minecraft.nbt.NBTException
 *  org.apache.commons.io.FilenameUtils
 *  org.apache.commons.io.IOCase
 *  org.apache.commons.io.filefilter.WildcardFileFilter
 */
package ic2.core.profile;

import ic2.core.IC2;
import ic2.core.profile.Profile;
import ic2.core.profile.ProfileTarget;
import ic2.core.profile.RecipeChange;
import ic2.core.profile.TextureStyle;
import ic2.core.profile.Version;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import ic2.core.util.XmlUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ProfileParser {
    public static Profile parse(ProfileTarget root) throws IOException {
        return ProfileParser.parse(root, root.offset("profile.xml").asStream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Profile parse(ProfileTarget root, InputStream is) throws IOException {
        ProfileNode parent;
        try {
            parent = ProfileParser.create(is);
        }
        catch (SAXException e) {
            IC2.log.error(LogCategory.Resource, e, "Error reading profile XML");
            Profile profile = null;
            return profile;
        }
        finally {
            is.close();
        }
        String name = null;
        Version style = null;
        HashSet<TextureStyle> textures = new HashSet<TextureStyle>();
        ArrayList<RecipeChange> recipeChanges = new ArrayList<RecipeChange>();
        block20: for (Node rawNode : parent.getNodes()) {
            switch (rawNode.getType()) {
                case name: {
                    if (name != null) {
                        throw new RuntimeException("Duplicate profile names: " + name + " and " + ((NameNode)rawNode).name);
                    }
                    name = ((NameNode)rawNode).name;
                    break;
                }
                case style: {
                    if (style != null) {
                        throw new RuntimeException("Duplicate profile styles: " + (Object)((Object)style) + " and " + (Object)((Object)((StyleNode)rawNode).style));
                    }
                    style = ((StyleNode)rawNode).style;
                    break;
                }
                case textures: {
                    textures.add(((TextureNode)rawNode).style.apply(root));
                    break;
                }
                case blocks: 
                case items: {
                    break;
                }
                case crafting: {
                    block21: for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
                        switch (cookedNode.getType()) {
                            case shaped: 
                            case shapeless: {
                                recipeChanges.addAll(ProfileParser.parseChanges(root, cookedNode.getType().name() + "_recipes", (ParentNode)cookedNode));
                                continue block21;
                            }
                        }
                        assert (NodeType.crafting.validChildren.contains((Object)cookedNode.getType()));
                        throw new IllegalStateException("Unexpected child element in crafting node: " + cookedNode);
                    }
                    continue block20;
                }
                case furnace: 
                case macerator: 
                case compressor: 
                case extractor: 
                case ore_washer: 
                case thermal_centrifuge: 
                case blast_furnace: 
                case block_cutter: {
                    recipeChanges.addAll(ProfileParser.parseChanges(root, (ParentNode)rawNode));
                    break;
                }
                case metal_former: {
                    block22: for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
                        switch (cookedNode.getType()) {
                            case cutting: 
                            case extruding: 
                            case rolling: {
                                recipeChanges.addAll(ProfileParser.parseChanges(root, "metal_former_" + cookedNode.getType().name(), (ParentNode)cookedNode));
                                continue block22;
                            }
                        }
                        assert (NodeType.metal_former.validChildren.contains((Object)cookedNode.getType()));
                        throw new IllegalStateException("Unexpected child element in metal former node: " + cookedNode);
                    }
                    continue block20;
                }
                default: {
                    assert (parent.getType().validChildren.contains((Object)rawNode.getType()));
                    throw new IllegalStateException("Unexpected child element in " + (Object)((Object)parent.getType()) + ": " + rawNode);
                }
            }
        }
        if (name == null) {
            throw new RuntimeException("Missing name for profile at " + root + "/profile.xml!");
        }
        if (style == null) {
            style = Version.NEW;
        }
        if (textures.isEmpty()) {
            textures.add(TextureStyle.EXPERIMENTAL);
        }
        return new Profile(name, textures, style, recipeChanges.toArray(new RecipeChange[0]));
    }

    private static List<RecipeChange> parseChanges(ProfileTarget root, ParentNode parent) {
        return ProfileParser.parseChanges(root, parent.getType().name(), parent);
    }

    private static List<RecipeChange> parseChanges(ProfileTarget root, String name, ParentNode parent) {
        ArrayList<RecipeChange> ret = new ArrayList<RecipeChange>();
        boolean madeReplacement = false;
        block14: for (Node rawNode : parent.getNodes()) {
            switch (rawNode.getType()) {
                case extend: {
                    if (madeReplacement) {
                        throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode);
                    }
                    if (ret.stream().anyMatch(change -> change.type == RecipeChange.ChangeType.EXTENSION)) {
                        throw new RuntimeException("Duplicate profile extension!");
                    }
                    ret.add(new RecipeChange.RecipeExtension(name, ((ExtensionNode)rawNode).profile));
                    continue block14;
                }
                case replacements: {
                    if (!madeReplacement && !ret.isEmpty()) {
                        throw new RuntimeException("Non-replacement changes made alongside replacement: " + ret);
                    }
                    assert (ret.stream().allMatch(change -> change.type == RecipeChange.ChangeType.REPLACEMENT));
                    madeReplacement = true;
                    ArrayList<ProfileTarget> targets = new ArrayList();
                    block15: for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
                        switch (cookedNode.getType()) {
                            case file: {
                                targets.add(root.offset(((FileNode)cookedNode).path));
                                continue block15;
                            }
                            case folder: {
                                targets.addAll(((FolderNode)cookedNode).getFiles(root));
                                continue block15;
                            }
                        }
                        assert (NodeType.replacements.validChildren.contains((Object)cookedNode.getType()));
                        throw new IllegalStateException("Unexpected child element in replacements node: " + cookedNode);
                    }
                    ret.add(new RecipeChange.RecipeReplacement(name, targets.toArray(new ProfileTarget[0])));
                    continue block14;
                }
                case additions: {
                    if (madeReplacement) {
                        throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode);
                    }
                    ArrayList<ProfileTarget> targets = new ArrayList<ProfileTarget>();
                    block16: for (Node cookedNode : ((ParentNode)rawNode).getNodes()) {
                        switch (cookedNode.getType()) {
                            case file: {
                                targets.add(root.offset(((FileNode)cookedNode).path));
                                continue block16;
                            }
                            case folder: {
                                targets.addAll(((FolderNode)cookedNode).getFiles(root));
                                continue block16;
                            }
                        }
                        assert (NodeType.additions.validChildren.contains((Object)cookedNode.getType()));
                        throw new IllegalStateException("Unexpected child element in additions node: " + cookedNode);
                    }
                    ret.add(new RecipeChange.RecipeAddition(name, targets.toArray(new ProfileTarget[0])));
                    continue block14;
                }
                case removals: {
                    if (madeReplacement) {
                        throw new RuntimeException("Non-replacement changes made alongside replacement: " + rawNode);
                    }
                    ret.add(null);
                    continue block14;
                }
            }
            assert (parent.getType().validChildren.contains((Object)rawNode.getType()));
            throw new IllegalStateException("Unexpected child element in " + (Object)((Object)parent.getType()) + ": " + rawNode);
        }
        return ret;
    }

    private static ProfileNode create(InputStream is) throws SAXException, IOException {
        is = new BufferedInputStream(is);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            SaxHandler handler = new SaxHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(is));
            return handler.getResult();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ExtensionNode
    extends Node {
        public final String profile;

        ExtensionNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            this.profile = XmlUtil.getAttr(attributes, "profile");
        }

        @Override
        public NodeType getType() {
            return NodeType.extend;
        }
    }

    public static class FolderNode
    extends Node {
        public final String path;

        FolderNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            this.path = XmlUtil.getAttr(attributes, "path");
        }

        @Override
        public NodeType getType() {
            return NodeType.folder;
        }

        Set<ProfileTarget> getFiles(ProfileTarget root) {
            ProfileTarget folder = root.offset(this.path);
            HashSet<ProfileTarget> files = new HashSet<ProfileTarget>();
            if (!folder.isFile()) {
                for (File file : folder.asFile().listFiles((FilenameFilter)new WildcardFileFilter("*.INI", IOCase.INSENSITIVE))) {
                    if (!file.isFile()) continue;
                    files.add(folder.offset(file.getName()));
                }
            } else {
                for (ZipEntry zipEntry : Collections.list(folder.asZip().entries())) {
                    String name;
                    if (zipEntry.isDirectory() || !FilenameUtils.equals((String)this.path, (String)FilenameUtils.getPathNoEndSeparator((String)(name = zipEntry.getName()))) || !FilenameUtils.isExtension((String)name, (String)"ini")) continue;
                    files.add(folder.offset(FilenameUtils.getName((String)name)));
                }
            }
            return files;
        }
    }

    public static class FileNode
    extends Node {
        public final String path;

        FileNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            this.path = XmlUtil.getAttr(attributes, "path");
        }

        @Override
        public NodeType getType() {
            return NodeType.file;
        }
    }

    public static class OreDictionaryNode
    extends Node {
        public final String tag;

        OreDictionaryNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            this.tag = XmlUtil.getAttr(attributes, "tag");
        }

        @Override
        public NodeType getType() {
            return NodeType.ore_dict;
        }
    }

    public static class StackNode
    extends Node {
        public final ItemStack stack;

        StackNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            String combined = attributes.getValue("combined");
            if (combined != null) {
                try {
                    this.stack = ConfigUtil.asStack(combined);
                }
                catch (ParseException e) {
                    throw new SAXException("Invalid/Unknown stack requested: " + combined, e);
                }
            }
            Item id = Util.getItem(XmlUtil.getAttr(attributes, "id"));
            if (id == null) {
                throw new SAXException("Invalid/Unknown item requested: " + id);
            }
            int meta = XmlUtil.getIntAttr(attributes, "meta", Short.MAX_VALUE);
            this.stack = new ItemStack(id, 1, meta);
            String nbt = attributes.getValue("nbt");
            if (nbt != null) {
                try {
                    this.stack.func_77982_d(JsonToNBT.func_180713_a((String)nbt));
                }
                catch (NBTException e) {
                    throw new SAXException("Invalid stack NBT: " + nbt, (Exception)((Object)e));
                }
            }
        }

        @Override
        public NodeType getType() {
            return NodeType.stack;
        }
    }

    public static class StyleNode
    extends Node {
        public final Version style;

        StyleNode(ParentNode parent, Attributes attributes) throws SAXException {
            super(parent);
            this.style = Version.valueOf(XmlUtil.getAttr(attributes, "type"));
        }

        @Override
        public NodeType getType() {
            return NodeType.style;
        }
    }

    public static class TextureNode
    extends Node {
        private final String mod;
        public Function<ProfileTarget, TextureStyle> style;

        TextureNode(ParentNode parent, Attributes attributes) {
            super(parent);
            this.mod = XmlUtil.getAttr(attributes, "mod", "ic2");
        }

        @Override
        public NodeType getType() {
            return NodeType.textures;
        }

        @Override
        public void setContent(String content) throws SAXException {
            switch (content) {
                case "NEW": {
                    this.style = root -> TextureStyle.EXPERIMENTAL;
                    break;
                }
                case "OLD": {
                    this.style = root -> TextureStyle.CLASSIC;
                    break;
                }
                default: {
                    this.style = root -> new TextureStyle(this.mod, root.offset(content));
                }
            }
        }
    }

    public static class NameNode
    extends Node {
        public String name;

        NameNode(ParentNode parent) {
            super(parent);
        }

        @Override
        public NodeType getType() {
            return NodeType.name;
        }

        @Override
        public void setContent(String content) throws SAXException {
            this.name = content;
        }
    }

    public static class GenericParentNode
    extends ParentNode {
        private final NodeType type;

        GenericParentNode(ParentNode parent, NodeType type) {
            super(parent);
            this.type = type;
        }

        @Override
        public NodeType getType() {
            return this.type;
        }
    }

    public static class ProfileNode
    extends ParentNode {
        ProfileNode() {
            super(null);
        }

        @Override
        public NodeType getType() {
            return NodeType.profile;
        }
    }

    public static abstract class ParentNode
    extends Node {
        final List<Node> children = new ArrayList<Node>();

        ParentNode(ParentNode parent) {
            super(parent);
        }

        public void addNode(Node node) throws SAXException {
            if (!this.getType().validChildren.contains((Object)node.getType())) {
                throw new SAXException("Invalid child: " + node);
            }
            this.children.add(node);
        }

        public Iterable<Node> getNodes() {
            return this.children;
        }

        @Override
        public String toString() {
            return "ParentNode<" + (Object)((Object)this.getType()) + ": " + this.children + '>';
        }
    }

    public static abstract class Node {
        final ParentNode parent;

        Node(ParentNode parent) {
            this.parent = parent;
        }

        public abstract NodeType getType();

        public void setContent(String content) throws SAXException {
            throw new SAXException("Unexpected characters: " + content);
        }

        public String toString() {
            return "Node<" + (Object)((Object)this.getType()) + '>';
        }
    }

    public static enum NodeType {
        name(new NodeType[0]),
        textures(new NodeType[0]),
        style(new NodeType[0]),
        stack(new NodeType[0]),
        ore_dict(new NodeType[0]),
        file(new NodeType[0]),
        folder(new NodeType[0]),
        extend(new NodeType[0]),
        whitelist(stack),
        blacklist(stack),
        blocks(whitelist, blacklist),
        items(whitelist, blacklist),
        replacements(file, folder),
        additions(file, folder),
        removals(stack, ore_dict, file, folder),
        shaped(extend, replacements, additions, removals),
        shapeless(extend, replacements, additions, removals),
        crafting(shaped, shapeless),
        furnace(extend, replacements, additions, removals),
        macerator(extend, replacements, additions, removals),
        compressor(extend, replacements, additions, removals),
        extractor(extend, replacements, additions, removals),
        ore_washer(extend, replacements, additions, removals),
        thermal_centrifuge(extend, replacements, additions, removals),
        blast_furnace(extend, replacements, additions, removals),
        block_cutter(extend, replacements, additions, removals),
        cutting(extend, replacements, additions, removals),
        extruding(extend, replacements, additions, removals),
        rolling(extend, replacements, additions, removals),
        metal_former(cutting, extruding, rolling),
        profile(name, textures, style, blocks, items, crafting, furnace, macerator, compressor, extractor, ore_washer, thermal_centrifuge, blast_furnace, block_cutter, metal_former);

        private NodeType[] types;
        Set<NodeType> validChildren;
        private static final Map<String, NodeType> MAP;

        private NodeType(NodeType ... types) {
            this.types = types;
        }

        public static NodeType get(String name) {
            return MAP.get(name);
        }

        static {
            for (NodeType type : NodeType.values()) {
                type.validChildren = type.types.length == 0 ? Collections.emptySet() : EnumSet.copyOf(Arrays.asList(type.types));
                type.types = null;
            }
            MAP = Arrays.stream(NodeType.values()).collect(Collectors.toMap(Enum::name, Function.identity()));
        }
    }

    private static class SaxHandler
    extends DefaultHandler {
        private ParentNode parentNode;
        private Node currentNode;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            NodeType type = NodeType.get(qName);
            if (type == null) {
                type = NodeType.get(qName.toLowerCase(Locale.ENGLISH));
            }
            if (type == null) {
                throw new SAXException("Invalid element: " + qName);
            }
            if (type == NodeType.profile) {
                if (this.parentNode != null) {
                    throw new SAXException("Invalid profile element location");
                }
            } else if (this.parentNode == null) {
                throw new SAXException("invalid " + qName + " element location");
            }
            switch (type) {
                case profile: {
                    this.parentNode = new ProfileNode();
                    this.currentNode = this.parentNode;
                    break;
                }
                case shaped: 
                case shapeless: 
                case cutting: 
                case extruding: 
                case rolling: 
                case blocks: 
                case items: 
                case crafting: 
                case furnace: 
                case macerator: 
                case compressor: 
                case extractor: 
                case ore_washer: 
                case thermal_centrifuge: 
                case blast_furnace: 
                case block_cutter: 
                case metal_former: 
                case replacements: 
                case additions: 
                case removals: 
                case whitelist: 
                case blacklist: {
                    this.currentNode = new GenericParentNode(this.parentNode, type);
                    this.parentNode.addNode(this.currentNode);
                    this.parentNode = (ParentNode)this.currentNode;
                    break;
                }
                case name: {
                    this.currentNode = new NameNode(this.parentNode);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case textures: {
                    this.currentNode = new TextureNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case style: {
                    this.currentNode = new StyleNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case stack: {
                    this.currentNode = new StackNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case ore_dict: {
                    this.currentNode = new OreDictionaryNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case file: {
                    this.currentNode = new FileNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case folder: {
                    this.currentNode = new FolderNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                    break;
                }
                case extend: {
                    this.currentNode = new ExtensionNode(this.parentNode, attributes);
                    this.parentNode.addNode(this.currentNode);
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            while (length > 0 && Character.isWhitespace(ch[start])) {
                ++start;
                --length;
            }
            while (length > 0 && Character.isWhitespace(ch[start + length - 1])) {
                --length;
            }
            if (length != 0) {
                if (this.currentNode == null) {
                    throw new SAXException("unexpected characters");
                }
                this.currentNode.setContent(new String(ch, start, length));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this.currentNode == this.parentNode) {
                if (this.currentNode.getType() == NodeType.profile) {
                    this.currentNode = null;
                } else {
                    this.parentNode = this.parentNode.parent;
                    this.currentNode = this.parentNode;
                }
            } else {
                this.currentNode = this.parentNode;
            }
        }

        public ProfileNode getResult() {
            return (ProfileNode)this.parentNode;
        }
    }
}

