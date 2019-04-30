package com.rightside.appnodemcu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnLampada, btnLigar;
    TextView txtResultado;
    EditText editText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("usuario");

        btnLampada = findViewById(R.id.btnLampada);
        btnLigar = findViewById(R.id.btnLigar);
        editText = findViewById(R.id.editTeste);


        btnLampada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String name =  editText.getText().toString();


                databaseReference.setValue(name);

                Toast.makeText(MainActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();



            }
        });

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitaDados("0");
            }
        });
    }

    public void solicitaDados (String comando) {

        ConnectivityManager conManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            String url = "http://192.168.0.108/gpio/";

            new SolicitaDados().execute(url + comando);
        } else {
            Toast.makeText(MainActivity.this, "Não possui REDE", Toast.LENGTH_SHORT).show();
        }
    }



    private class SolicitaDados extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {
            return Conexao.getDados(url[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {

            if(resultado != null) {

                txtResultado.setText(resultado);
                
            } else {
                Toast.makeText(MainActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
            }
        }
    }
}
