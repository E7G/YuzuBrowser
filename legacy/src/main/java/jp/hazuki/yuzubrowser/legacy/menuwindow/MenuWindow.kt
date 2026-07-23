package jp.hazuki.yuzubrowser.legacy.menuwindow

import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import jp.hazuki.yuzubrowser.core.utility.extensions.convertDpToPx
import jp.hazuki.yuzubrowser.legacy.R
import jp.hazuki.yuzubrowser.legacy.action.ActionList
import jp.hazuki.yuzubrowser.legacy.action.ActionNameArray
import jp.hazuki.yuzubrowser.legacy.action.manager.ActionController
import jp.hazuki.yuzubrowser.legacy.action.manager.ActionIconManager
import jp.hazuki.yuzubrowser.ui.app.ThemeActivity
import jp.hazuki.yuzubrowser.ui.settings.AppPrefs

typealias OnMenuCloseListener = () -> Unit

class MenuWindow(context: ThemeActivity, private val actionList: ActionList, private val controller: ActionController, private val iconManager: ActionIconManager) {

    private val handler = Handler(Looper.getMainLooper())
    private var locking = false
    private var mListener: OnMenuCloseListener? = null
    private var dialog: BottomSheetDialog? = null
    private val array = ActionNameArray(context)
    private val showIcons = AppPrefs.menu_icon.get()

    fun show(root: View, gravity: Int) {
        if (locking || dialog?.isShowing == true) return

        dialog = BottomSheetDialog(root.context, R.style.MenuBottomSheet).apply {
            setContentView(R.layout.bottom_sheet_menu)

            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) ?: return@apply
            recyclerView.layoutManager = GridLayoutManager(context, 3)
            recyclerView.adapter = MenuGridAdapter(
                actionList, array, controller, iconManager, showIcons
            ) { dismiss() }

            setOnDismissListener {
                locking = true
                handler.postDelayed({ locking = false }, 50)
                mListener?.invoke()
            }

            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_MENU && event.repeatCount == 0 && event.action == KeyEvent.ACTION_DOWN) {
                    dismiss()
                    true
                } else false
            }

            show()

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isHideable = true
            behavior.skipCollapsed = true
        }
    }

    fun showAsDropDown(anchor: View) {
        show(anchor, android.view.Gravity.BOTTOM)
    }

    val isShowing: Boolean
        get() = dialog?.isShowing == true

    fun dismiss() {
        dialog?.dismiss()
    }

    fun setListener(listener: OnMenuCloseListener) {
        mListener = listener
    }
}
