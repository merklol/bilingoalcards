package com.bilingoal.bilingoalcards.models;

import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import com.bilingoal.bilingoalcards.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

public class PieChartStyleHelper {
    private final PieChart pieChart;
    private final PieDataSet pieDataSet;
    private PieData pieData;
    private final Context context;

    private PieChartStyleHelper(Context context, PieChart pieChart, PieDataSet pieDataSet) {
        this.context = context;
        this.pieChart = pieChart;
        this.pieDataSet = pieDataSet;
    }

    public void setCenterText(int value) {
        pieChart.setCenterText(value + "%");
    }

    private void stylePieChart() {
        pieChart.animateXY(1000, 1000);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setCenterTextSize(32f);
        pieChart.setData(pieData);
    }

    private void stylePieDataSet() {
        pieData  = new PieData(pieDataSet);

        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.WHITE);
        int[] colors = new int[] {
                ContextCompat.getColor(context, R.color.pieDataSetColor1),
                ContextCompat.getColor(context, R.color.pieDataSetColor2)
        };

        pieDataSet.setColors(colors);
    }

    public static PieChartStyleHelper create(Context context, PieChart pieChart, PieDataSet pieDataSet) {
        PieChartStyleHelper styleHelper = new PieChartStyleHelper(context, pieChart, pieDataSet);
        styleHelper.stylePieDataSet();
        styleHelper.stylePieChart();
        return styleHelper;
    }
}
