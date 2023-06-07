package sv.edu.utec.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.utec.proyectofinal.datos.baseHelper;
import sv.edu.utec.proyectofinal.datos.daoUsuarios;

public class MainActivity extends AppCompatActivity {

    Context context;
    EditText edtEmail, edtPassword;
    Button btnRegistro, btnLogin;

    daoUsuarios dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botones
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        // EditText
        edtEmail = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        dao = new daoUsuarios(this);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(getApplicationContext(), Registro.class));
                startActivity(new Intent(i));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked();
            }
        });


    }


    public void loginClicked() {
        String username = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (isValidLogin(username, password)) {
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Inicio de sesión no válido", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidLogin(String username, String password) {
        baseHelper baseHelper = new baseHelper(getApplicationContext());
        SQLiteDatabase db = baseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email=? AND password=?", new String[]{username, password});

        boolean isValid = cursor.moveToFirst();

        cursor.close();
        db.close();

        return isValid;
    }

}