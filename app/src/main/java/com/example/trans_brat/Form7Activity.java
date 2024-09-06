package com.example.trans_brat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Form7Activity extends AppCompatActivity {
    private final String logId = "Form7Activity_LOG";
    private ArrayList<String> imagePaths;
    /* Respostas do formulário anterior
    [0] Id da resposta
    [1] Id do TextView de exibição
    [2] Tipo de resposta
    -> 0: Texto
    -> 1: Checkbox
    */
    private final String[][] allAnswers = {
            {"F1_S0_Q1", String.valueOf(R.id.F1_S0_Q1), "0"},
            {"F1_S0_Q2", String.valueOf(R.id.F1_S0_Q2), "0"},
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

    /* Configurações do PDF */
    String pdfColorBlue = "#0A4575";
    int pageWidth = 595; // Largura das páginas do PDF
    int pageHeight = 842; // Altura das páginas do PDF
    int column1X = 40; // X do início no PDF
    int column2X = 290; // X do meio no PDF
    int carWidth = 260; // Largura da imagem do carro no PDF
    int carHeight = 160; // Altura da imagem do carro no PDF
    int checkboxWidth = carWidth / 3; // Largura do checkbox do carro no PDF
    int checkboxHeight = carHeight / 2; // Largura do checkbox do carro no PDF

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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(intent);
            createPDF();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /* Função para criar PDF */
    private void createPDF() {
        String fileName = "e-brat.pdf";
        File directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        }
        File file = new File(directoryPath, fileName);
        PdfDocument pdfDocument = new PdfDocument();

        // Página 1
        createPage1(pdfDocument);

        // Página 2
        createPage2(pdfDocument);

        // Página 3
        createPage3(pdfDocument);

        // Página 4
        createPage4(pdfDocument);

        if(isAnswerMoreThan("F4_S0_Q1", "2")) {
            // Página 5
            createPage5(pdfDocument);

            // Página 6
            createPage6(pdfDocument);

            // Página 7
            createPage7(pdfDocument);
        }

        if(isAnswerMoreThan("F4_S0_Q1", "3")) {
            // Página 8
            createPage8(pdfDocument);

            // Página 9
            createPage9(pdfDocument);

            // Página 10
            createPage10(pdfDocument);
        }

        if(isAnswerMoreThan("F4_S0_Q1", "4")) {
            // Página 11
            createPage11(pdfDocument);

            // Página 12
            createPage12(pdfDocument);

            // Página 13
            createPage13(pdfDocument);
        }

        if(isAnswerMoreThan("F4_S0_Q1", "5")) {
            // Página 14
            createPage14(pdfDocument);

            // Página 15
            createPage15(pdfDocument);

            // Página 16
            createPage16(pdfDocument);
        }

        // Página 17
        if(isAnyAnswerValid(new String[] {"F5_S1_Q2", "F5_S1_Q3", "F5_S1_Q4", "F5_S2_Q2", "F5_S2_Q3", "F5_S2_Q4", "F5_S3_Q2", "F5_S3_Q3", "F5_S3_Q4", "F5_S4_Q2", "F5_S4_Q3", "F5_S4_Q4"})) {
            createPage17(pdfDocument);
        }

        // Página 18
        createPage18(pdfDocument);

        if (file.exists()) {
            file.delete();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Salvar o PDF no armazenamento do dispositivo
            pdfDocument.writeTo(fos);
            Log.i(logId, "PDF salvo com sucesso em: " + directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(logId, "Erro ao salvar o PDF: " + e.getMessage());
        }

        pdfDocument.close();
        submitPDF(file);
    }

    private void createPage1(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);

        Canvas canvas1 = page1.getCanvas();
        Paint paint1 = new Paint();
        paint1.setColor(Color.parseColor(pdfColorBlue));
        paint1.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas1, paint1);

        // Seção 1
        int baseY = 120;
        drawRectWithText(canvas1, paint1, "Enviado por", column1X, baseY, 500, 40);

        drawQuestion(canvas1, paint1, "Nome", column1X, baseY + 70);
        drawText(canvas1, paint1, "F1_S0_Q1", column1X, baseY + 90);
        drawQuestion(canvas1, paint1, "Matrícula", column1X, baseY + 130);
        drawText(canvas1, paint1, "F1_S0_Q2", column1X, baseY + 150);

        drawRectWithText(canvas1, paint1, "Dados da ocorrência", 40, baseY + 160, 500, 40);

        drawQuestion(canvas1, paint1, "Data", column1X, baseY + 230);
        drawText(canvas1, paint1, "F2_S1_Q2", column1X, baseY + 250);
        drawQuestion(canvas1, paint1, "Hora", column2X, baseY + 230);
        drawText(canvas1, paint1, "F2_S1_Q3", column2X, baseY + 250);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, baseY + 270, 540, baseY + 270, paint1);

        drawQuestion(canvas1, paint1, "Onde?", column1X, baseY + 300);
        drawText(canvas1, paint1, "F2_S2_Q2", column1X, baseY + 320);
        drawQuestion(canvas1, paint1, "Especifique", column1X, baseY + 360);
        drawText(canvas1, paint1, "F2_S2_Q3", column1X, baseY + 380);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, baseY + 400, 540, baseY + 400, paint1);

        drawQuestion(canvas1, paint1, "CEP", column1X, baseY + 440);
        drawText(canvas1, paint1, "F2_S3_Q2", column1X, baseY + 460);
        drawQuestion(canvas1, paint1, "Número", column2X, baseY + 440);
        drawText(canvas1, paint1, "F2_S3_Q4", column2X, baseY + 460);
        drawQuestion(canvas1, paint1, "Endereço", column1X, baseY + 500);
        drawText(canvas1, paint1, "F2_S3_Q3", column1X, baseY + 520);
        drawQuestion(canvas1, paint1, "Complemento", column1X, baseY + 560);
        drawText(canvas1, paint1, "F2_S3_Q5", column1X, baseY + 580);
        drawQuestion(canvas1, paint1, "Bairro", column2X, baseY + 560);
        drawText(canvas1, paint1, "F2_S3_Q6", column2X, baseY + 580);
        drawQuestion(canvas1, paint1, "Cidade", column1X, baseY + 620);
        drawText(canvas1, paint1, "F2_S3_Q7", column1X, baseY + 640);
        drawQuestion(canvas1, paint1, "Estado", column2X, baseY + 620);
        drawText(canvas1, paint1, "F2_S3_Q8", column2X, baseY + 640);
        drawQuestion(canvas1, paint1, "Ponto de referência", column1X, baseY + 680);
        drawText(canvas1, paint1, "F2_S3_Q9", column1X, baseY + 700);

        pdfDocument.finishPage(page1);
    }

    private void createPage2(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create();
        PdfDocument.Page page2 = pdfDocument.startPage(pageInfo2);

        Canvas canvas2 = page2.getCanvas();
        Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor(pdfColorBlue));
        paint2.setTextSize(42);

        int baseY = 40;

        drawQuestion(canvas2, paint2, "Quais condições de visibilidade?", column1X, baseY);
        drawText(canvas2, paint2, "F2_S4_Q2", column1X, baseY + 20);
        drawQuestion(canvas2, paint2, "Quais condições da pista?", column1X, baseY + 60);
        drawText(canvas2, paint2, "F2_S4_Q3", column1X, baseY + 80);
        drawQuestion(canvas2, paint2, "Quais as condições de sinalização?", column1X, baseY + 120);
        drawText(canvas2, paint2, "F2_S4_Q4", column1X, baseY + 140);
        drawQuestion(canvas2, paint2, "Qual foi o tipo de acidente?", column1X, baseY + 180);
        drawText(canvas2, paint2, "F2_S5_Q1", column1X, baseY + 200);

        drawRectWithText(canvas2, paint2, "Veículo principal", 40, baseY + 220, 500, 40);

        drawQuestion(canvas2, paint2, "Nº de ordem", column1X, baseY + 290);
        drawText(canvas2, paint2, "F3_S1_Q2", column1X, baseY + 310);
        drawQuestion(canvas2, paint2, "Placa", column2X, baseY + 290);
        drawText(canvas2, paint2, "F3_S1_Q3", column2X, baseY + 310);

        paint2.setColor(Color.GRAY);
        canvas2.drawLine(column1X, baseY + 330, 540, baseY + 330, paint2);

        drawQuestion(canvas2, paint2, "Marca/Modelo", column1X, baseY + 360);
        drawText(canvas2, paint2, "F3_S1_Q4", column1X, baseY + 380);
        drawQuestion(canvas2, paint2, "Tipo do veículo", column1X, baseY + 420);
        drawText(canvas2, paint2, "F3_S1_Q5", column1X, baseY + 440);
        drawQuestion(canvas2, paint2, "Cor predominante", column1X, baseY + 480);
        drawText(canvas2, paint2, "F3_S1_Q6", column1X, baseY + 500);
        drawQuestion(canvas2, paint2, "UF", column2X, baseY + 480);
        drawText(canvas2, paint2, "F3_S1_Q7", column2X, baseY + 500);
        drawQuestion(canvas2, paint2, "Ano de fabricação", column1X, baseY + 540);
        drawText(canvas2, paint2, "F3_S1_Q8", column1X, baseY + 560);
        drawQuestion(canvas2, paint2, "Ano do modelo", column2X, baseY + 540);
        drawText(canvas2, paint2, "F3_S1_Q9", column2X, baseY + 560);
        drawQuestion(canvas2, paint2, "Nº do Chassi", column1X, baseY + 600);
        drawText(canvas2, paint2, "F3_S1_Q10", column1X, baseY + 620);
        drawQuestion(canvas2, paint2, "RENAVAM", column2X, baseY + 600);
        drawText(canvas2, paint2, "F3_S1_Q11", column2X, baseY + 620);
        drawQuestion(canvas2, paint2, "O veículo é da empresa?", column1X, baseY + 660);
        drawText(canvas2, paint2, "F3_S1_Q12", column1X, baseY + 680);

        pdfDocument.finishPage(page2);
    }

    private void createPage3(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 3).create();
        PdfDocument.Page page3 = pdfDocument.startPage(pageInfo3);

        Canvas canvas3 = page3.getCanvas();
        Paint paint3 = new Paint();
        paint3.setColor(Color.parseColor(pdfColorBlue));
        paint3.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F3_S2_Q2", "F3_S2_Q3"})) {
            drawTitle(canvas3, paint3, "Dados da empresa", column1X, baseY);
            drawQuestion(canvas3, paint3, "Razão social", column1X, baseY + 40);
            drawText(canvas3, paint3, "F3_S2_Q2", column1X, baseY + 60);
            drawQuestion(canvas3, paint3, "CNPJ", column1X, baseY + 100);
            drawText(canvas3, paint3, "F3_S2_Q3", column1X, baseY + 120);

            paint3.setColor(Color.GRAY);
            canvas3.drawLine(column1X, baseY + 140, 540, baseY + 140, paint3);
            baseY += 180;
        }

        if(isAnyAnswerValid(new String[] {"F3_S3_Q2", "F3_S3_Q3", "F3_S3_Q4", "F3_S3_Q5"})) {
            drawTitle(canvas3, paint3, "Dados do proprietário", column1X, baseY);
            drawQuestion(canvas3, paint3, "Nome", column1X, baseY + 40);
            drawText(canvas3, paint3, "F3_S3_Q2", column1X, baseY + 60);
            drawQuestion(canvas3, paint3, "CPF", column1X, baseY + 100);
            drawText(canvas3, paint3, "F3_S3_Q3", column1X, baseY + 120);
            drawQuestion(canvas3, paint3, "Data nascimento", column2X, baseY + 100);
            drawText(canvas3, paint3, "F3_S3_Q4", column2X, baseY + 120);
            drawQuestion(canvas3, paint3, "O proprietário é o condutor?", column1X, baseY + 160);
            drawText(canvas3, paint3, "F3_S3_Q5", column1X, baseY + 180);

            paint3.setColor(Color.GRAY);
            canvas3.drawLine(column1X, baseY + 200, 540, baseY + 200, paint3);
            baseY += 240;
        }

        if(isAnyAnswerValid(new String[] {"F3_S4_Q2", "F3_S4_Q3", "F3_S4_Q4", "F3_S4_Q5", "F3_S4_Q6", "F3_S4_Q7", "F3_S4_Q8", "F3_S4_Q9"})) {
            drawTitle(canvas3, paint3, "Endereço", column1X, baseY);
            drawQuestion(canvas3, paint3, "CEP", column1X, baseY + 40);
            drawText(canvas3, paint3, "F3_S4_Q2", column1X, baseY + 60);
            drawQuestion(canvas3, paint3, "Lograd.", column1X, baseY + 100);
            drawText(canvas3, paint3, "F3_S4_Q3", column1X, baseY + 120);
            drawQuestion(canvas3, paint3, "Número", column1X, baseY + 160);
            drawText(canvas3, paint3, "F3_S4_Q4", column1X, baseY + 180);
            drawQuestion(canvas3, paint3, "Complemento", column2X, baseY + 160);
            drawText(canvas3, paint3, "F3_S4_Q5", column2X, baseY + 180);
            drawQuestion(canvas3, paint3, "Bairro", column1X, baseY + 220);
            drawText(canvas3, paint3, "F3_S4_Q6", column1X, baseY + 240);
            drawQuestion(canvas3, paint3, "Cidade", column2X, baseY + 220);
            drawText(canvas3, paint3, "F3_S4_Q7", column2X, baseY + 240);
            drawQuestion(canvas3, paint3, "UF", column1X, baseY + 280);
            drawText(canvas3, paint3, "F3_S4_Q8", column1X, baseY + 300);
            drawQuestion(canvas3, paint3, "O proprietário é o condutor?", column1X, baseY + 340);
            drawText(canvas3, paint3, "F3_S4_Q9", column1X, baseY + 360);

            paint3.setColor(Color.GRAY);
            canvas3.drawLine(column1X, baseY + 380, 540, baseY + 380, paint3);
            baseY += 420;
        }

        if(isAnyAnswerValid(new String[] {"F3_S5_Q2", "F3_S5_Q3", "F3_S5_Q4", "F3_S5_Q5", "F3_S5_Q6", "F3_S5_Q7", "F3_S5_Q8", "F3_S5_Q9", "F3_S5_Q10"})) {
            drawTitle(canvas3, paint3, "Endereço do proprietário", column1X, baseY);
            drawQuestion(canvas3, paint3, "CEP", column1X, baseY + 40);
            drawText(canvas3, paint3, "F3_S5_Q2", column1X, baseY + 60);
            drawQuestion(canvas3, paint3, "Lograd.", column1X, baseY + 100);
            drawText(canvas3, paint3, "F3_S5_Q3", column1X, baseY + 120);
            drawQuestion(canvas3, paint3, "Número", column1X, baseY + 160);
            drawText(canvas3, paint3, "F3_S5_Q4", column1X, baseY + 180);
            drawQuestion(canvas3, paint3, "Complemento", column2X, baseY + 160);
            drawText(canvas3, paint3, "F3_S5_Q5", column2X, baseY + 180);
            drawQuestion(canvas3, paint3, "Bairro", column1X, baseY + 220);
            drawText(canvas3, paint3, "F3_S5_Q6", column1X, baseY + 240);
            drawQuestion(canvas3, paint3, "Cidade", column2X, baseY + 220);
            drawText(canvas3, paint3, "F3_S5_Q7", column2X, baseY + 240);
            drawQuestion(canvas3, paint3, "UF", column1X, baseY + 280);
            drawText(canvas3, paint3, "F3_S5_Q8", column1X, baseY + 300);
            drawQuestion(canvas3, paint3, "Nº CNH", column2X, baseY + 280);
            drawText(canvas3, paint3, "F3_S5_Q9", column2X, baseY + 300);
            drawQuestion(canvas3, paint3, "Validade", column1X, baseY + 340);
            drawText(canvas3, paint3, "F3_S5_Q10", column1X, baseY + 360);
        }

        pdfDocument.finishPage(page3);
    }

    private void createPage4(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo4 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 4).create();
        PdfDocument.Page page4 = pdfDocument.startPage(pageInfo4);

        Canvas canvas4 = page4.getCanvas();
        Paint paint4 = new Paint();
        paint4.setColor(Color.parseColor(pdfColorBlue));
        paint4.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F3_S6_Q2", "F3_S6_Q3", "F3_S6_Q4", "F3_S6_Q5", "F3_S6_Q6"})) {
            drawTitle(canvas4, paint4, "Dados do condutor", column1X, baseY);
            drawQuestion(canvas4, paint4, "Nome", column1X, baseY + 40);
            drawText(canvas4, paint4, "F3_S6_Q2", column1X, baseY + 60);
            drawQuestion(canvas4, paint4, "CPF", column1X, baseY + 100);
            drawText(canvas4, paint4, "F3_S6_Q3", column1X, baseY + 120);
            drawQuestion(canvas4, paint4, "Data de nascimento", column2X, baseY + 100);
            drawText(canvas4, paint4, "F3_S6_Q4", column2X, baseY + 120);
            drawQuestion(canvas4, paint4, "Nº CNH", column1X, baseY + 160);
            drawText(canvas4, paint4, "F3_S6_Q5", column1X, baseY + 180);
            drawQuestion(canvas4, paint4, "Validade", column2X, baseY + 160);
            drawText(canvas4, paint4, "F3_S6_Q6", column2X, baseY + 180);

            paint4.setColor(Color.GRAY);
            canvas4.drawLine(column1X, baseY + 200, 540, baseY + 200, paint4);
            baseY += 240;
        }

        if(isAnyAnswerValid(new String[] {"F3_S7_Q2", "F3_S7_Q3", "F3_S7_Q4", "F3_S7_Q5", "F3_S7_Q6", "F3_S7_Q7", "F3_S7_Q8"})) {
            drawTitle(canvas4, paint4, "Endereço do condutor", column1X, baseY);
            drawQuestion(canvas4, paint4, "CEP", column1X, baseY + 40);
            drawText(canvas4, paint4, "F3_S7_Q2", column1X, baseY + 60);
            drawQuestion(canvas4, paint4, "Lograd.", column1X, baseY + 100);
            drawText(canvas4, paint4, "F3_S7_Q3", column1X, baseY + 120);
            drawQuestion(canvas4, paint4, "Número", column1X, baseY + 160);
            drawText(canvas4, paint4, "F3_S7_Q4", column1X, baseY + 180);
            drawQuestion(canvas4, paint4, "Complemento", column2X, baseY + 160);
            drawText(canvas4, paint4, "F3_S7_Q5", column2X, baseY + 180);
            drawQuestion(canvas4, paint4, "Bairro", column1X, baseY + 220);
            drawText(canvas4, paint4, "F3_S7_Q6", column1X, baseY + 240);
            drawQuestion(canvas4, paint4, "Cidade", column2X, baseY + 220);
            drawText(canvas4, paint4, "F3_S7_Q7", column2X, baseY + 240);
            drawQuestion(canvas4, paint4, "UF", column1X, baseY + 280);
            drawText(canvas4, paint4, "F3_S7_Q8", column1X, baseY + 300);

            paint4.setColor(Color.GRAY);
            canvas4.drawLine(column1X, baseY + 320, 540, baseY + 320, paint4);
            baseY += 360;
        }

        drawTitle(canvas4, paint4, "Danos", column1X, baseY);
        drawImage(canvas4, paint4, R.drawable.carro, 40, baseY + 20, carWidth, carHeight, 180);
        drawCheckbox(canvas4, paint4, "F3_S8_Q1", 40, baseY + 20, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas4, paint4, "F3_S8_Q2", 40 + checkboxWidth, baseY + 20, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas4, paint4, "F3_S8_Q3", 40 + (checkboxWidth * 2), baseY + 20, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas4, paint4, "F3_S8_Q4", 40, baseY + 20 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas4, paint4, "F3_S8_Q5", 40 + checkboxWidth, baseY + 20 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas4, paint4, "F3_S8_Q6", 40 + (checkboxWidth * 2), baseY + 20 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page4);
    }

    private void createPage5(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo5 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 5).create();
        PdfDocument.Page page5 = pdfDocument.startPage(pageInfo5);

        Canvas canvas5 = page5.getCanvas();
        Paint paint5 = new Paint();
        paint5.setColor(Color.parseColor(pdfColorBlue));
        paint5.setTextSize(42);

        int baseY = 20;

        drawRectWithText(canvas5, paint5, "Veículo 2", 40, baseY, 500, 40);
        drawQuestion(canvas5, paint5, "Placa", column1X, baseY + 80);
        drawText(canvas5, paint5, "F4_S1_M1_Q3", column1X, baseY + 100);
        drawQuestion(canvas5, paint5, "Marca/Modelo", column1X, baseY + 140);
        drawText(canvas5, paint5, "F4_S1_M1_Q4", column1X, baseY + 160);
        drawQuestion(canvas5, paint5, "Tipo do veículo", column2X, baseY + 140);
        drawText(canvas5, paint5, "F4_S1_M1_Q5", column2X, baseY + 160);
        drawQuestion(canvas5, paint5, "Cor predominante", column1X, baseY + 200);
        drawText(canvas5, paint5, "F4_S1_M1_Q6", column1X, baseY + 220);
        drawQuestion(canvas5, paint5, "UF", column2X, baseY + 200);
        drawText(canvas5, paint5, "F4_S1_M1_Q7", column2X, baseY + 220);
        drawQuestion(canvas5, paint5, "Ano de fabricação", column1X, baseY + 260);
        drawText(canvas5, paint5, "F4_S1_M1_Q8", column1X, baseY + 280);
        drawQuestion(canvas5, paint5, "Ano do modelo", column2X, baseY + 260);
        drawText(canvas5, paint5, "F4_S1_M1_Q9", column2X, baseY + 280);
        drawQuestion(canvas5, paint5, "Nº do Chassi", column1X, baseY + 320);
        drawText(canvas5, paint5, "F4_S1_M1_Q10", column1X, baseY + 340);
        drawQuestion(canvas5, paint5, "RENAVAM", column2X, baseY + 320);
        drawText(canvas5, paint5, "F4_S1_M1_Q11", column2X, baseY + 340);
        drawQuestion(canvas5, paint5, "Veículo é da empresa?", column1X, baseY + 380);
        drawText(canvas5, paint5, "F4_S1_M1_Q12", column1X, baseY + 400);

        if(isAnyAnswerValid(new String[] {"F4_S1_M2_Q2", "F4_S1_M2_Q3"})) {
            drawTitle(canvas5, paint5, "Dados da empresa", column1X, baseY + 440);
            drawQuestion(canvas5, paint5, "Razão social", column1X, baseY + 480);
            drawText(canvas5, paint5, "F4_S1_M2_Q2", column1X, baseY + 500);
            drawQuestion(canvas5, paint5, "CNPJ", column1X, baseY + 540);
            drawText(canvas5, paint5, "F4_S1_M2_Q3", column1X, baseY + 560);

            paint5.setColor(Color.GRAY);
            canvas5.drawLine(column1X, baseY + 580, 540, baseY + 580, paint5);
            baseY += 620;
        } else {
            baseY += 440;
        }

        if(isAnyAnswerValid(new String[] {"F4_S1_M3_Q2", "F4_S1_M3_Q3", "F4_S1_M3_Q4", "F4_S1_M3_Q5"})) {
            drawTitle(canvas5, paint5, "Dados do proprietário", column1X, baseY);
            drawQuestion(canvas5, paint5, "Nome", column1X, baseY + 40);
            drawText(canvas5, paint5, "F4_S1_M3_Q2", column1X, baseY + 60);
            drawQuestion(canvas5, paint5, "CPF", column1X, baseY + 100);
            drawText(canvas5, paint5, "F4_S1_M3_Q3", column1X, baseY + 120);
            drawQuestion(canvas5, paint5, "Data nascimento", column2X, baseY + 100);
            drawText(canvas5, paint5, "F4_S1_M3_Q4", column2X, baseY + 120);
            drawQuestion(canvas5, paint5, "O proprietário é o condutor?", column1X, baseY + 160);
            drawText(canvas5, paint5, "F4_S1_M3_Q5", column1X, baseY + 180);
        }

        pdfDocument.finishPage(page5);
    }

    private void createPage6(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo6 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 6).create();
        PdfDocument.Page page6 = pdfDocument.startPage(pageInfo6);

        Canvas canvas6 = page6.getCanvas();
        Paint paint6 = new Paint();
        paint6.setColor(Color.parseColor(pdfColorBlue));
        paint6.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F4_S1_M4_Q2", "F4_S1_M4_Q3", "F4_S1_M4_Q4", "F4_S1_M4_Q5", "F4_S1_M4_Q6", "F4_S1_M4_Q7", "F4_S1_M4_Q8", "F4_S1_M4_Q9"})) {
            drawTitle(canvas6, paint6, "Endereço", column1X, baseY);
            drawQuestion(canvas6, paint6, "CEP", column1X, baseY + 40);
            drawText(canvas6, paint6, "F4_S1_M4_Q2", column1X, baseY + 60);
            drawQuestion(canvas6, paint6, "Lograd.", column1X, baseY + 100);
            drawText(canvas6, paint6, "F4_S1_M4_Q3", column1X, baseY + 120);
            drawQuestion(canvas6, paint6, "Número", column1X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M4_Q4", column1X, baseY + 180);
            drawQuestion(canvas6, paint6, "Complemento", column2X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M4_Q5", column2X, baseY + 180);
            drawQuestion(canvas6, paint6, "Bairro", column1X, baseY + 220);
            drawText(canvas6, paint6, "F4_S1_M4_Q6", column1X, baseY + 240);
            drawQuestion(canvas6, paint6, "Cidade", column2X, baseY + 220);
            drawText(canvas6, paint6, "F4_S1_M4_Q7", column2X, baseY + 240);
            drawQuestion(canvas6, paint6, "UF", column1X, baseY + 280);
            drawText(canvas6, paint6, "F4_S1_M4_Q8", column1X, baseY + 300);
            drawQuestion(canvas6, paint6, "O proprietário é o condutor?", column2X, baseY + 280);
            drawText(canvas6, paint6, "F4_S1_M4_Q9", column2X, baseY + 300);

            paint6.setColor(Color.GRAY);
            canvas6.drawLine(column1X, baseY + 320, 540, baseY + 320, paint6);
            baseY += 360;
        }

        if(isAnyAnswerValid(new String[] {"F4_S1_M5_Q2", "F4_S1_M5_Q3", "F4_S1_M5_Q4", "F4_S1_M5_Q5", "F4_S1_M5_Q6", "F4_S1_M5_Q7", "F4_S1_M5_Q8", "F4_S1_M5_Q9", "F4_S1_M5_Q10"})) {
            drawTitle(canvas6, paint6, "Endereço do proprietário", column1X, baseY);
            drawQuestion(canvas6, paint6, "CEP", column1X, baseY + 40);
            drawText(canvas6, paint6, "F4_S1_M5_Q2", column1X, baseY + 60);
            drawQuestion(canvas6, paint6, "Lograd.", column1X, baseY + 100);
            drawText(canvas6, paint6, "F4_S1_M5_Q3", column1X, baseY + 120);
            drawQuestion(canvas6, paint6, "Número", column1X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M5_Q4", column1X, baseY + 180);
            drawQuestion(canvas6, paint6, "Complemento", column2X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M5_Q5", column2X, baseY + 180);
            drawQuestion(canvas6, paint6, "Bairro", column1X, baseY + 220);
            drawText(canvas6, paint6, "F4_S1_M5_Q6", column1X, baseY + 240);
            drawQuestion(canvas6, paint6, "Cidade", column2X, baseY + 220);
            drawText(canvas6, paint6, "F4_S1_M5_Q7", column2X, baseY + 240);
            drawQuestion(canvas6, paint6, "UF", column1X, baseY + 280);
            drawText(canvas6, paint6, "F4_S1_M5_Q8", column1X, baseY + 300);
            drawQuestion(canvas6, paint6, "Nº CNH", column2X, baseY + 280);
            drawText(canvas6, paint6, "F4_S1_M5_Q9", column2X, baseY + 300);
            drawQuestion(canvas6, paint6, "Validade", column1X, baseY + 340);
            drawText(canvas6, paint6, "F4_S1_M5_Q10", column1X, baseY + 360);

            paint6.setColor(Color.GRAY);
            canvas6.drawLine(column1X, baseY + 380, 540, baseY + 380, paint6);
            baseY += 420;
        }

        if(isAnyAnswerValid(new String[] {"F4_S1_M6_Q2", "F4_S1_M6_Q3", "F4_S1_M6_Q4", "F4_S1_M6_Q5", "F4_S1_M6_Q6"})) {
            drawTitle(canvas6, paint6, "Dados do condutor", column1X, baseY);
            drawQuestion(canvas6, paint6, "Nome", column1X, baseY + 40);
            drawText(canvas6, paint6, "F4_S1_M6_Q2", column1X, baseY + 60);
            drawQuestion(canvas6, paint6, "CPF", column1X, baseY + 100);
            drawText(canvas6, paint6, "F4_S1_M6_Q3", column1X, baseY + 120);
            drawQuestion(canvas6, paint6, "Data de nascimento", column2X, baseY + 100);
            drawText(canvas6, paint6, "F4_S1_M6_Q4", column2X, baseY + 120);
            drawQuestion(canvas6, paint6, "Nº CNH", column1X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M6_Q5", column1X, baseY + 180);
            drawQuestion(canvas6, paint6, "Validade", column2X, baseY + 160);
            drawText(canvas6, paint6, "F4_S1_M6_Q6", column2X, baseY + 180);
        }

        pdfDocument.finishPage(page6);
    }

    private void createPage7(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo7 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 7).create();
        PdfDocument.Page page7 = pdfDocument.startPage(pageInfo7);

        Canvas canvas7 = page7.getCanvas();
        Paint paint7 = new Paint();
        paint7.setColor(Color.parseColor(pdfColorBlue));
        paint7.setTextSize(42);

        int baseY = 40;

        if(!isAnyAnswerValid(new String[] {"F4_S1_M7_Q2", "F4_S1_M7_Q3", "F4_S1_M7_Q4", "F4_S1_M7_Q5", "F4_S1_M7_Q6", "F4_S1_M7_Q7", "F4_S1_M7_Q8"})) {
            drawTitle(canvas7, paint7, "Endereço do condutor", column1X, baseY);
            drawQuestion(canvas7, paint7, "CEP", column1X, baseY + 40);
            drawText(canvas7, paint7, "F4_S1_M7_Q2", column1X, baseY + 60);
            drawQuestion(canvas7, paint7, "Lograd.", column1X, baseY + 100);
            drawText(canvas7, paint7, "F4_S1_M7_Q3", column1X, baseY + 120);
            drawQuestion(canvas7, paint7, "Número", column1X, baseY + 160);
            drawText(canvas7, paint7, "F4_S1_M7_Q4", column1X, baseY + 180);
            drawQuestion(canvas7, paint7, "Complemento", column2X, baseY + 160);
            drawText(canvas7, paint7, "F4_S1_M7_Q5", column2X, baseY + 180);
            drawQuestion(canvas7, paint7, "Bairro", column1X, baseY + 220);
            drawText(canvas7, paint7, "F4_S1_M7_Q6", column1X, baseY + 240);
            drawQuestion(canvas7, paint7, "Cidade", column2X, baseY + 220);
            drawText(canvas7, paint7, "F4_S1_M7_Q7", column2X, baseY + 240);
            drawQuestion(canvas7, paint7, "UF", column1X, baseY + 280);
            drawText(canvas7, paint7, "F4_S1_M7_Q8", column1X, baseY + 300);

            paint7.setColor(Color.GRAY);
            canvas7.drawLine(column1X, baseY + 320, 540, baseY + 320, paint7);
            baseY += 360;
        }

        drawTitle(canvas7, paint7, "Danos", column1X, baseY);
        drawImage(canvas7, paint7, R.drawable.carro, 40, baseY + 40, carWidth, carHeight, 180);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q1", 40, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q2", 40 + checkboxWidth, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q3", 40 + (checkboxWidth * 2), baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q4", 40, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q5", 40 + checkboxWidth, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas7, paint7, "F4_S1_M8_Q6", 40 + (checkboxWidth * 2), baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page7);
    }

    private void createPage8(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo8 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 8).create();
        PdfDocument.Page page8 = pdfDocument.startPage(pageInfo8);

        Canvas canvas8 = page8.getCanvas();
        Paint paint8 = new Paint();
        paint8.setColor(Color.parseColor(pdfColorBlue));
        paint8.setTextSize(42);

        int baseY = 20;

        drawRectWithText(canvas8, paint8, "Veículo 3", 40, baseY, 500, 40);
        drawQuestion(canvas8, paint8, "Placa", column1X, baseY + 80);
        drawText(canvas8, paint8, "F4_S2_M1_Q3", column1X, baseY + 100);
        drawQuestion(canvas8, paint8, "Marca/Modelo", column1X, baseY + 140);
        drawText(canvas8, paint8, "F4_S2_M1_Q4", column1X, baseY + 160);
        drawQuestion(canvas8, paint8, "Tipo do veículo", column2X, baseY + 140);
        drawText(canvas8, paint8, "F4_S2_M1_Q5", column2X, baseY + 160);
        drawQuestion(canvas8, paint8, "Cor predominante", column1X, baseY + 200);
        drawText(canvas8, paint8, "F4_S2_M1_Q6", column1X, baseY + 220);
        drawQuestion(canvas8, paint8, "UF", column2X, baseY + 200);
        drawText(canvas8, paint8, "F4_S2_M1_Q7", column2X, baseY + 220);
        drawQuestion(canvas8, paint8, "Ano de fabricação", column1X, baseY + 260);
        drawText(canvas8, paint8, "F4_S2_M1_Q8", column1X, baseY + 280);
        drawQuestion(canvas8, paint8, "Ano do modelo", column2X, baseY + 260);
        drawText(canvas8, paint8, "F4_S2_M1_Q9", column2X, baseY + 280);
        drawQuestion(canvas8, paint8, "Nº do Chassi", column1X, baseY + 320);
        drawText(canvas8, paint8, "F4_S2_M1_Q10", column1X, baseY + 340);
        drawQuestion(canvas8, paint8, "RENAVAM", column2X, baseY + 320);
        drawText(canvas8, paint8, "F4_S2_M1_Q11", column2X, baseY + 340);
        drawQuestion(canvas8, paint8, "Veículo é da empresa?", column1X, baseY + 380);
        drawText(canvas8, paint8, "F4_S2_M1_Q12", column1X, baseY + 400);

        if(isAnyAnswerValid(new String[] {"F4_S2_M2_Q2", "F4_S2_M2_Q3"})) {
            drawTitle(canvas8, paint8, "Dados da empresa", column1X, baseY + 440);
            drawQuestion(canvas8, paint8, "Razão social", column1X, baseY + 480);
            drawText(canvas8, paint8, "F4_S2_M2_Q2", column1X, baseY + 500);
            drawQuestion(canvas8, paint8, "CNPJ", column1X, baseY + 540);
            drawText(canvas8, paint8, "F4_S2_M2_Q3", column1X, baseY + 560);

            paint8.setColor(Color.GRAY);
            canvas8.drawLine(column1X, baseY + 580, 540, baseY + 580, paint8);
            baseY += 620;
        } else {
            baseY += 440;
        }

        if(isAnyAnswerValid(new String[] {"F4_S2_M3_Q2", "F4_S2_M3_Q3", "F4_S2_M3_Q4", "F4_S2_M3_Q5"})) {
            drawTitle(canvas8, paint8, "Dados do proprietário", column1X, baseY);
            drawQuestion(canvas8, paint8, "Nome", column1X, baseY + 40);
            drawText(canvas8, paint8, "F4_S2_M3_Q2", column1X, baseY + 60);
            drawQuestion(canvas8, paint8, "CPF", column1X, baseY + 100);
            drawText(canvas8, paint8, "F4_S2_M3_Q3", column1X, baseY + 120);
            drawQuestion(canvas8, paint8, "Data nascimento", column2X, baseY + 100);
            drawText(canvas8, paint8, "F4_S2_M3_Q4", column2X, baseY + 120);
            drawQuestion(canvas8, paint8, "O proprietário é o condutor?", column1X, baseY + 160);
            drawText(canvas8, paint8, "F4_S2_M3_Q5", column1X, baseY + 180);
        }

        pdfDocument.finishPage(page8);
    }

    private void createPage9(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo9 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 9).create();
        PdfDocument.Page page9 = pdfDocument.startPage(pageInfo9);

        Canvas canvas9 = page9.getCanvas();
        Paint paint9 = new Paint();
        paint9.setColor(Color.parseColor(pdfColorBlue));
        paint9.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F4_S2_M4_Q2", "F4_S2_M4_Q3", "F4_S2_M4_Q4", "F4_S2_M4_Q5", "F4_S2_M4_Q6", "F4_S2_M4_Q7", "F4_S2_M4_Q8", "F4_S2_M4_Q9"})) {
            drawTitle(canvas9, paint9, "Endereço", column1X, baseY);
            drawQuestion(canvas9, paint9, "CEP", column1X, baseY + 40);
            drawText(canvas9, paint9, "F4_S2_M4_Q2", column1X, baseY + 60);
            drawQuestion(canvas9, paint9, "Lograd.", column1X, baseY + 100);
            drawText(canvas9, paint9, "F4_S2_M4_Q3", column1X, baseY + 120);
            drawQuestion(canvas9, paint9, "Número", column1X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M4_Q4", column1X, baseY + 180);
            drawQuestion(canvas9, paint9, "Complemento", column2X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M4_Q5", column2X, baseY + 180);
            drawQuestion(canvas9, paint9, "Bairro", column1X, baseY + 220);
            drawText(canvas9, paint9, "F4_S2_M4_Q6", column1X, baseY + 240);
            drawQuestion(canvas9, paint9, "Cidade", column2X, baseY + 220);
            drawText(canvas9, paint9, "F4_S2_M4_Q7", column2X, baseY + 240);
            drawQuestion(canvas9, paint9, "UF", column1X, baseY + 280);
            drawText(canvas9, paint9, "F4_S2_M4_Q8", column1X, baseY + 300);
            drawQuestion(canvas9, paint9, "O proprietário é o condutor?", column2X, baseY + 280);
            drawText(canvas9, paint9, "F4_S2_M4_Q9", column2X, baseY + 300);

            paint9.setColor(Color.GRAY);
            canvas9.drawLine(column1X, baseY + 320, 540, baseY + 320, paint9);
            baseY += 360;
        }

        if(isAnyAnswerValid(new String[] {"F4_S2_M5_Q2", "F4_S2_M5_Q3", "F4_S2_M5_Q4", "F4_S2_M5_Q5", "F4_S2_M5_Q6", "F4_S2_M5_Q7", "F4_S2_M5_Q8", "F4_S2_M5_Q9", "F4_S2_M5_Q10"})) {
            drawTitle(canvas9, paint9, "Endereço do proprietário", column1X, baseY);
            drawQuestion(canvas9, paint9, "CEP", column1X, baseY + 40);
            drawText(canvas9, paint9, "F4_S2_M5_Q2", column1X, baseY + 60);
            drawQuestion(canvas9, paint9, "Lograd.", column1X, baseY + 100);
            drawText(canvas9, paint9, "F4_S2_M5_Q3", column1X, baseY + 120);
            drawQuestion(canvas9, paint9, "Número", column1X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M5_Q4", column1X, baseY + 180);
            drawQuestion(canvas9, paint9, "Complemento", column2X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M5_Q5", column2X, baseY + 180);
            drawQuestion(canvas9, paint9, "Bairro", column1X, baseY + 220);
            drawText(canvas9, paint9, "F4_S2_M5_Q6", column1X, baseY + 240);
            drawQuestion(canvas9, paint9, "Cidade", column2X, baseY + 220);
            drawText(canvas9, paint9, "F4_S2_M5_Q7", column2X, baseY + 240);
            drawQuestion(canvas9, paint9, "UF", column1X, baseY + 280);
            drawText(canvas9, paint9, "F4_S2_M5_Q8", column1X, baseY + 300);
            drawQuestion(canvas9, paint9, "Nº CNH", column2X, baseY + 280);
            drawText(canvas9, paint9, "F4_S2_M5_Q9", column2X, baseY + 300);
            drawQuestion(canvas9, paint9, "Validade", column1X, baseY + 340);
            drawText(canvas9, paint9, "F4_S2_M5_Q10", column1X, baseY + 360);

            paint9.setColor(Color.GRAY);
            canvas9.drawLine(column1X, baseY + 380, 540, baseY + 380, paint9);
            baseY += 420;
        }

        if(isAnyAnswerValid(new String[] {"F4_S2_M6_Q2", "F4_S2_M6_Q3", "F4_S2_M6_Q4", "F4_S2_M6_Q5", "F4_S2_M6_Q6"})) {
            drawTitle(canvas9, paint9, "Dados do condutor", column1X, baseY);
            drawQuestion(canvas9, paint9, "Nome", column1X, baseY + 40);
            drawText(canvas9, paint9, "F4_S2_M6_Q2", column1X, baseY + 60);
            drawQuestion(canvas9, paint9, "CPF", column1X, baseY + 100);
            drawText(canvas9, paint9, "F4_S2_M6_Q3", column1X, baseY + 120);
            drawQuestion(canvas9, paint9, "Data de nascimento", column2X, baseY + 100);
            drawText(canvas9, paint9, "F4_S2_M6_Q4", column2X, baseY + 120);
            drawQuestion(canvas9, paint9, "Nº CNH", column1X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M6_Q5", column1X, baseY + 180);
            drawQuestion(canvas9, paint9, "Validade", column2X, baseY + 160);
            drawText(canvas9, paint9, "F4_S2_M6_Q6", column2X, baseY + 180);
        }

        pdfDocument.finishPage(page9);
    }

    private void createPage10(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo10 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 10).create();
        PdfDocument.Page page10 = pdfDocument.startPage(pageInfo10);

        Canvas canvas10 = page10.getCanvas();
        Paint paint10 = new Paint();
        paint10.setColor(Color.parseColor(pdfColorBlue));
        paint10.setTextSize(42);

        int baseY = 40;

        if(!isAnyAnswerValid(new String[] {"F4_S2_M7_Q2", "F4_S2_M7_Q3", "F4_S2_M7_Q4", "F4_S2_M7_Q5", "F4_S2_M7_Q6", "F4_S2_M7_Q7", "F4_S2_M7_Q8"})) {
            drawTitle(canvas10, paint10, "Endereço do condutor", column1X, baseY);
            drawQuestion(canvas10, paint10, "CEP", column1X, baseY + 40);
            drawText(canvas10, paint10, "F4_S2_M7_Q2", column1X, baseY + 60);
            drawQuestion(canvas10, paint10, "Lograd.", column1X, baseY + 100);
            drawText(canvas10, paint10, "F4_S2_M7_Q3", column1X, baseY + 120);
            drawQuestion(canvas10, paint10, "Número", column1X, baseY + 160);
            drawText(canvas10, paint10, "F4_S2_M7_Q4", column1X, baseY + 180);
            drawQuestion(canvas10, paint10, "Complemento", column2X, baseY + 160);
            drawText(canvas10, paint10, "F4_S2_M7_Q5", column2X, baseY + 180);
            drawQuestion(canvas10, paint10, "Bairro", column1X, baseY + 220);
            drawText(canvas10, paint10, "F4_S2_M7_Q6", column1X, baseY + 240);
            drawQuestion(canvas10, paint10, "Cidade", column2X, baseY + 220);
            drawText(canvas10, paint10, "F4_S2_M7_Q7", column2X, baseY + 240);
            drawQuestion(canvas10, paint10, "UF", column1X, baseY + 280);
            drawText(canvas10, paint10, "F4_S2_M7_Q8", column1X, baseY + 300);

            paint10.setColor(Color.GRAY);
            canvas10.drawLine(column1X, baseY + 320, 540, baseY + 320, paint10);
            baseY += 360;
        }

        drawTitle(canvas10, paint10, "Danos", column1X, baseY);
        drawImage(canvas10, paint10, R.drawable.carro, 40, baseY + 40, carWidth, carHeight, 180);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q1", 40, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q2", 40 + checkboxWidth, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q3", 40 + (checkboxWidth * 2), baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q4", 40, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q5", 40 + checkboxWidth, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S2_M8_Q6", 40 + (checkboxWidth * 2), baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page10);
    }

    private void createPage11(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo11 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 11).create();
        PdfDocument.Page page11 = pdfDocument.startPage(pageInfo11);

        Canvas canvas11 = page11.getCanvas();
        Paint paint11 = new Paint();
        paint11.setColor(Color.parseColor(pdfColorBlue));
        paint11.setTextSize(42);

        int baseY = 20;

        drawRectWithText(canvas11, paint11, "Veículo 4", 40, baseY, 500, 40);
        drawQuestion(canvas11, paint11, "Placa", column1X, baseY + 80);
        drawText(canvas11, paint11, "F4_S3_M1_Q3", column1X, baseY + 100);
        drawQuestion(canvas11, paint11, "Marca/Modelo", column1X, baseY + 140);
        drawText(canvas11, paint11, "F4_S3_M1_Q4", column1X, baseY + 160);
        drawQuestion(canvas11, paint11, "Tipo do veículo", column2X, baseY + 140);
        drawText(canvas11, paint11, "F4_S3_M1_Q5", column2X, baseY + 160);
        drawQuestion(canvas11, paint11, "Cor predominante", column1X, baseY + 200);
        drawText(canvas11, paint11, "F4_S3_M1_Q6", column1X, baseY + 220);
        drawQuestion(canvas11, paint11, "UF", column2X, baseY + 200);
        drawText(canvas11, paint11, "F4_S3_M1_Q7", column2X, baseY + 220);
        drawQuestion(canvas11, paint11, "Ano de fabricação", column1X, baseY + 260);
        drawText(canvas11, paint11, "F4_S3_M1_Q8", column1X, baseY + 280);
        drawQuestion(canvas11, paint11, "Ano do modelo", column2X, baseY + 260);
        drawText(canvas11, paint11, "F4_S3_M1_Q9", column2X, baseY + 280);
        drawQuestion(canvas11, paint11, "Nº do Chassi", column1X, baseY + 320);
        drawText(canvas11, paint11, "F4_S3_M1_Q10", column1X, baseY + 340);
        drawQuestion(canvas11, paint11, "RENAVAM", column2X, baseY + 320);
        drawText(canvas11, paint11, "F4_S3_M1_Q11", column2X, baseY + 340);
        drawQuestion(canvas11, paint11, "Veículo é da empresa?", column1X, baseY + 380);
        drawText(canvas11, paint11, "F4_S3_M1_Q12", column1X, baseY + 400);

        if(isAnyAnswerValid(new String[] {"F4_S3_M2_Q2", "F4_S3_M2_Q3"})) {
            drawTitle(canvas11, paint11, "Dados da empresa", column1X, baseY + 440);
            drawQuestion(canvas11, paint11, "Razão social", column1X, baseY + 480);
            drawText(canvas11, paint11, "F4_S3_M2_Q2", column1X, baseY + 500);
            drawQuestion(canvas11, paint11, "CNPJ", column1X, baseY + 540);
            drawText(canvas11, paint11, "F4_S3_M2_Q3", column1X, baseY + 560);

            paint11.setColor(Color.GRAY);
            canvas11.drawLine(column1X, baseY + 580, 540, baseY + 580, paint11);
            baseY += 620;
        } else {
            baseY += 440;
        }

        if(isAnyAnswerValid(new String[] {"F4_S3_M3_Q2", "F4_S3_M3_Q3", "F4_S3_M3_Q4", "F4_S3_M3_Q5"})) {
            drawTitle(canvas11, paint11, "Dados do proprietário", column1X, baseY);
            drawQuestion(canvas11, paint11, "Nome", column1X, baseY + 40);
            drawText(canvas11, paint11, "F4_S3_M3_Q2", column1X, baseY + 60);
            drawQuestion(canvas11, paint11, "CPF", column1X, baseY + 100);
            drawText(canvas11, paint11, "F4_S3_M3_Q3", column1X, baseY + 120);
            drawQuestion(canvas11, paint11, "Data nascimento", column2X, baseY + 100);
            drawText(canvas11, paint11, "F4_S3_M3_Q4", column2X, baseY + 120);
            drawQuestion(canvas11, paint11, "O proprietário é o condutor?", column1X, baseY + 160);
            drawText(canvas11, paint11, "F4_S3_M3_Q5", column1X, baseY + 180);
        }

        pdfDocument.finishPage(page11);
    }

    private void createPage12(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo12 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 12).create();
        PdfDocument.Page page12 = pdfDocument.startPage(pageInfo12);

        Canvas canvas12 = page12.getCanvas();
        Paint paint12 = new Paint();
        paint12.setColor(Color.parseColor(pdfColorBlue));
        paint12.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F4_S3_M4_Q2", "F4_S3_M4_Q3", "F4_S3_M4_Q4", "F4_S3_M4_Q5", "F4_S3_M4_Q6", "F4_S3_M4_Q7", "F4_S3_M4_Q8", "F4_S3_M4_Q9"})) {
            drawTitle(canvas12, paint12, "Endereço", column1X, baseY);
            drawQuestion(canvas12, paint12, "CEP", column1X, baseY + 40);
            drawText(canvas12, paint12, "F4_S3_M4_Q2", column1X, baseY + 60);
            drawQuestion(canvas12, paint12, "Lograd.", column1X, baseY + 100);
            drawText(canvas12, paint12, "F4_S3_M4_Q3", column1X, baseY + 120);
            drawQuestion(canvas12, paint12, "Número", column1X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M4_Q4", column1X, baseY + 180);
            drawQuestion(canvas12, paint12, "Complemento", column2X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M4_Q5", column2X, baseY + 180);
            drawQuestion(canvas12, paint12, "Bairro", column1X, baseY + 220);
            drawText(canvas12, paint12, "F4_S3_M4_Q6", column1X, baseY + 240);
            drawQuestion(canvas12, paint12, "Cidade", column2X, baseY + 220);
            drawText(canvas12, paint12, "F4_S3_M4_Q7", column2X, baseY + 240);
            drawQuestion(canvas12, paint12, "UF", column1X, baseY + 280);
            drawText(canvas12, paint12, "F4_S3_M4_Q8", column1X, baseY + 300);
            drawQuestion(canvas12, paint12, "O proprietário é o condutor?", column2X, baseY + 280);
            drawText(canvas12, paint12, "F4_S3_M4_Q9", column2X, baseY + 300);

            paint12.setColor(Color.GRAY);
            canvas12.drawLine(column1X, baseY + 320, 540, baseY + 320, paint12);
            baseY += 360;
        }

        if(isAnyAnswerValid(new String[] {"F4_S3_M5_Q2", "F4_S3_M5_Q3", "F4_S3_M5_Q4", "F4_S3_M5_Q5", "F4_S3_M5_Q6", "F4_S3_M5_Q7", "F4_S3_M5_Q8", "F4_S3_M5_Q9", "F4_S3_M5_Q10"})) {
            drawTitle(canvas12, paint12, "Endereço do proprietário", column1X, baseY);
            drawQuestion(canvas12, paint12, "CEP", column1X, baseY + 40);
            drawText(canvas12, paint12, "F4_S3_M5_Q2", column1X, baseY + 60);
            drawQuestion(canvas12, paint12, "Lograd.", column1X, baseY + 100);
            drawText(canvas12, paint12, "F4_S3_M5_Q3", column1X, baseY + 120);
            drawQuestion(canvas12, paint12, "Número", column1X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M5_Q4", column1X, baseY + 180);
            drawQuestion(canvas12, paint12, "Complemento", column2X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M5_Q5", column2X, baseY + 180);
            drawQuestion(canvas12, paint12, "Bairro", column1X, baseY + 220);
            drawText(canvas12, paint12, "F4_S3_M5_Q6", column1X, baseY + 240);
            drawQuestion(canvas12, paint12, "Cidade", column2X, baseY + 220);
            drawText(canvas12, paint12, "F4_S3_M5_Q7", column2X, baseY + 240);
            drawQuestion(canvas12, paint12, "UF", column1X, baseY + 280);
            drawText(canvas12, paint12, "F4_S3_M5_Q8", column1X, baseY + 300);
            drawQuestion(canvas12, paint12, "Nº CNH", column2X, baseY + 280);
            drawText(canvas12, paint12, "F4_S3_M5_Q9", column2X, baseY + 300);
            drawQuestion(canvas12, paint12, "Validade", column1X, baseY + 340);
            drawText(canvas12, paint12, "F4_S3_M5_Q10", column1X, baseY + 360);

            paint12.setColor(Color.GRAY);
            canvas12.drawLine(column1X, baseY + 380, 540, baseY + 380, paint12);
            baseY += 420;
        }

        if(isAnyAnswerValid(new String[] {"F4_S3_M6_Q2", "F4_S3_M6_Q3", "F4_S3_M6_Q4", "F4_S3_M6_Q5", "F4_S3_M6_Q6"})) {
            drawTitle(canvas12, paint12, "Dados do condutor", column1X, baseY);
            drawQuestion(canvas12, paint12, "Nome", column1X, baseY + 40);
            drawText(canvas12, paint12, "F4_S3_M6_Q2", column1X, baseY + 60);
            drawQuestion(canvas12, paint12, "CPF", column1X, baseY + 100);
            drawText(canvas12, paint12, "F4_S3_M6_Q3", column1X, baseY + 120);
            drawQuestion(canvas12, paint12, "Data de nascimento", column2X, baseY + 100);
            drawText(canvas12, paint12, "F4_S3_M6_Q4", column2X, baseY + 120);
            drawQuestion(canvas12, paint12, "Nº CNH", column1X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M6_Q5", column1X, baseY + 180);
            drawQuestion(canvas12, paint12, "Validade", column2X, baseY + 160);
            drawText(canvas12, paint12, "F4_S3_M6_Q6", column2X, baseY + 180);
        }

        pdfDocument.finishPage(page12);
    }

    private void createPage13(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo13 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 13).create();
        PdfDocument.Page page13 = pdfDocument.startPage(pageInfo13);

        Canvas canvas13 = page13.getCanvas();
        Paint paint13 = new Paint();
        paint13.setColor(Color.parseColor(pdfColorBlue));
        paint13.setTextSize(42);

        int baseY = 40;

        if(!isAnyAnswerValid(new String[] {"F4_S3_M7_Q2", "F4_S3_M7_Q3", "F4_S3_M7_Q4", "F4_S3_M7_Q5", "F4_S3_M7_Q6", "F4_S3_M7_Q7", "F4_S3_M7_Q8"})) {
            drawTitle(canvas13, paint13, "Endereço do condutor", column1X, baseY);
            drawQuestion(canvas13, paint13, "CEP", column1X, baseY + 40);
            drawText(canvas13, paint13, "F4_S3_M7_Q2", column1X, baseY + 60);
            drawQuestion(canvas13, paint13, "Lograd.", column1X, baseY + 100);
            drawText(canvas13, paint13, "F4_S3_M7_Q3", column1X, baseY + 120);
            drawQuestion(canvas13, paint13, "Número", column1X, baseY + 160);
            drawText(canvas13, paint13, "F4_S3_M7_Q4", column1X, baseY + 180);
            drawQuestion(canvas13, paint13, "Complemento", column2X, baseY + 160);
            drawText(canvas13, paint13, "F4_S3_M7_Q5", column2X, baseY + 180);
            drawQuestion(canvas13, paint13, "Bairro", column1X, baseY + 220);
            drawText(canvas13, paint13, "F4_S3_M7_Q6", column1X, baseY + 240);
            drawQuestion(canvas13, paint13, "Cidade", column2X, baseY + 220);
            drawText(canvas13, paint13, "F4_S3_M7_Q7", column2X, baseY + 240);
            drawQuestion(canvas13, paint13, "UF", column1X, baseY + 280);
            drawText(canvas13, paint13, "F4_S3_M7_Q8", column1X, baseY + 300);

            paint13.setColor(Color.GRAY);
            canvas13.drawLine(column1X, baseY + 320, 540, baseY + 320, paint13);
            baseY += 360;
        }

        drawTitle(canvas13, paint13, "Danos", column1X, baseY);
        drawImage(canvas13, paint13, R.drawable.carro, 40, baseY + 40, carWidth, carHeight, 180);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q1", 40, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q2", 40 + checkboxWidth, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q3", 40 + (checkboxWidth * 2), baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q4", 40, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q5", 40 + checkboxWidth, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas13, paint13, "F4_S3_M8_Q6", 40 + (checkboxWidth * 2), baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page13);
    }

    private void createPage14(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo14 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 14).create();
        PdfDocument.Page page14 = pdfDocument.startPage(pageInfo14);

        Canvas canvas14 = page14.getCanvas();
        Paint paint14 = new Paint();
        paint14.setColor(Color.parseColor(pdfColorBlue));
        paint14.setTextSize(42);

        int baseY = 20;

        drawRectWithText(canvas14, paint14, "Veículo 5", 40, baseY, 500, 40);
        drawQuestion(canvas14, paint14, "Placa", column1X, baseY + 80);
        drawText(canvas14, paint14, "F4_S4_M1_Q3", column1X, baseY + 100);
        drawQuestion(canvas14, paint14, "Marca/Modelo", column1X, baseY + 140);
        drawText(canvas14, paint14, "F4_S4_M1_Q4", column1X, baseY + 160);
        drawQuestion(canvas14, paint14, "Tipo do veículo", column2X, baseY + 140);
        drawText(canvas14, paint14, "F4_S4_M1_Q5", column2X, baseY + 160);
        drawQuestion(canvas14, paint14, "Cor predominante", column1X, baseY + 200);
        drawText(canvas14, paint14, "F4_S4_M1_Q6", column1X, baseY + 220);
        drawQuestion(canvas14, paint14, "UF", column2X, baseY + 200);
        drawText(canvas14, paint14, "F4_S4_M1_Q7", column2X, baseY + 220);
        drawQuestion(canvas14, paint14, "Ano de fabricação", column1X, baseY + 260);
        drawText(canvas14, paint14, "F4_S4_M1_Q8", column1X, baseY + 280);
        drawQuestion(canvas14, paint14, "Ano do modelo", column2X, baseY + 260);
        drawText(canvas14, paint14, "F4_S4_M1_Q9", column2X, baseY + 280);
        drawQuestion(canvas14, paint14, "Nº do Chassi", column1X, baseY + 320);
        drawText(canvas14, paint14, "F4_S4_M1_Q10", column1X, baseY + 340);
        drawQuestion(canvas14, paint14, "RENAVAM", column2X, baseY + 320);
        drawText(canvas14, paint14, "F4_S4_M1_Q11", column2X, baseY + 340);
        drawQuestion(canvas14, paint14, "Veículo é da empresa?", column1X, baseY + 380);
        drawText(canvas14, paint14, "F4_S4_M1_Q12", column1X, baseY + 400);

        if(isAnyAnswerValid(new String[] {"F4_S4_M2_Q2", "F4_S4_M2_Q3"})) {
            drawTitle(canvas14, paint14, "Dados da empresa", column1X, baseY + 440);
            drawQuestion(canvas14, paint14, "Razão social", column1X, baseY + 480);
            drawText(canvas14, paint14, "F4_S4_M2_Q2", column1X, baseY + 500);
            drawQuestion(canvas14, paint14, "CNPJ", column1X, baseY + 540);
            drawText(canvas14, paint14, "F4_S4_M2_Q3", column1X, baseY + 560);

            paint14.setColor(Color.GRAY);
            canvas14.drawLine(column1X, baseY + 580, 540, baseY + 580, paint14);
            baseY += 620;
        } else {
            baseY += 440;
        }

        if(isAnyAnswerValid(new String[] {"F4_S4_M3_Q2", "F4_S4_M3_Q3", "F4_S4_M3_Q4", "F4_S4_M3_Q5"})) {
            drawTitle(canvas14, paint14, "Dados do proprietário", column1X, baseY);
            drawQuestion(canvas14, paint14, "Nome", column1X, baseY + 40);
            drawText(canvas14, paint14, "F4_S4_M3_Q2", column1X, baseY + 60);
            drawQuestion(canvas14, paint14, "CPF", column1X, baseY + 100);
            drawText(canvas14, paint14, "F4_S4_M3_Q3", column1X, baseY + 120);
            drawQuestion(canvas14, paint14, "Data nascimento", column2X, baseY + 100);
            drawText(canvas14, paint14, "F4_S4_M3_Q4", column2X, baseY + 120);
            drawQuestion(canvas14, paint14, "O proprietário é o condutor?", column1X, baseY + 160);
            drawText(canvas14, paint14, "F4_S4_M3_Q5", column1X, baseY + 180);
        }

        pdfDocument.finishPage(page14);
    }

    private void createPage15(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo15 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 15).create();
        PdfDocument.Page page15 = pdfDocument.startPage(pageInfo15);

        Canvas canvas15 = page15.getCanvas();
        Paint paint15 = new Paint();
        paint15.setColor(Color.parseColor(pdfColorBlue));
        paint15.setTextSize(42);

        int baseY = 40;

        if(isAnyAnswerValid(new String[] {"F4_S4_M4_Q2", "F4_S4_M4_Q3", "F4_S4_M4_Q4", "F4_S4_M4_Q5", "F4_S4_M4_Q6", "F4_S4_M4_Q7", "F4_S4_M4_Q8", "F4_S4_M4_Q9"})) {
            drawTitle(canvas15, paint15, "Endereço", column1X, baseY);
            drawQuestion(canvas15, paint15, "CEP", column1X, baseY + 40);
            drawText(canvas15, paint15, "F4_S4_M4_Q2", column1X, baseY + 60);
            drawQuestion(canvas15, paint15, "Lograd.", column1X, baseY + 100);
            drawText(canvas15, paint15, "F4_S4_M4_Q3", column1X, baseY + 120);
            drawQuestion(canvas15, paint15, "Número", column1X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M4_Q4", column1X, baseY + 180);
            drawQuestion(canvas15, paint15, "Complemento", column2X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M4_Q5", column2X, baseY + 180);
            drawQuestion(canvas15, paint15, "Bairro", column1X, baseY + 220);
            drawText(canvas15, paint15, "F4_S4_M4_Q6", column1X, baseY + 240);
            drawQuestion(canvas15, paint15, "Cidade", column2X, baseY + 220);
            drawText(canvas15, paint15, "F4_S4_M4_Q7", column2X, baseY + 240);
            drawQuestion(canvas15, paint15, "UF", column1X, baseY + 280);
            drawText(canvas15, paint15, "F4_S4_M4_Q8", column1X, baseY + 300);
            drawQuestion(canvas15, paint15, "O proprietário é o condutor?", column2X, baseY + 280);
            drawText(canvas15, paint15, "F4_S4_M4_Q9", column2X, baseY + 300);

            paint15.setColor(Color.GRAY);
            canvas15.drawLine(column1X, baseY + 320, 540, baseY + 320, paint15);
            baseY += 360;
        }

        if(isAnyAnswerValid(new String[] {"F4_S4_M5_Q2", "F4_S4_M5_Q3", "F4_S4_M5_Q4", "F4_S4_M5_Q5", "F4_S4_M5_Q6", "F4_S4_M5_Q7", "F4_S4_M5_Q8", "F4_S4_M5_Q9", "F4_S4_M5_Q10"})) {
            drawTitle(canvas15, paint15, "Endereço do proprietário", column1X, baseY);
            drawQuestion(canvas15, paint15, "CEP", column1X, baseY + 40);
            drawText(canvas15, paint15, "F4_S4_M5_Q2", column1X, baseY + 60);
            drawQuestion(canvas15, paint15, "Lograd.", column1X, baseY + 100);
            drawText(canvas15, paint15, "F4_S4_M5_Q3", column1X, baseY + 120);
            drawQuestion(canvas15, paint15, "Número", column1X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M5_Q4", column1X, baseY + 180);
            drawQuestion(canvas15, paint15, "Complemento", column2X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M5_Q5", column2X, baseY + 180);
            drawQuestion(canvas15, paint15, "Bairro", column1X, baseY + 220);
            drawText(canvas15, paint15, "F4_S4_M5_Q6", column1X, baseY + 240);
            drawQuestion(canvas15, paint15, "Cidade", column2X, baseY + 220);
            drawText(canvas15, paint15, "F4_S4_M5_Q7", column2X, baseY + 240);
            drawQuestion(canvas15, paint15, "UF", column1X, baseY + 280);
            drawText(canvas15, paint15, "F4_S4_M5_Q8", column1X, baseY + 300);
            drawQuestion(canvas15, paint15, "Nº CNH", column2X, baseY + 280);
            drawText(canvas15, paint15, "F4_S4_M5_Q9", column2X, baseY + 300);
            drawQuestion(canvas15, paint15, "Validade", column1X, baseY + 340);
            drawText(canvas15, paint15, "F4_S4_M5_Q10", column1X, baseY + 360);

            paint15.setColor(Color.GRAY);
            canvas15.drawLine(column1X, baseY + 380, 540, baseY + 380, paint15);
            baseY += 420;
        }

        if(isAnyAnswerValid(new String[] {"F4_S4_M6_Q2", "F4_S4_M6_Q3", "F4_S4_M6_Q4", "F4_S4_M6_Q5", "F4_S4_M6_Q6"})) {
            drawTitle(canvas15, paint15, "Dados do condutor", column1X, baseY);
            drawQuestion(canvas15, paint15, "Nome", column1X, baseY + 40);
            drawText(canvas15, paint15, "F4_S4_M6_Q2", column1X, baseY + 60);
            drawQuestion(canvas15, paint15, "CPF", column1X, baseY + 100);
            drawText(canvas15, paint15, "F4_S4_M6_Q3", column1X, baseY + 120);
            drawQuestion(canvas15, paint15, "Data de nascimento", column2X, baseY + 100);
            drawText(canvas15, paint15, "F4_S4_M6_Q4", column2X, baseY + 120);
            drawQuestion(canvas15, paint15, "Nº CNH", column1X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M6_Q5", column1X, baseY + 180);
            drawQuestion(canvas15, paint15, "Validade", column2X, baseY + 160);
            drawText(canvas15, paint15, "F4_S4_M6_Q6", column2X, baseY + 180);
        }

        pdfDocument.finishPage(page15);
    }

    private void createPage16(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo16 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 16).create();
        PdfDocument.Page page16 = pdfDocument.startPage(pageInfo16);

        Canvas canvas16 = page16.getCanvas();
        Paint paint16 = new Paint();
        paint16.setColor(Color.parseColor(pdfColorBlue));
        paint16.setTextSize(42);

        int baseY = 40;

        if(!isAnyAnswerValid(new String[] {"F4_S4_M7_Q2", "F4_S4_M7_Q3", "F4_S4_M7_Q4", "F4_S4_M7_Q5", "F4_S4_M7_Q6", "F4_S4_M7_Q7", "F4_S4_M7_Q8"})) {
            drawTitle(canvas16, paint16, "Endereço do condutor", column1X, baseY);
            drawQuestion(canvas16, paint16, "CEP", column1X, baseY + 40);
            drawText(canvas16, paint16, "F4_S4_M7_Q2", column1X, baseY + 60);
            drawQuestion(canvas16, paint16, "Lograd.", column1X, baseY + 100);
            drawText(canvas16, paint16, "F4_S4_M7_Q3", column1X, baseY + 120);
            drawQuestion(canvas16, paint16, "Número", column1X, baseY + 160);
            drawText(canvas16, paint16, "F4_S4_M7_Q4", column1X, baseY + 180);
            drawQuestion(canvas16, paint16, "Complemento", column2X, baseY + 160);
            drawText(canvas16, paint16, "F4_S4_M7_Q5", column2X, baseY + 180);
            drawQuestion(canvas16, paint16, "Bairro", column1X, baseY + 220);
            drawText(canvas16, paint16, "F4_S4_M7_Q6", column1X, baseY + 240);
            drawQuestion(canvas16, paint16, "Cidade", column2X, baseY + 220);
            drawText(canvas16, paint16, "F4_S4_M7_Q7", column2X, baseY + 240);
            drawQuestion(canvas16, paint16, "UF", column1X, baseY + 280);
            drawText(canvas16, paint16, "F4_S4_M7_Q8", column1X, baseY + 300);

            paint16.setColor(Color.GRAY);
            canvas16.drawLine(column1X, baseY + 320, 540, baseY + 320, paint16);
            baseY += 360;
        }

        drawTitle(canvas16, paint16, "Danos", column1X, baseY);
        drawImage(canvas16, paint16, R.drawable.carro, 40, baseY + 40, carWidth, carHeight, 180);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q1", 40, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q2", 40 + checkboxWidth, baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q3", 40 + (checkboxWidth * 2), baseY + 40, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q4", 40, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q5", 40 + checkboxWidth, baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas16, paint16, "F4_S4_M8_Q6", 40 + (checkboxWidth * 2), baseY + 40 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page16);
    }

    private void createPage17(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo17 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 17).create();
        PdfDocument.Page page17 = pdfDocument.startPage(pageInfo17);

        Canvas canvas17 = page17.getCanvas();
        Paint paint17 = new Paint();
        paint17.setColor(Color.parseColor(pdfColorBlue));
        paint17.setTextSize(42);

        int baseY = 40;

        drawRectWithText(canvas17, paint17, "Testemunhas", 40, baseY, 500, 40);
        if(isAnyAnswerValid(new String[] {"F5_S1_Q2", "F5_S1_Q3", "F5_S1_Q4"})) {
            createPage17Testemunha(canvas17, paint17, 1, baseY + 80);
            baseY += 180;
        }

        if(isAnyAnswerValid(new String[] {"F5_S2_Q2", "F5_S2_Q3", "F5_S2_Q4"})) {
            createPage17Testemunha(canvas17, paint17, 2, baseY + 80);
            baseY += 180;
        }

        if(isAnyAnswerValid(new String[] {"F5_S3_Q2", "F5_S3_Q3", "F5_S3_Q4"})) {
            createPage17Testemunha(canvas17, paint17, 3, baseY + 80);
            baseY += 180;
        }

        if(isAnyAnswerValid(new String[] {"F5_S4_Q2", "F5_S4_Q3", "F5_S4_Q4"})) {
            createPage17Testemunha(canvas17, paint17, 4, baseY + 80);
            baseY += 180;
        }

        pdfDocument.finishPage(page17);
    }

    private void createPage17Testemunha(Canvas canvas, Paint paint, int index, int baseY) {
        drawTitle(canvas, paint, "Testemunha "+index, column1X, baseY);
        drawQuestion(canvas, paint, "Nome", column1X, baseY + 40);
        drawText(canvas, paint, "F5_S"+index+"_Q2", column1X, baseY + 60);
        drawQuestion(canvas, paint, "CPF", column1X, baseY + 100);
        drawText(canvas, paint, "F5_S"+index+"_Q3", column1X, baseY + 120);
        drawQuestion(canvas, paint, "Celular", column2X, baseY + 100);
        drawText(canvas, paint, "F5_S"+index+"_Q4", column2X, baseY + 120);

        paint.setColor(Color.GRAY);
        canvas.drawLine(column1X, baseY + 140, 540, baseY + 140, paint);
    }

    private void createPage18(PdfDocument pdfDocument) {
        PdfDocument.PageInfo pageInfo18 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 18).create();
        PdfDocument.Page page18 = pdfDocument.startPage(pageInfo18);

        Canvas canvas18 = page18.getCanvas();
        Paint paint18 = new Paint();
        paint18.setColor(Color.parseColor(pdfColorBlue));
        paint18.setTextSize(42);
        //
        TextPaint paint18_2 = new TextPaint();
        paint18_2.setColor(Color.parseColor(pdfColorBlue));
        paint18_2.setTextSize(42);

        int baseY = 40;

        drawRectWithText(canvas18, paint18, "Detalhes", 40, baseY, 500, 40);
        drawMultiText(canvas18, paint18_2, "F6_S1_Q2", column1X, baseY + 80, 500);

        pdfDocument.finishPage(page18);
    }

    /* Função do PDF: Desenhando o cabeçalho */
    private void drawHeader(Canvas canvas, Paint paint) {
        canvas.drawText("E-brat gerado", 40, 70, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trlogo);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, false);
        canvas.drawBitmap(scaledBitmap, 480, 20, paint);

        paint.setStrokeWidth(2f);
        canvas.drawLine(40, 100, 540, 100, paint);
    }

    /* Função do PDF: Desenhando um retângulo com texto */
    private void drawRectWithText(Canvas canvas, Paint paint, String text, int x, int y, int width, int height) {
        paint.setColor(Color.parseColor(pdfColorBlue));
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
        paint.setTextSize(20);

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

    /* Função do PDF: Desenhando um texto com multiplas linhas */
    private void drawMultiText(Canvas canvas, TextPaint paint, String answerId, int x, int y, int width) {
        paint.setColor(Color.GRAY);
        paint.setTextSize(24);

        try {
            TextView textView = (TextView) findViewByName(answerId);
            if(textView != null) {
                CharSequence text = textView.getText();
                if(text.length() > 0) {
                    StaticLayout staticLayout = new StaticLayout(
                            text,
                            paint,
                            width,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false
                    );
                    canvas.save();
                    canvas.translate(x, y);
                    staticLayout.draw(canvas);
                    canvas.restore();
                } else {
                    canvas.drawText("Não informado", x, y, paint);
                }
            }
        } catch(Exception e) {
            Log.e(logId, "" + e);
        }
    }

    /* Função do PDF: Desenhando uma imagem */
    private void drawImage(Canvas canvas, Paint paint, int image, int x, int y, int width, int height, int rotation) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        canvas.drawBitmap(rotatedBitmap, x, y, paint);
    }

    /* Função do PDF: Desenhando um checkbox */
    private void drawCheckbox(Canvas canvas, Paint paint, String answerId, int x, int y, int width, int height) {
        try {
            CheckBox checkBox = (CheckBox) findViewByName(answerId);
            if(checkBox != null) {
                Paint borderPaint = new Paint();
                borderPaint.setColor(Color.LTGRAY);
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setStrokeWidth(3);
                if(checkBox.isChecked()) {
                    paint.setColor(Color.parseColor("#332090FF"));
                } else {
                    paint.setColor(Color.parseColor("#00FFFFFF"));
                }
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(x, y, (x + width), (y + height), paint);
                canvas.drawRect(x, y, (x + width), (y + height), borderPaint);
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

    /* Função para verificar se ao menos alguma resposta de um array existe (não é null) */
    private boolean isAnyAnswerValid(String[] array) {
        for(String item : array) {
            TextView textView = (TextView) findViewByName(item);
            if(textView != null) {
                CharSequence text = textView.getText();
                if (text.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Função para verificar se uma resposta é igual a algum valor */
    private boolean isAnswerEqualTo(String answer, String value) {
        TextView textView = (TextView) findViewByName(answer);
        if(textView != null) {
            CharSequence text = textView.getText();
            if (text.length() > 0) {
                if(text == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Função para verificar se uma resposta é maior que algum número */
    private boolean isAnswerMoreThan(String answer, String value) {
        TextView textView = (TextView) findViewByName(answer);
        if(textView != null) {
            CharSequence text = textView.getText();
            if (text.length() > 0) {
                if (Integer.valueOf((String) text) >= Integer.valueOf(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* Função para converter imagem em arquivo */
    public File base64ToFile(String base64String, String fileName) throws IOException {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        File file = new File(Form7Activity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(decodedBytes);
        fos.close();
        return file;
    }

    /* Função para enviar o PDF por email */
    private void submitPDF(File pdfFile) {
        try {
            if (!pdfFile.exists()) {
                Log.e(logId, "O arquivo PDF não existe: " + pdfFile.getAbsolutePath());
                return;
            }

            // Obtendo data do acidente
            String dataDoAcidente = getIntent().getStringExtra("F2_S1_Q2");
            Log.d(logId, "1");
            if(dataDoAcidente == null) {
                Log.d(logId, "2");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Log.d(logId, "3");
                Date date = new Date();
                Log.d(logId, "4");
                String data = formatter.format(date);
                Log.d(logId, "5");
                dataDoAcidente = data;
                Log.v(logId, dataDoAcidente);
            }

            // Obtendo número de ordem
            String numeroDeOrdem = getIntent().getStringExtra("F3_S1_Q2");
            if(numeroDeOrdem == null) {
                numeroDeOrdem = "0";
            }

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("application/zip");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"josival@transriver.com.br"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "E-brat " + numeroDeOrdem + " " + dataDoAcidente);
            //emailIntent.putExtra(Intent.EXTRA_TEXT, "Segue em anexo o PDF e as imagens.");

            Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", pdfFile);

            imagePaths = getIntent().getStringArrayListExtra("F6_S2_Q1");
            ArrayList<Uri> uris = new ArrayList<>();

            List<Uri> imageUris = convertStringsToUris(imagePaths);

            uris.add(pdfUri);
            uris.addAll(imageUris);
            Log.d(logId, uris.toString());

            /* ZIP */
            // Nome do Zip
            String zipFileName = "e-brat_" + numeroDeOrdem + ".zip";

            File zipFile = createZipFile(Form7Activity.this, uris, zipFileName);
            Uri zipUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", zipFile);
            emailIntent.putExtra(Intent.EXTRA_STREAM, zipUri);
            //emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(emailIntent, "Escolha um aplicativo de e-mail"));
        } catch (Exception e) {
            Log.e(logId, "Erro ao enviar o PDF: " + e);
        }
    }

    public List<Uri> convertStringsToUris(List<String> contentUriStrings) {
        List<Uri> uris = new ArrayList<>();
        for (String uriString : contentUriStrings) {
            if(uriString != null) {
                uris.add(Uri.parse(uriString));
            }
        }
        return uris;
    }

    public InputStream getInputStreamFromUri(Context context, Uri contentUri) throws IOException {
        return context.getContentResolver().openInputStream(contentUri);
    }

    /* Converter PDF e imagens em ZIP */
    public File createZipFile(Context context, List<Uri> fileUris, String zipFileName) throws IOException {
        // Cria o arquivo ZIP no diretório de arquivos externos do aplicativo
        File zipFile = new File(context.getExternalFilesDir(null), zipFileName);
        Set<String> fileNames = new HashSet<>();

        // Configura o ZipOutputStream
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)))) {
            byte[] buffer = new byte[1024];

            // Itera sobre as URIs dos arquivos que serão incluídos no ZIP
            for (Uri uri : fileUris) {
                String fileName = uri.getLastPathSegment();

                int count = 1;
                String originalFileName = fileName;
                while (fileNames.contains(fileName)) {
                    fileName = originalFileName + "(" + count + ")";
                    count++;
                }
                fileNames.add(fileName);

                try (BufferedInputStream bis = new BufferedInputStream(context.getContentResolver().openInputStream(uri))) {
                    // Adiciona cada arquivo como uma nova entrada no ZIP
                    ZipEntry entry = new ZipEntry(uri.getLastPathSegment());
                    zos.putNextEntry(entry);

                    // Lê o arquivo e escreve no ZIP
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }

                    // Fecha a entrada do ZIP
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Propaga a exceção para que o chamador possa lidar com ela
        }

        // Retorna o arquivo ZIP criado
        return zipFile;
    }
}