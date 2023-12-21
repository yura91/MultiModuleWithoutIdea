package net.pst.cash.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
        if (parent.adapter != null && parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}