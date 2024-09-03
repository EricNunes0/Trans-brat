package com.example.trans_brat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.provider.MediaStore;
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

        String pdfColorBlue = "#0585FF";
        int pageWidth = 595; // Largura das páginas
        int pageHeight = 842; // Altura das páginas
        int column1X = 40;
        int column2X = 290;
        int carWidth = 500; // Largura da imagem do carro
        int carHeight = 320; // Altura da imagem do carro
        int checkboxWidth = carWidth / 3; // Largura do checkbox do carro
        int checkboxHeight = carHeight / 2; // Largura do checkbox do carro


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

        drawQuestion(canvas1, paint1, "Nome", column1X, 210);
        drawText(canvas1, paint1, "F1_S0_Q1", column1X, 230);
        drawQuestion(canvas1, paint1, "Matrícula", column1X, 270);
        drawText(canvas1, paint1, "F1_S0_Q2", column1X, 290);

        drawRectWithText(canvas1, paint1, "Dados da ocorrência", 40, 300, 500, 40);

        drawQuestion(canvas1, paint1, "Data", column1X, 360);
        drawText(canvas1, paint1, "F2_S1_Q2", column1X, 380);
        drawQuestion(canvas1, paint1, "Hora", column2X, 360);
        drawText(canvas1, paint1, "F2_S1_Q3", column2X, 380);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, 400, 540, 400, paint1);

        drawQuestion(canvas1, paint1, "Onde?", column1X, 440);
        drawText(canvas1, paint1, "F2_S2_Q2", column1X, 460);
        drawQuestion(canvas1, paint1, "Especifique", column1X, 500);
        drawText(canvas1, paint1, "F2_S2_Q3", column1X, 520);

        paint1.setColor(Color.GRAY);
        canvas1.drawLine(column1X, 540, 540, 540, paint1);

        drawQuestion(canvas1, paint1, "CEP", column1X, 580);
        drawText(canvas1, paint1, "F2_S3_Q2", column1X, 600);
        drawQuestion(canvas1, paint1, "Número", column2X, 580);
        drawText(canvas1, paint1, "F2_S3_Q4", column2X, 600);
        drawQuestion(canvas1, paint1, "Endereço", column1X, 640);
        drawText(canvas1, paint1, "F2_S3_Q3", column1X, 660);
        drawQuestion(canvas1, paint1, "Complemento", column1X, 700);
        drawText(canvas1, paint1, "F2_S3_Q5", column1X, 720);
        drawQuestion(canvas1, paint1, "Bairro", column2X, 700);
        drawText(canvas1, paint1, "F2_S3_Q6", column2X, 720);
        drawQuestion(canvas1, paint1, "Cidade", column1X, 760);
        drawText(canvas1, paint1, "F2_S3_Q7", column1X, 780);
        drawQuestion(canvas1, paint1, "Estado", column2X, 760);
        drawText(canvas1, paint1, "F2_S3_Q8", column2X, 780);
        drawQuestion(canvas1, paint1, "Ponto de referência", column1X, 820);
        drawText(canvas1, paint1, "F2_S3_Q9", column1X, 840);

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
        drawImage(canvas6, paint6, R.drawable.carro, 40, 190, carWidth, carHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q1", 40, 190, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q2", 40 + checkboxWidth, 190, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q3", 40 + (checkboxWidth * 2), 190, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q4", 40, 190 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q5", 40 + checkboxWidth, 190 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas6, paint6, "F3_S8_Q6", 40 + (checkboxWidth * 2), 190 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page6);

        /* Página 7 */
        PdfDocument.PageInfo pageInfo7 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 7).create();
        PdfDocument.Page page7 = pdfDocument.startPage(pageInfo7);

        Canvas canvas7 = page7.getCanvas();
        Paint paint7 = new Paint();
        paint7.setColor(Color.parseColor(pdfColorBlue));
        paint7.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas7, paint7);

        drawRectWithText(canvas7, paint7, "Veículo 2", 40, 150, 500, 40);

        drawQuestion(canvas7, paint7, "Nº de ordem", column1X, 210);
        drawText(canvas7, paint7, "F4_S1_M1_Q2", column1X, 230);
        drawQuestion(canvas7, paint7, "Placa", column2X, 210);
        drawText(canvas7, paint7, "F4_S1_M1_Q3", column2X, 230);

        paint7.setColor(Color.GRAY);
        canvas7.drawLine(column1X, 250, 540, 250, paint7);

        drawQuestion(canvas7, paint7, "Marca/Modelo", column1X, 290);
        drawText(canvas7, paint7, "F4_S1_M1_Q4", column1X, 310);
        drawQuestion(canvas7, paint7, "Tipo do veículo", column2X, 290);
        drawText(canvas7, paint7, "F4_S1_M1_Q5", column2X, 310);
        drawQuestion(canvas7, paint7, "Cor predominante", column1X, 350);
        drawText(canvas7, paint7, "F4_S1_M1_Q6", column1X, 370);
        drawQuestion(canvas7, paint7, "UF", column2X, 350);
        drawText(canvas7, paint7, "F4_S1_M1_Q7", column2X, 370);
        drawQuestion(canvas7, paint7, "Ano de fabricação", column1X, 410);
        drawText(canvas7, paint7, "F4_S1_M1_Q8", column1X, 430);
        drawQuestion(canvas7, paint7, "Ano do modelo", column2X, 410);
        drawText(canvas7, paint7, "F4_S1_M1_Q9", column2X, 430);
        drawQuestion(canvas7, paint7, "Nº do Chassi", column1X, 470);
        drawText(canvas7, paint7, "F4_S1_M1_Q10", column1X, 490);
        drawQuestion(canvas7, paint7, "RENAVAM", column2X, 470);
        drawText(canvas7, paint7, "F4_S1_M1_Q11", column2X, 490);
        drawQuestion(canvas7, paint7, "Veículo é da empresa?", column1X, 530);
        drawText(canvas7, paint7, "F4_S1_M1_Q12", column1X, 550);

        drawTitle(canvas7, paint7, "Dados da empresa", column1X, 590);
        drawQuestion(canvas7, paint7, "Razão social", column1X, 630);
        drawText(canvas7, paint7, "F4_S1_M2_Q2", column1X, 650);
        drawQuestion(canvas7, paint7, "CNPJ", column1X, 690);
        drawText(canvas7, paint7, "F4_S1_M2_Q3", column1X, 710);

        pdfDocument.finishPage(page7);

        /* Página 8 */
        PdfDocument.PageInfo pageInfo8 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 8).create();
        PdfDocument.Page page8 = pdfDocument.startPage(pageInfo8);

        Canvas canvas8 = page8.getCanvas();
        Paint paint8 = new Paint();
        paint8.setColor(Color.parseColor(pdfColorBlue));
        paint8.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas8, paint8);

        drawTitle(canvas8, paint8, "Dados do proprietário", column1X, 150);
        drawQuestion(canvas8, paint8, "Nome", column1X, 190);
        drawText(canvas8, paint8, "F4_S1_M3_Q2", column1X, 210);
        drawQuestion(canvas8, paint8, "CPF", column1X, 250);
        drawText(canvas8, paint8, "F4_S1_M3_Q3", column1X, 270);
        drawQuestion(canvas8, paint8, "Data nascimento", column2X, 250);
        drawText(canvas8, paint8, "F4_S1_M3_Q4", column2X, 270);
        drawQuestion(canvas8, paint8, "O proprietário é o condutor?", column1X, 310);
        drawText(canvas8, paint8, "F4_S1_M3_Q5", column1X, 330);

        paint8.setColor(Color.GRAY);
        canvas8.drawLine(column1X, 350, 540, 350, paint8);

        drawTitle(canvas8, paint8, "Endereço", column1X, 390);
        drawQuestion(canvas8, paint8, "CEP", column1X, 430);
        drawText(canvas8, paint8, "F4_S1_M4_Q2", column1X, 450);
        drawQuestion(canvas8, paint8, "Lograd.", column1X, 490);
        drawText(canvas8, paint8, "F4_S1_M4_Q3", column1X, 510);
        drawQuestion(canvas8, paint8, "Número", column1X, 550);
        drawText(canvas8, paint8, "F4_S1_M4_Q4", column1X, 570);
        drawQuestion(canvas8, paint8, "Complemento", column2X, 550);
        drawText(canvas8, paint8, "F4_S1_M4_Q5", column2X, 570);
        drawQuestion(canvas8, paint8, "Bairro", column1X, 610);
        drawText(canvas8, paint8, "F4_S1_M4_Q6", column1X, 630);
        drawQuestion(canvas8, paint8, "Cidade", column2X, 610);
        drawText(canvas8, paint8, "F4_S1_M4_Q7", column2X, 630);
        drawQuestion(canvas8, paint8, "UF", column1X, 670);
        drawText(canvas8, paint8, "F4_S1_M4_Q8", column1X, 690);
        drawQuestion(canvas8, paint8, "O proprietário é o condutor?", column2X, 670);
        drawText(canvas8, paint8, "F4_S1_M4_Q9", column2X, 690);

        pdfDocument.finishPage(page8);

        /* Página 9 */
        PdfDocument.PageInfo pageInfo9 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 9).create();
        PdfDocument.Page page9 = pdfDocument.startPage(pageInfo9);

        Canvas canvas9 = page9.getCanvas();
        Paint paint9 = new Paint();
        paint9.setColor(Color.parseColor(pdfColorBlue));
        paint9.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas9, paint9);

        drawTitle(canvas9, paint9, "Endereço do proprietário", column1X, 150);
        drawQuestion(canvas9, paint9, "CEP", column1X, 190);
        drawText(canvas9, paint9, "F4_S1_M5_Q2", column1X, 210);
        drawQuestion(canvas9, paint9, "Lograd.", column1X, 250);
        drawText(canvas9, paint9, "F4_S1_M5_Q3", column1X, 270);
        drawQuestion(canvas9, paint9, "Número", column1X, 310);
        drawText(canvas9, paint9, "F4_S1_M5_Q4", column1X, 330);
        drawQuestion(canvas9, paint9, "Complemento", column2X, 310);
        drawText(canvas9, paint9, "F4_S1_M5_Q5", column2X, 330);
        drawQuestion(canvas9, paint9, "Bairro", column1X, 370);
        drawText(canvas9, paint9, "F4_S1_M5_Q6", column1X, 390);
        drawQuestion(canvas9, paint9, "Cidade", column2X, 370);
        drawText(canvas9, paint9, "F4_S1_M5_Q7", column2X, 390);
        drawQuestion(canvas9, paint9, "UF", column1X, 430);
        drawText(canvas9, paint9, "F4_S1_M5_Q8", column1X, 450);
        drawQuestion(canvas9, paint9, "Nº CNH", column2X, 430);
        drawText(canvas9, paint9, "F4_S1_M5_Q9", column2X, 450);
        drawQuestion(canvas9, paint9, "Validade", column1X, 490);
        drawText(canvas9, paint9, "F4_S1_M5_Q10", column1X, 510);

        paint9.setColor(Color.GRAY);
        canvas9.drawLine(column1X, 530, 540, 530, paint9);

        drawTitle(canvas9, paint9, "Dados do condutor", column1X, 570);
        drawQuestion(canvas9, paint9, "Nome", column1X, 610);
        drawText(canvas9, paint9, "F4_S1_M6_Q2", column1X, 630);
        drawQuestion(canvas9, paint9, "CPF", column1X, 670);
        drawText(canvas9, paint9, "F4_S1_M6_Q3", column1X, 690);
        drawQuestion(canvas9, paint9, "Data de nascimento", column2X, 670);
        drawText(canvas9, paint9, "F4_S1_M6_Q4", column2X, 690);
        drawQuestion(canvas9, paint9, "Nº CNH", column1X, 730);
        drawText(canvas9, paint9, "F4_S1_M6_Q5", column1X, 750);
        drawQuestion(canvas9, paint9, "Validade", column2X, 730);
        drawText(canvas9, paint9, "F4_S1_M6_Q6", column2X, 750);

        pdfDocument.finishPage(page9);

        /* Página 10 */
        PdfDocument.PageInfo pageInfo10 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 10).create();
        PdfDocument.Page page10 = pdfDocument.startPage(pageInfo10);

        Canvas canvas10 = page10.getCanvas();
        Paint paint10 = new Paint();
        paint10.setColor(Color.parseColor(pdfColorBlue));
        paint10.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas10, paint10);

        drawTitle(canvas10, paint10, "Endereço do condutor", column1X, 150);
        drawQuestion(canvas10, paint10, "CEP", column1X, 190);
        drawText(canvas10, paint10, "F4_S1_M7_Q2", column1X, 210);
        drawQuestion(canvas10, paint10, "Lograd.", column1X, 250);
        drawText(canvas10, paint10, "F4_S1_M7_Q3", column1X, 270);
        drawQuestion(canvas10, paint10, "Número", column1X, 310);
        drawText(canvas10, paint10, "F4_S1_M7_Q4", column1X, 330);
        drawQuestion(canvas10, paint10, "Complemento", column2X, 310);
        drawText(canvas10, paint10, "F4_S1_M7_Q5", column2X, 330);
        drawQuestion(canvas10, paint10, "Bairro", column1X, 370);
        drawText(canvas10, paint10, "F4_S1_M7_Q6", column1X, 390);
        drawQuestion(canvas10, paint10, "Cidade", column2X, 370);
        drawText(canvas10, paint10, "F4_S1_M7_Q7", column2X, 390);
        drawQuestion(canvas10, paint10, "UF", column1X, 430);
        drawText(canvas10, paint10, "F4_S1_M7_Q8", column1X, 450);

        paint10.setColor(Color.GRAY);
        canvas10.drawLine(column1X, 470, 540, 470, paint10);

        drawTitle(canvas10, paint10, "Danos", column1X, 510);
        drawImage(canvas10, paint10, R.drawable.carro, 40, 530, carWidth, carHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q1", 40, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q2", 40 + checkboxWidth, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q3", 40 + (checkboxWidth * 2), 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q4", 40, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q5", 40 + checkboxWidth, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas10, paint10, "F4_S1_M8_Q6", 40 + (checkboxWidth * 2), 530 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page10);

        /* Página 11 */
        PdfDocument.PageInfo pageInfo11 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 11).create();
        PdfDocument.Page page11 = pdfDocument.startPage(pageInfo11);

        Canvas canvas11 = page11.getCanvas();
        Paint paint11 = new Paint();
        paint11.setColor(Color.parseColor(pdfColorBlue));
        paint11.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas11, paint11);

        drawRectWithText(canvas11, paint11, "Veículo 3", 40, 150, 500, 40);

        drawQuestion(canvas11, paint11, "Nº de ordem", column1X, 210);
        drawText(canvas11, paint11, "F4_S2_M1_Q2", column1X, 230);
        drawQuestion(canvas11, paint11, "Placa", column2X, 210);
        drawText(canvas11, paint11, "F4_S2_M1_Q3", column2X, 230);

        paint11.setColor(Color.GRAY);
        canvas11.drawLine(column1X, 250, 540, 250, paint11);

        drawQuestion(canvas11, paint11, "Marca/Modelo", column1X, 290);
        drawText(canvas11, paint11, "F4_S2_M1_Q4", column1X, 310);
        drawQuestion(canvas11, paint11, "Tipo do veículo", column2X, 290);
        drawText(canvas11, paint11, "F4_S2_M1_Q5", column2X, 310);
        drawQuestion(canvas11, paint11, "Cor predominante", column1X, 350);
        drawText(canvas11, paint11, "F4_S2_M1_Q6", column1X, 370);
        drawQuestion(canvas11, paint11, "UF", column2X, 350);
        drawText(canvas11, paint11, "F4_S2_M1_Q7", column2X, 370);
        drawQuestion(canvas11, paint11, "Ano de fabricação", column1X, 410);
        drawText(canvas11, paint11, "F4_S2_M1_Q8", column1X, 430);
        drawQuestion(canvas11, paint11, "Ano do modelo", column2X, 410);
        drawText(canvas11, paint11, "F4_S2_M1_Q9", column2X, 430);
        drawQuestion(canvas11, paint11, "Nº do Chassi", column1X, 470);
        drawText(canvas11, paint11, "F4_S2_M1_Q10", column1X, 490);
        drawQuestion(canvas11, paint11, "RENAVAM", column2X, 470);
        drawText(canvas11, paint11, "F4_S2_M1_Q11", column2X, 490);
        drawQuestion(canvas11, paint11, "Veículo é da empresa?", column1X, 530);
        drawText(canvas11, paint11, "F4_S2_M1_Q12", column1X, 550);

        drawTitle(canvas11, paint11, "Dados da empresa", column1X, 590);
        drawQuestion(canvas11, paint11, "Razão social", column1X, 630);
        drawText(canvas11, paint11, "F4_S2_M2_Q2", column1X, 650);
        drawQuestion(canvas11, paint11, "CNPJ", column1X, 690);
        drawText(canvas11, paint11, "F4_S2_M2_Q3", column1X, 710);

        pdfDocument.finishPage(page11);

        /* Página 12 */
        PdfDocument.PageInfo pageInfo12 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 12).create();
        PdfDocument.Page page12 = pdfDocument.startPage(pageInfo12);

        Canvas canvas12 = page12.getCanvas();
        Paint paint12 = new Paint();
        paint12.setColor(Color.parseColor(pdfColorBlue));
        paint12.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas12, paint12);

        drawTitle(canvas12, paint12, "Dados do proprietário", column1X, 150);
        drawQuestion(canvas12, paint12, "Nome", column1X, 190);
        drawText(canvas12, paint12, "F4_S2_M3_Q2", column1X, 210);
        drawQuestion(canvas12, paint12, "CPF", column1X, 250);
        drawText(canvas12, paint12, "F4_S2_M3_Q3", column1X, 270);
        drawQuestion(canvas12, paint12, "Data nascimento", column2X, 250);
        drawText(canvas12, paint12, "F4_S2_M3_Q4", column2X, 270);
        drawQuestion(canvas12, paint12, "O proprietário é o condutor?", column1X, 310);
        drawText(canvas12, paint12, "F4_S2_M3_Q5", column1X, 330);

        paint12.setColor(Color.GRAY);
        canvas12.drawLine(column1X, 350, 540, 350, paint12);

        drawTitle(canvas12, paint12, "Endereço", column1X, 390);
        drawQuestion(canvas12, paint12, "CEP", column1X, 430);
        drawText(canvas12, paint12, "F4_S2_M4_Q2", column1X, 450);
        drawQuestion(canvas12, paint12, "Lograd.", column1X, 490);
        drawText(canvas12, paint12, "F4_S2_M4_Q3", column1X, 510);
        drawQuestion(canvas12, paint12, "Número", column1X, 550);
        drawText(canvas12, paint12, "F4_S2_M4_Q4", column1X, 570);
        drawQuestion(canvas12, paint12, "Complemento", column2X, 550);
        drawText(canvas12, paint12, "F4_S2_M4_Q5", column2X, 570);
        drawQuestion(canvas12, paint12, "Bairro", column1X, 610);
        drawText(canvas12, paint12, "F4_S2_M4_Q6", column1X, 630);
        drawQuestion(canvas12, paint12, "Cidade", column2X, 610);
        drawText(canvas12, paint12, "F4_S2_M4_Q7", column2X, 630);
        drawQuestion(canvas12, paint12, "UF", column1X, 670);
        drawText(canvas12, paint12, "F4_S2_M4_Q8", column1X, 690);
        drawQuestion(canvas12, paint12, "O proprietário é o condutor?", column2X, 670);
        drawText(canvas12, paint12, "F4_S2_M4_Q9", column2X, 690);

        pdfDocument.finishPage(page12);

        /* Página 13 */
        PdfDocument.PageInfo pageInfo13 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 13).create();
        PdfDocument.Page page13 = pdfDocument.startPage(pageInfo13);

        Canvas canvas13 = page13.getCanvas();
        Paint paint13 = new Paint();
        paint13.setColor(Color.parseColor(pdfColorBlue));
        paint13.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas13, paint13);

        drawTitle(canvas13, paint13, "Endereço do proprietário", column1X, 150);
        drawQuestion(canvas13, paint13, "CEP", column1X, 190);
        drawText(canvas13, paint13, "F4_S2_M5_Q2", column1X, 210);
        drawQuestion(canvas13, paint13, "Lograd.", column1X, 250);
        drawText(canvas13, paint13, "F4_S2_M5_Q3", column1X, 270);
        drawQuestion(canvas13, paint13, "Número", column1X, 310);
        drawText(canvas13, paint13, "F4_S2_M5_Q4", column1X, 330);
        drawQuestion(canvas13, paint13, "Complemento", column2X, 310);
        drawText(canvas13, paint13, "F4_S2_M5_Q5", column2X, 330);
        drawQuestion(canvas13, paint13, "Bairro", column1X, 370);
        drawText(canvas13, paint13, "F4_S2_M5_Q6", column1X, 390);
        drawQuestion(canvas13, paint13, "Cidade", column2X, 370);
        drawText(canvas13, paint13, "F4_S2_M5_Q7", column2X, 390);
        drawQuestion(canvas13, paint13, "UF", column1X, 430);
        drawText(canvas13, paint13, "F4_S2_M5_Q8", column1X, 450);
        drawQuestion(canvas13, paint13, "Nº CNH", column2X, 430);
        drawText(canvas13, paint13, "F4_S2_M5_Q9", column2X, 450);
        drawQuestion(canvas13, paint13, "Validade", column1X, 490);
        drawText(canvas13, paint13, "F4_S2_M5_Q10", column1X, 510);

        paint13.setColor(Color.GRAY);
        canvas13.drawLine(column1X, 530, 540, 530, paint13);

        drawTitle(canvas13, paint13, "Dados do condutor", column1X, 570);
        drawQuestion(canvas13, paint13, "Nome", column1X, 610);
        drawText(canvas13, paint13, "F4_S2_M6_Q2", column1X, 630);
        drawQuestion(canvas13, paint13, "CPF", column1X, 670);
        drawText(canvas13, paint13, "F4_S2_M6_Q3", column1X, 690);
        drawQuestion(canvas13, paint13, "Data de nascimento", column2X, 670);
        drawText(canvas13, paint13, "F4_S2_M6_Q4", column2X, 690);
        drawQuestion(canvas13, paint13, "Nº CNH", column1X, 730);
        drawText(canvas13, paint13, "F4_S2_M6_Q5", column1X, 750);
        drawQuestion(canvas13, paint13, "Validade", column2X, 730);
        drawText(canvas13, paint13, "F4_S2_M6_Q6", column2X, 750);

        pdfDocument.finishPage(page13);

        /* Página 14 */
        PdfDocument.PageInfo pageInfo14 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 14).create();
        PdfDocument.Page page14 = pdfDocument.startPage(pageInfo14);

        Canvas canvas14 = page14.getCanvas();
        Paint paint14 = new Paint();
        paint14.setColor(Color.parseColor(pdfColorBlue));
        paint14.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas14, paint14);

        drawTitle(canvas14, paint14, "Endereço do condutor", column1X, 150);
        drawQuestion(canvas14, paint14, "CEP", column1X, 190);
        drawText(canvas14, paint14, "F4_S2_M7_Q2", column1X, 210);
        drawQuestion(canvas14, paint14, "Lograd.", column1X, 250);
        drawText(canvas14, paint14, "F4_S2_M7_Q3", column1X, 270);
        drawQuestion(canvas14, paint14, "Número", column1X, 310);
        drawText(canvas14, paint14, "F4_S2_M7_Q4", column1X, 330);
        drawQuestion(canvas14, paint14, "Complemento", column2X, 310);
        drawText(canvas14, paint14, "F4_S2_M7_Q5", column2X, 330);
        drawQuestion(canvas14, paint14, "Bairro", column1X, 370);
        drawText(canvas14, paint14, "F4_S2_M7_Q6", column1X, 390);
        drawQuestion(canvas14, paint14, "Cidade", column2X, 370);
        drawText(canvas14, paint14, "F4_S2_M7_Q7", column2X, 390);
        drawQuestion(canvas14, paint14, "UF", column1X, 430);
        drawText(canvas14, paint14, "F4_S2_M7_Q8", column1X, 450);

        paint14.setColor(Color.GRAY);
        canvas14.drawLine(column1X, 470, 540, 470, paint14);

        drawTitle(canvas14, paint14, "Danos", column1X, 510);
        drawImage(canvas14, paint14, R.drawable.carro, 40, 530, carWidth, carHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q1", 40, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q2", 40 + checkboxWidth, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q3", 40 + (checkboxWidth * 2), 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q4", 40, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q5", 40 + checkboxWidth, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas14, paint14, "F4_S2_M8_Q6", 40 + (checkboxWidth * 2), 530 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page14);

        /* Página 15 */
        PdfDocument.PageInfo pageInfo15 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 15).create();
        PdfDocument.Page page15 = pdfDocument.startPage(pageInfo15);

        Canvas canvas15 = page15.getCanvas();
        Paint paint15 = new Paint();
        paint15.setColor(Color.parseColor(pdfColorBlue));
        paint15.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas15, paint15);

        drawRectWithText(canvas15, paint15, "Veículo 4", 40, 150, 500, 40);

        drawQuestion(canvas15, paint15, "Nº de ordem", column1X, 210);
        drawText(canvas15, paint15, "F4_S3_M1_Q2", column1X, 230);
        drawQuestion(canvas15, paint15, "Placa", column2X, 210);
        drawText(canvas15, paint15, "F4_S3_M1_Q3", column2X, 230);

        paint15.setColor(Color.GRAY);
        canvas15.drawLine(column1X, 250, 540, 250, paint15);

        drawQuestion(canvas15, paint15, "Marca/Modelo", column1X, 290);
        drawText(canvas15, paint15, "F4_S3_M1_Q4", column1X, 310);
        drawQuestion(canvas15, paint15, "Tipo do veículo", column2X, 290);
        drawText(canvas15, paint15, "F4_S3_M1_Q5", column2X, 310);
        drawQuestion(canvas15, paint15, "Cor predominante", column1X, 350);
        drawText(canvas15, paint15, "F4_S3_M1_Q6", column1X, 370);
        drawQuestion(canvas15, paint15, "UF", column2X, 350);
        drawText(canvas15, paint15, "F4_S3_M1_Q7", column2X, 370);
        drawQuestion(canvas15, paint15, "Ano de fabricação", column1X, 410);
        drawText(canvas15, paint15, "F4_S3_M1_Q8", column1X, 430);
        drawQuestion(canvas15, paint15, "Ano do modelo", column2X, 410);
        drawText(canvas15, paint15, "F4_S3_M1_Q9", column2X, 430);
        drawQuestion(canvas15, paint15, "Nº do Chassi", column1X, 470);
        drawText(canvas15, paint15, "F4_S3_M1_Q10", column1X, 490);
        drawQuestion(canvas15, paint15, "RENAVAM", column2X, 470);
        drawText(canvas15, paint15, "F4_S3_M1_Q11", column2X, 490);
        drawQuestion(canvas15, paint15, "Veículo é da empresa?", column1X, 530);
        drawText(canvas15, paint15, "F4_S3_M1_Q12", column1X, 550);

        drawTitle(canvas15, paint15, "Dados da empresa", column1X, 590);
        drawQuestion(canvas15, paint15, "Razão social", column1X, 630);
        drawText(canvas15, paint15, "F4_S3_M2_Q2", column1X, 650);
        drawQuestion(canvas15, paint15, "CNPJ", column1X, 690);
        drawText(canvas15, paint15, "F4_S3_M2_Q3", column1X, 710);

        pdfDocument.finishPage(page15);

        /* Página 16 */
        PdfDocument.PageInfo pageInfo16 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 16).create();
        PdfDocument.Page page16 = pdfDocument.startPage(pageInfo16);

        Canvas canvas16 = page16.getCanvas();
        Paint paint16 = new Paint();
        paint16.setColor(Color.parseColor(pdfColorBlue));
        paint16.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas16, paint16);

        drawTitle(canvas16, paint16, "Dados do proprietário", column1X, 150);
        drawQuestion(canvas16, paint16, "Nome", column1X, 190);
        drawText(canvas16, paint16, "F4_S3_M3_Q2", column1X, 210);
        drawQuestion(canvas16, paint16, "CPF", column1X, 250);
        drawText(canvas16, paint16, "F4_S3_M3_Q3", column1X, 270);
        drawQuestion(canvas16, paint16, "Data nascimento", column2X, 250);
        drawText(canvas16, paint16, "F4_S3_M3_Q4", column2X, 270);
        drawQuestion(canvas16, paint16, "O proprietário é o condutor?", column1X, 310);
        drawText(canvas16, paint16, "F4_S3_M3_Q5", column1X, 330);

        paint16.setColor(Color.GRAY);
        canvas16.drawLine(column1X, 350, 540, 350, paint16);

        drawTitle(canvas16, paint16, "Endereço", column1X, 390);
        drawQuestion(canvas16, paint16, "CEP", column1X, 430);
        drawText(canvas16, paint16, "F4_S3_M4_Q2", column1X, 450);
        drawQuestion(canvas16, paint16, "Lograd.", column1X, 490);
        drawText(canvas16, paint16, "F4_S3_M4_Q3", column1X, 510);
        drawQuestion(canvas16, paint16, "Número", column1X, 550);
        drawText(canvas16, paint16, "F4_S3_M4_Q4", column1X, 570);
        drawQuestion(canvas16, paint16, "Complemento", column2X, 550);
        drawText(canvas16, paint16, "F4_S3_M4_Q5", column2X, 570);
        drawQuestion(canvas16, paint16, "Bairro", column1X, 610);
        drawText(canvas16, paint16, "F4_S3_M4_Q6", column1X, 630);
        drawQuestion(canvas16, paint16, "Cidade", column2X, 610);
        drawText(canvas16, paint16, "F4_S3_M4_Q7", column2X, 630);
        drawQuestion(canvas16, paint16, "UF", column1X, 670);
        drawText(canvas16, paint16, "F4_S3_M4_Q8", column1X, 690);
        drawQuestion(canvas16, paint16, "O proprietário é o condutor?", column2X, 670);
        drawText(canvas16, paint16, "F4_S3_M4_Q9", column2X, 690);

        pdfDocument.finishPage(page16);

        /* Página 17 */
        PdfDocument.PageInfo pageInfo17 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 17).create();
        PdfDocument.Page page17 = pdfDocument.startPage(pageInfo17);

        Canvas canvas17 = page17.getCanvas();
        Paint paint17 = new Paint();
        paint17.setColor(Color.parseColor(pdfColorBlue));
        paint17.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas17, paint17);

        drawTitle(canvas17, paint17, "Endereço do proprietário", column1X, 150);
        drawQuestion(canvas17, paint17, "CEP", column1X, 190);
        drawText(canvas17, paint17, "F4_S3_M5_Q2", column1X, 210);
        drawQuestion(canvas17, paint17, "Lograd.", column1X, 250);
        drawText(canvas17, paint17, "F4_S3_M5_Q3", column1X, 270);
        drawQuestion(canvas17, paint17, "Número", column1X, 310);
        drawText(canvas17, paint17, "F4_S3_M5_Q4", column1X, 330);
        drawQuestion(canvas17, paint17, "Complemento", column2X, 310);
        drawText(canvas17, paint17, "F4_S3_M5_Q5", column2X, 330);
        drawQuestion(canvas17, paint17, "Bairro", column1X, 370);
        drawText(canvas17, paint17, "F4_S3_M5_Q6", column1X, 390);
        drawQuestion(canvas17, paint17, "Cidade", column2X, 370);
        drawText(canvas17, paint17, "F4_S3_M5_Q7", column2X, 390);
        drawQuestion(canvas17, paint17, "UF", column1X, 430);
        drawText(canvas17, paint17, "F4_S3_M5_Q8", column1X, 450);
        drawQuestion(canvas17, paint17, "Nº CNH", column2X, 430);
        drawText(canvas17, paint17, "F4_S3_M5_Q9", column2X, 450);
        drawQuestion(canvas17, paint17, "Validade", column1X, 490);
        drawText(canvas17, paint17, "F4_S3_M5_Q10", column1X, 510);

        paint17.setColor(Color.GRAY);
        canvas17.drawLine(column1X, 530, 540, 530, paint17);

        drawTitle(canvas17, paint17, "Dados do condutor", column1X, 570);
        drawQuestion(canvas17, paint17, "Nome", column1X, 610);
        drawText(canvas17, paint17, "F4_S3_M6_Q2", column1X, 630);
        drawQuestion(canvas17, paint17, "CPF", column1X, 670);
        drawText(canvas17, paint17, "F4_S3_M6_Q3", column1X, 690);
        drawQuestion(canvas17, paint17, "Data de nascimento", column2X, 670);
        drawText(canvas17, paint17, "F4_S3_M6_Q4", column2X, 690);
        drawQuestion(canvas17, paint17, "Nº CNH", column1X, 730);
        drawText(canvas17, paint17, "F4_S3_M6_Q5", column1X, 750);
        drawQuestion(canvas17, paint17, "Validade", column2X, 730);
        drawText(canvas17, paint17, "F4_S3_M6_Q6", column2X, 750);

        pdfDocument.finishPage(page17);

        /* Página 18 */
        PdfDocument.PageInfo pageInfo18 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 18).create();
        PdfDocument.Page page18 = pdfDocument.startPage(pageInfo18);

        Canvas canvas18 = page18.getCanvas();
        Paint paint18 = new Paint();
        paint18.setColor(Color.parseColor(pdfColorBlue));
        paint18.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas18, paint18);

        drawTitle(canvas18, paint18, "Endereço do condutor", column1X, 150);
        drawQuestion(canvas18, paint18, "CEP", column1X, 190);
        drawText(canvas18, paint18, "F4_S3_M7_Q2", column1X, 210);
        drawQuestion(canvas18, paint18, "Lograd.", column1X, 250);
        drawText(canvas18, paint18, "F4_S3_M7_Q3", column1X, 270);
        drawQuestion(canvas18, paint18, "Número", column1X, 310);
        drawText(canvas18, paint18, "F4_S3_M7_Q4", column1X, 330);
        drawQuestion(canvas18, paint18, "Complemento", column2X, 310);
        drawText(canvas18, paint18, "F4_S3_M7_Q5", column2X, 330);
        drawQuestion(canvas18, paint18, "Bairro", column1X, 370);
        drawText(canvas18, paint18, "F4_S3_M7_Q6", column1X, 390);
        drawQuestion(canvas18, paint18, "Cidade", column2X, 370);
        drawText(canvas18, paint18, "F4_S3_M7_Q7", column2X, 390);
        drawQuestion(canvas18, paint18, "UF", column1X, 430);
        drawText(canvas18, paint18, "F4_S3_M7_Q8", column1X, 450);

        paint18.setColor(Color.GRAY);
        canvas18.drawLine(column1X, 470, 540, 470, paint18);

        drawTitle(canvas18, paint18, "Danos", column1X, 510);
        drawImage(canvas18, paint18, R.drawable.carro, 40, 530, carWidth, carHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q1", 40, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q2", 40 + checkboxWidth, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q3", 40 + (checkboxWidth * 2), 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q4", 40, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q5", 40 + checkboxWidth, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas18, paint18, "F4_S3_M8_Q6", 40 + (checkboxWidth * 2), 530 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page18);

        /* Página 19 */
        PdfDocument.PageInfo pageInfo19 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 19).create();
        PdfDocument.Page page19 = pdfDocument.startPage(pageInfo19);

        Canvas canvas19 = page19.getCanvas();
        Paint paint19 = new Paint();
        paint19.setColor(Color.parseColor(pdfColorBlue));
        paint19.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas19, paint19);

        drawRectWithText(canvas19, paint19, "Veículo 5", 40, 150, 500, 40);

        drawQuestion(canvas19, paint19, "Nº de ordem", column1X, 210);
        drawText(canvas19, paint19, "F4_S4_M1_Q2", column1X, 230);
        drawQuestion(canvas19, paint19, "Placa", column2X, 210);
        drawText(canvas19, paint19, "F4_S4_M1_Q3", column2X, 230);

        paint19.setColor(Color.GRAY);
        canvas19.drawLine(column1X, 250, 540, 250, paint19);

        drawQuestion(canvas19, paint19, "Marca/Modelo", column1X, 290);
        drawText(canvas19, paint19, "F4_S4_M1_Q4", column1X, 310);
        drawQuestion(canvas19, paint19, "Tipo do veículo", column2X, 290);
        drawText(canvas19, paint19, "F4_S4_M1_Q5", column2X, 310);
        drawQuestion(canvas19, paint19, "Cor predominante", column1X, 350);
        drawText(canvas19, paint19, "F4_S4_M1_Q6", column1X, 370);
        drawQuestion(canvas19, paint19, "UF", column2X, 350);
        drawText(canvas19, paint19, "F4_S4_M1_Q7", column2X, 370);
        drawQuestion(canvas19, paint19, "Ano de fabricação", column1X, 410);
        drawText(canvas19, paint19, "F4_S4_M1_Q8", column1X, 430);
        drawQuestion(canvas19, paint19, "Ano do modelo", column2X, 410);
        drawText(canvas19, paint19, "F4_S4_M1_Q9", column2X, 430);
        drawQuestion(canvas19, paint19, "Nº do Chassi", column1X, 470);
        drawText(canvas19, paint19, "F4_S4_M1_Q10", column1X, 490);
        drawQuestion(canvas19, paint19, "RENAVAM", column2X, 470);
        drawText(canvas19, paint19, "F4_S4_M1_Q11", column2X, 490);
        drawQuestion(canvas19, paint19, "Veículo é da empresa?", column1X, 530);
        drawText(canvas19, paint19, "F4_S4_M1_Q12", column1X, 550);

        drawTitle(canvas19, paint19, "Dados da empresa", column1X, 590);
        drawQuestion(canvas19, paint19, "Razão social", column1X, 630);
        drawText(canvas19, paint19, "F4_S4_M2_Q2", column1X, 650);
        drawQuestion(canvas19, paint19, "CNPJ", column1X, 690);
        drawText(canvas19, paint19, "F4_S4_M2_Q3", column1X, 710);

        pdfDocument.finishPage(page19);

        /* Página 20 */
        PdfDocument.PageInfo pageInfo20 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 20).create();
        PdfDocument.Page page20 = pdfDocument.startPage(pageInfo20);

        Canvas canvas20 = page20.getCanvas();
        Paint paint20 = new Paint();
        paint20.setColor(Color.parseColor(pdfColorBlue));
        paint20.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas20, paint20);

        drawTitle(canvas20, paint20, "Dados do proprietário", column1X, 150);
        drawQuestion(canvas20, paint20, "Nome", column1X, 190);
        drawText(canvas20, paint20, "F4_S4_M3_Q2", column1X, 210);
        drawQuestion(canvas20, paint20, "CPF", column1X, 250);
        drawText(canvas20, paint20, "F4_S4_M3_Q3", column1X, 270);
        drawQuestion(canvas20, paint20, "Data nascimento", column2X, 250);
        drawText(canvas20, paint20, "F4_S4_M3_Q4", column2X, 270);
        drawQuestion(canvas20, paint20, "O proprietário é o condutor?", column1X, 310);
        drawText(canvas20, paint20, "F4_S4_M3_Q5", column1X, 330);

        paint20.setColor(Color.GRAY);
        canvas20.drawLine(column1X, 350, 540, 350, paint20);

        drawTitle(canvas20, paint20, "Endereço", column1X, 390);
        drawQuestion(canvas20, paint20, "CEP", column1X, 430);
        drawText(canvas20, paint20, "F4_S4_M4_Q2", column1X, 450);
        drawQuestion(canvas20, paint20, "Lograd.", column1X, 490);
        drawText(canvas20, paint20, "F4_S4_M4_Q3", column1X, 510);
        drawQuestion(canvas20, paint20, "Número", column1X, 550);
        drawText(canvas20, paint20, "F4_S4_M4_Q4", column1X, 570);
        drawQuestion(canvas20, paint20, "Complemento", column2X, 550);
        drawText(canvas20, paint20, "F4_S4_M4_Q5", column2X, 570);
        drawQuestion(canvas20, paint20, "Bairro", column1X, 610);
        drawText(canvas20, paint20, "F4_S4_M4_Q6", column1X, 630);
        drawQuestion(canvas20, paint20, "Cidade", column2X, 610);
        drawText(canvas20, paint20, "F4_S4_M4_Q7", column2X, 630);
        drawQuestion(canvas20, paint20, "UF", column1X, 670);
        drawText(canvas20, paint20, "F4_S4_M4_Q8", column1X, 690);
        drawQuestion(canvas20, paint20, "O proprietário é o condutor?", column2X, 670);
        drawText(canvas20, paint20, "F4_S4_M4_Q9", column2X, 690);

        pdfDocument.finishPage(page20);

        /* Página 21 */
        PdfDocument.PageInfo pageInfo21 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 21).create();
        PdfDocument.Page page21 = pdfDocument.startPage(pageInfo21);

        Canvas canvas21 = page21.getCanvas();
        Paint paint21 = new Paint();
        paint21.setColor(Color.parseColor(pdfColorBlue));
        paint21.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas21, paint21);

        drawTitle(canvas21, paint21, "Endereço do proprietário", column1X, 150);
        drawQuestion(canvas21, paint21, "CEP", column1X, 190);
        drawText(canvas21, paint21, "F4_S4_M5_Q2", column1X, 210);
        drawQuestion(canvas21, paint21, "Lograd.", column1X, 250);
        drawText(canvas21, paint21, "F4_S4_M5_Q3", column1X, 270);
        drawQuestion(canvas21, paint21, "Número", column1X, 310);
        drawText(canvas21, paint21, "F4_S4_M5_Q4", column1X, 330);
        drawQuestion(canvas21, paint21, "Complemento", column2X, 310);
        drawText(canvas21, paint21, "F4_S4_M5_Q5", column2X, 330);
        drawQuestion(canvas21, paint21, "Bairro", column1X, 370);
        drawText(canvas21, paint21, "F4_S4_M5_Q6", column1X, 390);
        drawQuestion(canvas21, paint21, "Cidade", column2X, 370);
        drawText(canvas21, paint21, "F4_S4_M5_Q7", column2X, 390);
        drawQuestion(canvas21, paint21, "UF", column1X, 430);
        drawText(canvas21, paint21, "F4_S4_M5_Q8", column1X, 450);
        drawQuestion(canvas21, paint21, "Nº CNH", column2X, 430);
        drawText(canvas21, paint21, "F4_S4_M5_Q9", column2X, 450);
        drawQuestion(canvas21, paint21, "Validade", column1X, 490);
        drawText(canvas21, paint21, "F4_S4_M5_Q10", column1X, 510);

        paint21.setColor(Color.GRAY);
        canvas21.drawLine(column1X, 530, 540, 530, paint21);

        drawTitle(canvas21, paint21, "Dados do condutor", column1X, 570);
        drawQuestion(canvas21, paint21, "Nome", column1X, 610);
        drawText(canvas21, paint21, "F4_S4_M6_Q2", column1X, 630);
        drawQuestion(canvas21, paint21, "CPF", column1X, 670);
        drawText(canvas21, paint21, "F4_S4_M6_Q3", column1X, 690);
        drawQuestion(canvas21, paint21, "Data de nascimento", column2X, 670);
        drawText(canvas21, paint21, "F4_S4_M6_Q4", column2X, 690);
        drawQuestion(canvas21, paint21, "Nº CNH", column1X, 730);
        drawText(canvas21, paint21, "F4_S4_M6_Q5", column1X, 750);
        drawQuestion(canvas21, paint21, "Validade", column2X, 730);
        drawText(canvas21, paint21, "F4_S4_M6_Q6", column2X, 750);

        pdfDocument.finishPage(page21);

        /* Página 22 */
        PdfDocument.PageInfo pageInfo22 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 22).create();
        PdfDocument.Page page22 = pdfDocument.startPage(pageInfo22);

        Canvas canvas22 = page22.getCanvas();
        Paint paint22 = new Paint();
        paint22.setColor(Color.parseColor(pdfColorBlue));
        paint22.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas22, paint22);

        drawTitle(canvas22, paint22, "Endereço do condutor", column1X, 150);
        drawQuestion(canvas22, paint22, "CEP", column1X, 190);
        drawText(canvas22, paint22, "F4_S4_M7_Q2", column1X, 210);
        drawQuestion(canvas22, paint22, "Lograd.", column1X, 250);
        drawText(canvas22, paint22, "F4_S4_M7_Q3", column1X, 270);
        drawQuestion(canvas22, paint22, "Número", column1X, 310);
        drawText(canvas22, paint22, "F4_S4_M7_Q4", column1X, 330);
        drawQuestion(canvas22, paint22, "Complemento", column2X, 310);
        drawText(canvas22, paint22, "F4_S4_M7_Q5", column2X, 330);
        drawQuestion(canvas22, paint22, "Bairro", column1X, 370);
        drawText(canvas22, paint22, "F4_S4_M7_Q6", column1X, 390);
        drawQuestion(canvas22, paint22, "Cidade", column2X, 370);
        drawText(canvas22, paint22, "F4_S4_M7_Q7", column2X, 390);
        drawQuestion(canvas22, paint22, "UF", column1X, 430);
        drawText(canvas22, paint22, "F4_S4_M7_Q8", column1X, 450);

        paint22.setColor(Color.GRAY);
        canvas22.drawLine(column1X, 470, 540, 470, paint22);

        drawTitle(canvas22, paint22, "Danos", column1X, 510);
        drawImage(canvas22, paint22, R.drawable.carro, 40, 530, carWidth, carHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q1", 40, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q2", 40 + checkboxWidth, 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q3", 40 + (checkboxWidth * 2), 530, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q4", 40, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q5", 40 + checkboxWidth, 530 + checkboxHeight, checkboxWidth, checkboxHeight);
        drawCheckbox(canvas22, paint22, "F4_S4_M8_Q6", 40 + (checkboxWidth * 2), 530 + checkboxHeight, checkboxWidth, checkboxHeight);

        pdfDocument.finishPage(page22);

        /* Página 23 */
        PdfDocument.PageInfo pageInfo23 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 23).create();
        PdfDocument.Page page23 = pdfDocument.startPage(pageInfo23);

        Canvas canvas23 = page23.getCanvas();
        Paint paint23 = new Paint();
        paint23.setColor(Color.parseColor(pdfColorBlue));
        paint23.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas23, paint23);

        drawTitle(canvas23, paint23, "Testemunha 1", column1X, 150);
        drawQuestion(canvas23, paint23, "Nome", column1X, 190);
        drawText(canvas23, paint23, "F5_S1_Q2", column1X, 210);
        drawQuestion(canvas23, paint23, "CPF", column1X, 250);
        drawText(canvas23, paint23, "F5_S1_Q3", column1X, 270);
        drawQuestion(canvas23, paint23, "Celular", column2X, 250);
        drawText(canvas23, paint23, "F5_S1_Q4", column2X, 270);

        paint23.setColor(Color.GRAY);
        canvas23.drawLine(column1X, 290, 540, 290, paint23);

        drawTitle(canvas23, paint23, "Testemunha 2", column1X, 330);
        drawQuestion(canvas23, paint23, "Nome", column1X, 370);
        drawText(canvas23, paint23, "F5_S2_Q2", column1X, 390);
        drawQuestion(canvas23, paint23, "CPF", column1X, 430);
        drawText(canvas23, paint23, "F5_S2_Q3", column1X, 450);
        drawQuestion(canvas23, paint23, "Celular", column2X, 430);
        drawText(canvas23, paint23, "F5_S2_Q4", column2X, 450);

        paint23.setColor(Color.GRAY);
        canvas23.drawLine(column1X, 470, 540, 470, paint23);

        drawTitle(canvas23, paint23, "Testemunha 3", column1X, 510);
        drawQuestion(canvas23, paint23, "Nome", column1X, 550);
        drawText(canvas23, paint23, "F5_S3_Q2", column1X, 570);
        drawQuestion(canvas23, paint23, "CPF", column1X, 610);
        drawText(canvas23, paint23, "F5_S3_Q3", column1X, 630);
        drawQuestion(canvas23, paint23, "Celular", column2X, 610);
        drawText(canvas23, paint23, "F5_S3_Q4", column2X, 630);

        paint23.setColor(Color.GRAY);
        canvas23.drawLine(column1X, 650, 540, 650, paint23);

        drawTitle(canvas23, paint23, "Testemunha 4", column1X, 690);
        drawQuestion(canvas23, paint23, "Nome", column1X, 730);
        drawText(canvas23, paint23, "F5_S3_Q2", column1X, 750);
        drawQuestion(canvas23, paint23, "CPF", column1X, 790);
        drawText(canvas23, paint23, "F5_S3_Q3", column1X, 810);
        drawQuestion(canvas23, paint23, "Celular", column2X, 790);
        drawText(canvas23, paint23, "F5_S3_Q4", column2X, 810);

        pdfDocument.finishPage(page23);

        /* Página 24 */
        PdfDocument.PageInfo pageInfo24 = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 24).create();
        PdfDocument.Page page24 = pdfDocument.startPage(pageInfo24);

        Canvas canvas24 = page24.getCanvas();
        Paint paint24 = new Paint();
        paint24.setColor(Color.parseColor(pdfColorBlue));
        paint24.setTextSize(42);
        //
        TextPaint paint24_2 = new TextPaint();
        paint24_2.setColor(Color.parseColor(pdfColorBlue));
        paint24_2.setTextSize(42);

        /* Cabeçalho */
        drawHeader(canvas24, paint24);

        drawTitle(canvas24, paint24, "Detalhes do fato", column1X, 150);
        drawMultiText(canvas24, paint24_2, "F6_S1_Q2", column1X, 170, 500);

        pdfDocument.finishPage(page24);

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
    private void drawImage(Canvas canvas, Paint paint, int image, int x, int y, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        canvas.drawBitmap(scaledBitmap, x, y, paint);
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

    public Uri stringToUri(String contentUriString) {
        return Uri.parse(contentUriString);
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