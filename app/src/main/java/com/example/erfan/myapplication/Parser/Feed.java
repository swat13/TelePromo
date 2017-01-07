package com.example.erfan.myapplication.Parser;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Created by erfan on 1/5/2017.
 */

public class Feed implements Serializable {

    private static final long serialVersionUID = 1L;
    private int _itemcount = 0;
    private List<Item> _itemlist;

    public Feed() {
        _itemlist = new Vector<Item>(0);
    }

    public void addItem(Item item) {
        _itemlist.add(item);
        _itemcount++;
    }

    public void removeItem(int position) {
        _itemlist.remove(position);
        _itemcount--;
    }

    public Item getItem(int location) {
        return _itemlist.get(location);
    }

    public int getItemCount() {
        return _itemcount;
    }

}