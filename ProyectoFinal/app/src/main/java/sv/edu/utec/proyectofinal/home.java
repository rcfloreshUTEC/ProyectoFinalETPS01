package sv.edu.utec.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sv.edu.utec.proyectofinal.adaptador.FarmaciasAdapter;
import sv.edu.utec.proyectofinal.datos.baseHelper;
import sv.edu.utec.proyectofinal.entidades.Farmacia;

public class home extends AppCompatActivity {

    private SQLiteOpenHelper baseHelper;

    Spinner spDepartamento, spMunicipio;
    EditText edtSmedicamento;
    Button btnSBuscar;
    GridView grdvFarmacias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        baseHelper = new baseHelper(this);
        SQLiteDatabase db = baseHelper.getReadableDatabase();

        spDepartamento = findViewById(R.id.spSDepartamento);
        spMunicipio = findViewById(R.id.spSMunicipio);

        edtSmedicamento = findViewById(R.id.edtSMedicamento);
        btnSBuscar = findViewById(R.id.btnSBuscar);
        grdvFarmacias = findViewById(R.id.grdvFarmacias);

        Cursor departamentosCursor = db.query("departamentos", null, null, null, null, null, null);

        ArrayAdapter<String> departamentosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> municipioAdapter = ArrayAdapter.createFromResource(
                this, R.array.municipios_array, android.R.layout.simple_spinner_item);
        municipioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMunicipio.setAdapter(municipioAdapter);


        if (departamentosCursor.moveToFirst()) {
            int departamentoColumnIndex = departamentosCursor.getColumnIndex("departamento");
            do {
                String departamento = departamentosCursor.getString(departamentoColumnIndex);
                departamentosAdapter.add(departamento);
            } while (departamentosCursor.moveToNext());
        }

        departamentosCursor.close();
        db.close();
        spDepartamento.setAdapter(departamentosAdapter);

        spDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDepartamento = departamentosAdapter.getItem(position);

                int selectedDepartamentoId = getIdDepartamento(selectedDepartamento);

                updateMunicipiosSpinner(selectedDepartamentoId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //return false;
    }

    private int getIdMunicipioSeleccionado() {
        Municipio municipio = (Municipio) spMunicipio.getSelectedItem();

        int idMunicipio = municipio.getId();

        return idMunicipio;
    }
    private void buscarFarmacias() {
        String medicamento = edtSmedicamento.getText().toString();
         int idMunicipio = getIdMunicipioSeleccionado();

        SQLiteDatabase db = baseHelper.getReadableDatabase();

        String consulta = "SELECT f.nombre FROM farmacias AS f " +
                "INNER JOIN medicamento AS m ON f.id = m.idFarmacia " +
                "WHERE m.descripcion LIKE '%" + medicamento + "%' " +
                "AND f.idMunicipio = " + idMunicipio;

        Cursor cursor = db.rawQuery(consulta, null);

        List<String> nombresFarmacias = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String nombreFarmacia = String.valueOf(cursor.getColumnIndex("nombre"));
                nombresFarmacias.add(nombreFarmacia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Mostrar los resultados en el GridView
        ArrayAdapter<String> farmaciaAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, nombresFarmacias);
        grdvFarmacias.setAdapter(farmaciaAdapter);
    }

    private int obtenerIdMunicipioSeleccionado() {
        Municipio municipio = (Municipio) spMunicipio.getSelectedItem();

        int idMunicipio = municipio.getId();

        return idMunicipio;
    }

    private int getIdDepartamento(String departamento) {
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        String[] projection = {"id"};
        String selection = "departamento = ?";
        String[] selectionArgs = {departamento};
        Cursor cursor = db.query("departamentos", projection, selection, selectionArgs, null, null, null);
        int idDepartamento = -1;
        if (cursor.moveToFirst()) {
            int idDepartamentoColumnIndex = cursor.getColumnIndex("id");
            idDepartamento = cursor.getInt(idDepartamentoColumnIndex);
        }
        cursor.close();
        db.close();
        return idDepartamento;
    }

    private void updateMunicipiosSpinner(int idDepartamento) {
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        String[] projection = {"municipio"};
        String selection = "idDepartamento = ?";
        String[] selectionArgs = {String.valueOf(idDepartamento)};
        Cursor municipiosCursor = db.query("municipios", projection, selection, selectionArgs, null, null, null);
        ArrayAdapter<String> municipiosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        if (municipiosCursor.moveToFirst()) {
            int municipioColumnIndex = municipiosCursor.getColumnIndex("municipio");
            do {
                String municipio = municipiosCursor.getString(municipioColumnIndex);
                municipiosAdapter.add(municipio);
            } while (municipiosCursor.moveToNext());
        }
        municipiosCursor.close();
        db.close();
        spMunicipio.setAdapter(municipiosAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio,  menu);
        return super.onCreateOptionsMenu(menu);
    }
}