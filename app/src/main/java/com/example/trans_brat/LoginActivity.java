package com.example.trans_brat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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

public class LoginActivity extends AppCompatActivity {
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setExitTransition(new Slide(Gravity.RIGHT));
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText inputMatricula = findViewById(R.id.input_matricula); // Input de matrícula
        EditText inputSenha = findViewById(R.id.input_password); // Input de senha
        Button buttonLogin = findViewById(R.id.button_login); // Botão de confirmar login

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

        /* Ao clicar no botão de confirmar login */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Obtendo valores dos inputs de matrícula e senha */
                validateLogin(inputMatricula, inputSenha);

            }
        });

        /* Alterando para a tela de cadastro */
        Button buttonLoginRedirect = findViewById(R.id.login_redirect);
        buttonLoginRedirect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                finish();
            }
        });
    }

    /* Função para validar login */
    private void validateLogin(EditText inputMatricula, EditText inputSenha) {
        /* Obtendo textos dos inputs */
        String matricula = inputMatricula.getText().toString().trim();
        String senha = inputSenha.getText().toString().trim();

        /* Se a matrícula não for informada */
        if (matricula.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Insira a sua matrícula", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputMatricula);
            return;
        }

        /* Verifica se a matrícula tem o mínimo de caracteres necessários e se são apenas números */
        int matriculaRequired = 12;
        if (matricula.length() < matriculaRequired) {
            Toast.makeText(LoginActivity.this, "Sua matrícula precisa ter " + matriculaRequired + " caracteres", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputMatricula);
            return;
        }
        removeInputErrorBorders(new EditText[] {inputMatricula, inputSenha});

        /* Se a senha não for informada */
        if (senha.length() == 0) {
            Toast.makeText(LoginActivity.this, "Insira a sua senha", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputSenha);
            return;
        }

        /* Verifica se a senha tem no mínimo 8 caracteres */
        int senhaRequired = 8;
        if (senha.length() < 8) {
            Toast.makeText(LoginActivity.this, "Sua senha precisa ter " + senhaRequired + " caracteres", Toast.LENGTH_SHORT).show();
            setInputErrorBorder(inputSenha);
            return;
        }
        removeInputErrorBorders(new EditText[] {inputMatricula, inputSenha});

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
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
        editText.setBackgroundResource(R.drawable.edit_text_login);
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
}