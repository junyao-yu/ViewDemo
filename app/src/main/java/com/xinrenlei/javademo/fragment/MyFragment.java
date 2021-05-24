package com.xinrenlei.javademo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xinrenlei.javademo.R;

/**
 * Auth：yujunyao
 * Since: 2020/9/18 11:15 AM
 * Email：yujunyao@xinrenlei.net
 */

public class MyFragment extends Fragment {

    private int type;

    public static MyFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MyFragment myFragment =  new MyFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        type = getArguments().getInt("type");
        System.out.println("onAttach" + type);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate" + type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("onCreateView" + type);
        View view = inflater.inflate(R.layout.fragment_test, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText("" + type);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated" + type);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart" + type);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume" + type);
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause" + type);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop" + type);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView" + type);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy" + type);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("onDetach" + type);
    }

}
