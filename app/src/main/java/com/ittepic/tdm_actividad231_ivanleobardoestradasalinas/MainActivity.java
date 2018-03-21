package com.ittepic.tdm_actividad231_ivanleobardoestradasalinas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText texto;
    Button bguardar,bcargar;
    TextView mostrar;
    static final int READ_BLOCK_SIZE = 100;
    boolean sdDisponible=false;
    boolean sdEscritura=false;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = findViewById(R.id.texto);
        bguardar= findViewById(R.id.guardar);
        bcargar= findViewById(R.id.cargar);
        mostrar=findViewById(R.id.mostrar);


        String estado = Environment.getExternalStorageState();
        if(estado.equals(Environment.MEDIA_MOUNTED)){
            sdDisponible=true;
            sdEscritura=true;
        }else if(estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            sdDisponible=true;
            sdEscritura=false;
        }else{
            sdDisponible=false;
            sdEscritura=false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        bguardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /***************************************************/
                /*Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                intent.setData(uri);
                MainActivity.this.startActivity(intent);*/
                /**************************************************/
                String str = texto.getText().toString();

                if(sdDisponible&&sdEscritura){
                    try{
                        File ruta=Environment.getExternalStorageDirectory();
                        File f=new File(ruta.getAbsolutePath(),"fichero.txt");
                        OutputStreamWriter fout=new OutputStreamWriter(new FileOutputStream(f));
                        fout.write(str);
                        fout.close();
                        Toast.makeText(MainActivity.this,"Guardado Exitoso", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.e("Ficheros","Error al guardar texto en la tarjeta SD");
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Tarjeta SD no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bcargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sdDisponible){
                    try{
                        File ruta=Environment.getExternalStorageDirectory();
                        File f=new File(ruta.getAbsolutePath(),"fichero.txt");
                        BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        String texto=fin.readLine();
                        mostrar.setText(texto);
                        fin.close();
                    }catch(Exception e){
                        Log.e("Ficheros","Error al leer fichero desde la tarjeta SD");
                    }
                }
            }
        });
}
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }
}
