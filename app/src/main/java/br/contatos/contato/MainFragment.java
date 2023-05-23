package br.contatos.contato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.contatos.R;


public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //open listar_contantos.xml and return it
        View view = inflater.inflate(R.layout.fragment_listar_contatos, container, false);

        FloatingActionButton btAdicionar = view.findViewById(R.id.idFABadd);
        //set click listener
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open AdicionarFragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new AdicionarFragment()).commit();
            }
        });

        return view;
    }
}