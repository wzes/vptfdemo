package xuantang.vptf

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.tab_item.*
import java.util.*

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return TabFragment("Fragment $position", position)
    }

    override fun getCount(): Int {
        return 4
    }

    @SuppressLint("ValidFragment")
    class TabFragment(private var content: String, private var position: Int) : Fragment() {

        private val colors = Arrays.asList(Color.GRAY, Color.RED, Color.BLUE, Color.YELLOW)!!
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            text_view.text = content
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.tab_item, container, false)
            view.setBackgroundColor(colors[position % colors.size])
            view.tag = "$position"
            return view
        }
    }
}