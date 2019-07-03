package xuantang.vptf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = TabAdapter(supportFragmentManager)

        view_pager.offscreenPageLimit = 4

        view_pager.setPageTransformer(true, ViewPagerTransformer(TransformType.DEPTH))
    }
}
