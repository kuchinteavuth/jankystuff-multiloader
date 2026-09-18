package com.inteavuthkuch.jankystuff.common;

public interface ISortableBlockEntity {
    boolean getIsSortByAmount();
    boolean getIsSortAscending();
    void sort(boolean byAmount, boolean ascending);
}
