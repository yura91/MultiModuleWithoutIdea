package net.pst.cash.presentation.cardstack;

public interface ScrollDelegate {

    void scrollViewTo(int x, int y);

    int getViewScrollY();

    void setViewScrollY(int y);

    int getViewScrollX();

    void setViewScrollX(int x);

}
