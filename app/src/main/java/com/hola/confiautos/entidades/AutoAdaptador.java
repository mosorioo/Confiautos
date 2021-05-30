package com.hola.confiautos.entidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.hola.confiautos.R;

import java.io.File;
import java.util.List;

public class AutoAdaptador extends ArrayAdapter<Auto> {

    List<Auto> autoList;
    public Context context;
    public int resourseLayout;
    public ImageView imgPost;
    public CardView cardImagen;
    public AutoAdaptador(@NonNull Context context, int resource, @NonNull List<Auto> objects) {
        super(context, resource, objects);
        this.autoList=objects;
        this.context=context;
        this.resourseLayout=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_view_auto,null);

        Auto autoObjeto=autoList.get(position);

        ImageView imagen=view.findViewById(R.id.imgAutoCard);
        TextView marca=view.findViewById(R.id.marcaTV);
        TextView modelo=view.findViewById(R.id.modeloTV);
        TextView anio=view.findViewById(R.id.anioTV);
        TextView nroChasis=view.findViewById(R.id.nroChasisTV);
        TextView nroMotor=view.findViewById(R.id.nroMotorTV);

        marca.setText(autoObjeto.getMarca());
        modelo.setText(autoObjeto.getModelo());
        anio.setText(autoObjeto.getAnio().toString());
        nroChasis.setText(autoObjeto.getMarca());
        nroMotor.setText(autoObjeto.getNroMotor());

        if (autoObjeto.getFotoAuto() !=null){
            File imagenfile=new File(autoObjeto.getFotoAuto());
            Bitmap bitmap = BitmapFactory.decodeFile(imagenfile.getAbsolutePath());
            imagen.setImageBitmap(bitmap);
        }else{
            imagen.setImageResource(R.drawable.iconoauto);
        }

        return view;
    }
}
