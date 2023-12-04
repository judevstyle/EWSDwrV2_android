package com.ssoft.ews4thai.share.bottomSheet;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ssoft.ews4thai.R;
import com.ssoft.ews4thai.data.model.warning.WarningStation;
import com.ssoft.ews4thai.views.WarningDescActivity;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class WarningBottomSheetDialog extends BottomSheetMapsDialog {

    TextView titleTV;
    ImageView icon;
    TextView txt1TV;
    TextView txt2TV;
    TextView countTV;
    LinearLayout viewmore;
    TextView txt3TV;
    TextView pmTV;
    TextView unitTV;


    WarningStation data;

    public WarningBottomSheetDialog(WarningStation data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_waring, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        icon = view.findViewById(R.id.icon);
        titleTV = view.findViewById(R.id.titleTV);
        txt1TV = view.findViewById(R.id.txt1TV);
        txt2TV = view.findViewById(R.id.txt2TV);
        countTV = view.findViewById(R.id.countTV);
        txt3TV = view.findViewById(R.id.txt3TV);
        pmTV = view.findViewById(R.id.pm2_5TV);
        unitTV = view.findViewById(R.id.unitTV);

        viewmore = view.findViewById(R.id.viewmore);


        titleTV.setText(data.getName());
        txt1TV.setText("ต." + data.getTambon() + " อ." + data.getAmphoe() + " จ." + data.getProvince() + "");
        txt2TV.setText("หมู่บ้านครอบคลุมจำนวน " + data.getStn_cover() + " หมู่บ้าน");


//        if ()
        pmTV.setText("pm2.5 : "+data.getPm25());


        DecimalFormat df = new DecimalFormat("0.0");
        countTV.setText(data.getRain_value()+"");


        if (data.getWarning_type().equals("rain")) {
            unitTV.setText("มม.");
            txt3TV.setText("ปริมาณน้ำฝน");


        }else {
            unitTV.setText("ม.");
            txt3TV.setText("ระดับน้ำ");
//            countTV.setText(df.format(Double.parseDouble(data.getWarn_wl())));

        }

        if (data.getWarn_type() == 9 || data.getWarn_type() == -999){
            countTV.setText(data.getRain12h()+"");
        }

        viewmore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList va = new ArrayList();
                        va.add(data);

                        Intent intent = new Intent(getContext(), WarningDescActivity.class);
                        intent.putExtra("data", va);
                        intent.putExtra("isSigle",true);
                        startActivity(intent);



                    }
                }
        );

//        Log.e("ddaction",""+data.getStation().getAction());
        switch (data.getShow_status()) {
            case 1: {
                Glide.with(getContext())
                        .load(R.drawable.rain_tornado)
                        .into(icon);
                break;
            }
            case 2: {
                Glide.with(getContext())
                        .load(R.drawable.rain_thunder)
                        .into(icon);
                break;
            }
            case 3: {
                Glide.with(getContext())
                        .load(R.drawable.rain_1)
                        .into(icon);
                break;
            }
            default: {
                Glide.with(getContext())
                        .load(R.drawable.overcast)
                        .into(icon);
                break;
            }


//                codeTV.text = "CODE : ${post!!.u_code}"
//                idTV.text = "รหัสตัวแทนจำหน่าย ${post!!.u_code_id}"
        }


//        Glide.with(getContext()).load(data.getU_avatar()).into(img);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ShopDescActivity.class);
//                intent.putExtra("data", Parcels.wrap(data));
//                startActivity(intent);
//                dismiss();
//
//            }
//        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
