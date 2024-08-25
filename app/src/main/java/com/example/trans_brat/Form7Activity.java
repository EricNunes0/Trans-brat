package com.example.trans_brat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Form7Activity extends AppCompatActivity {
    private final String logId = "Form7Activity_LOG";
    /* Respostas do formulário anterior
    [0] Id da resposta
    [1] Id do TextView de exibição
    [2] Tipo de resposta
    -> 0: Texto
    */
    private final String[][] allAnswers = {
            {"F2_S1_Q2", String.valueOf(R.id.F2_S1_Q2), "0"},
            {"F2_S1_Q3", String.valueOf(R.id.F2_S1_Q3), "0"},
            {"F2_S2_Q2", String.valueOf(R.id.F2_S2_Q2), "0"},
            {"F2_S2_Q3", String.valueOf(R.id.F2_S2_Q3), "0"},
            {"F2_S3_Q2", String.valueOf(R.id.F2_S3_Q2), "0"},
            {"F2_S3_Q3", String.valueOf(R.id.F2_S3_Q3), "0"},
            {"F2_S3_Q4", String.valueOf(R.id.F2_S3_Q4), "0"},
            {"F2_S3_Q5", String.valueOf(R.id.F2_S3_Q5), "0"},
            {"F2_S3_Q6", String.valueOf(R.id.F2_S3_Q6), "0"},
            {"F2_S3_Q7", String.valueOf(R.id.F2_S3_Q7), "0"},
            {"F2_S3_Q8", String.valueOf(R.id.F2_S3_Q8), "0"},
            {"F2_S3_Q9", String.valueOf(R.id.F2_S3_Q9), "0"},
            {"F2_S4_Q2", String.valueOf(R.id.F2_S4_Q2), "0"},
            {"F2_S4_Q3", String.valueOf(R.id.F2_S4_Q3), "0"},
            {"F2_S4_Q4", String.valueOf(R.id.F2_S4_Q4), "0"},
            {"F2_S5_Q1", String.valueOf(R.id.F2_S5_Q1), "0"},
            /*"F3_S1_Q2",
            "F3_S1_Q3",
            "F3_S1_Q4",
            "F3_S1_Q5",
            "F3_S1_Q6",
            "F3_S1_Q7",
            "F3_S1_Q8",
            "F3_S1_Q9",
            "F3_S1_Q10",
            "F3_S1_Q11",
            "F3_S1_Q12",
            "F3_S2_Q2",
            "F3_S2_Q3",
            "F3_S3_Q2",
            "F3_S3_Q3",
            "F3_S3_Q4",
            "F3_S3_Q5",
            "F3_S4_Q2",
            "F3_S4_Q3",
            "F3_S4_Q4",
            "F3_S4_Q5",
            "F3_S4_Q6",
            "F3_S4_Q7",
            "F3_S4_Q8",
            "F3_S4_Q9",
            "F3_S5_Q2",
            "F3_S5_Q3",
            "F3_S5_Q4",
            "F3_S5_Q5",
            "F3_S5_Q6",
            "F3_S5_Q7",
            "F3_S5_Q8",
            "F3_S5_Q9",
            "F3_S5_Q10",
            "F3_S6_Q2",
            "F3_S6_Q3",
            "F3_S6_Q4",
            "F3_S6_Q5",
            "F3_S6_Q6",
            "F3_S7_Q2",
            "F3_S7_Q3",
            "F3_S7_Q4",
            "F3_S7_Q5",
            "F3_S7_Q6",
            "F3_S7_Q7",
            "F3_S7_Q8",
            "F3_S8_Q1",
            "F3_S8_Q2",
            "F3_S8_Q3",
            "F3_S8_Q4",
            "F3_S8_Q5",
            "F3_S8_Q6",
            "F4_S0_Q1",
            "F4_S1_M1_Q2",
            "F4_S1_M1_Q3",
            "F4_S1_M1_Q4",
            "F4_S1_M1_Q5",
            "F4_S1_M1_Q6",
            "F4_S1_M1_Q7",
            "F4_S1_M1_Q8",
            "F4_S1_M1_Q9",
            "F4_S1_M1_Q10",
            "F4_S1_M1_Q11",
            "F4_S1_M1_Q12",
            "F4_S1_M2_Q2",
            "F4_S1_M2_Q3",
            "F4_S1_M3_Q2",
            "F4_S1_M3_Q3",
            "F4_S1_M3_Q4",
            "F4_S1_M3_Q5",
            "F4_S1_M4_Q2",
            "F4_S1_M4_Q3",
            "F4_S1_M4_Q4",
            "F4_S1_M4_Q5",
            "F4_S1_M4_Q6",
            "F4_S1_M4_Q7",
            "F4_S1_M4_Q8",
            "F4_S1_M4_Q9",
            "F4_S1_M5_Q2",
            "F4_S1_M5_Q3",
            "F4_S1_M5_Q4",
            "F4_S1_M5_Q5",
            "F4_S1_M5_Q6",
            "F4_S1_M5_Q7",
            "F4_S1_M5_Q8",
            "F4_S1_M5_Q9",
            "F4_S1_M5_Q10",
            "F4_S1_M6_Q2",
            "F4_S1_M6_Q3",
            "F4_S1_M6_Q4",
            "F4_S1_M6_Q5",
            "F4_S1_M6_Q6",
            "F4_S1_M7_Q2",
            "F4_S1_M7_Q3",
            "F4_S1_M7_Q4",
            "F4_S1_M7_Q5",
            "F4_S1_M7_Q6",
            "F4_S1_M7_Q7",
            "F4_S1_M7_Q8",
            "F4_S1_M8_Q1",
            "F4_S1_M8_Q2",
            "F4_S1_M8_Q3",
            "F4_S1_M8_Q4",
            "F4_S1_M8_Q5",
            "F4_S1_M8_Q6",
            "F4_S2_M1_Q2",
            "F4_S2_M1_Q3",
            "F4_S2_M1_Q4",
            "F4_S2_M1_Q5",
            "F4_S2_M1_Q6",
            "F4_S2_M1_Q7",
            "F4_S2_M1_Q8",
            "F4_S2_M1_Q9",
            "F4_S2_M1_Q10",
            "F4_S2_M1_Q11",
            "F4_S2_M1_Q12",
            "F4_S2_M2_Q2",
            "F4_S2_M2_Q3",
            "F4_S2_M3_Q2",
            "F4_S2_M3_Q3",
            "F4_S2_M3_Q4",
            "F4_S2_M3_Q5",
            "F4_S2_M4_Q2",
            "F4_S2_M4_Q3",
            "F4_S2_M4_Q4",
            "F4_S2_M4_Q5",
            "F4_S2_M4_Q6",
            "F4_S2_M4_Q7",
            "F4_S2_M4_Q8",
            "F4_S2_M4_Q9",
            "F4_S2_M5_Q2",
            "F4_S2_M5_Q3",
            "F4_S2_M5_Q4",
            "F4_S2_M5_Q5",
            "F4_S2_M5_Q6",
            "F4_S2_M5_Q7",
            "F4_S2_M5_Q8",
            "F4_S2_M5_Q9",
            "F4_S2_M5_Q10",
            "F4_S2_M6_Q2",
            "F4_S2_M6_Q3",
            "F4_S2_M6_Q4",
            "F4_S2_M6_Q5",
            "F4_S2_M6_Q6",
            "F4_S2_M7_Q2",
            "F4_S2_M7_Q3",
            "F4_S2_M7_Q4",
            "F4_S2_M7_Q5",
            "F4_S2_M7_Q6",
            "F4_S2_M7_Q7",
            "F4_S2_M7_Q8",
            "F4_S2_M8_Q1",
            "F4_S2_M8_Q2",
            "F4_S2_M8_Q3",
            "F4_S2_M8_Q4",
            "F4_S2_M8_Q5",
            "F4_S2_M8_Q6",
            "F4_S3_M1_Q2",
            "F4_S3_M1_Q3",
            "F4_S3_M1_Q4",
            "F4_S3_M1_Q5",
            "F4_S3_M1_Q6",
            "F4_S3_M1_Q7",
            "F4_S3_M1_Q8",
            "F4_S3_M1_Q9",
            "F4_S3_M1_Q10",
            "F4_S3_M1_Q11",
            "F4_S3_M1_Q12",
            "F4_S3_M2_Q2",
            "F4_S3_M2_Q3",
            "F4_S3_M3_Q2",
            "F4_S3_M3_Q3",
            "F4_S3_M3_Q4",
            "F4_S3_M3_Q5",
            "F4_S3_M4_Q2",
            "F4_S3_M4_Q3",
            "F4_S3_M4_Q4",
            "F4_S3_M4_Q5",
            "F4_S3_M4_Q6",
            "F4_S3_M4_Q7",
            "F4_S3_M4_Q8",
            "F4_S3_M4_Q9",
            "F4_S3_M5_Q2",
            "F4_S3_M5_Q3",
            "F4_S3_M5_Q4",
            "F4_S3_M5_Q5",
            "F4_S3_M5_Q6",
            "F4_S3_M5_Q7",
            "F4_S3_M5_Q8",
            "F4_S3_M5_Q9",
            "F4_S3_M5_Q10",
            "F4_S3_M6_Q2",
            "F4_S3_M6_Q3",
            "F4_S3_M6_Q4",
            "F4_S3_M6_Q5",
            "F4_S3_M6_Q6",
            "F4_S3_M7_Q2",
            "F4_S3_M7_Q3",
            "F4_S3_M7_Q4",
            "F4_S3_M7_Q5",
            "F4_S3_M7_Q6",
            "F4_S3_M7_Q7",
            "F4_S3_M7_Q8",
            "F4_S3_M8_Q1",
            "F4_S3_M8_Q2",
            "F4_S3_M8_Q3",
            "F4_S3_M8_Q4",
            "F4_S3_M8_Q5",
            "F4_S3_M8_Q6",
            "F4_S4_M1_Q2",
            "F4_S4_M1_Q3",
            "F4_S4_M1_Q4",
            "F4_S4_M1_Q5",
            "F4_S4_M1_Q6",
            "F4_S4_M1_Q7",
            "F4_S4_M1_Q8",
            "F4_S4_M1_Q9",
            "F4_S4_M1_Q10",
            "F4_S4_M1_Q11",
            "F4_S4_M1_Q12",
            "F4_S4_M2_Q2",
            "F4_S4_M2_Q3",
            "F4_S4_M3_Q2",
            "F4_S4_M3_Q3",
            "F4_S4_M3_Q4",
            "F4_S4_M3_Q5",
            "F4_S4_M4_Q2",
            "F4_S4_M4_Q3",
            "F4_S4_M4_Q4",
            "F4_S4_M4_Q5",
            "F4_S4_M4_Q6",
            "F4_S4_M4_Q7",
            "F4_S4_M4_Q8",
            "F4_S4_M4_Q9",
            "F4_S4_M5_Q2",
            "F4_S4_M5_Q3",
            "F4_S4_M5_Q4",
            "F4_S4_M5_Q5",
            "F4_S4_M5_Q6",
            "F4_S4_M5_Q7",
            "F4_S4_M5_Q8",
            "F4_S4_M5_Q9",
            "F4_S4_M5_Q10",
            "F4_S4_M6_Q2",
            "F4_S4_M6_Q3",
            "F4_S4_M6_Q4",
            "F4_S4_M6_Q5",
            "F4_S4_M6_Q6",
            "F4_S4_M7_Q2",
            "F4_S4_M7_Q3",
            "F4_S4_M7_Q4",
            "F4_S4_M7_Q5",
            "F4_S4_M7_Q6",
            "F4_S4_M7_Q7",
            "F4_S4_M7_Q8",
            "F4_S4_M8_Q1",
            "F4_S4_M8_Q2",
            "F4_S4_M8_Q3",
            "F4_S4_M8_Q4",
            "F4_S4_M8_Q5",
            "F4_S4_M8_Q6",
            "F5_S1_Q2",
            "F5_S1_Q3",
            "F5_S1_Q4",
            "F5_S2_Q2",
            "F5_S2_Q3",
            "F5_S2_Q4",
            "F5_S3_Q2",
            "F5_S3_Q3",
            "F5_S3_Q4",
            "F5_S4_Q2",
            "F5_S4_Q3",
            "F5_S4_Q4",
            "F6_S1_Q2",
            "F6_S2_Q1"*/
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_7);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_7), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        /* Adicionando respostas dos formulários anteriores ao formulário atual */
        addPreviousFormAnswersToConfirmation();

        /* Criando PDF */
        //Button buttonPDF = findViewById(R.id.create_pdf_button);
        //buttonPDF.setOnClickListener(v -> createPDF());

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonNext = findViewById(R.id.next_button);

        /* (Voltar) Voltando para o formulário 5 */
        buttonBack.setOnClickListener(v -> finish());

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Avançar) Avançando para o formulário ? */
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Form7Activity.this, Form7Activity.class);
                try {
                    sendAnswersToNextForm(intent);
                } catch (Exception e) {
                    Log.e(logId, "Erro ao enviar respostas:" + e);
                }
                //startActivity(intent);
            }
        });
    }

    /* Adicionando respostas dos formulários anteriores ao formulário atual */
    private void addPreviousFormAnswersToConfirmation() {
        for(String[] answer : allAnswers) {
            try {
                String answerType = answer[2];
                if(Objects.equals(answerType, "0")) {// Se a resposta for do tipo texto
                    String answerContent = getIntent().getStringExtra(answer[0]);
                    TextView answerTextView = findViewById(Integer.parseInt(answer[1]));
                    answerTextView.setText(answerContent);
                    Log.d(logId, answer[0] + " - " + answerContent);
                }
            } catch (Exception e) {
                Log.e(logId, "" + e);
            }
        }
    }

    /* Função para enviar respostas para o formulário seguinte */
    private void sendAnswersToNextForm(Intent intent) {
        /* Enviando respostas dos formulários anteriores */
        /*for(int i = 0; i <= previousAnswersIds.length - 1; i++) {
            String toSendAnswerId = previousAnswersIds[i];
            String answer = getIntent().getStringExtra(toSendAnswerId);
            intent.putExtra(toSendAnswerId, answer);
            Log.i(logId, toSendAnswerId + " - " + answer);
        }*/
        /* Enviando respostas deste formulário */
        /*int j = 0;
        for(int i = 0; i <= toSendAnswersIds.length - 1; i++) {
            if(i < all_questions.length) {
                //Log.e(logId, i + " Index do all_questions: " + toSendAnswersIds[i + j] + "\nTipo do Index: " + all_questions[i][2]);
                if (all_questions[i][2] == 3) {
                    getCheckboxAnswers(intent, i, i + j);
                    j += 5;
                } if (all_questions[i][2] == 4) {
                    getRecyclerViewAnswers(intent, i);
                    j += 9;
                } else {
                    String toSendAnswerId = toSendAnswersIds[i + j];
                    int requiredQuestionId = all_questions[i][1];
                    int requiredQuestionType = all_questions[i][2];
                    String answer = getInputAnswer(requiredQuestionId, requiredQuestionType);
                    Log.i(logId, toSendAnswerId + " - " + answer);
                    j += 0;
                }
            } else {
                break;
            }
        }*/
    }

    /* Função ao cancelar formulário */
    private void cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form7Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_warning, null);
        builder.setView(dialogLayout);
        builder.setTitle("Atenção!");
        builder.setMessage("Ao continuar, seu e-brat será cancelado e seu processo será perdido.");
        builder.setIcon(R.drawable.icon_warning);
        builder.setPositiveButton("Continuar", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form7Activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.setNegativeButton("Voltar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função para criar PDF */
    private void createPDF() {
        // Cria um novo documento PDF
        PdfDocument document = new PdfDocument();

        // Define uma página para o PDF
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4 Size
        PdfDocument.Page page = document.startPage(pageInfo);

        // Configura o conteúdo do PDF
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(16);
        canvas.drawText("Hello, World!", 100, 100, paint);

        // Finaliza a página
        document.finishPage(page);

        // Salva o documento PDF
        File directory = new File(Environment.getExternalStorageDirectory(), "MyApp");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "example.pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            // Opcional: mostrar uma mensagem ao usuário sobre o sucesso da criação
        } catch (IOException e) {
            e.printStackTrace();
            // Opcional: tratar o erro
        }

        // Fecha o documento
        document.close();
    }
}