package com.jw.flickrviewr.gallery

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jw.flickrviewr.R
import com.jw.flickrviewr.adapter.PhotoAdapter
import com.jw.flickrviewr.data.Photo
import com.jw.flickrviewr.util.*
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * UI for the main Gallery screen.
 */
class GalleryFragment : Fragment() {

    private val TAG by lazy { GalleryFragment::class.java.simpleName }

    private var reachedBottom: Boolean = false

    private lateinit var snackbar: Snackbar

    private val args: GalleryFragmentArgs by navArgs()

    private val viewModel: GalleryViewModel by viewModels { Injector.provideGalleryViewModelFactory(args.tag) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        initGallerySnackBar()

        viewModel.photos.observe(this, Observer<List<Photo>> {
            if (it.isNotEmpty()) {
                (recycler_view.adapter as PhotoAdapter).loadNewData(it)
                information_text.visibility = View.GONE
            } else {
                information_text.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_gallery, menu)

    override fun onPause() {
        if (snackbar.isShownOrQueued) {
            snackbar.dismiss()
            reachedBottom = false
        }
        super.onPause()
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
                    GalleryFragmentDirections.actionGalleryFragmentToPhotoFragment(
                        photo.url(), photo.id
                    ), FragmentNavigator.Extras.Builder().addSharedElement(
                        view, resources.getString(R.string.photo_transition_name)
                    ).build()
                )
            }

            setRecyclerViewListeners(
                { _, _, dy -> // onScrolled
                    if (dy < 0) { // Hide the snackbar when scrolling down.
                        if (snackbar.isShownOrQueued)
                            snackbar.dismiss()

                        reachedBottom = false
                    }
                },
                { recyclerView, _ -> //onScrollStateChanged
                    // Check if RecyclerView is at the very bottom.
                    if (!recyclerView.canScrollVertically(View.SCROLL_AXIS_VERTICAL) and !reachedBottom) {
                        reachedBottom = true

                        if (recyclerView.isNotEmpty())
                            snackbar.show()
                    }
                }
            )

            // Postpone enter transition to support return animation of shared element.
            postponeEnterTransition()
            afterMeasure {
                // Start postponed enter transition after layout is fully populated.
                startPostponedEnterTransition()
            }
        }
    }

    private fun initGallerySnackBar() {
        snackbar = Snackbar.make(requireActivity().findViewById(R.id.main_container),
            resources.getString(R.string.gallery_snackbar_load_txt), Snackbar.LENGTH_INDEFINITE).let {
                it.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grid_view_title_bg))

                val layoutParams = it.view.layoutParams as CoordinatorLayout.LayoutParams
                layoutParams.anchorId = R.id.nav_view
                layoutParams.anchorGravity = Gravity.TOP
                layoutParams.gravity = Gravity.TOP
                it.view.layoutParams = layoutParams

                it.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.gallery_snackbar_action_color))
                it.setAction(resources.getString(R.string.gallery_snackbar_load_action)) {
                    when {
                        NetworkUtil.isInternetConnected(requireContext()) -> {
                            viewModel.loadMorePhotos()
                            reachedBottom = false
                        } else -> {
                            Toast.makeText(
                                    requireContext(),
                                    resources.getString(R.string.internet_not_connected_msg),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

}