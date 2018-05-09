package com.z_soft.z_finance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.z_soft.z_finance.R;
import com.z_soft.z_finance.core.enums.OperationType;
import com.z_soft.z_finance.core.interfaces.Source;
import com.z_soft.z_finance.core.interfaces.TreeNode;

// отвеачает за добавление и редактирование категории
public class EditSourceActivity<T extends Source> extends AppCompatActivity{


    public final static String NODE_OBJECT = "com.z_soft.z_finance.activities.EditSourceActivity.NodeObject";
    public final static int REQUEST_NODE_EDIT = 101;
    public final static int REQUEST_NODE_ADD = 102;
    public final static int REQUEST_NODE_ADD_CHILD = 103;

    private Toolbar toolbar;
    private TextView etName;
    private ImageView imgSave;
    private Spinner spSourceType;
    private ArrayAdapter<OperationType> spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_source);

        toolbar = findViewById(R.id.toolbar_edit_source);
        etName = findViewById(R.id.et_source_name);
        imgSave = findViewById(R.id.img_node_save);
        spSourceType = findViewById(R.id.sp_source_type);

        spAdapter = new ArrayAdapter<OperationType>(this, android.R.layout.simple_spinner_item, OperationType.getList().subList(0,2));// нам нужны только доход и расход для категорий

        spSourceType.setAdapter(spAdapter);

        setSupportActionBar(toolbar);

        final T node = (T) getIntent().getSerializableExtra(NODE_OBJECT); // получаем переданный объект для редактирования

        etName.setText(node.getName());


        if (node.getOperationType()!=null) {// при редактировании объекта - это поле будет заполнено
            spSourceType.setEnabled(false);
            spSourceType.setSelection(OperationType.getList().indexOf(node.getOperationType()));
        }


        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newName = etName.getText().toString();

                // не давать сохранять пустое значение
                if (newName.trim().length() == 0) {
                    Toast.makeText(EditSourceActivity.this, R.string.enter_value, Toast.LENGTH_SHORT).show();
                    return;
                }

                // чтобы лишний раз не сохранять - проверяем, были ли изменены данные, если нет, то при сохранении просто закрываем активити
                if (!newName.equals(node.getName())
                    //TODO также проверять иконку
                        ) {
                    node.setName(newName);
                    node.setOperationType((OperationType) spSourceType.getSelectedItem());

                    Intent intent = new Intent();
                    intent.putExtra(NODE_OBJECT, node);// сюда попадает уже отредактированный объект, который нужно сохранить в БД
                    setResult(RESULT_OK, intent);

                }

                finish();// закрыть активити

            }
        });

    }

}