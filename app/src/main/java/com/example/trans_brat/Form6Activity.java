package com.example.trans_brat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Form6Activity extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    private List<String> selectedImagePaths;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_6), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //requiredQuestions();

        /* Upload de imagens */
        selectedImagePaths = new ArrayList<>();
        RecyclerView imagesRecyclerView = findViewById(R.id.section_2_question_1_imagerecyclerview);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        imageAdapter = new ImageAdapter(this, selectedImagePaths);
        imagesRecyclerView.setAdapter(imageAdapter);
        findViewById(R.id.section_2_question_1_input).setOnClickListener(v -> openGallery());

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 5 */
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form6Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /* (Avançar) Avançando para o formulário ? */
        /*
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form6Activity.this, Form6Activity.class);
                startActivity(intent);
            }
        });*/
    }

    /* Função para perguntas obrigatórias */
    private void requiredQuestions() {
        /* Definindo perguntas obrigatórias */
        int[] questions_ids = {
                R.id.section_3_question_1
        };

        /* Para cada pergunta obrigatória */
        for (int question_id : questions_ids) {
            /* Obtendo texto pelos ids das perguntas */
            TextView textView = findViewById(question_id);
            String text = textView.getText().toString();

            /* Adicionando asteriscos nas perguntas obrigatórias */
            String textWithAsterisk = text + " *";
            textView.setText(textWithAsterisk);

            // Verificando se o texto não está vazio
            if (textWithAsterisk.length() > 0) {
                SpannableString spannableString = new SpannableString(textWithAsterisk);

                // Aplicando a cor vermelha ao último caractere (asteriscos)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecione até 10 imagens"), PICK_IMAGES_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) { // Se múltiplas imagens foram selecionadas
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count && i < 10; i++) { // Limitar a 10 imagens
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        selectedImagePaths.add(getRealPathFromURI(imageUri));
                    }
                } else if (data.getData() != null) { // Se apenas uma imagem foi selecionada
                    Uri imageUri = data.getData();
                    selectedImagePaths.add(getRealPathFromURI(imageUri));
                }
                imageAdapter.notifyDataSetChanged();
            }
        }
    }

    // Converter URI em caminho real do arquivo
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }
}