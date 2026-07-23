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

class BottomMenuAdapter(
    private val actionList: ActionList,
    private val array: ActionNameArray,
    private val controller: ActionController,
    private val iconManager: ActionIconManager,
    private val showIcons: Boolean,
    private val onItemClick: () -> Unit
) : RecyclerView.Adapter<BottomMenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.iconImageView)
        val name: TextView = view.findViewById(R.id.actionNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bottom_sheet_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = actionList[position]
        holder.name.text = action.toString(array)

        if (showIcons) {
            val iconDrawable = iconManager[action]
            if (iconDrawable != null) {
                holder.icon.setImageDrawable(iconDrawable)
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
