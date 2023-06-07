package sv.edu.utec.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import sv.edu.utec.proyectofinal.datos.daoUsuarios;

public class Registro extends AppCompatActivity {

    EditText Nombres, Apellidos, FechaNacimiento, Email, Password;
    Button btnRegresar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Botones
        btnRegresar = findViewById(R.id.btnRRegresar);
        btnRegistrar = findViewById(R.id.btnRRegistrar);

        // TextPlains
        Nombres = findViewById(R.id.edtNombres);
        Apellidos = findViewById(R.id.edtApellidos);
        FechaNacimiento = findViewById(R.id.edtDFecNac);
        Email = findViewById(R.id.edtEmail);
        Password = findViewById(R.id.edtRPassword);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id=0;

                if(Nombres.equals("") || Apellidos.equals("")||FechaNacimiento.equals("")||Email.equals("")||Password.equals("")){
                    validacion();
                }
                else{
                    daoUsuarios tbUsuario = new daoUsuarios(getApplicationContext());
                    id = tbUsuario.insertarUsuario(Nombres.getText().toString(), Apellidos.getText().toString(),FechaNacimiento.getText().toString(),Email.getText().toString(),Password.getText().toString());
                    if(id>0)
                    {
                        Toast.makeText(getApplicationContext(), "Registro almacenado",Toast.LENGTH_LONG).show();
                        limpiar();
                        Intent i= new Intent(new Intent(getApplicationContext(), MainActivity.class));
                        startActivity(new Intent(i));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Registro no almacenado",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(new Intent(getApplicationContext(), MainActivity.class));
                startActivity(new Intent(i));
            }
        });


    }

    private void limpiar(){
        Nombres.setText("");
        Apellidos.setText("");
        FechaNacimiento.setText("");
        Email.setText("");
        Password.setText("");
    }

    private void validacion()
    {
        if(Nombres.equals(""))
        {
            Nombres.setError("Campo requerido");
        } else if (Apellidos.equals("")) {
            Apellidos.setError("Campo requerido");
        } else if (FechaNacimiento.equals("")){
            FechaNacimiento.setError("Campo requerido");
        } else if (Email.equals("")) {
            Email.setError("Campo requerido");
        } else if (Password.equals("")) {
            Password.setError("Campo requerido");
        }


    }

    public void abrirCalendario (View view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + month + "/" + year;
                FechaNacimiento.setText(fecha);
            }
        }, 2000, month, day);

        dpd.show();
    }
}