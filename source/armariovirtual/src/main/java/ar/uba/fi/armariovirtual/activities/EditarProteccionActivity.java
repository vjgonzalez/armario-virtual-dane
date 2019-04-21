package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorFuncionalidadProtegible;

public class EditarProteccionActivity extends BarraBaseActivity {

    List<FuncionalidadProtegible> funcionalidades;
    AdaptadorFuncionalidadProtegible adaptadorFuncionalidadProtegible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proteccion);

        funcionalidades = FuncionalidadProtegible.obtenerTodos();
        adaptadorFuncionalidadProtegible = new AdaptadorFuncionalidadProtegible(this, funcionalidades);
        ((ListView) findViewById(R.id.lista_funcionalidades)).setAdapter(adaptadorFuncionalidadProtegible);

        findViewById(R.id.btn_guardar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                guardar();
                finish();
            }
        });

        findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(EditarProteccionActivity.this, ConfiguracionActivity.class);
                startActivity(myIntent);            }
        });


        setUpBarraConBotonDeAtras(true, false, new Intent(EditarProteccionActivity.this, ConfiguracionActivity.class));
    }

    private void guardar(){
        for (FuncionalidadProtegible funcionalidad : funcionalidades) {
            funcionalidad.save();
        }
    }

}
