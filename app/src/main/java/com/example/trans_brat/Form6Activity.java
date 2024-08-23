package com.example.trans_brat;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Form6Activity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImages;
    private static final int IMAGES_MAX = 10;


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

        requiredQuestions();

        /* Evento para iniciar o launcher que abre a galeria do celular */
        eventGalleryInitLauncher();

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
        buttonCancel.setOnClickListener(v -> cancel());

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
                R.id.section_1_question_1,
                R.id.section_2_question_1
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
            if (!textWithAsterisk.isEmpty()) {
                SpannableString spannableString = new SpannableString(textWithAsterisk);

                // Aplicando a cor vermelha ao último caractere (asteriscos)
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), textWithAsterisk.length() - 1, textWithAsterisk.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form6Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form6Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Evento para iniciar o launcher que abre a galeria do celular */
    private void eventGalleryInitLauncher() {
        selectedImages = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, selectedImages);

        RecyclerView recyclerView = findViewById(R.id.section_2_question_1_picture);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Grade de 3 colunas
        recyclerView.setAdapter(imageAdapter);

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImages.clear();
                        if (result.getData().getClipData() != null) {
                            int count = result.getData().getClipData().getItemCount();
                            int tooHeavy = 0;
                            if (count > IMAGES_MAX) {
                                Toast.makeText(this, "Você só pode selecionar até 10 imagens", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                                if (isImageValid(imageUri)) {
                                    selectedImages.add(imageUri);
                                } else {
                                    tooHeavy++;
                                }
                            }

                            /* Alertando caso alguma imagem ultrapasse 1MB */
                            if(tooHeavy > 0) {
                                if(tooHeavy == 1) {
                                    Toast.makeText(Form6Activity.this, tooHeavy + " imagem ultrapassa o limite de 1MB", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Form6Activity.this, tooHeavy + " imagens ultrapassam o limite de 1MB", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else if (result.getData().getData() != null) {
                            // Caso apenas uma imagem foi selecionada
                            Uri imageUri = result.getData().getData();
                            selectedImages.add(imageUri);
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                });
        Button buttonOpenGallery = findViewById(R.id.section_2_question_1_input);
        buttonOpenGallery.setOnClickListener(view -> openGallery());
    }

    /* Função para abrir a galeria do celular ao clicar no botão */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        galleryLauncher.launch(intent);
    }

    /* Função para verificar se uma imagem ultrapassa 1MB */
    private boolean isImageValid(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream != null) {
                long fileSize = inputStream.available();
                inputStream.close();
                return fileSize <= 1 * 1024 * 1024; // 1 MB
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}