package br.contatos.contato;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.contatos.R;


public class AdapterFragment extends RecyclerView.Adapter<AdapterFragment.ContatoViewHolder> {
    private final List<Contato> contato;
    private final List<String> contatoIds;
    private final FragmentActivity activity;

    AdapterFragment(List<Contato> contato, List<String> contatoIds, FragmentActivity activity) {
        this.contato = contato;
        this.contatoIds = contatoIds;
        this.activity = activity;
    }

    static class ContatoViewHolder extends RecyclerView.ViewHolder {
        private final TextView nomeView;
        private final TextView celularView;
        private final TextView typeView;

        ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.idTVContactName);
            celularView = itemView.findViewById(R.id.idTVContactNumber);
            typeView = itemView.findViewById(R.id.idTVContactType);
        }
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contato_item, parent, false);
        return new ContatoViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ContatoViewHolder viewHolder, int i) {
        final String id = contatoIds.get(i);
        viewHolder.nomeView.setText(contato.get(i).getNome());

        List<Contato.Telefone> telefones = contato.get(i).getTelefones();
        if (!telefones.isEmpty()) {
            Contato.Telefone telefone = telefones.get(0);
            viewHolder.celularView.setText(telefone.getNumero());
            viewHolder.typeView.setText(telefone.getTipo());
        }

        Log.i("AdapterFragment", contato.get(i).getNome() + " - " + contatoIds.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("id", id);

                EditarFragment editarFragment = new EditarFragment();
                editarFragment.setArguments(b);
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameMain, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contato.size();
    }
}
