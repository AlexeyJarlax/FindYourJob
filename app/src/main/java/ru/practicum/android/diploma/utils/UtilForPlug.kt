package ru.practicum.android.diploma.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.utils.StringConstants.EMPTY_FAVORITES
import ru.practicum.android.diploma.utils.StringConstants.NO_INTERNET
import ru.practicum.android.diploma.utils.StringConstants.NO_REGION
import ru.practicum.android.diploma.utils.StringConstants.NO_RESULTS_CARPET
import ru.practicum.android.diploma.utils.StringConstants.NO_RESULTS_CAT
import ru.practicum.android.diploma.utils.StringConstants.SEARCH
import ru.practicum.android.diploma.utils.StringConstants.SERVER_ERROR_CAT
import ru.practicum.android.diploma.utils.StringConstants.SERVER_ERROR_TOWEL

/** заглушки на ошибки или остутствии результата поиска
 *
 * пример вызова из фрагмента:
 * showPlug(requireContext(), NO_INTERNET)
 *
 * в макете:
 *     <include
 *         android:id="@+id/util_plug"
 *         layout="@layout/util_plug"
 *         android:visibility="gone" />
 * */

object UtilForPlug {

    val plugOptions = listOf(
        SEARCH,
        NO_INTERNET,
        NO_RESULTS_CAT,
        NO_REGION,
        NO_RESULTS_CARPET,
        SERVER_ERROR_TOWEL,
        SERVER_ERROR_CAT,
        EMPTY_FAVORITES
    )
}

fun Fragment.showPlug(
    context: Context,
    problemTip: String
) {
    val utilPlugBox = view?.findViewById<LinearLayout>(R.id.util_plug)
    val plugText = view?.findViewById<TextView>(R.id.plug_text)
    val plugIcon = view?.findViewById<ImageView>(R.id.plug_icon)
    utilPlugBox?.visibility = View.VISIBLE

    when (problemTip) {
        SEARCH -> showSearchPlug(plugText, plugIcon)
        NO_INTERNET -> showNoInternetPlug(context, plugText, plugIcon)
        NO_RESULTS_CAT -> showNoResultsCatPlug(context, plugText, plugIcon)
        NO_REGION -> showNoRegionPlug(context, plugText, plugIcon)
        NO_RESULTS_CARPET -> showNoResultsCarpetPlug(context, plugText, plugIcon)
        SERVER_ERROR_TOWEL -> showServerErrorTowelPlug(context, plugText, plugIcon)
        SERVER_ERROR_CAT -> showServerErrorCatPlug(context, plugText, plugIcon)
        EMPTY_FAVORITES -> showEmptyFavoritesPlug(context, plugText, plugIcon)
        else -> utilPlugBox?.visibility = View.GONE
    }
}

private fun showSearchPlug(plugText: TextView?, plugIcon: ImageView?) {
    plugText?.visibility = View.GONE
    plugIcon?.setImageResource(R.drawable.placeholder_search)
}

private fun showNoInternetPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_no_internet)
    plugIcon?.setImageResource(R.drawable.placeholder_no_internet)
}

private fun showNoResultsCatPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_no_results)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
}

private fun showNoRegionPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.region_no_region)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
}

private fun showNoResultsCarpetPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.region_error)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_carpet)
}

private fun showServerErrorTowelPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_server_error)
    plugIcon?.setImageResource(R.drawable.placeholder_server_error)
}

private fun showServerErrorCatPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.vacancy_server_error)
    plugIcon?.setImageResource(R.drawable.placeholder_server_error_cat)
}

private fun showEmptyFavoritesPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.favorites_empty)
    plugIcon?.setImageResource(R.drawable.placeholder_empty_favorites)
}
