package fr.oni.cookbook.dialog.edit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Recipe;

public class EditIngredientDialogFragment extends DialogFragment {

    private EditText editText;
    private int positionIngredient;
    private EditDialogListener listener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Data data = (Data) getActivity().getApplicationContext();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.edit_ingredient_dialog_layout, null);
        final int position = data.getPosition();
        final Recipe recipe = data.getRecipes().get(position);

        editText = (EditText) v.findViewById(R.id.edit_ingredient_field);
        positionIngredient = getArguments().getInt(StringConstant.KEY_POSITION_INGREDIENT);
        editText.setText(recipe.getIngredients().get(positionIngredient).getName());

        builder.setView(v);
        builder.setTitle(R.string.ingredient_edit_title);
        builder.setPositiveButton(R.string.edit_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recipe.getIngredients().get(positionIngredient).setName(editText.getText().toString());
                listener.onCloseDialog();
                EditIngredientDialogFragment.this.getDialog().dismiss();
            }
        });
        builder.setNegativeButton(R.string.edit_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCloseDialog();
                EditIngredientDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }


    public void setListener(EditDialogListener listener) {
        this.listener = listener;
    }


}
