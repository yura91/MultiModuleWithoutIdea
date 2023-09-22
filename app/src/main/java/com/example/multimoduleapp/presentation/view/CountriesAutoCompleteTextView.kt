package com.example.multimoduleapp.presentation.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.example.multimoduleapp.R


class CountriesAutoCompleteTextView(context: Context, attrs: AttributeSet) :
    AppCompatAutoCompleteTextView(context, attrs) {

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFilterComplete(count: Int) {
        super.onFilterComplete(count)
        if (isPopupShowing) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                AppCompatResources.getDrawable(context, R.drawable.ic_text_arrow_up),
                null
            )
        } else {
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                AppCompatResources.getDrawable(context, R.drawable.ic_text_arrow_down),
                null
            )
        }
    }

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused && adapter != null) {
            performFiltering(text, 0)
        }
    }
}

