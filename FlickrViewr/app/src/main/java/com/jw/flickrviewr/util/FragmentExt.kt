package com.jw.flickrviewr.util

import androidx.fragment.app.Fragment
import com.jw.flickrviewr.MainActivity


/**
 * Extension functions for Fragment class.
 */
fun Fragment.enableBackButton(enabled: Boolean) =
    (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(enabled);