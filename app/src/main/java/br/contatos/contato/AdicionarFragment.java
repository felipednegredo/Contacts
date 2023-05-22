package br.contatos.contato;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner spinner;

    private FirebaseFirestore db;

    public AdicionarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_criar_contato, container, false);

        etNome = view.findViewById(R.id.idTVContactName);
        etNumero = view.findViewById(R.id.idTVContactNumber);
        spinner = view.findViewById(R.id.idSpnPhoneType);

        Button btSalvar = view.findViewById(R.id.buttonAdicionar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        //make with button AdicionarTelefone call a new fragment_telefone_info.xml and add a new phone number to the contact

        Button btAdicionarTelefone = view.findViewById(R.id.idBtnAddPhoneNumber);

        btAdicionarTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;

    }

    private void adicionar() {
        String nome = etNome.getText().toString();
        String numero = etNumero.getText().toString();
        String tipo = spinner.getSelectedItem().toString();

        if (nome.isEmpty() || numero.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference contatosRef = db.collection("contatos");
        Contato contato = new Contato(nome, numero, tipo);

        contatosRef.add(contato).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Contato adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        Log.d("AdicionarFragment", "Contato adicionado com sucesso");
                        etNome.setText("");
                        etNumero.setText("");
                        spinner.setSelection(0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),
                                "Erro ao adicionar contato: " + e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("AdicionarFragment", "Erro ao adicionar contato", e);
                    }
                });
    }



}
