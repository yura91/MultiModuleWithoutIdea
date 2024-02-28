package net.pst.cash.presentation.model

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.dpToPx(dp: Float): Float {
    val scale = resources.displayMetrics.density
    return dp * scale
}

fun Float.dpToPx(): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return this * scale
}

fun Float.pxFromDp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale).toInt()
}

fun Activity.hideKeyBoard(
    view: View?
) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun formatDate(calendar: Calendar): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}