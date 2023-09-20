package com.example.multimoduleapp.model

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.ColorInt

fun String.getClickableSpan(
    toSpan: String,
    @ColorInt color: Int = 0,
    isHavingUnderline: Boolean = true,
    shouldBeBold: Boolean = false,
    clickEvent: (View) -> Unit
): SpannableString {
    val signUpSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents()
            clickEvent.invoke(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            if (!isHavingUnderline) {
                ds.isUnderlineText = false
            }

            if (shouldBeBold) {
                ds.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }

    val spanStartIndex = this.indexOf(toSpan)
    val spanEndIndex = spanStartIndex + toSpan.length
    val span = SpannableString(this)
    span.setSpan(
        signUpSpan,
        spanStartIndex,
        spanEndIndex,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (color != 0) {
        span.setSpan(
            ForegroundColorSpan(color),
            spanStartIndex,
            spanEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return span
}