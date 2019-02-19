package com.dryseed.module_widget.viewpager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dryseed.module_widget.R;

@SuppressLint("ValidFragment")
public class BaseFragment extends Fragment {

    private static final String TAG = "MMM";

    private int mPosition = 0;

    public BaseFragment(int index) {
        mPosition = index;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "BaseFragment " + mPosition + " :  onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "BaseFragment " + mPosition + " :  onCreate");
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt("index", 0);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onCreateView");
        final LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.view_holder_channel_item, container, false);
        TextView textView = rootView.findViewById(R.id.title);
        textView.setText(mPosition + "");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onPause");
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "BaseFragment " + mPosition + " :  onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, "BaseFragment " + mPosition + " :  setUserVisibleHint : " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }
}
