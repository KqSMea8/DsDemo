package com.dryseed.module_recyclerview.itemTouchHelper.demo1;

public interface ItemTouchHelperListener {
    //数据交换
    void onItemMove(int fromPosition, int toPosition);

    //数据删除
    void onItemDissmiss(int position);

    //操作结束
    void onItemFinished(int position);

    //操作开始
    void onItemStart(int position);
}
