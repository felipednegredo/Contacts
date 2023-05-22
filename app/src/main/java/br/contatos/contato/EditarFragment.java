package br.contatos.contato;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import br.contatos.R;

public class EditarFragment extends Fragment {

    private FirebaseFirestore db;
    private Contato c;
    private EditText etNome;
    private EditText etNumero;

    private Spinner etTipo;

    public EditarFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();

        View v = inflater.inflate(R.layout.fragment_editar_contato, container, false);

        etNome = v.findViewById(R.id.idTVContactName);
        etNumero = v.findViewById(R.id.idTVContactNumber);
        etTipo = v.findViewById(R.id.idSpnPhoneType);

        Bundle bundle = getArguments();

        String id;
        if (bundle != null) {
            id = bundle.getString("id");
            if (id != null) {
                DocumentReference documentContato = db.collection("Contatos").document(id);
                documentContato.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        c = task.getResult().toObject(Contato.class);
                        etNome.setText(c.getNome());
                        etNumero.setText(c.getTelefone(0));
                        etTipo.setSelection(c.getTipo(0));
                    }
                });
            }
        }

        //call editar function when item in l

        private void editar (String id){
            c.setNome(etNome.getText().toString());
            c.setTelefone(0, etNumero.getText().toString());
            c.setTipo(0, etTipo.getSelectedItemPosition());

            db.collection("Contatos").document(id)
                    .set(c)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Contato editado!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ListarFragment()).commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("EditarContato", "Erro: ", e);
                        }
                    });
        }

        private void excluir (String id){
            db.collection("Contatos").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Contato excluído!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMae, new ListarFragment()).commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ExcluirContato", "Erro: ", e);
                        }
                    });
        }


        private void mostrarDialogExclusao (String id_contato){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_excluir_contato);
            builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    excluir(id_contato);
                }
            });
            builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Não faz nada
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
