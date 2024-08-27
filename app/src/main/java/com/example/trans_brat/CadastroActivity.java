package com.example.trans_brat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {
    private boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText inputNome = findViewById(R.id.input_name); // Input de nome
        EditText inputMatricula = findViewById(R.id.input_matricula); // Input de matrícula
        EditText inputSenha = findViewById(R.id.input_password); // Input de senha
        Button buttonCadastro = findViewById(R.id.button_cadastro); // Botão de confirmar cadastro

        /* Ao clicar no ícone de exibir senha */
        inputSenha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (inputSenha.getRight() - inputSenha.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility(inputSenha);
                        return true;
                    }
                }
                return false;
            }
        });

        /* Ao clicar no botão de confirmar cadastro */
        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Obtendo valores dos inputs de matrícula e senha */
                validateCadastro(inputNome, inputMatricula, inputSenha);
            }
        });

        /* Alterando para a tela de login */
        Button buttonCadastroRedirect = findViewById(R.id.cadastro_redirect);
        buttonCadastroRedirect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /* Função para validar cadastro */
    private void validateCadastro(EditText inputNome, EditText inputMatricula, EditText inputSenha) {
        /* Obtendo textos dos inputs */
        String nome = inputNome.getText().toString().trim();
        String matricula = inputMatricula.getText().toString().trim();
        String senha = inputSenha.getText().toString().trim();

        /* Se o nome não for informado */
        if (nome.isEmpty()) {
            Toast.makeText(CadastroActivity.this, "Insira o seu nome", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputNome);
            return;
        }
        removeInputErrorBorders(new EditText[] {inputNome, inputMatricula, inputSenha});

        /* Se a matrícula não for informada */
        if (matricula.isEmpty()) {
            Toast.makeText(CadastroActivity.this, "Insira a sua matrícula", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputMatricula);
            return;
        }

        /* Verifica se a matrícula tem o mínimo de caracteres necessários e se são apenas números */
        int matriculaRequired = 12;
        if (matricula.length() < matriculaRequired) {
            Toast.makeText(CadastroActivity.this, "Sua matrícula precisa ter " + matriculaRequired + " caracteres", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputMatricula);
            return;
        }
        removeInputErrorBorders(new EditText[] {inputNome, inputMatricula, inputSenha});

        /* Se a senha não for informada */
        if (senha.length() == 0) {
            Toast.makeText(CadastroActivity.this, "Insira a sua senha", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputSenha);
            return;
        }

        /* Verifica se a senha tem no mínimo 8 caracteres */
        int senhaRequired = 8;
        if (senha.length() < 8) {
            Toast.makeText(CadastroActivity.this, "Sua senha precisa ter " + senhaRequired + " caracteres", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputSenha);
            return;
        }
        removeInputErrorBorders(new EditText[] {inputNome, inputMatricula, inputSenha});

        /* Criando cadastro no banco de dados*/
        //registerUser(nome, matricula, senha);

        Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(CadastroActivity.this).toBundle());
        finish();
    }

    /* Função para adicionar borda vermelha em um input */
    public void setInputErrorBorder(EditText editText) {
        editText.setBackgroundResource(R.drawable.edit_text_login_error);
        Drawable drawableRight = editText.getCompoundDrawables()[2];
        if (drawableRight != null) {
            drawableRight.setTint(getResources().getColor(R.color.red));
        }
    }

    /* Função para remover borda vermelha em um input */
    public void removeInputErrorBorder(EditText editText) {
        editText.setBackgroundResource(R.drawable.edit_text);
        Drawable drawableRight = editText.getCompoundDrawables()[2];
        if (drawableRight != null) {
            drawableRight.setTint(getResources().getColor(R.color.black));
        }
    }

    /* Função para remover bordas vermelhas de todos os input */
    public void removeInputErrorBorders(EditText[] editTexts) {
        for(EditText editText : editTexts) {
            removeInputErrorBorder(editText);
        }
    }

    /* Função para visualizar senha quando o usuário clicar no ícone à direita */
    private void togglePasswordVisibility(EditText editText) {
        if (isPasswordVisible) {
            /* Ocultando senha */
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_eye_closed, 0);
        } else {
            /* Mostrando senha */
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_eye_open, 0);
        }
        isPasswordVisible = !isPasswordVisible;

        /* Colocando o cursor no final do texto */
        editText.setSelection(editText.getText().length());
    }

    /* Função para adicionar um cadastro ao banco de dados */
    private void registerUser(String nome, String matricula, String senha) {
        final String nomeContent = nome.trim();
        final String matriculaContent = matricula.trim();
        final String senhaContent = senha.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://YOUR_SERVER_URL/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CadastroActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nome", nomeContent);
                params.put("matricula", matriculaContent);
                params.put("senha", senhaContent);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}