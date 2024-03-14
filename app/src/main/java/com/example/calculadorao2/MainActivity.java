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

    //Creación de variables para elementos de la interfaz
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

        //Vinculación de las variables con los elementos de la interfaz
        inputBares = findViewById(R.id.inputBares);
        inputLitrosB = findViewById(R.id.inputLitrosB);
        inputVelO2 = findViewById(R.id.inputVelO2);
        txtInfoLO2 = findViewById(R.id.txtInfoLO2);
        txtInfoTiempo = findViewById(R.id.txtInfoTiempo);
        seekBar = findViewById(R.id.seekBar);

        updateLO2Info();
        updateTiempoInfo();


        //Configuración valor predeterminado del seekBar
        int defaultValue = Integer.parseInt(inputBares.getText().toString());
        seekBar.setProgress(defaultValue);

        /*-----Comportamiento del seekBar para que modifique el valor de los bares-----*/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                inputBares.setText(Integer.toString(progress));
                updateLO2Info();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        /*---Comportamiento al editar los EditText---*/

        inputBares.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLO2Info();
                updateTiempoInfo();
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
                updateLO2Info();
                updateTiempoInfo();

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
                updateTiempoInfo();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    //Métodos para actualizar la información de LO2 y Tiempo restantes
    private void updateLO2Info(){
        txtInfoLO2.setText("There are "+getVolO2()+" L O2 left.");
    }
    private void updateTiempoInfo(){
        // Convertir los minutos que devuelve getMinutesRemaining a horas y minutos
        int horas = (int) getMinutesRemaining() / 60;
        int minutos = (int) getMinutesRemaining() % 60;
        //Formatear un String en hh:mm
        String tiempoFormateado = String.format("%02d:%02d", horas, minutos);
        //Establecer texto formateado en el edittext
        txtInfoTiempo.setText("There are "+ tiempoFormateado +" remaining.");
    }



    /*----Cálculo de los litros totales de oxígeno restantes----*/
    //[inputBares(bar) - VolumenResidual(bar)] x inputLitrosB (l) = litros totales O2 restantes
    private float getVolO2(){

        final float VR = 20;
        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        String inputBaresText = inputBares.getText().toString();
        String inputLitrosBText = inputLitrosB.getText().toString();

        float baresActuales = inputBaresText.isEmpty() ? 0 : Float.parseFloat(inputBaresText);
        float capacidadBotella = inputLitrosBText.isEmpty() ? 0 : Float.parseFloat(inputLitrosBText);

        float VolO2 = (baresActuales - VR)*capacidadBotella;

        return VolO2;
    }

    /*---Cálculo del tiempo que se puede suministrar el tratamiento actual (l/min)---*/
    //litros totales de O2 / velocidad de administración (l/min)
    private float getMinutesRemaining(){

        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        String inputVelO2Text = inputVelO2.getText().toString();
        float velocidadAdmin = inputVelO2Text.isEmpty() ? 0 : Float.parseFloat(inputVelO2Text);

        float minutes = getVolO2()/velocidadAdmin;

        return minutes;
    }

}