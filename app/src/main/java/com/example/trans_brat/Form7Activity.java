package com.example.trans_brat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Form7Activity extends AppCompatActivity {
    private final String logId = "Form7Activity_LOG";
    /* Respostas do formulário anterior
    [0] Id da resposta
    [1] Id do TextView de exibição
    [2] Tipo de resposta
    -> 0: Texto
    -> 1: Checkbox
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
            {"F3_S1_Q2", String.valueOf(R.id.F3_S1_Q2), "0"},
            {"F3_S1_Q3", String.valueOf(R.id.F3_S1_Q3), "0"},
            {"F3_S1_Q4", String.valueOf(R.id.F3_S1_Q4), "0"},
            {"F3_S1_Q5", String.valueOf(R.id.F3_S1_Q5), "0"},
            {"F3_S1_Q6", String.valueOf(R.id.F3_S1_Q6), "0"},
            {"F3_S1_Q7", String.valueOf(R.id.F3_S1_Q7), "0"},
            {"F3_S1_Q8", String.valueOf(R.id.F3_S1_Q8), "0"},
            {"F3_S1_Q9", String.valueOf(R.id.F3_S1_Q9), "0"},
            {"F3_S1_Q10", String.valueOf(R.id.F3_S1_Q10), "0"},
            {"F3_S1_Q11", String.valueOf(R.id.F3_S1_Q11), "0"},
            {"F3_S1_Q12", String.valueOf(R.id.F3_S1_Q12), "0"},
            {"F3_S2_Q2", String.valueOf(R.id.F3_S2_Q2), "0"},
            {"F3_S2_Q3", String.valueOf(R.id.F3_S2_Q3), "0"},
            {"F3_S3_Q2", String.valueOf(R.id.F3_S3_Q2), "0"},
            {"F3_S3_Q3", String.valueOf(R.id.F3_S3_Q3), "0"},
            {"F3_S3_Q4", String.valueOf(R.id.F3_S3_Q4), "0"},
            {"F3_S3_Q5", String.valueOf(R.id.F3_S3_Q5), "0"},
            {"F3_S4_Q2", String.valueOf(R.id.F3_S4_Q2), "0"},
            {"F3_S4_Q3", String.valueOf(R.id.F3_S4_Q3), "0"},
            {"F3_S4_Q4", String.valueOf(R.id.F3_S4_Q4), "0"},
            {"F3_S4_Q5", String.valueOf(R.id.F3_S4_Q5), "0"},
            {"F3_S4_Q6", String.valueOf(R.id.F3_S4_Q6), "0"},
            {"F3_S4_Q7", String.valueOf(R.id.F3_S4_Q7), "0"},
            {"F3_S4_Q8", String.valueOf(R.id.F3_S4_Q8), "0"},
            {"F3_S4_Q9", String.valueOf(R.id.F3_S4_Q9), "0"},
            {"F3_S5_Q2", String.valueOf(R.id.F3_S5_Q2), "0"},
            {"F3_S5_Q3", String.valueOf(R.id.F3_S5_Q3), "0"},
            {"F3_S5_Q4", String.valueOf(R.id.F3_S5_Q4), "0"},
            {"F3_S5_Q5", String.valueOf(R.id.F3_S5_Q5), "0"},
            {"F3_S5_Q6", String.valueOf(R.id.F3_S5_Q6), "0"},
            {"F3_S5_Q7", String.valueOf(R.id.F3_S5_Q7), "0"},
            {"F3_S5_Q8", String.valueOf(R.id.F3_S5_Q8), "0"},
            {"F3_S5_Q9", String.valueOf(R.id.F3_S5_Q9), "0"},
            {"F3_S5_Q10", String.valueOf(R.id.F3_S5_Q10), "0"},
            {"F3_S6_Q2", String.valueOf(R.id.F3_S6_Q2), "0"},
            {"F3_S6_Q3", String.valueOf(R.id.F3_S6_Q3), "0"},
            {"F3_S6_Q4", String.valueOf(R.id.F3_S6_Q4), "0"},
            {"F3_S6_Q5", String.valueOf(R.id.F3_S6_Q5), "0"},
            {"F3_S6_Q6", String.valueOf(R.id.F3_S6_Q6), "0"},
            {"F3_S7_Q2", String.valueOf(R.id.F3_S7_Q2), "0"},
            {"F3_S7_Q3", String.valueOf(R.id.F3_S7_Q3), "0"},
            {"F3_S7_Q4", String.valueOf(R.id.F3_S7_Q4), "0"},
            {"F3_S7_Q5", String.valueOf(R.id.F3_S7_Q5), "0"},
            {"F3_S7_Q6", String.valueOf(R.id.F3_S7_Q6), "0"},
            {"F3_S7_Q7", String.valueOf(R.id.F3_S7_Q7), "0"},
            {"F3_S7_Q8", String.valueOf(R.id.F3_S7_Q8), "0"},
            {"F3_S8_Q1", String.valueOf(R.id.F3_S8_Q1), "1"},
            {"F3_S8_Q2", String.valueOf(R.id.F3_S8_Q2), "1"},
            {"F3_S8_Q3", String.valueOf(R.id.F3_S8_Q3), "1"},
            {"F3_S8_Q4", String.valueOf(R.id.F3_S8_Q4), "1"},
            {"F3_S8_Q5", String.valueOf(R.id.F3_S8_Q5), "1"},
            {"F3_S8_Q6", String.valueOf(R.id.F3_S8_Q6), "1"},
            {"F4_S0_Q1", String.valueOf(R.id.F4_S0_Q1), "0"},
            {"F4_S1_M1_Q2", String.valueOf(R.id.F4_S1_M1_Q2), "0"},
            {"F4_S1_M1_Q3", String.valueOf(R.id.F4_S1_M1_Q3), "0"},
            {"F4_S1_M1_Q4", String.valueOf(R.id.F4_S1_M1_Q4), "0"},
            {"F4_S1_M1_Q5", String.valueOf(R.id.F4_S1_M1_Q5), "0"},
            {"F4_S1_M1_Q6", String.valueOf(R.id.F4_S1_M1_Q6), "0"},
            {"F4_S1_M1_Q7", String.valueOf(R.id.F4_S1_M1_Q7), "0"},
            {"F4_S1_M1_Q8", String.valueOf(R.id.F4_S1_M1_Q8), "0"},
            {"F4_S1_M1_Q9", String.valueOf(R.id.F4_S1_M1_Q9), "0"},
            {"F4_S1_M1_Q10", String.valueOf(R.id.F4_S1_M1_Q10), "0"},
            {"F4_S1_M1_Q11", String.valueOf(R.id.F4_S1_M1_Q11), "0"},
            {"F4_S1_M1_Q12", String.valueOf(R.id.F4_S1_M1_Q12), "0"},
            {"F4_S1_M2_Q2", String.valueOf(R.id.F4_S1_M2_Q2), "0"},
            {"F4_S1_M2_Q3", String.valueOf(R.id.F4_S1_M2_Q3), "0"},
            {"F4_S1_M3_Q2", String.valueOf(R.id.F4_S1_M3_Q2), "0"},
            {"F4_S1_M3_Q3", String.valueOf(R.id.F4_S1_M3_Q3), "0"},
            {"F4_S1_M3_Q4", String.valueOf(R.id.F4_S1_M3_Q4), "0"},
            {"F4_S1_M3_Q5", String.valueOf(R.id.F4_S1_M3_Q5), "0"},
            {"F4_S1_M4_Q2", String.valueOf(R.id.F4_S1_M4_Q2), "0"},
            {"F4_S1_M4_Q3", String.valueOf(R.id.F4_S1_M4_Q3), "0"},
            {"F4_S1_M4_Q4", String.valueOf(R.id.F4_S1_M4_Q4), "0"},
            {"F4_S1_M4_Q5", String.valueOf(R.id.F4_S1_M4_Q5), "0"},
            {"F4_S1_M4_Q6", String.valueOf(R.id.F4_S1_M4_Q6), "0"},
            {"F4_S1_M4_Q7", String.valueOf(R.id.F4_S1_M4_Q7), "0"},
            {"F4_S1_M4_Q8", String.valueOf(R.id.F4_S1_M4_Q8), "0"},
            {"F4_S1_M4_Q9", String.valueOf(R.id.F4_S1_M4_Q9), "0"},
            {"F4_S1_M5_Q2", String.valueOf(R.id.F4_S1_M5_Q2), "0"},
            {"F4_S1_M5_Q3", String.valueOf(R.id.F4_S1_M5_Q3), "0"},
            {"F4_S1_M5_Q4", String.valueOf(R.id.F4_S1_M5_Q4), "0"},
            {"F4_S1_M5_Q5", String.valueOf(R.id.F4_S1_M5_Q5), "0"},
            {"F4_S1_M5_Q6", String.valueOf(R.id.F4_S1_M5_Q6), "0"},
            {"F4_S1_M5_Q7", String.valueOf(R.id.F4_S1_M5_Q7), "0"},
            {"F4_S1_M5_Q8", String.valueOf(R.id.F4_S1_M5_Q8), "0"},
            {"F4_S1_M5_Q9", String.valueOf(R.id.F4_S1_M5_Q9), "0"},
            {"F4_S1_M5_Q10", String.valueOf(R.id.F4_S1_M5_Q10), "0"},
            {"F4_S1_M6_Q2", String.valueOf(R.id.F4_S1_M6_Q2), "0"},
            {"F4_S1_M6_Q3", String.valueOf(R.id.F4_S1_M6_Q3), "0"},
            {"F4_S1_M6_Q4", String.valueOf(R.id.F4_S1_M6_Q4), "0"},
            {"F4_S1_M6_Q5", String.valueOf(R.id.F4_S1_M6_Q5), "0"},
            {"F4_S1_M6_Q6", String.valueOf(R.id.F4_S1_M6_Q6), "0"},
            {"F4_S1_M7_Q2", String.valueOf(R.id.F4_S1_M7_Q2), "0"},
            {"F4_S1_M7_Q3", String.valueOf(R.id.F4_S1_M7_Q3), "0"},
            {"F4_S1_M7_Q4", String.valueOf(R.id.F4_S1_M7_Q4), "0"},
            {"F4_S1_M7_Q5", String.valueOf(R.id.F4_S1_M7_Q5), "0"},
            {"F4_S1_M7_Q6", String.valueOf(R.id.F4_S1_M7_Q6), "0"},
            {"F4_S1_M7_Q7", String.valueOf(R.id.F4_S1_M7_Q7), "0"},
            {"F4_S1_M7_Q8", String.valueOf(R.id.F4_S1_M7_Q8), "0"},
            {"F4_S1_M8_Q1", String.valueOf(R.id.F4_S1_M8_Q1), "1"},
            {"F4_S1_M8_Q2", String.valueOf(R.id.F4_S1_M8_Q2), "1"},
            {"F4_S1_M8_Q3", String.valueOf(R.id.F4_S1_M8_Q3), "1"},
            {"F4_S1_M8_Q4", String.valueOf(R.id.F4_S1_M8_Q4), "1"},
            {"F4_S1_M8_Q5", String.valueOf(R.id.F4_S1_M8_Q5), "1"},
            {"F4_S1_M8_Q6", String.valueOf(R.id.F4_S1_M8_Q6), "1"},
            {"F4_S2_M1_Q2", String.valueOf(R.id.F4_S2_M1_Q2), "0"},
            {"F4_S2_M1_Q3", String.valueOf(R.id.F4_S2_M1_Q3), "0"},
            {"F4_S2_M1_Q4", String.valueOf(R.id.F4_S2_M1_Q4), "0"},
            {"F4_S2_M1_Q5", String.valueOf(R.id.F4_S2_M1_Q5), "0"},
            {"F4_S2_M1_Q6", String.valueOf(R.id.F4_S2_M1_Q6), "0"},
            {"F4_S2_M1_Q7", String.valueOf(R.id.F4_S2_M1_Q7), "0"},
            {"F4_S2_M1_Q8", String.valueOf(R.id.F4_S2_M1_Q8), "0"},
            {"F4_S2_M1_Q9", String.valueOf(R.id.F4_S2_M1_Q9), "0"},
            {"F4_S2_M1_Q10", String.valueOf(R.id.F4_S2_M1_Q10), "0"},
            {"F4_S2_M1_Q11", String.valueOf(R.id.F4_S2_M1_Q11), "0"},
            {"F4_S2_M1_Q12", String.valueOf(R.id.F4_S2_M1_Q12), "0"},
            {"F4_S2_M2_Q2", String.valueOf(R.id.F4_S2_M2_Q2), "0"},
            {"F4_S2_M2_Q3", String.valueOf(R.id.F4_S2_M2_Q3), "0"},
            {"F4_S2_M3_Q2", String.valueOf(R.id.F4_S2_M3_Q2), "0"},
            {"F4_S2_M3_Q3", String.valueOf(R.id.F4_S2_M3_Q3), "0"},
            {"F4_S2_M3_Q4", String.valueOf(R.id.F4_S2_M3_Q4), "0"},
            {"F4_S2_M3_Q5", String.valueOf(R.id.F4_S2_M3_Q5), "0"},
            {"F4_S2_M4_Q2", String.valueOf(R.id.F4_S2_M4_Q2), "0"},
            {"F4_S2_M4_Q3", String.valueOf(R.id.F4_S2_M4_Q3), "0"},
            {"F4_S2_M4_Q4", String.valueOf(R.id.F4_S2_M4_Q4), "0"},
            {"F4_S2_M4_Q5", String.valueOf(R.id.F4_S2_M4_Q5), "0"},
            {"F4_S2_M4_Q6", String.valueOf(R.id.F4_S2_M4_Q6), "0"},
            {"F4_S2_M4_Q7", String.valueOf(R.id.F4_S2_M4_Q7), "0"},
            {"F4_S2_M4_Q8", String.valueOf(R.id.F4_S2_M4_Q8), "0"},
            {"F4_S2_M4_Q9", String.valueOf(R.id.F4_S2_M4_Q9), "0"},
            {"F4_S2_M5_Q2", String.valueOf(R.id.F4_S2_M5_Q2), "0"},
            {"F4_S2_M5_Q3", String.valueOf(R.id.F4_S2_M5_Q3), "0"},
            {"F4_S2_M5_Q4", String.valueOf(R.id.F4_S2_M5_Q4), "0"},
            {"F4_S2_M5_Q5", String.valueOf(R.id.F4_S2_M5_Q5), "0"},
            {"F4_S2_M5_Q6", String.valueOf(R.id.F4_S2_M5_Q6), "0"},
            {"F4_S2_M5_Q7", String.valueOf(R.id.F4_S2_M5_Q7), "0"},
            {"F4_S2_M5_Q8", String.valueOf(R.id.F4_S2_M5_Q8), "0"},
            {"F4_S2_M5_Q9", String.valueOf(R.id.F4_S2_M5_Q9), "0"},
            {"F4_S2_M5_Q10", String.valueOf(R.id.F4_S2_M5_Q10), "0"},
            {"F4_S2_M6_Q2", String.valueOf(R.id.F4_S2_M6_Q2), "0"},
            {"F4_S2_M6_Q3", String.valueOf(R.id.F4_S2_M6_Q3), "0"},
            {"F4_S2_M6_Q4", String.valueOf(R.id.F4_S2_M6_Q4), "0"},
            {"F4_S2_M6_Q5", String.valueOf(R.id.F4_S2_M6_Q5), "0"},
            {"F4_S2_M6_Q6", String.valueOf(R.id.F4_S2_M6_Q6), "0"},
            {"F4_S2_M7_Q2", String.valueOf(R.id.F4_S2_M7_Q2), "0"},
            {"F4_S2_M7_Q3", String.valueOf(R.id.F4_S2_M7_Q3), "0"},
            {"F4_S2_M7_Q4", String.valueOf(R.id.F4_S2_M7_Q4), "0"},
            {"F4_S2_M7_Q5", String.valueOf(R.id.F4_S2_M7_Q5), "0"},
            {"F4_S2_M7_Q6", String.valueOf(R.id.F4_S2_M7_Q6), "0"},
            {"F4_S2_M7_Q7", String.valueOf(R.id.F4_S2_M7_Q7), "0"},
            {"F4_S2_M7_Q8", String.valueOf(R.id.F4_S2_M7_Q8), "0"},
            {"F4_S2_M8_Q1", String.valueOf(R.id.F4_S2_M8_Q1), "1"},
            {"F4_S2_M8_Q2", String.valueOf(R.id.F4_S2_M8_Q2), "1"},
            {"F4_S2_M8_Q3", String.valueOf(R.id.F4_S2_M8_Q3), "1"},
            {"F4_S2_M8_Q4", String.valueOf(R.id.F4_S2_M8_Q4), "1"},
            {"F4_S2_M8_Q5", String.valueOf(R.id.F4_S2_M8_Q5), "1"},
            {"F4_S2_M8_Q6", String.valueOf(R.id.F4_S2_M8_Q6), "1"},
            {"F4_S3_M1_Q2", String.valueOf(R.id.F4_S3_M1_Q2), "0"},
            {"F4_S3_M1_Q3", String.valueOf(R.id.F4_S3_M1_Q3), "0"},
            {"F4_S3_M1_Q4", String.valueOf(R.id.F4_S3_M1_Q4), "0"},
            {"F4_S3_M1_Q5", String.valueOf(R.id.F4_S3_M1_Q5), "0"},
            {"F4_S3_M1_Q6", String.valueOf(R.id.F4_S3_M1_Q6), "0"},
            {"F4_S3_M1_Q7", String.valueOf(R.id.F4_S3_M1_Q7), "0"},
            {"F4_S3_M1_Q8", String.valueOf(R.id.F4_S3_M1_Q8), "0"},
            {"F4_S3_M1_Q9", String.valueOf(R.id.F4_S3_M1_Q9), "0"},
            {"F4_S3_M1_Q10", String.valueOf(R.id.F4_S3_M1_Q10), "0"},
            {"F4_S3_M1_Q11", String.valueOf(R.id.F4_S3_M1_Q11), "0"},
            {"F4_S3_M1_Q12", String.valueOf(R.id.F4_S3_M1_Q12), "0"},
            {"F4_S3_M2_Q2", String.valueOf(R.id.F4_S3_M2_Q2), "0"},
            {"F4_S3_M2_Q3", String.valueOf(R.id.F4_S3_M2_Q3), "0"},
            {"F4_S3_M3_Q2", String.valueOf(R.id.F4_S3_M3_Q2), "0"},
            {"F4_S3_M3_Q3", String.valueOf(R.id.F4_S3_M3_Q3), "0"},
            {"F4_S3_M3_Q4", String.valueOf(R.id.F4_S3_M3_Q4), "0"},
            {"F4_S3_M3_Q5", String.valueOf(R.id.F4_S3_M3_Q5), "0"},
            {"F4_S3_M4_Q2", String.valueOf(R.id.F4_S3_M4_Q2), "0"},
            {"F4_S3_M4_Q3", String.valueOf(R.id.F4_S3_M4_Q3), "0"},
            {"F4_S3_M4_Q4", String.valueOf(R.id.F4_S3_M4_Q4), "0"},
            {"F4_S3_M4_Q5", String.valueOf(R.id.F4_S3_M4_Q5), "0"},
            {"F4_S3_M4_Q6", String.valueOf(R.id.F4_S3_M4_Q6), "0"},
            {"F4_S3_M4_Q7", String.valueOf(R.id.F4_S3_M4_Q7), "0"},
            {"F4_S3_M4_Q8", String.valueOf(R.id.F4_S3_M4_Q8), "0"},
            {"F4_S3_M4_Q9", String.valueOf(R.id.F4_S3_M4_Q9), "0"},
            {"F4_S3_M5_Q2", String.valueOf(R.id.F4_S3_M5_Q2), "0"},
            {"F4_S3_M5_Q3", String.valueOf(R.id.F4_S3_M5_Q3), "0"},
            {"F4_S3_M5_Q4", String.valueOf(R.id.F4_S3_M5_Q4), "0"},
            {"F4_S3_M5_Q5", String.valueOf(R.id.F4_S3_M5_Q5), "0"},
            {"F4_S3_M5_Q6", String.valueOf(R.id.F4_S3_M5_Q6), "0"},
            {"F4_S3_M5_Q7", String.valueOf(R.id.F4_S3_M5_Q7), "0"},
            {"F4_S3_M5_Q8", String.valueOf(R.id.F4_S3_M5_Q8), "0"},
            {"F4_S3_M5_Q9", String.valueOf(R.id.F4_S3_M5_Q9), "0"},
            {"F4_S3_M5_Q10", String.valueOf(R.id.F4_S3_M5_Q10), "0"},
            {"F4_S3_M6_Q2", String.valueOf(R.id.F4_S3_M6_Q2), "0"},
            {"F4_S3_M6_Q3", String.valueOf(R.id.F4_S3_M6_Q3), "0"},
            {"F4_S3_M6_Q4", String.valueOf(R.id.F4_S3_M6_Q4), "0"},
            {"F4_S3_M6_Q5", String.valueOf(R.id.F4_S3_M6_Q5), "0"},
            {"F4_S3_M6_Q6", String.valueOf(R.id.F4_S3_M6_Q6), "0"},
            {"F4_S3_M7_Q2", String.valueOf(R.id.F4_S3_M7_Q2), "0"},
            {"F4_S3_M7_Q3", String.valueOf(R.id.F4_S3_M7_Q3), "0"},
            {"F4_S3_M7_Q4", String.valueOf(R.id.F4_S3_M7_Q4), "0"},
            {"F4_S3_M7_Q5", String.valueOf(R.id.F4_S3_M7_Q5), "0"},
            {"F4_S3_M7_Q6", String.valueOf(R.id.F4_S3_M7_Q6), "0"},
            {"F4_S3_M7_Q7", String.valueOf(R.id.F4_S3_M7_Q7), "0"},
            {"F4_S3_M7_Q8", String.valueOf(R.id.F4_S3_M7_Q8), "0"},
            {"F4_S3_M8_Q1", String.valueOf(R.id.F4_S3_M8_Q1), "1"},
            {"F4_S3_M8_Q2", String.valueOf(R.id.F4_S3_M8_Q2), "1"},
            {"F4_S3_M8_Q3", String.valueOf(R.id.F4_S3_M8_Q3), "1"},
            {"F4_S3_M8_Q4", String.valueOf(R.id.F4_S3_M8_Q4), "1"},
            {"F4_S3_M8_Q5", String.valueOf(R.id.F4_S3_M8_Q5), "1"},
            {"F4_S3_M8_Q6", String.valueOf(R.id.F4_S3_M8_Q6), "1"},
            {"F4_S4_M1_Q2", String.valueOf(R.id.F4_S4_M1_Q2), "0"},
            {"F4_S4_M1_Q3", String.valueOf(R.id.F4_S4_M1_Q3), "0"},
            {"F4_S4_M1_Q4", String.valueOf(R.id.F4_S4_M1_Q4), "0"},
            {"F4_S4_M1_Q5", String.valueOf(R.id.F4_S4_M1_Q5), "0"},
            {"F4_S4_M1_Q6", String.valueOf(R.id.F4_S4_M1_Q6), "0"},
            {"F4_S4_M1_Q7", String.valueOf(R.id.F4_S4_M1_Q7), "0"},
            {"F4_S4_M1_Q8", String.valueOf(R.id.F4_S4_M1_Q8), "0"},
            {"F4_S4_M1_Q9", String.valueOf(R.id.F4_S4_M1_Q9), "0"},
            {"F4_S4_M1_Q10", String.valueOf(R.id.F4_S4_M1_Q10), "0"},
            {"F4_S4_M1_Q11", String.valueOf(R.id.F4_S4_M1_Q11), "0"},
            {"F4_S4_M1_Q12", String.valueOf(R.id.F4_S4_M1_Q12), "0"},
            {"F4_S4_M2_Q2", String.valueOf(R.id.F4_S4_M2_Q2), "0"},
            {"F4_S4_M2_Q3", String.valueOf(R.id.F4_S4_M2_Q3), "0"},
            {"F4_S4_M3_Q2", String.valueOf(R.id.F4_S4_M3_Q2), "0"},
            {"F4_S4_M3_Q3", String.valueOf(R.id.F4_S4_M3_Q3), "0"},
            {"F4_S4_M3_Q4", String.valueOf(R.id.F4_S4_M3_Q4), "0"},
            {"F4_S4_M3_Q5", String.valueOf(R.id.F4_S4_M3_Q5), "0"},
            {"F4_S4_M4_Q2", String.valueOf(R.id.F4_S4_M4_Q2), "0"},
            {"F4_S4_M4_Q3", String.valueOf(R.id.F4_S4_M4_Q3), "0"},
            {"F4_S4_M4_Q4", String.valueOf(R.id.F4_S4_M4_Q4), "0"},
            {"F4_S4_M4_Q5", String.valueOf(R.id.F4_S4_M4_Q5), "0"},
            {"F4_S4_M4_Q6", String.valueOf(R.id.F4_S4_M4_Q6), "0"},
            {"F4_S4_M4_Q7", String.valueOf(R.id.F4_S4_M4_Q7), "0"},
            {"F4_S4_M4_Q8", String.valueOf(R.id.F4_S4_M4_Q8), "0"},
            {"F4_S4_M4_Q9", String.valueOf(R.id.F4_S4_M4_Q9), "0"},
            {"F4_S4_M5_Q2", String.valueOf(R.id.F4_S4_M5_Q2), "0"},
            {"F4_S4_M5_Q3", String.valueOf(R.id.F4_S4_M5_Q3), "0"},
            {"F4_S4_M5_Q4", String.valueOf(R.id.F4_S4_M5_Q4), "0"},
            {"F4_S4_M5_Q5", String.valueOf(R.id.F4_S4_M5_Q5), "0"},
            {"F4_S4_M5_Q6", String.valueOf(R.id.F4_S4_M5_Q6), "0"},
            {"F4_S4_M5_Q7", String.valueOf(R.id.F4_S4_M5_Q7), "0"},
            {"F4_S4_M5_Q8", String.valueOf(R.id.F4_S4_M5_Q8), "0"},
            {"F4_S4_M5_Q9", String.valueOf(R.id.F4_S4_M5_Q9), "0"},
            {"F4_S4_M5_Q10", String.valueOf(R.id.F4_S4_M5_Q10), "0"},
            {"F4_S4_M6_Q2", String.valueOf(R.id.F4_S4_M6_Q2), "0"},
            {"F4_S4_M6_Q3", String.valueOf(R.id.F4_S4_M6_Q3), "0"},
            {"F4_S4_M6_Q4", String.valueOf(R.id.F4_S4_M6_Q4), "0"},
            {"F4_S4_M6_Q5", String.valueOf(R.id.F4_S4_M6_Q5), "0"},
            {"F4_S4_M6_Q6", String.valueOf(R.id.F4_S4_M6_Q6), "0"},
            {"F4_S4_M7_Q2", String.valueOf(R.id.F4_S4_M7_Q2), "0"},
            {"F4_S4_M7_Q3", String.valueOf(R.id.F4_S4_M7_Q3), "0"},
            {"F4_S4_M7_Q4", String.valueOf(R.id.F4_S4_M7_Q4), "0"},
            {"F4_S4_M7_Q5", String.valueOf(R.id.F4_S4_M7_Q5), "0"},
            {"F4_S4_M7_Q6", String.valueOf(R.id.F4_S4_M7_Q6), "0"},
            {"F4_S4_M7_Q7", String.valueOf(R.id.F4_S4_M7_Q7), "0"},
            {"F4_S4_M7_Q8", String.valueOf(R.id.F4_S4_M7_Q8), "0"},
            {"F4_S4_M8_Q1", String.valueOf(R.id.F4_S4_M8_Q1), "1"},
            {"F4_S4_M8_Q2", String.valueOf(R.id.F4_S4_M8_Q2), "1"},
            {"F4_S4_M8_Q3", String.valueOf(R.id.F4_S4_M8_Q3), "1"},
            {"F4_S4_M8_Q4", String.valueOf(R.id.F4_S4_M8_Q4), "1"},
            {"F4_S4_M8_Q5", String.valueOf(R.id.F4_S4_M8_Q5), "1"},
            {"F4_S4_M8_Q6", String.valueOf(R.id.F4_S4_M8_Q6), "1"},
            {"F5_S1_Q2", String.valueOf(R.id.F5_S1_Q2), "0"},
            {"F5_S1_Q3", String.valueOf(R.id.F5_S1_Q3), "0"},
            {"F5_S1_Q4", String.valueOf(R.id.F5_S1_Q4), "0"},
            {"F5_S2_Q2", String.valueOf(R.id.F5_S2_Q2), "0"},
            {"F5_S2_Q3", String.valueOf(R.id.F5_S2_Q3), "0"},
            {"F5_S2_Q4", String.valueOf(R.id.F5_S2_Q4), "0"},
            {"F5_S3_Q2", String.valueOf(R.id.F5_S3_Q2), "0"},
            {"F5_S3_Q3", String.valueOf(R.id.F5_S3_Q3), "0"},
            {"F5_S3_Q4", String.valueOf(R.id.F5_S3_Q4), "0"},
            {"F5_S4_Q2", String.valueOf(R.id.F5_S4_Q2), "0"},
            {"F5_S4_Q3", String.valueOf(R.id.F5_S4_Q3), "0"},
            {"F5_S4_Q4", String.valueOf(R.id.F5_S4_Q4), "0"},
            {"F6_S1_Q2", String.valueOf(R.id.F6_S1_Q2), "0"},
            {"F6_S2_Q1", String.valueOf(R.id.F6_S2_Q1), "2"}
    };

    private int[] linearLayouts = {
            R.id.form_7_section_2_main,
            R.id.form_7_section_3_main,
            R.id.form_7_section_4_main,
            R.id.form_7_section_5_main,
            R.id.form_7_section_6_main
    };
    private int[] linearLayoutsToggleButtons = {
            R.id.form_7_section_2_expansion,
            R.id.form_7_section_3_expansion,
            R.id.form_7_section_4_expansion,
            R.id.form_7_section_5_expansion,
            R.id.form_7_section_6_expansion
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

        /* Evento de toggle dos LinearLayout's */
        linearLayoutsToggleEvent();

        /* Escondendo todos os LinearLayout's */
        linearLayoutsHide();

        /* Criando PDF */
        Button buttonPDF = findViewById(R.id.create_pdf_button);
        buttonPDF.setOnClickListener(v -> createPDF());

        /* Botões inferiores */
        Button buttonBack = findViewById(R.id.back_button);
        Button buttonCancel = findViewById(R.id.cancel_button);
        Button buttonSubmit = findViewById(R.id.submit_button);

        /* (Voltar) Voltando para o formulário 5 */
        buttonBack.setOnClickListener(v -> finish());

        /* (Cancelar) Voltando para o menu principal */
        buttonCancel.setOnClickListener(v -> cancel());

        /* (Enviar) Finalizando o e-brat */
        buttonSubmit.setOnClickListener(v -> submit());
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
                    //Log.d(logId, answer[0] + " - " + answerContent);
                } else if(Objects.equals(answerType, "1")) {// Se a resposta for do tipo checkbox
                    String answerContent = getIntent().getStringExtra(answer[0]);
                    CheckBox checkBox = findViewById(Integer.parseInt(answer[1]));
                    checkBox.setChecked(Objects.equals(answerContent, String.valueOf(true)));
                    //checkBox.setEnabled(false);
                    //Log.d(logId, answer[0] + " - " + answerContent);
                } else if(Objects.equals(answerType, "2")) {// Se a resposta for do tipo imagem
                    ArrayList<String> answerContent = getIntent().getStringArrayListExtra(answer[0]);
                    RecyclerView recyclerView = findViewById(Integer.parseInt(answer[1]));
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

                    // Filtrando valores null
                    List<String> filteredImages = new ArrayList<>();
                    if (answerContent != null) {
                        for (String imagePath : answerContent) {
                            if (imagePath != null) {
                                filteredImages.add(imagePath);
                            }
                        }
                    }

                    ImageAdapter7 imageAdapter = new ImageAdapter7((ArrayList<String>) filteredImages);
                    recyclerView.setAdapter(imageAdapter);
                    //Log.d(logId, answer[0] + " - " + answerContent);
                }
            } catch (Exception e) {
                Log.e(logId, "" + e);
            }
        }
    }

    /* Evento de toggle dos LinearLayout's */
    private void linearLayoutsToggleEvent() {
        for(int i = 0; i <= linearLayouts.length - 1; i++) {
            LinearLayout linearLayout = findViewById(linearLayouts[i]);
            Button linearLayoutToggleButton = findViewById(linearLayoutsToggleButtons[i]);
            linearLayoutToggleButton.setOnClickListener(v -> linearLayoutToggleVisibility(linearLayout, linearLayoutToggleButton));
        }
    }

    /* Função de toggle de um LinearLayout */
    private void linearLayoutToggleVisibility(LinearLayout linearLayout, Button button) {
        if (linearLayout.getVisibility() == View.VISIBLE) {
            linearLayout.setVisibility(View.GONE);
            Drawable drawableRight = ContextCompat.getDrawable(this, R.drawable.icon_expansion);
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            Drawable drawableRight = ContextCompat.getDrawable(this, R.drawable.icon_expansion_reverse);
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
        }
    }

    /* Função para esconder LinearLayout's */
    private void linearLayoutsHide() {
        for(int i = 0; i <= linearLayouts.length - 1; i++) {
            LinearLayout linearLayout = findViewById(linearLayouts[i]);
            Button linearLayoutToggleButton = findViewById(linearLayoutsToggleButtons[i]);
            linearLayout.setVisibility(View.GONE);
            Drawable drawableRight = ContextCompat.getDrawable(this, R.drawable.icon_expansion);
            linearLayoutToggleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
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

    /* Função ao finalizar formulário */
    private void submit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Form7Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_success, null);
        builder.setView(dialogLayout);
        builder.setTitle("Solicitação concluída!");
        builder.setMessage("Solicitação concluída com sucesso!");
        builder.setIcon(R.drawable.icon_success);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Form7Activity.this, HomeActivity.class);
            try {
                sendAnswersToNextForm(intent);
            } catch (Exception e) {
                Log.e(logId, "Erro ao enviar respostas:" + e);
            }
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(intent);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função para criar PDF */
    private void createPDF() {
        // Cria um novo documento PDF
        PdfDocument document = new PdfDocument();
        String fileName = "ebrat.pdf";
        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(directoryPath, fileName);
        String pdfColorBlue = "#0585FF";
        int pageWidth = 595; // Largura das páginas
        int pageHeight = 842; // Altura das páginas
        int column1X = 40;
        int column2X = 290;

        PdfDocument pdfDocument = new PdfDocument();

        /* Página 1 */
        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);

        Canvas canvas1 = page1.getCanvas();
        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor(pdfColorBlue));
        paint1.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas1, paint1);

        // Seção 1
        drawRectWithText(canvas1, paint1, "Enviado por", column1X, 140, 500, 40);

        /*drawQuestion(canvas1, paint1, "Nome", column1X, 210);
        drawText(canvas1, paint1, "NOME DO USUÁRIO", column1X, 230);
        drawQuestion(canvas1, paint1, "Matrícula", column1X, 270);
        drawText(canvas1, paint1, "12345678901234567890", column1X, 290);*/

        drawRectWithText(canvas1, paint1, "Dados da ocorrência", 40, 310, 500, 40);

        drawQuestion(canvas1, paint1, "Data", column1X, 380);
        drawText(canvas1, paint1, "F2_S1_Q2", column1X, 400);
        drawQuestion(canvas1, paint1, "Hora", column2X, 380);
        drawText(canvas1, paint1, "F2_S1_Q3", column2X, 400);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, 420, 540, 420, paint1);

        drawQuestion(canvas1, paint1, "Onde?", column1X, 460);
        drawText(canvas1, paint1, "F2_S2_Q2", column1X, 480);
        drawQuestion(canvas1, paint1, "Especifique", column2X, 460);
        drawText(canvas1, paint1, "F2_S2_Q3", column2X, 480);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, 500, 540, 500, paint1);

        drawQuestion(canvas1, paint1, "CEP", column1X, 540);
        drawText(canvas1, paint1, "F2_S3_Q2", column1X, 560);
        drawQuestion(canvas1, paint1, "Endereço", column1X, 600);
        drawText(canvas1, paint1, "F2_S3_Q3", column1X, 620);
        drawQuestion(canvas1, paint1, "Número", column1X, 660);
        drawText(canvas1, paint1, "F2_S3_Q4", column1X, 680);
        drawQuestion(canvas1, paint1, "Bairro", column2X, 660);
        drawText(canvas1, paint1, "F2_S3_Q5", column2X, 680);
        drawQuestion(canvas1, paint1, "Cidade", column1X, 720);
        drawText(canvas1, paint1, "F2_S3_Q6", column1X, 740);
        drawQuestion(canvas1, paint1, "Estado", column2X, 720);
        drawText(canvas1, paint1, "F2_S3_Q7", column2X, 740);
        drawQuestion(canvas1, paint1, "Ponto de referência", column1X, 780);
        drawText(canvas1, paint1, "F2_S3_Q8", column1X, 800);

        pdfDocument.finishPage(page1);

        /* Página 2 */
        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);

        Canvas canvas2 = page2.getCanvas();
        Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor(pdfColorBlue));
        paint2.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas2, paint2);

        drawQuestion(canvas2, paint2, "Quais condições de visibilidade?", column1X, 170);
        drawText(canvas2, paint2, "F2_S4_Q2", column1X, 190);
        drawQuestion(canvas2, paint2, "Quais condições da pista?", column1X, 230);
        drawText(canvas2, paint2, "F2_S4_Q3", column1X, 250);
        drawQuestion(canvas2, paint2, "Quais as condições de sinalização?", column1X, 290);
        drawText(canvas2, paint2, "F2_S4_Q4", column1X, 310);
        drawQuestion(canvas2, paint2, "Qual foi o tipo de acidente?", column1X, 350);
        drawText(canvas2, paint2, "F2_S4_Q5", column1X, 370);

        drawRectWithText(canvas2, paint2, "Veículo principal", 40, 390, 500, 40);

        drawQuestion(canvas2, paint2, "Nº de ordem", column1X, 470);
        drawText(canvas2, paint2, "F3_S1_Q2", column1X, 490);
        drawQuestion(canvas2, paint2, "Placa", column2X, 470);
        drawText(canvas2, paint2, "F3_S1_Q3", column2X, 490);

        paint2.setColor(Color.GRAY);
        canvas2.drawLine(column1X, 510, 540, 510, paint2);

        drawQuestion(canvas2, paint2, "Marca/Modelo", column1X, 550);
        drawText(canvas2, paint2, "F3_S1_Q4", column1X, 570);
        drawQuestion(canvas2, paint2, "Tipo do veículo", column2X, 550);
        drawText(canvas2, paint2, "F3_S1_Q5", column2X, 570);
        drawQuestion(canvas2, paint2, "Cor predominante", column1X, 610);
        drawText(canvas2, paint2, "F3_S1_Q6", column1X, 630);
        drawQuestion(canvas2, paint2, "UF", column2X, 610);
        drawText(canvas2, paint2, "F3_S1_Q7", column2X, 630);
        drawQuestion(canvas2, paint2, "Ano de fabricação", column1X, 670);
        drawText(canvas2, paint2, "F3_S1_Q8", column1X, 690);
        drawQuestion(canvas2, paint2, "Ano do modelo", column2X, 670);
        drawText(canvas2, paint2, "F3_S1_Q9", column2X, 690);
        drawQuestion(canvas2, paint2, "Nº do Chassi", column1X, 730);
        drawText(canvas2, paint2, "F3_S1_Q10", column1X, 750);
        drawQuestion(canvas2, paint2, "RENAVAM", column2X, 730);
        drawText(canvas2, paint2, "F3_S1_Q11", column2X, 750);
        drawQuestion(canvas2, paint2, "Veículo é da empresa?", column1X, 790);
        drawText(canvas2, paint2, "F3_S1_Q12", column1X, 810);

        pdfDocument.finishPage(page2);

        /* Página 3 */
        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 3).create();
        PdfDocument.Page page3 = pdfDocument.startPage(pageInfo3);

        Canvas canvas3 = page3.getCanvas();
        Paint paint3 = new Paint();
        paint3.setColor(Color.parseColor(pdfColorBlue));
        paint3.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas3, paint3);

        drawTitle(canvas3, paint3, "Dados da empresa", column1X, 170);
        drawQuestion(canvas3, paint3, "Razão social", column1X, 210);
        drawText(canvas3, paint3, "F3_S2_Q2", column1X, 230);
        drawQuestion(canvas3, paint3, "CNPJ", column1X, 270);
        drawText(canvas3, paint3, "F3_S2_Q3", column1X, 290);

        paint3.setColor(Color.GRAY);
        canvas3.drawLine(column1X, 330, 540, 330, paint3);

        drawTitle(canvas3, paint3, "Dados do proprietário", column1X, 370);
        drawQuestion(canvas3, paint3, "Nome", column1X, 410);
        drawText(canvas3, paint3, "F3_S3_Q2", column1X, 430);
        drawQuestion(canvas3, paint3, "CPF", column1X, 470);
        drawText(canvas3, paint3, "F3_S3_Q3", column1X, 490);
        drawQuestion(canvas3, paint3, "Data nascimento", column2X, 470);
        drawText(canvas3, paint3, "F3_S3_Q4", column2X, 490);
        drawQuestion(canvas3, paint3, "O proprietário é o condutor?", column1X, 530);
        drawText(canvas3, paint3, "F3_S3_Q5", column1X, 550);

        paint3.setColor(Color.GRAY);
        canvas3.drawLine(column1X, 570, 540, 570, paint3);

        drawTitle(canvas3, paint3, "Endereço", column1X, 610);
        drawQuestion(canvas3, paint3, "CEP", column1X, 650);
        drawText(canvas3, paint3, "F3_S4_Q2", column1X, 670);
        drawQuestion(canvas3, paint3, "Lograd.", column1X, 710);
        drawText(canvas3, paint3, "F3_S4_Q3", column1X, 730);
        drawQuestion(canvas3, paint3, "Número", column1X, 770);
        drawText(canvas3, paint3, "F3_S4_Q4", column1X, 790);
        drawQuestion(canvas3, paint3, "Complemento", column2X, 770);
        drawText(canvas3, paint3, "F3_S4_Q5", column2X, 790);

        pdfDocument.finishPage(page3);

        /* Página 4 */
        PdfDocument.PageInfo pageInfo4 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 4).create();
        PdfDocument.Page page4 = pdfDocument.startPage(pageInfo4);

        Canvas canvas4 = page4.getCanvas();
        Paint paint4 = new Paint();
        paint4.setColor(Color.parseColor(pdfColorBlue));
        paint4.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas4, paint4);

        drawQuestion(canvas4, paint4, "Bairro", column1X, 170);
        drawText(canvas4, paint4, "F3_S4_Q6", column1X, 190);
        drawQuestion(canvas4, paint4, "Cidade", column2X, 170);
        drawText(canvas4, paint4, "F3_S4_Q7", column2X, 190);
        drawQuestion(canvas4, paint4, "UF", column1X, 230);
        drawText(canvas4, paint4, "F3_S4_Q8", column1X, 250);
        drawQuestion(canvas4, paint4, "O proprietário é o condutor?", column2X, 230);
        drawText(canvas4, paint4, "F3_S4_Q9", column2X, 250);

        paint4.setColor(Color.GRAY);
        canvas4.drawLine(column1X, 290, 540, 290, paint4);

        drawTitle(canvas4, paint4, "Endereço do proprietário", column1X, 330);
        drawQuestion(canvas4, paint4, "CEP", column1X, 370);
        drawText(canvas4, paint4, "F3_S5_Q2", column1X, 390);
        drawQuestion(canvas4, paint4, "Lograd.", column1X, 430);
        drawText(canvas4, paint4, "F3_S5_Q3", column1X, 450);
        drawQuestion(canvas4, paint4, "Número", column1X, 490);
        drawText(canvas4, paint4, "F3_S5_Q4", column1X, 510);
        drawQuestion(canvas4, paint4, "Complemento", column2X, 490);
        drawText(canvas4, paint4, "F3_S5_Q5", column2X, 510);
        drawQuestion(canvas4, paint4, "Bairro", column1X, 550);
        drawText(canvas4, paint4, "F3_S5_Q6", column1X, 570);
        drawQuestion(canvas4, paint4, "Cidade", column2X, 550);
        drawText(canvas4, paint4, "F3_S5_Q7", column2X, 570);
        drawQuestion(canvas4, paint4, "UF", column1X, 610);
        drawText(canvas4, paint4, "F3_S5_Q8", column1X, 630);
        drawQuestion(canvas4, paint4, "Nº CNH", column2X, 610);
        drawText(canvas4, paint4, "F3_S5_Q9", column2X, 630);
        drawQuestion(canvas4, paint4, "Validade", column1X, 670);
        drawText(canvas4, paint4, "F3_S5_Q10", column1X, 690);

        pdfDocument.finishPage(page4);

        /* Página 5 */
        PdfDocument.PageInfo pageInfo5 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 5).create();
        PdfDocument.Page page5 = pdfDocument.startPage(pageInfo5);

        Canvas canvas5 = page5.getCanvas();
        Paint paint5 = new Paint();
        paint5.setColor(Color.parseColor(pdfColorBlue));
        paint5.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas5, paint5);

        drawTitle(canvas5, paint5, "Dados do condutor", column1X, 170);
        drawQuestion(canvas5, paint5, "Nome", column1X, 210);
        drawText(canvas5, paint5, "F3_S6_Q2", column1X, 230);
        drawQuestion(canvas5, paint5, "CPF", column1X, 270);
        drawText(canvas5, paint5, "F3_S6_Q3", column1X, 290);
        drawQuestion(canvas5, paint5, "Data de nascimento", column2X, 270);
        drawText(canvas5, paint5, "F3_S6_Q4", column2X, 290);
        drawQuestion(canvas5, paint5, "Nº CNH", column1X, 330);
        drawText(canvas5, paint5, "F3_S6_Q5", column1X, 350);
        drawQuestion(canvas5, paint5, "Validade", column2X, 330);
        drawText(canvas5, paint5, "F3_S6_Q6", column2X, 350);

        paint5.setColor(Color.GRAY);
        canvas5.drawLine(column1X, 390, 540, 390, paint5);

        drawTitle(canvas5, paint5, "Endereço do condutor", column1X, 430);
        drawQuestion(canvas5, paint5, "CEP", column1X, 470);
        drawText(canvas5, paint5, "F3_S7_Q2", column1X, 490);
        drawQuestion(canvas5, paint5, "Lograd.", column1X, 530);
        drawText(canvas5, paint5, "F3_S7_Q3", column1X, 550);
        drawQuestion(canvas5, paint5, "Número", column1X, 590);
        drawText(canvas5, paint5, "F3_S7_Q4", column1X, 610);
        drawQuestion(canvas5, paint5, "Complemento", column2X, 590);
        drawText(canvas5, paint5, "F3_S7_Q5", column2X, 610);
        drawQuestion(canvas5, paint5, "Bairro", column1X, 650);
        drawText(canvas5, paint5, "F3_S7_Q6", column1X, 670);
        drawQuestion(canvas5, paint5, "Cidade", column2X, 650);
        drawText(canvas5, paint5, "F3_S7_Q7", column2X, 670);
        drawQuestion(canvas5, paint5, "UF", column1X, 710);
        drawText(canvas5, paint5, "F3_S7_Q8", column1X, 730);

        pdfDocument.finishPage(page5);

        /* Página 6 */
        PdfDocument.PageInfo pageInfo6 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 6).create();
        PdfDocument.Page page6 = pdfDocument.startPage(pageInfo6);

        Canvas canvas6 = page6.getCanvas();
        Paint paint6 = new Paint();
        paint6.setColor(Color.parseColor(pdfColorBlue));
        paint6.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas6, paint6);

        drawTitle(canvas6, paint6, "Danos", column1X, 170);
        drawImage(canvas6, paint6, R.drawable.carro, 40, 190, 540, 360);
        drawCheckbox(canvas6, paint6, "F3_S8_Q1", 190, 100);

        pdfDocument.finishPage(page6);

        try {
            // Salvar o PDF no armazenamento do dispositivo
            pdfDocument.writeTo(new FileOutputStream(file));
            Log.i(logId, "PDF salvo com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(logId, "Erro ao salvar o PDF: " + e.getMessage());
        }

        pdfDocument.close();
        //submitPDF(file);
    }

    /* Função do PDF: Desenhando o cabeçalho */
    private void drawHeader(Canvas canvas, Paint paint) {
        canvas.drawText("E-brat gerado", 40, 80, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trlogo);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        canvas.drawBitmap(scaledBitmap, 480, 40, paint);

        paint.setStrokeWidth(2f);
        canvas.drawLine(40, 120, 540, 120, paint);
    }

    /* Função do PDF: Desenhando um retângulo com texto */
    private void drawRectWithText(Canvas canvas, Paint paint, String text, int x, int y, int width, int height) {
        paint.setColor(Color.parseColor("#0585FF"));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, (x + width), (y + height), paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        canvas.drawText(text, (x + 7.5F), ((y + height) - 10F), paint);
    }

    /* Função do PDF: Desenhando um título */
    private void drawTitle(Canvas canvas, Paint paint, String text, int x, int y) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(34);
        canvas.drawText(text, x, y, paint);
    }

    /* Função do PDF: Desenhando um título */
    private void drawQuestion(Canvas canvas, Paint paint, String text, int x, int y) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(24);
        canvas.drawText(text, x, y, paint);
    }

    /* Função do PDF: Desenhando um texto */
    private void drawText(Canvas canvas, Paint paint, String answerId, int x, int y) {
        paint.setColor(Color.GRAY);
        paint.setTextSize(24);

        try {
            TextView textView = (TextView) findViewByName(answerId);
            if(textView != null) {
                CharSequence text = textView.getText();
                if(text.length() > 0) {
                    canvas.drawText((String) text, x, y, paint);
                } else {
                    canvas.drawText("Não informado", x, y, paint);
                }
            }
        } catch(Exception e) {
            Log.e(logId, "" + e);
        }
    }

    /* Função do PDF: Desenhando uma imagem */
    private void drawImage(Canvas canvas, Paint paint, int image, int x, int y, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        canvas.drawBitmap(scaledBitmap, x, y, paint);
    }

    /* Função do PDF: Desenhando um checkbox */
    private void drawCheckbox(Canvas canvas, Paint paint, String answerId, int x, int y) {
        try {
            CheckBox checkBox = (CheckBox) findViewByName(answerId);
            if(checkBox != null) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.damage_area);
                Bitmap bitmap = drawableToBitmap(canvas, drawable);
                if (bitmap != null) {
                    assert drawable != null;
                    drawable.setBounds(0, 90, (500 / 3), (500 / 3));
                    drawable.draw(canvas);
                } else {
                    Log.e(logId, "E");
                }
            }
        } catch(Exception e) {
            Log.e(logId, "" + e);
        }
    }

    /* Método para obter uma View usando o nome da string do ID */
    private View findViewByName(String viewName) {
        int viewId = getResources().getIdentifier(viewName, "id", getPackageName());
        return findViewById(viewId);
    }

    /* Função para converter drawable em bitmap */
    private Bitmap drawableToBitmap(Canvas canvas, Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        if (width <= 0) {
            width = 100;
        }
        if (height <= 0) {
            height = 100;
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        //drawable.draw(canvas);

        return bitmap;
    }

    /* Função para enviar o PDF por email */
    private void submitPDF(File file) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("application/pdf");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eric26052004@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "E-brat gerado");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "O PDF está em um anexo.");

            Uri pdfUri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", file);
            emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(emailIntent, "Escolha um cliente de e-mail"));
            } else {
                Log.e(logId, "Nenhum cliente de e-mail encontrado");
            }
        } catch (Exception e) {
            Log.e(logId, "Erro ao enviar o PDF: " + e);
        }
    }
}