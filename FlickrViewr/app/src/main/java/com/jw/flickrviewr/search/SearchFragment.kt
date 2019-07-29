package com.jw.flickrviewr.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.CursorAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jw.flickrviewr.R
import com.jw.flickrviewr.util.Const.TERM_COLUMN
import com.jw.flickrviewr.util.Injector
import com.jw.flickrviewr.util.NetworkUtil
import com.jw.flickrviewr.util.enableBackButton

/**
 * UI for Search screen.
 */
class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView

    private lateinit var cursorAdapter: SimpleCursorAdapter

    private val viewModel: SearchViewModel by viewModels {
        Injector.provideSearchViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        setupCursorAdapter()

        enableBackButton(true)
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = (menu.findItem(R.id.app_bar_search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            isIconified = false
            suggestionsAdapter = cursorAdapter

            populateRecentSearchTerms()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String?): Boolean { return true }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (!NetworkUtil.isInternetConnected(requireContext())){
                            Snackbar.make(requireActivity().findViewById(R.id.main_container),
                                    resources.getString(R.string.internet_not_connected_msg), Snackbar.LENGTH_SHORT).show()
                            return@let
                        }
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToGalleryFragment(
                                query.toString()
                            )
                        findNavController().navigate(action)
                        viewModel.addToRecentSearchList(query)
                    }
                    return true
                }
            })

            setOnSuggestionListener(object: SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return true
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val cursor = cursorAdapter.getItem(position) as Cursor
                    val txt = cursor.getString(cursor.getColumnIndex(TERM_COLUMN))
                    setQuery(txt, true)
                    return true
                }
            })
            setOnCloseListener {
                findNavController().navigateUp()
                true
            }
        }
    }

    override fun onPause() {
        searchView.clearFocus()
        enableBackButton(false)
        super.onPause()
    }

    private fun setupCursorAdapter() {
        cursorAdapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            null,
            arrayOf(TERM_COLUMN),
            intArrayOf(android.R.id.text1),
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
    }

    private fun populateRecentSearchTerms() {
        val terms = viewModel.terms
        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, TERM_COLUMN))

        // List out the recent search terms in reverse order.
        var last = terms.size - 1
        for (i in 0 until terms.size) {
            cursor.addRow(arrayOf(i, terms[last].term))
            last--
        }

        cursorAdapter.changeCursor(cursor)
    }

}