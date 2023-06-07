package sv.edu.utec.proyectofinal.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import sv.edu.utec.proyectofinal.entidades.EntUsuarios;

public class daoUsuarios extends baseHelper{

    Context context;

    public daoUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario (String nombres, String apellidos, String fechaNaci, String email, String password)
    {
        long id = 0;
        try
        {
            baseHelper baseHelper = new baseHelper(context);
            SQLiteDatabase db = baseHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("nombres", nombres);
            cv.put("apellidos", apellidos);
            cv.put("fechaNac", fechaNaci);
            cv.put("email",email);
            cv.put("password",password);
            id = db.insert(TABLAUsuarios, null, cv);
        }
        catch (Exception ex)
        {
            ex.toString();
        }
        return id;
    }

    public ArrayList<EntUsuarios> selectUsuario(){
        baseHelper baseHelper = new baseHelper(context);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ArrayList<EntUsuarios> listaUser = new ArrayList<>();
        EntUsuarios entUsuarios = null;
        Cursor cursorUser = null;
        cursorUser = db.rawQuery("SELECT *FROM "+TABLAUsuarios, null);
        if(cursorUser.moveToFirst())
        {
            do {
                entUsuarios = new EntUsuarios();

                entUsuarios.setId(cursorUser.getInt(0));
                entUsuarios.setNombres(cursorUser.getString(1));
                entUsuarios.setApellidos(cursorUser.getString(2));
                entUsuarios.setFechaNac(cursorUser.getString(3));
                entUsuarios.setEmail(cursorUser.getString(4));
                entUsuarios.setPassword(cursorUser.getString(5));
                listaUser.add(entUsuarios);

            }while(cursorUser.moveToNext());
        }
        cursorUser.close();
        return  listaUser;
    }

    public int buscar (String u)
    {
        baseHelper baseHelper = new baseHelper(context);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ArrayList<EntUsuarios> listaUser = new ArrayList<>();
        EntUsuarios entUsuarios = null;
        Cursor cursorUser = null;

        int x=0;
        listaUser = selectUsuario();

        for(EntUsuarios us:listaUser)
        {
            if(us.getEmail().equals(u)){
                x++;
            }
        }
        return x;
    }
    public int login(EditText email, EditText password) {
        int a = 0;
        baseHelper baseHelper = new baseHelper(context);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ArrayList<EntUsuarios> listaUser = new ArrayList<>();
        EntUsuarios entUsuarios = null;
        Cursor cursorUser = null;
        cursorUser = db.rawQuery("SELECT email, password FROM '"+TABLAUsuarios+"' WHERE email = '" + email + "'  AND password = '" + password + "';", null);
        if(cursorUser!=null&&cursorUser.moveToFirst())
        {
            do {
                if(cursorUser.getString(0).equals(email)&&cursorUser.getString(1).equals(password))
                {
                    a++;
                }
            }while(cursorUser.moveToNext());
        }
        return a;
    }

    public EntUsuarios getUsuario(String username, String password){
        baseHelper baseHelper = new baseHelper(context);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ArrayList<EntUsuarios> listaUser = new ArrayList<>();
        listaUser = selectUsuario();
        for (EntUsuarios us:listaUser){
            if(us.getEmail().equals(username)&&us.getPassword().equals(password))
            {
                return us;
            }
        }
        return null;
    }

    public EntUsuarios getUsuarioById(int id){
        baseHelper baseHelper = new baseHelper(context);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        ArrayList<EntUsuarios> listaUser = new ArrayList<>();
        listaUser = selectUsuario();
        for (EntUsuarios us:listaUser){
            if(us.getId()==id)
            {
                return us;
            }
        }
        return null;
    }

}