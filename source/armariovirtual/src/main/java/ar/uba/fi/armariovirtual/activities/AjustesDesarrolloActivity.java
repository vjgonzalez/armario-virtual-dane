package ar.uba.fi.armariovirtual.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.InicializacionBD;
import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.utils.BotonSombra;
import ar.uba.fi.utilidadesdane.persistencia.DBUtils;

public class AjustesDesarrolloActivity extends BarraBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustes_desarrollo);

        (findViewById(R.id.dump_db_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                DBUtils.dump("Sugar.db", getPackageName(), "DebugSugar.db", AjustesDesarrolloActivity.this);
            }
        });


        (findViewById(R.id.empty_db_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new AlertDialog.Builder(AjustesDesarrolloActivity.this)
                        .setTitle("VACIAR BD")
                        .setMessage("VACIAR BD?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DBUtils.inicializar(AjustesDesarrolloActivity.this, true);
                                InicializacionBD.setUpModelo(getApplicationContext());
                                //finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });

        (findViewById(R.id.sanitizar_db_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new AlertDialog.Builder(AjustesDesarrolloActivity.this)
                        .setTitle("SANITIZAR BD")
                        .setMessage("SANITIZAR BD?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                InicializacionBD.sanitizarBD(getApplicationContext());
                                //finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });

        (findViewById(R.id.crear_datos_por_defecto_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                new AlertDialog.Builder(AjustesDesarrolloActivity.this)
                        .setTitle("CREAR DATOS")
                        .setMessage("CREAR DATOS POR DEFECTO?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                InicializacionBD.crearDatosPorDefecto(getApplicationContext());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        setUpBarraConBotonDeAtras(true, false, new Intent(AjustesDesarrolloActivity.this, ConfiguracionActivity.class));
    }

}
