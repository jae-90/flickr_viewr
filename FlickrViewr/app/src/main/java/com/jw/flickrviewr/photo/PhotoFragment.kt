package com.jw.flickrviewr.photo

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jw.flickrviewr.R
import com.jw.flickrviewr.data.Result
import com.jw.flickrviewr.util.Injector
import com.jw.flickrviewr.util.enableBackButton
import kotlinx.android.synthetic.main.fragment_photo.*
import java.io.File

/**
 * UI for the enlarged Photo screen.
 */
class PhotoFragment : Fragment() {

    private val TAG by lazy { PhotoFragment::class.java.simpleName }

    private val args: PhotoFragmentArgs by navArgs()

    private val viewModel: PhotoViewModel by viewModels {
        Injector.providePhotoViewModelFactory(requireActivity().application, args.url, args.id)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        enableBackButton(true)

        // Enable shared element transitions.
        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage(viewModel.url, photo_view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // Check if the photo is from local storage and remove the option to save image.
        if (!File(viewModel.url).exists())
            inflater.inflate(R.menu.menu_photo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            var msg: String

            // Get bitmap from photo_view.
            val bitmap = (photo_view.drawable as BitmapDrawable).bitmap
            viewModel.saveImage(bitmap, viewModel.id).let {
                msg = if (it is Result.Success)
                    resources.getString(R.string.favorites_saved_txt)
                else
                    resources.getString(R.string.favorites_already_saved_txt)
            }
            Snackbar.make(requireActivity().findViewById(R.id.main_container), msg, Snackbar.LENGTH_SHORT).show()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        enableBackButton(false)
        super.onPause()
    }

    private fun loadImage(url: String, view: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .into(view)
    }

}