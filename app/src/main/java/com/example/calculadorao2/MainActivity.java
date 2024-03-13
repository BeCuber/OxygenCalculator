package com.example.calculadorao2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Elementos de la interfaz
    EditText inputBares, inputLitrosB, inputVelO2;
    TextView txtInfoLO2, txtInfoTiempo;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputBares = findViewById(R.id.inputBares);
        inputLitrosB = findViewById(R.id.inputLitrosB);
        inputVelO2 = findViewById(R.id.inputVelO2);
        txtInfoLO2 = findViewById(R.id.txtInfoLO2);
        txtInfoTiempo = findViewById(R.id.txtInfoTiempo);
        seekBar = findViewById(R.id.seekBar);




        /*-----Comportamiento del seekBar para que modifique el valor de los bares-----*/

        int defaultValue = Integer.parseInt(inputBares.getText().toString());
        seekBar.setProgress(defaultValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                inputBares.setText(Integer.toString(progress));
                txtInfoLO2.setText("There are "+getVolO2()+" l O2 left.");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        txtInfoLO2.setText("There are "+getVolO2()+" l O2 left.");
        txtInfoTiempo.setText("There are "+getTimeRemaining()+" minutes remaining.");

        /*---Comportamiento al editar los EditText---*/

        inputBares.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfoLO2.setText("There are "+getVolO2()+" l O2 left.");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputLitrosB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txtInfoLO2.setText("There are "+getVolO2()+" l O2 left.");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputVelO2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfoTiempo.setText("There are "+getTimeRemaining()+" minutes remaining.");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    /*----Cálculo de los litros totales de oxígeno restantes----*/
    //[inputBares(bar) - VR(bar)] x inputLitrosB (l) = litros totales O2 restantes
    public float getVolO2(){
        float VolO2 = 0;
        final float VR = 20;

        float baresActuales = Float.parseFloat(inputBares.getText().toString());
        float capacidadBotella = Float.parseFloat(inputLitrosB.getText().toString());

        VolO2 = (baresActuales - VR)*capacidadBotella;

        return VolO2;
    }

    /*---Cálculo del tiempo que se puede suministrar el tratamiento actual (l/min)---*/
    //litros totales de O2 / velocidad de administración (l/min)
    public float getTimeRemaining(){
        float minutes = 0;
        float velocidadAdmin = Float.parseFloat(inputVelO2.getText().toString());
        minutes = getVolO2()/velocidadAdmin;

        return minutes;
    }

}