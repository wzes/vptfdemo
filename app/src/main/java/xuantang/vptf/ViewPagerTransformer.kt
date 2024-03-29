package xuantang.vptf

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

private const val MIN_SCALE_DEPTH = 0.75f
private const val MIN_SCALE_ZOOM = 0.85f
private const val MIN_ALPHA_ZOOM = 0.5f
private const val SCALE_FACTOR_SLIDE = 0.85f
private const val MIN_ALPHA_SLIDE = 0.35f

internal class ViewPagerTransformer(
        private val transformType: TransformType
) : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when (transformType) {
            TransformType.FLOW -> page.rotationY = position * -30f
            TransformType.SLIDE_OVER -> if (position < 0 && position > -1) {
                // this is the page to the left
                page.scaleXY = Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE) + SCALE_FACTOR_SLIDE
                page.alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position))
                val pageWidth = page.width
                val translateValue = position * -pageWidth
                if (translateValue > -pageWidth) {
                    page.translationX = translateValue
                } else {
                    page.translationX = 0f
                }
            } else {
                page.transformDefaults()
            }

            TransformType.DEPTH -> if (position > 0 && position < 1) {
                Log.e("TAG", page.tag.toString())
                // moving to the right
                page.alpha = 1 - position
                page.scaleXY = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position))
                page.translationX = page.width * -position
            } else {
                page.transformDefaults()
            }

            TransformType.ZOOM -> if (position >= -1 && position <= 1) {
                page.scaleXY = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position))
                page.alpha = MIN_ALPHA_ZOOM + (page.scaleXY - MIN_SCALE_ZOOM) /
                        (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM)
                val vMargin = page.height * (1 - page.scaleXY) / 2
                val hMargin = page.width * (1 - page.scaleXY) / 2
                if (position < 0) {
                    page.translationX = hMargin - vMargin / 2
                } else {
                    page.translationX = -hMargin + vMargin / 2
                }
            } else {
                page.transformDefaults()
            }
            TransformType.FADE -> {
                if (position <= -1.0f || position >= 1.0f) {
                    page.alpha = 0.0f
                    page.isClickable = false
                } else if (position == 0.0f) {
                    page.alpha = 1.0f
                    page.isClickable = true
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    page.alpha = 1.0f - Math.abs(position)
                }
            }
            TransformType.FOLD -> {
                if (position < -1) {
                    page.visibility = View.GONE
                } else {
                    page.visibility = View.VISIBLE
                }
                if (position >= 0) {
                    page.translationX = page.width * -position * 0.9F
                    page.scaleXY = Math.min(MIN_SCALE_ZOOM - Math.abs(position) * .1F, MIN_SCALE_ZOOM)
                }
            }
        }
    }
}

enum class TransformType {
    FLOW,
    DEPTH,
    ZOOM,
    SLIDE_OVER,
    FADE,
    FOLD
}

private fun View.transformDefaults() {
    this.alpha = 1f
    this.scaleXY = 1f
    this.translationX = 0f
}

private var View.scaleXY: Float
    get() = this.scaleX
    set(value) {
        this.scaleX = value
        this.scaleY = value
    }
