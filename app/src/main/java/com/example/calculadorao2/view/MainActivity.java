package com.example.calculadorao2.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.calculadorao2.R;
import com.example.calculadorao2.model.Bar;
import com.example.calculadorao2.model.Kpa;
import com.example.calculadorao2.model.PreviousUnit;
import com.example.calculadorao2.model.Psi;

public class MainActivity extends AppCompatActivity {

    //Elementos de la interfaz
    EditText inputPressure, inputVelO2;
    TextView txtInfoLO2, txtInfoTiempo;
    SeekBar seekBar;
    Spinner spinnerPressureUnit, spinnerVolCylinder;
    String baresFinalValue = ""; //Necesitamos que cualquier valor de inputPressure termine en bares para la fórmula getVolO2
    private PreviousUnit previousUnit = new PreviousUnit();



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
        inputPressure = findViewById(R.id.inputPressure);
        inputVelO2 = findViewById(R.id.inputVelO2);
        txtInfoLO2 = findViewById(R.id.txtInfoLO2);
        txtInfoTiempo = findViewById(R.id.txtInfoTiempo);
        seekBar = findViewById(R.id.seekBar);
        spinnerPressureUnit = findViewById(R.id.spinnerPressureUnit);
        spinnerVolCylinder = findViewById(R.id.spinnerVolCylinder);


        setupSpinnerPressureUnit();
        setupSpinnerVolCylinder();
        setupSeekBar();
        setupTextChangeListeners();


    }

    //Configuración del spinner de presión y su adaptador
    private void setupSpinnerPressureUnit(){
        int defaultValue = Integer.parseInt(inputPressure.getText().toString());
        seekBar.setProgress(defaultValue);


        //Creación del adapter para asignar las opciones al spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.PressureUnit,
                android.R.layout.simple_spinner_dropdown_item
        );
        //Asignar el adaptador al spinner
        spinnerPressureUnit.setAdapter(adapter);
        //Manejar los eventos al cambiar de unidad

        spinnerPressureUnit.setSelection(0);

        //Double inputPressureDouble = Double.parseDouble(inputPressure.getText().toString()); //Necesitamos el valor de inputPressure en tipo double para las operaciones

        previousUnit.setUnit(spinnerPressureUnit.getSelectedItem().toString());

        //String previousPressureUnit = spinnerPressureUnit.getSelectedItem().toString(); //Necesitamos saber la unidad desde la que hay que hacer la conversión

        spinnerPressureUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String newUnit = spinnerPressureUnit.getSelectedItem().toString();

                Double inputPressureDouble = Double.parseDouble(inputPressure.getText().toString()); //Necesitamos el valor de inputPressure en tipo double para las operaciones

                Log.i("INFO ", inputPressure.getText().toString() + " kPa antes del switch");
                Log.i("INFO ", Double.parseDouble(inputPressure.getText().toString()) + " kPa antes del switch");
                Log.i("INFO ", inputPressureDouble + " kPa var antes del switch");
                switch (newUnit){
                    case "bar":
                        seekBar.setMax(315);
                        if (previousUnit.getUnit().equals("kPa")){
                            Log.i("INFO", "De kPa a bares");

                            baresFinalValue = Double.toString(Kpa.toBar(inputPressureDouble));
                            Log.i("INFO ", inputPressureDouble + " kPa son " + Kpa.toBar(inputPressureDouble) + " bar.");
                            inputPressure.setText(baresFinalValue);

                        } else if (previousUnit.getUnit().equals("psi")) {
                            Log.i("INFO", "De psi a bares");

                            baresFinalValue = Double.toString(Psi.toBar(inputPressureDouble));
                            inputPressure.setText(baresFinalValue);


                        }
                        break;
                    case "kPa":
                        seekBar.setMax(35000);
                        if (previousUnit.getUnit().equals("bar")){
                            Log.i("INFO", "De bar a kPa");

                            baresFinalValue = inputPressure.getText().toString();
                            Bar.toKpa(inputPressureDouble);
                            inputPressure.setText(Double.toString(Bar.toKpa(inputPressureDouble)));


                        } else if (previousUnit.getUnit().equals("psi")) {
                            Log.i("INFO", "De psi a kPa");

                            baresFinalValue = Double.toString(Psi.toBar(inputPressureDouble));
                            Psi.toKpa(inputPressureDouble);
                            inputPressure.setText(Double.toString(Psi.toKpa(inputPressureDouble)));


                        }
                        break;
                    case "psi":
                        seekBar.setMax(5000);
                        if (previousUnit.getUnit().equals("bar")){
                            Log.i("INFO", "De bar a psi");

                            baresFinalValue = inputPressure.getText().toString();
                            Bar.toPsi(inputPressureDouble);
                            inputPressure.setText(Double.toString(Bar.toPsi(inputPressureDouble)));


                        } else if (previousUnit.getUnit().equals("kPa")) {
                            Log.i("INFO", "De kPa a psi");

                            baresFinalValue = Double.toString(Kpa.toBar(inputPressureDouble));
                            Kpa.toPsi(inputPressureDouble);
                            inputPressure.setText(Double.toString(Kpa.toPsi(inputPressureDouble)));
                        }
                        break;
                }
                previousUnit.setUnit(newUnit);
                //seekBar.setProgress(defaultValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }








    //Configuración del spinner de volumen y su adaptador
    private void setupSpinnerVolCylinder() {
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
        seekBar.setMin(0);
        seekBar.setMax(315);


        int defaultValue = Integer.parseInt(inputPressure.getText().toString());
        seekBar.setProgress(defaultValue);




        //Comportamiento del seekBar para que modifique el valor de la presión
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                inputPressure.setText(Integer.toString(progress));
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
        inputPressure.addTextChangedListener(new TextWatcher() {
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
    //[inputPressure(bar) - VolumenResidual(bar)] x inputLitrosB (l) = litros totales O2 restantes
    private double getVolO2(){ //cambiar inputPressure.getText().toString() a baresFinalValue(String)
        final double VR = 20;
         //inputPressure.getText().toString();
        String volSelected = (String) spinnerVolCylinder.getSelectedItem();
        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        double actualPressure = baresFinalValue.isEmpty() ? 0 : Double.parseDouble(baresFinalValue);//toBar()
        double capacidadBotella = Double.parseDouble(volSelected);

        return (actualPressure - VR) * capacidadBotella;
    }



    //Calcular el tiempo restante en minutos
    //litros totales de O2 / velocidad de administración (l/min)
    private double getMinutesRemaining(){

        //Comprueba si el edittext está vacío antes de convertir su cadena a float
        String inputVelO2Text = inputVelO2.getText().toString();
        double velocidadAdmin = inputVelO2Text.isEmpty() ? 0 : Float.parseFloat(inputVelO2Text);

        return getVolO2() / velocidadAdmin;
    }

}