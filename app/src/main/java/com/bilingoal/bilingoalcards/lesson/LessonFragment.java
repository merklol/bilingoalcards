package com.bilingoal.bilingoalcards.lesson;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.bilingoal.bilingoalcards.*;
import com.bilingoal.bilingoalcards.components.BilingoalDialog;
import com.bilingoal.bilingoalcards.database.BilingoalDatabase;
import com.bilingoal.bilingoalcards.database.entities.Word;
import com.bilingoal.bilingoalcards.databinding.FragmentLessonBinding;
import com.bilingoal.bilingoalcards.utils.PlayerUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.*;

public class LessonFragment extends Fragment implements LessonContract.View {
    private FragmentLessonBinding binding;
    private LessonPresenter presenter;

    public LessonFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BilingoalDatabase database = BilingoalDatabase.getInstance(requireContext());
        presenter = new LessonPresenter(database);
        binding = FragmentLessonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.dialogView.setOnContinueButtonClickListener(listener);
        setOnChoiceClickListener();

        binding.deckCard.setOnPlayButtonClicked(() ->
                PlayerUtil.playAssetSound(requireContext(), presenter.getSoundToPlay(),.8f));

        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), onBackPressedCallback);

        presenter.initLesson();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void setDefaultChoices(List<Word> words) {
        binding.choice.setText(words.get(0).getEng());
        binding.choice2.setText(words.get(1).getEng());
        binding.choice3.setText(words.get(2).getEng());
    }

    @Override
    public void setFirstWord(Word word) {
        binding.deckCard.updateCard(word);
        binding.deckCard.updateCard(word);
    }

    @Override
    public void displayDialog(Map<String, String> dialogMessage) {
        binding.dialogView.setMessage(dialogMessage);
        binding.dialogView.display();
    }

    @Override
    public void displayNextChoices(List<String> choices) {
        binding.choice.setText(choices.get(0));
        binding.choice2.setText(choices.get(1));
        binding.choice3.setText(choices.get(2));
    }

    @Override
    public void showResults() {
        NavHostFragment.findNavController(this).navigate(R.id.action_lessonFragment_to_resultFragment);
    }

    @Override
    public void updateDeckCard(Word word) {
        binding.deckCard.updateCard(word);
    }

    @Override
    public void playSound(boolean isCorrectAnswer) {
        final String sound = isCorrectAnswer ? "correct_answer" : "wrong_answer";
        PlayerUtil.playAssetSound(requireContext(), sound, .5f);
    }

    @Override
    public void setLessonTitle(String str) {
        Objects.requireNonNull(((MainActivity) requireActivity())
                .getSupportActionBar()).setTitle(str);
    }

    private final View.OnClickListener onClickListener = v -> {
        disableChoiceButtons();
        presenter.nextDialog(((MaterialButton)v).getText().toString().toLowerCase());
    };

    private final BilingoalDialog.OnContinueButtonClickListener listener = () -> {
        enableChoiceButtons();
        presenter.nextWord();
    };

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.alert_dialog_message)
                    .setPositiveButton(R.string.alert_dialog_positive_btn, (dialog, which) -> {
                        NavHostFragment.findNavController(LessonFragment.this)
                                .navigateUp();
                    })
                    .setNegativeButton(R.string.alert_dialog_negative_btn, null)
                    .show();
        }
    };

    private void setOnChoiceClickListener() {
        binding.choice.setOnClickListener(onClickListener);
        binding.choice2.setOnClickListener(onClickListener);
        binding.choice3.setOnClickListener(onClickListener);
    }

    private void disableChoiceButtons() {
        binding.choice.setEnabled(false);
        binding.choice2.setEnabled(false);
        binding.choice3.setEnabled(false);
    }

    private void enableChoiceButtons() {
        binding.choice.setEnabled(true);
        binding.choice2.setEnabled(true);
        binding.choice3.setEnabled(true);
    }
}