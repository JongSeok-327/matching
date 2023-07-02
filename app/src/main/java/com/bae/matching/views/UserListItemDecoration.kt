package com.bae.matching.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bae.matching.utils.dpToPx

class UserListItemDecoration: RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        with(outRect) {
            if (position == 0 || position == 1) {
                top = 18.dpToPx()
            }

            if (position % 2 == 0) {
                left = 20.dpToPx()
                right = 7.dpToPx()
            } else {
                left = 7.dpToPx()
                right = 20.dpToPx()
            }

            bottom = 15.dpToPx()
        }
    }
}