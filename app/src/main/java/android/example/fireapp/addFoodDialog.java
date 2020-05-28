package android.example.fireapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/*
 *This class adds food to a restaurant's menu.
 *@date 22.05.2020
 *@author Group 3C
 */

public class addFoodDialog extends AppCompatDialogFragment {

    //Properties

    private EditText etName;
    private EditText etIngredients;
    private EditText etPrice;
    private addFoodListener listener;


    //onCreateDialog

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_food_dialog, null);

        builer.setView(view).setTitle("Add Dish")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Add To Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();
                String ingredients = etIngredients.getText().toString();
                String price = etPrice.getText().toString();
                listener.applyTexts(name, ingredients, price);
            }
        });

        etName = view.findViewById(R.id.etNameFood);
        etIngredients = view.findViewById(R.id.etIngrediends);
        etPrice = view.findViewById(R.id.etPrice);
        return builer.create();
    }

    //Methods

    /**
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (addFoodListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement addFoodlistener");
        }
    }

    /**
     * interface to be used in foodDialog.
     */
    public interface addFoodListener{
        void applyTexts(String name, String ingredients, String price);
    }
}
