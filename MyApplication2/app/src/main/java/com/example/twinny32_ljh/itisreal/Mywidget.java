package com.example.twinny32_ljh.itisreal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Twinny32_LJH on 2017-09-11.
 */

public class Mywidget extends AppWidgetProvider {


    private TextView tvDate;                     //연/월 텍스트뷰
    private GridAdapter gridAdapter;            //  그리드뷰 어댑터
    private ArrayList<String> dayList;          //일 저장 할 리스트
    private GridView gridView;                  // 그리드뷰
    private Calendar mCal;                      // 캘린더 변수




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

        }

        tvDate=tvDate.findViewById(R.id.tv_date);//
        gridView=gridView.findViewById(R.id.gridview);//

        long now = System.currentTimeMillis();  // 오늘에 날짜를 세팅 해준다.

        final Date date = new Date(now);

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);

        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date)+"/"+curDayFormat.format(date));

        dayList =new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");


        mCal = Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);

        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(context, dayList);
        gridView.setAdapter(gridAdapter);
    }

    private void setCalendarDate(int month) {

        mCal.set(Calendar.MONTH, month - 1);


        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            dayList.add("" + (i + 1));

        }
    }


    private class GridAdapter extends BaseAdapter{


        private List<String> list;


        private LayoutInflater inflater;

        public GridAdapter(Context context, List<String> list) {

            this.list = list;

            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {            return list.size();        }
        @Override
        public Object getItem(int position) {            return list.get(position);        }
        @Override
        public long getItemId(int position) {            return position;        }
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ViewHolder holder = null;

            if(view==null){
                view = inflater.inflate(R.layout.item_calendar_gridview,viewGroup,false);
                holder  = new ViewHolder();
                holder.tvItemGridView = (TextView) view.findViewById(R.id.tv_item_gridview);
                view.setTag(holder);
            } else {
                holder  =(ViewHolder)view.getTag();
            }

            holder.tvItemGridView.setText(""+getItem(position));
            //해당 날짜 텍스트 컬러,배경 변경

            mCal = Calendar.getInstance();

            //오늘 day 가져옴

            Integer today = mCal.get(Calendar.DAY_OF_MONTH);

            String sToday = String.valueOf(today);

            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경

                holder.tvItemGridView.setTextColor(R.color.abc_tint_default);

            }

            return view;
        }



    }
    private class ViewHolder {

        TextView tvItemGridView;

    }
}
