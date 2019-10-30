package com.rightside.appnodemcu;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends FragmentActivity {

    private TextView txtQuantidade, txtHorario1, txtHorario2, txtConfig, txtReservatorio, txtRacao,txtAgua;
    private DatabaseReference database;
    private ImageView imgNivelAgua, imgNivelRacao;
    private Button buttonAlterarHorarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface font = ResourcesCompat.getFont(getApplicationContext(), R.font.adaliciaregular);
        setContentView(R.layout.activity_principal);
        txtQuantidade = findViewById(R.id.quantidadeALim);
        txtHorario1 = findViewById(R.id.horario1);
        txtHorario2 = findViewById(R.id.horario2);
        imgNivelAgua = findViewById(R.id.imgAgua);
        txtConfig = findViewById(R.id.txtconfig);
        txtReservatorio = findViewById(R.id.txtReservatorio);
        txtRacao = findViewById(R.id.txtRacao);
        txtAgua = findViewById(R.id.txtAgua);
        imgNivelRacao = findViewById(R.id.imgRacao);
        buttonAlterarHorarios = findViewById(R.id.buttonAlterarHorarios);
        txtHorario1.setTypeface(font);
        txtAgua.setTypeface(font);
        txtRacao.setTypeface(font);
        txtReservatorio.setTypeface(font);
        txtConfig.setTypeface(font);
        txtHorario2.setTypeface(font);
        txtQuantidade.setTypeface(font);
        buttonAlterarHorarios.setTypeface(font);
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nodemcu-8c8b9.firebaseio.com/");

        database.addValueEventListener(new ValueEventListener() {
                                           @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                               String hora1 = dataSnapshot.child("horaAlimentacao1").getValue().toString();
                                               String min1 = dataSnapshot.child("minAlimentacao").getValue().toString();
                                               String hora2 = dataSnapshot.child("horaAlimentacao2").getValue().toString();
                                               String min2 = dataSnapshot.child("minAlimentacao2").getValue().toString();
                                               boolean qtdAlim = (boolean) dataSnapshot.child("qtdAlim").getValue();
                                               int nivelReservatorio = Integer.valueOf(dataSnapshot.child("reservatorio").getValue().toString());
                                               txtHorario1.setText(hora1 + ":" + min1);


                                               if(qtdAlim == false) {
                                                   txtQuantidade.setText("Uma vez ao dia");
                                                   txtHorario2.setText("");
                                               } else {
                                                   txtQuantidade.setText("Duas vezes ao dia");
                                                   txtHorario2.setText(hora2 + ":" + min2);
                                               }

                                               if(nivelReservatorio == 0) {
                                                   int id = (int) (Math.random()*10000);
                                                   imgNivelAgua.setImageResource(R.drawable.vazio);
                                                   Notification notification = new Notification.Builder(getBaseContext())
                                                           .setContentTitle("Atenção! Reservatorio quase vazio")
                                                           .setContentText("A Água do seu pet está acabando, você precisa encher!").setSmallIcon(R.mipmap.ic_launcher)
                                                           .build();

                                                   NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                   notification.flags |= Notification.FLAG_AUTO_CANCEL;


                                                   notificationManager.notify(id,notification);


                                               } else if (nivelReservatorio == 1) {
                                                   imgNivelAgua.setImageResource(R.drawable.medio);
                                               } else if (nivelReservatorio == 2) {
                                                   imgNivelAgua.setImageResource(R.drawable.completo);
                                               }

                                               imgNivelRacao.setImageResource(R.drawable.completo);

                                           }


                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });

        buttonAlterarHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefinirHorariosController.alertaDeNovoHorario(PrincipalActivity.this);
            }
        });








    }

}
