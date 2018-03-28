package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList implements Cloneable, Serializable {
    private Entry<String> root;
    private int size;
    private List<Entry> listEntries = new ArrayList<>();

    public CustomTree() {
        this.root = new Entry<>("0");
        this.root.lineNumber = 0;
        this.root.parent = null;
        size = 0;
        listEntries.add(this.root);
    }

    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, String element) {

        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    //@Override


    @Override
    public boolean add(Object o) {
        return this.add(o.toString());
    }

    public boolean add(String element) {
        Entry entry = new Entry(element);
        if (listEntries.contains(entry)) {
            return false;
        }

        Iterator<Entry> iterator = listEntries.iterator();
        Entry newParent = null;
        while (iterator.hasNext()) {
            Entry e = iterator.next();
            if (e.isAvailableToAddChildren() && (e.elementName.equals("0") || e.parent != null)) {
                newParent = e;
                break;
            }
        }
        listEntries.add(entry);
        if (newParent != null) {
            if (newParent.availableToAddLeftChildren) {
                newParent.leftChild = entry;
            } else {
                newParent.rightChild = entry;
            }
            entry.parent = newParent;
            entry.lineNumber = entry.parent.lineNumber + 1;
            newParent.checkChildren();
            size++;
            return true;
        }
        return false;
    }





    public String getParent(String s) {
        Iterator<Entry> iterator = listEntries.iterator();
        Entry e = null;
        while (iterator.hasNext()) {
            e = iterator.next();
            if (e.elementName.equals(s)) {
                break;
            }
        }
        if (e !=null && e.parent != null) {
            return e.parent.elementName;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object o) {
        String s = (String) o;
        boolean flag = false;
        Iterator<Entry> iterator = listEntries.iterator();
        Queue<Entry> queue = new LinkedList<>();
        while (iterator.hasNext()) {
            Entry en = iterator.next();
            if (en.elementName.equals(s)) {
                queue.add(en);
                removing(queue);
                while (!queue.isEmpty()){
                    removing(queue);
                }
                flag = true;
                break;
            }
        }
        return flag;
    }
    private void removing(Queue<Entry> queue) {
        if (queue.peek() != null) {
            Entry e = queue.poll();
            if (!e.availableToAddLeftChildren) {
                queue.add(e.leftChild);
            }
            if (!e.availableToAddRightChildren) {
                queue.add(e.rightChild);
            }
            if (e.parent != null) {
                if (e.parent.leftChild == e) {
                    e.parent.leftChild = null;
                } else if (e.parent.rightChild == e) {
                    e.parent.rightChild = null;
                }
                e.parent.checkChildren();
            }
            e.parent = null;
            //listEntries.remove(e);
            size--;
        }
    }

    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent;
        Entry<T> leftChild;
        Entry<T> rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public void checkChildren(){
            if (leftChild != null) {
                availableToAddLeftChildren = false;
            }
            if (rightChild != null) {
                availableToAddRightChildren = false;
            }
        }

        public boolean isAvailableToAddChildren() {
            return (availableToAddLeftChildren || availableToAddRightChildren);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?> entry = (Entry<?>) o;
            return Objects.equals(elementName, entry.elementName);
        }

        @Override
        public int hashCode() {

            return Objects.hash(elementName);
        }
    }


    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        //System.out.println("Expected 3, actual is ");
        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
    }
}
