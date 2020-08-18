package com.bilingoal.bilingoalcards.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bilingoal.bilingoalcards.R;
import com.bilingoal.bilingoalcards.database.entities.Lesson;

import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {
    private final List<Lesson> lessonList;
    private final int layoutRes;
    private OnLessonCLickListener onLessonCLickListener;

    public LessonListAdapter(List<Lesson> lessonList, int layoutRes) {
        this.lessonList = lessonList;
        this.layoutRes = layoutRes;
    }

    public interface OnLessonCLickListener {
        void onClick(int selectedLessonId);
    }

    public void setOnLessonCLickListener(OnLessonCLickListener onLessonCLickListener) {
        this.onLessonCLickListener = onLessonCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonListAdapter.ViewHolder holder, int position) {
        holder.bind(lessonList.get(position));
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleView;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
            progressBar = itemView.findViewById(R.id.progress_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Lesson lesson) {
            titleView.setText(lesson.getLessonTitle());
            if(progressBar != null) progressBar.setProgress(lesson.getLessonProgress());
        }

        @Override
        public void onClick(View v) {
            onLessonCLickListener.onClick(lessonList.get(getAdapterPosition()).getId());
        }
    }
}
