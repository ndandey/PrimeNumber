

package com.ndandey.android.primenumber.fragments;

import java.util.List;

import com.ndandey.android.primenumber.R;
import com.ndandey.android.primenumber.adapters.EndlessRecyclerViewScrollListener;
import com.ndandey.android.primenumber.adapters.PrimeNumberListAdapter;
import com.ndandey.android.primenumber.controllers.PrimeNumberController;
import com.ndandey.android.primenumber.controllers.PrimeNumberEventHandler;
import com.ndandey.android.primenumber.utils.GenericUtility;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrimeNumberListFragment extends Fragment implements PrimeNumberEventHandler
{

    private static final String LIST_STATE_KEY = "LIST_STATE_KEY";
    Context mContext;
    private RecyclerView recyclerView;
    private PrimeNumberListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PrimeNumberController mPrimeNumberController;
    private Parcelable mListState;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener mScrollListener;

    public PrimeNumberListFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // setRetainInstance(Boolean.TRUE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View vReturnView = inflater.inflate(R.layout.fragment_prime_number, container, false);
        mContext = getActivity();
        if (mPrimeNumberController == null)
        {
            mPrimeNumberController = new PrimeNumberController(1, 100, mContext);
            mPrimeNumberController.getPrimeNumbers();
            mPrimeNumberController.mPrimeNumberEventHandler = this;
        }
        recyclerView = (RecyclerView) vReturnView.findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        if (mLayoutManager == null)
        {
            mLayoutManager = new GridLayoutManager(mContext, GenericUtility.calculateNoOfColumns(mContext, 95));
            recyclerView.setLayoutManager(mLayoutManager);
        }

        // recyclerView.addItemDecoration(new DividerItemDecoration(mContext,
        // DividerItemDecoration.HORIZONTAL));
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) mLayoutManager)
        {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
            {

                GenericUtility.showHideProgress(Boolean.TRUE, mContext);
                // get next batch of Prime Numbers
                mPrimeNumberController.getNextPrimeNumbers();
            }
        });
        if (mAdapter != null)
        {
            recyclerView.setAdapter(mAdapter);
        }
        return vReturnView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void showPrimeNumbers(List<String> pPrimeNumbersList)
    {
        GenericUtility.showHideProgress(Boolean.FALSE, mContext);
        if (mAdapter == null)
        {
            mAdapter = new PrimeNumberListAdapter(pPrimeNumbersList);
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.appendMoreData(pPrimeNumbersList);
        }
    }
}
