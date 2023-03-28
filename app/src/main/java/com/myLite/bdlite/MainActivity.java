package com.myLite.bdlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText CEDULA, NOMBRE, TELEFONO;

    Button REGISTRAR, CONSULTAR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CEDULA = findViewById(R.id.cc);
        NOMBRE = findViewById(R.id.nom);
        TELEFONO = findViewById(R.id.tel);

        REGISTRAR = findViewById(R.id.btnRegis);
        CONSULTAR = findViewById(R.id.btnCon);




    }

    public void onStart()
    {
        super.onStart();

        REGISTRAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });

        CONSULTAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar(view);
            }
        });

    }


    public void insertar(View view){
        AdminBD admin = new AdminBD(this,"BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String cedula, nombre, telefono;
        cedula = CEDULA.getText().toString();
        nombre = NOMBRE.getText().toString();
        telefono = TELEFONO.getText().toString();

        if(!cedula.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("cedula",cedula);
            registro.put("nombre",nombre);
            registro.put("telefono",telefono);
            BD.insert("usuarios", null, registro);
            BD.close();

            CEDULA.setText("");
            NOMBRE.setText("");
            TELEFONO.setText("");

            Toast.makeText(this, "Registro exitoso",Toast.LENGTH_LONG).show();
        }else
        {
            Toast.makeText(this, "Complete los campos vacíos",Toast.LENGTH_LONG).show();
        }




    }


    public void consultar(View view)
    {
        AdminBD admin = new AdminBD(this,"BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();


        String cedula1 = CEDULA.getText().toString();
        if(!cedula1.isEmpty())
        {
            Cursor fila = BD.rawQuery("Select nombre, telefono from usuarios where cedula="+cedula1,null);

            if(fila.moveToFirst())
            {
                NOMBRE.setText(fila.getString(0));
                TELEFONO.setText(fila.getString(1));
                BD.close();
            }else
            {
                Toast.makeText(this, "No se encuentra el usuario",Toast.LENGTH_LONG).show();

            }
        }else
        {
            Toast.makeText(this, "Ingrese la cédula para la búsqueda",Toast.LENGTH_LONG).show();
        }

    }
}