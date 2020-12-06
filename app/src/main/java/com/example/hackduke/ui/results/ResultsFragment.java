package com.example.hackduke.ui.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hackduke.MainActivity;
import com.example.hackduke.R;

public class ResultsFragment extends Fragment implements View.OnClickListener{

    private ResultsViewModel resultsViewModel;
    private Button cam_button;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultsViewModel =
                new ViewModelProvider(this).get(ResultsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        TextView foodName = root.findViewById(R.id.foodName);
        foodName.setText("Food Name");
        TextView foodGroup= root.findViewById(R.id.foodGroup);
        foodName.setText("Food Group");
        TextView score = root.findViewById(R.id.score);
        foodName.setText("score");
        TextView  rec = root.findViewById(R.id.Recommendation);
        foodName.setText("Recommendation");
        ImageView img = root.findViewById(R.id.imageView2);
        cam_button = (Button) root.findViewById(R.id.backToCam);
        cam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    public void openCamera() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}
