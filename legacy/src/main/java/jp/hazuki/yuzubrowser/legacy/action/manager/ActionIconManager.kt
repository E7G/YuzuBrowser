/*
 * Copyright (C) 2017-2019 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.hazuki.yuzubrowser.legacy.action.manager

import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.DrawableCompat
import android.widget.Toast
import jp.hazuki.yuzubrowser.bookmark.repository.BookmarkManager
import jp.hazuki.yuzubrowser.legacy.R
import jp.hazuki.yuzubrowser.legacy.action.Action
import jp.hazuki.yuzubrowser.legacy.action.SingleAction
import jp.hazuki.yuzubrowser.legacy.action.item.CustomSingleAction
import jp.hazuki.yuzubrowser.legacy.action.item.WebScrollSingleAction
import jp.hazuki.yuzubrowser.legacy.action.item.startactivity.StartActivitySingleAction
import jp.hazuki.yuzubrowser.legacy.browser.BrowserInfo
import jp.hazuki.yuzubrowser.legacy.utils.graphics.SimpleLayerDrawable
import jp.hazuki.yuzubrowser.legacy.utils.graphics.TabListActionTextDrawable
import jp.hazuki.yuzubrowser.ui.settings.AppPrefs

class ActionIconManager(val info: BrowserInfo) {

    private fun tintDark(drawable: Drawable?): Drawable? {
        if (drawable == null) return null
        val wrapped = DrawableCompat.wrap(drawable.mutate())
        DrawableCompat.setTint(wrapped, 0xFF49454F.toInt())
        return wrapped
    }

    operator fun get(action: Action): Drawable? {
        return if (action.isEmpty()) null else get(action[0])
    }

    operator fun get(action: SingleAction): Drawable? {

        when (action.id) {
            SingleAction.GO_BACK -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.mWebView.canGoBack())
                    info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_back_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_back_disable_white_24dp, info.themeByInfo))
            }
            SingleAction.GO_FORWARD -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.mWebView.canGoForward())
                    info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_forward_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_forward_disable_white_24dp, info.themeByInfo))
            }
            SingleAction.WEB_RELOAD_STOP -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.isInPageLoad)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_clear_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_refresh_white_24px, info.themeByInfo))
            }
            SingleAction.WEB_RELOAD -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_refresh_white_24px, info.themeByInfo))
            SingleAction.WEB_STOP -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_clear_white_24dp, info.themeByInfo))
            SingleAction.GO_HOME -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_home_white_24dp, info.themeByInfo))
            SingleAction.ZOOM_IN -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_zoom_in_white_24dp, info.themeByInfo))
            SingleAction.ZOOM_OUT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_zoom_out_white_24dp, info.themeByInfo))
            SingleAction.PAGE_UP -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_upward_white_24dp, info.themeByInfo))
            SingleAction.PAGE_DOWN -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_arrow_downward_white_24dp, info.themeByInfo))
            SingleAction.PAGE_TOP -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp, info.themeByInfo))
            SingleAction.PAGE_BOTTOM -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp, info.themeByInfo))
            SingleAction.PAGE_SCROLL -> {
                val id = (action as WebScrollSingleAction).iconResourceId
                return if (id > 0)
                    info.resourcesByInfo.getDrawable(action.iconResourceId, info.themeByInfo)
                else
                    null
            }
            SingleAction.PAGE_FAST_SCROLL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_scroll_white_24dp, info.themeByInfo))
            SingleAction.PAGE_AUTO_SCROLL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_play_arrow_white_24dp, info.themeByInfo))
            SingleAction.FOCUS_UP -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_label_up_white_24px, info.themeByInfo))
            SingleAction.FOCUS_DOWN -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_label_down_white_24px, info.themeByInfo))
            SingleAction.FOCUS_LEFT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_label_left_white_24px, info.themeByInfo))
            SingleAction.FOCUS_RIGHT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_label_right_white_24px, info.themeByInfo))
            SingleAction.FOCUS_CLICK -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_fiber_manual_record_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_JS -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.mWebView.webSettings.javaScriptEnabled)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_memory_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_memory_white_disable_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_IMAGE -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.mWebView.webSettings.loadsImagesAutomatically)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_crop_original_white_24px, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_crop_original_disable_white_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_COOKIE -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.isEnableCookie)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_cookie_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_cookie_disable_24dp, info.themeByInfo))
            }
            SingleAction.TOGGLE_USERJS -> {
                return tintDark(if (info.isEnableUserScript)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_memory_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_memory_white_disable_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_NAV_LOCK -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.isNavLock)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_lock_outline_white_24px, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_lock_open_white_24px, info.themeByInfo))
            }
            SingleAction.PAGE_INFO -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_info_white_24dp, info.themeByInfo))
            SingleAction.COPY_URL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_mode_edit_white_24dp, info.themeByInfo))
            SingleAction.COPY_TITLE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_mode_edit_white_24dp, info.themeByInfo))
            SingleAction.COPY_TITLE_URL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_mode_edit_white_24dp, info.themeByInfo))
            SingleAction.TAB_HISTORY -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_undo_white_24dp, info.themeByInfo))
            SingleAction.MOUSE_POINTER -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_mouse_white_24dp, info.themeByInfo))
            SingleAction.FIND_ON_PAGE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_find_in_page_white_24px, info.themeByInfo))
            SingleAction.SAVE_SCREENSHOT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_photo_white_24dp, info.themeByInfo))
            SingleAction.SHARE_SCREENSHOT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_photo_white_24dp, info.themeByInfo))
            SingleAction.SAVE_PAGE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_save_white_24dp, info.themeByInfo))
            SingleAction.OPEN_URL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_book_white_24dp, info.themeByInfo))
            SingleAction.TRANSLATE_PAGE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_g_translate_white_24px, info.themeByInfo))
            SingleAction.NEW_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_add_box_white_24dp, info.themeByInfo))
            SingleAction.CLOSE_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_minas_box_white_24dp, info.themeByInfo))
            SingleAction.CLOSE_ALL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_minas_box_white_24dp, info.themeByInfo))
            SingleAction.CLOSE_AUTO_SELECT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_minas_box_white_24dp, info.themeByInfo))
            SingleAction.CLOSE_OTHERS -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_minas_box_white_24dp, info.themeByInfo))
            SingleAction.LEFT_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_chevron_left_white_24dp, info.themeByInfo))
            SingleAction.RIGHT_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_chevron_right_white_24dp, info.themeByInfo))
            SingleAction.SWAP_LEFT_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_fast_rewind_white_24dp, info.themeByInfo))
            SingleAction.SWAP_RIGHT_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_fast_forward_white_24dp, info.themeByInfo))
            SingleAction.TAB_LIST -> {
                val base = tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_tab_white_24dp, info.themeByInfo)) ?: return null
                val text = TabListActionTextDrawable(info.applicationContextInfo, info.tabSize)
                return SimpleLayerDrawable(base, text)
            }
            SingleAction.CLOSE_ALL_LEFT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_skip_previous_white_24dp, info.themeByInfo))
            SingleAction.CLOSE_ALL_RIGHT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_skip_next_white_24dp, info.themeByInfo))
            SingleAction.RESTORE_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_redo_white_24dp, info.themeByInfo))
            SingleAction.REPLICATE_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_content_copy_white_24dp, info.themeByInfo))
            SingleAction.SHOW_SEARCHBOX -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_search_white_24dp, info.themeByInfo))
            SingleAction.PASTE_SEARCHBOX -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_content_paste_white_24dp, info.themeByInfo))
            SingleAction.PASTE_GO -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_content_paste_white_24dp, info.themeByInfo))
            SingleAction.SHOW_BOOKMARK -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_collections_bookmark_white_24dp, info.themeByInfo))
            SingleAction.SHOW_HISTORY -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_history_white_24dp, info.themeByInfo))
            SingleAction.SHOW_DOWNLOADS -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_file_download_white_24dp, info.themeByInfo))
            SingleAction.SHOW_SETTINGS -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_settings_white_24dp, info.themeByInfo))
            SingleAction.OPEN_SPEED_DIAL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_speed_dial_white_24dp, info.themeByInfo))
            SingleAction.ADD_BOOKMARK -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (BookmarkManager.getInstance(info.applicationContextInfo).isBookmarked(tab.url))
                    info.resourcesByInfo.getDrawable(R.drawable.ic_star_white_24px, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_star_border_white_24px, info.themeByInfo))
            }
            SingleAction.ADD_SPEED_DIAL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_speed_dial_add_white_24dp, info.themeByInfo))
            SingleAction.ADD_PATTERN -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_pattern_add_white_24dp, info.themeByInfo))
            SingleAction.ADD_TO_HOME -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_add_to_home_white_24dp, info.themeByInfo))
            SingleAction.SUB_GESTURE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_24dp, info.themeByInfo))
            SingleAction.CLEAR_DATA -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_delete_sweep_white_24px, info.themeByInfo))
            SingleAction.SHOW_PROXY_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_import_export_white_24dp, info.themeByInfo))
            SingleAction.ORIENTATION_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_stay_current_portrait_white_24dp, info.themeByInfo))
            SingleAction.OPEN_LINK_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_link_white_24dp, info.themeByInfo))
            SingleAction.USERAGENT_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_group_white_24dp, info.themeByInfo))
            SingleAction.TEXTSIZE_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_format_size_white_24dp, info.themeByInfo))
            SingleAction.USERJS_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_memory_white_24dp, info.themeByInfo))
            SingleAction.WEB_ENCODE_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_format_shapes_white_24dp, info.themeByInfo))
            SingleAction.DEFALUT_USERAGENT_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_group_white_24dp, info.themeByInfo))
            SingleAction.RENDER_SETTING, SingleAction.RENDER_ALL_SETTING -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_blur_linear_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_VISIBLE_TAB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_remove_red_eye_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_VISIBLE_URL -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_remove_red_eye_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_VISIBLE_PROGRESS -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_remove_red_eye_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_VISIBLE_CUSTOM -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_remove_red_eye_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_WEB_TITLEBAR -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_web_asset_white_24dp, info.themeByInfo))
            SingleAction.TOGGLE_WEB_GESTURE -> {
                return tintDark(if (info.isEnableGesture)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_disable_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_FLICK -> {
                return tintDark(if (AppPrefs.flick_enable.get())
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_disable_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_QUICK_CONTROL -> {
                return tintDark(if (info.isEnableQuickControl)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_pie_chart_outlined_white_24px, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_pie_chart_outlined_disable_white_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_MULTI_FINGER_GESTURE -> {
                return tintDark(if (info.isEnableMultiFingerGesture)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_gesture_white_disable_24px, info.themeByInfo))
            }
            SingleAction.TOGGLE_AD_BLOCK -> {
                return tintDark(if (info.isEnableAdBlock)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_ad_block_enable_white_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_ad_block_disable_white_24dp, info.themeByInfo))
            }
            SingleAction.OPEN_BLACK_LIST -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_open_ad_block_black_24dp, info.themeByInfo))
            SingleAction.OPEN_WHITE_LIST -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_open_ad_block_white_24dp, info.themeByInfo))
            SingleAction.OPEN_WHITE_PATE_LIST -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_open_ad_block_white_page_24dp, info.themeByInfo))
            SingleAction.ADD_WHITE_LIST_PAGE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_add_white_page_24dp, info.themeByInfo))
            SingleAction.SHARE_WEB -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_share_white_24dp, info.themeByInfo))
            SingleAction.OPEN_OTHER -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_public_white_24dp, info.themeByInfo))
            SingleAction.START_ACTIVITY -> return tintDark((action as StartActivitySingleAction).getIconDrawable(info.applicationContextInfo))
            SingleAction.TOGGLE_FULL_SCREEN -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_fullscreen_white_24dp, info.themeByInfo))
            SingleAction.OPEN_OPTIONS_MENU -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_more_vert_white_24dp, info.themeByInfo))
            SingleAction.CUSTOM_MENU -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_more_vert_white_24dp, info.themeByInfo))
            SingleAction.FINISH -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_power_settings_white_24dp, info.themeByInfo))
            SingleAction.MINIMIZE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_fullscreen_exit_white_24dp, info.themeByInfo))
            SingleAction.CUSTOM_ACTION -> return get((action as CustomSingleAction).action)
            SingleAction.VIBRATION -> return null
            SingleAction.TOAST -> return null
            SingleAction.PRIVATE -> return tintDark(if (info.isPrivateMode)
                info.resourcesByInfo.getDrawable(R.drawable.ic_private_white_24dp, info.themeByInfo)
            else
                info.resourcesByInfo.getDrawable(R.drawable.ic_private_white_disable_24dp, info.themeByInfo))
            SingleAction.VIEW_SOURCE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_view_source_white_24dp, info.themeByInfo))
            SingleAction.PRINT -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_print_white_24dp, info.themeByInfo))
            SingleAction.TAB_PINNING -> {
                val tab = info.currentTabData ?: return null
                return tintDark(if (tab.isPinning)
                    info.resourcesByInfo.getDrawable(R.drawable.ic_pin_24dp, info.themeByInfo)
                else
                    info.resourcesByInfo.getDrawable(R.drawable.ic_pin_disable_24dp, info.themeByInfo))
            }
            SingleAction.ALL_ACTION -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_list_white_24dp, info.themeByInfo))
            SingleAction.READER_MODE -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_chrome_reader_mode_white_24dp, info.themeByInfo))
            SingleAction.READ_IT_LATER -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_watch_later_white_24dp, info.themeByInfo))
            SingleAction.READ_IT_LATER_LIST -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_read_it_list_white_24px, info.themeByInfo))
            SingleAction.WEB_THEME -> return tintDark(info.resourcesByInfo.getDrawable(R.drawable.ic_baseline_dark_mode_24, info.themeByInfo))
            else -> {
                Toast.makeText(info.applicationContextInfo, "Unknown action:" + action.id, Toast.LENGTH_LONG).show()
                return null
            }

        }
    }
}
