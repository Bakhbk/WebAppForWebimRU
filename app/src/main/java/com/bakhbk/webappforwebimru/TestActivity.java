package com.bakhbk.webappforwebimru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public class TestActivity extends AppCompatActivity {

    private static final int[] IDS = {R.id.users_get, R.id.friends_get};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_test, container, false);
            for (int id : IDS) {
                view.findViewById(id).setOnClickListener(this);
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.users_get: {
                    VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                            "id,first_name,last_name"));
                    request.secure = false;
                    request.useSystemLanguage = false;
                    startApiCall(request);
                }
                break;
                case R.id.friends_get:
                    startApiCall(VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name", VKApiConst.COUNT, 5)));
                    break;
            }
        }

        private void startApiCall(VKRequest request)  {


            Intent i = new Intent(getActivity(), ApiCallActivity.class);
            i.putExtra("request", request.registerObject());
            startActivity(i);
        }
    }
}