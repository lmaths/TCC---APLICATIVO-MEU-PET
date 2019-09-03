package com.rightside.appnodemcu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText hour, min, hourSegunda, minSegunda;
    private DatabaseReference horaAlimentacao1, horaAlimentacao2, minAlimentacao, minAlimentacao2, quantidadeAlim;
    private RadioButton radioButton, radioButtonDois;

    private Button buttonSalvar;
    private TextView alimentarPet;
    private LinearLayout segundaAlim, horariosLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        horariosLayout.setVisibility(View.GONE);



        horaAlimentacao1 = FirebaseDatabase.getInstance().getReference("horaAlimentacao1");
        horaAlimentacao2 = FirebaseDatabase.getInstance().getReference("horaAlimentacao2");
        minAlimentacao = FirebaseDatabase.getInstance().getReference("minAlimentacao");
        minAlimentacao2 = FirebaseDatabase.getInstance().getReference("minAlimentacao2");
        quantidadeAlim = FirebaseDatabase.getInstance().getReference("qtdAlim");





        buttonSalvar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            int horaAlim = Integer.parseInt(hour.getText().toString());
            int minAlim = Integer.parseInt(min.getText().toString());

            int hourAlimSegunda = Integer.parseInt(hourSegunda.getText().toString());
            int minAlimSegunda = Integer.parseInt(minSegunda.getText().toString());



               Toast.makeText(MainActivity.this, "Hr" + horaAlim + minAlim + hourAlimSegunda + minAlimSegunda, Toast.LENGTH_LONG).show();

            horaAlimentacao1.setValue(horaAlim);
            minAlimentacao.setValue(minAlim);
            horaAlimentacao2.setValue(hourAlimSegunda);
            minAlimentacao2.setValue(minAlimSegunda);

           }
       });






}

    public void checkButton(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()) {
            case R.id.btnDois:
                if(checked)
                    horariosLayout.setVisibility(View.VISIBLE);
                    segundaAlim.setVisibility(View.VISIBLE);
                    quantidadeAlim.setValue(true);
                    radioButtonDois.setBackgroundColor(getResources().getColor(R.color.colorclicou));
                    radioButton.setBackgroundColor(getResources().getColor(R.color.colorNaoSelecionado));
                break;

            case R.id.btnUm:
                if(checked)
                    horariosLayout.setVisibility(View.VISIBLE);
                    segundaAlim.setVisibility(View.GONE);
                    minSegunda.setText("0");
                    hourSegunda.setText("0");
                    quantidadeAlim.setValue(false);
                   radioButton.setBackgroundColor(getResources().getColor(R.color.colorclicou));
                   radioButtonDois.setBackgroundColor(getResources().getColor(R.color.colorNaoSelecionado));
                    break;

        }

    }

    public void inicializarComponentes() {
        buttonSalvar = findViewById(R.id.btnSalvar);
        segundaAlim = findViewById(R.id.segundaAlim);
        hour = findViewById(R.id.hour);
        min = findViewById(R.id.min);
        hourSegunda = findViewById(R.id.hourSegunda);
        minSegunda = findViewById(R.id.minSegunda);
        horariosLayout = findViewById(R.id.horarioslayout);
        alimentarPet = findViewById(R.id.alimPet);
        radioButton = findViewById(R.id.btnUm);
        radioButtonDois = findViewById(R.id.btnDois);

    }
    }
