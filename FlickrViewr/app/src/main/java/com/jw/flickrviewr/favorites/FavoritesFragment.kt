package com.jw.flickrviewr.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.GridLayoutManager
import com.jw.flickrviewr.R
import com.jw.flickrviewr.adapter.PhotoAdapter
import com.jw.flickrviewr.data.Photo
import com.jw.flickrviewr.util.GridDecoration
import com.jw.flickrviewr.util.Injector
import com.jw.flickrviewr.util.afterMeasure
import com.jw.flickrviewr.util.enableBackButton
import kotlinx.android.synthetic.main.fragment_favorites.*

/**
 * UI for the Favorites screen to see saved photos.
 */
class FavoritesFragment : Fragment() {

    private val TAG by lazy { FavoritesFragment::class.java.simpleName }

    private val viewModel: FavoritesViewModel by viewModels {
        Injector.provideFavoritesViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        enableBackButton(false)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.savedPhotos.observe(this, Observer<List<Photo>> {
            if (it.isNotEmpty()) {
                (recycler_view.adapter as PhotoAdapter).loadNewData(it)
                information_text.visibility = View.GONE
            } else {
                information_text.visibility = View.VISIBLE
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            addItemDecoration(GridDecoration(context))
            layoutManager = GridLayoutManager(context, 2)
            adapter = PhotoAdapter(
                emptyList() // Add an empty list as a parameter for initialization.
            )

            // OnClickListener for grid items.
            { view, photo ->
                findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesFragmentToPhotoFragment(
                        photo.url(), photo.id
                    ), FragmentNavigator.Extras.Builder().addSharedElement(
                        view, resources.getString(R.string.photo_transition_name)
                    ).build()
                )
            }
            // Postpone enter transition to support return animation of shared element.
            postponeEnterTransition()
            afterMeasure {
                // Start postponed enter transition after layout is fully populated.
                startPostponedEnterTransition()
            }
        }
    }

}