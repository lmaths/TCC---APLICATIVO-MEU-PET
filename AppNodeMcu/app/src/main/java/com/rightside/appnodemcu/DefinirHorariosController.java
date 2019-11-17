package com.rightside.appnodemcu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

public class DefinirHorariosController {

    public static void alertaDeNovoHorario(final FragmentActivity activity) {

        final AlertDialog.Builder alerta = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK);
        alerta.setTitle("HORÁRIOS DE ALIMENTAÇÃO").setMessage("Deseja definir novos horários?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DefinirHorariosDialogFragment.novaInstancia().show(activity.getSupportFragmentManager(), "teste");
                    }
                }).setNegativeButton("Cancelar", null).show();
    }



}
