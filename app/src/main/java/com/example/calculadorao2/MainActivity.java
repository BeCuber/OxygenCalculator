package com.example.calculadorao2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Elementos de la interfaz
    EditText inputBares, inputVelO2;
    TextView txtInfoLO2, txtInfoTiempo;
    SeekBar seekBar;
    Spinner spinnerVolCylinder;


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

        //Vinculación de las vistas con las variables
        inputBares = findViewById(R.id.inputBares);
        inputVelO2 = findViewById(R.id.inputVelO2);
        txtInfoLO2 = findViewById(R.id.txtInfoLO2);
        txtInfoTiempo = findViewById(R.id.txtInfoTiempo);
        seekBar = findViewById(R.id.seekBar);
        spinnerVolCylinder = findViewById(R.id.spinnerVolCylinder);

        setupSpinner();
        setupSeekBar();
        setupTextChangeListeners();
    }

    //Configuración del spinner y su adaptador
    private void setupSpinner() {
        //Creación del adapter para asignar las opciones al spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.VolCylinder,
                android.R.layout.simple_spinner_dropdown_item
        );
        //Asignar el adaptador al spinner
        spinnerVolCylinder.setAdapter(adapter);
        //Manejar el evento al cambiar el item
        spinnerVolCylinder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateLO2Info();
                updateTiempoInfo();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Configurar el seekbar
    private void setupSeekBar(){
        //Configuración valor predeterminado del seekBar
        int defaultValue = Integer.parseInt(inputBares.getText().toString());
        seekBar.setProgress(defaultValue);
        //Comportamiento del seekBar para que modifique el valor de los bares
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
    }



    //Configurar los listeners para los cambios en los EditText
    private void setupTextChangeListeners(){
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



    //Actualizar la información de volumen de O2 restante
    private void updateLO2Info(){
        txtInfoLO2.setText("There are "+getVolO2()+" L O2 left.");
    }



    //Actualizar la información de tiempo restante
    private void updateTiempoInfo(){
        // Convertir los minutos que devuelve getMinutesRemaining a horas y minutos
        int horas = (int) getMinutesRemaining() / 60;
        int minutos = (int) getMinutesRemaining() % 60;
        //Formatear un String en hh:mm
        String tiempoFormateado = String.format("%02d:%02d", horas, minutos);
        //Establecer texto formateado en el edittext
        txtInfoTiempo.setText("There are "+ tiempoFormateado +" remaining.");
    }



    ///Calcular los litros totales de oxígeno restantes
    //[inputBares(bar) - VolumenResidual(bar)] x inputLitrosB (l) = litros totales O2 restantes
    private float getVolO2(){
        final float VR = 20;
        String inputBaresText = inputBares.getText().toString();
        String volSelected = (String) spinnerVolCylinder.getSelectedItem();
        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        float baresActuales = inputBaresText.isEmpty() ? 0 : Float.parseFloat(inputBaresText);
        float capacidadBotella = Float.parseFloat(volSelected);

        return (baresActuales - VR) * capacidadBotella;
    }



    //Calcular el tiempo restante en minutos
    //litros totales de O2 / velocidad de administración (l/min)
    private float getMinutesRemaining(){

        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        String inputVelO2Text = inputVelO2.getText().toString();
        float velocidadAdmin = inputVelO2Text.isEmpty() ? 0 : Float.parseFloat(inputVelO2Text);

        return getVolO2() / velocidadAdmin;
    }

}