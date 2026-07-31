/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.bytes.ByteArrayList
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  net.minecraft.nbt.ByteArrayTag
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongArrayTag
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.ShortTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 */
package ic2.core.item.logic;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class NBTVisualizer {
    public static JFrame load(Supplier<CompoundTag> provider, final Runnable run) {
        JFrame frame = new JFrame("NBTVisualizer");
        frame.addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                run.run();
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }
        });
        JTree tree = new JTree(new DefaultMutableTreeNode("Root: 0 Entries"));
        frame.setDefaultCloseOperation(2);
        frame.setSize(700, 480);
        frame.setLocationRelativeTo(null);
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add((Component)scrollPane, "Center");
        scrollPane.setViewportView(tree);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenuItem menu = menuBar.add(new JMenu("Base")).add("Reload");
        menu.addActionListener(T -> EventQueue.invokeLater(() -> NBTVisualizer.lambda$load$0(tree, (Supplier)provider)));
        menu.setAccelerator(KeyStroke.getKeyStroke('r'));
        EventQueue.invokeLater(() -> {
            tree.setModel(new DefaultTreeModel(NBTVisualizer.readMap((CompoundTag)provider.get(), "")));
            tree.expandRow(0);
            tree.updateUI();
        });
        frame.setVisible(true);
        return frame;
    }

    public static DefaultMutableTreeNode readMap(CompoundTag map, String prefix) {
        if (map == null) {
            return new DefaultMutableTreeNode("0 Entries");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            NbtIo.m_128941_((CompoundTag)map, (DataOutput)new DataOutputStream(out));
        }
        catch (Exception exception) {
            // empty catch block
        }
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(prefix + map.m_128440_() + " Entries: " + out.size() + " Bytes");
        for (String key : map.m_128431_()) {
            DefaultMutableTreeNode subNode;
            Tag tag = map.m_128423_(key);
            if (tag instanceof ListTag) {
                node.add(NBTVisualizer.readList((ListTag)tag, key + ": "));
                continue;
            }
            if (tag instanceof CompoundTag) {
                node.add(NBTVisualizer.readMap((CompoundTag)tag, key + ": "));
                continue;
            }
            if (tag.m_7060_() >= 1 && tag.m_7060_() <= 6 || tag.m_7060_() == 8) {
                subNode = NBTVisualizer.readValue(tag, key + ": ");
                if (subNode == null) continue;
                node.add(subNode);
                continue;
            }
            if (tag.m_7060_() != 7 && tag.m_7060_() != 11 && tag.m_7060_() != 12 || (subNode = NBTVisualizer.readArray(tag, key + ": ")) == null) continue;
            node.add(subNode);
        }
        return node;
    }

    public static DefaultMutableTreeNode readList(ListTag list, String prefix) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(prefix + list.size() + " Entries");
        for (int i = 0; i < list.size(); ++i) {
            DefaultMutableTreeNode subNode;
            Tag tag = list.get(i);
            if (tag instanceof ListTag) {
                node.add(NBTVisualizer.readList((ListTag)tag, ""));
                continue;
            }
            if (tag instanceof CompoundTag) {
                node.add(NBTVisualizer.readMap((CompoundTag)tag, ""));
                continue;
            }
            if (tag.m_7060_() >= 1 && tag.m_7060_() <= 6 || tag.m_7060_() == 8) {
                subNode = NBTVisualizer.readValue(tag, "");
                if (subNode == null) continue;
                node.add(subNode);
                continue;
            }
            if (tag.m_7060_() != 7 && tag.m_7060_() != 11 && tag.m_7060_() != 12 || (subNode = NBTVisualizer.readArray(tag, "")) == null) continue;
            node.add(subNode);
        }
        return node;
    }

    public static DefaultMutableTreeNode readArray(Tag tag, String prefix) {
        if (tag instanceof ByteArrayTag) {
            return new DefaultMutableTreeNode(prefix + ByteArrayList.wrap((byte[])((ByteArrayTag)tag).m_128227_()) + ", (Byte Array)");
        }
        if (tag instanceof IntArrayTag) {
            return new DefaultMutableTreeNode(prefix + IntArrayList.wrap((int[])((IntArrayTag)tag).m_128648_()) + ", (Int Array)");
        }
        if (tag instanceof LongArrayTag) {
            return new DefaultMutableTreeNode(prefix + LongArrayList.wrap((long[])((LongArrayTag)tag).m_128851_()) + ", (Long Array)");
        }
        return null;
    }

    public static DefaultMutableTreeNode readValue(Tag tag, String prefix) {
        if (tag instanceof ByteTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7063_() + ", (Byte)");
        }
        if (tag instanceof ShortTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7053_() + ", (Short)");
        }
        if (tag instanceof IntTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7047_() + ", (Int)");
        }
        if (tag instanceof LongTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7046_() + ", (Long)");
        }
        if (tag instanceof FloatTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7057_() + ", (Float)");
        }
        if (tag instanceof DoubleTag) {
            return new DefaultMutableTreeNode(prefix + ((NumericTag)tag).m_7061_() + ", (Double)");
        }
        if (tag instanceof StringTag) {
            return new DefaultMutableTreeNode(prefix + tag.m_7916_() + ", (String)");
        }
        return null;
    }

    private static /* synthetic */ void lambda$load$0(JTree tree, Supplier provider) {
        tree.setModel(new DefaultTreeModel(NBTVisualizer.readMap((CompoundTag)provider.get(), "")));
        tree.updateUI();
    }
}

