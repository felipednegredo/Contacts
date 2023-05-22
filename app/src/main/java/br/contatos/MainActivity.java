package br.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.contatos.contato.MainFragment;

import br.contatos.R;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main); //activity_main.xml
            getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new MainFragment()).commit();
        }
}