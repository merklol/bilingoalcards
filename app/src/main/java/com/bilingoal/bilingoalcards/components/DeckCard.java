package com.bilingoal.bilingoalcards.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bilingoal.bilingoalcards.R;
import com.bilingoal.bilingoalcards.database.entities.Word;
import com.bilingoal.bilingoalcards.databinding.DeckCardViewBinding;

public class DeckCard extends ConstraintLayout {
    private OnPlayButtonClicked playButtonClicked;
    private final DeckCardViewBinding binding;

    public interface OnPlayButtonClicked {
        void onClicked();
    }

    public DeckCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DeckCardViewBinding.inflate(inflater, this);
    }

    public void setOnPlayButtonClicked(OnPlayButtonClicked playButtonClicked) {
        this.playButtonClicked = playButtonClicked;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        float scale = getResources().getDisplayMetrics().density;
        float distance = binding.frontCardView.getCameraDistance() * (scale + (scale / 3));
        binding.frontCardView.setCameraDistance(distance);
        binding.backCardView.setCameraDistance(distance);

        binding.frontCardView.setOnClickListener(flipCardListener);
        binding.backCardView.setOnClickListener(flipCardListener);
        binding.soundBtn.setOnClickListener(v -> playButtonClicked.onClicked());
    }

    private void setFrontText(String text) {
        binding.frontTextView.setText(text);
    }

    private void setBackText(String text) {
        binding.backTextView.setText(text);
    }

    private void setPinyin(String text) {
        binding.frontPinyinView.setText(text);
    }

    private final OnClickListener flipCardListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.front_card_view) {
                binding.backCardView.setRotationY(0);
                binding.frontCardView.animate().withLayer().rotationY(90).setDuration(300).withEndAction(
                        () -> {
                            binding.backCardView.setVisibility(VISIBLE);
                            binding.frontCardView.setVisibility(GONE);
                            binding.frontCardView.setAlpha(0f);
                            binding.backCardView.animate().alpha(1f).setDuration(50).start();
                        }).start();
            } else {
                binding.frontCardView.setRotationY(0);
                binding.backCardView.animate().withLayer().rotationY(90).setDuration(300).withEndAction(
                        () -> {
                            binding.backCardView.setVisibility(GONE);
                            binding.frontCardView.setVisibility(VISIBLE);
                            binding.backCardView.setAlpha(0f);
                            binding.frontCardView.animate().alpha(1f).setDuration(50).start();
                        }).start();
            }
        }
    };

    public void invalidate() {
        binding.frontCardView.setRotationY(0);
        binding.frontCardView.setAlpha(1f);
        binding.backCardView.setVisibility(GONE);
        binding.frontCardView.setVisibility(VISIBLE);
    }

    public void updateCard(Word word) {
        setFrontText(word.getCh());
        setBackText(word.getEng());
        setPinyin(word.getPinyin());
        invalidate();
    }
}


