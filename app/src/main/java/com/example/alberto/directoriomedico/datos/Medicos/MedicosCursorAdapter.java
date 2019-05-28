package com.example.alberto.directoriomedico.datos.Medicos;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.alberto.directoriomedico.R;
import com.example.alberto.directoriomedico.datos.Medicos.MedicosContract;

public class MedicosCursorAdapter extends CursorAdapter {

    public MedicosCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_medico,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameText = (TextView)view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView)view.findViewById(R.id.iv_avatar);

        String name = cursor.getString(cursor.getColumnIndex(MedicosContract.MedicoEntry.NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(MedicosContract.MedicoEntry.AVATAR_URI));

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
