package com.infowebmentsolution.ghosh.clickforflick.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infowebmentsolution.ghosh.clickforflick.Activity.MoviePlayActivity;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.SearchAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.SearchRecyclerAdapter;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchList;
import com.infowebmentsolution.ghosh.clickforflick.Model.SearchModel;
import com.infowebmentsolution.ghosh.clickforflick.Model.SharedViewModel;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Response.SearchResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchRecyclerAdapter.ClickedOnItemListener{//,IOnBackPressed{

    String API_Url = "https://clickforflick.com//Api/search";
    /* renamed from: i */
    int i = 0;
    private SharedViewModel viewModel;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<SearchModel> searchItemList;
    SearchRecyclerAdapter searchRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.Recyle_Search);
        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);


        searchItemList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        searchRecyclerAdapter = new SearchRecyclerAdapter(this::ClickedItem);
        searchRecyclerAdapter.setData( searchItemList);
        recyclerView.setAdapter(searchRecyclerAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {


                clean();

                i++;
                if(i == 1){
                    search((String) charSequence);
                }

                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }

    private void search(String keyword){

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, API_Url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("result").equals("error")){
                        Toast.makeText(getActivity(),"Data Not Found",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        i=0;


                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    progressBar.setVisibility(View.GONE);
                    i=0;
                    for(int i=0;i<jsonArray.length();i++){


                        JSONObject data = jsonArray.getJSONObject(i);

                        SearchModel searchModel = new SearchModel();
                        searchModel.setTitle(data.getString("title"));
                        searchModel.setEpisode("Ep: "+data.getString("episode"));
                        searchModel.setLang("◉ "+data.getString("lang"));
                        searchModel.setThrumbnail("https://clickforflick.com//uploads/thrumbnail/"+data.getString("thrumbnail"));
                        searchModel.setTrailername(data.getString("trailername"));
                        searchModel.setVideoname(data.getString("videoname"));
                        searchModel.setUploadid(data.getString("uploadid"));
                        searchModel.setIs_free(data.getString("is_free"));
                        searchModel.setCname(data.getString("cname"));
                        searchItemList.add(searchModel);
                        searchRecyclerAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }
        };

        queue.add(request);
    }
    public void clean(){

        searchItemList.clear();

        searchRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void ClickedItem(SearchModel searchModelList) {
        Intent intent = new Intent(getActivity(), MoviePlayActivity.class);
        intent.putExtra("tUrl", searchModelList.getTrailername());
        intent.putExtra("url", searchModelList.getVideoname());
        intent.putExtra("vId", searchModelList.getUploadid());
        intent.putExtra("vname", searchModelList.getTitle());
        intent.putExtra("vdec", searchModelList.getDescription());
        intent.putExtra("isFree", searchModelList.getIs_free());
        intent.putExtra("cname", searchModelList.getCname());
        intent.putExtra("W.T.", "no");
        startActivity(intent);

    }
}

