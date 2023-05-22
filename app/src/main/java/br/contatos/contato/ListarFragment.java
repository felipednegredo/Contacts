package br.contatos.contato;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.contatos.R;


public class ListarFragment extends Fragment {

    public ListarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listar_contatos, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerViewContatos = v.findViewById(R.id.idRVContacts);

        CollectionReference collectionContatos = db.collection("Contatos");
        collectionContatos.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
                    recyclerViewContatos.setLayoutManager(manager);
                    recyclerViewContatos.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));
                    recyclerViewContatos.setHasFixedSize(true);
                    List<Contato> contatos = task.getResult().toObjects(Contato.class);
                    List<String> contatoIds = new ArrayList<String>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        contatoIds.add(document.getId());
                    }
                    AdapterFragment adapterMaes = new AdapterFragment(contatos, contatoIds, getActivity());
                    recyclerViewContatos.setAdapter(adapterMaes);
                } else {
                    Toast.makeText(getActivity(), "Erro ao buscar as contatos!", Toast.LENGTH_LONG).show();
                    Log.d("ListarContatos", "mensagem de erro: ", task.getException());
                }
            }
        });
        return v;
    }
}