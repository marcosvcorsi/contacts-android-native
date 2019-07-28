package com.example.contactsandroidnative.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contactsandroidnative.R;
import com.example.contactsandroidnative.data.model.Contato;
import com.example.contactsandroidnative.util.ImageUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaContatoAdapter extends BaseAdapter {

    private Context context;
    private List<Contato> dataSource = new ArrayList<>();

    public ListaContatoAdapter(Context context, List<Contato> list){
        this.context = context;

        if(list != null){
            this.dataSource = list;
        }
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Contato getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_lista_contatos, viewGroup, false);
        }

        Contato contato = getItem(i);

        if(contato != null){
            ImageView viewImg = view.findViewById(R.id.item_lista_image);
            TextView viewNome = view.findViewById(R.id.item_lista_nome);
            TextView viewEmail = view.findViewById(R.id.item_lista_email);

            viewNome.setText(contato.getNome());
            viewEmail.setText(contato.getEmail());

            ImageUtils.setImage(viewImg, contato.getImagem(), 60, 60);
        }

        return view;
    }
}
