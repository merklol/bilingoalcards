package com.bilingoal.bilingoalcards.results;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.bilingoal.bilingoalcards.models.LessonResults;
import com.bilingoal.bilingoalcards.models.PieChartStyleHelper;
import com.bilingoal.bilingoalcards.R;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.bilingoal.bilingoalcards.databinding.FragmentResultBinding;
import com.bilingoal.bilingoalcards.utils.PlayerUtil;
import com.github.mikephil.charting.data.*;

public class ResultFragment extends Fragment implements ResultsContract.View {
    private FragmentResultBinding binding;
    private ResultsPresenter presenter;

    public ResultFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new ResultsPresenter(BilingoalDatabase.getInstance(requireContext()));
        presenter.subscribe(this);

        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.displayLessonResults();

        binding.continueBtn.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_resultFragment_to_mainFragment);
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void displayPieChartView(PieDataSet pieDataSet, LessonResults lessonResults) {
        PieChartStyleHelper styleHelper = PieChartStyleHelper.create(
                requireContext(),binding.pieChartView, pieDataSet
        );
        styleHelper.setCenterText(lessonResults.getPercentageOfCorrectAnswers());
        PlayerUtil.playAssetSound(requireContext(), "tada", .5f);
    }
}