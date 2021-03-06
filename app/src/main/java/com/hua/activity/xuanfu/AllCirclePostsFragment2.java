package com.hua.activity.xuanfu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hua.R;
import com.hua.constant.EventBusConstant;
import com.hua.view.RefleshListView;

import org.simple.eventbus.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllCirclePostsFragment2 extends ListViewFragment {

    public static final String TAG = AllCirclePostsFragment2.class.getSimpleName();

    public static Fragment newInstance(int position) {
        AllCirclePostsFragment2 fragment = new AllCirclePostsFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public AllCirclePostsFragment2() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPosition = getArguments().getInt(ARG_POSITION);

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (RefleshListView) view.findViewById(R.id.listview);
        View placeHolderView = inflater.inflate(R.layout.header_placeholder, mListView, false);
        mListView.addHeaderView(placeHolderView);


        setAdapter();
        setListViewOnScrollListener();

        return view;
    }

    private void setAdapter() {
        if (getActivity() == null) return;

        int size = 27;
        String[] stringArray = new String[size];
        for (int i = 0; i < size; ++i) {
            stringArray[i] = ""+i;
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);

        mListView.setAdapter(adapter);
    }

    @Subscriber(tag = EventBusConstant.TEST)
    void test(int test){

        mListView.scrollTo(0,test);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            mListView.scrollTo(0,200);
        }
    }
}
