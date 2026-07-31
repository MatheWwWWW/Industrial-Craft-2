/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.uu;

import ic2.core.uu.LeanItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class RecipeTransformation {
    public final double transformCost;
    public List<List<LeanItemStack>> inputs;
    public List<LeanItemStack> outputs;

    public RecipeTransformation(double transformCost, List<List<LeanItemStack>> inputs, LeanItemStack ... outputs) {
        this(transformCost, inputs, Arrays.asList(outputs));
    }

    public RecipeTransformation(double transformCost, List<List<LeanItemStack>> inputs, List<LeanItemStack> outputs) {
        this.transformCost = transformCost;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    protected void merge() {
        ArrayList<List<LeanItemStack>> cleanInputs = new ArrayList<List<LeanItemStack>>();
        for (List<LeanItemStack> inputList : this.inputs) {
            boolean found = false;
            ListIterator<List<LeanItemStack>> listIterator = cleanInputs.listIterator();
            while (listIterator.hasNext()) {
                List<LeanItemStack> cleanInputList = (List<LeanItemStack>)listIterator.next();
                if ((cleanInputList = this.mergeEqualLists(inputList, cleanInputList)) == null) continue;
                found = true;
                listIterator.set(cleanInputList);
                break;
            }
            if (found) continue;
            cleanInputs.add(inputList);
        }
        for (List<LeanItemStack> inputList : this.inputs) {
            block3: for (List list : cleanInputs) {
                LinkedList<LeanItemStack> unmatched = new LinkedList<LeanItemStack>(inputList);
                boolean found = false;
                for (LeanItemStack stackOffer : list) {
                    found = false;
                    Iterator it = unmatched.iterator();
                    while (it.hasNext()) {
                        LeanItemStack stackReq = (LeanItemStack)it.next();
                        if (!stackOffer.hasSameItem(stackReq)) continue;
                        found = true;
                        it.remove();
                        break;
                    }
                    if (found) continue;
                    continue block3;
                }
            }
        }
        this.inputs = cleanInputs;
        ArrayList<LeanItemStack> cleanOutputs = new ArrayList<LeanItemStack>();
        for (LeanItemStack output : this.outputs) {
            boolean bl = false;
            ListIterator<LeanItemStack> it = cleanOutputs.listIterator();
            while (it.hasNext()) {
                LeanItemStack stack = (LeanItemStack)it.next();
                if (!output.hasSameItem(stack)) continue;
                bl = true;
                it.set(stack.copyWithSize(stack.getSize() + output.getSize()));
                break;
            }
            if (bl) continue;
            cleanOutputs.add(output);
        }
        this.outputs = cleanOutputs;
    }

    public String toString() {
        return "{ " + this.transformCost + " + " + this.inputs + " -> " + this.outputs + " }";
    }

    private List<LeanItemStack> mergeEqualLists(List<LeanItemStack> listA, List<LeanItemStack> listB) {
        if (listA.size() != listB.size()) {
            return null;
        }
        ArrayList<LeanItemStack> ret = new ArrayList<LeanItemStack>(listA.size());
        LinkedList<LeanItemStack> listBCopy = new LinkedList<LeanItemStack>(listB);
        for (LeanItemStack a : listA) {
            boolean found = false;
            Iterator it = listBCopy.iterator();
            while (it.hasNext()) {
                LeanItemStack b = (LeanItemStack)it.next();
                if (!a.hasSameItem(b)) continue;
                found = true;
                ret.add(a.copyWithSize(a.getSize() + b.getSize()));
                it.remove();
                break;
            }
            if (found) continue;
            return null;
        }
        return ret;
    }
}

