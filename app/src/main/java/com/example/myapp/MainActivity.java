package com.example.myapp;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 100;
    long animationDuration = 1000; // 1초

    public static boolean vib_mode; // 알림 진동 설정 (true - o , false - x)
    public static boolean use_set; // 사용 설정 (true - ON , false - OFF)
    //Drawable round = getResources().getDrawable(R.drawable.roundbtn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_set_use = (Button) findViewById(R.id.btn_set_use); // 어플 사용 설정 버튼
        Button btn_set_vib = (Button) findViewById(R.id.btn_set_vibration); // 진동 알림 설정 버튼

        // 진동 알림 설정 버튼
        btn_set_vib.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String str_btn_vib = btn_set_vib.getText().toString(); // 진동 알림 설정 버튼의 텍스트 변경

                // 진동알림 ON -> OFF
                if(str_btn_vib.equals("진동 알림               ON  ")){
                    btn_set_vib.setText("진동 알림               OFF  ");

                    // 버튼 클릭시 애니메이션
                    ValueAnimator animator1 = ObjectAnimator.ofFloat(btn_set_vib, "translationX", 100f,200f,50f); // values 수정 필요
                    animator1.setDuration(animationDuration);
                    animator1.start();

                    // 알림 진동 설정 끔
                    vib_mode = false;

                }
                // 진동알림 OFF -> ON
                else if(str_btn_vib.equals("진동 알림               OFF  ")){
                    btn_set_vib.setText("진동 알림               ON  ");

                    // 버튼 클릭시 애니메이션
                    ValueAnimator animator2 = ObjectAnimator.ofFloat(btn_set_vib, "translationX", 100f,200f,50f); // values 수정 필요
                    animator2.setDuration(animationDuration);
                    animator2.start();

                    // 알림 진동 설정 켬
                    vib_mode = true;
                }
            }

        });

        // 어플 설정 버튼
        btn_set_use.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String str_btn = btn_set_use.getText().toString(); // 어플 설정 버튼의 텍스트
                TextView txt = findViewById(R.id.textView2); // 어플 설정 버튼 밑 텍스트뷰

                if(str_btn.equals("ON")){ // 클릭 -> 실시간 탐지 OFF

                    // 버튼 및 텍스트 뷰의 텍스트 변경
                    btn_set_use.setText("OFF");
                    txt.setText("실시간 탐지가 꺼졌습니다.");

                    // 버튼 색 변경 (parseColor 때문에 어플 강제종료 돼서 주석처리 해놓음)
                    //round.setColorFilter(Color.parseColor("#B8860B"), PorterDuff.Mode.SRC); // 색상 값 넣으면 오류 뜸 ..
                    //btn_set_use.setBackground(round);

                    // 어플 사용 설정 OFF
                    use_set = false;

                }
                else if(str_btn.equals("OFF")){ // 클릭 -> 실시간 탐지 ON

                    // + 휴대폰 권한 받아오기
                    onCheckPermission();

                    // 버튼 및 텍스트 뷰의 텍스트 변경
                    btn_set_use.setText("ON");
                    txt.setText("실시간 탐지 중입니다.");

                    // 버튼 색 변경 (parseColor 때문에 어플 강제종료 돼서 주석처리 해놓음)
                    //round.setColorFilter(Color.parseColor("#B4B4FF"), PorterDuff.Mode.SRC); // 색상 값 넣으면 오류 뜸 ..
                    //btn_set_use.setBackground(round);

                    // 어플 사용 설정 ON
                    use_set = true;

                }
            }
        });
    }

    
    // 어플 사용설정 최초 ON 에 한해서 권한을 받아옴
    public void onCheckPermission(){
        if(ActivityCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, READ_PHONE_STATE) && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CALL_LOG)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, SYSTEM_ALERT_WINDOW)){
                Toast.makeText(this, "어플 사용을 위해서는 권한 설정이 필요합니다.", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this,new String[]{READ_PHONE_STATE, READ_CALL_LOG,SYSTEM_ALERT_WINDOW}, PERMISSIONS_REQUEST);
            } else{
                ActivityCompat.requestPermissions(this,new String[]{READ_PHONE_STATE, READ_CALL_LOG,SYSTEM_ALERT_WINDOW}, PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "어플 실행을 위한 권한이 설정 되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "어플 실행을 위한 권한이 취소 되었습니다.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}