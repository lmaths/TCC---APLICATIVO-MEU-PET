package com.rightside.appnodemcu;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DefinirHorariosDialogFragment extends DialogFragment {

    private EditText hour, min, hourSegunda, minSegunda;
    private DatabaseReference horaAlimentacao1, horaAlimentacao2, minAlimentacao, minAlimentacao2, quantidadeAlim;
    private RadioButton radioButton, radioButtonDois;

    private Button buttonSalvar;
    private TextView alimentarPet, definirHorarios;
    private LinearLayout segundaAlim, horariosLayout;
    private Calendar calendar;
    private int hora, minute;
    private int horaAlim, minAlim, hourAlimSegunda, minAlimSegunda;
    private TimePickerDialog timePickerDialog;

    public static DefinirHorariosDialogFragment novaInstancia() {
        DefinirHorariosDialogFragment definirHorariosDialogFragment = new DefinirHorariosDialogFragment();
    return definirHorariosDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_definir_horarios_dialog, container, false);
        Typeface font = ResourcesCompat.getFont(getContext(), R.font.adaliciaregular);
        buttonSalvar = view.findViewById(R.id.btnSalvar);
        alimentarPet = view.findViewById(R.id.alimPet);
        definirHorarios = view.findViewById(R.id.definHorarios);
        alimentarPet.setTypeface(font);
        definirHorarios.setTypeface(font);
        segundaAlim = view.findViewById(R.id.segundaAlim);
        hour = view.findViewById(R.id.hour);
        //min = view.findViewById(R.id.min);
        hourSegunda = view.findViewById(R.id.hourSegunda);
        //minSegunda = view.findViewById(R.id.minSegunda);
        horariosLayout = view.findViewById(R.id.horarioslayout);
        alimentarPet = view.findViewById(R.id.alimPet);
        radioButton = view.findViewById(R.id.btnUm);
        radioButtonDois = view.findViewById(R.id.btnDois);

        radioButton.setTypeface(font);
        radioButtonDois.setTypeface(font);

        hour.setTypeface(font);
       // min.setTypeface(font);
        hourSegunda.setTypeface(font);
       // minSegunda.setTypeface(font);

        buttonSalvar.setTypeface(font);

        hour.setInputType(InputType.TYPE_NULL);
        hourSegunda.setInputType(InputType.TYPE_NULL);

        RadioGroup radioGroup = view.findViewById(R.id.radiogrupo);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btnDois:
                            horariosLayout.setVisibility(View.VISIBLE);
                        segundaAlim.setVisibility(View.VISIBLE);
                        quantidadeAlim.setValue(true);
                        radioButtonDois.setBackgroundColor(getResources().getColor(R.color.colorclicou));
                        radioButton.setBackgroundColor(getResources().getColor(R.color.colorNaoSelecionado));
                        break;

                    case R.id.btnUm:
                        horariosLayout.setVisibility(View.VISIBLE);
                        segundaAlim.setVisibility(View.GONE);
                        hourSegunda.setText("0");
                        quantidadeAlim.setValue(false);
                        radioButton.setBackgroundColor(getResources().getColor(R.color.colorclicou));
                        radioButtonDois.setBackgroundColor(getResources().getColor(R.color.colorNaoSelecionado));
                        break;
                }
            }
        });

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);


                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour.setText(hourOfDay + ":" + minute);

                        horaAlim = hourOfDay;
                        minAlim  = minute;



                    }
                }, hora, minute, true);



                timePickerDialog.show();
            }
        });


        hourSegunda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourSegunda.setText(hourOfDay + ":" + minute);
                        hourAlimSegunda = hourOfDay;
                        minAlimSegunda = minute;
                    }
                }, hora, minute, true);

                timePickerDialog.show();
            }
        });


        horariosLayout.setVisibility(View.GONE);


        horaAlimentacao1 = FirebaseDatabase.getInstance().getReference("horaAlimentacao1");
        horaAlimentacao2 = FirebaseDatabase.getInstance().getReference("horaAlimentacao2");
        minAlimentacao = FirebaseDatabase.getInstance().getReference("minAlimentacao");
        minAlimentacao2 = FirebaseDatabase.getInstance().getReference("minAlimentacao2");
        quantidadeAlim = FirebaseDatabase.getInstance().getReference("qtdAlim");




        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                horaAlimentacao1.setValue(horaAlim);
                minAlimentacao.setValue(minAlim);
                horaAlimentacao2.setValue(hourAlimSegunda);
                minAlimentacao2.setValue(minAlimSegunda);

                Toast.makeText(getContext(), "Horários definidos com sucesso", Toast.LENGTH_SHORT).show();
                dismiss();



            }
        });
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        Dialog definirHorarios = getDialog();
        if (definirHorarios != null) {
            definirHorarios.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            definirHorarios.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }
}
