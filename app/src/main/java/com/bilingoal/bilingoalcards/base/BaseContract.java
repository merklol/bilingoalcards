package com.bilingoal.bilingoalcards.base;

public interface BaseContract {
    interface Presenter<T extends View> {
        void subscribe(T view);
        void unsubscribe();
    }

    interface View { }
}