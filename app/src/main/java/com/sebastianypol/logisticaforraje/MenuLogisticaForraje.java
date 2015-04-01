package com.sebastianypol.logisticaforraje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Pablo on 31/03/2015.
 */
public class MenuLogisticaForraje extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logistica_forraje);
        findViewById(R.id.btnPicadoraMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuLogisticaForraje.this, Logistica.class));
            }
        });
        findViewById(R.id.btnCarroForrajeroMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuLogisticaForraje.this, CarroForrajero.class));
            }
        });
        findViewById(R.id.btnEmbolsadoraMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuLogisticaForraje.this, Embolsadora.class));
            }
        });
    }
}
