package jp.hazuki.yuzubrowser.legacy.menuwindow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.hazuki.yuzubrowser.legacy.R
import jp.hazuki.yuzubrowser.legacy.action.ActionList
import jp.hazuki.yuzubrowser.legacy.action.ActionNameArray
import jp.hazuki.yuzubrowser.legacy.action.manager.ActionController
import jp.hazuki.yuzubrowser.legacy.action.manager.ActionIconManager

class MenuGridAdapter(
    private val actionList: ActionList,
    private val array: ActionNameArray,
    private val controller: ActionController,
    private val iconManager: ActionIconManager,
    private val showIcons: Boolean,
    private val onItemClick: () -> Unit
) : RecyclerView.Adapter<MenuGridAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.menuIcon)
        val name: TextView = view.findViewById(R.id.menuName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_menu_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val action = actionList[position]
        holder.name.text = action.toString(array)

        if (showIcons) {
            val d = iconManager[action]
            if (d != null) {
                holder.icon.setImageDrawable(d)
                holder.icon.visibility = View.VISIBLE
            } else {
                holder.icon.visibility = View.GONE
            }
        } else {
            holder.icon.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            controller.run(action)
            onItemClick()
        }
    }

    override fun getItemCount(): Int = actionList.size
}
