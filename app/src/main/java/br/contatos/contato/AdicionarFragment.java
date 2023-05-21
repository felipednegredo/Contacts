package br.contatos.contato;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.contatos.R;


public class AdicionarFragment extends Fragment {

    private EditText etNome;
    private EditText etNumero;

    private FirebaseFirestore db;

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_criar_contato, container, false);

        db = FirebaseFirestore.getInstance();

        etNome = v.findViewById(R.id.editTextNome);
        etNumero = v.findViewById(R.id.editTextNumero);


        Button btnSalvar = v.findViewById(R.id.buttonAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etNumero.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        } else {
            Contato c = new Contato();
            c.setNome(etNome.getText().toString());
            c.setTelefone(0, "Telefone da Casa: " + etNumero.getText().toString());
            c.setTelefone(1, "Telefone Celular: " + etNumero.getText().toString());
            c.setTelefone(2, "Telefone do Trabalho: " + etNumero.getText().toString());

            CollectionReference collectionContatos = db.collection("Contatos");
            collectionContatos.add(c).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getActivity(), "Contato salvo!", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Erro ao salvar!", Toast.LENGTH_LONG).show();
                    Log.d("AdicionarContato", "mensagem de erro: ", e);
                }
            });
        }
    }

}