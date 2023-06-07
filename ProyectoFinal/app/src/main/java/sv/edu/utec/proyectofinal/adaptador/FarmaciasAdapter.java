package sv.edu.utec.proyectofinal.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sv.edu.utec.proyectofinal.R;
import sv.edu.utec.proyectofinal.entidades.Farmacia;

public class FarmaciasAdapter extends ArrayAdapter<Farmacia> {
    private Context context;

    TextView txtNombre, txtMunicipio, txtEstado;

    public FarmaciasAdapter(Context context, List<Farmacia> farmacias) {
        super(context, 0, farmacias);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_farmacia, parent, false);
        }

        Farmacia farmacia = getItem(position);

        txtNombre = view.findViewById(R.id.txtNombre);
        txtMunicipio = view.findViewById(R.id.txtMunicipio);
        txtEstado = view.findViewById(R.id.txtEstado);

        txtNombre.setText(farmacia.getNombre());
        txtMunicipio.setText("Municipio: " + farmacia.getIdMunicipio());
        txtEstado.setText(farmacia.isEsActiva() ? "Activa" : "Inactiva");

        return view;
    }

}
