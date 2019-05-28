package com.example.alberto.directoriomedico.datos.Pacientes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosContract;

public class PacientesCursorAdapter extends CursorAdapter {


    public PacientesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_paciente,viewGroup,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
      //  Button btnEditar = (Button)view.findViewById(R.id.btnEditP);
        TextView nameText = (TextView)view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView)view.findViewById(R.id.iv_avatar);

        String name = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.AVATAR_URI));

        nameText.setText(name);





        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage){
                    @Override
                    protected void setResource(Bitmap resource){
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(),resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });
    }
}
