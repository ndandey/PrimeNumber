

package com.ndandey.android.primenumber.fragments;

import java.util.List;

import com.ndandey.android.primenumber.R;
import com.ndandey.android.primenumber.adapters.EndlessRecyclerViewScrollListener;
import com.ndandey.android.primenumber.adapters.PrimeNumberListAdapter;
import com.ndandey.android.primenumber.controllers.PrimeNumberController;
import com.ndandey.android.primenumber.controllers.PrimeNumberEventHandler;
import com.ndandey.android.primenumber.utils.GenericUtility;

import android.content.Context;
import android.os.Bundle;
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
    private Context mContext;
    private RecyclerView recyclerView;
    private PrimeNumberListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PrimeNumberController mPrimeNumberController;

    public PrimeNumberListFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(Boolean.TRUE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mContext = getActivity();
        View vReturnView = inflater.inflate(R.layout.fragment_prime_number, container, false);
        if (mPrimeNumberController == null)
        {
            mPrimeNumberController = new PrimeNumberController(1, 100, mContext);
            mPrimeNumberController.getPrimeNumbers();
            mPrimeNumberController.mPrimeNumberEventHandler = this;
        }
        recyclerView = (RecyclerView) vReturnView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(mContext, GenericUtility.calculateNoOfColumns(mContext, 80));
        recyclerView.setLayoutManager(mLayoutManager);
        if (mAdapter != null)
        {
            recyclerView.setAdapter(mAdapter);
        }
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
        return vReturnView;
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
