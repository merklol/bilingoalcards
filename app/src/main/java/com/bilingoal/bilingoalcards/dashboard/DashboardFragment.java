package com.bilingoal.bilingoalcards.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import com.bilingoal.bilingoalcards.R;
import com.bilingoal.bilingoalcards.models.StateManager;
import com.bilingoal.bilingoalcards.adapters.LessonListAdapter;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.bilingoal.bilingoalcards.database.entities.Lesson;
import com.bilingoal.bilingoalcards.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends Fragment implements DashboardContract.View {
    private DashboardPresenter presenter;
    private FragmentDashboardBinding binding;
    private Context context;

    public DashboardFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BilingoalDatabase database = BilingoalDatabase.getInstance(requireContext());
        context = requireContext();
        presenter = new DashboardPresenter(database);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        presenter.loadLessons();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(this);
        context = requireContext();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings) {
            Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_settingsFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayLastCreatedLessons(List<Lesson> lessons) {
        LessonListAdapter lessonListAdapter = new LessonListAdapter(lessons, R.layout.item_view2);

        binding.newestLessonsListView.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false));

        binding.newestLessonsListView.setAdapter(lessonListAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.newestLessonsListView);

        lessonListAdapter.setOnLessonCLickListener(onLessonClickListener);
    }

    @Override
    public void displayAllLessons(List<Lesson> lessons) {
        LessonListAdapter lessonListAdapter = new LessonListAdapter(lessons, R.layout.item_view);
        binding.allLessonsListView.setLayoutManager(new LinearLayoutManager(context));
        binding.allLessonsListView.setAdapter(lessonListAdapter);

        lessonListAdapter.setOnLessonCLickListener(onLessonClickListener);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            requireActivity().finish();
        }
    };

    private final LessonListAdapter.OnLessonCLickListener onLessonClickListener = selected -> {
        StateManager.getInstance().setLessonId(selected);
        NavHostFragment.findNavController(DashboardFragment.this)
                .navigate(R.id.action_mainFragment_to_lessonFragment);
    };
}