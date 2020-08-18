package com.bilingoal.bilingoalcards.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bilingoal.bilingoalcards.databinding.BilingoalDialogBinding;

import java.util.Map;

public class BilingoalDialog extends ConstraintLayout {
    private BilingoalDialogBinding binding;
    private OnContinueButtonClickListener listener;
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    public interface OnContinueButtonClickListener {
        void onClick();
    }

    public void setOnContinueButtonClickListener(OnContinueButtonClickListener listener) {
        this.listener = listener;
    }

    public BilingoalDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = BilingoalDialogBinding.inflate(inflater, this);
    }

    public void setMessage(Map<String, String> message) {
        binding.bilingoalDialogTitleView.setText(message.get("title"));
        binding.bilingoalDialogMessageView.setText(message.get("message"));
    }

    public void display() {
        binding.bilingoalDialogRoot.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .start();
    }

    private void hide() {
        binding.bilingoalDialogRoot.animate()
                .translationY(1000f)
                .alpha(0f)
                .setDuration(500)
                .start();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        binding.bilingoalDialogRoot.setAlpha(0f);
        binding.bilingoalDialogRoot.setTranslationY(1000f);
        binding.bilingoalDialogContinueBtn.setOnClickListener(v -> {
            hide();
            listener.onClick();
        });
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        binding = null;
    }
}
