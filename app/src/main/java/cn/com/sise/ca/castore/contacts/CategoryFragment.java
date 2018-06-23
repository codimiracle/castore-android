package cn.com.sise.ca.castore.contacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.com.sise.ca.castore.R;

public class CategoryFragment extends Fragment{
    public static CategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private ListView categoryList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.category_fragment_view, container, false);
        categoryList = view.findViewById(R.id.category_list);
        return view;
    }
}
