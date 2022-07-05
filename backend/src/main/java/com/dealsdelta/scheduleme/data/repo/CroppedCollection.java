package com.dealsdelta.scheduleme.data.repo;


import java.util.Collection;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/05/21
 */

public class CroppedCollection<T extends Collection> {
    private int size;
    private T items;
    public CroppedCollection(int size, T items) {
        this.size = size;
        this.items = items;
    }

    public int getSize() {
        return size;
    }

    public T getItems() {
        return items;
    }
}